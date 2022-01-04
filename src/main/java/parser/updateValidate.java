package parser;

import Resources.Database;
import logs.QueryError;
import logs.QueryRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class updateValidate {

  Database queryToken = new Database();
  ArrayList<String> listOfColumns= new ArrayList<String>();
  QueryRecord queryRecord = new QueryRecord();
  QueryError queryError = new QueryError();

 public void update(String query) throws IOException {
   long startTime = System.nanoTime();
   if(updateSyntax(query)){
     System.out.println("Update successful");
     long endTime = System.nanoTime();
     long timeElapsed = endTime - startTime;
     queryRecord.event(query,timeElapsed);
   } else {
     System.out.println("Update unsuccessful");
     long endTime = System.nanoTime();
     long timeElapsed = endTime - startTime;
     queryError.event(query,timeElapsed);
   }
 }

 public boolean updateSyntax(String query) throws IOException {
   String[] splitUpdate = query.toLowerCase().split("where");
   String[] getValue = splitUpdate[0].split("set");
   String[] getTableName = getValue[0].split(" ");
   String whereCondition = splitUpdate[1];
   String valueToBeUpdated = getValue[1];
   String tableName = getTableName[1];
   if(checkTableNameExists(tableName) && checkColumnNameExists(valueToBeUpdated)
           && setValuesInQueryToken(tableName,valueToBeUpdated,whereCondition)){
     updateParser parser = new updateParser();
     parser.updateQuery(queryToken,whereCondition);
     return true;
   }
   return false;
 }

  private boolean setValuesInQueryToken(String tableName, String valueToBeUpdated, String whereCondition) throws FileNotFoundException {
    queryToken.setTableName(tableName);
    if(checkIfPrimary(valueToBeUpdated)){
      valueToBeUpdated = "#" + valueToBeUpdated;
      if(checkIfKeyValueExists(valueToBeUpdated)) {
        System.out.println("Value for the key column already exists");
        return false;
      }
    }
    queryToken.setValue(valueToBeUpdated.trim());
    System.out.println("valuetobeupdated"+queryToken.getValue());
    if (checkIfPrimary(whereCondition)){
      whereCondition = "#" + whereCondition;
    }
    queryToken.setCondition(whereCondition.trim());
    System.out.println("where"+queryToken.getCondition());

    return true;
  }

  private boolean checkIfKeyValueExists(String valueToBeUpdated) throws FileNotFoundException {
   List<String> valuesList = new ArrayList<>();
    File file = new File(queryToken.getPath());
    String line;
    String enteredValue = valueToBeUpdated.split("=")[1].trim();
    Scanner sc = new Scanner(file);
    while(sc.hasNextLine()) {
      line = sc.nextLine();
      String[] str = line.split(",");
      for (String words:str) {
        String[] colArr = words.split("=");
        if(colArr[0].contains("#")) {
          valuesList.add(colArr[1].trim());
        }
      }
    }
    return valuesList.contains(enteredValue);
  }

  private boolean checkIfPrimary(String value) {
    String[] splitCol = value.split("=");
    return listOfColumns.contains("#"+splitCol[0].trim());
  }

  private boolean checkColumnNameExists(String valueToBeUpdated) throws FileNotFoundException {
    File file = new File(queryToken.getPath());
    String line = null;
    Scanner sc = new Scanner(file);
    line = sc.nextLine();
    System.out.println(line);
    while(sc.hasNextLine()) {
      line = sc.nextLine();
      System.out.println("linee:"+line);
    }
    String[] labels = new String[10];
    String[] str = line.split(",");
    for (String words:str) {
      System.out.println(words);
      labels = words.split("=");
      listOfColumns.add(labels[0]);
    }

    String[] getColumnValue = valueToBeUpdated.split("=");
    String col = getColumnValue[0];
    System.out.println("listtt"+listOfColumns.contains(col.trim()));
    for (String s: listOfColumns){
      if (s.contains(col.trim()) || s.contains('"'+"#"+col.trim() + '"')){
        return true;
      }
    }
    System.out.println("The column doesnt exist");
    return false;
  }

  private boolean checkTableNameExists(String tableName) {
    String dbName = Database.getDatabase();
    String path =  dbName+"/"+tableName + ".txt";
    File tableFile = new File(path);
    if (tableFile.exists()) {
      queryToken.setPath(path);
      return true;
    }
    return false;
  }

}
