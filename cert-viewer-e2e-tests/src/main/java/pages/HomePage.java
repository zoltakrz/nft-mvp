package pages;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.JSHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.capgemini.mrchecker.test.core.logger.BFLogger;
import pages.config.BasePage;


@EqualsAndHashCode(callSuper = true)
@Data
public class HomePage extends BasePage {


    // URL
    private final String url = "https://certviewer.azurewebsites.net";


    // SIGN UP
    private final String getCertificatesButton = "'GET CERTIFICATES'";
    private final String emailInput = "#email";
    private final String transactionHashLink = "[data-test='transaction_hash_link']";
    private final String uriCopyButton = "//table/tbody/tr[5]/td[3]/span";
    private final String uriStringSelector = "body > div.layout-container > main > section > section > div:nth-child(5) > div > div > div > div:nth-child(3) > table > tbody > tr:nth-child(5) > td:nth-child(3) > pre";

    public HomePage(Page page) {
        this.page = page;
    }

    public void load() {
        BFLogger.logInfo("Loading URL:" +  url);
        page.navigate(url);
    }

    public Locator getColumnInTable(Integer index) {
        String columnInTable = "#root > main > div > div > table > tbody > tr > td:nth-child(" + index + ")";
        BFLogger.logInfo("Getting column in table with index:" +  index);
        return page.locator(columnInTable);
    }

    @Step("Clicking GET CERTIFICATES Button")
    public HomePage clickGetCertificatesButton() {
        page.click(getCertificatesButton);
        BFLogger.logInfo("Clicking GET CERTIFICATES button");
        return this;
    }

    @Step("Filling username field")
    public HomePage fillEmail(String email) {
        page.fill(emailInput, email);
        BFLogger.logInfo("Filling email field with:" + email);
        return this;
    }

    @Step("Click on link button")
    public HomePage clickLinkButton() {
        page.getByText("Link").click();
        BFLogger.logInfo("Clicking on link button");
        return this;
    }
    @Step("Click on transaction hash link")
    public HomePage clickTransactionHashLink() {
        page.click(transactionHashLink);
        BFLogger.logInfo("Clicking on transaction hash link");
        return this;
    }

    @Step("Copy uri string")
    public String copyUriString() {
        ElementHandle element = page.querySelector(uriStringSelector);
        String value = null;
        if (element != null) {
            value = element.innerText();
            BFLogger.logInfo("Copied URI string:" +  value);
            return value;
        } else {
            BFLogger.logInfo("Element not found");
        }
        return value;
    }

}