package webdriver;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_15_Upload_SendKey {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe"); //trỏ đến file geckodriver.exe
		driver = new FirefoxDriver(); //library
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();	
		}
	
	// Image name
	String imageName1 = "MDC_1612.JPG";
	String imageName2 = "MDC_1911.JPG";
	String imageName3 = "MDC_1912.JPG";
	// Upload file folder: Dùng separator là tùy vào hệ điều hành sử dụng tự thêm vào sang trái/phải 
	String imageUploadFolder = projectPath + File.separator + "uploadFiles" + File.separator;
	//String image1FilePath, image2FilePath, image3FilePath;
	
	//Image Path
	String image1FilePath = imageUploadFolder + imageName1;
	String image2FilePath = imageUploadFolder + imageName2;
	String image3FilePath = imageUploadFolder + imageName3;

	@Test
	// Upload 1 lần 1 file
	public void TC_01_Upload_Single() {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		sleepInsecond(3);

		By uploadFile = By.cssSelector("input[type='file']");

		// Load File lên (Browser file)
		driver.findElement(uploadFile).sendKeys(image1FilePath);
		driver.findElement(uploadFile).sendKeys(image2FilePath);
		driver.findElement(uploadFile).sendKeys(image3FilePath);
		sleepInsecond(3);

		// Verify image load lên thành công
		Assert.assertTrue(driver.findElement(By.xpath("//p[text()='MDC_1612.JPG']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[text()='MDC_1911.JPG']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[text()='MDC_1912.JPG']")).isDisplayed());
		sleepInsecond(3);

		// Thực hiện upload
		List<WebElement> buttonStarts = driver.findElements(By.cssSelector("table button.start"));
		for (WebElement button : buttonStarts) {
			button.click();
			sleepInsecond(2);
		}
		
		// Verify image upload success
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='MDC_1612.JPG']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='MDC_1911.JPG']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='MDC_1912.JPG']")).isDisplayed());
	}
	
	@Test
	// Upload 1 lần nhiều File
	public void TC_02_Upload_Multiple() {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		sleepInsecond(3);

		By uploadFile = By.cssSelector("input[type='file']");
		driver.findElement(uploadFile).sendKeys(image1FilePath + "\n" + image2FilePath + "\n" + image3FilePath);
		sleepInsecond(3);

		Assert.assertTrue(driver.findElement(By.xpath("//p[text()='MDC_1612.JPG']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[text()='MDC_1911.JPG']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[text()='MDC_1912.JPG']")).isDisplayed());
		sleepInsecond(3);

		List<WebElement> buttonStarts = driver.findElements(By.cssSelector("table button.start"));
		for (WebElement button : buttonStarts) {
			button.click();
			sleepInsecond(2);
		}
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='MDC_1612.JPG']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='MDC_1911.JPG']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='MDC_1912.JPG']")).isDisplayed());		
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public void sleepInsecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int generateRandomNumber() {
		Random rand = new Random();
		return rand.nextInt(9999);
	}
}