package pages;

import com.microsoft.playwright.JSHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pages.config.BasePage;

@EqualsAndHashCode(callSuper = true)
@Data
public class HomePage extends BasePage {

    // URL
    private final String url = "https://certviewer-00.azurewebsites.net";


    // SIGN UP
    private final String getCertificatesButton = "'GET CERTIFICATES'";
    private final String emailInput = "#email";
    private final String transactionHashLink = "[data-test='transaction_hash_link']";
    private final String uriCopyButton = "//table/tbody/tr[5]/td[3]/span";

    public HomePage(Page page) {
        this.page = page;
    }

    public void load() {
        page.navigate(url);
    }

    public Locator getColumnInTable(Integer index) {
        String columnInTable = "#root > main > div > div > table > tbody > tr > td:nth-child(" + index + ")";
        return page.locator(columnInTable);
    }

    @Step("Clicking GET CERTIFICATES Button")
    public HomePage clickGetCertificatesButton() {
        page.click(getCertificatesButton);
        return this;
    }

    @Step("Filling username field")
    public HomePage fillEmail(String email) {
        page.fill(emailInput, email);
        return this;
    }

    @Step("Click on link button")
    public HomePage clickLinkButton() {
        page.getByText("Link").click();
        return this;
    }
    @Step("Click on transaction hash link")
    public HomePage clickTransactionHashLink() {
        page.click(transactionHashLink);
        return this;
    }
    @Step("Click on uri copy button")
    public HomePage clickUriCopyButton() {
        page.click(uriCopyButton);
        return this;
    }

    @Step("Copy a hash link")
    public String validateCopyToClipboard() {
        JSHandle clipboardHandle = page.evaluateHandle("() => navigator.clipboard.readText()");
        String clipboardValue = clipboardHandle.jsonValue().toString();
        clipboardHandle.dispose();
        return clipboardValue;
    }
}