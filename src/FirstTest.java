import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
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
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description']//*[@text='Programming language']"),
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

  @Test
  public void testSwipeArticleTitle() {
    waitForElementAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' input",
            5);

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            "Appium",
            "Cannot find search input",
            5);

    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
            "Cannot find 'Appium' search in article",
            5);

    waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            15);

    swipeUpToElement(
            By.xpath("//*[@text='View page in browser']"),
            "Cannot find the end of the article",
            20
    );

  }

  @Test
  public void saveFirstArticleToMyList() {
    waitForElementAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' input",
            5);

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            "Appium",
            "Cannot find search input",
            5);

    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
            "Cannot find 'Appium' search in article",
            5);

    waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/view_page_subtitle_text'][@text='Add title description']"),
            "Cannot find article title",
            15);

    waitForElementAndClick(
            By.xpath("//android.widget.ImageView[@content-desc='More options']"),
            "Cannot find button to open article options",
            5);

    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/title'][@text='Add to reading list']"),
            "Cannot find article to reading list",
            5);

    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/onboarding_button'][@text='GOT IT']"),
            "Cannot find 'Got it' tip overlay",
            5);

    waitForElementAndClear(
            By.xpath("//*[@resource-id='org.wikipedia:id/text_input'][@text='My reading list']"),
            "Cannot find input to set name of articles folder",
            5);

    String name_of_folder = "Learning programming";

    waitForElementAndSendKeys(
            By.xpath("//*[@resource-id='org.wikipedia:id/text_input']"),
            name_of_folder,
            "Cannot put text into articles folder",
            5);

    waitForElementAndClick(
            By.xpath("//*[@text='OK']"),
            "Cannot press OK button",
            5);

    waitForElementAndClick(
            By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
            "Cannot close article, cannot find X link",
            5);

    waitForElementAndClick(
            By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
            "Cannot find navigation button to My list",
            5);

    waitForElementAndClick(
            By.xpath("//*[@text='" + name_of_folder + "']"),
            "Cannot find created folder",
            5);

    swipeElementToLeft(
            By.xpath("//*[@text='Appium']"),
            "Cannot find saved article in the created folder");

    waitForElementNotPresent(
            By.xpath("//*[@text='Appium']"),
            "Cannot delete saved article",
            5);
  }

  @Test
  public void testAmountOfNotEmptySearch() {
    waitForElementAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' input",
            5);

    String search_line = "Linkin Park Diskography";
    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            search_line,
            "Cannot find search input",
            5);

    String search_result_locator = "//*[resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
    waitForElementPresent(By.xpath(search_result_locator),
            "Cannot find anything by the request " + search_line,
            15);

    int amount_of_search_results = getAmountOfElements(
            By.xpath(search_result_locator)
    );

    assertTrue(
            "We found too few results",
            amount_of_search_results > 0);
  }

  @Test
  public void testAmountOfEmptySearch() {
    waitForElementAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' input",
            5);

    String search_line = "zxzxzxzxzx";
    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            search_line,
            "Cannot find search input",
            5);

    String search_result_locator = "//*[resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
    String empty_result_label = "//*[@text='No results found']";
    waitForElementPresent(
            By.xpath(empty_result_label),
            "Cannot find empty results label by the request " + search_line,
            15);

    assertElementNotPresent(
            By.xpath(search_result_locator),
            "We found some results by request " + search_line);
  }

  @Test
  public void testChangeScreenOrientionOnSearchResults() {
    waitForElementAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' input",
            5);

    String search_line = "Java";
    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            search_line,
            "Cannot find search input",
            5);

    waitForElementAndClick(
            By.xpath("//*[resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Java (programming language)']"),
            "Cannot find 'Object-oriented programming language' topic searching by " + search_line,
            15);

    String title_before_rotation = waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article",
            15);

    driver.rotate(ScreenOrientation.LANDSCAPE);

    String title_after_rotation = waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article",
            15);

    assertEquals(
            "Article title have been changed after screen rotation",
            title_before_rotation,
            title_after_rotation);

    driver.rotate(ScreenOrientation.PORTRAIT);

    String title_after_second_rotation = waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article",
            15);

    assertEquals(
            "Article title have been changed after screen rotation",
            title_before_rotation,
            title_after_second_rotation);

  }

  @Test
  public void testCheckSearchArticleInBackground()
  {
    waitForElementAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' input",
            5);

    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            "Java",
            "Cannot find search input",
            5);

    waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Java']"),
            "Cannot find 'Java' search in article",
            5);

    driver.runAppInBackground(2);

    waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Java']"),
            "Cannot find article after onResume",
            5);
  }

//Ex5: Тест: сохранение двух статей
  @Test
  public void saveTwoArticlesAndDeleteFirst() {
//Сохраняем первую статью
    String name_of_folder = "Myfolder";
    waitForElementAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' input",
            5);

    String first_search_line = "Iphone";
    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            first_search_line,
            "Cannot find search input",
            5);

    waitForElementAndClick(
            By.xpath("//*[resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Line of smartphones designed and marketed by Apple Inc.']"),
            "Cannot find 'Line of smartphones designed and marketed by Apple Inc.' topic searching by " + first_search_line,
            15);

    waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text'][@text='iPhone']"),
            "Cannot find article title",
            15);

    waitForElementAndClick(
            By.xpath("//android.widget.ImageView[@content-desc='More options']"),
            "Cannot find button to open article options",
            5);

    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/title'][@text='Add to reading list']"),
            "Cannot find article to reading list",
            5);

    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/onboarding_button'][@text='GOT IT']"),
            "Cannot find 'Got it' tip overlay",
            5);

    waitForElementAndClear(
            By.xpath("//*[@resource-id='org.wikipedia:id/text_input'][@text='My reading list']"),
            "Cannot find input to set name of articles folder",
            5);

    waitForElementAndSendKeys(
            By.xpath("//*[@resource-id='org.wikipedia:id/text_input']"),
            name_of_folder,
            "Cannot put text into articles folder",
            5);

    waitForElementAndClick(
            By.xpath("//*[@text='OK']"),
            "Cannot press OK button",
            5);

    waitForElementAndClick(
            By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
            "Cannot close article, cannot find X link",
            5);

    //Поиск и сохранение второй статьи
    waitForElementAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' input",
            5);

    String second_search_line = "Android";
    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            second_search_line,
            "Cannot find search input",
            5);

    waitForElementAndClick(
            By.xpath("//*[resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Open-source operating system for mobile devices created by Google]"),
            "Cannot find 'Line of smartphones designed and marketed by Apple Inc.' topic searching by " + second_search_line,
            15);

    waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text'][@text='Android']"),
            "Cannot find article title",
            15);

    waitForElementAndClick(
            By.xpath("//android.widget.ImageView[@content-desc='More options']"),
            "Cannot find button to open article options",
            5);

    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/title'][@text='Add to reading list']"),
            "Cannot find article to reading list",
            5);

    //выбор существующей папки из списка
    waitForElementAndClick(
            By.xpath("//*[@text='" + name_of_folder + "']"),
                    "Cannot find folder with title " + name_of_folder,
                    5);

    waitForElementAndClick(
            By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
            "Cannot close article, cannot find X link",
            5);

    waitForElementAndClick(
            By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
            "Cannot find navigation button to My list",
            5);

    waitForElementAndClick(
            By.xpath("//*[@text='" + name_of_folder + "']"),
            "Cannot find created folder",
            5);

    //2. Удаляет одну из статей
    swipeElementToLeft(
            By.xpath("//*[@text='Iphone']"),
            "Cannot find saved article in the created folder");

    //3. Убеждается, что вторая осталась
    waitForElementPresent(
            By.xpath("//*[@text='Android']"),
            "Android article don't deleted from folder",
            5);

    //4. Переходит в неё и убеждается, что title совпадает
    waitForElementAndClick(
            By.xpath("//*[@text='Android']"),
                    "Cannot find article in the created folder",
                    5);

    // проверка заголовка 2-ой статьи
    WebElement second_title_element = waitForElementPresent(
                    By.xpath("//*[@text='Android']"),
                    "Cannot find title of the remained article",
                    5);

    String article_title = second_title_element.getAttribute("text");

    assertEquals(
            "Title does not match with 'Android'",
            "Android",
            article_title);
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

  private void assertElementsListTitleHasText(By by, String error_message, long timeOutInSeconds) {
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

  protected void swipeUpQuick() {
    swipeUp(200);
  }

  protected void swipeUpToElement(By by, String error_message, int max_swipes) {
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

  protected void swipeElementToLeft(By by, String error_message) {
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

  private int getAmountOfElements(By by) {
    List elements = driver.findElements((by));
    return elements.size();
  }

  private void assertElementNotPresent(By by, String error_message) {
    int amount_of_elements = getAmountOfElements(by);
    if (amount_of_elements > 0) {
      String default_message = "An element '" + by.toString() + "' supposed to be not present";
      throw new AssertionError(default_message + " " + error_message);
    }
  }

  private String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeOutInSeconds)
  {
    WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
    return element.getAttribute(attribute);
  }
}
