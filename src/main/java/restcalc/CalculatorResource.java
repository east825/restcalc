package restcalc;

import org.xml.sax.SAXException;
import restcalc.expr.Expression;
import restcalc.expr.NumberExpression;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.StringReader;
import java.net.URL;

@Path("/calc")
public class CalculatorResource {

    private static Marshaller marshaller;
    private static Unmarshaller unmarshaller;

    static {
        JAXBContext context = null;
        try {
            // JAXB context for all expression types
            context = JAXBContext.newInstance("restcalc.expr");
        } catch (JAXBException e) {
            throw new AssertionError("Can't create JAXBContext", e);
        }
        try {
            marshaller = context.createMarshaller();
            unmarshaller = context.createUnmarshaller();
        } catch (JAXBException e) {
            throw new AssertionError("Can't create marshaller/unmarshaller");
        }
        try {
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (PropertyException e) {
            throw new AssertionError("Can't set formatted output property on marshaller");
        }
        URL schemaURL = CalculatorResource.class.getResource("/expression.xsd");
        Schema schema;
        try {
            schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(schemaURL);
        } catch (SAXException e) {
            throw new AssertionError("Can't load schema");
        }
        unmarshaller.setSchema(schema);
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public NumberExpression calcExpression(String xmlSource) {
        Expression expr;
        try {
            expr = (Expression) JAXBIntrospector.getValue(unmarshaller.unmarshal(new StringReader(xmlSource)));
        } catch (JAXBException e) {
            throw new WebApplicationException(400);
        }
        NumberExpression res = new NumberExpression();
        res.setValue(expr.evaluate());
        return res;
    }
}
