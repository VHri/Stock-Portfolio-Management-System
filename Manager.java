
public class Manager extends Account {

    public Manager (String userName, String password){
        super(userName, password);
    }

    public boolean editStockPrice(StockMarket market, Stock s, double price){
        return market.setPriceOf(s, price);
    }    
    
}
