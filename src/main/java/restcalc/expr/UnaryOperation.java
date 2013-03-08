
package restcalc.expr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UnaryOperation complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="UnaryOperation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{}expr"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UnaryOperation", propOrder = {
        "sum",
        "sub",
        "mult",
        "div",
        "neg",
        "num"
})
@XmlSeeAlso({
        NegExpression.class
})
public abstract class UnaryOperation extends Expression {

    protected SumExpression sum;
    protected SubExpression sub;
    protected MultExpression mult;
    protected DivExpression div;
    protected NegExpression neg;
    protected NumberExpression num;

    /**
     * Gets the value of the sum property.
     *
     * @return possible object is
     *         {@link SumExpression }
     */
    public SumExpression getSum() {
        return sum;
    }

    /**
     * Sets the value of the sum property.
     *
     * @param value allowed object is
     *              {@link SumExpression }
     */
    public void setSum(SumExpression value) {
        this.sum = value;
    }

    /**
     * Gets the value of the sub property.
     *
     * @return possible object is
     *         {@link SubExpression }
     */
    public SubExpression getSub() {
        return sub;
    }

    /**
     * Sets the value of the sub property.
     *
     * @param value allowed object is
     *              {@link SubExpression }
     */
    public void setSub(SubExpression value) {
        this.sub = value;
    }

    /**
     * Gets the value of the mult property.
     *
     * @return possible object is
     *         {@link MultExpression }
     */
    public MultExpression getMult() {
        return mult;
    }

    /**
     * Sets the value of the mult property.
     *
     * @param value allowed object is
     *              {@link MultExpression }
     */
    public void setMult(MultExpression value) {
        this.mult = value;
    }

    /**
     * Gets the value of the div property.
     *
     * @return possible object is
     *         {@link DivExpression }
     */
    public DivExpression getDiv() {
        return div;
    }

    /**
     * Sets the value of the div property.
     *
     * @param value allowed object is
     *              {@link DivExpression }
     */
    public void setDiv(DivExpression value) {
        this.div = value;
    }

    /**
     * Gets the value of the neg property.
     *
     * @return possible object is
     *         {@link NegExpression }
     */
    public NegExpression getNeg() {
        return neg;
    }

    /**
     * Sets the value of the neg property.
     *
     * @param value allowed object is
     *              {@link NegExpression }
     */
    public void setNeg(NegExpression value) {
        this.neg = value;
    }

    /**
     * Gets the value of the num property.
     *
     * @return possible object is
     *         {@link NumberExpression }
     */
    public NumberExpression getNum() {
        return num;
    }

    /**
     * Sets the value of the num property.
     *
     * @param value allowed object is
     *              {@link NumberExpression }
     */
    public void setNum(NumberExpression value) {
        this.num = value;
    }

    public Expression operand() {
        return sum != null ? sum :
                sub != null ? sub :
                mult != null ? mult :
                div != null ? div :
                neg != null ? neg :
                num;
    }

}
