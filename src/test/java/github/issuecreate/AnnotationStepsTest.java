package github.issuecreate;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Step;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AnnotationStepsTest {

    private final static Credentials creds = new Credentials();
    private final static String IssueName = "TestIssue";
    private final static String Assign = "SoaD-git";
    private final static String Label1 = "bug";
    private final static String Repository = "SoaD-git/QA_lesson_4";

    @Test
    @DisplayName("Тест с аннотациями")
    @Feature("Создание задачи в репозитории " + Repository)
    @Link(url = "https://github.com/", name = "Тестинг")
    public void githubCreateIssueTest() {
        BaseSteps steps = new BaseSteps();
        steps.openMainPage();
        steps.userLogin();
        steps.findRepository();
        steps.findCreateNewIssueButton();
        steps.fillRequiredFieldsAndCreateIssue();
        steps.checkCreatedIssue();
        steps.deleteIssue();
    }

    public static class BaseSteps {

        @Step("Открываем главную страницу")
        public void openMainPage() {
            open("https://github.com/");
        }

        @Step("Логинимся под пользователем")
        public void userLogin() {
            $("[href='/login']").click();
            $("#login_field").val(creds.getUser());
            $("#password").val(creds.getPassword());
            $("[name='commit']").click();
        }

        @Step("Находим нужный репозиторий " + Repository)
        public void findRepository() {
            $(".header-search-input").click();
            $(".header-search-input").val(Repository);
            $(".header-search-input").submit();
        }

        @Step("Находим кнопку создания новой Issue")
        public void findCreateNewIssueButton() {
            $(By.linkText(Repository)).click();
            $("[aria-label=\"Create new…\"]").click();
            $("[data-ga-click=\"Header, create new issue\"]").click();
        }

        @Step("Заполняем необходимые поля и создаем Issue")
        public void fillRequiredFieldsAndCreateIssue() {
            $("#issue_title").val(IssueName);
            $(byText("Assignees")).click();
            $("#assignee-filter-field").val(Assign);
            $(".select-menu-item").$(byText(Assign)).click();
            $(".select-menu-item").sendKeys(Keys.ESCAPE);
            $(byText("Labels")).click();
            $("#label-filter-field").val(Label1);
            $(".label-select-menu-item").$(byText(Label1)).click();
            $(".label-select-menu-item").sendKeys(Keys.ESCAPE);
            $(byText("Submit new issue")).click();
        }

        @Step("Проверяем наличие Issue")
        public void checkCreatedIssue() {
            open("https://github.com/SoaD-git/QA_lesson_4/issues");
            $(withText(IssueName)).shouldHave(Condition.exist);
        }

        @Step("Удаляем созданную Issue")
        public void deleteIssue() {
            open("https://github.com/SoaD-git/QA_lesson_4/issues");
            $(byText(IssueName)).click();
            $(byText("Delete issue")).click();
            $("[name='verify_delete']").click();
        }
    }
}
