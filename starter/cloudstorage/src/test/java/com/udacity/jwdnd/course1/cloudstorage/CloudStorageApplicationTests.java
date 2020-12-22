package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	//unauthorized user can only access the login and signup pages.
	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void signupPage() throws InterruptedException {
		signup();

		WebElement success = driver.findElement(By.id("signupSuccess"));
		Assertions.assertEquals("You successfully signed up! Please continue to the login page.",success.getText());
	}

	@Test
	public void loginPage() throws InterruptedException {
		signup();
		WebElement gologinpage = driver.findElement(By.id("btn-goLoginPage"));
		gologinpage.click();

		login();
		Thread.sleep(1000);
		Assertions.assertEquals("Home", driver.getTitle());
	}

	@Test
	public void logoutFunction() throws InterruptedException {
		signup();
		WebElement gologinpage = driver.findElement(By.id("btn-goLoginPage"));
		gologinpage.click();
		login();
		Thread.sleep(1000);
		logout();
		Thread.sleep(1000);

		driver.get("http://localhost:" + this.port + "/home");

		Assertions.assertEquals("Login", driver.getTitle());

	}

	@Test
	public void noteCreation() throws InterruptedException {
		signup();
		WebElement gologinpage = driver.findElement(By.id("btn-goLoginPage"));
		gologinpage.click();
		login();

		WebElement goNoteTab = driver.findElement(By.id("nav-notes-tab"));
		goNoteTab.click();
		Thread.sleep(5000);
		WebElement addNoteButton = driver.findElement(By.id("btn-addNote"));
		addNoteButton.click();
		Thread.sleep(5000);
		WebElement inputTitle = driver.findElement(By.id("note-title"));
		inputTitle.sendKeys("Hello");
		WebElement inputDescription = driver.findElement(By.id("note-description"));
		inputDescription.sendKeys("This is junit test!");
		WebElement btnSubmitNote = driver.findElement(By.id("btn-submitNote"));
		btnSubmitNote.click();
		Thread.sleep(3000);
		goNoteTab = driver.findElement(By.id("nav-notes-tab"));
		goNoteTab.click();
		Thread.sleep(3000);

		WebElement table = driver.findElement(By.xpath("//*[@id='userTable']/tbody/tr/th"));

		Assertions.assertEquals("editHello", table.getText());
	}

	@Test
	public void credentialCreation() throws InterruptedException {
		signup();
		WebElement gologinpage = driver.findElement(By.id("btn-goLoginPage"));
		gologinpage.click();
		login();

		WebElement goCredentialTab = driver.findElement(By.id("nav-credentials-tab"));
		goCredentialTab.click();
		Thread.sleep(5000);
		WebElement addCredentialButton = driver.findElement(By.id("btn-addNetCredential"));
		addCredentialButton.click();
		Thread.sleep(5000);
		WebElement inputUrl = driver.findElement(By.id("credential-url"));
		inputUrl.sendKeys("www.google.com");
		WebElement inputusername = driver.findElement(By.id("credential-username"));
		inputusername.sendKeys("udacity");
		WebElement inputpassword = driver.findElement(By.id("credential-password"));
		inputpassword.sendKeys("udacity");
		WebElement btnSubmitCredential = driver.findElement(By.id("btn-credentialSubmit"));
		btnSubmitCredential.click();
		Thread.sleep(3000);
		goCredentialTab = driver.findElement(By.id("nav-credentials-tab"));
		goCredentialTab.click();
		Thread.sleep(3000);

		WebElement table = driver.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/th"));
		Assertions.assertEquals("www.google.com", table.getText());
	}

	@Test
	public void deleteNote()throws InterruptedException {
		signup();
		WebElement gologinpage = driver.findElement(By.id("btn-goLoginPage"));
		gologinpage.click();
		login();
		WebElement goNoteTab = driver.findElement(By.id("nav-notes-tab"));
		goNoteTab.click();
		Thread.sleep(5000);
		WebElement addNoteButton = driver.findElement(By.id("btn-addNote"));
		addNoteButton.click();
		Thread.sleep(5000);
		WebElement inputTitle = driver.findElement(By.id("note-title"));
		inputTitle.sendKeys("Hello");
		WebElement inputDescription = driver.findElement(By.id("note-description"));
		inputDescription.sendKeys("This is junit test!");
		WebElement btnSubmitNote = driver.findElement(By.id("btn-submitNote"));
		btnSubmitNote.click();
		Thread.sleep(3000);
		goNoteTab = driver.findElement(By.id("nav-notes-tab"));
		goNoteTab.click();
		Thread.sleep(3000);

		WebElement table = driver.findElement(By.xpath("//*[@id='userTable']/tbody"));

		table.findElement(By.id("btn-deleteNote")).click();
		Thread.sleep(3000);
		goNoteTab = driver.findElement(By.id("nav-notes-tab"));
		goNoteTab.click();
		Thread.sleep(3000);
		table = driver.findElement(By.xpath("//*[@id='userTable']/tbody"));
		Assertions.assertEquals("", table.getText());
	}

	@Test
	public void deleteCredential() throws InterruptedException{
		signup();
		WebElement gologinpage = driver.findElement(By.id("btn-goLoginPage"));
		gologinpage.click();
		login();

		WebElement goCredentialTab = driver.findElement(By.id("nav-credentials-tab"));
		goCredentialTab.click();
		Thread.sleep(5000);
		WebElement addCredentialButton = driver.findElement(By.id("btn-addNetCredential"));
		addCredentialButton.click();
		Thread.sleep(5000);
		WebElement inputUrl = driver.findElement(By.id("credential-url"));
		inputUrl.sendKeys("www.google.com");
		WebElement inputusername = driver.findElement(By.id("credential-username"));
		inputusername.sendKeys("udacity");
		WebElement inputpassword = driver.findElement(By.id("credential-password"));
		inputpassword.sendKeys("udacity");
		WebElement btnSubmitCredential = driver.findElement(By.id("btn-credentialSubmit"));
		btnSubmitCredential.click();
		Thread.sleep(3000);
		goCredentialTab = driver.findElement(By.id("nav-credentials-tab"));
		goCredentialTab.click();
		Thread.sleep(3000);


		WebElement table = driver.findElement(By.xpath("//*[@id='credentialTable']/tbody"));

		table.findElement(By.id("btn-deleteCredential")).click();
		Thread.sleep(3000);
		goCredentialTab = driver.findElement(By.id("nav-credentials-tab"));
		goCredentialTab.click();
		Thread.sleep(3000);
		table = driver.findElement(By.xpath("//*[@id='credentialTable']/tbody"));
		Assertions.assertEquals("", table.getText());

	}

	@Test
	public void editNote()throws InterruptedException{
		signup();
		WebElement gologinpage = driver.findElement(By.id("btn-goLoginPage"));
		gologinpage.click();
		login();
		WebElement goNoteTab = driver.findElement(By.id("nav-notes-tab"));
		goNoteTab.click();
		Thread.sleep(5000);
		WebElement addNoteButton = driver.findElement(By.id("btn-addNote"));
		addNoteButton.click();
		Thread.sleep(5000);
		WebElement inputTitle = driver.findElement(By.id("note-title"));
		inputTitle.sendKeys("Hello");
		WebElement inputDescription = driver.findElement(By.id("note-description"));
		inputDescription.sendKeys("This is junit test!");
		WebElement btnSubmitNote = driver.findElement(By.id("btn-submitNote"));
		btnSubmitNote.click();
		Thread.sleep(3000);
		goNoteTab = driver.findElement(By.id("nav-notes-tab"));
		goNoteTab.click();
		Thread.sleep(3000);

		WebElement table = driver.findElement(By.xpath("//*[@id='userTable']/tbody"));

		table.findElement(By.id("btn-editNote")).click();
		Thread.sleep(3000);

		inputTitle = driver.findElement(By.id("note-title"));
		inputTitle.clear();
		inputTitle.sendKeys("edited udacity");

		btnSubmitNote = driver.findElement(By.id("btn-submitNote"));
		btnSubmitNote.click();
		Thread.sleep(3000);

		goNoteTab = driver.findElement(By.id("nav-notes-tab"));
		goNoteTab.click();
		Thread.sleep(3000);

		table = driver.findElement(By.xpath("//*[@id='userTable']/tbody/tr/th"));

		Assertions.assertEquals("edited udacity", table.getText());
	}

	@Test
	public void editCredential()throws InterruptedException{
		signup();
		WebElement gologinpage = driver.findElement(By.id("btn-goLoginPage"));
		gologinpage.click();
		login();

		WebElement goCredentialTab = driver.findElement(By.id("nav-credentials-tab"));
		goCredentialTab.click();
		Thread.sleep(5000);
		WebElement addCredentialButton = driver.findElement(By.id("btn-addNetCredential"));
		addCredentialButton.click();
		Thread.sleep(5000);
		WebElement inputUrl = driver.findElement(By.id("credential-url"));
		inputUrl.sendKeys("www.google.com");
		WebElement inputusername = driver.findElement(By.id("credential-username"));
		inputusername.sendKeys("udacity");
		WebElement inputpassword = driver.findElement(By.id("credential-password"));
		inputpassword.sendKeys("udacity");
		WebElement btnSubmitCredential = driver.findElement(By.id("btn-credentialSubmit"));
		btnSubmitCredential.click();
		Thread.sleep(3000);
		goCredentialTab = driver.findElement(By.id("nav-credentials-tab"));
		goCredentialTab.click();
		Thread.sleep(3000);

		WebElement table = driver.findElement(By.xpath("//*[@id='credentialTable']/tbody"));

		table.findElement(By.id("btn-editCredential")).click();
		Thread.sleep(3000);

		inputusername = driver.findElement(By.id("credential-username"));
		inputusername.clear();
		inputusername.sendKeys("editedudacity");

		btnSubmitCredential = driver.findElement(By.id("btn-credentialSubmit"));
		btnSubmitCredential.click();
		Thread.sleep(3000);

		goCredentialTab = driver.findElement(By.id("nav-credentials-tab"));
		goCredentialTab.click();
		Thread.sleep(3000);

		table = driver.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/td[2]"));
		//*[@id="credentialTable"]/tbody/tr/td[2]

		Assertions.assertEquals("editedudacity", table.getText());
	}

	private void logout(){
		WebElement logoutButton = driver.findElement(By.id("btn-logout"));
		logoutButton.click();
	}

	private void login() throws InterruptedException {
		WebElement inputUsernameField = driver.findElement(By.id("inputUsername"));
		inputUsernameField.sendKeys("udacity");

		WebElement inputPasswordField = driver.findElement(By.id("inputPassword"));
		inputPasswordField.sendKeys("udacity");

		WebElement loginButton = driver.findElement(By.id("btn-login"));
		loginButton.click();

		Thread.sleep(5000);
	}

	private void signup() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("sign up", driver.getTitle().toLowerCase());
		WebElement inputFirstNameField = driver.findElement(By.id("inputFirstName"));
		inputFirstNameField.sendKeys("Spencer");

		WebElement inputLastNameField = driver.findElement(By.id("inputLastName"));
		inputLastNameField.sendKeys("Liu");

		WebElement inputUsernameField = driver.findElement(By.id("inputUsername"));
		inputUsernameField.sendKeys("udacity");

		WebElement inputPasswordField = driver.findElement(By.id("inputPassword"));
		inputPasswordField.sendKeys("udacity");

		WebElement signupButton = driver.findElement(By.id("signup"));
		signupButton.click();

		Thread.sleep(1000);
	}

}
