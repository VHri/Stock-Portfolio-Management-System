
/*
 * LoginGUI.java creates a Customer login GUI
 * REFERENCES: CS611 Event Handler Program, Youtube Java GUI Tutorial - Make a Login GUI by AlexLee
 * 
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class LoginGUI extends JFrame {

    private JButton loginButton;
    private JButton signinButton;
    private JPanel panel;
    private JLabel userLabel;
    private JTextField userText;
    private JLabel passwordLabel;
    private JPasswordField passwordText;
    private LoginListener loginLabel;
    private SigninListener signinLabel;
    // private JLabel message;

    private PortfolioManageSystem system;

    public LoginGUI(PortfolioManageSystem system) {

        this.system = system;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        // Calculate 70% of the screen size
        int frameWidth = (int) (width * 0.3);
        int frameHeight = (int) (height * 0.3);

        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create panel to place buttons on
        panel = new JPanel(null);

        // User label
        userLabel = new JLabel("Username");
        userLabel.setBounds(70, 40, 80, 25); // x,y,width,height
        panel.add(userLabel);

        // User text field
        userText = new JTextField(20);
        userText.setBounds(170, 40, 200, 25);
        panel.add(userText);

        // password label
        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(70, 70, 80, 25);
        panel.add(passwordLabel);

        // Password text field
        passwordText = new JPasswordField();
        passwordText.setBounds(170, 70, 200, 25);
        panel.add(passwordText);

        // Associate events to button action
        loginButton = new JButton("Login");
        loginLabel = new LoginListener(this);
        loginButton.addActionListener(loginLabel);
        loginButton.setBounds(70, 120, 80, 25);
        panel.add(loginButton);

        // Associate events to button action
        signinButton = new JButton("Create Account");
        signinLabel = new SigninListener(this);
        signinButton.addActionListener(signinLabel);
        signinButton.setBounds(170, 120, 150, 25);
        panel.add(signinButton);

        // Add panel to the frame
        add(panel);

        // Success/Failiure message
        // message = new JLabel("");
        // message.setBounds(10, 110, 300, 25);
        // panel.add(message);

        setVisible(true);

    }

    // HGandle Event
    class LoginListener implements ActionListener {
        private JFrame frame;

        public LoginListener(JFrame frame) {
            this.frame = frame;
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("LoginUI: Login button clicked.");
            String username = userText.getText();
            String password = new String(passwordText.getPassword());
            System.out.println("LoginUI: Input username:" + username + "\tpassword:" + password);

            if (!lengthValid(username, password)) {
                return;
            }

            int identity = system.login(username, password);

            System.out.printf("LoginUI: return identity %d \n", identity);

            switch (identity) {
                case Constant.APPROVED_USER:
                    Customer c = system.getCustomer(username);
                    JFrame newFrame = new CustomerMainGUI(system, username);
                    newFrame.setVisible(true);
                    frame.dispose();
                    break;
                case Constant.MANAGER:
                    new ManagerMainGUI();
                    frame.dispose();
                    break;
                case Constant.UNAPPROVED_USER:
                    JOptionPane.showMessageDialog(frame, "Pending user, wait for manager approval.");
                    // message.setText("Pending user, wait for manager approval.");
                    break;
                case Constant.WRONG_PASSWORD:
                    JOptionPane.showMessageDialog(frame, "Wrong pass word, try again");
                    // message.setText("Wrong pass word, try again");
                    break;
                default:
                    JOptionPane.showMessageDialog(frame, "Login Failed");
                    // System.err.println("Login Failed");
            }

        }
    }

    // HGandle Event
    class SigninListener implements ActionListener {
        private JFrame frame;

        public SigninListener(JFrame frame) {
            this.frame = frame;
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("SignUI: Signin button clicked.");
            String username = userText.getText();
            String password = new String(passwordText.getPassword());

            System.out.println("SignUI: Input username:" + username + "\tpassword:" + password);

            if (!lengthValid(username, password)) {
                return;
            }

            int result = system.signin(username, password);

            switch (result) {
                case Constant.WRONG_PASSWORD:
                case Constant.APPROVED_USER:
                    JOptionPane.showMessageDialog(frame, "Username already exist as a customer.");
                    break;
                case Constant.MANAGER:
                    JOptionPane.showMessageDialog(frame, "Username already exist as a manager.");
                    break;
                case Constant.UNAPPROVED_USER:
                    JOptionPane.showMessageDialog(frame, "Pending user, please wait for manager approval.");
                    break;
                case Constant.SUCCESS:
                    JOptionPane.showMessageDialog(frame, "Sign in success, please wait for manager approval.");
                    break;
                case Constant.FAILURE:
                    JOptionPane.showMessageDialog(frame, "Sign in failed.");
                default:
                    JOptionPane.showMessageDialog(frame, "Something wrong with sign-in");
            }

        }
    }

    public static void run(PortfolioManageSystem system) {
        JFrame frame = new LoginGUI(system);

    }

    private boolean lengthValid(String username, String password) {
        if (username.length() > Constant.MAX_USERNAME_LEN) {

            JOptionPane.showMessageDialog(this, "Username too long : <" + Constant.MAX_USERNAME_LEN);
            return false;
        } else if (username.length() < Constant.MIN_USERNAME_LEN) {
            JOptionPane.showMessageDialog(this, "Username too short: >" + Constant.MIN_USERNAME_LEN);
            return false;
        }

        if (password.length() > Constant.MAX_PW_LEN) {
            JOptionPane.showMessageDialog(this, "Password too long : <" + Constant.MAX_PW_LEN);
            return false;
        } else if (password.length() < Constant.MIN_PW_LEN) {
            JOptionPane.showMessageDialog(this, "Password too short: >" + Constant.MIN_PW_LEN);
            return false;
        }
        return true;
    }


}
