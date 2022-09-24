package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_10_User_Interaction_PI {
	WebDriver driver;
	// Khai báo
	Actions action;
	WebDriverWait expliciWait;
	// Dung cho ham scroll
	JavascriptExecutor jsExecutor;
	
	// Khai báo + Khởi tạo: lấy dữ liệu r gán vào
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		// Khởi tạo
		driver = new FirefoxDriver();
		action = new Actions(driver);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	@Test
	public void TC_01_Hover() {
		driver.get("https://automationfc.github.io/jquery-tooltip/");
		WebElement ageTextbox = driver.findElement(By.cssSelector("input#age"));
		action.moveToElement(ageTextbox).perform();
		sleepInSecond(5);
		
		Assert.assertEquals(driver.findElement(By.cssSelector("div.ui-tooltip-content")).getText(), "We ask for your age only for statistical purposes.");	
	}
	
	public void TC_02_Hover() {
		driver.get("https://www.myntra.com/");
		WebElement kidLink = driver.findElement(By.xpath("//header[@id='desktop-header-cnt']//a[text()='Kids']"));
		action.moveToElement(kidLink).perform();
		sleepInSecond(3);
		
		action.click(driver.findElement(By.xpath("//header[@id='desktop-header-cnt']//a[text()='Home & Bath']"))).perform();
		sleepInSecond(3);
		Assert.assertTrue(driver.findElement(By.xpath("//h1[text()='Kids Home Bath']")).isDisplayed());
	}
	
	public void TC_03_Hover() {
		driver.get("https://fptshop.com.vn/");
		action.moveToElement(driver.findElement(By.xpath("//a[text()='ĐIỆN THOẠI']"))).perform();
		sleepInSecond(3);
		driver.findElement(By.xpath("//a[text()='Apple (iPhone)']")).click();
		sleepInSecond(3);
		Assert.assertEquals(driver.getCurrentUrl(), "https://fptshop.com.vn/dien-thoai/apple-iphone");
	}
	
	public void checkToCheckBoxOrRadio(String xpathLocator) {
		/* Kiểm tra trước nó đã chọn hay chưa
		 * Nếu chọn rồi thì k cần click nữa
		 * Nếu chưa chọn thì click vào -> Được chọn 
		 * Nhận đk đúng thì mới vào If
		 */
		if (driver.findElement(By.xpath(xpathLocator)).isSelected()) {
			driver.findElement(By.xpath(xpathLocator)).click();
		}
	}
	
	public void uncheckToCheckBoxOrRadio(String xpathLocator) {
		if (driver.findElement(By.xpath(xpathLocator)).isSelected()) {
			driver.findElement(By.xpath(xpathLocator)).click();
		}
	}
	
	public boolean isElementSelected(String xpathLocator) {
		return driver.findElement(By.xpath(xpathLocator)).isSelected();
	}
	
	public void checkToCheckBoxOrRadio(WebElement element) {
		if (!element.isSelected() && element.isEnabled()) {
			System.out.println("Click to element: " + element);
			element.click();
			Assert.assertTrue(isElementSelected(element));
		}
	}

	public boolean isElementSelected(WebElement element) {
		return element.isSelected();
	}
	
	public void uncheckToCheckBoxOrRadio(WebElement element) {
		if (element.isSelected() && element.isEnabled()) {
			System.out.println("Click to element: " + element);
			element.click();
			Assert.assertFalse(isElementSelected(element));
		}
	}
	
	@AfterClass
	public void afterClass() {
	driver.quit();
	}
	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000); // Hàm java
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
