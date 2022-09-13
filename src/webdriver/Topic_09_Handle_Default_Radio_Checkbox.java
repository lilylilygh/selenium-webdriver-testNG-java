package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_09_Handle_Default_Radio_Checkbox {
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
	public void TC_02_Jotform() {
		driver.get("https://automationfc.github.io/multiple-fields/");
		// Chọn checkbox Cancer- Fainting Spells
		/* 1. Input có value: //input[@value='Cancer']
		 * 2. Input không có value: //label[contains(text(),'Cancer')]/preceding-sibling::input
		 */
		/****CODE CHAY - K dùng hàm void ***/
		driver.findElement(By.xpath("//input[@value='Cancer']")).click();
		driver.findElement(By.xpath("//input[@value='Fainting Spells']")).click();
		
		// Dùng hàm public void thì làm :checkToCheckBoxOrRadio("//input[@value='Cancer']");
		
		// Verify nó chọn thành công
		Assert.assertTrue(driver.findElement(By.xpath("//input[@value='Cancer']")).isSelected());
		Assert.assertTrue(driver.findElement(By.xpath("//input[@value='Fainting Spells']")).isSelected());
		
		// Assert.assertTrue(isElementSelected("//input[@value='Cancer']"));
		
		// Chọn Radio bất kỳ: 5+ days - 1-2 cups/day
		driver.findElement(By.xpath("//input[@value='5+ days']")).click();
		driver.findElement(By.xpath("//input[@value='1-2 cups/day']")).click();
		// Verify nó chọn thành công
		Assert.assertTrue(driver.findElement(By.xpath("//input[@value='5+ days']")).isSelected());
		Assert.assertTrue(driver.findElement(By.xpath("//input[@value='1-2 cups/day']")).isSelected());
		
		// Bỏ Chọn checkbox Cancer- Fainting Spells (chỉ cần click 1 lần nữa là bỏ chọn)
		driver.findElement(By.xpath("//input[@value='Cancer']")).click();
		driver.findElement(By.xpath("//input[@value='Fainting Spells']")).click();
		// uncheckToCheckBoxOrRadio("//input[@value='Cancer']");
		
		// Verify nó bỏ chọn thành công
		Assert.assertFalse(driver.findElement(By.xpath("//input[@value='Cancer']")).isSelected());
		Assert.assertFalse(driver.findElement(By.xpath("//input[@value='Fainting Spells']")).isSelected());		
		// Bỏ Chọn Radio bất kỳ: 5+ days - 1-2 cups/day (bắt buộc chọn 1 cái radio khác)
		driver.findElement(By.xpath("//input[@value='Never']")).click();
		driver.findElement(By.xpath("//input[@value='1-2 cups/day']")).click();		
		// Verify nó bỏ chọn thành công
		Assert.assertTrue(driver.findElement(By.xpath("//input[@value='Never']")).isSelected());
		Assert.assertTrue(driver.findElement(By.xpath("//input[@value='1-2 cups/day']")).isSelected());
	}
	
	@Test
	public void TC_02_Jotform_Select_All() {
		driver.get("https://automationfc.github.io/multiple-fields/");
		List<WebElement> allCheckboxes = driver.findElements(By.xpath("//input[@type='checkbox']"));
		// Dùng vòng lặp "for" để duyệt qua và click chọn
		for (WebElement checkbox: allCheckboxes) {
			if(!checkbox.isSelected()) {
				checkbox.click();
			}
			
		/* Nếu dùng cùng hàm public void ở dưới thì 
		 * trong 'for' không cần khai báo if mà sẽ khai báo như sau:
		 * checkToCheckboxOrRadio(checkbox);
		 * sleepInSecond(1);
		 */
		}
		// Dùng vòng lặp "for" để duyệt qua và kiểm tra
				for (WebElement checkbox: allCheckboxes) {
					Assert.assertTrue(checkbox.isSelected());
					}
				/* Nếu dùng cùng hàm public void ở dưới thì 
				 * trong 'for' sẽ khai báo như sau:
				 * Assert.assertTrue(isElementSelected(checkbox));
				 */
		for (WebElement checkbox: allCheckboxes) {
			uncheckToCheckBoxOrRadio(checkbox);
			
		}
		for (WebElement checkbox: allCheckboxes) {
			Assert.assertFalse(isElementSelected(checkbox));
			}
	}
	
	@Test
	public void TC_02_Kendo_jQuery_Select_All() {
		driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");
		sleepInSecond(10);
		
		List<WebElement> allCheckboxes = driver.findElements(By.xpath("//div[@id='example']//input[@type='checkbox']"));
		
		// Vừa click + vừa verify
		for (WebElement checkbox : allCheckboxes) {
			checkToCheckBoxOrRadio(checkbox);
		}
		
		for (WebElement checkbox : allCheckboxes) {
			uncheckToCheckBoxOrRadio(checkbox);
		}
	}
	
	@Test
	public void TC_02_Default_Kendo_jQuery() {
		driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");
		sleepInSecond(10);
		
		checkToCheckBoxOrRadio("//label[text()='Dual-zone air conditioning']/preceding-sibling::input");
		Assert.assertTrue(isElementSelected("//label[text()='Dual-zone air conditioning']/preceding-sibling::input"));
		
		uncheckToCheckBoxOrRadio("//label[text()='Dual-zone air conditioning']/preceding-sibling::input");
		Assert.assertFalse(isElementSelected("//label[text()='Dual-zone air conditioning']/preceding-sibling::input"));
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
