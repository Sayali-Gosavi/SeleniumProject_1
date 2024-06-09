package project.qa.swaglabs.factory;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//import static java.lang.ThreadLocal.*;
import static java.lang.InheritableThreadLocal.*;

public class DriverFactory {
	
	public WebDriver driver;
	public static ThreadLocal<WebDriver> tLDriver = new ThreadLocal<WebDriver>();
//	public static InheritableThreadLocal<WebDriver> tLDriver = new InheritableThreadLocal<WebDriver>();
	
	public WebDriver init_driver(String browserName) {
		System.out.println("browser name is :"+browserName);
		if(browserName.equals("chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");
			
			options.addArguments("--window-size=1920,1080");
			options.addArguments("--ignore-certificate-errors");
			options.addArguments("--allow-running-insecure-content");
			options.addArguments("--disable-gpu");
			options.addArguments("--no-sandbox");
			options.addArguments("--ignore-urlfetcher-cert-requests");
			
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\sayal\\OneDrive\\Documents\\workspaces\\eclipse workspace\\Selenium workspace\\SwagLabs_SeleniumPOM\\chromedriver.exe");
			tLDriver.set(new ChromeDriver(options));
			
		}else if(browserName.equals("edge")) {
			driver = new EdgeDriver();
		}else if(browserName.equals("firefox")) {
			driver = new FirefoxDriver();
		}else {
			System.out.println("please pass the right browser :"+browserName);
		}
		getDriver().get("https://www.saucedemo.com/");
		getDriver().manage().window().fullscreen();
		getDriver().manage().deleteAllCookies();
		
		return getDriver();

		
	}
	
	public static synchronized WebDriver getDriver() {
		return tLDriver.get();
	}
	
	public void login(String userLogin) {
		WebDriverWait wait = new WebDriverWait(getDriver(),Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("email xpath")));
		getDriver().findElement(By.xpath("email xpath")).sendKeys(userLogin);
		getDriver().findElement(By.xpath("submit button xpath")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(("brand image xpath"))));
		String title = getDriver().getTitle();
		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		System.out.println("=====Browser Session Start=====");
		
	}
	
	public String getScreenshot(String testName) {
		File srcFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		String path = "C:\\Users\\sayal\\OneDrive\\Documents\\workspaces\\eclipse workspace\\Selenium workspace\\SwagLabs_SeleniumPOM\\Screenshots\\"+testName+".jpeg";
		File destination = new File(path);
		try {
			FileUtils.copyFile(srcFile,destination);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return path;
	}

}
