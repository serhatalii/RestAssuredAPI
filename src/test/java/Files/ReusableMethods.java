package Files;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.io.File;

public class ReusableMethods {
    public static JsonPath rowToJason(Response response){
        JsonPath js1=new JsonPath((File) response);
        return js1;
    }
}
