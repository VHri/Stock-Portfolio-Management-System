
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class CustomerMainGUI extends PortfolioFrame {

    private JButton balanceButton;
    private JButton stocksButton;
    private JButton notificationButton;
    private JButton derivativeTradingButton;
    private JButton logoutButton;

    private Customer customer;
    private StockMarket stockMarket;
    private JLabel balanceLabel;
    private JLabel netGainLabel;
    private JLabel numStocksOwnedLabel;
    private JLabel unrealizedProfitLabel;

    private PortfolioManageSystem system;

    private static CustomerMainGUI customerMainGUI;

    private CustomerMainGUI(PortfolioManageSystem system, String username) {

        super(username + " Info Page");

        this.system = system;
        this.stockMarket = system.getMarket();

        // System.out.println(customerStockList.get(0));
        this.customer = Database.getCustomer(username);
        // this.customer = Database.getCustomerInfo(username);

        // Set up JFrame

        int topSpacing = 90;
        int spacing = 30;

        // Create Buttons
        int buttonWidth = 200;
        int buttonSpacing = spacing;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(Box.createRigidArea(new Dimension(0, topSpacing)));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        balanceButton = addButton("View Stocks", buttonPanel, buttonWidth, buttonSpacing);
        balanceButton.addActionListener(this::handleStocksButtonClick);

        stocksButton = addButton("Adjust Balance", buttonPanel, buttonWidth, buttonSpacing);
        stocksButton.addActionListener(this::handleBalanceButtonClick);

        notificationButton = addButton("View Notifications", buttonPanel, buttonWidth, buttonSpacing);
        notificationButton.addActionListener(this::handleNotificationButtonClick);

        derivativeTradingButton = addButton("Derivative Trading", buttonPanel, buttonWidth, buttonSpacing);
        derivativeTradingButton.addActionListener(this::handleDerivativeTradingButtonClick);
        derivativeTradingButton.setEnabled(this.customer.getNetGain() > 10000);

        logoutButton = addButton("Logout", buttonPanel, buttonWidth, buttonSpacing);
        logoutButton.addActionListener(e -> handleLogoutButtonClick());

        // setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        // System.out.println("CUSTOMER BALANCE: " + customer.getBalance());
        // Text fields for Customer General Info

        int textWidth = 200;
        int textSpacing = spacing;
        // DecimalFormat df = new DecimalFormat(Constant.DECIMAL_PATTERN);
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.add(Box.createRigidArea(new Dimension(0, topSpacing)));

        balanceLabel = addLabel("Username: " + customer.getUsername(), textPanel, textWidth, textSpacing);
        balanceLabel = addLabel("Balance: " + Constant.round(customer.getBalance()), textPanel, textWidth, textSpacing);

        unrealizedProfitLabel = addLabel(
                "Unrealized profit: " + Constant.round(customer.computeUnrealizedProfit(stockMarket)),
                textPanel, textWidth,
                textSpacing);

        netGainLabel = addLabel(
                "Realized profit: " + Constant.round(customer.getNetGain()),
                textPanel, textWidth,
                textSpacing);

        numStocksOwnedLabel = addLabel(
                "Stocks Owned: " + customer.getStocks().size(),
                textPanel,
                textWidth, textSpacing);

        JPanel mainPanel = new JPanel();
        // mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setLayout(new FlowLayout());
        // mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        mainPanel.add(textPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(50, 0)));
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
        JFrame newFrame = CustomerNotificationGUI.getFrame(customer, this);
        newFrame.setVisible(true);
        setVisible(false);
    }

    private void handleDerivativeTradingButtonClick(ActionEvent e) {
        JFrame newFrame = CustomerDerivativeAccountGUI.getFrame(customer, this);
        newFrame.setVisible(true);
        setVisible(false);
    }

    private void handleBalanceButtonClick(ActionEvent e) {

        JFrame newFrame = CustomerBalanceGUI.getFrame(system, this);
        newFrame.setVisible(true);
        setVisible(false);
    }

    private void handleStocksButtonClick(ActionEvent e) {
        CustomerStockGUI newFrame = CustomerStockGUI.getFrame(system, this);
        newFrame.updateTables();
        newFrame.setVisible(true);
        this.setVisible(false);
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
        LoginGUI.run(PortfolioManageSystem.getSystem()); // Open login GUI
        dispose(); // close current frame
    }

    public void updateLabels() {
        balanceLabel.setText("Balance: " + system.getCurrentCustomer().getBalance());
        netGainLabel.setText("Realized Profit: " + system.getCurrentCustomer().getNetGain());
        numStocksOwnedLabel.setText("Stocks Owned: " + system.getCurrentCustomer().getStocks().size());
        unrealizedProfitLabel.setText(
                "Unrealized Profit: " + system.getCurrentCustomer().computeUnrealizedProfit(system.getMarket()));

    }

    public static CustomerMainGUI getFrame(PortfolioManageSystem system, String username) {
        if (customerMainGUI == null) {
            customerMainGUI = new CustomerMainGUI(system, username);
        }
        return customerMainGUI;
    }

}
