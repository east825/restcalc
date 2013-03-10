package restcalc;

import org.xml.sax.SAXException;
import restcalc.expr.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.StringReader;
import java.net.URL;
import java.util.List;

@Path("/calc")
public class CalculatorResource {

    private static Marshaller marshaller;
    private static Unmarshaller unmarshaller;

    static {
        JAXBContext context;
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

    private ObjectFactory objectFactory = new ObjectFactory();

    private <T> double evaluate(T e) {
        if (e == null)
            throw new NullPointerException("Attempt to evaluate null");
        if (e instanceof NumberExpression) {
            return ((NumberExpression) e).getValue();
        } else if (e instanceof NegExpression) {
            NegExpression ne = (NegExpression) e;
            if (ne.getNum() != null)
                return -evaluate(ne.getNum());
            if (ne.getSum() != null)
                return -evaluate(ne.getSum());
            if (ne.getSub() != null)
                return -evaluate(ne.getSub());
            if (ne.getDiv() != null)
                return -evaluate(ne.getDiv());
            if (ne.getMult() != null)
                return -evaluate(ne.getMult());
            if (ne.getNeg() != null)
                return -evaluate((ne.getNeg()));
            throw new IllegalArgumentException("All NegExpression subexpression undefined");
        } else if (e instanceof SumExpression) {
            List<Object> es = ((SumExpression) e).getSumOrSubOrMult();
            return evaluate(es.get(0)) + evaluate(es.get(1));
        } else if (e instanceof SubExpression){
            List<Object> es = ((SubExpression) e).getSumOrSubOrMult();
            return evaluate(es.get(0)) - evaluate(es.get(1));
        } else if (e instanceof MultExpression){
            List<Object> es = ((MultExpression) e).getSumOrSubOrMult();
            return evaluate(es.get(0)) * evaluate(es.get(1));
        } else if (e instanceof DivExpression){
            List<Object> es = ((DivExpression) e).getSumOrSubOrMult();
            return evaluate(es.get(0)) / evaluate(es.get(1));
        } else {
            throw new IllegalArgumentException("Unknown expression type: " + e.getClass());
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public JAXBElement<NumberExpression> calcExpression(String xmlSource) {
        JAXBElement<?> expr;
        try {
            expr = (JAXBElement<?>) unmarshaller.unmarshal(new StringReader(xmlSource));
        } catch (JAXBException e) {
            throw new WebApplicationException(400);
        }
//        System.out.println(expr.getName().getLocalPart());
//        System.out.println(((SumExpression)expr.getValue()).getSumOrSubOrMult().get(0));
//        NumberExpression res = new NumberExpression();
//        res.setValue(evaluate(expr));
        NumberExpression result = objectFactory.createNumberExpression();
        result.setValue(evaluate(expr.getValue()));
        return objectFactory.createNum(result);
    }
}
