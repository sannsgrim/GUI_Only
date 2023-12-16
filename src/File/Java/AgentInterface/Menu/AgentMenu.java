package File.Java.AgentInterface.Menu;

import File.Java.AgentInterface.Function.InboundCall.View_InboundCall;
import File.Java.AgentInterface.Function.ViewSales.View_Sales;
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

public class AgentMenu extends JPanel {

    private JButton View_InboundCalls, View_Sale, Logout;
    private BufferedImage backgroundImage;
    private float darknessFactor = 0.5f;

    public AgentMenu() {
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
        View_InboundCalls = new JButton("View Inbound Calls");
        View_Sale = new JButton("View Sales");
        Logout = new JButton("Logout");

        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "fill,350:380"));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "[light]background:darken(@background,3%);" +
                "[dark]background:lighten(@background,3%)");
        View_InboundCalls.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        View_Sale.putClientProperty(FlatClientProperties.STYLE, "" +
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

        JLabel lbTitle = new JLabel("CCATS: Agent Menu");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +10");
        JLabel lbDescription = new JLabel("What would you like to do?");
        lbDescription.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)");

        Logout.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                FormsManager.getInstance().showForm(new LandingPage());
            }
        });

        View_InboundCalls.addActionListener(e -> {
            FormsManager.getInstance().showForm(new View_InboundCall());
        });

        View_Sale.addActionListener(e -> {
            FormsManager.getInstance().showForm(new View_Sales());
        });



        panel.add(lbTitle);
        panel.add(lbDescription);
        panel.add(View_InboundCalls, "gapy 10");
        panel.add(View_Sale, "gapy 10");
        panel.add(Logout, "gapy 10");
        add(panel);

    }

}
