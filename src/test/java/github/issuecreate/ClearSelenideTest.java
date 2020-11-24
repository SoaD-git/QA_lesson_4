package github.issuecreate;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ClearSelenideTest {

    Credentials creds = new Credentials();
    String IssueName = "TestIssue";
    String Assign = "SoaD-git";
    String Label1 = "bug";


    @Test
    public void githubCreateIssueTest() {

        open("https://github.com/");

        $("[href='/login']").click();
        $("#login_field").val(creds.getUser());
        $("#password").val(creds.getPassword());
        $("[name='commit']").click();


        $(".header-search-input").click();
        $(".header-search-input").val("/QA_lesson_4");
        $(".header-search-input").submit();

        $(By.linkText("SoaD-git/QA_lesson_4")).click();
        $("[aria-label=\"Create new…\"]").click();
        $("[data-ga-click=\"Header, create new issue\"]").click();

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

        open("https://github.com/SoaD-git/QA_lesson_4/issues");
        $(withText(IssueName)).shouldHave(Condition.exist);

    }

}
