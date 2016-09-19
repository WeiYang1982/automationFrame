import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import util.*;

/**
 * Created by yangwei on 2015/5/14.
 */
public class TestTemplate extends StartDriver {
    LogWriter writer;
    Logger logger;
    static WebDriver webDriver;


    @BeforeMethod(alwaysRun = true)
    public void startDriver(){
        webDriver = StartDriver.webDriver;
        logger = StartDriver.log;
        writer = StartDriver.writer;
        webDriver.manage().deleteAllCookies();
        setTestCaseName(this.getClass().getSimpleName());
        System.setProperty("TestCaseName",this.getClass().getSimpleName());
    }

    @AfterMethod(alwaysRun = true)
    public void deleteAllCookie(){
        webDriver.manage().deleteAllCookies();
        manage().deleteAllCookies();
    }

    /**
     * 根据测试用例名称分隔日志内容
     * @param testCaseName 测试用例名称
     */
    private void setTestCaseName(String testCaseName) {
        writer.writeFileAndReport("==================TestCaseName:" + testCaseName + "====================\n");
    }



}
