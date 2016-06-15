package pageElement;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by A on 2016/1/28.
 */
public class pageLogin {
    WebDriver driver;

    public pageLogin(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(className = "buttonSign")
    public WebElement signButton;

    @FindBy(id = "username")
    public WebElement userName;

    @FindBy(id = "password")
    public WebElement passWord;

    @FindBy(id = "goSubmit")
    public WebElement login;

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

}

