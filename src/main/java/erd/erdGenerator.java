package erd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Resources.Database;

/**
 * @author Deeksha Sareen: This class generates an ERD of the database tables.
 *
 */

public class erdGenerator {


	public void ERD() throws IOException {
		FileReader fileReader = null;
        String dbPath = Database.getDatabase();
		String[] tablenames;
		File directory = new File(dbPath);
		String[] getdatabasename = Database.getDatabase().split("/");
		
		String tablePath = "ERD/"+getdatabasename[1]+".txt";
		File erdFile = new File(tablePath);
		if(erdFile.exists()) {
			erdFile.delete();
		}
		erdFile.createNewFile();
		File[] files = directory.listFiles();
		FileWriter fw = new FileWriter(erdFile,true);
		for (File file : files) {
			if(file.getName().startsWith("meta")) {
				fileReader = new FileReader(Database.getDatabase()+"/"+file.getName());
				
				String[] fileName = file.getName().split(".txt");
				tablenames=fileName[0].split("_");
				String line = "";
				ArrayList<String>cols = new ArrayList<>();
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				fw.write("Table_Name = "+tablenames[1]);
				fw.write(System.lineSeparator());
				while ((line = bufferedReader.readLine()) != null) {
					fw.write(line);
					fw.write(System.lineSeparator());
				}
				fw.write("-----------------------------------------------------------------");
				fw.write(System.lineSeparator());
				fw.flush();
			}

		}
	}

}
