import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;


class GeekBrainsTests extends AbstractTest{

    @Test
    void loginTest(){
        driver.get("http://test-stand.gb.ru/login");
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("form#login input[type='text']"))).sendKeys(getGB_username());
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("form#login input[type='password']"))).sendKeys(getGB_password());

        WebElement loginButton = driver.findElement(By.cssSelector("form#login button"));
        loginButton.click();
        wait.until(ExpectedConditions.invisibilityOf(loginButton));

        WebElement userNameLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(getGB_username())));
        assertEquals(String.format("Hello, %s", getGB_username()), userNameLink.getText().replace("\n", " ").trim());

    }

    @Test
    void NewGroupTest() throws InterruptedException, IOException {
        loginTest();
        Long unicLong = System.currentTimeMillis();
        WebElement createButton = driver.findElement(By.id("create-btn"));
        createButton.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"upsert-item\"]/div[1]/label/input"))).sendKeys("Ivan"+ unicLong);
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id='upsert-item']/div[5]/label/input"))).sendKeys("Ivan"+ unicLong);
        WebElement formButton = driver.findElement(
                By.xpath("//*[@id=\"upsert-item\"]/div[8]/button"));
        formButton.click();
        WebElement quitButton = driver.findElement(
                By.xpath("//*[@id=\"app\"]/main/div/div/div[3]/div[2]/div/div[1]/button"));
        quitButton.click();
        wait.until(ExpectedConditions.invisibilityOf(quitButton));
        Thread.sleep(2000);
        WebElement firstFieldOfName = driver.findElement(By.xpath("//*[@id=\"app\"]/main/div/div/div[1]/div[1]/table/tbody/tr[1]/td[2]"));
        System.out.println(firstFieldOfName.getText());
        assertEquals("Ivan"+ unicLong, firstFieldOfName.getText());
        TakesScreenshot screenshotTaker = (TakesScreenshot) driver;
        File screenshot = screenshotTaker.getScreenshotAs(OutputType.FILE);
        File destination = new File("src\\test\\resources\\screenshot.png");
        Files.copy(screenshot.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

}
