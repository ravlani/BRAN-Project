package Project03;

import java.util.ArrayList;
import java.util.Date;

public class Family {
	String id;
	String husband;
	String wife;
	Date marriageDate;
	Date divorceDate;
	ArrayList<String> child ;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHusband() {
		return husband;
	}
	public void setHusband(String husband) {
		this.husband = husband;
	}
	public String getWife() {
		return wife;
	}
	public void setWife(String wife) {
		this.wife = wife;
	}
	public Date getMarriageDate() {
		return marriageDate;
	}
	public void setMarriageDate(Date marriageDate) {
		this.marriageDate = marriageDate;
	}
	public Date getDivorceDate() {
		return divorceDate;
	}
	public void setDivorceDate(Date divorceDate) {
		this.divorceDate = divorceDate;
	}
	public ArrayList<String> getChild() {
		return child;
	}
	public void setChild(String child) {
		if(this.child == null){
			this.child = new ArrayList<String>();
		}
		this.child.add(child);
	}
	@Override
	public String toString() {
		return "Family id : " + id + ""
				+ "\n Husband id : " + husband + ""
				+ "\n Wife id : " + wife + ""
				+ "\n Marriage Date : " + marriageDate
				+ "\n Divorce Date : " + divorceDate + ""
				+ "\n Child : " + child + "\n";
	}
	
	
	
	
}
