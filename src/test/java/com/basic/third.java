package com.basic;

import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class third {
    private WebDriver driver;

    @BeforeSuite
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://app-dev.pulseconnect.us/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return new Object[][] {
            { "smith@mailinator.com", "sdfsfdsfdor.com" },    // invalid password
            { "smithmailinator.com", "smih@mailinator.com" }, // invalid email
            { "", "" },                                        // blank inputs
            { "smith@mailinator.com", "smith@mailinator.com" } // valid inputs
        };
    }

    @Test(dataProvider = "loginData")
    public void loginTest(String email, String password) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Find and clear the email input field
        WebElement emailInput = driver.findElement(By.xpath("//input[@id='exampleForm.ControlInput1']"));
        emailInput.clear(); // Clear any existing text
        emailInput.sendKeys(email);

        // Find and clear the password input field
        WebElement passwordInput = driver.findElement(By.xpath("//input[@id='exampleForm.ControlTextarea1']"));
        passwordInput.clear(); // Clear any existing text
        passwordInput.sendKeys(password);

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

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
