import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

public class ManagerEditStocksGUI extends PortfolioFrame {
    private ArrayList<Stock> stockList;
    private String[] stockColumnNames = { "Symbol", "Company", "Shares", "Price" };
    private DefaultTableModel tableModel;
    private JPanel inputPanel;
    private JPanel changePricePanel;
    private JPanel addStockPanel;
    private JPanel deleteStockPanel;

    public ManagerEditStocksGUI() {
        super("Manager Stock Editor GUI");
        this.stockList = Database.getStocks();
        tableModel = new DefaultTableModel(getTableFormattedStockData(this.stockList), stockColumnNames);

        JTable stockTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(stockTable);

        inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());

        changePricePanel = createChangePricePanel();
        addStockPanel = createAddStockPanel();
        deleteStockPanel = createDeleteStockPanel();

        inputPanel.add(changePricePanel, BorderLayout.CENTER);

        JRadioButton changePriceRadioButton = new JRadioButton("Change Stock Price");
        JRadioButton addStockRadioButton = new JRadioButton("Add Stock");
        JRadioButton deleteStockRadioButton = new JRadioButton("Delete Stock");

        ButtonGroup radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(changePriceRadioButton);
        radioButtonGroup.add(addStockRadioButton);
        radioButtonGroup.add(deleteStockRadioButton);

        JPanel radioPanel = new JPanel();
        radioPanel.add(changePriceRadioButton);
        radioPanel.add(addStockRadioButton);
        radioPanel.add(deleteStockRadioButton);

        changePriceRadioButton.addActionListener(e -> showPanel(changePricePanel, 1));
        addStockRadioButton.addActionListener(e -> showPanel(addStockPanel, 2));
        deleteStockRadioButton.addActionListener(e -> showPanel(deleteStockPanel, 3));

        changePriceRadioButton.setSelected(true);

        inputPanel.add(radioPanel, BorderLayout.NORTH);

        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        JButton returnButton = new JButton("Back");
        JPanel returnPanel = new JPanel();
        returnPanel.add(returnButton);
        inputPanel.add(returnPanel, BorderLayout.SOUTH);
        returnButton.addActionListener(e -> {
            new ManagerMainGUI();
            dispose();
        });

        setVisible(true);
    }

    private JPanel createChangePricePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField stockSymbolTextField = new JTextField();
        JTextField newStockPriceTextField = new JTextField();

        panel.add(createInputPanel("Stock Symbol:", stockSymbolTextField));
        panel.add(createInputPanel("New Stock Price:", newStockPriceTextField));

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> handleSubmitButtonClick(stockSymbolTextField, newStockPriceTextField));
        panel.add(createButtonPanel(submitButton));

        return panel;
    }

    private JPanel createAddStockPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField stockSymbolTextField = new JTextField();
        JTextField companyNameTextField = new JTextField();
        JTextField sharesTextField = new JTextField();
        JTextField newStockPriceTextField = new JTextField();

        panel.add(createInputPanel("Stock Symbol:", stockSymbolTextField));
        panel.add(createInputPanel("Company Name:", companyNameTextField));
        panel.add(createInputPanel("Shares:", sharesTextField));
        panel.add(createInputPanel("New Stock Price:", newStockPriceTextField));

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> handleAddStockButtonClick(stockSymbolTextField, companyNameTextField,
                sharesTextField, newStockPriceTextField));
        panel.add(createButtonPanel(submitButton));

        return panel;
    }

    private JPanel createDeleteStockPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField stockSymbolTextField = new JTextField();

        panel.add(createInputPanel("Stock Symbol:", stockSymbolTextField));

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> handleDeleteStockButtonClick(stockSymbolTextField));
        panel.add(createButtonPanel(submitButton));

        return panel;
    }

    private JPanel createInputPanel(String label, JTextField textField) {
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5)); // FlowLayout with a smaller gap
        inputPanel.add(new JLabel(label));
        
        // Set preferred size for the text field (adjust width as needed)
        textField.setPreferredSize(new Dimension(200, textField.getPreferredSize().height));
        
        inputPanel.add(textField);
        return inputPanel;
    }

    private JPanel createButtonPanel(JButton button) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(button);
        return buttonPanel;
    }

    private void showPanel(JPanel panel, int radioButton) {
        inputPanel.removeAll();
        
        JRadioButton changePriceRadioButton = new JRadioButton("Change Stock Price");
        JRadioButton addStockRadioButton = new JRadioButton("Add Stock");
        JRadioButton deleteStockRadioButton = new JRadioButton("Delete Stock");

        ButtonGroup radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(changePriceRadioButton);
        radioButtonGroup.add(addStockRadioButton);
        radioButtonGroup.add(deleteStockRadioButton);

        JPanel radioPanel = new JPanel();
        radioPanel.add(changePriceRadioButton);
        radioPanel.add(addStockRadioButton);
        radioPanel.add(deleteStockRadioButton);

        changePriceRadioButton.addActionListener(e -> showPanel(changePricePanel, 1));
        addStockRadioButton.addActionListener(e -> showPanel(addStockPanel, 2));
        deleteStockRadioButton.addActionListener(e -> showPanel(deleteStockPanel, 3));
        
        if(radioButton == 1) {
            changePriceRadioButton.setSelected(true);
        } else if(radioButton == 2) {
            addStockRadioButton.setSelected(true);
        } else {
            deleteStockRadioButton.setSelected(true);
        }

        inputPanel.add(radioPanel, BorderLayout.NORTH);

        add(inputPanel, BorderLayout.SOUTH);

        JButton returnButton = new JButton("Back");
        JPanel returnPanel = new JPanel();
        returnPanel.add(returnButton);
        inputPanel.add(returnPanel, BorderLayout.SOUTH);
        returnButton.addActionListener(e -> {
            new ManagerMainGUI();
            dispose();
        });
        
        inputPanel.add(panel, BorderLayout.CENTER);
        inputPanel.revalidate();
        inputPanel.repaint();
    }

    private void handleSubmitButtonClick(JTextField stockSymbolTextField, JTextField newStockPriceTextField) {
        // Update stock price logic
        for (Stock i : this.stockList) {
            if (i.getTickerSymbol().equalsIgnoreCase(stockSymbolTextField.getText())) {
                i.setPrice(Double.parseDouble(newStockPriceTextField.getText()));
                Database.changeStockPrice(i.getTickerSymbol(), i.getPrice());
            }
        }
        tableModel.setDataVector(getTableFormattedStockData(stockList), stockColumnNames);
    }

    private void handleAddStockButtonClick(JTextField stockSymbolTextField, JTextField companyNameTextField,
            JTextField sharesTextField, JTextField newStockPriceTextField) {
        Database.addStock(stockSymbolTextField.getText(), companyNameTextField.getText(), Integer.parseInt(sharesTextField.getText()), Double.parseDouble(newStockPriceTextField.getText()));
        stockList.add(new Stock(companyNameTextField.getText(), stockSymbolTextField.getText(), Double.parseDouble(newStockPriceTextField.getText()), Integer.parseInt(sharesTextField.getText())));
        tableModel.setDataVector(getTableFormattedStockData(stockList), stockColumnNames);
   }

    private void handleDeleteStockButtonClick(JTextField stockSymbolTextField) {
        Database.removeStock(stockSymbolTextField.getText());
        Iterator<Stock> iterator = stockList.iterator();
        while (iterator.hasNext()) {
            Stock stock = iterator.next();
            if (stockSymbolTextField.getText().equals(stock.getTickerSymbol())) {
                iterator.remove();
            }
        }
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
