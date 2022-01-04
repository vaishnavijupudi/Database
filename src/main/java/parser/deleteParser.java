package parser;

import Resources.Database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class deleteParser {

  public void deleteRowQuery(Database queryToken) throws IOException {
    File file = new File(queryToken.getPath());
    String line = null;
    String[] rows = new String[10];
    Scanner sc = new Scanner(file);
    sc.useDelimiter("\\Z");
    line = sc.next();
    String parts[] = line.split("\\r?\\n");
    int i = 0;
    for (String word : parts) {
      String[] temp = word.split(",");
      System.out.println("temp:"+ Arrays.toString(temp));
      String tempstr = temp[0] + "," + temp[1] + "," + temp[2];
      System.out.println("tempstr:"+tempstr);
      if(!tempstr.contains(queryToken.getCondition())){
        rows[i] = tempstr;
        i++;
      }
    }
    FileWriter myWriter = new FileWriter(file);
    for(int j=0; j<i;j++){
      System.out.println("rowsss:"+rows[j]);
      myWriter.write(rows[j] + "\n");
    }
    myWriter.close();
  }

  public void deleteAllRowsQuery(Database queryToken) throws IOException {
    File file = new File(queryToken.getPath());
    FileWriter deleteWriter = new FileWriter(file);
    deleteWriter.write( " ");
    deleteWriter.close();
  }
}
