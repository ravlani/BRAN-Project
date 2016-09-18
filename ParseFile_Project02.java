package Project02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ParseFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		Reads each line of a GEDCOM file
		Prints each line
		Prints the level number of each line
		Prints the tag of each line that has a valid tag for our project, or prints "Invalid tag"
		INDI, NAME, SEX, BIRT, DEAT, 
		FAMC, FAM, MAR, HUSB, WIFE, CHIL, DIV, DATE, HEAD, TRLR, NOTE
		 */
		
		String fileName ="Project01_NikitaDmello.ged";
		
		String line = null;
		String delimiter = "[ ]";
		String[] validTags = {"INDI","NAME","SEX","BIRT","DEAT","FAMC",
		"FAM","MAR","HUSB","WIFE","CHIL","DIV","DATE","HEAD","TRLR","NOTE"};
		String[] lineTokens = null;
		boolean ifValid = false;
		
		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			
			while((line = br.readLine())!= null){
				System.out.println("\n"+line);
				lineTokens = line.split(delimiter);
				System.out.println(lineTokens[0]);
				
				if (lineTokens[0].equals("1") && lineTokens[1].equals("DATE")){
					System.out.println("Invalid tag");
				}
				else if(lineTokens.length > 2 && (lineTokens[2].equals("INDI") || lineTokens[2].equals("FAM"))){
					System.out.println(lineTokens[2]);
				}
				else{
					for(String valid: validTags){
						ifValid = lineTokens[1].equals(valid);
						if(ifValid){
							System.out.println(lineTokens[1]);
							break;
						}
					}
					if(ifValid == false){
						System.out.println("Invalid tag");
					}
				}	
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
