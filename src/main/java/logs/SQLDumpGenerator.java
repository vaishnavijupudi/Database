package logs;

import Resources.Database;
import Resources.UserID;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * File: SQLDumpGenerator.java
 *
 * @author Yashvi Lad
 * Purpose: Dump generation for databases
 * Description: This class writes dump file on the provided path
 */
public class SQLDumpGenerator {

  private static boolean writeFile = true;

  public SQLDumpGenerator() throws IOException {
  }

  //this method writes dump into text file.(writes user, query and date-time)
  public static void dump(String query) throws IOException {

    if(writeFile) {

      String path = "LogAndDumpFiles/SQLDump/"+UserID.getUserID()+"-generatedDump.txt";
      File file = new File(path);
      try {
        FileWriter fileWriter = new FileWriter(file, true);

        fileWriter.write(query + "\n");

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
