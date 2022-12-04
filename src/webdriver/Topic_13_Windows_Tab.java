package webdriver;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Sleeper;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_13_Windows_Tab {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe"); //trỏ đến file geckodriver.exe
		driver = new FirefoxDriver(); //library
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Tab_Basic_Form() {
		// Driver Đang ở trang A
		driver.get("https://automationfc.github.io/basic-form/");
		String parentID = driver.getWindowHandle();
		// Vừa mở ra nó chỉ có duy nhất 1 tab
		// Lấy ra ID của driver đang đứng tại tab/window (Active - trong code)
		/* ---- Dùng Switch Window: chỉ dùng 2 tab/window ----
		 * String formTabID = driver.getWindowHandle();
		System.out.println("Form tab ID = " + formTabID);
		driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
		sleepInSecond(3);
		// Switch sang trang B
		switchToWindowByID(formTabID);
		// Driver Đang ở Trang B
		driver.findElement(By.name("q")).sendKeys("Selenium");
		String googleTabID = driver.getWindowHandle();
		// Quay về trang A
		switchToWindowByID(googleTabID);
		// Driver đang ở trang A
		driver.findElement(By.xpath("//a[text()='FACEBOOK']")).click();
		*/
		
		/* ---- Dùng Switch title: Dùng cho >=2 Tab/Window ----- */
		// Nên sleep sau khi click để sure page đã load xong
		driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
		sleepInSecond(3);
		// Switch qua trang B
		switchToWindowByTitle("Google");
		// Driver đang ở trang B
		driver.findElement(By.name("q")).sendKeys("Selenium");
		sleepInSecond(3);
		// Quay về trang A
		switchToWindowByTitle("SELENIUM WEBDRIVER FORM DEMO");
		// Driver đang ở trang A
		driver.findElement(By.xpath("//a[text()='FACEBOOK']")).click();
		sleepInSecond(3);
		// Switch qua trang C
		switchToWindowByTitle("Facebook – log in or sign up");
		driver.findElement(By.id("email")).sendKeys("automationfc.vn@gmail.com");
		driver.findElement(By.id("pass")).sendKeys("123456789");
		sleepInSecond(3);
		// Quay về trang A
		switchToWindowByTitle("SELENIUM WEBDRIVER FORM DEMO");
		// Driver đang ở trang A
		driver.findElement(By.xpath("//a[text()='TIKI']")).click();
		sleepInSecond(3);
		// Switch qua trang D
		switchToWindowByTitle("Tiki - Mua hàng online giá tốt, hàng chuẩn, ship nhanh");
		closeAllWindowWithoutParent(parentID);
		sleepInSecond(3);
	}

	@Test
	public void TC_02_Window_TechPanda() {
		// Driver đang ở trang MOBILE
		driver.get("http://live.techpanda.org/index.php/mobile.html");
		// Click vào Add to Compare
		driver.findElement(By.xpath("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']//a[text()='Add to Compare']")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector("li.success-msg span")).getText(), "The product Samsung Galaxy has been added to comparison list.");
	
		driver.findElement(By.xpath("//a[text()='Sony Xperia']/parent::h2/following-sibling::div[@class='actions']//a[text()='Add to Compare']")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector("li.success-msg span")).getText(), "The product Sony Xperia has been added to comparison list.");
	
		// Click vào Compare button
		driver.findElement(By.cssSelector("button[title='Compare']")).click();
		sleepInSecond(3);
		// Switch qua Windows Compare
		switchToWindowByTitle("Products Comparison List - Magento Commerce");
		// Verify nó hiển thị Product compare
		Assert.assertTrue(driver.findElement(By.xpath("//h1[text()='Compare Products']")).isDisplayed());
		// Click vào button Close Window
		driver.findElement(By.cssSelector("button[title='Close Window']")).click();
		sleepInSecond(3);
		// Driver đang ở trang Compare
		// Switch qua Mobile
		switchToWindowByTitle("Mobile");
		driver.findElement(By.id("search")).sendKeys("Samsung Galaxy");
		sleepInSecond(3);
		
		driver.findElement(By.xpath("//a[text()='Clear All']")).click();
		Alert alert = driver.switchTo().alert();
		sleepInSecond(5);
		alert.accept();
		sleepInSecond(5);
		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='The comparison list was cleared.']")).isDisplayed());
	}

	@Test
	public void TC_03_Cambridge_Dictionary() {
		driver.get("https://dictionary.cambridge.org/vi/");
		String parentID = driver.getWindowHandle();
		driver.findElement(By.xpath("//span[contains(@class,'login-button')]//span[text()='Đăng nhập']")).click();
		sleepInSecond(5);

		switchToWindowByID(parentID);
		driver.findElement(By.xpath("//input[@value ='Log in']")).click();
		Assert.assertTrue(driver
				.findElement(
						By.xpath("//div[contains(@class,'control-textbox')]/span[text()='This field is required']"))
				.isDisplayed());
		Assert.assertTrue(driver
				.findElement(
						By.xpath("//div[contains(@class,'control-password')]/span[text()='This field is required']"))
				.isDisplayed());
		String childernID = driver.getWindowHandle();

		driver.findElement(By.xpath("//input[@placeholder='Email *']")).sendKeys("automationfc.com@gmail.com");
		driver.findElement(By.xpath("//input[@placeholder='Password *']")).sendKeys("Automation000***");
		driver.findElement(By.xpath("//input[@value ='Log in']")).click();
		sleepInSecond(5);

		switchToWindowByID(childernID);
		sleepInSecond(5);
		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='Automation FC']")).isDisplayed());
	}
	
	// Nó chỉ dùng duy nhất 2 tab/window
	public void switchToWindowByID(String parentID) {
		// Lấy ra all ID of tab/windows đang có
		Set<String> allWindowIDs = driver.getWindowHandles();
		// Dùng vòng lặp để duyệt qua từng ID
		// Cách này dùng vòng lặp for each: Nghĩa là dùng biến đại diện để duyệt qua
		// từng cái
		for (String id : allWindowIDs) {
			// Nếu như có ID nào mà khác vs parentID
			// id: Là 1 biến tạm dùng để duyệt qua từng giá trị trong vòng lặp
			// (allWindowIDs)
			/*
			 * Lần duyệt thứ 1 id = A, formTabID = A -> Bỏ qua if Lần duyệt thứ 2 id = B,
			 * formTabID = A -> Vào if -> switch qua thành công -> Run next step
			 */
			if (!id.equals(parentID)) {
				// Switch vào
				driver.switchTo().window(id);
				break;
			}
			sleepInSecond(2);
		}
		System.out.println(driver.getCurrentUrl());
		System.out.println(driver.getTitle());
	}

	// Dùng cho 2 hoặc nhiều tab/window
	public void switchToWindowByTitle(String expectedPageTitle) {
		// Lấy ra all ID of tab/windows đang có
		Set<String> allWindowIDs = driver.getWindowHandles();
		// Dùng vòng lặp để duyệt qua từng ID
		for (String id : allWindowIDs) {
			// Switch vào từng tab/window trước
			driver.switchTo().window(id);
			// Lấy ra title của page đã switch vào
			String currentPageTitle = driver.getTitle();
			System.out.println(currentPageTitle);
			// Nếu như Title này = title mình mong muốn
			if (currentPageTitle.equals(expectedPageTitle)) {
				// Thoát khỏi vòng lặp - ko duyệt nữa
				break;
			}
		}
	}
	
	public void closeAllWindowWithoutParent(String parentID) {
		// Lấy ra all ID of tab/windows đang có
		Set<String> allWindowIDs = driver.getWindowHandles();
		// Dùng vòng lặp để duyệt qua từng ID
		for (String id : allWindowIDs) {
			// Mỗi lần duyệt qua nó sẽ kiểm tra
			// Nếu như id nào mà khác parentID
			if (!id.equals(parentID)) {
				// Switch vào
				driver.switchTo().window(id);
				// Đóng các tab đang active
				driver.close();
				// Đóng all browser. k quan tâm bao nhiêu tab
				// driver.quit();
			}
		}
		
		// Vẫn còn lại parentID
		driver.switchTo().window(parentID);
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	@Test
	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000); // Hàm java
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}