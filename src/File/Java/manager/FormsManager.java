package File.Java.manager;

import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import File.Java.main.Main;

import javax.swing.*;
import java.awt.*;

public class FormsManager {
    private Main main;
    private static FormsManager instance;

    public static FormsManager getInstance() {
        if (instance == null) {
            instance = new FormsManager();
        }
        return instance;
    }

    private FormsManager() {

    }

    public void LayoutMain(Main main) {
        this.main = main;
    }

    public void showForm(JComponent form) {
        EventQueue.invokeLater(() -> {
            FlatAnimatedLafChange.showSnapshot();
            main.setContentPane(form);
            main.revalidate();
            main.repaint();
            FlatAnimatedLafChange.hideSnapshotWithAnimation();
        });
    }
}
