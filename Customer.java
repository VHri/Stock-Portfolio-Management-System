import java.util.ArrayList;

public class Customer extends Account{
    private ArrayList<Stock> stocks;
    private double balance;

    public Customer(String userName, String password){
        super(userName, password);
        this.stocks = new ArrayList<Stock>();
    }

    public void setStocks(ArrayList<Stock> stocks){
        this.stocks = stocks;
    }

    public ArrayList<Stock> getStocks(){
        return this.stocks;
    }

    public void setBalance(double balance){
        this.balance = balance;
    }

    public double getBalance(){
        return this.balance;
    }

}
