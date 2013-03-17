package restcalc;

import restcalc.expr.Constant;
import restcalc.expr.Expression;
import restcalc.expr.ObjectFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/calc")
public class CalculatorResource {

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Expression calcExpression(Expression request) {
        ObjectFactory objectFactory = new ObjectFactory();
        System.out.println(request);
        Constant resultConst = objectFactory.createConstant();
        resultConst.setValue(request.getExpr().evaluate());
        Expression result = objectFactory.createExpression();
        result.setExpr(resultConst);
        return result;
    }
}
