package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Facebook_demo {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	WebDriverWait expliciWait;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe"); //trỏ đến file geckodriver.exe
		driver = new FirefoxDriver(); //library
		expliciWait = new WebDriverWait(driver, 10);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test
	public void TC_01_Visible_Displayed_Visibility() {
		driver.get("https://www.facebook.com/");
		
		/* 1. Có trên UI (Bắt buộc)
		 * 1. Có trong HTML (Bắt buộc)
		 */
		/* Wait cho email address textbox hiển thị
		 * Chờ cho email textbox hiển thị trong vòng 10s
		 */
		expliciWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));	
	}

	@Test
	public void TC_02_Invisible_Undisplayed_Invisibility_I() {
		
		/* 2. K có trên UI (bắt buộc)
		 * 1. Có trong HTML
		 */
		driver.get("https://www.facebook.com/");
		driver.findElement(By.xpath("//a[@data-testid='open-registration-form-button']")).click();
		// Chờ cho Re-enter email textbox k hiển thị trong vòng 10s
		expliciWait.until(ExpectedConditions.invisibilityOfElementLocated(By.name("reg_email_confirmation__")));
	}

	@Test
	public void TC_02_Invisible_Undisplayed_Invisibility_II() {
		/* 2. K có trên UI (bắt buộc)
		 * 2. K Có trong HTML
		 */
		driver.get("https://www.facebook.com/");
		expliciWait.until(ExpectedConditions.invisibilityOfElementLocated(By.name("reg_email_confirmation__")));
	}

	@Test
	public void TC_03_Presence_I() {
		/* 1. Có trên UI 
		 * 1. Có trong HTML (bắt buộc)
		 */
		driver.get("https://www.facebook.com/");
		 // Chờ cho email textbox presence trong HTML trong vòng 10s
		expliciWait.until(ExpectedConditions.presenceOfElementLocated(By.id("email")));
	}
	
	@Test
	public void TC_03_Presence_II() {
		/* 2. K có trên UI 
		 * 1. Có trong HTML (bắt buộc)
		 */
		driver.get("https://www.facebook.com/");
		driver.findElement(By.xpath("//a[@data-testid='open-registration-form-button']")).click();
		// Chờ cho Re-enter email textbox k hiển thị trong vòng 10s
		expliciWait.until(ExpectedConditions.presenceOfElementLocated(By.name("reg_email_confirmation__")));
	}
	
	@Test
	public void TC_04_Staleness() {
		/* 2. K có trên UI (bắt buộc)
		 * 2. K Có trong HTML 
		 */
		driver.get("https://www.facebook.com/");
		driver.findElement(By.xpath("//a[@data-testid='open-registration-form-button']")).click();

		// Phase 1: Element có trong cây HTML
		WebElement reEnterEmailAddressTextbox = driver.findElement(By.name("reg_email_confirmation__"));

		// Thao tác với element khác làm cho element re-enter k còn trong DOM nữa
		// Close popup
		driver.findElement(By.cssSelector("img._8idr")).click();
		// Chờ cho re-Enter email textbox k còn trong DOM trong vòng 10s
		expliciWait.until(ExpectedConditions.stalenessOf(reEnterEmailAddressTextbox));
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