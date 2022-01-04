package logs;

import java.io.*;
import java.util.*;

public class Record {

    public Map<String, List<String>> readTableValues(String tableName, String databaseName, String location) throws IOException {
      String line = "";
      String splitBy = "\n";
      int temp = 0;
      String file_address = "";

      ArrayList<String> tableValues = new ArrayList<>();
      Map<String, List<String>> readTableMap = new HashMap<>();
      List<List<String>> rowKeeper = new ArrayList<>();
      List<String> finalValues = new ArrayList<>();
      File file = new File("src/com/package/DATABASE/" + databaseName + ".txt");


      if (location.equalsIgnoreCase("local")) {
        if (file.exists()) {
          //file = new File("src/com/package/TABLES/local/" + tableName + ".txt");
          file_address = "src/com/package/TABLES/local/" + tableName + ".txt";
        } else {
          //response = "Database table not found";
        }
      } else if(location.equalsIgnoreCase("remote")) {
        if (file.exists()) {
          //file = new File("src/com/package/TABLES/remote/" + tableName + ".txt");
          file_address = "src/com/package/TABLES/remote/" + tableName + ".txt";
        } else {
          //response = "Database table not found";
        }
      }
      BufferedReader br = new BufferedReader(new FileReader(file_address));
      while ((line = br.readLine()) != null) {
        String[] users = line.split(splitBy);
        tableValues.add(users[0]);
      }


      for (String i : tableValues) {

        if (temp == 2) {

          List<String> db = new ArrayList<>();
          db.add(i);
          readTableMap.put("database", db);
        } else if (temp == 4) {

          List<String> tabel = new ArrayList<>();
          tabel.add(i);
          System.out.println(i);
          readTableMap.put("table", tabel);
        } else if (temp == 6) {

          List<String> columns = Arrays.asList(i.split("~"));
          readTableMap.put("column", columns);
        } else if (temp == 8) {

          List<String> metas = Arrays.asList(i.split("~"));
          readTableMap.put("meta", metas);
        } else if (temp > 9) {

          List<String> rows = Arrays.asList(i.split("~"));
          rowKeeper.add(rows);
        }
        temp++;
      }


      for (List<String> row : rowKeeper) {
        for (String value : row) {
          finalValues.add(value);
        }
      }
      readTableMap.put("value",finalValues);

      queryLog(tableName, location);


      return readTableMap;
    }


    private void queryLog(String tableName, String location) {
      try {
        File file = new File("src/com/package/LOG/eventlog.txt");
        if (file.exists()) {
          FileWriter fileWriter = new FileWriter(file, true);
          if (fileWriter != null) {
            fileWriter.append(tableName + "\t" + location + "\t->READ OPERATION" + "\n");
          }
          fileWriter.flush();
          fileWriter.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }


}

