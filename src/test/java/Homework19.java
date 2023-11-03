import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Homework19 extends BaseTest{
@Test
    public void deletePlaylist() throws InterruptedException{
    String expectedPlaylistDeletedMessage = "Deleted playlist \"shriya.\"";

    navigateToLoginPage();
    provideEmail("akansha.shukla@testpro.io");
    providePassword("te$t$tudent");
    clickSubmit();
    Thread.sleep(2000);
    openPlaylist();
    clickDeletePlaylistBtn();
    Assert.assertEquals(getDeletedPlaylistMsg(),expectedPlaylistDeletedMessage);

}
    public void openPlaylist(){
        WebElement emptyPlaylist = driver.findElement(By.cssSelector(".playlist:nth-child(3)"));
        emptyPlaylist.click();
    }
    public void clickDeletePlaylistBtn(){
    WebElement deletePlaylist = driver.findElement(By.cssSelector(".btn-delete-playlist"));
    deletePlaylist.click();
    }
    public String getDeletedPlaylistMsg(){
    WebElement notificationMsg = driver.findElement(By.cssSelector("div.success.show"));
    return notificationMsg.getText();
    }
}
