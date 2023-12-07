public abstract class Account {
    private String userName;
    private String password;
    private StockMarket market;
    
    public Account(String userName, String password, StockMarket market) {
        this.userName = userName;
        this.password = password;
        this.market = market;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public StockMarket getMarket() {
        return market;
    }
    public void setMarket(StockMarket market) {
        this.market = market;
    }

    

    
}
