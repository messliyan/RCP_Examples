package gef_model;

public class Enterprise extends Node {
	
	public static final String PROPERTY_CAPITAL = "EnterpriseCapital";
	
	private String address;
	private int capital;
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setCapital(int capital) {
		this.capital = capital;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public int getCapital() {
		return this.capital;
	}
}