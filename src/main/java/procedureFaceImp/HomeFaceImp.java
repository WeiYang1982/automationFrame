package procedureFaceImp;


import org.openqa.selenium.WebDriver;
import pageElement.PageHome;
import procedureFace.HomeFace;

/**
 * Created by A on 2016/9/20.
 */
public class HomeFaceImp implements HomeFace {
    WebDriver driver;
    public HomeFaceImp(WebDriver driver){
        this.driver = driver;
    }

    public void home(){
        PageHome pageHome = new PageHome(driver);
        System.out.println(pageHome.logo.getLocation());
//        pageHome.get();
    }

}
