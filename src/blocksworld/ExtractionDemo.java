package blocksworld;

import modelling.*;
import datamining.*;
import java.util.*;
import bwgenerator.*;
import bwgeneratordemo.*;

public class ExtractionDemo {

    	static int nbBlocks = 5; 
    	static int nbPiles = 5;   

    	public static void main(String[] args) {
        
		int n = 10000;
		Random random = new Random();
		BWGenerator generator = new BWGenerator(nbBlocks, nbPiles);
		generator.setClear(0, 0.5);    
		generator.setOnBlock(1, 0, 0.8);
		generator.setOnTable(2, 1, 0.9); 
		BooleanBuilder bwdb = new BooleanBuilder(nbBlocks, nbPiles);
		BooleanDatabase db = new BooleanDatabase(bwdb.getAllVariables());
		
		System.out.println("nb instances : " + n);

		for (int i = 0; i < n; i++) {
		    	List<List<Integer>> state = bwgeneratordemo.Demo.getState(random); 
		    	Set<BooleanVariable> stateVars = bwdb.instanceFromState(state);
		    	db.add(stateVars);
		}
		
		System.out.println("extraction of frequent itemsets\n");
		float minFreq = 2.0f / 3.0f;
		System.out.println("minFreq : " + minFreq * 100);
		long t0 = System.currentTimeMillis();
		Apriori apriori = new Apriori(db);
		Set<Itemset> frequentItemsets = apriori.extract(minFreq);
		long t1 = System.currentTimeMillis();
		
		System.out.println("extraction time : " + (t1 - t0) + " ms");
		System.out.println("frequent itemsets size : " + frequentItemsets.size() + "\n");
			
		
		System.out.println("extraction of rules\n");
		
		float minConf = 0.95f;
		System.out.println("minFreq : " + minFreq * 100);
		System.out.println("minConf : " + minConf * 100);
		
		long t2 = System.currentTimeMillis();
		BruteForceAssociationRuleMiner bfarm = new BruteForceAssociationRuleMiner(db);
		Set<AssociationRule> associationRules = bfarm.extract(minFreq, minConf);
		long t3 = System.currentTimeMillis();
		
		System.out.println("extraction time : " + (t3 - t2) + " ms");
		System.out.println("nb of rules : " + associationRules.size());
		System.out.println();
		
		if (!associationRules.isEmpty()) {
		    for (AssociationRule rule : associationRules) {
		        
		        String premisse = "";
		        for (BooleanVariable var : rule.getPremise()) {
		            if (!premisse.isEmpty()) premisse += ", ";
		            premisse += var.getName();
		        }
		        
		        String conclusion = "";
		        for (BooleanVariable var : rule.getConclusion()) {
		            if (!conclusion.isEmpty()) conclusion += ", ";
		            conclusion += var.getName();
		        }
		        
		        System.out.println("  (" + premisse + ") -> (" + conclusion + ")");
		        System.out.println("freq : " + (rule.getFrequency() * 100) + " confidence : " + (rule.getConfidence() * 100));
		    }
		    
		}
		System.out.println("inst : " + n);
		System.out.println("bool variables : " + bwdb.getAllVariables().size());
		System.out.println("frequentItemsets : " + frequentItemsets.size());
		System.out.println("associationRules: " + associationRules.size());
		
	       
	    }
}
