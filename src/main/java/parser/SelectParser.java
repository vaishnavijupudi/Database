
package parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Deeksha Sareen
 * This class is reponsible for parsing the SQL query
 */
public class SelectParser {

	public static String selectFields;
	public static String where = "";
	public static String tableName;

	public Map<String, ArrayList<String>> parsingAttributes(String query) {

		String colFields = "";
		String conditions = "";
		Map<String, ArrayList<String>> mapping = new HashMap<String, ArrayList<String>>();

		String selectquery = query.replace(";", "");
		String[] splitQuery = selectquery.trim().split("\\s");
		for (int word = 1; word < splitQuery.length; word++) {
			if (splitQuery[word].equalsIgnoreCase("FROM")) {
				ArrayList<String> from = new ArrayList<>();
				from.add(splitQuery[word + 1]);
				mapping.put("From", from);

				ArrayList<String> attr = new ArrayList<>();
				String[] attributes = colFields.split("\\s");
				for (String a : attributes) {
					attr.add(a);
				}
				mapping.put("Attributes", attr);
				break;
			} else {
				colFields = splitQuery[word] + " " + colFields;
			}
		}
		if (selectquery.indexOf("where") != -1) {
			String[] where = selectquery.split("where");
			String[] whereClause = where[1].trim().split(" ");
			ArrayList<String> Clause = new ArrayList<>();
			for (String b : whereClause) {
				Clause.add(b);
			}
			mapping.put("Where", Clause);
		}

		return mapping;
	}

}
