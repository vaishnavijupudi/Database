package Resources;

/**
 * @author Yashvi Lad
 *
 */
public class UserID {

  private static String emailID;

  public UserID() {
  }

  private static UserID userID;

  public static void setUserID(String emailID) {
    if (userID == null) {
      userID = new UserID();
      userID.setUserID(emailID);
    }
  }
  public static UserID instance() {
    return userID;
  }

  public void setUserIDName(String emailID) {
    this.emailID = emailID;
  }

  public static String getUserID() {
    return emailID;
  }
}
