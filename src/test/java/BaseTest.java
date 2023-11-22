import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.ss.formula.atp.Switch;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
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
import java.util.Map;

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


    public static WebDriver driver = null;
    public String url = "https://qa.koel.app";

    public WebDriverWait wait = null;
    public static Actions actions = null;
    private static final ThreadLocal <WebDriver> threadDriver = new ThreadLocal<>();
    @BeforeSuite
    static void setupClass() {
       // WebDriverManager.chromedriver().setup();
         //WebDriverManager.firefoxdriver().setup();

    }
    public void setupBrowser(String BaseURL) throws MalformedURLException {
        threadDriver.set(pickBrowser(System.getProperty("browser")));
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        navigateToLoginPage(BaseURL);
    }

    public static WebDriver getDriver(){
        return threadDriver.get();
    }

@BeforeMethod
    @Parameters({"BaseURL"})
   // public void launchBrowser(String BaseURL) throws MalformedURLException {
       // ChromeOptions options = new ChromeOptions();
       // options.addArguments("--remote-allow-origins=*");
        //driver = new ChromeDriver(options);
public void launchBrowser(String BaseURL) throws MalformedURLException {
    driver = pickBrowser(System.getProperty("browser"));
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    actions = new Actions(driver);
    driver.manage().window().maximize();
    navigateToLoginPage(BaseURL);

    }
    public static WebDriver pickBrowser(String browser) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        String gridURL = "http://192.168.68.84:4444";

        switch (browser) {

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                return driver = new FirefoxDriver();
            case "MicrosoftEdge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
              edgeOptions.addArguments("--remote-allow-origins=*");
                return driver = new EdgeDriver(edgeOptions);
            //Selenium Grid
            case "grid-edge": // gradle clean test -Dbrowser=grid-edge
                caps.setCapability("browserName", "MicrosoftEdge");
                return driver = new RemoteWebDriver(URI.create(gridURL).toURL(),caps);

            case "grid-firefox": // gradle clean test -Dbrowser=grid-firefox
                caps.setCapability("browserName", "firefox");
                return driver = new RemoteWebDriver(URI.create(gridURL).toURL(), caps);


            case "grid-chrome": // gradle clean test -Dbrowser=grid-chrome
                caps.setCapability("browserName", "chrome");
                return driver = new RemoteWebDriver(URI.create(gridURL).toURL(), caps);

                case "cloud":
                return lambdaTest();

            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*");
                driver = new ChromeDriver(options);
                return driver = new ChromeDriver(options);

        }

    }
    public static WebDriver lambdaTest() throws MalformedURLException {
        String username = "akansha.shukla";
        String authKey = "roMXHrpovMm6c7MLjmPURZva19HMQvbV0VvL29427AyNr6LN1D";
        String hub = "@hub.lambdatest.com/wd/hub";

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platform", "Windows 11");
        caps.setCapability("browserName", "Chrome");
        caps.setCapability("version", "119.0");
        caps.setCapability("resolution", "1024x768");
        caps.setCapability("build", "TestNG with Java");
        caps.setCapability("name", BaseTest.class.getName());
        caps.setCapability("plugin", "java-testNG");


        return new RemoteWebDriver(new URL("https://" +username+ ":" +authKey + hub), caps);
    }

    @AfterMethod
    public void closeBrowser(){
        driver.quit();
        }
    public void tearDown(){
        threadDriver.get().close();
        threadDriver.remove();
    }


        public void navigateToLoginPage () {
            driver.get(url);
        }

        public void navigateToLoginPage (String BaseURL){
            driver.get(BaseURL);
        }

        public void provideEmail (String email){
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='email']")));
            //WebElement emailField = driver.findElement(By.cssSelector("input[type='email']"));
            emailField.clear();
            emailField.sendKeys(email);
        }

        public void providePassword (String password){
            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='password']")));
            // WebElement passwordField = driver.findElement(By.cssSelector("input[type='password']"));
            passwordField.clear();
            passwordField.sendKeys(password);
        }

        public void clickSubmit () {
            // WebElement submit = driver.findElement(By.cssSelector("button[type='submit']"));
            WebElement submit = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type='submit']")));
            submit.click();
        }
        public String[][] getExcelData (String fileName, String sheetName){
            String[][] data = null;
            try {
                FileInputStream fileInputStream = new FileInputStream(fileName);
                XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);
                XSSFSheet sheet = wb.getSheet(sheetName);
                XSSFRow row = sheet.getRow(0);
                int numOfRows = sheet.getPhysicalNumberOfRows();
                int numOfColumns = row.getLastCellNum();
                XSSFCell cell;
                data = new String[numOfRows - 1][numOfColumns];
                for (int i = 1; i < numOfRows; i++) {
                    for (int j = 0; j < numOfColumns; j++) {
                        row = sheet.getRow(i);
                        cell = row.getCell(j);
                        data[i - 1][j] = cell.getStringCellValue();
                    }
                }
            } catch (Exception e) {
                System.out.println("Something went wrong." + e);
            }
            return data;
        }

    }