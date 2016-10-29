package Project03;

import java.security.interfaces.RSAMultiPrimePrivateCrtKey;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.PriorityQueue;

public class BErrorChecker {

    private LinkedHashMap<String, Individual> individualMap;
    private LinkedHashMap<String, Family> familyMap;


    public BErrorChecker(LinkedHashMap<String, Individual> individualMap, LinkedHashMap<String, Family> familyMap){
        this.individualMap = individualMap;
        this.familyMap = familyMap;
    }
    
    public void check(){
    	checkUS07();
    	checkUS08();
    	checkUS10();
    	checkUS28();
    	checkUS31();
    }

    private void checkUS07(){
        for(Entry<String, Individual> indiMap: this.individualMap.entrySet()){
    		Individual indi = indiMap.getValue();
    		if(!indi.isUnder150YearsOld())
    			System.out.println("Anomaly US07: " + indi.name + "(" + indi.id + ")" + "'s age exceeds 150 years.");
    	}
    }

    private List<LocalDate> findParentMarriageAndDivorceDate(Individual indi){
        for(Entry<String, Family> familyEntry: familyMap.entrySet()){
            Family family = familyEntry.getValue();
            if(family.child.contains(indi.id)){
                Calendar calMarriage = Calendar.getInstance();
                calMarriage.setTime(family.marriageDate);
                LocalDate marriageDate = LocalDate.of(calMarriage.get(Calendar.YEAR), calMarriage.get(Calendar.MONTH)+1, calMarriage.get(Calendar.DATE));
                LocalDate divorceDate = null;
                if(family.divorceDate != null){
                    Calendar calDivorce = Calendar.getInstance();
                    calDivorce.setTime(family.divorceDate);
                    divorceDate = LocalDate.of(calDivorce.get(Calendar.YEAR), calDivorce.get(Calendar.MONTH)+1, calDivorce.get(Calendar.DATE));
                }

                return Arrays.asList(marriageDate, divorceDate);
            }
        }
        return Arrays.asList(null, null);
    }

    private void checkUS08(){
        for(Entry<String, Individual> indiEntry: individualMap.entrySet()){
            Individual indi = indiEntry.getValue();
            Calendar cal = Calendar.getInstance();
            cal.setTime(indi.birthDate);
            LocalDate indiBirthLocal = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE));
            List<LocalDate> marriageAndDivorceDate = findParentMarriageAndDivorceDate(indi);
            LocalDate parentMarriageDate = marriageAndDivorceDate.get(0);
            LocalDate parentDivorceDate = marriageAndDivorceDate.get(1);
            if(parentMarriageDate == null && parentDivorceDate == null)
                continue;

            Period childBirthAndParentMarriage = Period.between(parentMarriageDate, indiBirthLocal);
            if(childBirthAndParentMarriage.isNegative()){
                System.out.println("Anomaly US08: " + indi.name + "(" + indi.id + ")" + "'s birth " + Math.abs(childBirthAndParentMarriage.getYears()) + " year(s) before parents' merriage.");
                break;
            }
            if(parentDivorceDate == null)
                continue;
            Period childBirthAndParentDivorce = Period.between(indiBirthLocal, parentDivorceDate);
            if(childBirthAndParentDivorce.isNegative()){
                System.out.println("Anomaly US08: " + indi.name + "(" + indi.id + ")" + "'s birth after parents' divorce.");
            }
        }
    }
    
    private void checkUS10(){
    	String husbandId;
    	String wifeId;
    	Individual husband;
    	Individual wife;
    	for(Entry<String, Family> famEntry: this.familyMap.entrySet()){
    		husbandId = famEntry.getValue().husband;
    		wifeId = famEntry.getValue().wife;
    		husband = individualMap.get(husbandId);
    		wife = individualMap.get(wifeId);
    		Calendar calHusband = Calendar.getInstance();
    		calHusband.setTime(husband.birthDate);
    		Calendar calWife = Calendar.getInstance();
    		calWife.setTime(wife.birthDate);
    		LocalDate husbandBirthLocalDate = LocalDate.of(calHusband.get(Calendar.YEAR), calHusband.get(Calendar.MONTH)+1, calHusband.get(Calendar.DATE));
    		LocalDate wifeBirthLocalDate = LocalDate.of(calWife.get(Calendar.YEAR), calWife.get(Calendar.MONTH)+1, calWife.get(Calendar.DATE));

    		boolean isHusbandUnder14 = Period.between(husbandBirthLocalDate, LocalDate.now().minusYears(14)).isNegative();
    		boolean isWifeUnder14 = Period.between(wifeBirthLocalDate, LocalDate.now().minusYears(14)).isNegative();
    		
    		if(isHusbandUnder14 || isWifeUnder14){
    			System.out.printf("Error US10: One(or two) of the spouses from this family(%1s) are under 14 years old.\n", famEntry.getKey() );
    		}
    	}
    }

    private void checkUS28(){
    	for(Entry<String, Family> famEntry: this.familyMap.entrySet()){
    		Family family = famEntry.getValue();
    		PriorityQueue<Individual> priorityQueue = new PriorityQueue<>(new IndividualAgeComparator());
    		if(family.child.size()<2)
    			continue;
    		StringBuilder sb = new StringBuilder(String.format("US28: Family %1s has children:", family.id));
    		for(String id: family.child){
    			Individual child = this.individualMap.get(id);
    			priorityQueue.add(child);
    		}
    		
    		while(priorityQueue.peek() != null){
    			Individual indi = priorityQueue.poll();
    			Calendar cal = Calendar.getInstance();
        		cal.setTime(indi.birthDate);
        		LocalDate birthDate = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE));
        		Integer age = Period.between(birthDate, LocalDate.now()).getYears();
    			sb.append(String.format(" %1$s(%2$s) age %3$s,", indi.name, indi.id, age.toString()));
    		}
    		sb.deleteCharAt(sb.length()-1);
    		System.out.println(sb);
    	}
    }
    
    private void checkUS31(){
    	for(Entry<String, Individual> indiEntry: this.individualMap.entrySet()){
    		Individual indi = indiEntry.getValue();
    		Calendar cal = Calendar.getInstance();
    		cal.setTime(indi.birthDate);
    		LocalDate birthDate = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE));
    		int age = Period.between(birthDate, LocalDate.now()).getYears();
    		if(indi.FspouseId == null && age >= 30){
    			System.out.printf(String.format("US31: %1s(%2s) is over 30 and single.\n", indi.name, indi.id));
    		}
    	}
    }
}

class IndividualAgeComparator implements Comparator<Individual>{
	@Override
	public int compare(Individual i1, Individual i2){
		if(i1.birthDate.after(i2.birthDate)){
			return 1;
		}
		if(i1.birthDate.before(i2.birthDate)){
			return -1;
		}
		return 0;
	}
}
