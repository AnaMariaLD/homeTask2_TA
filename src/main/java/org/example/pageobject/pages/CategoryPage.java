package org.example.pageobject.pages;

import org.example.pageobject.BasePage;
import org.example.utils.SupportMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;



public class CategoryPage extends BasePage {

    @FindBy (xpath="//span[contains(text(),'Featured Brands')]/parent::div/parent::div")
    private WebElement featuredBrands;
    private final By brandCheckbox =By.cssSelector(".a-icon.a-icon-checkbox");
    private final By brandName = By.cssSelector(".a-size-base.a-color-base.a-text-bold");
    @FindBy (xpath = "//div[contains(@class, 's-title-instructions-style')]")
    private List<WebElement> brandResultsList;
    @FindBy(xpath ="//span[@class='a-price-whole']" )
    List<WebElement> pricesCheckRange ;
    List<Integer> priceCheck = new ArrayList<>();

    private SupportMethods supportMethods = new SupportMethods();

    public CategoryPage(WebDriver driver) {
        super(driver);
    }

    public void clickOnBrand(){
        this.featuredBrands.findElement(brandCheckbox).click();

    }

    public ItemPage getOneRandomItemPage(){
        By oneRandomItem = By.xpath("(//a[@class='a-link-normal s-no-outline'])["+4+"]");
        WebElement item = new WebDriverWait(driver,MEDIUM_WAIT).until(ExpectedConditions.visibilityOfElementLocated(oneRandomItem));
        item.click();
        return new ItemPage(driver);

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
    public void selectPriceRange(String min, String max){
       WebElement priceLowerLimit = new WebDriverWait(driver,SHORT_WAIT).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='low-price']")));
       priceLowerLimit.sendKeys(min);
        WebElement priceHigherLimit = new WebDriverWait(driver,SHORT_WAIT).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='high-price']")));
       priceHigherLimit.sendKeys(max);
       WebElement goButton = driver.findElement(By.xpath("//span[@class='a-button-inner']//input[@type='submit']"));
       goButton.click();
    }

    public List<Integer> priceList(){
        pricesCheckRange.stream().filter(price -> supportMethods.isNotEmptyString(price.getText())).forEach(price -> priceCheck.add(Integer.valueOf(price.getText())));
        return priceCheck;
    }
}
