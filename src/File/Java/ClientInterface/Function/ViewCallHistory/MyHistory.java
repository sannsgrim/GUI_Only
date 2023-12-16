package File.Java.ClientInterface.Function.ViewCallHistory;

import File.Java.AdminInterface.Function.Tables.Design_viewTables;
import File.Java.AdminInterface.Menu.AdminMenu;
import File.Java.ClientInterface.Menu.ClientMenu;
import File.Java.manager.FormsManager;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MyHistory extends JPanel{
    private JScrollPane scroll;
    private JTable table;


    public MyHistory(){
        initComponents();
        table.setDefaultRenderer(Object.class, new Design_viewTables());
        scroll.putClientProperty(FlatClientProperties.STYLE, "" + "border:3,0,3,0,$Table.background,10,10");
        scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, "" + "hoverTrackColor:null");
        testData("");
    }

    private void testData(String where, Object... search){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        model.addRow(new Object[]{"Customer Repair", "CircuitRep", "123456789", "2021-04-01"});
        model.addRow(new Object[]{"Customer Service", "PlayTech", "123456789", "2021-04-01"});
        model.addRow(new Object[]{"Customer Support", "TechTown", "123456789", "2021-04-01"});
    }


    public void initComponents(){
        setLayout(new MigLayout("fill, insets 10", "[grow][]", "[]10[]10[]10[grow]"));

        JLabel lbTitle = new JLabel("CCATS: My History");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +10");
        add(lbTitle, "wrap, align center ");

        JLabel lbdescription = new JLabel("Records of taken calls.");
        add(lbdescription, "wrap, align center ");

        JComboBox<String> actionComboBox = new JComboBox<>(new String[] { "Recent to Oldest", "Oldest to Recent"});
        add(actionComboBox, "wrap, align left");
        actionComboBox.addActionListener(e -> {
            String action = (String) actionComboBox.getSelectedItem();
            switch (action) {
                case "Recent to Oldest":
                    testData("");
                    break;
                case "Oldest to Recent":
                    testData("WHERE CallDate = CURDATE()");
                    break;
            }
        });

        table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "Purchased/Services", "Company Name", "Ticket", "Date"}
        ){
            boolean[] canEdit = new boolean[]{false, false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex){
                return canEdit[columnIndex];
            }
        });
        table.setRowHeight(40);

        scroll = new JScrollPane(table);
        add(scroll, "span,grow, wrap");

        JButton cmdBack = new JButton("Back");
        add(cmdBack, "wrap, align right");

        cmdBack.addActionListener(e -> {
            FormsManager.getInstance().showForm(new ClientMenu());
        });
    }
}

