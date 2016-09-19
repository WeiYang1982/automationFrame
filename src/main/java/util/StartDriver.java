package util;

import org.apache.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * Created with IntelliJ IDEA.
 * User: win7
 * Date: 13-7-31
 * Time: ����9:40
 * To change this template use File | Settings | File Templates.
 */
public class StartDriver extends EventFiringWebDriver{
    static String selenium_grid_url;
    public static LogWriter writer;
    public static Logger log;
    public static WebDriver webDriver = null;
    public static final Thread CLOSE_THREAD = new Thread() {
        @Override
        public void run() {
            webDriver.close();
        }
    };

    static {
        writer = new LogWriter();
        log = writer.setLoggerWriter();
        Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
        ConfigManager config = new ConfigManager();
        String Host = config.get("host");
        String BrowserType = config.get("browser");
        startRemoteWebDriver(Host,BrowserType);

    }

    public StartDriver() {
        super(webDriver);
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    @Override
    public void close() {
        System.out.println(Thread.currentThread());
        if (Thread.currentThread() != CLOSE_THREAD) {
            throw new UnsupportedOperationException("You shouldn't close this WebDriver. It's shared and will close when the JVM exits.");
        }
        super.close();
        super.quit();
    }



    private static WebDriver startRemoteWebDriver(String Host,String BrowserType) {
        selenium_grid_url = "http://" + Host + ":4444/wd/hub";
        DesiredCapabilities desiredCapabilities = null;
        if (BrowserType.contains("chrome")){
            desiredCapabilities = ChromeDes();
        }else if (BrowserType.contains("firefox")){
            desiredCapabilities = FirefoxDes();
        }else if (BrowserType.contains("internet Explorer")){
            desiredCapabilities = IEDes();
        }else {
            System.out.println("没有找到对应浏览器类型");
        }
        EventListener eventListener = new EventListener();
        eventListener.setLog(log);
        eventListener.setLogWriter(writer);
        try {
            webDriver = new RemoteWebDriver(new URL(selenium_grid_url),desiredCapabilities);
            webDriver = new EventFiringWebDriver(webDriver).register(eventListener);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return webDriver;

    }


 /*   *//**
     * 启动单机浏览器
     * @param type 浏览器类型
     * @return
     *//*
    public WebDriver SetupSingelDriver(String type,String ChromePath,String FirefoxBinary){
        if (type.contains("chrome")){
            webDriver = SingelChromeDriver(ChromePath);
        }else if (type.contains("firefox")){
            webDriver = SingelFirefoxDriver(FirefoxBinary);
        }else if (type.contains("internet Explorer")){
            webDriver = SingelIEDriver();
        }else {
            System.out.println("没有找到对应浏览器类型");
        }
        return webDriver;
    }*/

    /**
     * 配置远程chrome浏览器设置
     * @return
     */
    private static DesiredCapabilities ChromeDes(){
        try {
            ChromeOptions options = new ChromeOptions();
            LoggingPreferences loggingPreferences = new LoggingPreferences();

            loggingPreferences.enable(LogType.BROWSER, Level.ALL);
            options.addArguments("test-type", "start-maximized");
            DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();
            desiredCapabilities.setBrowserName("chrome");
            desiredCapabilities.setJavascriptEnabled(true);
            desiredCapabilities.setCapability(CapabilityType.LOGGING_PREFS,loggingPreferences);
            desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS,true);
            desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
            return desiredCapabilities;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 配置远程IE浏览器设置
     * @return
     */
    private static DesiredCapabilities IEDes(){
        try {
            DesiredCapabilities desiredCapabilities = DesiredCapabilities.internetExplorer();
            desiredCapabilities.setBrowserName("internet Explorer");
            desiredCapabilities.setVersion("11");
            desiredCapabilities.setPlatform(Platform.WINDOWS);
            desiredCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            desiredCapabilities.setCapability("ignoreProtectedModeSettings", true);
            return desiredCapabilities;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 配置远程firefox浏览器设置
     * @return
     */
    private static DesiredCapabilities FirefoxDes(){
        try {
            DesiredCapabilities desiredCapabilities = DesiredCapabilities.firefox();
            desiredCapabilities.setBrowserName("firefox");
            return desiredCapabilities;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 打开单机chrome浏览器
     * @return
     *//*
    private WebDriver SingelChromeDriver(String path){
        System.setProperty("webdriver.chrome.driver", path);
        DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized","test-type");
        desiredCapabilities.setCapability(ChromeOptions.CAPABILITY,options);
        try {
            @SuppressWarnings("deprecation")
            ChromeDriverService service = new ChromeDriverService.Builder().usingDriverExecutable(
                    new File("lib\\chromedriver.exe")).usingAnyFreePort().build();
            service.start();
            webDriver = new RemoteWebDriver(service.getUrl(),desiredCapabilities);
        }catch (Exception e){
            e.printStackTrace();
        }
        return webDriver;
    }

    *//**
     * 打开单机ie浏览器
     * @return
     *//*
    private WebDriver SingelIEDriver(){
        try {
            System.setProperty("webdriver.ie.driver", "lib\\IEDriverServer.exe");
            DesiredCapabilities desiredCapabilities = DesiredCapabilities.internetExplorer();
            desiredCapabilities.setBrowserName("internet Explorer");
            desiredCapabilities.setVersion("11");
            desiredCapabilities.setPlatform(Platform.WINDOWS);
            desiredCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            desiredCapabilities.setCapability("ignoreProtectedModeSettings", true);
            webDriver = new InternetExplorerDriver(desiredCapabilities);
            return webDriver;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    *//**
     * 打开单机firefox浏览器
     * @return
     *//*
    private WebDriver SingelFirefoxDriver(String FirefoxBinary){
        try {
            DesiredCapabilities desiredCapabilities = DesiredCapabilities.firefox();
            desiredCapabilities.setBrowserName("firefox");
            desiredCapabilities.setCapability("firefox_binary", FirefoxBinary);
            File pathToFirefoxBinary = new File(FirefoxBinary);
            FirefoxBinary firefoxbin = new FirefoxBinary(pathToFirefoxBinary);
            webDriver = new FirefoxDriver(firefoxbin,null);
            return webDriver;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

*/

}

