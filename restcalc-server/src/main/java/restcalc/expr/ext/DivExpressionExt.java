package restcalc.expr.ext;

import restcalc.expr.DivExpressionType;

import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public class DivExpressionExt extends DivExpressionType {
    @Override
    public double evaluate() {
        return getLeftOperand().evaluate() / getRightOperand().evaluate();
    }
}
