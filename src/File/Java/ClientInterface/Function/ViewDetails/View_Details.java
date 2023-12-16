package File.Java.ClientInterface.Function.ViewDetails;

import File.Java.AdminInterface.Menu.AdminMenu;
import File.Java.manager.FormsManager;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import File.Java.LogInClass.component.PasswordStrengthStatus;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;
public class View_Details extends JPanel{

    public View_Details(){
        initComponents();

    }



    public void initComponents(){
        setLayout(new MigLayout("fill, insets 10", "[grow][]", "[]10[]10[]10[grow]"));

        JLabel lbTitle = new JLabel("CCATS: View Details");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +10");
        add(lbTitle, "wrap, align center ");

        JLabel lbdescription = new JLabel("Displaying as if the client already talking to an agent to take his/her orders.");
        add(lbdescription, "wrap, align center, gapbottom 70");

        JLabel lbname = new JLabel("Name:");
        add(lbname, "wrap, gapleft 150");

        JLabel lbservices = new JLabel("Services:");
        add(lbservices, "wrap, gapleft 150 ");

        JLabel lbaddress = new JLabel("Address:");
        add(lbaddress, "wrap, gapleft 150 ");

        JLabel lbticketnumber = new JLabel("Ticket Number:");
        add(lbticketnumber, "wrap, gapleft 150 ");

        JButton cmdDiscard = new JButton("Discard");
        add(cmdDiscard, "align left");

        JButton cmdDone = new JButton("Done");
        add(cmdDone, "align right");

        cmdDiscard.addActionListener(e -> {
            FormsManager.getInstance().showForm(new AdminMenu());
        });

        cmdDone.addActionListener(e -> {
            FormsManager.getInstance().showForm(new AdminMenu());
        });
    }
}

