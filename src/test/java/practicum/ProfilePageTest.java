package practicum;

import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import practicum.api.mainStellarBurgersApi;
import practicum.request.userReq;
import practicum.pages.LoginPage;
import practicum.pages.MainPage;
import practicum.pages.ProfilePage;

import static practicum.constants.stringResp.*;

public class ProfilePageTest {

    @Rule
    public DriverRule driverRule = new DriverRule();
    private LoginPage loginPage;
    private MainPage mainPage;
    private ProfilePage profilePage;
    private mainStellarBurgersApi api;
    private userReq userReq;
    private String accessToken;

    @Before
    public void setUp() {
        WebDriver webDriver = driverRule.getWebDriver();
        loginPage = new LoginPage(webDriver);
        mainPage = new MainPage(webDriver);
        profilePage = new ProfilePage(webDriver);
        api = new mainStellarBurgersApi();
        userReq = StepsForTest.createRandomUser();
        accessToken = api.createUser(userReq);
    }

    @Test
    @DisplayName("Проверка кнопки - лк")
    public void testProfileButton() {
        mainPage.open();
        mainPage.waitForLoad();
        mainPage.clickEnterProfileLink();
        loginPage.waitForLoad();

        Assert.assertTrue(PROFILE_LNK_NOT_WORK, loginPage.isEnterHeaderVisible());
    }

    @Test
    @DisplayName("")
    public void testLogoButton() throws InterruptedException {
        mainPage.open();
        mainPage.waitForLoad();
        mainPage.clickEnterProfileLink();
        loginPage.waitForLoad();
        Thread.sleep(10000);
        loginPage.clickLogoButton();
        mainPage.waitForLoad();
        Assert.assertTrue(LOGO_BUT_NOT_WORK, mainPage.isCreateOrderHeaderVisible());
    }

    @Test
    @DisplayName("Проверка кнопки - Конструктор")
    public void testConstructorButton() {
        mainPage.open();
        mainPage.waitForLoad();
        mainPage.clickEnterProfileLink();
        loginPage.waitForLoad();

        loginPage.clickConstructorButton();
        mainPage.waitForLoad();
        Assert.assertTrue(COSTR_BUT_NOT_WORK, mainPage.isCreateOrderHeaderVisible());
    }

    @Test
    @DisplayName("Проверка кнопки - Выход")
    public void testLogoutButton() {
        mainPage.open();
        mainPage.waitForLoad();
        mainPage.clickEnterAccountButton();
        loginPage.waitForLoad();
        loginPage.enterLoginData(userReq.getEmail(), userReq.getPassword());
        loginPage.clickEnterButton();
        mainPage.waitForLoad();
        mainPage.isCreateOrderHeaderVisible();
        mainPage.clickEnterProfileLink();
        profilePage.waitForLoad();
        profilePage.clickLogoutButton();

        loginPage.waitForLoad();
        Assert.assertTrue(LOGO_BUT_NOT_WORK, loginPage.isEnterHeaderVisible());
    }

    @After
    public void tearDown() {
        api.deleteUser(accessToken);
    }
}
