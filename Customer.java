
/*
 * withdrawCash() --> reduces balance
 * make the sellStock method add to the balance of the customer
 */
import java.util.ArrayList;

public class Customer extends Account {

    private PortfolioManageSystem system;
    private ArrayList<Stock> stocks;
    private double balance;
    private double netGain;

    public Customer(String userName, String password, PortfolioManageSystem system) {
        super(userName, password);
        balance = 0.0;
        netGain = 0.0;
        this.stocks = new ArrayList<Stock>();
        this.system = system;
    }

    public Customer(String userName, String password, double balance, PortfolioManageSystem system) {
        super(userName, password);
        this.balance = balance;
        this.netGain = 0;
        this.stocks = new ArrayList<Stock>();
        this.system = system;
    }

    public Customer(String userName, String password, double balance) {
        super(userName, password);
        this.balance = balance;
        this.netGain = 0;
        this.stocks = new ArrayList<Stock>();
    }

    public Customer(String userName, String password, double balance, double netGain) {
        super(userName, password);
        this.balance = balance;
        this.netGain = netGain;
        this.stocks = Database.getCustomerStocks(userName);
    }

    /**
     * compute the total value of all stocks
     * 
     * @return total value of stocks
     */
    public double computeTotalValue() {
        double totalValue = 0.0;
        for (Stock s : stocks) {
            totalValue += s.getPrice() * s.getCount();
        }
        return totalValue;
    }

    /**
     * compute the total unrealized profit of this account
     * 
     * @return unrealized profit
     */
    public double computeUnrealizedProfit(StockMarket stockMarket) {
        double totalProfit = 0.0;
        for (Stock s : stocks) {
            totalProfit += (double) s.getCount() * stockMarket.getPriceOf(s) - s.getTotalValue();
        }
        return totalProfit;
    }

    /**
     * buy a stock
     * 
     * @param stock
     * @param count
     */
    public int buyStock(Stock stock, int count) {

        double cost = stock.getPrice() * (double) count;

        if (cost > balance) {
            return Constant.NOT_ENOUGH_BALANCE;
        }

        int tradeStatus = system.getMarket().sellStock(stock, count);

        if (tradeStatus != Constant.SUCCESS) {
            return tradeStatus;
        }
        // manage balance
        balance -= stock.getPrice() * (double) count;

        // check for existing stock
        for (Stock s : stocks) {
            if (s.equals(stock)) {
                Tester.print(s + " == " + stock);
                s.setCount(s.getCount() + count);
                s.setTotalValue(s.getTotalValue() + cost);
                Database.removeCustomerStock(getUsername());
                Database.updateCustomerData(this);
                system.checkCustomerGain(this);
                return Constant.SUCCESS;
            }
        }

        // account does not has this stock yet
        Stock newStock = new Stock(stock, count);
        stocks.add(newStock);
        Database.removeCustomerStock(getUsername());
        Database.updateCustomerData(this);

        // check realized
        system.checkCustomerGain(this);

        return Constant.SUCCESS;

    }

    /**
     * sell an existing stock if exist
     * 
     * @param stock
     * @param count
     * @return boolean success or failed
     */
    public int sellStock(Stock stock, int count, StockMarket market) {


        // check existing stocks
        for (Stock s : stocks) {
            if (s.equals(stock)) {
                if (s.getCount() < count) {
                    Tester.print("Customer: stock count not enough");
                    return Constant.TOO_MANY_SHARE;
                } else {

                    int tradeStatus = system.getMarket().buyStock(stock, count);

                    if (tradeStatus != Constant.SUCCESS) {
                        Tester.print("Customer: stock market trade failed");
                        return tradeStatus;
                    }
                    // manage balance and net gain
                    Tester.print("Customer: stock count enough: " + s.getCount());

                    // Tester.print("Customer: bought in value " + s.getCount());
                    double currentPrice = market.getPriceOf(stock);
                    balance += currentPrice * (double) count;
                    netGain += currentPrice * (double) count - s.getTotalValue();

                    Tester.print("Customer: set netgain to: " + netGain);
                    s.setCount(s.getCount() - count);
                    s.setTotalValue(s.getTotalValue() - (double) count * stock.getPrice());

                    if (s.getCount() == 0) {
                        stocks.remove(s);
                    }

                    // update in database
                    Database.updateCustomerData(this);

                    // check realized
                    system.checkCustomerGain(this);
                    return Constant.SUCCESS;
                }
            }
        }

        Tester.print("Customer: stock not found");
        return Constant.STOCK_DNE;
    }

    public void deposit(double value) {
        setBalance(this.balance + value);
        Database.updateCustomerData(this);
    }

    public void withdraw(double value) {
        if (value <= this.balance) {
            setBalance(this.balance - value);
            Database.updateCustomerData(this);
        } else {
            Tester.print("Customer: Exceeds withdraw limit");
        }
    }

    // getters and setters
    public void setStocks(ArrayList<Stock> stocks) {
        this.stocks = stocks;
    }

    public ArrayList<Stock> getStocks() {
        return this.stocks;
    }

    private void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return this.balance;
    }

    public double getNetGain() {

        return this.netGain;
    }

    public PortfolioManageSystem getSystem() {
        return system;
    }

    public void setSystem(PortfolioManageSystem system) {
        this.system = system;
    }

}
