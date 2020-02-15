package com.mavanit.selenium;

        import org.junit.Test;
        import org.openqa.selenium.By;
        import org.openqa.selenium.Keys;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.support.ui.Select;

        import java.util.List;
        import java.util.Random;
        import java.util.concurrent.TimeUnit;

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
        //String selectedProductName = selectedElement.getText();
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

   public void waitTime(int time)
    {
        driver.manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);
    }

    @Test
    public void continueShopping() throws InterruptedException,NumberFormatException
    {
        doSearch("puma");
        waitTime(5000);
        List<WebElement> productWebElements = driver.findElements(By.cssSelector("a[data-test='component-product-card-title']"));
        if (productWebElements.size() == 0) {
            fail("No Products found with: " + "nike");
        }

        Random random = new Random();
        int randomNumber = random.nextInt(productWebElements.size() - 1);

        WebElement selectedElement = productWebElements.get(randomNumber);
        selectedElement.click();

        //Drag and drop selection

        WebElement NoOfQuantity=driver.findElement(By.cssSelector(".xs-4--none select[data-test=\"select\"]"));
        Select selectedNumber=new Select(NoOfQuantity);
        selectedNumber.selectByVisibleText("1");

        addToBasket();

        //Continue shopping
        driver.findElement(By.cssSelector("button[data-test=\"component-att-button-continue\"]")).click();

        doSearch("nike");

        List<WebElement> productWebElements1 = driver.findElements(By.cssSelector("a[data-test='component-product-card-title']"));
        if (productWebElements1.size() == 0) {
            fail("No Products found with: " + "nike");
        }

        Random random1 = new Random();
        int randomNumber1 = random1.nextInt(productWebElements1.size() - 1);

        WebElement selectedElement1 = productWebElements1.get(randomNumber1);
        selectedElement1.click();

        //Drag and drop selection
         WebElement NoOfQuantity1=driver.findElement(By.cssSelector(".xs-4--none select[data-test=\"select\"]"));
        Select selectedNumber1=new Select(NoOfQuantity1);
        selectedNumber1.selectByVisibleText("1");

        addToBasket();
        goToBasket();

        int noOfProduct;
        double productPrice,unitPrice,totalIndProduct;
        double grossTotal=0.0;

        waitTime(5000);
        List<WebElement> productsInBasket=driver.findElements(By.cssSelector(".xs-12--none.md-6--none.lg-7--none.undefined li[data-e2e=\"basket-productcard\"]"));
        for (WebElement indiProduct : productsInBasket)
        {
            String nameOfProduct=indiProduct.findElement(By.cssSelector(".ProductCard__content__9U9b1.xsHidden.lgFlex .ProductCard__titleLink__1PgaZ")).getText();
            System.out.println("Name of the product="+nameOfProduct);

           //Selection of number of products
            WebElement noOfQuantity=indiProduct.findElement(By.cssSelector(".ProductCard__quantityContainer__2gY5E .ProductCard__quantitySelect__2y1R3"));
            Select selectedNo=new Select(noOfQuantity);
            WebElement valueSelected=selectedNo.getFirstSelectedOption();
            String presentValue=valueSelected.getText();
            noOfProduct=Integer.parseInt(presentValue);
            System.out.println("No of Quantity"+noOfProduct);

            //Selection of product price
            String productValue=indiProduct.findElement(By.cssSelector(".ProductCard__pricesContainer__dA7SA .ProductCard__price__1vkg0")).getText();
            System.out.println("Product value:"+productValue);
            productPrice=(productValue.contains("£"))?Double.parseDouble(productValue.replace("£"," ")):0.0;
            System.out.println("Products total amount"+productPrice);

            //Selection of unit price
            if(noOfProduct==1)
            {
                totalIndProduct=resultCalculation(productPrice,noOfProduct);

            }
            else
            {
                String productsUnitAmount=indiProduct.findElement(By.cssSelector(".ProductCard__unitPrice__rTWTs span[data-e2e=\"product-unit-price\"]")).getText();
                unitPrice=(productsUnitAmount.contains("£"))?Double.parseDouble(productsUnitAmount.replace("£"," ")):0.0;
                System.out.println("Total Amount"+unitPrice);
                totalIndProduct=resultCalculation(unitPrice,noOfProduct);
            }
            grossTotal=grossTotal+totalIndProduct;
        }
        System.out.println("Gross Total="+grossTotal);

        String actualValue=driver.findElement(By.cssSelector(".Summary__totalInformation__2hwn3 .Summary__subTotalLabel__2GphY")).getText();
        System.out.println("Total value="+actualValue);
        double totalValue=(actualValue.contains("£"))?Double.parseDouble(actualValue.replace("£","")):0.0;
        System.out.println("Actual Total Amount="+totalValue);

        assertThat(totalValue,is(grossTotal));
        }

}
