package File.Java.AgentInterface.Function.InboundCall;

import File.Java.AdminInterface.Function.Tables.Design_viewTables;
import File.Java.AdminInterface.Menu.AdminMenu;
import File.Java.AgentInterface.Menu.AgentMenu;
import File.Java.manager.FormsManager;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class View_InboundCall extends JPanel{

    private JScrollPane scroll;
    private JTable table;


    public View_InboundCall() {
        initComponents();
        table.setDefaultRenderer(Object.class, new Design_viewTables());
        scroll.putClientProperty(FlatClientProperties.STYLE, "" + "border:3,0,3,0,$Table.background,10,10");
        scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, "" + "hoverTrackColor:null");
        testData("");
    }

    private void testData(String where, Object... search) {

    }


    private void initComponents() {
        setLayout(new MigLayout("fill, insets 10", "[grow][]", "[]10[]10[]10[center]"));

        JLabel lbTitle = new JLabel("CCATS: Inbound Call");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +10");
        add(lbTitle, "wrap, align center, span");

        JLabel lbDescription = new JLabel("View and Select the Incoming Calls");
        lbDescription.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)");
        add(lbDescription, "wrap, align center, span");

        JLabel infoShow = new JLabel("Incoming Calls . . .");
        infoShow.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)");
        add(infoShow, "wrap, align left");



        table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][] {},
                new String[] { "Client Name", "Phone Number", "Time"}
        ) {
            boolean[] canEdit = new boolean[] { false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        table.setRowHeight(40);

        scroll = new JScrollPane(table);
        add(scroll, "span, grow, wrap");

        JButton closeButton = new JButton("Close");
        add(closeButton, "align left");

        JButton takeCall = new JButton("Take Call");
        takeCall.setBackground(Color.ORANGE);
        takeCall.setForeground(Color.BLACK);
        add(takeCall, "wrap, align right, gap 750, split 2");

        closeButton.addActionListener(e -> {
            FormsManager.getInstance().showForm(new AgentMenu());
        });

        takeCall.addActionListener(e -> {
            FormsManager.getInstance().showForm(new Calling_View());
        });

    }

}


