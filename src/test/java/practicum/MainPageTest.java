package practicum;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import practicum.pages.MainPage;

import static practicum.constants.stringResp.*;

public class MainPageTest {

    @Rule
    public DriverRule driverRule = new DriverRule();
    private MainPage mainPage;

    @Before
    public void setUp() {
        WebDriver webDriver = driverRule.getWebDriver();
        mainPage = new MainPage(webDriver);
    }

    @Test
    @DisplayName("Проверка кнопки - соусы")
    public void testSauceSectionButton() {
        mainPage.open();
        mainPage.waitForLoad();

        mainPage.clickSauceButton();
        Assert.assertTrue(SAUCE_BUT_NOT_WORK, mainPage.isSauceSectionSelected());
    }

    @Test
    @DisplayName("Проверка кнопки - булки")
    public void testBunSectionButton() {
        mainPage.open();
        mainPage.waitForLoad();

        mainPage.clickSauceButton();
        mainPage.clickBunButton();
        Assert.assertTrue(BUN_BUT_NOT_WORK, mainPage.isBunSectionSelected());
    }

    @Test
    @DisplayName("Проверка кнопки - начинки")
    public void testFillingSectionButton() {
        mainPage.open();
        mainPage.waitForLoad();

        mainPage.clickFillingButton();
        Assert.assertTrue(FILL_BUT_NOT_WORK, mainPage.isFillingSectionSelected());
    }
}
