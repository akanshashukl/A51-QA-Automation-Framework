import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTests extends BaseTest {


    @Test
    public void loginEmptyEmailPassword() throws InterruptedException {

        navigateToLoginPage();
        clickSubmit();
        Assert.assertEquals(driver.getCurrentUrl(),url);
    }
    @Test
    public void loginValidEmailPassword()throws InterruptedException{

        //steps

        navigateToLoginPage();
        provideEmail("akansha.shukla@testpro.io");
        providePassword("te$t$tudent");
        clickSubmit();

        WebElement avatarIcon = driver.findElement(By.cssSelector("img[class='avatar']"));
        //Expected result
        Assert.assertTrue(avatarIcon.isDisplayed());
    }
    @Test
    public void loginInValidEmailValidPassword() throws InterruptedException{
        //steps
        navigateToLoginPage();
        provideEmail("InvalidEmail@testpro.io");
        providePassword("te$t$tudent");
        clickSubmit();
        //Expected result
        WebElement avatarIcon = driver.findElement(By.cssSelector("img[class='avatar']"));
        //Expected result
        Assert.assertTrue(avatarIcon.isDisplayed());
    }
    @Test
    public void loginValidEmailNoPassword()throws InterruptedException{

        //steps
        navigateToLoginPage();
        provideEmail("akansha.shukla@testpro.io");
        providePassword("");
        clickSubmit();

        WebElement avatarIcon = driver.findElement(By.cssSelector("img[class='avatar']"));
        //Expected result
        Assert.assertTrue(avatarIcon.isDisplayed());
    }
}
