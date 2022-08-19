package webdriver;

import java.util.List;
import java.util.Random;
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
public class Topic_08_Custom_Dropdown {
	WebDriver driver;
	// Khai báo
	WebDriverWait expliciWait;
	Random rand;
	// Khai báo + Khởi tạo: lấy dữ liệu r gán vào
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");


	@BeforeClass
	public void beforeClass() {
	System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe"); 
	// Khởi tạo
	driver = new FirefoxDriver(); 
	// Khởi tạo wait
	expliciWait = new WebDriverWait(driver, 30);
	
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Test
	public void TC_06_Customer_Dropdown() {
		driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");
		// 1. Click vào 1 phần tử nào đó thuộc dropdown để cho nó xổ ra
		driver.findElement(By.cssSelector("span#number-button")).click();
		// 2. Chờ tất cả các item trong dropdown được load ra xong
		/* Lưu ý: Ko dùng sleep cứng được, dữ liệu nhiều load lâu hơn, ít thì chưa load ra hết
		 * Phải dùng hàm wait nào để nó linh động (Cần khai báo + khởi tạo "new" nếu k sẽ bị null -> Fail)  
		 * nếu như chưa tìm thấy sẽ tìm lại trong khoảng time đc set.
		 * nếu như tìm thấy rồi thì ko cần phải chờ hết khoảng time này */
		// Bắt được 1 locator đại diện cho all items
		expliciWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("ul#number-menu div")));
		// 3.1. Nếu như item cần chọn đang hiển thị
		// 3.2. Nếu item cần chọn không hiển thị thì scroll down
		// 4. Kiểm tra text của item - nếu đúng với cái mình cần thì click vào
		/* Cần phải viết code để duyệt qua từng item và kiểm tra theo điều kiện
		 * Cần lưu trữ all items lại thì ms duyệt đc*/
		List<WebElement> allItems = driver.findElements(By.cssSelector("ul#number-menu div"));
		 /* Duyệt qua từng item - lấy ra text và kiểm tra nếu bằng text mình muốn thì click vào
		  * Vòng lặp foreach: dùng 1 kiểu dữ liệu để duyệt qua */
		for (WebElement item : allItems) {
			// Dùng biến item để thao tác trong vòng lặp for
			// lấy ra text
			String textItem = item.getText();
			// kiểm tra nếu bằng text mình muốn thì click vào
			if (textItem.equals("7")) {
			/* If: Nó sẽ nhận vào 1 đk là boolean (true/false)
			 * Nếu đk đúng thì sẽ vào hàm If
			 * Nếu k đúng thì bỏ qua*/
				item.click();
			}
	/* Selec: K dùng cho bất kì 1 dropdown cụ thể nào
	 * Dùng cho các dropdown chung của 1 dự án
	 */
		}
		
		
		
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);		// Hàm java
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
