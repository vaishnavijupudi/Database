package parser;

import Resources.Database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class updateParser {

  public void updateQuery(Database queryToken, String where) throws IOException {
    File file = new File(queryToken.getPath());
    String line = null;
    String[] rows = new String[100];
    String[] remainingRows = new String[100];
    Scanner sc = new Scanner(file);
    sc.useDelimiter("\\Z");
    line = sc.next();
    System.out.println(line);

    String updateParts[] = line.split("\\r?\\n");
    int i = 0;
    int c = 0;
    for (String word : updateParts) {
      String[] temp = word.split(",");
      String tempstr = temp[0] + "," + temp[1] + "," + temp[2];
      if (tempstr.contains(where.trim())){
        rows[i] = tempstr;
      } else {
        remainingRows[c] = tempstr;
        c++;
      }
    }
    System.out.println("remm:"+Arrays.toString(remainingRows));
    System.out.println("rows:"+Arrays.toString(rows));
    String[] splitCol = queryToken.getValue().split("=");
    for (int x = 0; x <= i; x++) {
      String[] tempArr = rows[x].split(",");
      String str = null;
      for (int y = 0; y < tempArr.length; y++) {
        if (tempArr[y].contains(splitCol[0])) {
          str = tempArr[y];
        }
      }
      if (rows[x].contains(splitCol[0])) {
        rows[x] = rows[x].replace(str, queryToken.getValue());
      }
    }

    FileWriter myWriter = new FileWriter(file);
    myWriter.write(rows[0] + "\n");
    for (int s = 0; s < updateParts.length - 1; s++) {
      myWriter.write(remainingRows[s] + "\n");
    }
    myWriter.close();
  }

}
