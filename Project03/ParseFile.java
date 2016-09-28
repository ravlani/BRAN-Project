package Project03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map.Entry;

public class ParseFile {
	static boolean validLine;
	
	static String delimiter = "[ ]";
	static String[] lineTokens = null;
	static String icounter = "";
	static String fcounter = "";
	static String IFcache = null; 
	static String BDcache = null;
	static String DIMcache = null;
	
	static LinkedHashMap<String, Individual> indiMap = new LinkedHashMap<String, Individual>();
	static LinkedHashMap<String, Family> famMap = new LinkedHashMap<String, Family>();
	
	public static void main(String[] args) {
		String fileName ="/Users/AcquinDmello/Documents/workspace/CS555_Agile/src/Project02_textFile.txt";
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
            		printErrorsAnomalies();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//To print Individual and Family Data
	public static void printData(){
		for (Entry<String, Individual> i :indiMap.entrySet()){
			System.out.println(i.getValue().toString());
		}
		for (Entry<String, Family> f :famMap.entrySet()){
			Family fam = f.getValue();
			
			System.out.println("\nFamily id : "+fam.getId());
			if(fam.getHusband() != null){
				if(indiMap.containsKey(fam.getHusband())){
					Individual husb = indiMap.get(fam.getHusband());
					System.out.println("Husband Name : "+husb.getName());
				}
			}
			if(fam.getWife() != null){
				if(indiMap.containsKey(fam.getWife())){
					Individual wife = indiMap.get(fam.getWife());
					System.out.println("Wife Name : "+wife.getName());
				}
			}
			System.out.println(fam.getMarriageDate());
			if(fam.getDivorceDate() != null){
				System.out.println("Divorce Date : "+fam.getDivorceDate());
			}
			if(fam.getChild() != null){
				System.out.println("Child : "+fam.getChild());
			}
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
							icounter = lineTokens[1];
							i.setId(lineTokens[1]);
							indiMap.put(lineTokens[1], i);
							IFcache = "I";
							break;
						case "FAM":
							f = new Family();
							fcounter = lineTokens[1];
							f.setId(lineTokens[1]);
							famMap.put(lineTokens[1], f);
							IFcache ="F";
							break;
						default:break;
						}
			
				}
				break;
			case "1":
				switch(IFcache){
					case "I":i = indiMap.get(icounter);
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
						
					case "F":f = famMap.get(fcounter);
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
					case "I":i = indiMap.get(icounter);
						switch(BDcache){
							case "B":i.setBirthDate(getDate(lineTokens[2]+lineTokens[3]+lineTokens[4]));break;
							case "D":i.setDeathDate(getDate(lineTokens[2]+lineTokens[3]+lineTokens[4]));break;
							default:break;
						}
						break;
					
					case "F": f = famMap.get(fcounter);
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

    	private static void printErrorsAnomalies(){
            ErrorAnomalyChecker errorAnomalyChecker = new ErrorAnomalyChecker(indiMap, famMap);
            errorAnomalyChecker.checkUS07();
            errorAnomalyChecker.checkUS08();
        }
	


}
