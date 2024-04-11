package practicum;

import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import practicum.api.mainStellarBurgersApi;
import practicum.request.authorizationReq;
import practicum.request.userReq;
import practicum.pages.LoginPage;
import practicum.pages.MainPage;
import practicum.pages.RegisterPage;

import static practicum.constants.stringResp.INVALID_PASS;
import static practicum.constants.stringResp.SUC_REG_FAIL;

public class RegistrationTest {

    @Rule
    public DriverRule driverRule = new DriverRule();
    private mainStellarBurgersApi api;
    private userReq userReq;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegisterPage registerPage;

    @Before
    public void setUp() {
        WebDriver webDriver = driverRule.getWebDriver();

        mainPage = new MainPage(webDriver);
        loginPage = new LoginPage(webDriver);
        registerPage = new RegisterPage(webDriver);

        api = new mainStellarBurgersApi();
        userReq = StepsForTest.createRandomUser();
    }

    @Test
    @DisplayName("Проверка успешной регистрации")
    public void testSuccessfulRegistration() {
        mainPage.open();
        mainPage.waitForLoad();
        mainPage.clickEnterAccountButton();

        loginPage.clickRegisterLink();
        registerPage.waitForLoad();
        registerPage.enterNewAccountData(
                userReq.getName(),
                userReq.getEmail(),
                userReq.getPassword()
        );
        registerPage.clickRegisterButton();
        loginPage.waitForLoad();
        loginPage.enterLoginData(userReq.getEmail(), userReq.getPassword());
        loginPage.clickEnterButton();
        mainPage.waitForLoad();

        Assert.assertTrue(SUC_REG_FAIL, mainPage.isOrderButtonVisible());
    }

    @Test
    @DisplayName("Проверка регистрации с невалидным паролем")
    public void testRegistrationWithInvalidPassword() {
        api.createUser(userReq);
        mainPage.open();
        mainPage.waitForLoad();
        mainPage.clickEnterAccountButton();

        loginPage.clickRegisterLink();
        registerPage.waitForLoad();
        int maxInvalidPasswordLength = 5;
        registerPage.enterNewAccountData(
                userReq.getName(),
                userReq.getEmail(),
                StepsForTest.randomPassword(maxInvalidPasswordLength)
        );
        registerPage.clickRegisterButton();
        Assert.assertTrue(INVALID_PASS,
                registerPage.isIncorrectPasswordLabelVisible());
    }

    @After
    public void tearDown() {
        authorizationReq authorizationReq = new authorizationReq(
                userReq.getEmail(),
                userReq.getPassword());
        String accessToken = api.loginUser(authorizationReq);
        api.deleteUser(accessToken);
    }
}
