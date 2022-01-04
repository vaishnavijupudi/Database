package Resources;

import java.util.ArrayList;

/**
 * @author Deeksha Sareen
 *
 */
public class Database {

	private static String databaseName;
	private String tableName = "";
	private String value = "";
	private String condition = "";
	private String path = "";

	public Database() {
	}

	private static Database database;

	public static void setDatabase(String databasename) {
		if (database == null) {
			database = new Database();
			database.setDatabaseName(databasename);
		}
	}
	public static Database instance() {
		return database;
	}

	public void setDatabaseName(String databasename) {
		this.databaseName = databasename;
	}

	public static String getDatabase() {
		return databaseName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		if (condition == null) {
			condition = "";
		}
		this.condition = condition;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		if (value == null) {
			value = "";
		}
		this.value = value;
	}
}
