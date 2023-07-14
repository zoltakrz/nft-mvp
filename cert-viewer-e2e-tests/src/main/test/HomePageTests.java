import helpful.UserData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import config.BaseUITest;
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
    @Test
    void shouldLogin() {
        homePage.fillEmail(testMail)
                .clickGetCertificatesButton();
        assertThat(homePage).hasTitle("Capgemini Certificate Viewer");
    }
    @Test
    void shouldLoginWithAlert() {
        String email = "test@capgemini.com";
        homePage.fillEmail(email)
                .clickGetCertificatesButton();
        assertThat("//*[@id=\"root\"]/main/div/h1[2]").hasText("Certificates for that email not found. Currently only Capgemini FS Poland organization is included.");
    }

    @Test
    void shouldNotLogin() {
        String email = "test@test.com";
        homePage.fillEmail(email)
                .clickGetCertificatesButton();
        assertThat("//*[@id=\"email-helper-text\"]").hasText("Invalid Capgemini email");
    }

    @Test
    void checkIfLabelsDisplayCorrectValues() {
        homePage.fillEmail(testMail)
                .clickGetCertificatesButton()
                .clickLinkButton()
                .clickTransactionHashLink()
                .clickUriCopyButton()
                .clickUriCopyButton()
                .clickUriCopyButton()
                .clickUriCopyButton()
                .clickUriCopyButton()
                .validateCopyToClipboard();

        homePage.load();
        homePage.fillEmail(testMail)
                .clickGetCertificatesButton();

        String clipboardValue = homePage.validateCopyToClipboard();
        UserData userData = decode(clipboardValue);
        assertThat("#root > main > div > div > table > tbody > tr > td:nth-child(1)").hasText(userData.getFirstName());
        assertThat("#root > main > div > div > table > tbody > tr > td:nth-child(2)").hasText(userData.getLastName());
        assertThat("#root > main > div > div > table > tbody > tr > td:nth-child(3)").hasText(userData.getCertType());
        assertThat("#root > main > div > div > table > tbody > tr > td:nth-child(4)").hasText(userData.getCertLevel());



    }
}