import PageObjectModel.AllSongsPage;
import PageObjectModel.BasePage;
import PageObjectModel.HomePage;
import PageObjectModel.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class HomeTest extends BaseTest {

    @Test
    public void playSongWithContextClick()  {
        // Login
        provideEmail("akansha.shukla@testpro.io");
        providePassword("te$t$tudent");
        clickSubmit();
        //Choose All Songs List
        chooseAllSongsList();
        //Right/Context Click
        contextClickFirstSong();
        //Choose Play
        choosePlayOption();
        //Assertion
        Assert.assertTrue(isSongPlaying());
    }

    @Test
    public void playSong() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);
        AllSongsPage songsPage = new AllSongsPage(driver);
        // Login
        loginPage.provideEmail("akansha.shukla@testpro.io");
        loginPage.providePassword("te$t$tudent");
        loginPage.clickSubmit();
        homePage.chooseAllSongsList();
        songsPage.contextClickFirstSong();
        songsPage.choosePlayOption();
        //Assertion
        Assert.assertTrue(songsPage.isSongPlaying());
    }

/*    @Test
    public void hoverOverPlayButton() throws InterruptedException {
        //Login
        provideEmail("akansha.shukla@testpro.io");
 public void hoverOverPlayButton() throws InterruptedException {
        //Thread.sleep(2000);
        //Assertion
        Assert.assertTrue(hoverPlay().isDisplayed());
    }*/

    @Test
    public void hoverOverPlayBtn() throws InterruptedException {
        //Login
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);

        loginPage.provideEmail("akansha.shukla@testpro.io");
        loginPage.providePassword("te$t$tudent");
        loginPage.clickSubmit();
        Thread.sleep(2000);
        //Assertion
        Assert.assertTrue(homePage.hoverPlay().isDisplayed());
    }

    @Test
    public void choosePlaylistByName(String playlistName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'" + playlistName + "')]"))).click();
    }

    public int countSongs() {
        return driver.findElements(By.cssSelector("section#playlistWrapper td.title")).size();
    }

    public String getPlaylistDetails() {
        return driver.findElement(By.cssSelector("span.meta.text-secondary span.meta")).getText();
    }

    public void displayAllSongs() {
        List<WebElement> songList = driver.findElements(By.cssSelector("section#playlistWrapper td.title"));
        System.out.println("Number of Soungs found: " + countSongs());
        for (WebElement e : songList) {
            System.out.println(e.getText());
        }
    }

    public WebElement hoverPlay() throws InterruptedException {
        //WebElement play = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='play-btn']")));
        WebElement play = driver.findElement(By.cssSelector("[data-testid='play-btn']"));
        actions.moveToElement(play).perform();
        return wait.until(ExpectedConditions.visibilityOf(play));
    }


    public void chooseAllSongsList() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li a.songs"))).click();
    }

    public void contextClickFirstSong() {
        WebElement firstSongElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".all-songs tr.song-item:nth-child(1)")));
        actions.contextClick(firstSongElement).perform();
    }

    public void choosePlayOption() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li.playback"))).click();
    }

    public boolean isSongPlaying() {
        WebElement soundBarVisualizer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid= 'sound-bar-play']")));
        return soundBarVisualizer.isDisplayed();

    }
}