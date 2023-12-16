package File.Java.AdminInterface.Function.HistoryCall;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;


import File.Database.DB_Class;
import File.Java.AdminInterface.Function.Tables.Design_viewTables;
import File.Java.AdminInterface.Menu.AdminMenu;
import File.Java.manager.FormsManager;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import java.sql.Connection;
import java.sql.Date;

public class Calls_History extends JPanel {
    private JScrollPane scroll;
    private JTable table;
    private Connection connection = DB_Class.getConnection();


    public Calls_History(){
        initComponents();
        table.setDefaultRenderer(Object.class, new Design_viewTables());
        scroll.putClientProperty(FlatClientProperties.STYLE, "" + "border:3,0,3,0,$Table.background,10,10");
        scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, "" + "hoverTrackColor:null");
        testData("");
    }

    private void testData(String where, Object... search){
        try{
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            java.sql.PreparedStatement ps = connection.prepareStatement("SELECT * FROM callhistory " + where);
            for (int i = 0; i < search.length; i++) {
                ps.setObject(i + 1, search[i]);
            }
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Date date = rs.getDate("CallDate");
                int id = rs.getInt("AgentIDs");
                String name = rs.getString("CompanyNames");
                String address = rs.getString("CompanyServices");
                int ticket = rs.getInt("Ticket");
                String duration = rs.getString("callDuration");

                model.addRow(new Object[]{date,id, name, address,ticket, duration});
            }
            rs.close();
            ps.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void initComponents(){
        setLayout(new MigLayout("fill, insets 10", "[grow][]", "[]10[]10[]10[grow]"));

        JLabel lbTitle = new JLabel("Call History");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +10");
        add(lbTitle, "wrap, align center ");

        JComboBox<String> actionComboBox = new JComboBox<>(new String[] { "All", "Daily", "Weekly", "Monthly", "Yearly" });
        add(actionComboBox, "wrap, align right");
        actionComboBox.addActionListener(e -> {
            String action = (String) actionComboBox.getSelectedItem();
            switch (action) {
                case "All":
                    testData("");
                    break;
                case "Daily":
                    testData("WHERE CallDate = CURDATE()");
                    break;
                case "Weekly":
                    testData("WHERE YEARWEEK(CallDate) = YEARWEEK(CURDATE())");
                    break;
                case "Monthly":
                    testData("WHERE MONTH(CallDate) = MONTH(CURDATE()) AND YEAR(CallDate) = YEAR(CURDATE())");
                    break;
                case "Yearly":
                    testData("WHERE YEAR(CallDate) = YEAR(CURDATE())");
                    break;
            }
        });

        table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "Date", "Agent ID", "Company Name", "Service", "Ticket" , "Duration"}
        ){
            boolean[] canEdit = new boolean[]{false, false, false, false, false, false};

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
            FormsManager.getInstance().showForm(new AdminMenu());
        });
    }
}

