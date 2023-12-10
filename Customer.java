/*
 * withdrawCash() --> reduces balance
 * make the sellStock method add to the balance of the customer
 */
import java.util.ArrayList;

public class Customer extends Account{

    private PortfolioManageSystem system;
    private ArrayList<Stock> stocks;
    private double balance;
    private double netGain;


    public Customer(String userName, String password, PortfolioManageSystem system){
        super(userName, password);
        balance = 0.0;
        netGain= 0.0;
        this.stocks = new ArrayList<Stock>();
        this.system = system;
    }

    public Customer(String userName, String password, double balance, PortfolioManageSystem system){
        super(userName, password);
        this.balance = balance;
        this.netGain = 0;
        this.stocks = new ArrayList<Stock>();
        this.system = system;
    }

    public Customer(String userName, String password, double balance){
        super(userName, password);
        this.balance = balance;
        this.netGain = 0;
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
    public double computeUnrealizedProfit(StockMarket stockMarket){
        double totalProfit = 0.0;
        for (Stock s: stocks){
            totalProfit += 
            (double)s.getCount()*stockMarket.getPriceOf(s) -s.getTotalValue();
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

        system.checkCustomerGain(this);

    }

    /**
     * sell an existing stock if exist
     * @param stock
     * @param count
     * @return boolean success or failed
     */
    public boolean sellStock(Stock stock, int count, StockMarket market){
        // check existing stocks
        for(Stock s: stocks){
            if (s.equals(stock)){
                if (s.getCount()<count){
                    System.err.printf("Customer: stock count not enough");
                    return false;
                } else {
                    // manage balance and net gain
                    double currentPrice = market.getPriceOf(stock);
                    balance += currentPrice * (double)count;
                    netGain += (currentPrice - stock.getPrice())* (double)count;
                    s.setCount(s.getCount()-stock.getCount());
                    s.setTotalValue(s.getTotalValue() - (double)count*stock.getPrice());
                    system.checkCustomerGain(this);
                    return true;
                }
            }
        }

        System.err.printf("Customer: stock not found");
        return false;
    }


    public void deposit(double value) {
        setBalance(this.balance + value);
    }

    public void withdraw(double value) { 
        if(value <= this.balance) {
            setBalance(this.balance - value);
        } else {
            System.out.println("Customer: Exceeds withdraw limit - negative balance illegal.");
        }
        
    }

    // getters and setters
    public void setStocks(ArrayList<Stock> stocks){
        this.stocks = stocks;
    }

    public ArrayList<Stock> getStocks(){
        return this.stocks;
    }

    private void setBalance(double balance){
        this.balance = balance;
    }

    public double getBalance(){
        return this.balance;
    }

    public double getNetGain(){
        return this.netGain;
    }

}
