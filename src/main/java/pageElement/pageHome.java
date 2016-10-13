package pageElement;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import util.WaitForElement;

import static org.openqa.selenium.By.linkText;

/**
 * Created by A on 2016/2/4.
 */
public class PageHome extends LoadableComponent<PageHome>{
    WebDriver driver;

    public PageHome( WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy( linkText = "图片管理" )
    public WebElement picConfig;

    @FindBy(className = "xbnLogo")
    public WebElement logo;

    protected void load() {

    }

    protected void isLoaded() throws Error {
        try {
            WaitForElement.waitForElement(driver,linkText("图片管理"));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
