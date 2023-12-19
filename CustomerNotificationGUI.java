import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerNotificationGUI extends PortfolioFrame {

    private Customer customer;
    private JLabel notificationLabel;

    private static CustomerNotificationGUI customerNotificationGUI;

    private CustomerNotificationGUI(Customer customer, CustomerMainGUI prev) {
        super("Notifications");
        this.customer = customer;

        // Main panel that will contain the money info sections
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JButton returnButton = new JButton("Back");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                prev.setVisible(true);
                prev.updateLabels();

            }
        });

        mainPanel.add(returnButton, BorderLayout.WEST);

        notificationLabel = new JLabel();
        notificationLabel.setHorizontalAlignment(JLabel.CENTER);
        notificationLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        mainPanel.add(notificationLabel);
        add(mainPanel, BorderLayout.CENTER);

        if (Database.getAccountStatus(this.customer.getUsername()).equals("Super Customer")) {
            setNotificationMessage(
                    "<html><center><br>Dear valued customer,<br>We are pleased to inform you that you have exceeded $10,000 in realized trading gains! As a token of appreciation for your successful trading journey, you now have the exclusive opportunity to create a derivative trading account.<br>This derivative trading account will grant you the ability to explore advanced trading strategies, including the exciting world of options trading.<br>Thank you for choosing our platform, and we wish you continued success in your trading endeavors!</center></html>");
        } else {
            setNotificationMessage("<html><center><br>You have no messages</center></html>");
        }
        setVisible(true);
    }

    private void setNotificationMessage(String message) {
        notificationLabel.setText(message);
    }

    public static JFrame getFrame(Customer customer, CustomerMainGUI prev) {
        if (customerNotificationGUI == null) {
            customerNotificationGUI = new CustomerNotificationGUI(customer, prev);
        }
        return customerNotificationGUI;
    }
}
