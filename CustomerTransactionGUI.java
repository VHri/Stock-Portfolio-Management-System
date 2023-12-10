
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

public class CustomerTransactionGUI extends JFrame {

    private JButton balanceButton;
    private JButton stocksButton;
    private JPanel panel;
    private BalanceListener balanceListener;
    private StockListener stockListener;
    private JLabel message;

    private Account account;

    public CustomerTransactionGUI(Account account) {
        this.account = account;
        //
        // JFrame frame = new CustomerTransactionGUI(account);

        // frame.setTitle( "Entry for Stock and Balance Info" );

        //Create Buttons
        balanceButton = new JButton("View Balance");
        stocksButton = new JButton("View Stocks");

        //Create panel to place buttons on
        panel = new JPanel(null);
        panel.add(balanceButton);
        panel.add(stocksButton);

        // Add panel to the frame
        add(panel);

        //Associate events to button action
        balanceListener = new BalanceListener();
        stockListener = new StockListener();
        balanceButton.addActionListener(balanceListener);
        stocksButton.addActionListener(stockListener);
        balanceButton.setBounds(10,80,150,25);
        stocksButton.setBounds(180,80,150,25); 
       
        //Success/Failiure message
        message = new JLabel("");
        message.setBounds(10,110,300,25);
        panel.add(message);
      

    }
   

//HGandle Event
    class BalanceListener implements ActionListener {
        private JFrame frame;
        
        // public BalanceListener(JFrame frame) {
        //     this.frame = frame;
        // }

        public BalanceListener() {
           
        }

        public void actionPerformed( ActionEvent e ) {
            //System.out.println( "Ur broke" );
            
        }
    }

    class StockListener implements ActionListener {
        private JFrame frame;
        
        // public StockListener(JFrame frame) {
        //     this.frame = frame;
        // }
        public StockListener() {
        
        }


        public void actionPerformed( ActionEvent e ) {
            //System.out.println( "Stock info will pop up" );
            JFrame newFrame = new CustomerTransactionGUI(account);
            newFrame.setVisible(true);
            //frame.dispose();
        }
    }

//    public static void main(String[] args) {
        
//         //Create a new frame
//         JFrame frame = new CustomerTransactionGUI(account);

//         frame.setTitle( "Entry for Stock and Balance Info" );
//         frame.setSize(350,200);
//         frame.setLocation( 200, 100 );
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
//         frame.setVisible(true);

        
//     }
}
