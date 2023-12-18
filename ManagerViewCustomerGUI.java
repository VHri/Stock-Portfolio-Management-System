import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ManagerViewCustomerGUI extends PortfolioFrame {
    private JList<String> customerList;
    private DefaultListModel<String> listModel;
    private JTextArea customerDetailsTextArea;
    private JButton approveCustomerButton;
    private JPanel leftPanel;

    public ManagerViewCustomerGUI() {
        super("Manager View Customer GUI");
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setSize(800, 600);

        // Create a panel for the customer list
        leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        // Create a label for heading/instructions at the top
        JLabel headerInstructionsLabel = new JLabel("Customer List - Select a customer to see further details:");
        leftPanel.add(headerInstructionsLabel, BorderLayout.NORTH);

        // Create a list model for the JList and add non-Manager users in the list for
        // manager's view
        populateCustomerList();

        // Add the JList to a scroll pane
        JScrollPane listScrollPane = new JScrollPane(customerList);

        // Add the scroll pane to the left panel
        leftPanel.add(listScrollPane, BorderLayout.CENTER);

        // Create a panel for the right side (customer details)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        // Create a JTextArea for displaying customer details
        customerDetailsTextArea = new JTextArea();
        customerDetailsTextArea.setEditable(false);

        // Add the JTextArea to a scroll pane
        JScrollPane textAreaScrollPane = new JScrollPane(customerDetailsTextArea);

        // Add the scroll pane to the right panel
        rightPanel.add(textAreaScrollPane, BorderLayout.CENTER);

        // Create a button panel for the "Approve Customer" button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Create a button for approving customers
        approveCustomerButton = new JButton("Approve Customer");
        approveCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleApproveCustomerButtonClick();
            }
        });

        // Initially, hide the button
        approveCustomerButton.setVisible(false);

        // Add the button to the button panel
        buttonPanel.add(approveCustomerButton);

        // Add the button panel to the bottom of the left panel
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add back button and handle the action
        JPanel returnPanel = new JPanel();
        JButton returnButton = new JButton("Back");
        returnPanel.add(returnButton);
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ManagerMainGUI(); // open ManagerMainGUI
                dispose(); // dispose of current frame
            }
        });
        leftPanel.add(returnPanel, BorderLayout.SOUTH);

        // Create a split pane to divide the frame into two halves vertically
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setResizeWeight(0.5);
        add(splitPane);

        setVisible(true);
    }

    private Customer returnSelectedCustomer(int selectedIndex) {
        return Database.getCustomer((String) (listModel.getElementAt(selectedIndex)).split("-", 2)[0].trim());
    }

    private void displaySelectedCustomer() {
        int selectedIndex = customerList.getSelectedIndex();
        if (selectedIndex != -1) {
            // Customer selected_customer = Database.getCustomer((String)
            // (listModel.getElementAt(selectedIndex)).split("-", 2)[0].trim());
            Customer selected_customer = returnSelectedCustomer(selectedIndex);
            customerDetailsTextArea.setText(
                    String.format(
                            "Selected Customer:%n%nUsername: %s%nStatus: %s%nBalance: %.2f%nRealized Profit: %.2f%nUnrealized Profit: %.2f%n%n%nStocks:%n",
                            selected_customer.getUsername(),
                            Database.getAccountStatus(selected_customer.getUsername()),
                            selected_customer.getBalance(),
                            selected_customer.getNetGain(),
                            selected_customer.computeUnrealizedProfit(new StockMarket())));
            // iterate through all the stocks and print it
            for (Stock s : Database.getCustomerStocks(selected_customer.getUsername())) {
                customerDetailsTextArea.append("--------------------------------------\n");
                customerDetailsTextArea.append(s.toString());
            }
            customerDetailsTextArea.append("--------------------------------------\n");
            // Show the "Approve Customer" button when a customer is selected
            if (Database.getAccountStatus(selected_customer.getUsername())
                    .equalsIgnoreCase(Constant.UNAPPROVED_STATUS)) {
                approveCustomerButton.setVisible(true);
            } else { // Hide the button if selected customer is approved
                approveCustomerButton.setVisible(false);
            }
        } else { // Hide the button if no customer is selected
            approveCustomerButton.setVisible(false);
        }
    }

    private void populateCustomerList() {
        ArrayList<Customer> customers = Database.getCustomers();
        // Create a default list model for the JList and add non-Manager users in the
        // list
        listModel = new DefaultListModel<>();
        for (Customer c : customers) {
            if (!Database.getAccountStatus(c.getUsername()).equalsIgnoreCase(Constant.MANAGER_STATUS)) {
                listModel.addElement(
                        String.format("%s - %s", c.getUsername(), Database.getAccountStatus(c.getUsername())));
            }
        }
        // Create the JList with the default list model
        customerList = new JList<>(listModel);
        customerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        customerList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    displaySelectedCustomer();
                }
            }
        });
    }

    private void handleApproveCustomerButtonClick() {
        // logic for approving customers and update the customerDetailsTextArea
        // accordingly
        try {
            Customer selected_customer = returnSelectedCustomer(customerList.getSelectedIndex());
            Database.changeAccountStatus(selected_customer.getUsername(), Constant.CUSTOMER_STATUS);
            customerDetailsTextArea.append("\n\n\n\n\n\n\nApproval Status: Approved!");

            // Repopulate the customer list to refresh the left panel
            populateCustomerList();
            approveCustomerButton.setVisible(false); // Hide the button after approval
        } catch (Exception e) {
            customerDetailsTextArea.append("\n\n\n\n\n\n\nSomething went wrong while approving customer");
        }
    }

    public static void main(String[] args) {
        new ManagerViewCustomerGUI();
    }
}
