package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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

public class Topic_10_User_Interaction {
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
		jsExecutor = (JavascriptExecutor) driver;
		
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
	
	@Test
	public void TC_02_Hover() {
		driver.get("https://www.myntra.com/");
		WebElement kidLink = driver.findElement(By.xpath("//header[@id='desktop-header-cnt']//a[text()='Kids']"));
		action.moveToElement(kidLink).perform();
		sleepInSecond(3);
		
		action.click(driver.findElement(By.xpath("//header[@id='desktop-header-cnt']//a[text()='Home & Bath']"))).perform();
		sleepInSecond(3);
		Assert.assertTrue(driver.findElement(By.xpath("//h1[text()='Kids Home Bath']")).isDisplayed());
	}
	
	@Test
	public void TC_03_Hover() {
		driver.get("https://fptshop.com.vn/");
		action.moveToElement(driver.findElement(By.xpath("//a[title()='ĐIỆN THOẠI']"))).perform();
		sleepInSecond(5);
		action.click(driver.findElement(By.xpath("//a[title()='Apple (iPhone)']"))).perform();
		sleepInSecond(5);
		Assert.assertEquals(driver.getCurrentUrl(), "https://fptshop.com.vn/dien-thoai/apple-iphone");
	}
	
	@Test
	public void TC_04_Click_And_Hold_Block() {
		driver.get("https://automationfc.github.io/jquery-selectable/");

		// Store all 12 elements
		List<WebElement> allNumbers = driver.findElements(By.cssSelector("ol#selectable>li"));
		Assert.assertEquals(allNumbers.size(), 12);
		// Click and hold mouse tại số thứ 1
		action.clickAndHold(allNumbers.get(0))
		// Hover chuột tới số 11
		.moveToElement(allNumbers.get(10))
		// Nhả chuột trái ra
		.release()
		// Thực thi các action trên
		.perform();
		
		// tìm 9 elements
		allNumbers = driver.findElements(By.cssSelector("ol#selectable>li.ui-selected"));
		Assert.assertEquals(allNumbers.size(), 9);
	}
	
	@Test
	public void TC_05_Click_And_Hold_Random() {
		driver.get("https://automationfc.github.io/jquery-selectable/");
		// Store all 12 elements
		List<WebElement> allNumbers = driver.findElements(By.cssSelector("ol#selectable>li"));
		Assert.assertEquals(allNumbers.size(), 12);
		
		// Nhả phím Control xuống
		action.keyDown(Keys.CONTROL).perform();
		
		action.click(allNumbers.get(0))
		.click(allNumbers.get(2))
		.click(allNumbers.get(5))
		.click(allNumbers.get(7))
		.click(allNumbers.get(10)).perform();
		
		// Nhả phím Control ra
		action.keyUp(Keys.CONTROL).perform();
		
		// tìm 9 elements
		allNumbers = driver.findElements(By.cssSelector("ol#selectable>li.ui-selected"));
		Assert.assertEquals(allNumbers.size(), 5);
	}
	
	@Test
	public void TC_06_Double_Click() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		WebElement doubleClickMeText = driver.findElement(By.xpath("//button[text()='Double click me']"));
		
		/* Scroll to element
		 * True: mép trên của element và kéo element lên trên cùng
		 * False: mép dưới của element và kéo element xuống dưới cùng
		 */
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", doubleClickMeText);
		sleepInSecond(3);
		action.doubleClick(doubleClickMeText).perform();
		sleepInSecond(3);
		
		Assert.assertEquals(driver.findElement(By.cssSelector("p#demo")).getText(), "Hello Automation Guys!");
	}
	
	@Test
	public void TC_07_Right_Click_ToElement() {
		driver.get("http://swisnl.github.io/jQuery-contextMenu/demo.html");
	
		action.contextClick(driver.findElement(By.xpath("//span[text()='right click me']"))).perform();
		sleepInSecond(3); //thuc te bo sleep di run se nhanh hon
		
		WebElement deleteBefore = driver.findElement(By.cssSelector("li.context-menu-icon-delete"));
		action.moveToElement(deleteBefore).perform();
		sleepInSecond(3);
		
		Assert.assertTrue(driver.findElement(By.cssSelector("li.context-menu-icon-delete.context-menu-visible.context-menu-hover")).isDisplayed());
		action.click(deleteBefore).perform();
		
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "clicked: delete");
		alert.accept();
		sleepInSecond(3);
		
		Assert.assertFalse(deleteBefore.isDisplayed());
	}
	
	@Test
	public void TC_08_Drag_And_Drop_HTML4() {
		driver.get("https://automationfc.github.io/kendo-drag-drop/");
		WebElement sourceCircle = driver.findElement(By.cssSelector("div#draggable"));
		WebElement targetCircle = driver.findElement(By.cssSelector("div#droptarget"));
		action.dragAndDrop(sourceCircle, targetCircle).perform();
		sleepInSecond(3);
		
		Assert.assertEquals(targetCircle.getText(), "You did great!");
		String hexaColor = Color.fromString(targetCircle.getCssValue("background-color")).asHex().toUpperCase();
		Assert.assertEquals(hexaColor, "#03A9F4");
		
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
