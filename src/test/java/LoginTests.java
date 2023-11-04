import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

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

       // WebElement avatarIcon = driver.findElement(By.cssSelector("img[class='avatar']"));
        WebElement avatarIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img[class='avatar']")));
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
        WebElement avatarIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img[class='avatar']")));
        //WebElement avatarIcon = driver.findElement(By.cssSelector("img[class='avatar']"));
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
