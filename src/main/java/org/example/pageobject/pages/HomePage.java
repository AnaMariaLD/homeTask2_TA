package org.example.pageobject.pages;

import org.example.pageobject.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class HomePage extends BasePage {


    private WebElement searchField;
    @FindBy (css = "[aria-label='Computers & Accessories']")
    private WebElement category;
    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy (id = "nav-cart")
    private WebElement cartButton;

    public SearchPage search(String text){
        searchField = new WebDriverWait(driver, SHORT_WAIT)
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("twotabsearchtextbox")));
        searchField.sendKeys(text + Keys.ENTER);
        return new SearchPage(driver);
    }

    public CategoryPage goToCategory(){
        this.category.click();
        return new CategoryPage(driver);
    }

    public HomePage open(){
        driver.get("https://www.amazon.com/");
        return this;
    }

    public CartPage openCartPage(){
        cartButton.click();
        return new CartPage(driver);
    }
}
