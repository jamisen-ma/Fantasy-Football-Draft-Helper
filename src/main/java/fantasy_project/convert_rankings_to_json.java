package fantasy_project;
import java.io.*;
import java.util.*;
 
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class convert_rankings_to_json {
   public static final String delimiter = ",";
   public static Map<String, Map<String, String>> get_projected_data(String csvFile) {
    
      JSONObject rankings_list= new JSONObject();
      
      try {
         File file = new File(csvFile);
         FileReader fr = new FileReader(file);
         BufferedReader br = new BufferedReader(fr);
         String line = "";
         String[] tempArr;
         int rankings_counter = 0;
         while((line = br.readLine()) != null) {
            tempArr = line.split(delimiter);
            for(String tempStr : tempArr) {
                rankings_counter += 1;
            tempArr = line.split(delimiter);
            rankings_list.put(rankings_counter,tempStr);
         }
        }
            
         
         br.close();
         } catch(IOException ioe) {
            ioe.printStackTrace();
         }

       return rankings_list;

   }

   public static void main(String[] args) {
      // csv file to read
      String ProjectedCsvFile = "/Users/jamisenma/fantasy_project_final/fantasy_project_final/src/main/java/fantasy_project/rankings.csv";
       Map<String, Map<String,String>> rankings_list = convert_rankings_to_json.get_projected_data(ProjectedCsvFile);
       try (FileWriter file = new FileWriter("src/Frontend/stat_files/rankings_list.json")) {
         //We can write any JSONArray or JSONObject instance to the file
         file.write(((JSONObject) rankings_list).toJSONString()); 
         file.flush();

     } catch (IOException i) {
         i.printStackTrace();
     }
       
   }
}