//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.11.22 at 10:29:14 AM EST 
//


package gov.niem.release.niem.codes.fbi_ndex._3;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IncidentStatusCodeSimpleType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="IncidentStatusCodeSimpleType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="ACTIVE"/>
 *     &lt;enumeration value="ADMINISTRATIVELY CLOSED"/>
 *     &lt;enumeration value="CLEARED BY ARREST"/>
 *     &lt;enumeration value="CLEARED BY EXCEPTIONAL MEANS"/>
 *     &lt;enumeration value="CLOSED"/>
 *     &lt;enumeration value="COLD"/>
 *     &lt;enumeration value="INACTIVE"/>
 *     &lt;enumeration value="OPEN"/>
 *     &lt;enumeration value="PENDING"/>
 *     &lt;enumeration value="RE_OPENED"/>
 *     &lt;enumeration value="UNFOUNDED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "IncidentStatusCodeSimpleType")
@XmlEnum
public enum IncidentStatusCodeSimpleType {


    /**
     * Active
     * 
     */
    ACTIVE("ACTIVE"),

    /**
     * Administratively Closed
     * 
     */
    @XmlEnumValue("ADMINISTRATIVELY CLOSED")
    ADMINISTRATIVELY_CLOSED("ADMINISTRATIVELY CLOSED"),

    /**
     * Cleared by Arrest
     * 
     */
    @XmlEnumValue("CLEARED BY ARREST")
    CLEARED_BY_ARREST("CLEARED BY ARREST"),

    /**
     * Cleared by Exceptional Means
     * 
     */
    @XmlEnumValue("CLEARED BY EXCEPTIONAL MEANS")
    CLEARED_BY_EXCEPTIONAL_MEANS("CLEARED BY EXCEPTIONAL MEANS"),

    /**
     * Closed
     * 
     */
    CLOSED("CLOSED"),

    /**
     * Cold
     * 
     */
    COLD("COLD"),

    /**
     * Inactive
     * 
     */
    INACTIVE("INACTIVE"),

    /**
     * Open
     * 
     */
    OPEN("OPEN"),

    /**
     * Pending
     * 
     */
    PENDING("PENDING"),

    /**
     * Re_opened
     * 
     */
    RE_OPENED("RE_OPENED"),

    /**
     * Unfounded
     * 
     */
    UNFOUNDED("UNFOUNDED");
    private final String value;

    IncidentStatusCodeSimpleType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static IncidentStatusCodeSimpleType fromValue(String v) {
        for (IncidentStatusCodeSimpleType c: IncidentStatusCodeSimpleType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}