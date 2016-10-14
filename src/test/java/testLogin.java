import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import procedure.imp.LoginFaceImp;
import util.ExcelReader;

import java.io.File;


/**
 * Created by a on 2015/8/11.
 */
public class testLogin extends TestTemplate {

    @DataProvider(name = "test")
    public Object[][] provider(){
        String path = System.getProperty("user.dir") + File.separator + "testdata" + File.separator + "login.xls";
        Object[][] result = (Object[][]) ExcelReader.getSingleExpectationData(path,"success");
        System.out.println(result);
        return result;
    }

    @Test(dataProvider = "test",groups = "xbniao" )
    public void login(String URL,String userName,String passWord,String expcatation){
        LoginFaceImp login = new LoginFaceImp(webDriver,URL);
        login.setUserName(userName);
        login.setPassWord(passWord);
        login.login();
    }


}
