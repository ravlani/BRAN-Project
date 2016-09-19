package Project03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ParseFile {
	static boolean validLine;
	
	static String delimiter = "[ ]";
	static String[] lineTokens = null;
	static int icounter = -1;
	static int fcounter = -1;
	static String IFcache = null; 
	static String BDcache = null;
	static String DIMcache = null;
	
	static ArrayList<Individual> individualsList = new ArrayList<Individual>();
	static ArrayList<Family> FamilyList = new ArrayList<Family>();
	
	public static void main(String[] args) {
		String fileName ="test.ged";
		String line = null;
		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			
			while((line = br.readLine())!= null){
				lineTokens = line.split(delimiter);
				storeData(lineTokens);
			}
			br.close();
			printData();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//To print Individual and Family Data
	public static void printData(){
		for (Individual i:individualsList){
			System.out.println(i.toString());
		}
		for (Family f : FamilyList){
			System.out.println("\nFamily Id:"+ f.getId());
			for(Individual i : individualsList){
				if(f.getHusband()!= null){
					if(f.getHusband().equals(i.getId())){
						System.out.println("Husband Name : " +i.getName());
					}
				}
				if(f.getWife()!=null){
					if(f.getWife().equals(i.getId())){
						System.out.println("Wife Name : " +i.getName());
					}
				}
			}
			System.out.println("Marriage Date : "+f.getMarriageDate());
			System.out.println("Divorce Date : "+f.getDivorceDate());
			System.out.println("Child : "+f.getChild());
		}
	}
	
	// To store individual data in Individual List and family data in Family list
	public static void storeData(String []lineTokens){
		Individual i;
		Family f;
		switch(lineTokens[0]){
			case "0":
				if(lineTokens.length>2){
					switch(lineTokens[2]){
						case "INDI":
							i = new Individual();
							icounter ++;
							i.setId(lineTokens[1]);
							individualsList.add(i);
							IFcache = "I";
							break;
						case "FAM":
							f = new Family();
							fcounter++;
							f.setId(lineTokens[1]);
							FamilyList.add(f);
							IFcache ="F";
							break;
						default:break;
						}
			
				}
				break;
			case "1":
				switch(IFcache){
					case "I":i = individualsList.get(icounter);
							switch(lineTokens[1]){
								case "NAME":i.setName(lineTokens[2]+" "+lineTokens[3].replaceAll("/",""));break;
								case "SEX":i.setGender(lineTokens[2]);break;
								case "BIRT":BDcache = "B";break;
								case "DEAT":BDcache = "D";break;
								case "FAMS":i.setFspouseId(lineTokens[2]); break;
								case "FAMC":i.setFchildId(lineTokens[2]); break;
								default:break;
								
							}
						break;
						
					case "F":f = FamilyList.get(fcounter);
							switch(lineTokens[1]){
								case "HUSB":f.setHusband(lineTokens[2]);break;
								case "WIFE":f.setWife(lineTokens[2]);break;
								case "MARR":DIMcache = "M";break;
								case "DIV":DIMcache = "DI";break;
								case "CHIL": f.setChild(lineTokens[2]); break;
								default:break;
							}
						break;
					default:break;
				}
				break;
			case "2":
				switch(IFcache){
					case "I":i = individualsList.get(icounter);
						switch(BDcache){
							case "B":i.setBirthDate(getDate(lineTokens[2]+lineTokens[3]+lineTokens[4]));break;
							case "D":i.setDeathDate(getDate(lineTokens[2]+lineTokens[3]+lineTokens[4]));break;
							default:break;
						}
						break;
					
					case "F": f = FamilyList.get(fcounter);
						switch(DIMcache){
							case "M":f.setMarriageDate(getDate(lineTokens[2]+lineTokens[3]+lineTokens[4]));break;
							case "DI":f.setDivorceDate(getDate(lineTokens[2]+lineTokens[3]+lineTokens[4]));break;
							default:break;
						}
						break;
					default:break;
					}
				break;
			default:break;
		}
		
	}
	
	// To convert String date to Date Object
	public static Date getDate(String date){
		DateFormat format = new SimpleDateFormat("ddMMMyyyy", Locale.ENGLISH);
		Date dt = null;
		try {
			dt = format.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dt;
	}
	


}
