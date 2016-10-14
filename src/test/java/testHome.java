import org.testng.annotations.Test;
import procedureFace.imp.HomeFaceImp;
import procedureFace.imp.LoginFaceImp;

/**
 * Created by A on 2016/9/20.
 */
public class testHome extends TestTemplate{

    @Test
    public void home(){
        LoginFaceImp login = new LoginFaceImp(webDriver,"http://tplatform.xbniao.com/?a=home&n=view_home");
        login.setUserName("15624965577");
        login.setPassWord("111111");
        login.login();
        HomeFaceImp home = new HomeFaceImp(webDriver);
        home.home();
    }
}
