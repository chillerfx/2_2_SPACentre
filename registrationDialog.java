import Models.*;
import Utils.Crypto;

import javax.swing.*;
import javax.xml.bind.JAXBException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import static java.awt.ComponentOrientation.RIGHT_TO_LEFT;

public class registrationDialog extends JFrame {
    /*BUTTONS*/
    private JButton loginButton;
    private JButton registerButton;
    /*LABELS*/
    private JLabel userLabel;
    private JLabel passwordLabel;
    private JLabel passwordConfirmLabel;
    private JLabel emailLabel;
    /*MENU*/
    private JMenu menu;
    private JTextField userField;
    private JPasswordField passwordField;
    private JPanel panel;
    private JPanel panel_1;
    private JPanel panel_2;
    private JPanel panel_3;
    private JPanel panel_4;
    private JPanel panel_5;
    private JPanel panel_6;
    private JPanel panel_7;
    private JPanel panel_8;

    public registrationDialog() {
        initRegistrationWindow();        //vartotojo prisijungimas
    }

    private void initRegistrationWindow() {

        // setSize(746,480);
        setTitle("Vartotojo registracija");
        setComponentOrientation(RIGHT_TO_LEFT);
        setBounds(100, 100, 450, 300);
        Font f = new Font("Ariel", Font.PLAIN, 12);
        setFont(f);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //3. Create components and put them in the frame.
        //menu = new JMenuBar("Nustatymai");
        //getMenuBar().add(menu);
        getContentPane().removeAll();
        userLabel = new JLabel("Vartotojo vardas");
        final JTextField userField = new JTextField(20);

        JLabel userNameLabel = new JLabel("Vardas");
        final JTextField userNameField = new JTextField(20);

        final JPasswordField passwordField = new JPasswordField(20);
        passwordLabel = new JLabel("Slaptažodis"); // trailing spaces hack

        final JPasswordField passwordConfirmField = new JPasswordField(20);
        passwordConfirmLabel = new JLabel("Pakartokite Slaptažodį"); // trailing spaces hack

        JLabel userSurnameLabel = new JLabel("Pavardė");
        final JTextField userSurnameField = new JTextField(20);

        JLabel userCityLabel = new JLabel("Miestas");
        final JTextField userCityField = new JTextField(20);

        JLabel userEmailLabel = new JLabel("El. paštas");
        final JTextField userEmailField = new JTextField(20);


        loginButton = new JButton("Prisijungti");
        registerButton = new JButton("Registruotis");

        {
            panel = new JPanel();
            getContentPane().add(panel, BorderLayout.NORTH);
            panel.setLayout(new GridLayout(0, 1, 1, 3));
            {
                panel_1 = new JPanel();
                panel_1.setLayout(new FlowLayout());
                panel.add(panel_1);
                {
                    panel_1.setLayout(new FlowLayout(FlowLayout.RIGHT));
                    panel_1.add(userLabel);
                    panel_1.add(userField);
                }
            }
            {
                panel_8 = new JPanel();
                panel_8.setLayout(new FlowLayout());
                panel.add(panel_8);
                {
                    panel_8.setLayout(new FlowLayout(FlowLayout.RIGHT));
                    panel_8.add(userEmailLabel);
                    panel_8.add(userEmailField);
                }
            }
            {
                panel_2 = new JPanel();
                panel.add(panel_2);
                {
                    panel_2.setLayout(new FlowLayout(FlowLayout.RIGHT));
                    panel_2.add(passwordLabel);
                    panel_2.add(passwordField);
                }
            }
            {
                panel_7 = new JPanel();
                panel.add(panel_7);
                {
                    panel_7.setLayout(new FlowLayout(FlowLayout.RIGHT));
                    panel_7.add(passwordConfirmLabel);
                    panel_7.add(passwordConfirmField);
                }
            }
            {
                panel_4 = new JPanel();
                panel.add(panel_4);
                {
                    panel_4.setLayout(new FlowLayout(FlowLayout.RIGHT));
                    panel_4.add(userNameLabel);
                    panel_4.add(userNameField);
                }
            }
            {
                panel_5 = new JPanel();
                panel.add(panel_5);
                {
                    panel_5.setLayout(new FlowLayout(FlowLayout.RIGHT));
                    panel_5.add(userSurnameLabel);
                    panel_5.add(userSurnameField);
                }
            }
            {
                panel_6 = new JPanel();
                panel.add(panel_6);
                {
                    panel_6.setLayout(new FlowLayout(FlowLayout.RIGHT));
                    panel_6.add(userCityLabel);
                    panel_6.add(userCityField);
                }
            }
            {
                panel_3 = new JPanel();
                panel.add(panel_3);
                {
                    panel_3.setLayout(new FlowLayout(FlowLayout.RIGHT));
                    panel_3.add(loginButton);
                    panel_3.add(registerButton);
                }
            }
        }
        pack();
        setVisible(true);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new loginDialog();
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*@TODO add age and other params later*/
                String username = userField.getText();
                String password = passwordField.getText();
                String passwordConfirm = passwordConfirmField.getText();
                String userName = userNameField.getText();
                String userSurname = userSurnameField.getText();
                String userCity = userCityField.getText();
                String userEmail = userEmailField.getText();
                int userLevel = 0;
                if (Objects.equals(Crypto.cryptWithMD5(password), Crypto.cryptWithMD5(passwordConfirm))) {
                    Crypto pass = new Crypto();
                    String strPass = pass.cryptWithMD5(password);
                    User newUser = new User(username, strPass, userName, userSurname, userEmail, userCity, 0, 0);
                    int id = System.identityHashCode(newUser);
                    newUser.setId(id);
                    try {
                        Users user1 = new Users();
                        boolean regs = user1.addNewUser(newUser);
                        if(regs == true) {
                            JOptionPane.showMessageDialog(null, "Vartotojo registracija sėkminga", "Registracija", JOptionPane.INFORMATION_MESSAGE);
                            setVisible(false);
                            new loginDialog();
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Toks vartotojas jau egzistuoja",
                                    "Klaida",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (JAXBException e1) {
                        e1.printStackTrace();
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Slaptažodžiai nėra vienodi", "Slaptažodių neatitikimas", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
