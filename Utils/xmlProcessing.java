package Utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class xmlProcessing {
    public Object readData(Class myClass,String filename) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(myClass);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Object ctx = jaxbUnmarshaller.unmarshal(new File(filename));
        return ctx;
    }
    public void writeData(Class myClass,Object obj,String filename) throws JAXBException  {
        JAXBContext jaxbContext = JAXBContext.newInstance(myClass);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(obj, new File(filename));
    }
}
