package restcalc;

import restcalc.expr.CalculationRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;


@Provider
@Consumes(MediaType.APPLICATION_XML)
public class ValidatingExpressionReader implements MessageBodyReader<CalculationRequest> {
    private Unmarshaller unmarshaller;
    public ValidatingExpressionReader() {

        try {
            JAXBContext context = JAXBContext.newInstance(CalculationRequest.class);
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(getClass().getResource("/expression.xsd"));
            unmarshaller = context.createUnmarshaller();
            unmarshaller.setSchema(schema);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return type == CalculationRequest.class;
    }

    @Override
    public CalculationRequest readFrom(Class<CalculationRequest> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
        try {
            return (CalculationRequest) unmarshaller.unmarshal(entityStream);
        } catch (JAXBException e) {
            throw new WebApplicationException(400);
        }
    }
}
