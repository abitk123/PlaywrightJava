package testcases;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.SelectOption;

import java.awt.*;

public class Practice {
    public static void main(String[] args) throws InterruptedException {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        Playwright playwright = Playwright.create();
        Browser browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext browserContext = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height));
        Page page = browserContext.newPage();
        page.navigate("https://www.wikipedia.org/");

        page.selectOption("#searchLanguage", "et");

        page.selectOption("#searchLanguage", new SelectOption().setLabel("Eesti"));

        Locator options = page.locator("select option");
        System.out.println(options.count());

        for (int i = 0; i <= options.count(); i++) {

            System.out.println(options.nth(i).textContent() + "------" + options.nth(i).getAttribute("lang"));

        }


    }
}
