package restcalc.expr.impl;

import restcalc.expr.SumExpressionType;

import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public class SumExpressionMixin extends SumExpressionType {
    @Override
    public double evaluate() {
        return getLeftOperand().evaluate() + getRightOperand().evaluate();
    }
}
