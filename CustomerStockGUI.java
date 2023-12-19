import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerStockGUI extends PortfolioFrame {

    private JTextField stockSymbolField;
    private JTextField numberOfSharesField;
    private PortfolioManageSystem system;

    private JTable customerStockTable;
    private JTable marketStockTable;

    private JScrollPane marketPane;
    private JScrollPane customerPane;

    private static CustomerStockGUI customerStockGUI;

    private CustomerStockGUI(PortfolioManageSystem system, CustomerMainGUI prevFrame) {

        super("Customer Stock Center");
        this.system = system;

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

        JPanel returnPanel = new JPanel();
        JButton returnButton = new JButton("Back");
        returnPanel.add(returnButton);
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Action for return button goes here
                dispose();
                prevFrame.setVisible(true);
                prevFrame.updateLabels();

            }
        });

        purchaseSection.add(returnPanel, BorderLayout.WEST);
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

        StockMarket market = system.getMarket();
        Stock s = market.getStockBySymbol(symbol);
        double cost = system.getMarket().getPriceOf(s) * shareCnt;

        if (s == null) {
            JOptionPane.showMessageDialog(this, "Entered stock ticker symbol does not exist in market: " + symbol);
            return;
        }

        int customerResult = system.getCurrentCustomer().buyStock(s, shareCnt);

        if (customerResult == Constant.NOT_ENOUGH_BALANCE) {

            JOptionPane.showMessageDialog(this, "Not enough balance on your account: " + cost);
            return;
        }

        updateTables();
        // pop msgbox
        JOptionPane.showMessageDialog(this,
                "Purchased " + shares + " shares of " + symbol + " at price " + system.getMarket().getPriceOf(s)
                        + "\nSpent " + cost + " balance\nCurrent balance is "
                        + system.getCurrentCustomer().getBalance());
    }

    private void sellStock() {
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

        StockMarket market = system.getMarket();
        Stock s = market.getStockBySymbol(symbol);
        double income = system.getMarket().getPriceOf(s) * shareCnt;

        if (s == null) {
            JOptionPane.showMessageDialog(this, "Entered stock ticker symbol does not exist in market: " + symbol);
            return;
        }

        int customerResult = system.getCurrentCustomer().sellStock(s, shareCnt, market);

        if (customerResult == Constant.TOO_MANY_SHARE) {
            JOptionPane.showMessageDialog(this, "Entered number of shares too many");
            return;
        }

        updateTables();
        // pop msgbox
        // JOptionPane.showMessageDialog(this, "Sold " + shares + " shares of " +
        // symbol);
        JOptionPane.showMessageDialog(this,
                "Sold " + shares + " shares of " + symbol + " at price " + system.getMarket().getPriceOf(s)
                        + "\nBalance increased by " + income + "\nCurrent balance is "
                        + system.getCurrentCustomer().getBalance());
    }

    private JTable createUserStockTable() {
        String[] columnNames;
        Customer c = system.getCurrentCustomer();
        columnNames = new String[] { "Name", "Symbol", "Cost Basis", "Number of Shares" };
        Object[][] data = new Object[c.getStocks().size()][columnNames.length];
        for (int i = 0; i < c.getStocks().size(); i++) {
            Object[] sd = new Object[columnNames.length];
            sd[0] = c.getStocks().get(i).getName();
            sd[1] = c.getStocks().get(i).getTickerSymbol();
            sd[2] = c.getStocks().get(i).getTotalValue() / (double) c.getStocks().get(i).getCount();
            // sd[3] = c.getStocks().get(i).getPrice();
            sd[3] = c.getStocks().get(i).getCount();
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

    public static JFrame getFrame(PortfolioManageSystem system, CustomerMainGUI prev) {
        if (customerStockGUI == null) {
            customerStockGUI = new CustomerStockGUI(system, prev);
        }
        return customerStockGUI;
    }
}
