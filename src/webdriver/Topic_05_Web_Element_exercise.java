package webdriver;

import java.security.PublicKey;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_05_Web_Element_exercise {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Test
	public void TC_01_isDisplayed() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		// Email Textbox
		WebElement emailTextbox = driver.findElement(By.cssSelector("input#mail"));
		
		if (emailTextbox.isDisplayed()) {
			emailTextbox.sendKeys("linh@gmail.com");
			System.out.println("Email textbox is displayed");
		} else {
			System.out.println("Email textbox is not displayed");
		}
		//Cách tối ưu
		System.out.println(emailTextbox.isDisplayed());
		
		// Age Under 18 Radio Button
		WebElement ageUnder18Radio = driver.findElement(By.cssSelector("input#under_18"));
		if (ageUnder18Radio.isDisplayed()) {
			ageUnder18Radio.click();
			System.out.println("Age under 18 radio is displayed");
		} else {
			System.out.println("Age under 18 radio is not displayed");
		}
		System.out.println(ageUnder18Radio.isDisplayed());
		
		// Education TextArea
		WebElement educationTextarea = driver.findElement(By.cssSelector("textarea#edu"));
		if (educationTextarea.isDisplayed()) {
			educationTextarea.sendKeys("Automation Testing");
			System.out.println("Education textarea is displayed");
		} else {
			System.out.println("Education textarea is not displayed");
		}
		System.out.println(educationTextarea.isDisplayed());
		
		// Name: User5 (hover)
		WebElement User5 = driver.findElement(By.xpath("//h5[text()='Name: User5']"));
		if (User5.isDisplayed()) {
			System.out.println("User5 is displayed");
		} else {
			System.out.println("User5 is not displayed");
		}
		
		// Nếu như element hiển thị thì hàm isDisplayed trả về True
		// Nếu như element k hiển thị thì hàm isDisplayed trả về False
		System.out.println(User5.isDisplayed());
	}

	@Test
	public void TC_02_isEnabled() {
		// Có thể tương tác được = enabled -> True
		// K tương tác lên được = disabled -> false
		// Phạm vi áp dụng: tât cả các loại element (Textbox/ TextArea/ Radio/ Link/ Button...)
		driver.get("https://automationfc.github.io/basic-form/index.html");
		// Email Textbox
		WebElement email = driver.findElement(By.cssSelector("input#mail"));
		//If/else kiểm tra đk hoặc đúng hoặc sai
		if (email.isEnabled()) {
			System.out.println("Email is enabled");
		} else {
			System.out.println("Email is disabled");
		}
		System.out.println(email.isEnabled());
		
		// Age (Under18)
		WebElement ageUnder18 = driver.findElement(By.cssSelector("input#under_18"));
		if (ageUnder18.isEnabled()) {
			System.out.println("ageUnder18 is enabled");
		} else {
			System.out.println("ageUnder18 is disabled");
		}
		System.out.println(ageUnder18.isEnabled());
		
		// Education
		WebElement education = driver.findElement(By.cssSelector("textarea#edu"));
		if (education.isEnabled()) {
			System.out.println("ageUnder18 is enabled");
		} else {
			System.out.println("ageUnder18 is disabled");
		}
		System.out.println(education.isEnabled());
		
		// Password Textbox
		WebElement password = driver.findElement(By.cssSelector("input#disable_password"));
		if (password.isEnabled()) {
			System.out.println("password is enabled");
		} else {
			System.out.println("password is disabled");
		}
		System.out.println(password.isEnabled());
		
	}

	@Test
	public void TC_03_isSelected() {
		// Đã chọn được chưa = Selected -> True
		// Chưa chọn = deselcted -> False
		// Phạm vi áp dụng: Radio button/ Checkbox/ Dropdown list (Default) - DL có thư viện riêng
		driver.get("https://automationfc.github.io/basic-form/index.html");
		WebElement ageUnder18Radio = driver.findElement(By.cssSelector("input#under_18"));
		ageUnder18Radio.click();
		if (ageUnder18Radio.isSelected()) {
			System.out.println("Age under 18 is Selected");
		} else {
				System.out.println("Age under 18 is de-selected");
			}
		
	// Languages Java Checkbox:
	driver.get("https://automationfc.github.io/basic-form/index.html");
	WebElement javaCheckbox = driver.findElement(By.cssSelector("input#java"));
	javaCheckbox.click();
	if (javaCheckbox.isSelected()) {
		System.out.println("Java checkbox is Selected");
	} else {
			System.out.println("Java checkbox is de-selected");
		}
	}
	
	@Test
	public void TC_04_MailChimp() {
		driver.get("https://login.mailchimp.com/signup/post");
		
		// Email/ Username Textbox
		driver.findElement(By.cssSelector("input#email")).sendKeys("automationfc@gmail.net");
		// Sleep cứng (Static wait): điều kiện thỏa mãn rồi bắt buộc chờ đến khi timeout
		sleepInSecond(3); // Sleep 3s tự động điền field username
		
		// Sử dụng throws Exception: nếu như đọc trực tiếp trong TC
		// sử dụng try cat: nếu như chạy được thì vào try, nếu fail thì vào catch)
		
		WebElement passwordTextbox = driver.findElement(By.cssSelector("input#new_password"));
	    // Check lowercase
		passwordTextbox.sendKeys("aa");
		sleepInSecond(2);
		// CSS muốn lấy giá trị phải thay khoảng trắng thành "." thì mới dùng được
		// Vừa số vừa có not thì sẽ k dùng đc css
		Assert.assertTrue(driver.findElement(By.cssSelector("li.lowercase-char.completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.uppercase-char.not-completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.number-char.not-completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.special-char.not-completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char not-completed']")).isDisplayed());
		
		// Check uppercase 
		passwordTextbox.clear();
		passwordTextbox.sendKeys("AAA");
		sleepInSecond(2);
		
		Assert.assertTrue(driver.findElement(By.cssSelector("li.lowercase-char.not-completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.uppercase-char.completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.number-char.not-completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("li.special-char.not-completed")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char not-completed']")).isDisplayed());
		
		// Full
		passwordTextbox.clear();
		passwordTextbox.sendKeys("ABCabc@@@123");
		sleepInSecond(2);
		Assert.assertTrue(driver.findElement(By.cssSelector("input#new_password")).isDisplayed());
		
	}
	
	@AfterClass
	public void sleepInSecond(long timeInSecond) {
			try {
				Thread.sleep(timeInSecond * 1000);		// Hàm java
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	
	public void afterClass() {
		driver.quit();
	}
}
