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

    /**
     * Try to wish birthday to all the friends who's birthday it is today
     */
    public void wishBirthday() {
        try {
            driver.get("https://m.facebook.com/events/birthdays");
            WebElement todaysBirthdays = waitForElement(By.xpath("//span[text()=\"Today's Birthdays\"]/parent::*/parent::*"));
            List<WebElement> birthdays = todaysBirthdays.findElements(By.tagName("a"));
            Set<String> birthdayUrls = new HashSet<>();
            for (WebElement birthday : birthdays) {
                birthdayUrls.add(birthday.getAttribute("href"));
            }
            for (String friendUrl : birthdayUrls) {
                System.out.println("Birthdays: " + friendUrl);
                sendMessageByUrl(friendUrl, "Happy Birthday :)");
            }
        } catch (Exception e) {
            System.out.println("Checking for login");
            allowLoginIfNeeded();
            e.printStackTrace();
        }
    }

    /**
     * Method that waits for 5 minutes if login is required
     */
    private void allowLoginIfNeeded() {
        try {
            driver.get("https://m.facebook.com");
            waitForElement(By.id("email_input_container"));
            System.out.println("Waiting for login. Please perform within 5 minutes");
            Utils.delay(5 * 60); //Wait for 5 minutes
        } catch (Exception e) {
            System.out.println("Login not required");
            e.printStackTrace();
        }
    }

    /**
     * Method to try to send {@code message} to friend by profile link
     *
     * @param friendUrl profile link of person to send the message to
     * @param message   message to be sent
     */
    public void sendMessageByUrl(String friendUrl, String message) {
        try {
            String profileId = getFbId(friendUrl);
            sendMessage(profileId, message);
        } catch (Exception e) {
            System.out.println("Couldn't send message to " + friendUrl);
            e.printStackTrace();
        }
    }

    /**
     * Gets real facebook id of profile from profile link
     *
     * @param friendUrl profile link of person to find the profile id
     * @return profile id if found
     * @throws Exception if profile id is not found
     */
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

    /**
     * Method to send {@code message} to {@code profileId}
     *
     * @param profileId profile id of person to send message to
     * @param message   message to be sent
     * @throws Exception if unable to send message
     */
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
        Utils.delay(5);//Wait 5 seconds for the message to go
    }

    /**
     * Method that acts as an arbiter of implicit timeouts of sorts. Performs {@code MAX_ATTEMPTS} attempts
     * after interval of 1 seconds
     *
     * @param by selector to find the element
     * @return WebElement if found
     * @throws Exception if WebElement not found
     */
    private WebElement waitForElement(By by) throws Exception {
        int attempts = 0;
        int size = driver.findElements(by).size();

        while (size == 0) {
            size = driver.findElements(by).size();
            if (attempts == MAX_ATTEMPTS) {
                throw new Exception("Could not find the element by: " + by.toString());
            }
            attempts++;
            Utils.delay(1);
        }

        if (size > 1) {
            System.out.println("WARN: There are more than 1 " + by.toString() + " 's!");
        }
        return driver.findElement(by);
    }


}