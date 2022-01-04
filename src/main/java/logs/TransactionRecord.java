package logs;

import Resources.UserID;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * File: TransactionRecord.java
 *
 * @author Yashvi Lad
 * Purpose & Description: This java file generates transaction logs.
 */
public class TransactionRecord {

  /*
   * This method writes transaction logs into text file
   * */
  public void transactionLog(List<String> queries) throws IOException {
    for(int i = 0; i < queries.size(); i++) {
      FileWriter fileWriter = new FileWriter("LogAndDumpFiles/TransactionLogs/"+UserID.getUserID()+"transactions.txt", true);
      fileWriter.write(queries.get(i) + "\n");
      fileWriter.close();
    }
  }

}
