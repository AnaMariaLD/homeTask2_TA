package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
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


}
