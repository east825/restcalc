package restcalc;

import restcalc.expr.*;
import restcalc.expr.Expression;

public class Expressions {

    public static Addition newAddition(Expression e1, Expression e2) {
        Addition addition = new Addition();
        addition.getOperands().add(e1);
        addition.getOperands().add(e2);
        return addition;
    }

    public static Subtraction newSubtraction(Expression e1, Expression e2) {
        Subtraction subtraction = new Subtraction();
        subtraction.getOperands().add(e1);
        subtraction.getOperands().add(e2);
        return subtraction;
    }

    public static Multiplication newMultiplication(Expression e1, Expression e2) {
        Multiplication multiplication = new Multiplication();
        multiplication.getOperands().add(e1);
        multiplication.getOperands().add(e2);
        return multiplication;
    }

    public static Division newDivision(Expression e1, Expression e2) {
        Division division = new Division();
        division.getOperands().add(e1);
        division.getOperands().add(e2);
        return division;
    }

    public static Negation newNegation(Expression e) {
        Negation negation = new Negation();
        negation.setOperand(e);
        return negation;
    }

    public static Constant newConstant(double value) {
        Constant constant = new Constant();
        constant.setValue(value);
        return constant;
    }
}
