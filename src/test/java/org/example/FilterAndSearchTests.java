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
    private static final int INDEX_TO_BE_REMOVED = 14;
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

    private static void checkNumberInRange(int min, int max, Integer integer) {
        assertTrue(integer >= min && integer <= max, String.format("Value %s not in range.", integer));
    }

    private boolean isNotEmptyString(String string) {
        return !string.isEmpty();
    }
}




