package Project03
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class RErrorChecker {
	private LinkedHashMap<String, Individual> individual_Map;
	private LinkedHashMap<String, Family> family_Map;
	HashMap<String, Date> m = new HashMap<String, Date>();
	
	public RErrorChecker(LinkedHashMap<String, Individual> individual_Map, LinkedHashMap<String, Family> family_Map){
		this.individual_Map = individual_Map;
		this.family_Map = family_Map;
	
	}
	public void user_stories(){
		for(Entry<String, Individual> individual : individual_Map.entrySet()) {
			Date currentDate = new Date();
			if(currentDate.before(individual.getValue().getBirthDate())){
				String name = individual.getValue().getName();
				System.out.println("Anamoly US01: "+name+ "("+individual.getValue().getId()+")birth is before current date");
				
			}
		}
		for(Entry<String, Family> family : family_Map.entrySet()) {
			Family family1 = family.getValue();
			String husband = individual_Map.get(family1.getHusband()).getName();
			String wife = individual_Map.get(family1.getWife()).getName();
			Date birth_hushband = individual_Map.get(family1.getHusband()).getBirthDate();
			Date birth_wife = individual_Map.get(family1.getWife()).getBirthDate();
			if(birth_hushband != null){
				if(family1.getMarriageDate().before(birth_hushband)){
					if(!m.containsKey(husband))
					{
					System.out.println("Anamoly US02: "+husband+"("+family1.getHusband()+") birth is before marriage");	
					m.put(husband, birth_hushband);
					}	
				}
				if(birth_wife != null){
					if(family1.getMarriageDate().before(birth_wife))
						if(!m.containsKey(wife))
						{
							System.out.println("Anomaly US02 : "+wife+"("+family1.getWife()+") birth is  before marriage");
							m.put(wife, birth_wife);
						}			
			}
		}
	}
	}
}
	
