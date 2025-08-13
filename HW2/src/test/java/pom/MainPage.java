package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pom.elements.TableRow;

import java.util.List;

public class MainPage {
    private final WebDriverWait wait;

    @FindBy(css = "nav li.mdc-menu-surface--anchor a")
    private WebElement UsernameLinkInNavBar;

    @FindBy(id = "create-btn")
    private WebElement CreateGroupButton;

    @FindBy(xpath = "//*[@id='upsert-item']/div[5]/label/input")
    private WebElement StudentNameLoginField;


    @FindBy(xpath = "//*[@id='upsert-item']/div[1]/label/input")
    private WebElement StudentNameField;

    @FindBy(css = "form div.submit button")
    private WebElement SubmitButtonOnModalWindow;

    @FindBy(xpath = "//*[@id=\"app\"]/main/div/div/div[3]/div[2]/div/div[1]/button")
    private WebElement CloseCreateFormIcon;

    @FindBy(xpath = "//table[@aria-label='Dummies list']/tbody/tr")
    public List<WebElement> RowsInTable;


    public MainPage(WebDriver driver, WebDriverWait wait){
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public WebElement waitAndGetNameByText(String name){
        String xpath = String.format("//table[@aria-label='Dummies list']/tbody//td[text()='%s']", name);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    public void createStudent(String studentName){
        wait.until(ExpectedConditions.visibilityOf(CreateGroupButton)).click();
        wait.until(ExpectedConditions.visibilityOf(StudentNameLoginField)).sendKeys(studentName);
        wait.until(ExpectedConditions.visibilityOf(StudentNameField)).sendKeys(studentName);
        SubmitButtonOnModalWindow.click();
        waitAndGetNameByText(studentName);
    }

    public String getUsernameLabelText(){
        return wait.until(ExpectedConditions.visibilityOf(UsernameLinkInNavBar))
                .getText().replace("\n", "");
    }

    public void closeCreateForm(){
        CloseCreateFormIcon.click();
        wait.until(ExpectedConditions.invisibilityOf(CloseCreateFormIcon));
    }

    public void clickTrashIconOnStudentWithName(String name){
        getRowByName(name).clickTrashIcon();
    }

    public void clickRestoreFromTrashIconOnStudentWithName(String name){
        getRowByName(name).clickRestoreFromTrashIcon();
    }

    public String getStatusWithName(String name){
        return getRowByName(name).getStatus();
    }

    private TableRow getRowByName(String name){
        return RowsInTable.stream()
                .map(TableRow::new)
                .filter(row -> row.getName().equals(name))
                .findFirst().orElseThrow();
    }






}
