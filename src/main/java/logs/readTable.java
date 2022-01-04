package logs;

import Resources.UserID;
import user.login;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * File: readTable.java
 *
 * @author Yashvi Lad
 * Purpose: Query log printing
 * Description: This class will write all query logs in one text file.
 */
public class readTable {

  private static boolean fileOut = true;

  /*
   * This method writes query logs into text file with date-time, user id  and message
   * */
  public static void print(String output) {
    LocalDateTime localDateTime = LocalDateTime.now();

    if (fileOut) {

      String path = "LogAndDumpFiles/QueryLogs/queryLog.txt";
      File file = new File(path);
      try {

        FileWriter fileWriter = new FileWriter(file, true);

        fileWriter.write(UserID.getUserID() + "\t" + output + "\t" + localDateTime + "\n");

        fileWriter.flush();
        fileWriter.close();

      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      System.out.print(output);
    }
  }

}

