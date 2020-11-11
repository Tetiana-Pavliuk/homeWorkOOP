import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertFalse;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SeleniumWebDriver_HomeTask1 {

    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void loginTest() throws InterruptedException {
        String url = "http://facebook.com";
        String login = "TesterJavaTraining";
        String pass = "q1w2e3r4";

        driver.get(url);
        driver.findElement(By.id("email")).sendKeys(login);
        driver.findElement(By.id("pass")).sendKeys(pass);
        driver.findElement(By.xpath("//*[@type=\"submit\"]")).submit();

        assertFalse(driver.findElement(By.xpath("//*[@type=\"submit\"]")).isDisplayed(), "You are not log in!");
    }

    @AfterAll
    public static void tearDown()
    {
        driver.quit();
    }
}