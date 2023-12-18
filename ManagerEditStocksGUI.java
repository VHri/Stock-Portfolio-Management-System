import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.*;
import java.util.ArrayList;

public class ManagerEditStocksGUI extends PortfolioFrame {
    private ArrayList<Stock> stockList;
    private String[] stockColumnNames = { "Symbol", "Company", "Shares", "Price" };
    private DefaultTableModel tableModel; // Declare the table model as an instance variable

    public ManagerEditStocksGUI() {
        super("Manager Stock Editor GUI");
        this.stockList = Database.getStocks();
        // Create a table model
        tableModel = new DefaultTableModel(getTableFormattedStockData(this.stockList), stockColumnNames);

        // Create the table using the model
        JTable stockTable = new JTable(tableModel);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(stockTable);

        // Create a panel for text fields and submit button
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));

        // Add back button and handle the action
        JPanel returnPanel = new JPanel();
        JButton returnButton = new JButton("Back");
        returnPanel.add(returnButton);
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // ManagerGUI.run(Database.getStocks());
                new ManagerMainGUI(); // open ManagerMainGUI
                dispose(); // dispose of current frame
            }
        });
        inputPanel.add(returnPanel);
        inputPanel.add(Box.createRigidArea(new Dimension(6, 0))); // Add some spacing
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

        setVisible(true);
    }

    private void handleSubmitButtonClick(JTextField stockSymbolTextField, JTextField newStockPriceTextField) {
        // updating the stock price
        for (Stock i : this.stockList) {
            if (i.getTickerSymbol().equalsIgnoreCase(stockSymbolTextField.getText())) {
                i.setPrice(Double.parseDouble(newStockPriceTextField.getText()));
                Database.changeStockPrice(i.getTickerSymbol(), i.getPrice());
            }
        }
        // Update the table model with the modified data
        tableModel.setDataVector(getTableFormattedStockData(stockList), stockColumnNames);
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

    public static void main(String[] args) {
        new ManagerEditStocksGUI();
    }
}
