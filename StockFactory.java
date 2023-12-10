import java.util.ArrayList;

public class StockFactory {

    public Stock createStock(String name, String tickerSymbol, double price, int count){
        return new Stock(name, tickerSymbol, price, count);
    }

    // return an arraylist of stocks to be added to the Stock Market fetched from the database
    public ArrayList<Stock> generateStocks(){
        return Database.getStocks();
    }

}
