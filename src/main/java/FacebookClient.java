import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by Gaurav on 18/06/20.
 */
public class FacebookClient {
    private static final int MAX_ATTEMPTS = 10; //Seconds
    private final WebDriver driver;

    public FacebookClient(WebDriver driver) {
        this.driver = driver;
    }

    public void wishBirthday() throws Exception {
        driver.get("https://m.facebook.com/events/birthdays");
        WebElement todaysBirthdays = waitForElement(By.xpath("//span[text()=\"Today's Birthdays\"]/parent::*/parent::*"));
        List<WebElement> birthdays = todaysBirthdays.findElements(By.tagName("a"));
        Set<String> birthdayUrls = new HashSet<>();
        for (WebElement birthday : birthdays) {
            birthdayUrls.add(birthday.getAttribute("href"));
        }
        for (String friendUrl : birthdayUrls) {
            System.out.println("Birthdays: " + friendUrl);
            try {
                String profileId = getFbId(friendUrl);
                sendMessage(profileId, "Happy Birthday :)");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getFbId(String friendUrl) throws Exception {
        driver.get(friendUrl);
        WebElement timeLineSection = waitForElement(By.id("m-timeline-cover-section"));
        List<WebElement> urlElements = timeLineSection.findElements(By.tagName("a"));
        Set<String> profileUrls = new HashSet<>();
        for (WebElement element : urlElements) {
            profileUrls.add(element.getAttribute("href"));
        }
        return Utils.getIdFromUrl(profileUrls);
    }

    public void sendMessage(String profileId, String message) throws Exception {
        if (profileId == null || profileId.isEmpty()) {
            return;
        }
        driver.get("https://m.facebook.com/messages/thread/" + profileId);
        WebElement inputBox = waitForElement(By.id("composerInput"));
        inputBox.clear();
        inputBox.sendKeys(message);
        inputBox.sendKeys(Keys.ENTER);
        WebElement sendButton = waitForElement(By.name("send"));
        sendButton.click();
    }


    /**
     * Method that acts as an arbiter of implicit timeouts of sorts
     */
    public WebElement waitForElement(By by) throws Exception {
        int attempts = 0;
        int size = driver.findElements(by).size();

        while (size == 0) {
            size = driver.findElements(by).size();
            if (attempts == MAX_ATTEMPTS) {
                throw new Exception("Could not find the element by: " + by.toString());
            }
            attempts++;
            try {
                Thread.sleep(1000); // sleep for 1 second.
            } catch (Exception x) {
                x.printStackTrace();
            }
        }

        if (size > 1) {
            System.out.println("WARN: There are more than 1 " + by.toString() + " 's!");
        }
        return driver.findElement(by);
    }

}