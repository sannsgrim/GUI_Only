package File.Java.AdminInterface.Function.Search;

import File.Database.DB_Class;
import File.Java.AdminInterface.Function.Tables.Design_searchTables;
import File.Java.AdminInterface.Function.Tables.TableActionCellEditor;
import File.Java.AdminInterface.Function.Tables.TableActionEvent;
import File.Java.AdminInterface.Menu.AdminMenu;
import File.Java.manager.FormsManager;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Search_Company extends JPanel {

    private JScrollPane scroll;
    private JTable table;
    private Search_TextField search;
    private Connection connection = DB_Class.getConnection();
    private DefaultTableModel model;

    public Search_Company() {
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                editData(row);
            }

            @Override
            public void onDelete(int row) {
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this data?", "Delete Data", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    deleteData(row);
                } else {
                    JOptionPane.getRootFrame().dispose();
                }
            }
        };

        initComponents();
        initComponentsSearch();

        table.getColumnModel().getColumn(3).setCellRenderer(new Design_searchTables());
        table.getColumnModel().getColumn(3).setCellEditor(new TableActionCellEditor(event));
        scroll.putClientProperty(FlatClientProperties.STYLE, "" + "border:3,0,3,0,$Table.background,10,10");
        scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, "" + "hoverTrackColor:null");
        model = (DefaultTableModel) table.getModel();
        table.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
                setHorizontalAlignment(SwingConstants.LEFT);
                return super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1);
            }
        });
        testData("");
    }

    private void editData(int row) {
        EditDialog editDialog = new EditDialog(
                (int) model.getValueAt(row, 0),
                (String) model.getValueAt(row, 1),
                (String) model.getValueAt(row, 2)
        );

        editDialog.setEditDialogListener(() -> {
            System.out.println("Save changes event triggered.");
            model.setValueAt(editDialog.getNo(), row, 0);
            model.setValueAt(editDialog.getName(), row, 1);
            model.setValueAt(editDialog.getService(), row, 2);

            model.fireTableCellUpdated(row, 0);
            model.fireTableCellUpdated(row, 1);
            model.fireTableCellUpdated(row, 2);
        });

        editDialog.setVisible(true);
    }

    private void deleteData(int row) {
        model.removeRow(row);
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
                int CompanyNo = rs.getInt("CompanyNo");
                String CompanyName = rs.getString("CompanyName");
                String Service = rs.getString("CompanyService");

                model.addRow(new Object[]{CompanyNo, CompanyName, Service});
            }
            rs.close();
            ps.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void initComponentsSearch() {
        search.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (search.getText().isEmpty()) {
                    testData("");
                }
            }
        });
        search.addEvent(new SearchTextEvent() {
            @Override
            public void onPressed(Search_Event call) {
                try {
                    for (int i = 1; i <= 50; i++) {
                        Thread.sleep(10);
                    }
                    String where = "WHERE CompanyName LIKE ?";
                    testData(where, "%" + search.getText() + "%");
                    call.done();
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onCancel() {
                InterruptedException e = new InterruptedException();
                e.printStackTrace();
            }
        });
    }

    private void initComponents() {
        setLayout(new MigLayout("fill, insets 10", "[grow][]", "[]10[]10[]10[grow]"));

        JLabel lbTitle = new JLabel("Search Company");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +10");
        add(lbTitle, "wrap, align center ");

        search = new Search_TextField();
        search.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Company Name");
        search.setPreferredSize(new Dimension(300, 30));
        add(search, "gapy 20, wrap, align left");

        table = new JTable();

        table.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Company No", "Company Name", "Service", "Action"}
        ) {
            boolean[] canEdit = new boolean[]{false, false, false, true};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        table.setRowHeight(40);
        table.setSelectionBackground(new java.awt.Color(20, 2, 100, 255));

        scroll = new JScrollPane(table);
        add(scroll, "span, grow, wrap");

        JButton closeButton = new JButton("Close");
        add(closeButton, "align right, wrap");

        closeButton.addActionListener(e -> {
            FormsManager.getInstance().showForm(new AdminMenu());
        });
    }
}

class EditDialog extends JDialog {

    private JTextField Company_nameTextField;
    private JTextField CompanyNoTextField;
    private JTextField ServiceTextField;
    private Connection connection = DB_Class.getConnection();
    private boolean changesMade;

    public interface EditDialogListener {
        void onSaveChanges();
    }

    private EditDialogListener editDialogListener;

    public EditDialog(int CompanyNo, String CompanyName, String Service) {
        setTitle("Edit Company Information");
        setSize(400, 250);
        setLayout(new MigLayout("fill", "[][grow]"));
        setLocationRelativeTo(null);

        CompanyNoTextField = new JTextField(String.valueOf(CompanyNo));
        CompanyNoTextField.setEditable(false);
        Company_nameTextField = new JTextField(CompanyName);
        ServiceTextField = new JTextField(Service);

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> {
            changesMade = true;
            if (editDialogListener != null) {
                editDialogListener.onSaveChanges();
            }
            updateDatabase();
            dispose();
        });

        cancelButton.addActionListener(e -> {
            dispose();
        });

        add(new JLabel("Company ID:"));
        add(CompanyNoTextField, "growx, wrap");
        add(new JLabel("Company  Name:"));
        add(Company_nameTextField, "growx, wrap");
        add(new JLabel("Service:"));
        add(ServiceTextField, "growx, wrap");
        add(saveButton, "split 2, align right");
        add(cancelButton, "align right");
    }

    public void setEditDialogListener(EditDialogListener listener) {
        this.editDialogListener = listener;
    }
    private void updateDatabase() {
        try {
            String updateQuery = "UPDATE companyinfo SET CompanyName=?, companyService=? WHERE CompanyNo=?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                preparedStatement.setString(1, getName());
                preparedStatement.setString(2, getService());
                preparedStatement.setInt(3, getNo());

                preparedStatement.executeUpdate();
                preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return Company_nameTextField.getText();
    }

    public int getNo() {
        return Integer.parseInt(CompanyNoTextField.getText());
    }

    public String getService() {
        return ServiceTextField.getText();
    }
}
