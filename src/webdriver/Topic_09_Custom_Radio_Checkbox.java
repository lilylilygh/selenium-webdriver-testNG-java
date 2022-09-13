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

public class Topic_09_Custom_Radio_Checkbox {
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
	public void TC_03_Custom_Checkbox_Angular() {
		driver.get("https://material.angular.io/components/checkbox/examples");
		/* Case 1:
		 * Thẻ input bị ẩn nên k click được
		 * Thẻ input dùng để verify được
		 * Case 2:
		 * Ko dùng thẻ input để click -> thay thế bằng 1 thẻ đang hiển thị đại diện cho checkbox/radio: span/ div/...
		 * Các thẻ này lại k verify đc
		 * Case 3: 
		 * Thẻ span để click
		 * Thẻ input để verify
		 * Trong 1 dự án mà 1 element cần tới 2 locator để defince thì sinh ra nhiều code/ cần phải maintain nhiều -> Dễ confuse cho ng mới
 		 * Click
		driver.findElement(By.xpath("//span[text()='Checked']/preceding-sibling::span")).click();
		sleepInSecond(3);
		 * Verify
		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='Checked']/preceding-sibling::span/input")).isSelected());
		*/
		
		/* Case 4: Workaround (Best)
		 * Thẻ input để click + verify
		 * Hàm click() của WebElement k click vào element bị ẩn được 
		 * Hàm click() của JavascriptExecutor để click: k quan tâm element bị ẩn hay ko
		 */
		By checkedCheckbox = By.xpath("//span[text()='Checked']/preceding-sibling::span/input");
		// argument 0 là đại diện cho cả element "checkedCheckbox"
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(checkedCheckbox));
		sleepInSecond(3);
		Assert.assertTrue(driver.findElement(checkedCheckbox).isSelected());
	}
	
	@Test
	public void TC_03_Custom_RadioButton_Angular() {
		driver.get("https://material.angular.io/components/radio/examples");
		By springRadio = By.xpath("//span[contains(text(),'Spring')]/preceding-sibling::span/input");
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(springRadio));
		sleepInSecond(3);
		Assert.assertTrue(driver.findElement(springRadio).isSelected());
	}
	
	@Test
	public void TC_04_Custom_RadioButton_Google_Doc() {
	driver.get("https://docs.google.com/forms/d/e/1FAIpQLSfiypnd69zhuDkjKgqvpID9kwO29UCzeCVrGGtbNPZXQok0jA/viewform");
	By canThoRadio = By.xpath("//div[@aria-label='Cần Thơ']");
	// Verify trước khi click, Radio button k thể bỏ chọn. 
	// Cách 1: dùng getAttribute
	driver.findElement(canThoRadio).getAttribute("aria-checked");
	Assert.assertEquals(driver.findElement(canThoRadio).getAttribute("aria-checked"), "false");
	driver.findElement(canThoRadio).click();
	sleepInSecond(3);
	Assert.assertEquals(driver.findElement(canThoRadio).getAttribute("aria-checked"), "true");
	
	// Checkbox có thể bỏ chọn
	By miQuangcheckbox = By.xpath("//div[@aria-label='Mì Quảng']");
	checkToCheckbox("//div[@aria-label='Mì Quảng']");
	sleepInSecond(3);
	Assert.assertEquals(driver.findElement(miQuangcheckbox).getAttribute("aria-checked"), "true");
	// Cách 2:
	Assert.assertTrue(driver.findElement(By.xpath("//div[@aria-label='Mì Quảng' and @aria-checked='true']")).isDisplayed());
	
	uncheckToCheckbox("//div[@aria-label='Mì Quảng']");
	sleepInSecond(3);
	Assert.assertEquals(driver.findElement(miQuangcheckbox).getAttribute("aria-checked"), "false");
	Assert.assertTrue(driver.findElement(By.xpath("//div[@aria-label='Mì Quảng' and @aria-checked='false']")).isDisplayed());
	
	// Cách 2: Dùng hàm isDisplay()
	Assert.assertTrue(driver.findElement(By.xpath("//div[@aria-label='Cần Thơ' and @aria-checked='true']")).isDisplayed());
	}
	
	public void checkToCheckbox(String xpathLocator) {
		WebElement element = driver.findElement(By.xpath(xpathLocator));
		if (element.getAttribute("aria-checked").equals("false")){
			element.click();
		}
	}
	
	public void uncheckToCheckbox(String xpathLocator) {
		WebElement element = driver.findElement(By.xpath(xpathLocator));
		if (element.getAttribute("aria-checked").equals("true")){
			element.click();
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
