package ui;

import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import helpful.UserData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import ui.config.BaseUITest;
import static helpful.Base64Decoder.decode;


public class HomePageTests extends BaseUITest {

    private HomePage homePage;
    private final String testMail = "joe.fortesting@capgemini.com";

    @BeforeMethod(alwaysRun = true, description = "Prepare HomePage object and load page")
    void setUp() {
        homePage = new HomePage(getContextPage());
        homePage.load();
        assertThat(homePage).hasTitle("Capgemini Certificate Viewer");
    }
    @Test(groups = "Login")
    void shouldLogin() {
        homePage.fillEmail(testMail)
                .clickGetCertificatesButton();
        assertThat(homePage).hasTitle("Capgemini Certificate Viewer");
    }
    @Test(groups = "Login")
    void shouldLoginWithAlert() {
        String email = "test@capgemini.com";
        homePage.fillEmail(email)
                .clickGetCertificatesButton();
        assertThat("//*[@id=\"root\"]/main/div/h1[2]").hasText("Certificates for that email not found. Currently only Capgemini FS Poland organization is included.");
    }

    @Test(groups = "Login")
    void shouldNotLogin() {
        String email = "test@test.com";
        homePage.fillEmail(email)
                .clickGetCertificatesButton();
        assertThat("//*[@id=\"email-helper-text\"]").hasText("Invalid Capgemini email");
    }

    @Test(groups = "Check")
    void checkIfLabelsDisplayCorrectValues() {
        int randomRow = random.nextInt(homePage.countRows() + 1);
        homePage.fillEmail(testMail)
                .clickGetCertificatesButton()
                .clickLinkButton(randomRow)
                .clickTransactionHashLink();
        String uriStringValue = homePage.copyUriString();

        homePage.load();
        homePage.fillEmail(testMail)
                .clickGetCertificatesButton();

        UserData userData = decode(uriStringValue);
        assertThat("//table[contains(@class,'MuiTable-root css-19mj8md')]/tbody[1]/tr[1]/td["+ randomRow+"]").hasText(userData.getFirstName());
        assertThat("//table[contains(@class,'MuiTable-root css-19mj8md')]/tbody[1]/tr[1]/td["+ randomRow+"]").hasText(userData.getLastName());
        assertThat("//table[contains(@class,'MuiTable-root css-19mj8md')]/tbody[1]/tr[1]/td["+ randomRow+"]").hasText(userData.getCertType());
        assertThat("//table[contains(@class,'MuiTable-root css-19mj8md')]/tbody[1]/tr[1]/td["+ randomRow+"]").hasText(userData.getCertLevel());
    }
}