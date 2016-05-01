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
@XmlRootElement(name = "orders")
@XmlAccessorType (XmlAccessType.FIELD)
public class Orders {
    //private Models.Models users = new Models.Models();
    @XmlElement(name = "order")
    private List<Order> orders = null;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Orders getAllOrders() throws JAXBException {
        xmlProcessing xml = new xmlProcessing();
        return (Orders) xml.readData(Orders.class, "Orders.xml");
    }
    public Orders getAllOrders(User b) throws JAXBException {
        Orders orders = new Orders();
        orders.setOrders(new ArrayList<Order>());
        xmlProcessing xml = new xmlProcessing();
        Orders orderData = (Orders) xml.readData(Orders.class, "Orders.xml");
        if(b.getUserLevel() >= 10) {
            for (Order order : orderData.getOrders()) {
                orders.getOrders().add(order);
            }
        } else {
            for (Order order : orderData.getOrders()) {
                if (order.getUserId() == b.getId()) {
                    orders.getOrders().add(order);
                }
            }
        }
        return orders;
    }


    public boolean addNewOrder(Order newOrder) throws JAXBException {
        xmlProcessing xml = new xmlProcessing();
        Orders orders = (Orders) xml.readData(Orders.class, "Orders.xml");

        /*for (Order o : orders.getOrders()){
            *//*Check if order date and time @TODO (slot?) is available? *//*
            if(Objects.equals(o.getDate(), newOrder.getDate()) &&
                    Objects.equals(o.getTimeSlot(), newOrder.getTimeSlot())){
                return false;
            }
        }*/
        orders.getOrders().add(newOrder);
        xml.writeData(Orders.class, orders, "Orders.xml");
        return true;
    }
}
