import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverExample {
    public static void main(String[] args){
        System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://google.com");
        System.out.println("Page title:"+ driver.getTitle());
        driver.quit();

    }
}
