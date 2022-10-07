package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Array;
import java.time.Duration;
import java.util.*;

public class DeliverToFunctionalityTest {
    private WebDriver webdriver;

    @BeforeMethod
    public void setUpDriver() {
        System.setProperty(
                "webdriver.chrome.driver",
                "src/test/resources/webdriver/chromedriver.exe"
        );
        webdriver = new ChromeDriver();
        webdriver.get("https://www.amazon.com/");
        webdriver.manage().window().maximize();
    }

    @Test
    public void deliverToValueUpdate() throws InterruptedException {

        WebElement deliverToButton = webdriver.findElement(By.id("glow-ingress-block"));
        deliverToButton.click();

        WebElement usZipCodeField = new WebDriverWait(webdriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id=\"GLUXZipUpdateInput\"]")));
        usZipCodeField.sendKeys("36104");

        WebElement deliverApplyLocationButton = new WebDriverWait(webdriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id=\"GLUXZipUpdate\"]")));
        deliverApplyLocationButton.click();

        WebElement deliverContinueLocationButton = new WebDriverWait(webdriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'a-popover-footer')]/span[contains(@class, 'a-button a-column a-button-primary a-button-span4')]")));
        deliverContinueLocationButton.click();
        String expectedDeliveryAddress = "Montgomery 36104";
        WebElement deliverLocationLabel = new WebDriverWait(webdriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format("//span[@id='glow-ingress-line2' and contains(text(), '%s')]", expectedDeliveryAddress))));
        Assert.assertTrue(deliverLocationLabel.getText().contains(expectedDeliveryAddress), "Location are not the same");
    }

    @Test
    public void VerifyListOfCountries() {
        WebElement deliverToButton = webdriver.findElement(By.id("glow-ingress-block"));
        deliverToButton.click();
        WebElement deliverShipOutsideUsButton = new WebDriverWait(webdriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id=\"GLUXCountryListDropdown\"]/span")));
        deliverShipOutsideUsButton.click();
        List<WebElement> countryList = (webdriver.findElements(By.xpath("//div[contains(@class, 'a-popover-wrapper')]//a[contains(@class, 'a-dropdown-link')]")));
        for (WebElement element : countryList
        ) {
            if (element.getText().equals("Poland")) {
                element.click();
            }
        }

    }

    @Test
    public void ChooseCountry() {

        WebElement deliverToButton = webdriver.findElement(By.id("glow-ingress-block"));
        deliverToButton.click();
        WebElement deliverShipOutsideUsButton = new WebDriverWait(webdriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id=\"GLUXCountryListDropdown\"]/span")));
        deliverShipOutsideUsButton.click();
        List<WebElement> countryList = (webdriver.findElements(By.xpath("//div[contains(@class, 'a-popover-wrapper')]//a[contains(@class, 'a-dropdown-link')]")));
        countryList.get(3).click();
        WebElement deliverdoneButton = new WebDriverWait(webdriver, Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='a-button a-button-primary']//span[@class='a-button-inner']")));
        deliverdoneButton.click();
        WebElement productcategory = new WebDriverWait(webdriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Chairs")));
        productcategory.click();
        WebElement productselect = new WebDriverWait(webdriver, Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='s-widget-container s-spacing-small s-widget-container-height-small celwidget slot=MAIN template=SEARCH_RESULTS widgetId=search-results_5']//div[@class='s-card-container s-overflow-hidden aok-relative puis-expand-height puis-include-content-margin s-latency-cf-section s-card-border']//div[@class='a-section a-spacing-base']//div[@class='a-section a-spacing-micro puis-padding-left-small puis-padding-right-small']//div[@class='a-section a-spacing-none a-spacing-top-micro s-title-instructions-style']//h2[@class='a-size-mini a-spacing-none a-color-base s-line-clamp-2']//a[@class='a-link-normal s-underline-text s-underline-link-text s-link-style a-text-normal']")));
        productselect.click();
        WebElement expectedDeliverLocationLabel = new WebDriverWait(webdriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='glow-ingress-line2']")));
        WebElement DeliverToLocation = new WebDriverWait(webdriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='contextualIngressPtLabel_deliveryShortLine']//span[last()]")));
        Assert.assertTrue(DeliverToLocation.getText().contains(expectedDeliverLocationLabel.getText()), "Location of delivery not correct");
    }
    @AfterMethod
    public void closeDriver() {
        webdriver.quit();
    }
}


