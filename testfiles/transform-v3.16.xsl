<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:fn="http://www.w3.org/2005/xpath-functions"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:cjis="http://fbi.gov/cjis/1.0" 
    xmlns:cjiscodes="http://fbi.gov/cjis/cjis-codes/1.0" 
    xmlns:i="http://release.niem.gov/niem/appinfo/3.0/" 
    xmlns:ucr="http://release.niem.gov/niem/codes/fbi_ucr/3.2/" 
    xmlns:j="http://release.niem.gov/niem/domains/jxdm/5.2/" 
    xmlns:term="http://release.niem.gov/niem/localTerminology/3.0/" 
    xmlns:nc="http://release.niem.gov/niem/niem-core/3.0/" 
    xmlns:niem-xsd="http://release.niem.gov/niem/proxy/xsd/3.0/" 
    xmlns:s="http://release.niem.gov/niem/structures/3.0/" 
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
    xmlns:xsd="http://www.w3.org/2001/XMLSchema" 
    version="1.0">
    <xsl:strip-space elements="*"/>
    <xsl:template match="/">
        <xsl:for-each select="/*[local-name()='Submission']/*[local-name()='Report']">
            <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ws="http://ws.nibrs.ucr.cjis.fbi.gov/">
            <soapenv:Header/>
            <soapenv:Body>
            <ws:SubmitNibrsReport>
            <NibrsReport>
            <formatType>XML</formatType>
            <xsl:choose>
                <xsl:when test="*[local-name()='ReportHeader']/*[local-name()='NIBRSReportCategoryCode']='GROUP A INCIDENT REPORT'">
                    <xsl:apply-templates select="j:Arrestee" mode="GroupA"/>
                    <xsl:apply-templates select="nc:Incident"/>
                    <xsl:apply-templates select="j:Subject"/>
                    <xsl:apply-templates select="j:Offense"/>
                    <xsl:call-template name="nibrsProperties"/>
                    <!--<xsl:apply-templates select="nc:Substance"/>-->
                    <xsl:apply-templates select="*[local-name()='ReportHeader']"/>
                    <xsl:apply-templates select="j:Victim"/>
                </xsl:when>
                <xsl:when test="*[local-name()='ReportHeader']/*[local-name()='NIBRSReportCategoryCode']='GROUP B ARREST REPORT'">
                    <xsl:apply-templates select="j:Arrestee" mode="GroupB"/>
                    <xsl:apply-templates select="nc:Incident"/>
                    <xsl:apply-templates select="j:Subject"/>
                    <xsl:apply-templates select="j:Offense"/>
                    <xsl:call-template name="nibrsProperties"/>
                    <!--<xsl:apply-templates select="nc:Substance"/>-->
                    <xsl:apply-templates select="*[local-name()='ReportHeader']"/>
                    <xsl:apply-templates select="j:Victim"/>
                </xsl:when>
                <xsl:when test="*[local-name()='ReportHeader']/*[local-name()='NIBRSReportCategoryCode']='ZERO REPORT'">
                    <xsl:apply-templates select="*[local-name()='ReportHeader']"/>
                    <zeroReport>
                    <!-- Element 2, Incident Number -->
                    <incidentNumber><xsl:value-of select="*[local-name()='ReportHeader']/*[local-name()='ReportingAgency']/j:OrganizationAugmentation/j:OrganizationORIIdentification/nc:IdentificationID"/></incidentNumber>
                    <!-- Zero Report Month -->
                    <month><xsl:call-template name="date">
                        <xsl:with-param name="Date" select="*[local-name()='ReportHeader']/*[local-name()='ReportDate']/nc:Date"/>
                        <xsl:with-param name="DateTime" select="*[local-name()='ReportHeader']/*[local-name()='ReportDate']/nc:DateTime"/>
                        <xsl:with-param name="YearMonthDate" select="*[local-name()='ReportHeader']/*[local-name()='ReportDate']/nc:YearMonthDate"/>
                        <xsl:with-param name="get" select="'month'"/>
                    </xsl:call-template></month>
                    <!-- Zero Report Year -->
                    <year><xsl:call-template name="date">
                        <xsl:with-param name="Date" select="*[local-name()='ReportHeader']/*[local-name()='ReportDate']/nc:Date"/>
                        <xsl:with-param name="DateTime" select="*[local-name()='ReportHeader']/*[local-name()='ReportDate']/nc:DateTime"/>
                        <xsl:with-param name="YearMonthDate" select="*[local-name()='ReportHeader']/*[local-name()='ReportDate']/nc:YearMonthDate"/>
                        <xsl:with-param name="get" select="'year'"/>
                    </xsl:call-template></year>
                    </zeroReport>
                </xsl:when>
            </xsl:choose>
            </NibrsReport>
            </ws:SubmitNibrsReport>
            </soapenv:Body>
            </soapenv:Envelope>
        </xsl:for-each>
    </xsl:template>
    <xsl:template match="j:Arrestee" mode="GroupA">
        <xsl:variable name="person-node" select="../nc:Person[@s:id=current()/nc:RoleOfPerson/@s:ref]"/>
        <xsl:variable name="arrest-ref" select="../j:ArrestSubjectAssociation[j:Subject/@s:ref=current()/@s:id]/nc:Activity/@s:ref"/>
        <xsl:variable name="arrest" select="../j:Arrest[@s:id=$arrest-ref]"/>
        <nibrsGroupAArrestees>
        <!-- Element 47, Age of Arrestee (only one would be included per victim)-->
        <xsl:call-template name="age">
            <xsl:with-param name="person-node" select="$person-node"/>
        </xsl:call-template>
        <!-- Element 50, Ethnicity of Arrestee -->
        <ethnicity><xsl:value-of select="$person-node/j:PersonEthnicityCode"/></ethnicity>
        <!-- Element 49, Race of Arrestee -->
        <race><xsl:value-of select="$person-node/j:PersonRaceNDExCode"/></race>
        <!-- Element 48, Sex of Arrestee -->
        <sex><xsl:value-of select="$person-node/j:PersonSexCode"/></sex>
        <!-- Element 46, Arrestee Was Armed With -->
        <xsl:apply-templates select="j:ArresteeArmedWithCode"/>
        <!-- Element 52, Disposition of Arrestee Under 18 -->
        <juvenileDisposition><xsl:value-of select="j:ArresteeJuvenileDispositionCode"/></juvenileDisposition>
        <nibrsArrest>
        <!-- Element 45, UCR Arrest Offense Code -->
        <arrestChargeUCRCode><xsl:value-of select="$arrest/j:ArrestCharge/*[local-name()='ChargeUCRCode']"/></arrestChargeUCRCode>
        <!-- Element 42, Arrest Date -->
        <xsl:apply-templates select="$arrest/nc:ActivityDate" mode="arrestDate"/>
        <!-- Element 41, Arrest Transaction Number -->
        <arrestTransactionNumber><xsl:value-of select="$arrest/nc:ActivityIdentification/nc:IdentificationID"/></arrestTransactionNumber>
        <!-- Element 2, Incident Number -->
        <incidentNumber><xsl:value-of select="../nc:Incident/nc:ActivityIdentification/nc:IdentificationID"/></incidentNumber>
        <!-- Element 43, Type Of Arrest -->
        <typeOfArrest><xsl:value-of select="$arrest/j:ArrestCategoryCode"/></typeOfArrest>
        </nibrsArrest>
        <!-- Element 51, Resident Status -->
        <residentStatus><xsl:value-of select="$person-node/j:PersonResidentCode"/></residentStatus>
        <!-- Element 40, Arrestee Sequence Number -->
        <sequenceNumber><xsl:value-of select="j:ArrestSequenceID"/></sequenceNumber>
        <!-- Element 44, Multiple Arrestee Segments Indicator -->
        <multipleArresteeSegmentIndicator><xsl:value-of select="j:ArrestSubjectCountCode"/></multipleArresteeSegmentIndicator>
        </nibrsGroupAArrestees>
    </xsl:template>
    <xsl:template match="j:Arrestee" mode="GroupB">
        <xsl:variable name="person-node" select="../nc:Person[@s:id=current()/nc:RoleOfPerson/@s:ref]"/>
        <xsl:variable name="arrest-ref" select="../j:ArrestSubjectAssociation[j:Subject/@s:ref=current()/@s:id]/nc:Activity/@s:ref"/>
        <xsl:variable name="arrest" select="../j:Arrest[@s:id=$arrest-ref]"/>
        <nibrsGroupBArrestees>
        <!-- Element 47, Age of Arrestee (only one would be included per victim)-->
        <xsl:call-template name="age">
            <xsl:with-param name="person-node" select="$person-node"/>
        </xsl:call-template>
        <!-- Element 50, Ethnicity of Arrestee -->
        <ethnicity><xsl:value-of select="$person-node/j:PersonEthnicityCode"/></ethnicity>
        <!-- Element 49, Race of Arrestee -->
        <race><xsl:value-of select="$person-node/j:PersonRaceNDExCode"/></race>
        <!-- Element 48, Sex of Arrestee -->
        <sex><xsl:value-of select="$person-node/j:PersonSexCode"/></sex>
        <!-- Element 46, Arrestee Was Armed With -->
        <xsl:apply-templates select="j:ArresteeArmedWithCode"/>
        <!-- Element 52, Disposition of Arrestee Under 18 -->
        <juvenileDisposition><xsl:value-of select="j:ArresteeJuvenileDispositionCode"/></juvenileDisposition>
        <nibrsArrest>
        <!-- Element 45, UCR Arrest Offense Code -->
        <arrestChargeUCRCode><xsl:value-of select="$arrest/j:ArrestCharge/*[local-name()='ChargeUCRCode']"/></arrestChargeUCRCode>
        <!-- Element 42, Arrest Date -->
        <xsl:apply-templates select="$arrest/nc:ActivityDate" mode="arrestDate"/>
        <!-- Element 41, Arrest Transaction Number -->
        <arrestTransactionNumber><xsl:value-of select="$arrest/nc:ActivityIdentification/nc:IdentificationID"/></arrestTransactionNumber>
        <!-- Element 2, Incident Number -->
        <!--<incidentNumber><xsl:value-of select="../nc:Incident/nc:ActivityIdentification/nc:IdentificationID"/></incidentNumber>-->
        <!-- Element 43, Type Of Arrest -->
        <typeOfArrest><xsl:value-of select="$arrest/j:ArrestCategoryCode"/></typeOfArrest>
        </nibrsArrest>
        <!-- Element 51, Resident Status -->
        <residentStatus><xsl:value-of select="$person-node/j:PersonResidentCode"/></residentStatus>
        <!-- Element 40, Arrestee Sequence Number -->
        <sequenceNumber><xsl:value-of select="j:ArrestSequenceID"/></sequenceNumber>
        </nibrsGroupBArrestees>
    </xsl:template>
    <xsl:template match="nc:Incident">
        <nibrsIncident>
        <!-- Element 2A, Cargo Theft Indicator: True/False-->
        <cargoTheftIndicator><xsl:choose>
            <xsl:when test="cjis:IncidentAugmentation/j:OffenseCargoTheftIndicator='true'">Y</xsl:when>
            <xsl:when test="cjis:IncidentAugmentation/j:OffenseCargoTheftIndicator='false'">N</xsl:when>
        </xsl:choose></cargoTheftIndicator>
        <!--<exceptionalClearanceCodes></exceptionalClearanceCodes>-->
        <!-- Element 5, Exceptional Clearance Date -->
        <exceptionalClearanceDate><xsl:apply-templates select="j:IncidentAugmentation/j:IncidentExceptionalClearanceDate"/></exceptionalClearanceDate>
        <!-- Element 4, Cleared Exceptionally -->
        <exceptionallyClearedFlag><xsl:value-of select="j:IncidentAugmentation/j:IncidentExceptionalClearanceCode"/></exceptionallyClearedFlag>
        <!-- Element 3, Incident Date -->
        <xsl:apply-templates select="nc:ActivityDate" mode="incidentDate"/>
        <!-- Element 3, Incident Hour -->
        <incidentHour><xsl:value-of select="substring-before(substring-after(nc:ActivityDate/nc:DateTime, 'T'), ':')"/></incidentHour>
        <!-- Element 2, Incident Number -->
        <incidentNumber><xsl:value-of select="nc:ActivityIdentification/nc:IdentificationID"/></incidentNumber>
        <!-- Element 3, IncidentReportDateIndicator is true to designate that the ActivityDate is the Report Date rather than the Incident Date -->
        <reportDateIndicator><xsl:if test="cjis:IncidentAugmentation/cjis:IncidentReportDateIndicator='true'">R</xsl:if></reportDateIndicator>
        </nibrsIncident>
    </xsl:template>
    <xsl:template match="j:Subject">
        <xsl:variable name="person-node" select="../nc:Person[@s:id=current()/nc:RoleOfPerson/@s:ref]"/>
        <nibrsOffenders>
        <!-- Element 37, Age of Subject (only one would be included per subject)-->
        <xsl:call-template name="age">
            <xsl:with-param name="person-node" select="$person-node"/>
        </xsl:call-template>
        <!-- Element 39A, Ethnicity of Subject -->
        <ethnicity><xsl:value-of select="$person-node/j:PersonEthnicityCode"/></ethnicity>
        <!-- Element 39, Race of Subject -->
        <race><xsl:value-of select="$person-node/j:PersonRaceNDExCode"/></race>
        <!-- Element 38, Sex of Subject -->
        <sex><xsl:value-of select="$person-node/j:PersonSexCode"/></sex>
        <!-- Element 36, Offender Sequence Number -->
        <sequenceNumber><xsl:value-of select="j:SubjectSequenceNumberText"/></sequenceNumber>
        </nibrsOffenders>
    </xsl:template>
    <xsl:template match="j:Offense">
        <xsl:variable name="loc-ref" select="../j:OffenseLocationAssociation[j:Offense/@s:ref=current()/@s:id]/nc:Location/@s:ref"/>
        <nibrsOffenses>
        <!-- Element 8A, Bias Motivation -->
        <xsl:apply-templates select="j:OffenseFactorBiasMotivationCode"/>
        <!-- Element 12, Type Criminal Activity/Gang Information -->
        <xsl:apply-templates select="*[local-name()='CriminalActivityCategoryCode']"/>
        <!-- Element 9, Location Type -->
        <locationType><xsl:value-of select="../nc:Location[@s:id=$loc-ref]/*[local-name()='LocationCategoryCode']"/></locationType>
        <!-- Element 11, Method of Entry -->
        <methodOfEntry><xsl:value-of select="j:OffenseEntryPoint/j:PassagePointMethodCode"/></methodOfEntry>
        <numberPremisesEntered><xsl:value-of select="j:OffenseStructuresEnteredQuantity"/></numberPremisesEntered>
        <!-- Element 8, Offender(s) Suspected Of Using  -->
        <xsl:apply-templates select="j:OffenseFactor/j:OffenseFactorCode"/>
        <!-- Element 7, Attempted/Completed -->
        <offenseAttemptedIndicator><xsl:choose>
            <xsl:when test="j:OffenseAttemptedIndicator='true'">A</xsl:when>
            <xsl:when test="j:OffenseAttemptedIndicator='false'">C</xsl:when>
        </xsl:choose></offenseAttemptedIndicator>
        <!-- Element 13, Type Weapon/Force Involved -->
        <xsl:apply-templates select="j:OffenseForce/j:ForceCategoryCode"/>
        <!-- Element 6, Offense Code -->
        <UCROffenseCode><xsl:value-of select="*[local-name()='OffenseUCRCode']"/></UCROffenseCode>
        </nibrsOffenses>
    </xsl:template>
    <xsl:template name="nibrsProperties">
        <xsl:variable name="blank" select="nc:Item[not(nc:ItemStatus/cjis:ItemStatusCode)]"></xsl:variable>
        <xsl:variable name="none" select="nc:Item[nc:ItemStatus/cjis:ItemStatusCode='NONE'] | nc:Substance[nc:ItemStatus/cjis:ItemStatusCode='NONE']"/>
        <!--nc:Item[not(nc:ItemStatus/cjis:ItemStatusCode)]-->
        <xsl:variable name="burned" select="nc:Item[nc:ItemStatus/cjis:ItemStatusCode='BURNED'] | nc:Substance[nc:ItemStatus/cjis:ItemStatusCode='BURNED']"/>
        <xsl:variable name="counterfeited" select="nc:Item[nc:ItemStatus/cjis:ItemStatusCode='COUNTERFEITED'] | nc:Substance[nc:ItemStatus/cjis:ItemStatusCode='COUNTERFEITED']"/>
        <xsl:variable name="destroyed" select="nc:Item[nc:ItemStatus/cjis:ItemStatusCode='DESTROYED_DAMAGED_VANDALIZED'] | nc:Item[nc:ItemStatus/cjis:ItemStatusCode='DAMAGED'] | nc:Item[nc:ItemStatus/cjis:ItemStatusCode='DESTROYED'] | nc:Item[nc:ItemStatus/cjis:ItemStatusCode='VANDALIZED'] | nc:Substance[nc:ItemStatus/cjis:ItemStatusCode='DESTROYED_DAMAGED_VANDALIZED'] | nc:Substance[nc:ItemStatus/cjis:ItemStatusCode='DAMAGED'] | nc:Substance[nc:ItemStatus/cjis:ItemStatusCode='DESTROYED'] | nc:Substance[nc:ItemStatus/cjis:ItemStatusCode='VANDALIZED']"/>
        <xsl:variable name="recovered" select="nc:Item[nc:ItemStatus/cjis:ItemStatusCode='RECOVERED'] | nc:Substance[nc:ItemStatus/cjis:ItemStatusCode='RECOVERED']"/>
        <xsl:variable name="seized" select="nc:Item[nc:ItemStatus/cjis:ItemStatusCode='SEIZED'] | nc:Substance[nc:ItemStatus/cjis:ItemStatusCode='SEIZED'] | nc:Substance[not(nc:ItemStatus/cjis:ItemStatusCode)]"/>
        <xsl:variable name="stolen" select="nc:Item[nc:ItemStatus/cjis:ItemStatusCode='STOLEN'] | nc:Item[nc:ItemStatus/cjis:ItemStatusCode='STOLEN_BRIBED'] | nc:Item[nc:ItemStatus/cjis:ItemStatusCode='STOLEN_DEFRAUDED'] | nc:Item[nc:ItemStatus/cjis:ItemStatusCode='STOLEN_EMBEZZLED']| nc:Item[nc:ItemStatus/cjis:ItemStatusCode='STOLEN_EXTORTED'] | nc:Item[nc:ItemStatus/cjis:ItemStatusCode='STOLEN_RANSOMED'] | nc:Item[nc:ItemStatus/cjis:ItemStatusCode='STOLEN_ROBBED'] | nc:Substance[nc:ItemStatus/cjis:ItemStatusCode='STOLEN'] | nc:Substance[nc:ItemStatus/cjis:ItemStatusCode='STOLEN_BRIBED'] | nc:Substance[nc:ItemStatus/cjis:ItemStatusCode='STOLEN_DEFRAUDED'] | nc:Substance[nc:ItemStatus/cjis:ItemStatusCode='STOLEN_EMBEZZLED']| nc:Substance[nc:ItemStatus/cjis:ItemStatusCode='STOLEN_EXTORTED'] | nc:Substance[nc:ItemStatus/cjis:ItemStatusCode='STOLEN_RANSOMED'] | nc:Substance[nc:ItemStatus/cjis:ItemStatusCode='STOLEN_ROBBED']"/>
        <!-- UNKNOWN, BAIT, CARGO, CONTRABAND, CULTIVATED, FOUND, LOST, RETURNED -->
        <xsl:variable name="unknown" select="nc:Item[nc:ItemStatus/cjis:ItemStatusCode='UNKNOWN'] | nc:Substance[nc:ItemStatus/cjis:ItemStatusCode='UNKNOWN']"/>
        <xsl:variable name="wrong" select="nc:Item[nc:ItemStatus/cjis:ItemStatusCode and nc:ItemStatus/cjis:ItemStatusCode!='NONE' and nc:ItemStatus/cjis:ItemStatusCode!='BURNED' and nc:ItemStatus/cjis:ItemStatusCode!='COUNTERFEITED' and nc:ItemStatus/cjis:ItemStatusCode!='DESTROYED_DAMAGED_VANDALIZED' and nc:ItemStatus/cjis:ItemStatusCode!='DAMAGED' and nc:ItemStatus/cjis:ItemStatusCode!='DESTROYED' and nc:ItemStatus/cjis:ItemStatusCode!='VANDALIZED' and nc:ItemStatus/cjis:ItemStatusCode!='DESTROYED_DAMAGED_VANDALIZED' and nc:ItemStatus/cjis:ItemStatusCode!='RECOVERED' and nc:ItemStatus/cjis:ItemStatusCode!='SEIZED' and nc:ItemStatus/cjis:ItemStatusCode!='STOLEN' and nc:ItemStatus/cjis:ItemStatusCode!='STOLEN_BRIBED' and nc:ItemStatus/cjis:ItemStatusCode!='STOLEN_DEFRAUDED' and nc:ItemStatus/cjis:ItemStatusCode!='STOLEN_EMBEZZLED' and nc:ItemStatus/cjis:ItemStatusCode!='STOLEN_EXTORTED' and nc:ItemStatus/cjis:ItemStatusCode!='STOLEN_RANSOMED' and nc:ItemStatus/cjis:ItemStatusCode!='STOLEN_ROBBED' and nc:ItemStatus/cjis:ItemStatusCode!='UNKNOWN'] | nc:Substance[nc:ItemStatus/cjis:ItemStatusCode and nc:ItemStatus/cjis:ItemStatusCode!='NONE' and nc:ItemStatus/cjis:ItemStatusCode !='BURNED' and nc:ItemStatus/cjis:ItemStatusCode!='COUNTERFEITED' and nc:ItemStatus/cjis:ItemStatusCode!='DESTROYED_DAMAGED_VANDALIZED' and nc:ItemStatus/cjis:ItemStatusCode!='DAMAGED' and nc:ItemStatus/cjis:ItemStatusCode!='DESTROYED' and nc:ItemStatus/cjis:ItemStatusCode!='VANDALIZED' and nc:ItemStatus/cjis:ItemStatusCode!='DESTROYED_DAMAGED_VANDALIZED' and nc:ItemStatus/cjis:ItemStatusCode!='RECOVERED' and nc:ItemStatus/cjis:ItemStatusCode!='SEIZED' and nc:ItemStatus/cjis:ItemStatusCode!='STOLEN' and nc:ItemStatus/cjis:ItemStatusCode!='STOLEN_BRIBED' and nc:ItemStatus/cjis:ItemStatusCode!='STOLEN_DEFRAUDED' and nc:ItemStatus/cjis:ItemStatusCode!='STOLEN_EMBEZZLED' and nc:ItemStatus/cjis:ItemStatusCode!='STOLEN_EXTORTED' and nc:ItemStatus/cjis:ItemStatusCode!='STOLEN_RANSOMED' and nc:ItemStatus/cjis:ItemStatusCode!='STOLEN_ROBBED' and nc:ItemStatus/cjis:ItemStatusCode!='UNKNOWN']"></xsl:variable>
        <xsl:if test="$blank">
            <nibrsProperties>
            <!-- Element 2, Incident Number -->
            <incidentNumber><xsl:value-of select="nc:Incident/nc:ActivityIdentification/nc:IdentificationID"/></incidentNumber>
            <xsl:apply-templates select="$blank"/>
            </nibrsProperties>
        </xsl:if>
        <xsl:if test="$none">
            <nibrsProperties>
            <!-- Element 2, Incident Number -->
            <incidentNumber><xsl:value-of select="nc:Incident/nc:ActivityIdentification/nc:IdentificationID"/></incidentNumber>
            <xsl:apply-templates select="$none"/>
            <!-- Element 14, Type Property Loss/etc Substituted for nc:ItemStatus -->
            <typePropertyLossCode>1</typePropertyLossCode>
            </nibrsProperties>
        </xsl:if>
        <xsl:if test="$burned">
            <nibrsProperties>
            <!-- Element 2, Incident Number -->
            <incidentNumber><xsl:value-of select="nc:Incident/nc:ActivityIdentification/nc:IdentificationID"/></incidentNumber>
            <xsl:apply-templates select="$burned"/>
            <!-- Element 14, Type Property Loss/etc Substituted for nc:ItemStatus -->
            <typePropertyLossCode>2</typePropertyLossCode>
            </nibrsProperties>
        </xsl:if>
        <xsl:if test="$counterfeited">
            <nibrsProperties>
            <!-- Element 2, Incident Number -->
            <incidentNumber><xsl:value-of select="nc:Incident/nc:ActivityIdentification/nc:IdentificationID"/></incidentNumber>
            <xsl:apply-templates select="$counterfeited"/>
            <!-- Element 14, Type Property Loss/etc Substituted for nc:ItemStatus -->
            <typePropertyLossCode>3</typePropertyLossCode>
            </nibrsProperties>
        </xsl:if>
        <xsl:if test="$destroyed">
            <nibrsProperties>
            <!-- Element 2, Incident Number -->
            <incidentNumber><xsl:value-of select="nc:Incident/nc:ActivityIdentification/nc:IdentificationID"/></incidentNumber>
            <xsl:apply-templates select="$destroyed"/>
            <!-- Element 14, Type Property Loss/etc Substituted for nc:ItemStatus -->
            <typePropertyLossCode>4</typePropertyLossCode>
            </nibrsProperties>
        </xsl:if>
        <xsl:if test="$recovered">
            <nibrsProperties>
            <!-- Element 2, Incident Number -->
            <incidentNumber><xsl:value-of select="nc:Incident/nc:ActivityIdentification/nc:IdentificationID"/></incidentNumber>
            <xsl:apply-templates select="$recovered"/>
            <xsl:if test="j:Offense/*[local-name()='OffenseUCRCode']='240' and j:Offense/j:OffenseAttemptedIndicator='false' and ($recovered/j:ItemCategoryNIBRSPropertyCategoryCode='03' or $recovered/j:ItemCategoryNIBRSPropertyCategoryCode='05' or $recovered/j:ItemCategoryNIBRSPropertyCategoryCode='24' or $recovered/j:ItemCategoryNIBRSPropertyCategoryCode='28' or $recovered/j:ItemCategoryNIBRSPropertyCategoryCode='37')">
                <!-- Element 19, Number of Recovered Motor Vehicles, if Status is Recovered -->
                <recoveredVehicleQuantity><xsl:call-template name="vehicleQuantity"><xsl:with-param name="items" select="$recovered"/><xsl:with-param name="position" select="1"/><xsl:with-param name="count" select="0"/></xsl:call-template></recoveredVehicleQuantity>
            </xsl:if>
            <!-- Element 14, Type Property Loss/etc Substituted for nc:ItemStatus -->
            <typePropertyLossCode>5</typePropertyLossCode>
            </nibrsProperties>
        </xsl:if>
        <xsl:if test="$seized">
            <nibrsProperties>
            <!-- Element 2, Incident Number -->
            <incidentNumber><xsl:value-of select="nc:Incident/nc:ActivityIdentification/nc:IdentificationID"/></incidentNumber>
            <xsl:apply-templates select="$seized"/>
            <!-- Element 14, Type Property Loss/etc Substituted for nc:ItemStatus -->
            <typePropertyLossCode>6</typePropertyLossCode>
            </nibrsProperties>
        </xsl:if>
        <xsl:if test="$stolen">
            <nibrsProperties>
            <!-- Element 2, Incident Number -->
            <incidentNumber><xsl:value-of select="nc:Incident/nc:ActivityIdentification/nc:IdentificationID"/></incidentNumber>
            <xsl:apply-templates select="$stolen"/>
            <!-- Element 14, Type Property Loss/etc Substituted for nc:ItemStatus -->
            <xsl:if test="j:Offense/*[local-name()='OffenseUCRCode']='240' and j:Offense/j:OffenseAttemptedIndicator='false' and ($stolen/j:ItemCategoryNIBRSPropertyCategoryCode='03' or $stolen/j:ItemCategoryNIBRSPropertyCategoryCode='05' or $stolen/j:ItemCategoryNIBRSPropertyCategoryCode='24' or $stolen/j:ItemCategoryNIBRSPropertyCategoryCode='28' or $stolen/j:ItemCategoryNIBRSPropertyCategoryCode='37')">
                <!-- Element 18, Number of Stolen Motor Vehicles, if Status is Stolen -->
                <stolenVehicleQuantity><xsl:call-template name="vehicleQuantity"><xsl:with-param name="items" select="$stolen"/><xsl:with-param name="position" select="1"/><xsl:with-param name="count" select="0"/></xsl:call-template></stolenVehicleQuantity>
            </xsl:if>
            <typePropertyLossCode>7</typePropertyLossCode>
            </nibrsProperties>
        </xsl:if>
        <xsl:if test="$unknown">
            <nibrsProperties>
            <!-- Element 2, Incident Number -->
            <incidentNumber><xsl:value-of select="nc:Incident/nc:ActivityIdentification/nc:IdentificationID"/></incidentNumber>
            <xsl:apply-templates select="$unknown"/>
            <!-- Element 14, Type Property Loss/etc Substituted for nc:ItemStatus -->
            <typePropertyLossCode>8</typePropertyLossCode>
            </nibrsProperties>
        </xsl:if>
        <xsl:if test="$wrong">
            <nibrsProperties>
            <!-- Element 2, Incident Number -->
            <incidentNumber><xsl:value-of select="nc:Incident/nc:ActivityIdentification/nc:IdentificationID"/></incidentNumber>
            <xsl:apply-templates select="$wrong"/>
            <!-- Element 14, Type Property Loss/etc Substituted for nc:ItemStatus -->
            <typePropertyLossCode><xsl:value-of select="$wrong/nc:ItemStatus/cjis:ItemStatusCode"/></typePropertyLossCode>
            </nibrsProperties>
        </xsl:if>
    </xsl:template>
    <xsl:template match="nc:Item">
        <nibrsPropertyItems>
        <!-- Element 16, Value of Property in US Dollars -->
        <amount><xsl:value-of select="nc:ItemValue/nc:ItemValueAmount"/></amount>
        <!-- Element 15, Property Description -->
        <categoryCode><xsl:value-of select="j:ItemCategoryNIBRSPropertyCategoryCode"/></categoryCode>
        <!-- Element 17, Date Recovered -->
        <xsl:apply-templates select="nc:ItemValue/nc:ItemValueDate"/>
        </nibrsPropertyItems>
    </xsl:template>
    <xsl:template name="vehicleQuantity">
        <xsl:param name="items"/>
        <xsl:param name="position"/>
        <xsl:param name="count"/>
        <xsl:choose>
            <xsl:when test="$items[$position]">
                <xsl:variable name="code" select="$items[$position]/j:ItemCategoryNIBRSPropertyCategoryCode"/>
                <xsl:choose>   
                    <xsl:when test="$code='03' or $code='05' or $code='24' or $code='28' or $code='37'">
                        <xsl:call-template name="vehicleQuantity">
                            <xsl:with-param name="items" select="$items"/>
                            <xsl:with-param name="position" select="$position+1"/>
                            <xsl:with-param name="count">
                                <xsl:choose>
                                    <xsl:when test="not($items[$position]/nc:ItemQuantity)">
                                        <!-- Add 1 or add 0, if the item does not have an nc:ItemQuantity ??? -->
                                        <xsl:value-of select="$count+1"/>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:value-of select="$count + $items[$position]/nc:ItemQuantity"/>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </xsl:with-param>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:call-template name="vehicleQuantity">
                            <xsl:with-param name="items" select="$items"/>
                            <xsl:with-param name="position" select="$position+1"/>
                            <xsl:with-param name="count" select="$count"/>
                        </xsl:call-template>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$count"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template match="nc:Substance">
        <!--<nibrsProperties>-->
        <!-- Element 2, Incident Number -->
        <!--<incidentNumber><xsl:value-of select="../nc:Incident/nc:ActivityIdentification/nc:IdentificationID"/></incidentNumber>-->
        <itemSubstances>
        <!-- Element 21, Estimated Quantity -->
        <estimatedDrugQuantity><xsl:value-of select="nc:SubstanceQuantityMeasure/nc:MeasureDecimalValue"/></estimatedDrugQuantity>
        <!-- Element 20, Suspected Involved Drug Type -->
        <suspectedDrugType><xsl:value-of select="j:DrugCategoryCode"/></suspectedDrugType>
        <!-- Element 22, Fraction -->
        <typeDrugMeasurement><xsl:value-of select="nc:SubstanceQuantityMeasure/j:SubstanceUnitCode"/></typeDrugMeasurement>
        </itemSubstances>
        <!--</nibrsProperties>-->
    </xsl:template>  
    <xsl:template match="*[local-name()='ReportHeader']">
        <nibrsReportHeader>
        <actionType><xsl:value-of select="*[local-name()='ReportActionCategoryCode']"/></actionType>
        <!-- Element 1, ORI Code -->
        <agencyORI><xsl:value-of select="*[local-name()='ReportingAgency']/j:OrganizationAugmentation/j:OrganizationORIIdentification/nc:IdentificationID"/></agencyORI>
        <reportDateMonth><xsl:call-template name="date">
            <xsl:with-param name="get" select="'month'"/>
            <xsl:with-param name="Date" select="*[local-name()='ReportDate']/nc:Date"/>
            <xsl:with-param name="DateTime" select="*[local-name()='ReportDate']/nc:DateTime"/>
            <xsl:with-param name="YearMonthDate" select="*[local-name()='ReportDate']/nc:YearMonthDate"/>
        </xsl:call-template></reportDateMonth>
        <reportDateYear><xsl:call-template name="date">
            <xsl:with-param name="get" select="'year'"/>
            <xsl:with-param name="Date" select="*[local-name()='ReportDate']/nc:Date"/>
            <xsl:with-param name="DateTime" select="*[local-name()='ReportDate']/nc:DateTime"/>
            <xsl:with-param name="YearMonthDate" select="*[local-name()='ReportDate']/nc:YearMonthDate"/>
        </xsl:call-template></reportDateYear>
        <segmentLevel><xsl:choose>
            <xsl:when test="*[local-name()='NIBRSReportCategoryCode']='GROUP A INCIDENT REPORT'">1</xsl:when>
            <xsl:when test="*[local-name()='NIBRSReportCategoryCode']='GROUP B ARREST REPORT'">7</xsl:when>
            <xsl:when test="*[local-name()='NIBRSReportCategoryCode']='ZERO REPORT'">0</xsl:when>
        </xsl:choose></segmentLevel>
        </nibrsReportHeader> 
    </xsl:template>
    <xsl:template match="j:Victim">
        <xsl:variable name="person-node" select="../nc:Person[@s:id=current()/nc:RoleOfPerson/@s:ref]"/>
        <xsl:variable name="enforcementOfficial" select="../j:EnforcementOfficial[nc:RoleOfPerson/@s:ref=current()/nc:RoleOfPerson/@s:ref]"/>
        <nibrsVictims>
        <!-- Element 26, Age of Victim (only one would be included per victim)-->
        <xsl:call-template name="age">
            <xsl:with-param name="person-node" select="$person-node"/>
        </xsl:call-template>
        <!-- Element 29, Ethnicity of Victim -->
        <ethnicity><xsl:value-of select="$person-node/j:PersonEthnicityCode"/></ethnicity>
        <!-- Element 28, Race of Victim -->
        <race><xsl:value-of select="$person-node/j:PersonRaceNDExCode"/></race>
        <!-- Element 27, Sex of Victim -->
        <sex><xsl:value-of select="$person-node/j:PersonSexCode"/></sex>
        <!-- Element 31, Aggravated Assault/Homicide Circumstances -->
        <xsl:apply-templates select="j:VictimAggravatedAssaultHomicideFactorCode"/>
        <!-- Element 25B, Assignment Type (Officer) -->
        <assignmentType><xsl:value-of select="$enforcementOfficial/j:EnforcementOfficialAssignmentCategoryCode"/></assignmentType>
        <xsl:for-each select="../j:OffenseVictimAssociation[j:Victim/@s:ref=current()/@s:id]">
            <xsl:variable name="offense" select="../j:Offense[@s:id=current()/j:Offense/@s:ref]"/>
            <!-- Element 24, Victim Connected to UCR Offense Code -->
            <connectedToUCROffenseCodes><xsl:value-of select="$offense/*[local-name()='OffenseUCRCode']"/></connectedToUCROffenseCodes>
        </xsl:for-each>
        <!-- Element 33, Type Injury -->
        <xsl:apply-templates select="j:VictimInjury"/>
        <!-- Element 32, Additional Justifiable Homicide Circumstances -->
        <justifiableHomicideCode><xsl:value-of select="j:VictimJustifiableHomicideFactorCode"/></justifiableHomicideCode>
        <xsl:for-each select="../j:SubjectVictimAssociation[j:Victim/@s:ref=current()/@s:id]">
            <xsl:variable name="subject" select="../j:Subject[@s:id=current()/j:Subject/@s:ref]"/>
            <nibrsRelationships>
            <!--Element 34, Offender Number(s) to be related -->
            <offenderNumber><xsl:value-of select="$subject/j:SubjectSequenceNumberText"/></offenderNumber>
            <!-- Element 35, Relationship(s) of Victim To Offender -->
            <victimToOffenderRelationship><xsl:choose>
                <xsl:when test="j:VictimToSubjectRelationshipCode='Acquaintance'">AQ</xsl:when>
                <xsl:when test="j:VictimToSubjectRelationshipCode='Babysittee'">BE</xsl:when>
                <xsl:when test="j:VictimToSubjectRelationshipCode='Boyfriend_Girlfriend'">BG</xsl:when>
                <xsl:when test="j:VictimToSubjectRelationshipCode='Child of Boyfriend_Girlfriend'">CF</xsl:when>
                <xsl:when test="j:VictimToSubjectRelationshipCode='Family Member_Child'">CH</xsl:when>
                <xsl:when test="j:VictimToSubjectRelationshipCode='Family Member_Spouse_Common Law'">CS</xsl:when>
                <xsl:when test="j:VictimToSubjectRelationshipCode='Employee'">EE</xsl:when>
                <xsl:when test="j:VictimToSubjectRelationshipCode='Employer'">ER</xsl:when>
                <xsl:when test="j:VictimToSubjectRelationshipCode='Friend'">FR</xsl:when>
                <xsl:when test="j:VictimToSubjectRelationshipCode='Family Member_Grandchild'">GC</xsl:when>
                <xsl:when test="j:VictimToSubjectRelationshipCode='Family Member_Grandparent'">GP</xsl:when>
                <xsl:when test="j:VictimToSubjectRelationshipCode='Family Member_In-Law'">IL</xsl:when>
                <xsl:when test="j:VictimToSubjectRelationshipCode='Neighbor'">NE</xsl:when>
                <xsl:when test="j:VictimToSubjectRelationshipCode='Family Member'">OF</xsl:when>
                <xsl:when test="j:VictimToSubjectRelationshipCode='NonFamily_Otherwise Known'">OK</xsl:when>
                <xsl:when test="j:VictimToSubjectRelationshipCode='Family Member_Parent'">PA</xsl:when>
                <xsl:when test="j:VictimToSubjectRelationshipCode='Relationship Unknown'">RU</xsl:when>
                <xsl:when test="j:VictimToSubjectRelationshipCode='Family Member_Sibling'">SB</xsl:when>
                <xsl:when test="j:VictimToSubjectRelationshipCode='Family Member_Stepchild'">SC</xsl:when>
                <xsl:when test="j:VictimToSubjectRelationshipCode='Family Member_Spouse'">SE</xsl:when>
                <xsl:when test="j:VictimToSubjectRelationshipCode='Family Member_Stepparent'">SP</xsl:when>
                <xsl:when test="j:VictimToSubjectRelationshipCode='Family Member_Stepsibling'">SS</xsl:when>
                <xsl:when test="j:VictimToSubjectRelationshipCode='Stranger'">ST</xsl:when>
                <xsl:when test="j:VictimToSubjectRelationshipCode='Victim Was Offender'">VO</xsl:when>
                <xsl:when test="j:VictimToSubjectRelationshipCode='Ex_Spouse'">XS</xsl:when>
                <xsl:otherwise><xsl:value-of select="j:VictimToSubjectRelationshipCode"/></xsl:otherwise>
            </xsl:choose></victimToOffenderRelationship>
            </nibrsRelationships>
        </xsl:for-each>
        <!-- Element 25C, ORI-Other Jurisdiction (Officer) -->
        <outsideAgencyORI><xsl:value-of select="$enforcementOfficial/j:EnforcementOfficialUnit/j:OrganizationAugmentation/j:OrganizationORIIdentification/nc:IdentificationID"/></outsideAgencyORI>
        <!-- Element 30, Resident Status -->
        <residentStatus><xsl:value-of select="$person-node/j:PersonResidentCode"/></residentStatus>
        <!-- Element 23, Victim Sequence Number -->
        <sequenceNumber><xsl:value-of select="j:VictimSequenceNumberText"/></sequenceNumber>
        <!-- Element 25A, Type of Activity (Officer)/ Circumstance-->
        <typeOfActivity><xsl:value-of select="$enforcementOfficial/j:EnforcementOfficialActivityCategoryCode"/></typeOfActivity>
        <!-- Element 25, Type of Victim -->
        <victimType><xsl:value-of select="j:VictimCategoryCode"/></victimType>
        </nibrsVictims>
    </xsl:template>
    <xsl:template name="age">
        <xsl:param name="person-node"/>
        <xsl:variable name="max" select="$person-node/nc:PersonAgeMeasure/nc:MeasureIntegerRange/nc:RangeMaximumIntegerValue"/>
        <xsl:variable name="min" select="$person-node/nc:PersonAgeMeasure/nc:MeasureIntegerRange/nc:RangeMinimumIntegerValue"/>
        <xsl:variable name="int" select="$person-node/nc:PersonAgeMeasure/nc:MeasureIntegerValue"/>
        <xsl:variable name="text" select="$person-node/nc:PersonAgeMeasure/nc:MeasureValueText"/>
        <xsl:if test="$max">
            <ageMaximumValue><xsl:choose>
                <xsl:when test="string-length($max)=1"><xsl:value-of select="concat('0', $max)"/></xsl:when>
                <xsl:otherwise><xsl:value-of select="$max"/></xsl:otherwise>
            </xsl:choose></ageMaximumValue>
        </xsl:if>
        <xsl:if test="$min">
            <ageMinimumValue><xsl:choose>
                <xsl:when test="string-length($min)=1"><xsl:value-of select="concat('0', $min)"/></xsl:when>
                <xsl:otherwise><xsl:value-of select="$min"/></xsl:otherwise>
            </xsl:choose></ageMinimumValue>
        </xsl:if>
        <xsl:choose>
            <xsl:when test="$max and $min">
            <ageRange><xsl:choose>
                <xsl:when test="string-length($min)=1 and string-length($max)=1"><xsl:value-of select="concat('0', $min, '0', $max)"/></xsl:when>
                <xsl:when test="string-length($min)=1 and string-length($max)>1"><xsl:value-of select="concat('0', $min, $max)"/></xsl:when>
                <xsl:when test="string-length($min)>1 and string-length($max)=1"><xsl:value-of select="concat($min, '0', $max)"/></xsl:when>
                <xsl:otherwise><xsl:value-of select="concat($min, $max)"/></xsl:otherwise>
            </xsl:choose></ageRange>
        </xsl:when>
            <xsl:when test="$int"><ageRange><xsl:choose>
                <xsl:when test="string-length($int)=1"><xsl:value-of select="concat('0', $int)"/></xsl:when>
                <xsl:otherwise><xsl:value-of select="$int"/></xsl:otherwise>
            </xsl:choose></ageRange>
            </xsl:when>
        </xsl:choose>
        <xsl:if test="$text"><ageRange><xsl:choose>
            <xsl:when test="$text='NEONATAL'">NN</xsl:when>
            <xsl:when test="$text='NEWBORN'">NB</xsl:when>
            <xsl:when test="$text='BABY'">BB</xsl:when>
            <xsl:when test="$text='UNKNOWN'">00</xsl:when>
            <xsl:otherwise><xsl:value-of select="$text"/></xsl:otherwise>
        </xsl:choose></ageRange></xsl:if>
    </xsl:template>
    <xsl:template name="date">
        <xsl:param name="Date"/>
        <xsl:param name="DateTime"/>
        <xsl:param name="YearMonthDate"/>
        <xsl:param name="get"/>
        <xsl:choose>
            <xsl:when test="$get='month'">
                <xsl:choose>
                    <xsl:when test="$YearMonthDate">
                        <xsl:value-of select="substring-after($YearMonthDate, '-')"/>
                    </xsl:when>
                    <xsl:when test="$Date">
                        <xsl:value-of select="substring-before(substring-after($Date, '-'), '-')"/>
                    </xsl:when>
                    <xsl:when test="$DateTime">
                        <xsl:value-of select="substring-before(substring-after($DateTime, '-'), '-')"/>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="$get='year'">
                <xsl:choose>
                    <xsl:when test="$YearMonthDate">
                        <xsl:value-of select="substring-before($YearMonthDate, '-')"/>
                    </xsl:when>
                    <xsl:when test="$Date">
                        <xsl:value-of select="substring-before($Date, '-')"/>
                    </xsl:when>
                    <xsl:when test="$DateTime">
                        <xsl:value-of select="substring-before($DateTime, '-')"/>
                    </xsl:when>
                </xsl:choose> 
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template match="j:ArresteeArmedWithCode">
        <armedWith>
        <xsl:choose>
            <xsl:when test="contains(., 'A')">
                <automaticIndicator>A</automaticIndicator>
                <weaponCode><xsl:value-of select="substring-before(., 'A')"/></weaponCode>
            </xsl:when>
            <xsl:otherwise>
                <weaponCode><xsl:value-of select="."/></weaponCode>
            </xsl:otherwise>
        </xsl:choose>
        </armedWith>
    </xsl:template>
    <xsl:template match="j:OffenseFactorBiasMotivationCode">
        <biasMotivations><xsl:choose>
            <xsl:when test=".='ANTIWHITE'">11</xsl:when>
            <xsl:when test=".='ANTIBLACK_AFRICAN AMERICAN'">12</xsl:when>
            <xsl:when test=".='ANTIAMERICAN INDIAN_ ALASKAN NATIVE'">13</xsl:when> 
            <xsl:when test=".='ANTIASIAN'">14</xsl:when>
            <xsl:when test=".='ANTIMULTIRACIAL GROUP'">15</xsl:when>
            <xsl:when test=".='ANTINATIVEHAWAIIAN_OTHERPACIFICISLANDER'">16</xsl:when>
            <xsl:when test=".='ANTIJEWISH'">21</xsl:when>
            <xsl:when test=".='ANTICATHOLIC'">22</xsl:when>
            <xsl:when test=".='ANTIPROTESTANT'">23</xsl:when>
            <xsl:when test=".='ANTIISLAMIC'">24</xsl:when>
            <xsl:when test=".='ANTIOTHER RELIGION'">25</xsl:when>
            <xsl:when test=".='ANTIMULTIRELIGIOUS GROUP'">26</xsl:when>
            <xsl:when test=".='ANTIATHEIST_AGNOSTIC'">27</xsl:when>
            <xsl:when test=".='ANTIMORMON'">28</xsl:when>
            <xsl:when test=".='ANTIJEHOVAHWITNESS'">29</xsl:when>
            <xsl:when test=".='ANTIARAB'">31</xsl:when>
            <xsl:when test=".='ANTIHISPANIC_LATINO'">32</xsl:when>
            <xsl:when test=".='ANTIOTHER ETHNICITY_NATIONAL ORIGIN'">33</xsl:when>
            <xsl:when test=".='ANTIMALE HOMOSEXUAL'">41</xsl:when>
            <xsl:when test=".='ANTIFEMALE HOMOSEXUAL'">42</xsl:when>
            <xsl:when test=".='ANTIHETEROSEXUAL'">44</xsl:when>
            <xsl:when test=".='ANTIBISEXUAL'">45</xsl:when>
            <xsl:when test=".='ANTIPHYSICAL DISABILITY'">51</xsl:when>
            <xsl:when test=".='ANTIMENTAL DISABILITY'">52</xsl:when>
            <xsl:when test=".='ANTIMALE'">61</xsl:when>
            <xsl:when test=".='ANTIFEMALE'">62</xsl:when>
            <xsl:when test=".='ANTITRANSGENDER'">71</xsl:when>
            <xsl:when test=".='ANTIGENDER_NONCONFORMING'">72</xsl:when>
            <xsl:when test=".='ANTIEASTERNORTHODOX'">81</xsl:when>
            <xsl:when test=".='ANTIOTHER CHRISTIAN'">82</xsl:when>
            <xsl:when test=".='ANTIBUDDHIST'">83</xsl:when>
            <xsl:when test=".='ANTIHINDU'">84</xsl:when>
            <xsl:when test=".='ANTISIKH'">85</xsl:when>
            <xsl:when test=".='NONE'">88</xsl:when>
            <xsl:when test=".='UNKNOWN'">99</xsl:when>
            <xsl:otherwise><xsl:value-of select="."/></xsl:otherwise>
        </xsl:choose></biasMotivations>
    </xsl:template>
    <xsl:template match="*[local-name()='CriminalActivityCategoryCode']">
        <criminalActs><xsl:value-of select="."/></criminalActs>
    </xsl:template>
    <xsl:template match="j:OffenseFactor/j:OffenseFactorCode">
        <offenderSuspectedUsing><xsl:value-of select="."/></offenderSuspectedUsing>
    </xsl:template>
    <xsl:template match="j:OffenseForce/j:ForceCategoryCode">
        <typeWeaponForce>
        <xsl:choose>
            <xsl:when test="contains(., 'A')">
                <automaticIndicator>A</automaticIndicator>
                <weaponCode><xsl:value-of select="substring-before(., 'A')"/></weaponCode>
            </xsl:when>
            <xsl:otherwise>
                <weaponCode><xsl:value-of select="."/></weaponCode>
            </xsl:otherwise>
        </xsl:choose>
        </typeWeaponForce>
    </xsl:template>
    <xsl:template match="j:VictimAggravatedAssaultHomicideFactorCode">
        <aggAssaultHomicideCodes><xsl:value-of select="."/></aggAssaultHomicideCodes>
    </xsl:template>
    <xsl:template match="j:VictimInjury">
        <injuryCodes><xsl:value-of select="j:InjuryCategoryCode"/></injuryCodes>
    </xsl:template>
    <xsl:template match="nc:ActivityDate" mode="incidentDate">
        <xsl:choose>
            <xsl:when test="nc:Date">
                <incidentDate><xsl:value-of select="translate(nc:Date, '-', '')"/></incidentDate>
            </xsl:when>
            <xsl:when test="nc:DateTime">
                <incidentDate><xsl:value-of select="substring-before(translate(nc:DateTime, '-', ''), 'T')"/></incidentDate>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template match="nc:ActivityDate" mode="arrestDate">
        <xsl:choose>
            <xsl:when test="nc:Date">
                <arrestDate><xsl:value-of select="translate(nc:Date, '-', '')"/></arrestDate>
            </xsl:when>
            <xsl:when test="nc:DateTime">
                <arrestDate><xsl:value-of select="substring-before(translate(nc:DateTime, '-', ''), 'T')"/></arrestDate>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template match="j:IncidentExceptionalClearanceDate">
        <xsl:choose>
            <xsl:when test="nc:Date">
                <xsl:value-of select="translate(nc:Date, '-', '')"/>
            </xsl:when>
            <xsl:when test="nc:DateTime">
                <xsl:value-of select="substring-before(translate(nc:DateTime, '-', ''), 'T')"/>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template match="nc:ItemValueDate">
        <xsl:choose>
            <xsl:when test="nc:Date">
                <recoveredDate><xsl:value-of select="translate(nc:Date, '-', '')"/></recoveredDate>
            </xsl:when>
            <xsl:when test="nc:DateTime">
                <recoveredDate><xsl:value-of select="substring-before(translate(nc:DateTime, '-', ''), 'T')"/></recoveredDate>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
</xsl:stylesheet>