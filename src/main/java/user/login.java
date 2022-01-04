package user;

import Resources.UserID;

import java.io.*;
import java.util.*;

/**
 * File: login.java
 *
 * @author Yashvi Lad Purpose: It contains logic for login and registration part
 * Description: This class will register user details for new user as
 * well as contains login logic
 */
public class login {

  FileWriter storeUserDetails;
  FileWriter mfaWriter;
  Map<String, String> map = new HashMap<String, String>();

  //this method gets all the details from user to register into the system
  public void register() throws IOException {

    Scanner input = new Scanner(System.in);

    System.out.println("Enter firstname:");
    String firstname = input.nextLine();

    if (firstname.matches("^[0-9]")) {
      System.out.println("Your input is not valid");
      return;
    }

    if (firstname == null || firstname.isEmpty()) {
      System.out.println("Input cannot be empty!");
      return;
    }

    System.out.println("Enter lastname:");
    String lastname = input.nextLine();

    if (lastname.matches("^[0-9]")) {
      System.out.println("Your input is not valid");
      return;
    }

    if (lastname == null || lastname.isEmpty()) {
      System.out.println("Input cannot be empty!");
      return;
    }

    System.out.println("Enter email:");
    String email = input.nextLine();

    if (email.matches("^[0-9]")) {
      System.out.println("Your input is not valid");
      return;
    }

    if (email == null || email.isEmpty()) {
      System.out.println("Input cannot be empty!");
      return;
    }

    if (email.matches("[^@.]")) {
      System.out.println("Email id cannot have any other special characters except @ and .");
    }

    System.out.println("Confirm email:");
    String confirmEmail = input.nextLine();

    if (confirmEmail.matches("^[0-9]")) {
      System.out.println("Your input is not valid");
      return;
    }

    if (confirmEmail == null || confirmEmail.isEmpty()) {
      System.out.println("Input cannot be empty!");
      return;
    }
    String reenterEmail = null;
    if (!email.equalsIgnoreCase(confirmEmail)) {
      System.out.println("Email entered does not match.");

      System.out.println("Please re-enter email:");
      reenterEmail = input.nextLine();

    }

    System.out.println("Enter password:");
    String password = input.nextLine();

    if (password.matches("^[0-9]")) {
      System.out.println("Your input is not valid");
      return;
    }

    if (password == null || password.isEmpty()) {
      System.out.println("Input cannot be empty!");
      return;
    }

    System.out.println("Confirm password:");
    String confirmPassword = input.nextLine();

    if (confirmPassword.matches("^[0-9]")) {
      System.out.println("Your input is not valid");
      return;
    }

    if (confirmPassword == null || confirmPassword.isEmpty()) {
      System.out.println("Input cannot be empty!");
      return;
    }
    String reenterPassword = null;
    if (!password.equals(confirmPassword)) {
      System.out.println("Password does not match");

      System.out.println("Please re-enter proper password");
      reenterPassword = input.nextLine();
    }

    System.out.println("Provide answers to security questions");
    System.out.println("Please remember the provided answers");

    System.out.println("Enter your favourite color");
    String color = input.nextLine();

    if (color.matches("^[0-9]")) {
      System.out.println("Your input is not valid");
      return;
    }

    if (color == null || color.isEmpty()) {
      System.out.println("Input cannot be empty!");
      return;
    }

    System.out.println("Enter your favourite animal");
    String animal = input.nextLine();

    if (animal.matches("^[0-9]")) {
      System.out.println("Your input is not valid");
      return;
    }

    if (animal == null || animal.isEmpty()) {
      System.out.println("Input cannot be empty!");
      return;
    }

    System.out.println("Enter your favourite food");
    String food = input.nextLine();

    if (food.matches("^[0-9]")) {
      System.out.println("Your input is not valid");
      return;
    }

    if (food == null || food.isEmpty()) {
      System.out.println("Input cannot be empty!");
      return;
    }

    storeUserDetails = new FileWriter("UserRegisteredDetails", true);
    storeUserDetails.write(System.lineSeparator());
    storeUserDetails.write("\nFirstname: " + firstname + "\n" + "Lastname: " + lastname);

    if (!email.equalsIgnoreCase(confirmEmail)) {
      storeUserDetails.write("\nEmailID: " + reenterEmail);
    } else {
      storeUserDetails.write("\nEmailID: " + confirmEmail);
    }

    System.out.println("\n");

    if (!password.equals(confirmPassword)) {
      storeUserDetails.write("\nPassword: " + reenterPassword);
    } else {
      storeUserDetails.write("\nPassword: " + confirmPassword);
    }

    mfaWriter = new FileWriter("mfaAnswers.txt", true);
    mfaWriter.write("\nEnter your favourite color:" + color + " " + email + "\n" + "Enter your favourite animal:" + animal + " " + email + "\n" + "Enter your favourite food:" + food + " " + email + "\n");
    mfaWriter.close();
    storeUserDetails.close();
  }

  static String email_id;
  static String answer;

  /*
   * this method contains logic that asks user to enter login credentials
   * It checks if the entered credentials are in record or not and allows users access grants based on it.
   * */
  public boolean login() throws IOException {

    Scanner input = new Scanner(System.in);
    System.out.println("Enter EmailID: ");
    email_id = input.nextLine();
    File file = new File("UserRegisteredDetails");
    BufferedReader br = new BufferedReader(new FileReader(file));
    String str;
    int count = 0;
    while ((str = br.readLine()) != null) {
      if (str.contains(email_id)) {
        input = new Scanner(System.in);

        System.out.println("Enter password: ");
        String password = input.nextLine();
        String str2;
        int count2 = 0;
        while ((str2 = br.readLine()) != null) {

          if (str2.contains(password)) {
            count2++;
            System.out.println("Please answer a security question");
            if (mfa() == true) {
              return true;
            } else {
              return false;
            }
          }
        }
        if (count2 == 0) {
          System.out.println("Incorrect password");
          return false;
        }
        count++;
      }
    }
    if (count == 0) {
      System.out.println("Incorrect email");
      return false;
    }
    return true;
  }

  /*
   * this method is called if the user entered credentials are correct to answer one random question-
   * as a Multi Factor Authentication
   * */
  public boolean mfa() throws IOException {

    Scanner input = new Scanner(System.in);
    String[] questions = new String[3];
    questions[0] = "Enter your favourite color ";
    questions[1] = "Enter your favourite animal ";
    questions[2] = "Enter your favourite food ";

    Random generator = new Random();
    Integer num = generator.nextInt(questions.length);

    System.out.println(questions[num]);
    answer = input.nextLine();

    File file = new File("mfaAnswers.txt");
    Scanner sc = new Scanner(file);
    sc.useDelimiter("\\Z");
    String line = sc.next();
    String[] data = line.split("\\r?\\n");

    for (int i = 0; i < data.length; i++) {
      if (data[i].contains(email_id)) {
        String[] a = data[i].split(":");
        String[] color = a[1].split(" ");
        map.put(a[0], color[0]);
      }
    }
    int count = 0;
    for (String key : map.keySet()) {
      if (map.containsValue(answer)) {
        count++;
      }
    }
    if (count == 0) {
      System.out.println("Login failed! Entered answer does not match");
      return false;
    } else {
      UserID userID = new UserID();
      userID.setUserIDName(email_id);
      System.out.println("Login successful!!");
      return true;
    }
  }

}
