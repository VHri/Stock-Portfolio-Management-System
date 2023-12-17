import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerDerivativeAccountGUI extends JFrame {

    private Customer customer;
    private JLabel notificationLabel;

    public CustomerDerivativeAccountGUI(Customer customer, CustomerMainGUI prev) {
        this.customer = customer;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        // Calculate 70% of the screen size
        int frameWidth = (int) (width * 0.7);
        int frameHeight = (int) (height * 0.7);

        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

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

        setNotificationMessage("Here you can create a derivative trading account!\n Click the create button below to get started");
        setVisible(true);
    }

    private void setNotificationMessage(String message) {
        notificationLabel.setText(message);
    }
}

