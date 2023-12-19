import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerDerivativeAccountGUI extends PortfolioFrame {

    private JLabel notificationLabel;
    private static CustomerDerivativeAccountGUI customerDerivativeAccountGUI;

    private CustomerDerivativeAccountGUI(Customer customer, CustomerMainGUI prev) {

        super("Derivative Trading");

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

        JButton button = new JButton("Create");
        mainPanel.add(button, BorderLayout.SOUTH);

        setNotificationMessage(
                "<html><center>Create a derivative trading account here!<br>Click the 'Create' button below to get started</center></html>");
        setVisible(true);
    }

    private void setNotificationMessage(String message) {
        notificationLabel.setText(message);
    }

    public static JFrame getFrame(Customer customer, CustomerMainGUI prev) {
        if (customerDerivativeAccountGUI == null) {
            customerDerivativeAccountGUI = new CustomerDerivativeAccountGUI(customer, prev);
        }
        return customerDerivativeAccountGUI;
    }

}
