package restcalc;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import org.glassfish.grizzly.http.server.HttpServer;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

public class TestServer {

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost/").port(8000).build();
    }

    public static final URI BASE_URI = getBaseURI();

    public static void main(String[] args) throws IOException {
        System.out.println("Starting grizzly...");
//        ResourceConfig rc = new PackagesResourceConfig("restcalc");
        HttpServer httpServer = GrizzlyServerFactory.createHttpServer(BASE_URI, new TestApplication());
        System.out.println("Press enter to stop server");
        System.in.read();
        httpServer.stop();
    }
}
