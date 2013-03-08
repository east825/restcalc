
package restcalc.expr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NegExpression complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="NegExpression">
 *   &lt;complexContent>
 *     &lt;extension base="{}UnaryOperation">
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlRootElement(name = "neg")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NegExpression")
public class NegExpression extends UnaryOperation {
    @Override
    public double evaluate() {
        return -operand().evaluate();
    }
}
