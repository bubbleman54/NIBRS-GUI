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
 * <p>Java class for BiasMotivationCodeSimpleType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BiasMotivationCodeSimpleType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="ANTIAMERICAN INDIAN_ ALASKAN NATIVE"/>
 *     &lt;enumeration value="ANTIARAB"/>
 *     &lt;enumeration value="ANTIASIAN"/>
 *     &lt;enumeration value="ANTIATHEIST_AGNOSTIC"/>
 *     &lt;enumeration value="ANTIBISEXUAL"/>
 *     &lt;enumeration value="ANTIBLACK_AFRICAN AMERICAN"/>
 *     &lt;enumeration value="ANTIBUDDHIST"/>
 *     &lt;enumeration value="ANTICATHOLIC"/>
 *     &lt;enumeration value="ANTIEASTERNORTHODOX"/>
 *     &lt;enumeration value="ANTIFEMALE"/>
 *     &lt;enumeration value="ANTIFEMALE HOMOSEXUAL"/>
 *     &lt;enumeration value="ANTIGENDER_NONCONFORMING"/>
 *     &lt;enumeration value="ANTIHETEROSEXUAL"/>
 *     &lt;enumeration value="ANTIHINDU"/>
 *     &lt;enumeration value="ANTIHISPANIC_LATINO"/>
 *     &lt;enumeration value="ANTIHOMOSEXUAL"/>
 *     &lt;enumeration value="ANTIISLAMIC"/>
 *     &lt;enumeration value="ANTIJEHOVAHWITNESS"/>
 *     &lt;enumeration value="ANTIJEWISH"/>
 *     &lt;enumeration value="ANTIMALE"/>
 *     &lt;enumeration value="ANTIMALE HOMOSEXUAL"/>
 *     &lt;enumeration value="ANTIMENTAL DISABILITY"/>
 *     &lt;enumeration value="ANTIMORMON"/>
 *     &lt;enumeration value="ANTIMULTIRACIAL GROUP"/>
 *     &lt;enumeration value="ANTIMULTIRELIGIOUS GROUP"/>
 *     &lt;enumeration value="ANTINATIVEHAWAIIAN_OTHERPACIFICISLANDER"/>
 *     &lt;enumeration value="ANTIOTHER CHRISTIAN"/>
 *     &lt;enumeration value="ANTIOTHER ETHNICITY_NATIONAL ORIGIN"/>
 *     &lt;enumeration value="ANTIOTHER RELIGION"/>
 *     &lt;enumeration value="ANTIPHYSICAL DISABILITY"/>
 *     &lt;enumeration value="ANTIPROTESTANT"/>
 *     &lt;enumeration value="ANTISIKH"/>
 *     &lt;enumeration value="ANTITRANSGENDER"/>
 *     &lt;enumeration value="ANTIWHITE"/>
 *     &lt;enumeration value="NONE"/>
 *     &lt;enumeration value="UNKNOWN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "BiasMotivationCodeSimpleType")
@XmlEnum
public enum BiasMotivationCodeSimpleType {


    /**
     * Anti-American Indian or Alaskan Native_race ethnicity bias
     * 
     */
    @XmlEnumValue("ANTIAMERICAN INDIAN_ ALASKAN NATIVE")
    ANTIAMERICAN_INDIAN_ALASKAN_NATIVE("ANTIAMERICAN INDIAN_ ALASKAN NATIVE"),

    /**
     * Anti-Arab_race ethnicity bias
     * 
     */
    ANTIARAB("ANTIARAB"),

    /**
     * Anti-Asian_race ethnicity bias
     * 
     */
    ANTIASIAN("ANTIASIAN"),

    /**
     * Anti-Atheist or Agnostic_religious bias
     * 
     */
    ANTIATHEIST_AGNOSTIC("ANTIATHEIST_AGNOSTIC"),

    /**
     * Anti-Bisexual
     * 
     */
    ANTIBISEXUAL("ANTIBISEXUAL"),

    /**
     * Anti-Black or African American_race ethnicity bias
     * 
     */
    @XmlEnumValue("ANTIBLACK_AFRICAN AMERICAN")
    ANTIBLACK_AFRICAN_AMERICAN("ANTIBLACK_AFRICAN AMERICAN"),

    /**
     * Anti-Buddhist_religious bias
     * 
     */
    ANTIBUDDHIST("ANTIBUDDHIST"),

    /**
     * Anti-Catholic religion_religious bias
     * 
     */
    ANTICATHOLIC("ANTICATHOLIC"),

    /**
     * Anti-Eastern Orthodox (Russian, Greek, Other)_religious bias
     * 
     */
    ANTIEASTERNORTHODOX("ANTIEASTERNORTHODOX"),

    /**
     * Anti-Female_gender bias
     * 
     */
    ANTIFEMALE("ANTIFEMALE"),

    /**
     * Anti-Female Homosexual (Lesbian) _sexual orientation bias
     * 
     */
    @XmlEnumValue("ANTIFEMALE HOMOSEXUAL")
    ANTIFEMALE_HOMOSEXUAL("ANTIFEMALE HOMOSEXUAL"),

    /**
     * Anti-Gender Non-Conforming
     * 
     */
    ANTIGENDER_NONCONFORMING("ANTIGENDER_NONCONFORMING"),

    /**
     * Anti-Heterosexual _sexual orientation bias
     * 
     */
    ANTIHETEROSEXUAL("ANTIHETEROSEXUAL"),

    /**
     * Anti-Hindu_religious bias
     * 
     */
    ANTIHINDU("ANTIHINDU"),

    /**
     * Anti-Hispanic or Latino_race ethnicity bias
     * 
     */
    ANTIHISPANIC_LATINO("ANTIHISPANIC_LATINO"),

    /**
     * Anti-Homosexual, e.g., Lesbian, Gay, Bisexual, and transgender (mixed group), _sexual orientation bias
     * 
     */
    ANTIHOMOSEXUAL("ANTIHOMOSEXUAL"),

    /**
     * Anti-Islamic, includes muslim_religious bias
     * 
     */
    ANTIISLAMIC("ANTIISLAMIC"),

    /**
     * Anti-Jehovah's Witness_religious bias
     * 
     */
    ANTIJEHOVAHWITNESS("ANTIJEHOVAHWITNESS"),

    /**
     * Anti-Jewish_religious bias
     * 
     */
    ANTIJEWISH("ANTIJEWISH"),

    /**
     * Anti-Male_gender bias
     * 
     */
    ANTIMALE("ANTIMALE"),

    /**
     * Anti-Male Homosexual (Gay) _sexual orientation bias
     * 
     */
    @XmlEnumValue("ANTIMALE HOMOSEXUAL")
    ANTIMALE_HOMOSEXUAL("ANTIMALE HOMOSEXUAL"),

    /**
     * Anti-Mental Disability_disability bias
     * 
     */
    @XmlEnumValue("ANTIMENTAL DISABILITY")
    ANTIMENTAL_DISABILITY("ANTIMENTAL DISABILITY"),

    /**
     * Anti-Mormon_religious bias
     * 
     */
    ANTIMORMON("ANTIMORMON"),

    /**
     * Anti-Multi-Racial Group, e.g., Arab and Asian and American Indian and White and etc._race ethnicity bias
     * 
     */
    @XmlEnumValue("ANTIMULTIRACIAL GROUP")
    ANTIMULTIRACIAL_GROUP("ANTIMULTIRACIAL GROUP"),

    /**
     * Anti-Multi-Religious Group, e.g., Catholic and Mormon and Islamic and etc._religious bias
     * 
     */
    @XmlEnumValue("ANTIMULTIRELIGIOUS GROUP")
    ANTIMULTIRELIGIOUS_GROUP("ANTIMULTIRELIGIOUS GROUP"),

    /**
     * Anti-Native Hawaiian or Other Pacific Islander_race ethnicity bias
     * 
     */
    ANTINATIVEHAWAIIAN_OTHERPACIFICISLANDER("ANTINATIVEHAWAIIAN_OTHERPACIFICISLANDER"),

    /**
     * Anti-Other Christian_religious bias
     * 
     */
    @XmlEnumValue("ANTIOTHER CHRISTIAN")
    ANTIOTHER_CHRISTIAN("ANTIOTHER CHRISTIAN"),

    /**
     * Anti-Other Race, Ethnicity, Ancestry, or National Origin_race ethnicity bias
     * 
     */
    @XmlEnumValue("ANTIOTHER ETHNICITY_NATIONAL ORIGIN")
    ANTIOTHER_ETHNICITY_NATIONAL_ORIGIN("ANTIOTHER ETHNICITY_NATIONAL ORIGIN"),

    /**
     * Anti-Other Religion_religious bias
     * 
     */
    @XmlEnumValue("ANTIOTHER RELIGION")
    ANTIOTHER_RELIGION("ANTIOTHER RELIGION"),

    /**
     * Anti-Physical Disability_disability bias
     * 
     */
    @XmlEnumValue("ANTIPHYSICAL DISABILITY")
    ANTIPHYSICAL_DISABILITY("ANTIPHYSICAL DISABILITY"),

    /**
     * Anti-Protestant_religious bias
     * 
     */
    ANTIPROTESTANT("ANTIPROTESTANT"),

    /**
     * Anti-Sikh_religious bias
     * 
     */
    ANTISIKH("ANTISIKH"),

    /**
     * Anti-Transgender_gender identity
     * 
     */
    ANTITRANSGENDER("ANTITRANSGENDER"),

    /**
     * Anti-White_race ethnicity bias
     * 
     */
    ANTIWHITE("ANTIWHITE"),

    /**
     * None (no bias)
     * 
     */
    NONE("NONE"),

    /**
     * Unknown (offender's motivation not known)
     * 
     */
    UNKNOWN("UNKNOWN");
    private final String value;

    BiasMotivationCodeSimpleType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static BiasMotivationCodeSimpleType fromValue(String v) {
        for (BiasMotivationCodeSimpleType c: BiasMotivationCodeSimpleType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
