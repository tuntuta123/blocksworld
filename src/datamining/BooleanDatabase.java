package datamining;

import java.util.*;
import modelling.*;

public class BooleanDatabase{
	
	public Set<BooleanVariable> items;
	public ArrayList<Set<BooleanVariable> > transactions;
	
	public BooleanDatabase(Set<BooleanVariable> items){
		this.items = items;
		this.transactions = new ArrayList<>();
	}
	
	
	
	public void add(Set<BooleanVariable> tx){
		if(!items.containsAll(tx)){
			throw new IllegalArgumentException("at least one argument in tx not present in items");
		}
		this.transactions.add(tx);
	}
	
	public Set<BooleanVariable> getItems(){
		return this.items;
	}
	public List<Set<BooleanVariable>> getTransactions(){
		return this.transactions;
	}
	
	
}
