package org.example.pageobject.pages;

import org.example.pageobject.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;



public class CategoryPage extends BasePage {

    @FindBy (xpath="//span[contains(text(),'Featured Brands')]/parent::div/parent::div")
    private WebElement featuredBrands;
    private final By brandCheckbox =By.cssSelector(".a-icon.a-icon-checkbox");
    private final By brandName = By.cssSelector(".a-size-base.a-color-base.a-text-bold");
    @FindBy (xpath = "//div[contains(@class, 's-title-instructions-style')]")
    private List<WebElement> brandResultsList;
    public CategoryPage(WebDriver driver) {
        super(driver);
    }

    public void clickOnBrand(){
        this.featuredBrands.findElement(brandCheckbox).click();

    }

    public String getBrandTitle(){
        return this.featuredBrands.findElement(brandName).getText();
    }

    public boolean checkAllResultsTitlesMatchBrand(String brandName){
        return this.brandResultsList
                .stream()
                .map(WebElement::getText)
                .allMatch(result ->result.contains(brandName));

    }
}
