import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class ManagerGUI extends JFrame {
    private JButton editStocksButton, viewCustomersButton;
    private ArrayList<Stock> stockList;
    private ArrayList<Customer> customers;
    private String[] stockColumnNames, customerColumnNames;
    private DefaultTableModel tableModel; // Declare the table model as an instance variable

    public ManagerGUI(ArrayList<Stock> stockList, String[] stockColumnNames, ArrayList<Customer> customers, String[] customerColumnNames) {
        this.stockList = stockList;
        this.stockColumnNames = stockColumnNames;
        this.customerColumnNames = customerColumnNames;
        this.customers = customers;
        // Set up JFrame
        setTitle("Manager GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);

        // Create the "Edit Stocks" button
        editStocksButton = new JButton("Edit Stocks");
        editStocksButton.addActionListener(this::handleEditStocksButtonClick);

        // Create the "View Customers" button
        viewCustomersButton = new JButton("View Customers");
        viewCustomersButton.addActionListener(this::handleViewCustomersButtonClick);

        add(editStocksButton, BorderLayout.NORTH);
        add(viewCustomersButton, BorderLayout.SOUTH);

        // Display the frame
        setVisible(true);
    }

    public void clearJFrame(){
        remove(editStocksButton);
        remove(viewCustomersButton);
    }

    private void handleViewCustomersButtonClick(ActionEvent e) {
        this.clearJFrame(); // clear the frame
        // Set up JFrame
        setTitle("Manager View Customers");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 400);
        // create table for viewing customer data
        tableModel = new DefaultTableModel(getTableFormattedCustomerData(this.customers), customerColumnNames);
        // Create the table using the model
        JTable stockTable = new JTable(tableModel);
        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(stockTable);
        // Create a panel for text fields and submit button
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));

        add(scrollPane, BorderLayout.CENTER);

        // Repaint the frame to reflect the changes
        revalidate();
        repaint();
    }

    private void handleEditStocksButtonClick(ActionEvent e) {
        // Remove the "Edit Stocks" button from the frame
        this.clearJFrame(); // clear the frame

        // Set up JFrame
        setTitle("Manager Stock Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 400);
        // Create a table model
        tableModel = new DefaultTableModel(getTableFormattedStockData(this.stockList), stockColumnNames);

        // Create the table using the model
        JTable stockTable = new JTable(tableModel);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(stockTable);

        // Create a panel for text fields and submit button
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));

        // Create and add text fields
        JTextField stockSymbolTextField = new JTextField();
        JTextField newStockPriceTextField = new JTextField();
        inputPanel.add(new JLabel("Stock Symbol:"));
        inputPanel.add(stockSymbolTextField);
        inputPanel.add(Box.createRigidArea(new Dimension(6, 0))); // Add some spacing
        inputPanel.add(new JLabel("New Stock Price:"));
        inputPanel.add(newStockPriceTextField);

        // Create and add the submit button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e2 -> handleSubmitButtonClick(stockSymbolTextField, newStockPriceTextField));
        inputPanel.add(Box.createRigidArea(new Dimension(6, 0))); // Add some spacing
        inputPanel.add(submitButton);

        // Add the scroll pane and input panel to the frame
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        // Repaint the frame to reflect the changes
        revalidate();
        repaint();
    }

    private void handleSubmitButtonClick(JTextField stockSymbolTextField, JTextField newStockPriceTextField) {
        // updating the stock price
        for (Stock i : this.stockList) {
            if (i.getTickerSymbol().equalsIgnoreCase(stockSymbolTextField.getText())) {
                i.setPrice(Double.parseDouble(newStockPriceTextField.getText()));
            }
        }
        // Update the table model with the modified data
        tableModel.setDataVector(getTableFormattedStockData(stockList), stockColumnNames);

        for (Stock i : this.stockList) {
            System.out.printf("%s: %f%n", i.getTickerSymbol(), i.getPrice());
        }
    }

    public Object[][] getTableFormattedStockData(ArrayList<Stock> stockList){
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

    public Object[][] getTableFormattedCustomerData(ArrayList<Customer> customers){
        Object[][] data = new Object[stockList.size()][5];
        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            data[i][0] = customer.getUsername();
            data[i][1] = customer.getPassword();
            data[i][2] = customer.getBalance();
            data[i][3] = customer.getNetGain();
            data[i][4] = customer.getStocks().size();
        }
        return data;
    }

    
    public static void run(ArrayList<Stock> stockList){
        String[] stockColumnNames = {"Symbol", "Company", "Shares", "Price"};
        String[] customerColumnNames = {"Username", "Password", "Balance", "NetGain", "# Stocks"};

        ArrayList<Stock> stocks = Database.getStocks(); // fetch all stocks from database
        ArrayList<Customer> customers = Database.getCustomers();//fetch all customers from database


        new ManagerGUI(stocks, stockColumnNames, customers, customerColumnNames);

    }
}
