package Resource.JarFiles;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

class RoundButton extends JButton {
    public RoundButton(String label) {
        super(label);
        setContentAreaFilled(false); // Make the button transparent
        setFocusPainted(false); // Remove focus border
        setBorderPainted(false); // Remove border
        setBackground(Color.yellow);
        setForeground(Color.black);

    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.gray);
        } else {
            g.setColor(getBackground());
        }

        // Draw a rounded shape
        g.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 15, 15);

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Do not paint a border
    }
}

public class TestMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Round Button Example");
            JButton add;
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new FlowLayout());
            add = new RoundButton("Add");
            add.setPreferredSize(new Dimension(100, 100));
            frame.add(new RoundButton("Click Me"));


            frame.add(add);
            frame.setSize(300, 100);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
