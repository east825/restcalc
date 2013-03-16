package restcalc.expr.impl;

import restcalc.expr.DivExpressionType;

import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public class DivExpressionMixin extends DivExpressionType {
    @Override
    public double evaluate() {
        return getLeftOperand().evaluate() / getRightOperand().evaluate();
    }
}
