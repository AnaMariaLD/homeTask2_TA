package org.example;

import org.example.factory.WebDriverFactory;
import org.openqa.selenium.WebDriver;



public class BaseTests {

    protected final WebDriver driver= new WebDriverFactory().getWebDriver();
    public void setUpDriver() {
        driver.get("https://www.amazon.com/");
        driver.manage().window().maximize();
    }

    public void quit() {
        driver.quit();
    }
}
