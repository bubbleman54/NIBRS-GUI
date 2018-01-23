<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:cjis="http://fbi.gov/cjis/1.0" xmlns:cjiscodes="http://fbi.gov/cjis/cjis-codes/1.0" xmlns:fn="http://www.w3.org/2005/xpath-functions"
    xmlns:i="http://release.niem.gov/niem/appinfo/3.0/" xmlns:j="http://release.niem.gov/niem/domains/jxdm/5.2/" xmlns:nc="http://release.niem.gov/niem/niem-core/3.0/"
    xmlns:niem-xsd="http://release.niem.gov/niem/proxy/xsd/3.0/" xmlns:s="http://release.niem.gov/niem/structures/3.0/" xmlns:term="http://release.niem.gov/niem/localTerminology/3.0/"
    xmlns:ucr="http://release.niem.gov/niem/codes/fbi_ucr/3.2/" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:xsd="http://www.w3.org/2001/XMLSchema"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:strip-space elements="*"/>
    
    <xsl:template match="*[descendant::text() or descendant-or-self::*/@*[string()]]">
        <xsl:copy>
            <xsl:apply-templates select="node()|@*"/>
        </xsl:copy>
    </xsl:template>
    
    <xsl:template match="@*[string()]">
        <xsl:copy/>
    </xsl:template>
    
</xsl:stylesheet>
