package restcalc.expr.impl;

import restcalc.expr.ExpressionType;

import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public abstract class ExpressionMixin extends ExpressionType {
    public abstract double evaluate();
}
