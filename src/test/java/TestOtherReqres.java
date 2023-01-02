import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Clock;

import static io.restassured.RestAssured.given;

public class TestOtherReqres extends TestInit {

    @Test
    public void testCreate() {
        SPEC201();
        ModelCREATE modelCREATE = new ModelCREATE("morpheus", "leader");
        ModelCreateResponse modelCreateResponse = given()
                .body(modelCREATE)
                .when()
                .post("api/users")
                .then().log().body()
                .extract().body().jsonPath().getObject(".", ModelCreateResponse.class);
        String rejex = "(.{8})$";
        String rejex1 = "(.{5})$";
        String currentTime = Clock.systemUTC().instant().toString().replaceAll(rejex, "");
        System.out.println(currentTime);
        System.out.println(modelCreateResponse.createdAt.replaceAll(rejex1, ""));

        Assert.assertEquals(currentTime, modelCreateResponse.createdAt.replaceAll(rejex1, ""));
        Assert.assertEquals(modelCreateResponse.name, "morpheus");
        Assert.assertEquals(modelCreateResponse.job, "leader");
    }

    @Test
    public void testPutReqres() {

        String name = "morpheus";
        String job = "zion resident";

        SPEC200();
        ModelCREATE modelCREATE = new ModelCREATE("morpheus", "zion resident");
        ModelCreateResponse modelCreateResponse = given()
                .body(modelCREATE)
                .when()
                .put("api/users/2")
                .then().log().all()
                .extract().as(ModelCreateResponse.class);
        String rejex = "(.{5})$";
        String rejex1 = "(.{8})$";
        String currenttime = Clock.systemUTC().instant().toString().replaceAll(rejex1, "");
        System.out.println(currenttime);
        System.out.println(modelCreateResponse.updatedAt.replaceAll(rejex, ""));
        Assert.assertEquals(modelCreateResponse.name, name);
        Assert.assertEquals(modelCreateResponse.job, job);
        Assert.assertEquals(currenttime, modelCreateResponse.updatedAt.replaceAll(rejex, ""));

    }

    @Test
    public void testDeleteReqres() {
        SPEC204();
        Response response = given()
                .when()
                .delete("/api/users/2")
                .then().log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), 204);
    }

    @Test
    public void testRegistration() {
        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";

        ModelRegistrationRequest modelRegistrationRequest = new ModelRegistrationRequest("eve.holt@reqres.in", "pistol");

        SPEC200();
        ModelRegistrationResponse modelRegistrationResponse = given()
                .body(modelRegistrationRequest)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().response().as(ModelRegistrationResponse.class);

        Assert.assertEquals(modelRegistrationResponse.getId(), id);
        Assert.assertEquals(modelRegistrationResponse.getToken(), token);
    }


    @Test
    public void testLogin200() {
        String token = "QpwL5tke4Pnpja7X4";
        ModelRegistrationRequest modelRegistrationRequest = new ModelRegistrationRequest("eve.holt@reqres.in", "cityslicka");
        SPEC200();
        ModelRegistrationResponse modelRegistrationResponse = given()
                .body(modelRegistrationRequest)
                .when()
                .post("/api/login")
                .then().log().all()
                .extract().response().as(ModelRegistrationResponse.class);

        Assert.assertEquals(modelRegistrationResponse.getToken(), token);
    }


}
