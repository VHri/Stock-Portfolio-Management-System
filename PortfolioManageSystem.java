import java.util.ArrayList;

public class PortfolioManageSystem {
    // ArrayList<Customer> customers;
    Manager manager;
    StockMarket market;
    Customer currentCustomer;
    // ArrayList<Customer> superCustomers;

    private static final double SUPER_CUSTOMER_THRESHOLD = 10000.0;

    private static PortfolioManageSystem portfolioManageSystem;

    private PortfolioManageSystem() {

        // fetch from database
        manager = new Manager(null, null);
        market = new StockMarket();

    }

    /**
     * if a customer has a net gain over the threshold
     * 
     * @param c
     */
    public void checkCustomerGain(Customer c) {
        double gain = c.getNetGain();
        String status = Database.getAccountStatus(c.getUsername());
        if (gain >= SUPER_CUSTOMER_THRESHOLD && status != "Super Customer") {
            Database.changeAccountStatus(c.getUsername(), "Super Customer");
        }
    }

    // public int verifyUser(String username, String password ){
    // // check the database
    // return Constant.APPROVED_USER;
    // }

    public void addStock(Stock s) {
        market.addStock(s);
    }

    public StockMarket getMarket() {
        return market;
    }

    public void setMarket(StockMarket market) {
        this.market = market;
    }

    // below are database access methods

    public int verify(String username, String password) {

        Customer c = Database.getCustomer(username);
        if (c != null) {
            System.out.println("System: database returned customer " + c);
            if (c.getPassword().equals(password)) {

                if (Database.getAccountStatus(username).equals(Constant.UNAPPROVED_STATUS)) {
                    System.out.println("System: unapproved");
                    return Constant.UNAPPROVED_USER;
                }

                System.out.println("System: pw matched");
                return Constant.APPROVED_USER;
            } else {
                System.out.println("System: pw ! matched:\t" + password + " vs " + c.getPassword());
                return Constant.WRONG_PASSWORD;
            }
        } else {
            Manager m = Database.getManager(username);
            System.out.println("System: database returned manager " + m);
            if (m != null) {
                if (m.getPassword().equals(password)) {
                    System.out.println("System: pw matched");
                    return Constant.MANAGER;
                } else {
                    System.out.println("System: pw ! matched:\t" + password + " vs " + m.getPassword());
                    return Constant.WRONG_PASSWORD;
                }
            }
        }
        return Constant.UNKNOWN_USER;
    }

    /**
     * 
     * @param username
     * @param password
     * @return
     */
    public int login(String username, String password) {
        int identity = this.verify(username, password);
        switch (identity) {
            case Constant.APPROVED_USER:
                this.currentCustomer = Database.getCustomer(username);
                currentCustomer.setSystem(this);
                break;
            case Constant.MANAGER:
                this.manager = Database.getManager(username);
                break;
            default:
                break;
        }
        return identity;
    }

    public int signin(String username, String password) {
        int identity = this.verify(username, password);
        int result = Constant.FAILURE;
        switch (identity) {
            case Constant.APPROVED_USER:
                this.currentCustomer = Database.getCustomer(username);
                result = Constant.APPROVED_USER;
                break;
            case Constant.MANAGER:
                this.manager = Database.getManager(username);
                result = Constant.MANAGER;
                break;
            case Constant.UNKNOWN_USER:
                Database.addUser(username, password, "Unapproved Customer", 0, 0);
                result = Constant.SUCCESS;
                break;
            default:
                break;
        }
        return result;
    }

    public Customer getCustomer(String username) {
        return Database.getCustomer(username);
    }

    public ArrayList<Stock> getStocks() {
        return Database.getStocks();
    }

    public Manager getManager(String username) {
        return Database.getManager(username);
    }

    public boolean addUnApprovedCustomer(Customer c) {
        // TODO
        System.err.printf("System: To be implemented");
        return false;
    }

    public boolean approvedCustomer(Customer c) {
        // TODO
        System.err.printf("System: To be implemented");
        return false;
    }

    public boolean isSuperCustomer(Customer c) {
        return Database.getAccountStatus(c.getUsername()) == "Super Customer";
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    public static void writePersist() {
        // TODO: save customer, and market stock
    }

    public static void writeTransactions() {
        // TODO: save history
    }

    public static PortfolioManageSystem getSystem() {
        if (portfolioManageSystem == null) {
            portfolioManageSystem = new PortfolioManageSystem();
        }

        return portfolioManageSystem;
    }

}
