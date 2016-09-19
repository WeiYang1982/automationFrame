package pageElement;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.WaitForElement;

/**
 * Created by A on 2016/9/9.
 */
public class PageLogin extends LoadableComponent<PageLogin>{
    WebDriver driver;
    String URL;

    public PageLogin(WebDriver driver, String URL){
        this.driver=driver;
        this.URL = URL;
        PageFactory.initElements(driver,this);
        driver.get(URL);
    }

    @FindBy (className = "buttonSign")
    public WebElement buttonSign;

    @FindBy (id = "username")
    public WebElement userName;

    public void setUsername(String username){
        try {
            WaitForElement.waitForElement(driver,By.id("username")).sendKeys(username);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @FindBy (id = "password")
    public WebElement password;

    @FindBy (id = "goSubmit")
    public WebElement loginButton;

    @FindBy(className = "xbnLogo")
    private WebElement logo;

    @FindBy(id = "username-error")
    private WebElement errorMessage;

    public String getErrorMessage(){
        WebDriverWait wait = new WebDriverWait(driver,10);
        try {
            wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver){
                    boolean loadcomplete = errorMessage.isDisplayed();
                    return loadcomplete;
                }
            });
            return errorMessage.getText();
        }catch (Exception e){
            e.printStackTrace();
            return e.toString();
        }
    }

    public boolean logoIsExist() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        try {
            wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    boolean loadcomplete = logo.isDisplayed();
                    return loadcomplete;
                }
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void load(){
        driver.navigate().refresh();
    }

    @Override
    public void isLoaded(){
        WebDriverWait Wait = new WebDriverWait(driver, 10);
        Wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript(
                        "return document.readyState"
                ).equals("complete");
            }
        });
    }


}
