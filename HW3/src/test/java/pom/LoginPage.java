package pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class LoginPage {

    private final WebDriverWait wait;

    @FindBy(css = "form#login input[type='text']")
    private WebElement UsernameField;

    @FindBy(css = "form#login input[type='password']")
    private WebElement PasswordField;

    @FindBy(css = "form#login button")
    private WebElement LoginButton;

    @FindBy(xpath = "//*[@id=\"app\"]/main/div/div/div[2]/p[1]")
    public WebElement ErrorMassage;


    public LoginPage(WebDriver driver, WebDriverWait wait) {
        PageFactory.initElements(driver, this);
        this.wait = wait;
    }

    public void login(String username, String password){
        typeUserNameInField(username);
        typePasswordInField(password);
        clickLoginButton();
    }

    public void loginWithOutUsernameAndPassword(){
        typeUserNameInField("");
        typePasswordInField("");
        clickLoginButton();
        wait.until(ExpectedConditions.visibilityOf(ErrorMassage));
    }

    public String getErrorMassage(){
        return ErrorMassage.getText();
    }

    public void typeUserNameInField(String username){
        wait.until(ExpectedConditions.visibilityOf(UsernameField)).sendKeys(username);
    }

    public void typePasswordInField(String password){
        wait.until(ExpectedConditions.visibilityOf(PasswordField)).sendKeys(password);
    }

    public void clickLoginButton(){
        wait.until(ExpectedConditions.visibilityOf(LoginButton)).click();
    }
}
