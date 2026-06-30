package datamining;

import java.util.*;
import modelling.*;

public abstract class AbstractItemsetMiner implements ItemsetMiner{
	public BooleanDatabase base;
	public static final Comparator<BooleanVariable> COMPARATOR =
(var1, var2) -> var1.getName().compareTo(var2.getName());
	
	public AbstractItemsetMiner(BooleanDatabase base){
		this.base = base;
	}

	public BooleanDatabase getDatabase(){
		return this.base;
	}
	
	public float frequency(Set<BooleanVariable> items){
		int size = this.base.getTransactions().size();
		int count = 0;
		if (size==0) return 0f;
		
		for(Set<BooleanVariable> tx : base.getTransactions()){
			if (tx.containsAll(items)){
				count++;
			}
		}
		if(count==0) return 0f;
		return (float) count/size;
	}
	
	

}
