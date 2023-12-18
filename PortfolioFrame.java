import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.awt.*;

public class PortfolioFrame extends JFrame {
    public PortfolioFrame(String title) {
        this(title, Constant.WINDOW_RATIO);

    }

    public PortfolioFrame(String title, double ratio) {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        int frameWidth = (int) (width * ratio);
        int frameHeight = (int) (height * ratio);

        setTitle(title);
        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setGlobalFont(new Font("Arial", Font.PLAIN, Constant.GLOBAL_FONT_SIZE));
    }

    private static void setGlobalFont(Font font) {
        UIManager.getLookAndFeelDefaults().entrySet().stream()
                .filter(e -> e.getKey().toString().endsWith(".font"))
                .forEach(e -> UIManager.put(e.getKey(), font));
    }

    protected static JButton addButton(String text, JPanel panel, int width, int spacing) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(width, button.getPreferredSize().height));
        button.setMaximumSize(new Dimension(width, button.getPreferredSize().height));
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(0, spacing)));
        return button;
    }

    protected static JLabel addLabel(String text, JPanel panel, int width, int spacing) {
        JLabel textField = new JLabel(text);
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        textField.setPreferredSize(new Dimension(width, textField.getPreferredSize().height));
        textField.setMaximumSize(new Dimension(width, textField.getPreferredSize().height));
        panel.add(textField);
        panel.add(Box.createRigidArea(new Dimension(0, spacing)));
        return textField;
    }
}
