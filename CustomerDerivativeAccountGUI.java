import javax.swing.*;
import java.awt.*;

public class CustomerDerivativeAccountGUI extends JFrame {

    private Customer customer;
    private JLabel notificationLabel;

    public CustomerDerivativeAccountGUI(Customer customer) {
        this.customer = customer;

        setTitle("Derivative Trading Account Setup");
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

        JButton button = new JButton("Create");
        add(button, BorderLayout.SOUTH);

        setNotificationMessage("Here you can create a derivative trading account!\n Click the create button below to get started");
        setVisible(true);
    }

    private void setNotificationMessage(String message) {
        notificationLabel.setText(message);
    }
}
