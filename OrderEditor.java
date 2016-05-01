import Models.Order;
import Models.Orders;
import Models.User;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.xml.bind.JAXBException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

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
    private User user;
    public OrderEditor(JCheckBox checkBox, User b) {
        super(checkBox);
        user = b;
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
        final JTextField qty  = new JTextField(10);
        JLabel dateLabel = new JLabel("Apsilankymo data (formatas)");
        final DateFormatter dateFormatter = new DateFormatter(new SimpleDateFormat("dd/MM/yyyy"));
        final DefaultFormatterFactory dateFormatterFactory = new DefaultFormatterFactory(dateFormatter, new DateFormatter(), dateFormatter);
        final JFormattedTextField dateField = new JFormattedTextField(dateFormatterFactory);
        dateField.setValue(new Date());
        JButton btn = new JButton("Naujas užsakymas");

        panel.add(qtyLabel);
        panel.add(qty);
        panel.add(dateLabel);
        panel.add(dateField);
        panel.add(btn);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer q =  Integer.parseInt(qty.getText());
                String d = dateField.getText();
                Order newOrder = new Order();
                newOrder.setUserId(userId);
                newOrder.setOrderStatus("NEW ORDER");
                newOrder.setServiceId(serviceId);
                newOrder.setDate(d);
                newOrder.setQuantity(q);
                int hash = newOrder.hashCode();
                newOrder.setId(hash);
                Orders o = new Orders();
                try {
                    //o.addNewOrder(newOrder);
                    boolean c = o.addNewOrder(newOrder);
                    if (c) {
                        JOptionPane.showMessageDialog(null, "Užsakymas sėkmingas!", "Užsakymas", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        //errorDialog();
                    }
                } catch (JAXBException e1) {
                    e1.printStackTrace();
                }
            }
        });
        if (isPushed) {
            final JDialog dialog = new JDialog(new JFrame(),
                    "Naujos paslaugos forma",
                    true);
            dialog.setContentPane(panel);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            //JOptionPane.showMessageDialog(null, panel, true);

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