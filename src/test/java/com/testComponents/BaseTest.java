package com.testComponents;

import org.testng.annotations.AfterMethod;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pageObjects.LandingPage;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	
	public static WebDriver driver;
	public LandingPage landingPage;
	
	
	public WebDriver initializeDriver() throws Exception {
		
		Properties properties = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") +"//src//main//resources//GlobalData.properties");
		properties.load(fis);
		
		
		String browserName = System.getProperty("browser")!=null? System.getProperty("browser"): properties.getProperty("browser");
//				properties.getProperty("browser");
		
		if(browserName.equalsIgnoreCase("chrome")) {
		
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		}
		else if(browserName.equalsIgnoreCase("firefox")) {
			
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}else if(browserName.equalsIgnoreCase("edge")) {
			
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		
		return driver;
	}
	
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws Exception {
		
		String jsonContent = FileUtils.readFileToString(new File(filePath),StandardCharsets.UTF_8);
		ObjectMapper mapper = new ObjectMapper();
		
		//string to hashMap
		List<HashMap<String, String>>	data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
		});
		
		return data;
	}
	
	public String getScreenshot(String testCaseName, WebDriver driver) throws Exception {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File dest = new File(System.getProperty("user.dir")+"//reports//"+ testCaseName+ ".png");
		FileUtils.copyFile(source, dest);
		return System.getProperty("user.dir")+"//reports//"+ testCaseName+ ".png";
	}
	
	@BeforeMethod(alwaysRun = true)
	public LandingPage launchApp() throws Exception {
		driver = initializeDriver();
		 landingPage = new LandingPage(driver);
		landingPage.goTo();
		return landingPage;
	}
	
	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.close();
		driver.quit();
	}

}
