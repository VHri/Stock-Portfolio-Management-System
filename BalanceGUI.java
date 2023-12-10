/*
 * BalanceGUI.java
 */
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.*;

public class BalanceGUI extends JFrame {

    private JButton addButton;
    private JButton withdrawButton;
    private  JTextField addText;
    private  JTextField withdrawText;
    private JPanel panel;
    private AddMoneyListener addMoneyListener;
    private WithdrawMoneyListener withdrawMoneyListener;
    private JLabel message;

    private Account account;

    public BalanceGUI(Account account) {
        this.account = account;
        //
        // JFrame frame = new CustomerTransactionGUI(account);

        // frame.setTitle( "Entry for Stock and Balance Info" );

        //Create Buttons
        addButton = new JButton("Add");
        withdrawButton = new JButton("Withdraw");

        //Textfields
        addText = new JTextField(20);
        addText.setBounds(100,20,165,25);
        panel.add(addText);

        withdrawText = new JTextField(20);
        withdrawText.setBounds(100,20,165,25);
        panel.add(addText);

        //Create panel to place buttons on
        panel = new JPanel(null);
        panel.add(addButton);
        panel.add(withdrawButton);

        // Add panel to the frame
        add(panel);

        //Associate events to button action
        addMoneyListener = new AddMoneyListener();
        withdrawMoneyListener = new WithdrawMoneyListener();
        addButton.addActionListener(addMoneyListener);
        withdrawButton.addActionListener(withdrawMoneyListener);
        addButton.setBounds(10,80,150,25);
        withdrawButton.setBounds(180,80,150,25); 
       
        //Success/Failiure message
        message = new JLabel("");
        message.setBounds(10,110,300,25);
        panel.add(message);
      

    }
   

//HGandle Event
    class AddMoneyListener implements ActionListener {
        private JFrame frame;
        
        // public BalanceListener(JFrame frame) {
        //     this.frame = frame;
        // }

        public AddMoneyListener() {
           
        }

        public void actionPerformed( ActionEvent e ) {
            String addString = addText.getText();
            System.out.println("Amount withdrawn = " + addString);
            
        }
    }

    class WithdrawMoneyListener implements ActionListener {
        private JFrame frame;
        
        // public StockListener(JFrame frame) {
        //     this.frame = frame;
        // }
        public WithdrawMoneyListener() {
        
        }


        public void actionPerformed( ActionEvent e ) {
            //System.out.println( "Stock info will pop up" );
           String withdrawString = withdrawText.getText();
           System.out.println("Amount withdrawn = " + withdrawString);
            //frame.dispose();
        }
    }

   public static void main(String[] args) {
        Account account = Database.getUser("exampleUser"); //OBTAIN ACCOUNT
        //Create a new frame
        JFrame frame = new BalanceGUI(account);

        frame.setTitle( "Customer Account Balance" );
        frame.setSize(350,200);
        frame.setLocation( 200, 100 );
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setVisible(true);

        
    }
}

