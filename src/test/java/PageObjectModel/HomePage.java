package PageObjectModel;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage{
    public HomePage(WebDriver givenDriver) {
        super(givenDriver);
    }
    By firstPlaylist = By.cssSelector(".playlist:nth-child(3)");
    By playlistNameField = By.cssSelector("[name='name']");
    By renamePlaylistSuccessMsg =  By.cssSelector("div.success.show");

    public void doubleClickPlayList(){
        doubleClick(firstPlaylist);

    }
    public void enterNewPlaylistName(String playlistName){
        findElement(playlistNameField).sendKeys(Keys.chord(Keys.CONTROL,"A",Keys.BACK_SPACE));
        findElement(playlistNameField).sendKeys("Aaryav");
        findElement(playlistNameField).sendKeys(Keys.ENTER);

    }
    public String getRenamePlaylistSuccessMsg(){
        return findElement(renamePlaylistSuccessMsg).getText();
    }

    By userAvatarIcon = By.cssSelector("img.avatar");

    public WebElement getUserAvatar(){
        return findElement(userAvatarIcon);
    }
}