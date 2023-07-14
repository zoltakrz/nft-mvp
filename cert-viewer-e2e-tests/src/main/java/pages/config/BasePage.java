package pages.config;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public abstract class BasePage {

    public Page page;

    public String getUrl() {
        return page.url();
    }

    @Step("Going back one page")
    public void goBack() {
        page.goBack();
    }

    @Step("Validation if element is visible")
    public boolean isElementWithTextVisible(String textOfElement) {
        String selector = "text=" + "'" + textOfElement + "'";
        return page.locator(selector).nth(0).isVisible();
    }

    @Step("Waiting for alert")
    public void waitForAlert() {
        page.getByRole(AriaRole.ALERT).waitFor();
    }
}
