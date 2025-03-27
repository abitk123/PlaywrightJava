package demoblaze.testsuite;

import com.github.javafaker.Faker;
import com.microsoft.playwright.*;
import demoblaze.PageObject.LoginPage;
import demoblaze.PageObject.MainPage;
import demoblaze.PageObject.RegisterPage;
import org.junit.jupiter.api.*;


import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DemoblazeTests {


    static Playwright pw;
    static Browser browser;
    static BrowserContext context;
    static Page page;
    String url = "https://www.demoblaze.com/";
    String testUser = "TestNameUser";
    String testPass = "TestPass";
    MainPage mainPage;
    RegisterPage registerPage;
    LoginPage loginPage;
    Faker faker;


    @BeforeEach
    void launch() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        pw = Playwright.create();
        browser = pw.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height));
        page = context.newPage();
        page.navigate(url);

        mainPage = new MainPage(page);
        registerPage = new RegisterPage(page);
        loginPage = new LoginPage(page);


    }

    @Test
    void registrationTest() {
        faker = new Faker();
        String name = faker.name().username();
        String pass = faker.internet().password();

        mainPage.gotoRegister();
        registerPage.register(name, pass);
        assertTrue(registerPage.getModalWindow().isVisible());

    }

    @Test
    void loginTest() {
        mainPage.gotoLogin();
        loginPage.login(testUser, testPass);

        Locator welcomeMess = loginPage.getWelcomeMessage();
        welcomeMess.waitFor();
        assertTrue(welcomeMess.isVisible());
        assertTrue(welcomeMess.textContent().contains(testUser));
    }


    @AfterEach
    void tearDown() {
        page.close();
        pw.close();
    }


}
