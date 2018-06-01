package apk.romero.debtor.javaClass.myLists;

import java.io.Serializable;
import java.util.Date;

//What defines a debt
public class Debt implements Serializable{
	
	private String person;
	private double amount;
	private String reason;
	private boolean iown;
	private Date date;
	private int id;
	
	public Debt(String person, double amount, String reason, boolean iown, int id) {
		this.person = person;
		this.amount = amount;
		this.reason = reason;
		this.iown = iown;
		this.date = new Date();
		this.id = id;
	}
	
	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}


	public boolean isIown() {
		return iown;
	}

	public void setIown(boolean iown) {
		this.iown = iown;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getId() {
		return id;
	}
	//no need set id because we cant change it	
}
