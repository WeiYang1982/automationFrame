package procedure;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageElement.pageLogin;

/**
 * Created by A on 2016/1/28.
 */
public class login {



    public String login(final WebDriver webDriver, String URL, String userName, String passWord){
        String errorMessage = "";
        final pageLogin page = new pageLogin(webDriver);
        WebDriverWait wait = new WebDriverWait(webDriver,10);
        webDriver.get(URL);
        page.signButton.click();
        page.userName.clear();
        page.userName.sendKeys(userName);
        page.passWord.clear();
        page.passWord.sendKeys(passWord);
        page.login.click();
        try {
            wait.until(ExpectedConditions.titleContains("首页"));
            if (page.logoIsExist()){
                return "logo is exist!";
            }else {
                return "logo is not exist!";
            }
        }catch (TimeoutException no){
            try {
                errorMessage = page.getErrorMessage();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return errorMessage;
    }

}
