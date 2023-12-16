import javax.swing.*;
import java.awt.*;

public class CustomerNotificationGUI extends JFrame {

    private Customer customer;
    private JLabel notificationLabel;

    public CustomerNotificationGUI(Customer customer) {
        this.customer = customer;

        setTitle("Notifications");
        setSize(700, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Main panel that will contain the money info sections
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        notificationLabel = new JLabel();
        notificationLabel.setHorizontalAlignment(JLabel.CENTER);
        notificationLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        mainPanel.add(notificationLabel);
        add(mainPanel, BorderLayout.CENTER);

        if(Database.getAccountStatus(this.customer.getUsername()).equals("Super Customer")) {
            setNotificationMessage("\"Dear valued customer,\n" + //
                    "\n" + //
                    "We are pleased to inform you that you have exceeded $10,000 in realized trading gains! As a token of appreciation for your successful trading journey, you now have the exclusive opportunity to create a derivative trading account.\n" + //
                    "\n" + //
                    "This derivative trading account will grant you the ability to explore advanced trading strategies, including the exciting world of options trading. Please note that the actual implementation of the ability to trade options is not yet available, but stay tuned for future updates.\n" + //
                    "\n" + //
                    "Thank you for choosing our platform, and we wish you continued success in your trading endeavors!\n");
        } else {
            setNotificationMessage("You have no messages");
        }
        setVisible(true);
    }

    private void setNotificationMessage(String message) {
        notificationLabel.setText(message);
    }

    public static void main(String[] args) {

        Customer c = Database.getCustomer("elonmusk");
        // new CustomerDerivativeAccountGUI(c);
        new CustomerNotificationGUI(c);
    }
}
