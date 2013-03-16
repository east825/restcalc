package restcalc.expr.impl;

import restcalc.expr.SubExpressionType;

import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public class SubExpressionMixin extends SubExpressionType {
    @Override
    public double evaluate() {
        return getLeftOperand().evaluate() - getRightOperand().evaluate();
    }
}
