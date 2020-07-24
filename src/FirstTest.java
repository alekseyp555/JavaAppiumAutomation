import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FirstTest {

  private AppiumDriver driver;

  @Before
  public void setUp() throws Exception {
    DesiredCapabilities capabilities = new DesiredCapabilities();

    capabilities.setCapability("platformName", "Android");
    capabilities.setCapability("deviceName", "AndroidTestDevice");
    capabilities.setCapability("platformVersion", "8.0");
    capabilities.setCapability("automatationName", "Appium");
    capabilities.setCapability("appPackage", "org.wikipedia");
    capabilities.setCapability("appActivity", ".main.MainActivity");
    capabilities.setCapability("app", "");
    capabilities.setCapability("app", "/Users/Admin/projects/JavaAppiumAutomation/apks/org.wikipedia.apk");

    driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
  }

  @After
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void firstTest() {
    waitForElementAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find search wikipedia input",
            5);

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            "Java",
            "Cannot find search input",
            5);


    waitForElementPresent(
            By.xpath("//*[@resource-id=org.wikipedia:id/page_list_item_description']//*[@text='Programming language']"),
            "Cannot find 'Programming language' topic searching by 'Java",
            15);
  }

  @Test
  public void testCancelSearch() {
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' input",
            5);

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            "Java",
            "Cannot find search input",
            5);

    waitForElementAndClear(
            By.id("org.wikipedia:id/search_src_text"),
            "Cannot find search field",
            5);

    waitForElementAndClick(
            By.id("org.wikipedia:id/search_close_btn"),
            "Cannot find X to cancel search",
            5);


    waitForElementNotPresent(
            By.id("org.wikipedia:id/search_close_btn"),
            "X is still present on the page",
            5);
  }

  @Test
  public void testCompareArticleTitle() {
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' input",
            5);

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            "Java",
            "Cannot find search input",
            5);

    waitForElementAndClick(
            By.xpath("//*[@resource-id=org.wikipedia:id/page_list_item_description']//*[@text='Programming language']"),
            "Cannot find 'Search Wikipedia' input",
            5);

    WebElement title_element = waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            15);

    String article_title = title_element.getAttribute("text");
    assertEquals(
            "We see unexpected title!",
            "Java (programming language)",
            article_title);
  }


  //Ex2: Создание метода
  @Test
  public void testCompareElementText() {
    assertElementHasText(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Search Wikipedia",
            "We see unexpected element text!"
    );
  }

  //Ex3: Тест: отмена поиска
  @Test
  public void testCancelSearchArticle() {
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' input",
            5);

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            "Oracle",
            "Cannot find search input",
            5);

    waitForElementPresent(
            By.xpath("//*[@resource-id=org.wikipedia:id/page_list_item_description']//*[@text='American multinational computer technology corporation']"),
            "Cannot find 'American multinational computer technology corporation' topic searching by 'Oracle",
            15);

    waitForElementPresent(
            By.xpath("//*[@resource-id=org.wikipedia:id/page_list_item_description']//*[@text='Database software']"),
            "Cannot find 'Database software' topic searching by 'Oracle",
            15);

    waitForElementAndClick(
            By.id("org.wikipedia:id/search_close_btn"),
            "Cannot find X to cancel search",
            5);

    waitForElementNotPresent(
            By.id("org.wikipedia:id/search_close_btn"),
            "X is still present on the page",
            5);

    waitForElementNotPresent(
            By.xpath("//*[@resource-id=org.wikipedia:id/page_list_item_description']//*[@text='American multinational computer technology corporation']"),
            "Cannot find 'American multinational computer technology corporation' topic searching by 'Oracle",
            15);

    waitForElementNotPresent(
            By.xpath("//*[@resource-id=org.wikipedia:id/page_list_item_description']//*[@text='Database software']"),
            "Cannot find 'Database software' topic searching by 'Oracle",
            15);
  }

  //Ex4*: Тест: проверка слов в поиске
  @Test
  public void testCheckTitleSearchResults() {
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_container"),
            "Cannot find 'Search Wikipedia' input",
            5);

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            "Java",
            "Cannot find search input",
            5);

    assertElementsListTitleHasText(
            By.id("org.wikipedia:id/page_list_item_container"),
            "Java text not found",
            15);
  }


  private WebElement waitForElementPresent(By by, String error_message, long timeOutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
    wait.withMessage(error_message + "\n");
    return wait.until(
            ExpectedConditions.presenceOfElementLocated(by));
  }

  private WebElement waitForElementPresent(By by, String error_message) {
    return waitForElementPresent(by, error_message, 5);
  }

  private WebElement waitForElementAndClick(By by, String error_message, long timeOutInSeconds) {
    WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
    element.click();
    return element;
  }

  private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeOutInSeconds) {
    WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
    element.sendKeys(value);
    return element;
  }

  private boolean waitForElementNotPresent(By by, String error_message, long timeOutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
    wait.withMessage(error_message + "\n");
    return wait.until(
            ExpectedConditions.invisibilityOfElementLocated(by));
  }

  private WebElement waitForElementAndClear(By by, String error_message, long timeOutInSeconds) {
    WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
    element.clear();
    return element;
  }

  private void assertElementHasText(By by, String expected_text, String error_message) {
    String expected_element_text = expected_text;
    WebElement element = waitForElementPresent(by, error_message);
    String actual_element_text = element.getAttribute("text");
    assertEquals(
            error_message,
            expected_element_text,
            actual_element_text
    );
  }

  private void assertElementsListTitleHasText(By by, String error_message, long timeOutInSeconds)
  {
    List<WebElement> searchResult = driver.findElements(by);

    for (WebElement currentSearchResult : searchResult)
    {
      String actual = currentSearchResult.getAttribute("text");
      String expected = "Java";
      assertTrue("Search result doesn't contain 'Java' word", actual.contains(expected));
    }
  }
}
