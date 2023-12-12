import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class ManagerGUI extends JFrame {
    private JButton editStocksButton;
    private ArrayList<Stock> stockList;
    private String[] columnNames;

    public ManagerGUI(ArrayList<Stock> stockList, String[] columnNames) {
        this.stockList = stockList;
        this.columnNames = columnNames;

        // Set up JFrame
        setTitle("Stock Table Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 400);

        // Create the "Edit Stocks" button
        editStocksButton = new JButton("Edit Stocks");
        editStocksButton.addActionListener(this::handleEditStocksButtonClick);

        // Add the button to the frame
        add(editStocksButton, BorderLayout.CENTER);

        // Display the frame
        setVisible(true);
    }

    private void handleEditStocksButtonClick(ActionEvent e) {
        // Remove the "Edit Stocks" button from the frame
        remove(editStocksButton);

        // Convert ArrayList<Stock> to data array
        Object[][] data = new Object[stockList.size()][4];
        for (int i = 0; i < stockList.size(); i++) {
            Stock stock = stockList.get(i);
            data[i][0] = stock.getTickerSymbol();
            data[i][1] = stock.getName();
            data[i][2] = stock.getCount();
            data[i][3] = stock.getPrice();
        }

        // Create a table model
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);

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
        submitButton.addActionListener(e2 -> handleSubmitButtonClick(stockSymbolTextField, newStockPriceTextField, stockList));
        inputPanel.add(Box.createRigidArea(new Dimension(6, 0))); // Add some spacing
        inputPanel.add(submitButton);

        // Add the scroll pane and input panel to the frame
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        // Repaint the frame to reflect the changes
        revalidate();
        repaint();
    }

    private void handleSubmitButtonClick(JTextField stockSymbolTextField, JTextField newStockPriceTextField, ArrayList<Stock> stockList) {
        // Get the text from both text fields and print to the terminal
        String stockSymbol = stockSymbolTextField.getText();
        String newStockPrice = newStockPriceTextField.getText();
        
        System.out.println("Stock Symbol: " + stockSymbol);
        System.out.println("New Stock Price: " + newStockPrice);

        for(Stock i: stockList){
            if(i.getTickerSymbol().equalsIgnoreCase(stockSymbolTextField.getText())){
                System.out.printf("Found Stock %s%n", stockSymbolTextField.getText());
                // i.setPrice((double)newStockPriceTextField.getText());
                i.setPrice(Double.parseDouble(newStockPriceTextField.getText()));
                System.out.printf("%s: %s%n", stockSymbolTextField.getText(), newStockPriceTextField.getText());
            }
        }

        for(Stock i: stockList){
            System.out.printf("%s: %f%n", i.getTickerSymbol(), i.getPrice());
        }


    }

    public static void main(String[] args) {
        String[] columnNames = {"Symbol", "Company", "Shares", "Price"};

        ArrayList<Stock> stocks = new ArrayList<>();
        stocks.add(new Stock("Stock1", "S1", 13.5, 100));
        stocks.add(new Stock("Stock2", "S2", 15.7, 200));
        stocks.add(new Stock("Stock3", "S3", 135.9, 300));
        stocks.add(new Stock("Stock4", "S4", 0.1, 400));

        // Run the GUI code on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> new ManagerGUI(stocks, columnNames));

        
    }
}
