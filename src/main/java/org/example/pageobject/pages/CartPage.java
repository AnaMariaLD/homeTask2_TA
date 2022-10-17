package org.example.pageobject.pages;

import org.example.pageobject.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class CartPage extends BasePage {
    public CartPage(WebDriver driver) {
        super(driver);
    }

    public Boolean removeAllElementsFromCart(){
        WebElement deleteButton = new WebDriverWait(driver, MEDIUM_WAIT)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='Delete']")));

        boolean deleted = false;
        while (deleteButton.isDisplayed()){
            deleted = true;
            deleteButton.click();
            deleteButton = driver.findElement(By.xpath("//input[@value='Delete']"));
        }
        return deleted;
    }


}
