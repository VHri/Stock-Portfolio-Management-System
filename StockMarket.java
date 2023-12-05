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
}
