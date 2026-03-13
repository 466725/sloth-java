package selfhealing;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hints used by the healing engine to find a "similar" element when a locator breaks.
 */
public record HealingHints(
        String expectedTag,
        String expectedText,
        String expectedId,
        Set<String> expectedClasses,
        Map<String, String> expectedAttributes
) {
    public HealingHints {
        expectedClasses = expectedClasses == null ? Collections.emptySet() : Set.copyOf(expectedClasses);
        expectedAttributes = expectedAttributes == null ? Collections.emptyMap() : Map.copyOf(expectedAttributes);
    }

    public static HealingHints none() {
        return new HealingHints(null, null, null, Collections.emptySet(), Collections.emptyMap());
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Best-effort inference of healing hints from a Playwright selector string.
     *
     * <p>Supported (partial): {@code #id}, {@code .class}, {@code [attr="value"]}, {@code text=...}, {@code :has-text("...")}.
     */
    public static HealingHints inferFromSelector(String selector) {
        if (selector == null || selector.isBlank()) {
            return HealingHints.none();
        }
        String s = selector.trim();
        Builder b = builder();

        if (s.startsWith("#") && s.length() > 1) {
            b.expectedId(s.substring(1));
        }
        if (s.startsWith(".") && s.length() > 1) {
            b.addExpectedClass(s.substring(1));
        }

        // [attr="value"] or [attr='value']
        Matcher attrMatcher = Patterns.ATTR_EQ.matcher(s);
        while (attrMatcher.find()) {
            String attr = attrMatcher.group(1);
            String value = attrMatcher.group(2);
            if (attr != null && value != null) {
                b.putExpectedAttribute(attr, value);
            }
        }

        // text=Login
        Matcher textEq = Patterns.TEXT_EQ.matcher(s);
        if (textEq.find()) {
            b.expectedText(textEq.group(1));
        }

        // :has-text("Login") or :has-text('Login')
        Matcher hasText = Patterns.HAS_TEXT.matcher(s);
        if (hasText.find()) {
            String t = hasText.group(1) != null ? hasText.group(1) : hasText.group(2);
            b.expectedText(t);
        }

        return b.build();
    }

    public HealingHints merge(HealingHints other) {
        if (other == null) {
            return this;
        }
        Builder b = builder();
        b.expectedTag(firstNonBlank(other.expectedTag, this.expectedTag));
        b.expectedText(firstNonBlank(other.expectedText, this.expectedText));
        b.expectedId(firstNonBlank(other.expectedId, this.expectedId));

        LinkedHashSet<String> classes = new LinkedHashSet<>(this.expectedClasses);
        classes.addAll(other.expectedClasses);
        b.expectedClasses(classes);

        LinkedHashMap<String, String> attrs = new LinkedHashMap<>(this.expectedAttributes);
        attrs.putAll(other.expectedAttributes);
        b.expectedAttributes(attrs);
        return b.build();
    }

    private static String firstNonBlank(String a, String b) {
        if (a != null && !a.isBlank()) {
            return a;
        }
        if (b != null && !b.isBlank()) {
            return b;
        }
        return null;
    }

    public static final class Builder {
        private String expectedTag;
        private String expectedText;
        private String expectedId;
        private final LinkedHashSet<String> expectedClasses = new LinkedHashSet<>();
        private final LinkedHashMap<String, String> expectedAttributes = new LinkedHashMap<>();

        private Builder() {
        }

        public Builder expectedTag(String expectedTag) {
            this.expectedTag = expectedTag;
            return this;
        }

        public Builder expectedText(String expectedText) {
            this.expectedText = expectedText;
            return this;
        }

        public Builder expectedId(String expectedId) {
            this.expectedId = expectedId;
            return this;
        }

        public Builder expectedClasses(Set<String> expectedClasses) {
            this.expectedClasses.clear();
            if (expectedClasses != null) {
                for (String c : expectedClasses) {
                    if (c != null && !c.isBlank()) {
                        this.expectedClasses.add(c.trim());
                    }
                }
            }
            return this;
        }

        public Builder addExpectedClass(String clazz) {
            if (clazz != null && !clazz.isBlank()) {
                expectedClasses.add(clazz.trim());
            }
            return this;
        }

        public Builder expectedAttributes(Map<String, String> attrs) {
            this.expectedAttributes.clear();
            if (attrs != null) {
                attrs.forEach(this::putExpectedAttribute);
            }
            return this;
        }

        public Builder putExpectedAttribute(String key, String value) {
            if (key == null || key.isBlank() || value == null || value.isBlank()) {
                return this;
            }
            expectedAttributes.put(key.trim(), value.trim());
            return this;
        }

        public HealingHints build() {
            return new HealingHints(
                    blankToNull(expectedTag),
                    blankToNull(expectedText),
                    blankToNull(expectedId),
                    expectedClasses,
                    expectedAttributes
            );
        }

        private static String blankToNull(String s) {
            if (s == null) {
                return null;
            }
            String trimmed = s.trim();
            return trimmed.isEmpty() ? null : trimmed;
        }
    }

    private static final class Patterns {
        private static final Pattern ATTR_EQ = Pattern.compile("\\[\\s*([a-zA-Z0-9_\\-:]+)\\s*=\\s*['\\\"]([^'\\\"]+)['\\\"]\\s*\\]");
        private static final Pattern TEXT_EQ = Pattern.compile("(?i)\\btext\\s*=\\s*([^\\s]+)");
        private static final Pattern HAS_TEXT = Pattern.compile("(?i):has-text\\((?:\"([^\"]+)\"|'([^']+)')\\)");

        private Patterns() {
        }
    }
}
