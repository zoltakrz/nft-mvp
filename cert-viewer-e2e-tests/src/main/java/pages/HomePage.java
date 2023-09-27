package pages;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.capgemini.mrchecker.test.core.logger.BFLogger;
import pages.config.BasePage;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
    private final String uriStringSelector = "(//pre[@class='transaction-input-text pre-wrap']//code)[3]";

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

    public Locator email() {
        return page.getByLabel("Email Address");
    }

    public Locator getcert() {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("GET CERTIFICATES"));
    }

    @Step
    public HomePage fillEmailAndPressGet(String test) {
        email().fill(test);
        getcert().click();
        return this;
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
    public HomePage clickLinkButton(int row) {
        page.locator("//table[contains(@class,'MuiTable-root css-19mj8md')]/tbody[1]/tr["+ row +"]/td[5]/a[1]").click();
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
            String regex = "data:application/json;base64,([A-Za-z0-9+/=]+)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(value);

            if (matcher.find()) {
                String base64Value = matcher.group(1);
                BFLogger.logInfo("Copied URI string:" + base64Value);
                return base64Value;
            } else {
                BFLogger.logInfo("Element not found");
            }
        }
        return value;

    }

    @Step("Count rows in table")
    public Integer countRows() {
        ElementHandle table = page.waitForSelector("#root > main > div > div > table");
        List<ElementHandle> trList = table.querySelectorAll("tr");
        BFLogger.logInfo(String.valueOf(trList.size()));
        return trList.size();
    }

}