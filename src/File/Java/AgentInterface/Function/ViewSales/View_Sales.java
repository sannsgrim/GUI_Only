package File.Java.AgentInterface.Function.ViewSales;

import File.Java.AdminInterface.Function.Tables.Design_viewTables;
import File.Java.AdminInterface.Menu.AdminMenu;
import File.Java.AgentInterface.Function.InboundCall.Calling_View;
import File.Java.AgentInterface.Menu.AgentMenu;
import File.Java.manager.FormsManager;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class View_Sales extends JPanel{

    private JScrollPane scroll;
    private JTable table;


    public View_Sales() {
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

        JLabel lbTitle = new JLabel("CCATS: Sales");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +10");
        add(lbTitle, "wrap, align center");

        JLabel lbDescription = new JLabel("View all the sales taken daily, monthly, and yearly");
        lbDescription.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)");
        add(lbDescription, "wrap, align center");

        JComboBox<String> OldToRecent = new JComboBox<>(new String[] { "Recent to Oldest", "Oldest to Recent"});
        add(OldToRecent, "align left");
        OldToRecent.addActionListener(e -> {
        });

        JComboBox<String> DateSort = new JComboBox<>(new String[] { "All", "Daily", "Weekly", "Monthly"});
        add(DateSort, "wrap, align right");
        DateSort.addActionListener(e -> {
        });

        table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][] {},
                new String[] { "Ticket", "Client Name","Phone Number", "Company Name", "Date"}
        ) {
            boolean[] canEdit = new boolean[] { false, false, false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        table.setRowHeight(40);

        scroll = new JScrollPane(table);
        add(scroll, "span, grow, wrap");

        JButton closeButton = new JButton("Close");
        add(closeButton, "align left, wrap");


        closeButton.addActionListener(e -> {
            FormsManager.getInstance().showForm(new AgentMenu());
        });
    }

}



