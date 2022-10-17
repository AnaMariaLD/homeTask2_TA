package org.example;

import com.google.common.collect.Ordering;
import org.example.pageobject.pages.CategoryPage;
import org.example.pageobject.pages.HomePage;
import org.example.utils.SupportMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.testng.Assert.assertTrue;

public class FilterAndSearchTests extends BaseTests {
    private final int RESULT_PER_PAGE = 35;
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
    public void sortingFunction(){
        openCategory();

        WebElement dropDownMenu = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"a-autoid-0-announce\"]")));
        dropDownMenu.click();

        WebElement dropDownList= driver.findElement(By.xpath("//*[@id='s-result-sort-select_1']"));
        dropDownList.click();

        assertTrue(checkProductListAreAscending());
    }

    private boolean checkProductListAreAscending() {
        WebElement resultIsLoaded = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class=\"a-price-whole\"]")));
        List<WebElement> listOfProductsPriceWhole = null;
        List<WebElement> listOfProductsPriceDecimal = null;
        if (resultIsLoaded.isDisplayed()) {
            listOfProductsPriceWhole = driver.findElements(By.xpath("//*[@class=\"a-price-whole\"]"));
            listOfProductsPriceDecimal = driver.findElements(By.xpath("//*[@class=\"a-price-fraction\"]"));
            return Ordering.natural().isOrdered(getFullPrice(listOfProductsPriceWhole.iterator(), listOfProductsPriceDecimal.iterator()));
        }
        return false;
    }

    @Nullable
    private List<Double> getFullPrice(Iterator<WebElement> wholePrice, Iterator<WebElement> decimalPrice) {
        List<Double> productsList = new ArrayList<>();
        int i = 0;
        while (wholePrice.hasNext() && decimalPrice.hasNext() && i < RESULT_PER_PAGE) {
            i++;
            String combinedPrice = wholePrice.next().getText().replace(",", "") + "." + decimalPrice.next().getText();
            if (combinedPrice.equals(".")) {
                continue;
            }
            double temp = Double.parseDouble(combinedPrice);
            productsList.add(temp);
        }
        return productsList;
    }

    public void openCategory() {
        WebElement categoryElement;
        try {
            categoryElement = driver.findElement(By.partialLinkText("Chairs"));
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("NO SUCH ELEMENT");
        }
        categoryElement.click();
    }
}




