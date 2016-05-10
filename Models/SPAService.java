package Models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SPAService")
@XmlAccessorType(XmlAccessType.FIELD)
public class SPAService {


    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(int maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public boolean isAgeDiscounts() {
        return ageDiscounts;
    }

    public void setAgeDiscounts(boolean ageDiscounts) {
        this.ageDiscounts = ageDiscounts;
    }

    public String getSPAService() {
        return SPAService;
    }

    public void setSPAService(String SPAService) {
        this.SPAService = SPAService;
    }
    
    public String getTrukme() {
        return trukme;
    }

    public void setTrukme(String trukme) {
        this.trukme = trukme;
    }

    public String getSPAName() {
        return SPAName;
    }

    public void setSPAName(String SPAName) {
        this.SPAName = SPAName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SPAService(){};

    int id;
    String city;
    String SPAName;
    String SPAService;
    boolean ageDiscounts;
    int maxQuantity;
    Double unitPrice;
    String trukme;
/*@TODO implement some sort of max quantity per day or smt
    * (Think slots per timeframe)
    * */
}
