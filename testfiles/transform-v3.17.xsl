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
            &lt;soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ws="http://ws.nibrs.ucr.cjis.fbi.gov/"&gt;
            &lt;soapenv:Header/&gt;
            &lt;soapenv:Body&gt;
            &lt;ws:SubmitNibrsReport&gt;
            &lt;NibrsReport&gt;
            &lt;formatType&gt;XML&lt;/formatType&gt;
            <xsl:choose>
                <xsl:when test="*[local-name()='ReportHeader']/*[local-name()='NIBRSReportCategoryCode']='GROUP A INCIDENT REPORT'">
                    <xsl:apply-templates select="j:Arrestee" mode="GroupA"/>
                    <xsl:apply-templates select="nc:Incident"/>
                    <xsl:apply-templates select="j:Subject"/>
                    <xsl:apply-templates select="j:Offense"/>
                    <xsl:call-template name="nibrsProperties"/>
                    <xsl:apply-templates select="*[local-name()='ReportHeader']"/>
                    <xsl:apply-templates select="j:Victim"/>
                </xsl:when>
                <xsl:when test="*[local-name()='ReportHeader']/*[local-name()='NIBRSReportCategoryCode']='GROUP B ARREST REPORT'">
                    <xsl:apply-templates select="j:Arrestee" mode="GroupB"/>
                    <xsl:apply-templates select="nc:Incident"/>
                    <xsl:apply-templates select="j:Subject"/>
                    <xsl:apply-templates select="j:Offense"/>
                    <xsl:call-template name="nibrsProperties"/>
                    <xsl:apply-templates select="*[local-name()='ReportHeader']"/>
                    <xsl:apply-templates select="j:Victim"/>
                </xsl:when>
                <xsl:when test="*[local-name()='ReportHeader']/*[local-name()='NIBRSReportCategoryCode']='ZERO REPORT'">
                    <xsl:apply-templates select="*[local-name()='ReportHeader']"/>
                    &lt;zeroReport&gt;
                    <!-- Element 2, Incident Number -->
                    &lt;incidentNumber&gt;<xsl:value-of select="*[local-name()='ReportHeader']/*[local-name()='ReportingAgency']/j:OrganizationAugmentation/j:OrganizationORIIdentification/nc:IdentificationID"/>&lt;/incidentNumber&gt;
                    <!-- Zero Report Month -->
                    &lt;month&gt;<xsl:call-template name="date">
                        <xsl:with-param name="Date" select="*[local-name()='ReportHeader']/*[local-name()='ReportDate']/nc:Date"/>
                        <xsl:with-param name="DateTime" select="*[local-name()='ReportHeader']/*[local-name()='ReportDate']/nc:DateTime"/>
                        <xsl:with-param name="YearMonthDate" select="*[local-name()='ReportHeader']/*[local-name()='ReportDate']/nc:YearMonthDate"/>
                        <xsl:with-param name="get" select="'month'"/>
                    </xsl:call-template>&lt;/month&gt;
                    <!-- Zero Report Year -->
                    &lt;year&gt;<xsl:call-template name="date">
                        <xsl:with-param name="Date" select="*[local-name()='ReportHeader']/*[local-name()='ReportDate']/nc:Date"/>
                        <xsl:with-param name="DateTime" select="*[local-name()='ReportHeader']/*[local-name()='ReportDate']/nc:DateTime"/>
                        <xsl:with-param name="YearMonthDate" select="*[local-name()='ReportHeader']/*[local-name()='ReportDate']/nc:YearMonthDate"/>
                        <xsl:with-param name="get" select="'year'"/>
                    </xsl:call-template>&lt;/year&gt;
                    &lt;/zeroReport&gt;
                </xsl:when>
            </xsl:choose>
            &lt;/NibrsReport&gt;
            &lt;/ws:SubmitNibrsReport&gt;
            &lt;/soapenv:Body&gt;
            &lt;/soapenv:Envelope&gt;
        </xsl:for-each>
    </xsl:template>
    <xsl:template match="j:Arrestee" mode="GroupA">
        <xsl:variable name="person-node" select="../nc:Person[@s:id=current()/nc:RoleOfPerson/@s:ref]"/>
        <xsl:variable name="arrest-ref" select="../j:ArrestSubjectAssociation[j:Subject/@s:ref=current()/@s:id]/nc:Activity/@s:ref"/>
        <xsl:variable name="arrest" select="../j:Arrest[@s:id=$arrest-ref]"/>
        &lt;nibrsGroupAArrestees&gt;
        <!-- Element 47, Age of Arrestee (only one would be included per victim)-->
        <xsl:call-template name="age">
            <xsl:with-param name="person-node" select="$person-node"/>
        </xsl:call-template>
        <!-- Element 50, Ethnicity of Arrestee -->
        &lt;ethnicity&gt;<xsl:value-of select="$person-node/j:PersonEthnicityCode"/>&lt;/ethnicity&gt;
        <!-- Element 49, Race of Arrestee -->
        &lt;race&gt;<xsl:value-of select="$person-node/j:PersonRaceNDExCode"/>&lt;/race&gt;
        <!-- Element 48, Sex of Arrestee -->
        &lt;sex&gt;<xsl:value-of select="$person-node/j:PersonSexCode"/>&lt;/sex&gt;
        <!-- Element 46, Arrestee Was Armed With -->
        <xsl:apply-templates select="j:ArresteeArmedWithCode"/>
        <!-- Element 52, Disposition of Arrestee Under 18 -->
        &lt;juvenileDisposition&gt;<xsl:value-of select="j:ArresteeJuvenileDispositionCode"/>&lt;/juvenileDisposition&gt;
        &lt;nibrsArrest&gt;
        <!-- Element 45, UCR Arrest Offense Code -->
        &lt;arrestChargeUCRCode&gt;<xsl:value-of select="$arrest/j:ArrestCharge/*[local-name()='ChargeUCRCode']"/>&lt;/arrestChargeUCRCode&gt;
        <!-- Element 42, Arrest Date -->
        <xsl:apply-templates select="$arrest/nc:ActivityDate" mode="arrestDate"/>
        <!-- Element 41, Arrest Transaction Number -->
        &lt;arrestTransactionNumber&gt;<xsl:value-of select="$arrest/nc:ActivityIdentification/nc:IdentificationID"/>&lt;/arrestTransactionNumber&gt;
        <!-- Element 2, Incident Number -->
        &lt;incidentNumber&gt;<xsl:value-of select="../nc:Incident/nc:ActivityIdentification/nc:IdentificationID"/>&lt;/incidentNumber&gt;
        <!-- Element 43, Type Of Arrest -->
        &lt;typeOfArrest&gt;<xsl:value-of select="$arrest/j:ArrestCategoryCode"/>&lt;/typeOfArrest&gt;
        &lt;/nibrsArrest&gt;
        <!-- Element 51, Resident Status -->
        &lt;residentStatus&gt;<xsl:value-of select="$person-node/j:PersonResidentCode"/>&lt;/residentStatus&gt;
        <!-- Element 40, Arrestee Sequence Number -->
        &lt;sequenceNumber&gt;<xsl:value-of select="j:ArrestSequenceID"/>&lt;/sequenceNumber&gt;
        <!-- Element 44, Multiple Arrestee Segments Indicator -->
        &lt;multipleArresteeSegmentIndicator&gt;<xsl:value-of select="j:ArrestSubjectCountCode"/>&lt;/multipleArresteeSegmentIndicator&gt;
        &lt;/nibrsGroupAArrestees&gt;
    </xsl:template>
    <xsl:template match="j:Arrestee" mode="GroupB">
        <xsl:variable name="person-node" select="../nc:Person[@s:id=current()/nc:RoleOfPerson/@s:ref]"/>
        <xsl:variable name="arrest-ref" select="../j:ArrestSubjectAssociation[j:Subject/@s:ref=current()/@s:id]/nc:Activity/@s:ref"/>
        <xsl:variable name="arrest" select="../j:Arrest[@s:id=$arrest-ref]"/>
        &lt;nibrsGroupBArrestees&gt;
        <!-- Element 47, Age of Arrestee (only one would be included per victim)-->
        <xsl:call-template name="age">
            <xsl:with-param name="person-node" select="$person-node"/>
        </xsl:call-template>
        <!-- Element 50, Ethnicity of Arrestee -->
        &lt;ethnicity&gt;<xsl:value-of select="$person-node/j:PersonEthnicityCode"/>&lt;/ethnicity&gt;
        <!-- Element 49, Race of Arrestee -->
        &lt;race&gt;<xsl:value-of select="$person-node/j:PersonRaceNDExCode"/>&lt;/race&gt;
        <!-- Element 48, Sex of Arrestee -->
        &lt;sex&gt;<xsl:value-of select="$person-node/j:PersonSexCode"/>&lt;/sex&gt;
        <!-- Element 46, Arrestee Was Armed With -->
        <xsl:apply-templates select="j:ArresteeArmedWithCode"/>
        <!-- Element 52, Disposition of Arrestee Under 18 -->
        &lt;juvenileDisposition&gt;<xsl:value-of select="j:ArresteeJuvenileDispositionCode"/>&lt;/juvenileDisposition&gt;
        &lt;nibrsArrest&gt;
        <!-- Element 45, UCR Arrest Offense Code -->
        &lt;arrestChargeUCRCode&gt;<xsl:value-of select="$arrest/j:ArrestCharge/*[local-name()='ChargeUCRCode']"/>&lt;/arrestChargeUCRCode&gt;
        <!-- Element 42, Arrest Date -->
        <xsl:apply-templates select="$arrest/nc:ActivityDate" mode="arrestDate"/>
        <!-- Element 41, Arrest Transaction Number -->
        &lt;arrestTransactionNumber&gt;<xsl:value-of select="$arrest/nc:ActivityIdentification/nc:IdentificationID"/>&lt;/arrestTransactionNumber&gt;
        <!-- Element 2, Incident Number -->
        <!--&lt;incidentNumber&gt;<xsl:value-of select="../nc:Incident/nc:ActivityIdentification/nc:IdentificationID"/>&lt;/incidentNumber&gt;-->
        <!-- Element 43, Type Of Arrest -->
        &lt;typeOfArrest&gt;<xsl:value-of select="$arrest/j:ArrestCategoryCode"/>&lt;/typeOfArrest&gt;
        &lt;/nibrsArrest&gt;
        <!-- Element 51, Resident Status -->
        &lt;residentStatus&gt;<xsl:value-of select="$person-node/j:PersonResidentCode"/>&lt;/residentStatus&gt;
        <!-- Element 40, Arrestee Sequence Number -->
        &lt;sequenceNumber&gt;<xsl:value-of select="j:ArrestSequenceID"/>&lt;/sequenceNumber&gt;
        &lt;/nibrsGroupBArrestees&gt;
    </xsl:template>
    <xsl:template match="nc:Incident">
        &lt;nibrsIncident&gt;
        <!-- Element 2A, Cargo Theft Indicator: True/False-->
        &lt;cargoTheftIndicator&gt;<xsl:choose>
            <xsl:when test="cjis:IncidentAugmentation/j:OffenseCargoTheftIndicator='true'">Y</xsl:when>
            <xsl:when test="cjis:IncidentAugmentation/j:OffenseCargoTheftIndicator='false'">N</xsl:when>
        </xsl:choose>&lt;/cargoTheftIndicator&gt;
        <!--&lt;exceptionalClearanceCodes&gt;&lt;/exceptionalClearanceCodes&gt;-->
        <!-- Element 5, Exceptional Clearance Date -->
        &lt;exceptionalClearanceDate&gt;<xsl:apply-templates select="j:IncidentAugmentation/j:IncidentExceptionalClearanceDate"/>&lt;/exceptionalClearanceDate&gt;
        <!-- Element 4, Cleared Exceptionally -->
        &lt;exceptionallyClearedFlag&gt;<xsl:value-of select="j:IncidentAugmentation/j:IncidentExceptionalClearanceCode"/>&lt;/exceptionallyClearedFlag&gt;
        <!-- Element 3, Incident Date -->
        <xsl:apply-templates select="nc:ActivityDate" mode="incidentDate"/>
        <!-- Element 3, Incident Hour -->
        &lt;incidentHour&gt;<xsl:value-of select="substring-before(substring-after(nc:ActivityDate/nc:DateTime, 'T'), ':')"/>&lt;/incidentHour&gt;
        <!-- Element 2, Incident Number -->
        &lt;incidentNumber&gt;<xsl:value-of select="nc:ActivityIdentification/nc:IdentificationID"/>&lt;/incidentNumber&gt;
        <!-- Element 3, IncidentReportDateIndicator is true to designate that the ActivityDate is the Report Date rather than the Incident Date -->
        &lt;reportDateIndicator&gt;<xsl:if test="cjis:IncidentAugmentation/cjis:IncidentReportDateIndicator='true'">R</xsl:if>&lt;/reportDateIndicator&gt;
        &lt;/nibrsIncident&gt;
    </xsl:template>
    <xsl:template match="j:Subject">
        <xsl:variable name="person-node" select="../nc:Person[@s:id=current()/nc:RoleOfPerson/@s:ref]"/>
        &lt;nibrsOffenders&gt;
        <!-- Element 37, Age of Subject (only one would be included per subject)-->
        <xsl:call-template name="age">
            <xsl:with-param name="person-node" select="$person-node"/>
        </xsl:call-template>
        <!-- Element 39A, Ethnicity of Subject -->
        &lt;ethnicity&gt;<xsl:value-of select="$person-node/j:PersonEthnicityCode"/>&lt;/ethnicity&gt;
        <!-- Element 39, Race of Subject -->
        &lt;race&gt;<xsl:value-of select="$person-node/j:PersonRaceNDExCode"/>&lt;/race&gt;
        <!-- Element 38, Sex of Subject -->
        &lt;sex&gt;<xsl:value-of select="$person-node/j:PersonSexCode"/>&lt;/sex&gt;
        <!-- Element 36, Offender Sequence Number -->
        &lt;sequenceNumber&gt;<xsl:value-of select="j:SubjectSequenceNumberText"/>&lt;/sequenceNumber&gt;
        &lt;/nibrsOffenders&gt;
    </xsl:template>
    <xsl:template match="j:Offense">
        <xsl:variable name="loc-ref" select="../j:OffenseLocationAssociation[j:Offense/@s:ref=current()/@s:id]/nc:Location/@s:ref"/>
        &lt;nibrsOffenses&gt;
        <!-- Element 8A, Bias Motivation -->
        <xsl:apply-templates select="j:OffenseFactorBiasMotivationCode"/>
        <!-- Element 12, Type Criminal Activity/Gang Information -->
        <xsl:apply-templates select="*[local-name()='CriminalActivityCategoryCode']"/>
        <!-- Element 9, Location Type -->
        &lt;locationType&gt;<xsl:value-of select="../nc:Location[@s:id=$loc-ref]/*[local-name()='LocationCategoryCode']"/>&lt;/locationType&gt;
        <!-- Element 11, Method of Entry -->
        &lt;methodOfEntry&gt;<xsl:value-of select="j:OffenseEntryPoint/j:PassagePointMethodCode"/>&lt;/methodOfEntry&gt;
        &lt;numberPremisesEntered&gt;<xsl:value-of select="j:OffenseStructuresEnteredQuantity"/>&lt;/numberPremisesEntered&gt;
        <!-- Element 8, Offender(s) Suspected Of Using  -->
        <xsl:apply-templates select="j:OffenseFactor/j:OffenseFactorCode"/>
        <!-- Element 7, Attempted/Completed -->
        &lt;offenseAttemptedIndicator&gt;<xsl:choose>
            <xsl:when test="j:OffenseAttemptedIndicator='true'">A</xsl:when>
            <xsl:when test="j:OffenseAttemptedIndicator='false'">C</xsl:when>
        </xsl:choose>&lt;/offenseAttemptedIndicator&gt;
        <!-- Element 13, Type Weapon/Force Involved -->
        <xsl:apply-templates select="j:OffenseForce/j:ForceCategoryCode"/>
        <!-- Element 6, Offense Code -->
        &lt;UCROffenseCode&gt;<xsl:value-of select="*[local-name()='OffenseUCRCode']"/>&lt;/UCROffenseCode&gt;
        &lt;/nibrsOffenses&gt;
    </xsl:template>
    <xsl:template name="nibrsProperties">
        <xsl:variable name="blank" select="nc:Item[not(nc:ItemStatus/cjis:ItemStatusCode)]"/>
        <xsl:variable name="none" select="nc:Item[nc:ItemStatus/cjis:ItemStatusCode='NONE']"/>
        <xsl:variable name="burned" select="nc:Item[nc:ItemStatus/cjis:ItemStatusCode='BURNED']"/>
        <xsl:variable name="counterfeited" select="nc:Item[nc:ItemStatus/cjis:ItemStatusCode='COUNTERFEITED']"/>
        <xsl:variable name="destroyed" select="nc:Item[nc:ItemStatus/cjis:ItemStatusCode='DESTROYED_DAMAGED_VANDALIZED' 
            or nc:ItemStatus/cjis:ItemStatusCode='DESTROYED' 
            or nc:ItemStatus/cjis:ItemStatusCode='DAMAGED' 
            or nc:ItemStatus/cjis:ItemStatusCode='VANDALIZED']"/>
        <xsl:variable name="recovered" select="nc:Item[nc:ItemStatus/cjis:ItemStatusCode='RECOVERED']"/>
        <xsl:variable name="seized" select="nc:Item[nc:ItemStatus/cjis:ItemStatusCode='SEIZED']"/>
        <xsl:variable name="stolen" select="nc:Item[nc:ItemStatus/cjis:ItemStatusCode='STOLEN'
            or nc:ItemStatus/cjis:ItemStatusCode='STOLEN_BRIBED'
            or nc:ItemStatus/cjis:ItemStatusCode='STOLEN_DEFRAUDED'
            or nc:ItemStatus/cjis:ItemStatusCode='STOLEN_EMBEZZLED'
            or nc:ItemStatus/cjis:ItemStatusCode='STOLEN_EXTORTED'
            or nc:ItemStatus/cjis:ItemStatusCode='STOLEN_RANSOMED' 
            or nc:ItemStatus/cjis:ItemStatusCode='STOLEN_ROBBED']"/>
        <xsl:variable name="unknown" select="nc:Item[nc:ItemStatus/cjis:ItemStatusCode='UNKNOWN']"/>
        <xsl:variable name="wrong" select="nc:Item[nc:ItemStatus/cjis:ItemStatusCode 
            and nc:ItemStatus/cjis:ItemStatusCode!='NONE' 
            and nc:ItemStatus/cjis:ItemStatusCode!='BURNED' 
            and nc:ItemStatus/cjis:ItemStatusCode!='COUNTERFEITED' 
            and nc:ItemStatus/cjis:ItemStatusCode!='DESTROYED_DAMAGED_VANDALIZED' 
            and nc:ItemStatus/cjis:ItemStatusCode!='DAMAGED' 
            and nc:ItemStatus/cjis:ItemStatusCode!='DESTROYED' 
            and nc:ItemStatus/cjis:ItemStatusCode!='VANDALIZED' 
            and nc:ItemStatus/cjis:ItemStatusCode!='DESTROYED_DAMAGED_VANDALIZED' 
            and nc:ItemStatus/cjis:ItemStatusCode!='RECOVERED' 
            and nc:ItemStatus/cjis:ItemStatusCode!='SEIZED' 
            and nc:ItemStatus/cjis:ItemStatusCode!='STOLEN' 
            and nc:ItemStatus/cjis:ItemStatusCode!='STOLEN_BRIBED' 
            and nc:ItemStatus/cjis:ItemStatusCode!='STOLEN_DEFRAUDED' 
            and nc:ItemStatus/cjis:ItemStatusCode!='STOLEN_EMBEZZLED' 
            and nc:ItemStatus/cjis:ItemStatusCode!='STOLEN_EXTORTED' 
            and nc:ItemStatus/cjis:ItemStatusCode!='STOLEN_RANSOMED' 
            and nc:ItemStatus/cjis:ItemStatusCode!='STOLEN_ROBBED' 
            and nc:ItemStatus/cjis:ItemStatusCode!='UNKNOWN']"/>    
        <xsl:if test="$blank">
            &lt;nibrsProperties&gt;
            <!-- Element 2, Incident Number -->
            &lt;incidentNumber&gt;<xsl:value-of select="nc:Incident/nc:ActivityIdentification/nc:IdentificationID"/>&lt;/incidentNumber&gt;
            <xsl:apply-templates select="$blank"/>
            &lt;/nibrsProperties&gt;
        </xsl:if>       
        <xsl:if test="$none">
            &lt;nibrsProperties&gt;
            <!-- Element 2, Incident Number -->
            &lt;incidentNumber&gt;<xsl:value-of select="nc:Incident/nc:ActivityIdentification/nc:IdentificationID"/>&lt;/incidentNumber&gt;
            <xsl:apply-templates select="$none"/>
            <!-- Element 14, Type Property Loss/etc Substituted for nc:ItemStatus -->
            &lt;typePropertyLossCode&gt;1&lt;/typePropertyLossCode&gt;
            &lt;/nibrsProperties&gt;
        </xsl:if>      
        <xsl:if test="$burned">
            &lt;nibrsProperties&gt;
            <!-- Element 2, Incident Number -->
            &lt;incidentNumber&gt;<xsl:value-of select="nc:Incident/nc:ActivityIdentification/nc:IdentificationID"/>&lt;/incidentNumber&gt;
            <xsl:apply-templates select="$burned"/>
            <!-- Element 14, Type Property Loss/etc Substituted for nc:ItemStatus -->
            &lt;typePropertyLossCode&gt;2&lt;/typePropertyLossCode&gt;
            &lt;/nibrsProperties&gt;
        </xsl:if>
        <xsl:if test="$counterfeited">
            &lt;nibrsProperties&gt;
            <!-- Element 2, Incident Number -->
            &lt;incidentNumber&gt;<xsl:value-of select="nc:Incident/nc:ActivityIdentification/nc:IdentificationID"/>&lt;/incidentNumber&gt;
            <xsl:apply-templates select="$counterfeited"/>
            <!-- Element 14, Type Property Loss/etc Substituted for nc:ItemStatus -->
            &lt;typePropertyLossCode&gt;3&lt;/typePropertyLossCode&gt;
            &lt;/nibrsProperties&gt;
        </xsl:if>
        <xsl:if test="$destroyed">
            &lt;nibrsProperties&gt;
            <!-- Element 2, Incident Number -->
            &lt;incidentNumber&gt;<xsl:value-of select="nc:Incident/nc:ActivityIdentification/nc:IdentificationID"/>&lt;/incidentNumber&gt;
            <xsl:apply-templates select="$destroyed"/>
            <!-- Element 14, Type Property Loss/etc Substituted for nc:ItemStatus -->
            &lt;typePropertyLossCode&gt;4&lt;/typePropertyLossCode&gt;
            &lt;/nibrsProperties&gt;
        </xsl:if>
        <xsl:if test="$recovered">
            &lt;nibrsProperties&gt;
            <!-- Element 2, Incident Number -->
            &lt;incidentNumber&gt;<xsl:value-of select="nc:Incident/nc:ActivityIdentification/nc:IdentificationID"/>&lt;/incidentNumber&gt;
            <xsl:apply-templates select="$recovered"/>
            <xsl:if test="j:Offense/*[local-name()='OffenseUCRCode']='240' and j:Offense/j:OffenseAttemptedIndicator='false' and ($recovered/j:ItemCategoryNIBRSPropertyCategoryCode='03' or $recovered/j:ItemCategoryNIBRSPropertyCategoryCode='05' or $recovered/j:ItemCategoryNIBRSPropertyCategoryCode='24' or $recovered/j:ItemCategoryNIBRSPropertyCategoryCode='28' or $recovered/j:ItemCategoryNIBRSPropertyCategoryCode='37')">
                <!-- Element 19, Number of Recovered Motor Vehicles, if Status is Recovered -->
                &lt;recoveredVehicleQuantity&gt;<xsl:call-template name="vehicleQuantity"><xsl:with-param name="items" select="$recovered"/><xsl:with-param name="position" select="1"/><xsl:with-param name="count" select="0"/></xsl:call-template>&lt;/recoveredVehicleQuantity&gt;
            </xsl:if>
            <!-- Element 14, Type Property Loss/etc Substituted for nc:ItemStatus -->
            &lt;typePropertyLossCode&gt;5&lt;/typePropertyLossCode&gt;
            &lt;/nibrsProperties&gt;
        </xsl:if>
        <xsl:if test="$seized">
            &lt;nibrsProperties&gt;
            <!-- Element 2, Incident Number -->
            &lt;incidentNumber&gt;<xsl:value-of select="nc:Incident/nc:ActivityIdentification/nc:IdentificationID"/>&lt;/incidentNumber&gt;
            <xsl:apply-templates select="$seized"/>
            <!-- Element 14, Type Property Loss/etc Substituted for nc:ItemStatus -->
            &lt;typePropertyLossCode&gt;6&lt;/typePropertyLossCode&gt;
            &lt;/nibrsProperties&gt;
        </xsl:if>
        <xsl:if test="$stolen">
            &lt;nibrsProperties&gt;
            <!-- Element 2, Incident Number -->
            &lt;incidentNumber&gt;<xsl:value-of select="nc:Incident/nc:ActivityIdentification/nc:IdentificationID"/>&lt;/incidentNumber&gt;
            <xsl:apply-templates select="$stolen"/>
            <xsl:if test="j:Offense/*[local-name()='OffenseUCRCode']='240' and j:Offense/j:OffenseAttemptedIndicator='false' and ($stolen/j:ItemCategoryNIBRSPropertyCategoryCode='03' or $stolen/j:ItemCategoryNIBRSPropertyCategoryCode='05' or $stolen/j:ItemCategoryNIBRSPropertyCategoryCode='24' or $stolen/j:ItemCategoryNIBRSPropertyCategoryCode='28' or $stolen/j:ItemCategoryNIBRSPropertyCategoryCode='37')">
                <!-- Element 18, Number of Stolen Motor Vehicles, if Status is Stolen -->
                &lt;stolenVehicleQuantity&gt;<xsl:call-template name="vehicleQuantity"><xsl:with-param name="items" select="$stolen"/><xsl:with-param name="position" select="1"/><xsl:with-param name="count" select="0"/></xsl:call-template>&lt;/stolenVehicleQuantity&gt;
            </xsl:if>
            <!-- Element 14, Type Property Loss/etc Substituted for nc:ItemStatus -->
            &lt;typePropertyLossCode&gt;7&lt;/typePropertyLossCode&gt;
            &lt;/nibrsProperties&gt;
        </xsl:if>
        <xsl:if test="$unknown">
            &lt;nibrsProperties&gt;
            <!-- Element 2, Incident Number -->
            &lt;incidentNumber&gt;<xsl:value-of select="nc:Incident/nc:ActivityIdentification/nc:IdentificationID"/>&lt;/incidentNumber&gt;
            <xsl:apply-templates select="$unknown"/>
            <!-- Element 14, Type Property Loss/etc Substituted for nc:ItemStatus -->
            &lt;typePropertyLossCode&gt;8&lt;/typePropertyLossCode&gt;
            &lt;/nibrsProperties&gt;
        </xsl:if>
        <xsl:if test="$wrong">
            &lt;nibrsProperties&gt;
            <!-- Element 2, Incident Number -->
            &lt;incidentNumber&gt;<xsl:value-of select="nc:Incident/nc:ActivityIdentification/nc:IdentificationID"/>&lt;/incidentNumber&gt;
            <xsl:apply-templates select="$wrong"/>
            <!-- Element 14, Type Property Loss/etc Substituted for nc:ItemStatus -->
            &lt;typePropertyLossCode&gt;<xsl:value-of select="$wrong/nc:ItemStatus/cjis:ItemStatusCode"/>&lt;/typePropertyLossCode&gt;
            &lt;/nibrsProperties&gt;
        </xsl:if>
        
        <xsl:variable name="none_S" select="nc:Substance[nc:ItemStatus/cjis:ItemStatusCode='NONE']"/>
        <xsl:variable name="burned_S" select="nc:Substance[nc:ItemStatus/cjis:ItemStatusCode='BURNED']"/>
        <xsl:variable name="counterfeited_S" select="nc:Substance[nc:ItemStatus/cjis:ItemStatusCode='COUNTERFEITED']"/>
        <xsl:variable name="destroyed_S" select="nc:Substance[nc:ItemStatus/cjis:ItemStatusCode='DESTROYED_DAMAGED_VANDALIZED' 
            or nc:ItemStatus/cjis:ItemStatusCode='DESTROYED' 
            or nc:ItemStatus/cjis:ItemStatusCode='DAMAGED' 
            or nc:ItemStatus/cjis:ItemStatusCode='VANDALIZED']"/>
        <xsl:variable name="recovered_S" select="nc:Substance[nc:ItemStatus/cjis:ItemStatusCode='RECOVERED']"/>
        <xsl:variable name="seized_S" select="nc:Substance[not(nc:ItemStatus/cjis:ItemStatusCode) 
            or nc:ItemStatus/cjis:ItemStatusCode='SEIZED']"/>
        <xsl:variable name="stolen_S" select="nc:Substance[nc:ItemStatus/cjis:ItemStatusCode='STOLEN'
            or nc:ItemStatus/cjis:ItemStatusCode='STOLEN_BRIBED'
            or nc:ItemStatus/cjis:ItemStatusCode='STOLEN_DEFRAUDED'
            or nc:ItemStatus/cjis:ItemStatusCode='STOLEN_EMBEZZLED'
            or nc:ItemStatus/cjis:ItemStatusCode='STOLEN_EXTORTED'
            or nc:ItemStatus/cjis:ItemStatusCode='STOLEN_RANSOMED' 
            or nc:ItemStatus/cjis:ItemStatusCode='STOLEN_ROBBED']"/>
        <xsl:variable name="unknown_S" select="nc:Substance[nc:ItemStatus/cjis:ItemStatusCode='UNKNOWN']"/>
        <xsl:variable name="wrong_S" select="nc:Substance[nc:ItemStatus/cjis:ItemStatusCode 
            and nc:ItemStatus/cjis:ItemStatusCode!='NONE' 
            and nc:ItemStatus/cjis:ItemStatusCode!='BURNED' 
            and nc:ItemStatus/cjis:ItemStatusCode!='COUNTERFEITED' 
            and nc:ItemStatus/cjis:ItemStatusCode!='DESTROYED_DAMAGED_VANDALIZED' 
            and nc:ItemStatus/cjis:ItemStatusCode!='DAMAGED' 
            and nc:ItemStatus/cjis:ItemStatusCode!='DESTROYED' 
            and nc:ItemStatus/cjis:ItemStatusCode!='VANDALIZED' 
            and nc:ItemStatus/cjis:ItemStatusCode!='DESTROYED_DAMAGED_VANDALIZED' 
            and nc:ItemStatus/cjis:ItemStatusCode!='RECOVERED'
            and nc:ItemStatus/cjis:ItemStatusCode!='SEIZED' 
            and nc:ItemStatus/cjis:ItemStatusCode!='STOLEN' 
            and nc:ItemStatus/cjis:ItemStatusCode!='STOLEN_BRIBED' 
            and nc:ItemStatus/cjis:ItemStatusCode!='STOLEN_DEFRAUDED' 
            and nc:ItemStatus/cjis:ItemStatusCode!='STOLEN_EMBEZZLED' 
            and nc:ItemStatus/cjis:ItemStatusCode!='STOLEN_EXTORTED' 
            and nc:ItemStatus/cjis:ItemStatusCode!='STOLEN_RANSOMED' 
            and nc:ItemStatus/cjis:ItemStatusCode!='STOLEN_ROBBED' 
            and nc:ItemStatus/cjis:ItemStatusCode!='UNKNOWN']"/>        
        <xsl:if test="$none_S">
            &lt;nibrsProperties&gt;
            <!-- Element 2, Incident Number -->
            &lt;incidentNumber&gt;<xsl:value-of select="nc:Incident/nc:ActivityIdentification/nc:IdentificationID"/>&lt;/incidentNumber&gt;
            <xsl:apply-templates select="$none_S"/>
            <!-- Element 14, Type Property Loss/etc Substituted for nc:ItemStatus -->
            &lt;typePropertyLossCode&gt;1&lt;/typePropertyLossCode&gt;
            &lt;/nibrsProperties&gt;
        </xsl:if>      
        <xsl:if test="$burned_S">
            &lt;nibrsProperties&gt;
            <!-- Element 2, Incident Number -->
            &lt;incidentNumber&gt;<xsl:value-of select="nc:Incident/nc:ActivityIdentification/nc:IdentificationID"/>&lt;/incidentNumber&gt;
            <xsl:apply-templates select="$burned_S"/>
            <!-- Element 14, Type Property Loss/etc Substituted for nc:ItemStatus -->
            &lt;typePropertyLossCode&gt;2&lt;/typePropertyLossCode&gt;
            &lt;/nibrsProperties&gt;
        </xsl:if>
        <xsl:if test="$counterfeited_S">
            &lt;nibrsProperties&gt;
            <!-- Element 2, Incident Number -->
            &lt;incidentNumber&gt;<xsl:value-of select="nc:Incident/nc:ActivityIdentification/nc:IdentificationID"/>&lt;/incidentNumber&gt;
            <xsl:apply-templates select="$counterfeited_S"/>
            <!-- Element 14, Type Property Loss/etc Substituted for nc:ItemStatus -->
            &lt;typePropertyLossCode&gt;3&lt;/typePropertyLossCode&gt;
            &lt;/nibrsProperties&gt;
        </xsl:if>
        <xsl:if test="$destroyed_S">
            &lt;nibrsProperties&gt;
            <!-- Element 2, Incident Number -->
            &lt;incidentNumber&gt;<xsl:value-of select="nc:Incident/nc:ActivityIdentification/nc:IdentificationID"/>&lt;/incidentNumber&gt;
            <xsl:apply-templates select="$destroyed_S"/>
            <!-- Element 14, Type Property Loss/etc Substituted for nc:ItemStatus -->
            &lt;typePropertyLossCode&gt;4&lt;/typePropertyLossCode&gt;
            &lt;/nibrsProperties&gt;
        </xsl:if>
        <xsl:if test="$recovered_S">
            &lt;nibrsProperties&gt;
            <!-- Element 2, Incident Number -->
            &lt;incidentNumber&gt;<xsl:value-of select="nc:Incident/nc:ActivityIdentification/nc:IdentificationID"/>&lt;/incidentNumber&gt;
            <xsl:apply-templates select="$recovered_S"/>
            <!-- Element 14, Type Property Loss/etc Substituted for nc:ItemStatus -->
            &lt;typePropertyLossCode&gt;5&lt;/typePropertyLossCode&gt;
            &lt;/nibrsProperties&gt;
        </xsl:if>
        <xsl:if test="$seized_S">
            &lt;nibrsProperties&gt;
            <!-- Element 2, Incident Number -->
            &lt;incidentNumber&gt;<xsl:value-of select="nc:Incident/nc:ActivityIdentification/nc:IdentificationID"/>&lt;/incidentNumber&gt;
            <xsl:apply-templates select="$seized_S"/>
            <!-- Element 14, Type Property Loss/etc Substituted for nc:ItemStatus -->
            &lt;typePropertyLossCode&gt;6&lt;/typePropertyLossCode&gt;
            &lt;/nibrsProperties&gt;
        </xsl:if>
        <xsl:if test="$stolen_S">
            &lt;nibrsProperties&gt;
            <!-- Element 2, Incident Number -->
            &lt;incidentNumber&gt;<xsl:value-of select="nc:Incident/nc:ActivityIdentification/nc:IdentificationID"/>&lt;/incidentNumber&gt;
            <xsl:apply-templates select="$stolen_S"/>
            <!-- Element 14, Type Property Loss/etc Substituted for nc:ItemStatus -->
            &lt;typePropertyLossCode&gt;7&lt;/typePropertyLossCode&gt;
            &lt;/nibrsProperties&gt;
        </xsl:if>
        <xsl:if test="$unknown_S">
            &lt;nibrsProperties&gt;
            <!-- Element 2, Incident Number -->
            &lt;incidentNumber&gt;<xsl:value-of select="nc:Incident/nc:ActivityIdentification/nc:IdentificationID"/>&lt;/incidentNumber&gt;
            <xsl:apply-templates select="$unknown_S"/>
            <!-- Element 14, Type Property Loss/etc Substituted for nc:ItemStatus -->
            &lt;typePropertyLossCode&gt;8&lt;/typePropertyLossCode&gt;
            &lt;/nibrsProperties&gt;
        </xsl:if>
        <xsl:if test="$wrong_S">
            &lt;nibrsProperties&gt;
            <!-- Element 2, Incident Number -->
            &lt;incidentNumber&gt;<xsl:value-of select="nc:Incident/nc:ActivityIdentification/nc:IdentificationID"/>&lt;/incidentNumber&gt;
            <xsl:apply-templates select="$wrong_S"/>
            <!-- Element 14, Type Property Loss/etc Substituted for nc:ItemStatus -->
            &lt;typePropertyLossCode&gt;<xsl:value-of select="$wrong_S/nc:ItemStatus/cjis:ItemStatusCode"/>&lt;/typePropertyLossCode&gt;
            &lt;/nibrsProperties&gt;
        </xsl:if>
    </xsl:template>
    <xsl:template match="nc:Item">
        &lt;nibrsPropertyItems&gt;
        <!-- Element 16, Value of Property in US Dollars -->
        &lt;amount&gt;<xsl:value-of select="nc:ItemValue/nc:ItemValueAmount"/>&lt;/amount&gt;
        <!-- Element 15, Property Description -->
        &lt;categoryCode&gt;<xsl:value-of select="j:ItemCategoryNIBRSPropertyCategoryCode"/>&lt;/categoryCode&gt;
        <!-- Element 17, Date Recovered -->
        <xsl:apply-templates select="nc:ItemValue/nc:ItemValueDate"/>
        &lt;/nibrsPropertyItems&gt;
    </xsl:template>
    <xsl:template match="nc:Substance">
        &lt;itemSubstances&gt;
        <!-- Element 21, Estimated Quantity -->
        &lt;estimatedDrugQuantity&gt;<xsl:value-of select="nc:SubstanceQuantityMeasure/nc:MeasureDecimalValue"/>&lt;/estimatedDrugQuantity&gt;
        <!-- Element 20, Suspected Involved Drug Type -->
        &lt;suspectedDrugType&gt;<xsl:value-of select="j:DrugCategoryCode"/>&lt;/suspectedDrugType&gt;
        <!-- Element 22, Fraction -->
        &lt;typeDrugMeasurement&gt;<xsl:value-of select="nc:SubstanceQuantityMeasure/j:SubstanceUnitCode"/>&lt;/typeDrugMeasurement&gt;
        &lt;/itemSubstances&gt;
        &lt;nibrsPropertyItemsS&gt;
        <!-- Element 16, Value of Property in US Dollars -->
        &lt;amount&gt;<xsl:value-of select="nc:ItemValue/nc:ItemValueAmount"/>&lt;/amount&gt;
        <!-- Element 15, Property Description -->
        &lt;categoryCode&gt;<xsl:value-of select="j:ItemCategoryNIBRSPropertyCategoryCode"/>&lt;/categoryCode&gt;
        <!-- Element 17, Date Recovered -->
        <xsl:apply-templates select="nc:ItemValue/nc:ItemValueDate"/>
        &lt;/nibrsPropertyItemsS&gt;
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
    <xsl:template match="*[local-name()='ReportHeader']">
        &lt;nibrsReportHeader&gt;
        &lt;actionType&gt;<xsl:value-of select="*[local-name()='ReportActionCategoryCode']"/>&lt;/actionType&gt;
        <!-- Element 1, ORI Code -->
        &lt;agencyORI&gt;<xsl:value-of select="*[local-name()='ReportingAgency']/j:OrganizationAugmentation/j:OrganizationORIIdentification/nc:IdentificationID"/>&lt;/agencyORI&gt;
        &lt;reportDateMonth&gt;<xsl:call-template name="date">
            <xsl:with-param name="get" select="'month'"/>
            <xsl:with-param name="Date" select="*[local-name()='ReportDate']/nc:Date"/>
            <xsl:with-param name="DateTime" select="*[local-name()='ReportDate']/nc:DateTime"/>
            <xsl:with-param name="YearMonthDate" select="*[local-name()='ReportDate']/nc:YearMonthDate"/>
        </xsl:call-template>&lt;/reportDateMonth&gt;
        &lt;reportDateYear&gt;<xsl:call-template name="date">
            <xsl:with-param name="get" select="'year'"/>
            <xsl:with-param name="Date" select="*[local-name()='ReportDate']/nc:Date"/>
            <xsl:with-param name="DateTime" select="*[local-name()='ReportDate']/nc:DateTime"/>
            <xsl:with-param name="YearMonthDate" select="*[local-name()='ReportDate']/nc:YearMonthDate"/>
        </xsl:call-template>&lt;/reportDateYear&gt;
        &lt;segmentLevel&gt;<xsl:choose>
            <xsl:when test="*[local-name()='NIBRSReportCategoryCode']='GROUP A INCIDENT REPORT'">1</xsl:when>
            <xsl:when test="*[local-name()='NIBRSReportCategoryCode']='GROUP B ARREST REPORT'">7</xsl:when>
            <xsl:when test="*[local-name()='NIBRSReportCategoryCode']='ZERO REPORT'">0</xsl:when>
        </xsl:choose>&lt;/segmentLevel&gt;
        &lt;/nibrsReportHeader&gt; 
    </xsl:template>
    <xsl:template match="j:Victim">
        <xsl:variable name="person-node" select="../nc:Person[@s:id=current()/nc:RoleOfPerson/@s:ref]"/>
        <xsl:variable name="enforcementOfficial" select="../j:EnforcementOfficial[nc:RoleOfPerson/@s:ref=current()/nc:RoleOfPerson/@s:ref]"/>
        &lt;nibrsVictims&gt;
        <!-- Element 26, Age of Victim (only one would be included per victim)-->
        <xsl:call-template name="age">
            <xsl:with-param name="person-node" select="$person-node"/>
        </xsl:call-template>
        <!-- Element 29, Ethnicity of Victim -->
        &lt;ethnicity&gt;<xsl:value-of select="$person-node/j:PersonEthnicityCode"/>&lt;/ethnicity&gt;
        <!-- Element 28, Race of Victim -->
        &lt;race&gt;<xsl:value-of select="$person-node/j:PersonRaceNDExCode"/>&lt;/race&gt;
        <!-- Element 27, Sex of Victim -->
        &lt;sex&gt;<xsl:value-of select="$person-node/j:PersonSexCode"/>&lt;/sex&gt;
        <!-- Element 31, Aggravated Assault/Homicide Circumstances -->
        <xsl:apply-templates select="j:VictimAggravatedAssaultHomicideFactorCode"/>
        <!-- Element 25B, Assignment Type (Officer) -->
        &lt;assignmentType&gt;<xsl:value-of select="$enforcementOfficial/j:EnforcementOfficialAssignmentCategoryCode"/>&lt;/assignmentType&gt;
        <xsl:for-each select="../j:OffenseVictimAssociation[j:Victim/@s:ref=current()/@s:id]">
            <xsl:variable name="offense" select="../j:Offense[@s:id=current()/j:Offense/@s:ref]"/>
            <!-- Element 24, Victim Connected to UCR Offense Code -->
            &lt;connectedToUCROffenseCodes&gt;<xsl:value-of select="$offense/*[local-name()='OffenseUCRCode']"/>&lt;/connectedToUCROffenseCodes&gt;
        </xsl:for-each>
        <!-- Element 33, Type Injury -->
        <xsl:apply-templates select="j:VictimInjury"/>
        <!-- Element 32, Additional Justifiable Homicide Circumstances -->
        &lt;justifiableHomicideCode&gt;<xsl:value-of select="j:VictimJustifiableHomicideFactorCode"/>&lt;/justifiableHomicideCode&gt;
        <xsl:for-each select="../j:SubjectVictimAssociation[j:Victim/@s:ref=current()/@s:id]">
            <xsl:variable name="subject" select="../j:Subject[@s:id=current()/j:Subject/@s:ref]"/>
            &lt;nibrsRelationships&gt;
            <!--Element 34, Offender Number(s) to be related -->
            &lt;offenderNumber&gt;<xsl:value-of select="$subject/j:SubjectSequenceNumberText"/>&lt;/offenderNumber&gt;
            <!-- Element 35, Relationship(s) of Victim To Offender -->
            &lt;victimToOffenderRelationship&gt;<xsl:choose>
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
            </xsl:choose>&lt;/victimToOffenderRelationship&gt;
            &lt;/nibrsRelationships&gt;
        </xsl:for-each>
        <!-- Element 25C, ORI-Other Jurisdiction (Officer) -->
        &lt;outsideAgencyORI&gt;<xsl:value-of select="$enforcementOfficial/j:EnforcementOfficialUnit/j:OrganizationAugmentation/j:OrganizationORIIdentification/nc:IdentificationID"/>&lt;/outsideAgencyORI&gt;
        <!-- Element 30, Resident Status -->
        &lt;residentStatus&gt;<xsl:value-of select="$person-node/j:PersonResidentCode"/>&lt;/residentStatus&gt;
        <!-- Element 23, Victim Sequence Number -->
        &lt;sequenceNumber&gt;<xsl:value-of select="j:VictimSequenceNumberText"/>&lt;/sequenceNumber&gt;
        <!-- Element 25A, Type of Activity (Officer)/ Circumstance-->
        &lt;typeOfActivity&gt;<xsl:value-of select="$enforcementOfficial/j:EnforcementOfficialActivityCategoryCode"/>&lt;/typeOfActivity&gt;
        <!-- Element 25, Type of Victim -->
        &lt;victimType&gt;<xsl:value-of select="j:VictimCategoryCode"/>&lt;/victimType&gt;
        &lt;/nibrsVictims&gt;
    </xsl:template>
    <xsl:template name="age">
        <xsl:param name="person-node"/>
        <xsl:variable name="max" select="$person-node/nc:PersonAgeMeasure/nc:MeasureIntegerRange/nc:RangeMaximumIntegerValue"/>
        <xsl:variable name="min" select="$person-node/nc:PersonAgeMeasure/nc:MeasureIntegerRange/nc:RangeMinimumIntegerValue"/>
        <xsl:variable name="int" select="$person-node/nc:PersonAgeMeasure/nc:MeasureIntegerValue"/>
        <xsl:variable name="text" select="$person-node/nc:PersonAgeMeasure/nc:MeasureValueText"/>
        <xsl:if test="$max">
            &lt;ageMaximumValue&gt;<xsl:choose>
                <xsl:when test="string-length($max)=1"><xsl:value-of select="concat('0', $max)"/></xsl:when>
                <xsl:otherwise><xsl:value-of select="$max"/></xsl:otherwise>
            </xsl:choose>&lt;/ageMaximumValue&gt;
        </xsl:if>
        <xsl:if test="$min">
            &lt;ageMinimumValue&gt;<xsl:choose>
                <xsl:when test="string-length($min)=1"><xsl:value-of select="concat('0', $min)"/></xsl:when>
                <xsl:otherwise><xsl:value-of select="$min"/></xsl:otherwise>
            </xsl:choose>&lt;/ageMinimumValue&gt;
        </xsl:if>
        <xsl:choose>
            <xsl:when test="$max and $min">
            &lt;ageRange&gt;<xsl:choose>
                <xsl:when test="string-length($min)=1 and string-length($max)=1"><xsl:value-of select="concat('0', $min, '0', $max)"/></xsl:when>
                <xsl:when test="string-length($min)=1 and string-length($max)>1"><xsl:value-of select="concat('0', $min, $max)"/></xsl:when>
                <xsl:when test="string-length($min)>1 and string-length($max)=1"><xsl:value-of select="concat($min, '0', $max)"/></xsl:when>
                <xsl:otherwise><xsl:value-of select="concat($min, $max)"/></xsl:otherwise>
            </xsl:choose>&lt;/ageRange&gt;
        </xsl:when>
            <xsl:when test="$int">&lt;ageRange&gt;<xsl:choose>
                <xsl:when test="string-length($int)=1"><xsl:value-of select="concat('0', $int)"/></xsl:when>
                <xsl:otherwise><xsl:value-of select="$int"/></xsl:otherwise>
            </xsl:choose>&lt;/ageRange&gt;
            </xsl:when>
        </xsl:choose>
        <xsl:if test="$text">&lt;ageRange&gt;<xsl:choose>
            <xsl:when test="$text='NEONATAL'">NN</xsl:when>
            <xsl:when test="$text='NEWBORN'">NB</xsl:when>
            <xsl:when test="$text='BABY'">BB</xsl:when>
            <xsl:when test="$text='UNKNOWN'">00</xsl:when>
            <xsl:otherwise><xsl:value-of select="$text"/></xsl:otherwise>
        </xsl:choose>&lt;/ageRange&gt;</xsl:if>
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
        &lt;armedWith&gt;
        <xsl:choose>
            <xsl:when test="contains(., 'A')">
                &lt;automaticIndicator&gt;A&lt;/automaticIndicator&gt;
                &lt;weaponCode&gt;<xsl:value-of select="substring-before(., 'A')"/>&lt;/weaponCode&gt;
            </xsl:when>
            <xsl:otherwise>
                &lt;weaponCode&gt;<xsl:value-of select="."/>&lt;/weaponCode&gt;
            </xsl:otherwise>
        </xsl:choose>
        &lt;/armedWith&gt;
    </xsl:template>
    <xsl:template match="j:OffenseFactorBiasMotivationCode">
        &lt;biasMotivations&gt;<xsl:choose>
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
        </xsl:choose>&lt;/biasMotivations&gt;
    </xsl:template>
    <xsl:template match="*[local-name()='CriminalActivityCategoryCode']">
        &lt;criminalActs&gt;<xsl:value-of select="."/>&lt;/criminalActs&gt;
    </xsl:template>
    <xsl:template match="j:OffenseFactor/j:OffenseFactorCode">
        &lt;offenderSuspectedUsing&gt;<xsl:value-of select="."/>&lt;/offenderSuspectedUsing&gt;
    </xsl:template>
    <xsl:template match="j:OffenseForce/j:ForceCategoryCode">
        &lt;typeWeaponForce&gt;
        <xsl:choose>
            <xsl:when test="contains(., 'A')">
                &lt;automaticIndicator&gt;A&lt;/automaticIndicator&gt;
                &lt;weaponCode&gt;<xsl:value-of select="substring-before(., 'A')"/>&lt;/weaponCode&gt;
            </xsl:when>
            <xsl:otherwise>
                &lt;weaponCode&gt;<xsl:value-of select="."/>&lt;/weaponCode&gt;
            </xsl:otherwise>
        </xsl:choose>
        &lt;/typeWeaponForce&gt;
    </xsl:template>
    <xsl:template match="j:VictimAggravatedAssaultHomicideFactorCode">
        &lt;aggAssaultHomicideCodes&gt;<xsl:value-of select="."/>&lt;/aggAssaultHomicideCodes&gt;
    </xsl:template>
    <xsl:template match="j:VictimInjury">
        &lt;injuryCodes&gt;<xsl:value-of select="j:InjuryCategoryCode"/>&lt;/injuryCodes&gt;
    </xsl:template>
    <xsl:template match="nc:ActivityDate" mode="incidentDate">
        <xsl:choose>
            <xsl:when test="nc:Date">
                &lt;incidentDate&gt;<xsl:value-of select="translate(nc:Date, '-', '')"/>&lt;/incidentDate&gt;
            </xsl:when>
            <xsl:when test="nc:DateTime">
                &lt;incidentDate&gt;<xsl:value-of select="substring-before(translate(nc:DateTime, '-', ''), 'T')"/>&lt;/incidentDate&gt;
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template match="nc:ActivityDate" mode="arrestDate">
        <xsl:choose>
            <xsl:when test="nc:Date">
                &lt;arrestDate&gt;<xsl:value-of select="translate(nc:Date, '-', '')"/>&lt;/arrestDate&gt;
            </xsl:when>
            <xsl:when test="nc:DateTime">
                &lt;arrestDate&gt;<xsl:value-of select="substring-before(translate(nc:DateTime, '-', ''), 'T')"/>&lt;/arrestDate&gt;
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
                &lt;recoveredDate&gt;<xsl:value-of select="translate(nc:Date, '-', '')"/>&lt;/recoveredDate&gt;
            </xsl:when>
            <xsl:when test="nc:DateTime">
                &lt;recoveredDate&gt;<xsl:value-of select="substring-before(translate(nc:DateTime, '-', ''), 'T')"/>&lt;/recoveredDate&gt;
            </xsl:when>
        </xsl:choose>
    </xsl:template>
</xsl:stylesheet>