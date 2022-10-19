package org.example.pageobject.pages;

import org.example.pageobject.BasePage;
import org.example.utils.SupportMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

public class SearchPage extends BasePage {

    private static final int RESULT_PER_PAGE = 5;

    private final By byNoResultsMessage = By.xpath("//*[text()='No results for ']");
    private final By byResultsForMessage =By.xpath("//span[text()='\"laptop\"']");
    private final By byResultsList = By.className("a-size-medium");

    private final By bySortDropDown = By.xpath("//*[@class='a-dropdown-prompt']");

    private final By bySortDropDownLowToHigh = By.xpath("//*[@class=\"a-popover-wrapper\"]/div/ul/li[2]");

    private final By ByItemWholePrice = By.xpath("//*[@class='a-price-whole']");
    private final By ByItemFractionPrice = By.xpath("//*[@class='a-price-fraction']");

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public void sortItemsByAscendingPrice(){
        WebElement dropDown = new WebDriverWait(driver, MEDIUM_WAIT)
                .until(ExpectedConditions.visibilityOfElementLocated(bySortDropDown));
        dropDown.click();

        WebElement dropDownLowToHigh = new WebDriverWait(driver, MEDIUM_WAIT)
                .until(ExpectedConditions.visibilityOfElementLocated(bySortDropDownLowToHigh));
        dropDownLowToHigh.click();
    }

    public List<Double> getFullPriceItemList(){
        //Check if the website is reloaded
        WebElement resultIsLoaded = new WebDriverWait(driver, MEDIUM_WAIT)
                .until(ExpectedConditions.visibilityOfElementLocated(ByItemWholePrice));


        if (resultIsLoaded.isDisplayed()) {
            List<WebElement> listOfProductsPriceWhole = driver.findElements(ByItemWholePrice);
            List<WebElement> listOfProductsPriceFraction = driver.findElements(ByItemFractionPrice);

            return SupportMethods.getFullPrice(listOfProductsPriceWhole.iterator(), listOfProductsPriceFraction.iterator(), RESULT_PER_PAGE);
        }
        return null;
    }

    public String getNoResultsMessage() {
        WebElement noResultsMessage = new WebDriverWait(driver, MEDIUM_WAIT)
                .until(ExpectedConditions.visibilityOfElementLocated(byNoResultsMessage));
        return noResultsMessage.getText();
    }

    public String checkResultsForPhrase() {
        WebElement resultsForMessage = new WebDriverWait(driver, MEDIUM_WAIT)
                .until(ExpectedConditions.visibilityOfElementLocated(byResultsForMessage));
        return resultsForMessage.getText();
    }

    public boolean checkAnyMatchForResults(String searchPhrase) {
        List<WebElement> resultsList = new WebDriverWait(driver, MEDIUM_WAIT)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(byResultsList));
        return resultsList.stream()
                .map(WebElement::getText)
                .anyMatch(text -> containsIgnoreCase(text, searchPhrase));
    }
}
