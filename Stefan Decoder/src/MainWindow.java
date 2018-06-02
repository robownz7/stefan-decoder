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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;


public class MainWindow implements ActionListener {

	public JCheckBox cbCompare, cbExport;
	public JTextField tfCode;
	public JTextArea taFasta;
	public JTextArea taMotifOutput;
	private JButton btMotif, btClearHighlights;
	private JLabel lbCount;
	
	public MainWindow() {
		//Initialize frame
		JFrame mainFrame = new JFrame("Stefan Decoder");

		JPanel pnMainPanel;

		pnMainPanel = new JPanel();
		GridBagLayout gbMainPanel = new GridBagLayout();
		GridBagConstraints gbcMainPanel = new GridBagConstraints();
		pnMainPanel.setLayout( gbMainPanel );

		//Create GUI components
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
		tfCode.setToolTipText( "Enter a nucleotide code here." );
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
		taFasta.setToolTipText( "Enter a FASTA string here." );
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
		cbExport.setSelected( false );
		cbExport.setEnabled(false);
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

		lbCount = new JLabel( "0 : 0   "  );
		lbCount.setHorizontalAlignment(SwingConstants.RIGHT);
		gbcMainPanel.gridx = 10;
		gbcMainPanel.gridy = 19;
		gbcMainPanel.gridwidth = 10;
		gbcMainPanel.gridheight = 1;
		gbcMainPanel.fill = GridBagConstraints.BOTH;
		gbcMainPanel.weightx = 1;
		gbcMainPanel.weighty = 0;
		gbcMainPanel.anchor = GridBagConstraints.NORTH;
		gbMainPanel.setConstraints( lbCount, gbcMainPanel );
		pnMainPanel.add( lbCount );

		//Add action listeners
		btMotif.addActionListener(this);
		btClearHighlights.addActionListener(this);
		cbCompare.addActionListener(this);
		
		//taFASTA Docuemnt and Caret listener for character count and position updates.
		taFasta.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {		
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				lbCount.setText(taFasta.getCaretPosition() + " : " + taFasta.getText().length() + "   ");
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {				
			}		
		});
		
		taFasta.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent arg0) {
				lbCount.setText(taFasta.getCaretPosition() + " : " + taFasta.getText().length() + "   ");
			}			
		});
		
		//Add window elements to the main frame.
		mainFrame.add(pnMainPanel);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(800,600);
		mainFrame.setMinimumSize(new Dimension(500,150));
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);		
		mainFrame.getRootPane().setDefaultButton(btMotif);
		
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
		
		//Remove line breaks from Fasta.
		taFasta.setText(taFasta.getText().replaceAll("[\\r\\n]+", ""));
		taFasta.setLineWrap(true);
				
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
