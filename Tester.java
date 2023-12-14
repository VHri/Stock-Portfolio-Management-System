import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.crypto.Data;

public class Tester {
    public static void testCustomerBasic(){
        
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
        assert c.getBalance() == balance - (double)purchaseCount*price;
        assert c.computeTotalValue() == (double)purchaseCount*price;
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

    public static void testLogin(){
        PortfolioManageSystem system = new PortfolioManageSystem();
        StockMarket stmkt = new StockMarket();
        stmkt.setStocks(system.getStocks());
        system.setMarket(stmkt);

        LoginGUI.run(system);


        
    }

    public static void testUnrealized(){
        PortfolioManageSystem system = new PortfolioManageSystem();
        StockMarket stmkt = new StockMarket();
        stmkt.setStocks(system.getStocks());
        system.setMarket(stmkt);
        //LoginGUI.run(system);

        String username = "johndoe";
        ArrayList<Stock> customerStocks = Database.getCustomerStocks(username);

        Customer john = Database.getCustomer(username);
        john.setStocks(customerStocks);


        System.out.printf("John's profit %f\n", john.computeUnrealizedProfit(stmkt));
        
        Database.changeStockPrice("LG", 113.4);

        System.out.printf("John's profit %f\n", john.computeUnrealizedProfit(stmkt));


    }
}
