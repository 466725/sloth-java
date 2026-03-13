package webpages.selfhealing;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LocatorHealerTest {

    @Test
    public void shouldHealByIdAndTextSimilarity() {
        String html = """
                <html>
                  <body>
                    <button id="login_button" class="primary">Login</button>
                    <button id="signup_button">Sign Up</button>
                  </body>
                </html>
                """;

        HealingHints hints = HealingHints.builder()
                .expectedText("Login")
                .build();

        HealedSelector healed = LocatorHealer.healHtml(html, "#login", hints, 200, 0.20);
        Assert.assertNotNull(healed, "Expected a healed selector");
        Assert.assertTrue(healed.selector().contains("login_button"), "Healed selector should target the login button");
        Assert.assertTrue(healed.score() >= 0.20, "Expected a score above the test threshold");
    }
}

