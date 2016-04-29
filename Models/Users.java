package Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import Utils.*;
@XmlRootElement(name = "users")
@XmlAccessorType (XmlAccessType.FIELD)
public class Users {
        @XmlElement(name = "user")
        private List<User> users = null;

        public List<User> getUsers() {
            return users;
        }
        public void setUsers(List<User> users) {
            this.users = users;
        }

    public boolean addNewUser(User newUser) throws JAXBException {
        xmlProcessing xml = new xmlProcessing();
        Users usrs = (Users) xml.readData(Users.class, "Users.xml");
        /* check for existing user */
        for (User u : usrs.getUsers()){
            if(Objects.equals(u.getUsername(), newUser.getUsername()) ||
                    Objects.equals(u.getEmail(), newUser.getEmail())){
                return false;
            }
        }
        usrs.getUsers().add(newUser);
        xml.writeData(Users.class, usrs, "Users.xml");
        return true;
    }
    public Users getAllUsers() throws JAXBException {
        Users users = new Users();
        users.setUsers(new ArrayList<User>());
        xmlProcessing xml = new xmlProcessing();
        Users usersData = (Users) xml.readData(Users.class, "Users.xml");
        for(User user : usersData.getUsers()){
            users.getUsers().add(user);
        }
        return users;
    }
}
