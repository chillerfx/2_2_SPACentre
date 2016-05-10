package Models;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.ArrayList;
import Utils.*;
public class initData {
    private Users users = new Users();
    private Orders orders = new Orders();
    private SPAServices SPAs = new SPAServices();
    private File usersData = new File("Users.xml");

    public initData() throws JAXBException {
        if(usersData.exists() && !usersData.isDirectory()) {
            return;
        } else {
            /*Add some test objects*/
            loadTestUsers();
            loadTestSPAs();
           // addTestOrders();
        }
    }
    void loadTestSPAs() throws JAXBException {
        SPAs.setSPAServices(new ArrayList<SPAService>());

        SPAService SPA1 = new SPAService();
        SPA1.setCity("Vilnius");
        SPA1.setAgeDiscounts(true);
        SPA1.setSPAName("UAB Auksinė vonia");
        SPA1.setSPAService("Klasikinis masažas");
        SPA1.setTrukme("60 min");
        SPA1.setMaxQuantity(2);
        SPA1.setUnitPrice(25.00);
        int id1 = SPA1.hashCode();
        SPA1.setId(id1);
        SPAs.getSPAServices().add(SPA1);
        
        SPAService SPA2 = new SPAService();
        SPA2.setCity("Vilnius");
        SPA2.setAgeDiscounts(true);
        SPA2.setSPAName("UAB Auksinė vonia");
        SPA2.setSPAService("Terapinis masažas");
        SPA2.setTrukme("40 min");
        SPA2.setMaxQuantity(2);
        SPA2.setUnitPrice(30.00);
        int id2 = SPA2.hashCode();
        SPA2.setId(id2);
        SPAs.getSPAServices().add(SPA2);
        
        SPAService SPA3 = new SPAService();
        SPA3.setCity("Vilnius");
        SPA3.setAgeDiscounts(true);
        SPA3.setSPAName("UAB Auksinė vonia");
        SPA3.setSPAService("Kaklo masažas");
        SPA3.setTrukme("20 min");
        SPA3.setMaxQuantity(2);
        SPA3.setUnitPrice(10.00);
        int id3 = SPA3.hashCode();
        SPA3.setId(id3);
        SPAs.getSPAServices().add(SPA3);
        
        SPAService SPA4 = new SPAService();
        SPA4.setCity("Vilnius");
        SPA4.setAgeDiscounts(true);
        SPA4.setSPAName("UAB Auksinė vonia");
        SPA4.setSPAService("Pečių juostos masažas");
        SPA4.setTrukme("25 min");
        SPA4.setMaxQuantity(2);
        SPA4.setUnitPrice(15.00);
        int id4 = SPA4.hashCode();
        SPA4.setId(id4);
        SPAs.getSPAServices().add(SPA4);
        
        SPAService SPA5 = new SPAService();
        SPA5.setCity("Vilnius");
        SPA5.setAgeDiscounts(true);
        SPA5.setSPAName("UAB Auksinė vonia");
        SPA5.setSPAService("Atpalaiduojantis kūno masažas");
        SPA5.setTrukme("40 min");
        SPA5.setMaxQuantity(2);
        SPA5.setUnitPrice(20.00);
        int id5 = SPA5.hashCode();
        SPA5.setId(id5);
        SPAs.getSPAServices().add(SPA5);

        xmlProcessing xml = new xmlProcessing();
        xml.writeData(SPAServices.class, SPAs, "SPAServices.xml");
    }

    void addTestOrders() throws JAXBException {
        orders.setOrders(new ArrayList<Order>());
        Order order1 = new Order();
        /*use enum for city list?*/
        order1.setUserId(1284693); //admin id // userId <=> User.getId;
        order1.setCity("Vilnius");
        order1.setDate("test date");
        order1.setFirm("UAB SPA");
        order1.setQuantity(1); //people qantity in the group
        order1.setTimeSlot(0);
        order1.setOrderStatus("NEW ORDER");
        //@TODO add procedure type, etc
        int id1 = order1.hashCode();
        order1.setId(id1);

        orders.getOrders().add(order1);
        xmlProcessing xml = new xmlProcessing();
        xml.writeData(Orders.class, orders, "Orders.xml");
    }
    void loadTestUsers () throws JAXBException {
        users.setUsers(new ArrayList<User>());

        User usr1 = new User();
        usr1.setEmail("admin@email.com");
        Crypto pass = new Crypto();
        String password = pass.cryptWithMD5("admin");
        usr1.setPassword(password);
        usr1.setUserCity("Vilnius");
        usr1.setUsername("admin");
        usr1.setUserName("Adminas");
        usr1.setUserSurname("Padminis");
        usr1.setUserLevel(10);
        int id1 = System.identityHashCode(usr1);
            usr1.setId(id1);

        User usr2 = new User();
        usr2.setEmail("test@email.com");
        Crypto pass2 = new Crypto();
        String password2 = pass2.cryptWithMD5("test");
        usr2.setPassword(password2);
        usr2.setUserCity("Vilnius");
        usr2.setUsername("test");
        usr2.setUserName("Testinas");
        usr2.setUserSurname("Patestinis");
        usr2.setUserLevel(0);
        int id2  = System.identityHashCode(usr2);
        usr2.setId(id2);

        users.getUsers().add(usr1);
        users.getUsers().add(usr2);

        xmlProcessing xml = new xmlProcessing();
        xml.writeData(Users.class, users, "Users.xml");
    }
}
