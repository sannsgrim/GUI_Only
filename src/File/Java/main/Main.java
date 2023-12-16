package File.Java.main;


import File.Database.DB_Class;
import File.Java.AdminInterface.Function.Search.Search_Agents;
import File.Java.AdminInterface.Function.Search.Search_Company;
import File.Java.AdminInterface.Function.Tables.View_Company;
import File.Java.AgentInterface.Function.InboundCall.Calling_View;
import File.Java.AgentInterface.Function.InboundCall.View_InboundCall;
import File.Java.AgentInterface.Function.ViewSales.View_Sales;
import File.Java.AgentInterface.Menu.AgentMenu;
import File.Java.ClientInterface.Function.SelectService.SelectService;
import File.Java.ClientInterface.Function.ViewCallHistory.MyHistory;
import File.Java.ClientInterface.Function.ViewDetails.View_Details;
import File.Java.ClientInterface.Menu.ClientMenu;
import File.Java.ClientInterface.SignUpClass.Client_SignUp;
import File.Java.LogInClass.login.Client_Login;
import File.Java.LogInClass.login.LandingPage;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.*;
import File.Java.manager.FormsManager;

import javax.swing.*;
import java.awt.*;


public class Main extends JFrame{

    public Main(){

        TopView();
    }
    public void TopView (){
        setTitle("Call Center System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1200, 750));
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Resource/Images/tel.png"));
        setIconImage(icon);
        setContentPane(new LandingPage());
        setResizable(true);
        setLocationRelativeTo(null);
        FormsManager.getInstance().LayoutMain(this);
    }
    public static void main(String[] args) {
        DB_Class initializeDB = new DB_Class();
        initializeDB.createDataBase();
        initializeDB.createPresetAdminAccount();

        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("Resource");
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        FlatMacDarkLaf.setup();
        EventQueue.invokeLater(() -> new Main().setVisible(true));
    }
}