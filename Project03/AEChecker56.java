package Project03_LHM;

/*
 User Story  5 - Marriage before death
 User Story 6 - Divorce before death
 
 Author  - Nikita Dmello
 */
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class AEChecker56 {
	
	private LinkedHashMap<String, Individual> iMap;
	private LinkedHashMap<String, Family> fMap;


	public AEChecker56(LinkedHashMap<String, Individual> iMap, LinkedHashMap<String, Family> fMap) {
		super();
		this.iMap = iMap;
		this.fMap = fMap;
	}


	public void mar_div_BeforeDeath(){
		
		for(Entry<String,Family> fam : fMap.entrySet()){
			Family f  = fam.getValue();
			String husband = iMap.get(f.getHusband()).getName();
			String wife = iMap.get(f.getWife()).getName();
			Date hus_dd = iMap.get(f.getHusband()).getDeathDate();
			Date wife_dd = iMap.get(f.getWife()).getDeathDate();
			
			
			if(hus_dd != null){
				if(hus_dd.before(f.getMarriageDate()))
					System.out.println("Anomaly US05 : Husband's ("+husband+" - "+f.getHusband()+") death before marriage");
				
				if(f.getDivorceDate() != null){
					if(hus_dd.before(f.getDivorceDate()))
					System.out.println("Anomaly US06 : Husband's ("+husband+" - "+f.getHusband()+") death before divorce");
				}
				
			}
			if(wife_dd != null){
				if(wife_dd.before(f.getMarriageDate()))
					System.out.println("Anomaly US05 : Wife's ("+wife+" - "+f.getWife()+") death before marriage");
				if(f.getDivorceDate() != null){
					if(wife_dd.before(f.getDivorceDate()))
						System.out.println("Anomaly US06 : Wife's ("+wife+" - "+f.getWife()+") death before divorce");
				
				}
			}	
			
		}
	}
	
	
	
}
