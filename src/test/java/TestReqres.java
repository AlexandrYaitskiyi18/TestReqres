import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Clock;
import java.util.List;

import static io.restassured.RestAssured.given;

public class TestReqres {

    static final String URL = "https://reqres.in/";
    static final Integer STATUSOK = 200;
    static final Integer STATUSBAD = 404;

    public static void SPEC200() {
        Specifications.runSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecification200());
    }


    @Test
    public void testGetRequest() {
        int i = 6;
        Response response = given()
                .baseUri(URL)
                .when()
                .contentType(ContentType.JSON)
                .get("api/users?page=2")
                .then().log().all()
                .extract().response();
        List<ModelUsers> modelUsersList = response.jsonPath().getList("data", ModelUsers.class);

        Assert.assertEquals(modelUsersList.size(), i);
        Assert.assertEquals(response.statusCode(), STATUSOK);
    }

    @Test
    public void testSingleUser() {

        Integer id = 2;
        String email = "janet.weaver@reqres.in";
        String first_name = "Janet";
        String last_name = "Weaver";
        String avatar = "https://reqres.in/img/faces/2-image.jpg";

        ModelSingleUser modelSingleUser = given()
                .baseUri(URL)
                .when()
                .contentType(ContentType.JSON)
                .get("api/users/2")
                .then().log().all()
                .extract().response().jsonPath().getObject("data", ModelSingleUser.class);


        Assert.assertEquals(modelSingleUser.id, id);
        Assert.assertEquals(modelSingleUser.email, email);
        Assert.assertEquals(modelSingleUser.first_name, first_name);
        Assert.assertEquals(modelSingleUser.last_name, last_name);
        Assert.assertEquals(modelSingleUser.avatar, avatar);


    }

    @Test
    public void testSingleUser1() {

        Response response = given()
                .baseUri(URL)
                .when()
                .contentType(ContentType.JSON)
                .get("api/users/23")
                .then().log().all()
                .extract().response();

        Assert.assertEquals(response.statusCode(), STATUSBAD);


    }

    @Test
    public void testListResourse() {
        List<ModelResourse> modelResourseList = given()
                .baseUri(URL)
                .when()
                .contentType(ContentType.JSON)
                .get("/api/unknown")
                .then().log().all()
                .extract().body().jsonPath().getList("data", ModelResourse.class);

        Assert.assertEquals(modelResourseList.size(), 6);
    }

    @Test
    public void testSingleResourse() {

        Integer id = 2;
        String name = "fuchsia rose";
        Integer year = 2001;
        String color = "#C74375";
        String pantone_value = "17-2031";

        Response response = given()
                .baseUri(URL)
                .when()
                .contentType(ContentType.JSON)
                .get("/api/unknown/2")
                .then().log().all()
                .extract().response();
        ModelResourse modelResourse = response.body().jsonPath().getObject("data", ModelResourse.class);

        Assert.assertEquals(response.statusCode(), STATUSOK);
        Assert.assertEquals(modelResourse.id, id);
        Assert.assertEquals(modelResourse.name, name);
        Assert.assertEquals(modelResourse.year, year);
        Assert.assertEquals(modelResourse.color, color);
        Assert.assertEquals(modelResourse.pantone_value, pantone_value);

    }

    @Test
    public void testSingleResourse1() {

        Integer statusCode = given()
                .baseUri(URL)
                .when()
                .contentType(ContentType.JSON)
                .get("api/unknown/23")
                .then().extract().statusCode();

        Assert.assertEquals(statusCode.intValue(), STATUSBAD);
    }

    @Test
    public void testCreate() {

        ModelCREATE modelCREATE = new ModelCREATE("morpheus", "leader");
        ModelCreateResponse modelCreateResponse = given()
                .baseUri(URL)
                .body(modelCREATE)
                .when()
                .contentType(ContentType.JSON)
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

        Specifications.runSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecification200());
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
        Specifications.runSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecification204());
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
        Specifications.runSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecification200());
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
    public void testRgistration400() {
        String error = "Missing password";
        ModelRegistrationRequest modelRegistrationRequest = new ModelRegistrationRequest("sydney@fife");
        Specifications.runSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecification400());
        ModelRegistrationResponse modelRegistrationResponse = given()
                .body(modelRegistrationRequest)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().response().as(ModelRegistrationResponse.class);

        Assert.assertEquals(modelRegistrationResponse.getError(), error);


    }

    @Test
    public void testLogin200() {
        String token = "QpwL5tke4Pnpja7X4";
        ModelRegistrationRequest modelRegistrationRequest = new ModelRegistrationRequest("eve.holt@reqres.in", "cityslicka");
        Specifications.runSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecification200());
        ModelRegistrationResponse modelRegistrationResponse = given()
                .body(modelRegistrationRequest)
                .when()
                .post("/api/login")
                .then().log().all()
                .extract().response().as(ModelRegistrationResponse.class);

        Assert.assertEquals(modelRegistrationResponse.getToken(), token);
    }

    @Test
    public void testLogin400() {
        String error = "Missing password";
        ModelRegistrationRequest modelRegistrationRequest = new ModelRegistrationRequest("peter@klaven");
        Specifications.runSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecification400());
        ModelRegistrationResponse modelRegistrationResponse = given()
                .body(modelRegistrationRequest)
                .when()
                .post("/api/login")
                .then().log().all()
                .extract().response().as(ModelRegistrationResponse.class);

        Assert.assertEquals(modelRegistrationResponse.getError(), error);

    }


    @Test
    public void testDelayedResponsed(){
        SPEC200(); //Specifications.runSpecification(Specifications.requestSpecification(URL), Specifications.responseSpecification200());
        List<ModelUsers> modelUsersList = given()
                .when()
                .get("api/users?delay=3")
                .then().log().all()
                .extract().body().jsonPath().getList("data", ModelUsers.class);

        Assert.assertEquals(modelUsersList.size(),6);
    }



}