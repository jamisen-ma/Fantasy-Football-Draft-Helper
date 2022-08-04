package fantasy_project;
import java.io.*;
import java.util.*;
 
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class get_simplified_data {
   public static final String delimiter = ",";
   public static Map<String, Map<String, String>> get_projected_data(String csvFile) {
    
      JSONObject fantasy_dictionary = new JSONObject();
      JSONObject rankings_list= new JSONObject();
      
      List<String> stat_names_list = new ArrayList<>(Arrays.asList("Projected Rank","Name","Pos","Team","STD PTS","2022 Projected","PPR PTS","Cmp","Att","Pass Yds","Pass TD","Int","Rush Att","Rush Yds","Run TD","Tgt","Rec","Rec Yds","Rec TD")); 
      try {
         File file = new File(csvFile);
         FileReader fr = new FileReader(file);
         BufferedReader br = new BufferedReader(fr);
         String line = "";
         String[] tempArr;
         int rankings_counter = 0;
         while((line = br.readLine()) != null) {
            rankings_counter += 1;
            tempArr = line.split(delimiter);
            List<String> player_data_list = new ArrayList<>();
            Map<String, String> player_data_dict = new HashMap<String, String>();
            int counter = 0;
            for(String tempStr : tempArr) {
            //   System.out.println(tempStr);
               if (counter == 1 || counter == 2 || counter == 5 || counter == 3){
          
                  player_data_dict.put(stat_names_list.get(counter), tempStr);
            }
             counter += 1;
         }
            String name  = player_data_dict.get("Name");
            fantasy_dictionary.put(name,player_data_dict);
            rankings_list.put(rankings_counter,name);

        }

        fantasy_dictionary.put("rankings_list",rankings_list);

         br.close();
         } catch(IOException ioe) {
            ioe.printStackTrace();
         }

       return fantasy_dictionary;

   }

   public static Map<String, Map<String, String>> get_old_data(String csvFile,Map<String, Map<String, String>> fantasy_dict ) {
    try {
        File file = new File(csvFile);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line = "";
        String[] tempArr;
  

        while((line = br.readLine()) != null) {

           tempArr = line.split(delimiter);
  
           int counter = 0;
           List<String> player_old_data_list = new ArrayList<>();
           for(String tempStr : tempArr) {
              player_old_data_list.add(tempStr);
           }

           float half_ppr_fantasy_points = 0;
           if (player_old_data_list.get(28) != ""){
            float ppr_fantasy_points = Float.parseFloat(player_old_data_list.get(28));
            float receptions = Float.parseFloat(player_old_data_list.get(17));
            half_ppr_fantasy_points = ppr_fantasy_points-(receptions/2);
           }


           String name = player_old_data_list.get(1);
           
           Map<String, String>stats_hashmap = fantasy_dict.get(name);
           if (stats_hashmap != null){
            stats_hashmap.put("2021 Points", String.valueOf(half_ppr_fantasy_points));
            
        }
    }
       
        br.close();
        } catch(IOException ioe) {
           ioe.printStackTrace();
        }
        return fantasy_dict;
   }
   



   public static void main(String[] args) {
      // csv file to read
      String ProjectedCsvFile = "/Users/jamisenma/fantasy_project_final/fantasy_project_final/src/main/java/fantasy_project/projected_data.csv";
      String OldCsvFile = "/Users/jamisenma/fantasy_project_final/fantasy_project_final/src/main/java/fantasy_project/2021_fantasy_data.csv";
       Map<String, Map<String,String>> fantasy_dictionary = get_simplified_data.get_projected_data(ProjectedCsvFile);
       Map<String, Map<String,String>> final_fantasy_dictionary = get_simplified_data.get_old_data(OldCsvFile, fantasy_dictionary);
       List<String> alphabets = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));
       String e = "E";
       try (FileWriter file = new FileWriter("src/Frontend/stat_files/simplified_fantasy_data.json")) {
         //We can write any JSONArray or JSONObject instance to the file
         file.write(((JSONObject) final_fantasy_dictionary).toJSONString()); 
         file.flush();

     } catch (IOException i) {
         i.printStackTrace();
     }
       
   }
}