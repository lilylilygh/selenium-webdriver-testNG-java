package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.ClickAction;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_11_Fixed_Popup {
	WebDriver driver;

	// Khai báo + Khởi tạo: lấy dữ liệu r gán vào
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	JavascriptExecutor jsExecutor;
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		// Khởi tạo
		driver = new FirefoxDriver();
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	@Test
	public void TC_01_FixedInDom_Ngoaingu24h() {
		driver.get("https://ngoaingu24h.vn/");
		WebElement loginPopup = driver.findElement(By.xpath("//div[@id='modal-login-v1'][1]"));
		// Verify Login popup is undisplayed
		Assert.assertFalse(loginPopup.isDisplayed());
		driver.findElement(By.cssSelector("button.login_")).click();
		sleepInSecond(3);
		// Verify Login popup is displayed
		Assert.assertTrue(loginPopup.isDisplayed());
		
		driver.findElement(By.xpath("//div[@id='modal-login-v1'][1]//input[@id='account-input']")).sendKeys("automationfc");
		driver.findElement(By.xpath("//div[@id='modal-login-v1'][1]//input[@id='password-input']")).sendKeys("automationfc");
		driver.findElement(By.xpath("//div[@id='modal-login-v1'][1]//button[contains(@class,'btn-login-v1')]")).click();
		sleepInSecond(3);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@id='modal-login-v1'][1]//div[@class='row error-login-panel']")).getText(), "Tài khoản không tồn tại!");
	
		// Click to close popup
		driver.findElement(By.xpath("//div[@id='modal-login-v1'][1]//button[@class='close']")).click();
		sleepInSecond(3);
		// Verify Login popup is undisplayed
		Assert.assertFalse(loginPopup.isDisplayed());
	}

	@Test
	public void TC_03_FixedNotInDom_Tiki() {
		driver.get("https://tiki.vn/");
		/* Khi mới mở trang ra thì popup k hề có trong DOM nên sẽ k findElement được
		* findElement should not be used to look for non-present elements
		* 15s Sau nó sẽ FAIL vì implicitlyWait(15, TimeUnit.SECONDS)
		* Show ra lỗi NoSuchElementException sau 1 khoảng time là xx giây (impliciWait)
		* Trong TH popup k có trong DOM -> findElements sẽ tìm thấy 0 element
		* Và cũng chờ hết timeout của impliciWait, k đánh Fail TC và k show Exception -> trả về EmptyList
		* Empty List = 0 element
		*/
		List<WebElement> loginPopup = driver.findElements(By.cssSelector("div.ReactModal__Content"));
		
		// Verify Login popup is undisplayed
		Assert.assertEquals(loginPopup.size(), 0);
		
		// Click vào Đăng nhập để show popup lên
		driver.findElement(By.xpath("//span[text()='Đăng Nhập / Đăng Ký']")).click();
		sleepInSecond(3);
		// Displayed (Single Element: findElement), Popup hiển thị -> Có thể dùng findElement
		Assert.assertTrue(driver.findElement(By.cssSelector("div.ReactModal__Content")).isDisplayed());
		// Displayed (Multiple Element: findElements)
		loginPopup = driver.findElements(By.cssSelector("div.ReactModal__Content"));
		Assert.assertEquals(loginPopup.size(), 1);
		Assert.assertTrue(loginPopup.get(0).isDisplayed());
		// Click để đóng popup
		driver.findElement(By.cssSelector("img.close-img")).click();
		sleepInSecond(3);
		// Undisplayed:
		loginPopup = driver.findElements(By.cssSelector("div.ReactModal__Content"));
		Assert.assertEquals(loginPopup.size(), 0);
	}
	
	@Test
	public void TC_05_Random_Popup_In_DOM_Kmplayer() {
		driver.get("https://www.kmplayer.com/home");
		
		WebElement popupLayer = driver.findElement(By.cssSelector("div.pop-layer"));
		/* Phải luôn chạy được TC dù popup có hiển thị hay k
		 * 1 - Đang trong đợt sale nó hiển thị thì script mình phải đóng nó đi để chạy tiếp
		 * 2 - Hết đợt sale k hiển thị thì script chạy luôn  
		 * Muốn test thử thì cố tình tắt popup manual đi trc khi gọi tới hàm check displayed
		 * (set SleepInSecond 8s -> tắt popup manual -> sẽ k vào hàm if nữa, vì k hiển thị nên sẽ nhảy qua step tiếp theo)
		 */
		if (popupLayer.isDisplayed()){
			// Close nó đi để chạy tiếp cái step tiếp theo
			System.out.println("------Step để close popup------");
			jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.cssSelector("area#btn-r")));
			sleepInSecond(3);	
		} 
		
		System.out.println("------Step tiếp theo------");
		// Step tiếp theo
		driver.findElement(By.cssSelector("p.donate-coffee")).click();
		sleepInSecond(3);
		Assert.assertEquals(driver.getCurrentUrl(), "https://www.buymeacoffee.com/kmplayer?ref=hp&kind=top");
	}
	
	@Test
	public void TC_06_Random_Popup_Not_In_DOM_Dehieupage() {
		// Đây là site đang chạy live cho user dùng - k nên spam, chạy script đăng kí
		// Độ phân giải màn hình thấp: 1366x768
		driver.manage().window().setSize(new Dimension(1366, 768));
		driver.get("https://dehieu.vn/");
		sleepInSecond(10);
		/* TC cần check
		 * 1 - Đang trong đợt sale nó hiển thị thì script mình phải đóng nó đi để chạy tiếp
		 * 2 - Hết đợt sale k hiển thị thì script chạy luôn 
		 */
		List<WebElement> contentPopup = driver.findElements(By.cssSelector("div.popup-content"));
		// Nếu element > 0 (từ 1 trở lên) thì có xuất hiện và contentpopup lấy ra element đầu tiên (get0) là hiển thị
		// Khi nào hiển thị thì ms send keys
		// Dùng List Element thì display đc, còn Element số ít thì k đc
		if (contentPopup.size() > 0 && contentPopup.get(0).isDisplayed()) {
			driver.findElement(By.id("popup-name")).sendKeys("John Wick");
			driver.findElement(By.id("popup-email")).sendKeys("Johnwick@gmail.com");
			driver.findElement(By.id("popup-phone")).sendKeys("0986564617");
			sleepInSecond(4);
			// Close popup đi
			jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.cssSelector("button#close-popup")));
			sleepInSecond(4);			
		}
	// Step tiếp theo
		driver.findElement(By.xpath("//a[text()='Tất cả khóa học']")).click();
		sleepInSecond(3);
		Assert.assertEquals(driver.getCurrentUrl(), "https://dehieu.vn/khoa-hoc");
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
