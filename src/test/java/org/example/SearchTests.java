package org.example;

import org.example.pageobject.pages.HomePage;
import org.example.pageobject.pages.SearchPage;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SearchTests extends BaseTests {
    private SearchPage searchPage;
    private final String validSearchPhrase ="laptop";

    @BeforeMethod
    public void setUp() {
        setUpDriver();
    }

    @AfterTest
    public void tearDown() {
        quit();
    }


    private SearchPage enterSearchPhrase(String searchPhrase) {
        HomePage homePage = new HomePage(driver);
        return homePage.search(searchPhrase);
    }

    @Test
    public void checkNoResultsMessage() {
        String invalidSearchPhrase = "a,smnfkjehriirjkjfnkjcnk90039034854tuihdfkjdfjnknjse!!@#$%^&(*&^%$#@#$%^&*^%$#@kflnvmoashgsdkjfnlkejnskld";
        searchPage = enterSearchPhrase(invalidSearchPhrase);
        Assert.assertEquals(searchPage.getNoResultsMessage(), "No results for",
                "No results message is not as expected");
    }

    @Test
    public void checkResultsForMessage() {
        searchPage = enterSearchPhrase(validSearchPhrase);
        Assert.assertEquals(searchPage.checkResultsForPhrase(), "\"" + validSearchPhrase +"\"",
                "Results and search are not matching");
    }

    @Test
    public void checkResultsList() {
        searchPage = enterSearchPhrase(validSearchPhrase);
        Assert.assertTrue(searchPage.checkAnyMatchForResults(validSearchPhrase),
                "No results found containing searched text");
    }


}
