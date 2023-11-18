import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.time.Duration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Reporter;
import org.testng.annotations.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCell;
public class BaseTest {
    @DataProvider(name="LoginData")
public Object[][] getDataFromDataProvider(){
    return new Object[][]{
            {"akansha.shukla@testpro.io", "te$t$tudent"},
            {"invalidemail@class.com", "te$t$tudent"},
            {"demo@class.com", "InvalidPassword"},
            {"",""}
    };
}

    @DataProvider(name="excel-data")
    public Object[][] excelDP() throws IOException {
        Object [][] arrObj;
        //Object[][] arrObj = getExcelData("./src/test/resources/test.xlsx", "test.xlsx");
        arrObj = getExcelData("./src/test/resources/test.xlsx", "Sheet1");
        return arrObj;
    }

    // DataProviders End here

    //References start here


    public WebDriver driver;
    public String url = "https://qa.koel.app";

    public WebDriverWait wait;
    Actions actions;
    @BeforeSuite
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    @Parameters({"BaseURL"})
    public void launchBrowser(String BaseURL){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        actions = new Actions(driver);
        driver.manage().window().maximize();
        navigateToLoginPage(BaseURL);
    }
    @AfterMethod
    public void closeBrowser(){
        driver.quit();
    }


    public void navigateToLoginPage(){
        driver.get(url);
    }

    public void navigateToLoginPage(String BaseURL){
        driver.get(BaseURL);
    }

    public void provideEmail(String email){
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='email']")));
        //WebElement emailField = driver.findElement(By.cssSelector("input[type='email']"));
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void providePassword(String password){
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='password']")));
       // WebElement passwordField = driver.findElement(By.cssSelector("input[type='password']"));
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickSubmit(){
       // WebElement submit = driver.findElement(By.cssSelector("button[type='submit']"));
        WebElement submit = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type='submit']")));
        submit.click();
    }
    public String [][] getExcelData(String fileName, String sheetName){
        String [][] data = null;
        try{
            FileInputStream fileInputStream = new FileInputStream(fileName);
            XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = wb.getSheet(sheetName);
            XSSFRow row = sheet.getRow(0);
            int numOfRows = sheet.getPhysicalNumberOfRows();
            int numOfColumns = row.getLastCellNum();
            XSSFCell cell;
            data = new String[numOfRows-1][numOfColumns];
            for(int i = 1; i < numOfRows; i++){
                for (int j = 0; j < numOfColumns; j++){
                    row = sheet.getRow(i);
                    cell = row.getCell(j);
                    data [i-1][j] = cell.getStringCellValue();
                }
            }
        } catch(Exception e){
            System.out.println("Something went wrong." +e);
        }
        return data;
    }

}