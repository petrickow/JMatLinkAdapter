package no.uio.taco.pukaMatControl.puka;

/*
 * frmLoadData.java
 *
 * Created on September 5, 2002, 10:42 AM
 */

/**
 * this form must be called before any analysis to load the raw data into matlab and set variables
 * @author  Joset A. Etzel
*/

import java.awt.*; import java.awt.event.*; import java.awt.datatransfer.*; import java.io.*;
import javax.swing.filechooser.*;

import matlabcontrol.MatlabInvocationException;

import javax.swing.*; import java.sql.*;
import java.util.StringTokenizer; import java.util.ArrayList;
import java.io.File;
import javax.swing.event.InternalFrameEvent; import java.util.zip.*; import java.util.Enumeration;
import java.util.Vector;

import no.uio.taco.pukaMatControl.matControlAdapter.*;

/** this form must be called before any analysis to load the raw data into matlab and set variables
 */
public class frmLoadData extends javax.swing.JInternalFrame implements javax.swing.event.InternalFrameListener {
  /** engMatLab is used by all classes to connect to matlab via JMatLink  */
  public static JMatLinkAdapter engMatLab; private static String strClipName = "";
  private Connection conData; private Statement stmSQL;
	private static boolean bolIsZip = false; private static String strPath = "";
	private static String strFile = ""; private static String strDataFile = "";

  private static int intStartTime = 0;  /** time point at which the stimulus started */
  private static double dblTotalTime = 0;   /** total time of the stimulus, as number of measurements. */
	private static int intStopTime = 0; private static boolean bolLoadedFromDB = false;
	private static boolean bolLoadedFromEduardoDB = false;

  /** Creates new form frmLoadData */
    public frmLoadData() {

			initComponents();
			FillBoxes();
			setBounds(20, 10, 750, 350);
			setFrameIcon(new ImageIcon(frmPreferences.getInstallPath() + "iconSmall.gif"));


			engMatLab = new JMatLinkAdapter();  //initiate connection
			engMatLab.engOpen();
			/*
			try {
		       System.load("JMatlink.dll");
		       engMatLab.setDebug( true );
				int intC = engMatLab.engOpen();  //open connection to MATLAB
	      	} catch (Exception e) { e.printStackTrace(); }
			*/
    }

		public void FillBoxes() {
			//refresh information that needs to go on the screen
			int intC = 0; Vector jcList;

			if ((frmMain.getDBName()).equals("NONE")) { cmdChooseDB.setEnabled(false); }

			jcList = frmPreferences.getClipNames();
			cboClip.removeAllItems();
			for (intC = 0; intC < jcList.size(); intC++) { cboClip.addItem(jcList.get(intC)); }
		}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
  private void initComponents() {//GEN-BEGIN:initComponents
    java.awt.GridBagConstraints gridBagConstraints;

    buttonGroup1 = new javax.swing.ButtonGroup();
    cmdLoad = new javax.swing.JButton();
    txtPath = new javax.swing.JTextField();
    lblFileName = new javax.swing.JLabel();
    lblPath = new javax.swing.JLabel();
    lblClip = new javax.swing.JLabel();
    cmdSelectFile = new javax.swing.JButton();
    cboClip = new javax.swing.JComboBox();
    rdoRawText = new javax.swing.JRadioButton();
    rdoZip = new javax.swing.JRadioButton();
    cmdChooseDB = new javax.swing.JButton();
    cboDataFile = new javax.swing.JComboBox();
    cmdShowFiles = new javax.swing.JButton();
    lblOnset = new javax.swing.JLabel();
    txtOnset = new javax.swing.JTextField();
    cmdOnset = new javax.swing.JButton();

    getContentPane().setLayout(new java.awt.GridBagLayout());

    setClosable(true);
    setIconifiable(true);
    setMaximizable(true);
    setResizable(true);
    setTitle("Load File");
    setMinimumSize(new java.awt.Dimension(355, 100));
    setPreferredSize(new java.awt.Dimension(355, 100));
    cmdLoad.setMnemonic('L');
    cmdLoad.setText("Load File");
    cmdLoad.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmdLoadActionPerformed(evt);
      }
    });

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 6;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
    gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
    getContentPane().add(cmdLoad, gridBagConstraints);

    txtPath.setMaximumSize(new java.awt.Dimension(500, 20));
    txtPath.setMinimumSize(new java.awt.Dimension(250, 20));
    txtPath.setPreferredSize(new java.awt.Dimension(500, 20));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
    getContentPane().add(txtPath, gridBagConstraints);

    lblFileName.setText("data file name:");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 10);
    getContentPane().add(lblFileName, gridBagConstraints);

    lblPath.setText("archive or raw file path:");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 10);
    getContentPane().add(lblPath, gridBagConstraints);

    lblClip.setText("indicate clip:");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 10);
    getContentPane().add(lblClip, gridBagConstraints);

    cmdSelectFile.setMnemonic('S');
    cmdSelectFile.setText("Select File");
    cmdSelectFile.setToolTipText("not yet");
    cmdSelectFile.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmdSelectFileActionPerformed(evt);
      }
    });

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
    getContentPane().add(cmdSelectFile, gridBagConstraints);

    cboClip.setMaximumSize(new java.awt.Dimension(400, 25));
    cboClip.setMinimumSize(new java.awt.Dimension(150, 20));
    cboClip.setPreferredSize(new java.awt.Dimension(200, 20));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
    getContentPane().add(cboClip, gridBagConstraints);

    rdoRawText.setMnemonic('r');
    rdoRawText.setSelected(true);
    rdoRawText.setText("single raw text file");
    buttonGroup1.add(rdoRawText);
    rdoRawText.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        rdoRawTextActionPerformed(evt);
      }
    });

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
    getContentPane().add(rdoRawText, gridBagConstraints);

    rdoZip.setMnemonic('z');
    rdoZip.setText("file within zipped archive");
    buttonGroup1.add(rdoZip);
    rdoZip.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        rdoZipActionPerformed(evt);
      }
    });

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
    getContentPane().add(rdoZip, gridBagConstraints);

    cmdChooseDB.setMnemonic('h');
    cmdChooseDB.setText("Choose from DB");
    cmdChooseDB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmdChooseDBActionPerformed(evt);
      }
    });

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
    getContentPane().add(cmdChooseDB, gridBagConstraints);

    cboDataFile.setPreferredSize(new java.awt.Dimension(500, 20));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
    getContentPane().add(cboDataFile, gridBagConstraints);

    cmdShowFiles.setMnemonic('z');
    cmdShowFiles.setText("Show files within zip");
    cmdShowFiles.setMinimumSize(new java.awt.Dimension(149, 24));
    cmdShowFiles.setPreferredSize(new java.awt.Dimension(149, 24));
    cmdShowFiles.setEnabled(false);
    cmdShowFiles.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmdShowFilesActionPerformed(evt);
      }
    });

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 0);
    getContentPane().add(cmdShowFiles, gridBagConstraints);

    lblOnset.setText("onset time:");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 5;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 10);
    getContentPane().add(lblOnset, gridBagConstraints);

    txtOnset.setPreferredSize(new java.awt.Dimension(150, 20));
    txtOnset.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        txtOnsetKeyReleased(evt);
      }
    });

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 5;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
    getContentPane().add(txtOnset, gridBagConstraints);

    cmdOnset.setMnemonic('A');
    cmdOnset.setText("Alter");
    cmdOnset.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmdOnsetActionPerformed(evt);
      }
    });

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 5;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
    getContentPane().add(cmdOnset, gridBagConstraints);

  }//GEN-END:initComponents

	private void txtOnsetKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOnsetKeyReleased
		//pressing enter in txtOnset is the same as clicking cmdOnset
		if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {	cmdOnset.doClick();	}
	}//GEN-LAST:event_txtOnsetKeyReleased

	private void cmdOnsetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdOnsetActionPerformed
		//assign the value in txtOnset to be the start time for the clip
		double dblTemp = 0; int intTime = 0; Integer IntTemp; String strClipName = ""; double dblTotalTime = 0;

		engMatLab.engEvalString("size = max(size(data1));"); // this causes fault... size not created, how about n = size(data1); size = max(n); seems to work...
		try {
			dblTemp = frmLoadData.engMatLab.engGetScalar("size");
		} catch (MatlabInvocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		dblTemp = dblTemp - 1;
		IntTemp = new Integer(txtOnset.getText());
		try { intTime = IntTemp.intValue(); }
		catch (java.lang.NumberFormatException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "The onset time must be an integer greater than 0 and less than " + dblTemp + ".\nPlease indicate an onset time.", "Onset Trigger Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (intTime < 1) { JOptionPane.showMessageDialog(null, "The onset time must be an integer greater than 0.\nPlease indicate an onset time.", "Onset Trigger Error", JOptionPane.ERROR_MESSAGE); return; }
		if (intTime > dblTemp) { JOptionPane.showMessageDialog(null, "The onset time must be less than " + dblTemp + ".\nPlease indicate an onset time.", "Onset Trigger Error", JOptionPane.ERROR_MESSAGE); return; }

		intStartTime = (int)intTime;  //haven't exited, so assign start time to public variable
		engMatLab.engPutArray("onsetTime", (double)intStartTime);

		//use combo box selection look up clip length from lengths entered in frmPreferences
		strClipName = (String)cboClip.getSelectedItem();  //store for use later from frmRespiration, etc.
		dblTotalTime = frmPreferences.getClipLength((String)cboClip.getSelectedItem());
		intStopTime = intStartTime + (int)dblTotalTime;  //add for time point of clip end in samples
		engMatLab.engPutArray("endTime", (double)intStopTime);
	}//GEN-LAST:event_cmdOnsetActionPerformed

	private void rdoRawTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoRawTextActionPerformed
		if (rdoRawText.isSelected() == true) { cmdShowFiles.setEnabled(false); }
	}//GEN-LAST:event_rdoRawTextActionPerformed

	private void cmdShowFilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdShowFilesActionPerformed
		//shows cboDataFile and fills it with the data files present in the indicated zipped archive
		ZipFile jcZipFile; String strTemp = ""; File jcFile;

		strTemp = txtPath.getText();
		if (strTemp.equals("")) { JOptionPane.showMessageDialog(null, "A file must be selected by clicking Select File or Choose from DB.", "File Error", JOptionPane.ERROR_MESSAGE); return; }
		jcFile = new File(strTemp);
		if (jcFile.exists() == false) { JOptionPane.showMessageDialog(null, "The file " + strTemp + " does not exist.", "File Error", JOptionPane.ERROR_MESSAGE); return; }
		if (jcFile.canRead() == false) { JOptionPane.showMessageDialog(null, "The file " + strTemp + " can not be read.", "File Error", JOptionPane.ERROR_MESSAGE); return; }

		try {
			jcZipFile = new ZipFile(jcFile);
			Enumeration jcEnum = jcZipFile.entries();
			if ((jcEnum == null) | (jcEnum.hasMoreElements() == false)) {  //not a zipped archive
				rdoRawText.setSelected(true); rdoZip.setSelected(false);  //switch rdo buttons back
				JOptionPane.showMessageDialog(null, "The file is not a valid zipped archive.", "File Error", JOptionPane.ERROR_MESSAGE); return; }
			else {  //is a valid zipped archive, so show files
				cboDataFile.removeAllItems();
				while (jcEnum.hasMoreElements() == true) { cboDataFile.addItem(jcEnum.nextElement().toString()); } }
		} catch (Exception e) { e.printStackTrace(); }
	}//GEN-LAST:event_cmdShowFilesActionPerformed

	private void rdoZipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoZipActionPerformed
		//enable this so can show the files in the archive
		if (rdoZip.isSelected() == true) { cmdShowFiles.setEnabled(true); }
	}//GEN-LAST:event_rdoZipActionPerformed

	private void cmdChooseDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdChooseDBActionPerformed
		//loads the class frmLoadFromDB and shows it in frmMain's jDesktopPane1 IF have a db name

		if (frmMain.getDBName().equals("NONE")) { cmdChooseDB.setEnabled(false); }
		else {
			try {
				JDesktopPane jcPane = getDesktopPane();
				frmLoadFromDB frmLoadFromDB1 = new frmLoadFromDB();
				frmLoadFromDB1.addInternalFrameListener(this);  //so know when it closes
				jcPane.add(frmLoadFromDB1, javax.swing.JLayeredPane.DEFAULT_LAYER);
				frmLoadFromDB1.show();
			}
			catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "There was an error connecting to the database.  Check the database name and connection.", "Database Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}//GEN-LAST:event_cmdChooseDBActionPerformed

	public void internalFrameClosed(InternalFrameEvent e) {
		//put the info identified from the database onto this form so the data can be loaded
		txtPath.setText(strPath);
		if (bolIsZip == true) { rdoZip.setSelected(true); cmdShowFiles.setEnabled(true); cmdShowFiles.doClick(); }
		else { rdoRawText.setSelected(true); cmdShowFiles.setEnabled(false); }
		bolLoadedFromDB = true;  //reset flag
  }

	public static void setPath(String strDBPath) { strPath = strDBPath; }
	public static void setIsZip(boolean bolDBIsZip) { bolIsZip = bolDBIsZip; }

	//these are needed for the class to implement javax.swing.event.InternalFrameListener
	public void internalFrameOpened(javax.swing.event.InternalFrameEvent e) { }
	public void internalFrameClosing(javax.swing.event.InternalFrameEvent e) { }
	public void internalFrameIconified(javax.swing.event.InternalFrameEvent e) { }
	public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent e) { }
	public void internalFrameActivated(javax.swing.event.InternalFrameEvent e) { }
	public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent e) { }

    private void cmdSelectFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSelectFileActionPerformed
      //bring up a file selection dialog so can pick the data file, put the file name in txtFileName and the entire
      //path in txtPath
      int intResult = 0; File fileChosen;

      //show a file chooser to let user pick a text file for compression.
      JFileChooser frmFileOpen = new JFileChooser(frmPreferences.getInstallPath());  //declare a JFileChooser - the select file box
      frmFileOpen.setApproveButtonText("Select");  //have button say Select instead of Open
      frmFileOpen.setMultiSelectionEnabled(false);  //only can pick one file
      intResult = frmFileOpen.showOpenDialog(null);  //this returns if select or cancel was clicked
      if (intResult == JFileChooser.CANCEL_OPTION) { return; }
      fileChosen = frmFileOpen.getSelectedFile();  //the file picked
      if (fileChosen.isFile() != true) { JOptionPane.showMessageDialog(null, "The file is not valid.", "File Validation Error", JOptionPane.ERROR_MESSAGE); return; }

      txtPath.setText(fileChosen.getPath());  //put path in txtPath
    }//GEN-LAST:event_cmdSelectFileActionPerformed

    private void cmdLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdLoadActionPerformed
      //load the file in txtPath into the open matlab workspace.  get the onset time from the trigger column.
      int intReturn = 0; double dblTemp = 0; File jcFile; String strTemp = ""; ZipFile jcZipFile;
			byte[] bytBuffer = new byte[1024]; int intLen;

			//check the entered paths and option choices for accuracy
			if (txtPath.getText().equals("")) { JOptionPane.showMessageDialog(null, "A file must be selected by clicking Select File or Choose from DB.", "File Error", JOptionPane.ERROR_MESSAGE); return; }

			try {
				//get the path to the file to open and send to matlab.  if zipped archive, need to open it.
				if (rdoZip.isSelected() == true) { //open the raw text file from out of the zipped archive
					strTemp = txtPath.getText();
					jcFile = new File(strTemp);
					if (jcFile.exists() == false) { JOptionPane.showMessageDialog(null, "The file " + strTemp + " does not exist.", "File Error", JOptionPane.ERROR_MESSAGE); return; }
					if (jcFile.canRead() == false) { JOptionPane.showMessageDialog(null, "The file " + strTemp + " can not be read.", "File Error", JOptionPane.ERROR_MESSAGE); return; }
					jcZipFile = new ZipFile(jcFile);

					//get the file out of the archive
					ZipEntry zipEntry = jcZipFile.getEntry((String)cboDataFile.getSelectedItem());
					InputStream jcInputStream = jcZipFile.getInputStream(zipEntry);
					BufferedOutputStream jcOutputStream = new BufferedOutputStream(new FileOutputStream(frmPreferences.getInstallPath() + "rawData.txt"));
					while((intLen = jcInputStream.read(bytBuffer)) >= 0) {
						jcOutputStream.write(bytBuffer, 0, intLen); }
					jcInputStream.close(); jcOutputStream.close();
					jcFile = new File(frmPreferences.getInstallPath() + "rawData.txt");
				}
				else { jcFile = new File(txtPath.getText()); }    //open raw text file directly
				strDataFile = jcFile.getPath();  //assign file path & name to variable so frmRSA can see it.

				engMatLab.engEvalString("clear;");  //remove all previous information in workspace, if any
				engMatLab.engEvalString("data1 = load('" + jcFile.getPath() + "');");  //load data file
				engMatLab.engEvalString("y = data1(:, " + frmPreferences.getColTrigger() + ");");  //get trigger col into y in matlab
				System.out.println("cd ('" + frmPreferences.getInstallPath() + "matlabScripts')");
				engMatLab.engEvalString("cd ('" + frmPreferences.getInstallPath() + "matlabScripts')");
				engMatLab.engEvalString("onsetTime = 1;");  //run function, put result in intNew
				dblTemp = frmLoadData.engMatLab.engGetScalar("onsetTime");  //get the stimulus onset time point

				//if findOnset returns a time of -1 then it was unable to locate a good onset time
				if ((int)dblTemp <= 0) { JOptionPane.showMessageDialog(null, "A valid onset trigger was not detected.\nPlease indicate an onset time.", "Onset Trigger Error", JOptionPane.ERROR_MESSAGE); }
				intStartTime = (int)dblTemp;  //assign start time to public variable
				txtOnset.setText("" + intStartTime);  //show start time on GUI
      } catch (Exception e) { e.printStackTrace(); }

			//use combo box selection look up clip length from lengths entered in frmPreferences
			strClipName = (String)cboClip.getSelectedItem();  //store for use later from frmRespiration, etc.
			dblTotalTime = frmPreferences.getClipLength((String)cboClip.getSelectedItem());
			intStopTime = intStartTime + (int)dblTotalTime;  //add for time point of clip end in samples
			engMatLab.engPutArray("endTime", (double)intStopTime);
    }//GEN-LAST:event_cmdLoadActionPerformed

	public static int getStartTime() { return intStartTime; }
	public static double getTotalTime() { return dblTotalTime; }
	public static int getStopTime() { return intStopTime; }
	public static String getDataFilePath() { return strDataFile; }
	public static String getClipName() { return strClipName; }
	public static boolean loadedFromDB() { return bolLoadedFromDB; }
	public static boolean getLoadedFromEduardoDB() { return bolLoadedFromEduardoDB; }
	public static void setLoadedFromEduardoDB(boolean bolLoaded) { bolLoadedFromEduardoDB = true; }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.ButtonGroup buttonGroup1;
  private javax.swing.JComboBox cboClip;
  private javax.swing.JComboBox cboDataFile;
  private javax.swing.JButton cmdChooseDB;
  private javax.swing.JButton cmdLoad;
  private javax.swing.JButton cmdOnset;
  private javax.swing.JButton cmdSelectFile;
  private javax.swing.JButton cmdShowFiles;
  private javax.swing.JLabel lblClip;
  private javax.swing.JLabel lblFileName;
  private javax.swing.JLabel lblOnset;
  private javax.swing.JLabel lblPath;
  private javax.swing.JRadioButton rdoRawText;
  private javax.swing.JRadioButton rdoZip;
  private javax.swing.JTextField txtOnset;
  private javax.swing.JTextField txtPath;
  // End of variables declaration//GEN-END:variables

}
