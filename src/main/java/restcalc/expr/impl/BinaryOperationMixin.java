package restcalc.expr.impl;

import restcalc.expr.BinaryOperationType;
import restcalc.expr.Expression;

import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public abstract class BinaryOperationMixin extends BinaryOperationType {

    public Expression getLeftOperand() {
        return operands.get(0);
    }

    public Expression getRightOperand() {
        return operands.get(1);
    }

}
