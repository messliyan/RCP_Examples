package gef_model;

public class Employee extends Node {
	public static final String PROPERTY_FIRSTNAME = "EmployeePrenom";
	private String prenom;
	
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	public String getPrenom() {
		return this.prenom;
	}
}