package webpages.selfhealing;

/**
 * Result of a healing attempt.
 */
public record HealedSelector(
        String selector,
        double score,
        String matchedTag,
        String matchedId,
        String matchedClasses,
        String matchedText
) {
}

