package datamining;

import java.util.*;
import modelling.*;

public abstract class AbstractAssociationRuleMiner implements AssociationRuleMiner{
	
	public BooleanDatabase database;

	
	public AbstractAssociationRuleMiner(BooleanDatabase db){
		this.database = db;
	}
	
	@Override
	
	public BooleanDatabase getDatabase(){
		return this.database;
	}
	
	public static float frequency(Set<BooleanVariable> items ,Set<Itemset> itemsets){
		for (Itemset it : itemsets) {
            		if (it.getItems().equals(items)) {
                		return it.getFrequency();
            		}
        	}
        	throw new IllegalArgumentException("itemset not found");
	}
	
	public static float confidence(Set<BooleanVariable> premise,
                                   Set<BooleanVariable> conclusion,
                                   Set<Itemset> itemsets) {

        	Set<BooleanVariable> union = new HashSet<>(premise);
        	union.addAll(conclusion);

        	float fPremise = frequency(premise, itemsets);
        	float fUnion   = frequency(union, itemsets);

        	if (fPremise == 0f) {
            		throw new IllegalArgumentException("freq 0");
        	}

        	return fUnion / fPremise;
    	}

}
