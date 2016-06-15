package util;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.ScreenshotException;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 ����* This is an customized webdriver event listener.
 ����* Now it implements onException method: webdriver will take a screenshot
 ����* when it meets an exception. It's good but not so usable. And when we use
 ����* WebDriverWait to wait for an element appearing, the webdriver will throw
 ����* exception always and the onException will be excuted again and again, which
 ����* generates a lot of screenshots.
 ����* Put here for study
 ����* Usage:
 ����* WebDriver driver = new FirefoxDriver();
 ����* WebDriverEventListener listener = new CustomWebDriverEventListener();
 ����* return new EventFiringWebDriver(driver).register(listener);
 ����*
 ����* @author qa
 ����*
    */
public class EventListener extends AbstractWebDriverEventListener {
    private String caseName;
    private By lastFindBy;
    private String originalValue;
    String ErrorImgFilePath;
    Logger log;
    LogWriter logWriter;

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public void setLog(Logger log) {
        this.log = log;
    }

    public void setErrorImgFilePath(String errorImgFilePath) {
        ErrorImgFilePath = errorImgFilePath;
    }

    public void setLogWriter(LogWriter logWriter) {
        this.logWriter = logWriter;
    }

    public void autoScreenShot(Throwable ex){
        String filename = generateRandomFilename(ex);
        try {
            byte[] btDataFile = Base64.decodeBase64(extractScreenShot(ex).getBytes());
            File of = new File(filename);
            File parent = of.getParentFile();

            if(parent!=null&&!parent.exists()){
                parent.mkdirs();
            }
            FileOutputStream osf = new FileOutputStream(of);
            osf.write(btDataFile);
            osf.flush();
            osf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成截图
     * @param ex
     * @return
     */
    private String extractScreenShot(Throwable ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof ScreenshotException) {
            return ((ScreenshotException) cause).getBase64EncodedScreenshot();
        }
        return null;
    }

    /**
     * 生成文件
     * @param ex
     * @return
     */
    private String generateRandomFilename(Throwable ex) {
        String sp = File.separator;
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy-MM-dd-hh-mm-ss");
        String dateString = formatter.format(new Date());
        String filename = ex.getMessage();
        int i = filename.indexOf('\n');
        System.out.println("File~caseName :" + caseName);
        filename = filename.substring(0, i).replaceAll("\\s", "_")
                .replaceAll(":", "") ;
        filename = ErrorImgFilePath + "" + sp +  caseName + "" + sp + "" + caseName + "@@" + dateString + "@@" + filename + ".png";
        System.out.println("filename : " + filename);
        return filename;
    }



    @Override
    public void onException(Throwable ex, WebDriver arg1 ) {
        autoScreenShot(ex);
        if(ex.getClass().equals(NoSuchElementException.class)){
            logWriter.writeFileAndReport("\n" + "WebDriver error: Element not found: " + lastFindBy + "\n");
        }else if (ex.getClass().equals(TimeoutException.class)){
            logWriter.writeFileAndReport("\n" + "WebDriver error: Element Time Out: " + lastFindBy + "\n");
        }else {
            log.error("WebDriver error:", ex);
        }
    }


    /**
     * 知道记录浏览器各种操作
     */
    public void beforeNavigateTo(String url, WebDriver selenium){
        log.info("WebDriver navigating to: '"+url+"'");
    }
    public void beforeChangeValueOf(WebElement element, WebDriver selenium){
        originalValue = element.getAttribute("value");
    }
    public void afterChangeValueOf(WebElement element, WebDriver selenium){
        log.info("WebDriver changing value in element found: "+lastFindBy+" from '"+originalValue+"' to '"+element.getAttribute("value")+"'");
    }
    public void beforeFindBy(By by, WebElement element, WebDriver selenium){
        lastFindBy = by;
        //找东西前等三秒wait 3 second for every find by
        selenium.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    }
    public void beforeNavigateBack(WebDriver selenium){}
    public void beforeNavigateForward(WebDriver selenium){}
    public void beforeClickOn(WebElement element, WebDriver selenium){}
    public void beforeScript(String script, WebDriver selenium){}
    public void afterClickOn(WebElement element, WebDriver selenium){
        String locator=element.toString().split("-> ")[1];
        log.info("WebDriver clicking on: '"+locator.substring(0, locator.length()-1)+"'");
    }
    public void afterFindBy(By by, WebElement element, WebDriver selenium){
        if (by != null && element != null){
            element = element.findElement(by);
            ((JavascriptExecutor) selenium).executeScript("arguments[0].scrollIntoView();", element);
        }else if (by != null && element == null){
            element = selenium.findElement(by);
            ((JavascriptExecutor) selenium).executeScript("arguments[0].scrollIntoView();", element);
        }
    }
    public void afterNavigateBack(WebDriver selenium){}
    public void afterNavigateForward(WebDriver selenium){}
    public void afterNavigateTo(String url, WebDriver selenium){}
    public void afterScript(String script, WebDriver selenium){}
    }