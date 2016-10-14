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
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

public class NErrorChecker {

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
				if(dateDifference(wife.getBirthDate(),child.getBirthDate())>60){
					System.out.println("Anomaly US12 : Mother "+wife.getName()+" is more than 60 years old than "
							+ "child "+child.getName());				
					}
			}
		}
	}
	
	public int dateDifference(Date d, Date d1){
		LocalDate ld = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate ld1 = d1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Period p = Period.between(ld, ld1);
		return p.getYears();
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
}
