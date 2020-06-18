import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Created by Gaurav on 18/06/20.
 */
public class Main {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "chromedriver.mac");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=~/Library/Application Support/Google/Chrome/Default/");
        options.addArguments("--start-maximized");
        WebDriver driver = new ChromeDriver(options);
        FacebookClient facebookClient = new FacebookClient(driver);
        facebookClient.wishBirthday();
        driver.quit();
    }
}
