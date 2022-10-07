package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Random;

public class AppTest {

    private WebDriver webDriver;
    private final int ELEMENT_IN_CART = 1;

    @BeforeTest
    public void setUpDriver() {
        System.setProperty(
                "webdriver.chrome.driver",
                "src/test/resources/webdriver/chromedriver.exe"
        );

        this.webDriver = new ChromeDriver();
        setupAmazon();
    }

    @DataProvider(name = "Categories")
    public Object[][] categoriesOnAmazonHomePage(){
        return new Object[][]{
                {"Keyboards"}
        };
    }

    @Test(dataProvider = "Categories")
    public void addMultipleElementToCart(String category) {
        for(int i = 0; i < ELEMENT_IN_CART; i++){
            openCategory(category);
            addOneRandomElementToCartFromCategory();
            returnToAmazonHomePage();
        }

    }

    @Test(dataProvider = "Categories")
    public void emptyCartWithItems(String category) {
        addMultipleElementToCart(category);
        removeEelementFromCart();
    }

    public void addOneRandomElementToCartFromCategory(){
        int random = new Random().nextInt(25)+2;
        WebElement productFromList = new WebDriverWait(webDriver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[@class=\"a-link-normal s-no-outline\"])["+random+"]")));
        productFromList.click();

        WebElement addToCart = new WebDriverWait(webDriver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"add-to-cart-button\"]")));
        addToCart.click();

        Boolean afterAddedToCart = new WebDriverWait(webDriver, Duration.ofSeconds(10)).until(ExpectedConditions.titleIs("Amazon.com Shopping Cart"));
        Assert.assertEquals(afterAddedToCart,true);
    }

    //TODO to improve, to remove all element element
    public void removeEelementFromCart() {
        openCartFromNavBar();
            WebElement deleteButton = webDriver.findElement(By.xpath("//input[@value=\"Delete\"]"));

            while (deleteButton.isDisplayed()){
                deleteButton.click();
                deleteButton = webDriver.findElement(By.xpath("//input[@value=\"Delete\"]"));
            }

    }

    public void openCartFromNavBar() {
        WebElement cart = new WebDriverWait(webDriver,Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-cart")));
        cart.click();
    }

    public void openAmazonAndMaximazeWindow() {
        webDriver.get("https://www.amazon.com/");
        webDriver.manage().window().maximize();
    }


    public void openCategory(String category) {
        WebElement categoryElement;
        try {
            categoryElement = webDriver.findElement(By.partialLinkText(category));
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("NO SUCH ELEMENT");
        }
        categoryElement.click();

    }

    public void setupAmazon(){
        openAmazonAndMaximazeWindow();
    }

    public void returnToAmazonHomePage(){
        WebElement homePage = new WebDriverWait(webDriver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-logo-sprites")));
        homePage.click();
    }


    @AfterTest
    public void closeDriver() {
        webDriver.quit();
    }
}
