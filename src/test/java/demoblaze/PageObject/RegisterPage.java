package demoblaze.PageObject;

import com.microsoft.playwright.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterPage {

    private final Page page;
    private final Locator username;
    private final Locator password;
    private final Locator registerButton;
    private final Locator modalWindow;


    public RegisterPage(Page page) {
        this.page = page;
        this.username = this.page.locator("#sign-username");
        this.password = this.page.locator("#sign-password");
        this.registerButton = this.page.locator("xpath=//div[@class='modal-footer']//button[contains(text(), 'Sign up')]");
        this.modalWindow = this.page.locator("#signInModal .modal-body");

    }

    public void register(String username, String password) {
        this.username.waitFor(new Locator.WaitForOptions().setTimeout(1000));
        this.username.fill(username);
        this.password.fill(password);

        this.page.onDialog(dialog -> {
            assertEquals(dialog.message(), "Sign up successful.");
            dialog.accept();
        });

        this.registerButton.click();
    }

    public Locator getModalWindow() {
        return modalWindow;
    }
}
