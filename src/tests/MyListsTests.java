package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {

  @Test
  public void testSaveFirstArticleToMyList() {
    SearchPageObject searchPageObject = new SearchPageObject(driver);
    searchPageObject.initSearchInput();
    searchPageObject.typeSearchLine("Java");
    searchPageObject.clickByArticleWithSubstring("Objected-oriented programming language");

    ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
    String article_title = ArticlePageObject.getArticleTitle();
    String name_of_folder = "Learning programming";

    ArticlePageObject.addArticleToMyList(name_of_folder);
    ArticlePageObject.closeArticle();

    NavigationUI NavigationUI = new NavigationUI(driver);
    NavigationUI.clickMyLists();

    MyListsPageObject MylistPageObject = new MyListsPageObject(driver);
    MylistPageObject.openFolderByName(name_of_folder);
    MylistPageObject.swipeByArticleToDelete(article_title);
  }

  //Refactoring Ex5: Тест: сохранение двух статей
  @Test
  public void saveTwoArticlesAndDeleteFirst() {
  //Сохраняем первую статью
    SearchPageObject SearchPageObject = new SearchPageObject(driver);
    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Iphone");
    SearchPageObject.clickByArticleWithSubstring("Iphone");

    ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
    ArticlePageObject.waitForTitleElement();

    String first_article_title = ArticlePageObject.getArticleTitle();
    String name_of_folder = "Myfolder";

    ArticlePageObject.addArticleToMyList(name_of_folder);
    ArticlePageObject.closeArticle();

//Поиск и сохранение второй статьи
    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Android");
    SearchPageObject.clickByArticleWithSubstring("Android");

    ArticlePageObject.waitForTitleElement();
    String second_article_title = ArticlePageObject.getArticleTitle();

    ArticlePageObject.addArticleToMyList(name_of_folder);
    ArticlePageObject.closeArticle();

    NavigationUI NavigationUI = new NavigationUI(driver);
    NavigationUI.clickMyLists();

    MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
    MyListsPageObject.openFolderByName(name_of_folder);
    //2. Удаляет одну из статей
    MyListsPageObject.swipeByArticleToDelete(second_article_title);
    //3. Убеждается, что вторая статья осталась
    MyListsPageObject.waitForArticleToAppearByTitle(first_article_title);
  }
}
