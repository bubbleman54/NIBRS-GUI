//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.11.22 at 10:29:14 AM EST 
//


package gov.niem.release.niem.codes.fbi_ndex._3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for JuvenileDispositionCodeSimpleType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="JuvenileDispositionCodeSimpleType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="H"/>
 *     &lt;enumeration value="R"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "JuvenileDispositionCodeSimpleType")
@XmlEnum
public enum JuvenileDispositionCodeSimpleType {


    /**
     * Handled Within Department 
     * 
     */
    H,

    /**
     * Referred to Other Authorities
     * 
     */
    R;

    public String value() {
        return name();
    }

    public static JuvenileDispositionCodeSimpleType fromValue(String v) {
        return valueOf(v);
    }

}
