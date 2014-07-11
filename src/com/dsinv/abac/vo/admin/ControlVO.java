package com.dsinv.abac.vo.admin;

import java.util.List;

import com.dsinv.abac.entity.Control;

public class ControlVO {

	private List<Control> controls;
	private String comment;
	private String involvedPerson;
	
	// tab information
    private String tab1 = "\n\nTransaction Details";
    private String tab2 = "\n\nControls";
    private String tab3 = "\n\nAudit Trail"; 
    private String tab1Selected = "\n\nTransaction Details";
    private String tab2Selected = "\n\nControls";
    private String tab3Selected = "\n\nAudit Trail"; 

	public List<Control> getControls() {
		return controls;
	}
	
	public void setControls(List<Control> controls) {
		this.controls = controls;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getInvolvedPerson() {
		return involvedPerson;
	}
	
	public void setInvolvedPerson(String involvedPerson) {
		this.involvedPerson = involvedPerson;
	}
	
	public String getTab1() {
		return tab1;
	}
	public void setTab1(String tab1) {
		this.tab1 = tab1;
	}
	
	public String getTab2() {
		return tab2;
	}
	
	public void setTab2(String tab2) {
		this.tab2 = tab2;
	}
	public String getTab3() {
		return tab3;
	}
	
	public void setTab3(String tab3) {
		this.tab3 = tab3;
	}
	
	public String getTab1Selected() {
		return tab1Selected;
	}
	
	public void setTab1Selected(String tab1Selected) {
		this.tab1Selected = tab1Selected;
	}
	
	public String getTab2Selected() {
		return tab2Selected;
	}
	
	public void setTab2Selected(String tab2Selected) {
		this.tab2Selected = tab2Selected;
	}
	
	public String getTab3Selected() {
		return tab3Selected;
	}
	
	public void setTab3Selected(String tab3Selected) {
		this.tab3Selected = tab3Selected;
	}
}
