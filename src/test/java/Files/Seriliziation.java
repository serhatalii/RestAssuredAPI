package Files;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class Seriliziation {
    public static void main(String[] args) {
        addPlace b = new addPlace();
        b.setAccuracy(50);
        b.setAddress("29, side layout, cohen 09");
        b.setLanguage("French-IN");
        b.setPhone_number("(+91) 983 893 3937");
        b.setWebsite("http://google.com");
        b.setName("Frontline house");
        List<String> list = new ArrayList<String>();
        list.add("shoe park");
        list.add("shop");
        b.setTypes(list);
        Location loc = new Location();
        loc.setLat(-38.383494);
        loc.setLng(33.427362);
        b.setLocation(loc);

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        Response response = given().log().all().queryParam("key", "qaclick123").body(b)
                .when().post("/maps/api/place/add/json").then().assertThat().statusCode(200).extract().response();
        String responseAsString = response.asString();
        System.out.println(responseAsString);

    }
}
