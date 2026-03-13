clean Java prototype that implements AI-like locator auto-healing using:

Selenium WebDriver

JSoup

Levenshtein similarity for scoring

This is a practical design you can integrate into your Java test framework quickly.

1️⃣ Concept of Java Locator Auto-Healing

Workflow:

Try original locator
│
▼
Element not found
│
▼
Parse DOM
│
▼
Find similar elements
│
▼
Score candidates
│
▼
Pick best match
│
▼
Retry action
2️⃣ Maven Dependencies

Add to your pom.xml:

<dependencies>

    <!-- Selenium -->
    <dependency>
        <groupId>org.seleniumhq.selenium</groupId>
        <artifactId>selenium-java</artifactId>
        <version>4.18.1</version>
    </dependency>

    <!-- JSoup HTML parser -->
    <dependency>
        <groupId>org.jsoup</groupId>
        <artifactId>jsoup</artifactId>
        <version>1.17.2</version>
    </dependency>

</dependencies>
3️⃣ Example Test (Simulate Broken Locator)
WebDriver driver = new ChromeDriver();

driver.get("https://example.com");

WebElement element = SmartFinder.findElement(driver,
By.xpath("//button[text()='Login']"));

element.click();

If locator breaks, SmartFinder will heal it.

4️⃣ Smart Finder Implementation
public class SmartFinder {

    public static WebElement findElement(WebDriver driver, By locator) {

        try {
            return driver.findElement(locator);
        } catch (NoSuchElementException e) {

            System.out.println("Locator broken. Attempting auto-healing...");

            return healLocator(driver, locator);
        }
    }

}
5️⃣ Healing Engine
private static WebElement healLocator(WebDriver driver, By locator) {

    String html = driver.getPageSource();

    Document doc = Jsoup.parse(html);

    Elements candidates = doc.select("button");

    Element bestMatch = null;
    double bestScore = 0;

    String targetText = "Login";   // original expected text

    for (Element e : candidates) {

        String candidateText = e.text();

        double score = similarity(targetText, candidateText);

        if (score > bestScore) {
            bestScore = score;
            bestMatch = e;
        }
    }

    if (bestMatch != null) {

        String healedXpath = generateXpath(bestMatch);

        System.out.println("Healed locator: " + healedXpath);

        return driver.findElement(By.xpath(healedXpath));
    }

    throw new NoSuchElementException("Unable to heal locator");
}
6️⃣ Text Similarity (Simple AI scoring)
private static double similarity(String s1, String s2) {

    int distance = levenshtein(s1, s2);

    int maxLength = Math.max(s1.length(), s2.length());

    return 1.0 - ((double) distance / maxLength);
}
7️⃣ Levenshtein Distance Implementation
public static int levenshtein(String a, String b) {

    int[][] dp = new int[a.length()+1][b.length()+1];

    for (int i=0;i<=a.length();i++)
        dp[i][0]=i;

    for (int j=0;j<=b.length();j++)
        dp[0][j]=j;

    for (int i=1;i<=a.length();i++){
        for (int j=1;j<=b.length();j++){

            int cost = a.charAt(i-1)==b.charAt(j-1)?0:1;

            dp[i][j] = Math.min(
                    Math.min(dp[i-1][j]+1,
                             dp[i][j-1]+1),
                    dp[i-1][j-1]+cost);
        }
    }

    return dp[a.length()][b.length()];
}
8️⃣ Generate XPath for Healed Element
private static String generateXpath(Element element) {

    String tag = element.tagName();

    String text = element.text();

    return "//" + tag + "[text()='" + text + "']";
}
9️⃣ Example Scenario

Original locator:

//button[text()='Login']

Developer changes button to:

Sign In

Your system finds:

Login vs Sign In similarity

Then generates:

//button[text()='Sign In']

And retries automatically.

🔟 How to Make It Much Smarter (Next Step)

Your Python version likely already does some of these:

Feature scoring:

score =
0.3 tag match
0.3 text similarity
0.2 class similarity
0.1 id similarity
0.1 DOM position

Example:

score += textSimilarity * 0.4;
score += classSimilarity * 0.2;
score += idSimilarity * 0.2;
score += tagMatch * 0.2;

This dramatically improves accuracy.