package org.example;

import com.google.common.collect.Ordering;
import org.example.pageobject.pages.CategoryPage;
import org.example.pageobject.pages.HomePage;
import org.example.pageobject.pages.SearchPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import static org.testng.Assert.assertTrue;

public class FilterAndSearchTests extends BaseTests {
    private final String minPrice = "20";
    private final String maxPrice = "45";
    @FindBy(css = "[aria-label='Headsets']")
    private WebElement categorySearch;
    private CategoryPage categoryPage;

    @BeforeMethod
    public void setUp() {
        setUpDriver();
    }

    @AfterTest
    public void tearDown() {
        quit();
    }

    private static void checkNumberInRange(int min, int max, Integer integer) {
        assertTrue(integer >= min && integer <= max, String.format("Value %s not in range.", integer));
    }

    private CategoryPage clickCategory() {
        HomePage homePage = new HomePage(driver);
        return homePage.goToCategory();
    }

    @Test
    public void checkBrandTitle() {
        categoryPage = clickCategory();
        categoryPage.clickOnBrand();
        String brandName = categoryPage.getBrandTitle();
        Assert.assertTrue(categoryPage.checkAllResultsTitlesMatchBrand(brandName),
                "Results did not all match the brand selected");
    }

    @Test
    public void checkPriceRange() {
        categoryPage = clickCategory();
        categoryPage.selectPriceRange(minPrice,maxPrice);
        categoryPage.priceList().stream().filter(price -> price != 0).forEach(price -> checkNumberInRange(Integer.valueOf(minPrice), Integer.valueOf(maxPrice), price));
    }


    @Test
    public void checkProductListAreAscending() {

        SearchPage searchPage = new HomePage(driver).search("laptop");
        searchPage.sortItemsByAscendingPrice();
        List<Double> itemList = searchPage.getFullPriceItemList();

        //System.out.println(itemList);

        Assert.assertTrue(Ordering.<Double> natural().isOrdered(itemList));
    }

}




