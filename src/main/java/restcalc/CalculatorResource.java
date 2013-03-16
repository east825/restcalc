package restcalc;

import org.xml.sax.SAXException;
import restcalc.expr.Constant;
import restcalc.expr.Expression;
import restcalc.expr.ObjectFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.net.URL;


@Path("/calc")
public class CalculatorResource {

    private static Marshaller marshaller;
    private static Unmarshaller unmarshaller;

    static {
        initializeJAXB();
    }

    private static void initializeJAXB() {
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
            throw new AssertionError("Can't create marshaller/unmarshaller", e);
        }
        try {
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (PropertyException e) {
            throw new AssertionError("Can't set formatted output property on marshaller", e);
        }
        URL schemaURL = CalculatorResource.class.getResource("/expression.xsd");
        Schema schema;
        try {
            schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(schemaURL);
        } catch (SAXException e) {
            throw new AssertionError("Can't load schema", e);
        }
        unmarshaller.setSchema(schema);
    }

    private ObjectFactory objectFactory = new ObjectFactory();


    /**
     * Actually it's an example of *very* poor OOP style with explicit type checking and casts.
     * But such solution allows to use xjc-generated classes directly without any manual
     * customization.
     */
//    private <T> double evaluate(T e) {
//        if (e == null)
//            throw new NullPointerException("Attempt to evaluate null");
//        if (e instanceof ConstantExpressionMixin) {
//            return ((ConstantExpressionMixin) e).getValue();
//        } else if (e instanceof NegExpressionMixin) {
//            NegExpressionMixin ne = (NegExpressionMixin) e;
//            if (ne.getNum() != null)
//                return -evaluate(ne.getNum());
//            if (ne.getSum() != null)
//                return -evaluate(ne.getSum());
//            if (ne.getSub() != null)
//                return -evaluate(ne.getSub());
//            if (ne.getDiv() != null)
//                return -evaluate(ne.getDiv());
//            if (ne.getMult() != null)
//                return -evaluate(ne.getMult());
//            if (ne.getNeg() != null)
//                return -evaluate((ne.getNeg()));
//            throw new IllegalArgumentException("All NegExpressionMixin subexpression undefined");
//        } else if (e instanceof SumExpressionMixin) {
//            List<Object> es = ((SumExpressionMixin) e).getSumOrSubOrMult();
//            return evaluate(es.get(0)) + evaluate(es.get(1));
//        } else if (e instanceof SubExpressionMixin){
//            List<Object> es = ((SubExpressionMixin) e).getSumOrSubOrMult();
//            return evaluate(es.get(0)) - evaluate(es.get(1));
//        } else if (e instanceof MultExpressionMixin){
//            List<Object> es = ((MultExpressionMixin) e).getSumOrSubOrMult();
//            return evaluate(es.get(0)) * evaluate(es.get(1));
//        } else if (e instanceof DivExpressionMixin){
//            List<Object> es = ((DivExpressionMixin) e).getSumOrSubOrMult();
//            return evaluate(es.get(0)) / evaluate(es.get(1));
//        } else {
//            throw new IllegalArgumentException("Unknown expression type: " + e.getClass());
//        }
//    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Expression calcExpression(Expression request) {
//        Expression request;
//        try {
//            request = (Expression) unmarshaller.unmarshal(stream);
//        } catch (JAXBException e) {
//            return Response.status(400).build();
//        }
//        System.out.println(expr.getName().getLocalPart());
//        System.out.println(((SumExpressionMixin)expr.getValue()).getSumOrSubOrMult().get(0));
//        ConstantExpressionMixin res = new ConstantExpressionMixin();
//        res.setValue(evaluate(expr));
        System.out.println(request);
        Constant resultConst = objectFactory.createConstant();
        resultConst.setValue(request.getExpr().evaluate());
        Expression result = objectFactory.createExpression();
        result.setExpr(resultConst);
        return result;
    }
}
