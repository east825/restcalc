package restcalc.expr.ext;

import restcalc.expr.SumExpressionType;

import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public class SumExpressionExt extends SumExpressionType {
    @Override
    public double evaluate() {
        return getLeftOperand().evaluate() + getRightOperand().evaluate();
    }
}
