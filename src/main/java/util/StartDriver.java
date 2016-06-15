package util;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

/**
 * Created with IntelliJ IDEA.
 * User: win7
 * Date: 13-7-31
 * Time: ����9:40
 * To change this template use File | Settings | File Templates.
 */
public class StartDriver {
    public EventListener eventListener;
    String selenium_grid_url;
    public WebDriver webDriver;

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    /**
     * 启动远程浏览器
     * @param Host 远程机器IP
     * @param BrowserType 浏览器类型
     * @return
     */
    public WebDriver setup(String Host,String BrowserType) {
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
        try {
            webDriver = new RemoteWebDriver(new URL(selenium_grid_url),desiredCapabilities);
            webDriver = new EventFiringWebDriver(webDriver).register(eventListener);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return webDriver;
    }

    /**
     * 启动单机浏览器
     * @param type 浏览器类型
     * @return
     */
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
    }

    /**
     * 配置远程chrome浏览器设置
     * @return
     */
    private DesiredCapabilities ChromeDes(){
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
    private DesiredCapabilities IEDes(){
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
    private DesiredCapabilities FirefoxDes(){
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
     */
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

    /**
     * 打开单机ie浏览器
     * @return
     */
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

    /**
     * 打开单机firefox浏览器
     * @return
     */
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



}

