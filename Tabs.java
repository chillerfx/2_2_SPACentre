import Models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.xml.bind.JAXBException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.List;

import static java.awt.ComponentOrientation.LEFT_TO_RIGHT;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Tabs  extends JPanel{
    private Orders orders = new Orders();
    private SPAServices spaServices = new SPAServices();
    private Users users = new Users();

    private JButton newServiceBtn               = new JButton("Paslaugų užsakymas");
    private JButton addServiceBtn               = new JButton("Pridėti paslaugą (Admin only)");

    private DefaultTableModel ordersTableModel = new DefaultTableModel();


    public JPanel ordersTab(Models.User b, JFrame frame) throws JAXBException {
        JPanel panel = new JPanel();
        Object[] columnNames = {
                "Apsilankymo Data",
                "Abonimento Tipas(?)",
                "Užsakymo dydis (žmonių kiekis)",
                "Suma",
                "Būsena"
        };
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setColumnIdentifiers(columnNames);
        for(Order order: allUserOrdersData(b)) {
            String  visitTime = order.getDate();
            int qantity = order.getQuantity();
            double total = qantity * spaServices.getPriceServiceById(order.getServiceId());
            String status = order.getOrderStatus();
            dtm.addRow(new Object[] {
                    "" + visitTime + "",
                    "g",
                    ""+ qantity  +"",
                    ""+ total + "",
                    ""+ status + ""});
        }
        JTable table = new JTable(dtm);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);
        return panel;
    }

    public JPanel usersTab(Models.User b, JFrame frame) throws JAXBException {
        JPanel panel = new JPanel();
        Object[] columnNames = {
                "UserID",
                "Username",
                "Slaptažodis",
                "Vardas",
                "Pavardė",
                "El. paštas",
                "Vartotojo tipas"
        };
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setColumnIdentifiers(columnNames);
        for(User user : allUsers()) {
            dtm.addRow(new Object[] {
                    user.getId(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getUserName(),
                    user.getUserSurname(),
                    user.getEmail(),
                    user.getUserLevel()
            });
        }
        JTable table = new JTable(dtm);
        //table.setModel(dtm);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);
        return panel;
    }
    public JPanel serviceTab(final User b, final JFrame frame) throws JAXBException {
        JPanel grid         = new JPanel();
        grid.setPreferredSize(new Dimension(500, 300));
        final JPanel leftColumn   = new JPanel();
        JPanel rightColumn  = new JPanel();
        grid.setLayout(new FlowLayout());
        leftColumn.setLayout(new BoxLayout(leftColumn, BoxLayout.Y_AXIS));
        rightColumn.setLayout(new GridLayout());

        grid.setComponentOrientation(LEFT_TO_RIGHT);

        newServiceBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        addServiceBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        //allServicesBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftColumn.add(newServiceBtn);
        if (b.getUserLevel() > 1) {
            leftColumn.add(addServiceBtn);
        }
        //leftColumn.add(allServicesBtn);
        addServiceBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewServiceDialog(frame);
            }
        });
        newServiceBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    orderNewServiceDialog(frame, b);
                } catch (JAXBException e1) {
                    e1.printStackTrace();
                }
            }
        });
        leftColumn.setComponentOrientation(LEFT_TO_RIGHT);
        grid.add(leftColumn);
        grid.add(rightColumn);
        return grid;
    }
    public void orderNewServiceDialog(final JFrame frame, final User user) throws JAXBException {
        JPanel panel = new JPanel();
        Object[] columnNames = {
                "ID",
                "Miestas",
                "SPA",
                "Procedūra",
                "  "
        };
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setColumnIdentifiers(columnNames);
        for(int i = 0; i<allSPAData().size(); i++) {
            SPAService service = allSPAData().get(i);
            dtm.addRow(new Object[]{
                            service.getId(),
                            service.getCity(),
                            service.getSPAName(),
                            service.getSPAService(),
                            "Užsisakyti"
                    }
            );
        }
        dtm.setColumnIdentifiers(columnNames );
        JTable table = new JTable(dtm);

        table.getColumn("  ").setCellRenderer(new OrderRenderer());
        table.getColumn("  ").setCellEditor(new OrderEditor(new JCheckBox(), user));
        table.getColumnModel().getColumn(4).setPreferredWidth(100);

        table.setModel(dtm);
        table.setRowHeight( 32 );
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);
        JDialog dialog = new JDialog(frame,
                "Naujos paslaugos užsakymas",
                true);
        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
                try {
                    new mainFrame(user);
                } catch (JAXBException e1) {
                    e1.printStackTrace();
                }
            }
        });
        dialog.setVisible(true);

    }


    public void addNewServiceDialog(JFrame frame)  {
        JLabel      maxQLabel        = new JLabel("Maksimalus kiekis per dieną");
        final JTextField  maxQField    = new JTextField(5);
        JLabel      ageDiscountLabel = new JLabel("Nuolaidos amžiaus grupėms");
        final JCheckBox   ageDiscountCheckBox = new JCheckBox();
        JButton     addBtn          = new JButton("Pridėti SPA");
        JLabel      priceLabel       = new JLabel("Kaina, vnt. EUR");
        final JTextField  priceField   = new JTextField(20);
        JLabel      spaNameLabel     = new JLabel("SPA pavadinimas");
        final JTextField  spaNameField = new JTextField(20);
        JLabel      cityLabel        = new JLabel("Miestas");
        JTextField  cityField    = new JTextField(20);
        JLabel      spaServiceNameLabel = new JLabel("Procedūra");
        final JTextField  spaServiceNameField = new JTextField(20);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 1, 3));
        {
            JPanel panel_1 = new JPanel();
            panel_1.setLayout(new FlowLayout());
            panel.add(panel_1);
            {

                panel_1.setLayout(new FlowLayout(FlowLayout.RIGHT));
                panel_1.add(cityLabel);
                panel_1.add(cityField);
            }
            JPanel panel_2 = new JPanel();
            panel_2.setLayout(new FlowLayout());
            panel.add(panel_2);
            {

                panel_2.setLayout(new FlowLayout(FlowLayout.RIGHT));
                panel_2.add(spaNameLabel);
                panel_2.add(spaNameField);
            }
            JPanel panel_3 = new JPanel();
            panel_3.setLayout(new FlowLayout());
            panel.add(panel_3);
            {

                panel_3.setLayout(new FlowLayout(FlowLayout.RIGHT));
                panel_3.add(priceLabel);
                panel_3.add(priceField);
            }
            JPanel panel_7 = new JPanel();
            panel_7.setLayout(new FlowLayout());
            panel.add(panel_7);
            {

                panel_7.setLayout(new FlowLayout(FlowLayout.RIGHT));
                panel_7.add(spaServiceNameLabel);
                panel_7.add(spaServiceNameField);
            }
            JPanel panel_4 = new JPanel();
            panel_4.setLayout(new FlowLayout());
            panel.add(panel_4);
            {

                panel_4.setLayout(new FlowLayout(FlowLayout.RIGHT));
                panel_4.add(maxQLabel);
                panel_4.add(maxQField);
            }
            JPanel panel_5 = new JPanel();
            panel_5.setLayout(new FlowLayout());
            panel.add(panel_5);
            {

                panel_5.setLayout(new FlowLayout(FlowLayout.RIGHT));
                panel_5.add(ageDiscountLabel);
                panel_5.add(ageDiscountCheckBox);
            }
            JPanel panel_6 = new JPanel();
            panel_6.setLayout(new FlowLayout());
            panel.add(panel_6);
            {
                panel_5.setLayout(new FlowLayout(FlowLayout.RIGHT));
                panel_5.add(addBtn);
                /*addBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("asdsd");
                    }});*/
            }
        }
        final JDialog dialog = new JDialog(frame,
                "Naujos paslaugos forma",
                true);
        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("click");
                SPAService newSPAservice = new SPAService();
                newSPAservice.setMaxQuantity(Integer.parseInt(maxQField.getText()));
                newSPAservice.setSPAName(spaNameField.getText());
                newSPAservice.setUnitPrice(Double.parseDouble(priceField.getText()));
                newSPAservice.setAgeDiscounts(ageDiscountCheckBox.isSelected());
                newSPAservice.setSPAService(spaServiceNameField.getText());
                int id = System.identityHashCode(newSPAservice);
                newSPAservice.setId(id);

                try {
                    SPAServices sservices = new SPAServices();
                    boolean nsservices = sservices.addNewService(newSPAservice);
                    if(nsservices  == true) {
                        JOptionPane.showMessageDialog(null, "Procedūra sėkmingai pridšta", "Veiksmas", JOptionPane.INFORMATION_MESSAGE);
                        dialog.setVisible(false);

                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Toks vartotojas jau egzistuoja",
                                "Klaida",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (JAXBException e1) {
                    e1.printStackTrace();
                }
            }
        });
        dialog.setVisible(true);
    }
    public List<User> allUsers() throws JAXBException {
        Users allUsers = users.getAllUsers();
        return allUsers.getUsers();
    }
    public List<SPAService> allSPAData() throws JAXBException {
        SPAServices allSPAServices = spaServices.getAllServices();
        return allSPAServices.getSPAServices();
    }
    public List<Order> allUserOrdersData(User b) throws JAXBException {
        Orders allUserOrders = orders.getAllOrders(b);
        return allUserOrders.getOrders();
    }
    public void updateDialog (JDialog dialog, JPanel panel, JComponent element ) {
        panel.add(element);
        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

}

