package fantasy_project;
import java.io.*;
import java.util.*;
 
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
 
public class getNFLdata {
   public static final String delimiter = ",";
   public static Map<String, Map<String, String>> get_projected_data(String csvFile) {
    
      JSONObject fantasy_dictionary = new JSONObject();
      List<String> stat_names_list = new ArrayList<>(Arrays.asList("Rank","Name","Pos","Team","STD PTS","HALF PPR PTS","PPR PTS","Cmp","Pass Att","Pass Yds","Pass TD","Int","Rush Att","Rush Yds","Run TD","Tgt","Rec","Rec Yds","Rec TD")); 
      try {
         File file = new File(csvFile);
         FileReader fr = new FileReader(file);
         BufferedReader br = new BufferedReader(fr);
         String line = "";
         String[] tempArr;
         
         while((line = br.readLine()) != null) {
            tempArr = line.split(delimiter);
            List<String> player_data_list = new ArrayList<>();
            Map<String, String> player_data_dict = new HashMap<String, String>();
            int counter = 0;
            for(String tempStr : tempArr) {
               System.out.println(tempStr);
               player_data_dict.put(stat_names_list.get(counter), tempStr);
               counter += 1;
            }
            
            fantasy_dictionary.put(player_data_dict.get("Name"),new HashMap<String, String>());

        }


         
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
        //List<String> stat_names_list = new ArrayList<>(Arrays.asList("Rk","Player","Tm","FantPos","Age","G","GS","Cmp","Att","Yds","TD","Int","Att","Yds","Y/A","TD","Tgt","Rec","Yds","Y/R","TD","Fmb","FL","TD","2PM","2PP","FantPt","PPR","DKPt","FDPt","VBD","PosRank","OvRank","-9999")); 
        List<String> stat_names_list = new ArrayList<>(Arrays.asList("Rk","Player","Team","Position","Age","G","placement","Cmp","Att","Pass Yds","Pass TD","Int","Rush Att","Rush Yds","AVG","Rush TD","Tgt","Rec","Yds","placement","Rec TD","placement","placement","Total TD","placement","placement","placement","PPR","placement","placement","placement","PosRank","placement","placement"));
  

        while((line = br.readLine()) != null) {

          
         tempArr = line.split(delimiter);
         List<String> player_data_list = new ArrayList<>();
         Map<String, String> player_data_dict = new HashMap<String, String>();
         int counter = 0;
         for(String tempStr : tempArr) {
            System.out.println(tempStr);
            player_data_dict.put(stat_names_list.get(counter), tempStr);
            counter += 1;
         }
         float half_ppr_fantasy_points = 0;
         String ppr_string = player_data_dict.get("ppr");
         String reception_string = player_data_dict.get("receptions");
         System.out.println("mimimi");
         if (ppr_string != null && ppr_string != "0" && reception_string != null && reception_string != "0"  ){
          float ppr_fantasy_points = Float.parseFloat(ppr_string);
          float receptions = Float.parseFloat(reception_string);
          half_ppr_fantasy_points = ppr_fantasy_points-(receptions/2);
         }


         String name = player_data_dict.get("Player");
         
         Map<String, String>stats_hashmap = fantasy_dict.get(name);
         if (stats_hashmap != null){
          stats_hashmap.put("2021 Points", String.valueOf(half_ppr_fantasy_points));
         }
         player_data_dict.remove("placement");
         fantasy_dict.put(name,player_data_dict);
    }
       
        br.close();
        } catch(IOException ioe) {
           ioe.printStackTrace();
        }
        return fantasy_dict;
   }
   



   public static void main(String[] args) {
      // csv file to read
      String ProjectedCsvFile = "src/main/java/fantasy_project/projected_data.csv";
      String OldCsvFile = "src/main/java/fantasy_project/2021_fantasy_data.csv";
      
       Map<String, Map<String,String>> fantasy_dictionary = getNFLdata.get_projected_data(ProjectedCsvFile);
       Map<String, Map<String,String>> final_fantasy_dictionary = getNFLdata.get_old_data(OldCsvFile, fantasy_dictionary);
       List<String> alphabets = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));
       String e = "E";
       System.out.println("BRUHHH");
       System.out.println(final_fantasy_dictionary);
       try (FileWriter file = new FileWriter("src/Frontend/stat_files/All_fantasy_data.json")) {
         //We can write any JSONArray or JSONObject instance to the file
         file.write(((JSONObject) final_fantasy_dictionary).toJSONString()); 
         file.flush();

     } catch (IOException i) {
         i.printStackTrace();
     }
       
   }
}