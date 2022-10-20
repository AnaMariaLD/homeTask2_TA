package org.example;

import org.example.pageobject.pages.HomePage;
import org.example.pageobject.pages.ItemPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class CartTest {

    WebDriver driver = BaseTests.driver;


    @BeforeMethod
    public void setUpDriver() {
        BaseTests.setUpDriver();
    }

    @Test
    public void addOneRandomElementToCartFromCategory(){
        HomePage homePage = new HomePage(driver);
        ItemPage item= homePage.open()
                .goToCategory()
                .getOneRandomItemPage();

        String itemUrl = item.getCurrentUrl();
        String resultUrl = item.addOneItemToCart();

        Assert.assertEquals(resultUrl,itemUrl);

    }

    @Test
    public void removeAllElementsFromCart() {
        HomePage homePage = new HomePage(driver);
        Boolean result = homePage.open()
                .openCartPage()
                .removeAllElementsFromCart();
        Assert.assertTrue(result);
    }

    @AfterTest
    public void closeDriver() {
        BaseTests.quit();
    }
}
