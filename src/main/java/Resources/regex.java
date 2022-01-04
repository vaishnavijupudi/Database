package Resources;

/**
 * @author Deeksha Sareen: Regex utilized throughout the program.
 *
 */
public class regex {

	private regex() {
	}

	public static final String CREATETABLE = "(create\\s+table\\s+(\\w+)\\((((\\w+\\s+(int|varchar\\((\\s+)?(\\d+)(\\s+)?\\)|double|float)+(\\s+(primary key|not null|unique|(unique not null)))?(\\s+)?(\\,)?(\\s+)?)*)(\\,(\\s+)?foreign\\s+key\\s+\\w+\\s+references\\s+\\w+(\\s+)?\\((\\s+)?\\w+(\\s+)?\\))?(\\s+)?\\))\\;)";
	public static final String SELECT = "select\\s+(.*?)\\s*from\\s+(.*?)\\s*(where\\s(.*?)\\s*)?;";
	public static final String DELETE = "";
	public static final String UPDATE = "";
	public static final String DATABASE = "^use\s+[^;]*;$";
	public static final String INSERT = "insert into\\s(.*?)\\s(.*?)\\svalues\\s(.*?);";
	public static final String CREATEDB = "^create\sdatabase\s[^;]*;$";
	public static final String BETWEENBRACKETS = "\\((.*?)\\)";
}
