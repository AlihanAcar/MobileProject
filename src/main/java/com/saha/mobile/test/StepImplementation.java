package com.saha.mobile.test;


import com.saha.mobile.base.BaseTest;
import com.saha.mobile.mapping.MapMethodType;
import com.saha.mobile.mapping.Mapper;
import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import com.thoughtworks.gauge.Step;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.saha.mobile.mapping.Mapper.foundActivity;

public class StepImplementation extends BaseTest{
    public static final int DEFAULT_WAIT = 10;

    private WebDriverWait wait;
    public static AppiumDriver<MobileElement>driver;


    @BeforeScenario
    public void setUpStep() throws MalformedURLException {
        setUp();
        driver = new AppiumDriver<MobileElement>(BaseTest.capabilities);
        wait = new WebDriverWait(driver, 60);
        contextSwitch("WEBVIEW");
    }



    public void contextSwitch(String context) {
        Set<String> contextNames = driver.getContextHandles();
        for (String contextName : contextNames) {
            log.info(contextNames);
            if (contextName.contains(context)) {
                driver.context(contextName);
                break;

            }
        }
    }

    public int getPhoneX() {
        Dimension d = driver.manage().window().getSize();
        return d.width;
    }

    public int getPhoneY() {
        Dimension d = driver.manage().window().getSize();
        return d.height;
    }

    public void swipeUpAccordingToPhoneSize() {
        int height = getPhoneY();
        int width = getPhoneX();

        int swipeStartWidth = width / 2;
        int swipeEndWidth = width / 2;

        int swipeStartHeight = (height * 70) / 100;
        int swipeEndHeight = (height * 30) / 100;
        TouchAction action = new TouchAction(driver);
        action.press(swipeStartWidth,swipeStartHeight).waitAction().moveTo(swipeEndWidth,swipeEndHeight).release().perform();
    }

    public void swipeDownAccordingToPhoneSize() {
        int height = getPhoneY();
        int width = getPhoneX();

        int swipeStartWidth = width / 2;
        int swipeEndWidth = width / 2;

        int swipeStartHeight = (height * 30) / 100;
        int swipeEndHeight = (height * 70) / 100;
        TouchAction action = new TouchAction(driver);
        action.press(swipeStartWidth,swipeStartHeight).waitAction().moveTo(swipeEndWidth,swipeEndHeight).release().perform();
    }

    public void swipeLeftAccordingToPhoneSize() {
        int height = getPhoneY();
        int width = getPhoneX();

        int swipeStartWidth = (width * 80) / 100;
        int swipeEndWidth = (width * 15) / 100;
        int swipeStartHeight = height / 3;
        int swipeEndHeight = height / 3;
        TouchAction action = new TouchAction(driver);
        action.press(swipeStartWidth,swipeStartHeight).waitAction().moveTo(swipeEndWidth,swipeEndHeight).release().perform();
    }

    public void swipeRightAccordingToPhoneSize() {
        int height = getPhoneY();
        int width = getPhoneX();

        int swipeStartWidth = (width * 15) / 100;
        int swipeEndWidth = (width * 80) / 100;
        int swipeStartHeight = height / 3;
        int swipeEndHeight = height / 3;
        TouchAction action = new TouchAction(driver);
        action.press(swipeStartWidth,swipeStartHeight).waitAction().moveTo(swipeEndWidth,swipeEndHeight).release().perform();
    }

    public MobileElement waitForElement(int seconds, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, seconds, DEFAULT_WAIT);
        return (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public boolean isexist(By locator, int seconds) {
        try {
            waitForElement(seconds, locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }





    /**
    *
    *
    *
    *                         STEPS
    *
    *
    *
     */



        @Step("<url> adresine git")
        public void goURL(String url) {
            driver.navigate().to(url);
    }



        @Step("<by> butonuna tıkla")
        public void clickButton ( String button) {
        wait.until(ExpectedConditions.elementToBeClickable(foundActivity(MapMethodType.CLICK_ELEMENT, button)));
        driver.findElement(Mapper.foundActivity(MapMethodType.CLICK_ELEMENT,button)).click();
        }

        @Step("<by> native butonuna tıkla")
        public void clickNativeButton(String button){
            contextSwitch("NATIVE");
            wait.until(ExpectedConditions.elementToBeClickable(foundActivity(MapMethodType.CLICK_ELEMENT, button)));
            driver.findElement(Mapper.foundActivity(MapMethodType.CLICK_ELEMENT,button)).click();
            contextSwitch("CHROMIUM");
        }


        @Step("<by> alanına <text> yaz")
        public void inputText (String by, String text) {
        wait.until(ExpectedConditions.presenceOfElementLocated(foundActivity(MapMethodType.INPUT_ELEMENT,by)));
        log.info(text);
        driver.findElement(Mapper.foundActivity(MapMethodType.INPUT_ELEMENT,by)).sendKeys(text);

        }



        @Step("<list> listesinden <text> elementini seç")
        public void selectFromList (String list, String text) {
        List<MobileElement> listElement = driver.findElements(foundActivity(MapMethodType.SELECT_OPTION,list));
            for (WebElement element :
                    listElement ) {
                if(element.getText().contains(text)){
                    element.click();
                    break;
                }
            }

        }

        @Step("<list> listesinden <i> numaralı elemente tıkla")
        public void selectFromListWithIndex(String list, int i){
            wait.until(ExpectedConditions.presenceOfElementLocated(foundActivity(MapMethodType.SELECT_OPTION, list)));
            driver.findElements(Mapper.foundActivity(MapMethodType.SELECT_OPTION, list));
            List<MobileElement> listElement = driver.findElements(foundActivity(MapMethodType.SELECT_OPTION,list));
            listElement.get(i-1).click();
        }

        @Step("<seconds> saniye bekle")
        public void waitSeconds(int seconds){
            try {
                TimeUnit.SECONDS.sleep(seconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Step("<by> native kapat")
        public void closeNative(String button){
            contextSwitch("NATIVE");
            clickButton(button);
            contextSwitch("CHROMIUM");
        }

            //mobil web test için
        @Step("<by> elementine git")
        public void scrollElement(String by){
            MobileElement element =driver.findElement(foundActivity(MapMethodType.IS_ELEMENT,by));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        }

        @Step("<by> elementini görene kadar aşağı in")
        public void swipeUpUntilSeeElement(String by){
            contextSwitch("NATIVE");
            for (int i=0 ; i<1000; i++) {
                try {
                    driver.findElement(foundActivity(MapMethodType.IS_ELEMENT, by)).isDisplayed();
                    break;
                } catch (Exception ex) {
                    swipeUpAccordingToPhoneSize();
                }
            }
            contextSwitch("CHROMIUM");
        }

        @Step("<by> elementini görene kadar yukarı çık")
        public void swipeDownUntilSeeElement(String by){
            contextSwitch("NATIVE");
            for (int i=0 ; i<1000; i++) {
                try {
                    driver.findElement(foundActivity(MapMethodType.IS_ELEMENT, by)).isDisplayed();
                    break;
                } catch (Exception ex) {
                    swipeDownAccordingToPhoneSize();
                }
            }
            contextSwitch("CHROMIUM");
        }

         @Step("<by> elementini görene kadar sağa git")
         public void swipeLeftUntilSeeElement(String by){
            contextSwitch("NATIVE");
            for (int i=0 ; i<1000; i++) {
                try {
                    driver.findElement(foundActivity(MapMethodType.IS_ELEMENT, by)).isDisplayed();
                    break;
                } catch (Exception ex) {
                    swipeLeftAccordingToPhoneSize();
                    waitSeconds(2);
                }
            }
            contextSwitch("CHROMIUM");
        }

         @Step("<by> elementini görene kadar sola git")
        public void swipeRightUntilSeeElement(String by){
            contextSwitch("NATIVE");
            for (int i=0 ; i<1000; i++) {
                try {
                    driver.findElement(foundActivity(MapMethodType.IS_ELEMENT, by)).isDisplayed();
                    break;
                } catch (Exception ex) {
                    swipeRightAccordingToPhoneSize();
                    waitSeconds(2);
                }
            }
            contextSwitch("CHROMIUM");
        }

        @Step("<i> kere aşağı in")
        public void swipeUp(int i){
            contextSwitch("NATIVE");
            for (int j=0 ; j<i; j++) {
                    swipeUpAccordingToPhoneSize();
            }
            contextSwitch("CHROMIUM");
        }

        @Step("<i> kere yukarı çık")
        public void swipeDown(int i){
            contextSwitch("NATIVE");
            for (int j=0 ; j<i; j++) {
                swipeDownAccordingToPhoneSize();
            }
            contextSwitch("CHROMIUM");
        }

        @Step("<i> kere sağa git")
        public void swipeLeft(int i){
            contextSwitch("NATIVE");
            for (int j=0 ; j<i; j++) {
                swipeLeftAccordingToPhoneSize();
                waitSeconds(2);
            }
            contextSwitch("CHROMIUM");
        }

         @Step("<i> kere sola git")
        public void swipeRight(int i){
            contextSwitch("NATIVE");
            for (int j=0 ; j<i; j++) {
                swipeRightAccordingToPhoneSize();
                waitSeconds(2);
            }
            contextSwitch("CHROMIUM");
        }

        @Step("<i> numaralı sekmeye geç")
        public void switchTab(int i){
            ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
            driver.switchTo().window(tabs.get(i-1));
        }

        @Step("<i> numaralı sekmeyi kapat")
        public void closeTab(int i){
            ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
            driver.switchTo().window(tabs.get(i-1));
            driver.close();
        }

        @Step("<i> numaralı sekmeyi kapat ve <j> numaralı sekmeye geç")
        public void closeTabAndSwitchAnotherTab(int i, int j){
            ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
            driver.switchTo().window(tabs.get(i-1));
            driver.close();
            driver.switchTo().window(tabs.get(j-1));
        }


        /**
        *
        *
        *
        *                          AFTER SCENARIO
        *
        *
        *
        */

        /*
        //Mobile Webtest AfterScenario
         @AfterScenario
         public void tearDownStep() {
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        int i;
        int n = tabs.size();
        for(i=n;i>=1;i--){
            driver.switchTo().window(tabs.get(i-1));
            driver.close();
        }
        }
        */


        //Native AfterScenario
        @AfterScenario
        public void tearDownStep() throws IOException {
            super.tearDown();
            }

}
