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
    private JButton signinButton;
    private JPanel panel;
    private JLabel userLabel; 
    private  JTextField userText;
    private JLabel passwordLabel;
    private JPasswordField passwordText;
    private LoginListener loginLabel;
    private SigninListener signinLabel;
    private JLabel message;

    private PortfolioManageSystem system;


    public LoginGUI(PortfolioManageSystem system) {

        this.system = system;

        //Create panel to place buttons on
        panel = new JPanel(null);


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
        
        //Associate events to button action
        loginButton = new JButton("Login");
        loginLabel = new LoginListener(this);
        loginButton.addActionListener(loginLabel);
        loginButton.setBounds(10,80,80,25);
        panel.add(loginButton);

        //Associate events to button action
        signinButton = new JButton("Create Account");
        signinLabel = new SigninListener(this);
        signinButton.addActionListener(signinLabel);
        signinButton.setBounds(100,80,80,25);
        panel.add(signinButton);

        // Add panel to the frame
        add(panel);

        //Success/Failiure message
        message = new JLabel("");
        message.setBounds(10,110,300,25);
        panel.add(message);
      

    }
   

    //HGandle Event
    class LoginListener implements ActionListener {
            private JFrame frame;
            
            public LoginListener(JFrame frame) {
                this.frame = frame;
            }

            public void actionPerformed( ActionEvent e ) {
                System.out.println( "LoginUI: Login button clicked." );
                String username = userText.getText();
                String password = new String(passwordText.getPassword());
                System.out.println( "LoginUI: Input username:" + username + "\tpassword:" + password );
                int identity = system.login(username, password);

                System.out.printf( "LoginUI: return identity %d \n", identity);

                switch (identity){
                    case Constant.APPROVED_USER:
                        message.setText("Customer Login Successful.");
                        Customer c = system.getCustomer(username);
                        JFrame newFrame = new CustomerMainGUI(system, username);
                        newFrame.setVisible(true);
                        frame.dispose();
                        break;
                    case Constant.MANAGER:
                        message.setText("Manager Login Successful.");
                        ManagerGUI.run(system.getStocks());;
                        // newFrame.setVisible(true);
                        frame.dispose();
                        break;
                    case Constant.UNAPPROVED_USER:
                        message.setText("Pending user, wait for manager approval.");
                        break;
                    case Constant.WRONG_PASSWORD:
                        message.setText("Wrong pass word, try again");
                        break;
                    default:
                        System.err.println("Login Failed");
                }
                

            }
        } 

    //HGandle Event
    class SigninListener implements ActionListener {
            private JFrame frame;
            
            public SigninListener(JFrame frame) {
                this.frame = frame;
            }

            public void actionPerformed( ActionEvent e ) {
                System.out.println( "SignUI: Signin button clicked." );
                String username = userText.getText();
                String password = new String(passwordText.getPassword());
                // add check for minimum requirements for username and password (length/strength)
                System.out.println( "SignUI: Input username:" + username + "\tpassword:" + password );
                int result = system.signin(username, password);

                System.out.printf( "SignUI: return result %d \n", result);

                switch (result){
                    case Constant.WRONG_PASSWORD:
                    case Constant.APPROVED_USER:
                        message.setText("Username already exist as a customer.");
                        break;
                    case Constant.MANAGER:
                        message.setText("Username already exist as a manager.");
                        ManagerGUI.run(system.getStocks());;
                        // newFrame.setVisible(true);
                        frame.dispose();
                        break;
                    case Constant.UNAPPROVED_USER:
                        message.setText("Pending user, please wait for manager approval.");
                        break;
                    case Constant.SUCCESS:
                        message.setText("Sign in success, please wait for manager approval.");
                        break;
                    default:
                        System.err.println("");
                }
                

            }
        } 

    public static void run(PortfolioManageSystem system){
        JFrame frame = new LoginGUI(system);
        frame.setTitle( "Customer Login" );
        frame.setSize(350,200);
        frame.setLocation( 200, 100 );
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // public static void main(String[] args) {
        
        

    //     //Create a new frame
    //     JFrame frame = new LoginGUI();
    //     //frame = new LoginGUI();

    //     frame.setTitle( "Customer Login" );
    //     frame.setSize(350,200);
    //     frame.setLocation( 200, 100 );
    //     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    //     frame.setVisible(true);

        
    // }
}
