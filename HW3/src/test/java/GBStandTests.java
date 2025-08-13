import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pom.LoginPage;
import pom.MainPage;
import pom.ProfilePage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.module.Configuration;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.Properties;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GBStandTests {
    static Properties prop = new Properties();
    public WebDriver driver;
    public WebDriverWait wait;
    public static String gb_username;
    public static String gb_password;
    private LoginPage loginPage;
    private MainPage mainPage;

    @BeforeAll
    static void initTests() throws IOException {
        prop.load(new FileInputStream("src/test/resources/stand.properties"));
        gb_username =  prop.getProperty("gb_username");
        gb_password = prop.getProperty("gb_password");

    }
    @BeforeEach
    public void setupTest(){
        WebDriverManager.chromedriver().setup();
        Selenide.open("http://test-stand.gb.ru/login");
        driver = WebDriverRunner.getWebDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        loginPage = new LoginPage(driver, wait);

    }



    @AfterEach
    public void tearDown(){
        WebDriverRunner.closeWebDriver();
    }


    @Test
    void loginTest(){
        loginPage.login(gb_username, gb_password);
        mainPage = new MainPage(driver, wait);
        assertTrue(mainPage.getUsernameLabelText().contains(gb_username));
    }

    @Test
    public void testAddingGroupOnMainPage() throws IOException {
        loginPage.login(gb_username, gb_password);
        mainPage = new MainPage(driver, wait);
        assertTrue(mainPage.getUsernameLabelText().contains(gb_username));

        String studentTestName = "Student " + System.currentTimeMillis();
        mainPage.createStudent(studentTestName);
        assertTrue(mainPage.waitAndGetNameByText(studentTestName).isDisplayed());
        TakesScreenshot screenshotTaker = (TakesScreenshot) driver;
        File screenshot = screenshotTaker.getScreenshotAs(OutputType.FILE);
        File destination = new File("src\\test\\resources\\screenshot.png");
        Files.copy(screenshot.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    public void incorrectLoginTest(){
        loginPage.loginWithOutUsernameAndPassword();
        assertEquals("Invalid credentials.", loginPage.getErrorMassage());

    }

    @Test
    public void trashButtonCorrectWork(){
        loginPage.login(gb_username, gb_password);
        mainPage = new MainPage(driver, wait);
        assertTrue(mainPage.getUsernameLabelText().contains(gb_username));
        String studentTestName = "Student " + System.currentTimeMillis();
        mainPage.createStudent(studentTestName);
        mainPage.closeCreateForm();
        assertEquals("active", mainPage.getStatusWithName(studentTestName));
        mainPage.clickTrashIconOnStudentWithName(studentTestName);
        assertEquals("inactive", mainPage.getStatusWithName(studentTestName));
        mainPage.clickRestoreFromTrashIconOnStudentWithName(studentTestName);
        assertEquals("active", mainPage.getStatusWithName(studentTestName));
    }

    @Test
    public void testFullNameOnProfilePage(){
        loginPage.login(gb_username, gb_password);
        mainPage = new MainPage(driver, wait);
        assertTrue(mainPage.getUsernameLabelText().contains(gb_username));
        mainPage.clickUserNameLabel();
        mainPage.clickProfileLink();
        ProfilePage profilePage = Selenide.page(ProfilePage.class);
        assertEquals("Afinogenov Mihail", profilePage.getFullNameFromAdditionalInfo());
        assertEquals("Afinogenov Mihail", profilePage.getFullNameFromAvatarSection());
    }





}

