package fantasy_project;
 
import java.io.FileWriter;
import java.io.IOException;

import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
 
public class App
{
    @SuppressWarnings("unchecked")
    public static void main( String[] args )
    {
        //First Employee
        JSONObject employeeList = new JSONObject();
        employeeList.put("firstName", "Lokesh");
            //etc
           
        //Write JSON file
        try (FileWriter file = new FileWriter("employees.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(employeeList.toJSONString()); 
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}