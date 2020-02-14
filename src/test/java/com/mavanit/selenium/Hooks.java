package com.mavanit.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class Hooks {
    public static WebDriver driver;
    public String browser="";
    @Before
    public void setUp()
    {
        openBrowser();
        navigateTo("https://www.argos.co.uk/");
        manageSize();
    }
    @After
    public void tearDown()
    {
        //driver.quit();
    }
    public void openBrowser()
    {
        switch (browser)
        {
            case "ie" :
                WebDriverManager.iedriver().setup();
                driver=new InternetExplorerDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver=new FirefoxDriver();
                break;
            default:
                WebDriverManager.chromedriver().setup();
                driver=new ChromeDriver();
        }
    }
    public void navigateTo(String url)
    {
        driver.get(url);
    }
    public void manageSize()
    {
        driver.manage().window().maximize();
    }
}
