import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Main {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", Utils.findFile("chromedriver.mac"));
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=~/Library/Application Support/Google/Chrome/Default/");
        options.addArguments("--start-maximized");
        WebDriver driver = new ChromeDriver(options);
        wishBirthday(driver);
        driver.quit();
    }

    public static void wishBirthday(WebDriver driver) {
        driver.get("https://m.facebook.com/events/birthdays");
        WebElement todaysBirthdays = Utils.waitForElement(driver, By.xpath("//span[text()=\"Today's Birthdays\"]/parent::*/parent::*"));
        List<WebElement> birthdays = todaysBirthdays.findElements(By.tagName("a"));
        Set<String> birthdayUrls = new HashSet<>();
        for (WebElement birthday : birthdays) {
            birthdayUrls.add(birthday.getAttribute("href"));
        }
        for (String friendUrl : birthdayUrls) {
            System.out.println("Birthdays: " + friendUrl);
            String profileId = getFbId(driver, friendUrl);
            if (profileId != null && !profileId.isEmpty()) {
                sendMessage(driver, profileId, "Happy Birthday :)");
            }
        }
    }

    public static String getFbId(WebDriver driver, String friendUrl) {
        driver.get(friendUrl);
        WebElement timeLineSection = Utils.waitForElement(driver, By.id("m-timeline-cover-section"));
        List<WebElement> urlElements = timeLineSection.findElements(By.tagName("a"));
        Set<String> profileUrls = new HashSet<>();
        for (WebElement element : urlElements) {
            profileUrls.add(element.getAttribute("href"));
        }
        return Utils.getIdFromUrl(profileUrls);
    }

    public static void sendMessage(WebDriver driver, String profileId, String message) {
        driver.get("https://m.facebook.com/messages/thread/" + profileId);
        WebElement inputBox = Utils.waitForElement(driver, By.id("composerInput"));
        inputBox.clear();
        inputBox.sendKeys(message);
        inputBox.sendKeys(Keys.ENTER);
        WebElement sendButton = Utils.waitForElement(driver, By.name("send"));
        sendButton.click();
    }

}
