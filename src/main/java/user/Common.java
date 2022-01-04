package user;

import Resources.UserID;
import logs.SQLDumpGenerator;
import logs.SQLDumpGeneratorTable;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Deeksha Sareen: This class creates an instance of the prompt. prompt
 *         recurs until the user enters exit.
 *
 */
public class Common {

	private Common() {
	}

	private static Common common;

	public static Common getInstance() {
		if (common == null)
			common = new Common();
		return common;
	}

	public void takeinput() throws IOException, InterruptedException {

		System.out.println("Enter query ~");
		Scanner scanner = new Scanner(System.in);
		String query = scanner.nextLine();
		logs.readTable.print(query);
		String[] split = query.split(" ");
		if (split[0].equals("create") && split[1].equals("database")) {
			SQLDumpGenerator.dump(query);
		} else if (split[0].equals("create") && split[1].equals("table")) {
			SQLDumpGeneratorTable.dump(query);
		} else if(split[0].equals("insert")) {
			SQLDumpGeneratorTable.dump(query);
		}
		application obj = new application();
		obj.Application(query);
		takeinput();
	}
}
