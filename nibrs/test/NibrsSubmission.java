package nibrs.test;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.stream.StreamSource;

import gov.fbi.cjis._1.MessageMetadataType;
import gov.fbi.cjis.cjis_codes._1.ItemStatusCodeSimpleType;
import gov.fbi.cjis.cjis_codes._1.ItemStatusCodeType;
import gov.fbi.cjis.nibrs._4.ReportHeaderType;
import gov.fbi.cjis.nibrs._4.ReportType;
import gov.fbi.cjis.nibrs._4.SubmissionType;
import gov.fbi.cjis.nibrs.nibrs_codes._4.CriminalActivityCategoryCodeSimpleType;
import gov.fbi.cjis.nibrs.nibrs_codes._4.CriminalActivityCategoryCodeType;
import gov.fbi.cjis.nibrs.nibrs_codes._4.LocationCategoryCodeType;
import gov.fbi.cjis.nibrs.nibrs_codes._4.NIBRSReportCategoryCodeSimpleType;
import gov.fbi.cjis.nibrs.nibrs_codes._4.NIBRSReportCategoryCodeType;
import gov.fbi.cjis.nibrs.nibrs_codes._4.OffenseCodeType;
import gov.fbi.cjis.nibrs.nibrs_codes._4.ReportActionCategoryCodeSimpleType;
import gov.fbi.cjis.nibrs.nibrs_codes._4.ReportActionCategoryCodeType;
import gov.fbi.cjis.nibrs.nibrs_codes._4.VictimToSubjectRelationshipCodeSimpleType;
import gov.fbi.cjis.nibrs.nibrs_codes._4.VictimToSubjectRelationshipCodeType;
import gov.niem.release.niem.codes.fbi_ncic._3.SEXCodeSimpleType;
import gov.niem.release.niem.codes.fbi_ncic._3.SEXCodeType;
import gov.niem.release.niem.codes.fbi_ndex._3.BiasMotivationCodeSimpleType;
import gov.niem.release.niem.codes.fbi_ndex._3.BiasMotivationCodeType;
import gov.niem.release.niem.codes.fbi_ndex._3.JuvenileDispositionCodeSimpleType;
import gov.niem.release.niem.codes.fbi_ndex._3.JuvenileDispositionCodeType;
import gov.niem.release.niem.codes.fbi_ndex._3.RACCodeSimpleType;
import gov.niem.release.niem.codes.fbi_ndex._3.RACCodeType;
import gov.niem.release.niem.codes.fbi_ucr._3.AggravatedAssaultHomicideFactorsCodeType;
import gov.niem.release.niem.codes.fbi_ucr._3.ArrestCategoryCodeSimpleType;
import gov.niem.release.niem.codes.fbi_ucr._3.ArrestCategoryCodeType;
import gov.niem.release.niem.codes.fbi_ucr._3.ArresteeWeaponCodeType;
import gov.niem.release.niem.codes.fbi_ucr._3.DrugCategoryCodeSimpleType;
import gov.niem.release.niem.codes.fbi_ucr._3.DrugCategoryCodeType;
import gov.niem.release.niem.codes.fbi_ucr._3.DrugMeasurementCodeSimpleType;
import gov.niem.release.niem.codes.fbi_ucr._3.DrugMeasurementCodeType;
import gov.niem.release.niem.codes.fbi_ucr._3.EthnicityCodeSimpleType;
import gov.niem.release.niem.codes.fbi_ucr._3.EthnicityCodeType;
import gov.niem.release.niem.codes.fbi_ucr._3.ForceCategoryCodeType;
import gov.niem.release.niem.codes.fbi_ucr._3.IncidentExceptionalClearanceCodeSimpleType;
import gov.niem.release.niem.codes.fbi_ucr._3.IncidentExceptionalClearanceCodeType;
import gov.niem.release.niem.codes.fbi_ucr._3.IncidentFactorCodeSimpleType;
import gov.niem.release.niem.codes.fbi_ucr._3.IncidentFactorCodeType;
import gov.niem.release.niem.codes.fbi_ucr._3.InjuryCategoryCodeSimpleType;
import gov.niem.release.niem.codes.fbi_ucr._3.InjuryCategoryCodeType;
import gov.niem.release.niem.codes.fbi_ucr._3.JustifiableHomicideFactorsCodeSimpleType;
import gov.niem.release.niem.codes.fbi_ucr._3.JustifiableHomicideFactorsCodeType;
import gov.niem.release.niem.codes.fbi_ucr._3.LEOKAActivityCategoryCodeType;
import gov.niem.release.niem.codes.fbi_ucr._3.LEOKAOfficerAssignmentCategoryCodeSimpleType;
import gov.niem.release.niem.codes.fbi_ucr._3.LEOKAOfficerAssignmentCategoryCodeType;
import gov.niem.release.niem.codes.fbi_ucr._3.MethodOfEntryCodeSimpleType;
import gov.niem.release.niem.codes.fbi_ucr._3.MethodOfEntryCodeType;
import gov.niem.release.niem.codes.fbi_ucr._3.MultipleArresteeSegmentsCodeSimpleType;
import gov.niem.release.niem.codes.fbi_ucr._3.MultipleArresteeSegmentsCodeType;
import gov.niem.release.niem.codes.fbi_ucr._3.PropertyCategoryCodeType;
import gov.niem.release.niem.codes.fbi_ucr._3.ResidentCodeSimpleType;
import gov.niem.release.niem.codes.fbi_ucr._3.ResidentCodeType;
import gov.niem.release.niem.codes.fbi_ucr._3.VictimCategoryCodeSimpleType;
import gov.niem.release.niem.codes.fbi_ucr._3.VictimCategoryCodeType;
import gov.niem.release.niem.domains.jxdm._5.ActivitySubjectAssociationType;
import gov.niem.release.niem.domains.jxdm._5.ArrestType;
import gov.niem.release.niem.domains.jxdm._5.ArresteeType;
import gov.niem.release.niem.domains.jxdm._5.ChargeType;
import gov.niem.release.niem.domains.jxdm._5.EnforcementOfficialType;
import gov.niem.release.niem.domains.jxdm._5.EnforcementUnitType;
import gov.niem.release.niem.domains.jxdm._5.ForceType;
import gov.niem.release.niem.domains.jxdm._5.OffenseFactorType;
import gov.niem.release.niem.domains.jxdm._5.OffenseLocationAssociationType;
import gov.niem.release.niem.domains.jxdm._5.OffenseType;
import gov.niem.release.niem.domains.jxdm._5.OffenseVictimAssociationType;
import gov.niem.release.niem.domains.jxdm._5.OrganizationAugmentationType;
import gov.niem.release.niem.domains.jxdm._5.PassagePointType;
import gov.niem.release.niem.domains.jxdm._5.SubjectType;
import gov.niem.release.niem.domains.jxdm._5.SubjectVictimAssociationType;
import gov.niem.release.niem.domains.jxdm._5.VictimType;
import gov.niem.release.niem.niem_core._3.ActivityType;
import gov.niem.release.niem.niem_core._3.AmountType;
import gov.niem.release.niem.niem_core._3.DateType;
import gov.niem.release.niem.niem_core._3.IdentificationType;
import gov.niem.release.niem.niem_core._3.IncidentType;
import gov.niem.release.niem.niem_core._3.InjuryType;
import gov.niem.release.niem.niem_core._3.IntegerRangeType;
import gov.niem.release.niem.niem_core._3.ItemType;
import gov.niem.release.niem.niem_core._3.ItemValueType;
import gov.niem.release.niem.niem_core._3.LocationType;
import gov.niem.release.niem.niem_core._3.MeasureType;
import gov.niem.release.niem.niem_core._3.OrganizationType;
import gov.niem.release.niem.niem_core._3.PersonType;
import gov.niem.release.niem.niem_core._3.QuantityType;
import gov.niem.release.niem.niem_core._3.StatusType;
import gov.niem.release.niem.niem_core._3.SubstanceType;
import gov.niem.release.niem.niem_core._3.TextType;
import gov.niem.release.niem.proxy.xsd._3.Date;
import gov.niem.release.niem.proxy.xsd._3.DateTime;
import gov.niem.release.niem.proxy.xsd._3.Decimal;
import gov.niem.release.niem.proxy.xsd._3.GYearMonth;
import gov.niem.release.niem.structures._3.MetadataType;

public class NibrsSubmission {

	JTextArea textArea;
	JFrame frame;

	gov.niem.release.niem.domains.jxdm._5.ObjectFactory jxdm5ObjectFactory = new gov.niem.release.niem.domains.jxdm._5.ObjectFactory();
	gov.niem.release.niem.niem_core._3.ObjectFactory niemCore3ObjectFactory = new gov.niem.release.niem.niem_core._3.ObjectFactory();
	gov.fbi.cjis._1.ObjectFactory cjis1ObjectFactory = new gov.fbi.cjis._1.ObjectFactory();
	gov.fbi.cjis.nibrs._4.ObjectFactory nibrs4ObjectFactory = new gov.fbi.cjis.nibrs._4.ObjectFactory();
	gov.fbi.cjis.nibrs.nibrs_codes._4.ObjectFactory nibrsCodes4ObjectFactory = new gov.fbi.cjis.nibrs.nibrs_codes._4.ObjectFactory();
	gov.niem.release.niem.proxy.xsd._3.ObjectFactory niemProxy3ObjectFactory = new gov.niem.release.niem.proxy.xsd._3.ObjectFactory();
	gov.niem.release.niem.codes.fbi_ucr._3.ObjectFactory ucr3ObjectFactory = new gov.niem.release.niem.codes.fbi_ucr._3.ObjectFactory();

	MessageMetadata mmd = new MessageMetadata();

	public void unmarshallSubmission(java.lang.String inputFile) {

		textAreaGUI();

		StreamSource streamSource = new StreamSource(inputFile);
		JAXBContext context;
		Unmarshaller unmarshaller;
		JAXBElement<SubmissionType> subElement;
		SubmissionType submission;
		MessageMetadata mmd = new MessageMetadata();
		Report report = new Report();

		try {
			// context = JAXBContext.newInstance("gov.fbi.cjis.nibrs._4");
			context = JAXBContext.newInstance(nibrs4ObjectFactory.getClass());

			unmarshaller = context.createUnmarshaller();

			subElement = unmarshaller.unmarshal(streamSource, SubmissionType.class);
			submission = subElement.getValue();

			mmd.unmarshallMessageMetadata(submission);
			report.unmarshallReport(submission);

		} catch (JAXBException e1) {

			e1.printStackTrace();
		} // catch

	}// public SubmissionType unmarshallSubmission
	public void unmarshallSubmission(File inputFile) {

		textAreaGUI(inputFile);

		StreamSource streamSource = new StreamSource(inputFile);
		JAXBContext context;
		Unmarshaller unmarshaller;
		JAXBElement<SubmissionType> subElement;
		SubmissionType submission;
		MessageMetadata mmd = new MessageMetadata();
		Report report = new Report();

		try {
			// context = JAXBContext.newInstance("gov.fbi.cjis.nibrs._4");
			context = JAXBContext.newInstance(nibrs4ObjectFactory.getClass());

			unmarshaller = context.createUnmarshaller();

			subElement = unmarshaller.unmarshal(streamSource, SubmissionType.class);
			submission = subElement.getValue();

			mmd.unmarshallMessageMetadata(submission);
			report.unmarshallReport(submission);

		} catch (JAXBException e1) {

			e1.printStackTrace();
		} // catch

	}// public SubmissionType unmarshallSubmission
	public void marshallSubmission(String outputFile, String[] mmdArray, String[] rhArray, String[] incArray,
			ArrayList<String[]> offList, ArrayList<String[]> locList, ArrayList<String[]> itemList,
			ArrayList<String[]> substanceList, ArrayList<String[]> personList, ArrayList<String[]> enfOffList,
			ArrayList<String[]> vicList, ArrayList<String[]> subjectList, ArrayList<String[]> arresteeList,
			ArrayList<String[]> arrestList, ArrayList<String[]> activitySubjectList, ArrayList<String[]> offenseLocationList,
			ArrayList<String[]> offenseVictimList, ArrayList<String[]> subjectVictimList) throws JAXBException {

		//NibrsSubmission submission = new NibrsSubmission();
		
		FileWriter fw;
		BufferedWriter bw;

		try {
			fw = new FileWriter(outputFile);
			bw = new BufferedWriter(fw);

			Marshaller marshaller;
			JAXBElement<SubmissionType> jx;
			JAXBContext context;

			try {
				context = JAXBContext.newInstance(nibrs4ObjectFactory.getClass());
				marshaller = context.createMarshaller();
				jx = nibrs4ObjectFactory.createSubmission(
						createSubmission(mmdArray, rhArray, incArray, offList, locList, itemList,
								substanceList, personList, enfOffList, vicList, subjectList, arresteeList, arrestList, 
								activitySubjectList, offenseLocationList, offenseVictimList, subjectVictimList));
				marshaller.marshal(jx, bw);
				if (bw !=null){
					bw.close();
					fw.close();
				}
			} catch (JAXBException e) {
				e.printStackTrace();
			} // catch

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}// public void marshallSubmission
	public SubmissionType createSubmission(String[] mmdValues, String[] rhArray, String[] incArray,
			ArrayList<String[]> offList, ArrayList<String[]> locList, ArrayList<String[]> itemList,
			ArrayList<String[]> substanceList, ArrayList<String[]> personList, ArrayList<String[]> enfOffList,
			ArrayList<String[]> vicList, ArrayList<String[]> subjectList, ArrayList<String[]> arresteeList,
			ArrayList<String[]> arrestList, ArrayList<String[]> activitySubjectList, ArrayList<String[]> offenseLocationList,
			ArrayList<String[]> offenseVictimList, ArrayList<String[]> subjectVictimList) {

		SubmissionType submission = nibrs4ObjectFactory.createSubmissionType();

		Report report = new Report();
		MessageMetadata mmd = new MessageMetadata();
		submission.setMessageMetadata(mmd.createMessageMetaData(mmdValues));
		submission.setReport(report.createReport(rhArray, incArray, offList, locList, itemList, substanceList,
				personList, enfOffList, vicList, subjectList, arresteeList, arrestList, activitySubjectList, 
				offenseLocationList, offenseVictimList, subjectVictimList));

		return submission;
	}

	private XMLGregorianCalendar createGregCal(int year, int month, int day, int hour, int min, int sec) {
		XMLGregorianCalendar gregCalendar = null;
		try {
			gregCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
			gregCalendar.setYear(year);
			gregCalendar.setMonth(month);
			gregCalendar.setDay(day);
			gregCalendar.setHour(hour);
			gregCalendar.setMinute(min);
			gregCalendar.setSecond(sec);
		} catch (DatatypeConfigurationException e1) {
			e1.printStackTrace();
		} // catch

		return gregCalendar;
	}// private XMLGregorianCalendar createGregCal
	private XMLGregorianCalendar createGregCal(int year, int month, int day, int hour, int min) {
		XMLGregorianCalendar gregCalendar = null;
		try {
			gregCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
			gregCalendar.setYear(year);
			gregCalendar.setMonth(month);
			gregCalendar.setDay(day);
			gregCalendar.setHour(hour);
			gregCalendar.setMinute(min);
			gregCalendar.setSecond(0);;
		} catch (DatatypeConfigurationException e1) {
			e1.printStackTrace();
		} // catch
		return gregCalendar;
	}
	private XMLGregorianCalendar createGregCal(int year, int month, int day, int hour) {
		XMLGregorianCalendar gregCalendar = null;
		try {
			gregCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
			gregCalendar.setYear(year);
			gregCalendar.setMonth(month);
			gregCalendar.setDay(day);
			gregCalendar.setHour(hour);
			gregCalendar.setMinute(0);
			gregCalendar.setSecond(0);;
		} catch (DatatypeConfigurationException e1) {
			e1.printStackTrace();
		} // catch
		return gregCalendar;
	}
	private XMLGregorianCalendar createGregCal(int year, int month, int day) {
		XMLGregorianCalendar gregCalendar = null;
		try {
			gregCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
			gregCalendar.setYear(year);
			gregCalendar.setMonth(month);
			gregCalendar.setDay(day);
		} catch (DatatypeConfigurationException e1) {
			e1.printStackTrace();
		} // catch
		return gregCalendar;
	}// private XMLGregorianCalendar createGregCal
	private XMLGregorianCalendar createGregCal(int year, int month) {
		XMLGregorianCalendar gregCalendar = null;
		try {
			gregCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
			gregCalendar.setYear(year);
			gregCalendar.setMonth(month);
		} catch (DatatypeConfigurationException e1) {
			e1.printStackTrace();
		} // catch
		return gregCalendar;
	}// private XMLGregorianCalendar createGregCal

	private void textAreaGUI(File filename) {
		frame = new JFrame();
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(filename.getName());
		frame.setSize(800, 700);
		frame.setResizable(true);
		frame.setVisible(true);
		frame.setLocation(300, 300);

		JButton closeButton = new JButton("Close Window");
		closeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				frame.dispose();
			}
		});

		textArea = new JTextArea(600, 500);
		textArea.setLineWrap(true);

		JScrollPane scroller = new JScrollPane(textArea);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		frame.getContentPane().add(BorderLayout.CENTER, scroller);
		frame.getContentPane().add(BorderLayout.NORTH, closeButton);

	}
	private void textAreaGUI() {
		frame = new JFrame();
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 700);
		frame.setResizable(true);
		frame.setVisible(true);
		frame.setLocation(300, 300);

		JButton closeButton = new JButton("Close Window");
		closeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				frame.dispose();
			}
		});

		textArea = new JTextArea(600, 500);
		textArea.setLineWrap(true);

		JScrollPane scroller = new JScrollPane(textArea);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		frame.getContentPane().add(BorderLayout.CENTER, scroller);
		frame.getContentPane().add(BorderLayout.NORTH, closeButton);

	}


	private class MessageMetadata {

		MessageMetadataType messageMetaData = new MessageMetadataType();

		public void unmarshallMessageMetadata(SubmissionType sub) {
			textArea.append("************MessageMetaData************ \n");
			MessageMetadataType mmd = sub.getMessageMetadata();
			getMessageDateTime(mmd);
			getMessageID(mmd);
			getImpVer(mmd);
			getSubmittingOrg(mmd);
		}

		// Message Date Time
		private void getMessageDateTime(MessageMetadataType messageMeta) {
			textArea.append("Message Date and Time: " + messageMeta.getMessageDateTime().getValue() + "\n");
		}
		private void setMessageDateTime(String year, String month, String day, String hour, String min, String sec) {
			DateTime messageDateTime = new DateTime();
			if (!year.isEmpty() && !month.isEmpty() && !day.isEmpty() && !hour.isEmpty() && !min.isEmpty()
					&& !day.isEmpty()) {
				messageDateTime.setValue(createGregCal(Integer.parseInt(year), Integer.parseInt(month),
						Integer.parseInt(day), Integer.parseInt(hour), Integer.parseInt(min), Integer.parseInt(sec)));
				messageMetaData.setMessageDateTime(messageDateTime);

			} else if (!year.isEmpty() && !month.isEmpty() && !day.isEmpty()) {
				messageDateTime.setValue(
						createGregCal(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)));
				messageMetaData.setMessageDateTime(messageDateTime);

			} else {
				messageDateTime.setValue(createGregCal(1999, 1, 1, 1, 1, 1));
				messageMetaData.setMessageDateTime(messageDateTime);
			}
		}

		// Message Identification
		private void getMessageID(MessageMetadataType messageMeta) {
			textArea.append("Message ID: " + messageMeta.getMessageIdentification().getIdentificationID().getValue() + "\n");
		}
		private void setMessageID(String id) {
			gov.niem.release.niem.proxy.xsd._3.String messageID = new gov.niem.release.niem.proxy.xsd._3.String();
			messageID.setValue(id);
			IdentificationType messageIdentification = new IdentificationType();
			messageIdentification.setIdentificationID(messageID);
			messageMetaData.setMessageIdentification(messageIdentification);
		}

		// Message Implementation Version
		private void getImpVer(MessageMetadataType messageMeta) {
			textArea.append("Message Implementation Version: " + messageMeta.getMessageImplementationVersion().getValue() + "\n");
		}
		private void setImpVer(String ver) {
			TextType messImpVer = new TextType();
			messImpVer.setValue(ver);
			messageMetaData.setMessageImplementationVersion(messImpVer);
		}

		// Message Submitting Organization
		private void getSubmittingOrg(MessageMetadataType messageMeta) {
			JAXBElement<?> orgAugElement = messageMeta.getMessageSubmittingOrganization()
					.getOrganizationAugmentationPoint();
			if (orgAugElement.getValue() instanceof gov.fbi.cjis._1.OrganizationAugmentationType) {
				gov.fbi.cjis._1.OrganizationAugmentationType orgID = (gov.fbi.cjis._1.OrganizationAugmentationType) orgAugElement
						.getValue();
				ArrayList<IdentificationType> orgIDList = (ArrayList<IdentificationType>) orgID
						.getDirectReportingCityIdentification();
				orgIDList.forEach((id) -> {
					textArea.append("Submitting Organization: " + id.getIdentificationID() + "\n");
				});

			} else if (orgAugElement
					.getValue() instanceof gov.niem.release.niem.domains.jxdm._5.OrganizationAugmentationType) {
				gov.niem.release.niem.domains.jxdm._5.OrganizationAugmentationType orgID = (gov.niem.release.niem.domains.jxdm._5.OrganizationAugmentationType) orgAugElement
						.getValue();
				JAXBElement<?> orgIDElement = orgID.getOrganizationIdentification();
				if (orgIDElement.getValue() instanceof IdentificationType) {
					IdentificationType id = (IdentificationType) orgIDElement.getValue();
					textArea.append("Submitting Organization: " + id.getIdentificationID().getValue() + "\n");
				} else if (orgIDElement.getValue() instanceof Object) {
					Object id = (Object) orgIDElement.getValue();
					textArea.append("Submitting Organization: " + id.toString() + "\n");
				}

			} else if (orgAugElement.getValue() instanceof Object) {
				Object orgID = (Object) orgAugElement.getValue();
				textArea.append("Submitting Organization: " + orgID.toString() + "\n");
			}

		}
		private void setSubmittingOrg(String ORI) {
			OrganizationType submittingOrg = new OrganizationType();
			gov.niem.release.niem.domains.jxdm._5.OrganizationAugmentationType submittingOrgAug = new gov.niem.release.niem.domains.jxdm._5.OrganizationAugmentationType();
			IdentificationType submittingOrgID = new IdentificationType();
			gov.niem.release.niem.proxy.xsd._3.String submittingOrgString = new gov.niem.release.niem.proxy.xsd._3.String();
			submittingOrgString.setValue(ORI);
			submittingOrgID.setIdentificationID(submittingOrgString);
			JAXBElement<IdentificationType> submittingOrgIDElement = jxdm5ObjectFactory
					.createOrganizationORIIdentification(submittingOrgID);
			submittingOrgAug.setOrganizationIdentification(submittingOrgIDElement);
			JAXBElement<gov.niem.release.niem.domains.jxdm._5.OrganizationAugmentationType> submittingOrgAugElement = jxdm5ObjectFactory
					.createOrganizationAugmentation(submittingOrgAug);
			submittingOrg.setOrganizationAugmentationPoint(submittingOrgAugElement);
			messageMetaData.setMessageSubmittingOrganization(submittingOrg);
		}

		public MessageMetadataType createMessageMetaData(String[] stringArray) {
			setMessageID(stringArray[0]);
			setImpVer(stringArray[1]);
			setSubmittingOrg(stringArray[2]);
			setMessageDateTime(stringArray[3], stringArray[4], stringArray[5], 
					stringArray[6], stringArray[7],	stringArray[8]);
			return messageMetaData;
		}
	};
	private class Report {

		ReportType report = new ReportType();

		private void unmarshallReport(SubmissionType sub) {
			ReportType rep = sub.getReport();

			Header header = new Header();
			header.unmarshallHeader(rep);

			Incident incident = new Incident();
			incident.unmarshallIncident(rep);

			Offense offense = new Offense();
			offense.unmarshallOffense(rep);

			Location location = new Location();
			location.unmarshallLocation(rep);

			Item item = new Item();
			item.unmarshallItem(rep);

			Substance substance = new Substance();
			substance.unmarshallSubstance(rep);

			Person person = new Person();
			person.unmarshallPerson(rep);

			EnforcementOfficial enforcementOfficial = new EnforcementOfficial();
			enforcementOfficial.unmarshallEnforcementOfficial(rep);

			Victim victim = new Victim();
			victim.unmarshallVictim(rep);

			Subject subject = new Subject();
			subject.unmarshallSubject(rep);

			Arrestee arrestee = new Arrestee();
			arrestee.unmarshallArrestee(rep);

			Arrest arrest = new Arrest();
			arrest.unmarshallArrest(rep);

			
			ActivitySubjectAssociation actSubjAssoc = new ActivitySubjectAssociation();
			actSubjAssoc.unmarshallActivitySubjectAssociation(rep);

			OffenseLocationAssociation offLocAssoc = new OffenseLocationAssociation();
			offLocAssoc.unmarshallOffenseLocationAssociation(rep);

			OffenseVictimAssociation offVicAssoc = new OffenseVictimAssociation();
			offVicAssoc.unmarshallOffenseVictimAssociation(rep);

			SubjectVictimAssociation subVicAssoc = new SubjectVictimAssociation();
			subVicAssoc.unmarshallSubjectVictimAssociation(rep);
		}
		private ReportType createReport(String[] rhArray, String[] incArray, ArrayList<String[]> offList,
				ArrayList<String[]> locList, ArrayList<String[]> itemList, ArrayList<String[]> substanceList,
				ArrayList<String[]> personList, ArrayList<String[]> enfOffList, ArrayList<String[]> vicList,
				ArrayList<String[]> subjectList, ArrayList<String[]> arresteeList, ArrayList<String[]> arrestList,
				ArrayList<String[]> activitySubjectList, ArrayList<String[]> offenseLocationList, 
				ArrayList<String[]> offenseVictimList, ArrayList<String[]> subjectVictimList) {
			Header header = new Header();
			report.setReportHeader(header.createReportHeader(rhArray));

			locList.forEach((locArray) -> {
				Location location = new Location();
				report.getLocation().add(location.createLocation(locArray));
			});

			Incident incident = new Incident();
			report.setIncident(incident.createIncident(incArray));

			offList.forEach((offArray) -> {
				Offense offense = new Offense();
				report.getOffense().add(offense.createOffense(offArray));
			});

			itemList.forEach((itemArray) -> {
				Item item = new Item();
				report.getItem().add(item.createItem(itemArray));
			});

			substanceList.forEach((itemArray) -> {
				Substance substance = new Substance();
				report.getSubstance().add(substance.createSubstance(itemArray));
			});

			personList.forEach((personArray) -> {
				Person person = new Person();
				report.getPerson().add(person.createPerson(personArray));
			});

			enfOffList.forEach((enfOffArray) -> {
				EnforcementOfficial enforcementOfficial = new EnforcementOfficial();
				report.getEnforcementOfficial().add(enforcementOfficial.createEnforcementOfficial(enfOffArray));
			});

			vicList.forEach((vicArray) -> {
				Victim victim = new Victim();
				report.getVictim().add(victim.createVictim(vicArray));
			});

			subjectList.forEach((subjectArray) -> {
				Subject subject = new Subject();
				report.getSubject().add(subject.createSubject(subjectArray));
			});

			arresteeList.forEach((arresteeArray) -> {
				Arrestee arrestee = new Arrestee();
				report.getArrestee().add(arrestee.createArrestee(arresteeArray));
			});

			arrestList.forEach((arrestArray) -> {
				Arrest arrest = new Arrest();
				report.getArrest().add(arrest.createArrest(arrestArray));
			});

			activitySubjectList.forEach((activitySubjectArray) -> {
				ActivitySubjectAssociation actSubAssoc = new ActivitySubjectAssociation();
				report.getArrestSubjectAssociation().add(actSubAssoc.createActSubjAssoc(activitySubjectArray));
			});

		
			 offenseLocationList.forEach((offenseLocationArray) -> {
				 OffenseLocationAssociation offLocAssoc = new  OffenseLocationAssociation();
						 report.getOffenseLocationAssociation().add(offLocAssoc.createOffLocAssoc(offenseLocationArray));
			 });
			 
			 offenseVictimList.forEach((offVicArray) -> {
				 OffenseVictimAssociation offVicAssoc = new OffenseVictimAssociation();
						 report.getOffenseVictimAssociation().add(offVicAssoc.createOffVicAssoc(offVicArray));
			 });

			 
			 subjectVictimList.forEach((subjVicArray) -> {
				 SubjectVictimAssociation subVicAssoc = new SubjectVictimAssociation();
						 report.getSubjectVictimAssociation().add(subVicAssoc.createSubjVicAssoc(subjVicArray));
			 });
			 

			return report;
		}
		private class Header {

			ReportHeaderType reportHeader = new ReportHeaderType();

			private void unmarshallHeader(ReportType report) {
				textArea.append("***********Report Header ************ \n");
				ReportHeaderType rh = report.getReportHeader();
				if (rh != null) {

					getReportCategoryCode(rh);
					getReportActionCategoryCode(rh);
					getReportingAgency(rh);
					getReportDate(rh);
				}

			}

			// Report Category Code
			private void getReportCategoryCode(ReportHeaderType rh) {
				textArea.append(
						"NIBRS Report Category Group:" + rh.getNIBRSReportCategoryCode().getValue() + "\n");
			}
			private void setReportCategoryCode(String string) {
				NIBRSReportCategoryCodeType reportCategoryCode = new NIBRSReportCategoryCodeType();
				switch (string) {
				case "Group A":
					NIBRSReportCategoryCodeSimpleType a = NIBRSReportCategoryCodeSimpleType.GROUP_A_INCIDENT_REPORT;
					reportCategoryCode.setValue(a);
					reportHeader.setNIBRSReportCategoryCode(reportCategoryCode);
					break;
				case "Group B":
					NIBRSReportCategoryCodeSimpleType b = NIBRSReportCategoryCodeSimpleType.GROUP_B_ARREST_REPORT;
					reportCategoryCode.setValue(b);
					reportHeader.setNIBRSReportCategoryCode(reportCategoryCode);
					break;
				case "Zero Report":
					NIBRSReportCategoryCodeSimpleType z = NIBRSReportCategoryCodeSimpleType.ZERO_REPORT;
					reportCategoryCode.setValue(z);
					reportHeader.setNIBRSReportCategoryCode(reportCategoryCode);
					break;

				}
			}

			// Report Action Category Code
			private void getReportActionCategoryCode(ReportHeaderType rh) {
				textArea.append(
						"Report Action Category Code:" + rh.getReportActionCategoryCode().getValue().value() + "\n");
			}
			private void setReportActionCategoryCode(String string) {
				ReportActionCategoryCodeType reportActionCategoryCode = new ReportActionCategoryCodeType();
				switch (string) {
				case "Incident Report":
					ReportActionCategoryCodeSimpleType i = ReportActionCategoryCodeSimpleType.I;
					reportActionCategoryCode.setValue(i);
					reportHeader.setReportActionCategoryCode(reportActionCategoryCode);
					break;
				case "Delete":
					ReportActionCategoryCodeSimpleType d = ReportActionCategoryCodeSimpleType.D;
					reportActionCategoryCode.setValue(d);
					reportHeader.setReportActionCategoryCode(reportActionCategoryCode);
					break;
				case "Replace":
					ReportActionCategoryCodeSimpleType r = ReportActionCategoryCodeSimpleType.R;
					reportActionCategoryCode.setValue(r);
					reportHeader.setReportActionCategoryCode(reportActionCategoryCode);
					break;
				case "Add":
					ReportActionCategoryCodeSimpleType a = ReportActionCategoryCodeSimpleType.A;
					reportActionCategoryCode.setValue(a);
					reportHeader.setReportActionCategoryCode(reportActionCategoryCode);
					break;

				}
			}

			// reporting Agency
			private void getReportingAgency(ReportHeaderType rh) {
				JAXBElement<?> repOrgAugElement = rh.getReportingAgency().getOrganizationAugmentationPoint();
				if (repOrgAugElement.getValue() instanceof gov.fbi.cjis._1.OrganizationAugmentationType) {
					gov.fbi.cjis._1.OrganizationAugmentationType orgID = (gov.fbi.cjis._1.OrganizationAugmentationType) repOrgAugElement
							.getValue();
					ArrayList<IdentificationType> orgIDList = (ArrayList<IdentificationType>) orgID
							.getDirectReportingCityIdentification();
					orgIDList.forEach((id) -> {
						textArea.append("Reporting Agency:" + id.getIdentificationID() + "\n");
					});
				} else if (repOrgAugElement
						.getValue() instanceof gov.niem.release.niem.domains.jxdm._5.OrganizationAugmentationType) {
					gov.niem.release.niem.domains.jxdm._5.OrganizationAugmentationType orgID = (gov.niem.release.niem.domains.jxdm._5.OrganizationAugmentationType) repOrgAugElement
							.getValue();
					JAXBElement<?> orgIDElement = orgID.getOrganizationIdentification();
					if (orgIDElement.getValue() instanceof IdentificationType) {
						IdentificationType id = (IdentificationType) orgIDElement.getValue();
						textArea.append("Reporting Agency: " + id.getIdentificationID().getValue() + "\n");
					} else if (orgIDElement.getValue() instanceof Object) {
						Object id = (Object) orgIDElement.getValue();
						textArea.append("Reporting Agency: " + id.toString() + "\n");
					}
				} else if (repOrgAugElement.getValue() instanceof Object) {
					Object orgID = (Object) repOrgAugElement.getValue();
					textArea.append("Reporting Agency: " + orgID.toString() + "\n");
				}
			}
			private void setReportingAgency(String string) {
				OrganizationType submittingOrg = new OrganizationType();
				gov.niem.release.niem.domains.jxdm._5.OrganizationAugmentationType submittingOrgAug = new gov.niem.release.niem.domains.jxdm._5.OrganizationAugmentationType();
				IdentificationType submittingOrgID = new IdentificationType();
				gov.niem.release.niem.proxy.xsd._3.String submittingOrgString = new gov.niem.release.niem.proxy.xsd._3.String();
				submittingOrgString.setValue(string);
				submittingOrgID.setIdentificationID(submittingOrgString);
				JAXBElement<IdentificationType> submittingOrgIDElement = jxdm5ObjectFactory
						.createOrganizationORIIdentification(submittingOrgID);
				submittingOrgAug.setOrganizationIdentification(submittingOrgIDElement);
				JAXBElement<gov.niem.release.niem.domains.jxdm._5.OrganizationAugmentationType> submittingOrgAugElement = jxdm5ObjectFactory
						.createOrganizationAugmentation(submittingOrgAug);
				submittingOrg.setOrganizationAugmentationPoint(submittingOrgAugElement);

				reportHeader.setReportingAgency(submittingOrg);
			}

			// Report Date
			private void getReportDate(ReportHeaderType rh) {
				ArrayList<JAXBElement<?>> dateList = (ArrayList<JAXBElement<?>>) rh.getReportDate()
						.getDateRepresentation();
				dateList.forEach((date) -> {
					if (date.getValue() instanceof Date) {
						Date dateValue = (Date) date.getValue();
						textArea.append("Report Date (Date):" + dateValue.getValue().toString() + "\n");
					} else if (date.getValue() instanceof DateTime) {
						DateTime dateValue = (DateTime) date.getValue();
						textArea.append("Report Date (DateTime):" + dateValue.getValue().toString() + "\n");
					} else if (date.getValue() instanceof GYearMonth) {
						GYearMonth dateValue = (GYearMonth) date.getValue();
						textArea.append("Report Date (GYearMonth):" + dateValue.getValue().toString() + "\n");
					} else if (date.getValue() instanceof Object) {
						Object dateValue = (Object) date.getValue();
						textArea.append("Report Date (Object):" + dateValue.toString() + "\n");
					}
				});
			}
			private void setReportDate(String year, String month) {
				DateType reportHeaderDateType = new DateType();
				GYearMonth reportHeaderDate = new GYearMonth();
				if (!year.isEmpty() && !month.isEmpty()) {
					reportHeaderDate.setValue(
							createGregCal(Integer.parseInt(year), Integer.parseInt(month)));
					JAXBElement<GYearMonth> dateElement = niemCore3ObjectFactory.createYearMonthDate(reportHeaderDate);
					reportHeaderDateType.getDateRepresentation().add(dateElement);
					reportHeader.setReportDate(reportHeaderDateType);
				} else {
					reportHeaderDate.setValue(createGregCal(1999, 1));
					JAXBElement<GYearMonth> dateElement = niemCore3ObjectFactory.createYearMonthDate(reportHeaderDate);
					reportHeaderDateType.getDateRepresentation().add(dateElement);
					reportHeader.setReportDate(reportHeaderDateType);
				}
			}

			private ReportHeaderType createReportHeader(String[] rhValues) {
				setReportCategoryCode(rhValues[0]);
				setReportActionCategoryCode(rhValues[1]);
				setReportingAgency(rhValues[2]);
				setReportDate(rhValues[3], rhValues[4]);

				return reportHeader;
			}
		};
		private class Incident {

			IncidentType incident = new IncidentType();

			private void unmarshallIncident(ReportType report) {
				textArea.append("***********Incident ************ \n");

				IncidentType inc = report.getIncident();
				if (inc != null) {

					getActivityID(inc);
					getIncidentDate(inc);
					getAugmentationValues(inc);
				}
			}

			// Activity Identification
			private void getActivityID(IncidentType inc) {
				if (inc.getActivityIdentification() != null) {
					IdentificationType incidentID = inc.getActivityIdentification();
					textArea.append("Activity Identification: " + incidentID.getIdentificationID().getValue() + "\n");
				}
			}

			private void setActivityID(String incID) {
				IdentificationType activityIdentification = new IdentificationType();
				gov.niem.release.niem.proxy.xsd._3.String activityID = new gov.niem.release.niem.proxy.xsd._3.String();
				activityID.setValue(incID);
				activityIdentification.setIdentificationID(activityID);
				incident.setActivityIdentification(activityIdentification);
			}

			// Incident Date
			private void getIncidentDate(IncidentType inc) {
				JAXBElement<?> incDateElement = inc.getActivityDateRepresentation();

				if (incDateElement != null) {
					if (incDateElement.getValue() instanceof DateType) {
						DateType incDate = (DateType) incDateElement.getValue();

						ArrayList<JAXBElement<?>> activityDateList = (ArrayList<JAXBElement<?>>) incDate
								.getDateRepresentation();

						activityDateList.forEach((incDateClass) -> {
							if (incDateClass.getValue() instanceof Date) {
								Date dateValue = (Date) incDateClass.getValue();
								textArea.append("ActivityDate (Date):" + dateValue.getValue().toString() + "\n");
							} else if (incDateClass.getValue() instanceof DateTime) {
								DateTime dateValue = (DateTime) incDateClass.getValue();
								textArea.append("ActivityDate (DateTime):" + dateValue.getValue().toString() + "\n");
							} else if (incDateClass.getValue() instanceof GYearMonth) {
								GYearMonth dateValue = (GYearMonth) incDateClass.getValue();
								textArea.append("ActivityDate (GYearMonth):" + dateValue.getValue().toString() + "\n");
							} else if (incDateClass.getValue() instanceof Object) {
								Object dateValue = (Object) incDateClass.getValue();
								textArea.append("ActivityDate (Object):" + dateValue.toString() + "\n");
							}

						});
					} else if (incDateElement.getValue() instanceof Object) {
						Object incDate = (Object) incDateElement.getValue();
						textArea.append("ActivityDate: " + incDate.toString() + "\n");
					}
				}

			}

			private void setIncidentDate(String year, String month, String day, String hour, String min, String sec) {
				DateTime incidentDateTime = new DateTime();
				GYearMonth incidentGYearMonth = new GYearMonth();
				Date incidentDate = new Date();
				DateType activityDate = new DateType();
				

				if (!year.isEmpty() && !month.isEmpty() && !day.isEmpty() && 
						!hour.isEmpty() && !min.isEmpty() && !sec.isEmpty()) {
					incidentDateTime.setValue(createGregCal(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), 
							Integer.parseInt(hour), Integer.parseInt(min), Integer.parseInt(sec)));
					JAXBElement<DateTime> incidentDateTimeElement = niemCore3ObjectFactory.createDateTime(incidentDateTime);
					activityDate.getDateRepresentation().add(incidentDateTimeElement);
					JAXBElement<DateType> activityDateTimeElement = niemCore3ObjectFactory.createActivityDate(activityDate);
					incident.setActivityDateRepresentation(activityDateTimeElement);
				} else if (!year.isEmpty() && !month.isEmpty() && !day.isEmpty() && 
						!hour.isEmpty() && !min.isEmpty()) {
					incidentDateTime.setValue(createGregCal(Integer.parseInt(year), Integer.parseInt(month),
							Integer.parseInt(day), Integer.parseInt(hour), Integer.parseInt(min)));
					JAXBElement<DateTime> incidentDateTimeElement = niemCore3ObjectFactory.createDateTime(incidentDateTime);
					activityDate.getDateRepresentation().add(incidentDateTimeElement);
					JAXBElement<DateType> activityDateTimeElement = niemCore3ObjectFactory.createActivityDate(activityDate);
					incident.setActivityDateRepresentation(activityDateTimeElement);
				} else if (!year.isEmpty() && !month.isEmpty() && !day.isEmpty() && 
						!hour.isEmpty()) {
					incidentDateTime.setValue(createGregCal(Integer.parseInt(year), Integer.parseInt(month),
							Integer.parseInt(day), Integer.parseInt(hour)));
					JAXBElement<DateTime> incidentDateTimeElement = niemCore3ObjectFactory.createDateTime(incidentDateTime);
					activityDate.getDateRepresentation().add(incidentDateTimeElement);
					JAXBElement<DateType> activityDateTimeElement = niemCore3ObjectFactory.createActivityDate(activityDate);
					incident.setActivityDateRepresentation(activityDateTimeElement);
				} else 	if (!year.isEmpty() && !month.isEmpty() && !day.isEmpty()) {
					incidentDate.setValue(
							createGregCal(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)));
					JAXBElement<Date> incidentDateElement = niemCore3ObjectFactory.createDate(incidentDate);
					activityDate.getDateRepresentation().add(incidentDateElement);
					JAXBElement<DateType> activityDateElement = niemCore3ObjectFactory.createActivityDate(activityDate);
					incident.setActivityDateRepresentation(activityDateElement);
				} else 	if (!year.isEmpty() && !month.isEmpty()) {
					incidentGYearMonth.setValue(
							createGregCal(Integer.parseInt(year), Integer.parseInt(month)));
					JAXBElement<GYearMonth> incidentDateElement = niemCore3ObjectFactory.createYearMonthDate(incidentGYearMonth);
					activityDate.getDateRepresentation().add(incidentDateElement);
					JAXBElement<DateType> activityDateElement = niemCore3ObjectFactory.createActivityDate(activityDate);
					incident.setActivityDateRepresentation(activityDateElement);
				}else {
					incidentDate.setValue(createGregCal(1999, 1, 1));
					JAXBElement<Date> incidentDateElement = niemCore3ObjectFactory.createDate(incidentDate);
					activityDate.getDateRepresentation().add(incidentDateElement);
					JAXBElement<DateType> activityDateElement = niemCore3ObjectFactory.createActivityDate(activityDate);
					incident.setActivityDateRepresentation(activityDateElement);
				}
			}

			/*******************************
			 * Incident Augmentation Point Exceptional Clearance Code
			 * Exceptional Clearance Date
			 *******************************/
			private void getAugmentationValues(IncidentType inc) {
				ArrayList<JAXBElement<?>> incAugList = (ArrayList<JAXBElement<?>>) inc.getIncidentAugmentationPoint();
				incAugList.forEach((incAugObject) -> {
					if (incAugObject
							.getValue() instanceof gov.niem.release.niem.domains.jxdm._5.IncidentAugmentationType) {
						gov.niem.release.niem.domains.jxdm._5.IncidentAugmentationType incRepDateInd = (gov.niem.release.niem.domains.jxdm._5.IncidentAugmentationType) incAugObject
								.getValue();
						ArrayList<JAXBElement<?>> incRepDateIndList = (ArrayList<JAXBElement<?>>) incRepDateInd.getIncidentExceptionalClearance();
						incRepDateIndList.forEach((incRepDateIndObject) -> {
							if (incRepDateIndObject.getValue() instanceof IncidentExceptionalClearanceCodeType) {
								IncidentExceptionalClearanceCodeType incExClrCode = (IncidentExceptionalClearanceCodeType) incRepDateIndObject.getValue();
								textArea.append("Exceptional Clearance Code: " + incExClrCode.getValue().name() + "\n");
							} else if (incRepDateIndObject.getValue() instanceof Object) {
								Object incExClrCode = (Object) incRepDateIndObject.getValue();
								textArea.append("Exceptional Clearance Code: " + incExClrCode.toString() + "\n");
							} 
						});
							

						// Exceptional Clearance Date
						ArrayList<DateType> incExClearanceDateList = (ArrayList<DateType>) incRepDateInd
								.getIncidentExceptionalClearanceDate();
						incExClearanceDateList.forEach((incExClearanceDateObject) -> {
							ArrayList<JAXBElement<?>> incExClearanceDateObjectList = (ArrayList<JAXBElement<?>>) incExClearanceDateObject
									.getDateRepresentation();
							incExClearanceDateObjectList.forEach((incExClr) -> {
								if (incExClr.getValue() instanceof Date) {
									Date exClrDate = (Date) incExClr.getValue();
									textArea.append("Exceptional Clearance Date (Date):"
											+ exClrDate.getValue().toString() + "\n");
								} else if (incExClr.getValue() instanceof DateTime) {
									DateTime exClrDate = (DateTime) incExClr.getValue();
									textArea.append("Exceptional Clearance Date (DateTime):"
											+ exClrDate.getValue().toString() + "\n");
								} else if (incExClr.getValue() instanceof GYearMonth) {
									GYearMonth exClrDate = (GYearMonth) incExClr.getValue();
									textArea.append("Exceptional Clearance Date (GYearMonth):"
											+ exClrDate.getValue().toString() + "\n");
								} else if (incExClr.getValue() instanceof Object) {
									Object exClrDate = (Object) incExClr.getValue();
									textArea.append("Exceptional Clearance Date (Date):" + exClrDate.toString() + "\n");
								} 
							});
					});
						// Incident Report Date Indicator
					} else if (incAugObject.getValue() instanceof gov.fbi.cjis._1.IncidentAugmentationType) {
						gov.fbi.cjis._1.IncidentAugmentationType incRepDateInd = (gov.fbi.cjis._1.IncidentAugmentationType) incAugObject
								.getValue();
						if (incRepDateInd.getIncidentReportDateIndicator().isValue()) {
							textArea.append("Report Date Indicator: TRUE" + "\n");
						} else {
							textArea.append("Report Date Indicator: FALSE" + "\n");
						}
						if (incRepDateInd.getOffenseCargoTheftIndicator() != null) {
							if (incRepDateInd.getOffenseCargoTheftIndicator().isValue()) {
								textArea.append("Cargo Theft Indicator: TRUE" + "\n");
							} else {
								textArea.append("Cargo Theft Indicator: FALSE" + "\n");
							}
						}
					} else if (incAugObject.getValue() instanceof Object) {
						Object incRepDateInd = (Object) incAugObject.getValue();
						textArea.append("Report Date Indicator: " + incRepDateInd.toString() + "\n");
					} 
				});
			}

			// Exceptional Clearance Code
			private JAXBElement<IncidentExceptionalClearanceCodeType> getExpClrCodeElement(String excClrCode) {
				IncidentExceptionalClearanceCodeType incExceptionalClrCode = new IncidentExceptionalClearanceCodeType();
				IncidentExceptionalClearanceCodeSimpleType incExceptionalClrElementSimple;
				switch (excClrCode) {
				case "Death of Offender":
					incExceptionalClrElementSimple = IncidentExceptionalClearanceCodeSimpleType.A;
					incExceptionalClrCode.setValue(incExceptionalClrElementSimple);
					break;
				case "Prosecution Declined":
					incExceptionalClrElementSimple = IncidentExceptionalClearanceCodeSimpleType.B;
					incExceptionalClrCode.setValue(incExceptionalClrElementSimple);
					break;
				case "In Custody of Other Jurisdiction":
					incExceptionalClrElementSimple = IncidentExceptionalClearanceCodeSimpleType.C;
					incExceptionalClrCode.setValue(incExceptionalClrElementSimple);
					break;
				case "Victim Refused to Cooperate":
					incExceptionalClrElementSimple = IncidentExceptionalClearanceCodeSimpleType.D;
					incExceptionalClrCode.setValue(incExceptionalClrElementSimple);
					break;
				case "Juvenile/No Custody":
					incExceptionalClrElementSimple = IncidentExceptionalClearanceCodeSimpleType.E;
					incExceptionalClrCode.setValue(incExceptionalClrElementSimple);
					break;
				case "Not Applicable":
					incExceptionalClrElementSimple = IncidentExceptionalClearanceCodeSimpleType.N;
					incExceptionalClrCode.setValue(incExceptionalClrElementSimple);
					break;
				}
				JAXBElement<IncidentExceptionalClearanceCodeType> incExeptionalClrCodeElement = jxdm5ObjectFactory
						.createIncidentExceptionalClearanceCode(incExceptionalClrCode);
				return incExeptionalClrCodeElement;
			}

			// Exceptional Clearance Date
			private DateType setExcClrDate(String year, String month, String day) {
				DateType incExceptionalClrDate = new DateType();
				Date date = new Date();
				date.setValue(createGregCal(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)));

				JAXBElement<Date> incidentCrlDateTimeElement = niemCore3ObjectFactory.createDate(date);
				incExceptionalClrDate.getDateRepresentation().add(incidentCrlDateTimeElement);
				return incExceptionalClrDate;
			}
			private void setIncAugExcClrCodeExcClrDate(String excClrCode, String year, String month, String day) {

				gov.niem.release.niem.domains.jxdm._5.IncidentAugmentationType incAug = new gov.niem.release.niem.domains.jxdm._5.IncidentAugmentationType();
				incAug.getIncidentExceptionalClearance().add(getExpClrCodeElement(excClrCode));
				if (!year.isEmpty()) {
					incAug.getIncidentExceptionalClearanceDate().add(setExcClrDate(year, month, day));
				}
				JAXBElement<gov.niem.release.niem.domains.jxdm._5.IncidentAugmentationType> incClrCodeClrDateElement = jxdm5ObjectFactory
						.createIncidentAugmentation(incAug);
				incident.getIncidentAugmentationPoint().add(incClrCodeClrDateElement);
			}

			/*******************************
			 * Incident Augmentation Point Report Date Indicator Cargo Theft
			 * Indicator
			 *******************************/
			private void setIncAugRepDateIndCargoTheftInd(String repDateInd, String cargoTheftInd) {
				gov.fbi.cjis._1.IncidentAugmentationType incAugCJIS = new gov.fbi.cjis._1.IncidentAugmentationType();

				// Set Incident Report Date Indicator
				incAugCJIS.setIncidentReportDateIndicator(setReportDateIndicator(repDateInd));
				// Set Incident Cargo Theft Indicator
				if (cargoTheftInd != "n/a")	incAugCJIS.setOffenseCargoTheftIndicator(setCargoTheftIndicator(cargoTheftInd));

				// Report Incident Report Date Indicator and Cargo Theft
				// Indicator
				JAXBElement<gov.fbi.cjis._1.IncidentAugmentationType> incReportDateIndicatorElement = cjis1ObjectFactory
						.createIncidentAugmentation(incAugCJIS);
				incident.getIncidentAugmentationPoint().add(incReportDateIndicatorElement);
			}

			// Set Incident Report Date Indicator
			private gov.niem.release.niem.proxy.xsd._3.Boolean setReportDateIndicator(String ind) {
				gov.niem.release.niem.proxy.xsd._3.Boolean incReportDateInd = new gov.niem.release.niem.proxy.xsd._3.Boolean();
				if (ind == "true")
					incReportDateInd.setValue(true);
				else
					incReportDateInd.setValue(false);
				return incReportDateInd;
			}

			// Set Incident Cargo Theft Indicator
			private gov.niem.release.niem.proxy.xsd._3.Boolean setCargoTheftIndicator(String ind) {
					gov.niem.release.niem.proxy.xsd._3.Boolean cargoTheftInd = new gov.niem.release.niem.proxy.xsd._3.Boolean();
					if (ind == "TRUE")
						cargoTheftInd.setValue(true);
					else
						cargoTheftInd.setValue(false);
					return cargoTheftInd;
			}

			private IncidentType createIncident(String[] incidentData) {

				setActivityID(incidentData[0]);
				setIncidentDate(incidentData[1], incidentData[2], incidentData[3], 
						incidentData[4], incidentData[5], incidentData[6]);
				setIncAugExcClrCodeExcClrDate(incidentData[7], incidentData[8], incidentData[9], incidentData[10]);
				setIncAugRepDateIndCargoTheftInd(incidentData[11], incidentData[12]);

				return incident;
			}
		};
		private class Offense {

			OffenseType offense = jxdm5ObjectFactory.createOffenseType();

			private void unmarshallOffense(ReportType report) {
				textArea.append("***********Offense ************ \n");
				if (report.getOffense() != null) {
					ArrayList<OffenseType> offenseList = (ArrayList<OffenseType>) report.getOffense();
					offenseList.forEach((offense) -> {
						textArea.append("**Offense** \n");
						getID(offense);
						getOffenseCode(offense);
						getCriminalActivity(offense);
						getBiasMotivationCode(offense);
						getStructuresEnteredQuant(offense);
						getOffenseFactorCode(offense);
						getPassagePoint(offense);
						getForceCategoryCode(offense);
						getOffenseAttemptedInd(offense);
					});
				}
			}

			// Offense ID
			private void getID(OffenseType off) {
				textArea.append("Offense ID: " + off.getId() + "\n");
			}
			private void setID(String id) {
				offense.setId(id);
			}

			// Offense UCR Code
			private void getOffenseCode(OffenseType off) {
				if (offense.getOffenseDesignation() != null) {
					JAXBElement<?> offenseCodeEl = offense.getOffenseDesignation();
					if (offenseCodeEl.getValue() instanceof gov.fbi.cjis.nibrs.nibrs_codes._4.OffenseCodeType) {
						gov.fbi.cjis.nibrs.nibrs_codes._4.OffenseCodeType offenseCode = (gov.fbi.cjis.nibrs.nibrs_codes._4.OffenseCodeType) offenseCodeEl
								.getValue();
						textArea.append("Offense UCR Code: " + offenseCode.getValue() + "\n");
					} else if (offenseCodeEl.getValue() instanceof Object) {
						Object offenseCode = (Object) offenseCodeEl.getValue();
						textArea.append("Offense UCR Code(Object): " + offenseCode.toString() + "\n");
					} // if else if
				} // if (offense.getOffenseDesignation() != null) {
			}
			private void setOffenseCode(String offCode) {
				OffenseCodeType offenseCode = nibrsCodes4ObjectFactory.createOffenseCodeType();
				switch (offCode) {
				case "Murder & Nonnegligent Manslaughter":
					offenseCode.setValue("09A");
					break;
				case "Negligent Manslaughter":
					offenseCode.setValue("09B");
					break;
				case "Justifiable Homicide":
					offenseCode.setValue("09C");
					break;
				case "Kidnapping/Abduction":
					offenseCode.setValue("100");
					break;
				case "Rape":
					offenseCode.setValue("11A");
					break;
				case "Sodomy":
					offenseCode.setValue("11B");
					break;
				case "Sexual Assault With An Object":
					offenseCode.setValue("11C");
					break;
				case "Fondling":
					offenseCode.setValue("11D");
					break;
				case "Robbery":
					offenseCode.setValue("120");
					break;
				case "Aggravated Assault":
					offenseCode.setValue("13A");
					break;
				case "Simple Assault":
					offenseCode.setValue("13B");
					break;
				case "Intimidation":
					offenseCode.setValue("13C");
					break;
				case "Arson":
					offenseCode.setValue("200");
					break;
				case "Extortion/Blackmail":
					offenseCode.setValue("210");
					break;
				case "Burglary/Breaking & Entering":
					offenseCode.setValue("220");
					break;
				case "Pocket-picking":
					offenseCode.setValue("23A");
					break;
				case "Purse-snatching":
					offenseCode.setValue("23B");
					break;
				case "Shoplifting":
					offenseCode.setValue("90F");
					break;
				case "Theft From Building":
					offenseCode.setValue("23D");
					break;
				case "Theft From Coin-Operated Machine":
					offenseCode.setValue("23E");
					break;
				case "Theft From Motor Vehicle":
					offenseCode.setValue("23F");
					break;
				case "Theft of Motor Vehicle Parts":
					offenseCode.setValue("23G");
					break;
				case "All Other Larceny":
					offenseCode.setValue("23H");
					break;
				case "Motor Vehicle Theft":
					offenseCode.setValue("240");
					break;
				case "Counterfeiting/Forgery":
					offenseCode.setValue("250");
					break;
				case "False Pretenses/Swindle/Confidence Game":
					offenseCode.setValue("26A");
					break;
				case "Credit Card/Automated Teller Machine Fraud":
					offenseCode.setValue("26B");
					break;
				case "Impersonation":
					offenseCode.setValue("26C");
					break;
				case "Welfare Fraud":
					offenseCode.setValue("26D");
					break;
				case "Wire Fraud":
					offenseCode.setValue("26E");
					break;
				case "Identity Theft":
					offenseCode.setValue("26F");
					break;
				case "Hacking/Computer Invasion":
					offenseCode.setValue("26G");
					break;
				case "Embezzlement":
					offenseCode.setValue("270");
					break;
				case "Stolen Property Offenses":
					offenseCode.setValue("280");
					break;
				case "Destruction/Damage/Vandalism of Property":
					offenseCode.setValue("290");
					break;
				case "Drug/Narcotic Violations":
					offenseCode.setValue("35A");
					break;
				case "Drug Equipment Violations":
					offenseCode.setValue("35B");
					break;
				case "Incest":
					offenseCode.setValue("36A");
					break;
				case "Statutory Rape":
					offenseCode.setValue("36B");
					break;
				case "Pornography/Obscene Material":
					offenseCode.setValue("370");
					break;
				case "Betting/Wagering":
					offenseCode.setValue("39A");
					break;
				case "Operating/Promoting/Assisting Gambling":
					offenseCode.setValue("39B");
					break;
				case "Gambling Equipment Violation":
					offenseCode.setValue("39C");
					break;
				case "Sports Tampering":
					offenseCode.setValue("39D");
					break;
				case "Prostitution":
					offenseCode.setValue("40A");
					break;
				case "Assisting or Promoting Prostitution":
					offenseCode.setValue("40B");
					break;
				case "Purchasing Prostitution":
					offenseCode.setValue("40C");
					break;
				case "Bribery":
					offenseCode.setValue("510");
					break;
				case "Weapon Law Violations":
					offenseCode.setValue("520");
					break;
				case "Human Trafficking, Commercial Sex Acts":
					offenseCode.setValue("64A");
					break;
				case "Human Trafficking, Involuntary Servitude":
					offenseCode.setValue("64B");
					break;
				case "Animal Cruelty":
					offenseCode.setValue("720");
					break;
				case "Bad Checks":
					offenseCode.setValue("90A");
					break;
				case "Curfew/Loitering/Vagrancy Violations":
					offenseCode.setValue("90B");
					break;
				case "Disorderly Conduct":
					offenseCode.setValue("90C");
					break;
				case "Driving Under the Influence":
					offenseCode.setValue("90D");
					break;
				case "Drunkenness":
					offenseCode.setValue("90E");
					break;
				case "Family Offenses, Nonviolent":
					offenseCode.setValue("90F");
					break;
				case "Liquor Law Violations":
					offenseCode.setValue("90G");
					break;
				case "Peeping Tom":
					offenseCode.setValue("90H");
					break;
				case "Trespass of Real Property":
					offenseCode.setValue("90J");
					break;
				case "All Other Offenses":
					offenseCode.setValue("90Z");
					break;

				}
				JAXBElement<OffenseCodeType> offenseCodeElement = nibrs4ObjectFactory.createOffenseUCRCode(offenseCode);
				offense.setOffenseDesignation(offenseCodeElement);
			}

			// Type Criminal Activity/Gang Information
			private void getCriminalActivity(OffenseType off) {
				List<JAXBElement<?>> crimActList = off.getCriminalActivityCategory();
				for (Iterator<JAXBElement<?>> iterator = crimActList.iterator(); iterator.hasNext();) {
					JAXBElement<?> element = (JAXBElement<?>) iterator.next();
					if (element.getValue() instanceof CriminalActivityCategoryCodeType) {
						CriminalActivityCategoryCodeType crimActCode = (CriminalActivityCategoryCodeType) element
								.getValue();
						textArea.append(
								"Type Criminal Activity/Gang Information: " + crimActCode.getValue() + "\n");
					} else if (element.getValue() instanceof Object) {
						Object crimActCode = (Date) element.getValue();
						textArea.append(
								"Type Criminal Activity/Gang Information(Object):" + crimActCode.toString() + "\n");
					} // if else if
				} /// for
			}
			private void setCriminalActivity(String crimActivity) {
				if (crimActivity != "n/a"){
					CriminalActivityCategoryCodeType crimActCat = new CriminalActivityCategoryCodeType();
					CriminalActivityCategoryCodeSimpleType crimActCatSimple;

					switch (crimActivity) {
					case "Simple/Gross Neglect":
						crimActCatSimple = CriminalActivityCategoryCodeSimpleType.A;
						crimActCat.setValue(crimActCatSimple);
						break;
					case "Buying/Receiving":
						crimActCatSimple = CriminalActivityCategoryCodeSimpleType.B;
						crimActCat.setValue(crimActCatSimple);
						break;
					case "Cultivating/Manufacturing/Publishing":
						crimActCatSimple = CriminalActivityCategoryCodeSimpleType.C;
						crimActCat.setValue(crimActCatSimple);
						break;
					case "Distributing/Selling":
						crimActCatSimple = CriminalActivityCategoryCodeSimpleType.D;
						crimActCat.setValue(crimActCatSimple);
						break;
					case "Exploiting Children":
						crimActCatSimple = CriminalActivityCategoryCodeSimpleType.E;
						crimActCat.setValue(crimActCatSimple);
						break;
					case "Organized Abuse":
						crimActCatSimple = CriminalActivityCategoryCodeSimpleType.F;
						crimActCat.setValue(crimActCatSimple);
						break;
					case "Other Gang":
						crimActCatSimple = CriminalActivityCategoryCodeSimpleType.G;
						crimActCat.setValue(crimActCatSimple);
						break;
					case "Intentional Abuse and Torture":
						crimActCatSimple = CriminalActivityCategoryCodeSimpleType.I;
						crimActCat.setValue(crimActCatSimple);
						break;
					case "Juvenile Gang":
						crimActCatSimple = CriminalActivityCategoryCodeSimpleType.J;
						crimActCat.setValue(crimActCatSimple);
						break;
					case "None/Unknown":
						crimActCatSimple = CriminalActivityCategoryCodeSimpleType.N;
						crimActCat.setValue(crimActCatSimple);
						break;
					case "Operating/Promoting/Assisting":
						crimActCatSimple = CriminalActivityCategoryCodeSimpleType.O;
						crimActCat.setValue(crimActCatSimple);
						break;
					case "Possessing/Concealing":
						crimActCatSimple = CriminalActivityCategoryCodeSimpleType.P;
						crimActCat.setValue(crimActCatSimple);
						break;
					case "Animal Sexual Abuse":
						crimActCatSimple = CriminalActivityCategoryCodeSimpleType.S;
						crimActCat.setValue(crimActCatSimple);
						break;
					case "Transporting/Transmitting/Importing":
						crimActCatSimple = CriminalActivityCategoryCodeSimpleType.T;
						crimActCat.setValue(crimActCatSimple);
						break;
					case "Using/Consuming":
						crimActCatSimple = CriminalActivityCategoryCodeSimpleType.U;
						crimActCat.setValue(crimActCatSimple);
						break;
					}
					JAXBElement<CriminalActivityCategoryCodeType> crimActCatElement = nibrs4ObjectFactory
						.createCriminalActivityCategoryCode(crimActCat);
					offense.getCriminalActivityCategory().add(crimActCatElement);
				}
			}

			// Bias Motivation Code
			private void getBiasMotivationCode(OffenseType off) {
				List<JAXBElement<?>> biasList = off.getOffenseFactorBiasMotivation();
				for (Iterator<JAXBElement<?>> iterator = biasList.iterator(); iterator.hasNext();) {
					JAXBElement<?> element = (JAXBElement<?>) iterator.next();
					if (element.getValue() instanceof BiasMotivationCodeType) {
						BiasMotivationCodeType biasCode = (BiasMotivationCodeType) element.getValue();
						textArea.append("Bias Motivation Code: " + biasCode.getValue().value() + "\n");
					} else if (element.getValue() instanceof Object) {
						Object biasCode = (Date) element.getValue();
						textArea.append("Bias Motivation Code:" + biasCode.toString() + "\n");
					} // if else if
				} /// for
			}
			private void setBiasMotivationCode(String[] biasMotCodes) {
				for (int i = 0; i<=4; i++) {
					BiasMotivationCodeType biasMotivation = new BiasMotivationCodeType();
					BiasMotivationCodeSimpleType biasMotSimple;
					if (!biasMotCodes[i].isEmpty()) {
						switch (biasMotCodes[i]) {
						case "ANTIWHITE":
							biasMotSimple = BiasMotivationCodeSimpleType.ANTIWHITE;
							biasMotivation.setValue(biasMotSimple);
							break;
						case "ANTIBLACK_AFRICAN AMERICAN":
							biasMotSimple = BiasMotivationCodeSimpleType.ANTIBLACK_AFRICAN_AMERICAN;
							biasMotivation.setValue(biasMotSimple);
							break;
						case "ANTIAMERICAN INDIAN_ ALASKAN NATIVE":
							biasMotSimple = BiasMotivationCodeSimpleType.ANTIAMERICAN_INDIAN_ALASKAN_NATIVE;
							biasMotivation.setValue(biasMotSimple);
							break;
						case "ANTIASIAN":
							biasMotSimple = BiasMotivationCodeSimpleType.ANTIASIAN;
							biasMotivation.setValue(biasMotSimple);
							break;
						case "ANTIMULTIRACIAL GROUP":
							biasMotSimple = BiasMotivationCodeSimpleType.ANTIMULTIRACIAL_GROUP;
							biasMotivation.setValue(biasMotSimple);
							break;
						case "ANTINATIVEHAWAIIAN_OTHERPACIFICISLANDER":
							biasMotSimple = BiasMotivationCodeSimpleType.ANTINATIVEHAWAIIAN_OTHERPACIFICISLANDER;
							biasMotivation.setValue(biasMotSimple);
							break;
						case "ANTIJEWISH":
							biasMotSimple = BiasMotivationCodeSimpleType.ANTIJEWISH;
							biasMotivation.setValue(biasMotSimple);
							break;
						case "ANTICATHOLIC":
							biasMotSimple = BiasMotivationCodeSimpleType.ANTICATHOLIC;
							biasMotivation.setValue(biasMotSimple);
							break;
						case "ANTIPROTESTANT":
							biasMotSimple = BiasMotivationCodeSimpleType.ANTIPROTESTANT;
							biasMotivation.setValue(biasMotSimple);
							break;
						case "ANTIISLAMIC":
							biasMotSimple = BiasMotivationCodeSimpleType.ANTIISLAMIC;
							biasMotivation.setValue(biasMotSimple);
							break;
						case "ANTIOTHER RELIGION":
							biasMotSimple = BiasMotivationCodeSimpleType.ANTIOTHER_RELIGION;
							biasMotivation.setValue(biasMotSimple);
							break;
						case "ANTIMULTIRELIGIOUS GROUP":
							biasMotSimple = BiasMotivationCodeSimpleType.ANTIMULTIRELIGIOUS_GROUP;
							biasMotivation.setValue(biasMotSimple);
							break;
						case "ANTIATHEIST_AGNOSTIC":
							biasMotSimple = BiasMotivationCodeSimpleType.ANTIATHEIST_AGNOSTIC;
							biasMotivation.setValue(biasMotSimple);
							break;
						case "ANTIMORMON":
							biasMotSimple = BiasMotivationCodeSimpleType.ANTIMORMON;
							biasMotivation.setValue(biasMotSimple);
							break;
						case "ANTIJEHOVAHWITNESS":
							biasMotSimple = BiasMotivationCodeSimpleType.ANTIJEHOVAHWITNESS;
							biasMotivation.setValue(biasMotSimple);
							break;
						case "ANTIARAB":
							biasMotSimple = BiasMotivationCodeSimpleType.ANTIARAB;
							biasMotivation.setValue(biasMotSimple);
							break;
						case "ANTIHISPANIC_LATINO":
							biasMotSimple = BiasMotivationCodeSimpleType.ANTIHISPANIC_LATINO;
							biasMotivation.setValue(biasMotSimple);
							break;
						case "ANTIOTHER ETHNICITY_NATIONAL ORIGIN":
							biasMotSimple = BiasMotivationCodeSimpleType.ANTIOTHER_ETHNICITY_NATIONAL_ORIGIN;
							biasMotivation.setValue(biasMotSimple);
							break;
						case "ANTIMALE HOMOSEXUAL":
							biasMotSimple = BiasMotivationCodeSimpleType.ANTIMALE_HOMOSEXUAL;
							biasMotivation.setValue(biasMotSimple);
							break;
						case "ANTIFEMALE HOMOSEXUAL":
							biasMotSimple = BiasMotivationCodeSimpleType.ANTIFEMALE_HOMOSEXUAL;
							biasMotivation.setValue(biasMotSimple);
							break;
						case "ANTIHETEROSEXUAL":
							biasMotSimple = BiasMotivationCodeSimpleType.ANTIHETEROSEXUAL;
							biasMotivation.setValue(biasMotSimple);
							break;
						case "ANTIBISEXUAL":
							biasMotSimple = BiasMotivationCodeSimpleType.ANTIBISEXUAL;
							biasMotivation.setValue(biasMotSimple);
							break;
						case "ANTIPHYSICAL DISABILITY":
							biasMotSimple = BiasMotivationCodeSimpleType.ANTIPHYSICAL_DISABILITY;
							biasMotivation.setValue(biasMotSimple);
							break;
						case "ANTIMENTAL DISABILITY":
							biasMotSimple = BiasMotivationCodeSimpleType.ANTIMENTAL_DISABILITY;
							biasMotivation.setValue(biasMotSimple);
							break;
						case "ANTIMALE":
							biasMotSimple = BiasMotivationCodeSimpleType.ANTIMALE;
							biasMotivation.setValue(biasMotSimple);
							break;
						case "ANTIFEMALE":
							biasMotSimple = BiasMotivationCodeSimpleType.ANTIFEMALE;
							biasMotivation.setValue(biasMotSimple);
							break;
						case "ANTITRANSGENDER":
							biasMotSimple = BiasMotivationCodeSimpleType.ANTITRANSGENDER;
							biasMotivation.setValue(biasMotSimple);
							break;
						case "ANTIGENDER_NONCONFORMING":
							biasMotSimple = BiasMotivationCodeSimpleType.ANTIGENDER_NONCONFORMING;
							biasMotivation.setValue(biasMotSimple);
							break;
						case "ANTIEASTERNORTHODOX":
							biasMotSimple = BiasMotivationCodeSimpleType.ANTIEASTERNORTHODOX;
							biasMotivation.setValue(biasMotSimple);
							break;
						case "ANTIOTHER CHRISTIAN":
							biasMotSimple = BiasMotivationCodeSimpleType.ANTIOTHER_CHRISTIAN;
							biasMotivation.setValue(biasMotSimple);
							break;
						case "ANTIBUDDHIST":
							biasMotSimple = BiasMotivationCodeSimpleType.ANTIBUDDHIST;
							biasMotivation.setValue(biasMotSimple);
							break;
						case "ANTIHINDU":
							biasMotSimple = BiasMotivationCodeSimpleType.ANTIHINDU;
							biasMotivation.setValue(biasMotSimple);
							break;
						case "ANTISIKH":
							biasMotSimple = BiasMotivationCodeSimpleType.ANTISIKH;
							biasMotivation.setValue(biasMotSimple);
							break;
						case "NONE":
							biasMotSimple = BiasMotivationCodeSimpleType.NONE;
							biasMotivation.setValue(biasMotSimple);
							break;
						case "UNKNOWN":
							biasMotSimple = BiasMotivationCodeSimpleType.UNKNOWN;
							biasMotivation.setValue(biasMotSimple);
							break;
						}
						JAXBElement<BiasMotivationCodeType> biasMotivationElement = jxdm5ObjectFactory.createOffenseFactorBiasMotivationCode(biasMotivation);
						offense.getOffenseFactorBiasMotivation().add(biasMotivationElement);
					}
				}
			}

			// Structures Entered Quantity
			private void getStructuresEnteredQuant(OffenseType off) {
				if (off.getOffenseStructuresEnteredQuantity() != null) {
					textArea.append("Offense Structures Entered Quantity: "
							+ off.getOffenseStructuresEnteredQuantity().getValue() + "\n");
				}
			}
			private void setStructuresEnteredQuant(String quant) {
				QuantityType structuresEntered = new QuantityType();
				if (!quant.isEmpty()) {
					BigDecimal num = new BigDecimal(Integer.parseInt(quant));
					structuresEntered.setValue(num);
					offense.setOffenseStructuresEnteredQuantity(structuresEntered);
				}
			}

			// Offense Factor Code
			private void getOffenseFactorCode(OffenseType off) {
				List<OffenseFactorType> offenseFactorList = off.getOffenseFactor();
				for (Iterator<OffenseFactorType> iterator = offenseFactorList.iterator(); iterator.hasNext();) {
					OffenseFactorType element = (OffenseFactorType) iterator.next();
					JAXBElement<?> offenseFactorEl = element.getOffenseFactorDesignation();
					if (offenseFactorEl.getValue() instanceof IncidentFactorCodeType) {
						IncidentFactorCodeType offenseCode = (IncidentFactorCodeType) offenseFactorEl.getValue();
						textArea.append("Offense Factor Code: " + offenseCode.getValue().toString() + "\n");
					} else if (offenseFactorEl.getValue() instanceof Object) {
						Object offenseCode = (Object) offenseFactorEl.getValue();
						textArea.append("Offense Factor Code: " + offenseCode.toString() + "\n");
					} // if else if
				} // for
			}
			private void setOffenseFactorCode(String offFact) {
				OffenseFactorType offenseFactor = new OffenseFactorType();
				IncidentFactorCodeType incidentFactor = new IncidentFactorCodeType();
				IncidentFactorCodeSimpleType incidentFactorSimple;
				switch (offFact) {
				case "Alcohol":
					incidentFactorSimple = IncidentFactorCodeSimpleType.A;
					incidentFactor.setValue(incidentFactorSimple);
					break;
				case "Computer Equipment":
					incidentFactorSimple = IncidentFactorCodeSimpleType.C;
					incidentFactor.setValue(incidentFactorSimple);
					break;
				case "Drugs or Narcotics":
					incidentFactorSimple = IncidentFactorCodeSimpleType.D;
					incidentFactor.setValue(incidentFactorSimple);
					break;
				case "Not Applicable":
					incidentFactorSimple = IncidentFactorCodeSimpleType.N;
					incidentFactor.setValue(incidentFactorSimple);
					break;
				}
				JAXBElement<IncidentFactorCodeType> offenseFactorElement = jxdm5ObjectFactory
						.createOffenseFactorCode(incidentFactor);
				offenseFactor.setOffenseFactorDesignation(offenseFactorElement);
				offense.getOffenseFactor().add(offenseFactor);
			}

			// Offense Passage Point Method Code
			private void getPassagePoint(OffenseType off) {
				if (offense.getOffenseEntryPoint() != null) {
					JAXBElement<?> offenseEntryPoint = offense.getOffenseEntryPoint().getPassagePointMethod();

					if (offenseEntryPoint.getValue() instanceof MethodOfEntryCodeType) {
						MethodOfEntryCodeType entryCode = (MethodOfEntryCodeType) offenseEntryPoint.getValue();
						textArea.append("Method of Entry:" + entryCode.getValue().toString() + "\n");
					} else if (offenseEntryPoint.getValue() instanceof Object) {
						Object entryCode = (Object) offenseEntryPoint.getValue();
						textArea.append("Method of Entry:" + entryCode.toString() + "\n");
					} // if else if
				} // if
			}
			private void setPassagePoint(String passPnt) {
				if (passPnt != "n/a"){
					PassagePointType passagePoint = new PassagePointType();
					MethodOfEntryCodeType entryMethod = new MethodOfEntryCodeType();
					MethodOfEntryCodeSimpleType entryMethodSimple;
					switch (passPnt) {
					case "Force":
						entryMethodSimple = MethodOfEntryCodeSimpleType.F;
						entryMethod.setValue(entryMethodSimple);
						break;
					case "No Force":
						entryMethodSimple = MethodOfEntryCodeSimpleType.N;
						entryMethod.setValue(entryMethodSimple);
						break;
					}
					JAXBElement<MethodOfEntryCodeType> passagePointElement = jxdm5ObjectFactory
							.createPassagePointMethodCode(entryMethod);
					passagePoint.setPassagePointMethod(passagePointElement);
					offense.setOffenseEntryPoint(passagePoint);
				}
			}

			// Force Category Code
			private void getForceCategoryCode(OffenseType off) {
				if (offense.getOffenseForce() != null) {
					List<ForceType> offenseCodeList = off.getOffenseForce();
					for (Iterator<ForceType> iterator = offenseCodeList.iterator(); iterator.hasNext();) {
						ForceType element = (ForceType) iterator.next();
						JAXBElement<?> offenseCodeElement = element.getForceCategory();

						if (offenseCodeElement.getValue() instanceof ForceCategoryCodeType) {
							ForceCategoryCodeType offenseCode = (ForceCategoryCodeType) offenseCodeElement.getValue();
							textArea.append("Force Category Code: " + offenseCode.getValue() + "\n");
						} else if (offenseCodeElement.getValue() instanceof Object) {
							Object offenseCode = (Object) offenseCodeElement.getValue();
							textArea.append("Force Category Code: " + offenseCode.toString() + "\n");
						} // if else if
					} // for
				} // if
			}
			private void setForceCategoryCode(String[] catInput) {
				for (int i=0; i<=2; i++){
					ForceType offenseForce = new ForceType();
					ForceCategoryCodeType forceCatCode = new ForceCategoryCodeType();
					if (!catInput[i].isEmpty()){
						switch (catInput[i]) {
						case "Firearm (type not stated)":
							forceCatCode.setValue("11");
							break;
						case "Automatic Firearm (type not stated)":
							forceCatCode.setValue("11A");
							break;
						case "Handgun":
							forceCatCode.setValue("12");
							break;
						case "Automatic Handgun":
							forceCatCode.setValue("12A");
							break;
						case "Rifle":
							forceCatCode.setValue("13");
							break;
						case "Automatic Rifle":
							forceCatCode.setValue("13A");
							break;
						case "Shotgun":
							forceCatCode.setValue("14");
							break;
						case "Automatic Shotgun":
							forceCatCode.setValue("14A");
							break;
						case "Other Firearm":
							forceCatCode.setValue("15");
							break;
						case "Other Automatic Firearm":
							forceCatCode.setValue("15A");
							break;
						case "Lethal Cutting Instument":
							forceCatCode.setValue("20");
							break;
						case "Club/Blackjack/Brass Knuckles":
							forceCatCode.setValue("30");
							break;
						case "Motor Vehicle":
							forceCatCode.setValue("35");
							break;
						case "Personal Weapons":
							forceCatCode.setValue("40");
							break;
						case "Poison":
							forceCatCode.setValue("50");
							break;
						case "Explosives":
							forceCatCode.setValue("60");
							break;
						case "Fire/Incendiary Device":
							forceCatCode.setValue("65");
							break;
						case "Drugs/Narcotics/Sleeping Pills":
							forceCatCode.setValue("70");
							break;
						case "Asphyxiation":
							forceCatCode.setValue("85");
							break;
						case "Other":
							forceCatCode.setValue("90");
							break;
						case "Unknown":
							forceCatCode.setValue("95");
							break;
						case "None":
							forceCatCode.setValue("99");
							break;
						}//switch
						JAXBElement<ForceCategoryCodeType> offenseForceElement = jxdm5ObjectFactory
								.createForceCategoryCode(forceCatCode);
						offenseForce.setForceCategory(offenseForceElement);
						offense.getOffenseForce().add(offenseForce);
					}//if
				}//for
			}

			// Offense Attempted Indicator
			private void getOffenseAttemptedInd(OffenseType off) {
				gov.niem.release.niem.proxy.xsd._3.Boolean offenseCompleted = off.getOffenseAttemptedIndicator();
				if (offenseCompleted.isValue()) {
					textArea.append("Attempted Ind: TRUE \n");
				} else {
					textArea.append("Attempted Ind: FALSE \n");
				}
			}
			private void setOffenseAttemptedInd(String offInd) {
				gov.niem.release.niem.proxy.xsd._3.Boolean offenseAttempted = new gov.niem.release.niem.proxy.xsd._3.Boolean();
				if (offInd == "false")
					offenseAttempted.setValue(false);
				else
					offenseAttempted.setValue(true);
				offense.setOffenseAttemptedIndicator(offenseAttempted);
			}

			private OffenseType createOffense(String[] offValues) {

				String[] forceCatCodes = new String[3];
				String[] biasMotCodes = new String[5];
				
				for (int i=0; i<=2; i++){
					forceCatCodes[i] = offValues[i+11];
				}
				for (int i=0; i<=4; i++){
					biasMotCodes[i] = offValues[i+3];
				}
				
				
				setID(offValues[0]);
				setOffenseCode(offValues[1]);
				setCriminalActivity(offValues[2]);
				setBiasMotivationCode(biasMotCodes);
				setStructuresEnteredQuant(offValues[8]);
				setOffenseFactorCode(offValues[9]);
				setPassagePoint(offValues[10]);
				setForceCategoryCode(forceCatCodes);
				setOffenseAttemptedInd(offValues[14]);

				return offense;
			}
		};
		private class Location {

			LocationType location = new LocationType();

			private void unmarshallLocation(ReportType report) {
				if (report.getLocation() != null) {
					ArrayList<LocationType> locationList = (ArrayList<LocationType>) report.getLocation();
					textArea.append("***********Location ************ \n");
					locationList.forEach((location) -> {

						textArea.append("**Location** \n");
						getID(location);
						getLocationCategoryCode(location);

					});
				}
			}

			// Location ID
			private void getID(LocationType loc) {
				textArea.append("Location ID: " + loc.getId() + "\n");
			}
			private void setID(String locID) {
				location.setId(locID);
			}

			// Location Category Code
			private void getLocationCategoryCode(LocationType loc) {
				if (loc.getLocationCategory() != null) {
					JAXBElement<?> locationCodeElement = loc.getLocationCategory();
					if (locationCodeElement.getValue() instanceof LocationCategoryCodeType) {
						LocationCategoryCodeType locationCode = (LocationCategoryCodeType) locationCodeElement
								.getValue();
						textArea.append("Location Category: " + locationCode.getValue() + "\n");
					} else if (locationCodeElement
							.getValue() instanceof gov.fbi.cjis.nibrs.nibrs_codes._4.LocationCategoryCodeType) {
						gov.fbi.cjis.nibrs.nibrs_codes._4.LocationCategoryCodeType locationCode = (gov.fbi.cjis.nibrs.nibrs_codes._4.LocationCategoryCodeType) locationCodeElement
								.getValue();
						textArea.append("Location Category: " + locationCode.getValue() + "\n");
					} else if (locationCodeElement.getValue() instanceof Object) {
						Object locationCode = (Object) locationCodeElement.getValue();
						textArea.append("Location Category: " + locationCode.toString() + "\n");
					} // if else if
				} // if
			}
			private void setLocationCategoryCode(String code) {
				// Location Category Code
				LocationCategoryCodeType locationCode = new LocationCategoryCodeType();
				switch (code) {
				case "Air/Bus/Train Terminal":
					locationCode.setValue("01");
					break;
				case "Bank/Savings and Loan":
					locationCode.setValue("02");
					break;
				case "Bar/Nightclub":
					locationCode.setValue("03");
					break;
				case "Church/Synagogue/Temple/Mosque":
					locationCode.setValue("04");
					break;
				case "Commercial/Office Building":
					locationCode.setValue("05");
					break;
				case "Construction Site":
					locationCode.setValue("06");
					break;
				case "Convenience Store":
					locationCode.setValue("07");
					break;
				case "Department/Discount Store":
					locationCode.setValue("08");
					break;
				case "Drug Store/Doctor's Office/Hospital":
					locationCode.setValue("09");
					break;
				case "Field/Woods":
					locationCode.setValue("10");
					break;
				case "Government/Public Building":
					locationCode.setValue("11");
					break;
				case "Grocery/Supermarket":
					locationCode.setValue("12");
					break;
				case "Highway/Road/Alley/Street/Sidewalk":
					locationCode.setValue("13");
					break;
				case "Hotel/Motel/Etc":
					locationCode.setValue("14");
					break;
				case "Jail/Prison/Penitentiary/Corrections Facility":
					locationCode.setValue("15");
					break;
				case "Lake/Waterway/Beach":
					locationCode.setValue("16");
					break;
				case "Liquor Store":
					locationCode.setValue("17");
					break;
				case "Parking/Drop Lot/Garage":
					locationCode.setValue("18");
					break;
				case "Rental Storage Facility":
					locationCode.setValue("19");
					break;
				case "Residence/Home":
					locationCode.setValue("20");
					break;
				case "Restaurant":
					locationCode.setValue("21");
					break;
				case "School/College":
					locationCode.setValue("22");
					break;
				case "Service/Gas Station":
					locationCode.setValue("23");
					break;
				case "Specialty Store":
					locationCode.setValue("24");
					break;
				case "Other/Unknown":
					locationCode.setValue("25");
					break;
				case "Abandoned/Condemned Structure":
					locationCode.setValue("37");
					break;
				case "Amusement Park":
					locationCode.setValue("38");
					break;
				case "Arena/Stadium/Fairgrounds/Coliseum":
					locationCode.setValue("39");
					break;
				case "ATM Separate from Bank":
					locationCode.setValue("40");
					break;
				case "Auto Dealership New/Used":
					locationCode.setValue("41");
					break;
				case "Camp/Campground":
					locationCode.setValue("42");
					break;
				case "Daycare Facility":
					locationCode.setValue("44");
					break;
				case "Dock/Wharf/Freight/Modal Terminal":
					locationCode.setValue("45");
					break;
				case "Farm Facility":
					locationCode.setValue("46");
					break;
				case "Gambling Facility/Casino/Race Track":
					locationCode.setValue("47");
					break;
				case "Industrial Site":
					locationCode.setValue("48");
					break;
				case "Military Installation":
					locationCode.setValue("49");
					break;
				case "Park/Playground":
					locationCode.setValue("50");
					break;
				case "Rest Area":
					locationCode.setValue("51");
					break;
				case "School-College University":
					locationCode.setValue("52");
					break;
				case "School-Elementary/Secondary":
					locationCode.setValue("53");
					break;
				case "Shelter-Mission/Homeless":
					locationCode.setValue("54");
					break;
				case "Shopping Mall":
					locationCode.setValue("55");
					break;
				case "Tribal Lands":
					locationCode.setValue("56");
					break;
				case "Community Center":
					locationCode.setValue("57");
					break;
				}
				JAXBElement<LocationCategoryCodeType> locationCatElement = nibrs4ObjectFactory
						.createLocationCategoryCode(locationCode);
				location.setLocationCategory(locationCatElement);
			}

			private LocationType createLocation(String[] loc) {

				setID(loc[0]);
				setLocationCategoryCode(loc[1]);

				return location;
			}
		};
		private class Item {

			ItemType item = niemCore3ObjectFactory.createItemType();

			private void unmarshallItem(ReportType report) {
				if (report.getItem() != null) {
					ArrayList<ItemType> itemList = (ArrayList<ItemType>) report.getItem();
					textArea.append("***********Item ************ \n");
					itemList.forEach((item) -> {

						textArea.append("**Item** \n");
						getID(item);
						getItemStatus(item);
						getItemValue(item);
						getItemValueDate(item);
						getPropertyCategoryCode(item);
						getItemQuantity(item);
					});
				}
			}

			// Item ID
			private void getID(ItemType i) {
				textArea.append("Item ID: " + i.getId() + "\n");
			}
			private void setID(String id) {
				item.setId(id);
			}

			// Item Status Code
			private void getItemStatus(ItemType i) {
				JAXBElement<?> itemStatusElement = i.getItemStatus().getStatusDesignator();
				if (itemStatusElement.getValue() instanceof ItemStatusCodeType) {
					ItemStatusCodeType itemStatus = (ItemStatusCodeType) itemStatusElement.getValue();
					textArea.append("Item Status: " + itemStatus.getValue().value() + "\n");
				} else if (itemStatusElement.getValue() instanceof Object) {
					textArea.append("Item Status: " + "\n");
				}
			}
			private void setItemStatus(String status) {
				StatusType itemStatus = new StatusType();
				ItemStatusCodeType itemStatusCode = new ItemStatusCodeType();
				ItemStatusCodeSimpleType itemStatusCodeSimple;
				switch (status) {
				case "NONE":
					itemStatusCodeSimple = ItemStatusCodeSimpleType.NONE;
					itemStatusCode.setValue(itemStatusCodeSimple);
					break;
				case "BURNED":
					itemStatusCodeSimple = ItemStatusCodeSimpleType.BURNED;
					itemStatusCode.setValue(itemStatusCodeSimple);
					break;
				case "COUNTERFEITED":
					itemStatusCodeSimple = ItemStatusCodeSimpleType.COUNTERFEITED;
					itemStatusCode.setValue(itemStatusCodeSimple);
					break;
				case "DESTROYED_DAMAGED_VANDALIZED":
					itemStatusCodeSimple = ItemStatusCodeSimpleType.DESTROYED_DAMAGED_VANDALIZED;
					itemStatusCode.setValue(itemStatusCodeSimple);
					break;
				case "RECOVERED":
					itemStatusCodeSimple = ItemStatusCodeSimpleType.RECOVERED;
					itemStatusCode.setValue(itemStatusCodeSimple);
					break;
				case "SEIZED":
					itemStatusCodeSimple = ItemStatusCodeSimpleType.SEIZED;
					itemStatusCode.setValue(itemStatusCodeSimple);
					break;
				case "STOLEN":
					itemStatusCodeSimple = ItemStatusCodeSimpleType.STOLEN;
					itemStatusCode.setValue(itemStatusCodeSimple);
					break;
				case "UNKNOWN":
					itemStatusCodeSimple = ItemStatusCodeSimpleType.UNKNOWN;
					itemStatusCode.setValue(itemStatusCodeSimple);
					break;
				}
				JAXBElement<ItemStatusCodeType> itemStatusCodeElement = cjis1ObjectFactory
						.createItemStatusCode(itemStatusCode);
				itemStatus.setStatusDesignator(itemStatusCodeElement);
				item.setItemStatus(itemStatus);
			}

			/////////////
			// Item Value
			/////////////

			// Item Value Amount
			private void getItemValue(ItemType i) {
				if (i.getItemValue() != null) {
					textArea.append(
							"Item Value: " + i.getItemValue().getItemValueAmount().getAmount().getValue() + "\n");
				}
			}
			private AmountType setItemAmount(String value) {
				AmountType itemAmount = niemCore3ObjectFactory.createAmountType();
				Decimal amount = new Decimal();
				if (!value.isEmpty()) {
					BigDecimal amountBigDec = new BigDecimal(value);
					amount.setValue(amountBigDec);
					itemAmount.setAmount(amount);
					return itemAmount;

				} else {
					BigDecimal amountBigDec = new BigDecimal("0");
					amount.setValue(amountBigDec);
					itemAmount.setAmount(amount);
					return itemAmount;
				}
			}

			// Item Value Date
			private void getItemValueDate(ItemType i) {
				if (i.getItemValue() != null) {
					if (i.getItemValue().getItemValueDate() != null) {
						List<JAXBElement<?>> itemDateList = i.getItemValue().getItemValueDate().getDateRepresentation();
						for (Iterator<JAXBElement<?>> iterator = itemDateList.iterator(); iterator.hasNext();) {
							JAXBElement<?> element = (JAXBElement<?>) iterator.next();
							if (element.getValue() instanceof Date) {
								Date date = (Date) element.getValue();
								textArea.append("Date Recovered: " + date.getValue().toString() + "\n");
							} else if (element.getValue() instanceof DateTime) {
								DateTime date = (DateTime) element.getValue();
								textArea.append("Date Recovered: " + date.getValue().toString() + "\n");
							} else if (element.getValue() instanceof GYearMonth) {
								GYearMonth date = (GYearMonth) element.getValue();
								textArea.append("Date Recovered: " + date.getValue().toString() + "\n");
							} else if (element.getValue() instanceof Object) {
								Object date = (Date) element.getValue();
								textArea.append("Date Recovered: " + date.toString() + "\n");
							}
						}
					}
				}
			}
			private DateType setItemDate(String year, String month, String day, String hour, String min,
					String sec) {
				DateTime dateTime = new DateTime();
				if (!year.isEmpty() && !month.isEmpty() && !day.isEmpty() && !hour.isEmpty() && !min.isEmpty()
						&& !sec.isEmpty()) {
					dateTime.setValue(
							createGregCal(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day),
									Integer.parseInt(hour), Integer.parseInt(min), Integer.parseInt(sec)));
					DateType dateType = new DateType();
					JAXBElement<DateTime> dateTypeElement = niemCore3ObjectFactory.createDateTime(dateTime);
					dateType.getDateRepresentation().add(dateTypeElement);
					return dateType;
				} else if (!year.isEmpty() && !month.isEmpty() && !day.isEmpty()) {
					dateTime.setValue(
							createGregCal(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)));
					DateType dateType = new DateType();
					JAXBElement<DateTime> dateTypeElement = niemCore3ObjectFactory.createDateTime(dateTime);
					dateType.getDateRepresentation().add(dateTypeElement);
					return dateType;
				} else {
					dateTime.setValue(createGregCal(1999, 1, 1));
					DateType dateType = new DateType();
					JAXBElement<DateTime> dateTypeElement = niemCore3ObjectFactory.createDateTime(dateTime);
					dateType.getDateRepresentation().add(dateTypeElement);
					return dateType;
				}
			}

			private void setItemValue(String value, String year, String month, String day, String hour, String min,
					String sec) {
				ItemValueType itemValue = new ItemValueType();
				itemValue.setItemValueAmount(setItemAmount(value));
				itemValue.setItemValueDate(setItemDate(year, month, day, hour, min, sec));
				item.setItemValue(itemValue);
			}

			// NIBRS Property Category Code
			private void getPropertyCategoryCode(ItemType i) {
				if (i.getItemCategory() != null) {
					JAXBElement<?> itemCodeElement = (JAXBElement<?>) i.getItemCategory();
					if (itemCodeElement.getValue() instanceof PropertyCategoryCodeType) {
						PropertyCategoryCodeType itemCode = (PropertyCategoryCodeType) itemCodeElement.getValue();
						textArea.append("NIBRS Property Category Code: " + itemCode.getValue() + "\n");
					} else if (itemCodeElement.getValue() instanceof Object) {
						Object itemCode = (Object) itemCodeElement.getValue();
						textArea.append("NIBRS Property Category Code: " + itemCode.toString() + "\n");
					}
				}
			}

			private void setPropertyCategoryCode(String code) {
				PropertyCategoryCodeType propertyCatCode = new PropertyCategoryCodeType();
				switch (code) {
				case "Aircraft":
					propertyCatCode.setValue("01");
					break;
				case "Alcohol":
					propertyCatCode.setValue("02");
					break;
				case "Automobile":
					propertyCatCode.setValue("03");
					break;
				case "Bicycles":
					propertyCatCode.setValue("04");
					break;
				case "Buses":
					propertyCatCode.setValue("05");
					break;
				case "Clothes/Furs":
					propertyCatCode.setValue("06");
					break;
				case "Computer Hardware/Software":
					propertyCatCode.setValue("07");
					break;
				case "Consumable Goods":
					propertyCatCode.setValue("08");
					break;
				case "Credit/Debit Cards":
					propertyCatCode.setValue("09");
					break;
				case "Drugs/Narcotics":
					propertyCatCode.setValue("10");
					break;
				case "Drug/Narcotic Equipment":
					propertyCatCode.setValue("11");
					break;
				case "Farm Equipment":
					propertyCatCode.setValue("12");
					break;
				case "Firearms":
					propertyCatCode.setValue("13");
					break;
				case "Gambling Equipment":
					propertyCatCode.setValue("14");
					break;
				case "Heavy Construction/Industrial Equipment":
					propertyCatCode.setValue("15");
					break;
				case "Household Goods":
					propertyCatCode.setValue("16");
					break;
				case "Jewelry/Precious Metals/Gems":
					propertyCatCode.setValue("17");
					break;
				case "Livestock":
					propertyCatCode.setValue("18");
					break;
				case "Merchandise":
					propertyCatCode.setValue("19");
					break;
				case "Money":
					propertyCatCode.setValue("20");
					break;
				case "Negotiable Instruments":
					propertyCatCode.setValue("21");
					break;
				case "Nonnegotiable Instruments":
					propertyCatCode.setValue("22");
					break;
				case "Office-Type Equipment":
					propertyCatCode.setValue("23");
					break;
				case "Other Motor Vehicles":
					propertyCatCode.setValue("24");
					break;
				case "Purses/Handbags/Wallets":
					propertyCatCode.setValue("25");
					break;
				case "Radio/TV/VCR/DVD Players":
					propertyCatCode.setValue("26");
					break;
				case "Recordings - audio/visual":
					propertyCatCode.setValue("27");
					break;
				case "Recreational Vehicles":
					propertyCatCode.setValue("28");
					break;
				case "Structures - single occupancy dwellings":
					propertyCatCode.setValue("29");
					break;
				case "Structures - other dwellings":
					propertyCatCode.setValue("30");
					break;
				case "Structures - other commercial/business":
					propertyCatCode.setValue("31");
					break;
				case "Structures - industrial/manufacturing":
					propertyCatCode.setValue("32");
					break;
				case "Structures - public/community":
					propertyCatCode.setValue("33");
					break;
				case "Structures - storage":
					propertyCatCode.setValue("34");
					break;
				case "Structures - other":
					propertyCatCode.setValue("35");
					break;
				case "Tools":
					propertyCatCode.setValue("36");
					break;
				case "Trucks":
					propertyCatCode.setValue("37");
					break;
				case "Vehicle Parts/Accessories":
					propertyCatCode.setValue("38");
					break;
				case "Watercraft":
					propertyCatCode.setValue("39");
					break;
				case "Aircraft Parts/Accessories":
					propertyCatCode.setValue("41");
					break;
				case "Artistic Supplies/Accessories":
					propertyCatCode.setValue("42");
					break;
				case "building materials":
					propertyCatCode.setValue("43");
					break;
				case "Camping/Hunting/Fishing Equipment/Supplies":
					propertyCatCode.setValue("44");
					break;
				case "Chemicals":
					propertyCatCode.setValue("45");
					break;
				case "Collections/Collectibles":
					propertyCatCode.setValue("46");
					break;
				case "Crops":
					propertyCatCode.setValue("47");
					break;
				case "Documents/Personal or Business":
					propertyCatCode.setValue("48");
					break;
				case "Explosives":
					propertyCatCode.setValue("49");
					break;
				case "Firearm Accessories":
					propertyCatCode.setValue("59");
					break;
				case "Fuel":
					propertyCatCode.setValue("64");
					break;
				case "Identity Documents":
					propertyCatCode.setValue("65");
					break;
				case "Identity - Intangible":
					propertyCatCode.setValue("66");
					break;
				case "Law Enforcement Equipment":
					propertyCatCode.setValue("67");
					break;
				case "Lawn/Yard/Garden Equipment":
					propertyCatCode.setValue("68");
					break;
				case "Logging Equipment":
					propertyCatCode.setValue("69");
					break;
				case "Medical/Medical Lab Equipment":
					propertyCatCode.setValue("70");
					break;
				case "Metals, Non-Precious":
					propertyCatCode.setValue("71");
					break;
				case "Musical Instruments":
					propertyCatCode.setValue("72");
					break;
				case "Pets":
					propertyCatCode.setValue("73");
					break;
				case "Photographic/Optical Equipment":
					propertyCatCode.setValue("74");
					break;
				case "Portable Electronic Communications":
					propertyCatCode.setValue("75");
					break;
				case "Recreational/Sports Equipment":
					propertyCatCode.setValue("76");
					break;
				case "Other":
					propertyCatCode.setValue("77");
					break;
				case "Trailers":
					propertyCatCode.setValue("78");
					break;
				case "Watercraft Equipment/Parts/Accessories":
					propertyCatCode.setValue("79");
					break;
				case "Weapons - Other":
					propertyCatCode.setValue("80");
					break;
				case "Pending Inventory":
					propertyCatCode.setValue("88");
					break;
				}
				JAXBElement<PropertyCategoryCodeType> itemCatElement = jxdm5ObjectFactory
						.createItemCategoryNIBRSPropertyCategoryCode(propertyCatCode);
				item.setItemCategory(itemCatElement);
			}

			// Item Quantity
			private void getItemQuantity(ItemType i) {
				if (i.getItemQuantity() != null) {
					textArea.append("Item Quantity: " + i.getItemQuantity().getValue() + "\n");
				}
			}
			private void setItemQuantity(String quant) {
				QuantityType itemQuant = new QuantityType();
				if (!quant.isEmpty()) {
					BigDecimal quantBigDecimal = new BigDecimal(Integer.parseInt(quant));
					itemQuant.setValue(quantBigDecimal);
					item.setItemQuantity(itemQuant);
				}
//				} else {
//					BigDecimal quantBigDecimal = new BigDecimal(0);
//					itemQuant.setValue(quantBigDecimal);
//					item.setItemQuantity(itemQuant);
//				}
			}

			private ItemType createItem(String[] itemValues) {

				setID(itemValues[0]);
				setItemStatus(itemValues[9]);
				setItemValue(itemValues[7], itemValues[1], itemValues[2], itemValues[3], itemValues[4], itemValues[5],
						itemValues[6]);
				setPropertyCategoryCode(itemValues[10]);
				setItemQuantity(itemValues[8]);

				return item;
			}
		};
		private class Substance {

			SubstanceType substance = niemCore3ObjectFactory.createSubstanceType();
			
			private void unmarshallSubstance(ReportType report) {
				textArea.append("***********Substance ************ \n");
				if (report.getSubstance() != null) {
					ArrayList<SubstanceType> substanceList = (ArrayList<SubstanceType>) report.getSubstance();
					substanceList.forEach((substance) -> {

						textArea.append("**Substance** \n");
						getID(substance);
						getItemStatusCode(substance);
						getItemValue(substance);
						getPropertyCategoryCode(substance);
						getItemQuantity(substance);
						getDrugCategoryCode(substance);
						getMeasurementValue(substance);
						getMeasurementUnit(substance);

					});
				}

			}
			
			// Substance ID
			private void getID(SubstanceType sub) {
				textArea.append("Substance ID:" + sub.getId() + "\n");
			}
			private void setID(String id) {
				substance.setId(id);
			}

			// Item Status Code
			private void getItemStatusCode(SubstanceType sub) {
				if (sub.getItemStatus() != null) {
					JAXBElement<?> itemStatusElement = sub.getItemStatus().getStatusDesignator();
					if (itemStatusElement.getValue() instanceof ItemStatusCodeType) {
						ItemStatusCodeType itemStatus = (ItemStatusCodeType) itemStatusElement.getValue();
						textArea.append("Item Status: " + itemStatus.getValue().value() + "\n");
					} else if (itemStatusElement.getValue() instanceof Object) {
						textArea.append("Item Status: " + "\n");
					}
				}
			}
			private void setItemStatusCode(String statusCode){
				StatusType itemStatus = new StatusType();
				ItemStatusCodeType itemStatusCode = new ItemStatusCodeType();
				ItemStatusCodeSimpleType itemStatusCodeSimple;
				switch (statusCode) {
				case "NONE":
					itemStatusCodeSimple = ItemStatusCodeSimpleType.NONE;
					itemStatusCode.setValue(itemStatusCodeSimple);
					break;
				case "BURNED":
					itemStatusCodeSimple = ItemStatusCodeSimpleType.BURNED;
					itemStatusCode.setValue(itemStatusCodeSimple);
					break;
				case "COUNTERFEITED":
					itemStatusCodeSimple = ItemStatusCodeSimpleType.COUNTERFEITED;
					itemStatusCode.setValue(itemStatusCodeSimple);
					break;
				case "DESTROYED_DAMAGED_VANDALIZED":
					itemStatusCodeSimple = ItemStatusCodeSimpleType.DESTROYED_DAMAGED_VANDALIZED;
					itemStatusCode.setValue(itemStatusCodeSimple);
					break;
				case "RECOVERED":
					itemStatusCodeSimple = ItemStatusCodeSimpleType.RECOVERED;
					itemStatusCode.setValue(itemStatusCodeSimple);
					break;
				case "SEIZED":
					itemStatusCodeSimple = ItemStatusCodeSimpleType.SEIZED;
					itemStatusCode.setValue(itemStatusCodeSimple);
					break;
				case "STOLEN":
					itemStatusCodeSimple = ItemStatusCodeSimpleType.STOLEN;
					itemStatusCode.setValue(itemStatusCodeSimple);
					break;
				case "UNKNOWN":
					itemStatusCodeSimple = ItemStatusCodeSimpleType.UNKNOWN;
					itemStatusCode.setValue(itemStatusCodeSimple);
					break;
				}
				JAXBElement<ItemStatusCodeType> itemStatusCodeElement = cjis1ObjectFactory
						.createItemStatusCode(itemStatusCode);
				itemStatus.setStatusDesignator(itemStatusCodeElement);
				substance.setItemStatus(itemStatus);
			}
			
			//Item Value Amount
			private void getItemValue(SubstanceType sub) {
				if (sub.getItemValue() != null) {
					textArea.append(
							"Item Value: " + sub.getItemValue().getItemValueAmount().getAmount().getValue() + "\n");
				}
			}
			private AmountType setItemValueAmount(String value) {
				AmountType itemAmount = niemCore3ObjectFactory.createAmountType();
				Decimal amount = new Decimal();
				if (!value.isEmpty()) {
					BigDecimal amountBigDec = new BigDecimal(value);
					amount.setValue(amountBigDec);
					itemAmount.setAmount(amount);
					return itemAmount;

				} else {
					BigDecimal amountBigDec = new BigDecimal("0");
					amount.setValue(amountBigDec);
					itemAmount.setAmount(amount);
					return itemAmount;
				}
			}
			private void setItemValue(String value) {
				ItemValueType itemValue = new ItemValueType();
				itemValue.setItemValueAmount(setItemValueAmount(value));
				substance.setItemValue(itemValue);
			}
			
			//Item Property Category Code
			private void getPropertyCategoryCode(SubstanceType sub) {
				if (sub.getItemCategory() != null) {
					JAXBElement<?> itemCodeElement = (JAXBElement<?>) sub.getItemCategory();
					if (itemCodeElement.getValue() instanceof PropertyCategoryCodeType) {
						PropertyCategoryCodeType itemCode = (PropertyCategoryCodeType) itemCodeElement.getValue();
						textArea.append("NIBRS Property Category Code: " + itemCode.getValue() + "\n");
					} else if (itemCodeElement.getValue() instanceof Object) {
						Object itemCode = (Object) itemCodeElement.getValue();
						textArea.append("NIBRS Property Category Code: " + itemCode.toString() + "\n");
					}
				}
			}
			private void setPropertyCategoryCode(String propertyCode) {
				PropertyCategoryCodeType propertyCatCode = new PropertyCategoryCodeType();
				switch (propertyCode) {
				case "Aircraft":
					propertyCatCode.setValue("01");
					break;
				case "Alcohol":
					propertyCatCode.setValue("02");
					break;
				case "Automobile":
					propertyCatCode.setValue("03");
					break;
				case "Bicycles":
					propertyCatCode.setValue("04");
					break;
				case "Buses":
					propertyCatCode.setValue("05");
					break;
				case "Clothes/Furs":
					propertyCatCode.setValue("06");
					break;
				case "Computer Hardware/Software":
					propertyCatCode.setValue("07");
					break;
				case "Consumable Goods":
					propertyCatCode.setValue("08");
					break;
				case "Credit/Debit Cards":
					propertyCatCode.setValue("09");
					break;
				case "Drugs/Narcotics":
					propertyCatCode.setValue("10");
					break;
				case "Drug/Narcotic Equipment":
					propertyCatCode.setValue("11");
					break;
				case "Farm Equipment":
					propertyCatCode.setValue("12");
					break;
				case "Firearms":
					propertyCatCode.setValue("13");
					break;
				case "Gambling Equipment":
					propertyCatCode.setValue("14");
					break;
				case "Heavy Construction/Industrial Equipment":
					propertyCatCode.setValue("15");
					break;
				case "Household Goods":
					propertyCatCode.setValue("16");
					break;
				case "Jewelry/Precious Metals/Gems":
					propertyCatCode.setValue("17");
					break;
				case "Livestock":
					propertyCatCode.setValue("18");
					break;
				case "Merchandise":
					propertyCatCode.setValue("19");
					break;
				case "Money":
					propertyCatCode.setValue("20");
					break;
				case "Negotiable Instruments":
					propertyCatCode.setValue("21");
					break;
				case "Nonnegotiable Instruments":
					propertyCatCode.setValue("22");
					break;
				case "Office-Type Equipment":
					propertyCatCode.setValue("23");
					break;
				case "Other Motor Vehicles":
					propertyCatCode.setValue("24");
					break;
				case "Purses/Handbags/Wallets":
					propertyCatCode.setValue("25");
					break;
				case "Radio/TV/VCR/DVD Players":
					propertyCatCode.setValue("26");
					break;
				case "Recordings - audio/visual":
					propertyCatCode.setValue("27");
					break;
				case "Recreational Vehicles":
					propertyCatCode.setValue("28");
					break;
				case "Structures - single occupancy dwellings":
					propertyCatCode.setValue("29");
					break;
				case "Structures - other dwellings":
					propertyCatCode.setValue("30");
					break;
				case "Structures - other commercial/business":
					propertyCatCode.setValue("31");
					break;
				case "Structures - industrial/manufacturing":
					propertyCatCode.setValue("32");
					break;
				case "Structures - public/community":
					propertyCatCode.setValue("33");
					break;
				case "Structures - storage":
					propertyCatCode.setValue("34");
					break;
				case "Structures - other":
					propertyCatCode.setValue("35");
					break;
				case "Tools":
					propertyCatCode.setValue("36");
					break;
				case "Trucks":
					propertyCatCode.setValue("37");
					break;
				case "Vehicle Parts/Accessories":
					propertyCatCode.setValue("38");
					break;
				case "Watercraft":
					propertyCatCode.setValue("39");
					break;
				case "Aircraft Parts/Accessories":
					propertyCatCode.setValue("41");
					break;
				case "Artistic Supplies/Accessories":
					propertyCatCode.setValue("42");
					break;
				case "building materials":
					propertyCatCode.setValue("43");
					break;
				case "Camping/Hunting/Fishing Equipment/Supplies":
					propertyCatCode.setValue("44");
					break;
				case "Chemicals":
					propertyCatCode.setValue("45");
					break;
				case "Collections/Collectibles":
					propertyCatCode.setValue("46");
					break;
				case "Crops":
					propertyCatCode.setValue("47");
					break;
				case "Documents/Personal or Business":
					propertyCatCode.setValue("48");
					break;
				case "Explosives":
					propertyCatCode.setValue("49");
					break;
				case "Firearm Accessories":
					propertyCatCode.setValue("59");
					break;
				case "Fuel":
					propertyCatCode.setValue("64");
					break;
				case "Identity Documents":
					propertyCatCode.setValue("65");
					break;
				case "Identity - Intangible":
					propertyCatCode.setValue("66");
					break;
				case "Law Enforcement Equipment":
					propertyCatCode.setValue("67");
					break;
				case "Lawn/Yard/Garden Equipment":
					propertyCatCode.setValue("68");
					break;
				case "Logging Equipment":
					propertyCatCode.setValue("69");
					break;
				case "Medical/Medical Lab Equipment":
					propertyCatCode.setValue("70");
					break;
				case "Metals, Non-Precious":
					propertyCatCode.setValue("71");
					break;
				case "Musical Instruments":
					propertyCatCode.setValue("72");
					break;
				case "Pets":
					propertyCatCode.setValue("73");
					break;
				case "Photographic/Optical Equipment":
					propertyCatCode.setValue("74");
					break;
				case "Portable Electronic Communications":
					propertyCatCode.setValue("75");
					break;
				case "Recreational/Sports Equipment":
					propertyCatCode.setValue("76");
					break;
				case "Other":
					propertyCatCode.setValue("77");
					break;
				case "Trailers":
					propertyCatCode.setValue("78");
					break;
				case "Watercraft Equipment/Parts/Accessories":
					propertyCatCode.setValue("79");
					break;
				case "Weapons - Other":
					propertyCatCode.setValue("80");
					break;
				case "Pending Inventory":
					propertyCatCode.setValue("88");
					break;
				}
				JAXBElement<PropertyCategoryCodeType> itemCatElement = jxdm5ObjectFactory
						.createItemCategoryNIBRSPropertyCategoryCode(propertyCatCode);
				substance.setItemCategory(itemCatElement);
			}

			//Item Quantity
			private void getItemQuantity(SubstanceType sub) {
				if (sub.getItemQuantity() != null) {
					textArea.append("Item Quantity: " + sub.getItemQuantity().getValue() + "\n");
				}
			}
			private void setItemQuantity(String quant) {
				QuantityType itemQuant = new QuantityType();
				if (!quant.isEmpty()) {
					BigDecimal quantBigDecimal = new BigDecimal(Integer.parseInt(quant));
					itemQuant.setValue(quantBigDecimal);
					substance.setItemQuantity(itemQuant);
				} 
			}
			
			// Drug Category Code
			private void getDrugCategoryCode(SubstanceType sub) {
				List<JAXBElement<?>> substanceCatList = sub.getSubstanceCategory();
				for (Iterator<JAXBElement<?>> iterator = substanceCatList.iterator(); iterator.hasNext();) {
					JAXBElement<?> element = (JAXBElement<?>) iterator.next();

					if (element.getValue() instanceof DrugCategoryCodeType) {
						DrugCategoryCodeType substanceCode = (DrugCategoryCodeType) element.getValue();
						textArea.append("Drug Category Code:" + substanceCode.getValue().value() + "\n");
					} else if (element.getValue() instanceof Object) {
						Object substanceCode = (Object) element.getValue();
						textArea.append("Drug Category Code:" + substanceCode.toString() + "\n");
					}
				}

			}
			private void setDrugCategoryCode(String code) {
				DrugCategoryCodeType drugCatCode = new DrugCategoryCodeType();
				DrugCategoryCodeSimpleType drugCatCodeSimple;
				switch (code) {
				case "Crack Cocaine":
					drugCatCodeSimple = DrugCategoryCodeSimpleType.A;
					drugCatCode.setValue(drugCatCodeSimple);
					break;
				case "Cocaine (All forms except Crack)":
					drugCatCodeSimple = DrugCategoryCodeSimpleType.B;
					drugCatCode.setValue(drugCatCodeSimple);
					break;
				case "Hashish":
					drugCatCodeSimple = DrugCategoryCodeSimpleType.C;
					drugCatCode.setValue(drugCatCodeSimple);
					break;
				case "Heroin":
					drugCatCodeSimple = DrugCategoryCodeSimpleType.D;
					drugCatCode.setValue(drugCatCodeSimple);
					break;
				case "Marijuana":
					drugCatCodeSimple = DrugCategoryCodeSimpleType.E;
					drugCatCode.setValue(drugCatCodeSimple);
					break;
				case "Morphine":
					drugCatCodeSimple = DrugCategoryCodeSimpleType.F;
					drugCatCode.setValue(drugCatCodeSimple);
					break;
				case "Opium":
					drugCatCodeSimple = DrugCategoryCodeSimpleType.G;
					drugCatCode.setValue(drugCatCodeSimple);
					break;
				case "Other Narcotics":
					drugCatCodeSimple = DrugCategoryCodeSimpleType.H;
					drugCatCode.setValue(drugCatCodeSimple);
					break;
				case "LSD":
					drugCatCodeSimple = DrugCategoryCodeSimpleType.I;
					drugCatCode.setValue(drugCatCodeSimple);
					break;
				case "PCP":
					drugCatCodeSimple = DrugCategoryCodeSimpleType.J;
					drugCatCode.setValue(drugCatCodeSimple);
					break;
				case "Other Hallucinogens":
					drugCatCodeSimple = DrugCategoryCodeSimpleType.K;
					drugCatCode.setValue(drugCatCodeSimple);
					break;
				case "Amphetamines/Methamphetamines":
					drugCatCodeSimple = DrugCategoryCodeSimpleType.L;
					drugCatCode.setValue(drugCatCodeSimple);
					break;
				case "Other Stimulants":
					drugCatCodeSimple = DrugCategoryCodeSimpleType.M;
					drugCatCode.setValue(drugCatCodeSimple);
					break;
				case "Barbiturates":
					drugCatCodeSimple = DrugCategoryCodeSimpleType.N;
					drugCatCode.setValue(drugCatCodeSimple);
					break;
				case "Other Depressants":
					drugCatCodeSimple = DrugCategoryCodeSimpleType.O;
					drugCatCode.setValue(drugCatCodeSimple);
					break;
				case "Other Drugs":
					drugCatCodeSimple = DrugCategoryCodeSimpleType.P;
					drugCatCode.setValue(drugCatCodeSimple);
					break;
				case "Unknown Drug Type":
					drugCatCodeSimple = DrugCategoryCodeSimpleType.U;
					drugCatCode.setValue(drugCatCodeSimple);
					break;
				case "Over 3 Drug Types":
					drugCatCodeSimple = DrugCategoryCodeSimpleType.X;
					drugCatCode.setValue(drugCatCodeSimple);
					break;
				}
				JAXBElement<DrugCategoryCodeType> substanceCatElement = jxdm5ObjectFactory
						.createDrugCategoryCode(drugCatCode);
				substance.getSubstanceCategory().add(substanceCatElement);
			}

			// Measurement value
			private void getMeasurementValue(SubstanceType sub) {
				List<MeasureType> substanceQuantityList = sub.getSubstanceQuantityMeasure();
				for (Iterator<MeasureType> iterator = substanceQuantityList.iterator(); iterator.hasNext();) {
					MeasureType element = (MeasureType) iterator.next();

					List<JAXBElement<?>> substanceQuantityElementList = element.getMeasureValue();
					for (Iterator<JAXBElement<?>> iteratorQ = substanceQuantityElementList.iterator(); iteratorQ
							.hasNext();) {
						JAXBElement<?> elementQ = (JAXBElement<?>) iteratorQ.next();

						if (elementQ.getValue() instanceof TextType) {
							TextType substanceMeasureValue = (TextType) elementQ.getValue();
							textArea.append("Substance Measure Value:" + substanceMeasureValue.getValue() + "\n");
						} else if (elementQ.getValue() instanceof IntegerRangeType) {
							IntegerRangeType substanceMeasureValue = (IntegerRangeType) elementQ.getValue();
							textArea.append(
									"Substance Measure Value:" + substanceMeasureValue.getRangeMinimumIntegerValue()
											+ " to " + substanceMeasureValue.getRangeMaximumIntegerValue() + "\n");
						} else if (elementQ.getValue() instanceof Integer) {
							Integer substanceMeasureValue = (Integer) elementQ.getValue();
							textArea.append("Substance Measure Value:" + substanceMeasureValue.toString() + "\n");
						} else if (elementQ.getValue() instanceof Decimal) {
							Decimal substanceMeasureValue = (Decimal) elementQ.getValue();
							textArea.append("Substance Measure Value:" + substanceMeasureValue.getValue() + "\n");
						} else if (elementQ.getValue() instanceof Object) {
							Object substanceMeasureValue = (Object) elementQ.getValue();
							textArea.append("Substance Measure Value:" + substanceMeasureValue.toString() + "\n");
						}
					}
				}

			}
			private JAXBElement<Decimal> setMeasurementValue(String value) {
				if (!value.isEmpty()) {
					BigDecimal measureValBigDecimal = new BigDecimal(Integer.parseInt(value));
					Decimal measureValDecimal = new Decimal();
					measureValDecimal.setValue(measureValBigDecimal);
					JAXBElement<Decimal> measureValueElement = niemCore3ObjectFactory
							.createMeasureDecimalValue(measureValDecimal);
					return measureValueElement;
				} else {
					BigDecimal measureValBigDecimal = new BigDecimal(0);
					Decimal measureValDecimal = new Decimal();
					measureValDecimal.setValue(measureValBigDecimal);
					JAXBElement<Decimal> measureValueElement = niemCore3ObjectFactory
							.createMeasureDecimalValue(measureValDecimal);
					return measureValueElement;
				}
			}

			// Measurement Unit
			private void getMeasurementUnit(SubstanceType sub) {
				List<MeasureType> substanceUnitList = sub.getSubstanceQuantityMeasure();
				for (Iterator<MeasureType> iterator = substanceUnitList.iterator(); iterator.hasNext();) {
					MeasureType element = (MeasureType) iterator.next();
					List<JAXBElement<?>> substanceUnitElementList = element.getMeasureUnit();
					for (Iterator<JAXBElement<?>> iteratorU = substanceUnitElementList.iterator(); iteratorU
							.hasNext();) {
						JAXBElement<?> elementU = (JAXBElement<?>) iteratorU.next();

						if (elementU.getValue() instanceof DrugMeasurementCodeType) {
							DrugMeasurementCodeType substanceMeasureUnit = (DrugMeasurementCodeType) elementU
									.getValue();
							textArea.append(
									"Substance Measure Units:" + substanceMeasureUnit.getValue().toString() + "\n");
						} else if (elementU.getValue() instanceof Object) {
							Object substanceMeasureUnit = (Object) elementU.getValue();
							textArea.append("Substance Measure Value:" + substanceMeasureUnit.toString() + "\n");
						}
					}
				}

			}
			private JAXBElement<DrugMeasurementCodeType> setMeasurementUnit(String unit) {
				DrugMeasurementCodeType measureUnitCode = new DrugMeasurementCodeType();
				DrugMeasurementCodeSimpleType measureUnitCodeSimple;
				switch (unit) {
				case "Dosage Units/Items":
					measureUnitCodeSimple = DrugMeasurementCodeSimpleType.DU;
					measureUnitCode.setValue(measureUnitCodeSimple);
					break;
				case "Fluid Ounce":
					measureUnitCodeSimple = DrugMeasurementCodeSimpleType.FO;
					measureUnitCode.setValue(measureUnitCodeSimple);
					break;
				case "Gallon":
					measureUnitCodeSimple = DrugMeasurementCodeSimpleType.GL;
					measureUnitCode.setValue(measureUnitCodeSimple);
					break;
				case "Gram":
					measureUnitCodeSimple = DrugMeasurementCodeSimpleType.GM;
					measureUnitCode.setValue(measureUnitCodeSimple);
					break;
				case "Kilogram":
					measureUnitCodeSimple = DrugMeasurementCodeSimpleType.KG;
					measureUnitCode.setValue(measureUnitCodeSimple);
					break;
				case "Pound":
					measureUnitCodeSimple = DrugMeasurementCodeSimpleType.LB;
					measureUnitCode.setValue(measureUnitCodeSimple);
					break;
				case "Liter":
					measureUnitCodeSimple = DrugMeasurementCodeSimpleType.LT;
					measureUnitCode.setValue(measureUnitCodeSimple);
					break;
				case "Milliliter":
					measureUnitCodeSimple = DrugMeasurementCodeSimpleType.ML;
					measureUnitCode.setValue(measureUnitCodeSimple);
					break;
				case "Number of Plants":
					measureUnitCodeSimple = DrugMeasurementCodeSimpleType.NP;
					measureUnitCode.setValue(measureUnitCodeSimple);
					break;
				case "Ounce":
					measureUnitCodeSimple = DrugMeasurementCodeSimpleType.OZ;
					measureUnitCode.setValue(measureUnitCodeSimple);
					break;
				case "Not Reported":
					measureUnitCodeSimple = DrugMeasurementCodeSimpleType.XX;
					measureUnitCode.setValue(measureUnitCodeSimple);
					break;
				}
				JAXBElement<DrugMeasurementCodeType> measureUnitCodeElement = jxdm5ObjectFactory
						.createSubstanceUnitCode(measureUnitCode);
				return measureUnitCodeElement;
			}

			private void setDrugMeasure(String value, String unit) {
				MeasureType measureType = niemCore3ObjectFactory.createMeasureType();
				measureType.getMeasureValue().add(setMeasurementValue(value));
				measureType.getMeasureUnit().add(setMeasurementUnit(unit));
				substance.getSubstanceQuantityMeasure().add(measureType);
			}

			private SubstanceType createSubstance(String[] subArray) {

				setID(subArray[0]);
				setDrugCategoryCode(subArray[1]);
				setDrugMeasure(subArray[2], subArray[3]);
				setItemStatusCode(subArray[4]);
				setItemValue(subArray[5]);
				setPropertyCategoryCode(subArray[6]);
				setItemQuantity(subArray[7]);

				return substance;
			}
		};
		private class Person {

			PersonType person = niemCore3ObjectFactory.createPersonType();

			private void unmarshallPerson(ReportType report) {
				if (report.getPerson() != null) {
					ArrayList<PersonType> personList = (ArrayList<PersonType>) report.getPerson();
					textArea.append("***********Person ************ \n");
					personList.forEach((person) -> {

						textArea.append("**Person** \n");

						getID(person);
						getEthnicity(person);
						getRace(person);
						getResident(person);
						getGender(person);
						getAge(person);
					});
				}
			}

			// Person ID
			private void getID(PersonType per) {
				textArea.append("Person ID: " + per.getId() + "\n");
			}
			private void setID(String id) {
				person.setId(id);
			}

			// Ethnicity
			private void getEthnicity(PersonType per) {
				if (per.getPersonEthnicity() != null) {
					JAXBElement<?> ethnicityElement = per.getPersonEthnicity();
					if (ethnicityElement.getValue() instanceof EthnicityCodeType) {
						EthnicityCodeType ethnicityCode = (EthnicityCodeType) ethnicityElement.getValue();
						textArea.append("Ethnicity: " + ethnicityCode.getValue().toString() + "\n");
					} else if (ethnicityElement.getValue() instanceof Object) {
						Object ethnicityCode = (Object) ethnicityElement.getValue();
						textArea.append("Ethnicity: " + ethnicityCode.toString() + "\n");
					}
				}
			}
			private void setEthnicity(String e) {
				EthnicityCodeType ethnicityCode = new EthnicityCodeType();
				EthnicityCodeSimpleType ethnicityCodeSimple;
				switch (e) {
				case "Hispanic or Latino":
					ethnicityCodeSimple = EthnicityCodeSimpleType.H;
					ethnicityCode.setValue(ethnicityCodeSimple);
					break;
				case "Not Hispanic or Latino":
					ethnicityCodeSimple = EthnicityCodeSimpleType.N;
					ethnicityCode.setValue(ethnicityCodeSimple);
					break;
				case "Unknown":
					ethnicityCodeSimple = EthnicityCodeSimpleType.U;
					ethnicityCode.setValue(ethnicityCodeSimple);
					break;

				}
				JAXBElement<EthnicityCodeType> personEthnicityElement = jxdm5ObjectFactory
						.createPersonEthnicityCode(ethnicityCode);
				person.setPersonEthnicity(personEthnicityElement);
			}

			// Race
			private void getRace(PersonType per) {
				if (per.getPersonRace() != null) {
					JAXBElement<?> raceElement = per.getPersonRace();
					if (raceElement.getValue() instanceof RACCodeType) {
						RACCodeType raceCode = (RACCodeType) raceElement.getValue();
						textArea.append("Race: " + raceCode.getValue().toString() + "\n");
					} else if (raceElement.getValue() instanceof Object) {
						Object raceCode = (Object) raceElement.getValue();
						textArea.append("Race: " + raceCode.toString() + "\n");
					}
				}
			}
			private void setRace(String race) {
				RACCodeType personRaceCode = new RACCodeType();
				RACCodeSimpleType personRaceCodeSimple;
				switch (race) {
				case "ASIAN":
					personRaceCodeSimple = RACCodeSimpleType.A;
					personRaceCode.setValue(personRaceCodeSimple);
					break;
				case "BLACK or AFRICAN AMERICAN":
					personRaceCodeSimple = RACCodeSimpleType.B;
					personRaceCode.setValue(personRaceCodeSimple);
					break;
				case "AMERICAN INDIAN or ALASKA NATIVE":
					personRaceCodeSimple = RACCodeSimpleType.I;
					personRaceCode.setValue(personRaceCodeSimple);
					break;
				case "UNKNOWN":
					personRaceCodeSimple = RACCodeSimpleType.U;
					personRaceCode.setValue(personRaceCodeSimple);
					break;
				case "WHITE":
					personRaceCodeSimple = RACCodeSimpleType.W;
					personRaceCode.setValue(personRaceCodeSimple);
					break;
				case "NATIVE HAWAIIAN or OTHER PACIFIC ISLANDER":
					personRaceCodeSimple = RACCodeSimpleType.P;
					personRaceCode.setValue(personRaceCodeSimple);
					break;
				}
				JAXBElement<RACCodeType> personRaceElement = jxdm5ObjectFactory
						.createPersonRaceNDExCode(personRaceCode);
				person.setPersonRace(personRaceElement);
			}

			// Resident
			private void getResident(PersonType per) {
				if (person.getPersonResident() != null) {
					JAXBElement<?> residentElement = person.getPersonResident();
					if (residentElement.getValue() instanceof ResidentCodeType) {
						ResidentCodeType resCode = (ResidentCodeType) residentElement.getValue();
						textArea.append("Resident: " + resCode.getValue().toString() + "\n");
					} else if (residentElement.getValue() instanceof Object) {
						Object resCode = (Object) residentElement.getValue();
						textArea.append("Resident: " + resCode.toString() + "\n");
					}
				}
			}
			private void setResident(String res) {
				ResidentCodeType personResCode = new ResidentCodeType();
				ResidentCodeSimpleType personResCodeSimple;
				switch (res) {
				case "Nonresident":
					personResCodeSimple = ResidentCodeSimpleType.N;
					personResCode.setValue(personResCodeSimple);
					break;
				case "Resident":
					personResCodeSimple = ResidentCodeSimpleType.R;
					personResCode.setValue(personResCodeSimple);
					break;
				case "Unknown":
					personResCodeSimple = ResidentCodeSimpleType.U;
					personResCode.setValue(personResCodeSimple);
					break;

				}
				JAXBElement<ResidentCodeType> personResElement = jxdm5ObjectFactory
						.createPersonResidentCode(personResCode);
				person.setPersonResident(personResElement);
			}

			// Sex
			private void getGender(PersonType per) {
				if (per.getPersonSex() != null) {
					JAXBElement<?> sexElement = per.getPersonSex();
					if (sexElement.getValue() instanceof SEXCodeType) {
						SEXCodeType sexCode = (SEXCodeType) sexElement.getValue();
						textArea.append("Sex: " + sexCode.getValue().toString() + "\n");
					} else if (sexElement.getValue() instanceof Object) {
						Object sexCode = (Object) sexElement.getValue();
						textArea.append("Sex: " + sexCode.toString() + "\n");
					}
				}
			}
			private void setGender(String gen) {
				SEXCodeType personSexCode = new SEXCodeType();
				SEXCodeSimpleType personSexCodeSimple;
				switch (gen) {
				case "FEMALE":
					personSexCodeSimple = SEXCodeSimpleType.F;
					personSexCode.setValue(personSexCodeSimple);
					break;
				case "MALE":
					personSexCodeSimple = SEXCodeSimpleType.M;
					personSexCode.setValue(personSexCodeSimple);
					break;
				case "UNKNOWN":
					personSexCodeSimple = SEXCodeSimpleType.U;
					personSexCode.setValue(personSexCodeSimple);
					break;

				}
				JAXBElement<SEXCodeType> personSexElement = jxdm5ObjectFactory.createPersonSexCode(personSexCode);
				person.setPersonSex(personSexElement);
			}

			// Age
			private void getAge(PersonType per) {
				if (per.getPersonAgeMeasure() != null) {
					MeasureType personAgeMeasure = per.getPersonAgeMeasure();
					if (personAgeMeasure.getMeasureValue() != null) {
						List<JAXBElement<?>> personAgeElementList = personAgeMeasure.getMeasureValue();
						for (Iterator<JAXBElement<?>> iterator = personAgeElementList.iterator(); iterator.hasNext();) {
							JAXBElement<?> element = (JAXBElement<?>) iterator.next();

							if (element.getValue() instanceof TextType) {
								TextType personAge = (TextType) element.getValue();
								textArea.append("Person Age: " + personAge.getValue() + "\n");
							} else if (element.getValue() instanceof IntegerRangeType) {
								IntegerRangeType personAge = (IntegerRangeType) element.getValue();
								textArea.append("Person Age: " + personAge.getRangeMinimumIntegerValue().getValue() + " to "
										+ personAge.getRangeMaximumIntegerValue().getValue() + "\n");
							} else if (element.getValue() instanceof Integer) {
								Integer personAge = (Integer) element.getValue();
								textArea.append("Person Age: " + personAge.intValue() + "\n");
							} else if (element.getValue() instanceof Decimal) {
								Decimal personAge = (Decimal) element.getValue();
								textArea.append("Person Age: " + personAge.getValue() + "\n");
							} 
						} 
					} 
				} 
			}
			private void setAge(String age, String min, String max, String text) {
				MeasureType personAge = niemCore3ObjectFactory.createMeasureType();
				IntegerRangeType ageRange = new IntegerRangeType();
				gov.niem.release.niem.proxy.xsd._3.Integer intMin = new gov.niem.release.niem.proxy.xsd._3.Integer();
				gov.niem.release.niem.proxy.xsd._3.Integer intMax = new gov.niem.release.niem.proxy.xsd._3.Integer();
				
				if (!age.isEmpty()) {
					gov.niem.release.niem.proxy.xsd._3.Integer personAgeInt = new gov.niem.release.niem.proxy.xsd._3.Integer();
					personAgeInt.setValue(new BigInteger(age));
					JAXBElement<gov.niem.release.niem.proxy.xsd._3.Integer> ageMeasureIntValueElement = niemCore3ObjectFactory
							.createMeasureIntegerValue(personAgeInt);
					personAge.getMeasureValue().add(ageMeasureIntValueElement);
				} else if (!min.isEmpty() && !max.isEmpty()){
					intMin.setValue(new BigInteger(min));
					intMax.setValue(new BigInteger(max));
					
					ageRange.setRangeMinimumIntegerValue(intMin);
					ageRange.setRangeMaximumIntegerValue(intMax);

					JAXBElement<IntegerRangeType> ageRangeElement = niemCore3ObjectFactory.createMeasureIntegerRange(ageRange);
					personAge.getMeasureValue().add(ageRangeElement);
				} else if (!text.isEmpty()){
					TextType ageText = new TextType();
					switch (text){
					case "<24 hrs":
						ageText.setValue("NN");
						break;
					case "1-6 days":
						ageText.setValue("NB");
						break;
					case "7-364 days":
						ageText.setValue("BB");
						break;
					case "Over 98":
						ageText.setValue("99");
						break;
					case "Unknown":
						ageText.setValue("00");
						break;
					}
					JAXBElement<TextType> ageTextElement = niemCore3ObjectFactory.createMeasureValueText(ageText);
					personAge.getMeasureValue().add(ageTextElement);
				}
				person.setPersonAgeMeasure(personAge);
			}

			private PersonType createPerson(String[] personValues) {
				setID(personValues[0]);
				setEthnicity(personValues[1]);
				setRace(personValues[2]);
				setResident(personValues[3]);
				setGender(personValues[4]);
				setAge(personValues[5], personValues[6], personValues[7], personValues[8]);
				return person;
			}
		};
		private class EnforcementOfficial {

			EnforcementOfficialType enforcementOfficial = new EnforcementOfficialType();

			private void unmarshallEnforcementOfficial(ReportType report) {
				if (report.getEnforcementOfficial() != null){
				ArrayList<EnforcementOfficialType> enforcementOfficialList = (ArrayList<EnforcementOfficialType>) report
						.getEnforcementOfficial();
				textArea.append("***********Enforcement Official ************ \n");
				enforcementOfficialList.forEach((enforcementOffical) -> {

					textArea.append("**Enforcement Official** \n");

					getID(enforcementOfficial);
					getRole(enforcementOfficial);
					getOfficialActivityCode(enforcementOfficial);
					getOfficialAssignmentCode(enforcementOfficial);
					getORI(enforcementOfficial);

				});
			}}

			// ID
			private void getID(EnforcementOfficialType enfOff) {
				textArea.append("Enforcement Official ID: " + enfOff.getRoleOfPerson().getId() + "\n");
			}
			private void setID(String id) {
				enforcementOfficial.setId(id);
			}
			
			private void getRole(EnforcementOfficialType enfOff) {
				PersonType personRole = enfOff.getRoleOfPerson();
					textArea.append("Subject Role Ref:" + personRole.getId() + "\n");
			}
			private void setRole(String role) {
				PersonType personRole = new PersonType();
				personRole.setId(role);
				enforcementOfficial.setRoleOfPerson(personRole);
			}

			// Enforcement Official Activity Category Code
			private void getOfficialActivityCode(EnforcementOfficialType enfOff) {
				JAXBElement<?> activityCodeElement = enfOff.getEnforcementOfficialActivityCategory();
				if (activityCodeElement.getValue() instanceof LEOKAActivityCategoryCodeType) {
					LEOKAActivityCategoryCodeType activityCode = (LEOKAActivityCategoryCodeType) activityCodeElement
							.getValue();
					textArea.append("Activity: " + activityCode.getId() + "\n");
				} else if (activityCodeElement.getValue() instanceof Object) {
					Object activityCode = (Object) activityCodeElement.getValue();
					textArea.append("Activity: " + activityCode.toString() + "\n");
				}
			}
			private void setOfficialActivityCode(String activity) {
				LEOKAActivityCategoryCodeType offActCatCode = new LEOKAActivityCategoryCodeType();
				switch (activity) {
				case "Responding to Disturbance Call":
					offActCatCode.setValue("01");
					break;
				case "Burglaries in Progress or Pursuing Burglary Suspects":
					offActCatCode.setValue("02");
					break;
				case "Robberies in Progress or Pursuing Robbery Suspects":
					offActCatCode.setValue("03");
					break;
				case "Attempting Other Arrests":
					offActCatCode.setValue("04");
					break;
				case "Civil Disorder (Riot, Mass Disobedience)":
					offActCatCode.setValue("05");
					break;
				case "Handling, Transporting, Custody of Prisoners":
					offActCatCode.setValue("06");
					break;
				case "Investigating Suspicious Persons or Circumstances":
					offActCatCode.setValue("07");
					break;
				case "Ambush-No Warning":
					offActCatCode.setValue("08");
					break;
				case "Handling Persons with Mental Illness":
					offActCatCode.setValue("09");
					break;
				case "Traffic Pursuits and Stops":
					offActCatCode.setValue("10");
					break;
				case "All Other":
					offActCatCode.setValue("11");
					break;
				}
				JAXBElement<LEOKAActivityCategoryCodeType> offActCatCodeElement = jxdm5ObjectFactory
						.createEnforcementOfficialActivityCategoryCode(offActCatCode);
				enforcementOfficial.setEnforcementOfficialActivityCategory(offActCatCodeElement);
			}

			// Enforcement Official Assignment Category Code
			private void getOfficialAssignmentCode(EnforcementOfficialType enfOff) {
				JAXBElement<?> assignmentCodeElement = enfOff.getEnforcementOfficialActivityCategory();
				if (assignmentCodeElement.getValue() instanceof LEOKAActivityCategoryCodeType) {
					LEOKAActivityCategoryCodeType asignmentCode = (LEOKAActivityCategoryCodeType) assignmentCodeElement
							.getValue();
					textArea.append("Assignment: " + asignmentCode.getId() + "\n");
				} else if (assignmentCodeElement.getValue() instanceof Object) {
					Object asignmentCode = (Object) assignmentCodeElement.getValue();
					textArea.append("Assignment: " + asignmentCode.toString() + "\n");
				}
			}
			private void setOfficialAssignmentCode(String assignment) {
				LEOKAOfficerAssignmentCategoryCodeType offAssignment = new LEOKAOfficerAssignmentCategoryCodeType();
				LEOKAOfficerAssignmentCategoryCodeSimpleType offAssignmentSimple = LEOKAOfficerAssignmentCategoryCodeSimpleType.J;
				switch (assignment) {
				case "Two-Officer Vehicle - uniformed law enforcement officers":
					offAssignmentSimple = LEOKAOfficerAssignmentCategoryCodeSimpleType.F;
					offAssignment.setValue(offAssignmentSimple);
					break;
				case "One-Officer Vehicle (Alone) - uniformed law enforcement officers":
					offAssignmentSimple = LEOKAOfficerAssignmentCategoryCodeSimpleType.G;
					offAssignment.setValue(offAssignmentSimple);
					break;
				case "One-Officer Vehicle (Assisted) - uniformed law enforcement officers":
					offAssignmentSimple = LEOKAOfficerAssignmentCategoryCodeSimpleType.H;
					offAssignment.setValue(offAssignmentSimple);
					break;
				case "Detective or Special Assignment (Alone) - non-uniformed officers":
					offAssignmentSimple = LEOKAOfficerAssignmentCategoryCodeSimpleType.I;
					offAssignment.setValue(offAssignmentSimple);
					break;
				case "Detective or Special Assignment (Assisted) - non-uniformed officers":
					offAssignmentSimple = LEOKAOfficerAssignmentCategoryCodeSimpleType.J;
					offAssignment.setValue(offAssignmentSimple);
					break;
				case "Other (Alone) - law enforcement officers serving in other capacities":
					offAssignmentSimple = LEOKAOfficerAssignmentCategoryCodeSimpleType.K;
					offAssignment.setValue(offAssignmentSimple);
					break;
				case "Other (Assisted) - law enforcement officers serving in other capacities":
					offAssignmentSimple = LEOKAOfficerAssignmentCategoryCodeSimpleType.L;
					offAssignment.setValue(offAssignmentSimple);
					break;
				}
				JAXBElement<LEOKAOfficerAssignmentCategoryCodeType> offAssignmentElement = jxdm5ObjectFactory
						.createEnforcementOfficialAssignmentCategoryCode(offAssignment);
				enforcementOfficial.setEnforcementOfficialAssignmentCategory(offAssignmentElement);
			}

			// Organization ORI
			// reporting Agency
			private void getORI(EnforcementOfficialType enfOff) {
				// Organization ORI
				JAXBElement<?> enfOffAugElement = enfOff.getEnforcementOfficialUnit()
						.getOrganizationAugmentationPoint();
				if (enfOffAugElement.getValue() instanceof gov.fbi.cjis._1.OrganizationAugmentationType) {
					gov.fbi.cjis._1.OrganizationAugmentationType oriCode = (gov.fbi.cjis._1.OrganizationAugmentationType) enfOffAugElement
							.getValue();
					ArrayList<IdentificationType> oriIDList = (ArrayList<IdentificationType>) oriCode
							.getDirectReportingCityIdentification();
					oriIDList.forEach((oriID) -> {
						textArea.append("ORI: " + oriID.getIdentificationID() + "\n");
					});

				} else if (enfOffAugElement
						.getValue() instanceof gov.niem.release.niem.domains.jxdm._5.OrganizationAugmentationType) {
					gov.niem.release.niem.domains.jxdm._5.OrganizationAugmentationType oriCode = (gov.niem.release.niem.domains.jxdm._5.OrganizationAugmentationType) enfOffAugElement
							.getValue();
					JAXBElement<?> oriIDElement = oriCode.getOrganizationIdentification();
					if (oriIDElement.getValue() instanceof IdentificationType) {
						IdentificationType oriID = (IdentificationType) oriIDElement.getValue();
						textArea.append("ORI: " + oriID.getIdentificationID() + "\n");
					} else if (oriIDElement.getValue() instanceof Object) {
						Object oriID = (Object) oriIDElement.getValue();
						textArea.append("ORI: " + oriID.toString() + "\n");
					}

				} else if (enfOffAugElement.getValue() instanceof Object) {
					Object oriCode = (Object) enfOffAugElement.getValue();
					textArea.append("ORI: " + oriCode.toString() + "\n");
				}

			}
			private void setORI(String ori) {
				OrganizationType submittingOrg = new OrganizationType();
				gov.niem.release.niem.domains.jxdm._5.OrganizationAugmentationType submittingOrgAug = new gov.niem.release.niem.domains.jxdm._5.OrganizationAugmentationType();
				IdentificationType submittingOrgID = new IdentificationType();
				gov.niem.release.niem.proxy.xsd._3.String submittingOrgString = new gov.niem.release.niem.proxy.xsd._3.String();
				submittingOrgString.setValue(ori);
				submittingOrgID.setIdentificationID(submittingOrgString);
				JAXBElement<IdentificationType> submittingOrgIDElement = jxdm5ObjectFactory
						.createOrganizationORIIdentification(submittingOrgID);
				submittingOrgAug.setOrganizationIdentification(submittingOrgIDElement);
				JAXBElement<gov.niem.release.niem.domains.jxdm._5.OrganizationAugmentationType> submittingOrgAugElement = jxdm5ObjectFactory
						.createOrganizationAugmentation(submittingOrgAug);
				submittingOrg.setOrganizationAugmentationPoint(submittingOrgAugElement);
			}

			private void setEnforcementUnit(String enfUnit) {
				EnforcementUnitType enforcementUnit = new EnforcementUnitType();
				IdentificationType orgID = new IdentificationType();
				gov.niem.release.niem.proxy.xsd._3.String submittingOrgString = new gov.niem.release.niem.proxy.xsd._3.String();
				submittingOrgString.setValue(enfUnit);
				orgID.setIdentificationID(submittingOrgString);
				JAXBElement<IdentificationType> orgIDElement = jxdm5ObjectFactory
						.createOrganizationORIIdentification(orgID);
				OrganizationAugmentationType enforcementOfficialAug = new OrganizationAugmentationType();
				enforcementOfficialAug.setOrganizationIdentification(orgIDElement);
				JAXBElement<OrganizationAugmentationType> orgAugElement = jxdm5ObjectFactory
						.createOrganizationAugmentation(enforcementOfficialAug);
				enforcementUnit.setOrganizationAugmentationPoint(orgAugElement);
				enforcementOfficial.setEnforcementOfficialUnit(enforcementUnit);
			}

			private EnforcementOfficialType createEnforcementOfficial(String[] enfValues) {

				setID(enfValues[0]);
				setRole(enfValues[1]);
				setOfficialActivityCode(enfValues[2]);
				setOfficialAssignmentCode(enfValues[3]);
				setORI(enfValues[4]);
				setEnforcementUnit(enfValues[5]);

				return enforcementOfficial;
			}
		};
		private class Victim {

			VictimType victim = new VictimType();

			private void unmarshallVictim(ReportType report) {
				if (report.getVictim() != null){
				ArrayList<VictimType> victimlist = (ArrayList<VictimType>) report.getVictim();
				textArea.append("***********Victim ************ \n");

				victimlist.forEach((victim) -> {
					textArea.append("**Victim** \n");

					getID(victim);
					getRole(victim);
					getSeqNum(victim);
					getInjury(victim);
					getCategoryCode(victim);
					getAssaultHomFactor(victim);
					getJustHomCode(victim);
				});
			}}

			// Victim ID
			private void getID(VictimType vic) {
				textArea.append("Victim ID: " + vic.getId() + "\n");
			}
			private void setID(String id) {
				victim.setId(id);
			}

			// Roll of Victim
			private void getRole(VictimType vic) {
				if (vic.getRoleOfPerson() != null) {
					ArrayList<PersonType> victimRoleList = (ArrayList<PersonType>) vic.getRoleOfPerson();
					victimRoleList.forEach((person) -> {
						PersonType p = (PersonType) person.getRef();
						textArea.append("Role: " + p.getId() + "\n");
					});
				}
			}
			private void setRole(String role) {
				PersonType victimRole = new PersonType();
				victimRole.setId(role);
				victim.getRoleOfPerson().add(victimRole);
			}

			// Victim Sequence Number Text
			private void getSeqNum(VictimType vic) {
				if (vic.getVictimSequenceNumberText() != null) {
					textArea.append("Sequence Number: " + vic.getVictimSequenceNumberText().getValue() + "\n");
				}
			}
			private void setSeqNum(String seqNum) {
				TextType victSeqNumText = new TextType();
				victSeqNumText.setValue(seqNum);
				victim.setVictimSequenceNumberText(victSeqNumText);
			}

			// Victim Injury
			private void getInjury(VictimType vic) {
				if (vic.getVictimInjury() != null) {
					ArrayList<InjuryType> injuryList = (ArrayList<InjuryType>) vic.getVictimInjury();
					injuryList.forEach((injury) -> {
						JAXBElement<?> injuryElement = injury.getInjuryCategory();
						if (injuryElement.getValue() instanceof InjuryCategoryCodeType) {
							InjuryCategoryCodeType injuryCode = (InjuryCategoryCodeType) injuryElement.getValue();
							textArea.append("Injury: " + injuryCode.getValue().toString() + "\n");
						} else if (injuryElement.getValue() instanceof Object) {
							Object injuryCode = (Object) injuryElement.getValue();
							textArea.append("Injury: " + injuryCode.toString() + "\n");
						}
					});
				}
			}
			private void setInjury(String injury) {
				InjuryType victimInjury = new InjuryType();
				InjuryCategoryCodeType victimInjuryCatCode = new InjuryCategoryCodeType();
				InjuryCategoryCodeSimpleType victimInjuryCatCodeSimple;
				switch (injury) {
				case "n/a":
					// do nothing
					break;
				case "Apparent Broken Bones":
					victimInjuryCatCodeSimple = InjuryCategoryCodeSimpleType.B;
					victimInjuryCatCode.setValue(victimInjuryCatCodeSimple);
					break;
				case "Possible Internal Injury":
					victimInjuryCatCodeSimple = InjuryCategoryCodeSimpleType.I;
					victimInjuryCatCode.setValue(victimInjuryCatCodeSimple);
					break;
				case "Severe Laceration":
					victimInjuryCatCodeSimple = InjuryCategoryCodeSimpleType.L;
					victimInjuryCatCode.setValue(victimInjuryCatCodeSimple);
					break;
				case "Apparent Minor Injury":
					victimInjuryCatCodeSimple = InjuryCategoryCodeSimpleType.M;
					victimInjuryCatCode.setValue(victimInjuryCatCodeSimple);
					break;
				case "None":
					victimInjuryCatCodeSimple = InjuryCategoryCodeSimpleType.N;
					victimInjuryCatCode.setValue(victimInjuryCatCodeSimple);
					break;
				case "Other Major Injury":
					victimInjuryCatCodeSimple = InjuryCategoryCodeSimpleType.O;
					victimInjuryCatCode.setValue(victimInjuryCatCodeSimple);
					break;
				case "Loss of Teeth":
					victimInjuryCatCodeSimple = InjuryCategoryCodeSimpleType.T;
					victimInjuryCatCode.setValue(victimInjuryCatCodeSimple);
					break;
				case "Unconsciousness":
					victimInjuryCatCodeSimple = InjuryCategoryCodeSimpleType.U;
					victimInjuryCatCode.setValue(victimInjuryCatCodeSimple);
					break;
				}
				JAXBElement<InjuryCategoryCodeType> victimInjuryCatElement = jxdm5ObjectFactory
						.createInjuryCategoryCode(victimInjuryCatCode);
				victimInjury.setInjuryCategory(victimInjuryCatElement);
				if (injury!="n/a") victim.getVictimInjury().add(victimInjury);
			}

			// Victim Category Code
			private void getCategoryCode(VictimType vic) {
				if (vic.getVictimCategory() != null) {
					JAXBElement<?> victimCategoryElement = vic.getVictimCategory();
					if (victimCategoryElement.getValue() instanceof VictimCategoryCodeType) {
						VictimCategoryCodeType victimCode = (VictimCategoryCodeType) victimCategoryElement.getValue();
						textArea.append("Category Code: " + victimCode.getValue().toString() + "\n");
					} else if (victimCategoryElement.getValue() instanceof Object) {
						Object victimCode = (Object) victimCategoryElement.getValue();
						textArea.append("Category Code: " + victimCode.toString() + "\n");
					}
				}
			}
			private void setCategoryCode(String code) {
				VictimCategoryCodeType victimCatCode = new VictimCategoryCodeType();
				VictimCategoryCodeSimpleType victimCatCodeSimple;
				switch (code) {
				case "Business":
					victimCatCodeSimple = VictimCategoryCodeSimpleType.B;
					victimCatCode.setValue(victimCatCodeSimple);
					break;
				case "Financial Institution":
					victimCatCodeSimple = VictimCategoryCodeSimpleType.F;
					victimCatCode.setValue(victimCatCodeSimple);
					break;
				case "Government":
					victimCatCodeSimple = VictimCategoryCodeSimpleType.G;
					victimCatCode.setValue(victimCatCodeSimple);
					break;
				case "Individual":
					victimCatCodeSimple = VictimCategoryCodeSimpleType.I;
					victimCatCode.setValue(victimCatCodeSimple);
					break;
				case "Law Enforcement Officer":
					victimCatCodeSimple = VictimCategoryCodeSimpleType.L;
					victimCatCode.setValue(victimCatCodeSimple);
					break;
				case "Other":
					victimCatCodeSimple = VictimCategoryCodeSimpleType.O;
					victimCatCode.setValue(victimCatCodeSimple);
					break;
				case "Religious Organization":
					victimCatCodeSimple = VictimCategoryCodeSimpleType.R;
					victimCatCode.setValue(victimCatCodeSimple);
					break;
				case "Society/Public":
					victimCatCodeSimple = VictimCategoryCodeSimpleType.S;
					victimCatCode.setValue(victimCatCodeSimple);
					break;
				case "Unknown":
					victimCatCodeSimple = VictimCategoryCodeSimpleType.U;
					victimCatCode.setValue(victimCatCodeSimple);
					break;

				}
				JAXBElement<VictimCategoryCodeType> victimCatCodeElement = jxdm5ObjectFactory
						.createVictimCategoryCode(victimCatCode);
				victim.setVictimCategory(victimCatCodeElement);
			}

			// Victim Aggravated Assault Homicide Factor Code
			private void getAssaultHomFactor(VictimType vic) {
				if (vic.getVictimAggravatedAssaultHomicideFactor() != null) {
					List<JAXBElement<?>> aggAssaultElementList = vic.getVictimAggravatedAssaultHomicideFactor();
					for (Iterator<JAXBElement<?>> iterator = aggAssaultElementList.iterator(); iterator.hasNext();) {
						JAXBElement<?> element = (JAXBElement<?>) iterator.next();
						// JAXBElement<?> aggAssaultElement = (JAXBElement<?>)
						// element.getValue();
						JAXBElement<?> aggAssaultElement = (JAXBElement<?>) element;

						if (aggAssaultElement.getValue() instanceof AggravatedAssaultHomicideFactorsCodeType) {
							AggravatedAssaultHomicideFactorsCodeType aggAssaultCode = (AggravatedAssaultHomicideFactorsCodeType) aggAssaultElement
									.getValue();
							textArea.append("Agg Assault Hom Factor Code:  " + aggAssaultCode.getValue() + "\n");
						} else if (aggAssaultElement.getValue() instanceof Object) {
							Object aggAssaultCode = (Object) aggAssaultElement.getValue();
							textArea.append("Agg Assault Hom Factor Code:  " + aggAssaultCode.toString() + "\n");
						}
					}
				}
			}
			private void setAssaultHomFactor(String[] factors) {
				for (int i = 0; i < factors.length; i++) {
					AggravatedAssaultHomicideFactorsCodeType victimAggAssaultFactor = new AggravatedAssaultHomicideFactorsCodeType();
					switch (factors[i]) {
					case "n/a":
						// do nothing
						break;
					case "Argument":
						victimAggAssaultFactor.setValue("01");
						break;
					case "Assault on Law Enforcement Officer(s)":
						victimAggAssaultFactor.setValue("02");
						break;
					case "Drug Dealing":
						victimAggAssaultFactor.setValue("03");
						break;
					case "Gangland (Organized Crime Involvement)":
						victimAggAssaultFactor.setValue("04");
						break;
					case "Juvenile Gang":
						victimAggAssaultFactor.setValue("05");
						break;
					case "Lovers' Quarrel":
						victimAggAssaultFactor.setValue("06");
						break;
					case "Mercy Killing (Not applicable to Aggravated Assault)":
						victimAggAssaultFactor.setValue("07");
						break;
					case "Other Felony Involved":
						victimAggAssaultFactor.setValue("08");
						break;
					case "Other Circumstances":
						victimAggAssaultFactor.setValue("09");
						break;
					case "Unknown Circumstances":
						victimAggAssaultFactor.setValue("10");
						break;
					case "Criminal Killed by Private Citizen":
						victimAggAssaultFactor.setValue("20");
						break;
					case "Criminal Killed by Police Officer":
						victimAggAssaultFactor.setValue("21");
						break;
					case "Child Playing With Weapon":
						victimAggAssaultFactor.setValue("30");
						break;
					case "Gun-Cleaning Accident":
						victimAggAssaultFactor.setValue("31");
						break;
					case "Hunting Accident":
						victimAggAssaultFactor.setValue("32");
						break;
					case "Other Negligent Weapon Handling":
						victimAggAssaultFactor.setValue("33");
						break;
					case "Other Negligent Killings":
						victimAggAssaultFactor.setValue("34");
						break;
					}
					JAXBElement<AggravatedAssaultHomicideFactorsCodeType> victimAggAssaultFactorElement = jxdm5ObjectFactory
							.createVictimAggravatedAssaultHomicideFactorCode(victimAggAssaultFactor);
					if (factors[i] != "n/a")
						victim.getVictimAggravatedAssaultHomicideFactor().add(victimAggAssaultFactorElement);
				}
			}

			// Victim Justifiable Homicide Factor code
			private void getJustHomCode(VictimType vic) {
				if (vic.getVictimJustifiableHomicideFactor() != null) {
				List<JAXBElement<?>> justHomElementList = vic.getVictimJustifiableHomicideFactor();
					for (Iterator<JAXBElement<?>> iterator = justHomElementList.iterator(); iterator.hasNext();) {
						JAXBElement<?> element = (JAXBElement<?>) iterator.next();
						JAXBElement<?> justHomElement =  (JAXBElement<?>) element;

						if (justHomElement.getValue() instanceof JustifiableHomicideFactorsCodeType) {
							JustifiableHomicideFactorsCodeType jusHomeCode = (JustifiableHomicideFactorsCodeType) justHomElement
									.getValue();
							textArea.append("Just Hom Factor Code:  " + jusHomeCode.getValue().toString() + "\n");
						} else if (justHomElement.getValue() instanceof Object) {
							Object jusHomeCode = (Object) justHomElement.getValue();
							textArea.append("Just Hom Factor Code:  " + jusHomeCode.toString() + "\n");
						}
					}
				}
			}
			private void setJustHomCode(String code) {
				JustifiableHomicideFactorsCodeType victimJustHomCode = new JustifiableHomicideFactorsCodeType();
				JustifiableHomicideFactorsCodeSimpleType victimJustHomCodeSimple;
				switch (code) {
				case "n/a":
					// do nothing
					break;
				case "Criminal Attacked Police Officer and That Officer Killed Criminal":
					victimJustHomCodeSimple = JustifiableHomicideFactorsCodeSimpleType.A;
					victimJustHomCode.setValue(victimJustHomCodeSimple);
					break;
				case "Criminal Attacked Police Officer and Criminal Killed by Another Police Officer":
					victimJustHomCodeSimple = JustifiableHomicideFactorsCodeSimpleType.B;
					victimJustHomCode.setValue(victimJustHomCodeSimple);
					break;
				case "Criminal Attacked a Civilian":
					victimJustHomCodeSimple = JustifiableHomicideFactorsCodeSimpleType.C;
					victimJustHomCode.setValue(victimJustHomCodeSimple);
					break;
				case "Criminal Attempted Flight From a Crime":
					victimJustHomCodeSimple = JustifiableHomicideFactorsCodeSimpleType.D;
					victimJustHomCode.setValue(victimJustHomCodeSimple);
					break;
				case "Criminal Killed in Commission of a Crime":
					victimJustHomCodeSimple = JustifiableHomicideFactorsCodeSimpleType.E;
					victimJustHomCode.setValue(victimJustHomCodeSimple);
					break;
				case "Criminal Resisted Arrest":
					victimJustHomCodeSimple = JustifiableHomicideFactorsCodeSimpleType.F;
					victimJustHomCode.setValue(victimJustHomCodeSimple);
					break;
				case "Unable to Determine/Not Enough Information":
					victimJustHomCodeSimple = JustifiableHomicideFactorsCodeSimpleType.G;
					victimJustHomCode.setValue(victimJustHomCodeSimple);
					break;
				}
				JAXBElement<JustifiableHomicideFactorsCodeType> victimJustHomCodeElement = jxdm5ObjectFactory
						.createVictimJustifiableHomicideFactorCode(victimJustHomCode);
				if (code != "n/a") victim.getVictimJustifiableHomicideFactor().add(victimJustHomCodeElement);
			}

			private VictimType createVictim(String[] victimValues) {

				String[] aggAssaultValues = {victimValues[5], victimValues[6]};

				
				setID(victimValues[0]);
				setRole(victimValues[1]);
				setSeqNum(victimValues[2]);
				setInjury(victimValues[3]);
				setCategoryCode(victimValues[4]);
				setAssaultHomFactor(aggAssaultValues);
				setJustHomCode(victimValues[7]);

				return victim;
			}
		};
		private class Subject {

			SubjectType subject = new SubjectType();

			private void unmarshallSubject(ReportType report) {
				if (report.getSubject() != null){
				ArrayList<SubjectType> subjectList = (ArrayList<SubjectType>) report.getSubject();
				textArea.append("***********Subject ************ \n");
				subjectList.forEach((subject) -> {

					textArea.append("**Subject** \n");

					getID(subject);
					getRole(subject);
					getSeqNum(subject);
				});
			}}

			// Subject ID
			private void getID(SubjectType subj) {
				textArea.append("Subject ID: " + subj.getId() + "\n");
			}
			private void setID(String id) {
				subject.setId(id);
			}

			// Role
			private void getRole(SubjectType subj) {
				ArrayList<PersonType> subjectRoleList = (ArrayList<PersonType>) subj.getRoleOfPerson();
				subjectRoleList.forEach((person) -> {
					PersonType p = (PersonType) person.getRef();
					textArea.append("Role:" + p.getId() + "\n");
				});
			}
			private void setRole(String role) {
				PersonType subjectRole = new PersonType();
				subjectRole.setId(role);
				subject.getRoleOfPerson().add(subjectRole);
			}

			// Subject Sequence Number Text
			private void getSeqNum(SubjectType subj) {
				textArea.append("Sequence Number : " + subj.getSubjectSequenceNumberText().getValue() + "\n");
			}
			private void setSeqNum(String num) {
				TextType subSeqNumText = new TextType();
				subSeqNumText.setValue(num);
				subject.setSubjectSequenceNumberText(subSeqNumText);
			}

			private SubjectType createSubject(String[] subValues) {

				setID(subValues[0]);
				setRole(subValues[1]);
				setSeqNum(subValues[2]);

				return subject;
			}
		};
		private class Arrestee {

			ArresteeType arrestee = new ArresteeType();

			private void unmarshallArrestee(ReportType report) {
				if (report.getArrestee() != null){
				ArrayList<ArresteeType> arresteelist = (ArrayList<ArresteeType>) report.getArrestee();
				textArea.append("***********Arrestee ************ \n");
				arresteelist.forEach((arrestee) -> {

					textArea.append("**Arrestee** \n");

					getSeqID(arrestee);
					getRole(arrestee);
					getClrInd(arrestee);
					getMultArrSegCode(arrestee);
					getArmedWithCode(arrestee);
					getJuvDisp(arrestee);
					getSubCount(arrestee);
				});
				}

			}

			// Multiple Arrestee Seg. code
			private void getMultArrSegCode(ArresteeType ar) {
				JAXBElement<?> arrestSubjectCountElement = ar.getArrestSubjectCount();
				if (arrestSubjectCountElement.getValue() instanceof MultipleArresteeSegmentsCodeType) {
					MultipleArresteeSegmentsCodeType multSegCode = (MultipleArresteeSegmentsCodeType) arrestSubjectCountElement
							.getValue();
					textArea.append("Multiple Arrestee Segment Code:" + multSegCode.getValue().value() + "\n");
				} else if (arrestSubjectCountElement.getValue() instanceof Object) {
					Object multSegCode = (Object) arrestSubjectCountElement.getValue();
					textArea.append("Multiple Arrestee Segment Code:" + multSegCode.toString() + "\n");
				}
			}

			// Arrestee Sequence ID
			private void getSeqID(ArresteeType ar) {
				textArea.append("Arrest ID: " + ar.getId() + "\n");
				textArea.append("Arrest Seq ID: " + ar.getArrestSequenceID().getValue() + "\n");

			}
			private void setSeqID(String seqID) {
				gov.niem.release.niem.proxy.xsd._3.String arresteeSequenceID = new gov.niem.release.niem.proxy.xsd._3.String();
				arresteeSequenceID.setValue(seqID);
				arrestee.setArrestSequenceID(arresteeSequenceID);
			}

			// Arrestee Role of Person
			private void getRole(ArresteeType ar) {
				ArrayList<PersonType> personRoleList = (ArrayList<PersonType>) ar.getRoleOfPerson();
				personRoleList.forEach((personRole) -> {
					PersonType p = (PersonType) personRole.getRef();
					textArea.append("Arrestee Role Ref:" + p.getId() + "\n");
				});
			}
			private void setRole(String role) {
				PersonType arresteeRole = new PersonType();
				arresteeRole.setId(role);
				arrestee.getRoleOfPerson().add(arresteeRole);
			}

			// Arrestee Clearance Indicator
			private void getClrInd(ArresteeType ar) {
				if (ar.getArresteeClearanceIndicator() != null) {
					if (ar.getArresteeClearanceIndicator().isValue()) {
						textArea.append("Clearance Indicator: TRUE" + "\n");
					} else {
						textArea.append("Clearance Indicator: FALSE" + "\n");
					}
				}
			}
			private void setClrInd(String ind) {
				gov.niem.release.niem.proxy.xsd._3.Boolean arresteeClearanceInd = new gov.niem.release.niem.proxy.xsd._3.Boolean();
				if (ind == "true")
					arresteeClearanceInd.setValue(true);
				else
					arresteeClearanceInd.setValue(false);
				arrestee.setArresteeClearanceIndicator(arresteeClearanceInd);
			}

			// Arrestee Armed With Code
			private void getArmedWithCode(ArresteeType ar) {
				List<JAXBElement<?>> armedWithList = ar.getArresteeArmedWith();
				for (Iterator<JAXBElement<?>> iterator = armedWithList.iterator(); iterator.hasNext();) {
					JAXBElement<?> element = (JAXBElement<?>) iterator.next();
					if (element.getValue() instanceof ArresteeWeaponCodeType) {
						ArresteeWeaponCodeType weapon = (ArresteeWeaponCodeType) element.getValue();
						textArea.append("Armed With: " + weapon.getValue() + "\n");
					} else if (element.getValue() instanceof Object) {
						Object weapon = (Date) element.getValue();
						textArea.append("Armed With: " + weapon.toString() + "\n");
					}
				}
			}
			private void setArmedWithCode(String[] code) {
				for (int i = 0; i < code.length; i++) {
					ArresteeWeaponCodeType arresteeWeaponCode = new ArresteeWeaponCodeType();
					switch (code[i]) {
					case "Unarmed":
						arresteeWeaponCode.setValue("01");
						break;
					case "Firearm (type not stated)":
						arresteeWeaponCode.setValue("11");
						break;
					case "Automatic Firearm (type not stated)":
						arresteeWeaponCode.setValue("11A");
						break;
					case "Handgun":
						arresteeWeaponCode.setValue("12");
						break;
					case "Automatic Handgun":
						arresteeWeaponCode.setValue("12A");
						break;
					case "Rifle":
						arresteeWeaponCode.setValue("13");
						break;
					case "Automatic Rifle":
						arresteeWeaponCode.setValue("13A");
						break;
					case "Shotgun":
						arresteeWeaponCode.setValue("14");
						break;
					case "Automatic Shotgun":
						arresteeWeaponCode.setValue("14A");
						break;
					case "Other Firearm":
						arresteeWeaponCode.setValue("15");
						break;
					case "Other Automatic Firearm":
						arresteeWeaponCode.setValue("15A");
						break;
					case "Lethal Cutting Instument":
						arresteeWeaponCode.setValue("16");
						break;
					case "Club/Blackjack/Brass Knuckles":
						arresteeWeaponCode.setValue("17");
						break;

					}
					if (code[i] != "n/a") {
						JAXBElement<ArresteeWeaponCodeType> arresteeArmedWithElement = jxdm5ObjectFactory
								.createArresteeArmedWithCode(arresteeWeaponCode);
						arrestee.getArresteeArmedWith().add(arresteeArmedWithElement);
					}
				}
			}

			// Arrestee Juvenile Disposition
			private void getJuvDisp(ArresteeType ar) {
				if (ar.getArresteeJuvenileDisposition() != null) {
					JAXBElement<?> juvDispElement = ar.getArresteeJuvenileDisposition();
					if (juvDispElement.getValue() instanceof JuvenileDispositionCodeType) {
						JuvenileDispositionCodeType juvDispCode = (JuvenileDispositionCodeType) juvDispElement
								.getValue();
						textArea.append("Juv Disp Code: " + juvDispCode.getValue().toString() + "\n");
					} else if (juvDispElement.getValue() instanceof Object) {
						Object juvDispCode = (Object) juvDispElement.getValue();
						textArea.append("Juv Disp Code: " + juvDispCode.toString() + "\n");
					}
				}
			}
			private void setJuvDisp(String disp) {
				JuvenileDispositionCodeType juvDispCode = new JuvenileDispositionCodeType();
				JuvenileDispositionCodeSimpleType juvDispCodeSimple = JuvenileDispositionCodeSimpleType.H;
				juvDispCode.setValue(juvDispCodeSimple);
				switch (disp) {
				case "Handled Within Department":
					juvDispCodeSimple = JuvenileDispositionCodeSimpleType.H;
					juvDispCode.setValue(juvDispCodeSimple);
					break;
				case "Referred to Other Authorities":
					juvDispCodeSimple = JuvenileDispositionCodeSimpleType.R;
					juvDispCode.setValue(juvDispCodeSimple);
					break;
				}
				JAXBElement<JuvenileDispositionCodeType> juvDispCodeElement = jxdm5ObjectFactory
						.createArresteeJuvenileDispositionCode(juvDispCode);
				arrestee.setArresteeJuvenileDisposition(juvDispCodeElement);
			}

			// Arrestee Subject Count
			private void getSubCount(ArresteeType ar) {
				if (ar.getSubjectSequenceNumberText() != null) {
					textArea.append("Subject Sequence Number: " + ar.getSubjectSequenceNumberText().getValue() + "\n");
				}
			}
			private void setSubCount(String count) {
				MultipleArresteeSegmentsCodeType multArresteeSegCode = new MultipleArresteeSegmentsCodeType();
				MultipleArresteeSegmentsCodeSimpleType multArresteeSegCodeSimple = MultipleArresteeSegmentsCodeSimpleType.M;
				multArresteeSegCode.setValue(multArresteeSegCodeSimple);
				switch (count) {
				case "Count Arrestee":
					multArresteeSegCodeSimple = MultipleArresteeSegmentsCodeSimpleType.C;
					multArresteeSegCode.setValue(multArresteeSegCodeSimple);
				case "Multiple":
					multArresteeSegCodeSimple = MultipleArresteeSegmentsCodeSimpleType.M;
					multArresteeSegCode.setValue(multArresteeSegCodeSimple);
				case "Not Applicable":
					multArresteeSegCodeSimple = MultipleArresteeSegmentsCodeSimpleType.N;
					multArresteeSegCode.setValue(multArresteeSegCodeSimple);
				}
				JAXBElement<MultipleArresteeSegmentsCodeType> multArresteeSegCodeElement = jxdm5ObjectFactory
						.createArrestSubjectCountCode(multArresteeSegCode);
				arrestee.setArrestSubjectCount(multArresteeSegCodeElement);
			}

			private ArresteeType createArrestee(String[] arresteeValues) {

				String[] armedWithArray = new String[2];
				for (int i=0; i<2; i++) armedWithArray[i] = arresteeValues[i+3];
				
				setSeqID(arresteeValues[0]);
				setRole(arresteeValues[1]);
				setClrInd(arresteeValues[2]);
				setArmedWithCode(armedWithArray);
				if (arresteeValues[5] != "n/a") setJuvDisp(arresteeValues[5]);
				setSubCount(arresteeValues[6]);

				return arrestee;
			}
		};
		private class Arrest {

			ArrestType arrest = new ArrestType();

			private void unmarshallArrest(ReportType report) {
				if (report.getArrest() != null){
				List<ArrestType> arrestList = report.getArrest();
				textArea.append("***********Arrest ************ \n");
				arrestList.forEach((arrest) -> {

					textArea.append("**Arrest** \n");

					getID(arrest);
					getArrestDate(arrest);
					getArrestActivity(arrest);
					getArrestCategory(arrest);
					getArrestCharge(arrest);
				});
			}}

			// Arrest ID
			private void getID(ArrestType ar) {
				textArea.append("Arrest ID: " + ar.getId() + "\n");
			}
			private void setID(String id) {
				arrest.setId(id);
			}

			// Arrest Date
			private void getArrestDate(ArrestType ar) {
				JAXBElement<?> arrestDateElement = ar.getActivityDateRepresentation();
				if (arrestDateElement.getValue() instanceof DateType) {
					DateType date = (DateType) arrestDateElement.getValue();
					List<JAXBElement<?>> dateElementList = date.getDateRepresentation();
					for (Iterator<JAXBElement<?>> iterator = dateElementList.iterator(); iterator.hasNext();) {
						JAXBElement<?> element = (JAXBElement<?>) iterator.next();
						if (element.getValue() instanceof Date) {
							Date arrestDate = (Date) element.getValue();
							textArea.append("Arrest Date (Date): " + arrestDate.getValue().toString() + "\n");
						} else if (element.getValue() instanceof DateTime) {
							DateTime arrestDate = (DateTime) element.getValue();
							textArea.append("Arrest Date (DateTime): " + arrestDate.getValue().toString() + "\n");
						} else if (element.getValue() instanceof GYearMonth) {
							GYearMonth arrestDate = (GYearMonth) element.getValue();
							textArea.append("Arrest Date (GYearMonth): " + arrestDate.getValue().toString());
						} else if (element.getValue() instanceof Object) {
							Object arrestDate = (Date) element.getValue();
							textArea.append("Arrest Date (Object): " + arrestDate.toString() + "\n");
						}
					}
				} else if (arrestDateElement.getValue() instanceof Object) {
					textArea.append("Arrest Date Element (Object)" + "\n");
				}
			}
			private void setArrestDate(String year, String month, String day) {
				Date arrestDate = new Date();
				if (!year.isEmpty() && !month.isEmpty() && !day.isEmpty()) {
					arrestDate.setValue(
							createGregCal(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)));
					DateType arrestDateType = niemCore3ObjectFactory.createDateType();
					JAXBElement<Date> arrestDateElement = niemCore3ObjectFactory.createDate(arrestDate);
					arrestDateType.getDateRepresentation().add(arrestDateElement);
					JAXBElement<DateType> arrestDateTypeElement = niemCore3ObjectFactory
							.createActivityDate(arrestDateType);
					arrest.setActivityDateRepresentation(arrestDateTypeElement);
				}
			}

			// Arrest Activity
			private void getArrestActivity(ArrestType ar) {
				textArea.append("Arrest Activity Identification: "
						+ ar.getActivityIdentification().getIdentificationID().getValue() + "\n");
			}
			private void setArrestActivity(String activity) {
				IdentificationType arrestActivityID = new IdentificationType();
				gov.niem.release.niem.proxy.xsd._3.String arrestActivityString = new gov.niem.release.niem.proxy.xsd._3.String();
				arrestActivityString.setValue(activity);
				arrestActivityID.setIdentificationID(arrestActivityString);
				arrest.setActivityIdentification(arrestActivityID);
			}

			// Arrest Category
			private void getArrestCategory(ArrestType ar) {
				JAXBElement<?> arrestCategoryElement = ar.getArrestCategory();
				if (arrestCategoryElement.getValue() instanceof ArrestCategoryCodeType) {
					ArrestCategoryCodeType arrestCat = (ArrestCategoryCodeType) arrestCategoryElement.getValue();
					textArea.append("Arrest Category: " + arrestCat.getValue().value() + "\n");
				} else if (arrestCategoryElement.getValue() instanceof Object) {
					Object arrestCat = (Object) arrestCategoryElement.getValue();
					textArea.append("Arrest Category: " + arrestCat.toString() + "\n");
				}
			}
			private void setArrestCategory(String cat) {
				ArrestCategoryCodeType arrestCatCode = new ArrestCategoryCodeType();
				ArrestCategoryCodeSimpleType arrestCatCodeSimple;
				switch (cat) {
				case "On-View Arrest":
					arrestCatCodeSimple = ArrestCategoryCodeSimpleType.O;
					arrestCatCode.setValue(arrestCatCodeSimple);
					break;
				case "Summoned/Cited":
					arrestCatCodeSimple = ArrestCategoryCodeSimpleType.S;
					arrestCatCode.setValue(arrestCatCodeSimple);
					break;
				case "Taken Into Custody":
					arrestCatCodeSimple = ArrestCategoryCodeSimpleType.T;
					arrestCatCode.setValue(arrestCatCodeSimple);
					break;
				}
				JAXBElement<ArrestCategoryCodeType> arrestCatCodeElement = jxdm5ObjectFactory
						.createArrestCategoryCode(arrestCatCode);
				arrest.setArrestCategory(arrestCatCodeElement);
			}

			// Arrest Charge
			private void getArrestCharge(ArrestType ar) {
				JAXBElement<?> arrestChargeUCRElement = ar.getArrestCharge().getChargeUCR();
				if (arrestChargeUCRElement.getValue() instanceof gov.fbi.cjis.nibrs.nibrs_codes._4.OffenseCodeType) {
					gov.fbi.cjis.nibrs.nibrs_codes._4.OffenseCodeType arrestCode = (gov.fbi.cjis.nibrs.nibrs_codes._4.OffenseCodeType) arrestChargeUCRElement
							.getValue();
					textArea.append("Arrest UCR Charge Code:" + arrestCode.getValue() + "\n");
				} else if (arrestChargeUCRElement
						.getValue() instanceof gov.niem.release.niem.codes.fbi_ucr._3.OffenseCodeType) {
					gov.niem.release.niem.codes.fbi_ucr._3.OffenseCodeType arrestCode = (gov.niem.release.niem.codes.fbi_ucr._3.OffenseCodeType) arrestChargeUCRElement
							.getValue();
					textArea.append("Arrest UCR Charge Code:" + arrestCode.getValue() + "\n");
				} else if (arrestChargeUCRElement.getValue() instanceof Object) {
					Object arrestCode = (Object) arrestChargeUCRElement.getValue();
					textArea.append("Arrest UCR Charge Code:" + arrestCode.toString() + "\n");
				}
			}
			private void setArrestCharge(String charge) {
				ChargeType arrestCharg = new ChargeType();
				gov.niem.release.niem.codes.fbi_ucr._3.OffenseCodeType arrestChargeCode = new gov.niem.release.niem.codes.fbi_ucr._3.OffenseCodeType();
				switch (charge) {
				//////////////////////
				// GROUP A Offenses //
				//////////////////////
				case "Murder & Nonnegligent Manslaughter":
					arrestChargeCode.setValue("09A");
					break;
				case "Negligent Manslaughter":
					arrestChargeCode.setValue("09B");
					break;
				case "Justifiable Homicide":
					arrestChargeCode.setValue("09C");
					break;
				case "Kidnapping/Abduction":
					arrestChargeCode.setValue("100");
					break;
				case "Rape":
					arrestChargeCode.setValue("11A");
					break;
				case "Sodomy":
					arrestChargeCode.setValue("11B");
					break;
				case "Sexual Assault With An Object":
					arrestChargeCode.setValue("11C");
					break;
				case "Fondling":
					arrestChargeCode.setValue("11D");
					break;
				case "Robbery":
					arrestChargeCode.setValue("120");
					break;
				case "Aggravated Assault":
					arrestChargeCode.setValue("13A");
					break;
				case "Simple Assault":
					arrestChargeCode.setValue("13B");
					break;
				case "Intimidation":
					arrestChargeCode.setValue("13C");
					break;
				case "Arson":
					arrestChargeCode.setValue("200");
					break;
				case "Extortion/Blackmail":
					arrestChargeCode.setValue("210");
					break;
				case "Burglary/Breaking & Entering":
					arrestChargeCode.setValue("220");
					break;
				case "Pocket-picking":
					arrestChargeCode.setValue("23A");
					break;
				case "Purse-snatching":
					arrestChargeCode.setValue("23B");
					break;
				case "Shoplifting":
					arrestChargeCode.setValue("90F");
					break;
				case "Theft From Building":
					arrestChargeCode.setValue("23D");
					break;
				case "Theft From Coin-Operated Machine":
					arrestChargeCode.setValue("23E");
					break;
				case "Theft From Motor Vehicle":
					arrestChargeCode.setValue("23F");
					break;
				case "Theft of Motor Vehicle Parts":
					arrestChargeCode.setValue("23G");
					break;
				case "All Other Larceny":
					arrestChargeCode.setValue("23H");
					break;
				case "Motor Vehicle Theft":
					arrestChargeCode.setValue("240");
					break;
				case "Counterfeiting/Forgery":
					arrestChargeCode.setValue("250");
					break;
				case "False Pretenses/Swindle/Confidence Game":
					arrestChargeCode.setValue("26A");
					break;
				case "Credit Card/Automated Teller Machine Fraud":
					arrestChargeCode.setValue("26B");
					break;
				case "Impersonation":
					arrestChargeCode.setValue("26C");
					break;
				case "Welfare Fraud":
					arrestChargeCode.setValue("26D");
					break;
				case "Wire Fraud":
					arrestChargeCode.setValue("26E");
					break;
				case "Identity Theft":
					arrestChargeCode.setValue("26F");
					break;
				case "Hacking/Computer Invasion":
					arrestChargeCode.setValue("26G");
					break;
				case "Embezzlement":
					arrestChargeCode.setValue("270");
					break;
				case "Stolen Offenses":
					arrestChargeCode.setValue("280");
					break;
				case "Destruction/Damage/Vandalism of Property":
					arrestChargeCode.setValue("290");
					break;
				case "Drug/Narcotic Violations":
					arrestChargeCode.setValue("35A");
					break;
				case "Drug Equipment Violations":
					arrestChargeCode.setValue("35B");
					break;
				case "Incest":
					arrestChargeCode.setValue("36A");
					break;
				case "Statutory Rape":
					arrestChargeCode.setValue("36B");
					break;
				case "Pornography/Obscene Material":
					arrestChargeCode.setValue("370");
					break;
				case "Betting/Wagering":
					arrestChargeCode.setValue("39A");
					break;
				case "Operating/Promoting/Assisting Gambling":
					arrestChargeCode.setValue("39B");
					break;
				case "Gambling Equipment Violation":
					arrestChargeCode.setValue("39C");
					break;
				case "Sports Tampering":
					arrestChargeCode.setValue("39D");
					break;
				case "Prostitution":
					arrestChargeCode.setValue("40A");
					break;
				case "Assisting or Promoting Prostitution":
					arrestChargeCode.setValue("40B");
					break;
				case "Purchasing Prostitution":
					arrestChargeCode.setValue("40C");
					break;
				case "Bribery":
					arrestChargeCode.setValue("510");
					break;
				case "Weapon Law Violations":
					arrestChargeCode.setValue("520");
					break;
				case "Human Trafficking, Commercial Sex Acts":
					arrestChargeCode.setValue("64A");
					break;
				case "Human Trafficking, Involuntary Servitude":
					arrestChargeCode.setValue("64B");
					break;
				case "Animal Cruelty":
					arrestChargeCode.setValue("720");
					break;
					//////////////////////
					// GROUP B Offenses //
					//////////////////////
				case "Bad Checks":
					arrestChargeCode.setValue("90A");
					break;
				case "Curfew/Loitering/Vagrancy Violations":
					arrestChargeCode.setValue("90B");
					break;
				case "Disorderly Conduct":
					arrestChargeCode.setValue("90C");
					break;
				case "Driving Under the Influence":
					arrestChargeCode.setValue("90D");
					break;
				case "Drunkenness":
					arrestChargeCode.setValue("90E");
					break;
				case "Family Offenses, Nonviolent":
					arrestChargeCode.setValue("90F");
					break;
				case "Liquor Law Violations":
					arrestChargeCode.setValue("90G");
					break;
				case "Peeping Tom":
					arrestChargeCode.setValue("90H");
					break;
				case "Trespass of Real Property":
					arrestChargeCode.setValue("90J");
					break;
				case "All Other Offenses":
					arrestChargeCode.setValue("90Z");
					break;
				}
				JAXBElement<gov.niem.release.niem.codes.fbi_ucr._3.OffenseCodeType> arrestChargElement = jxdm5ObjectFactory
						.createChargeUCRCode(arrestChargeCode);
				arrestCharg.setChargeUCR(arrestChargElement);
				arrest.setArrestCharge(arrestCharg);
			}

			private ArrestType createArrest(String[] arrestValues) {
				setID(arrestValues[0]);
				setArrestDate(arrestValues[1], arrestValues[2], arrestValues[3]);
				setArrestActivity(arrestValues[7]);
				setArrestCategory(arrestValues[8]);
				setArrestCharge(arrestValues[9]);
				return arrest;
			}
		};
		private class ActivitySubjectAssociation {

			ActivitySubjectAssociationType activitySubjectAssociation = jxdm5ObjectFactory
					.createActivitySubjectAssociationType();

			private void unmarshallActivitySubjectAssociation(ReportType report) {
				if (report.getArrestSubjectAssociation() != null) {
					List<ActivitySubjectAssociationType> arrestSubjectAssociationList = report
							.getArrestSubjectAssociation();
					textArea.append("***********Arrest Subject Association ************" + "\n");
					List<ArrestType> arrestList = report.getArrest();
					arrestList.forEach((arrest) -> {

						arrestSubjectAssociationList.forEach((arrestSubjectAssociation) -> {
							ActivityType activityAssociationType = (ActivityType) arrestSubjectAssociation.getActivity()
									.getRef();

							if (activityAssociationType.getId().equals(arrest.getId())) {
								SubjectType subject = (SubjectType) arrestSubjectAssociation.getSubject().getRef();
								textArea.append("**Arrest/Subject Association**" + "\n");
								textArea.append("Activity:" + arrest.getId() + "\n");
								textArea.append("Subject:" + subject.getId() + "\n");
							}
						});
					});
				}

			}

			// private void setActivitySubjectAssociation()
			// activity
			private void setActivity(String activity) {
				List<ArrestType> arrestList = report.getArrest();
				arrestList.forEach((arrest) -> {
					if (arrest.getId() == activity){
						activitySubjectAssociation.setActivity(arrest);
					}
				});
			}
			// subject
			private void setSubject(String subject) {
				List<SubjectType> subjectList = report.getSubject();
				subjectList.forEach((sub) -> {
					if (sub.getId() == subject) activitySubjectAssociation.setSubject(sub);
				});
			}

			private ActivitySubjectAssociationType createActSubjAssoc(String[] values) {
				setActivity(values[0]);
				setSubject(values[1]);
				return activitySubjectAssociation;
			}
		};
		private class OffenseLocationAssociation {

			private OffenseLocationAssociationType offenseLocationAssociation = new OffenseLocationAssociationType();

			private void unmarshallOffenseLocationAssociation(ReportType report) {
				if (report.getOffenseLocationAssociation() != null) {
					List<OffenseLocationAssociationType> offenseLocationAssociationList = report
							.getOffenseLocationAssociation();
					textArea.append("***********Offense Location Association ************" + "\n");
					ArrayList<OffenseType> offenseList = (ArrayList<OffenseType>) report.getOffense();
					offenseList.forEach((offense) -> {
						offenseLocationAssociationList.forEach((offenseLocationAssociation) -> {
							OffenseType offenseType = (OffenseType) offenseLocationAssociation.getOffense().getRef();

							if (offenseType.getId().equals(offense.getId())) {
								LocationType locationType = (LocationType) offenseLocationAssociation.getLocation()
										.getRef();
								textArea.append("**Offense/Location Association**" + "\n");
								textArea.append("Offense: " + offense.getId() + "\n");
								textArea.append("Location: " + locationType.getId() + "\n");
							}
						});
					});
				}
			}

			// offense
			private void setOffense(String offenseInput) {
				List<OffenseType> offenseList = report.getOffense();
				offenseList.forEach((offense) -> {
					if (offense.getId() == offenseInput)	offenseLocationAssociation.setOffense(offense);
				});
			}

			// location
			private void setLocation(String locationInput) {
				List<LocationType> locList = report.getLocation();
				locList.forEach((location) -> {
					if (location.getId() == locationInput) 	offenseLocationAssociation.setLocation(location);
				});
			}

			private OffenseLocationAssociationType createOffLocAssoc(String[] offenseLocationArray) {
				setOffense(offenseLocationArray[0]);
				setLocation(offenseLocationArray[1]);
				return offenseLocationAssociation;
			}
		};
		private class OffenseVictimAssociation {

			OffenseVictimAssociationType offenseVictimAssociation = new OffenseVictimAssociationType();

			private void unmarshallOffenseVictimAssociation(ReportType report) {
				if (report.getOffenseVictimAssociation() != null) {

					List<OffenseVictimAssociationType> offenseVictimAssociationList = report
							.getOffenseVictimAssociation();
					textArea.append("***********Offense Victim Association ************" + "\n");
					ArrayList<OffenseType> offenseList = (ArrayList<OffenseType>) report.getOffense();
					offenseList.forEach((offense) -> {
						offenseVictimAssociationList.forEach((offenseVictimAssociation) -> {

							List<OffenseType> offenseTypeList = offenseVictimAssociation.getOffense();
							offenseTypeList.forEach((offenseType) -> {
								OffenseType offenseTypeRef = (OffenseType) offenseType.getRef();
								if (offenseTypeRef.getId().equals(offense.getId())) {

									List<VictimType> victimList = offenseVictimAssociation.getVictim();
									victimList.forEach((victim) -> {
										VictimType victimType = (VictimType) victim.getRef();
										textArea.append("**Offense/Victim Association**" + "\n");
										textArea.append("Offense: " + offense.getId() + "\n");
										textArea.append("Victim: " + victimType.getId() + "\n");
									});
								}
							});
						});
					});
				}
			}

			// offense
			private void setOffense(String offenseInput) {
				List<OffenseType> offenseList = report.getOffense();
				offenseList.forEach((offense) -> {
					if (offense.getId() == offenseInput) offenseVictimAssociation.getOffense().add(offense);
				});
			}

			// victim
			private void setVictim(String vic) {
				List<VictimType> vicList = report.getVictim();
				vicList.forEach((victim) -> {
					if (victim.getId() == vic) offenseVictimAssociation.getVictim().add(victim);
				});
			}

			private OffenseVictimAssociationType createOffVicAssoc(String[] offVicArray) {
				setOffense(offVicArray[0]);
				setVictim(offVicArray[1]);
				return offenseVictimAssociation;
			}
		};
		private class SubjectVictimAssociation {

			SubjectVictimAssociationType subjectVictimAssociation = new SubjectVictimAssociationType();

			private void unmarshallSubjectVictimAssociation(ReportType report) {
				if (report.getSubjectVictimAssociation() != null) {

					ArrayList<SubjectVictimAssociationType> subjectVictimAssociationList = (ArrayList<SubjectVictimAssociationType>) report
							.getSubjectVictimAssociation();
					textArea.append("***********Subject Victim Association ************" + "\n");
					ArrayList<SubjectType> subjectList = (ArrayList<SubjectType>) report.getSubject();
					subjectList.forEach((subject) -> {
						subjectVictimAssociationList.forEach((subjectVictimAssociation) -> {

							List<SubjectType> subjectTypeList = subjectVictimAssociation.getSubject();
							subjectTypeList.forEach((subjectType) -> {
								SubjectType subjectTypeRef = (SubjectType) subjectType.getRef();
								if (subjectTypeRef.getId().equals(subject.getId())) {

									List<VictimType> victimList = subjectVictimAssociation.getVictim();
									victimList.forEach((victim) -> {
										VictimType victimType = (VictimType) victim.getRef();
										textArea.append("**Subject/Victim Association**" + "\n");
										textArea.append("Subject: " + subject.getId() + "\n");
										textArea.append("Victim: " + victimType.getId() + "\n");

										List<JAXBElement<?>> subjectVictimRelationshipList = subjectVictimAssociation
												.getVictimToSubjectRelationship();
										subjectVictimRelationshipList.forEach((subjectVictimRelationship) -> {
											if (subjectVictimRelationship
													.getValue() instanceof VictimToSubjectRelationshipCodeType) {
												VictimToSubjectRelationshipCodeType victimSubjectRelationship = (VictimToSubjectRelationshipCodeType) subjectVictimRelationship
														.getValue();
												textArea.append("Victim to Subject Relationship:"
														+ victimSubjectRelationship.getValue().toString() + "\n");
											}
										});
									});
								}
							});
						});
					});
				}
			}

			// subject
			private void setSubject(String subjInput) {
				List<SubjectType> subjList = report.getSubject();
				subjList.forEach((subject) -> {
					if (subject.getId() == subjInput) subjectVictimAssociation.getSubject().add(subject);
				});
			}

			// victim
			private void setVictim(String vicInput) {
				List<VictimType> vicList = report.getVictim();
				vicList.forEach((victim) -> {
					if (victim.getId() == vicInput) subjectVictimAssociation.getVictim().add(victim);
				});
			}

			// subject/victim relationship
			private void setSubjVicRelationship(String relationship) {
				VictimToSubjectRelationshipCodeType subVicAssociationCode = new VictimToSubjectRelationshipCodeType();
				VictimToSubjectRelationshipCodeSimpleType subVicAssociationCodeSimple;
				switch (relationship){
				case "Acquaintance":
					subVicAssociationCodeSimple = VictimToSubjectRelationshipCodeSimpleType.ACQUAINTANCE;
					subVicAssociationCode.setValue(subVicAssociationCodeSimple);
					break;
				case "Babysittee":
					subVicAssociationCodeSimple = VictimToSubjectRelationshipCodeSimpleType.BABYSITTEE;
					subVicAssociationCode.setValue(subVicAssociationCodeSimple);
					break;
				case "Boyfriend_Girlfriend":
					subVicAssociationCodeSimple = VictimToSubjectRelationshipCodeSimpleType.BOYFRIEND_GIRLFRIEND;
					subVicAssociationCode.setValue(subVicAssociationCodeSimple);
					break;
				case "Child of Boyfriend_Girlfriend":
					subVicAssociationCodeSimple = VictimToSubjectRelationshipCodeSimpleType.CHILD_OF_BOYFRIEND_GIRLFRIEND;
					subVicAssociationCode.setValue(subVicAssociationCodeSimple);
					break;
				case "Family Member_Child":
					subVicAssociationCodeSimple = VictimToSubjectRelationshipCodeSimpleType.FAMILY_MEMBER_CHILD;
					subVicAssociationCode.setValue(subVicAssociationCodeSimple);
					break;
				case "Family Member_Spouse_Common Law":
					subVicAssociationCodeSimple = VictimToSubjectRelationshipCodeSimpleType.FAMILY_MEMBER_SPOUSE_COMMON_LAW;
					subVicAssociationCode.setValue(subVicAssociationCodeSimple);
					break;
				case "Employee":
					subVicAssociationCodeSimple = VictimToSubjectRelationshipCodeSimpleType.EMPLOYEE;
					subVicAssociationCode.setValue(subVicAssociationCodeSimple);
					break;
				case "Employer":
					subVicAssociationCodeSimple = VictimToSubjectRelationshipCodeSimpleType.EMPLOYER;
					subVicAssociationCode.setValue(subVicAssociationCodeSimple);
					break;
				case "Friend":
					subVicAssociationCodeSimple = VictimToSubjectRelationshipCodeSimpleType.FRIEND;
					subVicAssociationCode.setValue(subVicAssociationCodeSimple);
					break;
				case "Family Member_Grandchild":
					subVicAssociationCodeSimple = VictimToSubjectRelationshipCodeSimpleType.FAMILY_MEMBER_GRANDCHILD;
					subVicAssociationCode.setValue(subVicAssociationCodeSimple);
					break;
				case "Family Member_Grandparent":
					subVicAssociationCodeSimple = VictimToSubjectRelationshipCodeSimpleType.FAMILY_MEMBER_GRANDPARENT;
					subVicAssociationCode.setValue(subVicAssociationCodeSimple);
					break;
				case "Family Member_In-Law":
					subVicAssociationCodeSimple = VictimToSubjectRelationshipCodeSimpleType.FAMILY_MEMBER_IN_LAW;
					subVicAssociationCode.setValue(subVicAssociationCodeSimple);
					break;
				case "Neighbor":
					subVicAssociationCodeSimple = VictimToSubjectRelationshipCodeSimpleType.NEIGHBOR;
					subVicAssociationCode.setValue(subVicAssociationCodeSimple);
					break;
				case "Family Member":
					subVicAssociationCodeSimple = VictimToSubjectRelationshipCodeSimpleType.FAMILY_MEMBER;
					subVicAssociationCode.setValue(subVicAssociationCodeSimple);
					break;
				case "NonFamily_Otherwise Known":
					subVicAssociationCodeSimple = VictimToSubjectRelationshipCodeSimpleType.NON_FAMILY_OTHERWISE_KNOWN;
					subVicAssociationCode.setValue(subVicAssociationCodeSimple);
					break;
				case "Family Member_Parent":
					subVicAssociationCodeSimple = VictimToSubjectRelationshipCodeSimpleType.FAMILY_MEMBER_PARENT;
					subVicAssociationCode.setValue(subVicAssociationCodeSimple);
					break;
				case "Relationship Unknown":
					subVicAssociationCodeSimple = VictimToSubjectRelationshipCodeSimpleType.RELATIONSHIP_UNKNOWN;
					subVicAssociationCode.setValue(subVicAssociationCodeSimple);
					break;
				case "Family Member_Sibling":
					subVicAssociationCodeSimple = VictimToSubjectRelationshipCodeSimpleType.FAMILY_MEMBER_SIBLING;
					subVicAssociationCode.setValue(subVicAssociationCodeSimple);
					break;
				case "Family Member_Stepchild":
					subVicAssociationCodeSimple = VictimToSubjectRelationshipCodeSimpleType.FAMILY_MEMBER_STEPCHILD;
					subVicAssociationCode.setValue(subVicAssociationCodeSimple);
					break;
				case "Family Member_Spouse":
					subVicAssociationCodeSimple = VictimToSubjectRelationshipCodeSimpleType.FAMILY_MEMBER_SPOUSE;
					subVicAssociationCode.setValue(subVicAssociationCodeSimple);
					break;
				case "Family Member_Stepparent":
					subVicAssociationCodeSimple = VictimToSubjectRelationshipCodeSimpleType.FAMILY_MEMBER_STEPPARENT;
					subVicAssociationCode.setValue(subVicAssociationCodeSimple);
					break;
				case "Family Member_Stepsibling":
					subVicAssociationCodeSimple = VictimToSubjectRelationshipCodeSimpleType.FAMILY_MEMBER_STEPSIBLING;
					subVicAssociationCode.setValue(subVicAssociationCodeSimple);
					break;
				case "Stranger":
					subVicAssociationCodeSimple = VictimToSubjectRelationshipCodeSimpleType.STRANGER;
					subVicAssociationCode.setValue(subVicAssociationCodeSimple);
					break;
				case "Victim Was Offender":
					subVicAssociationCodeSimple = VictimToSubjectRelationshipCodeSimpleType.VICTIM_WAS_OFFENDER;
					subVicAssociationCode.setValue(subVicAssociationCodeSimple);
					break;
				case "Ex_Spouse":
					subVicAssociationCodeSimple = VictimToSubjectRelationshipCodeSimpleType.EX_SPOUSE;
					subVicAssociationCode.setValue(subVicAssociationCodeSimple);
					break;
				}
				JAXBElement<VictimToSubjectRelationshipCodeType> subVicAssociationElement = nibrs4ObjectFactory.createVictimToSubjectRelationshipCode(subVicAssociationCode);
				subjectVictimAssociation.getVictimToSubjectRelationship().add(subVicAssociationElement);
			}

			private SubjectVictimAssociationType createSubjVicAssoc(String[] subjVicArray) {
				setSubject(subjVicArray[0]);
				setVictim(subjVicArray[1]);
				setSubjVicRelationship(subjVicArray[2]);
				return subjectVictimAssociation;
			}
		}; // SubjectVictimAssociationClass
	};// report class
};// submission
