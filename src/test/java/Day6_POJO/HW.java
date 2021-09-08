package Day6_POJO;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;


//Homework-2
//-Create one mockaroo api for name,gender,phone
//send get request to retrieve random info from that api
//use those info to send post request to spartan

public class HW {

    @Test
    public void HW(){

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().header("X-API-Key", "3aca9ab0")
                .when().get("https://my.api.mockaroo.com/mock_api");

        String name = response.path("name[0]");
        String gender = response.path("gender[0]");
        String phone = response.path("phone[0]");


        // post to spartan

//        given().and().accept(ContentType.JSON)
//                .and().contentType(ContentType.JSON)
//                .and().body()
//                .when().post("http://54.237.100.89:8000/api/spartans/")
//                .then().statusCode(201)
//                .and().contentType(ContentType.JSON)


    }


}
