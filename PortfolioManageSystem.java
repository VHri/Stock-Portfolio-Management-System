import java.util.ArrayList;

public class PortfolioManageSystem {
    ArrayList<Customer> customers;
    Manager manager;
    StockMarket market;
    ArrayList<Customer> unapprovedCustomers;
    ArrayList<Customer> superCustomers;

    private static final double SUPER_CUSTOMER_THRESHOLD = 10000.0;

    public PortfolioManageSystem(){
        ArrayList<Customer> customers = new ArrayList<Customer>();
        ArrayList<Customer> unapprovedCustomers = new ArrayList<Customer>();

        // fetch from database
        manager = new Manager(null, null);
        market = new StockMarket();

    }

    public int verifyUser(String username, String password ){
        // check the database
        return Constant.APPROVED_USER;
    }

    public void addStock(Stock s){
        market.addStock(s);
    }

    public StockMarket getMarket() {
        return market;
    }
    public void setMarket(StockMarket market) {
        this.market = market;
    }

    /**
     * if a customer has a net gain over the threshold
     * @param c
     */
    public void checkCustomerGain(Customer c){
        double gain = c.getNetGain();
        if (gain >= SUPER_CUSTOMER_THRESHOLD && !superCustomers.contains(c)){
            superCustomers.add(c);
        }
    }

    public boolean addUnApprovedCustomer(Customer c){
        if (!customers.contains(c) && !unapprovedCustomers.contains(c)){
            unapprovedCustomers.add(c);
            return true;
        }
        System.err.printf("System: user already exist");
        return false;
    }

    public boolean approvedCustomer(Customer c){
        if (!customers.contains(c) && unapprovedCustomers.contains(c)){
            customers.add(c);
            unapprovedCustomers.remove(c);
            return true;
        }
        System.err.printf("System: user already approved or does not exist");
        return false;
    }

    public static void writePersist(){
        // TODO: save customer, and market stock
    }

    public static void writeTransactions(){
        // TODO: save history
    }

}
