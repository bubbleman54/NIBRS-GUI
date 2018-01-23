//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.11.22 at 10:29:14 AM EST 
//


package gov.niem.release.niem.domains.jxdm._5;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import gov.niem.release.niem.structures._3.ObjectType;


/**
 * A data type for a formal allegation that a specific person has committed a specific offense.
 * 
 * <p>Java class for ChargeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ChargeType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://release.niem.gov/niem/structures/3.0/}ObjectType">
 *       &lt;sequence>
 *         &lt;element ref="{http://release.niem.gov/niem/domains/jxdm/5.2/}ChargeUCR" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;anyAttribute processContents='lax' namespace='urn:us:gov:ic:ntk urn:us:gov:ic:ism'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChargeType", propOrder = {
    "chargeUCR"
})
public class ChargeType
    extends ObjectType
{

    @XmlElementRef(name = "ChargeUCR", namespace = "http://release.niem.gov/niem/domains/jxdm/5.2/", type = JAXBElement.class, required = false)
    protected JAXBElement<?> chargeUCR;

    /**
     * Gets the value of the chargeUCR property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link gov.niem.release.niem.codes.fbi_ucr._3.OffenseCodeType }{@code >}
     *     {@link JAXBElement }{@code <}{@link Object }{@code >}
     *     {@link JAXBElement }{@code <}{@link gov.fbi.cjis.nibrs.nibrs_codes._4.OffenseCodeType }{@code >}
     *     
     */
    public JAXBElement<?> getChargeUCR() {
        return chargeUCR;
    }

    /**
     * Sets the value of the chargeUCR property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link gov.niem.release.niem.codes.fbi_ucr._3.OffenseCodeType }{@code >}
     *     {@link JAXBElement }{@code <}{@link Object }{@code >}
     *     {@link JAXBElement }{@code <}{@link gov.fbi.cjis.nibrs.nibrs_codes._4.OffenseCodeType }{@code >}
     *     
     */
    public void setChargeUCR(JAXBElement<?> value) {
        this.chargeUCR = value;
    }

}