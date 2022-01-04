package parser;

import Resources.Database;
import logs.DeleteRecord;
import logs.EventFailRecord;

import java.io.File;
import java.io.IOException;

public class dropTable {
  Database queryToken = new Database();
  public void dropTableName(String query) throws IOException {

    if (dropSyntax(query)) {
      System.out.println("drop successful");
    }

  }

  private boolean dropSyntax(String query) throws IOException {
    String[] getTable = query.split("table");
    String tableName = getTable[1];
    if(checkIfTableExists(tableName)){
      drop();
    }
    return false;
  }

  private void drop() throws IOException {
    System.out.println(queryToken.getPath());
    File file = new File(queryToken.getPath());
    DeleteRecord deleteRecord = new DeleteRecord();
    EventFailRecord eventFailRecord = new EventFailRecord();
    long startTime = System.nanoTime();
    if(file.delete())
    {
      System.out.println("table deleted successfully");
      long endTime = System.nanoTime();
      long timeElapsed = endTime - startTime;
      deleteRecord.event("Table",timeElapsed);
    }
    else
    {
      System.out.println("Failed to delete the table");
      long endTime = System.nanoTime();
      long timeElapsed = endTime - startTime;
      eventFailRecord.event("",timeElapsed);
    }
  }

  private boolean checkIfTableExists(String tableName) {
    String dbName = Database.getDatabase();
    String path = dbName + "/" + tableName.trim() + ".txt";
    File tableFile = new File(path.trim());
    if (tableFile.exists()) {
      queryToken.setPath(path);
      return true;
    }
    return false;
  }
}
