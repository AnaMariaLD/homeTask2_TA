package org.example;

import org.example.pageobject.modules.DeliverToModule;
import org.example.pageobject.pages.CategoryPage;
import org.example.pageobject.pages.HomePage;
import org.example.pageobject.pages.ItemPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.*;

public class DeliverToTest extends BaseTests {
    HomePage homePage = new HomePage(driver);
    DeliverToModule deliverTo = new DeliverToModule(driver);
    CategoryPage categoryPage = new CategoryPage(driver);
    ItemPage itemPage = new ItemPage(driver);
    private final String usZipCode = "36104";
    private final String expectedDeliveryAdress = "Montgomery 36104";
    private final String countrySampleCheck = "Poland";
    private final Integer indexOfCountry = 3;
    @BeforeMethod
    public void settingUpDriver() {
        setUpDriver();
    }
    @AfterTest
    public void quitDriver() {
        quit();
    }

    @Test
    public void deliverToValueUpdate() {
        homePage.open()
                .openDeliverToModule()
                .select(usZipCode);
        deliverTo.confirmUsLocation();
        Assert.assertTrue(homePage.open().getDeliveryLocationLabel().contains(expectedDeliveryAdress), "Location are not the same");
    }

    @Test
    public void VerifyListOfCountries() {
        homePage.open()
                .openDeliverToModule()
                .selectDeliverShipOutsideUsButton();
        deliverTo.checkAnyMatchforRequiredCountrySearch(countrySampleCheck);
}

    @Test
    public void ChooseCountry() {
        homePage.open()
                .openDeliverToModule()
                .selectDeliverShipOutsideUsButton();
        deliverTo.getCountryOutsideUs(indexOfCountry);
        deliverTo.confirmDeliverOutsideUs();
        ItemPage item= homePage.open()
                .goToCategory()
                .getOneRandomItemPage();
        Assert.assertTrue(itemPage.getItemShippmentLocation().contains(homePage.getDeliveryLocationLabel()),"Location of delivery is not correct");
    }

}




