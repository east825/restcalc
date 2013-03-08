
package restcalc.expr;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BinaryOperation complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="BinaryOperation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="2" minOccurs="2">
 *         &lt;group ref="{}expr"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BinaryOperation", propOrder = {
        "subExpressions"
})
@XmlSeeAlso({
        MultExpression.class,
        SubExpression.class,
        DivExpression.class,
        SumExpression.class
})
public abstract class BinaryOperation extends Expression {

    @XmlElements({
            @XmlElement(name = "sum", type = SumExpression.class),
            @XmlElement(name = "sub", type = SubExpression.class),
            @XmlElement(name = "mult", type = MultExpression.class),
            @XmlElement(name = "div", type = DivExpression.class),
            @XmlElement(name = "neg", type = NegExpression.class),
            @XmlElement(name = "num", type = NumberExpression.class)
    })
    protected List<Object> subExpressions;

    /**
     * Gets the value of the subExpressions property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subExpressions property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubExpressions().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link SumExpression }
     * {@link SubExpression }
     * {@link MultExpression }
     * {@link DivExpression }
     * {@link NegExpression }
     * {@link NumberExpression }
     */
    public List<Object> getSubExpressions() {
        if (subExpressions == null) {
            subExpressions = new ArrayList<Object>();
        }
        return this.subExpressions;
    }

    public Expression leftOperand() {
        return (Expression) subExpressions.get(0);
    }

    public Expression rightOperand() {
        return (Expression) subExpressions.get(1);
    }

}
