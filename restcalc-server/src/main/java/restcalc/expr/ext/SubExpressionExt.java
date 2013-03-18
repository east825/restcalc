package restcalc.expr.ext;

import restcalc.expr.SubExpressionType;

import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public class SubExpressionExt extends SubExpressionType {
    @Override
    public double evaluate() {
        return getLeftOperand().evaluate() - getRightOperand().evaluate();
    }
}
