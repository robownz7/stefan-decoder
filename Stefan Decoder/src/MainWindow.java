import java.awt.Color;
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
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

import javafx.geometry.Insets;

public class MainWindow implements ActionListener {

	public JCheckBox cbCompare, cbExport;
	public JTextField tfCode;
	public JTextArea taFasta;
	public JTextArea taMotifOutput;
	private  JButton btMotif, btClearHighlights;
	
	public MainWindow() {
		//Initialize frame
		JFrame mainFrame = new JFrame("Stefan Decoder");

		JPanel pnMainPanel;

		pnMainPanel = new JPanel();
		GridBagLayout gbMainPanel = new GridBagLayout();
		GridBagConstraints gbcMainPanel = new GridBagConstraints();
		pnMainPanel.setLayout( gbMainPanel );

		btMotif = new JButton( "Build Motifs"  );
		gbcMainPanel.gridx = 9;
		gbcMainPanel.gridy = 0;
		gbcMainPanel.gridwidth = 8;
		gbcMainPanel.gridheight = 1;
		gbcMainPanel.fill = GridBagConstraints.BOTH;
		gbcMainPanel.weightx = 1;
		gbcMainPanel.weighty = 0;
		gbcMainPanel.anchor = GridBagConstraints.NORTH;
		gbMainPanel.setConstraints( btMotif, gbcMainPanel );
		pnMainPanel.add( btMotif );

		cbCompare = new JCheckBox( "Compare Motifs against FASTA string"  );
		cbCompare.setSelected( false );
		gbcMainPanel.gridx = 0;
		gbcMainPanel.gridy = 1;
		gbcMainPanel.gridwidth = 1;
		gbcMainPanel.gridheight = 2;
		gbcMainPanel.fill = GridBagConstraints.BOTH;
		gbcMainPanel.weightx = 1;
		gbcMainPanel.weighty = 0;
		gbcMainPanel.anchor = GridBagConstraints.CENTER;
		gbMainPanel.setConstraints( cbCompare, gbcMainPanel );
		pnMainPanel.add( cbCompare );

		tfCode = new JTextField( );
		gbcMainPanel.gridx = 0;
		gbcMainPanel.gridy = 0;
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
		gbcMainPanel.gridx = 0;
		gbcMainPanel.gridy = 3;
		gbcMainPanel.gridwidth = 9;
		gbcMainPanel.gridheight = 15;
		gbcMainPanel.fill = GridBagConstraints.BOTH;
		gbcMainPanel.weightx = 1;
		gbcMainPanel.weighty = 1;
		gbcMainPanel.anchor = GridBagConstraints.CENTER;
		gbMainPanel.setConstraints( scpFasta, gbcMainPanel );
		pnMainPanel.add( scpFasta );

		btClearHighlights = new JButton( "Clear Highlights"  );
		gbcMainPanel.gridx = 0;
		gbcMainPanel.gridy = 18;
		gbcMainPanel.gridwidth = 4;
		gbcMainPanel.gridheight = 2;
		gbcMainPanel.fill = GridBagConstraints.BOTH;
		gbcMainPanel.weightx = 1;
		gbcMainPanel.weighty = 0;
		gbcMainPanel.anchor = GridBagConstraints.SOUTHWEST;
		gbMainPanel.setConstraints( btClearHighlights, gbcMainPanel );
		pnMainPanel.add( btClearHighlights );

		cbExport = new JCheckBox( "Export to txt"  );
		cbExport.setSelected( true );
		gbcMainPanel.gridx = 1;
		gbcMainPanel.gridy = 1;
		gbcMainPanel.gridwidth = 8;
		gbcMainPanel.gridheight = 2;
		gbcMainPanel.fill = GridBagConstraints.BOTH;
		gbcMainPanel.weightx = 1;
		gbcMainPanel.weighty = 0;
		gbcMainPanel.anchor = GridBagConstraints.NORTH;
		gbMainPanel.setConstraints( cbExport, gbcMainPanel );
		pnMainPanel.add( cbExport );

		taMotifOutput = new JTextArea(2,10);
		JScrollPane scpMotifOutput = new JScrollPane( taMotifOutput );
		gbcMainPanel.gridx = 9;
		gbcMainPanel.gridy = 1;
		gbcMainPanel.gridwidth = 11;
		gbcMainPanel.gridheight = 17;
		gbcMainPanel.fill = GridBagConstraints.BOTH;
		gbcMainPanel.weightx = 1;
		gbcMainPanel.weighty = 1;
		gbcMainPanel.anchor = GridBagConstraints.NORTH;
		gbMainPanel.setConstraints( scpMotifOutput, gbcMainPanel );
		pnMainPanel.add( scpMotifOutput );

		//Add action listeners
		btMotif.addActionListener(this);
		btClearHighlights.addActionListener(this);
		cbCompare.addActionListener(this);
		
		
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
		String motifString = new String();
		//Check if build motif was triggered
		if(e.getSource() == btMotif) {
			tfCode.setText(tfCode.getText().toUpperCase());
			
			if(cbCompare.isSelected()) {
				//Build 
				try {
					motifString = motifCreator.createMotif(tfCode.getText());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(motifString != null) {
					taMotifOutput.setText(motifString);
					//Compare & Highlight
					try {
						runHighlighter(taMotifOutput.getText());
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}				
			}else {
				//Just build
				try {
					taMotifOutput.setText(motifCreator.createMotif(tfCode.getText()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		//Check if the Comparison checkbox was triggered
		if(e.getSource() == cbCompare){

			if(cbCompare.isSelected()) 
				btMotif.setText("Build & Compare");			
			else
				btMotif.setText("Build Motifs");
		}
		
		//Check if clear highlights was clicked
		if(e.getSource() == btClearHighlights) {
			//Clear all highlights
			Highlighter highlighter = taFasta.getHighlighter();
			Highlighter.Highlight[] highlights = highlighter.getHighlights();
			
			for(int i = 0; i < highlights.length; i++) {
				highlighter.removeHighlight(highlights[i]);
			}
		}
	}
	
	public void runHighlighter(String motifText) throws BadLocationException {
		
		//Split up all motifs
		String strToken[] = motifText.split("[\\r\\n]+");
		int numMotifs = strToken.length;
		int index;
		int length = strToken[0].length();
		
		//Create the highlighters
		Highlighter highlighter = taFasta.getHighlighter();
		HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
		
		//Loop through each Motif
		for(int i = 0; i < numMotifs; i++) {
			index = taFasta.getText().indexOf(strToken[i]);
			while(index >= 0) {
				//High light every motif instance we find in the FASTA
				highlighter.addHighlight(index, index+length, painter);
				index = taFasta.getText().indexOf(strToken[i], index+length);
			}			
		}

	}
}
