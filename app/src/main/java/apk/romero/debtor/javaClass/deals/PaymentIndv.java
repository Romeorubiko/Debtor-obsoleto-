package apk.romero.debtor.javaClass.deals;

public class PaymentIndv {
	private double paid, owe;
	private String name;
	
	public PaymentIndv(double paid, double owe, String name) {
		this.paid = paid;
		this.owe = owe;
		this.name = name;
	}

	public double getPaid() {
		return paid;
	}

	public void setPaid(double paid) {
		this.paid = paid;
	}

	public double getOwe() {
		return owe;
	}

	public void setOwe(double owe) {
		this.owe = owe;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
