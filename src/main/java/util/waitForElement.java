package util;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.util.concurrent.TimeUnit;

/**
 * Created by A on 2016/2/2.
 */
public class waitForElement {


    public static WebElement waitForElement(WebDriver driver, final By by) throws Throwable{
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait()
        final Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(30 * 1000, TimeUnit.MILLISECONDS)
                .pollingEvery(500, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class);

        System.out.println("======In this page to find the element " + by.toString() +"======");

        wait.until((new ExpectedCondition<Boolean>() {
//            @Override
            public Boolean apply(WebDriver driverObject) {
                return (Boolean) ((JavascriptExecutor) driverObject).executeScript("return document.readyState === 'complete'");
            }
        }));

        wait.until((new ExpectedCondition<Boolean>() {
//            @Override
            public Boolean apply(WebDriver driverObject) {
                System.out.println("Waiting times\n ");
                return wait.until(ExpectedConditions.visibilityOf(driverObject.findElement(by))).isDisplayed();
            }
        }));

        String pageurl = driver.getCurrentUrl();
        System.out.println("This page url is:"+pageurl);
        WebElement element = driver.findElement(by);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); //reset implicitlyWait
        return element;
    }
}
