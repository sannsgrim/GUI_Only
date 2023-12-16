package File.Java.AdminInterface.Menu;

import File.Java.AdminInterface.Function.HistoryCall.Calls_History;
import File.Java.AdminInterface.Function.Register.Agent_Register;
import File.Java.AdminInterface.Function.Register.Company_Register;
import File.Java.AdminInterface.Function.Report.ReportMenu;
import File.Java.AdminInterface.Function.Tables.*;
import File.Java.AdminInterface.Function.Search.SearchMenu;
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


public class AdminMenu extends JPanel {

    private JButton Add_Agent, Add_CompanyAgent, Report, Search, Call_History, View_Agent, View_Company, Logout;
    private BufferedImage backgroundImage;
    private float darknessFactor = 0.5f;

    public AdminMenu() {
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
        Add_Agent = new JButton("Add Agent");
        Add_CompanyAgent = new JButton("Add Company Agent");
        Report = new JButton("Report");
        Search = new JButton("Search");
        Call_History = new JButton("Call History");
        View_Agent = new JButton("View Agent");
        View_Company = new JButton("View Company");
        Logout = new JButton("Logout");
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "fill,350:380"));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "[light]background:darken(@background,3%);" +
                "[dark]background:lighten(@background,3%)");
        Add_Agent.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        Add_CompanyAgent.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        Report.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        Search.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        Call_History.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        View_Agent.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        View_Company.putClientProperty(FlatClientProperties.STYLE, "" +
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

        Add_Agent.addActionListener(e -> {
            FormsManager.getInstance().showForm(new Agent_Register());
        });

        Add_CompanyAgent.addActionListener(e -> {
            FormsManager.getInstance().showForm(new Company_Register());
        });

        Report.addActionListener(e -> {
            FormsManager.getInstance().showForm(new ReportMenu());
        });

        Search.addActionListener(e -> {
            FormsManager.getInstance().showForm(new SearchMenu());
        });

        Call_History.addActionListener(e -> {
            FormsManager.getInstance().showForm(new Calls_History());
        });

        View_Agent.addActionListener(e -> {
            FormsManager.getInstance().showForm(new View_Agents());
        });

        View_Company.addActionListener(e -> {
            FormsManager.getInstance().showForm(new View_Company());
        });

        Logout.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                FormsManager.getInstance().showForm(new LandingPage());
            }
        });


        panel.add(lbTitle);
        panel.add(lbDescription);
        panel.add(Add_Agent, "gapy 10");
        panel.add(Add_CompanyAgent, "gapy 10");
        panel.add(Report, "gapy 10");
        panel.add(Search, "gapy 10");
        panel.add(Call_History, "gapy 10");
        panel.add(View_Agent, "gapy 10");
        panel.add(View_Company, "gapy 10");
        panel.add(Logout, "gapy 10");
        add(panel);

    }
}

