package restcalc.expr.ext;

import restcalc.expr.ConstantExpressionType;

import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public class ConstantExpressionExt extends ConstantExpressionType {
    @Override
    public double evaluate() {
        return getValue();
    }
}
