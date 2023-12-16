package File.Java.LogInClass.login;

import java.sql.*;
import File.Java.AdminInterface.Menu.AdminMenu;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import File.Java.manager.FormsManager;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;


public class Admin_Login extends JPanel implements KeyListener {
    private JTextField UsernameInput;
    private JPasswordField PasswordInput;
    private JCheckBox RememberCheck;
    private JButton ButtonLogin, BackButton;
    private BufferedImage backgroundImage;
    private float darknessFactor = 0.5f;

    public Admin_Login() {
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Resource/Images/Back.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Layout();
        setFocusable(true);

        Action ctrlKAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(Admin_Login.this, "Log in as Client?");
                if (option == JOptionPane.YES_OPTION) {
                    FormsManager.getInstance().showForm(new Agent_Login());
                }
            }
        };
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_K, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()),
                "ctrlKAction"
        );
        getActionMap().put("ctrlKAction", ctrlKAction);

    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            g.setColor(new Color(0, 0, 0, (int) (255 * darknessFactor)));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    public void keyTyped(KeyEvent a) {

    }

    public void keyPressed(KeyEvent e) {
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_K) {
            int option = JOptionPane.showConfirmDialog(this, "Log in as Client?");
            if (option == JOptionPane.YES_OPTION) {
                FormsManager.getInstance().showForm(new Agent_Login());
            }
        }
    }

    public void keyReleased(KeyEvent e) {

    }

    private void Layout() {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));
        UsernameInput = new JTextField();
        PasswordInput = new JPasswordField();
        RememberCheck = new JCheckBox("Remember me");
        ButtonLogin = new JButton("Login");
        BackButton = new JButton("Back");
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "fill,350:380"));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "[light]background:darken(@background,3%);" +
                "[dark]background:lighten(@background,3%)");

        PasswordInput.putClientProperty(FlatClientProperties.STYLE, "" +
                "showRevealButton:true");
        ButtonLogin.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        BackButton.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");


        UsernameInput.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your username");
        PasswordInput.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your password");

        JLabel lbTitle = new JLabel("Welcome back!");
        JLabel description = new JLabel("Login As Admin User Account");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +10");
        description.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)");

        panel.add(lbTitle);
        panel.add(description);
        panel.add(new JLabel("Username"), "gapy 8");
        panel.add(UsernameInput);
        panel.add(new JLabel("Password"), "gapy 8");
        panel.add(PasswordInput);
        panel.add(RememberCheck, "grow 0");
        panel.add(ButtonLogin, "gapy 10");
        panel.add(BackButton, "gapy 10");
        add(panel);

        ButtonLogin.addActionListener(e -> {
            if(UsernameInput.getText().isEmpty() && PasswordInput.getText().isEmpty()){
                JDialog dialog = new JDialog();
                dialog.setAlwaysOnTop(true);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                JOptionPane.showMessageDialog(dialog, "Please enter your username and password");
            }else if(UsernameInput.getText().isEmpty()){
                JDialog dialog = new JDialog();
                dialog.setAlwaysOnTop(true);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                JOptionPane.showMessageDialog(dialog, "Please enter your username");
            }else if(PasswordInput.getText().isEmpty()){
                JDialog dialog = new JDialog();
                dialog.setAlwaysOnTop(true);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                JOptionPane.showMessageDialog(dialog, "Please enter your password");
            }else {
                if (validateAdminCredentials(UsernameInput.getText(), PasswordInput.getText())){
                    FormsManager.getInstance().showForm(new AdminMenu());
                } else {
                    JDialog dialog = new JDialog();
                    dialog.setAlwaysOnTop(true);
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    JOptionPane.showMessageDialog(dialog, "Invalid username or password");
                }

            }
        });

        BackButton.addActionListener(e -> {
            FormsManager.getInstance().showForm(new LandingPage());
        });

    }

    private boolean validateAdminCredentials(String username, String password) {
        try {
            String DB_URL = "jdbc:mysql://localhost/ccats";
            String USER = "root";
            String PASS = "";
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            if (conn != null) {
                String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    resultSet.close();
                    preparedStatement.close();
                    conn.close();
                    return true;
                }
                resultSet.close();
                preparedStatement.close();
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }


}
