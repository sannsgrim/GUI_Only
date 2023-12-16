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
import java.sql.PreparedStatement;
import java.sql.*;

public class Search_Agents extends JPanel {

    private JScrollPane scroll;
    private JTable table;
    private Search_TextField search;
    private Connection connection = DB_Class.getConnection();
    private DefaultTableModel model;

    public Search_Agents() {
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                editData(row);
            }
            @Override
            public void onDelete(int row) {
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this data?", "Delete Data", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION){
                    deleteData(row);
                }else{
                    JOptionPane.getRootFrame().dispose();
                }
            }
        };
        initComponents();
        initComponentsSearch();

        table.getColumnModel().getColumn(6).setCellRenderer(new Design_searchTables());
        table.getColumnModel().getColumn(6).setCellEditor(new TableActionCellEditor(event));
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
        EditDialogs editDialog = new EditDialogs(
                connection,
                (int) model.getValueAt(row, 0),
                (String) model.getValueAt(row, 2),
                (String) model.getValueAt(row, 3),
                (int) model.getValueAt(row, 4),
                (String) model.getValueAt(row, 5)
        );

        editDialog.setEditDialogListener(() -> {
            System.out.println("Save changes event triggered.");
            model.setValueAt(editDialog.getName(), row, 2);
            model.setValueAt(editDialog.getGender(), row, 3);
            model.setValueAt(editDialog.getAge(), row, 4);
            model.setValueAt(editDialog.getAddress(), row, 5);

            model.fireTableCellUpdated(row, 2);
            model.fireTableCellUpdated(row, 3);
            model.fireTableCellUpdated(row, 4);
            model.fireTableCellUpdated(row, 5);
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
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM agentinfo " + where);
            for (int i = 0; i < search.length; i++) {
                ps.setObject(i + 1, search[i]);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt("AgentID");
                String name = rs.getString("AgentFullName");
                String gender = rs.getString("AgentGender");
                int age = rs.getInt("AgentAge");
                String address = rs.getString("AgentAddress");
                Date hire = rs.getDate("dateHire");

                model.addRow(new Object[]{ id, hire, name, gender, age, address, null});
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
                    String where = "WHERE AgentID LIKE ?";
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
        setLayout(new MigLayout("fill, insets 10", "[grow][]", "[]10[]10[]10[center]"));

        JLabel lbTitle = new JLabel("Search Agents");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +10");
        add(lbTitle, "wrap, align center ");

        search = new Search_TextField();
        search.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Agents ID");
        search.setPreferredSize(new Dimension(300, 30));
        add(search, "gapy 20, align left, split 2");

        JComboBox<String> actionComboBox = new JComboBox<>(new String[] { "All", "Oldest to Recent", "Recent to Oldest"});
        add(actionComboBox, "gap 750, wrap, align right");
        actionComboBox.addActionListener(e -> {
            String action = (String) actionComboBox.getSelectedItem();
            assert action != null;
            if (action.equals("Recent to Oldest")){
                testData("ORDER BY dateHire DESC");
            } else if (action.equals("Oldest to Recent")){
                testData("ORDER BY dateHire ASC");
            } else if (action.equals("All")) {
                testData("");
            }
        });


        table = new JTable();
        table.setModel(new DefaultTableModel(
                new Object[][] {},
                new String[] { "Id", "Hire at", "Agent Name","Gender", "Age", "Address", "Action"}
        ) {
            boolean[] canEdit = new boolean[] { false, false, false, false, false, false, true };

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

class EditDialogs extends JDialog {


    private JTextField nameTextField;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private JTextField ageTextField;
    private JTextField addressTextField;
    private Connection connection = DB_Class.getConnection();;
    private boolean changesMade;
    public interface EditDialogListener {
        void onSaveChanges();
    }
    private EditDialogListener editDialogListener;

    public EditDialogs(Connection connection, int id, String name, String gender, int age, String address) {
        setTitle("Edit Agent Information");
        setSize(400, 250);
        setLayout(new MigLayout("fill", "[][grow]"));
        setLocationRelativeTo(null);

        nameTextField = new JTextField(name);
        maleRadioButton = new JRadioButton("Male");
        femaleRadioButton = new JRadioButton("Female");
        ageTextField = new JTextField(String.valueOf(age));
        addressTextField = new JTextField(address);

        if ("Male".equals(gender)) {
            maleRadioButton.setSelected(true);
        } else {
            femaleRadioButton.setSelected(true);
        }

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> {
            changesMade = true;
            if (editDialogListener != null) {
                editDialogListener.onSaveChanges();
            }
            updateDatabase(id);
            dispose();
        });

        cancelButton.addActionListener(e -> {
            dispose();
        });

        add(new JLabel("ID:"));
        add(new JLabel(String.valueOf(id)), "wrap");
        add(new JLabel("Name:"));
        add(nameTextField, "growx, wrap");
        add(new JLabel("Gender:"));
        add(maleRadioButton, "split 2");
        add(femaleRadioButton, "wrap");
        add(new JLabel("Age:"));
        add(ageTextField, "growx, wrap");
        add(new JLabel("Address:"));
        add(addressTextField, "growx, wrap");
        add(saveButton, "split 2, align right");
        add(cancelButton, "align right");
    }

    public void setEditDialogListener(EditDialogListener listener) {
        this.editDialogListener = listener;
    }
    private void updateDatabase(int id) {
        try {
            String updateQuery = "UPDATE agentinfo SET AgentFullName=?, AgentGender=?, AgentAge=?, AgentAddress=? WHERE AgentID=?";
            PreparedStatement ps = connection.prepareStatement(updateQuery);

            ps.setString(1, getName());
            ps.setString(2, getGender());
            ps.setInt(3, getAge());
            ps.setString(4, getAddress());
            ps.setInt(5, id);

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return nameTextField.getText();
    }

    public String getGender() {
        return maleRadioButton.isSelected() ? "Male" : "Female";
    }

    public int getAge() {
        return Integer.parseInt(ageTextField.getText());
    }

    public String getAddress() {
        return addressTextField.getText();
    }

}
