
package restcalc.expr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MultExpression complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="MultExpression">
 *   &lt;complexContent>
 *     &lt;extension base="{}BinaryOperation">
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlRootElement(name = "mult")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MultExpression")
public class MultExpression extends BinaryOperation {
    @Override
    public double evaluate() {
        return leftOperand().evaluate() * rightOperand().evaluate();
    }
}
