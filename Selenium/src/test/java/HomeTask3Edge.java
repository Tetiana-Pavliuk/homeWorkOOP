import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HomeTask3Edge {

    private static WebDriver driver;
    private JavascriptExecutor js = (JavascriptExecutor) driver;

    @BeforeAll
    public static void setUp() {
        String exePath = "C:\\Windows\\System32\\MicrosoftWebDriver.exe";
        System.setProperty("webdriver.edge.driver", exePath);
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void search() throws InterruptedException, IOException {

        boolean elementFound = false;
        String url = "http://google.com";
//        String search = "абракадабра";
//        String searchElement = "kpi.ua";

        //the result is located  further then 10th page
        //String search = "осциллограф";
        //String searchElement = "www.partsdirect.ru";

        //the result is located on the first page
        String search = "selenium automation testing";
        String searchElement = "www.selenium.dev";

        driver.get(url);
        driver.findElement(By.xpath("//input[@title=\"Search\"]")).clear();
        driver.findElement(By.xpath("//input[@title=\"Search\"]")).sendKeys(search);
        driver.findElement(By.xpath("//input[@value=\"Google Search\"]")).submit();

        int i = 1;
        elementFound = isElementFound(searchElement);
        if (elementFound) {
            System.out.println("The result is found on the page " + i);
        }

        while (!elementFound && driver.findElements(By.xpath("//a[@id=\"pnnext\"]")).size() != 0) {
            driver.findElement(By.xpath("//a[@id=\"pnnext\"]")).click();
            i++;
            elementFound = isElementFound(searchElement);
            if (elementFound) {
                System.out.println("The result is found on the page " + i);
            }
        }
        if (!elementFound) {
            System.out.println("The " + searchElement + "result is not found");
        }
    }

    private boolean isElementFound(String searchElement) throws IOException {
        boolean elementFound = false;
        for (WebElement elem : driver.findElements(By.tagName("cite"))) {
            if (elem.getText().contains(searchElement)) {
                System.out.println(elem.getText());
                js.executeScript("arguments[0].scrollIntoView();", elem);
                makeScr();
                elementFound = true;
                break;
            }
        }
        return elementFound;
    }

    private void makeScr() throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("SearchResult1.png"));
    }

    @AfterAll
    public static void tearDown() {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File("SearchResult.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        driver.quit();
    }
}
