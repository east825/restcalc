package restcalc.expr;

import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public abstract class Expression extends ExpressionType {
    public abstract double evaluate();
}
