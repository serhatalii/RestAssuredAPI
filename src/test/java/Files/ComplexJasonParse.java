package Files;

import io.restassured.path.json.JsonPath;

import static Files.payLoad.*;

public class ComplexJasonParse {
    public static void main(String[] args) {
        JsonPath js=new JsonPath(payLoad.CoursePrice());
       // Print Purchase Amount
        int count= js.getInt("courses.size()");
      //  System.out.println(count);
        int countOFDashborad=js.getInt("dashboard.purchaseAmount");
     //   System.out.println(countOFDashborad);
       // 3. Print Title of the first course
        String title=js.get("courses[2].title");
        System.out.println(title);
        for (int i=0; i<count; i++){
           String courseTitles= js.get("courses["+i+"].title");
            System.out.println(courseTitles);
            System.out.println(js.get("courses["+i+"].price").toString());

        }

        System.out.println("Print no of copies sold by RPA Course");
        for (int i=0; i<count; i++){
            String courseTitles= js.get("courses["+i+"].title");
           if (courseTitles.equalsIgnoreCase("RPA")){
               //copies sold
              int copiesCount=js.get("courses["+i+"].copies");
               System.out.println(copiesCount);
               break;
           }
        }
        System.out.println("Verify if Sum of all Course prices matches with Purchase Amount");


    }
}
