package tests;

import base.BaseApiTest;
import io.restassured.response.Response;
import methods.ApiTestMethods;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class ApiTestAutomation extends BaseApiTest {
    private final String baseUrl = "https://petstore.swagger.io/v2";
    private final int petId = 10;
    private ApiTestMethods methods;

    @BeforeClass
    public void setup() {
        methods = new ApiTestMethods();
    }

    private Map<String, Object> getHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        return headers;
    }

    @Test(priority = 1, description = "Create a new pet - Positive Scenario")
    public void createNewPet() {
        Map<String, Object> petPayload = new HashMap<>();
        petPayload.put("id", petId);
        petPayload.put("name", "Cash");

        Map<String, Object> category = new HashMap<>();
        category.put("id", 1);
        category.put("name", "Cat");
        petPayload.put("category", category);

        petPayload.put("status", "available");

        Response response = methods.postRequest(baseUrl + "/pet", petPayload, getHeaders());
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals("Cash", response.jsonPath().getString("name"));
        Assert.assertEquals("available", response.jsonPath().getString("status"));
    }

    @Test(priority = 2, description = "Get a pet by ID - Positive Scenario")
    public void getPetById() {
        Response response = methods.getRequest(baseUrl + "/pet/" + petId, getHeaders());
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals(petId, (int) response.jsonPath().getInt("id"));
        Assert.assertEquals("Cash", response.jsonPath().getString("name"));
    }

    @Test(priority = 3, description = "Find pets by status - Positive Scenario")
    public void findPetsByStatus() {
        Response response = methods.getRequest(baseUrl + "/pet/findByStatus?status=available", getHeaders());
        Assert.assertEquals(200, response.statusCode());
        Assert.assertTrue(response.jsonPath().getList("status").contains("available"));
    }

    @Test(priority = 4, description = "Update an existing pet - Positive Scenario")
    public void updatePet() {
        Map<String, Object> updatedPetPayload = new HashMap<>();
        updatedPetPayload.put("id", petId);
        updatedPetPayload.put("name", "CashUpdated");
        updatedPetPayload.put("status", "sold");

        Response response = methods.putRequest(baseUrl + "/pet", updatedPetPayload, getHeaders());
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals("CashUpdated", response.jsonPath().getString("name"));
        Assert.assertEquals("sold", response.jsonPath().getString("status"));
    }

    @Test(priority = 5, description = "Delete a pet - Positive Scenario")
    public void deletePet() {
        Response response = methods.deleteRequest(baseUrl + "/pet/" + petId, getHeaders());
        Assert.assertEquals(200, response.statusCode());
    }

    @Test(priority = 6, description = "Create pet with invalid data - Negative Scenario")
    public void createPetWithInvalidData() {
        Map<String, Object> invalidPayload = new HashMap<>();
        invalidPayload.put("id", "invalid");

        Response response = methods.postRequest(baseUrl + "/pet", invalidPayload, getHeaders());
        Assert.assertTrue(response.statusCode() == 400 || response.statusCode() == 500);
    }

    @Test(priority = 7, description = "Get non-existent pet - Negative Scenario")
    public void getNonExistentPet() {
        int nonExistentPetId = 999999;
        Response response = methods.getRequest(baseUrl + "/pet/" + nonExistentPetId, getHeaders());
        Assert.assertEquals(404, response.statusCode());
    }

    @Test(priority = 8, description = "Delete non-existent pet - Negative Scenario")
    public void deleteNonExistentPet() {
        int nonExistentPetId = 999999;
        Response response = methods.deleteRequest(baseUrl + "/pet/" + nonExistentPetId, getHeaders());
        Assert.assertTrue(response.statusCode() == 404 || response.statusCode() == 400);
    }
}
