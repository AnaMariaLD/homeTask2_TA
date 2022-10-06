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
    private final int ELEMENT_IN_CART = 2;

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
                {"Keyboards"}, {"Chairs"}, {"Headsets"}
        };
    }

    @Test(dataProvider = "Categories")
    public void addMultipleElementToCart(String category) throws InterruptedException {
        for(int i = 0; i < ELEMENT_IN_CART; i++){
            openCategory(category);
            addOneRandomElementToCartFromCategory();
            returnToAmazonHomePage();
        }

    }


    @Test
    public void addOneRandomElementToCartFromCategory(){
        int random = new Random().nextInt(25)+2;
        WebElement productFromList = new WebDriverWait(webDriver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"search\"]/div[1]/div[1]/div/span[3]/div[2]/div["+random+"]/div/div/div")));
        productFromList.findElement(By.tagName("a")).click();

        try {
            WebElement addToCart = new WebDriverWait(webDriver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.name("submit.add-to-cart")));
            addToCart.click();
        } catch (NoSuchElementException e){
            throw new NoSuchElementException("NO SUCH ELEMENT");
        }

        Boolean afterAddedToCart = new WebDriverWait(webDriver, Duration.ofSeconds(10)).until(ExpectedConditions.titleIs("Amazon.com Shopping Cart"));
        Assert.assertEquals(afterAddedToCart,true);

    }

    public void openCartFromNavBar() {
        WebElement cart = webDriver.findElement(By.id("nav-cart"));
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

    public void backButton(){
        //WebElement backbutton = new WebDriverWait(webDriver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-logo-sprites")));
        webDriver.navigate().back();
    }

    @AfterTest
    public void closeDriver() {
        webDriver.quit();
    }
}
