package logs;

import Resources.UserID;

import javax.annotation.processing.Generated;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * File: GeneralLogRecord.java
 *
 * @author Yashvi Lad
 * Purpose & Description: This java file generates general log.
 */
public class GeneralLogRecord {

  /*
   * This method writes general logs into text file with date-time and message
   * */
  public void generalLog() throws IOException {

    FileWriter fileWriter = new FileWriter("LogAndDumpFiles/GeneralLogs/dumpLogs.txt", true);
    LocalDateTime localDateTime = LocalDateTime.now();

    try {
      if (fileWriter != null) {
        fileWriter.append(UserID.getUserID() + "\t" + "**********SQL Dump Generated Successfully**********" + "\t" + localDateTime + "\n");
      }
      fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
