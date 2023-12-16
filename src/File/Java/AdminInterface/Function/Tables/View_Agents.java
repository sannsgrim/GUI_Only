package File.Java.AdminInterface.Function.Tables;


import File.Database.DB_Class;
import File.Java.AdminInterface.Menu.AdminMenu;
import File.Java.manager.FormsManager;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.*;

public class View_Agents extends JPanel {

    private JScrollPane scroll;
    private JTable table;
    private Connection connection = DB_Class.getConnection();


    public View_Agents() {
        initComponents();
        table.setDefaultRenderer(Object.class, new Design_viewTables());
        scroll.putClientProperty(FlatClientProperties.STYLE, "" + "border:3,0,3,0,$Table.background,10,10");
        scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, "" + "hoverTrackColor:null");
        testData("");
    }

    private void testData(String where, Object... search) {
        try{
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM agentinfo " + where);
            for (int i = 0; i < search.length; i++) {
                ps.setObject(i + 1, search[i]);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt("AgentID");
                String name = rs.getString("AgentFullName");
                String address = rs.getString("AgentStatus");

                model.addRow(new Object[]{id, name, address});
            }
            rs.close();
            ps.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void initComponents() {
        setLayout(new MigLayout("fill, insets 10", "[grow][]", "[]10[]10[]10[grow]"));

        JLabel lbTitle = new JLabel("View Agents");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +10");
        add(lbTitle, "wrap, align center ");

        JComboBox<String> actionComboBox = new JComboBox<>(new String[] { "All", "Active", "Inactive" });
        add(actionComboBox, "wrap, align right");
        actionComboBox.addActionListener(e -> {
            String action = (String) actionComboBox.getSelectedItem();
            assert action != null;
            if (action.equals("All")) {
                testData("");
            } else if (action.equals("Active")) {
                testData("WHERE AgentStatus = ?", "Active");
            } else if (action.equals("Inactive")) {
                testData("WHERE AgentStatus = ?", "Inactive");
            }
        });

        table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][] {},
                new String[] { "Id", "Agent Name", "Status"}
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
        add(closeButton, "wrap, align right");

        closeButton.addActionListener(e -> {
            FormsManager.getInstance().showForm(new AdminMenu());
        });

    }

}
