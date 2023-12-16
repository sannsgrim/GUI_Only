package File.Java.AdminInterface.Function.Tables;

import java.awt.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class Design_viewTables extends DefaultTableCellRenderer {
    private Color color;
    private boolean isSelected;
    private int row;

    public Design_viewTables() {
        this(Color.decode("#009FFF"));
    }

    public Design_viewTables(Color color) {
        this.color = color;
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        this.isSelected = isSelected;
        this.row = row;
        return com;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (isSelected) {
            setBackground(color);
        } else {
            setBackground(new Color(199, 199, 199, 0));
        }

        super.paintComponent(g);
    }
}
