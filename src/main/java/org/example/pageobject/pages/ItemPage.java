package org.example.pageobject.pages;

import org.example.pageobject.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ItemPage extends BasePage {

    @FindBy (xpath=("//*[@id=\"add-to-cart-button\"]"))
    private WebElement addToCartButton;

    public ItemPage(WebDriver driver) {
        super(driver);
    }

    public String getItemLinkAfterAddingToCart(){

        By element = By.xpath("//*[@id='add-to-cart-confirmation-image']/div/a");
        WebElement item = new WebDriverWait(driver,MEDIUM_WAIT).until(ExpectedConditions.visibilityOfElementLocated(element));
        String urlToItem = item.getAttribute("href");
        return urlToItem;
    }

    public String getCurrentUrl(){
        return driver.getCurrentUrl();
    }

    public String addOneItemToCart(){

        this.addToCartButton.click();
        return getItemLinkAfterAddingToCart();
    }
}
