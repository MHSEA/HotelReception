package Reception;

import java.io.*;
import javax.xml.bind.*;
import Reception.Hotel;

public class JAXB_XMLParser {

	private JAXBContext jaxbContext = null;     // generate a context to work in with JAXB											   
	private Unmarshaller unmarshaller = null;   // unmarshall = genrate objects from an xml file
	private Hotel mynewlib = null;            // the main object containing all data

	public JAXB_XMLParser() {

		try {
			jaxbContext = JAXBContext.newInstance("Reception");  // Package that contains ouer classes																													
			unmarshaller = jaxbContext.createUnmarshaller();
		}
		catch (JAXBException e) {
		}
	}
	
	
	// Instance objects and return a list with this objects in it
	@SuppressWarnings("rawtypes")
	public Hotel loadXML(InputStream fileinputstream) {

		try {
			Object xmltoobject = unmarshaller.unmarshal(fileinputstream);

			if (mynewlib == null) {

				// generate the mynewlib object that conatins all info from the xml document
				mynewlib = (Hotel) (((JAXBElement) xmltoobject).getValue());
				// The above (Library) is a candidate for a name change because you wont deal with 
				// a library any more in your conversion
				
				return mynewlib; // return Library Object
			}
		} // try

		catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
}