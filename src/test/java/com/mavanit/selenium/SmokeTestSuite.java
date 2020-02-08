package com.mavanit.selenium;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class SmokeTestSuite extends Hooks {
    @Test
    public void searchTest()
    {
        //Search
        driver.findElement(By.id("searchTerm")).sendKeys("puma");
        driver.findElement(By.id("searchTerm")).sendKeys(Keys.ENTER);

        //Asserts
        String url=driver.getCurrentUrl();
        assertThat(url,endsWith("puma"));
        System.out.println("Current URl"+url);
        //Collect a item to list
        //loop and verify
        //product contains string
        List<WebElement> productWebElement=driver.findElements(By.cssSelector("a[data-test='component-product-card-title']") );
        for (WebElement indProduct:productWebElement)
        {
            String actual=indProduct.getText();
            assertThat(actual,containsString("Puma"));
            System.out.println("Products:"+actual);
        }
        //Assert 3 - Title
        String actualTitle=driver.findElement(By.className("search-title__term")).getText();
        assertThat(actualTitle,is(equalToIgnoringCase("puma") ));

            }
    @Test
    public void basketTest()
    {
        driver.findElement(By.id("searchTerm")).sendKeys("nike");
        driver.findElement(By.id("searchTerm")).sendKeys(Keys.ENTER);
        List<WebElement> productWebElement=driver.findElements(By.cssSelector("a[data-test='component-product-card-title']") );

              driver.findElement(By.xpath("//*[@id=\"findability\"]/div/div[6]/div[1]/div[5]/div[3]/div[5]/div[1]/div/a") ).click();
        driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/div[1]/div[2]/section[2]/section/div[10]/div/div[2]/button/span/span") ).click();
    }
    @Test
    public void customerRating()
    {

        driver.findElement(By.id("searchTerm")).sendKeys("nike");
        driver.findElement(By.id("searchTerm")).sendKeys(Keys.ENTER);
        driver.findElement(By.cssSelector("label[id=\"4 or more-label\"]")).click();
        String pvalues=driver.findElement(By.xpath("//*[@id=\"findability\"]/div/div[6]/div[1]/div[5]/div[3]/div[4]/div[1]/div/div[3]")).getAttribute("data-star-rating");
        System.out.println("One value:"+pvalues);
        //List<WebElement> valuesOfRating=driver.findElements(By.cssSelector("div[data-test=\"component-product-card\"]"));
        //for (WebElement newValue:valuesOfRating)
        {
            String vals=newValue.getText();
            System.out.println(vals);
            // System.out.println("Hi:"+newValue.getAttribute("div[data-star-rating]"));
          //  String starRating=newValue.getAttribute("data-star-rating");
            //System.out.println("Ratings:"+starRating);
        }
    }

}
