package restcalc.expr.impl;

import restcalc.expr.ConstantExpressionType;

import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public class ConstantExpressionMixin extends ConstantExpressionType {
    @Override
    public double evaluate() {
        return getValue();
    }
}
