package apk.romero.debtor.javaClass.deals;

import java.util.LinkedList;
import java.util.List;


public class PaymentColl {
	private double totalAmount;
	private List<PaymentIndv> list;
	private String reason;
	
	public PaymentColl(){
		totalAmount = 0;
		list = new LinkedList<PaymentIndv>();
	}
	
	public void add(PaymentIndv p){
		list.add(list.size(),p);
	}
	
	public void remove(int pos){
		list.remove(pos);
	}
	
	public DealColl calculate(){
		return (new DealColl(reason, totalAmount, list)).calculate();
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public List<PaymentIndv> getList(){
		return list;
	}
}
