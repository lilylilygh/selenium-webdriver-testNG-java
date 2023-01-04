package webdriver;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
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

public class Topic_15_Upload_AutoIT {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String firefoxSinglePath = projectPath + "\\autoIT\\firefoxUploadOneTime.exe";
	String firefoxMultiplePath = projectPath + "\\autoIT\\firefoxUploadMultiple.exe";
	
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
	
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe"); // trỏ đến file
																											// geckodriver.exe
		driver = new FirefoxDriver(); // library
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	// Upload 1 lần 1 file
	public void TC_01_Upload_Single() throws IOException {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		driver.findElement(By.cssSelector("span.btn-success")).click();
		sleepInsecond(3);

		// Upload File bằng AutoIT
		Runtime.getRuntime().exec(new String[] { firefoxSinglePath, image1FilePath });
		sleepInsecond(3);

		// Verify image load lên thành công
		Assert.assertTrue(driver.findElement(By.xpath("//p[text()='MDC_1612.JPG']")).isDisplayed());
		sleepInsecond(3);

		// Thực hiện upload
		List<WebElement> buttonStarts = driver.findElements(By.cssSelector("table button.start"));
		for (WebElement button : buttonStarts) {
			button.click();
			sleepInsecond(2);
		}

		// Verify image upload success
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='MDC_1612.JPG']")).isDisplayed());
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

	@Test
	public void TC_03_Upload_Single_JavaRobot() throws AWTException {
		driver.get("https://blueimp.github.io/jQuery-File-Upload/");
		
		
		/* Load file = Robot: Giả lập hành vi copy path của 1 file -> Java support
		 * Giả lập hành vi paste và Enter vào Open File Dialog 
		 */
		
		// Copy File Path
		
		driver.findElement(By.cssSelector("span.btn-success")).click();
		sleepInsecond(3);
		
		// Gọi hàm
		loadFileByRobot(image1FilePath);
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public void loadFileByRobot(String filePath) throws AWTException {
		StringSelection select = new StringSelection(filePath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(select, null);
		// Load File
		Robot robot = new Robot();
		sleepInsecond(1);
		
		// Nhan xuong Ctrl+V
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		
		// Nha Ctrl+V
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);
		sleepInsecond(1);
		
		// Nhan Enter
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		sleepInsecond(1);
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