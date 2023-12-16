import javax.swing.*;
import java.awt.*;

public class TableExample {
    public static void main(String[] args) {
        // Create a frame
        JFrame frame = new JFrame("Table Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create an initial table
        String[][] data = {{"1", "Name 1"}, {"2", "Name 2"}};
        String[] columnNames = {"ID", "Name"};
        JTable table = new JTable(data, columnNames);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Create a button to replace the table
        JButton replaceButton = new JButton("Replace Table");
        replaceButton.addActionListener(e -> {
            // New data for the table
            String[][] newData = {{"3", "Name 3"}, {"4", "Name 4"}};
            JTable newTable = new JTable(newData, columnNames);

            // Replace the old table with the new one in the scroll pane
            scrollPane.setViewportView(newTable);
        });

        frame.add(replaceButton, BorderLayout.SOUTH);

        // Display the frame
        frame.setSize(400, 200);
        frame.setVisible(true);
    }
}
