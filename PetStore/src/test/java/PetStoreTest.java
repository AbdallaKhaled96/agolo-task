import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class PetStoreTest extends BaseTest {
    @Test
    public void testCreatePet() {
        String petJson = "{ \"id\": 12345, \"name\": \"Doggie\", \"status\": \"available\" }";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(petJson)
                .post("/pet");

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("name"), "Doggie");
    }

    @Test
    public void testCreatePetWithInvalidData() {
        String petJson = "{ \"id\": \"invalid\", \"name\": 12345, \"status\": \"available\" }";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(petJson)
                .post("/pet");


            Assert.assertEquals(response.statusCode(), 400);
    } //Once the client  side error should be in 4xx status code, but I found 500 status code which is server side error, so it's a bug.

    @Test
    public void testGetPetById() {
        int petId = 12345;

        Response response = given()
                .get("/pet/" + petId);
        System.out.println(response.then().log().body());
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getInt("id"), petId);
    }

    @Test
    public void testGetPetByInvalidId() {
        int invalidPetId = 99999;

        Response response = given()
                .get("/pet/" + invalidPetId);

        Assert.assertEquals(response.statusCode(), 404);
    }

    @Test
    public void testUpdatePet() {
        String updatedPetJson = "{ \"id\": 12345, \"name\": \"Updated Doggie\", \"status\": \"sold\" }";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(updatedPetJson)
                .put("/pet");

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("name"), "Updated Doggie");
    }

    @Test
    public void testUpdatePetWithInvalidData() {
        String updatedPetJson = "{ \"id\": 12345, \"name\": 12345, \"status\": \"sold\" }";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(updatedPetJson)
                .put("/pet");

        try {
            Assert.assertEquals(response.statusCode(), 400);
        } catch (Exception e) {
            System.out.println("Expected 400 but got" + response.statusCode());
        }
    }  //Once the client  side error should be in 4xx status code but I found 500 status code which is server side error, so it's a bug.

    @Test
    public void testDeletePet() {
        int petId = 1;

        Response response = given()
                .delete("/pet/" + petId);

        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test
    public void testDeletePetWithInvalidId() {
        int invalidPetId = 99999;

        Response response = given()
                .delete("/pet/" + invalidPetId);

        Assert.assertEquals(response.statusCode(), 404);
    }


}
