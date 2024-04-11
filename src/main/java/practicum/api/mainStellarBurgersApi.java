package practicum.api;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import practicum.constants.apiPaths;
import practicum.request.authorizationReq;
import practicum.request.userReq;
import io.restassured.http.Header;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;

public class mainStellarBurgersApi {

    private final Header contentTypeHeader = new Header(CONTENT_TYPE, APPLICATION_JSON.getMimeType());

    public mainStellarBurgersApi() {
        RestAssured.baseURI = apiPaths.BASE_URL;
    }

    @Step("Создание пользователя")
    public String createUser(userReq userReq) {
        Response response = given()
                .header(contentTypeHeader)
                .body(userReq)
                .when()
                .post(apiPaths.CREATE_USER_PATH);

        return response.getBody().jsonPath().getString("accessToken");
    }

    @Step("Авторизация пользователя")
    public String loginUser(authorizationReq authorizationReq) {
        Response response = given()
                .header(contentTypeHeader)
                .body(authorizationReq)
                .when()
                .post(apiPaths.LOGIN_USER_PATH);
        return response.getBody().jsonPath().getString("accessToken");
    }

    @Step("Удаление пользователя")
    public void deleteUser(String accessToken) {
        Header authHeader = new Header(AUTHORIZATION, accessToken);
        given()
                .header(authHeader)
                .when()
                .delete(apiPaths.AUTH_USER_PATH);
    }
}
