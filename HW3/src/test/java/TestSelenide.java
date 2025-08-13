import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import pom.MainPage;

import static org.junit.Assert.assertTrue;

public class TestSelenide {
    @Test
    void testSelenide(){
        System.out.println(SelenideLogger.class.getClassLoader().getResource("selenide.properties"));

    }
    @Test
    void testOpenPage() {

        Selenide.open("https://example.com");
        WebDriver driver = WebDriverRunner.getWebDriver();
        System.out.println("Browser = " + Configuration.browser); // chrome
        System.out.println("Timeout = " + Configuration.timeout); // 10000
    }

}
