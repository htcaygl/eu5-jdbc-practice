package Day6_POJO;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;


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


        Map<String ,Object> requestMap= new HashMap<>();
        requestMap.put("name",name);
        requestMap.put("gender",gender);
        requestMap.put("phone",phone);


        // post to spartan

        given().log().all().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .body(requestMap)
                .when().post("http://54.237.100.89:8000/api/spartans")
                .then().log().all()
                .statusCode(201);

    }
}
