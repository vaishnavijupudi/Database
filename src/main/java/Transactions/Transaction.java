package Transactions;

import parser.selectExecutioner;
import user.application;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import logs.TransactionRecord;

public class Transaction {

	public void transactionProcess() throws IOException, InterruptedException {

		Scanner scanner = new Scanner(System.in);
		List<String> list = new ArrayList<>();
		System.out.println("Start Transaction:");
		while (true) {
			String str = scanner.nextLine();
			if (str.equals("commit;") || str.equals("commit")) {
				break;
			}
			if (str.equals("rollback;")) {
				list.clear();
				break;
			}
			list.add(str);
		}
		if (list.isEmpty()) {
			System.out.println("Transaction rolled back");
		} else {
			for (int i = 0; i < list.size(); i++) {
				application app = new application();
				System.out.println(list.get(i));
				app.Application(list.get(i));
				Thread.sleep(1000);
			}
			System.out.println("Transaction committed");
		}
     
		TransactionRecord transactionRecord = new TransactionRecord();
		transactionRecord.transactionLog(list);
	}

}
