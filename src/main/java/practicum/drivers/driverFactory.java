package practicum.drivers;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class driverFactory {

    // Выбор драйвера для конкретного браузера по его названию
    @Step("Выбор драйвера в зависимости от входных параметров")
    public static WebDriver getDriver(String browserName){
        switch (browserName){
            case "chrome":
                // Добавление свойств браузера Google Chrome
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-fullscreen");
                options.addArguments("--incognito");
                return new ChromeDriver(options);
            case "firefox":
                // Добавление свойств браузера Firefox
                FirefoxOptions optionsF = new FirefoxOptions();
                optionsF.addArguments("--kiosk");
                optionsF.addArguments("-private");
                return new FirefoxDriver(optionsF);
            default:
                throw new RuntimeException("Введено некорректное название браузера");
        }
    }
}
