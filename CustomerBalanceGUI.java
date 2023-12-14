
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class CustomerBalanceGUI extends JFrame{
    public CustomerBalanceGUI(Customer customer) {
                // Set up JFrame
        setTitle("Adjust Balance");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 400);


        //Add balance label
        JLabel addBalanceLabel = new JLabel("Deposit");
        addBalanceLabel.setBounds(10,20,80,25); //x,y,width,height
        add(addBalanceLabel);
        
        JTextField addBalanceText = new JTextField(20);
        addBalanceText.setBounds(100,20,165,25);
        add(addBalanceText);

        setVisible(true); //REMOVE AFTER TESTING
        
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
    }

    public static void main(String[] args) {

        Customer c = Database.getCustomer("johndoe");
        new CustomerBalanceGUI(c);
    }
}
