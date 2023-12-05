public class Stock {
    private final String name;
    private final String tickerSymbol;
    private double price;
    private int count;
    private double totalValue;

    public Stock(String name, String tickerSymbol){
        this.name = name;
        this.tickerSymbol = tickerSymbol;
    }
    public Stock(String name, String tickerSymbol, double price, int count){
        this.name = name;
        this.tickerSymbol = tickerSymbol;
        this.price = price;
        this.count = count;
        this.totalValue = this.price * (double)this.count;
    }
    public String getName() {
        return name;
    }
    public String getTickerSymbol() {
        return tickerSymbol;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getCoutnt() {
        return count;
    }
    public void setCount(int cnt) {
        this.count = cnt;
    }
    public double getTotalValue() {
        return totalValue;
    }
    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    

}
