package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {
  private static final String
    TITLE = "org.wikipedia:id/view_page_title_text",
    FOOTER_ELEMENT="//*[@text='View page in browser']",
    OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
    OPTIONS_ADD_TO_MY_LIST_BUTTON = "//*[@text='Add to reading list']",
    ADD_TO_MY_LIST_OVERLAY = "org.wikipedia:id/onboarding_button",
    MY_LIST_NAME_INPUT = "text_input",
    MY_LIST_OK_BUTTON = "//*[@text='OK']",
    CLOSE_ARTICLE_TITLE = "//android.widget.ImageButton[@content-desc='Navigate up']",
    ARTICLE_SUBTITLE = "//*[@text='{SUBTITLE}']";

  public ArticlePageObject(AppiumDriver driver)
  {
    super(driver);
  }

  /* TEMPLATES METHODS */
  private static String getSubtitleElement(String substr) {
    return ARTICLE_SUBTITLE.replace("{SUBTITLE}", substr);
  }
  /* TEMPLATES METHODS */

  public WebElement waitForTitleElement()
  {
    return this.waitForElementPresent(By.id(TITLE),
            "Cannot find article title on page!",
            15);
  }

  public String getArticleTitle()
  {
    WebElement title_element = waitForTitleElement();
    return title_element.getAttribute("text");
  }

  public void swipeToFooter()
  {
   this.swipeUpToElement(
      By.xpath(FOOTER_ELEMENT),
     "Cannot find the of article",
           20);
  }

  public void addArticleToMyList(String name_of_folder)
  {
    this.waitForElementAndClick(
            By.xpath(OPTIONS_BUTTON),
            "Cannot find button to open article options",
            5);

    this.waitForElementAndClick(
            By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
            "Cannot find article to reading list",
            5);

    this.waitForElementAndClick(
            By.id(ADD_TO_MY_LIST_OVERLAY),
            "Cannot find 'Got it' tip overlay",
            5);

    this.waitForElementAndClear(
            By.id(MY_LIST_NAME_INPUT),
            "Cannot find input to set name of articles folder",
            5);

    this.waitForElementAndSendKeys(
            By.id(MY_LIST_NAME_INPUT),
            name_of_folder,
            "Cannot put text into articles folder",
            5);

    this.waitForElementAndClick(
            By.xpath(MY_LIST_OK_BUTTON),
            "Cannot press OK button",
            5);
  }

  public void closeArticle()
  {
    this.waitForElementAndClick(
            By.xpath(CLOSE_ARTICLE_TITLE),
            "Cannot close article, cannot find X link",
            5);
  }

  public void assertArticleTitlePresent () {
    this.assertElementPresent(
            TITLE,
            "Cannot find article title in the article page");
  }
}
