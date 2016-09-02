import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import procedure.login;
import util.ExcelReader;


/**
 * Created by a on 2015/8/11.
 */
public class testLogin extends testTemplate {

    @DataProvider(name = "test")
    public Object[][] provider(){
        String path = TestDataPath + "\\login.xls";
        Object[][] result = (Object[][]) ExcelReader.getSingleExpectationData(path,"success");
        System.out.println(result);
        return result;
    }


    @Test(dataProvider = "test",groups = "xbniao" )
    public void testLogin(String URL,String userName,String passWord,String expcatation)  {
        login login = new login();
        String real = login.login(webDriver,URL,userName,passWord);
        Assert.assertEquals(expcatation,real);
    }


}
