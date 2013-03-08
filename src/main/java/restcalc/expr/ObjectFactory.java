
package restcalc.expr;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the restcalc.expr package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Neg_QNAME = new QName("", "neg");
    private final static QName _Sub_QNAME = new QName("", "sub");
    private final static QName _Mult_QNAME = new QName("", "mult");
    private final static QName _Num_QNAME = new QName("", "num");
    private final static QName _Div_QNAME = new QName("", "div");
    private final static QName _Sum_QNAME = new QName("", "sum");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: restcalc.expr
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link NegExpression }
     */
    public NegExpression createNegExpression() {
        return new NegExpression();
    }

    /**
     * Create an instance of {@link NumberExpression }
     */
    public NumberExpression createNumberExpression() {
        return new NumberExpression();
    }

    /**
     * Create an instance of {@link MultExpression }
     */
    public MultExpression createMultExpression() {
        return new MultExpression();
    }

    /**
     * Create an instance of {@link SubExpression }
     */
    public SubExpression createSubExpression() {
        return new SubExpression();
    }

    /**
     * Create an instance of {@link DivExpression }
     */
    public DivExpression createDivExpression() {
        return new DivExpression();
    }

    /**
     * Create an instance of {@link SumExpression }
     */
    public SumExpression createSumExpression() {
        return new SumExpression();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NegExpression }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "neg")
    public JAXBElement<NegExpression> createNeg(NegExpression value) {
        return new JAXBElement<NegExpression>(_Neg_QNAME, NegExpression.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubExpression }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "sub")
    public JAXBElement<SubExpression> createSub(SubExpression value) {
        return new JAXBElement<SubExpression>(_Sub_QNAME, SubExpression.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MultExpression }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "mult")
    public JAXBElement<MultExpression> createMult(MultExpression value) {
        return new JAXBElement<MultExpression>(_Mult_QNAME, MultExpression.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NumberExpression }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "num")
    public JAXBElement<NumberExpression> createNum(NumberExpression value) {
        return new JAXBElement<NumberExpression>(_Num_QNAME, NumberExpression.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DivExpression }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "div")
    public JAXBElement<DivExpression> createDiv(DivExpression value) {
        return new JAXBElement<DivExpression>(_Div_QNAME, DivExpression.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SumExpression }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "sum")
    public JAXBElement<SumExpression> createSum(SumExpression value) {
        return new JAXBElement<SumExpression>(_Sum_QNAME, SumExpression.class, null, value);
    }

}
