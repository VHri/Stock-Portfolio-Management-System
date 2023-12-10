import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String URL = "jdbc:sqlite:data/cs611.db";

    public static List<Stock> getStocks() {
        List<Stock> stocks = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL)) {
            String sql = "SELECT * FROM Stocks";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String symbol = resultSet.getString("symbol");
                        String company = resultSet.getString("company");
                        int shares = resultSet.getInt("shares");
                        double price = resultSet.getDouble("price");

                        stocks.add(new Stock(company, symbol, price, shares));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stocks;
    }

    public static void addTransaction(String username, String stockCompany, int numberOfStocks, double boughtAt, String timestamp) {
        try (Connection connection = DriverManager.getConnection(URL)) {
            String sql = "INSERT INTO Transactions (stock_company, account_username, timestamp, number_of_shares, bought_at) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, stockCompany);
                preparedStatement.setString(2, username);
                preparedStatement.setString(3, timestamp);
                preparedStatement.setInt(4, numberOfStocks);
                preparedStatement.setDouble(5, boughtAt);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addUser(String username, String name, String password, boolean isCustomerAccount, double accountBalance) {
        try (Connection connection = DriverManager.getConnection(URL)) {
            String sql = "INSERT INTO Accounts (username, name, password, customer_account, account_balance) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, password);
                preparedStatement.setBoolean(4, isCustomerAccount);
                preparedStatement.setDouble(5, accountBalance);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addStock(String symbol, String company, int shares, double price) {
        try (Connection connection = DriverManager.getConnection(URL)) {
            String sql = "INSERT INTO Stocks (symbol, company, shares, price) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, symbol);
                preparedStatement.setString(2, company);
                preparedStatement.setInt(3, shares);
                preparedStatement.setDouble(4, price);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// import java.util.ArrayList;

// public class Database {
//     public Database(){

//     }

//     public static Account getUser(String username){
//         return new Customer(username, "password", 9999999999.0);
//     }

//     public static ArrayList<Stock> getMarketStocks(){
//         ArrayList<Stock> stocks = new ArrayList<Stock>();

//         stocks.add(new Stock("Stock1", "S1", 13.5, 100));
//         stocks.add(new Stock("Stock2", "S2", 15.7, 200));
//         stocks.add(new Stock("Stock3", "S3", 135.9, 300));
//         stocks.add(new Stock("Stock4", "S4", 0.1, 400));
//         return stocks;
//     }

// }
