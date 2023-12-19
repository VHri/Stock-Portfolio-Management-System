import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ManagerTransactionGUI extends PortfolioFrame {
    private String[] tableColumnNames = { "Username", "Symbol", "Timestamp", "Shares", "Price", "Status"};
    private DefaultTableModel tableModel;
    private JPanel inputPanel;
    ArrayList<String[]> transactionList;

    public ManagerTransactionGUI() {
        super("Manager Transaction View GUI");
        transactionList = Database.getTransactions();
        tableModel = new DefaultTableModel(getTableFormattedStockData(transactionList), tableColumnNames);

        JTable stockTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(stockTable);

        inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());

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

    public Object[][] getTableFormattedStockData(ArrayList<String[]> transactionlist) {
        Object[][] data = new Object[transactionlist.size()][6];
        // for (int i = 0; i < 3; i++) {
        //     data[i][0] = "Username";
        //     data[i][1] = "Symbol";
        //     data[i][2] = "Timestamp";
        //     data[i][3] = "Shares";
        //     data[i][4] = "Price";
        //     data[i][5] = "Status";
        // }
        // for(String[] s: transactionlist){
        for (int i = 0; i < transactionlist.size(); i++) {
            // for(int j = 0; j<transactionlist.get(0).length; i++){
            //     data[i][j] = transactionlist.get(i)[j];
            // }
            data[i][0] = transactionlist.get(i)[0];
            data[i][1] = transactionlist.get(i)[1];
            data[i][2] = transactionlist.get(i)[2];
            data[i][3] = transactionlist.get(i)[3];
            data[i][4] = transactionlist.get(i)[4];
            data[i][5] = transactionlist.get(i)[5];
        }
        return data;
    }

    public static void main(String[] args) {
        // System.out.println(Database.getTransactions().get(0));
        new ManagerTransactionGUI();
    }
}

