package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Resources.Database;

/**
 * @author Deeksha Sareen : This class is responsible for executing the select query.
 *
 */
public class selectExecutioner {

	String tablename = "";
	String wherefields = "";
	String attributefields = "";

	public void executeSelect(String query) throws IOException {
		SelectParser parse = new SelectParser();
		Map<String, ArrayList<String>> mapping = new HashMap<>(parse.parsingAttributes(query));
		//System.out.println(mapping);
		ArrayList<String> tablename = new ArrayList<>(mapping.get("From"));
		ArrayList<String> colFields = new ArrayList<>(mapping.get("Attributes"));
		if (mapping.containsKey("Where")) {
			ArrayList<String> where = new ArrayList<>(mapping.get("Where"));
			for (String a : where) {
				wherefields += " " + a;
			}
		}

		String store = Database.getDatabase() + "/" + tablename.get(0) + ".txt";
		//System.out.println(store);
		File tableFile = new File(store);
		if (tableFile.exists()) {
			if (colFields.get(0).equals("*") && wherefields.equals("")) {
				BufferedReader in = new BufferedReader(new FileReader(store));
				String line;
				while ((line = in.readLine()) != null) {
					System.out.println(line);
				}
				in.close();
			}
			if (colFields.get(0).equals("*") && !wherefields.equals("")) {

				String line;
				if (wherefields.contains("or")) {
					String[] whereclauses = wherefields.trim().split("or");

					for (int i = 0; i < whereclauses.length; i++) {

						BufferedReader in = new BufferedReader(new FileReader(store));
						while ((line = in.readLine()) != null) {
							if (line.contains(whereclauses[i].trim())) {
								System.out.println(line);
							}

						}
						in.close();
					}
				}
				if (wherefields.contains("and")) {
					int flag = 0;
					String[] whereclauses = wherefields.trim().split("and");
					BufferedReader in = new BufferedReader(new FileReader(store));
					while ((line = in.readLine()) != null) {
						for (int i = 0; i < whereclauses.length; i++) {
							if (!line.contains(whereclauses[i].trim())) {
								flag = 1;
							}
						}
						if (flag == 0) {
							System.out.println(line);
						}
					}
				} else {

					BufferedReader in = new BufferedReader(new FileReader(store));
					while ((line = in.readLine()) != null) {
						if (line.contains(wherefields)) {
							System.out.println(line);
						}

					}
				}

			}

		} else {
			System.out.println("The required table does not exist");
		}

	}
}
