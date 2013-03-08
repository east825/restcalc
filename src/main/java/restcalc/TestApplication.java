package restcalc;

import com.sun.jersey.api.core.PackagesResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/playground")
public class TestApplication extends PackagesResourceConfig {
    public TestApplication() {
        super("restcalc");
    }
}
