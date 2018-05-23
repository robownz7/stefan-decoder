import java.io.IOException;

/* The Stefan Decoder.
 * Made by Rob.
 * Made for Stefan.
 */
public class Main {
	
	public static void main(String[] args) throws IOException{
		//Initialize Variables
		String motifOutput = "motif output.txt";
		
		//Open Motif Creator window to get code input and process possible motifs.
		motifCreator.motifWindow(motifOutput);	
		
		System.exit(0);
		
	}
}
