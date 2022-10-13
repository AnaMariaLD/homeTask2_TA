package org.example;

import org.example.pageobject.pages.CategoryPage;
import org.example.pageobject.pages.HomePage;
import org.example.pageobject.pages.SearchPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

public class SearchTests extends BaseTests {

    @BeforeMethod
    public void setUp() {
        setUpDriver();
    }

    @AfterTest
    public void tearDown() {
        quit();
    }

    private WebElement searchField;

    private SearchPage enterSearchPhrase(String searchPhrase) {
        HomePage homePage = new HomePage(driver);
        return homePage.search(searchPhrase);
    }

    @Test
    public void searchResultsIncorrectInformation() {
        SearchPage searchPage = enterSearchPhrase("a,smnfkjehriirjkjfnkjcnk90039034854tuihdfkjdfjnknjse!!@#$%^&(*&^%$#@#$%^&*^%$#@kflnvmoashgsdkjfnlkejnskld");
        WebElement noResultsMessage = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='No results for ']")));
        Assert.assertEquals(noResultsMessage.getText(), "No results for",
                "Somehow you got some results");
    }

    @Test
    public void checkResultsForMessage(){
        SearchPage searchPage = enterSearchPhrase("laptop");
        WebElement resultsForMessage = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='\"laptop\"']")));
        Assert.assertEquals(resultsForMessage.getText(),"\"laptop\"",
                "Results and search are not matching");
    }

    @Test
    public void checkResultsList(){
        SearchPage searchPage = enterSearchPhrase("laptop");
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
