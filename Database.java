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
                        int shares = resultSet.getInt("number_of_shares");
                        double price = resultSet.getDouble("baseline_price");
                        stocks.add(new Stock(Database.getStockCompany(symbol), symbol, price, shares));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stocks;
    }

    public static String getStockCompany(String symbol) {
        try (Connection connection = DriverManager.getConnection(URL)) {
            String sql = "SELECT * FROM Stocks WHERE symbol = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, symbol);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String company = resultSet.getString("company");

                        return company;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getAccountNetGain(String username) {
        try (Connection connection = DriverManager.getConnection(URL)) {
            String sql = "SELECT * FROM Accounts WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String status = resultSet.getString("net_gain");

                        return status;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static double getAccountBalance(String username) {
        try (Connection connection = DriverManager.getConnection(URL)) {
            String sql = "SELECT * FROM Accounts WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        double accountBalance = resultSet.getDouble("account_balance");

                        return accountBalance;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public static String getAccountStatus(String username) {
        try (Connection connection = DriverManager.getConnection(URL)) {
            String sql = "SELECT * FROM Accounts WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String status = resultSet.getString("status");

                        return status;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getPassword(String username) {
        try (Connection connection = DriverManager.getConnection(URL)) {
            String sql = "SELECT * FROM Accounts WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String password = resultSet.getString("password");

                        return password;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Updates all customer data
    public static void updateCustomerData(Customer customer) {
        changeBalance(customer.getUserName(), customer.getBalance());
        changeNetGain(customer.getUserName(), customer.getNetGain());
        removeCustomerStock(customer.getUserName());
        for(Stock stock : customer.getStocks()) {
            addCustomerStock(customer, stock.getTickerSymbol(), stock.getCount(), stock.getPrice());
        }
    }

    public static void changeAccountStatus(String username, String status) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "UPDATE Accounts SET status = ? WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, status);
                preparedStatement.setString(2, username);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public static void changeNetGain(String username, double newNetGain) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "UPDATE Accounts SET net_gain = ? WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setDouble(1, newNetGain);
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

    public static void addUser(String username, String password, String status, double accountBalance, double netGain) {
        try (Connection connection = DriverManager.getConnection(URL)) {
            String sql = "INSERT INTO Accounts (username, password, status, account_balance, net_gain) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, status);
                preparedStatement.setDouble(4, accountBalance);
                preparedStatement.setDouble(5, netGain);

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
                        String status = resultSet.getString("status");

                        if (status.equals("Manager")) {
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
                       //String name = resultSet.getString("name");
                        //String name = resultSet.getString("");
                        String password = resultSet.getString("password");
                        String status = resultSet.getString("status");
                        double balance = resultSet.getDouble("account_balance");
                       // double netGain = resultSet.getDouble("realized_profit");
                        double netGain = resultSet.getDouble("net_gain");

                        if (status.equals("Customer") || status.equals("Super Customer")) {
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

    public static void addCustomerStock(Customer customer, String symbol, int number_of_shares, double price) {
        try (Connection connection = DriverManager.getConnection(URL)) {
            String sql = "INSERT INTO CustomerStocks (username, symbol, number_of_shares, baseline_price) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, customer.getUserName());
                preparedStatement.setString(2, symbol);
                preparedStatement.setInt(3, number_of_shares);
                preparedStatement.setDouble(4, price);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeCustomerStock(String username) {
        try (Connection connection = DriverManager.getConnection(URL)) {
            String sql = "DELETE FROM CustomerStocks WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

