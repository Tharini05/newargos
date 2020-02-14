package com.mavanit.selenium;

        import org.junit.Test;
        import org.openqa.selenium.By;
        import org.openqa.selenium.Keys;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.support.ui.Select;

        import java.util.List;
        import java.util.Random;

        import static org.hamcrest.MatcherAssert.assertThat;
        import static org.hamcrest.Matchers.*;
        import static org.junit.Assert.fail;

public class SmokeTest extends Hooks {

    @Test
    public void searchTest() {
        doSearch("puma");

        String url = driver.getCurrentUrl();
        assertThat(url, endsWith("puma"));


        List<WebElement> productWebElements = driver.findElements(By.cssSelector("a[data-test='component-product-card-title']"));

        for (WebElement indProduct : productWebElements) {
            String actual = indProduct.getText();
            assertThat(actual, containsString("Puma"));
        }

        String actualTitle = driver.findElement(By.className("search-title__term")).getText();
        assertThat(actualTitle, is(equalToIgnoringCase("puma")));

        driver.findElement(By.id("searchTerm")).sendKeys("puma");
        driver.findElement(By.id("searchTerm")).sendKeys(Keys.ENTER);

    }

    @Test
    public void basketTest() throws InterruptedException {
        doSearch("nike");

        List<WebElement> productWebElements = driver.findElements(By.cssSelector("a[data-test='component-product-card-title']"));
        if (productWebElements.size() == 0) {
            fail("No Products found with: " + "nike");
        }

        // TODO: 2020-02-08 this will be converted in future
        Random random = new Random();
        int randomNumber = random.nextInt(productWebElements.size() - 1);

        WebElement selectedElement = productWebElements.get(randomNumber);
        String selectedProductName = selectedElement.getText();
        selectedElement.click();

        addToBasket();
        goToBasket();
        String actual = getProductInBasket();
        assertThat(actual, is(equalToIgnoringCase(selectedProductName)));
    }

    public void doSearch(String searchTerm) {
        driver.findElement(By.id("searchTerm")).sendKeys(searchTerm);

        driver.findElement(By.id("searchTerm")).sendKeys(Keys.ENTER);
    }


    public void addToBasket() {
        driver.findElement(By.cssSelector("button[data-test='component-att-button']")).click();
    }

    public void goToBasket() throws InterruptedException{
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".xs-row a[data-test='component-att-button-basket']")).click();
    }
    public String getProductInBasket(){
        return driver.findElement(By.cssSelector(".ProductCard__content__9U9b1.xsHidden.lgFlex .ProductCard__titleLink__1PgaZ")).getText();
    }
    @Test
    public void selectMultiple()throws InterruptedException
    {
        doSearch("nike");
        Thread.sleep(1500);
        List<WebElement> productWebElements = driver.findElements(By.cssSelector("a[data-test='component-product-card-title']"));
        if (productWebElements.size() == 0) {
            fail("No Products found with: " + "nike");
        }

        // TODO: 2020-02-08 this will be converted in future
        Random random = new Random();
        int randomNumber = random.nextInt(productWebElements.size() - 1);

        WebElement selectedElement = productWebElements.get(randomNumber);
        String selectedProductName = selectedElement.getText();
        selectedElement.click();

                //Drag and drop selection
        Thread.sleep(2500);
        WebElement NoOfQuantity=driver.findElement(By.cssSelector(".xs-4--none select[data-test=\"select\"]"));
        Select selectedNumber=new Select(NoOfQuantity);
        selectedNumber.selectByVisibleText("2");
        WebElement valueSelected=selectedNumber.getFirstSelectedOption();
        String value=valueSelected.getText();
        int noOfProducts=Integer.parseInt(value);
        System.out.println("No of Quantity="+noOfProducts);

        addToBasket();
        Thread.sleep(1500);
        goToBasket();

        Thread.sleep(2500);


        //String actualValue=driver.findElement(By.cssSelector(".ProductCard__price__1vkg0 .ProductCard__productLinePrice__3QC7V")).getText();
        String actualValue=driver.findElement(By.cssSelector(".Summary__totalInformation__2hwn3 .Summary__subTotalLabel__2GphY")).getText();
        System.out.println("Total value="+actualValue);
        double totalValue=(actualValue.contains("£"))?Double.parseDouble(actualValue.replace("£","")):0.0;
        System.out.println("Actual Total Amount="+totalValue);

        String productsAmount=driver.findElement(By.cssSelector(".ProductCard__unitPrice__rTWTs span[data-e2e=\"product-unit-price\"]")).getText();
        System.out.println("Amount of single product"+productsAmount);
        double unitPrice=(productsAmount.contains("£"))?Double.parseDouble(productsAmount.replace("£","")):0.0;
        System.out.println("Total Amount"+unitPrice);

        double expectedResult=resultCalculation(unitPrice,noOfProducts);
        System.out.println("Expected result="+expectedResult);

        assertThat(totalValue,is(expectedResult));
    }

    public double resultCalculation(double unitPrice,double noOfProducts)
    {
        if (noOfProducts!=0)
        return unitPrice+resultCalculation(unitPrice,noOfProducts-1);
        else
            return 0;
    }

}
