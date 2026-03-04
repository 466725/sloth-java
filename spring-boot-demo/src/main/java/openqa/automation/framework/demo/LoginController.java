package openqa.automation.framework.demo;

import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class LoginController {

    private static final String VALID_USERNAME = "username";
    private static final String VALID_PASSWORD = "password";

    @PostMapping("/api/login")
    Map<String, String> login(@RequestParam String username, @RequestParam String password) {
        if (VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password)) {
            return Map.of("message", "login successful");
        }
        return Map.of("message", "wrong credentials");
    }
}
