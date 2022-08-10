package webdriver;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_05_Web_Browser_Element_knowledge {
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
		driver.get("https://www.facebook.com/");
		// Các hàm tương tác vs Element sẽ thông qua cái class WebElement (biến nào đó)
		
		// xóa dữ liệu trong 1 field dạng editable có thể nhập
		// Textbox/ Text area/ Editable Dropdown
		element.clear();
		// Nhập dữ liệu vào field dạng editable
		element.sendKeys("linh@gmail.com");
		element.sendKeys(Keys.ENTER);
		// click vào những element: Button/Radio/Link/Checkbox/...
		element.click();
		
		// Các hàm bdau bằng tiền tố "get" thì 100% trả về dữ liệu (kiểu dữ liệu nào đó)
		// Trả về gtri nằm trong Attribute của Element
		element.getAttribute("placeholder");
	
		driver.findElement(By.id("firstname")).getAttribute("value");
		
		// get css value trả về thuộc tính của element này
		// Font/size/color
		// Trả về màu nền của element
		element.getCssValue("background-color");
		// Trả về font size của element
		element.getCssValue("font-size");
		
		// Test GUI: Point/ Rectangle/ Size (Visualize Testing)
		element.getLocation();
		element.getRect();
		element.getSize();
		
		// Chụp hình và attach vào HTML Report
		element.getScreenshotAs(OutputType.FILE); 
		
		// Trả về thẻ HTML của element (ex: input)
		WebElement emailAddressTextbox = driver.findElement(By.xpath("/input[@id='email']"));
		emailAddressTextbox = driver.findElement(By.cssSelector("input[id='email']"));
		emailAddressTextbox.getTagName();
		// K biết thẻ là gì
		WebElement emailAddressTextbox1 = driver.findElement(By.xpath("//*[@id='email']"));
		emailAddressTextbox1 = driver.findElement(By.cssSelector("#email"));
		
		//** TRả về text của 1 element (Link/Header/Message lỗi/success/...)
		element.getText();
		
		// Trả về True/False của element có hiển thị hoặc k 
		// True: hiển thị/enable/chọn rồi 
		// False: ko hiển thị/Disable/chưa chọn
		element.isDisplayed();
		element.isEnabled();
		// Checkbox/RadioText
		element.isSelected();
		// Dropdown list: có 1 thư viện riêng để xử lí (Select)
		
		// Chỉ làm việc được với form (register/ login/ search/...- nằm trong thẻ form)
		// Submit = ENTER của 1 field nào đó trong form (Submit)
		element.submit();
		
		
		// 2 cách để mình thao tác
		// Khai báo biến và dùng lại (dùng đi dùng lại nhiều lần - ít nhất 2 lần thì ms cần khai báo biến)
		
		// Khai báo biến cùng với kiểu dữ liệu trả về của hàm findElement
		WebElement emailAddressTextbox2 = driver.findElement(By.id("email")); 
		emailAddressTextbox.clear();
		emailAddressTextbox.sendKeys("linh@gmail.com");
		
		// Dùng trực tiếp  (dùng 1 lần, k cần khai báo biến)
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys("linh@gmail.com");
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
