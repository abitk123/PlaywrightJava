package demoblaze.PageObject;

import com.microsoft.playwright.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginPage {
    private final Page page;
    private final Locator username;
    private final Locator password;
    private final Locator loginButton;

    public LoginPage(Page page) {
        this.page = page;
        this.username = page.locator("#loginusername");
        this.password = page.locator("#loginpassword");
        this.loginButton = page.locator("xpath=//div[@class='modal-footer']//button[contains(text(), 'Log in')]");
    }

    public void login(String user, String pass) {
        username.waitFor();
        username.fill(user);
        password.fill(pass);

        Response response = page.waitForResponse("https://api.demoblaze.com/login", () -> {
            loginButton.click();
        });

        assertEquals(200, response.status());
    }

    public Locator getWelcomeMessage() {
        return page.locator("#nameofuser");
    }
}
