/**
 * 
 */
package user;

import java.util.List;
import java.util.Scanner;

/**
 * @author Deeksha Sareen : This class is responsible for taking in the inputs from the user.
 *
 */
public class SwitchCase {

	private Scanner scanner;

	public int printSelection(List<String> selectionOptions) {
		int selected = -1;
		if (selectionOptions != null && !selectionOptions.isEmpty()) {
			for (int option = 0; option < selectionOptions.size(); option++) {
				System.out.println((option + 1) + ". " + selectionOptions.get(option));
			}
			System.out.println("Enter your selection below : ");
			scanner = new Scanner(System.in);
			if (scanner.hasNextInt()) {
				selected = scanner.nextInt();
			} else {
				System.err.println("INVALID_SELECTION");

				selected = printSelection(selectionOptions);
			}
		} else {
			System.err.println("INVALID_DISPLAY");
		}
		return selected;
	}
}
