package webdriver;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_05_Web_Browser {
	// Khai báo
	WebDriver driver;
	WebElement element;
	
	// Khai báo + Khởi tạo
	String projectPath = System.getProperty("user.dir"); 		// lấy 1 giá trị lấy ngược lại gán cho projectPath 
	String osName = System.getProperty("os.name");
	
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe"); //trỏ đến file geckodriver.exe
		// Khởi tạo (new)
		driver = new FirefoxDriver(); //library
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://www.facebook.com/");
		
		// Edit
	}

	@Test
	public void TC_01_Browser() {
		// Các hàm tương tác vs Browser sẽ thông qua biến driver
		// void thì k cần trả về, khác void thì phải trả về giá trị cần phải gắn
		// Lưu ý khi khai báo biến: nếu chỉ có 1 chữ thì dùng chữ thường, còn 2 chữ trở lên thì chữ thứ 2 viết hoa chữ cái đầu (Camel case của Selenium)
		// ** Đóng tab/ window đang active
		driver.close(); 
		// ** Đóng browser
		driver.quit(); 				
		
		// ** Tìm ra 1 element (single)
		WebElement loginButton = driver.findElement(By.cssSelector("")); 		// Trả về WebElement cần phải gán biến
		// ** Tìm ra nhiều element (multiple)
		List<WebElement> links = driver.findElements(By.cssSelector(""));
		
		// ** Mở ra cái URL truyền vào
		driver.get("https://www.facebook.com/");
		// Trả về 1 URL tại page đang đứng	
		String gamePageUrl = driver.getCurrentUrl();			// Chạy getCurrentUrl lấy dữ liệu ra rồi gán lại biến gamePageUrl, biến này có dữ liệu và dùng để verify dữ liệu
	
		// Source code của Page hiện tại
		String gamePageSourceCode = driver.getPageSource();
		
		// ** Lấy ra cái ID của tab/window đang đứng/ active
		String gameWindowHandle = driver.getWindowHandle();			// Trả về 1
		Set<String> gameWindowHandles = driver.getWindowHandles();	// Trả về tất cả các window
		
		driver.manage().getCookies();								// ** Cookies (Framework)
		driver.manage().logs().getAvailableLogTypes();				// Logs (Framework)	
		
		driver.manage().window().fullscreen();
		driver.manage().window().maximize();						// ** Cần mở max để chạy TS không bị Fail, sẽ Fail nếu màn hình nhỏ
		
		// Test GUI (Graphic User Interface): font/size/position/location/color...
		// Ưu tiên auto chức năng trc (Functional UI) -> giao diện sau (GUI)
		
		// ** chờ cho element được tìm thấy trong khoảng time xx giây (WebDriverWait)
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		// Chờ page load thành công sau xx giây
		driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
		// Chờ cho script được inject thành công vào browser/element sau xx giây (JavascriptExecutor lesson)
		driver.manage().timeouts().setScriptTimeout(15, TimeUnit.SECONDS);
		
		driver.navigate().back();
		driver.navigate().forward();
		driver.navigate().refresh();
		driver.navigate().to("https://www.facebook.com/");
		
		// ** 3 loại thực tế: Alert/ Frame (IFrame)/ Window (Tab)
		driver.switchTo().alert();
		driver.switchTo().frame(0);
		driver.switchTo().window("");
	}
	
	@Test
	public void TC_02_Element() {
		// Các hàm tương tác vs Element sẽ thông qua các element
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
