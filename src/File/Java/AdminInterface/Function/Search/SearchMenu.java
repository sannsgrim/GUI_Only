package File.Java.AdminInterface.Function.Search;

import File.Java.AdminInterface.Function.Search.Search_Agents;
import File.Java.AdminInterface.Function.Search.Search_Company;
import File.Java.AdminInterface.Menu.AdminMenu;
import File.Java.manager.FormsManager;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class SearchMenu extends JPanel {

    private JButton Agent_Search, Company_Search, Back;
    private BufferedImage backgroundImage;
    private float darknessFactor = 0.5f;

    public SearchMenu() {
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
        Agent_Search = new JButton("Search Agent");
        Company_Search = new JButton("Search Company");
        Back = new JButton("Back");
        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "fill,350:380"));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "[light]background:darken(@background,3%);" +
                "[dark]background:lighten(@background,3%)");

        Agent_Search.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");

        Company_Search.putClientProperty(FlatClientProperties.STYLE, "" +
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

        JLabel label = new JLabel("Search Menu");
        label.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +10");

        panel.add(label);

        panel.add(Agent_Search, "gapy 10");
        panel.add(Company_Search, "gapy 10");
        panel.add(Back, "gapy 10");
        add(panel);

        Agent_Search.addActionListener(e -> {
            FormsManager.getInstance().showForm(new Search_Agents());
        });

        Company_Search.addActionListener(e -> {
            FormsManager.getInstance().showForm(new Search_Company());
        });

        Back.addActionListener(e -> {
            FormsManager.getInstance().showForm(new AdminMenu());
        });

    }


}
