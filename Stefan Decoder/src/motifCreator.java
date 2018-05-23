//Take an input code string, and output all possible motifs.
import java.awt.Component;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

public class motifCreator {

	static FileWriter fileWriter;
    static PrintWriter printWriter;
    
	public static void motifWindow(String motifOutput) throws IOException {
		//initialize variables
		int length;
		
	    fileWriter = new FileWriter(motifOutput);
	    printWriter = new PrintWriter(fileWriter);
	    String input;
	    long startTime, endTime;
	    Component frame = null;
	    
	    do {
		//Get input code
		input = JOptionPane.showInputDialog("Enter an IUPAC Nucleotide Code string:");
		if(input == null) {
			return;
		}
		//store input length
		length = input.length();
		
		//Convert all characters to upper-case
		input = input.toUpperCase();	
		
		//Check validity of all code characters entered. If there is an invalid character, prompt for input again.
	    }while(!checkCharacterValidity(input));
	    
	    //Start process timer
		startTime = System.currentTimeMillis();
	    
		//Build Arrays
		char[] srcCodeArray = new char[length];
		char[] codeArray = new char[length];
		
		//Build Source Code Array. Store each character from input into an array
		for(int i = 0; i < length; i++) {
			srcCodeArray[i] = input.charAt(i);
		}
		
		//Print original code string to the file.
		printWriter.print(new String(srcCodeArray) + " - Original Code\r\n");
		
		//Copy source to working array
		System.arraycopy(srcCodeArray, 0, codeArray, 0, length);
		
		//Set start values for all bases in the code
		for(int i = 0; i < length; i++) {
			codeArray[i] = nextBase(srcCodeArray[i], codeArray[i]);
		}
		
		//Recurse though the array printing bases
		outputBase(length, 0, codeArray, srcCodeArray);

		printWriter.close();
		fileWriter.close();
		
		endTime = System.currentTimeMillis();
		JOptionPane.showMessageDialog(frame, "Processing Complete. Total time: " + (float)((endTime - startTime)/1000.0)+ " seconds", "Complete", JOptionPane.INFORMATION_MESSAGE);

		Desktop.getDesktop().open(new File(motifOutput));
	}

	public static void outputBase(int length, int currentIndex, char[] codeArray, char[] srcCodeArray) {
		int count;	
			
		//Check how many base iterations for the current index
		count = maxBaseSize(srcCodeArray[currentIndex]);
		
		for(int i = 0; i < count; i++) {
			//Check if this the right most character
			if(currentIndex == length - 1) {
				//We are farthest to the right, stop recursing 
				printWriter.print(new String(codeArray) + "\r\n");
				codeArray[currentIndex] = nextBase(srcCodeArray[currentIndex], codeArray[currentIndex]);
			}
			else {
				outputBase(length, currentIndex + 1, codeArray, srcCodeArray);
				codeArray[currentIndex] = nextBase(srcCodeArray[currentIndex], codeArray[currentIndex]);
			}
		}
		
		//Set our base back to the start
		codeArray[currentIndex] = nextBase(srcCodeArray[currentIndex], srcCodeArray[currentIndex]);
	}
	
	public static boolean checkCharacterValidity(String input) {
	
		String acceptableInput = "CATUGRYKMSWBHVDN";
		Component frame = null;
		
		//Check if string is empty
		if(input.length() == 0) {
			return false;
		}
		
		//Check every input character is acceptable
		for(int i = 0; i < input.length(); i++) {
			if((acceptableInput.indexOf(input.charAt(i))) == -1) {
				JOptionPane.showMessageDialog(frame, "Invalid Character '" + input.charAt(i) + "' entered.", "Invalid Character", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}
	
	
	public static int maxBaseSize(char base) {
		//Take the current code and return the number of different bases it could have
		int size;
		
		switch(base) {
		case 'C':
		case 'A':
		case 'T':
		case 'U':
		case 'G':
			size = 1;
			break;
		case 'R':
		case 'Y':
		case 'K':
		case 'M':
		case 'S':
		case 'W':
			size = 2;
			break;
		case 'B':
		case 'H':
		case 'V':
		case 'D':
			size = 3;
			break;
		case 'N':
			size = 4;
			break;
		default:
			printWriter.print("Invalid code to compute base size.");
			size = 0;
			break;
		}
		
		return size;
	}

	public static char nextBase(char srcBase, char currentBase) {
	//Take the source base and current base to determine what the next base will be
		char output;
		
		if(srcBase == 'C' || srcBase == 'A' || srcBase == 'T' || srcBase == 'G') {
			return srcBase;
		}
		
		switch(srcBase) {
		case 'U':
			output = 'T';
			break;
		case 'R':
			if(currentBase == srcBase) 
				output = 'A';
			else
				output = 'G';
			break;
		case 'Y':
			if(currentBase == srcBase) 
				output = 'T';
			else
				output = 'C';
			break;
		case 'K':
			if(currentBase == srcBase) 
				output = 'T';
			else
				output = 'G';
			break;
		case 'M':
			if(currentBase == srcBase) 
				output = 'A';
			else
				output = 'C';
			break;
		case 'S':
			if(currentBase == srcBase) 
				output = 'G';
			else
				output = 'C';
			break;
		case 'W':
			if(currentBase == srcBase) 
				output = 'A';
			else
				output = 'T';
			break;
		case 'B':
			if(currentBase == srcBase) 
				output = 'C';
			else if(currentBase == 'C')
				output = 'G';
			else
				output = 'T';
			break;
		case 'H':
			if(currentBase == srcBase) 
				output = 'A';
			else if(currentBase == 'A')
				output = 'C';
			else
				output = 'T';
			break;
		case 'V':
			if(currentBase == srcBase) 
				output = 'A';
			else if(currentBase == 'A')
				output = 'C';
			else
				output = 'G';
			break;
		case 'D':
			if(currentBase == srcBase) 
				output = 'A';
			else if(currentBase == 'A')
				output = 'G';
			else
				output = 'T';
			break;
		case 'N':
			if(currentBase == srcBase) 
				output = 'A';
			else if(currentBase == 'A')
				output = 'T';
			else if(currentBase == 'T')
				output = 'C';
			else
				output = 'G';
			break;
		default:
			printWriter.print("Invalid source base provided.");
			output = 'Z';
		}
		
		return output;
	}
}

