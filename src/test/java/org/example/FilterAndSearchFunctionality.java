package org.example;

import com.google.common.collect.Ordering;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class FilterAndSearchFunctionality {

    //compare the first RESULT_PER_PAGE result
    private final int RESULT_PER_PAGE = 35;

    private WebDriver webDriver;

    @BeforeTest
    public void setUpDriver() {
        System.setProperty(
                "webdriver.chrome.driver",
                "src/test/resources/webdriver/chromedriver.exe"
        );

        this.webDriver = new ChromeDriver();
        setupAmazon();
    }

    @Test
    public void sortingFunction(){
        openCategory();

        WebElement dropDownMenu = new WebDriverWait(webDriver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"a-autoid-0-announce\"]")));
        dropDownMenu.click();

        WebElement dropDownList= webDriver.findElement(By.xpath("//*[@id=\"s-result-sort-select_1\"]"));
        dropDownList.click();

        Assert.assertTrue(checkProductListAreAscending());

    }

    private boolean checkProductListAreAscending() {
        WebElement resultIsLoaded = new WebDriverWait(webDriver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class=\"a-price-whole\"]")));
        List<Double> productsList = new ArrayList<>();

        if (resultIsLoaded.isDisplayed()) {
            List<WebElement> listOfProductsPriceWhole = webDriver.findElements(By.xpath("//*[@class=\"a-price-whole\"]"));

            List<WebElement> listOfProductsPriceDecimal = webDriver.findElements(By.xpath("//*[@class=\"a-price-fraction\"]"));

            Iterator<WebElement> wholePrice = listOfProductsPriceWhole.iterator();
            Iterator<WebElement> decimalPrice = listOfProductsPriceDecimal.iterator();


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

        }

        return Ordering.natural().isOrdered(productsList);
    }


    public void setupAmazon(){
        openAmazonAndMaximazeWindow();
    }

    public void openAmazonAndMaximazeWindow() {
        webDriver.get("https://www.amazon.com/");
        webDriver.manage().window().maximize();
    }

    public void openCategory() {
        WebElement categoryElement;
        try {
            categoryElement = webDriver.findElement(By.partialLinkText("Chairs"));
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("NO SUCH ELEMENT");
        }
        categoryElement.click();

    }


    @AfterTest
    public void closeDriver() {
        webDriver.quit();
    }
}
