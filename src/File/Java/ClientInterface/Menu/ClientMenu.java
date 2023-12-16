package File.Java.ClientInterface.Menu;


import File.Java.AdminInterface.Function.HistoryCall.Calls_History;
import File.Java.AdminInterface.Function.Register.Agent_Register;
import File.Java.AdminInterface.Function.Register.Company_Register;
import File.Java.AdminInterface.Function.Report.ReportMenu;
import File.Java.AdminInterface.Function.Tables.*;
import File.Java.AdminInterface.Function.Search.SearchMenu;
import File.Java.ClientInterface.Function.SelectService.SelectService;
import File.Java.ClientInterface.Function.ViewCallHistory.MyHistory;
import File.Java.LogInClass.login.Agent_Login;
import File.Java.LogInClass.login.LandingPage;
import File.Java.manager.FormsManager;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;



import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;


public class ClientMenu extends JPanel {

    private JButton Select_Services, View_History, Logout;
    private BufferedImage backgroundImage;
    private float darknessFactor = 0.5f;

    public ClientMenu() {
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
        Select_Services = new JButton("Select Services");
        View_History = new JButton("View my History");
        Logout = new JButton("Logout");
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "fill,350:380"));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "[light]background:darken(@background,3%);" +
                "[dark]background:lighten(@background,3%)");
        Select_Services.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        View_History.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        Logout.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        JLabel lbTitle = new JLabel("Welcome back!");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +10");
        JLabel lbDescription = new JLabel("What would you like to do?");
        lbDescription.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)");

        Select_Services.addActionListener(e -> {
            FormsManager.getInstance().showForm(new SelectService());
        });

        View_History.addActionListener(e -> {
            FormsManager.getInstance().showForm(new MyHistory());
        });

        Logout.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                FormsManager.getInstance().showForm(new LandingPage());
            }
        });


        panel.add(lbTitle);
        panel.add(lbDescription);
        panel.add(Select_Services, "gapy 10, split 2");
        panel.add(View_History, "gapy 10, wrap");
        panel.add(Logout, "gapy 10");
        add(panel);

    }
}

