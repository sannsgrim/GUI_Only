package File.Java.ClientInterface.Function.SelectService;

import javax.swing.*;

//import File.Java.AdminInterface.Function.Tables.Design_viewTables;
import File.Java.AdminInterface.Menu.AdminMenu;
import File.Java.manager.FormsManager;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import java.awt.*;
import java.sql.*;
import java.util.*;


public class SelectService extends JPanel{
    //private JScrollPane scroll;
    private JTable table;
    private JComboBox<String> companyComboBox;
    private JComboBox<String> serviceComboBox;


    public SelectService(){
        initComponents();
        putClientProperty(FlatClientProperties.STYLE, "" + "border:3,0,3,0,$Table.background,10,10");
        setCompanyComboBoxData(getCompanyNamesFromDB());
        setServiceComboBoxData(getServiceNamesFromDB());
        setCallButtonPosition();
        testData("");
    }

    private void testData(String where, Object... search){}

    public void initComponents(){
        setLayout(new MigLayout("fill, insets 10", "[grow][]", "[]10[]10[]10[grow]"));

        JLabel lbTitle = new JLabel("CCATS: Selecting Services");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +10");
        add(lbTitle, "wrap, align center, span 2,gaptop 20 ");

        JLabel lbdescription = new JLabel("Select your transaction.");
        lbdescription.putClientProperty(FlatClientProperties.STYLE, "" + "font:regular +5");
        add(lbdescription, "wrap, align center, span 2, gapbottom 80");


    }

    private ArrayList<String> getCompanyNamesFromDB() {
        ArrayList<String> companyNames = new ArrayList<>();
        try {
            String DB_URL = "jdbc:mysql://localhost/ccats";
            String USER = "root";
            String PASS = "";
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            if (conn != null) {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT companyName FROM companyInfo");

                while (resultSet.next()) {
                    String companyName = resultSet.getString("companyName");
                    companyNames.add(companyName);
                }

                resultSet.close();
                statement.close();
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companyNames;
    }
    private void setCompanyComboBoxData(ArrayList<String> companyData) {
        if (companyData != null) {
            String[] companyArray = companyData.toArray(new String[0]);
            companyComboBox = new JComboBox<>(companyArray);
            companyComboBox.setFont(companyComboBox.getFont().deriveFont(Font.PLAIN, 16));
            companyComboBox.setPreferredSize(new Dimension(200, companyComboBox.getPreferredSize().height));
            add(companyComboBox, "align center, split 2, gapbottom 20, gapright 50");
            companyComboBox.addActionListener(e -> {
                String selectedCompany = (String) companyComboBox.getSelectedItem();
                // insert logic here

            });
        } else {
            companyComboBox = new JComboBox<>(new String[]{"No companies available"});
            companyComboBox.setFont(companyComboBox.getFont().deriveFont(Font.PLAIN, 16));
            companyComboBox.setPreferredSize(new Dimension(200, companyComboBox.getPreferredSize().height));
            add(companyComboBox, "align center, split 2, gapbottom 20, gapright 50");
            companyComboBox.setEnabled(false);
        }
    }

    private ArrayList<String> getServiceNamesFromDB() {
        ArrayList<String> serviceNames = new ArrayList<>();
        try {
            String DB_URL = "jdbc:mysql://localhost/ccats";
            String USER = "root";
            String PASS = "";
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            if (conn != null) {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT DISTINCT companyService FROM companyInfo");

                while (resultSet.next()) {
                    String serviceName = resultSet.getString("companyService");
                    serviceNames.add(serviceName);
                }

                resultSet.close();
                statement.close();
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: Unable to fetch service names from the database.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return serviceNames;
    }

    private void setServiceComboBoxData(ArrayList<String> serviceData) {
        if (serviceData != null && !serviceData.isEmpty()) {
            String[] serviceArray = serviceData.toArray(new String[0]);
            serviceComboBox = new JComboBox<>(serviceArray);
            serviceComboBox.setFont(serviceComboBox.getFont().deriveFont(Font.PLAIN, 16));
            serviceComboBox.setPreferredSize(new Dimension(200, serviceComboBox.getPreferredSize().height));
            add(serviceComboBox, "align right, wrap");
            serviceComboBox.addActionListener(e -> {
                String selectedService = (String) serviceComboBox.getSelectedItem();
                //insert logic here

            });
        } else {
            serviceComboBox = new JComboBox<>(new String[]{"No services available"});
            serviceComboBox.setFont(serviceComboBox.getFont().deriveFont(Font.PLAIN, 16));
            serviceComboBox.setPreferredSize(new Dimension(200, serviceComboBox.getPreferredSize().height));
            add(serviceComboBox, "align right, wrap");
            serviceComboBox.setEnabled(false);
        }
    }

    public void setCallButtonPosition() {
        // Remove any existing Call button from the layout
        Component[] components = getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                remove(component);
            }
        }

        // Re-add the Call button to the layout
        JButton cmdCall = new JButton("Call");
        cmdCall.setFont(cmdCall.getFont().deriveFont(Font.PLAIN, 16));
        cmdCall.setPreferredSize(new Dimension(200, cmdCall.getPreferredSize().height));
        add(cmdCall, "span 2, align center, wrap");

        cmdCall.addActionListener(e -> {
            FormsManager.getInstance().showForm(new Client_Calling_View());
        });

        revalidate();
        repaint();
    }

    

}


