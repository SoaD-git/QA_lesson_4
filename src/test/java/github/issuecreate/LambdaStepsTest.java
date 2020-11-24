package github.issuecreate;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class LambdaStepsTest {

    Credentials Cred = new Credentials();
    String IssueName = "TestIssue";
    String Assign = "SoaD-git";
    String Label1 = "bug";
    String Repository = "SoaD-git/QA_lesson_4";

    @Test
    public void githubCreateIssueTest() {
        step("Открываем главную стрницу", () -> open("https://github.com/"));

        step("Логинимся под пользователем", () -> {
            $("[href='/login']").click();
            $("#login_field").val(Cred.getUser());
            $("#password").val(Cred.getPassword());
            $("[name='commit']").click();
        });

        step("Находим нужный репозиторий", () -> {
            $(".header-search-input").click();
            $(".header-search-input").val(Repository);
            $(".header-search-input").submit();
        });

        step("Находим кнопку создания новой Issue", () -> {
            $(By.linkText(Repository)).click();
            $("[aria-label=\"Create new…\"]").click();
            $("[data-ga-click=\"Header, create new issue\"]").click();
        });

        step("Заполняем необходимые поля и создаем Issue", () -> {
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
        });

        step("Проверяем наличие Issue", () -> {
            open("https://github.com/SoaD-git/QA_lesson_4/issues");
            $(withText(IssueName)).shouldHave(Condition.exist);
        });

        step("Удаляем созданную Issue", () -> {
            open("https://github.com/SoaD-git/QA_lesson_4/issues");
            $(byText(IssueName)).click();
            $(byText("Delete issue")).click();
            $("[name='verify_delete']").click();
        });
    }
}
