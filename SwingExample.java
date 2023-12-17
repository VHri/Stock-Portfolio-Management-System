import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingExample extends JFrame{

    public SwingExample() {
        // Create the frame
        JFrame frame = new JFrame("Java Swing Example");

        // Create the button
        JButton button = new JButton("Click me!");

        // Add action listener to the button
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call the dummy function when the button is clicked
                setVisible(false);
                dispose();
                dummyFunction();
            }
        });

        // Add the button to the frame
        frame.getContentPane().add(button);

        // Set frame properties
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    private void dummyFunction() {
        // new ManagerViewCustomerGUI(this);
        // new ManagerEditStocksGUI(this);
    }

    public static void main(String[] args){
        new SwingExample();
    }
}