package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_04_Xpath_PartII {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

// change
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01() {

		driver.get("https://demo.nopcommerce.com/");

		// Find element = lay tuyet doi text
		// Format: //tagname[text()='']
		driver.findElement(By.xpath("//h2[text()='Welcome to our store']"));

		// Find element = lay tuyet doi attribute
		// Format : //tagname[@attribute=''];
		driver.findElement(By.xpath("//h2[@class='title']//a[@title='Show products in category Apparel']"));

		// Find element = lay tuong doi contains() attribute
		// Format : //tagname[contains(@attribute,'')]
		driver.findElement(
				By.xpath("//div[@class='picture']//a[contains(@title,'Show products in category Apparel')]"));
		// Find element = lay tuong doi contains() text
		// Format : //tagname[contains(text(),'')]
		driver.findElement(By.xpath("//div[contains(text(),'We have thought of everything')]"));
		// Find element = lay tuong doi starts-with text
		// Format : //tagname[starts-with(text(),'')]
		driver.findElement(By.xpath("//div[starts-with(text(),'The new nopCommerce store is open now')]"));
		// Find element = lay tuong doi starts-with attribute
		driver.findElement(By.xpath("//div[@class='footer-block my-account']//a[starts-with(@href,'/customer/info')]"));

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}