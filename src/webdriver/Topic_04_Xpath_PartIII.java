package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_04_Xpath_PartIII {
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
		driver.get("https://automationfc.github.io/basic-form/");
		
		//lay text tuyet doi
		driver.findElement(By.xpath("//h1[text()='SELENIUM WEBDRIVER API']"));
		
		//lay test contain text voi nested text
		driver.findElement(By.xpath("//h5[contains(text(),'Hello World!')]"));
		
		//lay text dung contain text,text nam o vi tri dau tien
		driver.findElement(By.xpath("//h5[contains(text(),'Michael Jackson')]"));
		
		//lay text contain text nam trong child node o bat ky vi tri nao
		driver.findElement(By.xpath("//h5[contains(.,'Michael Jackson')]"));
		driver.findElement(By.xpath("//h5[contains(.,'(Ignore Me)')]"));
		driver.findElement(By.xpath("//h5[contains(string(),'Michael Jackson')]"));
		driver.findElement(By.xpath("//h5[contains(string(),'(Ignore Me)')]"));
		
		//concat. Format: //tagname[text()=concat()]
		driver.findElement(By.xpath("//span[text()=concat('Hello \"John\", What',\"'s happened?\")]"));
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}