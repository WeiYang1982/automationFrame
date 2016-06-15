package util;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by A on 2016/2/1.
 */
public class IsElementExist {

    public boolean isElementExsit(WebDriver driver, By locator) {
        boolean flag = false;
        try {
            WebElement element=driver.findElement(locator);
            flag=null!=element;
        } catch (NoSuchElementException e) {
            System.out.println("Element:" + locator.toString()
                    + " is not exsit!");
        }
        return flag;
    }
}
