package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_06_Textbox_Textarea {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	String firstName, lastName, employeeID, editFirstName, editLastName;
	String immigrationNumber, comments;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe"); 
		driver = new FirefoxDriver(); 
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		firstName = "Nguyen";
		lastName = "linh";
		editFirstName = "Thuy";
		editLastName = "Linh";
		immigrationNumber = "774703475";
		// \r\n: xuống dòng, cách dòng
		comments = "Text box\nText area\nTopic 06";
	}
	
	@Test
	public void TC_01_Textbox_TextArea() {
		driver.get("https://opensource-demo.orangehrmlive.com/index.php");
		driver.findElement(By.cssSelector("input#txtUsername")).sendKeys("Admin");
		driver.findElement(By.cssSelector("input#txtPassword")).sendKeys("admin123");
		driver.findElement(By.cssSelector("input#btnLogin")).click();
		sleepInSecond(10);
		driver.get("https://opensource-demo.orangehrmlive.com/index.php/pim/addEmployee");
		// Nhập vào giá trị ("firstName")
		// driver.findElement(By.cssSelector("input#firstname")).sendKeys("firstName"); 
		
		// Nhập vào giá trị của biến (firstName) đã khởi tạo, giá trị là Nguyen
		driver.findElement(By.cssSelector("input#firstName")).sendKeys(firstName); 
		driver.findElement(By.cssSelector("input#lastName")).sendKeys(lastName); 
		// Lưu giá trị của EmployeeID vào biến
		// Lấy ra giá trị + Gán vào biến (=)
		employeeID = driver.findElement(By.cssSelector("input#employeeId")).getAttribute("value");
		driver.findElement(By.cssSelector("input#btnSave")).click();
		
		// Verify the fields are disabled. Nếu disable thì hàm trả về False
		Assert.assertFalse(driver.findElement(By.cssSelector("input#personal_txtEmpFirstName")).isEnabled());
		Assert.assertFalse(driver.findElement(By.cssSelector("input#personal_txtEmpLastName")).isEnabled());
		Assert.assertFalse(driver.findElement(By.cssSelector("input#personal_txtEmployeeId")).isEnabled());
		
		// Verify actual value = expected value
		Assert.assertEquals(driver.findElement(By.cssSelector("input#personal_txtEmpFirstName")).getAttribute("value"), firstName);
		Assert.assertEquals(driver.findElement(By.cssSelector("input#personal_txtEmpLastName")).getAttribute("value"), lastName);
		Assert.assertEquals(driver.findElement(By.cssSelector("input#personal_txtEmployeeId")).getAttribute("value"), employeeID);
		
		driver.findElement(By.cssSelector("input#btnSave")).click();
		sleepInSecond(3);
		// Verify the fields are enabled. Nếu enable thì hàm trả về True
		Assert.assertTrue(driver.findElement(By.cssSelector("input#personal_txtEmpFirstName")).isEnabled());
		Assert.assertTrue(driver.findElement(By.cssSelector("input#personal_txtEmpLastName")).isEnabled());
		Assert.assertTrue(driver.findElement(By.cssSelector("input#personal_txtEmployeeId")).isEnabled());
		
		// Edit fields: Firstname/ Lastname
		driver.findElement(By.cssSelector("input#personal_txtEmpFirstName")).clear();
		driver.findElement(By.cssSelector("input#personal_txtEmpFirstName")).sendKeys(editFirstName);
		driver.findElement(By.cssSelector("input#personal_txtEmpLastName")).clear();
		driver.findElement(By.cssSelector("input#personal_txtEmpLastName")).sendKeys(editLastName);
		driver.findElement(By.cssSelector("input#btnSave")).click();
		sleepInSecond(3);
		// Verify the fields are disabled. Nếu disable thì hàm trả về False
		Assert.assertFalse(driver.findElement(By.cssSelector("input#personal_txtEmpFirstName")).isEnabled());
		Assert.assertFalse(driver.findElement(By.cssSelector("input#personal_txtEmpLastName")).isEnabled());
		Assert.assertFalse(driver.findElement(By.cssSelector("input#personal_txtEmployeeId")).isEnabled());
		Assert.assertEquals(driver.findElement(By.cssSelector("input#personal_txtEmpFirstName")).getAttribute("value"), editFirstName);
		Assert.assertEquals(driver.findElement(By.cssSelector("input#personal_txtEmpLastName")).getAttribute("value"), editLastName);
		Assert.assertEquals(driver.findElement(By.cssSelector("input#personal_txtEmployeeId")).getAttribute("value"), employeeID);
		// Nằm trong thẻ Attribute value
		
		// Open immigration tab. Lưu ý: k nằm trong thẻ Attribute value, nhưng vẫn lấy được bằng attribute value
		driver.findElement(By.xpath("//a[text()='Immigration']")).click();
		driver.findElement(By.cssSelector("input#btnAdd")).click();
		
		// Enter to immigration number and Comments textarea
		driver.findElement(By.cssSelector("input#immigration_number")).sendKeys(immigrationNumber);
		driver.findElement(By.cssSelector("textarea#immigration_comments")).sendKeys(comments);
		sleepInSecond(5);
		driver.findElement(By.cssSelector("input#btnSave")).click();
		sleepInSecond(3);
		driver.findElement(By.xpath("//a[text()='Passport']")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector("input#immigration_number")).getAttribute("value"), immigrationNumber);
		Assert.assertEquals(driver.findElement(By.cssSelector("textarea#immigration_comments")).getAttribute("value"), comments);
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
