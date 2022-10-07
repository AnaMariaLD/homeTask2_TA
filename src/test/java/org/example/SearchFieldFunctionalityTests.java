package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

public class SearchFieldFunctionalityTests {
    private WebDriver driver;
    private WebElement searchField;
    @BeforeMethod
    public void setUp(){
        System.setProperty("webdriver.chrome.driver","src/test/resources/webdriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://www.amazon.com");
        searchField = new WebDriverWait(driver,Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("twotabsearchtextbox")));
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void searchResultsIncorrectInformation(){

        searchField.sendKeys("a,smnfkjehriirjkjfnkjcnk90039034854tuihdfkjdfjnknjse!!@#$%^&(*&^%$#@#$%^&*^%$#@kflnvmoashgsdkjfnlkejnskld"
                             + Keys.ENTER);
        WebElement noResultsMessage = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='No results for ']")));
        Assert.assertEquals(noResultsMessage.getText(),"No results for",
                "Somehow you got some results");
    }

    @Test
    public void checkResultsForMessage(){
        searchField.sendKeys("laptop"
                             +Keys.ENTER);
        WebElement resultsForMessage = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='\"laptop\"']")));
        Assert.assertEquals(resultsForMessage.getText(),"\"laptop\"",
                "Results and search are not matching");
    }

    @Test
    public void checkResultsList(){
        searchField.sendKeys("laptop"
                        +Keys.ENTER);
        List<WebElement> searchResults = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("a-size-medium")));
        boolean foundResult=false;
        for (WebElement searchResult: searchResults) {
            if(containsIgnoreCase(searchResult.getText(),"laptop")) {
                foundResult = true;
                break;
            }
        }
        Assert.assertTrue(foundResult,"No results found containing searched text");
    }

}