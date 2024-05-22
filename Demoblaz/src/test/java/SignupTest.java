import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class SignupTest extends BaseTest {
    @Test
    public void testSignup() {
        String newUserJson = "{ \"username\": \"AbdallaKhaled\", \"password\": \"testpassword123\" }";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(newUserJson)
                .post("/signup");

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertTrue(response.asString().contains("Sign up successful"));
    }

    @Test
    public void testSignupWithExistingUser() {
        String existingUserJson = "{ \"username\": \"existinguser\", \"password\": \"existingpassword\" }";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(existingUserJson)
                .post("/signup");

        Assert.assertEquals(response.statusCode(), 400);
        Assert.assertTrue(response.asString().contains("This user already exist."));
    }

}
