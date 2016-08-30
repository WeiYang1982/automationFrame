import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import pageElement.PageHome;
import procedure.login;
import util.ExcelReader;

/**
 * Created by A on 2016/2/4.
 */
public class testHome extends testTemplate{

    @Test(groups = "home")
    public void testHome(){
        String path = ".\\testdata\\login.xls";
        String[][] result = ExcelReader.getSingleExpectationData(path,"success");
        login login = new login();
        login.login(webDriver,result[0][0],result[0][1],result[0][2]);
        PageHome home = new PageHome(webDriver);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebDriverWait wait = new WebDriverWait(webDriver,10);
        wait.until(ExpectedConditions.elementToBeClickable(home.picConfig));
        home.picConfig.click();

    }
}
