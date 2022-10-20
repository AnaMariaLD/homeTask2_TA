package org.example;

import org.example.factory.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;


public class BaseTests {

    protected static final WebDriver driver= new WebDriverFactory().getWebDriver();

    @BeforeTest
    public static void setUpDriver() {
        driver.get("https://www.amazon.com/");
        driver.manage().window().maximize();
    }

    @AfterTest
    public static void quit() {
        driver.quit();
    }
}
