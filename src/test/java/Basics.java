import Files.payLoad;
import Files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.*; //Some package are static in RestAssured it comes from Rest assured
import static org.hamcrest.Matchers.*;

public class Basics {
    public static void main(String[] args) throws IOException {
        //VALIDATE IF ADD PLACE API IS WORKING AS EXPECTED
        //ADD PLACE -> UPDATE PLACE WITH NEW ADDRESS -> GET PLACE TO VALIDATE IF NEW ADDRESS IS PRESENT IN RESPONSE
        //Validation of Status Code
        //Validation of Scope value
        //Validation of Server response
        //Content of the file to String -> content of file can convert into Byte->Byte data to String
        /*
        Given-all inout details
        When- Submit the API
        Then- Validate the response
         */
        RestAssured.baseURI="https://rahulshettyacademy.com/#/index";

        String response=given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(Files.readAllBytes(Paths.get("https://d.docs.live.net/5949d1c6b1f1ff82/Desktop/API%20PROJECTS/ADD-DeletePlaceAPIs.docx"))).
                when().post("/maps/api/place/add/json").
                        then().assertThat().statusCode(200).body("scope",equalTo("APP"))
                .header("server","Apache/2.4.18 (Ubuntu)").extract().response().asString();
        System.out.println(response);
        JsonPath js=new JsonPath(response); //JsonPath class for printing "Place ID"
        String PlaceID=js.getString("place_id");
        System.out.println("******************");
        System.out.println(PlaceID);

        //Update PLACE with "PUT" keyword
        String newAddress="Summer Walk, Turkey";
        given().log().all().queryParam("key","qaclick123").header("Content-Type","text/plain")
                .body("{\n" +
                        "\"place_id\":\""+PlaceID+"\",\n" +
                        "\"address\":\""+newAddress+"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}").when()
                .when().put("maps/api/place/update/json")
                .then().assertThat().log().all().statusCode(200).body("msg",equalTo("Address successfully updated"));

        //Get Api
        String getPlaceResponse= given().log().all().queryParam("key", "qaclick123").queryParam("place_id", PlaceID)
                .when().get("maps/api/place/get/json").then().assertThat().statusCode(200).extract().response().asString();

      //  JsonPath js1=ReusableMethods.rowToJason(getPlaceResponse);
      //  String actualAddress=js1.getString("address");
       // System.out.println(actualAddress);
       // Assert.assertEquals(actualAddress,newAddress);
    }
}
