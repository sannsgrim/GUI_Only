package File.Java.AgentInterface.Function.InboundCall;
import File.Java.AdminInterface.Function.Tables.ActionButton;
import File.Java.AgentInterface.Menu.AgentMenu;
import File.Java.manager.FormsManager;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Objects;


public class Calling_View extends JPanel {

    private JLabel jLabel2;
    private JLabel jLabel1;
    private JLabel jLabel3;
    private ActionButton jButton1;
    private JLabel jLabel4;
    private boolean isTimerRunning;
    private long startTime;



    public Calling_View() {
        initComponents();
        startTimer();
    }

    private String stopTimer() {
        isTimerRunning = false;
        long elapsedTime = System.currentTimeMillis() - startTime;
        int hours = (int) (elapsedTime / 3600000);
        int minutes = (int) ((elapsedTime / 60000) % 60);
        int seconds = (int) ((elapsedTime / 1000) % 60);

        DecimalFormat df = new DecimalFormat("00");
        String formattedTime = df.format(hours) + ":" + df.format(minutes) + ":" + df.format(seconds);

        jLabel4.setText("Duration : " +formattedTime);


        return formattedTime;
    }

    private void startTimer() {
        isTimerRunning = true;
        startTime = System.currentTimeMillis();
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTimerLabel();
            }
        });
        timer.start();
    }

    private void updateTimerLabel() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        int hours = (int) (elapsedTime / 3600000);
        int minutes = (int) ((elapsedTime / 60000) % 60);
        int seconds = (int) ((elapsedTime / 1000) % 60);

        DecimalFormat df = new DecimalFormat("00");
        String formattedTime = df.format(hours) + ":" + df.format(minutes) + ":" + df.format(seconds);

        jLabel4.setText("Duration :  "+formattedTime);
    }


    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        jButton1 = new ActionButton();
        jLabel4 = new JLabel();



        Icon loadingGif = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Resource/Images/Call.gif")));




        jLabel1 = new JLabel(loadingGif);
        jLabel1.setIcon(loadingGif);

        jLabel1.setBorder(BorderFactory.createEmptyBorder());


        jLabel2.setText("CCATS : Calling Client");
        try{
            Font fileF = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Resource/Fonts/WorkSans-SemiBold.ttf"));
            jLabel2.setFont(fileF.deriveFont(Font.PLAIN, 25f));
        }catch (FontFormatException | IOException ex) {
            ex.printStackTrace();
        }

        jLabel3.setText("Displaying as if the Agent is Calling the Client");
        try{
            Font fileF = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Resource/Fonts/WorkSans-Medium.ttf"));
            jLabel3.setFont(fileF.deriveFont(Font.PLAIN, 15f));
        }catch (FontFormatException | IOException ex) {
            ex.printStackTrace();
        }

        jLabel4.setText("Duration : 00:00:00");





       jButton1.setIcon(new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/Resource/Images/unnamed.png"))));
       jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String elapsedTime = stopTimer();
                JOptionPane.showMessageDialog(Calling_View.this, "Duration of the Call : " + elapsedTime);
                startTimer();
                FormsManager.getInstance().showForm(new AgentMenu());
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(33, 33, 33)
                                                .addComponent(jLabel4)
                                                .addGap(45, 45, 45)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 858, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(557, 557, 557)
                                                .addComponent(jButton1))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(442, 442, 442)
                                                .addComponent(jLabel2))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(431, 431, 431)
                                                .addComponent(jLabel3)))
                                .addContainerGap(223, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1)
                                .addGap(12, 12, 12))
        );
    }
}
