import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class LoginTest extends BaseTest {
    @Test
    public void testLogin() {
        String validUserJson = "{ \"username\": \"AbdallaKhaled\", \"password\": \"testpassword123\" }";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(validUserJson)
                .post("/login");

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertTrue(response.asString().contains("Login successful"));
    }
    @Test
    public void testLoginWithInvalidCredentials() {
        String invalidUserJson = "{ \"username\": \"invaliduser\", \"password\": \"invalidpassword\" }";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(invalidUserJson)
                .post("/login");

        Assert.assertEquals(response.statusCode(), 401);
        Assert.assertTrue(response.asString().contains("User does not exist."));
    }

}
