package demoblaze.PageObject;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MainPage {
    private final Page page;
    private final Locator signUp;
    private final Locator login;

    public MainPage(Page page) {
        this.page = page;
        this.signUp = page.locator("#signin2");
        this.login = page.locator("#login2");
    }

    public void gotoRegister() {
        signUp.click();
    }

    public void gotoLogin() {
        login.click();
    }
}
