package Project03;

/*
 User Story  5 - Marriage before death
 User Story 6 - Divorce before death

 Author  - Nikita Dmello
 */
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class NErrorChecker {

	private static final String DateTimeFormat = null;
	private LinkedHashMap<String, Individual> iMap;
	private LinkedHashMap<String, Family> fMap;

	public NErrorChecker(LinkedHashMap<String, Individual> iMap,
			LinkedHashMap<String, Family> fMap) {
		super();
		this.iMap = iMap;
		this.fMap = fMap;
	}

	public void mar_div_BeforeDeath() {

		for (Entry<String, Family> fam : fMap.entrySet()) {
			Family f = fam.getValue();
			String husband = iMap.get(f.getHusband()).getName();
			String wife = iMap.get(f.getWife()).getName();
			Date hus_dd = iMap.get(f.getHusband()).getDeathDate();
			Date wife_dd = iMap.get(f.getWife()).getDeathDate();

			if (hus_dd != null) {
				if (hus_dd.before(f.getMarriageDate()))
					System.out.println("Anomaly US05 : " + husband + "("
							+ f.getHusband() + ")'s death is  before marriage");

				if (f.getDivorceDate() != null) {
					if (hus_dd.before(f.getDivorceDate()))
						System.out.println("Anomaly US06 : " + husband + "("
								+ f.getHusband()
								+ ")'s death is  before divorce");
				}

			}
			if (wife_dd != null) {
				if (wife_dd.before(f.getMarriageDate()))
					System.out.println("Anomaly US05 : " + wife + "("
							+ f.getWife() + ")'s death is  before marriage");
				if (f.getDivorceDate() != null) {
					if (wife_dd.before(f.getDivorceDate()))
						System.out.println("Anomaly US06 : " + wife + "("
								+ f.getWife() + ")'s death is  before divorce");

				}
			}

		}
	}

	public void parentsNotOld() {
		for(Entry<String, Family> fam : fMap.entrySet()){
			Family f = fam.getValue();
			Individual husband = iMap.get(f.getHusband());
			Individual wife = iMap.get(f.getWife());
			ArrayList<String> children = f.getChild();
			for(int i = 0; i<children.size();i++){
				Individual child = iMap.get(children.get(i));
				if(dateDifference(husband.getBirthDate(),child.getBirthDate())>80){
					System.out.println("Anomaly US12 : Father "+husband.getName()+" is more than 80 years old than "
							+ "child "+child.getName());
				}
				if(dateDifference(wife.getBirthDate(),child.getBirthDate(),"Y")>60){
					System.out.println("Anomaly US12 : Mother "+wife.getName()+" is more than 60 years old than "
							+ "child "+child.getName());				
					}
			}
		}
	}
	
	public long dateDifference(Date d, Date d1, String DMY){
		LocalDate ld = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate ld1 = d1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Period p = Period.between(ld,ld1);
		if(DMY.equals("D"))
		    return ChronoUnit.DAYS.between(ld, ld1);
		else if (DMY.equals("M"))
			return ChronoUnit.MONTHS.between(ld, ld1);
		else if(DMY.equals("Y"))
			return ChronoUnit.YEARS.between(ld, ld1);
		else if(DMY.equals("JD"))
			return p.getDays();
		else return 0;
	}
	
	public void uniqueNameBirthDate(){
		Set<String> checked = new HashSet<String>();
		for (Entry<String, Individual> indi1 : iMap.entrySet()){
			Individual i1 = indi1.getValue();
			for (Entry<String, Individual> indi2 : iMap.entrySet()){
				if(indi1.getKey()==indi2.getKey())
					continue;
				Individual i2 = indi2.getValue();
				if (i1.getName().equals(i2.getName())){
					if(i1.getBirthDate().equals(i2.getBirthDate())){
						if(checked.add(indi1.getKey())){
							if(checked.add(indi2.getKey()))
								System.out.println("Anomaly US23 : Individuals "+indi1.getKey()+" and "+indi2.getKey()+" have same name "+i1.getName()+" and birthdate");
						}
						else if(checked.add(indi2.getKey())){
							System.out.println("Anomaly US23 : Another individual "+indi2.getKey()+" have same name "+i1.getName()+" and birthdate");
						}
					}
				}
			}
		}
	}
	
	public void calculateAge(Individual i){
		Date d = new Date();
		if(i.getDeathDate() == null){
			System.out.println(" US27 Age : "+dateDifference(i.getBirthDate(),d,"Y")+"\n");
		}
		else{
			System.out.println(" US27 Age at death : " +dateDifference(i.getBirthDate(),i.getDeathDate(),"Y")+"\n");
		}
	}
	
	public void spouseAgeDiff(){
		for(Entry<String, Family> fam : fMap.entrySet()){
			Family f = fam.getValue();
			Individual husband = iMap.get(f.getHusband());
			Individual wife = iMap.get(f.getWife());
			long husband_age_at_marriage = dateDifference(husband.getBirthDate(),f.getMarriageDate(),"Y");
			long wife_age_at_marriage = dateDifference(wife.getBirthDate(), f.getMarriageDate(),"Y");
			
			if(husband_age_at_marriage>wife_age_at_marriage){
				if(wife_age_at_marriage == 0){
					if(husband_age_at_marriage > 2)
					System.out.println("Anomaly US34 : "+husband.getName()+"("+husband.getId()+") was more than twice as old as "+wife.getName()+"("+wife.getId()+") during their marriage");
				}
				else if(husband_age_at_marriage/wife_age_at_marriage >2){
					System.out.println("Anomaly US34 : "+husband.getName()+"("+husband.getId()+") was more than twice as old as "+wife.getName()+"("+wife.getId()+") during their marriage");
				}
			}
			else{
				if(husband_age_at_marriage == 0){
					if(wife_age_at_marriage > 2)
					System.out.println("Anomaly US34 : "+wife.getName()+"("+wife.getId()+") was more than twice as old as "+husband.getName()+"("+husband.getId()+") during their marriage");
				}
				else if (wife_age_at_marriage/husband_age_at_marriage >2){
					System.out.println("Anomaly US34 : "+wife.getName()+"("+wife.getId()+") was more than twice as old as "+husband.getName()+"("+husband.getId()+") during their marriage");
				}
			}
		}
	}
}
