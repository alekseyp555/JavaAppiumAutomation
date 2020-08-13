package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.regex.Pattern;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MainPageObject {

  protected AppiumDriver driver;

  public MainPageObject(AppiumDriver driver)
  {
    this.driver = driver;
  }
    public WebElement waitForElementPresent(By by, String error_message, long timeOutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
    wait.withMessage(error_message + "\n");
    return wait.until(
            ExpectedConditions.presenceOfElementLocated(by));
  }

  public WebElement waitForElementPresent(By by, String error_message) {
    return waitForElementPresent(by, error_message, 5);
  }

  public WebElement waitForElementAndClick(By by, String error_message, long timeOutInSeconds) {
    WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
    element.click();
    return element;
  }

  public WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeOutInSeconds) {
    WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
    element.sendKeys(value);
    return element;
  }

  public boolean waitForElementNotPresent(By by, String error_message, long timeOutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
    wait.withMessage(error_message + "\n");
    return wait.until(
            ExpectedConditions.invisibilityOfElementLocated(by));
  }

  public WebElement waitForElementAndClear(By by, String error_message, long timeOutInSeconds) {
    WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
    element.clear();
    return element;
  }

    public void assertElementHasText(By by, String expected_text, String error_message) {
    String expected_element_text = expected_text;
    WebElement element = waitForElementPresent(by, error_message);
    String actual_element_text = element.getAttribute("text");
    assertEquals(
            error_message,
            expected_element_text,
            actual_element_text
    );
  }

    public void assertElementsListTitleHasText(By by, String error_message, long timeOutInSeconds) {
    List<WebElement> searchResult = driver.findElements(by);

    for (WebElement currentSearchResult : searchResult) {
      String actual = currentSearchResult.getAttribute("text");
      String expected = "Java";
      assertTrue("Search result doesn't contain 'Java' word", actual.contains(expected));
    }
  }

    protected void swipeUp(int timeOfSwipe) {
    TouchAction action = new TouchAction(driver);
    Dimension size = driver.manage().window().getSize();
    int x = size.width / 2;
    int start_y = (int) (size.height * 0.8);
    int end_y = (int) (size.height * 0.2);
    action
            .press(x, start_y)
            .waitAction(timeOfSwipe)
            .moveTo(x, end_y)
            .release()
            .perform();
  }

    public void swipeUpQuick() {
    swipeUp(200);
  }

    public void swipeUpToElement(By by, String error_message, int max_swipes) {
    int already_swiped = 0;
    while (driver.findElements(by).size() == 0) {
      if (already_swiped > max_swipes) {
        waitForElementPresent(by, "Cannot find element by swiping up. \n" + error_message, 0);
        return;
      }
      swipeUpQuick();
      ++already_swiped;
    }
  }

    public void swipeElementToLeft(By by, String error_message) {
    WebElement element = waitForElementPresent(
            by,
            error_message,
            10);
    int left_x = element.getLocation().getX();
    int right_x = left_x + element.getSize().getWidth();
    int upper_y = element.getLocation().getY();
    int lower_y = upper_y + element.getSize().getHeight();
    int middle_y = (upper_y + lower_y) / 2;

    TouchAction action = new TouchAction(driver);
    action
            .press(right_x, middle_y)
            .waitAction(300)
            .moveTo(left_x, middle_y)
            .release()
            .perform();
  }

    public int getAmountOfElements(By by) {
    List elements = driver.findElements((by));
    return elements.size();
  }

    public void assertElementNotPresent(By by, String error_message) {
    int amount_of_elements = getAmountOfElements(by);
    if (amount_of_elements > 0) {
      String default_message = "An element '" + by.toString() + "' supposed to be not present";
      throw new AssertionError(default_message + " " + error_message);
    }
  }

    //метод получения аттрибута
    public String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeOutInSeconds) {
    WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
    return element.getAttribute(attribute);
  }
    public void assertElementPresent(String locator, String error_message) {
    By by = this.getLocatorByString(locator);
    WebElement element = driver.findElement(by);

    assertTrue("Cannot find element with " + locator + error_message, element.isDisplayed());
  }

  private By getLocatorByString(String locator_with_type){
    String[] exploded_locator = locator_with_type.split(Pattern.quote(":"), 2);
    String by_type = exploded_locator[0];
    String locator = exploded_locator[1];

    switch (by_type) {
      case "xpath":
        return By.xpath(locator);
      case "id":
        return  By.id(locator);
      default:
        throw  new IllegalArgumentException("Cannot get type of loactor. Locator: " + locator_with_type);
     }
  }
}

