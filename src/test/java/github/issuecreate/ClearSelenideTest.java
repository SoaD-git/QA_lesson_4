package github.issuecreate;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class ClearSelenideTest {

    Credentials creds = new Credentials();

    @Test
    public void githubCreateIssueTest() {

        open("https://github.com");

        $("[href='/login']").click();
        $("#login_field").val(creds.getUser());
        $("#password").val(creds.getPassword());
        $("[name='commit']").click();


        $(".header-search-input").click();
        $(".header-search-input").val("SoaD-git/QA_lesson_4");
        $(".header-search-input").submit();



    }

}
