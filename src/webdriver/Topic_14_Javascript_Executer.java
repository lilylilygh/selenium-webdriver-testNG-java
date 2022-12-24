package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_14_Javascript_Executer {
	WebDriver driver;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe"); //trỏ đến file geckodriver.exe
		driver = new FirefoxDriver(); //library
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		//driver.get("https://www.facebook.com/");
		
		// Vs 1 interface: add kiểu của driver qua jsExecutor, interface k dùng khởi tạo, k có new
		// js được khởi tạo gắn driver vào -> gọi đc
		jsExecutor = (JavascriptExecutor) driver;
	}

	@Test
	public void TC_01_TechPanda() {
		/* Note: Các đoạn Javascript vào ts chạy rất nhanh
		 * Khi chuyển trang (navigate và click) trên các browser
		 * nó sẽ k chờ các element đc ready trc khi thao tác
		 * -> Nên có time sleep 
		 */
		navigateToUrlByJS("http://live.techpanda.org/");
		sleepInSecond(3);
		String  techPandaDomain = (String) executeForBrowser(" return document.domain");
		Assert.assertEquals(techPandaDomain, "live.techpanda.org");
		
		String  homePageURL = (String) executeForBrowser(" return document.URL");
		Assert.assertEquals(homePageURL, "http://live.techpanda.org/");
		
		hightlightElement("//a[text()='Mobile']");
		clickToElementByJS("//a[text()='Mobile']");
		sleepInSecond(3);
		
		hightlightElement("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div/button[@title='Add to Cart']");
		clickToElementByJS("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div/button[@title='Add to Cart']");
		sleepInSecond(3);
		Assert.assertTrue(areExpectedTextInInnerText("Samsung Galaxy was added to your shopping cart."));
		
		hightlightElement("//a[text()='Customer Service']");
		clickToElementByJS("//a[text()='Customer Service']");
		sleepInSecond(3);
		
		hightlightElement("//input[@id='newsletter']");
		scrollToElementOnTop("//input[@id='newsletter']");
		
		String emailAddrress = "lily" + generateRandomNumber() + "@hotmail.vn";
		sendkeyToElementByJS("//input[@id='newsletter']",emailAddrress);
		
		clickToElementByJS("//button[@title='Subscribe']");
		sleepInSecond(3);
		Assert.assertTrue(areExpectedTextInInnerText("Thank you for your subscription."));

		navigateToUrlByJS("http://demo.guru99.com/v4/");
		sleepInSecond(3);
		String domainPageGuru  = (String) executeForBrowser("return document.domain");
		Assert.assertEquals(domainPageGuru, "demo.guru99.com");
	}

	@Test
	public void TC_02_HTML5() {
		navigateToUrlByJS("https://sieuthimaymocthietbi.com/account/register");
		sleepInSecond(5);
		
		clickToElementByJS("//button[text()='Đăng ký']");	
		String validationMessage = getElementValidationMessage("//input[@id='lastName']");
		Assert.assertEquals(validationMessage, "Please fill out this field.");
		sendkeyToElementByJS("//input[@id='lastName']","Le");
		
		clickToElementByJS("//button[text()='Đăng ký']");	
		validationMessage = getElementValidationMessage("//input[@id='firstName']");
		Assert.assertEquals(validationMessage, "Please fill out this field.");
		sendkeyToElementByJS("//input[@id='firstName']","Loc");
		
		clickToElementByJS("//button[text()='Đăng ký']");	
		validationMessage = getElementValidationMessage("//input[@id='email']");
		Assert.assertEquals(validationMessage, "Please fill out this field.");
		sendkeyToElementByJS("//input[@id='email']","leloc89@hotmail.com");
		
		clickToElementByJS("//button[text()='Đăng ký']");	
		validationMessage = getElementValidationMessage("//input[@id='password']");
		Assert.assertEquals(validationMessage, "Please fill out this field.");
		sendkeyToElementByJS("//input[@id='password']","leloc89@hotmail.com");
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
	
	// Những hàm này k phải của Selenium, mà chỉ là hàm của Javascript
	// Hàm WebBrowser execute
	public Object executeForBrowser(String javaScript) {
		return jsExecutor.executeScript(javaScript);
	}

	// Hàm gọi document inner text
	public String getInnerText() {
		return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
	}

	// Hàm kiểm tra 1 text có khớp với text truyền vào k?
	public boolean areExpectedTextInInnerText(String textExpected) {
		String textActual = (String) jsExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0];");
		return textActual.equals(textExpected);
	}

	
	public void scrollToBottomPage() {
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public void navigateToUrlByJS(String url) {
		jsExecutor.executeScript("window.location = '" + url + "'");
	}

	// Hàm highlight element để biết đang chạy phần nào
	public void hightlightElement(String locator) {
		WebElement element = getElement(locator);
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, "border: 2px solid red; border-style: dashed;");
		sleepInSecond(1);
		jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, originalStyle);
	}

	public void clickToElementByJS(String locator) {
		jsExecutor.executeScript("arguments[0].click();", getElement(locator));
	}

	public void scrollToElementOnTop(String locator) {
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getElement(locator));
	}

	public void scrollToElementOnDown(String locator) {
		jsExecutor.executeScript("arguments[0].scrollIntoView(false);", getElement(locator));
	}

	public void sendkeyToElementByJS(String locator, String value) {
		jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", getElement(locator));
	}

	public void removeAttributeInDOM(String locator, String attributeRemove) {
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getElement(locator));
	}

	public String getElementValidationMessage(String locator) {
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getElement(locator));
	}

	public boolean isImageLoaded(String locator) {
		boolean status = (boolean) jsExecutor.executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0",
				getElement(locator));
		return status;
	}

	public WebElement getElement(String locator) {
		return driver.findElement(By.xpath(locator));
	}
	
	public int generateRandomNumber() {
		Random rand = new Random();
		return rand.nextInt(9999);
	}
}