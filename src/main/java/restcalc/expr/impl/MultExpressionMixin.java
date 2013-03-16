package restcalc.expr.impl;

import restcalc.expr.MultExpressionType;

import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public class MultExpressionMixin extends MultExpressionType {
    @Override
    public double evaluate() {
        return getLeftOperand().evaluate() * getRightOperand().evaluate();
    }
}
