package restcalc.expr.impl;

import restcalc.expr.NegExpressionType;

import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public class NegExpressionMixin extends NegExpressionType {
    @Override
    public double evaluate() {
        return -getOperand().evaluate();
    }

}
