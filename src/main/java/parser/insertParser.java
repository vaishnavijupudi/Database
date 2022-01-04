package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Resources.Database;
import Resources.regex;
import logs.FailInsertionRecord;
import logs.InsertRecord;

/**
 * @author Deeksha Sareen: Insert query executioner
 *
 */
public class insertParser {

	Lock lock = new ReentrantLock();
	public void insertQuery(String query) throws IOException {
		String colNames = "";
		String values = "";
		Matcher m = Pattern.compile(regex.BETWEENBRACKETS).matcher(query);
		int flag = 0;
		while (m.find()) {
			if (flag == 0) {
				colNames += m.group(1);

			} else {
				values += m.group(1);
			}
			flag++;

		}
		String output = "";
		String line;
		String tablePath = Database.getDatabase() + "/" + getTable(query);
		String metaTablePath = Database.getDatabase() + "/meta_" + getTable(query);
		String[] entries = values.trim().split(",");
		String[] columns = colNames.trim().split(",");
		int fileExists = 1;
		int size = 0;
		int fkcheck= 1;
		int pkcheck = 1;
		long startTime = System.nanoTime();
		if (entries.length != columns.length) {
			System.err.println("Column names don't match the column values");
			long endTime = System.nanoTime();
			long timeElapsed = endTime - startTime;
			FailInsertionRecord failInsertionRecord = new FailInsertionRecord();
			failInsertionRecord.event("",timeElapsed);
		} else {
			
			for (int i = 0; i < columns.length; i++) {
				BufferedReader in = new BufferedReader(new FileReader(metaTablePath));
				while ((line = in.readLine()) != null) {
					if (!output.contains(columns[i])&&line.contains(columns[i].trim()) && line.toLowerCase().contains("primary--key")) {
						if (checkPrimaryConstraint(tablePath, entries[i].trim()) == true) {
							output += "#" + columns[i] + "=" + entries[i] + ", ";
		
							size++;
						} else {
							if(pkcheck==1) {
								System.err.println("Primary key constraint fails");
								
							}
							pkcheck=0;
						}

					} else if (line.contains(columns[i].trim()) && line.toLowerCase().contains("foreign--key")) {
						String[] references = line.toLowerCase().split("--references--");
						String[] refTableName = references[1].split("\\(");
						String refPath = Database.getDatabase() + "/" + refTableName[0] + ".txt";
						File file = new File(refPath);
						if (!file.exists()) {
							if(fileExists==1) {
								System.err.println("Reference table does not exist");
							}
							
							fileExists=0;
						} else if (!output.contains(columns[i])&& checkPrimaryConstraint(refPath, columns[i].trim()) == false && file.exists()) {
							output += columns[i] + "=" + entries[i] + ", ";
							size++;
						} else {
							if(fkcheck==1) {
								System.err.println("Foreign key constraint fails");
								
							}
							fkcheck=0;
							
						}

					}

					else if (!output.contains(columns[i]) && line.contains(columns[i].trim())
							&& !line.toLowerCase().contains("primary--key")
							&& !line.toLowerCase().contains("foreign--key")) {
						output += columns[i] + "=" + entries[i] + ", ";
						
						size++;
					}
				}
				

			}
			FileWriter fileWriter = null;
			if (size == columns.length && fileExists==1 && fkcheck==1 && pkcheck==1) {
					fileWriter = new FileWriter(tablePath, true);
					fileWriter.write(System.lineSeparator());
					fileWriter.write(output);
					
					System.out.println("Row successfully inserted.");
					
					fileWriter.flush();
				
			}
			long endTime = System.nanoTime();
			long timeElapsed = endTime - startTime;
			InsertRecord insertRecord = new InsertRecord();
			insertRecord.event("Row",timeElapsed);

		}

		// insert into student (id, name ,last_name) values (1,'Deeksha', 'Sareen');

	}

	private boolean checkPrimaryConstraint(String tablePath, String contraint) throws IOException {
		String line;
		BufferedReader in = new BufferedReader(new FileReader(tablePath));
		while ((line = in.readLine()) != null) {
			if (line.contains(contraint)) {
				return false;
			}
		}
		return true;
	}

	private String getTable(String query) {
		query = query.replaceAll(";", "");
		query = query.replaceAll(",", " ");
		query = query.replaceAll("[^a-zA-Z ]", " ");
		String[] sqlWords = query.split(" ");
		String name = sqlWords[2] + ".txt";
		return name;
	}
}
