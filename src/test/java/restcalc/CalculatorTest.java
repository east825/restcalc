package restcalc;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import restcalc.client.Restcalc;
import restcalc.expr.Addition;
import restcalc.expr.CalculationRequest;
import restcalc.expr.ObjectFactory;
import restcalc.expr.impl.ExpressionMixin;
import restcalc.result.CalculationResult;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Arrays;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static restcalc.Expressions.*;

//
public class CalculatorTest {
    private static final URI BASE_URI = UriBuilder.fromUri("http://restcalc").port(8000).build();
    private static final ObjectFactory factory = new ObjectFactory();
    private static final Restcalc.Calc calcResource = new Restcalc.Calc();
    private HttpServer server;


    @Before
    public void runServer() throws Exception {
        server = GrizzlyServerFactory.createHttpServer(BASE_URI, new TestApplication());
    }

    @After
    public void turnServerOff() {
        server.stop();
    }

    private void makeRequestAndCheckResult(ExpressionMixin expr, double expected) {
        CalculationRequest request = factory.createCalculationRequest();
        request.setExpr(expr);
        CalculationResult result = calcResource.postApplicationXmlAsApplicationXml(request, CalculationResult.class);
        assertThat(result.getValue(), equalTo(expected));
    }

    private void makeRequestAndCheckErrorCodeAndMessage(ExpressionMixin e) {
        CalculationRequest request = new CalculationRequest();
        request.setExpr(e);
        makeRequestAndCheckErrorCodeAndMessage(request);
    }
    private void makeRequestAndCheckErrorCodeAndMessage(Object entity) {
        ClientResponse response = calcResource.postApplicationXmlAsApplicationXml(entity, ClientResponse.class);
        assertThat(response.getStatus(), equalTo(Response.Status.BAD_REQUEST.getStatusCode()));
        assertThat(response.getEntity(CalculationResult.class).getError(), equalTo(("Malformed XML")));
    }

    @Test
    public void simpleTests() {
        // test constant
        makeRequestAndCheckResult(newConstant(42), 42);
        // test unary minus
        makeRequestAndCheckResult(newNegation(newNegation(newConstant(42))), 42);
        // test addition
        makeRequestAndCheckResult(newAddition(newConstant(42), newConstant(42)), 84);
        // test subtraction
        makeRequestAndCheckResult(newSubtraction(newConstant(42), newConstant(42)), 0);
        // test multiplication
        makeRequestAndCheckResult(newMultiplication(newConstant(10), newConstant(10)), 100);
        // test division
        makeRequestAndCheckResult(newDivision(newConstant(42), newConstant(42)), 1);
        // special check for zero division
        makeRequestAndCheckResult(newDivision(newConstant(1), newConstant(0)), Double.POSITIVE_INFINITY);
    }

    @Test
    public void complexExpression() {
        // -(10 + 1 * (20 - 10)) / 20
        ExpressionMixin expr = newDivision(
                newNegation(
                        newAddition(
                                newConstant(10),
                                newMultiplication(
                                        newConstant(1),
                                        newSubtraction(
                                                newConstant(20),
                                                newConstant(10)
                                        )
                                )
                        )
                ),
                newConstant(20)
        );
        makeRequestAndCheckResult(expr, -1);
    }

    /**
     * Test various malformed requests
     */
    @Test
    public void malformedXML() {
        makeRequestAndCheckErrorCodeAndMessage("<foo bar=\"baz\">quux</foo>");
        // not enough arguments
        makeRequestAndCheckErrorCodeAndMessage(newAddition(newConstant(1), null));
        // too many this time
        Addition malformed = new Addition();
        malformed.getOperands().addAll(Arrays.asList(newConstant(1), newConstant(2), newConstant(3)));
        makeRequestAndCheckErrorCodeAndMessage(malformed);
        // num tag attribute is not of type double
        makeRequestAndCheckErrorCodeAndMessage("<num value\"spam\"/>");
        // not valid XML at all
        makeRequestAndCheckErrorCodeAndMessage("This is not XML");
    }

}
