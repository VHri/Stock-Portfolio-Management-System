public class Stock {
    private final String name;
    private final String tickerSymbol;
    private double price;
    private int cnt;
    private double totalValue;

    public Stock(String name, String tickerSymbol){
        this.name = name;
        this.tickerSymbol = tickerSymbol;
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
    public int getCnt() {
        return cnt;
    }
    public void setCnt(int cnt) {
        this.cnt = cnt;
    }
    public double getTotalValue() {
        return totalValue;
    }
    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    

}
