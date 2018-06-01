package apk.romero.debtor.javaClass.deals;

public class DealIndv {
	private String from, to;
	private double amount;
	
	public DealIndv(String from, String to, double amount) {
		this.from = from;
		this.to = to;
		this.amount = amount;
	}
	//no need to put setters
	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public double getAmount() {
		return amount;
	}
	
}
