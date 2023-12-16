import java.util.ArrayList;

import javax.xml.crypto.Data;

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
        // for (Stock s: stocks){
        //     if(s.equals(stock)){
        //         return s.getPrice();
        //     }
        // }
        // System.err.println("Market: Stock not found");
        // return 0.0;

        ArrayList<Stock> stocks = Database.getStocks();
        for (Stock s : stocks){
            //System.out.println("Checking Stock: " + s);
            if(s.equals(stock)){
                return s.getPrice();
            }
        }

        System.err.println("Market: Stock not found");
        return 0.0;
    }

    /**
     * update the price of a stock
     * @param stock
     * @param price
     * @return boolean success or fail
     */
    public boolean setPriceOf(Stock stock, double price){
        for (Stock s: stocks){
            if(s.equals(stock)){
                s.setPrice(price);
                return true;
            }
        }
        System.err.println("Market: Stock not found");
        return false;

    }

    /**
     * add a stock to the market. If the stock already exist, update the price and count
     * @param stock
     */
    public void addStock(Stock stock){
        for (Stock s: stocks){
            if(s.equals(stock)){
                s.setPrice(stock.getPrice());
                s.setCount(s.getCount() + stock.getCount());
                return;
            }
        }

        this.stocks.add(stock);
    }

    public Stock getStockBySymbol(String symbol){
        for (Stock s: stocks){
            if(s.getTickerSymbol().equals(symbol)){
                return s;
            }
        }
        return null;
    }

    
}
