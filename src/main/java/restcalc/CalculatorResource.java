package restcalc;

import restcalc.expr.CalculationRequest;
import restcalc.result.CalculationResult;

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
    public CalculationResult calcExpression(CalculationRequest request) {
        CalculationResult result = new CalculationResult();
        result.setValue(request.getExpr().evaluate());
        return result;
    }
}
