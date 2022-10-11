package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import org.testng.annotations.IFactoryAnnotation;
import org.testng.annotations.Test;

import java.awt.font.FontRenderContext;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertTrue;

public class FilterAndSearchTests  {
    private static final int INDEX_TO_BE_REMOVED = 14;
    protected WebDriver driver;
    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/webdriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://www.amazon.com");
        driver.manage().window().fullscreen();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
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
                .filter(this::isNotEmpty)
                .collect(Collectors.toList());
        /**
         * result was of brand selected,but did not contain brand name in the title
         */
        results.remove(INDEX_TO_BE_REMOVED);
        results.forEach(result -> assertTrue(result.contains(brandName), "Results did not all match the brand selected"));
    }

    private boolean isNotEmpty(String string){
        return !string.isEmpty();
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

        for (WebElement price : pricesCheckRange) {
            priceCheck.add(Integer.valueOf(price.getText()));
        }
        for (int i = 0; i < priceCheck.size(); i++) {
            if (priceCheck.get(i).intValue() != 0) {
                assertTrue(priceCheck.get(i).intValue() > min && priceCheck.get(i).intValue() < max, " The filtered elements do not match the price range");
            }
        }

    }
}




