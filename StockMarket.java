import java.util.ArrayList;

public class StockMarket{
    private ArrayList<Stock> stocks;

    public StockMarket(){
        this.stocks = new StockFactory().generateStocks();
    }

    public void setStocks(ArrayList<Stock> stocks){
        this.stocks = stocks;
    }

    public ArrayList<Stock> getStocks(){
        return this.stocks;
    }

    public double getPriceOf(Stock stock){
        for (Stock s: stocks){
            if(s.equals(stock)){
                return s.getPrice();
            }
        }
        System.err.println("Market: Stock not found");
        return 0.0;

    }

    public void addStock(Stock stock){
        this.stocks.add(stock);
    }
}
