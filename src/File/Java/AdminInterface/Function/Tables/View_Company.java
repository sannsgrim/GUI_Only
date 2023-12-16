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

public class View_Company extends JPanel {

    private JScrollPane scroll;
    private JTable table;
    private Connection connection = DB_Class.getConnection();

    public View_Company() {
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
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM companyinfo " + where);
            for (int i = 0; i < search.length; i++) {
                ps.setObject(i + 1, search[i]);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt("CompanyNo");
                String name = rs.getString("CompanyName");
                String service = rs.getString("CompanyService");

                model.addRow(new Object[]{name, id, service});
            }
            rs.close();
            ps.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void initComponents() {
        setLayout(new MigLayout("fill, insets 10", "[grow][]", "[]10[]10[]10[grow]"));

        JLabel lbTitle = new JLabel("View Company");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +10");
        add(lbTitle, "wrap, align center ");

        table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][] {},
                new String[] { "Company Name", "Company No", "Service"}
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
