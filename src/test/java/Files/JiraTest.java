package Files;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.io.File;

import static io.restassured.RestAssured.*;
public class JiraTest {
    public static void main(String[] args) {
        baseURI="http://localhost:9090";
        //.relaxedHTTPSValidation is for HTTPS validation if you don't have RestAPI
        SessionFilter session=new SessionFilter();
      String response=given().header("Content-Type","application/json;charset=UTF-8")
                .body("{ \"username\": \"serhatalii\", \"password\": \"Serhat2709\" }")
                .log().all().filter(session).when().post("/rest/auth/1/session").then().log().all().extract().response().asString();

        String exceptedMessage="Hi how is it going";
        System.out.println("***************************");
         String addCommentResponse=given().pathParam("id","10202").log().all().header("Content-Type","application/json").body("{\n" +
                        "    \"body\": \""+exceptedMessage+"\",\n" +
                        "    \"visibility\": {\n" +
                        "        \"type\": \"role\",\n" +
                        "        \"value\": \"Administrators\"\n" +
                        "    }\n" +
                        "}").filter(session).when().post("/rest/api/2/issue/{id}/comment").then().assertThat().statusCode(201)
                 .extract().response().asString();
          JsonPath jsonPath=new JsonPath(addCommentResponse);
          String commandId=jsonPath.getString("id");




    //Add Attachment
        given().header("X-Atlassian-Token","no-check").filter(session).pathParam("key","10100").header("Content-Type","multipart/form-data")
                .multiPart("file",new File("jira.text")).when().post("/rest/api/2/issue/{key}/attachments").then().log().all()
                .assertThat().statusCode(200);




    //Get issue
        System.out.println("*****************************************");
       String issueDetails=given().filter(session).pathParam("key","10100")
               .queryParam("fields","comment").log().all().when().get("/rest/api/2/issue/{key}")
                .then().log().all().extract().response().asString();
        JsonPath js1=new JsonPath(issueDetails);
        int commentCount=js1.getInt("fields.comment.comments.size()");
        for (int i=0; i<commentCount; i++)
        {
          String commandIdIssue=js1.get("fields.comment.comments["+i+"].id").toString();
          if (commandIdIssue.equalsIgnoreCase(commandId)){
            String message=js1.get("fields.comment.comments["+i+"].body").toString();
              System.out.println(message);
              Assert.assertEquals(message,exceptedMessage);
          }
        }
    }
}
