import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerNotificationGUI extends PortfolioFrame {

    private Customer customer;
    private JLabel notificationLabel;

    public CustomerNotificationGUI(Customer customer, CustomerMainGUI prev) {
        super("Notifications");
        this.customer = customer;

        // Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // double width = screenSize.getWidth();
        // double height = screenSize.getHeight();

        // // Calculate 70% of the screen size
        // int frameWidth = (int) (width * 0.7);
        // int frameHeight = (int) (height * 0.7);

        // Main panel that will contain the money info sections
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JButton returnButton = new JButton("Back");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Action for return button goes here

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
            setNotificationMessage("\"Dear valued customer,\n" + //
                    "\n" + //
                    "We are pleased to inform you that you have exceeded $10,000 in realized trading gains! As a token of appreciation for your successful trading journey, you now have the exclusive opportunity to create a derivative trading account.\n"
                    + //
                    "\n" + //
                    "This derivative trading account will grant you the ability to explore advanced trading strategies, including the exciting world of options trading. Please note that the actual implementation of the ability to trade options is not yet available, but stay tuned for future updates.\n"
                    + //
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

    // public static void main(String[] args) {

    // Customer c = Database.getCustomer("elonmusk");
    // // new CustomerDerivativeAccountGUI(c);
    // // new CustomerNotificationGUI(c);
    // }
}
