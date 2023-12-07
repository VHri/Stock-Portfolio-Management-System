/*
 * LoginGUI.java creates a Customer login GUI
 * REFERENCES: CS611 Event Handler Program, Youtube Java GUI Tutorial - Make a Login GUI by AlexLee
 * 
 */
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.*;

public class LoginGUI extends JFrame  {

    private JButton loginButton;
    private JPanel panel;
    private JLabel userLabel; 
    private  JTextField userText;
    private JLabel passwordLabel;
    private JPasswordField passwordText;
    private LoginListener loginLabel;
    private JLabel message;


    public LoginGUI() {

        //Create Buttons
        loginButton = new JButton("Login");

        //Create panel to place buttons on
        panel = new JPanel(null);
        panel.add(loginButton);

        //User label
        userLabel = new JLabel("Username");
        userLabel.setBounds(10,20,80,25); //x,y,width,height
        panel.add(userLabel);
        
        //User text field
        userText = new JTextField(20);
        userText.setBounds(100,20,165,25);
        panel.add(userText);

        //password label
        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10,50,80,25);
        panel.add(passwordLabel);

        //Password text field
        passwordText = new JPasswordField();
        passwordText.setBounds(100,50,165,25);
        panel.add(passwordText);

        // Add panel to the frame
        add(panel);

        //Associate events to button action
        loginLabel = new LoginListener();
        loginButton.addActionListener(loginLabel);
        loginButton.setBounds(10,80,80,25);
       
        //Success/Failiure message
        message = new JLabel("");
        message.setBounds(10,110,300,25);
        panel.add(message);
      

    }
   

//HGandle Event
    class LoginListener implements ActionListener {
        public void actionPerformed( ActionEvent e ) {
            System.out.println( "Login button clicked." );
            String username = userText.getText();
            String password = passwordText.getText();

            System.out.println(username + " " + password);
            if (isPendingUser(username)) {
                message.setText("Pending user, wait for manager approval.");
            } else if (isExistingUser(username)) {
                if (isUsernamePasswordMatch(username, password)){

                }
                message.setText("Login Successful.");
                
            } else {
                message.setText("User does not exist, please try again.");
            }
        } 
    }


  
    public boolean isPendingUser(String username) {
        return false;   //PLACEHOLDER
    }

    public boolean isExistingUser(String username) {
        return false;   //PLACEHOLDER
    }

    public boolean isUsernamePasswordMatch(String username, String password) {
        return false;   //PLACEHOLDER
    }

   public static void main(String[] args) {
        
        //Create a new frame
        JFrame frame = new LoginGUI();

        frame.setTitle( "Customer Login" );
        frame.setSize(350,200);
        frame.setLocation( 200, 100 );
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setVisible(true);

        
    }
}
