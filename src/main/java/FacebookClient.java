import io.ddavison.conductor.Browser;
import io.ddavison.conductor.Config;
import io.ddavison.conductor.Locomotive;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Gaurav on 7/4/16.
 */

@Config(
        browser = Browser.CHROME,
        url = "https://m.facebook.com/"
)
public class FacebookClient extends Locomotive {



    public static void facebook(String[] args) {
        if (args == null || args.length != 2 || args[0] == null || args[0].trim().length() == 0 || args[1] == null || args[1].trim().length() == 0) {
            System.err.println("Invalid command. Please use the format:\nfbwisher <email> <password>");
            return;
        }
        FacebookClient facebook = new FacebookClient();
        try {
            facebook.login(args[0].trim(), args[1].trim());
            //facebook.wishBirthday();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            facebook.teardown();
        }
    }

    public void login(String email, String pass) {
        setText(By.id("m_login_email"), email);
        setText(By.id("m_login_password"), pass);
        click(By.name("login"));
        delay(500);
        waitForElement(By.id("MComposer"));
        delay(500);
    }

    public void sendMessage(String name, String message) {
        driver.navigate().to("https://www.facebook.com/messages/new");

        WebElement friendName = waitForElement(By.xpath("//input[@class='inputtext textInput']"));
        friendName.sendKeys(name);//Change it with your friend name
        friendName.sendKeys(Keys.ENTER);
        setText(By.className("uiTextareaAutogrow"), message);
        waitForElement(By.className("uiTextareaAutogrow")).sendKeys(Keys.ENTER);
    }

    public void wishBirthday() {
        try {
            driver.navigate().to("https://m.facebook.com/events/birthdays");
            WebElement birthdayContainer = waitForElement(By.xpath("//div[@title=\"Today's Birthdays\"]"));
            List<WebElement> birthdays = birthdayContainer.findElements(By.tagName("a"));
            Set<String> birthdayUrls = new HashSet<>();
            for (WebElement birthday : birthdays) {
                birthdayUrls.add(birthday.getAttribute("href"));
            }

            for (String friendUrl : birthdayUrls) {
                // sendMessage(new FacebookFriend(friendUrl, null), "Happy Birthday :)");
            }
        } catch (AssertionError e) {
            driver.close();
        }
    }

    public static void delay(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}