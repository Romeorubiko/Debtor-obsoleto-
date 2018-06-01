package apk.romero.debtor.javaClass.deals;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;



public class DealColl {
	private String reason;
	private Date date;
	private double totalAmount;
	private List<DealIndv> deals;
	private List<PaymentIndv> payments;
	
	public DealColl(String reason, double totalAmount,
			List<PaymentIndv> payments) {
		super();
		this.reason = reason;
		date = new Date();
		this.totalAmount = totalAmount;
		deals = new LinkedList<DealIndv>();
		this.payments = payments;
	}
	
	private void addDeal(DealIndv d){
		deals.add(deals.size(), d);
	}
	
	public DealColl calculate(){
		
		class Balance{
			public String name;
			public double balance;
			public Balance(String name, double balance){
				this.name = name;
				this.balance = balance;
			}
		};
		
		class ComparatorBalance implements Comparator<Balance> {
			  public int compare(Balance a, Balance b) {
				  if (a.balance-b.balance > 0 && a.balance > 0 && b.balance > 0 
						  || a.balance-b.balance < 0 && a.balance < 0 && b.balance < 0)return 1;
			    return -1;
			  }
		};
		
		Stack<Balance> positive = new Stack<>();
		Stack<Balance> negative = new Stack<>();
				
		for (PaymentIndv p : payments) {
			double balance = p.getOwe() - p.getPaid();
			if(balance > 0)  positive.push(new Balance(p.getName(), balance));
			if(balance < 0)  negative.push(new Balance(p.getName(), balance));
		}
		Collections.sort(positive, new ComparatorBalance());
		Collections.sort(negative, new ComparatorBalance());
				
		double change = 0;
		for (PaymentIndv p : payments) {
			change += p.getPaid();
		}
		
		change = change - totalAmount;
		if (change!=0) positive.push(new Balance("change", change));
		
		//calculate deals
		while (!positive.isEmpty()){
			Balance pos, neg;
			pos = positive.pop();
			neg = negative.pop();
			double result = pos.balance+neg.balance;
			if (result > 0){
				addDeal(new DealIndv(pos.name, neg.name, Math.rint(-neg.balance*100)/100));
				positive.push(new Balance(pos.name, result));
			}
			else if (result < 0){
				addDeal(new DealIndv(pos.name, neg.name, Math.rint(pos.balance*100)/100));
				negative.push(new Balance(neg.name, result));
			}
			else if (result == 0){
				addDeal(new DealIndv(pos.name, neg.name, Math.rint(pos.balance*100)/100));
			}
		}
		return this;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason){
		this.reason = reason;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount){
		this.totalAmount = totalAmount;
	}

	//no need to setters because we cannot change this class information
	public Date getDate() {
		return date;
	}

	public List<DealIndv> getDeals() {
		return deals;
	}

	public List<PaymentIndv> getPayments() {
		return payments;
	}
	
		
}
