
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class CustomerMainGUI extends PortfolioFrame {

    private JButton balanceButton;
    private JButton stocksButton;
    private JButton notificationButton;
    private JButton derivativeTradingButton;
    private JButton logoutButton;
    private ArrayList<Stock> customerStockList;
    private ArrayList<Customer> customers;
    // private Double currentBalance;
    private ArrayList<Double> netValueHistory;
    private String[] stockColumnNames, customerColumnNames;
    private DefaultTableModel tableModel; // Declare the table model as an instance variable

    private Account account;
    private Customer customer;
    private StockMarket stockMarket;
    private JLabel balanceLabel;
    private JLabel netGainLabel;
    private JLabel numStocksOwnedLabel;
    private JLabel unrealizedProfitLabel;

    private PortfolioManageSystem system;

    public CustomerMainGUI(PortfolioManageSystem system, String username) {

        super(username + " Info Page");

        this.system = system;
        this.stockMarket = system.getMarket();

        // System.out.println(customerStockList.get(0));
        this.customer = Database.getCustomer(username);
        // this.customer = Database.getCustomerInfo(username);
        this.customerStockList = Database.getCustomerStocks(username);

        // Set up JFrame

        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setSize(400, 400);

        // Create Buttons
        int buttonWidth = 200;
        int buttonSpacing = 20;
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        balanceButton = addButton("View Stocks", buttonPanel, buttonWidth, buttonSpacing);
        balanceButton.addActionListener(this::handleStocksButtonClick);

        stocksButton = addButton("Adjust Balance", buttonPanel, buttonWidth, buttonSpacing);
        stocksButton.addActionListener(this::handleBalanceButtonClick);

        notificationButton = addButton("View Notifications", buttonPanel, buttonWidth, buttonSpacing);
        notificationButton.addActionListener(this::handleNotificationButtonClick);

        if (this.customer.getNetGain() > 10000) {
            derivativeTradingButton = addButton("Derivative Trading", buttonPanel, buttonWidth, buttonSpacing);
            derivativeTradingButton.addActionListener(this::handleDerivativeTradingButtonClick);
        }

        logoutButton = addButton("Logout", buttonPanel, buttonWidth, buttonSpacing);
        logoutButton.addActionListener(e -> handleLogoutButtonClick());

        // setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        // System.out.println("CUSTOMER BALANCE: " + customer.getBalance());
        // Text fields for Customer General Info

        int textWidth = 200;
        int textSpacing = 20;
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        balanceLabel = addLabel("Balance: " + customer.getBalance(), textPanel, textWidth, textSpacing);

        unrealizedProfitLabel = addLabel(
                "Unrealized profit: " + customer.computeUnrealizedProfit(stockMarket),
                textPanel, textWidth,
                textSpacing);

        netGainLabel = addLabel("Realized profit: " + customer.getNetGain(),
                textPanel, textWidth,
                textSpacing);

        numStocksOwnedLabel = addLabel("Stocks Owned: " +
                customer.getStocks().size(), textPanel,
                textWidth, textSpacing);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        mainPanel.add(textPanel);
        mainPanel.add(buttonPanel);

        add(mainPanel);
        // Display the frame
        setVisible(true);
    }

    // Method for clearing frame
    public void clearJFrame() {
        remove(stocksButton);
        remove(balanceButton);
    }

    private void handleNotificationButtonClick(ActionEvent e) {
        JFrame newFrame = new CustomerNotificationGUI(customer, this);
        newFrame.setVisible(true);
        setVisible(false);
    }

    private void handleDerivativeTradingButtonClick(ActionEvent e) {
        JFrame newFrame = new CustomerDerivativeAccountGUI(customer, this);
        newFrame.setVisible(true);
        setVisible(false);
    }

    private void handleBalanceButtonClick(ActionEvent e) {

        JFrame newFrame = new CustomerBalanceGUI(system, this);
        newFrame.setVisible(true);
        setVisible(false);
    }

    private void handleStocksButtonClick(ActionEvent e) {
        JFrame newFrame = new CustomerStockGUI(system, this);
        newFrame.setVisible(true);
        this.setVisible(false);
    }

    private void handleSubmitButtonClick(JTextField stockSymbolTextField, JTextField newStockPriceTextField) {
        // updating the stock price
        for (Stock i : this.customerStockList) {
            if (i.getTickerSymbol().equalsIgnoreCase(stockSymbolTextField.getText())) {
                i.setPrice(Double.parseDouble(newStockPriceTextField.getText()));
            }
        }
        // Update the table model with the modified data
        tableModel.setDataVector(getTableFormattedStockData(customerStockList), stockColumnNames);

        for (Stock i : this.customerStockList) {
            System.out.printf("%s: %f%n", i.getTickerSymbol(), i.getPrice());
        }
    }

    public Object[][] getTableFormattedStockData(ArrayList<Stock> stockList) {
        Object[][] data = new Object[stockList.size()][4];
        for (int i = 0; i < stockList.size(); i++) {
            Stock stock = stockList.get(i);
            data[i][0] = stock.getTickerSymbol();
            data[i][1] = stock.getName();
            data[i][2] = stock.getCount();
            data[i][3] = stock.getPrice();
        }
        return data;
    }

    private void handleLogoutButtonClick() {
        System.out.println("Manager Logout");
        LoginGUI.run(new PortfolioManageSystem()); // Open login GUI
        dispose(); // close current frame
    }

    public void updateLabels() {
        balanceLabel.setText("Balance: " + system.getCurrentCustomer().getBalance());
        netGainLabel.setText("Realized Profit: " + system.getCurrentCustomer().getNetGain());
        numStocksOwnedLabel.setText("Stocks Owned: " + system.getCurrentCustomer().getStocks().size());
        unrealizedProfitLabel.setText(
                "Unrealized Profit: " + system.getCurrentCustomer().computeUnrealizedProfit(system.getMarket()));

    }

}
