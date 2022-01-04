package logs;

import Resources.Database;
import Resources.UserID;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * File: SQLDumpGeneratorTable.java
 *
 * @author Yashvi Lad
 * Purpose: Dump generation for create tables
 * Description: This class writes dump file on the provided path
 */
public class SQLDumpGeneratorTable {

  private static boolean writeFile = true;

  public SQLDumpGeneratorTable() throws IOException {
  }

  //this method writes dump into text file.(writes user, query and date-time)
  public static void dump(String query) throws IOException {

    if(writeFile) {

      String path = "LogAndDumpFiles/SQLDump/"+ UserID.getUserID()+"-generatedDump.txt";
      File file = new File(path);
      try {
        FileWriter fileWriter = new FileWriter(file, true);

        fileWriter.write(Database.getDatabase() + "\t"+query + "\n");

        fileWriter.flush();
        fileWriter.close();
        GeneralLogRecord generalLogRecord = new GeneralLogRecord();
        generalLogRecord.generalLog();

      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      System.out.print(query);
    }
  }


}
