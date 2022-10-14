package org.example.pageobject.pages;

import org.example.pageobject.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

public class SearchPage extends BasePage {

    private final By byNoResultsMessage = By.xpath("//*[text()='No results for ']");
    private final By byResultsForMessage =By.xpath("//span[text()='\"laptop\"']");
    private final By byResultsList = By.className("a-size-medium");

    public SearchPage(WebDriver driver) {
        super(driver);
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
