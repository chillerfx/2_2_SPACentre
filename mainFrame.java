import Models.Order;
import Models.Orders;
import Models.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.JAXBException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static java.awt.ComponentOrientation.LEFT_TO_RIGHT;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class mainFrame extends JFrame {

    private JFrame jframe                      = new JFrame();
    private JMenuBar jmenubar                  = new JMenuBar();
    private JMenu jmenu                        = new JMenu("Nustatymai");
    private JMenuItem logOut                   = new JMenuItem("Atsijungti");


    private JTabbedPane tabbedPane              = new JTabbedPane();

    public mainFrame(User currentUser) throws JAXBException {
        jframe.setLayout(new FlowLayout());
        jmenu.add(logOut);
        jmenubar.add(jmenu);
        jframe.setJMenuBar(jmenubar);

        Tabs tab = new Tabs();
        Tabs tab1 = new Tabs();
        Tabs tab2 = new Tabs();
        JPanel servicesTab = tab.serviceTab(currentUser, jframe);
        JPanel ordersTab = tab1.ordersTab(currentUser, jframe);
        JPanel ordersTabAll = tab1.ordersTabAll(currentUser, jframe);
        JPanel usersTab = tab.usersTab(currentUser, jframe);

        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        tabbedPane.addTab("Paslaugos",servicesTab);
        tabbedPane.addTab("Užsakymai",ordersTab);
        if(currentUser.getUserLevel() > 1) {
            tabbedPane.addTab("Užsakymai visų klientų", ordersTabAll);
            tabbedPane.addTab("Vartotojai", usersTab);
        }
        jframe.add(tabbedPane);
        jframe.pack();
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);

        logOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("click");
                jframe.setVisible(FALSE);
                new loginDialog().setVisible(TRUE);
            }
        });
    }
}
