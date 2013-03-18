package restcalc.expr.ext;

import restcalc.expr.MultExpressionType;

import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public class MultExpressionExt extends MultExpressionType {
    @Override
    public double evaluate() {
        return getLeftOperand().evaluate() * getRightOperand().evaluate();
    }
}
