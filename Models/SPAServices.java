package Models;

import Utils.xmlProcessing;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "SPAServices")
@XmlAccessorType(XmlAccessType.FIELD)
public class SPAServices {
    public List<SPAService> getSPAServices() {
        return SPAServices;
    }

    public void setSPAServices(List<SPAService> SPAServices) {
        this.SPAServices = SPAServices;
    }

    @XmlElement(name = "SPAService")
    private List<SPAService> SPAServices = null;

    public boolean addNewService(SPAService data) throws JAXBException {
        try {
            xmlProcessing xml = new xmlProcessing();
            SPAServices s = (SPAServices) xml.readData(SPAServices.class, "SPAServices.xml");

            s.getSPAServices().add(data);
            xml.writeData(SPAServices.class, s, "SPAServices.xml");
            return true;
        } catch (JAXBException e1) {
            e1.printStackTrace();
        }
        return false;
    }
    public SPAServices getAllServices() throws JAXBException {
        SPAServices spaServices = new SPAServices();
        spaServices.setSPAServices(new ArrayList<SPAService>());
        xmlProcessing xml = new xmlProcessing();
        SPAServices spaData = (SPAServices) xml.readData(SPAServices.class, "SPAServices.xml");
        for (SPAService spa : spaData.getSPAServices()) {
            spaServices.getSPAServices().add(spa);
        }
        return spaServices;
    }
}
