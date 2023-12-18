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

    // customer status static strings
    public static final String CUSTOMER_STATUS = "Customer";
    public static final String MANAGER_STATUS = "Manager";
    public static final String UNAPPROVED_STATUS = "Unapproved Customer";
    public static final String SUPERUSER_STATUS = "Super Customer";

    // button icons image path
    public static final String CUSTOMER_VIEW_APPROVE_BUTTON_IMG_PATH = "./img/customer-management.png";
    public static final String STOCKS_EDIT_BUTTON_IMG_PATH = "./img/stocks-edit.png";
    public static final String NOTIFICATION_BUTTON_IMG_PATH = "./img/notification.png";
    public static final String LOGOUT_BUTTON_IMG_PATH = "./img/logout-icon.png";

    public static double SUPER_CUSTOMER_PROFIT_THRESHOLD = 10000.00;

    public static double WINDOW_RATIO = 0.6;
    public static double LOGIN_WINDOW_RATIO = 0.3;
    public static int GLOBAL_FONT_SIZE = 15;
}
