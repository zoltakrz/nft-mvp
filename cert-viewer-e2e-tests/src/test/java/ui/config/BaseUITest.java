package ui.config;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.assertions.PageAssertions;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;
import org.testng.ITestResult;
import io.qameta.allure.Allure;
import pages.config.BasePage;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class BaseUITest {

    private static Playwright playwright;
    private static Browser browser;
    private BrowserContext context;
    private Page page;

    public Random random = new Random();

    @BeforeSuite(alwaysRun = true, description = "Launch browser")
    protected void launchBrowser() {
        playwright = Playwright.create();
        BrowserType browserType = switch (System.getProperty("browser")) {
            case "firefox" -> playwright.firefox();
            case "safari" -> playwright.webkit();
            default -> playwright.chromium();
        };
        // during maven run
        // -Dbrowser=firefox to launch firefox browser.
        // -Dbrowser=safari to launch safari/webkit browser.
        // skip to launch default chromium

        browser = browserType.launch(new BrowserType
                .LaunchOptions()
                .setHeadless(Boolean.parseBoolean(System.getProperty("headless")))
                .setSlowMo(Double.parseDouble(System.getProperty("slowmo"))));

        // during maven run
        // -Dheadless=false to see a browser,
        // -Dslowmo=2000 to wait 2 second for every action
        // you can combine these methods or skip it to invoke default behavior

        // go to systemPropertyVariables in pom.xml to see default variables.
    }

    @BeforeMethod(alwaysRun = true, description = "Create context and page")
    protected void createContextAndPage() {
        List<String> permissions = Arrays.asList("clipboard-read");

        context = browser.newContext(new Browser.NewContextOptions()
                .setStrictSelectors(false)
                .setPermissions(permissions));
        page = context.newPage();
        page.onDialog(Dialog::accept);
    }

    @AfterMethod(alwaysRun = true, description = "Take a screenshot if the test fails")
    protected void screenshotOnFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            byte[] screenshot = page.screenshot();
            ByteArrayInputStream screenshotStream = new ByteArrayInputStream(screenshot);
            Allure.addAttachment("Screenshot", screenshotStream);
        }
    }

    @AfterMethod(alwaysRun = true, dependsOnMethods = "screenshotOnFailure", description = "Close page and context")
    protected void closePageAndContext() {
        page.close();
        context.close();
    }

    @AfterSuite(alwaysRun = true)
    protected void closeBrowser() {
        browser.close();
        playwright.close();
    }

    protected Page getContextPage() {
        return page;
    }

    protected LocatorAssertions assertThat(AriaRole role) {
        return PlaywrightAssertions.assertThat(page.getByRole(role));
    }

    protected LocatorAssertions assertThat(String selector) {
        return PlaywrightAssertions.assertThat(page.locator(selector));
    }


    protected PageAssertions assertThat(BasePage pomPage) {
        return PlaywrightAssertions.assertThat(pomPage.page);
    }
}