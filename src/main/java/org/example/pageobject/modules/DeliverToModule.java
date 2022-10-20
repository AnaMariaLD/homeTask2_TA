package org.example.pageobject.modules;

import org.example.pageobject.BasePage;
import org.example.pageobject.pages.SearchPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

public class DeliverToModule extends BasePage {

    private WebElement usZipCodeField;
    private WebElement deliverApplyLocationButton;
    private WebElement continueConfirmLocationButton;
    private WebElement deliverShipOutsideUsButton;
    private WebElement confirmDeliveryOutsideUs;
    public DeliverToModule(WebDriver driver) {
        super(driver);
    }
    public void select(String text){
        usZipCodeField = new WebDriverWait(driver, SHORT_WAIT)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id=\"GLUXZipUpdateInput\"]")));
        usZipCodeField.sendKeys(text + Keys.ENTER);
    }
    public void confirmUsLocation(){
        deliverApplyLocationButton = new WebDriverWait(driver,SHORT_WAIT)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'a-popover-footer')]/span[contains(@class, 'a-button a-column a-button-primary a-button-span4')]")));
        deliverApplyLocationButton.click();
        continueConfirmLocationButton = new WebDriverWait(driver, SHORT_WAIT)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'a-popover-footer')]/span[contains(@class, 'a-button a-column a-button-primary a-button-span4')]")));
        continueConfirmLocationButton.click();
    }
    public void selectDeliverShipOutsideUsButton(){
        deliverShipOutsideUsButton =  new WebDriverWait(driver, SHORT_WAIT)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id=\"GLUXCountryListDropdown\"]/span")));
        deliverShipOutsideUsButton.click();
    }
    public boolean checkAnyMatchforRequiredCountrySearch(String countryCheck) {
        List<WebElement> countryList = new WebDriverWait(driver, LONG_WAIT)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(@class, 'a-popover-wrapper')]//a[contains(@class, 'a-dropdown-link')]")));
        return countryList.stream()
                .map(WebElement::getText)
                .anyMatch(text -> containsIgnoreCase(text, countryCheck));
    }
    public void getCountryOutsideUs(Integer indexofCountry){
        List<WebElement> countryList = (driver.findElements(By.xpath("//div[contains(@class, 'a-popover-wrapper')]//a[contains(@class, 'a-dropdown-link')]")));
        countryList.get(indexofCountry).click();
    }
    public void confirmDeliverOutsideUs(){
        confirmDeliveryOutsideUs = new WebDriverWait(driver, SHORT_WAIT)
               .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='a-button a-button-primary']//span[@class='a-button-inner']")));
        confirmDeliveryOutsideUs.click();
    }
}
