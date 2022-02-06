package models;

public class LocationStats {
	private String State;
	private String country;
	private int latesTotalCases;
	private int diffFromPrevDay;

	public int getDiffFromPrevDay() {
		return diffFromPrevDay;
	}

	public void setDiffFromPrevDay(int diffFromPrevDay) {
		this.diffFromPrevDay = diffFromPrevDay;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getLatesTotalCases() {
		return latesTotalCases;
	}

	public void setLatesTotalCases(int latesTotalCases) {
		this.latesTotalCases = latesTotalCases;
	}

}
