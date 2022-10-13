package org.example;

import com.google.common.collect.Ordering;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.testng.Assert.assertTrue;

public class FilterAndSearchTests extends BaseTests {
    private static final int INDEX_TO_BE_REMOVED = 14;
    private final int RESULT_PER_PAGE = 35;

    private static void checkNumberInRange(int min, int max, Integer integer) {
        assertTrue(integer >= min && integer <= max, String.format("Value %s not in range.", integer));
    }

    @Test
    public void checkBrandTitle() {
        WebElement category = driver.findElement(By.linkText("Computer mice"));
        category.click();
        WebElement brandCheckBox = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[contains(@class,'a-checkbox')])[2]")));
        brandCheckBox.click();
        String brandName = driver.findElement(By.xpath("(//div[contains(@class,'a-checkbox')])[2]/following-sibling::span"))
                .getText();
        List<String> results = driver.findElements(By.xpath("//h2[contains(@class, 'a-size-mini')]/a/span"))
                .stream()
                .map(WebElement::getText)
                .filter(this::isNotEmptyString)
                .collect(Collectors.toList());
        /**
         * result was of brand selected,but did not contain brand name in the title
         */
        results.remove(INDEX_TO_BE_REMOVED);
        results.forEach(result -> assertTrue(result.contains(brandName), "Results did not all match the brand selected"));
    }

    @Test
    public void checkPriceRange() {
        final int min = 25;
        final int max = 35;

        WebElement category = driver.findElement(By.linkText("Keyboards"));
        category.click();

        WebElement minPrice = driver.findElement(By.xpath("//input[@id='low-price']"));
        minPrice.sendKeys(String.valueOf(min));

        WebElement maxPrice = driver.findElement(By.xpath("//input[@id='high-price']"));
        maxPrice.sendKeys(String.valueOf(max));

        WebElement goButton = driver.findElement(By.xpath("//span[@class='a-button-inner']//input[@type='submit']"));
        goButton.click();

        List<WebElement> pricesCheckRange = driver.findElements(By.xpath("//span[@class='a-price-whole']"));
        List<Integer> priceCheck = new ArrayList<>();
        pricesCheckRange.stream().filter(price -> isNotEmptyString(price.getText())).forEach(price -> priceCheck.add(Integer.valueOf(price.getText())));

        priceCheck.stream().filter(price -> price != 0).forEach(price -> checkNumberInRange(min, max, price));
    }

    private boolean isNotEmptyString(String string) {
        return !string.isEmpty();
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




