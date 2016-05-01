import Models.User;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;

class OrderRenderer extends JButton implements TableCellRenderer {

    public OrderRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(UIManager.getColor("Button.background"));
        }
        setText((value == null) ? "" : value.toString());
        return this;
    }
}

class OrderEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;
    private Object serviceId;
    private int userId;

    public OrderEditor(JCheckBox checkBox, User b) {
        super(checkBox);
        userId = b.getId();
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        label = (value == null) ? "" : value.toString();
        button.setText(label);

        serviceId = table.getValueAt(row, 0);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        JPanel panel = new JPanel();
        JLabel qtyLabel = new JLabel("Kiekis");
        JTextField qty  = new JTextField(10);
        JLabel dateLabel = new JLabel("Apsilankymo data (formatas)");
        JTextField date  = new JTextField(10);

        Date today;
        String dateOut;
        DateFormat dateFormatter;
        dateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT);
        today = new Date();
        dateOut = dateFormatter.format(today);
        System.out.println(dateOut + " " );

        panel.add(qtyLabel);
        panel.add(qty);
        panel.add(dateLabel);
        panel.add(date);

        if (isPushed) {
            /*label + ": id " + id + "userId :" + userId*/
            JOptionPane.showMessageDialog(button, panel);
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}