/*
 * withdrawCash() --> reduces balance
 * make the sellStock method add to the balance of the customer
 */
import java.util.ArrayList;

public class Customer extends Account{
    private ArrayList<Stock> stocks;
    private double balance;


    public Customer(String userName, String password, StockMarket market){
        super(userName, password, market);
        this.stocks = new ArrayList<Stock>();
    }

    /**
     * compute the total value of all stocks
     * @return total value of stocks
     */
    public double computeTotalValue(){
        double totalValue = 0.0;
        for (Stock s: stocks){
            totalValue += s.getPrice() * s.getCount();
        }
        return totalValue;
    }

    /**
     * compute the total unrealized profit of this account
     * @return unrealized profit
     */
    public double computeUnrealizedProfit(){
        double totalProfit = 0.0;
        for (Stock s: stocks){
            totalProfit += s.getTotalValue() - (double)s.getCount()*getMarket().getPriceOf(s);
        }
        return totalProfit;
    }

    /**
     * buy a stock
     * @param stock
     * @param count
     */
    public void buyStock(Stock stock, int count){

        // manage balance
        balance -= stock.getPrice() * (double)count;

        // check for existing stock
        for(Stock s: stocks){
            if (s.equals(stock)){
                s.setCount(s.getCount()+stock.getCount());
                s.setTotalValue(s.getTotalValue() + stock.getTotalValue());
                break;
            }
        }

        // account does not has this stock yet
        Stock newStock = new Stock(stock, count);
        stocks.add(newStock);

    }

    /**
     * sell an existing stock if exist
     * @param stock
     * @param count
     * @return boolean success or failed
     */
    public boolean sellStock(Stock stock, int count){
        // check existing stocks
        for(Stock s: stocks){
            if (s.equals(stock)){
                if (s.getCount()<count){
                    System.err.printf("Customer: stock count not enough");
                    return false;
                } else {
                    // manage balance
                    balance += stock.getPrice() * (double)count;
                    s.setCount(s.getCount()-stock.getCount());
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
