package File.Java.AdminInterface.Function.Report;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;

import File.Database.DB_Class;
import File.Java.AdminInterface.Function.Tables.Design_viewTables;
import File.Java.AdminInterface.Menu.AdminMenu;
import File.Java.manager.FormsManager;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

public class Report_Agent extends JPanel {
    private JScrollPane scroll;
    private JTable table;
    private Connection connection = DB_Class.getConnection();


    public Report_Agent (){
        initComponents();
        table.setDefaultRenderer(Object.class, new Design_viewTables());
        scroll.putClientProperty(FlatClientProperties.STYLE, "" + "border:3,0,3,0,$Table.background,10,10");
        scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, "" + "hoverTrackColor:null");
        testData("");
    }

    private void testData(String where, Object... search) {
        try {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            String baseQuery = "SELECT agentInfo.agentID, agentInfo.agentFullName, " +
                    "COALESCE(COUNT(callhistory.ticket), 0) AS TotalTickets " +
                    "FROM agentInfo " +
                    "LEFT JOIN callhistory ON agentInfo.agentID = callhistory.agentIDs ";

            String timeQuery = "";
            switch (where) {
                case "All":
                    timeQuery = "";
                    break;
                case "Daily":
                    timeQuery = "WHERE callDate = CURDATE() ";
                    break;
                case "Weekly":
                    timeQuery = "WHERE callDate BETWEEN DATE_SUB(CURDATE(), INTERVAL WEEKDAY(CURDATE()) DAY) AND CURDATE() ";
                    break;
                case "Monthly":
                    timeQuery = "WHERE YEAR(callDate) = YEAR(CURDATE()) AND MONTH(callDate) = MONTH(CURDATE()) ";
                    break;
                default:
                    timeQuery = "";
                    break;
            }

            String finalQuery = baseQuery + timeQuery + "GROUP BY agentInfo.agentID, agentInfo.agentFullName";

            java.sql.PreparedStatement ps = connection.prepareStatement(finalQuery);

            for (int i = 0; i < search.length; i++) {
                ps.setObject(i + 1, search[i]);
            }
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("agentID");
                String name = rs.getString("agentFullName");
                int totalTickets = rs.getInt("TotalTickets");

                model.addRow(new Object[]{id, name, totalTickets});
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void initComponents(){
        setLayout(new MigLayout("fill, insets 10", "[grow][]", "[]10[]10[]10[grow]"));

        JLabel lbTitle = new JLabel("Report Agents");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +10");
        add(lbTitle, "wrap, align center ");

        JComboBox<String> actionComboBox = new JComboBox<>(new String[] { "All", "Daily", "Weekly", "Monthly" });
        add(actionComboBox, "wrap, align right");

        actionComboBox.addActionListener(e -> {
            String action = (String) actionComboBox.getSelectedItem();
            switch (action) {
                case "All":
                    testData("All");
                    break;
                case "Daily":
                    testData("Daily");
                    break;
                case "Weekly":
                    testData("Weekly");
                    break;
                case "Monthly":
                    testData("Monthly");
                    break;
            }
        });

        table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "Agent ID", "Agent Name", "Ticket Taken"}
        ){
            boolean[] canEdit = new boolean[]{false, false, false};

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
