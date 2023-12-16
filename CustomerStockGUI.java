import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CustomerStockGUI extends JFrame {

    private JTextField stockSymbolField;
    private JTextField numberOfSharesField;
    private PortfolioManageSystem system;

    private JTable customerStockTable;
    private JTable marketStockTable;

    private JScrollPane marketPane;
    private JScrollPane customerPane;

    public CustomerStockGUI(PortfolioManageSystem system) {

        this.system = system;

        setTitle("Customer Stock Center");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Main panel that will contain the two stock sections
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Section 1
        JPanel section1 = new JPanel(new BorderLayout());
        JLabel label1 = new JLabel("Your stocks");
        customerStockTable = createUserStockTable(); // Full table with all columns
        customerPane = new JScrollPane(customerStockTable);
        section1.add(label1, BorderLayout.NORTH);
        section1.add(customerPane, BorderLayout.CENTER);

        // Section 2
        JPanel section2 = new JPanel(new BorderLayout());
        JLabel label2 = new JLabel("Stocks on the market");
        marketStockTable = createMarketStockTable(); // Table without 'Bought-In Price' column
        marketPane = new JScrollPane(marketStockTable);
        section2.add(label2, BorderLayout.NORTH);
        section2.add(marketPane, BorderLayout.CENTER);

        // Adding sections to main panel with weight
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(section1);
        mainPanel.add(Box.createVerticalStrut(20)); // Space between the two sections
        mainPanel.add(section2);
        mainPanel.add(Box.createVerticalGlue());

        // Purchase Section
        JPanel purchaseSection = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel();
        JButton returnButton = new JButton("←");
        stockSymbolField = new JTextField(10);
        numberOfSharesField = new JTextField(5);
        JButton purchaseButton = new JButton("Purchase");
        JButton sellButton = new JButton("Sell");

        inputPanel.add(new JLabel("Stock Symbol:"));
        inputPanel.add(stockSymbolField);
        inputPanel.add(new JLabel("Number of Shares:"));
        inputPanel.add(numberOfSharesField);
        inputPanel.add(purchaseButton);
        inputPanel.add(sellButton);

        purchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                purchaseStock();
            }
        });

        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sellStock();
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Action for return button goes here

                dispose();

            }
        });

        purchaseSection.add(returnButton, BorderLayout.WEST);
        purchaseSection.add(inputPanel, BorderLayout.CENTER);

        // Adding main panel and purchase section to the frame
        add(mainPanel, BorderLayout.CENTER);
        add(purchaseSection, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void purchaseStock() {
        String symbol = stockSymbolField.getText();
        String shares = numberOfSharesField.getText();
        int shareCnt = 0;
        try {
            shareCnt = Integer.parseInt(shares);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Entered shares is not a integer: " + shares);
            return;
        }

        if (shareCnt < 0) {
            JOptionPane.showMessageDialog(this, "Entered shares is not positive: " + shares);
            return;
        }
        // TODO process the stock purchase

        StockMarket market = system.getMarket();
        Stock s = market.getStockBySymbol(symbol);

        if (s == null) {
            JOptionPane.showMessageDialog(this, "Entered stock ticker symbol does not exist in market: " + symbol);
            return;
        }

        system.getCurrentCustomer().buyStock(s, shareCnt);

        updateTables();
        // pop msgbox
        JOptionPane.showMessageDialog(this, "Purchased " + shares + " shares of " + symbol);
    }

    private void sellStock() {
        String symbol = stockSymbolField.getText();
        String shares = numberOfSharesField.getText();
        // TODO process the stock sell
        int shareCnt = 0;
        try {
            shareCnt = Integer.parseInt(shares);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Entered shares is not a integer: " + shares);
            return;
        }

        if (shareCnt < 0) {
            JOptionPane.showMessageDialog(this, "Entered shares is not positive: " + shares);
            return;
        }

        StockMarket market = system.getMarket();
        Stock s = market.getStockBySymbol(symbol);

        if (s == null) {
            JOptionPane.showMessageDialog(this, "Entered stock ticker symbol does not exist in market: " + symbol);
            return;
        }

        system.getCurrentCustomer().sellStock(s, shareCnt, market);

        updateTables();
        // pop msgbox
        JOptionPane.showMessageDialog(this, "Sold " + shares + " shares of " + symbol);
    }

    private JTable createUserStockTable() {
        String[] columnNames;
        Customer c = system.getCurrentCustomer();
        columnNames = new String[] { "Name", "Symbol", "Cost Basis", "Current Price", "Number of Shares" };
        Object[][] data = new Object[c.getStocks().size()][columnNames.length];
        for (int i = 0; i < c.getStocks().size(); i++) {
            Object[] sd = new Object[columnNames.length];
            sd[0] = c.getStocks().get(i).getName();
            sd[1] = c.getStocks().get(i).getTickerSymbol();
            sd[2] = c.getStocks().get(i).getTotalValue() / (double) c.getStocks().get(i).getCount();
            sd[3] = c.getStocks().get(i).getPrice();
            sd[4] = c.getStocks().get(i).getCount();
            data[i] = sd;
        }
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        return new JTable(model);
    }

    private JTable createMarketStockTable() {
        String[] columnNames;
        columnNames = new String[] { "Name", "Symbol", "Current Price", "Number of Shares" };
        Object[][] data = new Object[system.getStocks().size()][columnNames.length];
        for (int i = 0; i < system.getStocks().size(); i++) {
            Object[] sd = new Object[columnNames.length];
            sd[0] = system.getStocks().get(i).getName();
            sd[1] = system.getStocks().get(i).getTickerSymbol();
            sd[2] = system.getStocks().get(i).getPrice();
            sd[3] = system.getStocks().get(i).getCount();
            data[i] = sd;
        }
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        return new JTable(model);
    }

    /**
     * update the scroll pane with latest data
     */
    public void updateTables() {
        customerStockTable = createUserStockTable();
        customerPane.setViewportView(customerStockTable);
        marketStockTable = createMarketStockTable();
        marketPane.setViewportView(marketStockTable);

    }
}
