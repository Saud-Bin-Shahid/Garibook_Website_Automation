import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Garibook {

    WebDriver driver;

    @BeforeAll
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @Test
    public void visitURL() {
        driver.get("http://fe.garibook.com/");
        driver.findElements(By.className("gb-primary-solid-btn")).get(0).click();
        driver.findElement(By.className("form-control")).sendKeys("01906429128");
        driver.findElements(By.className("theme-primary-btn")).get(1).click();

        // Wait for the alert to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement alertElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-info")));

        // Get the OTP text
        String alertText = alertElement.getText();
        System.out.println("Alert Text: " + alertText);

        // Extract 4-digit OTP using regex
        Pattern pattern = Pattern.compile("\\b\\d{4}\\b");
        Matcher matcher = pattern.matcher(alertText);

        if (matcher.find()) {
            String otp = matcher.group(); // Extract OTP
            System.out.println("Extracted OTP: " + otp);

            // Enter OTP into four separate input fields by CSS selectors
            driver.findElement(By.id("otp1")).sendKeys(String.valueOf(otp.charAt(0)));
            driver.findElement(By.id("otp2")).sendKeys(String.valueOf(otp.charAt(1)));
            driver.findElement(By.id("otp3")).sendKeys(String.valueOf(otp.charAt(2)));
            driver.findElement(By.id("otp4")).sendKeys(String.valueOf(otp.charAt(3)));

            System.out.println("OTP entered successfully.");
        } else {
            System.out.println("No OTP found in the alert message.");
        }
        driver.findElements(By.className("theme-primary-btn")).get(1).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        // Scroll down to bring the button into view
        WebElement buttonElement = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[1]/div[2]/section[2]/div/div/div/div/div[2]/form/div[9]/button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", buttonElement);


        driver.findElement(By.xpath("//*[@id=\"__next\"]/div[1]/div[2]/section[2]/div/div/div/div/div[2]/form/div[3]/div/input")).sendKeys("Dhaka University, Nilkhet Road, Dhaka, Bangladesh");
        driver.findElement(By.xpath("//*[@id=\"__next\"]/div[1]/div[2]/section[2]/div/div/div/div/div[2]/form/div[5]/div/input")).sendKeys("Rampura Bridge, Dhaka, Bangladesh");
        driver.findElement(By.xpath("//*[@id=\"__next\"]/div[1]/div[2]/section[2]/div/div/div/div/div[2]/form/div[6]/div/div[1]/div/input")).sendKeys("2025-02-26 2:30 PM");
        driver.findElement(By.xpath("//*[@id=\"__next\"]/div[1]/div[2]/section[2]/div/div/div/div/div[2]/form/div[7]/div/input")).sendKeys("Promo Code");
        driver.findElement(By.xpath("//*[@id=\"__next\"]/div[1]/div[2]/section[2]/div/div/div/div/div[2]/form/div[9]/button")).click();


    }
}
