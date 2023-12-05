import java.util.ArrayList;

public class Customer extends Account{
    private ArrayList<Stock> stocks;
    public Customer(String userName, String password){
        super(userName, password);
        this.stocks = new ArrayList<Stock>();
    }
}
