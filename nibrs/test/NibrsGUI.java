package nibrs.test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.*;
import javax.xml.bind.JAXBException;

import java.awt.GridLayout;
import java.awt.event.*;


public class NibrsGUI extends NibrsSubmission {

	private int pos = 30;
	private int numOpenFrames = 0;
	private int arrestCnt = 0;
	private int subjectCnt = 0;
	private int offenseCnt = 0;
	private int locationCnt = 0;
	private int victimCnt = 0;
	private int personCnt = 0;

	private boolean arrestAdded = false;
	private boolean subjectAdded = false;
	private boolean offenseAdded = false;
	private boolean locationAdded = false;
	private boolean itemAdded = false;
	private boolean substanceAdded = false;
	private boolean victimAdded = false;
	private boolean personAdded = false;
	private boolean enfOffAdded = false;
	private boolean arresteeAdded = false;
	private boolean actSubjAdded = false;
	private boolean offLocAdded = false;
	private boolean offVicAdded = false;
	private boolean subjVicAdded = false;
	private boolean burglary = false;
	private boolean hotel = false;
	private boolean groupB = false;

	private String workingdir = System.getProperty("user.dir");
	private String testORI = "TSADMIN01";

	private File workingFileDir = new File(workingdir);

	private ArrayList<String[]> offenseList, locList, itemList, substanceList, personList;
	private ArrayList<String[]> enfOffList, vicList, subjectList, arresteeList, arrestList;
	private ArrayList<String[]> arrestSubjectList, offenseLocationList, offenseVictimList, subjectVictimList;

	private String[] mmdArray = { "", "", "", "", "", "", "", "", "" };
	private String[] rhArray = { "", "", "", "", "", "" };
	private String[] incArray = { "", "", "", "", "", "", "", "", "", "", "", "", "" };

	private JFrame initFrame;
	private JInternalFrame mmdFrame, rhFrame, incFrame, vicFrame, itemFrame, subsFrame, fileCreatedFrame;

	private JDesktopPane dtPane;

	static NibrsGUI gui = new NibrsGUI();
	static MmdGUI mmd = gui.new MmdGUI();
	static RepHdrGUI repHdr = gui.new RepHdrGUI();
	static IncidentGUI incident = gui.new IncidentGUI();;
	static OffenseGUI offense = gui.new OffenseGUI();
	static LocGUI location = gui.new LocGUI();
	static ItemGUI item = gui.new ItemGUI();
	static SubstanceGUI subs = gui.new SubstanceGUI();
	static PersonGUI person = gui.new PersonGUI();
	static EnfOffGUI enfOff = gui.new EnfOffGUI();;
	static VictimGUI vic = gui.new VictimGUI();;
	static SubjectGUI subj = gui.new SubjectGUI();;
	static ArresteeGUI arrestee = gui.new ArresteeGUI();;
	static ArrestGUI arrest = gui.new ArrestGUI();;
	static ArrestSubjectGUI arrestSubject = gui.new ArrestSubjectGUI();;
	static OffenseLocationGUI offenseLocation = gui.new OffenseLocationGUI();;
	static OffenseVictimGUI offenseVictim = gui.new OffenseVictimGUI();;
	static SubjectVictimGUI subjectVictim = gui.new SubjectVictimGUI();;

	static StartFrame sf = gui.new StartFrame();

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		sf.createFrame();
	}

	private class StartFrame {

		private void createFrame() {
			offenseList = new ArrayList<String[]>();
			locList = new ArrayList<String[]>();
			itemList = new ArrayList<String[]>();
			substanceList = new ArrayList<String[]>();
			personList = new ArrayList<String[]>();
			enfOffList = new ArrayList<String[]>();
			vicList = new ArrayList<String[]>();
			subjectList = new ArrayList<String[]>();
			arresteeList = new ArrayList<String[]>();
			arrestList = new ArrayList<String[]>();
			arrestSubjectList = new ArrayList<String[]>();
			offenseLocationList = new ArrayList<String[]>();
			offenseVictimList = new ArrayList<String[]>();
			subjectVictimList = new ArrayList<String[]>();

			mmdFrame = null;
			rhFrame = null;
			incFrame = null;

			JMenuBar menuBar = new JMenuBar();

			JMenu createXML = new JMenu("Create NIBRS XML file");
			JMenu parseXML = new JMenu("Parse NIBRS XML File");

			JMenuItem segments = new JMenu("Segment Categories");
			JMenuItem chooseFileToParse = new JMenuItem("Select File to be Parsed");

			JMenuItem metadata = new JMenuItem("Message Metadata");
			JMenuItem reportHeader = new JMenuItem("Report Header");
			JMenuItem incident = new JMenuItem("Incident");
			JMenuItem offense = new JMenuItem("Offense");
			JMenuItem location = new JMenuItem("Location");
			JMenuItem item = new JMenuItem("Item");
			JMenuItem substance = new JMenuItem("Substance");
			JMenuItem person = new JMenuItem("Person");
			JMenuItem enforcementOfficial = new JMenuItem("Enforcement Official");
			JMenuItem victim = new JMenuItem("Victim");
			JMenuItem subject = new JMenuItem("Subject");
			JMenuItem arrestee = new JMenuItem("Arrestee");
			JMenuItem arrest = new JMenuItem("Arrest");
			JMenuItem arrestSubjectAssociation = new JMenuItem("Arrest Subject Association");
			JMenuItem offenseLocationAssociation = new JMenuItem("Offense Location Association");
			JMenuItem offenseVictimAssociation = new JMenuItem("Offense Victim Association");
			JMenuItem subjectVictimAssociation = new JMenuItem("Subject Victim Association");

			menuBar.add(createXML);
			menuBar.add(parseXML);

			parseXML.add(chooseFileToParse);
			createXML.add(segments);

			segments.add(metadata);
			segments.add(reportHeader);
			segments.add(incident);
			segments.add(offense);
			segments.add(location);
			segments.add(item);
			segments.add(substance);
			segments.add(person);
			segments.add(enforcementOfficial);
			segments.add(victim);
			segments.add(subject);
			segments.add(arrestee);
			segments.add(arrest);
			segments.add(arrestSubjectAssociation);
			segments.add(offenseLocationAssociation);
			segments.add(offenseVictimAssociation);
			segments.add(subjectVictimAssociation);

			dtPane = new JDesktopPane();

			initFrame = new JFrame("NIBRS");
			initFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			initFrame.setVisible(true);
			initFrame.setResizable(true);
			initFrame.setSize(1220, 800);
			initFrame.setLocation(100, 100);

			initFrame.setJMenuBar(menuBar);

			initFrame.setContentPane(dtPane);

			chooseFileToParse.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					openFileChooserDialog();
				}
			});

			metadata.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					mmdFrame.setVisible(true);
				}
			});
			reportHeader.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					repHdr.createFrame();
				}
			});
			incident.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					NibrsGUI.incident.createFrame();
				}
			});
			offense.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					NibrsGUI.offense.createFrame();
				}
			});
			location.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					NibrsGUI.location.createFrame();
				}
			});
			item.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					NibrsGUI.item.createFrame();
				}
			});
			substance.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					subs.createFrame();
				}
			});
			person.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					NibrsGUI.person.createFrame();
				}
			});
			enforcementOfficial.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					enfOff.createFrame();
				}
			});
			victim.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					vic.createFrame();
				}
			});
			subject.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					subj.createFrame();
				}
			});
			arrestee.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					NibrsGUI.arrestee.createFrame();
				}
			});
			arrest.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					NibrsGUI.arrest.createFrame();
				}
			});
			arrestSubjectAssociation.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					arrestSubject.createFrame();
				}
			});
			offenseLocationAssociation.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					offenseLocation.createFrame();
				}
			});
			offenseVictimAssociation.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					offenseVictim.createFrame();
				}
			});
			subjectVictimAssociation.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					subjectVictim.createFrame();
				}
			});

			mmd.createFrame();
		}
	};

	private class MmdGUI {

		JButton createMmdBtn;
		JLabel id, ver, ori, mdy, t;
		JPanel panel1, panel2, panel3, panel4;
		JTextField msgID, impVer, subORI, mmdMonth, mmdDay, mmdYear, mmdHour, mmdMin, mmdSec;
		//String[] mmdArray = { "", "", "", "", "", "", "", "", "" };

		private void createFrame() {

			mmdFrame = new JInternalFrame("Message Metadata");
			mmdFrame.setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
			mmdFrame.setBounds(pos * numOpenFrames, pos * numOpenFrames, 400, 250);
			mmdFrame.setVisible(false);

			createMmdBtn = new JButton("Continue");

			msgID = new JTextField(15);
			impVer = new JTextField(15);
			subORI = new JTextField(15);
			mmdMonth = new JTextField(2);
			mmdDay = new JTextField(2);
			mmdYear = new JTextField(3);
			mmdHour = new JTextField(2);
			mmdMin = new JTextField(2);
			mmdSec = new JTextField(2);

			id = new JLabel("Message ID:");
			ver = new JLabel("Implementation Version:");
			ori = new JLabel("Submitting Org ORI:(AA1234567)");
			mdy = new JLabel("mm/dd/yyy hh:mm:ss");
			t = new JLabel("T");

			panel1 = new JPanel();
			panel2 = new JPanel();
			panel3 = new JPanel();
			panel4 = new JPanel();

			id.setLabelFor(msgID);
			ver.setLabelFor(impVer);
			ori.setLabelFor(subORI);
			mdy.setLabelFor(mmdMonth);
			t.setLabelFor(mmdHour);

			panel1.add(id);
			panel1.add(msgID);

			panel2.add(ver);
			panel2.add(impVer);

			panel3.add(ori);
			panel3.add(subORI);

			panel4.add(mdy);
			panel4.add(mmdMonth);
			panel4.add(mmdDay);
			panel4.add(mmdYear);
			panel4.add(t);
			panel4.add(mmdHour);
			panel4.add(mmdMin);
			panel4.add(mmdSec);

			msgID.setText(
					timeNow("YYYY") + timeNow("MM") + timeNow("dd") + timeNow("H") + timeNow("mm") + timeNow("ss"));

			impVer.setText("4.2");

			subORI.setText(testORI);

			mmdYear.setText(timeNow("YYYY"));
			mmdMonth.setText(timeNow("MM"));
			mmdDay.setText(timeNow("dd"));
			mmdHour.setText(timeNow("H"));
			mmdMin.setText(timeNow("mm"));
			mmdSec.setText(timeNow("ss"));

//			mmdArray[0] = msgID.getText();
//			mmdArray[1] = impVer.getText();
//			mmdArray[2] = subORI.getText();
//			mmdArray[3] = mmdYear.getText();
//			mmdArray[4] = mmdMonth.getText();
//			mmdArray[5] = mmdDay.getText();
//			mmdArray[6] = mmdHour.getText();
//			mmdArray[7] = mmdMin.getText();
//			mmdArray[8] = mmdSec.getText();

			mmdFrame.getContentPane().add(panel1);
			mmdFrame.getContentPane().add(panel2);
			mmdFrame.getContentPane().add(panel3);
			mmdFrame.getContentPane().add(panel4);
			mmdFrame.getContentPane().add(createMmdBtn);
			mmdFrame.setLayout(new GridLayout(5, 1));

			dtPane.add(mmdFrame);

			createMmdBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					addDataToArrayList();
					removeButton();
				}
			});

			msgID.requestFocus();
			numOpenFrames++;
			if (numOpenFrames > 17)
				numOpenFrames = 0;
		}

		private void removeButton() {
			mmdFrame.getContentPane().remove(createMmdBtn);
		}

		private void addDataToArrayList() {
			mmdArray = new String[9];
			mmdArray[0] = msgID.getText();
			mmdArray[1] = impVer.getText();
			mmdArray[2] = subORI.getText();
			mmdArray[3] = mmdYear.getText();
			mmdArray[4] = mmdMonth.getText();
			mmdArray[5] = mmdDay.getText();
			mmdArray[6] = mmdHour.getText();
			mmdArray[7] = mmdMin.getText();
			mmdArray[8] = mmdSec.getText();
		}

	};

	private class RepHdrGUI {

		JButton createRepHdrBtn;
		JLabel repCatCodeLbl, repActCatLbl, repAgencyLbl, mdyLbl;
		JPanel panel1, panel2, panel3, panel4, panel5;
		JTextField repAgencyFld, repMonth, repYear;
		JComboBox<String> repCatCodeCB, repActCodeCB;

		String[] repCatCodes = { "Group A", "Group B", "Zero Report" };
		String[] repActCodes = { "Incident Report", "Replace", "Delete", "Add" };

		private void createFrame() {
			rhFrame = new JInternalFrame("Report Header");
			rhFrame.setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
			rhFrame.setBounds(pos * numOpenFrames, pos * numOpenFrames, 400, 250);
			rhFrame.setVisible(true);

			createRepHdrBtn = new JButton("Continue");
			createRepHdrBtn.setSize(50, 100);

			repCatCodeCB = new JComboBox<String>(repCatCodes);
			repActCodeCB = new JComboBox<String>(repActCodes);
			repAgencyFld = new JTextField(15);
			repMonth = new JTextField(2);
			repYear = new JTextField(3);

			repYear.setText(timeNow("YYYY"));
			repMonth.setText(timeNow("MM"));
			repAgencyFld.setText(testORI);

			repCatCodeLbl = new JLabel("Report Category Code:");
			repActCatLbl = new JLabel("Report Action Code:");
			repAgencyLbl = new JLabel("Reporting Agency ORI:(AA1234567)");
			mdyLbl = new JLabel("mm/dd/yyyy");

			panel1 = new JPanel();
			panel2 = new JPanel();
			panel3 = new JPanel();
			panel4 = new JPanel();
			panel5 = new JPanel();

			repCatCodeLbl.setLabelFor(repCatCodeCB);
			repActCatLbl.setLabelFor(repActCodeCB);
			repAgencyLbl.setLabelFor(repAgencyFld);
			mdyLbl.setLabelFor(repMonth);

			panel1.add(repCatCodeLbl);
			panel1.add(repCatCodeCB);

			panel2.add(repActCatLbl);
			panel2.add(repActCodeCB);

			panel3.add(repAgencyLbl);
			panel3.add(repAgencyFld);

			panel4.add(mdyLbl);
			panel4.add(repMonth);
			panel4.add(repYear);

			panel5.add(createRepHdrBtn);

			rhFrame.getContentPane().add(panel1);
			rhFrame.getContentPane().add(panel2);
			rhFrame.getContentPane().add(panel3);
			rhFrame.getContentPane().add(panel4);
			rhFrame.getContentPane().add(panel5);
			rhFrame.setLayout(new GridLayout(5, 1));

			dtPane.add(rhFrame);
			rhFrame.moveToFront();

			createRepHdrBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					rhArray = new String[5];
					rhArray[0] = (String) repCatCodeCB.getItemAt(repCatCodeCB.getSelectedIndex());
					rhArray[1] = (String) repActCodeCB.getItemAt(repActCodeCB.getSelectedIndex());
					rhArray[2] = repAgencyFld.getText();
					rhArray[3] = repYear.getText();
					rhArray[4] = repMonth.getText();

					if (rhArray[0] == "Group A") {
						groupB = false;
						incident.createFrame();
						offense.createFrame();
						location.createFrame();
						person.createFrame();
					} else if (rhArray[0] == "Group B") {
						groupB = true;
						NibrsGUI.arrest.createFrame();
						NibrsGUI.arrestee.createFrame();
						person.createFrame();
					}
				}

			});

			repCatCodeLbl.requestFocus();

			initFrame.setContentPane(dtPane);
			numOpenFrames++;
			if (numOpenFrames > 17)
				numOpenFrames = 0;
		}
	};

	private class IncidentGUI {

		JLabel incIDLbl, incDateLabel, excClrDateLbl, incExcClrCodeLbl, tLbl, cargoTheftLbl;
		JPanel panel1, panel2, panel3, panel4, panel5;
		JTextField incIdFld, incMonth, incDay, incYear, incHr, incMin, excClrMonth, excClrDay, excClrYear;
		JCheckBox repDateIndBOX;
		JComboBox<String> cargoTheftCB, incExcClrCodeCB;

		//String[] incArray = { "", "", "", "", "", "", "", "", "", "", "", "", "" };
		String[] excClrCodes = { "Not Applicable", "Death of Offender", "Prosecution Declined",
				"In Custody of Other Jurisdiction", "Victim Refused to Cooperate", "Juvenile/No Custody" };
		String[] cargoTheftValues = { "n/a", "TRUE", "FALSE" };

		private void createFrame() {
			incFrame = new JInternalFrame("Incident");
			incFrame.setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
			incFrame.setBounds(pos * numOpenFrames, pos * numOpenFrames, 600, 250);
			incFrame.setVisible(true);

			incExcClrCodeCB = new JComboBox<String>(excClrCodes);
			cargoTheftCB = new JComboBox<String>(cargoTheftValues);
			repDateIndBOX = new JCheckBox("Report Date Indicator");

			incIdFld = new JTextField(15);
			incMonth = new JTextField(2);
			incDay = new JTextField(2);
			incYear = new JTextField(3);
			incHr = new JTextField(2);
			incMin = new JTextField(2);
			excClrMonth = new JTextField(2);
			excClrDay = new JTextField(2);
			excClrYear = new JTextField(3);

			int month = Integer.parseInt(timeNow("MM")) - 1;
			int year = Integer.parseInt(timeNow("YYY"));
			if (month+1 ==1){
				month = 12;
				year = year -1;
			}
			incYear.setText(Integer.toString(year));
			incMonth.setText(Integer.toString(month));
			incDay.setText(timeNow("dd"));
			incHr.setText(timeNow("H"));
			incMin.setText(timeNow("mm"));
			incIdFld.setText(incYear.getText() + incMonth.getText() + incDay.getText() + incMin.getText() + timeNow("ss"));


			incIDLbl = new JLabel("Incident ID:");
			incDateLabel = new JLabel("Incident Date: mm/dd/yyyy hh:mm");
			excClrDateLbl = new JLabel("Clearance Date: mm/dd/yyyy");
			incExcClrCodeLbl = new JLabel("Clearance Code:");
			tLbl = new JLabel("T");
			cargoTheftLbl = new JLabel("Cargo Theft");

			panel1 = new JPanel();
			panel2 = new JPanel();
			panel3 = new JPanel();
			panel4 = new JPanel();
			panel5 = new JPanel();

			incIDLbl.setLabelFor(incIdFld);
			incDateLabel.setLabelFor(incMonth);
			excClrDateLbl.setLabelFor(excClrMonth);
			tLbl.setLabelFor(incHr);
			incExcClrCodeLbl.setLabelFor(incExcClrCodeCB);
			cargoTheftLbl.setLabelFor(cargoTheftCB);

			panel1.add(incIDLbl);
			panel1.add(incIdFld);

			panel2.add(incDateLabel);
			panel2.add(incMonth);
			panel2.add(incDay);
			panel2.add(incYear);
			panel2.add(tLbl);
			panel2.add(incHr);
			panel2.add(incMin);

			panel3.add(incExcClrCodeLbl);
			panel3.add(incExcClrCodeCB);

			panel3.add(excClrDateLbl);
			panel3.add(excClrMonth);
			panel3.add(excClrDay);
			panel3.add(excClrYear);

			panel4.add(repDateIndBOX);
			panel4.add(cargoTheftLbl);
			panel4.add(cargoTheftCB);
			cargoTheftEnabled(false);

			incFrame.getContentPane().add(panel1);
			incFrame.getContentPane().add(panel2);
			incFrame.getContentPane().add(panel3);
			incFrame.getContentPane().add(panel4);
			incFrame.getContentPane().add(panel5);
			incFrame.setLayout(new GridLayout(5, 1));

			dtPane.add(incFrame);
			incFrame.moveToFront();

			incIdFld.requestFocus();

			numOpenFrames++;
			if (numOpenFrames > 17)
				numOpenFrames = 0;
		}

		private void addDataToArrayList() {
			if (incIdFld != null)
			{
				//incArray = new String[13];
				incArray[0] = incIdFld.getText();
				incArray[1] = incYear.getText();
				incArray[2] = incMonth.getText();
				incArray[3] = incDay.getText();
				incArray[4] = incHr.getText();
				incArray[5] = incMin.getText();
				// incArray[6] = incSec.getText();
				incArray[7] = (String) incExcClrCodeCB.getItemAt(incExcClrCodeCB.getSelectedIndex());
				incArray[8] = excClrYear.getText();
				incArray[9] = excClrMonth.getText();
				incArray[10] = excClrDay.getText();

				if (repDateIndBOX.isSelected())
					incArray[11] = "true";
				else
					incArray[11] = "false";

				incArray[12] = cargoTheftCB.getItemAt(cargoTheftCB.getSelectedIndex());
			}
		}

		private void cargoTheftEnabled(Boolean arg) {
			if (arg) {
				String[] cargoTheftValues = { "TRUE", "FALSE" };
				cargoTheftCB = new JComboBox<String>(cargoTheftValues);
				cargoTheftLbl.setLabelFor(cargoTheftCB);

				panel4.invalidate();
				panel4.removeAll();
				panel4.add(repDateIndBOX);
				panel4.add(cargoTheftLbl);
				panel4.add(cargoTheftCB);
				cargoTheftLbl.setEnabled(true);
				cargoTheftCB.setEnabled(true);
				panel4.revalidate();
				panel4.repaint();
			} else {
				String[] cargoTheftValues = { "n/a", "TRUE", "FALSE" };
				cargoTheftCB = new JComboBox<String>(cargoTheftValues);
				cargoTheftLbl.setLabelFor(cargoTheftCB);

				panel4.invalidate();
				panel4.removeAll();
				panel4.add(repDateIndBOX);
				panel4.add(cargoTheftLbl);
				panel4.add(cargoTheftCB);
				cargoTheftLbl.setEnabled(false);
				cargoTheftCB.setEnabled(false);
				panel4.revalidate();
				panel4.repaint();

			}
		}
	};

	private class OffenseGUI {

		JButton saveBtn, deleteBtn, createXMLBtn, saveAndAddBtn;
		JLabel offIDLbl, structuresEnteredLbl, offenseCodeLbl, criminalActivityLbl;
		JLabel gangActivityLbl, offenseFactorLbl, passagePointLbl;
		JLabel forceCatCodeLbl1, forceCatCodeLbl2, forceCatCodeLbl3;
		JLabel biasMotivationLbl1, biasMotivationLbl2, biasMotivationLbl3, biasMotivationLbl4, biasMotivationLbl5;
		JPanel panel1, panel2, panel3, panel4, panel5, panel6, panel7, panel8, panel9;
		JTextField offIDFld, structuresEnteredFld;
		JCheckBox offenseAttemptedIndBOX;
		JComboBox<String> offenseCodeCB, criminalActivityCB;
		JComboBox<String> offenseFactorCB, passagePointCB;
		JComboBox<String> biasMotivationCB1, biasMotivationCB2, biasMotivationCB3, biasMotivationCB4, biasMotivationCB5;
		JComboBox<String> forceCatCodeCB1, forceCatCodeCB2, forceCatCodeCB3;
		String[] offArray = { "", "", "", "", "", "", "", "", "" };
		String[] offenseCodes = { "", "Murder & Nonnegligent Manslaughter", "Negligent Manslaughter",
				"Justifiable Homicide", "Kidnapping/Abduction", "Rape", "Sodomy", "Sexual Assault With An Object",
				"Fondling", "Robbery", "Aggravated Assault", "Simple Assault", "Intimidation", "Arson",
				"Extortion/Blackmail", "Burglary/Breaking & Entering", "Pocket-picking", "Purse-snatching",
				"Shoplifting", "Theft From Building", "Theft From Coin-Operated Machine", "Theft From Motor Vehicle",
				"Theft of Motor Vehicle Parts", "All Other Larceny", "Motor Vehicle Theft", "Counterfeiting/Forgery",
				"False Pretenses/Swindle/Confidence Game", "Credit Card/Automated Teller Machine Fraud",
				"Impersonation", "Welfare Fraud", "Wire Fraud", "Identity Theft", "Hacking/Computer Invasion",
				"Embezzlement", "Stolen Property Offenses", "Destruction/Damage/Vandalism of Property",
				"Drug/Narcotic Violations", "Drug Equipment Violations", "Incest", "Statutory Rape",
				"Pornography/Obscene Material", "Betting/Wagering", "Operating/Promoting/Assisting Gambling",
				"Gambling Equipment Violation", "Sports Tampering", "Prostitution",
				"Assisting or Promoting Prostitution", "Purchasing Prostitution", "Bribery", "Weapon Law Violations",
				"Human Trafficking, Commercial Sex Acts", "Human Trafficking, Involuntary Servitude", "Animal Cruelty" };
		String[] crimActCodes = { "n/a", "None/Unknown", "Simple/Gross Neglect", "Buying/Receiving",
				"Cultivating/Manufacturing/Publishing", "Distributing/Selling", "Exploiting Children",
				"Organized Abuse", "Other Gang", "Intentional Abuse and Torture", "Juvenile Gang",
				"Operating/Promoting/Assisting", "Possessing/Concealing", "Animal Sexual Abuse",
				"Transporting/Transmitting/Importing", "Using/Consuming" };
		String[] biasMotCodes = { "", "NONE", "UNKNOWN", "ANTIWHITE", "ANTIBLACK_AFRICAN AMERICAN",
				"ANTIAMERICAN INDIAN_ ALASKAN NATIVE", "ANTIASIAN", "ANTIMULTIRACIAL GROUP",
				"ANTINATIVEHAWAIIAN_OTHERPACIFICISLANDER", "ANTIJEWISH", "ANTICATHOLIC", "ANTIPROTESTANT",
				"ANTIISLAMIC", "ANTIOTHER RELIGION", "ANTIMULTIRELIGIOUS GROUP", "ANTIATHEIST_AGNOSTIC", "ANTIMORMON",
				"ANTIJEHOVAHWITNESS", "ANTIARAB", "ANTIHISPANIC_LATINO", "ANTIOTHER ETHNICITY_NATIONAL ORIGIN",
				"ANTIMALE HOMOSEXUAL", "ANTIFEMALE HOMOSEXUAL", "ANTIHETEROSEXUAL", "ANTIBISEXUAL",
				"ANTIPHYSICAL DISABILITY", "ANTIMENTAL DISABILITY", "ANTIMALE", "ANTIFEMALE", "ANTITRANSGENDER",
				"ANTIGENDER_NONCONFORMING", "ANTIEASTERNORTHODOX", "ANTIOTHER CHRISTIAN", "ANTIBUDDHIST", "ANTIHINDU",
				"ANTISIKH", };
		String[] offFactCodes = { "Not Applicable", "Alcohol", "Computer Equipment", "Drugs or Narcotics" };
		String[] passPntCodes = { "n/a", "Force", "No Force" };
		String[] forceFactCodes = { "", "None", "Unknown", "Firearm (type not stated)",
				"Automatic Firearm (type not stated)", "Handgun", "Automatic Handgun", "Rifle", "Automatic Rifle",
				"Shotgun", "Automatic Shotgun", "Other Firearm", "Other Automatic Firearm", "Lethal Cutting Instument",
				"Club/Blackjack/Brass Knuckles", "Motor Vehicle", "Personal Weapons", "Poison", "Explosives",
				"Fire/Incendiary Device", "Drugs/Narcotics/Sleeping Pills", "Asphyxiation", "Other" };

		private void createFrame() {
			final JInternalFrame offFrame = new JInternalFrame("Offense");
			offFrame.setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
			offFrame.setBounds(pos * numOpenFrames, pos * numOpenFrames, 800, 450);
			offFrame.setVisible(true);

			saveAndAddBtn = new JButton("Save and Add");
			saveBtn = new JButton("Save");
			deleteBtn = new JButton("Delete");
			createXMLBtn = new JButton("Create XML");

			offenseCodeCB = new JComboBox<String>(offenseCodes);
			criminalActivityCB = new JComboBox<String>(crimActCodes);
			biasMotivationCB1 = new JComboBox<String>(biasMotCodes);
			biasMotivationCB2 = new JComboBox<String>(biasMotCodes);
			biasMotivationCB3 = new JComboBox<String>(biasMotCodes);
			biasMotivationCB4 = new JComboBox<String>(biasMotCodes);
			biasMotivationCB5 = new JComboBox<String>(biasMotCodes);
			offenseFactorCB = new JComboBox<String>(offFactCodes);
			passagePointCB = new JComboBox<String>(passPntCodes);
			forceCatCodeCB1 = new JComboBox<String>(forceFactCodes);
			forceCatCodeCB2 = new JComboBox<String>(forceFactCodes);
			forceCatCodeCB3 = new JComboBox<String>(forceFactCodes);
			offenseAttemptedIndBOX = new JCheckBox("Offense Attempted Indicator");

			final JTextField finalOffIDFld = new JTextField(15);
			structuresEnteredFld = new JTextField(2);

			offIDLbl = new JLabel("Offense ID:");
			structuresEnteredLbl = new JLabel("# Structures Entered");
			offenseCodeLbl = new JLabel("UCR Offense Code");
			criminalActivityLbl = new JLabel("Criminal Activity");
			gangActivityLbl = new JLabel("Gang Activity");
			biasMotivationLbl1 = new JLabel("Bias Motivation #1");
			biasMotivationLbl2 = new JLabel("Bias Motivation #2");
			biasMotivationLbl3 = new JLabel("Bias Motivation #3");
			biasMotivationLbl4 = new JLabel("Bias Motivation #4");
			biasMotivationLbl5 = new JLabel("Bias Motivation #5");
			offenseFactorLbl = new JLabel("Offense Factor");
			passagePointLbl = new JLabel("Forced Entry");
			forceCatCodeLbl1 = new JLabel("Force Category #1");
			forceCatCodeLbl2 = new JLabel("Force Category #2");
			forceCatCodeLbl3 = new JLabel("Force Category #3");

			panel1 = new JPanel();
			panel2 = new JPanel();
			panel3 = new JPanel();
			panel4 = new JPanel();
			panel5 = new JPanel();
			panel6 = new JPanel();
			panel7 = new JPanel();
			panel8 = new JPanel();
			panel9 = new JPanel();

			offIDLbl.setLabelFor(offIDFld);
			offenseCodeLbl.setLabelFor(offenseCodeCB);
			criminalActivityLbl.setLabelFor(criminalActivityCB);
			biasMotivationLbl1.setLabelFor(biasMotivationCB1);
			biasMotivationLbl2.setLabelFor(biasMotivationCB2);
			biasMotivationLbl3.setLabelFor(biasMotivationCB3);
			biasMotivationLbl4.setLabelFor(biasMotivationCB4);
			biasMotivationLbl5.setLabelFor(biasMotivationCB5);
			structuresEnteredLbl.setLabelFor(structuresEnteredFld);
			offenseFactorLbl.setLabelFor(offenseFactorCB);
			passagePointLbl.setLabelFor(passagePointCB);
			forceCatCodeLbl1.setLabelFor(forceCatCodeCB1);
			forceCatCodeLbl2.setLabelFor(forceCatCodeCB2);
			forceCatCodeLbl3.setLabelFor(forceCatCodeCB3);

			panel1.add(offIDLbl);
			panel1.add(finalOffIDFld);
			panel1.add(offenseCodeLbl);
			panel1.add(offenseCodeCB);

			panel2.add(criminalActivityLbl);
			panel2.add(criminalActivityCB);
			criminalActivityLbl.setEnabled(false);
			criminalActivityCB.setEnabled(false);
			panel2.add(offenseAttemptedIndBOX);

			panel3.add(structuresEnteredLbl);
			panel3.add(structuresEnteredFld);
			structuresEnteredLbl.setEnabled(false);
			structuresEnteredFld.setEnabled(false);
			panel3.add(passagePointLbl);
			panel3.add(passagePointCB);
			passagePointLbl.setEnabled(false);
			passagePointCB.setEnabled(false);
			panel3.add(offenseFactorLbl);
			panel3.add(offenseFactorCB);
			passagePointEnabled(false);

			panel4.add(forceCatCodeLbl1);
			panel4.add(forceCatCodeCB1);
			forceCatCodeLbl1.setEnabled(false);
			forceCatCodeCB1.setEnabled(false);
			panel4.add(biasMotivationLbl1);
			panel4.add(biasMotivationCB1);
	
			panel5.add(forceCatCodeLbl2);
			panel5.add(forceCatCodeCB2);
			forceCatCodeLbl2.setEnabled(false);
			forceCatCodeCB2.setEnabled(false);
			panel5.add(biasMotivationLbl2);
			panel5.add(biasMotivationCB2);

			panel6.add(forceCatCodeLbl3);
			panel6.add(forceCatCodeCB3);
			forceCatCodeLbl3.setEnabled(false);
			forceCatCodeCB3.setEnabled(false);
			forceCategoryEnabled(false);
			panel6.add(biasMotivationLbl3);
			panel6.add(biasMotivationCB3);

			panel7.add(biasMotivationLbl4);
			panel7.add(biasMotivationCB4);

			panel8.add(biasMotivationLbl5);
			panel8.add(biasMotivationCB5);

			panel9.add(createXMLBtn);
			panel9.add(deleteBtn);
			panel9.add(saveBtn);
			panel9.add(saveAndAddBtn);

			offFrame.getContentPane().add(panel1);
			offFrame.getContentPane().add(panel2);
			offFrame.getContentPane().add(panel3);
			offFrame.getContentPane().add(panel4);
			offFrame.getContentPane().add(panel5);
			offFrame.getContentPane().add(panel6);
			offFrame.getContentPane().add(panel7);
			offFrame.getContentPane().add(panel8);
			offFrame.getContentPane().add(panel9);
			offFrame.setLayout(new GridLayout(9, 1));

			dtPane.add(offFrame);
			offFrame.moveToFront();

			// createOffenseBtn.addActionListener(new OffenseButtonListener());
			createXMLBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					fileCreateGUI();
				}
			});
			deleteBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					//offFrame.setVisible(false);
					//offFrame.dispose();
					//deleteDataFromArrayList(finalOffIDFld);
					offFrame.setVisible(false);
					deleteDataFromArrayList(finalOffIDFld);
					initFrame.remove(offFrame);
					finalOffIDFld.setText(null);
				}
			});
			saveAndAddBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					addDataToArrayList(finalOffIDFld);
					offense.createFrame();
				}

			});
			saveBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					addDataToArrayList(finalOffIDFld);
					if (vicFrame != null)
						vic.updateRoles();
					offenseAdded = true;
				}

			});
			offenseCodeCB.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					switch (offenseCodeCB.getItemAt(offenseCodeCB.getSelectedIndex())) {
					/*
					 * Assault Codes
					 */
					case "Aggravated Assault":
						offenseAttemptedIndBOX.invalidate();
						offenseAttemptedIndBOX.setSelected(false);
						offenseAttemptedIndBOX.setEnabled(false);
						offenseAttemptedIndBOX.revalidate();
						offenseAttemptedIndBOX.repaint();
						if (vicFrame != null) {
							vic.enableInjury(true);
							vic.enableAggAssault(true);
							vicFrame.moveToFront();
						} else {
							vic.createFrame();
							vic.enableInjury(true);
							vic.enableAggAssault(true);
						}
						gangActivityEnabled(true);
						forceCategoryEnabled(true);
						break;
					case "Simple Assault":
						offenseAttemptedIndBOX.invalidate();
						offenseAttemptedIndBOX.setSelected(false);
						offenseAttemptedIndBOX.setEnabled(false);
						offenseAttemptedIndBOX.revalidate();
						offenseAttemptedIndBOX.repaint();
						if (vicFrame != null) {
							vic.enableInjury(true);
							vicFrame.moveToFront();
						} else {
							vic.createFrame();
							vic.enableInjury(true);
						}
						gangActivityEnabled(true);
						forceCategoryEnabled(true);
						break;
					/*
					 * Counterfeiting/Forgery
					 */
					case "Counterfeiting/Forgery":
						String[] crimActCodesForgery = { "Buying/Receiving", "Cultivating/Manufacturing/Publishing",
								"Distributing/Selling", "Operating/Promoting/Assisting", "Possessing/Concealing",
								"Transporting/Transmitting/Importing" };
						criminalActivityCB.invalidate();
						panel2.remove(criminalActivityLbl);
						panel2.remove(criminalActivityCB);
						criminalActivityCB = new JComboBox<String>(crimActCodesForgery);
						criminalActivityLbl.setLabelFor(criminalActivityCB);
						panel2.add(criminalActivityLbl);
						panel2.add(criminalActivityCB);
						panel2.add(offenseAttemptedIndBOX);
						criminalActivityLbl.setEnabled(true);
						criminalActivityCB.setEnabled(true);
						criminalActivityCB.revalidate();
						panel2.repaint();
						if (vicFrame != null) {
							vic.enableAggAssault(false);
							vic.enableInjury(false);
							vic.enableJustHom(false);
						}
						forceCategoryEnabled(false);
						break;
					/*
					 * Drug Violations
					 */
					case "Drug/Narcotic Violations":
						String[] crimActCodesDrugs = { "Buying/Receiving", "Cultivating/Manufacturing/Publishing",
								"Distributing/Selling", "Possessing/Concealing", "Transporting/Transmitting/Importing",
								"Using/Consuming" };
						criminalActivityCB.invalidate();
						panel2.remove(criminalActivityLbl);
						panel2.remove(criminalActivityCB);
						criminalActivityCB = new JComboBox<String>(crimActCodesDrugs);
						criminalActivityLbl.setLabelFor(criminalActivityCB);
						panel2.add(criminalActivityLbl);
						panel2.add(criminalActivityCB);
						panel2.add(offenseAttemptedIndBOX);
						criminalActivityLbl.setEnabled(true);
						criminalActivityCB.setEnabled(true);
						criminalActivityCB.revalidate();
						panel2.repaint();
						if (itemFrame != null) {
							item.enableValue(false);
							itemFrame.moveToFront();
						} else {
							item.createFrame();
							item.enableValue(false);
						}
						if (vicFrame != null) {
							vic.enableAggAssault(false);
							vic.enableInjury(false);
							vic.enableJustHom(false);
						}
						if (subsFrame != null ){
							subsFrame.moveToFront();
						}else{
							subs.createFrame();
						}
						
						forceCategoryEnabled(false);
						break;

					case "Drug Equipment Violations":
						String[] crimActCodesDrugEquip = { "Buying/Receiving", "Cultivating/Manufacturing/Publishing",
								"Distributing/Selling", "Possessing/Concealing", "Transporting/Transmitting/Importing",
								"Using/Consuming" };
						criminalActivityCB.invalidate();
						panel2.remove(criminalActivityLbl);
						panel2.remove(criminalActivityCB);
						criminalActivityCB = new JComboBox<String>(crimActCodesDrugEquip);
						criminalActivityLbl.setLabelFor(criminalActivityCB);
						panel2.add(criminalActivityLbl);
						panel2.add(criminalActivityCB);
						panel2.add(offenseAttemptedIndBOX);
						criminalActivityLbl.setEnabled(true);
						criminalActivityCB.setEnabled(true);
						criminalActivityCB.revalidate();
						panel2.repaint();
						if (itemFrame != null) {
							item.enableValue(false);
							itemFrame.moveToFront();
						} else {
							item.createFrame();
							item.enableValue(false);
						}
						if (vicFrame != null) {
							vic.enableAggAssault(false);
							vic.enableInjury(false);
							vic.enableJustHom(false);
						}
						forceCategoryEnabled(false);
						break;
					/*
					 * Embezzlement
					 */
					case "Embezzlement":
						if (vicFrame != null) {
							vic.embezzlementVictimCodes();
							vicFrame.moveToFront();
						} else {
							vic.createFrame();
							vic.embezzlementVictimCodes();
						}
						/// Cargo Theft
						if (incFrame != null) {
							incident.cargoTheftEnabled(true);
							incFrame.moveToFront();
						} else {
							incident.createFrame();
							incident.cargoTheftEnabled(true);
							incFrame.moveToFront();
						}
						if (vicFrame != null) {
							vic.enableAggAssault(false);
							vic.enableInjury(false);
							vic.enableJustHom(false);
						}
						forceCategoryEnabled(false);
						break;
					/*
					 * Gambling Equipment
					 */
					case "Gambling Equipment Violation":
						String[] crimActCodesGambling = { "Buying/Receiving", "Cultivating/Manufacturing/Publishing",
								"Distributing/Selling", "Possessing/Concealing",
								"Transporting/Transmitting/Importing" };
						criminalActivityCB.invalidate();
						panel2.remove(criminalActivityLbl);
						panel2.remove(criminalActivityCB);
						criminalActivityCB = new JComboBox<String>(crimActCodesGambling);
						criminalActivityLbl.setLabelFor(criminalActivityCB);
						panel2.add(criminalActivityLbl);
						panel2.add(criminalActivityCB);
						panel2.add(offenseAttemptedIndBOX);
						criminalActivityLbl.setEnabled(true);
						criminalActivityCB.setEnabled(true);
						criminalActivityCB.revalidate();
						panel2.repaint();
						if (vicFrame != null) {
							vic.enableAggAssault(false);
							vic.enableInjury(false);
							vic.enableJustHom(false);
						}
						forceCategoryEnabled(false);
						break;
					/*
					 * Murder, Negligent Manslaughter, Justifiable Homicide
					 */
					case "Murder & Nonnegligent Manslaughter":
						if (vicFrame != null) {
							vic.enableInjury(false);
							vic.enableAggAssault(true);
							vic.murderVictimCodes();
							vic.murderAggAssaultCodes();
							vicFrame.moveToFront();
						} else {
							vic.createFrame();
							vic.enableInjury(false);
							vic.enableAggAssault(true);
							vic.murderVictimCodes();
							vic.murderAggAssaultCodes();
						}
						gangActivityEnabled(true);
						forceCategoryEnabled(true);
						break;
					case "Negligent Manslaughter":
						if (vicFrame != null) {
							vic.enableInjury(false);
							vic.enableAggAssault(true);
							vic.murderVictimCodes();
							vic.negligentManslaughterCodes();
							vicFrame.moveToFront();
						} else {
							vic.createFrame();
							vic.enableInjury(false);
							vic.enableAggAssault(true);
							vic.murderVictimCodes();
							vic.negligentManslaughterCodes();
						}
						gangActivityEnabled(true);
						forceCategoryEnabled(true);
						break;
					case "Justifiable Homicide":
						if (vicFrame != null) {
							vic.enableAggAssault(false);
							vic.enableInjury(false);
							vic.justHomVictimCodes();
							vic.justHomCodes();
							vic.enableJustHom(true);
							vicFrame.moveToFront();
						} else {
							vic.createFrame();
							vic.enableAggAssault(false);
							vic.enableInjury(false);
							vic.justHomVictimCodes();
							vic.justHomCodes();
							vic.enableJustHom(true);
						}
						forceCategoryEnabled(true);
						break;
					/*
					 * Larceny/Theft Offenses
					 */
					case "Pocket-picking":
					case "Purse-snatching":
					case "Shoplifting":
					case "Theft From Coin-Operated Machine":
					case "Theft of Motor Vehicle Parts":
						if (itemFrame != null)
							itemFrame.moveToFront();
						else
							item.createFrame();
						forceCategoryEnabled(false);
						break;
					case "Theft From Building":
					case "Theft From Motor Vehicle":
					case "All Other Larceny":
						if (itemFrame != null)
							itemFrame.moveToFront();
						else
							item.createFrame();
						/// Cargo Theft
						if (incFrame != null) {
							incident.cargoTheftEnabled(true);
							incFrame.moveToFront();
						} else {
							incident.createFrame();
							incident.cargoTheftEnabled(true);
							incFrame.moveToFront();
						}
						if (vicFrame != null) {
							vic.enableAggAssault(false);
							vic.enableInjury(false);
							vic.enableJustHom(false);
						}
						forceCategoryEnabled(false);
						break;

					/*
					 * Motor Vehicle Theft
					 */
					case "Motor Vehicle Theft":
						if (itemFrame != null) {
							itemFrame.moveToFront();
							item.motorVehicleCodes();
						} else {
							item.createFrame();
							item.motorVehicleCodes();
						}
						/// Cargo Theft
						if (incFrame != null) {
							incident.cargoTheftEnabled(true);
							incFrame.moveToFront();
						} else {
							incident.createFrame();
							incident.cargoTheftEnabled(true);
							incFrame.moveToFront();
						}
						if (vicFrame != null) {
							vic.enableAggAssault(false);
							vic.enableInjury(false);
							vic.enableJustHom(false);
						}
						forceCategoryEnabled(false);
						break;
					/*
					 * Pornography
					 */
					case "Pornography/Obscene Material":
						String[] crimActCodesPorn = { "Buying/Receiving", "Cultivating/Manufacturing/Publishing",
								"Distributing/Selling", "Possessing/Concealing" };
						criminalActivityCB.invalidate();
						panel2.remove(criminalActivityLbl);
						panel2.remove(criminalActivityCB);
						criminalActivityCB = new JComboBox<String>(crimActCodesPorn);
						criminalActivityLbl.setLabelFor(criminalActivityCB);
						panel2.add(criminalActivityLbl);
						panel2.add(criminalActivityCB);
						panel2.add(offenseAttemptedIndBOX);
						criminalActivityLbl.setEnabled(true);
						criminalActivityCB.setEnabled(true);
						criminalActivityCB.revalidate();
						panel2.repaint();
						if (vicFrame != null) {
							vic.enableAggAssault(false);
							vic.enableInjury(false);
							vic.enableJustHom(false);
						}
						forceCategoryEnabled(false);
						break;
					/*
					 * Robbery
					 */
					case "Robbery":
						if (vicFrame != null) {
							vic.enableInjury(true);
							vicFrame.moveToFront();
						} else {
							vic.createFrame();
							vic.enableInjury(true);
						}
						/// Cargo Theft
						if (incFrame != null) {
							incident.cargoTheftEnabled(true);
							incFrame.moveToFront();
						} else {
							incident.createFrame();
							incident.cargoTheftEnabled(true);
							incFrame.moveToFront();
						}
						if (vicFrame != null) {
							vic.enableAggAssault(false);
							vic.enableInjury(false);
							vic.enableJustHom(false);
						}
						gangActivityEnabled(true);
						forceCategoryEnabled(true);
						break;
					/*
					 * Sex Offenses
					 */
					case "Rape":
					case "Sodomy":
					case "Sexual Assault With An Object":
					case "Fondling":
						if (vicFrame != null) {
							vic.enableInjury(true);
							vicFrame.moveToFront();
						} else {
							vic.createFrame();
							vic.enableInjury(true);
						}
						gangActivityEnabled(true);
						forceCategoryEnabled(true);
						break;
					/*
					 * Sex Offenses, Non-Forcible
					 */
					case "Incest":
					case "Statutory Rape":
						if (vicFrame != null) {
							vicFrame.moveToFront();
						} else {
							vic.createFrame();
						}
						forceCategoryEnabled(false);
						break;
					/*
					 * Stolen Property Offenses
					 */
					case "Stolen Property Offenses":
						String[] crimActCodesStolenProperty = { "Buying/Receiving", "Distributing/Selling",
								"Possessing/Concealing", "Transporting/Transmitting/Importing" };
						criminalActivityCB.invalidate();
						panel2.remove(criminalActivityLbl);
						panel2.remove(criminalActivityCB);
						criminalActivityCB = new JComboBox<String>(crimActCodesStolenProperty);
						criminalActivityLbl.setLabelFor(criminalActivityCB);
						panel2.add(criminalActivityLbl);
						panel2.add(criminalActivityCB);
						panel2.add(offenseAttemptedIndBOX);
						criminalActivityLbl.setEnabled(true);
						criminalActivityCB.setEnabled(true);
						criminalActivityCB.revalidate();
						panel2.repaint();
						if (vicFrame != null) {
							vic.enableAggAssault(false);
							vic.enableInjury(false);
							vic.enableJustHom(false);
						}
						forceCategoryEnabled(false);
						break;
					/*
					 * Weapon Law Violations
					 */
					case "Weapon Law Violations":
						String[] crimActCodesWeaponLaw = { "Buying/Receiving", "Cultivating/Manufacturing/Publishing",
								"Distributing/Selling", "Possessing/Concealing", "Transporting/Transmitting/Importing",
								"Using/Consuming" };
						criminalActivityCB.invalidate();
						panel2.remove(criminalActivityLbl);
						panel2.remove(criminalActivityCB);
						criminalActivityCB = new JComboBox<String>(crimActCodesWeaponLaw);
						criminalActivityLbl.setLabelFor(criminalActivityCB);
						panel2.add(criminalActivityLbl);
						panel2.add(criminalActivityCB);
						panel2.add(offenseAttemptedIndBOX);
						criminalActivityLbl.setEnabled(true);
						criminalActivityCB.setEnabled(true);
						criminalActivityCB.revalidate();
						panel2.repaint();
						if (vicFrame != null) {
							vic.enableAggAssault(false);
							vic.enableInjury(false);
							vic.enableJustHom(false);
						}
						forceCategoryEnabled(true);
						break;
					/*
					 * Other Cargo Theft Offenses
					 */
					case "Extortion/Blackmail":
						/// Cargo Theft
						if (incFrame != null) {
							incident.cargoTheftEnabled(true);
							incFrame.moveToFront();
						} else {
							incident.createFrame();
							incident.cargoTheftEnabled(true);
							incFrame.moveToFront();
						}
						if (vicFrame != null) {
							vic.enableAggAssault(false);
							vic.enableInjury(true);
							vic.enableJustHom(false);
						}
						forceCategoryEnabled(true);
						break;

					case "False Pretenses/Swindle/Confidence Game":
					case "Credit Card/Automated Teller Machine Fraud":
					case "Impersonation":
					case "Wire Fraud":
					case "Bribery":
						/// Cargo Theft
						if (incFrame != null) {
							incident.cargoTheftEnabled(true);
							incFrame.moveToFront();
						} else {
							incident.createFrame();
							incident.cargoTheftEnabled(true);
							incFrame.moveToFront();
						}
						if (vicFrame != null) {
							vic.enableAggAssault(false);
							vic.enableInjury(false);
							vic.enableJustHom(false);
						}
						forceCategoryEnabled(false);
						break;
					case "Burglary/Breaking & Entering":
						/// Cargo Theft
						burglary = true;
						passagePointEnabled(true);
						enableStructuresEntered(true);
						if (incFrame != null) {
							incident.cargoTheftEnabled(true);
							incFrame.moveToFront();
						} else {
							incident.createFrame();
							incident.cargoTheftEnabled(true);
							incFrame.moveToFront();
						}
						if (vicFrame != null) {
							vic.enableAggAssault(false);
							vic.enableInjury(false);
							vic.enableJustHom(false);
						}
						forceCategoryEnabled(false);
						break;
					/*
					 * Other Gang Related Offenses
					 */
					case "Kidnapping/Abduction":
						if (vicFrame != null) {
							vic.enableAggAssault(false);
							vic.enableInjury(true);
							vic.enableJustHom(false);
						}
						gangActivityEnabled(true);
						forceCategoryEnabled(true);
						break;
					case "Intimidation":
						if (vicFrame != null) {
							vic.enableAggAssault(false);
							vic.enableInjury(false);
							vic.enableJustHom(false);
						}
						gangActivityEnabled(true);
						forceCategoryEnabled(false);
						break;
					/*
					 * Human Trafficking Offenses
					 */
					case "Human Trafficking, Commercial Sex Acts":
					case "Human Trafficking, Involuntary Servitude":
						forceCategoryEnabled(true);
						break;

					////////////////////////////////////////////////////////////////
					default:
						//////
						burglary = false;
						passagePointEnabled(false);
						enableStructuresEntered(false);
						//////
//						offenseAttemptedIndBOX.invalidate();
						offenseAttemptedIndBOX.setEnabled(true);
//						offenseAttemptedIndBOX.revalidate();
//						offenseAttemptedIndBOX.repaint();
						//////
						criminalActivityEnabled(false);
						forceCategoryEnabled(false);
						//////
						if (vicFrame != null) {
							vic.normalCodes();
							vic.enableAggAssault(false);
							vic.enableJustHom(false);
							vicFrame.moveToFront();
						}
						if (itemFrame != null) {
							item.enableValue(true);
							item.normalCodes();
							itemFrame.moveToFront();
						}
						/// Cargo Theft
						if (incFrame != null) {
							incident.cargoTheftEnabled(false);
							incFrame.moveToFront();
						} else {
							incident.createFrame();
							incident.cargoTheftEnabled(false);
							incFrame.moveToFront();
						}

						break;
					}
				}
			});

			finalOffIDFld.requestFocus();

			offenseAdded = false;
			numOpenFrames++;
			if (numOpenFrames > 17)
				numOpenFrames = 0;

			offIDFld = finalOffIDFld;

		}

		private void enableStructuresEntered(boolean arg) {

			if (arg) {
				if (hotel && burglary) {
					structuresEnteredFld.invalidate();
					structuresEnteredFld.setEnabled(true);
					structuresEnteredLbl.setEnabled(true);
					structuresEnteredFld.revalidate();
					structuresEnteredFld.repaint();
					structuresEnteredLbl.repaint();
				}
			} else {
				structuresEnteredFld.invalidate();
				structuresEnteredFld.setEnabled(false);
				structuresEnteredLbl.setEnabled(false);
				structuresEnteredFld.revalidate();
				structuresEnteredFld.repaint();
				structuresEnteredLbl.repaint();

			}
		}

		private void passagePointEnabled(boolean arg) {
			if (arg) {
				String[] passPntCodes = { "Force", "No Force" };
				passagePointCB = new JComboBox<String>(passPntCodes);

				passagePointLbl.setLabelFor(passagePointCB);

				panel3.invalidate();
				panel3.removeAll();
				panel3.add(structuresEnteredLbl);
				structuresEnteredLbl.setEnabled(false);
				panel3.add(structuresEnteredFld);
				structuresEnteredFld.setEnabled(false);
				panel3.add(passagePointLbl);
				passagePointLbl.setEnabled(true);
				panel3.add(passagePointCB);
				passagePointCB.setEnabled(true);
				panel3.add(offenseFactorLbl);
				panel3.add(offenseFactorCB);
				panel3.revalidate();
				panel3.repaint();
			} else {
				String[] passPntCodes = { "n/a", "Force", "No Force" };
				passagePointCB = new JComboBox<String>(passPntCodes);

				passagePointLbl.setLabelFor(passagePointCB);

				panel3.invalidate();
				panel3.removeAll();
				panel3.add(structuresEnteredLbl);
				structuresEnteredLbl.setEnabled(false);
				panel3.add(structuresEnteredFld);
				structuresEnteredFld.setEnabled(false);
				panel3.add(passagePointLbl);
				passagePointLbl.setEnabled(false);
				panel3.add(passagePointCB);
				passagePointCB.setEnabled(false);
				panel3.add(offenseFactorLbl);
				panel3.add(offenseFactorCB);
				panel3.revalidate();
				panel3.repaint();

			}

		}

		private void criminalActivityEnabled(boolean arg) {
			if (arg) {
				String[] crimActCodes = { "Buying/Receiving", "Cultivating/Manufacturing/Publishing",
						"Distributing/Selling", "Exploiting Children", "Operating/Promoting/Assisting",
						"Possessing/Concealing", "Transporting/Transmitting/Importing", "Using/Consuming" };
				criminalActivityCB = new JComboBox<String>(crimActCodes);
				criminalActivityLbl.setLabelFor(criminalActivityCB);

				panel2.invalidate();
				panel2.removeAll();
				panel2.add(criminalActivityLbl);
				panel2.add(criminalActivityCB);
				panel2.add(offenseAttemptedIndBOX);
				criminalActivityLbl.setEnabled(true);
				criminalActivityCB.setEnabled(true);
				panel2.revalidate();
				panel2.repaint();

			} else {
				String[] crimActCodes = { "n/a", "None/Unknown", "Simple/Gross Neglect", "Buying/Receiving",
						"Cultivating/Manufacturing/Publishing", "Distributing/Selling", "Exploiting Children",
						"Organized Abuse", "Other Gang", "Intentional Abuse and Torture", "Juvenile Gang",
						"Operating/Promoting/Assisting", "Possessing/Concealing", "Animal Sexual Abuse",
						"Transporting/Transmitting/Importing", "Using/Consuming" };
				criminalActivityCB = new JComboBox<String>(crimActCodes);
				criminalActivityLbl.setLabelFor(criminalActivityCB);

				panel2.invalidate();
				panel2.removeAll();
				panel2.add(criminalActivityLbl);
				panel2.add(criminalActivityCB);
				panel2.add(offenseAttemptedIndBOX);
				criminalActivityLbl.setEnabled(false);
				criminalActivityCB.setEnabled(false);
				panel2.revalidate();
				panel2.repaint();
			}
		}

		private void gangActivityEnabled(boolean arg) {
			if (arg) {
				String[] gangCodes = { "None/Unknown", "Other Gang", "Juvenile Gang" };
				criminalActivityCB = new JComboBox<String>(gangCodes);
				gangActivityLbl.setLabelFor(criminalActivityCB);

				panel2.invalidate();
				panel2.removeAll();
				panel2.add(gangActivityLbl);
				panel2.add(criminalActivityCB);
				panel2.add(offenseAttemptedIndBOX);
				criminalActivityLbl.setEnabled(true);
				criminalActivityCB.setEnabled(true);
				panel2.revalidate();
				panel2.repaint();
			} else {
				String[] crimActCodes = { "n/a", "None/Unknown", "Simple/Gross Neglect", "Buying/Receiving",
						"Cultivating/Manufacturing/Publishing", "Distributing/Selling", "Exploiting Children",
						"Organized Abuse", "Other Gang", "Intentional Abuse and Torture", "Juvenile Gang",
						"Operating/Promoting/Assisting", "Possessing/Concealing", "Animal Sexual Abuse",
						"Transporting/Transmitting/Importing", "Using/Consuming" };
				criminalActivityCB = new JComboBox<String>(crimActCodes);
				criminalActivityLbl.setLabelFor(criminalActivityCB);

				panel2.invalidate();
				panel2.removeAll();
				panel2.add(criminalActivityLbl);
				panel2.add(criminalActivityCB);
				panel2.add(offenseAttemptedIndBOX);
				criminalActivityLbl.setEnabled(false);
				criminalActivityCB.setEnabled(false);
				panel2.revalidate();
				panel2.repaint();
			}
		}

		private void forceCategoryEnabled(boolean arg) {
			if (arg) {
				forceCatCodeLbl1.setEnabled(true);
				forceCatCodeCB1.setEnabled(true);
				panel4.repaint();

				forceCatCodeLbl2.setEnabled(true);
				forceCatCodeCB2.setEnabled(true);
				panel5.repaint();

				forceCatCodeLbl3.setEnabled(true);
				forceCatCodeCB3.setEnabled(true);
				panel6.repaint();

			} else {
				forceCatCodeLbl1.setEnabled(false);
				forceCatCodeCB1.setEnabled(false);
				panel4.repaint();

				forceCatCodeLbl2.setEnabled(false);
				forceCatCodeCB2.setEnabled(false);
				panel5.repaint();

				forceCatCodeLbl3.setEnabled(false);
				forceCatCodeCB3.setEnabled(false);
				panel6.repaint();
			}
		}

		private void addDataToArrayList() {
			if (offIDFld != null && !offIDFld.getText().isEmpty()) {
				offArray = new String[15];
				offArray[0] = offIDFld.getText();
				offArray[1] = offenseCodeCB.getItemAt(offenseCodeCB.getSelectedIndex());
				offArray[2] = criminalActivityCB.getItemAt(criminalActivityCB.getSelectedIndex());
				offArray[3] = biasMotivationCB1.getItemAt(biasMotivationCB1.getSelectedIndex());
				offArray[4] = biasMotivationCB2.getItemAt(biasMotivationCB2.getSelectedIndex());
				offArray[5] = biasMotivationCB3.getItemAt(biasMotivationCB3.getSelectedIndex());
				offArray[6] = biasMotivationCB4.getItemAt(biasMotivationCB4.getSelectedIndex());
				offArray[7] = biasMotivationCB5.getItemAt(biasMotivationCB5.getSelectedIndex());
				offArray[8] = structuresEnteredFld.getText();
				offArray[9] = offenseFactorCB.getItemAt(offenseFactorCB.getSelectedIndex());
				offArray[10] = passagePointCB.getItemAt(passagePointCB.getSelectedIndex());
				offArray[11] = forceCatCodeCB1.getItemAt(forceCatCodeCB1.getSelectedIndex());
				offArray[12] = forceCatCodeCB2.getItemAt(forceCatCodeCB2.getSelectedIndex());
				offArray[13] = forceCatCodeCB3.getItemAt(forceCatCodeCB3.getSelectedIndex());

				if (offenseAttemptedIndBOX.isSelected())
					offArray[14] = "true";
				else
					offArray[14] = "false";

				offenseList.add(offArray);
			}
		}

		private void addDataToArrayList(JTextField idFld) {

			if (idFld != null) {
				offArray = new String[15];
				offArray[0] = offIDFld.getText();
				offArray[1] = offenseCodeCB.getItemAt(offenseCodeCB.getSelectedIndex());
				offArray[2] = criminalActivityCB.getItemAt(criminalActivityCB.getSelectedIndex());
				offArray[3] = biasMotivationCB1.getItemAt(biasMotivationCB1.getSelectedIndex());
				offArray[4] = biasMotivationCB2.getItemAt(biasMotivationCB2.getSelectedIndex());
				offArray[5] = biasMotivationCB3.getItemAt(biasMotivationCB3.getSelectedIndex());
				offArray[6] = biasMotivationCB4.getItemAt(biasMotivationCB4.getSelectedIndex());
				offArray[7] = biasMotivationCB5.getItemAt(biasMotivationCB5.getSelectedIndex());
				offArray[8] = structuresEnteredFld.getText();
				offArray[9] = offenseFactorCB.getItemAt(offenseFactorCB.getSelectedIndex());
				offArray[10] = passagePointCB.getItemAt(passagePointCB.getSelectedIndex());
				offArray[11] = forceCatCodeCB1.getItemAt(forceCatCodeCB1.getSelectedIndex());
				offArray[12] = forceCatCodeCB2.getItemAt(forceCatCodeCB2.getSelectedIndex());
				offArray[13] = forceCatCodeCB3.getItemAt(forceCatCodeCB3.getSelectedIndex());

				if (offenseAttemptedIndBOX.isSelected())
					offArray[14] = "true";
				else
					offArray[14] = "false";

				offenseList.add(offArray);
			}
		}

		private void deleteDataFromArrayList(JTextField idFld) {
			for (int i=0; i<offenseList.size(); i++){
				String[] array = offenseList.get(i);
				if (array[0].equals(idFld.getText())) offenseList.remove(i);
			}
		}

	};

	private class LocGUI {

		JButton saveBtn, deleteBtn, createXMLBtn, saveAndAddBtn;
		JLabel locIdLbl, locCodeLbl;
		JPanel panel1, panel2, panel3, panel4, panel5;
		JTextField locIdFld;
		JComboBox<String> locCodeCB;
		String[] locArray = { "", "" };
		String[] locCodes = { "Air/Bus/Train Terminal", "Bank/Savings and Loan", "Bar/Nightclub",
				"Church/Synagogue/Temple/Mosque", "Commercial/Office Building", "Construction Site",
				"Convenience Store", "Department/Discount Store", "Drug Store/Doctor's Office/Hospital", "Field/Woods",
				"Government/Public Building", "Grocery/Supermarket", "Highway/Road/Alley/Street/Sidewalk",
				"Hotel/Motel/Etc", "Jail/Prison/Penitentiary/Corrections Facility", "Lake/Waterway/Beach",
				"Liquor Store", "Parking/Drop Lot/Garage", "Rental Storage Facility", "Residence/Home", "Restaurant",
				"School/College", "Service/Gas Station", "Specialty Store", "Other/Unknown",
				"Abandoned/Condemned Structure", "Amusement Park", "Arena/Stadium/Fairgrounds/Coliseum",
				"ATM Separate from Bank", "Auto Dealership New/Used", "Camp/Campground", "Daycare Facility",
				"Dock/Wharf/Freight/Modal Terminal", "Farm Facility", "Gambling Facility/Casino/Race Track",
				"Industrial Site", "Military Installation", "Park/Playground", "Rest Area", "School-College University",
				"School-Elementary/Secondary", "Shelter-Mission/Homeless", "Shopping Mall", "Tribal Lands",
				"Community Center" };

		private void createFrame() {
			final JInternalFrame locFrame = new JInternalFrame("Location");
			locFrame.setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
			locFrame.setBounds(pos * numOpenFrames, pos * numOpenFrames, 400, 250);
			locFrame.setVisible(true);

			locCodeCB = new JComboBox<String>(locCodes);
			final JTextField finalLocIdFld = new JTextField(15);

			saveAndAddBtn = new JButton("Save and Add");

			locIdLbl = new JLabel("Location ID:");
			locCodeLbl = new JLabel("Location Code:");

			panel1 = new JPanel();
			panel2 = new JPanel();
			panel3 = new JPanel();
			panel4 = new JPanel();
			panel5 = new JPanel();

			locIdLbl.setLabelFor(finalLocIdFld);
			locCodeLbl.setLabelFor(locCodeCB);

			panel1.add(locIdLbl);
			panel1.add(finalLocIdFld);

			panel2.add(locCodeLbl);
			panel2.add(locCodeCB);

			saveAndAddBtn = new JButton("Save and Add");
			saveBtn = new JButton("Save");
			deleteBtn = new JButton("Delete");
			createXMLBtn = new JButton("Create XML");

			createXMLBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					fileCreateGUI();
				}
			});
			deleteBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					//locFrame.setVisible(false);
					//locFrame.dispose();
					//deleteDataFromArrayList(finalLocIdFld);
					locFrame.setVisible(false);
					deleteDataFromArrayList(finalLocIdFld);
					initFrame.remove(locFrame);
					finalLocIdFld.setText(null);
				}
			});
			saveAndAddBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					addDataToArrayList(finalLocIdFld);
					location.createFrame();
				}

			});
			saveBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					addDataToArrayList(finalLocIdFld);
					locationAdded = true;
				}

			});

			panel5.add(createXMLBtn);
			panel5.add(deleteBtn);
			panel5.add(saveBtn);
			panel5.add(saveAndAddBtn);

			locFrame.getContentPane().add(panel1);
			locFrame.getContentPane().add(panel2);
			locFrame.getContentPane().add(panel3);
			locFrame.getContentPane().add(panel4);
			locFrame.getContentPane().add(panel5);
			locFrame.setLayout(new GridLayout(5, 1));

			dtPane.add(locFrame);
			locFrame.moveToFront();

			initFrame.setContentPane(dtPane);

			locCodeCB.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					switch (locCodeCB.getItemAt(locCodeCB.getSelectedIndex())) {
					case "Hotel/Motel/Etc":
					case "Rental Storage Facility":
						hotel = true;
						offense.enableStructuresEntered(true);
						break;
					default:
						hotel = false;
						offense.enableStructuresEntered(false);
						break;
					}
				}
			});

			finalLocIdFld.requestFocus();

			numOpenFrames++;
			if (numOpenFrames > 17)
				numOpenFrames = 0;

			locIdFld = finalLocIdFld;
		}

		private void addDataToArrayList() {
			if (locIdFld != null && !locIdFld.getText().isEmpty()) {
				locArray = new String[2];
				locArray[0] = locIdFld.getText();
				locArray[1] = (String) locCodeCB.getItemAt(locCodeCB.getSelectedIndex());

				locList.add(locArray);
			}

		}

		private void addDataToArrayList(JTextField idFld) {

			if (idFld != null) {
				locArray = new String[2];
				locArray[0] = idFld.getText();
				locArray[1] = (String) locCodeCB.getItemAt(locCodeCB.getSelectedIndex());

				locList.add(locArray);
			}
		}

		private void deleteDataFromArrayList(JTextField idFld) {
			for (int i=0; i<locList.size(); i++){
				String[] array = locList.get(i);
				if (array[0].equals(idFld.getText())) locList.remove(i);
			}
		}
	};

	private class ItemGUI {

		JButton saveBtn, deleteBtn, createXMLBtn, saveAndAddBtn;
		JLabel itemIDLbl, itemValAmountLbl, itemDateLbl, itemTLbl, itemQuantLbl, itemStatusLbl, propCatCodeLbl;
		JPanel panel1, panel2, panel3, panel4, panel5;
		JTextField itemIDFld, itemValAmountFld, itemMonthFld, itemDayFld, itemYearFld, itemHourFld, itemMinFld,
				itemSecFld, itemQuantFld;
		JComboBox<String> itemStatusCB, propCatCodeCB;
		String[] itemArray = { "", "", "", "", "", "", "", "", "", "", "", };
		String[] itemStatusCodes = { "NONE", "BURNED", "COUNTERFEITED", "DESTROYED_DAMAGED_VANDALIZED", "RECOVERED",
				"SEIZED", "STOLEN", "UNKNOWN" };
		String[] propCatCodes = { "Aircraft", "Alcohol", "Automobile", "Bicycles", "Buses", "Clothes/Furs",
				"Computer Hardware/Software", "Consumable Goods", "Credit/Debit Cards", "Drugs/Narcotics",
				"Drug/Narcotic Equipment", "Farm Equipment", "Firearms", "Gambling Equipment",
				"Heavy Construction/Industrial Equipment", "Household Goods", "Jewelry/Precious Metals/Gems",
				"Livestock", "Merchandise", "Money", "Negotiable Instruments", "Nonnegotiable Instruments",
				"Office-Type Equipment", "Other Motor Vehicles", "Purses/Handbags/Wallets", "Radio/TV/VCR/DVD Players",
				"Recordings - audio/visual", "Recreational Vehicles", "Structures - single occupancy dwellings",
				"Structures - other dwellings", "Structures - other commercial/business",
				"Structures - industrial/manufacturing", "Structures - public/community", "Structures - storage",
				"Structures - other", "Tools", "Trucks", "Vehicle Parts/Accessories", "Watercraft",
				"Aircraft Parts/Accessories", "Artistic Supplies/Accessories", "building materials",
				"Camping/Hunting/Fishing Equipment/Supplies", "Chemicals", "Collections/Collectibles", "Crops",
				"Documents/Personal or Business", "Explosives", "Firearm Accessories", "Fuel", "Identity Documents",
				"Identity - Intangible", "Law Enforcement Equipment", "Lawn/Yard/Garden Equipment", "Logging Equipment",
				"Medical/Medical Lab Equipment", "Metals, Non-Precious", "Musical Instruments", "Pets",
				"Photographic/Optical Equipment", "Portable Electronic Communications", "Recreational/Sports Equipment",
				"Other", "Trailers", "Watercraft Equipment/Parts/Accessories", "Weapons - Other", "Pending Inventory" };

		private void createFrame() {
			final JInternalFrame iFrame = new JInternalFrame("Item");
			iFrame.setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
			iFrame.setBounds(pos * numOpenFrames, pos * numOpenFrames, 600, 250);
			iFrame.setVisible(true);

			itemStatusCB = new JComboBox<String>(itemStatusCodes);
			propCatCodeCB = new JComboBox<String>(propCatCodes);

			// itemIDFld = new JTextField(15);
			final JTextField finalItemIdFld = new JTextField(15);
			itemValAmountFld = new JTextField(5);
			itemMonthFld = new JTextField(2);
			itemDayFld = new JTextField(2);
			itemYearFld = new JTextField(3);
			itemHourFld = new JTextField(2);
			itemMinFld = new JTextField(2);
			itemSecFld = new JTextField(2);
			itemQuantFld = new JTextField(3);

			itemYearFld.setText(timeNow("YYYY"));
			itemMonthFld.setText(timeNow("MM"));
			itemDayFld.setText(timeNow("dd"));
			itemHourFld.setText(timeNow("H"));
			itemMinFld.setText(timeNow("mm"));
			itemSecFld.setText(timeNow("ss"));

			itemIDLbl = new JLabel("Item ID:");
			itemDateLbl = new JLabel("Date mm/dd/yy hh:mm:ss");
			itemValAmountLbl = new JLabel("Item Value");
			itemTLbl = new JLabel("T");
			itemQuantLbl = new JLabel("Item Quantity");
			itemStatusLbl = new JLabel("Item Status");
			propCatCodeLbl = new JLabel("Property Category Code");

			panel1 = new JPanel();
			panel2 = new JPanel();
			panel3 = new JPanel();
			panel4 = new JPanel();
			panel5 = new JPanel();

			itemIDLbl.setLabelFor(finalItemIdFld);
			itemDateLbl.setLabelFor(itemMonthFld);
			itemValAmountLbl.setLabelFor(itemValAmountFld);
			itemTLbl.setLabelFor(itemHourFld);
			itemQuantLbl.setLabelFor(itemQuantFld);
			itemStatusLbl.setLabelFor(itemStatusCB);
			propCatCodeLbl.setLabelFor(propCatCodeCB);

			panel1.add(itemIDLbl);
			panel1.add(finalItemIdFld);

			panel2.add(itemDateLbl);
			panel2.add(itemMonthFld);
			panel2.add(itemDayFld);
			panel2.add(itemYearFld);
			panel2.add(itemTLbl);
			panel2.add(itemHourFld);
			panel2.add(itemMinFld);
			panel2.add(itemSecFld);

			panel3.add(itemQuantLbl);
			panel3.add(itemQuantFld);
			panel3.add(itemStatusLbl);
			panel3.add(itemStatusCB);

			panel4.add(itemValAmountLbl);
			panel4.add(itemValAmountFld);
			panel4.add(propCatCodeLbl);
			panel4.add(propCatCodeCB);

			saveAndAddBtn = new JButton("Save and Add");
			saveBtn = new JButton("Save");
			deleteBtn = new JButton("Delete");
			createXMLBtn = new JButton("Create XML");

			createXMLBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					fileCreateGUI();
				}
			});
			deleteBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					//itemFrame.setVisible(false);
					//itemFrame.dispose();
					//deleteDataFromArrayList(finalItemIdFld);
					itemFrame.setVisible(false);
					deleteDataFromArrayList(finalItemIdFld);
					initFrame.remove(itemFrame);
					finalItemIdFld.setText(null);
				}
			});
			saveAndAddBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					addDataToArrayList(finalItemIdFld);
					item.createFrame();
				}

			});
			saveBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					addDataToArrayList(finalItemIdFld);
					itemAdded = true;
				}

			});

			panel5.add(createXMLBtn);
			panel5.add(deleteBtn);
			panel5.add(saveBtn);
			panel5.add(saveAndAddBtn);

			iFrame.getContentPane().add(panel1);
			iFrame.getContentPane().add(panel2);
			iFrame.getContentPane().add(panel3);
			iFrame.getContentPane().add(panel4);
			iFrame.getContentPane().add(panel5);
			iFrame.setLayout(new GridLayout(5, 1));

			dtPane.add(iFrame);
			iFrame.moveToFront();

			finalItemIdFld.requestFocus();

			numOpenFrames++;
			if (numOpenFrames > 17)
				numOpenFrames = 0;

			itemIDFld = finalItemIdFld;
			itemFrame = iFrame;
		}

		private void enableValue(boolean arg) {
			if (arg) {
				itemValAmountFld.invalidate();
				itemValAmountFld.setEnabled(true);
				itemValAmountLbl.setEnabled(true);
				itemValAmountFld.revalidate();
				itemValAmountFld.repaint();
				itemValAmountLbl.repaint();
			} else {
				itemValAmountFld.invalidate();
				itemValAmountFld.setEnabled(false);
				itemValAmountLbl.setEnabled(false);
				itemValAmountFld.revalidate();
				itemValAmountFld.repaint();
				itemValAmountLbl.repaint();

			}
		}

		private void motorVehicleCodes() {
			String[] propCatCodes = { "Aircraft", "Automobile", "Buses", "Other Motor Vehicles",
					"Recreational Vehicles", "Trucks" };

			panel4.invalidate();
			panel4.removeAll();
			JComboBox<String> propCatCodeCB = new JComboBox<String>(propCatCodes);
			propCatCodeLbl.setLabelFor(propCatCodeCB);
			panel4.add(itemValAmountLbl);
			panel4.add(itemValAmountFld);
			panel4.add(propCatCodeLbl);
			panel4.add(propCatCodeCB);
			panel4.revalidate();
			panel4.repaint();
		}

		private void normalCodes() {
			String[] itemStatusCodes = { "NONE", "BURNED", "COUNTERFEITED", "DESTROYED_DAMAGED_VANDALIZED", "RECOVERED",
					"SEIZED", "STOLEN", "UNKNOWN" };
			String[] propCatCodes = { "Aircraft", "Alcohol", "Automobile", "Bicycles", "Buses", "Clothes/Furs",
					"Computer Hardware/Software", "Consumable Goods", "Credit/Debit Cards", "Drugs/Narcotics",
					"Drug/Narcotic Equipment", "Farm Equipment", "Firearms", "Gambling Equipment",
					"Heavy Construction/Industrial Equipment", "Household Goods", "Jewelry/Precious Metals/Gems",
					"Livestock", "Merchandise", "Money", "Negotiable Instruments", "Nonnegotiable Instruments",
					"Office-Type Equipment", "Other Motor Vehicles", "Purses/Handbags/Wallets",
					"Radio/TV/VCR/DVD Players", "Recordings - audio/visual", "Recreational Vehicles",
					"Structures - single occupancy dwellings", "Structures - other dwellings",
					"Structures - other commercial/business", "Structures - industrial/manufacturing",
					"Structures - public/community", "Structures - storage", "Structures - other", "Tools", "Trucks",
					"Vehicle Parts/Accessories", "Watercraft", "Aircraft Parts/Accessories",
					"Artistic Supplies/Accessories", "building materials", "Camping/Hunting/Fishing Equipment/Supplies",
					"Chemicals", "Collections/Collectibles", "Crops", "Documents/Personal or Business", "Explosives",
					"Firearm Accessories", "Fuel", "Identity Documents", "Identity - Intangible",
					"Law Enforcement Equipment", "Lawn/Yard/Garden Equipment", "Logging Equipment",
					"Medical/Medical Lab Equipment", "Metals, Non-Precious", "Musical Instruments", "Pets",
					"Photographic/Optical Equipment", "Portable Electronic Communications",
					"Recreational/Sports Equipment", "Other", "Trailers", "Watercraft Equipment/Parts/Accessories",
					"Weapons - Other", "Pending Inventory" };

			panel4.invalidate();
			panel4.removeAll();
			JComboBox<String> propCatCodeCB = new JComboBox<String>(propCatCodes);
			propCatCodeLbl.setLabelFor(propCatCodeCB);
			panel4.add(itemValAmountLbl);
			panel4.add(itemValAmountFld);
			panel4.add(propCatCodeLbl);
			panel4.add(propCatCodeCB);
			panel4.revalidate();
			panel4.repaint();

			panel3.invalidate();
			panel3.removeAll();
			JComboBox<String> itemStatusCB = new JComboBox<String>(itemStatusCodes);
			itemStatusLbl.setLabelFor(itemStatusCB);
			panel3.add(itemQuantLbl);
			panel3.add(itemQuantFld);
			panel3.add(itemStatusLbl);
			panel3.add(itemStatusCB);
			panel3.revalidate();
			panel3.repaint();

		}

		private void addDataToArrayList() {
			if (itemIDFld != null && !itemIDFld.getText().isEmpty()) {
				itemArray = new String[11];
				itemArray[0] = itemIDFld.getText();
				itemArray[1] = itemYearFld.getText();
				itemArray[2] = itemMonthFld.getText();
				itemArray[3] = itemDayFld.getText();
				itemArray[4] = itemHourFld.getText();
				itemArray[5] = itemMinFld.getText();
				itemArray[6] = itemSecFld.getText();
				itemArray[7] = itemValAmountFld.getText();
				itemArray[8] = itemQuantFld.getText();
				itemArray[9] = itemStatusCB.getItemAt(itemStatusCB.getSelectedIndex());
				itemArray[10] = propCatCodeCB.getItemAt(propCatCodeCB.getSelectedIndex());

				itemList.add(itemArray);
			}

		}

		private void addDataToArrayList(JTextField idFld) {

			if (idFld != null) {
				itemArray = new String[11];
				itemArray[0] = idFld.getText();
				itemArray[1] = itemYearFld.getText();
				itemArray[2] = itemMonthFld.getText();
				itemArray[3] = itemDayFld.getText();
				itemArray[4] = itemHourFld.getText();
				itemArray[5] = itemMinFld.getText();
				itemArray[6] = itemSecFld.getText();
				itemArray[7] = itemValAmountFld.getText();
				itemArray[8] = itemQuantFld.getText();
				itemArray[9] = itemStatusCB.getItemAt(itemStatusCB.getSelectedIndex());
				itemArray[10] = propCatCodeCB.getItemAt(propCatCodeCB.getSelectedIndex());

				itemList.add(itemArray);
			}
		}

		private void deleteDataFromArrayList(JTextField idFld) {
			for (int i=0; i<itemList.size(); i++){
				String[] array = itemList.get(i);
				if (array[0].equals(idFld.getText())) itemList.remove(i);
			}
		}
	};

	private class SubstanceGUI {

		JButton saveBtn, deleteBtn, createXMLBtn, saveAndAddBtn;
		JLabel substanceIDLbl, substanceAmountLbl, substanceMeasureUnitLbl, drugCatCodeLbl;
		JLabel itemValAmountLbl, itemQuantLbl, itemStatusLbl, propCatCodeLbl;
		JTextField itemValAmountFld, itemQuantFld;
		JTextField substanceIDFld, substanceAmountFld;
		JComboBox<String> substanceMeasureUnitCB, drugCatCodeCB;
		JComboBox<String> itemStatusCB, propCatCodeCB;
		JPanel panel1, panel2, panel3, panel4, panel5;
		String[] substanceArray = { "", "", "", "", "", "", "", ""};
		String[] drugMeasureUnits = { "Dosage Units/Items", "Fluid Ounce", "Gallon", "Gram", "Kilogram", "Pound",
				"Liter", "Milliliter", "Number of Plants", "Ounce", "Not Reported" };
		String[] drugCatCodes = { "Crack Cocaine", "Cocaine (All forms except Crack)", "Hashish", "Heroin", "Marijuana",
				"Morphine", "Opium", "Other Narcotics", "LSD", "PCP", "Other Hallucinogens",
				"Amphetamines/Methamphetamines", "Other Stimulants", "Barbiturates", "Other Depressants", "Other Drugs",
				"Unknown Drug Type", "Over 3 Drug Types" };
		String[] itemStatusCodes = { "NONE", "BURNED", "COUNTERFEITED", "DESTROYED_DAMAGED_VANDALIZED", "RECOVERED",
				"SEIZED", "STOLEN", "UNKNOWN" };
		String[] propCatCodes = { "Aircraft", "Alcohol", "Automobile", "Bicycles", "Buses", "Clothes/Furs",
				"Computer Hardware/Software", "Consumable Goods", "Credit/Debit Cards", "Drugs/Narcotics",
				"Drug/Narcotic Equipment", "Farm Equipment", "Firearms", "Gambling Equipment",
				"Heavy Construction/Industrial Equipment", "Household Goods", "Jewelry/Precious Metals/Gems",
				"Livestock", "Merchandise", "Money", "Negotiable Instruments", "Nonnegotiable Instruments",
				"Office-Type Equipment", "Other Motor Vehicles", "Purses/Handbags/Wallets", "Radio/TV/VCR/DVD Players",
				"Recordings - audio/visual", "Recreational Vehicles", "Structures - single occupancy dwellings",
				"Structures - other dwellings", "Structures - other commercial/business",
				"Structures - industrial/manufacturing", "Structures - public/community", "Structures - storage",
				"Structures - other", "Tools", "Trucks", "Vehicle Parts/Accessories", "Watercraft",
				"Aircraft Parts/Accessories", "Artistic Supplies/Accessories", "building materials",
				"Camping/Hunting/Fishing Equipment/Supplies", "Chemicals", "Collections/Collectibles", "Crops",
				"Documents/Personal or Business", "Explosives", "Firearm Accessories", "Fuel", "Identity Documents",
				"Identity - Intangible", "Law Enforcement Equipment", "Lawn/Yard/Garden Equipment", "Logging Equipment",
				"Medical/Medical Lab Equipment", "Metals, Non-Precious", "Musical Instruments", "Pets",
				"Photographic/Optical Equipment", "Portable Electronic Communications", "Recreational/Sports Equipment",
				"Other", "Trailers", "Watercraft Equipment/Parts/Accessories", "Weapons - Other", "Pending Inventory" };

		private void createFrame() {
			final JInternalFrame substanceFrame = new JInternalFrame("Substance");
			substanceFrame.setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
			substanceFrame.setBounds(pos * numOpenFrames, pos * numOpenFrames, 550, 250);
			substanceFrame.setVisible(true);

			itemStatusCB = new JComboBox<String>(itemStatusCodes);
			propCatCodeCB = new JComboBox<String>(propCatCodes);

			substanceMeasureUnitCB = new JComboBox<String>(drugMeasureUnits);
			drugCatCodeCB = new JComboBox<String>(drugCatCodes);

			final JTextField finalSubstanceIdFld = new JTextField(5);
			substanceAmountFld = new JTextField(5);
			itemValAmountFld = new JTextField(5);
			itemQuantFld = new JTextField(3);

			substanceIDLbl = new JLabel("Substance ID:");
			substanceAmountLbl = new JLabel("Measurement Amount:");
			substanceMeasureUnitLbl = new JLabel("Measurement Unit");
			drugCatCodeLbl = new JLabel("Drug Category Code:");
			itemValAmountLbl = new JLabel("Value");
			itemQuantLbl = new JLabel("Quantity");
			itemStatusLbl = new JLabel("Status");
			propCatCodeLbl = new JLabel("Property Category Code");

			panel1 = new JPanel();
			panel2 = new JPanel();
			panel3 = new JPanel();
			panel4 = new JPanel();
			panel5 = new JPanel();

			substanceIDLbl.setLabelFor(finalSubstanceIdFld);
			drugCatCodeLbl.setLabelFor(drugCatCodeCB);
			substanceAmountLbl.setLabelFor(substanceAmountFld);
			substanceMeasureUnitLbl.setLabelFor(substanceMeasureUnitCB);
			itemValAmountLbl.setLabelFor(itemValAmountFld);
			itemQuantLbl.setLabelFor(itemQuantFld);
			itemStatusLbl.setLabelFor(itemStatusCB);
			propCatCodeLbl.setLabelFor(propCatCodeCB);

			panel1.add(substanceIDLbl);
			panel1.add(finalSubstanceIdFld);
			panel1.add(itemStatusLbl);
			panel1.add(itemStatusCB);
			panel1.add(itemQuantLbl);
			panel1.add(itemQuantFld);

			panel2.add(drugCatCodeLbl);
			panel2.add(drugCatCodeCB);
			panel2.add(itemValAmountLbl);
			panel2.add(itemValAmountFld);

			panel3.add(substanceAmountLbl);
			panel3.add(substanceAmountFld);
			panel3.add(substanceMeasureUnitLbl);
			panel3.add(substanceMeasureUnitCB);

			panel4.add(propCatCodeLbl);
			panel4.add(propCatCodeCB);

			
			saveAndAddBtn = new JButton("Save and Add");
			saveBtn = new JButton("Save");
			deleteBtn = new JButton("Delete");
			createXMLBtn = new JButton("Create XML");

			createXMLBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					fileCreateGUI();
				}
			});
			deleteBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					//substanceFrame.setVisible(false);
					//substanceFrame.dispose();
					//deleteDataFromArrayList(finalSubstanceIdFld);
					substanceFrame.setVisible(false);
					deleteDataFromArrayList(finalSubstanceIdFld);
					initFrame.remove(substanceFrame);
					finalSubstanceIdFld.setText(null);
				}
			});
			saveAndAddBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					addDataToArrayList(finalSubstanceIdFld);
					subs.createFrame();
				}

			});
			saveBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					addDataToArrayList(finalSubstanceIdFld);
					substanceAdded = true;
				}

			});

			panel5.add(createXMLBtn);
			panel5.add(deleteBtn);
			panel5.add(saveBtn);
			panel5.add(saveAndAddBtn);

			substanceFrame.getContentPane().add(panel1);
			substanceFrame.getContentPane().add(panel2);
			substanceFrame.getContentPane().add(panel3);
			substanceFrame.getContentPane().add(panel4);
			substanceFrame.getContentPane().add(panel5);
			substanceFrame.setLayout(new GridLayout(5, 1));

			dtPane.add(substanceFrame);
			substanceFrame.moveToFront();

			finalSubstanceIdFld.requestFocus();

			initFrame.setContentPane(dtPane);
			numOpenFrames++;
			if (numOpenFrames > 17)
				numOpenFrames = 0;

			substanceIDFld = finalSubstanceIdFld;
			subsFrame = substanceFrame;
		}

		private void addDataToArrayList() {
			if (substanceIDFld != null && !substanceIDFld.getText().isEmpty()) {
				substanceArray = new String[8];
				substanceArray[0] = substanceIDFld.getText();
				substanceArray[1] = drugCatCodeCB.getItemAt(drugCatCodeCB.getSelectedIndex());
				substanceArray[2] = substanceAmountFld.getText();
				substanceArray[3] = substanceMeasureUnitCB.getItemAt(substanceMeasureUnitCB.getSelectedIndex());
				substanceArray[4] = itemStatusCB.getItemAt(itemStatusCB.getSelectedIndex());
				substanceArray[5] = itemValAmountFld.getText();
				substanceArray[6] = propCatCodeCB.getItemAt(propCatCodeCB.getSelectedIndex());
				substanceArray[7] = itemQuantFld.getText();

				substanceList.add(substanceArray);
			}

		}

		private void addDataToArrayList(JTextField idField) {

			if (idField != null) {
				substanceArray = new String[8];
				substanceArray[0] = substanceIDFld.getText();
				substanceArray[1] = drugCatCodeCB.getItemAt(drugCatCodeCB.getSelectedIndex());
				substanceArray[2] = substanceAmountFld.getText();
				substanceArray[3] = substanceMeasureUnitCB.getItemAt(substanceMeasureUnitCB.getSelectedIndex());
				substanceArray[4] = itemStatusCB.getItemAt(itemStatusCB.getSelectedIndex());
				substanceArray[5] = itemValAmountFld.getText();
				substanceArray[6] = propCatCodeCB.getItemAt(propCatCodeCB.getSelectedIndex());
				substanceArray[7] = itemQuantFld.getText();

				substanceList.add(substanceArray);
			}

		}

		private void deleteDataFromArrayList(JTextField idFld) {
			for (int i=0; i<substanceList.size(); i++){
				String[] array = substanceList.get(i);
				if (array[0].equals(idFld.getText())) substanceList.remove(i);
			}
		}

	};

	private class PersonGUI {

		JLabel personIDLbl, personEthLbl, personRaceLbl, personResLbl, personGenLbl, personAgeLbl, personToLbl;
		JPanel panel1, panel2, panel3, panel4, panel5;
		JCheckBox ageRangeBX, ageTextBX;
		JButton saveAndAddBtn, saveBtn, deleteBtn, createXMLBtn;
		JTextField personIDFld, personAgeFld, personAgeMinFld, personAgeMaxFld;
		JComboBox<String> personEthCB, personRaceCB, personResCB, personGenCB, personAgeCB;
		String[] personArray = { "", "", "", "", "", "", "", "", "" };
		String[] ethCodes = {"Unknown", "Hispanic or Latino", "Not Hispanic or Latino"};
		String[] raceCodes = { "UNKNOWN", "ASIAN", "BLACK or AFRICAN AMERICAN", "AMERICAN INDIAN or ALASKA NATIVE", 
				"WHITE", "NATIVE HAWAIIAN or OTHER PACIFIC ISLANDER" };
		String[] resCodes = {"Unknown", "Nonresident", "Resident"};
		String[] genderCodes = {"UNKNOWN", "FEMALE", "MALE"};
		String[] ageCodes = {"<24 hrs", "1-6 days", "7-364 days", "Over 98", "Unknown"};

		private void createFrame() {
			final JInternalFrame personFrame = new JInternalFrame("Person");
			personFrame.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
			personFrame.setClosable(true);
			personFrame.setBounds(pos * numOpenFrames, pos * numOpenFrames, 500, 250);
			personFrame.setVisible(true);

			ageRangeBX = new JCheckBox("Range");
			ageTextBX = new JCheckBox("Text Value");

			personEthCB = new JComboBox<String>(ethCodes);
			personRaceCB = new JComboBox<String>(raceCodes);
			personResCB = new JComboBox<String>(resCodes);
			personGenCB = new JComboBox<String>(genderCodes);
			personAgeCB = new JComboBox<String>(ageCodes);

			final JTextField finalPersonIDFld = new JTextField(15);
			personAgeFld = new JTextField(2);
			personAgeMinFld = new JTextField(2);
			personAgeMaxFld = new JTextField(2);

			personIDLbl = new JLabel("Person ID:");
			personEthLbl = new JLabel("Ethnicity:");
			personRaceLbl = new JLabel("Race");
			personResLbl = new JLabel("Resident:");
			personGenLbl = new JLabel("Gender:");
			personAgeLbl = new JLabel("Age:");
			personToLbl = new JLabel(" To ");

			panel1 = new JPanel();
			panel2 = new JPanel();
			panel3 = new JPanel();
			panel4 = new JPanel();
			panel5 = new JPanel();

			personIDLbl.setLabelFor(finalPersonIDFld);
			personEthLbl.setLabelFor(personEthCB);
			personRaceLbl.setLabelFor(personRaceCB);
			personResLbl.setLabelFor(personResCB);
			personGenLbl.setLabelFor(personGenCB);
			personAgeLbl.setLabelFor(personAgeFld);
			personAgeLbl.setLabelFor(personAgeMinFld);
			personToLbl.setLabelFor(personAgeMaxFld);

			panel1.add(personIDLbl);
			panel1.add(finalPersonIDFld);

			panel2.add(ageRangeBX);
			panel2.add(ageTextBX);
			panel2.add(personAgeLbl);
			panel2.add(personAgeFld);
			panel2.add(personResLbl);
			panel2.add(personResCB);

			panel3.add(personGenLbl);
			panel3.add(personGenCB);
			panel3.add(personEthLbl);
			panel3.add(personEthCB);

			panel4.add(personRaceLbl);
			panel4.add(personRaceCB);

			saveAndAddBtn = new JButton("Save and Add");
			saveBtn = new JButton("Save");
			deleteBtn = new JButton("Delete");
			createXMLBtn = new JButton("Create XML");

			createXMLBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					fileCreateGUI();
				}
			});
			deleteBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					personFrame.setVisible(false);
					deleteDataFromArrayList(finalPersonIDFld);
					initFrame.remove(personFrame);
					finalPersonIDFld.setText(null);
				}
			});
			saveAndAddBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					addDataToArrayList(finalPersonIDFld);
					if (vicFrame != null)
						vic.updateRoles();
					person.createFrame();
				}

			});
			saveBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					addDataToArrayList(finalPersonIDFld);
					if (vicFrame != null)
						vic.updateRoles();
					personAdded = true;
				}

			});

			panel5.add(createXMLBtn);
			panel5.add(deleteBtn);
			panel5.add(saveBtn);
			panel5.add(saveAndAddBtn);
			// panel5.add(createPersonBtn);

			personFrame.getContentPane().add(panel1);
			personFrame.getContentPane().add(panel2);
			personFrame.getContentPane().add(panel3);
			personFrame.getContentPane().add(panel4);
			personFrame.getContentPane().add(panel5);
			personFrame.setLayout(new GridLayout(5, 1));

			dtPane.add(personFrame);
			personFrame.moveToFront();
			personIDFld = finalPersonIDFld;

			ageRangeBX.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (ageRangeBX.isSelected()) {
						panel2.invalidate();
						panel2.remove(personAgeFld);
						panel2.add(personAgeMinFld);
						panel2.add(personToLbl);
						panel2.add(personAgeMaxFld);
						panel2.add(personResLbl);
						panel2.add(personResCB);
						panel2.repaint();
						panel2.validate();
					} else {
						panel2.invalidate();
						panel2.remove(personAgeMinFld);
						panel2.remove(personToLbl);
						panel2.remove(personAgeMaxFld);
						panel2.add(personAgeFld);
						panel2.add(personResLbl);
						panel2.add(personResCB);
						panel2.repaint();
						panel2.validate();
					}
				}
			});
			ageTextBX.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (ageTextBX.isSelected()) {
						panel2.invalidate();
						panel2.remove(personAgeFld);
						panel2.add(personAgeCB);
						panel2.add(personResLbl);
						panel2.add(personResCB);
						panel2.repaint();
						panel2.validate();
					} else {
						panel2.invalidate();
						panel2.remove(personAgeCB);
						panel2.add(personAgeFld);
						panel2.add(personResLbl);
						panel2.add(personResCB);
						panel2.repaint();
						panel2.validate();
					}
				}
			});

			finalPersonIDFld.requestFocus();

			initFrame.setContentPane(dtPane);
			numOpenFrames++;
			if (numOpenFrames > 17)
				numOpenFrames = 0;

		}

		private void addDataToArrayList() {
			if (personIDFld != null && !personIDFld.getText().isEmpty()) {
				personArray = new String[9];
				personArray[0] = personIDFld.getText();
				personArray[1] = personEthCB.getItemAt(personEthCB.getSelectedIndex());
				personArray[2] = personRaceCB.getItemAt(personRaceCB.getSelectedIndex());
				personArray[3] = personResCB.getItemAt(personResCB.getSelectedIndex());
				personArray[4] = personGenCB.getItemAt(personGenCB.getSelectedIndex());
				if (personAgeFld != null)
					personArray[5] = personAgeFld.getText();
				if (personAgeMinFld != null)
					personArray[6] = personAgeMinFld.getText();
				if (personAgeMaxFld != null)
					personArray[7] = personAgeMaxFld.getText();
				if (personAgeMaxFld != null)
					personArray[8] = personAgeCB.getItemAt(personAgeCB.getSelectedIndex());

				personList.add(personArray);
			}

		}

		private void addDataToArrayList(JTextField idField) {
			if (idField != null) {
				personArray = new String[9];
				personArray[0] = personIDFld.getText();
				personArray[1] = personEthCB.getItemAt(personEthCB.getSelectedIndex());
				personArray[2] = personRaceCB.getItemAt(personRaceCB.getSelectedIndex());
				personArray[3] = personResCB.getItemAt(personResCB.getSelectedIndex());
				personArray[4] = personGenCB.getItemAt(personGenCB.getSelectedIndex());
				if (personAgeFld != null)
					personArray[5] = personAgeFld.getText();
				if (personAgeMinFld != null)
					personArray[6] = personAgeMinFld.getText();
				if (personAgeMaxFld != null)
					personArray[7] = personAgeMaxFld.getText();
				if (personAgeMaxFld != null)
					personArray[8] = personAgeCB.getItemAt(personAgeCB.getSelectedIndex());

				personList.add(personArray);
			}

		}

		private void deleteDataFromArrayList(JTextField idFld) {
			for (int i=0; i<personList.size(); i++){
				String[] array = personList.get(i);
				if (array[0].equals(idFld.getText())) personList.remove(i);
			}
		}
	};

	private class EnfOffGUI {

		JButton saveBtn, deleteBtn, createXMLBtn, saveAndAddBtn;
		JLabel enfOffIDLbl, enfOffRoleLbl, enfOffORILbl, enfOffUnitLbl, enfOffActivityLbl, enfOffAssignmentLbl;
		JTextField enfOffIDFld, enfOffORIFld, enfOffUnitFld;
		JPanel panel1, panel2, panel3, panel4, panel5;
		JComboBox<String> enfOffActivityCB, enfOffAssignmentCB, enfOffRolesCB;
		String[] enfOffArray = { "", "", "", "", "", "" };
		String[] activityCodes = { "Responding to Disturbance Call",
				"Burglaries in Progress or Pursuing Burglary Suspects",
				"Robberies in Progress or Pursuing Robbery Suspects", "Attempting Other Arrests",
				"Civil Disorder (Riot, Mass Disobedience)", "Handling, Transporting, Custody of Prisoners",
				"Investigating Suspicious Persons or Circumstances", "Ambush-No Warning",
				"Handling Persons with Mental Illness", "Traffic Pursuits and Stops", "All Other" };
		String[] assignmentCodes = { "Two-Officer Vehicle - uniformed law enforcement officers",
				"One-Officer Vehicle (Alone) - uniformed law enforcement officers",
				"One-Officer Vehicle (Assisted) - uniformed law enforcement officers",
				"Detective or Special Assignment (Alone) - non-uniformed officers",
				"Detective or Special Assignment (Assisted) - non-uniformed officers",
				"Other (Alone) - law enforcement officers serving in other capacities",
				"Other (Assisted) - law enforcement officers serving in other capacities" };
		String[] persons = new String[500];

		private void createFrame() {
			final JInternalFrame enfOffFrame = new JInternalFrame("Enforcement Official");
			enfOffFrame.setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
			enfOffFrame.setBounds(pos * numOpenFrames, pos * numOpenFrames, 550, 250);
			enfOffFrame.setVisible(true);

			if (!personAdded) {
				person.addDataToArrayList();
				personAdded = true;
			}

			personCnt = 0;

			personList.forEach((personArray) -> {
				persons[personCnt] = personArray[0];
				personCnt++;
			});

			enfOffRolesCB = new JComboBox<String>(persons);

			enfOffActivityCB = new JComboBox<String>(activityCodes);
			enfOffAssignmentCB = new JComboBox<String>(assignmentCodes);

			final JTextField finalEnfOffIdFld = new JTextField(8);
			enfOffORIFld = new JTextField(8);
			enfOffUnitFld = new JTextField(8);

			enfOffORIFld.setText(testORI);

			enfOffIDLbl = new JLabel("Enforcment Official ID:");
			enfOffRoleLbl = new JLabel("Role:");
			enfOffORILbl = new JLabel("ORI:(AA1234567)");
			enfOffUnitLbl = new JLabel("Enforcement Unit:");
			enfOffActivityLbl = new JLabel("Activity:");
			enfOffAssignmentLbl = new JLabel("Assignment:");

			panel1 = new JPanel();
			panel2 = new JPanel();
			panel3 = new JPanel();
			panel4 = new JPanel();
			panel5 = new JPanel();

			enfOffIDLbl.setLabelFor(finalEnfOffIdFld);
			enfOffRoleLbl.setLabelFor(enfOffRolesCB);
			enfOffORILbl.setLabelFor(enfOffORIFld);
			enfOffUnitLbl.setLabelFor(enfOffUnitFld);
			enfOffActivityLbl.setLabelFor(enfOffActivityCB);
			enfOffAssignmentLbl.setLabelFor(enfOffAssignmentCB);

			panel1.add(enfOffIDLbl);
			panel1.add(finalEnfOffIdFld);
			panel1.add(enfOffRoleLbl);
			panel1.add(enfOffRolesCB);

			panel2.add(enfOffORILbl);
			panel2.add(enfOffORIFld);
			panel2.add(enfOffUnitLbl);
			panel2.add(enfOffUnitFld);

			panel3.add(enfOffActivityLbl);
			panel3.add(enfOffActivityCB);

			panel4.add(enfOffAssignmentLbl);
			panel4.add(enfOffAssignmentCB);

			saveAndAddBtn = new JButton("Save and Add");
			saveBtn = new JButton("Save");
			deleteBtn = new JButton("Delete");
			createXMLBtn = new JButton("Create XML");

			createXMLBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					fileCreateGUI();
				}
			});
			deleteBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					//enfOffFrame.setVisible(false);
					//enfOffFrame.dispose();
					//deleteDataFromArrayList(finalEnfOffIdFld);
					enfOffFrame.setVisible(false);
					deleteDataFromArrayList(finalEnfOffIdFld);
					initFrame.remove(enfOffFrame);
					finalEnfOffIdFld.setText(null);
				}
			});
			saveAndAddBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					addDataToArrayList(finalEnfOffIdFld);
					enfOff.createFrame();
				}

			});
			saveBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					addDataToArrayList(finalEnfOffIdFld);
					enfOffAdded = true;
				}
			});

			panel5.add(createXMLBtn);
			panel5.add(deleteBtn);
			panel5.add(saveBtn);
			panel5.add(saveAndAddBtn);

			enfOffFrame.getContentPane().add(panel1);
			enfOffFrame.getContentPane().add(panel2);
			enfOffFrame.getContentPane().add(panel3);
			enfOffFrame.getContentPane().add(panel4);
			enfOffFrame.getContentPane().add(panel5);
			enfOffFrame.setLayout(new GridLayout(5, 1));

			dtPane.add(enfOffFrame);
			enfOffFrame.moveToFront();

			finalEnfOffIdFld.requestFocus();

			initFrame.setContentPane(dtPane);
			numOpenFrames++;
			if (numOpenFrames > 17)
				numOpenFrames = 0;

			enfOffIDFld = finalEnfOffIdFld;
		}

		private void addDataToArrayList() {
			if (enfOffIDFld != null && !enfOffIDFld.getText().isEmpty()) {
				enfOffArray = new String[6];
				enfOffArray[0] = enfOffIDFld.getText();
				enfOffArray[1] = enfOffRolesCB.getItemAt(enfOffRolesCB.getSelectedIndex());// Role
				enfOffArray[2] = enfOffActivityCB.getItemAt(enfOffActivityCB.getSelectedIndex());// Activity
				enfOffArray[3] = enfOffAssignmentCB.getItemAt(enfOffAssignmentCB.getSelectedIndex());// Assignment
				enfOffArray[4] = enfOffORIFld.getText();// ORI
				enfOffArray[5] = enfOffUnitFld.getText();// Enforcement Unit

				enfOffList.add(enfOffArray);
			}

		}

		private void addDataToArrayList(JTextField idField) {

			if (idField != null) {
				enfOffArray = new String[6];
				enfOffArray[0] = enfOffIDFld.getText();
				enfOffArray[1] = enfOffRolesCB.getItemAt(enfOffRolesCB.getSelectedIndex());// Role
				enfOffArray[2] = enfOffActivityCB.getItemAt(enfOffActivityCB.getSelectedIndex());// Activity
				enfOffArray[3] = enfOffAssignmentCB.getItemAt(enfOffAssignmentCB.getSelectedIndex());// Assignment
				enfOffArray[4] = enfOffORIFld.getText();// ORI
				enfOffArray[5] = enfOffUnitFld.getText();// Enforcement Unit

				enfOffList.add(enfOffArray);
			}

		}

		private void deleteDataFromArrayList(JTextField idFld) {
			for (int i=0; i<enfOffList.size(); i++){
				String[] array = enfOffList.get(i);
				if (array[0].equals(idFld.getText())) enfOffList.remove(i);
			}
		}
	};

	private class VictimGUI {

		JButton saveBtn, deleteBtn, createXMLBtn, saveAndAddBtn;
		JLabel vicIDLbl, vicRoleLbl, vicSeqNumLbl, vicInjuryLbl, vicCatCodeLbl, vicAggAssaultHomFactLbl1, vicAggAssaultHomFactLbl2,
				vicJustHomFactLbl;
		JTextField vicIDFld, vicSeqNumFld;
		JPanel panel1, panel2, panel3, panel4, panel5, panel6;
		JComboBox<String> vicInjuryCB, vicCatCodeCB, vicAggAssaultHomFactCB1, vicAggAssaultHomFactCB2, vicJustHomFactCB, vicRolesCB;
		String[] vicArray = {"", "", "", "", "", "", "", ""};
		String[] persons = new String[500];
		String[] injuryCodes = { "n/a", "Apparent Broken Bones", "Possible Internal Injury", "Severe Laceration",
				"Apparent Minor Injury", "None", "Other Major Injury", "Loss of Teeth", "Unconsciousness" };
		String[] vicCatCodes = { "Business", "Financial Institution", "Government", "Individual",
				"Law Enforcement Officer", "Other", "Religious Organization", "Society/Public", "Unknown" };
		String[] aggAssaultCodes = { "n/a", "Argument", "Assault on Law Enforcement Officer(s)", "Drug Dealing",
				"Gangland (Organized Crime Involvement)", "Juvenile Gang", "Lovers' Quarrel",
				"Mercy Killing (Not applicable to Aggravated Assault)", "Other Felony Involved", "Other Circumstances",
				"Unknown Circumstances", "Criminal Killed by Private Citizen", "Criminal Killed by Police Officer",
				"Child Playing With Weapon", "Gun-Cleaning Accident", "Hunting Accident",
				"Other Negligent Weapon Handling", "Other Negligent Killings" };
		String[] justHomCodes = { "n/a", "Criminal Attacked Police Officer and That Officer Killed Criminal",
				"Criminal Attacked Police Officer and Criminal Killed by Another Police Officer",
				"Criminal Attacked a Civilian", "Criminal Attempted Flight From a Crime",
				"Criminal Killed in Commission of a Crime", "Criminal Resisted Arrest",
				"Unable to Determine/Not Enough Information" };

		private void createFrame() {
			final JInternalFrame victimFrame = new JInternalFrame("Victim");
			victimFrame.setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
			victimFrame.setBounds(pos * numOpenFrames, pos * numOpenFrames, 650, 300);
			victimFrame.setVisible(true);

			if (!personAdded) {
				person.addDataToArrayList();
				personAdded = true;
			}

			personCnt = 0;

			personList.forEach((personArray) -> {
				persons[personCnt] = personArray[0];
				personCnt++;
			});

			vicRolesCB = new JComboBox<String>(persons);

			vicInjuryCB = new JComboBox<String>(injuryCodes);
			vicCatCodeCB = new JComboBox<String>(vicCatCodes);
			vicAggAssaultHomFactCB1 = new JComboBox<String>(aggAssaultCodes);
			vicAggAssaultHomFactCB2 = new JComboBox<String>(aggAssaultCodes);
			vicJustHomFactCB = new JComboBox<String>(justHomCodes);

			final JTextField finalVicIdFld = new JTextField(8);
			// vicRoleFld = new JTextField(8);
			vicSeqNumFld = new JTextField(8);

			vicIDLbl = new JLabel("Victim ID:");
			vicRoleLbl = new JLabel("Role:");
			vicSeqNumLbl = new JLabel("Sequence #:");
			vicInjuryLbl = new JLabel("Injury Code:");
			vicCatCodeLbl = new JLabel("Victim Category Code:");
			vicAggAssaultHomFactLbl1 = new JLabel("Agg Assault Homicide Factor 1");
			vicAggAssaultHomFactLbl2 = new JLabel("Agg Assault Homicide Factor 2");
			vicJustHomFactLbl = new JLabel("Justifiable Homicide Factor:");

			panel1 = new JPanel();
			panel2 = new JPanel();
			panel3 = new JPanel();
			panel4 = new JPanel();
			panel5 = new JPanel();
			panel6 = new JPanel();

			vicIDLbl.setLabelFor(finalVicIdFld);
			vicRoleLbl.setLabelFor(vicRolesCB);
			vicSeqNumLbl.setLabelFor(vicSeqNumFld);
			vicInjuryLbl.setLabelFor(vicInjuryCB);
			vicCatCodeLbl.setLabelFor(vicCatCodeCB);
			vicAggAssaultHomFactLbl1.setLabelFor(vicAggAssaultHomFactCB1);
			vicAggAssaultHomFactLbl2.setLabelFor(vicAggAssaultHomFactCB2);
			vicJustHomFactLbl.setLabelFor(vicJustHomFactCB);

			panel1.add(vicIDLbl);
			panel1.add(finalVicIdFld);
			panel1.add(vicRoleLbl);
			panel1.add(vicRolesCB);
			panel1.add(vicSeqNumLbl);
			panel1.add(vicSeqNumFld);

			panel2.add(vicInjuryLbl);
			panel2.add(vicInjuryCB);
			panel2.add(vicCatCodeLbl);
			panel2.add(vicCatCodeCB);
			vicInjuryLbl.setEnabled(false);
			vicInjuryCB.setEnabled(false);

			panel3.add(vicAggAssaultHomFactLbl1);
			panel3.add(vicAggAssaultHomFactCB1);
			vicAggAssaultHomFactLbl1.setEnabled(false);
			vicAggAssaultHomFactCB1.setEnabled(false);
			
			panel4.add(vicAggAssaultHomFactLbl2);
			panel4.add(vicAggAssaultHomFactCB2);
			vicAggAssaultHomFactLbl2.setEnabled(false);
			vicAggAssaultHomFactCB2.setEnabled(false);

			panel5.add(vicJustHomFactLbl);
			panel5.add(vicJustHomFactCB);
			vicJustHomFactLbl.setEnabled(false);
			vicJustHomFactCB.setEnabled(false);

			saveAndAddBtn = new JButton("Save and Add");
			saveBtn = new JButton("Save");
			deleteBtn = new JButton("Delete");
			createXMLBtn = new JButton("Create XML");

			createXMLBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					fileCreateGUI();
				}
			});
			deleteBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					//victimFrame.setVisible(false);
					//victimFrame.dispose();
					//deleteDataFromArrayList(finalVicIdFld);
					victimFrame.setVisible(false);
					deleteDataFromArrayList(finalVicIdFld);
					initFrame.remove(victimFrame);
					finalVicIdFld.setText(null);
				}
			});
			saveAndAddBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					addDataToArrayList(finalVicIdFld);
					vic.createFrame();
				}

			});
			saveBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					addDataToArrayList(finalVicIdFld);
					victimAdded = true;
				}

			});

			panel6.add(createXMLBtn);
			panel6.add(deleteBtn);
			panel6.add(saveBtn);
			panel6.add(saveAndAddBtn);

			victimFrame.getContentPane().add(panel1);
			victimFrame.getContentPane().add(panel2);
			victimFrame.getContentPane().add(panel3);
			victimFrame.getContentPane().add(panel4);
			victimFrame.getContentPane().add(panel5);
			victimFrame.getContentPane().add(panel6);
			victimFrame.setLayout(new GridLayout(6, 1));

			dtPane.add(victimFrame);
			victimFrame.moveToFront();

			finalVicIdFld.requestFocus();

			numOpenFrames++;
			if (numOpenFrames > 17)
				numOpenFrames = 0;

			vicIDFld = finalVicIdFld;
			vicFrame = victimFrame;
		}

		private void updateRoles() {
			personCnt = 0;

			personList.forEach((personArray) -> {
				persons[personCnt] = personArray[0];
				personCnt++;
			});

			vicRolesCB = new JComboBox<String>(persons);

			panel1.invalidate();
			panel1.removeAll();
			vicRoleLbl.setLabelFor(vicRolesCB);
			vicIDLbl.setLabelFor(vicIDFld);
			vicRoleLbl.setLabelFor(vicRolesCB);
			vicSeqNumLbl.setLabelFor(vicSeqNumFld);
			panel1.add(vicIDLbl);
			panel1.add(vicIDFld);
			panel1.add(vicRoleLbl);
			panel1.add(vicRolesCB);
			panel1.add(vicSeqNumLbl);
			panel1.add(vicSeqNumFld);
			panel1.revalidate();
			panel1.repaint();

		}

		private void enableInjury(boolean arg) {
			if (arg){
//				vicInjuryLbl.invalidate();
//				vicInjuryCB.invalidate();
				vicInjuryLbl.setEnabled(true);
				vicInjuryCB.setEnabled(true);
//				vicInjuryLbl.revalidate();
//				vicInjuryCB.revalidate();
//				vicInjuryLbl.repaint();
//				vicInjuryCB.repaint();
			}
			else {
//				vicInjuryLbl.invalidate();
//				vicInjuryCB.invalidate();
				vicInjuryLbl.setEnabled(false);
				vicInjuryCB.setEnabled(false);
//				vicInjuryLbl.revalidate();
//				vicInjuryCB.revalidate();
//				vicInjuryLbl.repaint();
//				vicInjuryCB.repaint();
			}
		}

		private void enableAggAssault(boolean arg) {
			if (arg){
//				panel3.invalidate();
//				panel3.invalidate();
//				panel3.removeAll();
//				panel3.removeAll();
//				panel3.add(vicAggAssaultHomFactLbl);
//				panel3.add(vicAggAssaultHomFactLbl);
//				panel3.add(vicAggAssaultHomFactCB);
//				panel3.add(vicAggAssaultHomFactCB);
				vicAggAssaultHomFactLbl1.setEnabled(true);
				vicAggAssaultHomFactLbl2.setEnabled(true);
				vicAggAssaultHomFactCB1.setEnabled(true);
				vicAggAssaultHomFactCB2.setEnabled(true);
//				panel3.revalidate();
//				panel3.revalidate();
//				panel3.repaint();
//				panel3.repaint();
			} else {
//				panel3.invalidate();
//				panel3.removeAll();
//				panel3.add(vicAggAssaultHomFactLbl);
//				panel3.add(vicAggAssaultHomFactCB);
				vicAggAssaultHomFactLbl1.setEnabled(false);
				vicAggAssaultHomFactLbl2.setEnabled(false);
				vicAggAssaultHomFactCB1.setEnabled(false);
				vicAggAssaultHomFactCB2.setEnabled(false);
//				panel3.revalidate();
//				panel3.repaint();
			}
		}

		private void enableJustHom(boolean arg) {
			if (arg) {
//				panel5.invalidate();
//				panel5.removeAll();
//				panel5.add(vicJustHomFactLbl);
//				panel5.add(vicJustHomFactCB);
				vicJustHomFactLbl.setEnabled(true);
				vicJustHomFactCB.setEnabled(true);
//				panel5.revalidate();
//				panel5.repaint();
			} else {
//				panel5.invalidate();
//				panel5.removeAll();
//				panel5.add(vicJustHomFactLbl);
//				panel5.add(vicJustHomFactCB);
				vicJustHomFactLbl.setEnabled(false);
				vicJustHomFactCB.setEnabled(false);
//				panel5.revalidate();
//				panel5.repaint();

			}
		}

		private void murderAggAssaultCodes() {
			String[] aggAssaultCodes = { "n/a", "Argument", "Assault on Law Enforcement Officer(s)", "Drug Dealing",
					"Gangland (Organized Crime Involvement)", "Juvenile Gang", "Lovers' Quarrel",
					"Mercy Killing (Not applicable to Aggravated Assault)", "Other Felony Involved",
					"Other Circumstances", "Unknown Circumstances"};

			panel2.invalidate();
			panel2.removeAll();
			panel2.add(vicInjuryLbl);
			panel2.add(vicInjuryCB);
			vicInjuryLbl.setEnabled(false);
			vicInjuryCB.setEnabled(false);
			panel2.add(vicCatCodeLbl);
			panel2.add(vicCatCodeCB);
			panel2.revalidate();
			panel2.repaint();

			panel3.invalidate();
			panel3.removeAll();
			panel4.invalidate();
			panel4.removeAll();
			vicAggAssaultHomFactCB1 = new JComboBox<String>(aggAssaultCodes);
			vicAggAssaultHomFactCB2 = new JComboBox<String>(aggAssaultCodes);
			vicAggAssaultHomFactLbl1.setLabelFor(vicAggAssaultHomFactCB1);
			vicAggAssaultHomFactLbl2.setLabelFor(vicAggAssaultHomFactCB2);
			vicAggAssaultHomFactLbl1.setEnabled(true);
			vicAggAssaultHomFactLbl2.setEnabled(true);
			vicAggAssaultHomFactCB1.setEnabled(true);
			vicAggAssaultHomFactCB2.setEnabled(true);
			panel3.add(vicAggAssaultHomFactLbl1);
			panel3.add(vicAggAssaultHomFactCB1);
			panel4.add(vicAggAssaultHomFactLbl2);
			panel4.add(vicAggAssaultHomFactCB2);
			panel3.revalidate();
			panel3.repaint();
			panel4.revalidate();
			panel4.repaint();

			panel5.invalidate();
			vicJustHomFactLbl.setEnabled(false);
			vicJustHomFactCB.setEnabled(false);
			panel5.revalidate();
			panel5.repaint();

		}

		private void negligentManslaughterCodes() {
			String[] negManslaughterCodes = { "Child Playing With Weapon", "Gun-Cleaning Accident", "Hunting Accident",
					"Other Negligent Weapon Handling", "Other Negligent Killings" };

			panel2.invalidate();
			panel2.removeAll();
			panel2.add(vicInjuryLbl);
			panel2.add(vicInjuryCB);
			vicInjuryLbl.setEnabled(false);
			vicInjuryCB.setEnabled(false);
			panel2.add(vicCatCodeLbl);
			panel2.add(vicCatCodeCB);
			panel2.revalidate();
			panel2.repaint();

			panel3.invalidate();
			panel3.removeAll();
			panel4.invalidate();
			panel4.removeAll();
			vicAggAssaultHomFactCB1 = new JComboBox<String>(negManslaughterCodes);
			vicAggAssaultHomFactCB2 = new JComboBox<String>(negManslaughterCodes);
			vicAggAssaultHomFactLbl1.setLabelFor(vicAggAssaultHomFactCB1);
			vicAggAssaultHomFactLbl2.setLabelFor(vicAggAssaultHomFactCB2);
			vicAggAssaultHomFactLbl1.setEnabled(true);
			vicAggAssaultHomFactCB1.setEnabled(true);
			vicAggAssaultHomFactLbl2.setEnabled(true);
			vicAggAssaultHomFactCB2.setEnabled(true);
			panel3.add(vicAggAssaultHomFactLbl1);
			panel4.add(vicAggAssaultHomFactLbl2);
			panel3.add(vicAggAssaultHomFactCB1);
			panel4.add(vicAggAssaultHomFactCB2);
			panel3.revalidate();
			panel3.repaint();
			panel4.revalidate();
			panel4.repaint();

			panel5.invalidate();
			vicJustHomFactLbl.setEnabled(false);
			vicJustHomFactCB.setEnabled(false);
			panel5.revalidate();
			panel5.repaint();

		}

		private void justHomCodes() {
			String[] justHomCodes = { "", "Criminal Attacked Police Officer and That Officer Killed Criminal",
					"Criminal Attacked Police Officer and Criminal Killed by Another Police Officer",
					"Criminal Attacked a Civilian", "Criminal Attempted Flight From a Crime",
					"Criminal Killed in Commission of a Crime", "Criminal Resisted Arrest",
					"Unable to Determine/Not Enough Information" };

			panel5.invalidate();
			panel5.removeAll();
			vicJustHomFactCB = new JComboBox<String>(justHomCodes);
			vicJustHomFactLbl.setLabelFor(vicJustHomFactCB);
			panel5.add(vicJustHomFactLbl);
			panel5.add(vicJustHomFactCB);
			vicJustHomFactLbl.setEnabled(false);
			vicJustHomFactCB.setEnabled(false);
			panel5.add(vicJustHomFactLbl);
			panel5.add(vicJustHomFactCB);
			panel5.revalidate();
			panel5.repaint();
		}

		private void embezzlementVictimCodes() {
			String[] vicCatCodes = { "Business", "Financial Institution", "Government", "Individual", "Other",
					"Religious Organization", "Society/Public" };

			panel2.invalidate();
			panel2.removeAll();
			vicCatCodeCB = new JComboBox<String>(vicCatCodes);
			vicCatCodeLbl.setLabelFor(vicCatCodeCB);
			panel2.add(vicInjuryLbl);
			panel2.add(vicInjuryCB);
			vicInjuryLbl.setEnabled(false);
			vicInjuryCB.setEnabled(false);
			panel2.add(vicCatCodeLbl);
			panel2.add(vicCatCodeCB);
			panel2.revalidate();
			panel2.repaint();

			panel3.invalidate();
			vicAggAssaultHomFactLbl1.setEnabled(false);
			vicAggAssaultHomFactCB2.setEnabled(false);
			vicAggAssaultHomFactLbl1.setEnabled(false);
			vicAggAssaultHomFactCB2.setEnabled(false);
			panel3.revalidate();
			panel3.repaint();

			panel5.invalidate();
			vicJustHomFactLbl.setEnabled(false);
			vicJustHomFactCB.setEnabled(false);
			panel5.revalidate();
			panel5.repaint();

		}

		private void murderVictimCodes() {
			String[] vicCatCodes = { "Individual", "Law Enforcement Officer" };

			panel2.invalidate();
			panel2.removeAll();
			vicCatCodeCB = new JComboBox<String>(vicCatCodes);
			vicCatCodeLbl.setLabelFor(vicCatCodeCB);
			panel2.add(vicInjuryLbl);
			panel2.add(vicInjuryCB);
			vicInjuryLbl.setEnabled(false);
			vicInjuryCB.setEnabled(false);
			panel2.add(vicCatCodeLbl);
			panel2.add(vicCatCodeCB);
			panel2.revalidate();
			panel2.repaint();
		}

		private void justHomVictimCodes() {
			String[] vicCatCodes = { "Individual" };

			panel2.invalidate();
			panel2.removeAll();
			vicCatCodeCB = new JComboBox<String>(vicCatCodes);
			vicCatCodeLbl.setLabelFor(vicCatCodeCB);
			panel2.add(vicInjuryLbl);
			panel2.add(vicInjuryCB);
			vicInjuryLbl.setEnabled(false);
			vicInjuryCB.setEnabled(false);
			panel2.add(vicCatCodeLbl);
			panel2.add(vicCatCodeCB);
			panel2.revalidate();
			panel2.repaint();
		}

		private void normalCodes() {
			String[] vicCatCodes = { "Business", "Financial Institution", "Government", "Individual",
					"Law Enforcement Officer", "Other", "Religious Organization", "Society/Public", "Unknown" };
			String[] aggAssaultCodes = { "n/a", "Argument", "Assault on Law Enforcement Officer(s)", "Drug Dealing",
					"Gangland (Organized Crime Involvement)", "Juvenile Gang", "Lovers' Quarrel",
					"Mercy Killing (Not applicable to Aggravated Assault)", "Other Felony Involved",
					"Other Circumstances", "Unknown Circumstances", "Criminal Killed by Private Citizen",
					"Criminal Killed by Police Officer", "Child Playing With Weapon", "Gun-Cleaning Accident",
					"Hunting Accident", "Other Negligent Weapon Handling", "Other Negligent Killings" };
			String[] justHomCodes = { "n/a", "Criminal Attacked Police Officer and That Officer Killed Criminal",
					"Criminal Attacked Police Officer and Criminal Killed by Another Police Officer",
					"Criminal Attacked a Civilian", "Criminal Attempted Flight From a Crime",
					"Criminal Killed in Commission of a Crime", "Criminal Resisted Arrest",
					"Unable to Determine/Not Enough Information" };

			panel2.invalidate();
			panel2.removeAll();
			vicCatCodeCB = new JComboBox<String>(vicCatCodes);
			vicCatCodeLbl.setLabelFor(vicCatCodeCB);
			panel2.add(vicInjuryLbl);
			panel2.add(vicInjuryCB);
			vicInjuryLbl.setEnabled(false);
			vicInjuryCB.setEnabled(false);
			panel2.add(vicCatCodeLbl);
			panel2.add(vicCatCodeCB);
			panel2.revalidate();
			panel2.repaint();

			panel3.invalidate();
			panel3.removeAll();
			panel4.invalidate();
			panel4.removeAll();
			vicAggAssaultHomFactCB1 = new JComboBox<String>(aggAssaultCodes);
			vicAggAssaultHomFactCB1 = new JComboBox<String>(aggAssaultCodes);
			vicAggAssaultHomFactLbl1.setLabelFor(vicAggAssaultHomFactCB1);
			vicAggAssaultHomFactLbl2.setLabelFor(vicAggAssaultHomFactCB2);
			vicAggAssaultHomFactLbl1.setEnabled(false);
			vicAggAssaultHomFactCB1.setEnabled(false);
			vicAggAssaultHomFactLbl2.setEnabled(false);
			vicAggAssaultHomFactCB2.setEnabled(false);
			panel3.add(vicAggAssaultHomFactLbl1);
			panel3.add(vicAggAssaultHomFactCB1);
			panel4.add(vicAggAssaultHomFactLbl2);
			panel4.add(vicAggAssaultHomFactCB2);
			panel3.revalidate();
			panel3.repaint();
			panel4.revalidate();
			panel4.repaint();

			panel5.invalidate();
			panel5.removeAll();
			vicJustHomFactCB = new JComboBox<String>(justHomCodes);
			vicJustHomFactLbl.setLabelFor(vicJustHomFactCB);
			vicJustHomFactLbl.setEnabled(true);
			vicJustHomFactCB.setEnabled(true);
			panel5.add(vicJustHomFactLbl);
			panel5.add(vicJustHomFactCB);
			panel5.revalidate();
			panel5.repaint();
		}

		private void addDataToArrayList() {
			if (vicIDFld != null && !vicIDFld.getText().isEmpty()) {
				vicArray = new String[8];
				vicArray[0] = vicIDFld.getText();
				vicArray[1] = vicRolesCB.getItemAt(vicRolesCB.getSelectedIndex());
				vicArray[2] = vicSeqNumFld.getText();
				vicArray[3] = vicInjuryCB.getItemAt(vicInjuryCB.getSelectedIndex());
				vicArray[4] = vicCatCodeCB.getItemAt(vicCatCodeCB.getSelectedIndex());
				vicArray[5] = vicAggAssaultHomFactCB1.getItemAt(vicAggAssaultHomFactCB1.getSelectedIndex());
				vicArray[6] = vicAggAssaultHomFactCB2.getItemAt(vicAggAssaultHomFactCB2.getSelectedIndex());
				vicArray[7] = vicJustHomFactCB.getItemAt(vicJustHomFactCB.getSelectedIndex());

				vicList.add(vicArray);
			}

		}

		private void addDataToArrayList(JTextField idField) {

			if (idField != null) {
				vicArray = new String[8];
				vicArray[0] = vicIDFld.getText();
				vicArray[1] = vicRolesCB.getItemAt(vicRolesCB.getSelectedIndex());
				vicArray[2] = vicSeqNumFld.getText();
				vicArray[3] = vicInjuryCB.getItemAt(vicInjuryCB.getSelectedIndex());
				vicArray[4] = vicCatCodeCB.getItemAt(vicCatCodeCB.getSelectedIndex());
				vicArray[5] = vicAggAssaultHomFactCB1.getItemAt(vicAggAssaultHomFactCB1.getSelectedIndex());
				vicArray[6] = vicAggAssaultHomFactCB2.getItemAt(vicAggAssaultHomFactCB2.getSelectedIndex());
				vicArray[7] = vicJustHomFactCB.getItemAt(vicJustHomFactCB.getSelectedIndex());

				vicList.add(vicArray);
			}

		}

		private void deleteDataFromArrayList(JTextField idFld) {
			for (int i=0; i<vicList.size(); i++){
				String[] array = vicList.get(i);
				if (array[0].equals(idFld.getText())) vicList.remove(i);
			}
		}
	};

	private class SubjectGUI {

		JButton saveBtn, deleteBtn, createXMLBtn, saveAndAddBtn;
		JLabel subIDLbl, subRoleLbl, subSeqNumLbl;
		JPanel panel1, panel2, panel3, panel4, panel5;
		JTextField subIDFld, subSeqNumFld;
		JComboBox<String> subjRolesCB;
		String[] subjectArray = { "", "", "", "", "", "", "" };
		String[] persons = new String[500];

		private void createFrame() {
			final JInternalFrame subFrame = new JInternalFrame("Subject");
			subFrame.setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
			subFrame.setBounds(pos * numOpenFrames, pos * numOpenFrames, 400, 250);
			subFrame.setVisible(true);

			if (!personAdded) {
				person.addDataToArrayList();
				personAdded = true;
			}

			personCnt = 0;

			personList.forEach((personArray) -> {
				persons[personCnt] = personArray[0];
				personCnt++;
			});

			subjRolesCB = new JComboBox<String>(persons);

			final JTextField finalSubjIdFld = new JTextField(8);
			subSeqNumFld = new JTextField(8);

			subIDLbl = new JLabel("Subject ID:");
			subRoleLbl = new JLabel("Role:");
			subSeqNumLbl = new JLabel("Sequence #:");

			panel1 = new JPanel();
			panel2 = new JPanel();
			panel3 = new JPanel();
			panel4 = new JPanel();
			panel5 = new JPanel();

			subIDLbl.setLabelFor(finalSubjIdFld);
			subRoleLbl.setLabelFor(subjRolesCB);
			subSeqNumLbl.setLabelFor(subSeqNumFld);

			panel1.add(subIDLbl);
			panel1.add(finalSubjIdFld);

			panel2.add(subRoleLbl);
			panel2.add(subjRolesCB);

			panel3.add(subSeqNumLbl);
			panel3.add(subSeqNumFld);

			saveAndAddBtn = new JButton("Save and Add");
			saveBtn = new JButton("Save");
			deleteBtn = new JButton("Delete");
			createXMLBtn = new JButton("Create XML");

			createXMLBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					fileCreateGUI();
				}
			});
			deleteBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					//subFrame.setVisible(false);
					//subFrame.dispose();
					//deleteDataFromArrayList(finalSubjIdFld);
					subFrame.setVisible(false);
					deleteDataFromArrayList(finalSubjIdFld);
					initFrame.remove(subFrame);
					finalSubjIdFld.setText(null);
				}
			});
			saveAndAddBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					addDataToArrayList(finalSubjIdFld);
					subj.createFrame();
				}

			});
			saveBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					addDataToArrayList(finalSubjIdFld);
					subjectAdded = true;
				}
			});

			panel5.add(createXMLBtn);
			panel5.add(deleteBtn);
			panel5.add(saveBtn);
			panel5.add(saveAndAddBtn);

			subFrame.getContentPane().add(panel1);
			subFrame.getContentPane().add(panel2);
			subFrame.getContentPane().add(panel3);
			subFrame.getContentPane().add(panel4);
			subFrame.getContentPane().add(panel5);
			subFrame.setLayout(new GridLayout(5, 1));

			dtPane.add(subFrame);
			subFrame.moveToFront();

			finalSubjIdFld.requestFocus();

			initFrame.setContentPane(dtPane);
			numOpenFrames++;
			if (numOpenFrames > 17)
				numOpenFrames = 0;

			subIDFld = finalSubjIdFld;
		}

		private void addDataToArrayList() {
			if (subIDFld != null && !subIDFld.getText().isEmpty()) {
				subjectArray = new String[7];
				subjectArray[0] = subIDFld.getText();// ID
				subjectArray[1] = subjRolesCB.getItemAt(subjRolesCB.getSelectedIndex());// Role
				subjectArray[2] = subSeqNumFld.getText();// Sequence Number

				subjectList.add(subjectArray);
			}

		}

		private void addDataToArrayList(JTextField idField) {

			if (idField != null) {
				subjectArray = new String[7];
				subjectArray[0] = subIDFld.getText();// ID
				subjectArray[1] = subjRolesCB.getItemAt(subjRolesCB.getSelectedIndex());// Role
				subjectArray[2] = subSeqNumFld.getText();// Sequence Number

				subjectList.add(subjectArray);
			}

		}

		private void deleteDataFromArrayList(JTextField idFld) {
			for (int i=0; i<subjectList.size(); i++){
				String[] array = subjectList.get(i);
				if (array[0].equals(idFld.getText())) subjectList.remove(i);
			}
		}
	};

	private class ArresteeGUI {

		JButton saveBtn, deleteBtn, createXMLBtn, saveAndAddBtn;
		JLabel arresteeSeqIDLbl, arresteeRoleLbl, arresteeArmedWithLbl1, arresteeArmedWithLbl2, arresteeJuvDispLbl, arresteeSubCountLbl;
		JPanel panel1, panel2, panel3, panel4, panel5, panel6;
		JTextField arresteeSeqIDFld;
		JCheckBox arresteeClrIndBOX;
		JComboBox<String> arresteeArmedWithCB1, arresteeArmedWithCB2, arresteeJuvDispCB, arresteeSubCountCB, arresteeRolesCB;
		String[] arresteeArray = { "", "", "", "", "", "" };
		String[] persons = new String[500];
		String[] armedWithCodes = { "n/a", "Unarmed", "Firearm (type not stated)", "Automatic Firearm (type not stated)",
				"Handgun", "Automatic Handgun", "Rifle", "Automatic Rifle", "Shotgun", "Automatic Shotgun",
				"Other Firearm", "Other Automatic Firearm", "Lethal Cutting Instument",
				"Club/Blackjack/Brass Knuckles" };
		String[] juvDispCodes = { "n/a", "Handled Within Department", "Referred to Other Authorities" };
		String[] subjectCountCodes = { "Count Arrestee", "Multiple", "Not Applicable" };

		private void createFrame() {
			final JInternalFrame arresteeFrame = new JInternalFrame("Arrestee");
			arresteeFrame.setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
			arresteeFrame.setBounds(pos * numOpenFrames, pos * numOpenFrames, 400, 300);
			arresteeFrame.setVisible(true);

			if (!personAdded) {
				person.addDataToArrayList();
				personAdded = true;
			}

			personCnt = 0;

			personList.forEach((personArray) -> {
				persons[personCnt] = personArray[0];
				personCnt++;
			});

			arresteeRolesCB = new JComboBox<String>(persons);

			arresteeArmedWithCB1 = new JComboBox<String>(armedWithCodes);
			arresteeArmedWithCB2 = new JComboBox<String>(armedWithCodes);
			arresteeJuvDispCB = new JComboBox<String>(juvDispCodes);
			arresteeSubCountCB = new JComboBox<String>(subjectCountCodes);

			final JTextField finalSeqIdFld = new JTextField(8);

			arresteeClrIndBOX = new JCheckBox("Clearance Indicator");

			arresteeSeqIDLbl = new JLabel("Sequence ID:");
			arresteeRoleLbl = new JLabel("Role:");
			arresteeArmedWithLbl1 = new JLabel("Armed With:");
			arresteeArmedWithLbl2 = new JLabel("Armed With:");
			arresteeJuvDispLbl = new JLabel("Juvenile Disposition:");
			arresteeSubCountLbl = new JLabel("Subject Count:");

			panel1 = new JPanel();
			panel2 = new JPanel();
			panel3 = new JPanel();
			panel4 = new JPanel();
			panel5 = new JPanel();
			panel6 = new JPanel();

			arresteeSeqIDLbl.setLabelFor(finalSeqIdFld);
			arresteeRoleLbl.setLabelFor(arresteeRolesCB);
			arresteeArmedWithLbl1.setLabelFor(arresteeArmedWithCB1);
			arresteeArmedWithLbl2.setLabelFor(arresteeArmedWithCB2);
			arresteeJuvDispLbl.setLabelFor(arresteeJuvDispCB);
			arresteeSubCountLbl.setLabelFor(arresteeSubCountCB);

			panel1.add(arresteeSeqIDLbl);
			panel1.add(finalSeqIdFld);
			panel1.add(arresteeRoleLbl);
			panel1.add(arresteeRolesCB);

			panel2.add(arresteeArmedWithLbl1);
			panel2.add(arresteeArmedWithCB1);

			panel3.add(arresteeArmedWithLbl1);
			panel3.add(arresteeArmedWithCB1);

			panel4.add(arresteeJuvDispLbl);
			panel4.add(arresteeJuvDispCB);

			panel5.add(arresteeSubCountLbl);
			panel5.add(arresteeSubCountCB);
			panel5.add(arresteeClrIndBOX);

			saveAndAddBtn = new JButton("Save and Add");
			saveBtn = new JButton("Save");
			deleteBtn = new JButton("Delete");
			createXMLBtn = new JButton("Create XML");

			createXMLBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					fileCreateGUI();
				}
			});
			deleteBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					//arresteeFrame.setVisible(false);
					//arresteeFrame.dispose();
					//deleteDataFromArrayList(finalSeqIdFld);
					arresteeFrame.setVisible(false);
					deleteDataFromArrayList(finalSeqIdFld);
					initFrame.remove(arresteeFrame);
					finalSeqIdFld.setText(null);
				}
			});
			saveAndAddBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					addDataToArrayList(finalSeqIdFld);
					arrestee.createFrame();
				}

			});
			saveBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					addDataToArrayList(finalSeqIdFld);
					arresteeAdded = true;
				}
			});

			panel6.add(createXMLBtn);
			panel6.add(deleteBtn);
			panel6.add(saveBtn);
			panel6.add(saveAndAddBtn);

			arresteeFrame.getContentPane().add(panel1);
			arresteeFrame.getContentPane().add(panel2);
			arresteeFrame.getContentPane().add(panel3);
			arresteeFrame.getContentPane().add(panel4);
			arresteeFrame.getContentPane().add(panel5);
			arresteeFrame.getContentPane().add(panel6);
			arresteeFrame.setLayout(new GridLayout(6, 1));

			dtPane.add(arresteeFrame);
			arresteeFrame.moveToFront();

			finalSeqIdFld.requestFocus();

			initFrame.setContentPane(dtPane);
			numOpenFrames++;
			if (numOpenFrames > 17)
				numOpenFrames = 0;

			arresteeSeqIDFld = finalSeqIdFld;
		}

		private void addDataToArrayList() {
			if (arresteeSeqIDFld != null && !arresteeSeqIDFld.getText().isEmpty()) {
				arresteeArray = new String[7];
				arresteeArray[0] = arresteeSeqIDFld.getText();// ID
				arresteeArray[1] = arresteeRolesCB.getItemAt(arresteeRolesCB.getSelectedIndex());

				if (arresteeClrIndBOX.isSelected())
					arresteeArray[2] = "true";
				else
					arresteeArray[2] = "false";

				arresteeArray[3] = arresteeArmedWithCB1.getItemAt(arresteeArmedWithCB1.getSelectedIndex());
				arresteeArray[4] = arresteeArmedWithCB2.getItemAt(arresteeArmedWithCB2.getSelectedIndex());
				arresteeArray[5] = arresteeJuvDispCB.getItemAt(arresteeJuvDispCB.getSelectedIndex());
				arresteeArray[6] = arresteeSubCountCB.getItemAt(arresteeSubCountCB.getSelectedIndex());

				arresteeList.add(arresteeArray);
			}

		}

		private void addDataToArrayList(JTextField idField) {

			if (idField != null) {
				arresteeArray = new String[7];
				arresteeArray[0] = arresteeSeqIDFld.getText();// ID
				arresteeArray[1] = arresteeRolesCB.getItemAt(arresteeRolesCB.getSelectedIndex());

				if (arresteeClrIndBOX.isSelected())
					arresteeArray[2] = "true";
				else
					arresteeArray[2] = "false";

				arresteeArray[3] = arresteeArmedWithCB1.getItemAt(arresteeArmedWithCB1.getSelectedIndex());
				arresteeArray[4] = arresteeArmedWithCB2.getItemAt(arresteeArmedWithCB2.getSelectedIndex());
				arresteeArray[5] = arresteeJuvDispCB.getItemAt(arresteeJuvDispCB.getSelectedIndex());
				arresteeArray[6] = arresteeSubCountCB.getItemAt(arresteeSubCountCB.getSelectedIndex());

				arresteeList.add(arresteeArray);
			}

		}

		private void deleteDataFromArrayList(JTextField idFld) {
			for (int i=0; i<arresteeList.size(); i++){
				String[] array = arresteeList.get(i);
				if (array[0].equals(idFld.getText())) arresteeList.remove(i);
			}
		}

	};

	private class ArrestGUI {

		JButton saveBtn, deleteBtn, createXMLBtn, saveAndAddBtn;
		JLabel arrestIDLbl, arrestDateLbl, arrestActivityLbl, arrestCategoryLbl, arrestChargeLbl;
		JPanel panel1, panel2, panel3, panel4, panel5;
		JTextField arrestIDFld, arrestActivityFld, arrestYearFld, arrestMonthFld, arrestDayFld, arrestHourFld,
				arrestMinFld, arrestSecFld;
		JComboBox<String> arrestCategoryCB, arrestChargeCB;
		String[] arrestArray = { "", "", "", "", "", "", "", "", "", "", "" };
		String[] categoryCodes = { "On-View Arrest", "Summoned/Cited", "Taken Into Custody" };
		String[] chargeCodesGroupA = { "Murder & Nonnegligent Manslaughter", "Negligent Manslaughter", "Justifiable Homicide",
				"Kidnapping/Abduction", "Rape", "Sodomy", "Sexual Assault With An Object", "Fondling", "Robbery",
				"Aggravated Assault", "Simple Assault", "Intimidation", "Arson", "Extortion/Blackmail",
				"Burglary/Breaking & Entering", "Pocket-picking", "Purse-snatching", "Shoplifting",
				"Theft From Building", "Theft From Coin-Operated Machine", "Theft From Motor Vehicle",
				"Theft of Motor Vehicle Parts", "All Other Larceny", "Motor Vehicle Theft", "Counterfeiting/Forgery",
				"False Pretenses/Swindle/Confidence Game", "Credit Card/Automated Teller Machine Fraud",
				"Impersonation", "Welfare Fraud", "Wire Fraud", "Identity Theft", "Hacking/Computer Invasion",
				"Embezzlement", "Stolen Offenses", "Destruction/Damage/Vandalism of Property",
				"Drug/Narcotic Violations", "Drug Equipment Violations", "Incest", "Statutory Rape",
				"Pornography/Obscene Material", "Betting/Wagering", "Operating/Promoting/Assisting Gambling",
				"Gambling Equipment Violation", "Sports Tampering", "Prostitution",
				"Assisting or Promoting Prostitution", "Purchasing Prostitution", "Bribery", "Weapon Law Violations",
				"Human Trafficking, Commercial Sex Acts", "Human Trafficking, Involuntary Servitude", "Animal Cruelty"};
		String[] chargeCodesGroupB = { "Bad Checks", "Curfew/Loitering/Vagrancy Violations", "Disorderly Conduct", 
				"Driving Under the Influence", "Drunkenness", "Family Offenses, Nonviolent", "Liquor Law Violations", "Peeping Tom", 
				"Trespass of Real Property", "All Other Offenses" };

		private void createFrame() {
			final JInternalFrame arrestFrame = new JInternalFrame("Arrest");
			arrestFrame.setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
			arrestFrame.setBounds(pos * numOpenFrames, pos * numOpenFrames, 450, 250);
			arrestFrame.setVisible(true);

			arrestActivityFld = new JTextField(5);
			arrestCategoryCB = new JComboBox<String>(categoryCodes);
			if (groupB)	arrestChargeCB = new JComboBox<String>(chargeCodesGroupB);
			else arrestChargeCB = new JComboBox<String>(chargeCodesGroupA);

			final JTextField finalArrestIDFld = new JTextField(8);
			arrestMonthFld = new JTextField(2);
			arrestDayFld = new JTextField(2);
			arrestYearFld = new JTextField(3);
			arrestHourFld = new JTextField(2);
			arrestMinFld = new JTextField(2);
			arrestSecFld = new JTextField(2);
			arrestActivityFld = new JTextField(5);

			arrestYearFld.setText(timeNow("YYYY"));
			arrestMonthFld.setText(timeNow("MM"));
			arrestDayFld.setText(timeNow("dd"));
			arrestHourFld.setText(timeNow("H"));
			arrestMinFld.setText(timeNow("mm"));
			arrestSecFld.setText(timeNow("ss"));

			arrestIDLbl = new JLabel("Arrest ID:");
			arrestDateLbl = new JLabel("Arrest Date mm/dd/yyyy:");
			arrestActivityLbl = new JLabel("Activity ID:");
			arrestCategoryLbl = new JLabel("Category:");
			arrestChargeLbl = new JLabel("Charge:");

			panel1 = new JPanel();
			panel2 = new JPanel();
			panel3 = new JPanel();
			panel4 = new JPanel();
			panel5 = new JPanel();

			arrestIDLbl.setLabelFor(finalArrestIDFld);
			arrestDateLbl.setLabelFor(arrestMonthFld);
			arrestActivityLbl.setLabelFor(arrestActivityFld);
			arrestCategoryLbl.setLabelFor(arrestCategoryCB);
			arrestChargeLbl.setLabelFor(arrestChargeCB);

			panel1.add(arrestIDLbl);
			panel1.add(finalArrestIDFld);
			panel1.add(arrestDateLbl);
			panel1.add(arrestMonthFld);
			panel1.add(arrestDayFld);
			panel1.add(arrestYearFld);

			panel2.add(arrestActivityLbl);
			panel2.add(arrestActivityFld);

			panel3.add(arrestCategoryLbl);
			panel3.add(arrestCategoryCB);

			panel4.add(arrestChargeLbl);
			panel4.add(arrestChargeCB);

			saveAndAddBtn = new JButton("Save and Add");
			saveBtn = new JButton("Save");
			deleteBtn = new JButton("Delete");
			createXMLBtn = new JButton("Create XML");

			createXMLBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					fileCreateGUI();
				}
			});
			deleteBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					//arrestFrame.setVisible(false);
					//arrestFrame.dispose();
					//deleteDataFromArrayList(finalArrestIDFld);
					arrestFrame.setVisible(false);
					deleteDataFromArrayList(finalArrestIDFld);
					initFrame.remove(arrestFrame);
					finalArrestIDFld.setText(null);
				}
			});
			saveAndAddBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					addDataToArrayList(finalArrestIDFld);
					arrest.createFrame();
				}

			});
			saveBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					addDataToArrayList(finalArrestIDFld);
					arrestAdded = true;
				}
			});

			panel5.add(createXMLBtn);
			panel5.add(deleteBtn);
			panel5.add(saveBtn);
			panel5.add(saveAndAddBtn);

			arrestFrame.getContentPane().add(panel1);
			arrestFrame.getContentPane().add(panel2);
			arrestFrame.getContentPane().add(panel3);
			arrestFrame.getContentPane().add(panel4);
			arrestFrame.getContentPane().add(panel5);
			arrestFrame.setLayout(new GridLayout(5, 1));

			dtPane.add(arrestFrame);
			arrestFrame.moveToFront();

			finalArrestIDFld.requestFocus();

			initFrame.setContentPane(dtPane);
			numOpenFrames++;
			if (numOpenFrames > 17)
				numOpenFrames = 0;

			arrestIDFld = finalArrestIDFld;
		}

		private void addDataToArrayList() {
			if (arrestIDFld != null && !arrestIDFld.getText().isEmpty()) {
				arrestArray = new String[10];
				arrestArray[0] = arrestIDFld.getText();
				arrestArray[1] = arrestYearFld.getText();
				arrestArray[2] = arrestMonthFld.getText();
				arrestArray[3] = arrestDayFld.getText();
				arrestArray[4] = arrestHourFld.getText();
				arrestArray[5] = arrestMinFld.getText();
				arrestArray[6] = arrestSecFld.getText();
				arrestArray[7] = arrestActivityFld.getText();
				arrestArray[8] = arrestCategoryCB.getItemAt(arrestCategoryCB.getSelectedIndex());
				arrestArray[9] = arrestChargeCB.getItemAt(arrestChargeCB.getSelectedIndex());

				arrestList.add(arrestArray);
			}
		}

		private void addDataToArrayList(JTextField idField) {

			if (idField != null) {
				arrestArray = new String[10];
				arrestArray[0] = arrestIDFld.getText();
				arrestArray[1] = arrestYearFld.getText();
				arrestArray[2] = arrestMonthFld.getText();
				arrestArray[3] = arrestDayFld.getText();
				arrestArray[4] = arrestHourFld.getText();
				arrestArray[5] = arrestMinFld.getText();
				arrestArray[6] = arrestSecFld.getText();
				arrestArray[7] = arrestActivityFld.getText();
				arrestArray[8] = arrestCategoryCB.getItemAt(arrestCategoryCB.getSelectedIndex());
				arrestArray[9] = arrestChargeCB.getItemAt(arrestChargeCB.getSelectedIndex());

				arrestList.add(arrestArray);
			}
		}

		private void deleteDataFromArrayList(JTextField idFld) {
			for (int i=0; i<arrestList.size(); i++){
				String[] array = arrestList.get(i);
				if (array[0].equals(idFld.getText())) arrestList.remove(i);
			}
		}

	};

	private class ArrestSubjectGUI {

		JButton saveBtn, deleteBtn, createXMLBtn, saveAndAddBtn;
		JLabel arrestLbl, subjectLbl;
		JPanel panel1, panel2, panel3, panel4, panel5;
		JComboBox<String> arrestsCB, actSubjSubjectsCB;
		String[] arrestSubjectArray = { "", "" };
		String[] arrests = new String[10];
		String[] subjects = new String[10];

		private void createFrame() {
			final JInternalFrame arrestSubjectFrame = new JInternalFrame("Arrest/Subject Association");
			arrestSubjectFrame.setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
			arrestSubjectFrame.setBounds(pos * numOpenFrames, pos * numOpenFrames, 450, 250);
			arrestSubjectFrame.setVisible(true);

			if (!arrestAdded) {
				arrest.addDataToArrayList();
				arrestAdded = true;
			}

			if (!subjectAdded) {
				subj.addDataToArrayList();
				subjectAdded = true;
			}

			arrestCnt = 0;
			subjectCnt = 0;

			arrestList.forEach((arrestArray) -> {
				arrests[arrestCnt] = arrestArray[0];
				arrestCnt++;
			});

			subjectList.forEach((subjectArray) -> {
				subjects[subjectCnt] = subjectArray[0];
				subjectCnt++;
			});
			arresteeList.forEach((arresteeArray) -> {
				subjects[subjectCnt] = arresteeArray[0];
				subjectCnt++;
			});

			final JComboBox<String> finalArrestsCB = new JComboBox<String>(arrests);
			final JComboBox<String> finalSubjectsCB = new JComboBox<String>(subjects);

			arrestLbl = new JLabel("Arrest:");
			subjectLbl = new JLabel("Subject:");

			panel1 = new JPanel();
			panel2 = new JPanel();
			panel3 = new JPanel();
			panel4 = new JPanel();
			panel5 = new JPanel();

			arrestLbl.setLabelFor(finalArrestsCB);
			subjectLbl.setLabelFor(finalSubjectsCB);

			panel1.add(arrestLbl);
			panel1.add(finalArrestsCB);
			panel1.add(subjectLbl);
			panel1.add(finalSubjectsCB);

			saveAndAddBtn = new JButton("Save and Add");
			saveBtn = new JButton("Save");
			deleteBtn = new JButton("Delete");
			createXMLBtn = new JButton("Create XML");

			createXMLBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					fileCreateGUI();
				}
			});
			deleteBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
//					arrestSubjectFrame.setVisible(false);
//					arrestSubjectFrame.dispose();
//					if (!arrestList.isEmpty()) {
//						deleteDataFromArrayList(finalArrestsCB.getItemAt(finalArrestsCB.getSelectedIndex()),
//								finalSubjectsCB.getItemAt(finalSubjectsCB.getSelectedIndex()));
//						index = 0;
//					}
					arrestSubjectFrame.setVisible(false);
					if (!arrestList.isEmpty()) {
						deleteDataFromArrayList(finalArrestsCB.getItemAt(finalArrestsCB.getSelectedIndex()),
								finalSubjectsCB.getItemAt(finalSubjectsCB.getSelectedIndex()));
					}
					initFrame.remove(arrestSubjectFrame);
					finalArrestsCB.setSelectedItem(null);
					finalSubjectsCB.setSelectedItem(null);
				}
			});
			saveAndAddBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					addDataToArrayList(finalArrestsCB, finalSubjectsCB);
					arrestSubject.createFrame();
				}

			});
			saveBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					addDataToArrayList(finalArrestsCB, finalSubjectsCB);
					actSubjAdded = true;
				}
			});

			panel5.add(createXMLBtn);
			panel5.add(deleteBtn);
			panel5.add(saveBtn);
			panel5.add(saveAndAddBtn);

			arrestSubjectFrame.getContentPane().add(panel1);
			arrestSubjectFrame.getContentPane().add(panel2);
			arrestSubjectFrame.getContentPane().add(panel3);
			arrestSubjectFrame.getContentPane().add(panel4);
			arrestSubjectFrame.getContentPane().add(panel5);
			arrestSubjectFrame.setLayout(new GridLayout(5, 1));

			dtPane.add(arrestSubjectFrame);
			panel1.repaint();
			arrestSubjectFrame.moveToFront();

			finalArrestsCB.requestFocus();

			numOpenFrames++;
			if (numOpenFrames > 17)
				numOpenFrames = 0;

			arrestsCB = finalArrestsCB;
			actSubjSubjectsCB = finalSubjectsCB;

		}

		private void addDataToArrayList() {
			if (arrestsCB != null) {
				arrestSubjectArray = new String[2];
				arrestSubjectArray[0] = arrestsCB.getItemAt(arrestsCB.getSelectedIndex());
				arrestSubjectArray[1] = actSubjSubjectsCB.getItemAt(actSubjSubjectsCB.getSelectedIndex());
				
				if (arrestSubjectArray[0] != null) arrestSubjectList.add(arrestSubjectArray);
			}
		}

		private void addDataToArrayList(JComboBox<String> arrestCB, JComboBox<String> subjectCB) {
			if (arrestCB != null) {
				arrestSubjectArray = new String[2];
				arrestSubjectArray[0] = arrestCB.getItemAt(arrestCB.getSelectedIndex());
				arrestSubjectArray[1] = subjectCB.getItemAt(subjectCB.getSelectedIndex());

				if (!arrestCB.getItemAt(arrestCB.getSelectedIndex()).isEmpty() && 
						!subjectCB.getItemAt(subjectCB.getSelectedIndex()).isEmpty()) 
					arrestSubjectList.add(arrestSubjectArray);
			}
		}

		private void deleteDataFromArrayList(String arrest, String subject) {
			for (int i=0; i<arrestSubjectList.size(); i++){
				String[] array = arrestSubjectList.get(i);
				if (array[0].equals(arrest) && array[1].equals(subject)) arrestSubjectList.remove(i);
			}
		}
	};

	private class OffenseLocationGUI {

		JButton saveBtn, deleteBtn, createXMLBtn, saveAndAddBtn;
		JLabel offenseLbl, locationLbl;
		JPanel panel1, panel2, panel3, panel4, panel5;
		JComboBox<String> offLocOffensesCB, offLocLocationsCB;
		String[] offenseLocationArray = { "", "" };
		String[] offenses = new String[500];
		String[] locations = new String[500];

		private void createFrame() {
			final JInternalFrame offenseLocationFrame = new JInternalFrame("Offense/Location Association");
			offenseLocationFrame.setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
			offenseLocationFrame.setBounds(pos * numOpenFrames, pos * numOpenFrames, 450, 250);
			offenseLocationFrame.setVisible(true);

			if (!offenseAdded) {
				offense.addDataToArrayList();
				offenseAdded = true;
			}

			if (!locationAdded) {
				location.addDataToArrayList();
				locationAdded = true;
			}

			offenseCnt = 0;
			locationCnt = 0;

			offenseList.forEach((offenseArray) -> {
				offenses[offenseCnt] = offenseArray[0];
				offenseCnt++;
			});

			locList.forEach((locArray) -> {
				locations[locationCnt] = locArray[0];
				locationCnt++;
			});

			final JComboBox<String> finalOffLocOffensesCB = new JComboBox<String>(offenses);
			final JComboBox<String> finalOffLocLocationsCB = new JComboBox<String>(locations);

			offenseLbl = new JLabel("Offense:");
			locationLbl = new JLabel("Location:");

			panel1 = new JPanel();
			panel2 = new JPanel();
			panel3 = new JPanel();
			panel4 = new JPanel();
			panel5 = new JPanel();

			offenseLbl.setLabelFor(finalOffLocOffensesCB);
			locationLbl.setLabelFor(finalOffLocLocationsCB);

			panel1.add(offenseLbl);
			panel1.add(finalOffLocOffensesCB);
			panel1.add(locationLbl);
			panel1.add(finalOffLocLocationsCB);

			saveAndAddBtn = new JButton("Save and Add");
			saveBtn = new JButton("Save");
			deleteBtn = new JButton("Delete");
			createXMLBtn = new JButton("Create XML");

			createXMLBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					fileCreateGUI();
				}
			});
			deleteBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					offenseLocationFrame.setVisible(false);
					deleteDataFromArrayList(finalOffLocOffensesCB.getItemAt(finalOffLocOffensesCB.getSelectedIndex()),
								finalOffLocLocationsCB.getItemAt(finalOffLocLocationsCB.getSelectedIndex()));
					initFrame.remove(offenseLocationFrame);
					finalOffLocOffensesCB.setSelectedItem(null);
					finalOffLocLocationsCB.setSelectedItem(null);
				}
			});
			saveAndAddBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					addDataToArrayList(finalOffLocOffensesCB, finalOffLocLocationsCB);
					offenseLocation.createFrame();
				}

			});
			saveBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					addDataToArrayList(finalOffLocOffensesCB, finalOffLocLocationsCB);
					offLocAdded = true;
				}
			});

			panel5.add(createXMLBtn);
			panel5.add(deleteBtn);
			panel5.add(saveBtn);
			panel5.add(saveAndAddBtn);

			offenseLocationFrame.getContentPane().add(panel1);
			offenseLocationFrame.getContentPane().add(panel2);
			offenseLocationFrame.getContentPane().add(panel3);
			offenseLocationFrame.getContentPane().add(panel4);
			offenseLocationFrame.getContentPane().add(panel5);
			offenseLocationFrame.setLayout(new GridLayout(5, 1));

			dtPane.add(offenseLocationFrame);
			offenseLocationFrame.moveToFront();

			finalOffLocOffensesCB.requestFocus();

			numOpenFrames++;
			if (numOpenFrames > 17)
				numOpenFrames = 0;

			offLocOffensesCB = finalOffLocOffensesCB;
			offLocLocationsCB = finalOffLocLocationsCB;

		}

		private void addDataToArrayList() {
			if (offLocOffensesCB != null) {
				offenseLocationArray = new String[2];
				offenseLocationArray[0] = offLocOffensesCB.getItemAt(offLocOffensesCB.getSelectedIndex());
				offenseLocationArray[1] = offLocLocationsCB.getItemAt(offLocLocationsCB.getSelectedIndex());

				if (offenseLocationArray[0] != null) offenseLocationList.add(offenseLocationArray);
			}
		}

		private void addDataToArrayList(JComboBox<String> offenseCB, JComboBox<String> locationCB) {

			if (offenseCB != null) {
				offenseLocationArray = new String[2];
				offenseLocationArray[0] = offLocOffensesCB.getItemAt(offLocOffensesCB.getSelectedIndex());
				offenseLocationArray[1] = offLocLocationsCB.getItemAt(offLocLocationsCB.getSelectedIndex());

				offenseLocationList.add(offenseLocationArray);
			}
		}

		private void deleteDataFromArrayList(String offense, String location) {
			for (int i=0; i<offenseLocationList.size(); i++){
				String[] array = offenseLocationList.get(i);
				if (array[0].equals(offense) && array[1].equals(location)) offenseLocationList.remove(i);
			}
		}
	};

	private class OffenseVictimGUI {

		JButton saveBtn, deleteBtn, createXMLBtn, saveAndAddBtn;
		JLabel offenseLbl, victimLbl;
		JPanel panel1, panel2, panel3, panel4, panel5;
		JComboBox<String> offVicOffensesCB, offVicVictimsCB;
		String[] offenseVictimArray = { "", "" };
		String[] offenses = new String[500];
		String[] victims = new String[500];

		private void createFrame() {
			final JInternalFrame offenseVictimFrame = new JInternalFrame("Offense/Victim Association");
			offenseVictimFrame.setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
			offenseVictimFrame.setBounds(pos * numOpenFrames, pos * numOpenFrames, 450, 250);
			offenseVictimFrame.setVisible(true);

			if (!offenseAdded) {
				offense.addDataToArrayList();
				offenseAdded = true;
			}

			if (!victimAdded) {
				vic.addDataToArrayList();
				victimAdded = true;
			}

			offenseCnt = 0;
			victimCnt = 0;

			offenseList.forEach((offenseArray) -> {
				offenses[offenseCnt] = offenseArray[0];
				offenseCnt++;
			});

			vicList.forEach((vicArray) -> {
				victims[victimCnt] = vicArray[0];
				victimCnt++;
			});

			final JComboBox<String> finalOffVicOffensesCB = new JComboBox<String>(offenses);
			final JComboBox<String> finalOffVicVictimsCB = new JComboBox<String>(victims);

			offenseLbl = new JLabel("Offense:");
			victimLbl = new JLabel("Victim:");

			panel1 = new JPanel();
			panel2 = new JPanel();
			panel3 = new JPanel();
			panel4 = new JPanel();
			panel5 = new JPanel();

			offenseLbl.setLabelFor(finalOffVicOffensesCB);
			victimLbl.setLabelFor(finalOffVicVictimsCB);

			panel1.add(offenseLbl);
			panel1.add(finalOffVicOffensesCB);
			panel1.add(victimLbl);
			panel1.add(finalOffVicVictimsCB);

			saveAndAddBtn = new JButton("Save and Add");
			saveBtn = new JButton("Save");
			deleteBtn = new JButton("Delete");
			createXMLBtn = new JButton("Create XML");

			createXMLBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					fileCreateGUI();
				}
			});
			deleteBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					offenseVictimFrame.setVisible(false);
					deleteDataFromArrayList(finalOffVicOffensesCB.getItemAt(finalOffVicOffensesCB.getSelectedIndex()),
							finalOffVicVictimsCB.getItemAt(finalOffVicVictimsCB.getSelectedIndex()));
					initFrame.remove(offenseVictimFrame);
					finalOffVicOffensesCB.setSelectedItem(null);
					finalOffVicVictimsCB.setSelectedItem(null);
				}
			});
			saveAndAddBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					addDataToArrayList(finalOffVicOffensesCB, finalOffVicVictimsCB);
					offenseVictim.createFrame();
				}

			});
			saveBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					addDataToArrayList(finalOffVicOffensesCB, finalOffVicVictimsCB);
					offVicAdded = true;
				}
			});

			panel5.add(createXMLBtn);
			panel5.add(deleteBtn);
			panel5.add(saveBtn);
			panel5.add(saveAndAddBtn);

			offenseVictimFrame.getContentPane().add(panel1);
			offenseVictimFrame.getContentPane().add(panel2);
			offenseVictimFrame.getContentPane().add(panel3);
			offenseVictimFrame.getContentPane().add(panel4);
			offenseVictimFrame.getContentPane().add(panel5);
			offenseVictimFrame.setLayout(new GridLayout(5, 1));

			dtPane.add(offenseVictimFrame);
			offenseVictimFrame.moveToFront();

			finalOffVicOffensesCB.requestFocus();

			numOpenFrames++;
			if (numOpenFrames > 17)
				numOpenFrames = 0;

			offVicOffensesCB = finalOffVicOffensesCB;
			offVicVictimsCB = finalOffVicVictimsCB;
		}

		private void addDataToArrayList() {
			if (offVicOffensesCB != null) {
				offenseVictimArray = new String[2];
				offenseVictimArray[0] = offVicOffensesCB.getItemAt(offVicOffensesCB.getSelectedIndex());
				offenseVictimArray[1] = offVicVictimsCB.getItemAt(offVicVictimsCB.getSelectedIndex());

				if (offenseVictimArray[0] != null) offenseVictimList.add(offenseVictimArray);
			}
		}

		private void addDataToArrayList(JComboBox<String> offenseCB, JComboBox<String> victimCB) {

			if (offenseCB != null) {
				offenseVictimArray = new String[2];
				offenseVictimArray[0] = offenseCB.getItemAt(offenseCB.getSelectedIndex());
				offenseVictimArray[1] = victimCB.getItemAt(victimCB.getSelectedIndex());

				offenseVictimList.add(offenseVictimArray);
			}
		}

		private void deleteDataFromArrayList(String offense, String victim) {
			for (int i=0; i<offenseVictimList.size(); i++){
				String[] array = offenseVictimList.get(i);
				if (array[0].equals(offense) && array[1].equals(victim)) offenseVictimList.remove(i);
			}
//
//			offenseVictimList.forEach((offenseVictimArray) -> {
//				if (offenseVictimArray[0].equals(offense) && offenseVictimArray[0].equals(victim)) {
//					indexToDelete = index;
//				}
//				index++;
//			});
//			offenseVictimList.remove(indexToDelete);
		}
	};

	private class SubjectVictimGUI {

		JButton saveBtn, deleteBtn, createXMLBtn, saveAndAddBtn;
		JLabel subjectLbl, victimLbl, relationshipLbl;
		JPanel panel1, panel2, panel3, panel4, panel5;
		JComboBox<String> subjVicSubjectsCB, subjVicVictimsCB, subVicRelationshipCB;
		String[] subjectVictimArray = { "", "", "" };
		String[] subjects = new String[500];
		String[] victims = new String[500];
		String[] relationshipCodes = { "Acquaintance", "Babysittee", "Boyfriend_Girlfriend",
				"Child of Boyfriend_Girlfriend", "Family Member_Child", "Family Member_Spouse_Common Law", "Employee",
				"Employer", "Friend", "Family Member_Grandchild", "Family Member_Grandparent", "Family Member_In-Law",
				"Neighbor", "Family Member", "NonFamily_Otherwise Known", "Family Member_Parent",
				"Relationship Unknown", "Family Member_Sibling", "Family Member_Stepchild", "Family Member_Spouse",
				"Family Member_Stepparent", "Family Member_Stepsibling", "Stranger", "Victim Was Offender",
				"Ex_Spouse" };

		private void createFrame() {
			final JInternalFrame subjectVictimFrame = new JInternalFrame("Subject/Victim Association");
			subjectVictimFrame.setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
			subjectVictimFrame.setBounds(pos * numOpenFrames, pos * numOpenFrames, 450, 250);
			subjectVictimFrame.setVisible(true);

			if (!subjectAdded) {
				subj.addDataToArrayList();
				subjectAdded = true;
			}

			if (!victimAdded) {
				vic.addDataToArrayList();
				victimAdded = true;
			}

			subjectCnt = 0;
			victimCnt = 0;

			subjectList.forEach((subjectArray) -> {
				subjects[subjectCnt] = subjectArray[0];
				subjectCnt++;
			});

			vicList.forEach((vicArray) -> {
				victims[victimCnt] = vicArray[0];
				victimCnt++;
			});

			final JComboBox<String> finalSubjVicSubjectsCB = new JComboBox<String>(subjects);
			final JComboBox<String> finalSubjVicVictimsCB = new JComboBox<String>(victims);
			subVicRelationshipCB = new JComboBox<String>(relationshipCodes);

			subjectLbl = new JLabel("Offense:");
			victimLbl = new JLabel("Victim:");
			relationshipLbl = new JLabel("Subject to Victim Relationship:");

			panel1 = new JPanel();
			panel2 = new JPanel();
			panel3 = new JPanel();
			panel4 = new JPanel();
			panel5 = new JPanel();

			subjectLbl.setLabelFor(finalSubjVicSubjectsCB);
			victimLbl.setLabelFor(finalSubjVicVictimsCB);
			relationshipLbl.setLabelFor(subVicRelationshipCB);

			panel1.add(subjectLbl);
			panel1.add(finalSubjVicSubjectsCB);
			panel1.add(victimLbl);
			panel1.add(finalSubjVicVictimsCB);

			panel2.add(relationshipLbl);
			panel2.add(subVicRelationshipCB);

			saveAndAddBtn = new JButton("Save and Add");
			saveBtn = new JButton("Save");
			deleteBtn = new JButton("Delete");
			createXMLBtn = new JButton("Create XML");

			createXMLBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					fileCreateGUI();
				}
			});
			deleteBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					subjectVictimFrame.setVisible(false);
					deleteDataFromArrayList(finalSubjVicSubjectsCB.getItemAt(finalSubjVicSubjectsCB.getSelectedIndex()),
							finalSubjVicVictimsCB.getItemAt(finalSubjVicVictimsCB.getSelectedIndex()));
					initFrame.remove(subjectVictimFrame);
					finalSubjVicSubjectsCB.setSelectedItem(null);
					finalSubjVicVictimsCB.setSelectedItem(null);
				}
			});
			saveAndAddBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					addDataToArrayList(finalSubjVicSubjectsCB, finalSubjVicVictimsCB);
					subjectVictim.createFrame();
				}

			});
			saveBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					addDataToArrayList(finalSubjVicSubjectsCB, finalSubjVicVictimsCB);
					subjVicAdded = true;
				}
			});

			panel5.add(createXMLBtn);
			panel5.add(deleteBtn);
			panel5.add(saveBtn);
			panel5.add(saveAndAddBtn);

			subjectVictimFrame.getContentPane().add(panel1);
			subjectVictimFrame.getContentPane().add(panel2);
			subjectVictimFrame.getContentPane().add(panel3);
			subjectVictimFrame.getContentPane().add(panel4);
			subjectVictimFrame.getContentPane().add(panel5);
			subjectVictimFrame.setLayout(new GridLayout(5, 1));

			dtPane.add(subjectVictimFrame);
			subjectVictimFrame.moveToFront();

			finalSubjVicSubjectsCB.requestFocus();

			numOpenFrames++;
			if (numOpenFrames > 17)
				numOpenFrames = 0;

			subjVicSubjectsCB = finalSubjVicSubjectsCB;
			subjVicVictimsCB = finalSubjVicVictimsCB;
		}

		private void addDataToArrayList() {
			if (subjVicSubjectsCB != null) {
				subjectVictimArray = new String[3];
				subjectVictimArray[0] = subjVicSubjectsCB.getItemAt(subjVicSubjectsCB.getSelectedIndex());
				subjectVictimArray[1] = subjVicVictimsCB.getItemAt(subjVicVictimsCB.getSelectedIndex());
				subjectVictimArray[2] = subVicRelationshipCB.getItemAt(subVicRelationshipCB.getSelectedIndex());

				if (subjectVictimArray[0] != null) subjectVictimList.add(subjectVictimArray);
			}
		}

		private void addDataToArrayList(JComboBox<String> subjectCB, JComboBox<String> victimCB) {
			if (subjectCB != null) {
				subjectVictimArray = new String[3];
				subjectVictimArray[0] = subjVicSubjectsCB.getItemAt(subjVicSubjectsCB.getSelectedIndex());
				subjectVictimArray[1] = subjVicVictimsCB.getItemAt(subjVicVictimsCB.getSelectedIndex());
				subjectVictimArray[2] = subVicRelationshipCB.getItemAt(subVicRelationshipCB.getSelectedIndex());

				subjectVictimList.add(subjectVictimArray);
			}
		}

		private void deleteDataFromArrayList(String subject, String victim) {
			for (int i=0; i<subjectVictimList.size(); i++){
				String[] array = subjectVictimList.get(i);
				if (array[0].equals(subject) && array[1].equals(victim)) subjectVictimList.remove(i);
			}
		}
	};

	private void fileCreateGUI() {
		// JFrame frame = new JFrame();
		fileCreatedFrame = new JInternalFrame("XML File Created");
		fileCreatedFrame.setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
		fileCreatedFrame.setBounds(400, 400, 250, 175);
		fileCreatedFrame.setVisible(true);
		// rhFrame.setResizable(true);

		JButton closeBtn, createAnotherBtn;
		JLabel fileCreatedLbl;

		JPanel panel1, panel2;

		closeBtn = new JButton("Save and Close Window");
		createAnotherBtn = new JButton("Save and Return to Window");

		fileCreatedLbl = new JLabel("Save XML File");

		panel1 = new JPanel();
		panel2 = new JPanel();

		panel1.add(fileCreatedLbl);

		panel2.add(closeBtn);
		panel2.add(createAnotherBtn);

		fileCreatedFrame.getContentPane().add(panel1);
		fileCreatedFrame.getContentPane().add(panel2);
		fileCreatedFrame.setLayout(new GridLayout(2, 1));

		dtPane.add(fileCreatedFrame);
		fileCreatedFrame.moveToFront();

		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				makeXMLFile();
				initFrame.setVisible(false);
			}
		});
		createAnotherBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				makeXMLFile();
				initFrame.setVisible(false);
				numOpenFrames = 0;
				sf.createFrame();
			}
		});
		fileCreatedLbl.requestFocus();

		initFrame.setContentPane(dtPane);
		numOpenFrames++;
	}

	private void parseXML(File inputFile) {

		NibrsSubmission ns = new NibrsSubmission();
		ns.unmarshallSubmission(inputFile);

	}

	private void openFileChooserDialog() {

		JFileChooser fileOpen = new JFileChooser(workingFileDir);
		FileFilter filter = new FileNameExtensionFilter("XML Files", "xml");
		fileOpen.addChoosableFileFilter(filter);

		int ret = fileOpen.showDialog(null, "Open file");

		if (ret == JFileChooser.APPROVE_OPTION) {
			File file = fileOpen.getSelectedFile();
			workingFileDir = fileOpen.getCurrentDirectory();
			parseXML(file);
		}
	}

	private String saveXMLFile() {
		JFileChooser fileChooser = new JFileChooser(workingFileDir);
		fileChooser.setDialogTitle("Specify a file to save");
		FileFilter filter = new FileNameExtensionFilter("XML Files", "xml");
		fileChooser.addChoosableFileFilter(filter);
		File fileToSave = null;

		int userSelection = fileChooser.showDialog(null, "Save File");
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			fileToSave = fileChooser.getSelectedFile();
			try {
				if (fileToSave.createNewFile())
					workingFileDir = fileChooser.getCurrentDirectory();
			} catch (IOException e) {
				System.out.println("Can't create file");
				e.printStackTrace();
			}
		}
		return fileToSave.getAbsolutePath();
	}

	private void makeXMLFile() {
		String outputFile = saveXMLFile();
		mmd.addDataToArrayList();
		incident.addDataToArrayList();
		if (!offenseAdded)
			offense.addDataToArrayList();
		if (!locationAdded)
			location.addDataToArrayList();
		if (!itemAdded)
			item.addDataToArrayList();
		if (!substanceAdded)
			subs.addDataToArrayList();
		if (!personAdded)
			person.addDataToArrayList();
		if (!enfOffAdded)
			enfOff.addDataToArrayList();
		if (!victimAdded)
			vic.addDataToArrayList();
		if (!subjectAdded)
			subj.addDataToArrayList();
		if (!arresteeAdded)
			arrestee.addDataToArrayList();
		if (!arrestAdded)
			arrest.addDataToArrayList();
		if (!actSubjAdded)
			arrestSubject.addDataToArrayList();
		if (!offLocAdded)
			offenseLocation.addDataToArrayList();
		if (!offVicAdded)
			offenseVictim.addDataToArrayList();
		if (!subjVicAdded)
			subjectVictim.addDataToArrayList();

		try {
			gui.marshallSubmission(outputFile, mmdArray, rhArray, incArray, offenseList, locList, itemList,
					substanceList, personList, enfOffList, vicList, subjectList, arresteeList, arrestList,
					arrestSubjectList, offenseLocationList, offenseVictimList, subjectVictimList);
		} catch (JAXBException e) {
			System.out.println("Trouble Making XML File");
			e.printStackTrace();
		}
	}

	private String timeNow(String dateFormat) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(cal.getTime());

	}
};
