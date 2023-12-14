import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public static ArrayList<Stock> getCustomerStocks(String username) {
        ArrayList<Stock> stocks = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL)) {
            String sql = "SELECT * FROM CustomerStocks WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
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
    
    public static void changeStockPrice(String symbol, double newPrice) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "UPDATE Stocks SET price = ? WHERE symbol = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setDouble(1, newPrice);
                preparedStatement.setString(2, symbol);

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
                        double netGain = resultSet.getDouble("realized_profit");

                        if (isCustomer) {
                            return new Customer(username, password, balance, netGain);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static Customer getCustomerInfo(String username){
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
                        double netGain = resultSet.getDouble("realized_profit");

                        if (isCustomer) {
                            return new Customer(username, password, balance, netGain);
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

