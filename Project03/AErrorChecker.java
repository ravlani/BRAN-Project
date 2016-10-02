package Project03;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

class AErrorChecker {
	private LinkedHashMap<String, Individual> indiMap;
	private LinkedHashMap<String, Family> famMap;
	
	public AErrorChecker(LinkedHashMap<String, Individual> indiMap, LinkedHashMap<String, Family> famMap){
		this.indiMap = indiMap;
		this.famMap = famMap;
	}
	
	public void runLoop(){
		//Loop for Individuals
		for(Entry<String, Individual> set : indiMap.entrySet()){
			//For User story 03
			if(set.getValue().getDeathDate() != null){
				if(!us03(set.getValue().getDeathDate(), set.getValue().getBirthDate())){
					System.out.println("Anamoly US03 : "+set.getValue().getName()+"("+set.getValue().getId()+")'s Death Date is before Birth Date." );
				}
			}
			
		}
		
		//Loop for Family
		for(Entry<String, Family> set : famMap.entrySet()){
			//For User story 04
			if(set.getValue().getDivorceDate() != null){
				if(!us04(set.getValue().getDivorceDate(), set.getValue().getMarriageDate())){
					String husband = indiMap.get(set.getValue().getHusband()).getName();
					String wife = indiMap.get(set.getValue().getWife()).getName();
					System.out.println("Anamoly US04 : "+husband+"("+set.getValue().getHusband()+")'s and "+wife+"("+set.getValue().getWife()+")'s divorce is before their marriage." );
				}
			}
			
		}
	}
	
	public boolean us03(Date deathDate, Date birthDate){
		if(deathDate.before(birthDate)){
			return false;
		}
		return true;
	}
	
	public boolean us04(Date divorceDate, Date marrDate){
		if(divorceDate.before(marrDate)){
			return false;
		}
		return true;
	}
	
}
