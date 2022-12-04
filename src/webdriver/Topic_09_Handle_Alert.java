package webdriver;

import java.io.IOException;
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
		String hexaColor = Color
				.fromString(rgbaColor).asHex().toUpperCase();
		System.out.println("Hexa color = " + hexaColor);
		// Verify background color
		Assert.assertEquals(hexaColor, "#C92127");
	}
	
	@Test
	public void TC_02_Confirm_Alert() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
		
		alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "I am a JS Confirm");
		alert.dismiss();
		Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(), "You clicked: Cancel");
	}
	
//	@Test
	public void TC_03_Prompt_Alert() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
		
		alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "I am a JS prompt");
		String promptMessage = "Welcome to Prompt Alert";
		alert.sendKeys(promptMessage);
		alert.accept();
//		Assert.assertEquals(driver.findElement(By.cssSelector("p#result")), "You entered: "+ promptMessage);
		Assert.assertTrue(driver.findElement(By.cssSelector("p#result")).getText().contains(promptMessage), promptMessage);
		
		
	}
//	@Test
	public void TC_04_Authentication_Alert() {
//		http://the-internet.herokuapp.com/basic_auth
		String url = authenticationUrl("admin", "admin", "http://the-internet.herokuapp.com/basic_auth");
		driver.get(url);
		sleepInSecond(3);
		Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());
	}
//	@Test
	public void TC_05_Authentication_Alert_Navigate_From_Other_Page() {
//		http://the-internet.herokuapp.com/basic_auth
//		String originUrl = "http://the-internet.herokuapp.com/";
		driver.get("http://the-internet.herokuapp.com/");
		String newUrl = driver.findElement(By.xpath("//a[text()='Basic Auth']")).getAttribute("href");
		System.out.println(newUrl);
		String url = authenticationUrl("admin", "admin", newUrl);
		driver.get(url);
		sleepInSecond(3);
		Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());
	}
	@Test
	public void TC_06_Authentication_Alert_AutoIT() throws IOException {
//		http://the-internet.herokuapp.com/basic_auth
		String userName = "admin";
		String passWord = "admin";
		Runtime.getRuntime().exec(new String[] {autoITFireFox,userName,passWord}); 	
		
		driver.get("http://the-internet.herokuapp.com/basic_auth");
		sleepInSecond(20);
		Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());
	}
	public String authenticationUrl (String userName, String passWord, String url) {
		String[] split = url.split("//");
//		format: https:/admin:password@url
		String authenticationUrl = split[0] + userName + ":" + passWord + "@" + split[1];
		return authenticationUrl;
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
