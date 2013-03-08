
package restcalc.expr;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for NumberExpression complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="NumberExpression">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlRootElement(name = "num")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NumberExpression")
public class NumberExpression extends UnaryOperation {

    @XmlAttribute(name = "value", required = true)
    protected double value;

    /**
     * Gets the value of the value property.
     */
    public double getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     */
    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public double evaluate() {
        return value;
    }
}
