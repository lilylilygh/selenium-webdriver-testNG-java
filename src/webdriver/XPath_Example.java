package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class XPath_Example {
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
	public void TC_01_Example() {
		driver.get("https://www.facebook.com/");
		
		/*<input class="text form-control" 
		 * id="txtEmail" name="txtEmail" 
		 * placeholder="Địa chỉ email" type="email" 
		 * value="" aria-required="true" aria-invalid="true">*/
		
		
		// 1 - < hoặc <> : thẻ mở
		// 2 - tên thẻ (Tagname): input/ html/ body/ head/ form/ lable/ span/ p/...
		// 3 - thuộc tính (Attribute name): class/ id/ name/ placeholder/ type/ value
		// 4 - giá trị của thuộc tính (Attribute value): text form-control/ txtEmail/... 
		// 5 - > hoặc </> : thẻ đóng
		
		// tagname - Attribute name - Attribute value
		
		// XPath Format Basic
		// Absolute XPath: /html/body/...
		// Relative XPath: //tagname[@attribute-name='attribute-value']
		// Relative XPath: //tagname[@attribute-name="attribute-value"]
		
		// CSS Format Basic
		// tagname[attribute-name='attribute-value']
		// tagname[attribute-name="attribute-value"]
	
		// Locator code gọn hơn
		driver.findElement(By.xpath("//input[@id='Password']"));
		// Locator code xấu hơn - khó đọc hơn
		driver.findElement(By.xpath("//*[@id=\"Password\"]"));
	
	}
}
