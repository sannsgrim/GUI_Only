package File.Java.LogInClass.login;

import File.Java.AdminInterface.Menu.AdminMenu;
import File.Java.AgentInterface.Menu.AgentMenu;
import File.Java.ClientInterface.Menu.ClientMenu;
import File.Java.ClientInterface.SignUpClass.Client_SignUp;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
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
import java.sql.*;
import java.util.Objects;


public class Client_Login extends JPanel {
    private JTextField UsernameInput;
    private JPasswordField PasswordInput;
    private JCheckBox RememberCheck;
    private JButton ButtonLogin, BackButton, SignUpButton;
    private BufferedImage backgroundImage;
    private float darknessFactor = 0.5f;

    public Client_Login() {
        FlatMacLightLaf.setup();
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Resource/Images/Back.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Layout();
        setFocusable(true);

    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            g.setColor(new Color(0, 0, 0, (int) (255 * darknessFactor)));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }


    private void Layout() {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));
        UsernameInput = new JTextField();
        PasswordInput = new JPasswordField();
        RememberCheck = new JCheckBox("Remember me");
        ButtonLogin = new JButton("Login");
        SignUpButton = new JButton("Sign Up");
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

        SignUpButton.putClientProperty(FlatClientProperties.STYLE, "" +
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
        JLabel description = new JLabel("Login As Client User Account");
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
        panel.add(SignUpButton, "gapy 10");
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
                if (validateClientCredentials(UsernameInput.getText(), PasswordInput.getText())){
                    FormsManager.getInstance().showForm(new ClientMenu());
                } else {
                    JDialog dialog = new JDialog();
                    dialog.setAlwaysOnTop(true);
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    JOptionPane.showMessageDialog(dialog, "Invalid agent ID or password");
                }
            }
        });



        SignUpButton.addActionListener(e -> {
            FormsManager.getInstance().showForm(new Client_SignUp());
        });

        BackButton.addActionListener(e -> {
            FormsManager.getInstance().showForm(new LandingPage());
        });



    }

    private boolean validateClientCredentials(String username, String password) {
        try {
            String DB_URL = "jdbc:mysql://localhost/ccats";
            String USER = "root";
            String PASS = "";
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            if (conn != null) {
                String query = "SELECT * FROM clientInfo WHERE client_Username = ? AND cLient_Password\n = ?";
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

