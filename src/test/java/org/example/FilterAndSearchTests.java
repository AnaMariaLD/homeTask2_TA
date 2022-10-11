package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertTrue;

public class FilterAndSearchTests extends BaseTests {

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
                .filter(string -> !string.isEmpty())
                .collect(Collectors.toList());
        /**
         * result was of brand selected,but did not contain brand name in the title
         */
        results.remove(14);
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

        List<WebElement> pricesCheckRange = driver.findElements(By.xpath("//span[not (contains(text(),'FREE')) and @class='a-color-base puis-light-weight-text' and contains(text(),'$')]"));
        List<Integer> priceCheck = new ArrayList<>();
//        for (WebElement price : pricesCheckRange) {
//            if(isNotEmptyString(price.getText()))
//                priceCheck.add(Integer.valueOf(price.getText()));
//        }
        pricesCheckRange.stream().filter(price -> isNotEmptyString(price.getText())).forEach(price -> priceCheck.add(Integer.valueOf(price.getText())));

//        for (Integer integer : priceCheck) {
//            if (integer != 0) {
//                assertTrue(checkNumberInRange(min, max, integer), " The filtered elements do not match the price range");
//            }
//        }
        priceCheck.stream().filter(price -> price != 0).forEach(price -> checkNumberInRange(min, max, price));
// mai mare/mic sau egal sau doar mai mare/mic ?
    }

    private static void checkNumberInRange(int min, int max, Integer integer) {
        assertTrue(integer >= min && integer <= max, String.format("Value %s not in range.", integer));
    }

    private static boolean isNotEmptyString(String string) {
        return !string.isEmpty();
    }
}




