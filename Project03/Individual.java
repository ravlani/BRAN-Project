package Project03;

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

public class Individual {
	String id;
	String name;
	String gender;
	Date birthDate;
	Date deathDate;
	String FspouseId;
	String FchildId;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public Date getDeathDate() {
		return deathDate;
	}
	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}
	public String getFspouseId() {
		return FspouseId;
	}
	public void setFspouseId(String fspouseId) {
		FspouseId = fspouseId;
	}
	public String getFchildId() {
		return FchildId;
	}
	public void setFchildId(String fchildId) {
		FchildId = fchildId;
	}
	@Override
	public String toString() {
		return "Individual id : " + id + ""
				+ "\n Name : " + name + ""
				+ "\n Gender : " + gender + ""
				+ "\n Birth Date : " + birthDate
				+ "\n Death Date : " + deathDate + ""
				+ "\n Family Spouse Id : " + FspouseId + ""
				+ "\n Family Child Id : " + FchildId;
	}
	
	public boolean isUnder150YearsOld(){
	    if(this.birthDate == null)
	        return false;
	    Calendar calBirth = Calendar.getInstance();
	    calBirth.setTime(this.birthDate);
            LocalDate localBirthDate = LocalDate.of(calBirth.get(Calendar.YEAR), calBirth.get(Calendar.MONTH)+1, calBirth.get(Calendar.DAY_OF_MONTH));
            LocalDate yearsAgo150 = LocalDate.now().minusYears(150);
            Period age = Period.between(yearsAgo150, localBirthDate);
            if(age.isNegative())
        	return false;
            return true;
	}
	
}
