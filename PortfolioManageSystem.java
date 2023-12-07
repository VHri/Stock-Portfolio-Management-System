import java.util.ArrayList;

public class PortfolioManageSystem {
    ArrayList<Customer> customers;
    Manager manager;
    StockMarket market;
    ArrayList<Customer> unapprovedCustomers;

    public PortfolioManageSystem(){
        ArrayList<Customer> customers = new ArrayList<Customer>();
        ArrayList<Customer> unapprovedCustomers = new ArrayList<Customer>();

        // fetch from database
        manager = new Manager(null, null);
        market = new StockMarket();

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


}
