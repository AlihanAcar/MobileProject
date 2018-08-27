package com.saha.mobile.base;

import com.saha.mobile.test.StepImplementation;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class BaseTest {


    //String currentPhone = "A5"
    private static final String APPPACKAGE = "hurriyet.mobil.android";
    private static final String APPACTIVITY = "hurriyet.mobil.android.hurriyet.activities.main.MainActivity";
    public static final String BUNDLEID = "";
    //public static final String BROWSER_NAME = ""
    public static DesiredCapabilities capabilities;
    protected static final Logger log = Logger.getLogger(BaseTest.class);
    public static AppiumDriver<MobileElement> driver;
    String ip = "http://0.0.0.0:4723/wd/hub";

    boolean localAndroid = true;

    public void setUp() throws MalformedURLException {
        PropertyConfigurator.configure("properties/log4j.properties");
        capabilities = new DesiredCapabilities();
        if (StringUtils.isEmpty(System.getProperty("key"))) {

            //Native(app)
            if (localAndroid) {
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
                capabilities.setPlatform(Platform.ANDROID);
                log.info("*** PLATFORM :" + capabilities.getCapability("platformName"));
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator");
                capabilities.setCapability("appPackage", APPPACKAGE);
                capabilities.setCapability("appActivity", APPACTIVITY);
                capabilities.setCapability("unicodeKeyboard", true);
                capabilities.setCapability("resetKeyboard", false);
                capabilities.setCapability("interKeyDelay", 300);
                capabilities.setCapability(MobileCapabilityType.FULL_RESET,false);
                capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
                //capabilities.setCapability(MobileCapabilityType.CLEAR_SYSTEM_FILES,false);
                capabilities.setCapability("autoGrantPermissions", "true");
                driver = new AndroidDriver<>(new URL(ip), capabilities);
                driver.manage().timeouts().implicitlyWait(60L, TimeUnit.SECONDS);
            } else {

                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
                capabilities.setCapability("platformName", "iOS");
                log.info("*** PLATFORM :" + capabilities.getCapability("platformName"));
                // capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,
                // "8.1")
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
                capabilities.setCapability("xcodeOrgId", "PMLH8MF4G9");
                capabilities.setCapability("xcodeSigningId", "iPhone Developer");
                capabilities.setCapability("bundleId", BUNDLEID);
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "IPHONE_5S");
                //capabilities.setCapability("udid", "6a7e4e913da78778a1191c35d7ac98151637d3f0");
                capabilities.setCapability("udid", "aba6f4f90f7e7a9cac6aaf59b507aaa19827e1d4");

                //capabilities.setCapability(MobileCapabilityType.SUPPORTS_JAVASCRIPT, true);
                driver = new IOSDriver<>(new URL("http://localhost:4723/wd/hub"), capabilities);
                //driver.installApp("/Users/olcayekin/Desktop/ing-project/ingmobile-test/properties/INGMobile.ipa");
                //driver.manage().timeouts().implicitlyWait(10L, TimeUnit.SECONDS);
            }

            /*
            //Mobile Webtest
            if (localAndroid) {
                capabilities = new DesiredCapabilities();
                ChromeOptions options = new ChromeOptions();
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
                capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "chrome");
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "ANDROID");
                capabilities.setCapability("browserName", "chrome");
                capabilities.setCapability(CapabilityType.PLATFORM, Platform.ANDROID);
                options.addArguments("test-type");
                options.addArguments("disable-popup-blocking");
                options.addArguments("ignore-certificate-errors");
                options.addArguments("disable-translate");
                options.addArguments("--dns-prefetch-disable");
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);
                capabilities.setCapability("unicodeKeyboard", true);
                capabilities.setCapability("resetKeyboard", true);
                capabilities.setBrowserName("chrome");
                StepImplementation.driver = new AndroidDriver<>(new URL(ip), capabilities);
                StepImplementation.driver.manage().timeouts().implicitlyWait(120L, TimeUnit.SECONDS);
            } else {

                capabilities = new DesiredCapabilities();
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
                capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9.3.2");
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 5S");
                capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
                capabilities.setCapability(MobileCapabilityType.ACCEPT_SSL_CERTS, "true");
                capabilities.setCapability("nativeWebTap", true);
                capabilities.setCapability("xcodeOrgId", "PMLH8MF4G9");
                capabilities.setCapability("xcodeSigningId", "iPhone Developer");
                capabilities.setCapability("autoWebView", "true");
                capabilities.setCapability("ensureCleanSession", true);
                capabilities.setCapability("preventWDAAttachments", "true");
                capabilities.setCapability("safariInitialUrl", "http://www.google.com.tr");
                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                capabilities.setCapability("acceptSslCerts", true);
                capabilities.setCapability(MobileCapabilityType.UDID, "0fbe99646739467b8b2700c09c7072e1fd3e455f");
                capabilities.setPlatform(Platform.MAC);
                StepImplementation.driver = new IOSDriver<>(new URL("http://localhost:4723/wd/hub"), capabilities);

                //driver.installApp("/Users/olcayekin/Desktop/ing-project/ingmobile-test/properties/INGMobile.ipa");
                //driver.manage().timeouts().implicitlyWait(10L, TimeUnit.SECONDS);

            }
        */

        } else {
            capabilities.setCapability("key", System.getProperty("key"));
            String hubURL = "http://10.104.187.184:4444/wd/hub";

            if (System.getProperty("platform").equals("ANDROID")) {
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
                capabilities.setCapability("appPackage", APPPACKAGE);
                capabilities.setCapability("appActivity", APPACTIVITY);
                //capabilities.setCapability(MobileCapabilityType.FULL_RESET, true);
                StepImplementation.driver = new AndroidDriver<>(new URL(hubURL), capabilities);
                StepImplementation.driver = new AndroidDriver<>(new URL(hubURL), capabilities);
            } else {
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
                capabilities.setCapability("waitForAppScript", "$.delay(300);");
                //capabilities.setCapability(MobileCapabilityType.FULL_RESET, true);
                StepImplementation.driver = new IOSDriver<>(new URL(hubURL), capabilities);
            }
        }
    }


    public void tearDown() throws IOException {

        StepImplementation.driver.quit();
    }

}
