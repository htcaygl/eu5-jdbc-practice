package Day8;

import Day6_POJO.Spartan;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

public class SpartanFlow {

    int id;

    @BeforeClass
    public void beforeclass(){
        baseURI= ConfigurationReader.get("spartan_api_url");
    }

    // Homework
    @Test(priority = 1)
    public void POSTNewSpartan(){

        Spartan spartanPost = new Spartan();

        spartanPost.setName("Rose");
        spartanPost.setGender("Female");
        spartanPost.setPhone(1478523690l);

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .body(spartanPost)
                .when()
                .post("/api/spartans");


        // after post method, response body will be shown and we need to take its id, data.id
        id=response.path("data.id");
        System.out.println("id = " + id);


        // verify post method is working properly.
        response.prettyPrint();
        assertEquals(response.statusCode(),201);
        assertEquals(response.path("success"), "A Spartan is Born!");


    }

    @Test(priority = 2)
    public void PUTExistingSpartan(){

        //create a map
        Map<String,Object> putSpartan= new HashMap<>();

        putSpartan.put("name", "Rose1");
        putSpartan.put("gender", "Female");
        putSpartan.put("phone", 1111111111l);


        given().log().all().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .body(putSpartan)
                .pathParam("id",id)
        .when().put("/api/spartans/{id}")
        .then().log().all()
                .statusCode(204);

    }

    @Test(priority = 3)
    public void PATCHExistingSpartan(){

        Map<String,Object> patchSpartan= new HashMap<>();

        patchSpartan.put("name","RoseWithPatch");

        given().log().all()
                .contentType(ContentType.JSON)
                .body(patchSpartan)
                .pathParam("id",id)
        .when().patch("/api/spartans/{id}")
                .then().log().all()
                .assertThat().statusCode(204);

    }

    @Test(priority = 4)
    public void GETThatSpartan(){

        given().accept(ContentType.JSON)
                .pathParam("id",id)
        .when().get("/api/spartans/{id}")
        .then().statusCode(200).log().all()
                .and().body("name",is("RoseWithPatch"));

    }

    @Test(priority = 5)
    public void DELETEThatSpartan(){

        given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("id",id)
        .when().delete("/api/spartans/{id}")
        .then().log().all()
                .and().statusCode(204);

    }

}