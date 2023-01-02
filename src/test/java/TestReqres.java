import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Clock;
import java.util.List;

import static io.restassured.RestAssured.given;

public class TestReqres extends TestInit {

    @Test
    public void testSingleUser404() {
        SPEC404();
        Response response = given()
                .when()
                .get("api/users/23")
                .then().log().all()
                .extract().response();

        Assert.assertEquals(response.statusCode(), STATUSBAD);
    }

    @Test
    public void testSingleResourse404() {
        SPEC404();
        Integer statusCode = given()
                .when()
                .get("api/unknown/23")
                .then().extract().statusCode();

        Assert.assertEquals(statusCode.intValue(), STATUSBAD);
    }

    @Test
    public void testRgistration400() {
        String error = "Missing password";
        ModelRegistrationRequest modelRegistrationRequest = new ModelRegistrationRequest("sydney@fife");
        SPEC400();
        ModelRegistrationResponse modelRegistrationResponse = given()
                .body(modelRegistrationRequest)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().response().as(ModelRegistrationResponse.class);

        Assert.assertEquals(modelRegistrationResponse.getError(), error);


    }
    @Test
    public void testLogin400() {
        String error = "Missing password";
        ModelRegistrationRequest modelRegistrationRequest = new ModelRegistrationRequest("peter@klaven");
        SPEC400();
        ModelRegistrationResponse modelRegistrationResponse = given()
                .body(modelRegistrationRequest)
                .when()
                .post("api/login")
                .then().log().all()
                .extract().response().as(ModelRegistrationResponse.class);

        Assert.assertEquals(modelRegistrationResponse.getError(), error);

    }
}