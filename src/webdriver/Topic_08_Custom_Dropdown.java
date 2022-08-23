package webdriver;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_08_Custom_Dropdown {
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
	public void TC_06_Customer_Dropdown_JQuery01() {
		driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");
		// 1. Click vào 1 phần tử nào đó thuộc dropdown để cho nó xổ ra
		driver.findElement(By.cssSelector("span#number-button")).click();
		// 2. Chờ tất cả các item trong dropdown được load ra xong
		/*
		 * Lưu ý: Ko dùng sleep cứng được, dữ liệu nhiều load lâu hơn, ít thì chưa load
		 * ra hết Phải dùng hàm wait nào để nó linh động (Cần khai báo + khởi tạo "new"
		 * nếu k sẽ bị null -> Fail) nếu như chưa tìm thấy sẽ tìm lại trong khoảng time
		 * đc set. nếu như tìm thấy rồi thì ko cần phải chờ hết khoảng time này
		 */
		// Bắt được 1 locator đại diện cho all items
		expliciWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("ul#number-menu div")));
		// 3.1. Nếu như item cần chọn đang hiển thị
		// 3.2. Nếu item cần chọn không hiển thị thì scroll down
		// 4. Kiểm tra text của item - nếu đúng với cái mình cần thì click vào
		/*
		 * Cần phải viết code để duyệt qua từng item và kiểm tra theo điều kiện Cần lưu
		 * trữ all items lại thì ms duyệt đc
		 */
		List<WebElement> allItems = driver.findElements(By.cssSelector("ul#number-menu div"));
		/*
		 * Duyệt qua từng item - lấy ra text và kiểm tra nếu bằng text mình muốn thì
		 * click vào Vòng lặp foreach: dùng 1 kiểu dữ liệu để duyệt qua
		 */
		for (WebElement item : allItems) {
			// Dùng biến item để thao tác trong vòng lặp for
			// lấy ra text
			String textItem = item.getText();
			// kiểm tra nếu bằng text mình muốn thì click vào
			if (textItem.equals("7")) {
				/*
				 * If: Nó sẽ nhận vào 1 đk là boolean (true/false) Nếu đk đúng thì sẽ vào hàm If
				 * Nếu k đúng thì bỏ qua
				 */
				item.click();
			}
		}
		// Verify
		Assert.assertEquals(driver.findElement(By.cssSelector("span#number-button>span.ui-selectmenu-text")).getText(), "7");
		
		// Gọi hàm: Có thể gọi 1 hàm khác để dùng trong cùng 1 Class
		selectItemInCustomDropdown("span#number-button", "ul#number-menu div", "5");
		sleepInSecond(3);
		Assert.assertEquals(driver.findElement(By.cssSelector("span#number-button>span.ui-selectmenu-text")).getText(), "5");
	}

	@Test
	public void TC_06_Customer_Dropdown_JQuery02() {
		driver.get("https://www.honda.com.vn/o-to/du-toan-chi-phi");
		
		scrollToElement("div.carousel-item");
		sleepInSecond(3);
		
		selectItemInCustomDropdown("button#selectize-input", "button#selectize-input+div>a", "CITY L");
		sleepInSecond(3);
		Assert.assertEquals(driver.findElement(By.cssSelector("button#selectize-input")).getText(), "CITY L");
		
		scrollToElement("div.carousel-item");
		selectItemInCustomDropdown("button#selectize-input", "button#selectize-input+div>a", "CITY RS (Đỏ)");
		sleepInSecond(3);
		scrollToElement("div.carousel-item");
		selectItemInCustomDropdown("button#selectize-input", "button#selectize-input+div>a", "CR-V E");
		sleepInSecond(3);

		// Default Dropdown
		scrollToElement("div.container");
		sleepInSecond(3);
		
		/* Default dropdown, thư viện Select hỗ trợ nên k thể Verify findElement getText
		 * Cần khai báo 1 biến để dùng lại Select select = và "select.getFirstSelectedOption"
		 */
		Select select = new Select(driver.findElement(By.cssSelector("select#province")));
		select.selectByVisibleText("Đà Nẵng");
		sleepInSecond(3);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), "Đà Nẵng");
		
		select = new Select(driver.findElement(By.cssSelector("select#registration_fee")));
		select.selectByVisibleText("Khu vực II");
		sleepInSecond(3);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), "Khu vực II");
	}

	@Test
	public void TC_06_Customer_Dropdown_ReactJS() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection");
		selectItemInCustomDropdown("div.dropdown", "div.menu span.text", "Jenny Hess");
		sleepInSecond(3);
		Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(), "Jenny Hess");
		
		selectItemInCustomDropdown("div.dropdown", "div.menu span.text", "Stevie Feliciano");
		sleepInSecond(3);
		Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(), "Stevie Feliciano");
		
		selectItemInCustomDropdown("div.dropdown", "div.menu span.text", "Matt");
		sleepInSecond(3);
		Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(), "Matt");
	}

	@Test
	public void TC_06_Customer_Dropdown_VueJS() {
		driver.get("https://mikerodham.github.io/vue-dropdowns");
		selectItemInCustomDropdown("li.dropdown-toggle", "ul.dropdown-menu a", "Second Option");
		sleepInSecond(3);
		Assert.assertEquals(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText(), "Second Option");
		
		driver.get("https://mikerodham.github.io/vue-dropdowns");
		selectItemInCustomDropdown("li.dropdown-toggle", "ul.dropdown-menu a", "First Option");
		sleepInSecond(3);
		Assert.assertEquals(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText(), "First Option");
		
		driver.get("https://mikerodham.github.io/vue-dropdowns");
		selectItemInCustomDropdown("li.dropdown-toggle", "ul.dropdown-menu a", "Third Option");
		sleepInSecond(3);
		Assert.assertEquals(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText(), "Third Option");
	}
		
	@Test
	public void TC_06_Customer_Dropdown_Editable() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection");
		enterItemInCustomDropdown("input.search", "div.menu span.text", "Andorra");
		sleepInSecond(3);
		Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(), "Andorra");
		
		enterItemInCustomDropdown("input.search", "div.menu span.text", "Belgium");
		sleepInSecond(3);
		Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(), "Belgium");
	}
	
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	
	public void scrollToElement(String cssLocator) {
		/* Lấy element "cssLocator"  thay vào tham số "arguments[0]" thực hiện hàm scrollIntoView
		 * Nếu như cái này là True thì scroll lên mép trên, false thì scroll xuống mép dưới
		 */
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.cssSelector(cssLocator)));
	}

	/*
	 * Select: K dùng cho bất kì 1 dropdown cụ thể nào Dùng cho các dropdown chung/
	 * ko cụ thể của 1 dự án
	 */
	public void selectItemInCustomDropdown(String parentLocator, String childLocator, String textExpectedItem) {
		// Click vào 1 thẻ nào đó (parent) để cho nó xổ ra tât cả các item
		driver.findElement(By.cssSelector(parentLocator)).click();
		// Wait cho tất cả các item này có xuất hiện ở trong cây HTML/ DOM
		expliciWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(childLocator)));
		// Tìm lưu lại hết all items vào 1 list để cho step tiếp thao
		List<WebElement> allItems = driver.findElements(By.cssSelector(childLocator));
		// Duyệt qua từng phần tử (element) trong list trên
		for (WebElement item : allItems) {
			// Lấy text ra
			String textActualItem = item.getText();
			// So sánh nếu nó bằng = item mình mong muốn
			if (textActualItem.equals(textExpectedItem)) {
				// Thì click vào
				item.click();
				// Khi đã tìm thấy và thỏa mãn điều kiện rồi thì k cần duyệt tiếp
				break;
			}
		}
	}
	
	// Vừa chọn vừa filter (enter)
	public void enterItemInCustomDropdown(String parentLocator, String childLocator, String textExpectedItem) {
		// Click vào 1 thẻ nào đó (parent) để cho nó xổ ra tât cả các item
		driver.findElement(By.cssSelector(parentLocator)).clear();
		driver.findElement(By.cssSelector(parentLocator)).sendKeys(textExpectedItem);
		sleepInSecond(1);
		// Wait cho tất cả các item này có xuất hiện ở trong cây HTML/ DOM
		expliciWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(childLocator)));
		// Tìm lưu lại hết all items vào 1 list để cho step tiếp thao
		List<WebElement> allItems = driver.findElements(By.cssSelector(childLocator));
		// Duyệt qua từng phần tử (element) trong list trên
		for (WebElement item : allItems) {
			// Lấy text ra
			String textActualItem = item.getText();
			// So sánh nếu nó bằng = item mình mong muốn
			if (textActualItem.equals(textExpectedItem)) {
				// Thì click vào
				item.click();
				// Khi đã tìm thấy và thỏa mãn điều kiện rồi thì k cần duyệt tiếp
				break;
			}
		}
	}

	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000); // Hàm java
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
