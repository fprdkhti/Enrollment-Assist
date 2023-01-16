package com.example.UntitledTestSuite;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.apache.commons.io.FileUtils;
import java.io.File;

public class UntitledTestCase {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  JavascriptExecutor js;
  @Before
  public void setUp() throws Exception {
    System.setProperty("webdriver.chrome.driver", "D:\term9\Test\projects\6");
    driver = new ChromeDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
    js = (JavascriptExecutor) driver;
  }

  @Test
  public void testUntitledTestCase() throws Exception {
    driver.get("http://localhost:8080/swagger-ui/index.html#/");
    driver.findElement(By.xpath("//h4[@id='operations-tag-user-controller']/small")).click();
    driver.findElement(By.xpath("//div[@id='operations-user-controller-addOneUsingPOST_3']/div/div")).click();
    driver.findElement(By.xpath("//div[@id='operations-user-controller-addOneUsingPOST_3']/div[2]/div/div/div/div[2]/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-user-controller-addOneUsingPOST_3']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/div/label/div/select")).click();
    driver.findElement(By.xpath("//div[@id='operations-user-controller-addOneUsingPOST_3']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/div/label/div/select")).click();
    driver.findElement(By.xpath("//div[@id='operations-user-controller-addOneUsingPOST_3']/div[2]/div/div[2]/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-user-controller-addOneUsingPOST_3']/div")).click();
    driver.findElement(By.xpath("//h4[@id='operations-tag-student-controller']/small/div/p")).click();
    driver.findElement(By.xpath("//div[@id='operations-student-controller-addStudentUsingPOST']/div/div")).click();
    driver.findElement(By.xpath("//div[@id='operations-student-controller-addStudentUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/div[2]/label/div/select")).click();
    new Select(driver.findElement(By.xpath("//div[@id='operations-student-controller-addStudentUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/div[2]/label/div/select"))).selectByVisibleText("application/json");
    driver.findElement(By.xpath("//div[@id='operations-student-controller-addStudentUsingPOST']/div[2]/div/div/div/div[2]/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-student-controller-addStudentUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/textarea")).click();
    driver.findElement(By.xpath("//div[@id='operations-student-controller-addStudentUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/textarea")).click();
    //ERROR: Caught exception [ERROR: Unsupported command [doubleClick | xpath=//div[@id='operations-student-controller-addStudentUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/textarea | ]]
    driver.findElement(By.xpath("//div[@id='operations-student-controller-addStudentUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/textarea")).click();
    driver.findElement(By.xpath("//div[@id='operations-student-controller-addStudentUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/textarea")).click();
    //ERROR: Caught exception [ERROR: Unsupported command [doubleClick | xpath=//div[@id='operations-student-controller-addStudentUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/textarea | ]]
    driver.findElement(By.xpath("//div[@id='operations-student-controller-addStudentUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/textarea")).clear();
    driver.findElement(By.xpath("//div[@id='operations-student-controller-addStudentUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/textarea")).sendKeys("{\n  \"graduateLevel\": \"Masters\",\n  \"studentId\": 0,\n  \"studentNo\": {\n    \"number\": \"810197474\"\n  },\n  \"userId\": \"42\"\n}");
    driver.findElement(By.xpath("//div[@id='operations-student-controller-addStudentUsingPOST']/div[2]/div/div[2]/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-user-controller-addOneUsingPOST_3']/div/div")).click();
    driver.findElement(By.xpath("//div[@id='operations-student-controller-addStudentUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/textarea")).click();
    driver.findElement(By.xpath("//div[@id='operations-student-controller-addStudentUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/textarea")).click();
    driver.findElement(By.xpath("//div[@id='operations-student-controller-addStudentUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/textarea")).clear();
    driver.findElement(By.xpath("//div[@id='operations-student-controller-addStudentUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/textarea")).sendKeys("{\n  \"graduateLevel\": \"Masters\",\n  \"studentId\": 0,\n  \"studentNo\": {\n    \"number\": \"810197474\"\n  },\n  \"userId\": \"string\"\n}");
    driver.findElement(By.xpath("//div[@id='operations-student-controller-addStudentUsingPOST']/div[2]/div/div[2]/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-student-controller-addStudentUsingPOST']/div")).click();
    driver.findElement(By.xpath("//h4[@id='operations-tag-enrollment-list-controller']/small")).click();
    driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addOneUsingPOST']/div")).click();
    driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addOneUsingPOST']/div[2]/div/div/div/div[2]/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addOneUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/div/label/div/select")).click();
    new Select(driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addOneUsingPOST']/div[2]/div/div/div[2]/div/table/tbody/tr/td[2]/div[2]/div/div/div/label/div/select"))).selectByVisibleText("application/json");
    driver.findElement(By.xpath("//input[@value='']")).click();
    driver.findElement(By.xpath("//input[@value='43']")).clear();
    driver.findElement(By.xpath("//input[@value='43']")).sendKeys("43");
    driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addOneUsingPOST']/div[2]/div/div[2]/button")).click();
    driver.findElement(By.xpath("//input[@value='43']")).click();
    driver.findElement(By.xpath("//input[@value='43']")).click();
    //ERROR: Caught exception [ERROR: Unsupported command [doubleClick | xpath=//input[@value='43'] | ]]
    driver.findElement(By.xpath("//input[@value='810197474']")).clear();
    driver.findElement(By.xpath("//input[@value='810197474']")).sendKeys("810197474");
    driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addOneUsingPOST']/div[2]/div/div[2]/button")).click();
    driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addOneUsingPOST']/div")).click();
    driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addSectionUsingPUT']/div")).click();
    driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addOneUsingPOST']/div/div")).click();
    driver.findElement(By.xpath("//input[@value='']")).click();
    driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addSectionUsingPUT']/div[2]/div/div/div/div[2]/button")).click();
    driver.findElement(By.xpath("//input[@value='']")).click();
    driver.findElement(By.xpath("//input[@value='44']")).clear();
    driver.findElement(By.xpath("//input[@value='44']")).sendKeys("44");
    driver.findElement(By.xpath("//input[@value='']")).click();
    driver.findElement(By.xpath("//input[@value='30']")).clear();
    driver.findElement(By.xpath("//input[@value='30']")).sendKeys("30");
    driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addSectionUsingPUT']/div[2]/div/div[2]/button")).click();
    driver.findElement(By.xpath("//input[@value='30']")).click();
    driver.findElement(By.xpath("//input[@value='31']")).clear();
    driver.findElement(By.xpath("//input[@value='31']")).sendKeys("31");
    driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addSectionUsingPUT']/div[2]/div/div[2]/button")).click();
    driver.findElement(By.xpath("//input[@value='31']")).click();
    driver.findElement(By.xpath("//input[@value='38']")).clear();
    driver.findElement(By.xpath("//input[@value='38']")).sendKeys("38");
    driver.findElement(By.xpath("//div[@id='operations-enrollment-list-controller-addSectionUsingPUT']/div[2]/div/div[2]/button")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
