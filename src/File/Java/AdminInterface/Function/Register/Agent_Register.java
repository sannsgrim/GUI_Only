package File.Java.AdminInterface.Function.Register;

import File.Java.AdminInterface.Function.Tables.View_Agents;
import File.Java.AdminInterface.Menu.AdminMenu;
import File.Java.manager.FormsManager;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import File.Java.LogInClass.component.PasswordStrengthStatus;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class Agent_Register extends JPanel {

    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JRadioButton jrMale;
    private JRadioButton jrFemale;
    private JTextField txtAge;
    private JTextField txtAddress;
    private JTextField txtAgentID;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private ButtonGroup groupGender;
    private JButton cmdRegister;
    private JButton cmdBack;
    private PasswordStrengthStatus passwordStrengthStatus;
    private BufferedImage backgroundImage;
    private float darknessFactor = 0.5f;
    private String SelectedGender = null;
    public Agent_Register() {
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Resource/Images/Back.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Layout();
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
        txtFirstName = new JTextField();
        txtLastName = new JTextField();
        txtAge = new JTextField();
        txtAddress = new JTextField();
        txtAgentID = new JTextField();
        txtPassword = new JPasswordField();
        txtConfirmPassword = new JPasswordField();
        cmdRegister = new JButton("Add Agent");
        cmdBack = new JButton("Back");
        passwordStrengthStatus = new PasswordStrengthStatus();

        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "[fill,360]"));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "[light]background:darken(@background,3%);" +
                "[dark]background:lighten(@background,3%)");

        txtFirstName.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "First name");
        txtLastName.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Last name");
        txtAge.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your Age");
        txtAddress.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT,"Enter Your Address");
        txtAgentID.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Your Agent ID");
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your Password");
        txtConfirmPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Re-enter your password");
        txtPassword.putClientProperty(FlatClientProperties.STYLE, "" +
                "showRevealButton:true");
        txtConfirmPassword.putClientProperty(FlatClientProperties.STYLE, "" +
                "showRevealButton:true");

        cmdRegister.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        cmdBack.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        JLabel lbTitle = new JLabel("Welcome to CCATS : Add Agent");
        JLabel description = new JLabel("Please Enter Your Personal Information to get Started.");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +10");
        description.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)");

        passwordStrengthStatus.initPasswordField(txtPassword);

        panel.add(lbTitle);
        panel.add(description);
        panel.add(new JLabel("Full Name"), "gapy 10");
        panel.add(txtFirstName, "split 2");
        panel.add(txtLastName);
        panel.add(new JLabel("Gender"), "gapy 8");
        panel.add(createGenderPanel());
        panel.add(new JSeparator(), "gapy 5 5");
        panel.add(new JLabel("Age"));
        panel.add(txtAge);
        panel.add(new JLabel("Address"));
        panel.add(txtAddress);
        panel.add(new JLabel("Agent ID"));
        panel.add(txtAgentID);
        panel.add(new JLabel("Password"), "gapy 8");
        panel.add(txtPassword);
        panel.add(passwordStrengthStatus,"gapy 0");
        panel.add(new JLabel("Confirm Password"), "gapy 0");
        panel.add(txtConfirmPassword);
        panel.add(cmdRegister, "gapy 20");
        panel.add(cmdBack, "gapy 10");
        panel.add(createLoginLabel(), "gapy 10");
        add(panel);


        cmdBack.addActionListener(e -> {
            FormsManager.getInstance().showForm(new AdminMenu());
        });

        cmdRegister.addActionListener(e -> {
            String firstName = txtFirstName.getText() + " " + txtLastName.getText();
            String gender = SelectedGender;
            int age = Integer.parseInt(txtAge.getText());
            String address = txtAddress.getText();
            String status = "Active";
            int agentID = Integer.parseInt(txtAgentID.getText());
            String password = txtPassword.getText();

            int passwordStatus = passwordStrengthStatus.getType();

            if (passwordStatus == 0 || passwordStatus == 1){
                JOptionPane.showMessageDialog(this, "Password is too weak.");
                return;
            }

            try {
                String DB_URL = "jdbc:mysql://localhost/ccats";
                String USER = "root";
                String PASS = "";
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                String SqlCommand = "INSERT INTO agentInfo (agentFullName, agentGender, agentAge, agentAddress, agentID, agentPassword, agentStatus) " +
                        "VALUES (?,?,?,?,?,?,?)";

                PreparedStatement create = conn.prepareStatement(SqlCommand);
                create.setString(1, firstName);
                create.setString(2, gender);
                create.setInt(3, age);
                create.setString(4, address);
                create.setInt(5, agentID);
                create.setString(6, password);
                create.setString(7, status);
                create.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data has been saved successfully.");

                FormsManager.getInstance().showForm(new View_Agents());

                //implement stack here

            }catch (SQLException exception){
                System.out.println("Error: " + exception.getMessage());
                JOptionPane.showMessageDialog(this, "Error occurred while saving data.");

            }
        });





    }

    private Component createGenderPanel() {
        JPanel panel = new JPanel(new MigLayout("insets 0"));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null");
        jrMale = new JRadioButton("Male");
        jrFemale = new JRadioButton("Female");
        groupGender = new ButtonGroup();
        groupGender.add(jrMale);
        groupGender.add(jrFemale);

        jrMale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jrMale.isSelected()){
                    SelectedGender = "Male";
                }
            }
        });

        jrFemale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jrFemale.isSelected()){
                    SelectedGender = "Female";
                }
            }
        });


        panel.add(jrMale);
        panel.add(jrFemale);
        return panel;
    }




    private Component createLoginLabel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null");
        return panel;
    }



}
