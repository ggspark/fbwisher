import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Created by Gaurav on 18/06/20.
 */
public class Main {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "chromedriver.mac"); //Set chrome driver for mac in project dir, you can download latest from https://sites.google.com/a/chromium.org/chromedriver/downloads
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=~/Library/Application Support/Google/Chrome/Default/"); //Setting this to use the same account again and again without login
        WebDriver driver = new ChromeDriver(options); //Create web driver
        FacebookClient facebookClient = new FacebookClient(driver); //Create fb client obj with driver
        facebookClient.wishBirthday(); //Wish birthday
        driver.quit(); //Quit chrome after work is done
    }
}
