package webdriver;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test
public class Topic_07_Default_Dropdown {
	WebDriver driver;
	// Khai báo select để dùng Dropdown (Khởi tạo là phải = rồi gán biến)
	Select select;
	Random rand;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");


	@BeforeClass
	public void beforeClass() {
	System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe"); 
	// Khởi tạo
	driver = new FirefoxDriver(); 
	rand = new Random();
	
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Test
	public void TC_03_Default_Dropdown() {
		driver.get("https://demo.nopcommerce.com");
		driver.findElement(By.cssSelector("a.ico-register")).click();
		driver.findElement(By.cssSelector("input#FirstName")).sendKeys("Lily");
		driver.findElement(By.cssSelector("input#LastName")).sendKeys("Nguyen");
		
		// Thao tác với Dropdown (Select lib)
		// Khởi tạo select để thao tác với Day Dropdown
		select = new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthDay']")));
		// chọn item có text = 4
		select.selectByVisibleText("4");
		select = new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthMonth']")));
		select.selectByVisibleText("June");
		select = new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthYear']")));
		select.selectByVisibleText("2020");
		// Mỗi lần chạy sẽ ra 1 email khác nhau
		// Cần tạo 1 biến để chạy random email 
		String emailAddress = "lily" + rand.nextInt(9999) + "@hotmail.com";
		driver.findElement(By.cssSelector("input#Email")).sendKeys(emailAddress);
		driver.findElement(By.cssSelector("input#Company")).sendKeys("United States");
		driver.findElement(By.cssSelector("input#Password")).sendKeys("123456");
		driver.findElement(By.cssSelector("input#ConfirmPassword")).sendKeys("123456");
		driver.findElement(By.cssSelector("button#register-button")).click();
		
		// So sanh Web Element - String -> Nên phải có hàm getText, get text trả về String
		// Bằng nhau về kiểu dữ liệu, giá trị của dữ liệu
		Assert.assertEquals(driver.findElement(By.cssSelector("div.result")).getText(), "Your registration completed");
		driver.findElement(By.cssSelector("a.ico-account")).click();
		// Textbox nên sẽ nằm trong Attribute value
		Assert.assertEquals(driver.findElement(By.cssSelector("input#FirstName")).getAttribute("value"), "Lily");
		Assert.assertEquals(driver.findElement(By.cssSelector("input#LastName")).getAttribute("value"), "Nguyen");
		
		// Verify chọn 4 được rồi
		// getFirst là sử dụng single dropdown. Chọn 4 thì hiển thị 4
		select = new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthDay']")));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), "4");
		
		select = new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthMonth']")));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), "June");
		
		select = new Select(driver.findElement(By.cssSelector("select[name='DateOfBirthYear']")));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), "2020");
		
		
		Assert.assertEquals(driver.findElement(By.cssSelector("input#Email")).getAttribute("value"), emailAddress);
		Assert.assertEquals(driver.findElement(By.cssSelector("input#Company")).getAttribute("value"), "United States");
		// Verify 1 dropdown là single hay multiple
		// Single -> False, Multiple -> True
		// Assert.assertFalse(select.isMultiple());	
	}
	
	@Test
	public void TC_04_Default_Dropdown() {
		driver.get("https://rode.com/en/support/where-to-buy");
		select = new Select (driver.findElement(By.id("country")));
		select.selectByValue("Vietnam");
		sleepInSecond(10);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), "Vietnam");
		// In ra tất cả các tên trong dropdown (dùng findElements)
		List<WebElement> dealers = driver.findElements(By.cssSelector("div#map h4"));
		
		for (WebElement element : dealers) {
			System.out.println(element.getText());
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
