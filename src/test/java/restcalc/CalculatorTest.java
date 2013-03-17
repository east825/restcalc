package restcalc;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import restcalc.client.Restcalc;
import restcalc.expr.Expression;
import restcalc.expr.ObjectFactory;
import restcalc.expr.impl.ExpressionMixin;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

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

    private void makeRequestAndCheckAnswer(ExpressionMixin expr, double expected) {
        Expression request = factory.createExpression();
        request.setExpr(expr);
        Expression result = calcResource.postApplicationXmlAsApplicationXml(request, Expression.class);
        assertThat(result.getExpr().evaluate(), equalTo(expected));
    }

//    private void makeRequestAndCheckErrorCodeAndMessage(Object entity) {
//        Error response = calcResource.postApplicationXmlAsApplicationXml(entity, Error.class);
//        Error errorResponse = factory.createError();
//        errorResponse.setMsg("Malformed XML");
//        errorResponse.setType(ErrorType.INVALID_XML);
//        assertThat(response.getMsg(), equalTo(errorResponse.getMsg()));
//        assertThat(response.getType(), equalTo(errorResponse.getType()));
//    }

    @Test
    public void simpleTests() {
        // test constant
        makeRequestAndCheckAnswer(newConstant(42), 42);
        // test unary minus
        makeRequestAndCheckAnswer(newNegation(newNegation(newConstant(42))), 42);
        // test addition
        makeRequestAndCheckAnswer(newAddition(newConstant(42), newConstant(42)), 84);
        // test subtraction
        makeRequestAndCheckAnswer(newSubtraction(newConstant(42), newConstant(42)), 0);
        // test multiplication
        makeRequestAndCheckAnswer(newMultiplication(newConstant(10), newConstant(10)), 100);
        // test division
        makeRequestAndCheckAnswer(newDivision(newConstant(42), newConstant(42)), 1);
        // special check for zero division
        makeRequestAndCheckAnswer(newDivision(newConstant(1), newConstant(0)), Double.POSITIVE_INFINITY);
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
        makeRequestAndCheckAnswer(expr, -1);
    }

//    /**
//     * Test various malformed requests
//     */
//    @Test
//    public void malformedXML() {
//        makeRequestAndCheckErrorCodeAndMessage("<foo bar=\"baz\">quux</foo>");
//        ConstantExpressionMixin num = factory.createNumberExpression();
//        num.setValue(42);
//        SumExpressionMixin sumExpression = factory.createSumExpression();
//        sumExpression.getSumOrSubOrMult().add(num);
//        // not enough arguments
//        makeRequestAndCheckErrorCodeAndMessage(factory.createSum(sumExpression));
//        sumExpression.getSumOrSubOrMult().add(num);
//        sumExpression.getSumOrSubOrMult().add(num);
//        // too many this time
//        makeRequestAndCheckErrorCodeAndMessage(factory.createSum(sumExpression));
//        // num tag attribute is not of type double
//        makeRequestAndCheckErrorCodeAndMessage("<num value\"spam\"/>");
//        // not valid XML at all
//        makeRequestAndCheckErrorCodeAndMessage("This is not XML");
//    }

}
