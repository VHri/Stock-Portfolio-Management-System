import java.util.ArrayList;

public class Manager extends Account {

    private ArrayList<Customer> customers;


    public Manager (String userName, String password, StockMarket market){
        super(userName, password, market);
    }

    public boolean editStockPrice(Stock s, double price){
        return getMarket().setPriceOf(s, price);
    }

    public void addStock(Stock s){
        getMarket().addStock(s);
    }    

    public boolean approveCustomer(Customer c){
        if(!customers.contains(c)){
            customers.add(c);
            return true;
        }
        System.err.println("Manager: customer not found or already approved");
        return false;
    }
    
}
