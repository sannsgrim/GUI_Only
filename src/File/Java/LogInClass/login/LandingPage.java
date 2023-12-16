package File.Java.LogInClass.login;

import File.Java.manager.FormsManager;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class LandingPage extends JPanel {

    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private ImageIcon icon;



    public LandingPage() {
        FlatMacDarkLaf.setup();
        initComponents();
        setBackground(Color.decode("#373737"));
        addActionEvent();
    }

    @SuppressWarnings("unchecked")

    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();





        jLabel1.setText("Welcome to");
        try{
            Font fileF = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Resource/Fonts/WorkSans-Medium.ttf"));
            jLabel1.setFont(fileF.deriveFont(Font.PLAIN, 30f));
        }catch (FontFormatException | IOException ex) {
            ex.printStackTrace();
        }

        jLabel2.setText("Call Center Agent");
        try{
            Font fileF = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Resource/Fonts/WorkSans-SemiBold.ttf"));
            jLabel2.setFont(fileF.deriveFont(Font.PLAIN, 40f));
        }catch (FontFormatException | IOException ex) {
            ex.printStackTrace();
        }

        jLabel3.setText("Tracking System");
        try{
            Font fileF = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Resource/Fonts/WorkSans-SemiBold.ttf"));
            jLabel3.setFont(fileF.deriveFont(Font.PLAIN, 40f));
        }catch (FontFormatException | IOException ex) {
            ex.printStackTrace();
        }
        jLabel4.setText("Connecting Voices, Empowering");
        try{
            Font fileF = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Resource/Fonts/WorkSans-Medium.ttf"));
            jLabel4.setFont(fileF.deriveFont(Font.PLAIN, 15f));
        }catch (FontFormatException | IOException ex) {
            ex.printStackTrace();
        }

        jLabel5.setText("Businesses: Your Call Center Solution");
        try{
            Font fileF = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Resource/Fonts/WorkSans-Medium.ttf"));
            jLabel5.setFont(fileF.deriveFont(Font.PLAIN, 15f));
        }catch (FontFormatException | IOException ex) {
            ex.printStackTrace();
        }

        jLabel6.setText("Log-in As:");
        try{
            Font fileF = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Resource/Fonts/WorkSans-Medium.ttf"));
            jLabel6.setFont(fileF.deriveFont(Font.PLAIN, 15f));
        }catch (FontFormatException | IOException ex) {
            ex.printStackTrace();
        }

        jButton1.setText("Admin Login");

        jButton2.setText("Agent Login");

        jButton3.setText("Client Login");


        BufferedImage img = null;
        try {
            img = ImageIO.read(getClass().getResource("/Resource/Images/Icon.png"));
        }catch (IOException ex){
            ex.printStackTrace();
        }

        assert img != null;
        Image dimg = img.getScaledInstance(693, 593, Image.SCALE_SMOOTH);
        icon = new ImageIcon(dimg);
        jLabel7.setIcon(icon);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel6)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 788, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 603, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel3)
                                                .addGap(12, 12, 12)
                                                .addComponent(jLabel4)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel5)
                                                .addGap(133, 133, 133)
                                                .addComponent(jLabel6)
                                                .addGap(18, 18, 18)
                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(16, Short.MAX_VALUE))
        );




    }

    public void addActionEvent (){
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormsManager.getInstance().showForm(new Admin_Login());
            }
        });
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormsManager.getInstance().showForm(new Agent_Login());
            }
        });
        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormsManager.getInstance().showForm(new Client_Login());
            }
        });
    }


}
