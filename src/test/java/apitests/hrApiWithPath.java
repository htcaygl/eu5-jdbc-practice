package apitests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;


import java.util.List;

import static io.restassured.RestAssured.*;

import static org.testng.Assert.*;

public class hrApiWithPath {

    @BeforeClass
    public void beforeclass(){

        baseURI= ConfigurationReader.get("hr_api_url");
    }


    @Test
    public void getCountriesWithPath(){

        Response response=given().accept(ContentType.JSON)
                    .and().queryParam("q","{\"region_id\":2}")
                    .when().get("/countries");

        assertEquals(response.statusCode(),200);

        System.out.println("response.path(\"limit\") = " + response.path("limit"));

        System.out.println("response.path(\"hasMore\") = " + response.path("hasMore").toString());


        String firstCountryId= response.path("items.country_id[0]");

        System.out.println("firstCountryId = " + firstCountryId);

        String secondCountryName=response.path("items.country_name[1]");

        System.out.println("secondCountryName = " + secondCountryName);

        String link2 = response.path("items.links[2].href[0]");

        System.out.println("link2 = " + link2);

        //get all countries
        List<String> countryNames = response.path("items.country_name");
        System.out.println("countryNames = " + countryNames);

        //assert that all regions_id's equal to 2

        List<Integer> region_ids = response.path("items.region_id");
        System.out.println("region_ids = " + region_ids);

        for(int region: region_ids) {
            assertEquals(region,2);
        }
    }

    @Test
    public void test2() {
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"job_id\":\"IT_PROG\"}")
                .when().get("/employees");

        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json");
        assertTrue(response.body().asString().contains("IT_PROG"));


        //make sure we have only IT_PTOG as a job

        List<String> job_ids = response.path("items.job_id");
        System.out.println("job_ids = " + job_ids);


        for (String job_id : job_ids) {

            assertEquals(job_id,"IT_PROG");
        }

    }
}
