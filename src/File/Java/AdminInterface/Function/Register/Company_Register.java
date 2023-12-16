package File.Java.AdminInterface.Function.Register;

import File.Java.AdminInterface.Function.Tables.View_Company;
import File.Java.AdminInterface.Menu.AdminMenu;
import File.Java.manager.FormsManager;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import File.Java.LogInClass.component.PasswordStrengthStatus;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class Company_Register extends JPanel {

    private JTextField txtCompanyName;
    private JTextField txtService;
    private JTextField txtCompany_No;
    private JButton cmdRegister;
    private JButton cmdBack;
    private PasswordStrengthStatus passwordStrengthStatus;
    private BufferedImage backgroundImage;
    private float darknessFactor = 0.5f;
    public Company_Register() {
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
        txtCompanyName = new JTextField();
        txtService = new JTextField();
        txtCompany_No = new JTextField();
        cmdRegister = new JButton("Add Company");
        cmdBack = new JButton("Back");
        passwordStrengthStatus = new PasswordStrengthStatus();

        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "[fill,360]"));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "[light]background:darken(@background,3%);" +
                "[dark]background:lighten(@background,3%)");

        txtCompanyName.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Company Name");
        txtService.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter The Service");
        txtCompany_No.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT,"Enter Company No");


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

        JLabel lbTitle = new JLabel("Welcome to CCATS : Add Company");
        JLabel description = new JLabel("Please Enter The Company Information to get Started.");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +10");
        description.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)");


        panel.add(lbTitle);
        panel.add(description);
        panel.add(new JLabel("Company Name"), "gapy 10");
        panel.add(txtCompanyName);
        panel.add(new JLabel("Service"));
        panel.add(txtService);
        panel.add(new JLabel("Company No:"));
        panel.add(txtCompany_No);
        panel.add(cmdRegister, "gapy 20");
        panel.add(cmdBack, "gapy 20");
        panel.add(createLoginLabel(), "gapy 10");
        add(panel);


        cmdBack.addActionListener(e -> {
            FormsManager.getInstance().showForm(new AdminMenu());
        });

        cmdRegister.addActionListener(e -> {
            String CompanyName = txtCompanyName.getText();
            String Service = txtService.getText();
            String Company_No = txtCompany_No.getText();
            if (CompanyName.equals("") || Service.equals("") || Company_No.equals("")) {
                JOptionPane.showMessageDialog(null, "Please Fill All Fields");
            } else {
                try {
                    String DB_URL = "jdbc:mysql://localhost/ccats";
                    String USER = "root";
                    String PASS = "";
                    Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    String SqlCommand = "INSERT INTO companyinfo (companyName, companyService, companyNo) " +
                            "VALUES (?,?,?)";

                    PreparedStatement create = conn.prepareStatement(SqlCommand);
                    create.setString(1, CompanyName);
                    create.setString(2, Service);
                    create.setInt(3, Integer.parseInt(Company_No));
                    create.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Data has been saved successfully.");

                    FormsManager.getInstance().showForm(new View_Company());

                }catch (SQLException exception){
                    System.out.println("Error: " + exception.getMessage());
                    JOptionPane.showMessageDialog(this, "Error occurred while saving data.");

                }
            }
        });
    }








    private Component createLoginLabel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null");
        return panel;
    }



}
