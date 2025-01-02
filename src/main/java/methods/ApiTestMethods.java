package methods;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Map;

public class ApiTestMethods {
    public Response postRequest(String url,
                                Object requestBody,
                                Map<String, Object> headers)
    {
        return RestAssured.given()
                .headers(headers)
                .body(requestBody)
                .when()
                .post(url)
                .then()
                .extract()
                .response();
    }

    public Response getRequest(String url, Map<String, Object> headers) {
        return RestAssured.given()
                .headers(headers)
                .when()
                .get(url)
                .then()
                .extract()
                .response();
    }

    public Response putRequest(String url,
                               Object requestBody,
                               Map<String, Object> headers)
    {
        return RestAssured.given()
                .headers(headers)
                .body(requestBody)
                .when()
                .put(url)
                .then()
                .extract()
                .response();
    }

    public Response deleteRequest(String url,
                                  Map<String, Object> headers)
    {
        return RestAssured.given()
                .headers(headers)
                .when()
                .delete(url)
                .then()
                .extract()
                .response();
    }

}
