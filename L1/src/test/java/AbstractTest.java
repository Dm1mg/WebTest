import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public class AbstractTest {
    static Properties prop = new Properties();
    public WebDriver driver;
    public WebDriverWait wait;
    private static String gb_username;
    private static String gb_password;

    @BeforeAll
    static void initTests() throws IOException {
        System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\chromedriver.exe");
        prop.load(new FileInputStream("src/test/resources/stand.properties"));
        gb_username =  prop.getProperty("gb_username");
        gb_password = prop.getProperty("gb_password");

    }
    @BeforeEach
    public void setupTest(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown(){
       // driver.quit();
    }

    public static String getGB_username() {
        return gb_username;
    }

    public static String getGB_password() {
        return gb_password;
    }
}

