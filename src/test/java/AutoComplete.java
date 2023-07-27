import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AutoComplete {

    WebDriver driver;


    @BeforeTest
    public void setDriver(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://www.google.com/");
    }

    @Test
    public void allSuggestionsTest(){
        Cookie search = new Cookie("searchPreference", "automation");
        driver.manage().addCookie(search);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("history.go(0)");

        WebElement googleSearch = driver.findElement(By.name("q"));
        googleSearch.sendKeys(search.getValue());

        List<WebElement> suggestions = driver.findElements(By.cssSelector(".erkvQe"));
        while (suggestions.size() == 0) {
            suggestions = driver.findElements(By.cssSelector(".erkvQe"));
        }

        for (WebElement suggestion : suggestions){
            System.out.println(suggestion.getText());
        }
        suggestions.get(suggestions.size() - 1).click();


    }

    @AfterTest
    public void tearDown(){
        driver.close();
        driver.quit();
    }

}
