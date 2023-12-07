/*
 * withdrawCash() --> reduces balance
 * make the sellStock method add to the balance of the customer
 */
import java.util.ArrayList;

public class Customer extends Account{
    private ArrayList<Stock> stocks;
    private double balance; //cash amount

    public Customer(String userName, String password){
        super(userName, password);
        this.stocks = new ArrayList<Stock>();
    }

    public double computeTotalValue(){
        double totalValue = 0.0;
        for (Stock s: stocks){
            totalValue += s.getTotalValue();
        }
        return totalValue;
    }

    

    public void buyStock(Stock stock){
        for(Stock s: stocks){
            if (s.getName() == stock.getName()){
                s.setCount(s.getCoutnt()+stock.getCoutnt());
                s.setTotalValue(s.getTotalValue() + stock.getTotalValue());
                break;
            }
        }

        stocks.add(stock);
    }

    public boolean sellStock(Stock stock, int count){
        for(Stock s: stocks){
            if (s.getName() == stock.getName()){
                if (s.getCoutnt()<count){
                    System.err.printf("Customer: stock count not enough");
                    return false;
                } else {
                    s.setCount(s.getCoutnt()-stock.getCoutnt());
                    s.setTotalValue(s.getTotalValue() - (double)count*stock.getPrice());
                    return true;
                }
            }
        }

        System.err.printf("Customer: stock not found");
        return false;
    }

    // getters and setters
    public void setStocks(ArrayList<Stock> stocks){
        this.stocks = stocks;
    }

    public ArrayList<Stock> getStocks(){
        return this.stocks;
    }

    public void setBalance(double balance){
        this.balance = balance;
    }

    public void addCash(double cash) {
        setBalance(this.balance + cash);
    }

    public void withdrawCash(double cash) { 
        if(cash <= this.balance) {
            setBalance(this.balance - cash);
        } else {
            System.out.println("Exceeds withdraw limit - negative balance illegal.");
        }
        
    }

    public double getBalance(){
        return this.balance;
    }

}
