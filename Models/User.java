package Models;

import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;
import Utils.*;

@XmlRootElement(name = "User")
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(propOrder = {"username", "password", "userName", "user_surname"})
public class User {
    public int getId() { return id;}
    public void setId(int id)  { this.id = id; }
    public String getUserName() {
        return userName;
    }
    //@XmlElement
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUsername() {
        return username;
    }
    //@XmlElement
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    //@XmlElement
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUserCity() {
        return userCity;
    }
    //@XmlElement
    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }
    public String getUserSurname() {
        return userSurname;
    }
    //@XmlElement
    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }
    public String getEmail() {
        return email;
    }
    //@XmlElement
    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserLevel() {
            return userLevel;
        }
    //@XmlElement
    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }

    int id;
    String userName;
    String username;
    String password;
    String userSurname;
    String userCity;
    String email;



    String userBirthday;
    int    userLevel;
    public User() {}
    public User(User  user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.userName = user.getUserName();
        this.userSurname = user.getUserSurname();
        this.userCity = user.getUserCity();
        this.email = user.getEmail();
        this.userLevel = user.getUserLevel();
    }
    public User(String username,
                String password,
                String userName,
                String userSurname,
                String userEmail,
                String userCity,
                String userBirthday,
                int id,
                int userLevel) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userName = userName;
        this.userSurname = userSurname;
        this.userCity = userCity;
        this.email = userEmail;
        this.userBirthday = userBirthday;
        this.userLevel = userLevel;
    }

    public User login(String username, String password) throws JAXBException {
        xmlProcessing xml = new xmlProcessing();
        Users users = (Users) xml.readData(Users.class, "Users.xml");
        Crypto pass = new Crypto();
        String cmp = pass.cryptWithMD5(password);
        for(User user : users.getUsers()) {
            if(Objects.equals(user.getUsername(), username) &&
                    Objects.equals(user.getPassword(), cmp)){
                return user;
            }
        }
        User user = new User();
        user.setId(0);
        return user;
    }
}
