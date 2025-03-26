package testcases;

import com.github.javafaker.Faker;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DemoblazeTests {


    static Playwright pw;
    static Browser browser;
    static BrowserContext context;
    static Page page;
    String url = "https://www.demoblaze.com/";
    String testUser = "TestNameUser";
    String testPass = "TestPass";


    @BeforeEach
    void launch() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        pw = Playwright.create();
        browser = pw.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height));
        page = context.newPage();
        page.setDefaultTimeout(3000);
    }

    @Test
    void registrationTest() {
        Faker faker = new Faker();
        String name = faker.name().username();
        String pass = faker.internet().password();
        page.navigate(url);
        page.locator("#signin2").click();
        page.waitForSelector("#sign-username");
        page.locator("#sign-username").fill(name);
        page.locator("#sign-password").fill(pass);

        page.onDialog(dialog -> {
            assertEquals(dialog.message(), "Sign up successful.");
            dialog.accept();
        });

        page.locator("xpath=//div[@class='modal-footer']//button[contains(text(), 'Sign up')]").click();
    }

    @Test
    void loginTest() {

        page.navigate(url);
        page.locator("#login2").click();
        page.waitForSelector("#loginusername");
        page.locator("#loginusername").fill(testUser);
        page.locator("#loginpassword").fill(testPass);

        page.locator("xpath=//div[@class='modal-footer']//button[contains(text(), 'Log in')]").click();

        Response response = page.waitForResponse("https://api.demoblaze.com/login", () -> {
            page.locator("xpath=//div[@class='modal-footer']//button[contains(text(), 'Log in')]").click();

        });

        assertEquals(200, response.status());

        Locator welcomeMess = page.locator("#nameofuser");
        welcomeMess.waitFor(new Locator.WaitForOptions().setTimeout(2000));
        assertTrue(welcomeMess.isVisible());
        String welcomeName = welcomeMess.textContent();
        assertTrue(welcomeName.contains(testUser));


    }


    @AfterEach
    void tearDown() {
        page.close();
        pw.close();
    }


}
