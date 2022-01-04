package parser;

import Resources.Database;
import logs.QueryError;
import logs.QueryRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class deleteValidate {

  Database queryToken = new Database();
  ArrayList<String> listOfColumns = new ArrayList<String>();
  deleteParser parser = new deleteParser();
  QueryRecord queryRecord = new QueryRecord();
  QueryError queryError = new QueryError();


  public void delete(String query) throws IOException {
    if (deleteSyntax(query)) {
      System.out.println("delete successfull");
    }
  }

  public boolean deleteSyntax(String query) throws IOException {
    long startTime = System.nanoTime();
    if (query.contains("where")) {
      String[] deleteParts = query.split("where");
      String whereCondition = deleteParts[1];
      String[] table = deleteParts[0].split("from");
      String tableName = table[1];
      System.out.println(whereCondition);
      System.out.println(tableName);
      if (checkTableNameExists(tableName) && checkColumnNameExists(whereCondition)) {
        setValuesInQueryToken(tableName,whereCondition);
        parser.deleteRowQuery(queryToken);
        return true;
      }
    } else if(query.contains("from")){
      String[] table = query.split("from");
      String tableName = table[1];
      if (checkTableNameExists(tableName)){
        queryToken.setTableName(tableName);
        parser.deleteAllRowsQuery(queryToken);
        return true;
      }
      long endTime = System.nanoTime();
      long timeElapsed = endTime - startTime;
      queryRecord.event(query,timeElapsed);
    } else{
      System.out.println("Incorrect syntax. Please verify");
      long endTime = System.nanoTime();
      long timeElapsed = endTime - startTime;
      queryError.event("",timeElapsed);
    }
    return false;
  }

  private void setValuesInQueryToken(String tableName, String whereCondition) {
    queryToken.setTableName(tableName.trim());
    queryToken.setCondition(whereCondition.trim());
  }

  private boolean checkTableNameExists(String tableName) {
    String dbName = Database.getDatabase();
    String path = dbName + "/" + tableName.trim() + ".txt";
    File tableFile = new File(path.trim());
    String str = tableFile.getPath();
    if (tableFile.exists()) {
      queryToken.setPath(path);
      return true;
    }
    return false;
  }


  private boolean checkColumnNameExists(String whereCondition) throws FileNotFoundException {
    File file = new File(queryToken.getPath());
    String line = null;
    Scanner sc = new Scanner(file);
    line = sc.nextLine();
    System.out.println(line);
    while (sc.hasNextLine()) {
      line = sc.nextLine();
      System.out.println("linee:" + line);
    }
    String[] labels = new String[10];
    String[] str = line.split(",");
    for (String words : str) {
      System.out.println(words);
      labels = words.split("=");
      listOfColumns.add(labels[0]);
    }

    String[] getColumnValue = whereCondition.split("=");
    String col = getColumnValue[0];
    System.out.println("listtt" + listOfColumns.contains(col.trim()));
    for (String s : listOfColumns) {
      if (s.contains(col.trim()) || s.contains('"' + "#" + col.trim() + '"')) {
        return true;
      }

    }

return false;
  }
}
