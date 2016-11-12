package Project03;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

class AErrorChecker {
	private LinkedHashMap<String, Individual> indiMap;
	private LinkedHashMap<String, Family> famMap;
	private HashMap<String, Family> indiMarraige = new HashMap<String, Family>();
	private HashMap<String, Date> us32MultiBirth = new HashMap<String, Date>();
	
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
			
			//For User Story 35
			
			if(us35(set.getValue().getBirthDate())){
				System.out.println("US35: "+set.getValue().getName()+"("+set.getValue().getId()+") was born recently.");
			}
			
			if(set.getValue().getDeathDate() != null){
				if(us36(set.getValue().getDeathDate())){
					System.out.println("US36: "+set.getValue().getName()+"("+set.getValue().getId()+") died recently.");
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
			
			//For User Story 11
			us11(set);
			
			//For User Story 21
			if(!us21(set.getValue().getHusband(), "M")){
				String husband = indiMap.get(set.getValue().getHusband()).getName();
				System.out.println("Error: US21 : "+husband+"("+set.getValue().getHusband()+")'s gender for his role shoudld be (M) instead of (F)" );
				
			}
			if(!us21(set.getValue().getWife(), "F")){
				String wife= indiMap.get(set.getValue().getWife()).getName();
				System.out.println("Error: US21 : "+wife+"("+set.getValue().getWife()+")'s gender for her role shoudld be (F) instead of (M)" );
			}
			
			//For User Story 32
			us32(set.getValue());
			//For User Story 33
			us33(set.getValue());
			
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
	
	public void us11(Entry<String, Family> set){
		if(indiMarraige.containsKey(set.getValue().getHusband())){
			Family famData = indiMarraige.get(set.getValue().getHusband());
			Date currentMarraigeDate = set.getValue().getMarriageDate();
			String husband = indiMap.get(set.getValue().getHusband()).getName();
			Date WifesDeath = indiMap.get(famData.getWife()).getDeathDate();
			if(WifesDeath != null){
					if(currentMarraigeDate.before(WifesDeath)){
						System.out.println("Error: US11 : "+husband+"("+set.getValue().getHusband()+") has a Bigamy in Families "+set.getValue().getId()+" & "+famData.getId());
					}
			}
			if(famData.getDivorceDate() != null){
				if(currentMarraigeDate.before(famData.getDivorceDate())){
					System.out.println("Error: US11 : "+husband+"("+set.getValue().getHusband()+") has a Bigamy in Families "+set.getValue().getId()+" & "+famData.getId());
				}
			}
		}
		else{
			indiMarraige.put(set.getValue().getHusband(), set.getValue());
		}
		
		if(indiMarraige.containsKey(set.getValue().getWife())){
			Family famData = indiMarraige.get(set.getValue().getWife());
			Date currentMarraigeDate = set.getValue().getMarriageDate();
			Date HusbandsDeath = indiMap.get(famData.getHusband()).getDeathDate();
			String wife = indiMap.get(set.getValue().getWife()).getName();
			if(HusbandsDeath != null){
				if(currentMarraigeDate.before(HusbandsDeath)){
					System.out.println("Error: US11 : "+wife+"("+set.getValue().getWife()+") has a Bigamy in Families "+set.getValue().getId()+" & "+famData.getId());
				}
			}
			if(famData.getDivorceDate() != null){
				if(currentMarraigeDate.before(famData.getDivorceDate())){
					System.out.println("Error: US11 : "+wife+"("+set.getValue().getWife()+") has a Bigamy in Families "+set.getValue().getId()+" & "+famData.getId());
				}
			}
		}
		else
		{
			indiMarraige.put(set.getValue().getWife(), set.getValue());
		}
	}//End User Story 11
	
	public void us32(Family fam){
		ArrayList<String> children = new ArrayList<String>();
		children = fam.getChild();
		if(children.size()>1){
			for(int i=0;i<children.size();i++){
				for(int j=i+1;j<children.size();j++){
					Individual child1 = indiMap.get(children.get(i));
					Individual child2 = indiMap.get(children.get(j));
					if(child1.getBirthDate().equals(child2.getBirthDate())){
						System.out.println("Anamoly: US32: Family ("+fam.getId()+") has multiple births with child ("+child1.getId()+") and child ("+child2.getId()+")");
					}
				}
			}
		}
	}
	
	public void us33(Family fam){
		ArrayList<String> children = new ArrayList<String>();
		children = fam.getChild();
		for(int i=0;i<children.size();i++){
			Individual husIndi = indiMap.get(fam.getHusband());
			Individual wifeIndi = indiMap.get(fam.getWife());
			Individual childIndi = indiMap.get(children.get(i));
			if(husIndi.getDeathDate() != null && wifeIndi.getDeathDate() != null){
				if(getDiffYears(childIndi.getBirthDate(), new Date()) < 18){
					System.out.println("Anamoly: US33: Child ("+children.get(i)+") in family ("+fam.getId()+") is orphan.");
				}
			}
		}
	}
	
	public boolean us21(String ID, String role){
		return indiMap.get(ID).getGender().equals(role);
	}
	public String formatDate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		return format1.format(cal.getTime());
	}
	
	public boolean us35(Date bdate){
		Date currentDate = new Date();
		long diffInMilli = currentDate.getTime() - bdate.getTime();
		long daysDiff = TimeUnit.DAYS.convert(diffInMilli, TimeUnit.MILLISECONDS);
		if(daysDiff >=0 && daysDiff <= 30){
			return true;
		}
		return false;
	}
	
	public boolean us36(Date ddate){
		Date currentDate = new Date();
		long diffInMilli = currentDate.getTime() - ddate.getTime();
		long daysDiff = TimeUnit.DAYS.convert(diffInMilli, TimeUnit.MILLISECONDS);
		if(daysDiff >=0 && daysDiff <= 30){
			return true;
		}
		return false;
	}
	
	//Utils
	
	public static int getDiffYears(Date first, Date last) {
	    Calendar a = getCalendar(first);
	    Calendar b = getCalendar(last);
	    int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
	    if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) || 
	        (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
	        diff--;
	    }
	    return diff;
	}

	public static Calendar getCalendar(Date date) {
	    Calendar cal = Calendar.getInstance(Locale.US);
	    cal.setTime(date);
	    return cal;
	}
	
}
