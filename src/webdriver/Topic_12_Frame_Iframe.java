package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_12_Frame_Iframe {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe"); //trỏ đến file geckodriver.exe
		driver = new FirefoxDriver(); //library
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://www.facebook.com/");
	}

	@Test
	public void TC_01_Iframe_Kyna() {
		driver.get("https://skills.kynaenglish.vn/");
		/*iframe Facebook đã bị đổi 
		 * Đây là code cũ nhưng hiện tại k thể dùng được vì đã đổi Iframe FB lỗi
		 * driver.switchTo().frame(driver.findElement(By.cssSelector("div.fanpage iframe")));
		   String facebookLikeNumber = driver.findElement(By.xpath("String facebookLikeNumber = driver.findElement")).getText();
		   Assert.assertEquals(facebookLikeNumber, "166K likes"); 
		 */
		
		/* Hàm Switch To của Webdriver 
		 * Alert
		 * Frame/Iframe
		 * Windows/Tab
		 */
		/* Cách 1. Index: Nếu như thêm/xóa bớt iframe đi thì nó bị update index lại
		driver.switchTo().frame(0);
		Cách 2. ID/Name - frame/Iframe phải có thẻ id/name thì ms chạy được
		driver.switchTo().frame("cs_chat_iframe");
		Cách 3: WebElement - Áp dụng cho tất cả trường hợp (all cases)
		*/
		driver.switchTo().frame(driver.findElement(By.cssSelector("div.fanpage iframe")));
		// K đc phép, k support nhảy từ Iframe B qua Iframe C (2 iframe này đang thuộc 1 page chính A)
	
		// B -> A
		driver.switchTo().defaultContent();
		// A -> C -> Element thuộc C
		driver.switchTo().frame("cs_chat_iframe");
		driver.findElement(By.cssSelector("div.meshim_widget_Widget")).click();
		sleepInSecond(3);
		driver.findElement(By.cssSelector("input.input_name")).sendKeys("John K");
		driver.findElement(By.cssSelector("input.input_phone")).sendKeys("0987666677");
		new Select(driver.findElement(By.cssSelector("select#serviceSelect"))).selectByVisibleText("TƯ VẤN TUYỂN SINH");
		driver.findElement(By.cssSelector("textarea[name='message']")).sendKeys("Register new course");
		sleepInSecond(3);
		// C -> A -> Element thuộc A
		driver.switchTo().defaultContent();
		String keyword = "Excel";
		driver.findElement(By.cssSelector("input#live-search-bar")).sendKeys(keyword);
		driver.findElement(By.cssSelector("button.search-button")).click();
		sleepInSecond(3);
		// Verify
		List<WebElement> courseNames = driver.findElements(By.cssSelector("div.content>h4"));
		// Number
		Assert.assertEquals(courseNames.size(), 9);
		for (WebElement course : courseNames) {
			System.out.println(course.getText());
			// Course name contains keyword
			Assert.assertTrue(course.getText().contains(keyword));
		}
	}

	@Test
	public void TC_02_Frame_HDFC_Bank() {
		driver.get("https://netbanking.hdfcbank.com/netbanking/");
		driver.switchTo().frame("login_page");
		driver.findElement(By.name("fldLoginUserId")).sendKeys("Linhauto");
		driver.findElement(By.cssSelector("a.login-btn")).click();
		sleepInSecond(3);
		
		WebElement passwordTextbox = driver.findElement(By.id("fldPasswordDispId"));
		Assert.assertTrue(passwordTextbox.isDisplayed());
		passwordTextbox.sendKeys("automationfc");
		sleepInSecond(3);
	}

	@Test
	public void TC_03_() {
		
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