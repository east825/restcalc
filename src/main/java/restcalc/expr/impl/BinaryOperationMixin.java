package restcalc.expr.impl;

import restcalc.expr.BinaryOperationType;

import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public abstract class BinaryOperationMixin extends BinaryOperationType {

    public ExpressionMixin getLeftOperand() {
        return operands.get(0);
    }

    public ExpressionMixin getRightOperand() {
        return operands.get(1);
    }

}
