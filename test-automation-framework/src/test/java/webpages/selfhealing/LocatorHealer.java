package webpages.selfhealing;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Healing engine: parse DOM, score candidates, and generate a replacement selector.
 *
 * <p>Based on the algorithm described in {@code Self-Healing Framework.md}:
 * parse DOM with JSoup + score with Levenshtein similarity.
 */
public final class LocatorHealer {
    private static final Logger logger = LogManager.getLogger(LocatorHealer.class.getName());

    private LocatorHealer() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static HealedSelector healHtml(String html, String originalSelector, HealingHints hints) {
        return healHtml(html, originalSelector, hints, SelfHealingConfig.maxCandidates(), SelfHealingConfig.minScore());
    }

    public static HealedSelector healHtml(String html, String originalSelector, HealingHints hints, int maxCandidates, double minScore) {
        if (html == null || html.isBlank()) {
            return null;
        }
        HealingHints inferred = HealingHints.inferFromSelector(originalSelector);
        HealingHints effective = (hints == null ? HealingHints.none() : hints).merge(inferred);

        Document doc = Jsoup.parse(html);
        Elements candidates = selectCandidates(doc, effective);

        Scored best = null;
        int scored = 0;

        for (Element e : candidates) {
            if (scored >= maxCandidates) {
                break;
            }
            Scored s = scoreCandidate(e, effective);
            scored++;
            if (s == null) {
                continue;
            }
            if (best == null || s.score > best.score) {
                best = s;
            }
        }

        if (best == null) {
            return null;
        }
        if (best.score < minScore) {
            logger.warn("Self-healing best score below threshold. selector=" + originalSelector + " score=" + best.score);
            return null;
        }

        String healed = generateSelector(best.element);
        if (healed == null || healed.isBlank()) {
            return null;
        }
        return new HealedSelector(
                healed,
                best.score,
                best.element.tagName(),
                safeAttr(best.element, "id"),
                safeAttr(best.element, "class"),
                best.element.text()
        );
    }

    private static Elements selectCandidates(Document doc, HealingHints hints) {
        if (hints.expectedTag() != null && !hints.expectedTag().isBlank()) {
            return doc.select(hints.expectedTag());
        }

        // Prefer interactable controls first; fall back to any element with "signal" attrs.
        Elements controls = doc.select("button,a,input,textarea,select,label,[role=button]");
        if (!controls.isEmpty()) {
            return controls;
        }
        return doc.select("[id],[class],[data-testid],[aria-label],[name]");
    }

    private static Scored scoreCandidate(Element e, HealingHints hints) {
        String candidateId = safeAttr(e, "id");
        String candidateClassAttr = safeAttr(e, "class");
        Set<String> candidateClasses = Similarity.splitClasses(candidateClassAttr);
        String candidateText = normalizeTextForScore(e.text());

        Map<String, Double> parts = new LinkedHashMap<>();

        if (hints.expectedTag() != null) {
            double tagMatch = e.tagName().equalsIgnoreCase(hints.expectedTag()) ? 1.0 : 0.0;
            parts.put("tag", tagMatch);
        }

        if (hints.expectedText() != null) {
            parts.put("text", Similarity.textSimilarity(hints.expectedText(), candidateText));
        }

        if (hints.expectedId() != null) {
            parts.put("id", Similarity.levenshteinSimilarity(hints.expectedId(), candidateId));
        }

        if (hints.expectedClasses() != null && !hints.expectedClasses().isEmpty()) {
            parts.put("class", Similarity.classSetSimilarity(hints.expectedClasses(), candidateClasses));
        }

        if (hints.expectedAttributes() != null && !hints.expectedAttributes().isEmpty()) {
            double attrAvg = averageAttrSimilarity(hints.expectedAttributes(), e);
            parts.put("attrs", attrAvg);
        }

        if (parts.isEmpty()) {
            return null;
        }

        double score = weighted(parts);
        return new Scored(e, score, parts);
    }

    // Weighting inspired by "How to Make It Much Smarter" in Self-Healing Framework.md
    private static double weighted(Map<String, Double> parts) {
        double sum = 0.0;
        double wsum = 0.0;

        for (Map.Entry<String, Double> entry : parts.entrySet()) {
            String k = entry.getKey();
            double v = entry.getValue() == null ? 0.0 : entry.getValue();

            double w = switch (k) {
                case "text" -> 0.4;
                case "class" -> 0.2;
                case "id" -> 0.2;
                case "tag" -> 0.2;
                case "attrs" -> 0.1;
                default -> 0.1;
            };

            sum += (v * w);
            wsum += w;
        }

        if (wsum <= 0.0) {
            return 0.0;
        }
        return sum / wsum;
    }

    private static double averageAttrSimilarity(Map<String, String> expected, Element candidate) {
        List<Double> sims = new ArrayList<>();
        for (Map.Entry<String, String> entry : expected.entrySet()) {
            String attr = entry.getKey();
            String exp = entry.getValue();
            if (attr == null || attr.isBlank() || exp == null || exp.isBlank()) {
                continue;
            }
            String actual = safeAttr(candidate, attr);
            sims.add(Similarity.textSimilarity(exp, actual));
        }
        if (sims.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (double d : sims) {
            sum += d;
        }
        return sum / sims.size();
    }

    private static String generateSelector(Element element) {
        String id = safeAttr(element, "id");
        if (id != null && !id.isBlank()) {
            return "css=[id=\"" + escapeCssString(id) + "\"]";
        }

        for (String attr : List.of("data-testid", "aria-label", "name")) {
            String value = safeAttr(element, attr);
            if (value != null && !value.isBlank()) {
                return "css=[" + attr + "=\"" + escapeCssString(value) + "\"]";
            }
        }

        String cssPath = cssPath(element);
        if (cssPath != null && !cssPath.isBlank()) {
            return "css=" + cssPath;
        }

        String tag = element.tagName();
        String text = normalizeWhitespace(element.text());
        if (text != null && !text.isBlank()) {
            return "xpath=//" + tag + "[normalize-space(.)=" + xpathLiteral(text) + "]";
        }
        return null;
    }

    private static String cssPath(Element element) {
        if (element == null) {
            return null;
        }
        List<String> parts = new ArrayList<>();
        Element cur = element;
        while (cur != null) {
            String tag = cur.tagName();
            if (tag == null || tag.isBlank()) {
                break;
            }

            String id = safeAttr(cur, "id");
            if (id != null && !id.isBlank()) {
                parts.add(tag + "[id=\"" + escapeCssString(id) + "\"]");
                break;
            }

            int nth = nthOfType(cur);
            parts.add(tag + ":nth-of-type(" + nth + ")");

            // Stop at <html> or <body> to avoid overly long selectors.
            if ("html".equalsIgnoreCase(tag) || "body".equalsIgnoreCase(tag)) {
                break;
            }
            cur = cur.parent();
        }

        if (parts.isEmpty()) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = parts.size() - 1; i >= 0; i--) {
            sb.append(parts.get(i));
            if (i != 0) {
                sb.append(" > ");
            }
        }
        return sb.toString();
    }

    private static int nthOfType(Element element) {
        Element parent = element.parent();
        if (parent == null) {
            return 1;
        }
        int pos = 0;
        for (Element child : parent.children()) {
            if (child.tagName().equalsIgnoreCase(element.tagName())) {
                pos++;
            }
            if (child == element) {
                return Math.max(1, pos);
            }
        }
        return 1;
    }

    private static String xpathLiteral(String s) {
        if (s == null) {
            return "''";
        }
        if (!s.contains("'")) {
            return "'" + s + "'";
        }
        if (!s.contains("\"")) {
            return "\"" + s + "\"";
        }
        // concat('foo', "\"", 'bar')
        StringBuilder sb = new StringBuilder("concat(");
        boolean first = true;
        for (int i = 0; i < s.length(); i++) {
            String ch = s.substring(i, i + 1);
            String lit = switch (ch) {
                case "'" -> "\"'\"";
                case "\"" -> "'\"'";
                default -> "'" + ch + "'";
            };
            if (!first) {
                sb.append(",");
            }
            sb.append(lit);
            first = false;
        }
        sb.append(")");
        return sb.toString();
    }

    private static String escapeCssString(String s) {
        // Safe enough for attribute selectors: [attr="..."]
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private static String safeAttr(Element e, String attr) {
        if (e == null || attr == null || attr.isBlank()) {
            return "";
        }
        String v = e.hasAttr(attr) ? e.attr(attr) : "";
        return v == null ? "" : v;
    }

    private static String normalizeTextForScore(String s) {
        if (s == null) {
            return "";
        }
        return normalizeWhitespace(s).toLowerCase(Locale.ROOT);
    }

    private static String normalizeWhitespace(String s) {
        if (s == null) {
            return "";
        }
        return s.trim().replaceAll("\\s+", " ");
    }

    private record Scored(Element element, double score, Map<String, Double> parts) {
    }
}
