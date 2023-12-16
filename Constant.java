public class Constant {

    /**
     * Status code
     */
    // account status
    public static final int APPROVED_USER = 1001;
    public static final int UNAPPROVED_USER = 1002;
    public static final int MANAGER = 1003;
    public static final int UNKNOW_USER = 1004;
    public static final int UNKNOWN_USER = 0;
    public static final int WRONG_PASSWORD = 1005;

    // general return
    public static final int SUCCESS = 2001;
    public static final int FAILURE = 2002;

    // purchase/sell status
    public static final int TOO_MANY_SHARE = 3001;
    public static final int STOCK_DNE = 3001;
    public static final int NOT_ENOUGH_BALANCE = 3001;

    /**
     * globles
     */
    public static final int MIN_USERNAME_LEN = 3;
    public static final int MIN_PW_LEN = 5;

    public static final int MAX_USERNAME_LEN = 20;
    public static final int MAX_PW_LEN = 20;

}
