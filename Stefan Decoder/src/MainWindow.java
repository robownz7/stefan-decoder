import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainWindow implements ActionListener {

	public JCheckBox cbCompare, cbExport;
	public JTextField tfCode;
	public JTextArea taFasta;
	public JTextArea taMotifOutput;
	
	public MainWindow() {
		//Initialize frame
		JFrame mainFrame = new JFrame("Stefan Decoder");
		JPanel pnMainPanel;
		JButton btMotif;

		//Build window elements
		pnMainPanel = new JPanel();
		GridBagLayout gbMainPanel = new GridBagLayout();
		GridBagConstraints gbcMainPanel = new GridBagConstraints();
		pnMainPanel.setLayout( gbMainPanel );

		btMotif = new JButton( "Build Motifs"  );
		btMotif.addActionListener(this);
		gbcMainPanel.gridx = 12;
		gbcMainPanel.gridy = 1;
		gbcMainPanel.gridwidth = 8;
		gbcMainPanel.gridheight = 2;
		gbcMainPanel.fill = GridBagConstraints.BOTH;
		gbcMainPanel.weightx = 1;
		gbcMainPanel.weighty = 0;
		gbcMainPanel.anchor = GridBagConstraints.NORTH;
		gbMainPanel.setConstraints( btMotif, gbcMainPanel );
		pnMainPanel.add( btMotif );

		cbCompare = new JCheckBox( "Compare Motifs against FASTA string"  );
		cbCompare.setSelected( false );
		gbcMainPanel.gridx = 1;
		gbcMainPanel.gridy = 4;
		gbcMainPanel.gridwidth = 12;
		gbcMainPanel.gridheight = 3;
		gbcMainPanel.fill = GridBagConstraints.BOTH;
		gbcMainPanel.weightx = 1;
		gbcMainPanel.weighty = 0;
		gbcMainPanel.anchor = GridBagConstraints.CENTER;
		gbMainPanel.setConstraints( cbCompare, gbcMainPanel );
		pnMainPanel.add( cbCompare );
		
		cbExport = new JCheckBox( "Export to txt file"  );
		cbExport.setSelected( true );
		gbcMainPanel.gridx = 12;
		gbcMainPanel.gridy = 4;
		gbcMainPanel.gridwidth = 8;
		gbcMainPanel.gridheight = 3;
		gbcMainPanel.fill = GridBagConstraints.BOTH;
		gbcMainPanel.weightx = 1;
		gbcMainPanel.weighty = 0;
		gbcMainPanel.anchor = GridBagConstraints.NORTH;
		gbMainPanel.setConstraints( cbExport, gbcMainPanel );
		pnMainPanel.add( cbExport );

		tfCode = new JTextField( );
		gbcMainPanel.gridx = 3;
		gbcMainPanel.gridy = 2;
		gbcMainPanel.gridwidth = 9;
		gbcMainPanel.gridheight = 1;
		gbcMainPanel.fill = GridBagConstraints.BOTH;
		gbcMainPanel.weightx = 1;
		gbcMainPanel.weighty = 0;
		gbcMainPanel.anchor = GridBagConstraints.NORTH;
		gbMainPanel.setConstraints( tfCode, gbcMainPanel );
		pnMainPanel.add( tfCode );

		taFasta = new JTextArea(2,10);
		JScrollPane scpFasta = new JScrollPane( taFasta );
		gbcMainPanel.gridx = 1;
		gbcMainPanel.gridy = 9;
		gbcMainPanel.gridwidth = 19;
		gbcMainPanel.gridheight = 10;
		gbcMainPanel.fill = GridBagConstraints.BOTH;
		gbcMainPanel.weightx = 1;
		gbcMainPanel.weighty = 1;
		gbcMainPanel.anchor = GridBagConstraints.CENTER;
		gbMainPanel.setConstraints( scpFasta, gbcMainPanel );
		pnMainPanel.add( scpFasta );

		taMotifOutput = new JTextArea(2,10);
		JScrollPane scpMotifOutput = new JScrollPane( taMotifOutput );
		gbcMainPanel.gridx = 20;
		gbcMainPanel.gridy = 1;
		gbcMainPanel.gridwidth = 7;
		gbcMainPanel.gridheight = 20;
		gbcMainPanel.fill = GridBagConstraints.BOTH;
		gbcMainPanel.weightx = 1;
		gbcMainPanel.weighty = 1;
		gbcMainPanel.anchor = GridBagConstraints.NORTH;
		gbMainPanel.setConstraints( scpMotifOutput, gbcMainPanel );
		pnMainPanel.add( scpMotifOutput );

		//Add window elements to the main frame.
		mainFrame.add(pnMainPanel);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(600,450);
		mainFrame.setMinimumSize(new Dimension(500,150));
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//Build Motifs is pushed
		try {
			taMotifOutput.setText(motifCreator.motifWindow(tfCode.getText()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}	
}
