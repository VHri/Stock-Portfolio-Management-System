import javax.swing.*;
import java.awt.*;

public class ManagerMainGUI extends JFrame {


    public ManagerMainGUI() {
        setTitle("Manager Main GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        // Create buttons with images
        JButton approveCustomersButton = createButton("View and Approve Customers", Constant.CUSTOMER_VIEW_APPROVE_BUTTON_IMG_PATH);
        JButton editStocksButton = createButton("View and Edit Stocks", Constant.STOCKS_EDIT_BUTTON_IMG_PATH);
        JButton sendNotificationButton = createButton("Send Notification to Customers", Constant.NOTIFICATION_BUTTON_IMG_PATH);
        JButton logoutButton = createLogoutButton("Logout", Constant.LOGOUT_BUTTON_IMG_PATH);

        // Set fixed size for the first three buttons
        Dimension fixedButtonSize = new Dimension(450, approveCustomersButton.getPreferredSize().height);
        approveCustomersButton.setPreferredSize(fixedButtonSize);
        editStocksButton.setPreferredSize(fixedButtonSize);
        sendNotificationButton.setPreferredSize(fixedButtonSize);

        // Set smaller width for the Logout button
        Dimension logoutButtonSize = new Dimension(200, logoutButton.getPreferredSize().height);
        logoutButton.setPreferredSize(logoutButtonSize);

        // Add action listeners
        approveCustomersButton.addActionListener(e -> handleApproveCustomersButtonClick());
        editStocksButton.addActionListener(e -> handleEditStocksButtonClick());
        sendNotificationButton.addActionListener(e -> handleSendNotificationButtonClick());
        logoutButton.addActionListener(e -> handleLogoutButtonClick());

        // Create a button panel using FlowLayout for vertical stacking
        JPanel verticalButtonPanel = new JPanel(new GridLayout(0, 1, 10, 20));
        verticalButtonPanel.add(Box.createVerticalStrut(16));
        verticalButtonPanel.add(approveCustomersButton);
        verticalButtonPanel.add(editStocksButton);
        verticalButtonPanel.add(sendNotificationButton);
        verticalButtonPanel.add(logoutButton);

        // Create a label at the top
        JLabel titleLabel = new JLabel("Manager Functions");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        // Set layout for the main content using GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(titleLabel, gbc);
        gbc.gridy = 1;
        add(verticalButtonPanel, gbc);

        setVisible(true);
    }

    private JButton createButton(String text, String imagePath) {
        // Create button with text and icon
        JButton button = new JButton(text, new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        return button;
    }

    private JButton createLogoutButton(String text, String imagePath) {
        // Create Logout button with smaller width
        JButton button = createButton(text, imagePath);
        button.setPreferredSize(new Dimension(200, button.getPreferredSize().height));
        return button;
    }

    // Button Action Handlers
    private void handleApproveCustomersButtonClick() {
        System.out.println("View and Approve Customers");
        new ManagerViewCustomerGUI(); // Open Manager View and Approve Customer GUI
        dispose(); // close current frame
    }

    private void handleEditStocksButtonClick() {
        System.out.println("View and Edit Stocks");
        new ManagerEditStocksGUI(); // Open Manager Edit Stocks GUI
        dispose(); // close current frame
    }

    private void handleSendNotificationButtonClick() {
        System.out.println("Send Notification to Customers who have become Super Customers");

        String popup_notification_message = "Super Customer Notifications and Changes Made for following Customers:  ";
        boolean status_change_flag = false;
        for(Customer c: Database.getCustomers()){
            System.out.printf("%nUsername: %s | NetGain: %.2f | Status: %s | Statuscheck: %b", c.getUsername(), c.getNetGain(), Database.getAccountStatus(c.getUsername()), Database.getAccountStatus(c.getUsername()).trim().equalsIgnoreCase(Constant.CUSTOMER_STATUS));
            if( // if customer meets super customer realised profit threshold and is not already a super user
                (c.getNetGain() >= Constant.SUPER_CUSTOMER_PROFIT_THRESHOLD) &&
                (Database.getAccountStatus(c.getUsername()).trim().equalsIgnoreCase(Constant.CUSTOMER_STATUS))
            )
            {   
                System.out.printf("%nNotification Triggered for Username: %s%n", c.getUsername());
                status_change_flag = true;
                Database.changeAccountStatus(c.getUsername(), Constant.SUPERUSER_STATUS);
                popup_notification_message += String.format("Username: %s with Realised Profit: %.2f  ", c.getUsername(), c.getNetGain());
            }
        }

        // Create a new JFrame for the notification pop-up
        JFrame notificationFrame = new JFrame("Super Customer Notification Popup");
        notificationFrame.setSize(600, 150);
        notificationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create a panel for the notification content
        JPanel notificationPanel = new JPanel(new BorderLayout());
        JLabel notificationLabel;
        if(status_change_flag){
            notificationLabel = new JLabel(popup_notification_message);
        }
        else{
            notificationLabel = new JLabel("Super Customer Notifications and Changes Made for No Customers!");
        }
        notificationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        notificationPanel.add(notificationLabel, BorderLayout.CENTER);

        // Add a button to close the notification popup
        JButton okayButton = new JButton("Okay");
        okayButton.addActionListener(e -> {
            notificationFrame.dispose(); // Close the notification frame
        });
        notificationPanel.add(okayButton, BorderLayout.SOUTH);

        // Add the panel to the notification frame
        notificationFrame.add(notificationPanel);
        notificationFrame.setLocationRelativeTo(null);
        notificationFrame.setVisible(true);
    }

    private void handleLogoutButtonClick() {
        System.out.println("Manager Logout");
        LoginGUI.run(new PortfolioManageSystem()); // Open login GUI
        dispose(); // close current frame
    }

    public static void main(String[] args) {
        new ManagerMainGUI();
    }
}
