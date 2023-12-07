import java.util.ArrayList;

public class Database {
    public Database(){

    }

    public static Account getUser(String username){
        return new Customer(username, "password", 9999999999.0);
    }

    public static ArrayList<Stock> getMarketStocks(){
        ArrayList<Stock> stocks = new ArrayList<Stock>();

        stocks.add(new Stock("Stock1", "S1", 13.5, 100));
        stocks.add(new Stock("Stock2", "S2", 15.7, 200));
        stocks.add(new Stock("Stock3", "S3", 135.9, 300));
        stocks.add(new Stock("Stock4", "S4", 0.1, 400));
        return stocks;
    }

}
