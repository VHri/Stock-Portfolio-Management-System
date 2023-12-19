
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CustomerBalanceGUI extends PortfolioFrame {

    private JTextField depositField;
    private JTextField withdrawField;
    private JLabel currentBalanceLabel;
    private Customer customer;
    private PortfolioManageSystem system;

    private static CustomerBalanceGUI customerBalanceGUI;

    private CustomerBalanceGUI(PortfolioManageSystem system, CustomerMainGUI prev) {

        super("Account Balance");
        this.customer = system.getCurrentCustomer();
        this.system = system;

        // Main panel that will contain the money info sections
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Withdraw/deposit section
        JPanel actionsSection = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel();
        JButton returnButton = new JButton("Back");
        depositField = new JTextField(7);

        JButton depositButton = new JButton("Deposit");
        withdrawField = new JTextField(7);
        JButton withdrawButton = new JButton("Withdraw");

        currentBalanceLabel = new JLabel();
        currentBalanceLabel.setHorizontalAlignment(JLabel.CENTER);

        inputPanel.add(new JLabel("Deposit:"));
        inputPanel.add(depositField);
        inputPanel.add(depositButton);
        inputPanel.add(new JLabel("Withdraw:"));
        inputPanel.add(withdrawField);
        inputPanel.add(withdrawButton);

        displayBalance(customer.getBalance());

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deposit();
                // System.out.println("Clicked deposit button!");
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                withdraw();
                // System.out.println("Clicked deposit button!");
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Action for return button goes here

                dispose();
                prev.setVisible(true);
                prev.updateLabels();

            }
        });

        actionsSection.add(returnButton, BorderLayout.WEST);
        actionsSection.add(inputPanel, BorderLayout.CENTER);
        add(actionsSection, BorderLayout.SOUTH);

        mainPanel.add(currentBalanceLabel);
        add(mainPanel, BorderLayout.CENTER);
        setVisible(true); // REMOVE AFTER TESTING
    }

    private void deposit() {
        String val = depositField.getText();
        // Double deposit = 1.0 * Integer.parseInt(val);
        Double deposit = Double.parseDouble(val);
        System.out.println("Deposited $" + val);
        customer.deposit(deposit);
        displayBalance(customer.getBalance());
    }

    private void withdraw() {
        String val = withdrawField.getText();
        Double withdraw = Double.parseDouble(val);
        // Double withdraw = 1.0 * Integer.parseInt(val);
        System.out.println("Withdrew $" + val);
        customer.withdraw(withdraw);
        displayBalance(customer.getBalance());
    }

    private void displayBalance(Double balance) {
        currentBalanceLabel.setText("Current Balance: $" + balance);
    }

    private void sessionHistory() {

    }

    public static JFrame getFrame(PortfolioManageSystem system, CustomerMainGUI prev) {
        if (customerBalanceGUI == null) {
            customerBalanceGUI = new CustomerBalanceGUI(system, prev);
        }
        return customerBalanceGUI;
    }
}
