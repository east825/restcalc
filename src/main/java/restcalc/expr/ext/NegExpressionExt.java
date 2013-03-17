package restcalc.expr.ext;

import restcalc.expr.NegExpressionType;

import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public class NegExpressionExt extends NegExpressionType {
    @Override
    public double evaluate() {
        return -getOperand().evaluate();
    }

}
