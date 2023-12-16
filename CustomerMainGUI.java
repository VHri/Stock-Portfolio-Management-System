
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class CustomerMainGUI extends JFrame {

    private JButton balanceButton;
    private JButton stocksButton;
    private ArrayList<Stock> customerStockList;
    private ArrayList<Customer> customers;
    // private Double currentBalance;
    private ArrayList<Double> netValueHistory;
    private String[] stockColumnNames, customerColumnNames;
    private DefaultTableModel tableModel; // Declare the table model as an instance variable

    private Account account;
    private Customer customer;
    private JLabel balanceLabel;
    private JLabel netGainLabel;
    private JLabel numStocksOwnedLabel;

    private PortfolioManageSystem system;

    public CustomerMainGUI(PortfolioManageSystem system, String username) {

        this.system = system;

        // System.out.println(customerStockList.get(0));
        this.customer = Database.getCustomer(username);
        // this.customer = Database.getCustomerInfo(username);
        this.customerStockList = Database.getCustomerStocks(username);

        // Set up JFrame
        setTitle(username + " Info Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);

        // Create Buttons
        balanceButton = new JButton("View Stocks");
        balanceButton.addActionListener(this::handleStocksButtonClick);

        stocksButton = new JButton("Adjust Balance");
        stocksButton.addActionListener(this::handleBalanceButtonClick);

        add(stocksButton, BorderLayout.NORTH);
        add(balanceButton, BorderLayout.SOUTH);

        // System.out.println("CUSTOMER BALANCE: " + customer.getBalance());
        // Text fields for Customer General Info
        balanceLabel = new JLabel("Balance: " + customer.getBalance());
        balanceLabel.setBounds(10, 100, 150, 25); // x,y,width,height
        add(balanceLabel);

        netGainLabel = new JLabel("Net Gain: " + customer.getNetGain());
        netGainLabel.setBounds(10, 100, 150, 25);
        add(netGainLabel);

        numStocksOwnedLabel = new JLabel("Stocks owned: " + customer.getStocks().size());
        numStocksOwnedLabel.setBounds(10, 100, 150, 25);
        add(numStocksOwnedLabel);

        // Display the frame
        setVisible(true);
    }

    // Method for clearing frame
    public void clearJFrame() {
        remove(stocksButton);
        remove(balanceButton);
    }

    private void handleBalanceButtonClick(ActionEvent e) {

        JFrame newFrame = new CustomerBalanceGUI(customer);
        newFrame.setVisible(true);
        // frame.dispose();

        // this.clearJFrame(); // clear the frame

        // // Set up JFrame
        // setTitle("Adjust Balance");
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setSize(900, 400);

        // //Add balance label
        // JLabel addBalanceLabel = new JLabel("Deposit");
        // addBalanceLabel.setBounds(10,20,80,25); //x,y,width,height
        // add(addBalanceLabel);

        // JTextField addBalanceText = new JTextField(20);
        // addBalanceText.setBounds(100,20,165,25);
        // add(addBalanceText);
        // //CALL ADD BALANCE
        // String depositString = addBalanceText.getText();
        // Double deposit = 1.0*Integer.parseInt(depositString);
        // customer.deposit(deposit);

        // JLabel withdrawBalanceLabel = new JLabel("Withdraw Balance");
        // withdrawBalanceLabel.setBounds(10,20,80,25); //x,y,width,height
        // add(withdrawBalanceLabel);

        // JTextField withdrawBalanceText = new JTextField(20);
        // withdrawBalanceText.setBounds(100,20,165,25);
        // add(withdrawBalanceText);
        // //CALL WIDTHDRAW BALANCE
        // String withdrawString = withdrawBalanceText.getText();
        // Double withdraw = 1.0*Integer.parseInt(withdrawString);
        // customer.withdraw(withdraw);

        // // Repaint the frame to reflect the changes
        // revalidate();
        // repaint();
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

}
