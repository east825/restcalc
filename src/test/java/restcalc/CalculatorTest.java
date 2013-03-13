package restcalc;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import restcalc.client.Restcalc;
import restcalc.expr.*;
import restcalc.expr.Error;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.JAXBElement;
import java.net.URI;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class CalculatorTest {
    private static final URI BASE_URI = UriBuilder.fromUri("http://restcalc").port(8000).build();
    private Restcalc.Calc calcResource = new Restcalc.Calc();
    private ObjectFactory factory = new ObjectFactory();
    private HttpServer server;

    @Before
    public void runServer() throws Exception {
        server = GrizzlyServerFactory.createHttpServer(BASE_URI, new TestApplication());
    }

    @After
    public void turnServerOff() {
        server.stop();
    }

    private <T> void makeRequestAndCheckAnswer(JAXBElement<T> expr, double expected) {
        NumberExpression result = calcResource.postApplicationXmlAsApplicationXml(expr, NumberExpression.class);
        assertThat(result.getValue(), equalTo(expected));
    }

    private void makeRequestAndCheckErrorCodeAndMessage(Object entity) {
        Error response = calcResource.postApplicationXmlAsApplicationXml(entity, Error.class);
        Error errorResponse = factory.createError();
        errorResponse.setMsg("Malformed XML");
        errorResponse.setType(ErrorType.INVALID_XML);
        assertThat(response.getMsg(), equalTo(errorResponse.getMsg()));
        assertThat(response.getType(), equalTo(errorResponse.getType()));
    }

    @Test
    public void testNumberExpression() {
        NumberExpression expr = factory.createNumberExpression();
        expr.setValue(42);
        makeRequestAndCheckAnswer(factory.createNum(expr), 42);
    }

    @Test
    public void testSumExpression() {
        NumberExpression e1 = factory.createNumberExpression();
        e1.setValue(42);
        NumberExpression e2 = factory.createNumberExpression();
        e2.setValue(42);
        SumExpression sum = factory.createSumExpression();
        sum.getSumOrSubOrMult().add(e1);
        sum.getSumOrSubOrMult().add(e2);
        makeRequestAndCheckAnswer(factory.createSum(sum), 84);
    }

    @Test
    public void testDivExpression() {
        NumberExpression e1 = factory.createNumberExpression();
        e1.setValue(42);
        NumberExpression e2 = factory.createNumberExpression();
        e2.setValue(42);
        DivExpression div = factory.createDivExpression();
        div.getSumOrSubOrMult().add(e1);
        div.getSumOrSubOrMult().add(e2);
        makeRequestAndCheckAnswer(factory.createDiv(div), 1);
    }

    @Test
    /**
     * We used doubles as arguments in arithmetical expression, so
     * zero division should produce Inf, instead of RuntimeException on
     * server side.
     */
    public void testZeroDivision() {
        NumberExpression num = factory.createNumberExpression();
        num.setValue(1);
        NumberExpression denom = factory.createNumberExpression();
        denom.setValue(0);
        DivExpression expr = factory.createDivExpression();
        expr.getSumOrSubOrMult().add(num);
        expr.getSumOrSubOrMult().add(denom);
        makeRequestAndCheckAnswer(factory.createDiv(expr), Double.POSITIVE_INFINITY);
    }


    @Test
    public void testMultExpression() {
        NumberExpression e1 = factory.createNumberExpression();
        e1.setValue(42);
        NumberExpression e2 = factory.createNumberExpression();
        e2.setValue(42);
        MultExpression mult = factory.createMultExpression();
        mult.getSumOrSubOrMult().add(e1);
        mult.getSumOrSubOrMult().add(e2);
        makeRequestAndCheckAnswer(factory.createMult(mult), 1764);
    }

    @Test
    public void testSubExpression() {
        NumberExpression e1 = factory.createNumberExpression();
        e1.setValue(42);
        NumberExpression e2 = factory.createNumberExpression();
        e2.setValue(42);
        SubExpression sub = factory.createSubExpression();
        sub.getSumOrSubOrMult().add(e1);
        sub.getSumOrSubOrMult().add(e2);
        makeRequestAndCheckAnswer(factory.createSub(sub), 0);
    }

    @Test
    public void testNestedUnaryMinus() {
        NegExpression neg = factory.createNegExpression();
        NegExpression neg2 = factory.createNegExpression();
        NumberExpression n = factory.createNumberExpression();
        n.setValue(42);
        neg2.setNum(n);
        neg.setNeg(neg2);
        makeRequestAndCheckAnswer(factory.createNeg(neg), 42);
    }

    @Test
    public void testComplex() {
        // Evaluating: -(10 + 1 * (20 - 10)) / 20

        // -(10 + 1 * (20 - 10))
        DivExpression div = factory.createDivExpression();
        NegExpression neg = factory.createNegExpression();
        // (10 + 1 * (20 - 10))
        SumExpression sum = factory.createSumExpression();
        NumberExpression num = factory.createNumberExpression();
        num.setValue(10);
        sum.getSumOrSubOrMult().add(num);
        // 1 * (20 - 10)
        MultExpression mult = factory.createMultExpression();
        num = factory.createNumberExpression();
        num.setValue(1);
        mult.getSumOrSubOrMult().add(num);
        // 20 - 10
        SubExpression sub = factory.createSubExpression();
        num = factory.createNumberExpression();
        num.setValue(20);
        sub.getSumOrSubOrMult().add(num);
        num = factory.createNumberExpression();
        num.setValue(10);
        sub.getSumOrSubOrMult().add(num);
        mult.getSumOrSubOrMult().add(sub);
        sum.getSumOrSubOrMult().add(mult);
        neg.setSum(sum);
        div.getSumOrSubOrMult().add(neg);
        num = factory.createNumberExpression();
        num.setValue(20);
        div.getSumOrSubOrMult().add(num);
        makeRequestAndCheckAnswer(factory.createDiv(div), -1);
    }

    /**
     * Test various malformed requests
     */
    @Test
    public void malformedXML() {
        makeRequestAndCheckErrorCodeAndMessage("<foo bar=\"baz\">quux</foo>");
        NumberExpression num = factory.createNumberExpression();
        num.setValue(42);
        SumExpression sumExpression = factory.createSumExpression();
        sumExpression.getSumOrSubOrMult().add(num);
        // not enough arguments
        makeRequestAndCheckErrorCodeAndMessage(factory.createSum(sumExpression));
        sumExpression.getSumOrSubOrMult().add(num);
        sumExpression.getSumOrSubOrMult().add(num);
        // too many this time
        makeRequestAndCheckErrorCodeAndMessage(factory.createSum(sumExpression));
        // num tag attribute is not of type double
        makeRequestAndCheckErrorCodeAndMessage("<num value\"spam\"/>");
        // not valid XML at all
        makeRequestAndCheckErrorCodeAndMessage("This is not XML");
    }

}
