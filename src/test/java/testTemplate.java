import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import util.*;

/**
 * Created by yangwei on 2015/5/14.
 */
public class testTemplate {
    public String BrowserType;
    public String ErrorImgPath;
    public String LogPath;
    public String ConfigPath;
    public String TestDataPath;
    public String TestCaseName;
    public String Host;
    public String FirefoxBinary;
    public String ChromePath;
    LogWriter writer;
    Logger logger;
    static WebDriver webDriver;
    EventListener listener;

    @Parameters({"ErrorImgFilePath","LogFilePath","ConfigFilePath","TestDataFilePath"})
    @BeforeSuite(alwaysRun = true)
    protected void setup(String errorImgFilePath,String logFilePath,String configFilePath,String testDataFilePath){
        loadPathAndParameters(errorImgFilePath, logFilePath, configFilePath, testDataFilePath);
//        new CleanWorkspace(errorImgFilePath, logFilePath);
        setLogger(configFilePath);
        setTestCaseName(this.getClass().getSimpleName());
    }



    @BeforeMethod(alwaysRun = true)
    protected void startDriver(){
        StartDriver start = new StartDriver();
        listener = new EventListener();
        listener.setCaseName(TestCaseName);
        listener.setErrorImgFilePath(ErrorImgPath);
        listener.setLog(LogWriter.log);
        listener.setLogWriter(writer);
        start.setEventListener(listener);
        start.setup(Host, BrowserType);
        webDriver = start.webDriver;
    }

    @AfterMethod(alwaysRun = true)
    protected void closeDriver(){
        if (null != webDriver){
            webDriver.quit();
        }
    }

    /**
     * 读取设置
     * @param errorImgFilePath 错误截图路径
     * @param logFilePath 日志路径
     * @param configFilePath 配置路径
     * @param testDataFilePath 测试数据路径
     */
    private void loadPathAndParameters(String errorImgFilePath,String logFilePath,String configFilePath,String testDataFilePath){
        BasicPath BasicPath = new BasicPath();
        BasicPath.setFilePath(errorImgFilePath, logFilePath, configFilePath, testDataFilePath);
        ErrorImgPath = BasicPath.getErrorImgFilePath();
        LogPath = BasicPath.getLogFilePath();
        ConfigPath = BasicPath.getConfigFilePath();
        TestDataPath = BasicPath.getTestDataFilePath();
        BrowserType = LoadProperties.getPropertiesValue(configFilePath, "system.properties", "Browser");
        Host = LoadProperties.getPropertiesValue(configFilePath, "system.properties", "Host");
        FirefoxBinary = LoadProperties.getPropertiesValue(configFilePath,"system.properties","FirefoxBinary");
        ChromePath = LoadProperties.getPropertiesValue(configFilePath,"system.properties","ChromePath");
    }

    /**
     * 启动日志记录
     * @param logConfigFilePath 日志路径
     */
    private void setLogger(String logConfigFilePath){
        writer = new LogWriter();
        writer.setLoggerWriter(logConfigFilePath);
        logger = LogWriter.log;
    }

    /**
     * 根据测试用例名称分隔日志内容
     * @param testCaseName 测试用例名称
     */
    private void setTestCaseName(String testCaseName) {
        TestCaseName = testCaseName;
        logger.info("========================================");
        logger.info("TestCase: " + TestCaseName);
        logger.info("========================================");

        logger.error("========================================");
        logger.error("TestCase: " + TestCaseName);
        logger.error("========================================");
    }





}
