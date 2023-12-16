package File.Java.AdminInterface.Function.Report;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import File.Java.AdminInterface.Menu.AdminMenu;
import File.Java.manager.FormsManager;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

public class ReportMenu extends JPanel {
    private JButton Agent_Report, Company_Report, Back;
    private BufferedImage backgroundImage;
    private float darknessFactor = 0.5f;

    public ReportMenu() {
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Resource/Images/Back.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Layout();
        setFocusable(true);
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(backgroundImage != null){
            g.drawImage(backgroundImage,0,0,getWidth(),getHeight(),this);
            g.setColor(new Color(0,0,0,(int)(255*darknessFactor)));
            g.fillRect(0,0,getWidth(),getHeight());
        }
    }

    private void Layout() {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));
        Agent_Report = new JButton("Agent Report");
        Company_Report = new JButton("Company Report");
        Back = new JButton("Back");
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "fill,350:380"));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "[light]background:darken(@background,3%);" +
                "[dark]background:lighten(@background,3%)");

        Agent_Report.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        Company_Report.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");
        Back.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        JLabel label = new JLabel("Report Menu");
        label.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +10");
        panel.add(label);
        panel.add(Agent_Report,"gapy 10");
        panel.add(Company_Report,"gapy 10");
        panel.add(Back,"gapy 10");
        add(panel);

        Agent_Report.addActionListener(e -> {
            FormsManager.getInstance().showForm(new Report_Agent());
        });

        Company_Report.addActionListener(e -> {
            FormsManager.getInstance().showForm(new Report_Company());
        });

        Back.addActionListener(e -> {
            FormsManager.getInstance().showForm(new AdminMenu());
        });
    }
}
