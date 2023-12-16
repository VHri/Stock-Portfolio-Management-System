import java.sql.Connection;
import java.sql.SQLException;
public class Tester {
    public static void testCustomerBasic() {

        int totalCount = 100;
        int purchaseCount = 10;
        double price = 25.5;
        double balance = 9999999.0;

        PortfolioManageSystem system = new PortfolioManageSystem();

        StockMarket stmkt = new StockMarket();
        Stock s = new Stock("Test", "TT", price, totalCount);
        stmkt.addStock(s);
        Customer c = new Customer("TestUsr", "password", system);
        c.deposit(balance);
        assert c.getBalance() == balance;
        System.out.println("User balance is " + c.getBalance());
        System.out.println("Total value is " + c.computeTotalValue());

        c.buyStock(s, purchaseCount);
        assert c.getBalance() == balance - (double) purchaseCount * price;
        assert c.computeTotalValue() == (double) purchaseCount * price;
        System.out.println("User purchased stock ");
        System.out.println("User balance is " + c.getBalance());
        System.out.println("Total value is " + c.computeTotalValue());
    }

    public static void checkDBConnection() {
        try {
            // Get a connection to the database
            Connection connection = DatabaseConnection.getConnection();

            // Do something with the connection (e.g., query or update data)
            System.out.println("Connection successful!");

            // Close the connection when done
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void testLogin() {
        PortfolioManageSystem system = new PortfolioManageSystem();
        StockMarket stmkt = new StockMarket();
        stmkt.setStocks(system.getStocks());
        system.setMarket(stmkt);

        LoginGUI.run(system);

    }

    public static void testUnrealized() {
        PortfolioManageSystem system = new PortfolioManageSystem();
        StockMarket stmkt = new StockMarket();
        stmkt.setStocks(system.getStocks());
        system.setMarket(stmkt);
        // LoginGUI.run(system);

        String username = "johndoe";
        // ArrayList<Stock> customerStocks = Database.getCustomerStocks(username);

        Customer john = Database.getCustomer(username);
        // john.setStocks(customerStocks);

        // for (Stock s : john.getStocks()){
        // System.out.println(s);
        // }

        Database.changeStockPrice("LG", 113.4);

        System.out.printf("John's profit %f\n", john.computeUnrealizedProfit(stmkt));

        Database.changeStockPrice("LG", 153.4);

        System.out.printf("John's profit %f\n", john.computeUnrealizedProfit(stmkt));

        Database.changeStockPrice("LG", 113.4);

    }

    public static void testCustomerBuyStock() {
        PortfolioManageSystem system = new PortfolioManageSystem();
        StockMarket market = new StockMarket();
        market.setStocks(system.getStocks());
        system.setMarket(market);

        String username = "johndoe";
        Customer c = Database.getCustomer(username);

        system.login("johndoe", c.getPassword());

        Customer john = system.getCurrentCustomer();
        print("john's stocks");
        for (Stock s : john.getStocks()) {
            print(s);
        }

        print("");

        Stock lg = market.getStockBySymbol("LG");

        print("LG stocks: " + lg);

        john.buyStock(lg, 1);

        print("After purchase");

        print("john's stocks");
        for (Stock s : john.getStocks()) {
            print(s);
        }

    }

    public static void testRmvUserStock() {
        Database.removeCustomerStock("johndoe");
    }

    public static void testCustomerStockUI() {
        PortfolioManageSystem system = new PortfolioManageSystem();
        StockMarket stmkt = new StockMarket();
        stmkt.setStocks(system.getStocks());
        system.setMarket(stmkt);

        String username = "johndoe";
        Customer john = Database.getCustomer(username);

        system.login("johndoe", john.getPassword());

        CustomerStockGUI csg = new CustomerStockGUI(system, null);
    }

    public static void testChangeShare() {
        Database.changeStockShare("LG", 234);
    }

    public static void print(Object str) {
        System.out.println(str);
    }

}