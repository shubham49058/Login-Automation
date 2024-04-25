package com.basic;

import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;

public class second {
	 private WebDriver driver;
		 
	@BeforeMethod
	  public void OpenWindow() {
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://app-dev.pulseconnect.us/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  }
	
	@DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return new Object[][] {
            { "smith@mailinator.com", "sdfsfdsfdor.com" },    //invalid password
            { "smithmailinator.com", "smih@mailinator.com" }, // invalid email
            { "","" },										  // blank inputs
            {"smith@mailinator.com","smith@mailinator.com"}   // Valid inputs
        };
    }

	@Test(dataProvider = "loginData")
	public void loginTest(String email, String password) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Enter email and password
        driver.findElement(By.id("exampleForm.ControlInput1")).sendKeys(email);
        driver.findElement(By.id("exampleForm.ControlTextarea1")).sendKeys(password);

        // Click on the login button
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
        loginButton.click();

        // Wait for the success or failure condition
        try {
            wait.until(ExpectedConditions.urlContains("http://app-dev.pulseconnect.us/Smith/admin"));
            System.out.println("Login Successful - Email: " + email + ", Password: " + password);
            // You can add more specific assertions here if needed
        } catch (Exception e) {
            System.out.println("Login Failed - Email: " + email + ", Password: " + password);
            Assert.fail("Login was not successful");
        }
        Thread.sleep(2000);
    }
	
	@AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
	
}
