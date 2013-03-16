package restcalc;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import restcalc.expr.Constant;
import restcalc.expr.Expression;
import restcalc.expr.ObjectFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.net.URI;

public class ResourcePlayground {
    private static final URI BASE_URI = UriBuilder.fromUri("http://restcalc").port(8000).build();
    private static Client client = Client.create();
    private static ObjectFactory factory = new ObjectFactory();
    private static HttpServer server;
    private static Marshaller marshaller;

    @BeforeClass
    public static void setUpMarshaller() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("restcalc.expr");
        marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    }

    @Before
    public void runServer() throws Exception {
        server = GrizzlyServerFactory.createHttpServer(BASE_URI, new TestApplication());
    }

    @After
    public void turnServerOff() {
        server.stop();
    }

    @Test
    public void sendConstant() throws JAXBException{
        Expression req = factory.createExpression();
        Constant constant = factory.createConstant();
        constant.setValue(Math.PI);
        req.setExpr(constant);
        marshaller.marshal(req, System.out);
        ClientResponse response = client.resource(UriBuilder.fromUri(BASE_URI).path("calc").build())
                .accept(MediaType.APPLICATION_XML_TYPE)
                .type(MediaType.APPLICATION_XML_TYPE)
                .post(ClientResponse.class, req);
        System.out.println("Response code: " + response.getStatus());
        System.out.println("Response content: " + response.getEntity(String.class));
    }

}
