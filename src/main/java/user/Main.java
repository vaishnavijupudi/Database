package user;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Deeksha Sareen : The main file for running the application
 *
 */
public class Main {

    
	public static void main(String[] args) throws IOException, SQLException, InterruptedException {

		ApplicationOutput applicationOutput = ApplicationOutput.getInstance();
		applicationOutput.displayOutput();

	}
}
