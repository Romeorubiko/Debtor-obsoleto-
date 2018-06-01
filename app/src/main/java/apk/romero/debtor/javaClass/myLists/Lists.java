package apk.romero.debtor.javaClass.myLists;

import android.content.res.Resources;
import android.util.Log;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import apk.romero.debtor.R;
import apk.romero.debtor.ui.Mylists;

//Store the two lists that we need and manage them (add, remove, search...)
public class Lists implements Serializable{
	private List<Debt> iown, theyown;
	private LinkedList<Integer> idFree;
	private int id; // we need an id for make easier the search and distinction
	
	public Lists(){
		this.iown = new LinkedList<Debt>();
		this.theyown = new LinkedList<Debt>();
		this.idFree = new LinkedList<Integer>();
		this.id = 0;
	}
	
	public List<Debt> getIown() {
		return iown;
	}

	public void setIown(LinkedList<Debt> iown) {
		this.iown = iown;
	}

	public void setTheyown(LinkedList<Debt> theyown) {
		this.theyown = theyown;
	}

	public List<Debt> getTheyown() {
		return theyown;
	}

	public void addDebt(String person, double amount, String reason){
		this.iown.add(0,new Debt(person, (double)Math.round(amount*100)/100, reason, true, getId()));
	}

	public void addRight(String person, double amount, String reason){
		this.theyown.add(0,new Debt(person, (double)Math.round(amount*100)/100, reason, false, getId()));
	}
	
	public int remove(int id){
		for (Debt debt : iown) {
			if (debt.getId()==id){
				iown.remove(debt);
				idFree.addLast(id);
				return id;
			}
		}
		for (Debt debt : theyown) {
			if (debt.getId()==id){
				theyown.remove(debt);
				idFree.addLast(id);
				return id;
			}
		}
		return 0;
	}
	
	public void removeAll(){
		this.iown = new LinkedList<Debt>();
		this.theyown = new LinkedList<Debt>();
		this.idFree = new LinkedList<Integer>();
		this.id = 0;
	}
	
	public Debt search(int id){
		for (Debt debt : iown) if (debt.getId()==id) return debt;
		for (Debt debt : theyown) if (debt.getId()==id) return debt;
		return null;

	}
	
	public void setAmount(int id, double amount){
		search(id).setAmount((double)Math.round(amount*100)/100);
	}
	
	public void setReason(int id, String reason){
		search(id).setReason(reason);
	}
	
	private int getId(){ //get an id that it is not currently in the lists
		if (idFree.isEmpty()){
			id++;
			return id;
		}
		else {
			int newid = idFree.getFirst();
			idFree.removeFirst();
			return newid;
		}
	}
	
	public String toString(){
		String text = "";
		text+="I own:";
		for (Debt debt : iown) {
			text += "\n\n{\n" + debt.getPerson();
			text += "\n" + debt.getAmount();
			text += "\n" + debt.getReason();
			text += "\n" + debt.getDate();
			text += "\n}";
		}
		text+="\n\nThey own:";
		for (Debt debt : theyown) {
			text += "\n\n{\n" + debt.getPerson();
			text += "\n" + debt.getAmount();
			text += "\n" + debt.getReason();
			text += "\n" + debt.getDate();
			text += "\n}";
		}
		return text;
	}
}
