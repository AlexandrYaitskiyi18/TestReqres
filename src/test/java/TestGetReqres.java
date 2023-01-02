import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class TestGetReqres extends TestInit{

    @Test
    public void testGetRequest() {
        int i = 6;
        SPEC200();
        Response response = given()
                .when()
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

        SPEC200();
        ModelSingleUser modelSingleUser = given()
                .when()
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
    public void testListResourse() {
        SPEC200();
        List<ModelResourse> modelResourseList = given()
                .when()
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

        SPEC200();
        Response response = given()
                .when()
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
    public void testDelayedResponsed() {
        SPEC200();
        List<ModelUsers> modelUsersList = given()
                .when()
                .get("api/users?delay=3")
                .then().log().all()
                .extract().body().jsonPath().getList("data", ModelUsers.class);

        Assert.assertEquals(modelUsersList.size(), 6);
    }
}
