package Project03;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
					System.out.println("Error: US03 : "+set.getValue().getName()+"("+set.getValue().getId()+")'s Death Date "+formatDate(set.getValue().getDeathDate())+" is before Birth Date "+formatDate(set.getValue().getBirthDate())+"." );
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
					System.out.println("Error: US04 : "+husband+"("+set.getValue().getHusband()+")'s and "+wife+"("+set.getValue().getWife()+")'s divorce date "+formatDate(set.getValue().getDivorceDate())+" is before their marriage date "+formatDate(set.getValue().getMarriageDate())+" in Family ("+set.getValue().getId()+")." );
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
	
	public String formatDate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		return format1.format(cal.getTime());
	}
	
}
