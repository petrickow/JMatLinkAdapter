package no.uio.taco.pukaMatControl.puka;

/*
 * frmConvert.java
 *
 * Created on December 11, 2003, 4:28 PM
 */
import java.awt.*; import java.awt.event.*; import java.awt.datatransfer.*; import java.io.*;
import javax.swing.filechooser.*; import javax.swing.*; import java.sql.*;
import java.util.StringTokenizer; import java.util.ArrayList; import java.io.File;
import javax.swing.event.InternalFrameEvent; import java.util.*; import java.util.Enumeration;

import no.uio.taco.pukaMatControl.matControlAdapter.*;
/**
 *
 * @author  jaetzel
 */
public class frmConvert extends javax.swing.JFrame {
	  /** engMatLab is used by all classes to connect to matlab via JMatLink  */
  public static JMatLinkAdapter engMatLab; private static String strClipName = "";
  private Connection conData; private Statement stmSQL;
	private static boolean bolIsZip = false; private static String strPath = "";
	private static String strFile = ""; private static String strDataFile = "";

  private static int intStartTime = 0;  /** time point at which the stimulus started */
  private static double dblTotalTime = 0;   /** total time of the stimulus, as number of measurements. */
	private static int intStopTime = 0; private static boolean bolLoadedFromDB = false;

	/** Creates new form frmConvert */
	public frmConvert() {
		int intC = 0;

		JMatLinkAdapter engMatLab = new JMatLinkAdapter();

		/*engMatLab = new JMatLink();  //initiate connection
      		try {
       			System.load("JMatlink.dll");
       			engMatLab.setDebug( true );
			 	intC = engMatLab.engOpen();  //open connection to MATLAB
      		} catch (Exception e) { e.printStackTrace(); }
		 */
		initComponents();
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
  private void initComponents() {//GEN-BEGIN:initComponents
    java.awt.GridBagConstraints gridBagConstraints;

    txtPath = new javax.swing.JTextField();
    lblPath = new javax.swing.JLabel();
    cmdSelectFile = new javax.swing.JButton();
    cmdLoad = new javax.swing.JButton();
    lblOut = new javax.swing.JLabel();
    txtOut = new javax.swing.JTextField();
    cmdSelectOut = new javax.swing.JButton();
    lblRawFile = new javax.swing.JLabel();
    txtRawFile = new javax.swing.JTextField();
    cmdSelectFile1 = new javax.swing.JButton();
    cmdExit = new javax.swing.JButton();

    getContentPane().setLayout(new java.awt.GridBagLayout());

    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent evt) {
        exitForm(evt);
      }
    });

    txtPath.setMaximumSize(new java.awt.Dimension(400, 20));
    txtPath.setMinimumSize(new java.awt.Dimension(250, 20));
    txtPath.setPreferredSize(new java.awt.Dimension(400, 20));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    getContentPane().add(txtPath, gridBagConstraints);

    lblPath.setText("results file from puka:");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 10);
    gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
    getContentPane().add(lblPath, gridBagConstraints);

    cmdSelectFile.setMnemonic('S');
    cmdSelectFile.setText("Select File");
    cmdSelectFile.setToolTipText("not yet");
    cmdSelectFile.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmdSelectFileActionPerformed(evt);
      }
    });

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
    getContentPane().add(cmdSelectFile, gridBagConstraints);

    cmdLoad.setMnemonic('L');
    cmdLoad.setText("Load File");
    cmdLoad.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmdLoadActionPerformed(evt);
      }
    });

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
    gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
    getContentPane().add(cmdLoad, gridBagConstraints);

    lblOut.setText("output file:");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    getContentPane().add(lblOut, gridBagConstraints);

    txtOut.setMinimumSize(new java.awt.Dimension(250, 20));
    txtOut.setPreferredSize(new java.awt.Dimension(400, 20));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
    getContentPane().add(txtOut, gridBagConstraints);

    cmdSelectOut.setText("Select File");
    cmdSelectOut.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmdSelectOutActionPerformed(evt);
      }
    });

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
    getContentPane().add(cmdSelectOut, gridBagConstraints);

    lblRawFile.setText("raw data file:");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 10);
    gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
    getContentPane().add(lblRawFile, gridBagConstraints);

    txtRawFile.setMaximumSize(new java.awt.Dimension(400, 20));
    txtRawFile.setMinimumSize(new java.awt.Dimension(250, 20));
    txtRawFile.setPreferredSize(new java.awt.Dimension(400, 20));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    getContentPane().add(txtRawFile, gridBagConstraints);

    cmdSelectFile1.setMnemonic('S');
    cmdSelectFile1.setText("Select File");
    cmdSelectFile1.setToolTipText("not yet");
    cmdSelectFile1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmdSelectFile1ActionPerformed(evt);
      }
    });

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
    getContentPane().add(cmdSelectFile1, gridBagConstraints);

    cmdExit.setText("Close Matlab & Exit");
    cmdExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cmdExitActionPerformed(evt);
      }
    });

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    getContentPane().add(cmdExit, gridBagConstraints);

    pack();
  }//GEN-END:initComponents

	private void cmdExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdExitActionPerformed
		//close down the matlab connection and session
		engMatLab.engClose(); engMatLab.kill();
		System.exit(0);  //close everything else
	}//GEN-LAST:event_cmdExitActionPerformed

	private void cmdSelectFile1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSelectFile1ActionPerformed
     //bring up a file selection dialog so can pick the data file, put the file name in txtFileName and the entire
      //path in txtPath
      int intResult = 0; File fileChosen;

      //show a file chooser to let user pick a text file for compression.
      JFileChooser frmFileOpen = new JFileChooser("C:\\kailua\\puka\\respiration signals\\");  //declare a JFileChooser - the select file box
      frmFileOpen.setApproveButtonText("Select");  //have button say Select instead of Open
      frmFileOpen.setMultiSelectionEnabled(false);  //only can pick one file
      intResult = frmFileOpen.showOpenDialog(null);  //this returns if select or cancel was clicked
      if (intResult == JFileChooser.CANCEL_OPTION) { return; }
      fileChosen = frmFileOpen.getSelectedFile();  //the file picked
      if (fileChosen.isFile() != true) { JOptionPane.showMessageDialog(null, "The file is not valid.", "File Validation Error", JOptionPane.ERROR_MESSAGE); return; }

      txtRawFile.setText(fileChosen.getPath());  //put path in txtPath
	}//GEN-LAST:event_cmdSelectFile1ActionPerformed

	private void cmdSelectOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSelectOutActionPerformed
     //bring up a file selection dialog so can pick the data file, put the file name in txtFileName and the entire
      //path in txtOut
      int intResult = 0; File fileChosen;

      //show a file chooser to let user pick a text file for compression.
      JFileChooser frmFileOpen = new JFileChooser("C:\\kailua\\maile\\documents\\journalPaper\\allSignals\\New Folder\\");  //declare a JFileChooser - the select file box
      intResult = frmFileOpen.showSaveDialog(null);  //this returns if select or cancel was clicked
      if (intResult == JFileChooser.CANCEL_OPTION) { return; }
      fileChosen = frmFileOpen.getSelectedFile();  //the file picked

      txtOut.setText(fileChosen.getPath());  //put path in txtPath
	}//GEN-LAST:event_cmdSelectOutActionPerformed

	private void cmdLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdLoadActionPerformed
		 //load the file in txtPath into the open matlab workspace.  get the onset time from the trigger column.
      int intReturn = 0; double dblTemp = 0; File jcFile; String strTemp = ""; String strInput = ""; String strToken = "";
			StringTokenizer jcTokenizer; ArrayList jcPeakX = new ArrayList(); ArrayList jcPeakY = new ArrayList();
			ArrayList jcTroughX = new ArrayList(); ArrayList jcTroughY = new ArrayList(); int intTemp = 0;
			ArrayList jcTroughPauseX = new ArrayList(); ArrayList jcTroughPauseY = new ArrayList();
			ArrayList jcPeakPauseX = new ArrayList(); ArrayList jcPeakPauseY = new ArrayList(); int intC = 0;

			//check the entered paths and option choices for accuracy
			if (txtPath.getText().equals("")) { JOptionPane.showMessageDialog(null, "A file must be selected by clicking Select File or Choose from DB.", "File Error", JOptionPane.ERROR_MESSAGE); return; }

		try {
			jcFile = new File(txtRawFile.getText());
			engMatLab.engEvalString("clear;");  //remove all previous information in workspace, if any
			engMatLab.engEvalString("data1 = load('" + jcFile.getPath() + "');");  //load data file
			engMatLab.engEvalString("Qr = data1(:,2);");
			engMatLab.engEvalString("Qd = decimate(Qr, 5);");

			jcFile = new File(txtPath.getText());
			FileReader jcFReader = new FileReader(jcFile);  //reader to connect to the file
			BufferedReader jcBReader = new BufferedReader(jcFReader);  //another reader
			while( (strInput = jcBReader.readLine()) != null ) {
				if (strInput.equals("") == false) {
					jcTokenizer = new StringTokenizer(strInput, ">");  //read strInput with | as the only delimiter
					strToken = jcTokenizer.nextToken();  //get the first word - parameter name
					//System.out.println("strToken: " + strToken);
					if (strToken.equals("<peak")) {
						strTemp = strInput.substring(strInput.indexOf(">"));  //number plus closing tag
						strTemp = strTemp.substring(1, strTemp.indexOf("<"));  //System.out.println("strTemp2: " + strTemp);
						intTemp = (new Integer(strTemp)).intValue();
						intTemp = intTemp/5;
						engMatLab.engEvalString("y = Qd(" + intTemp + ");");	dblTemp = engMatLab.engGetScalar("y");
						jcPeakX.add("" + intTemp); jcPeakY.add(("" + dblTemp));  //save so can write them out later
					} else if (strToken.equals("<trough")) {
						strTemp = strInput.substring(strInput.indexOf(">"));  //number plus closing tag
						strTemp = strTemp.substring(1, strTemp.indexOf("<"));  //System.out.println("strTemp2: " + strTemp);
						intTemp = (new Integer(strTemp)).intValue();
						intTemp = intTemp/5;
						engMatLab.engEvalString("y = Qd(" + intTemp + ");");	dblTemp = engMatLab.engGetScalar("y");
						jcTroughX.add("" + intTemp); jcTroughY.add(("" + dblTemp));  //save so can write them out later
					} else if (strToken.equals("<peakPause")) {
						strTemp = strInput.substring(strInput.indexOf(">"));  //number plus closing tag
						strTemp = strTemp.substring(1, strTemp.indexOf("<"));  //System.out.println("strTemp2: " + strTemp);
						engMatLab.engEvalString("y = Qd(" + strTemp + ");");	dblTemp = engMatLab.engGetScalar("y");
						jcPeakPauseX.add(strTemp); jcPeakPauseY.add(("" + dblTemp));  //save so can write them out later
					} else if (strToken.equals("<troughPause")) {
						strTemp = strInput.substring(strInput.indexOf(">"));  //number plus closing tag
						strTemp = strTemp.substring(1, strTemp.indexOf("<"));  //System.out.println("strTemp2: " + strTemp);
						engMatLab.engEvalString("y = Qd(" + strTemp + ");");	dblTemp = engMatLab.engGetScalar("y");
						jcTroughPauseX.add(strTemp); jcTroughPauseY.add(("" + dblTemp));  //save so can write them out later
					}
				}
			}
			jcBReader.close(); jcFReader.close();

			FileWriter jcOut = new FileWriter(txtOut.getText() + "peaks.txt");  //write peaks file
			jcOut.write("peakX\tpeakY\n");
			Iterator jcIterator = jcPeakX.iterator(); intC = 0;
			while (jcIterator.hasNext()) { jcOut.write(jcPeakX.get(intC) + "\t" + jcPeakY.get(intC) + "\n"); jcIterator.next(); intC++; }
			jcOut.close();

			jcOut = new FileWriter(txtOut.getText() + "troughs.txt");  //writer to write output file
			jcOut.write("troughX\ttroughY\n");
			jcIterator = jcTroughX.iterator(); intC = 0;
			while (jcIterator.hasNext()) { jcOut.write(jcTroughX.get(intC) + "\t" + jcTroughY.get(intC) + "\n"); jcIterator.next(); intC++; }
			jcOut.close();

			jcOut = new FileWriter(txtOut.getText() + "PeakPauses.txt");  //writer to write output file
			jcOut.write("peakPauseX\tpeakPauseY\n");
			jcIterator = jcPeakPauseX.iterator(); intC = 0;
			while (jcIterator.hasNext()) { jcOut.write(jcPeakPauseX.get(intC) + "\t" + jcPeakPauseY.get(intC) + "\n"); jcIterator.next(); intC++; }
			jcOut.close();

			jcOut = new FileWriter(txtOut.getText() + "TroughPauses.txt");  //writer to write output file
			jcOut.write("troughPauseX\ttroughPauseY\n");
			jcIterator = jcTroughPauseX.iterator(); intC = 0;
			while (jcIterator.hasNext()) { jcOut.write(jcTroughPauseX.get(intC) + "\t" + jcTroughPauseY.get(intC) + "\n"); jcIterator.next(); intC++; }
			jcOut.close();

			engMatLab.engEvalString("save(" + txtOut.getText() + "Qd.txt" + ", 'Qd', '-ascii')");

	  } catch (Exception e) { e.printStackTrace(); }
	}//GEN-LAST:event_cmdLoadActionPerformed

	private void cmdSelectFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSelectFileActionPerformed
     //bring up a file selection dialog so can pick the data file, put the file name in txtFileName and the entire
      //path in txtPath
      int intResult = 0; File fileChosen;

      //show a file chooser to let user pick a text file for compression.
      JFileChooser frmFileOpen = new JFileChooser("C:\\kailua\\puka\\respiration signals\\analyzed data\\");  //declare a JFileChooser - the select file box
      frmFileOpen.setMultiSelectionEnabled(false);  //only can pick one file
      intResult = frmFileOpen.showOpenDialog(null);  //this returns if select or cancel was clicked
      if (intResult == JFileChooser.CANCEL_OPTION) { return; }
      fileChosen = frmFileOpen.getSelectedFile();  //the file picked
      if (fileChosen.isFile() != true) { JOptionPane.showMessageDialog(null, "The file is not valid.", "File Validation Error", JOptionPane.ERROR_MESSAGE); return; }

      txtPath.setText(fileChosen.getPath());  //put path in txtPath
	}//GEN-LAST:event_cmdSelectFileActionPerformed

	/** Exit the Application */
	private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
		System.exit(0);
	}//GEN-LAST:event_exitForm

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		new frmConvert().show();
	}


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton cmdExit;
  private javax.swing.JButton cmdLoad;
  private javax.swing.JButton cmdSelectFile;
  private javax.swing.JButton cmdSelectFile1;
  private javax.swing.JButton cmdSelectOut;
  private javax.swing.JLabel lblOut;
  private javax.swing.JLabel lblPath;
  private javax.swing.JLabel lblRawFile;
  private javax.swing.JTextField txtOut;
  private javax.swing.JTextField txtPath;
  private javax.swing.JTextField txtRawFile;
  // End of variables declaration//GEN-END:variables

}
