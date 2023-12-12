import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class Database {
    private static final String URL = "jdbc:sqlite:data/cs611.db";

    public static ArrayList<Stock> getStocks() {
        ArrayList<Stock> stocks = new ArrayList<>();
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

    public static void changeStockPrice(String company, double newPrice) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "UPDATE Stocks SET price = ? WHERE company = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setDouble(1, newPrice);
                preparedStatement.setString(2, company);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void changeBalance(String username, double newBalance) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "UPDATE Accounts SET account_balance = ? WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setDouble(1, newBalance);
                preparedStatement.setString(2, username);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    private static int getNumberOfShares(String username, String company, String timestamp) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Query to retrieve number of shares from CustomerStocks
            String numberOfSharesSql = "SELECT number_of_shares FROM CustomerStocks WHERE username = ? AND symbol = ? AND timestamp = ?";
            try (PreparedStatement numberOfSharesStatement = connection.prepareStatement(numberOfSharesSql)) {
                numberOfSharesStatement.setString(1, username);
                numberOfSharesStatement.setString(2, company);
                numberOfSharesStatement.setString(3, timestamp);

                try (ResultSet numberOfSharesResultSet = numberOfSharesStatement.executeQuery()) {
                    if (numberOfSharesResultSet.next()) {
                        return numberOfSharesResultSet.getInt("number_of_shares");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0; // Return 0 if there is an issue retrieving the number of shares
    }
 
    public static void buyStock(Customer customer, String company, int numOfShares) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Get customer username
            String username = customer.getUserName();

            // Get stock symbol and price from Stocks table
            String stockSymbol;
            double boughtAt;
            String stockInfoSql = "SELECT symbol, price FROM Stocks WHERE company = ?";
            try (PreparedStatement stockInfoStatement = connection.prepareStatement(stockInfoSql)) {
                stockInfoStatement.setString(1, company);

                try (ResultSet stockInfoResultSet = stockInfoStatement.executeQuery()) {
                    if (stockInfoResultSet.next()) {
                        stockSymbol = stockInfoResultSet.getString("symbol");
                        boughtAt = stockInfoResultSet.getDouble("price");
                    } else {
                        throw new SQLException("Stock not found: " + company);
                    }
                }
            }

            // Format timestamp as a string with date and time
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            // Add occurrence to CustomerStocks
            String customerStocksSql = "INSERT INTO CustomerStocks (username, symbol, timestamp) VALUES (?, ?, ?)";
            try (PreparedStatement customerStocksStatement = connection.prepareStatement(customerStocksSql)) {
                customerStocksStatement.setString(1, username);
                customerStocksStatement.setString(2, stockSymbol);
                customerStocksStatement.setString(3, timestamp);

                customerStocksStatement.executeUpdate();
            }
            // Add transaction
            addTransaction(username, company, numOfShares, boughtAt, timestamp);

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
    
    public static Account getManager(String username){
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Accounts WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String password = resultSet.getString("password");
                        boolean isCustomer = resultSet.getBoolean("customer_account");

                        if (!isCustomer) {
                            return new Manager(username, password);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Account getCustomer(String username){
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Accounts WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String name = resultSet.getString("name");
                        String password = resultSet.getString("password");
                        boolean isCustomer = resultSet.getBoolean("customer_account");
                        double balance = resultSet.getDouble("account_balance");

                        if (isCustomer) {
                            return new Customer(username, password, balance);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

