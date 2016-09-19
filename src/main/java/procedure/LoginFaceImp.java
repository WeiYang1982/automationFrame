package procedure;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import pageElement.PageLogin;

/**
 * Created by A on 2016/9/18.
 */
public class LoginFaceImp implements LoginFace {
    private WebDriver driver;
    private String URL;
    private PageLogin pageLogin;

    public LoginFaceImp(WebDriver driver,String URL){
        this.driver = driver;
        this.URL = URL;
        pageLogin = new PageLogin(driver, URL);
        pageLogin.get();
    }

    public void setUserName(String username){
        pageLogin.buttonSign.click();
        pageLogin.setUsername(username);
    }

    public void setPassWord(String password){
        pageLogin.password.clear();
        pageLogin.password.sendKeys(password);
    }

    public void login() {
        pageLogin.loginButton.click();
    }

    public void loginWithCookie(){
        Cookie cookie = new Cookie("JSESSIONID","4374FD759BDD1B6504DDA6FF5CC84306-n1.jvm-platform-210",".tplatform.xbniao.com","/",null);
        driver.manage().addCookie(cookie);
        Cookie cookie1 = new Cookie("Hm_lvt_8595e9493fb318e1ec353ae1fcad5863","1474160741",".tplatform.xbniao.com","/",null);
        driver.manage().addCookie(cookie1);
        Cookie cookie2 = new Cookie("Hm_lpvt_8595e9493fb318e1ec353ae1fcad5863","1474282341",".tplatform.xbniao.com","/",null);
        driver.manage().addCookie(cookie2);
        driver.get(URL);
    }
}