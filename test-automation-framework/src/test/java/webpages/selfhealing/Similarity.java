package webpages.selfhealing;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Similarity helpers used by locator self-healing scoring.
 */
public final class Similarity {
    private Similarity() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Normalized Levenshtein similarity in range [0..1].
     */
    public static double levenshteinSimilarity(String a, String b) {
        String s1 = normalize(a);
        String s2 = normalize(b);
        if (s1.isEmpty() && s2.isEmpty()) {
            return 1.0;
        }
        if (s1.isEmpty() || s2.isEmpty()) {
            return 0.0;
        }
        int dist = Levenshtein.distance(s1, s2);
        int maxLength = Math.max(s1.length(), s2.length());
        if (maxLength == 0) {
            return 1.0;
        }
        return clamp01(1.0 - ((double) dist / (double) maxLength));
    }

    /**
     * Jaccard similarity of tokens split on non-alphanumerics.
     */
    public static double tokenJaccard(String a, String b) {
        Set<String> ta = tokens(a);
        Set<String> tb = tokens(b);
        if (ta.isEmpty() && tb.isEmpty()) {
            return 1.0;
        }
        if (ta.isEmpty() || tb.isEmpty()) {
            return 0.0;
        }
        Set<String> inter = new HashSet<>(ta);
        inter.retainAll(tb);
        Set<String> union = new HashSet<>(ta);
        union.addAll(tb);
        return clamp01((double) inter.size() / (double) union.size());
    }

    /**
     * Best-effort text similarity that combines multiple signals.
     */
    public static double textSimilarity(String a, String b) {
        return Math.max(levenshteinSimilarity(a, b), tokenJaccard(a, b));
    }

    public static double classSetSimilarity(Set<String> expected, Set<String> actual) {
        if (expected == null || expected.isEmpty()) {
            return 0.0;
        }
        if (actual == null || actual.isEmpty()) {
            return 0.0;
        }
        Set<String> a = new HashSet<>();
        for (String s : expected) {
            if (s != null && !s.isBlank()) {
                a.add(s.trim().toLowerCase(Locale.ROOT));
            }
        }
        Set<String> b = new HashSet<>();
        for (String s : actual) {
            if (s != null && !s.isBlank()) {
                b.add(s.trim().toLowerCase(Locale.ROOT));
            }
        }
        if (a.isEmpty() || b.isEmpty()) {
            return 0.0;
        }
        Set<String> inter = new HashSet<>(a);
        inter.retainAll(b);
        Set<String> union = new HashSet<>(a);
        union.addAll(b);
        return clamp01((double) inter.size() / (double) union.size());
    }

    public static Set<String> splitClasses(String classAttr) {
        if (classAttr == null || classAttr.isBlank()) {
            return Set.of();
        }
        return Arrays.stream(classAttr.trim().split("\\s+"))
                .filter(s -> !s.isBlank())
                .collect(java.util.stream.Collectors.toUnmodifiableSet());
    }

    private static Set<String> tokens(String s) {
        if (s == null) {
            return Set.of();
        }
        String norm = normalize(s);
        if (norm.isEmpty()) {
            return Set.of();
        }
        return Arrays.stream(norm.split("[^a-z0-9]+"))
                .filter(t -> !t.isBlank())
                .collect(java.util.stream.Collectors.toUnmodifiableSet());
    }

    private static String normalize(String s) {
        if (s == null) {
            return "";
        }
        String trimmed = s.trim().toLowerCase(Locale.ROOT);
        return trimmed.replaceAll("\\s+", " ");
    }

    private static double clamp01(double v) {
        if (v < 0.0) {
            return 0.0;
        }
        if (v > 1.0) {
            return 1.0;
        }
        return v;
    }
}

