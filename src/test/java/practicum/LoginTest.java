package practicum;

import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import practicum.api.mainStellarBurgersApi;
import practicum.request.userReq;
import practicum.pages.ForgotPasswordPage;
import practicum.pages.LoginPage;
import practicum.pages.MainPage;
import practicum.pages.RegisterPage;

import static practicum.constants.stringResp.*;

public class LoginTest {

    @Rule
    public DriverRule driverRule = new DriverRule();
    private mainStellarBurgersApi api;
    private userReq userReq;
    private LoginPage loginPage;
    private MainPage mainPage;
    private RegisterPage registerPage;
    private ForgotPasswordPage forgotPasswordPage;
    private String accessToken;

    @Before
    public void setUp() {
        WebDriver webDriver = driverRule.getWebDriver();
        api = new mainStellarBurgersApi();
        userReq = StepsForTest.createRandomUser();
        loginPage = new LoginPage(webDriver);
        mainPage = new MainPage(webDriver);
        registerPage = new RegisterPage(webDriver);
        forgotPasswordPage = new ForgotPasswordPage(webDriver);
        accessToken = api.createUser(userReq);
    }

    @Test
    @DisplayName("Проверка регистрации. Аккаунт")
    public void testLoginByEnterAccountButton() {
        mainPage.open();
        mainPage.waitForLoad();
        mainPage.clickEnterAccountButton();
        loginPage.waitForLoad();
        loginPage.enterLoginData(userReq.getEmail(), userReq.getPassword());
        loginPage.clickEnterButton();
        mainPage.waitForLoad();

        Assert.assertTrue(LOG_BUT_FAIL, mainPage.isOrderButtonVisible());
    }

    @Test
    @DisplayName("Проверка регистрации. ЛК")
    public void testLoginByProfileLink() {
        mainPage.open();
        mainPage.waitForLoad();
        mainPage.clickEnterProfileLink();
        loginPage.waitForLoad();
        loginPage.enterLoginData(userReq.getEmail(), userReq.getPassword());
        loginPage.clickEnterButton();
        mainPage.waitForLoad();

        Assert.assertTrue(LOG_BUT_FAIL, mainPage.isOrderButtonVisible());
    }

    @Test
    @DisplayName("Проверка регистрации")
    public void testLoginOnRegistrationPage() {
        mainPage.open();
        mainPage.waitForLoad();
        mainPage.clickEnterAccountButton();
        loginPage.waitForLoad();
        loginPage.clickRegisterLink();
        registerPage.waitForLoad();
        registerPage.clickLoginLink();
        loginPage.waitForLoad();
        loginPage.enterLoginData(userReq.getEmail(), userReq.getPassword());
        loginPage.clickEnterButton();
        mainPage.waitForLoad();

        Assert.assertTrue(LOG_REG_FAIL, mainPage.isOrderButtonVisible());
    }

    @Test
    @DisplayName("Проверка регистрации через Восстановить пароль")
    public void testLoginOnPasswordRecoveryPage() {
        mainPage.open();
        mainPage.waitForLoad();
        mainPage.clickEnterAccountButton();
        loginPage.waitForLoad();
        loginPage.clickForgotPasswordLink();
        forgotPasswordPage.waitForLoad();
        forgotPasswordPage.clickEnterButton();
        loginPage.waitForLoad();
        loginPage.enterLoginData(userReq.getEmail(), userReq.getPassword());
        loginPage.clickEnterButton();
        mainPage.waitForLoad();

        Assert.assertTrue(LOG_PASS_FAIL, mainPage.isOrderButtonVisible());
    }

    @After
    public void tearDown() {
        api.deleteUser(accessToken);
    }
}
