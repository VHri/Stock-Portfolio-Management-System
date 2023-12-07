import java.util.ArrayList;

public class Manager extends Account {

    private ArrayList<Customer> customers;


    public Manager (String userName, String password){
        super(userName, password);
    }

    public boolean editStockPrice(StockMarket market, Stock s, double price){
        return market.setPriceOf(s, price);
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
