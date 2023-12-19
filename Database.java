import java.sql.Connection;
import java.sql.DatabaseMetaData;
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

    public static ArrayList<String> getColumnNames(String table) {
        ArrayList<String> columnNames = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL)) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getColumns(null, null, "CustomerStocks", null);
            while (resultSet.next()) {
                String columnName = resultSet.getString("COLUMN_NAME");
                columnNames.add(columnName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columnNames;
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

    public static double getBaselineStockPrice(String username, String symbol) {
        try (Connection connection = DriverManager.getConnection(URL)) {
            String sql = "SELECT * FROM CustomerStocks WHERE username = ? AND symbol = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, symbol);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        double baseline_price = resultSet.getDouble("baseline_price");

                        return baseline_price;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
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
        changeBalance(customer.getUsername(), customer.getBalance());
        changeNetGain(customer.getUsername(), customer.getNetGain());
        removeCustomerStock(customer.getUsername());
        for(Stock stock : customer.getStocks()) {
            addCustomerStock(customer, stock.getTickerSymbol(), stock.getCount(), stock.getPrice());
            changeBaselineStockPrice(customer.getUsername(), stock.getTickerSymbol(), stock.getBaselinePrice());
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

    public static void changeBaselineStockPrice(String username, String symbol, double newPrice) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "UPDATE CustomerStocks SET baseline_price = ? WHERE symbol = ? AND username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setDouble(1, newPrice);
                preparedStatement.setString(2, symbol);
                preparedStatement.setString(3, username);

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

    public static void changeStockSymbol(String symbol, double newSymbol) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "UPDATE Stocks SET price = ? WHERE symbol = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setDouble(1, newSymbol);
                preparedStatement.setString(2, symbol);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        public static void changeStockShare(String symbol, int newShares) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "UPDATE Stocks SET shares = ? WHERE symbol = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, newShares);
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

    public static ArrayList<String[]> getTransactions() {
        ArrayList<String[]> transactions = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL)) {
            String sql = "SELECT * FROM Transactions";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String username = resultSet.getString("username");
                        String symbol = resultSet.getString("symbol");
                        String timestamp = resultSet.getString("timestamp");
                        String shares = resultSet.getString("number_of_shares");
                        String price = resultSet.getString("price");
                        String status = resultSet.getString("status");
                        String[] array = {username, symbol, timestamp, shares, price, status};
                        transactions.add(array);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public static void addTransaction(String username, String symbol, int numberOfStocks, double boughtAt, String timestamp, String status) {
        try (Connection connection = DriverManager.getConnection(URL)) {
            String sql = "INSERT INTO Transactions (username, symbol, timestamp, number_of_shares, price, status) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, symbol);
                preparedStatement.setString(3, timestamp);
                preparedStatement.setInt(4, numberOfStocks);
                preparedStatement.setDouble(5, boughtAt);
                preparedStatement.setString(6, status);

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
    
    public static Manager getManager(String username){
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

    public static Customer getCustomer(String username){
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Accounts WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String password = resultSet.getString("password");
                        String status = resultSet.getString("status");
                        double balance = resultSet.getDouble("account_balance");
                        double netGain = resultSet.getDouble("net_gain");

                        if (!status.equals("Manager")) {
                            Customer c = new Customer(username, password, balance, netGain);
                            ArrayList<Stock> stocks = getCustomerStocks(username);
                            c.setStocks(stocks);
                            return c;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Customer> getCustomers(){
        ArrayList<Customer> customers = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Accounts";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String username = resultSet.getString("username");
                        String password = resultSet.getString("password");
                        double balance = resultSet.getDouble("account_balance");
                        double netGain = resultSet.getDouble("net_gain");

                        Customer c = new Customer(username, password, balance, netGain);
                        ArrayList<Stock> stocks = getCustomerStocks(username);
                        c.setStocks(stocks);
                        customers.add(c);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
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
                preparedStatement.setString(1, customer.getUsername());
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
                int rowsAffected = preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeStock(String symbol) {
        try (Connection connection = DriverManager.getConnection(URL)) {
            String sql = "DELETE FROM Stocks WHERE symbol = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, symbol);
                int rowsAffected = preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

