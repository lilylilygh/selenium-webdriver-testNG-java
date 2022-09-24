package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_09_Handle_Alert {
	WebDriver driver;
	// Khai báo
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
		
		driver.manage().window().maximize();
		
		jsExecutor = (JavascriptExecutor) driver;

		// Khởi tạo wait
		expliciWait = new WebDriverWait(driver, 30);

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void TC_05_Accept_Alert() {
		driver.get("https://www.fahasa.com/customer/account/create");
		sleepInSecond(4);
		 /* Close Popup
		 * driver.findElement(By.cssSelector("a#NC_CLOSE_ICON>img")).click(); 
		 * sleepInSecond(2);
		 * Switch qua Iframe trc khi close popup
		 * driver.switchTo().frame/iframe("iframe#preview-notification-frame");
		 * Quay về trang trước đó chứa iframe:
		 * driver.switchTo().defaultContent();
		 */
		
		// chuyển qua Tab Login
		driver.findElement(By.cssSelector("li.popup-login-tab-login")).click();
		// Verify "Đăng nhập" button is disabled
		Assert.assertFalse(driver.findElement(By.cssSelector("button.fhs-btn-login")).isEnabled());
		// Enter value to Email/password textbox
		driver.findElement(By.cssSelector("input#login_username")).sendKeys("linh@gmail.com");
		driver.findElement(By.cssSelector("input#login_password")).sendKeys("123456");
		// Verify "Đăng nhập" button is enabled
		Assert.assertTrue(driver.findElement(By.cssSelector("button.fhs-btn-login")).isEnabled());
		// Verify "Đăng nhập" button - background color (Red)
		String rgbaColor = driver.findElement(By.cssSelector("button.fhs-btn-login")).getCssValue("background-color");
		System.out.println("RGB Color = " + rgbaColor);
		// Convert rgbaColor to Hexa color
		String hexaColor = Color.fromString(rgbaColor).asHex().toUpperCase();
		System.out.println("Hexa color = " + hexaColor);
		// Verify background color
		Assert.assertEquals(hexaColor, "#C92127");
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
