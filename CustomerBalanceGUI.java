
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CustomerBalanceGUI extends JFrame{

    private JTextField depositField;
    private JTextField withdrawField;
    private JLabel currentBalanceLabel;
    private Customer customer;

    public CustomerBalanceGUI(Customer customer) {
        this.customer = customer;
              
        setTitle("Account Balance");
        setSize(700, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Main panel that will contain the money info sections
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        //Withdraw/deposit section
        JPanel actionsSection = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel();
        JButton returnButton = new JButton("←");
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
                //System.out.println("Clicked deposit button!");
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               withdraw();
            //System.out.println("Clicked deposit button!");
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Action for return button goes here

                dispose();

            }
        });

        actionsSection.add(returnButton, BorderLayout.WEST);
        actionsSection.add(inputPanel, BorderLayout.CENTER);
        add(actionsSection, BorderLayout.SOUTH);

        mainPanel.add(currentBalanceLabel);
        add(mainPanel, BorderLayout.CENTER);
        setVisible(true); //REMOVE AFTER TESTING
    }

    private void deposit() {
        String val = depositField.getText();     
        Double deposit = 1.0*Integer.parseInt(val);
        System.out.println("Deposited $" + val);
        customer.deposit(deposit);
        displayBalance(customer.getBalance());
    }

    private void withdraw() {
        String val = withdrawField.getText();
        Double withdraw = 1.0*Integer.parseInt(val);
        System.out.println("Withdrew $" + val);
        customer.withdraw(withdraw);
        displayBalance(customer.getBalance());
    }

    private void displayBalance(Double balance){
        currentBalanceLabel.setText("Current Balance: $" + balance);
    }

    private void sessionHistory() {

    }
    
    public static void main(String[] args) {

        Customer c = Database.getCustomer("johndoe");
        new CustomerBalanceGUI(c);
    }
}
