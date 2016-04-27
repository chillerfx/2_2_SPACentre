import Models.User;

import javax.swing.*;
import javax.xml.bind.JAXBException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class loginDialog extends JFrame {
    /*BUTTONS*/
    private JButton loginButton;
    private JButton registerButton;
    /*LABELS*/
    private JLabel  userLabel;
    private JLabel  passwordLabel;
    /*MENU*/
    private JMenu   menu;
    private JTextField userField;
    private JPasswordField passwordField;
    private JPanel panel;
    private JPanel panel_1;
    private JPanel panel_2;
    private JPanel panel_3;

    public loginDialog() {
        initLogin();        //vartotojo prisijungimas

    }
    private void initLogin() {
        setTitle("Vartotojo Prisijungimas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().removeAll();
        Font f = new Font("Ariel", Font.PLAIN, 12);
        setFont(f);
        userLabel = new JLabel("Vartotojo vardas");
        userField = new JTextField(20);

        passwordField = new JPasswordField(20);
        passwordLabel = new JLabel("         Slaptažodis");

        userField.setText("");
        passwordField.setText("");

        loginButton = new JButton("Prisijungti");
        registerButton = new JButton("Registruotis");

        {
            panel = new JPanel();
            getContentPane().add(panel, BorderLayout.NORTH);
            panel.setLayout(new GridLayout(0, 1, 0, 0));
            {
                panel_1 = new JPanel();
                panel.add(panel_1);
                {
                    panel_1.setLayout(new FlowLayout(FlowLayout.CENTER));
                    panel_1.add(userLabel);
                    panel_1.add(userField);
                }
            }
            {
                panel_2 = new JPanel();
                panel.add(panel_2);
                {
                    panel_2.setLayout(new FlowLayout(FlowLayout.CENTER));
                    panel_2.add(passwordLabel);
                    panel_2.add(passwordField);
                }
            }
            {
                panel_3 = new JPanel();
                panel.add(panel_3);
                {
                    panel_3.add(registerButton);
                    panel_3.add(loginButton);
                }
            }
        }
        pack();
        setVisible(true);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new registrationDialog();
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = new User();
                User b = null;
                if(!userField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
                    try {
                        b = user.login(userField.getText(), passwordField.getText());
                    } catch (JAXBException e1) {
                        e1.printStackTrace();
                    }
                }
                if(b != null) {
                    JOptionPane.showMessageDialog(null,
                            "Prisijungėte sėkimingai!",
                            "Info",
                            JOptionPane.INFORMATION_MESSAGE);
                    setVisible(false);
                    try {
                        new mainFrame(b); //.setVisible(true);
                    } catch (JAXBException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Blogi prisijungimo duomenys",
                            "Klaida",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
