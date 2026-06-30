package blocksworld;

import modelling.*;
import java.util.*;

public class BooleanBuilder {

    	public final int nbBlocks;
    	public final int nbPiles;
    	public final Set<BooleanVariable> variables;  
    	
    	public BooleanBuilder(int nbBlocks, int nbPiles) {
        	this.nbBlocks = nbBlocks;
        	this.nbPiles = nbPiles;
        	this.variables = buildVariables();
    	}

    	public BooleanVariable var(String name) {
		for (BooleanVariable v : this.variables) {
		    	if (v.getName().equals(name)) return v;
		}
		throw new IllegalArgumentException("Variable not found: " + name);
    	}

    	public Set<BooleanVariable> buildVariables() {
		Set<BooleanVariable> vars = new HashSet<>();

		for (int b = 0; b < this.nbBlocks; b++) {
		    	for (int bp = 0; bp < nbBlocks; bp++) {
		        	if (b == bp) continue;
		        	vars.add(new BooleanVariable("on_" + b + "_" + bp));
		    	}
		}

		for (int b = 0; b < this.nbBlocks; b++) {
		    	for (int p = 0; p < nbPiles; p++) {
		        	vars.add(new BooleanVariable("onTable_" + b + "_" + p));
		    	}
		}

		for (int b = 0; b < this.nbBlocks; b++) {
		    	vars.add(new BooleanVariable("fixed_" + b));
		}

		for (int p = 0; p < this.nbPiles; p++) {
		    	vars.add(new BooleanVariable("free_" + p));
		}

		return vars;
    	}

    	public Set<BooleanVariable> getAllVariables() {
        	return this.variables;
    	}

    	public Set<BooleanVariable> instanceFromState(List<List<Integer>> piles) {

        	Set<BooleanVariable> inst = new HashSet<>();

        	//on_b_b'
        	for (int p = 0; p < this.nbPiles; p++) {
            		List<Integer> pile = piles.get(p);

		    	for (int i = 1; i < pile.size(); i++) {
				int b  = pile.get(i);
				int bp = pile.get(i - 1);
				inst.add(var("on_" + b + "_" + bp));
		    	}
        	}

        	//onTable_b_p
        	for (int p = 0; p < this.nbPiles; p++) {
            		List<Integer> pile = piles.get(p);
            	
            		if (!pile.isEmpty()) {
                		int top = pile.get(pile.size() - 1);
                		inst.add(var("onTable_" + top + "_" + p));
            		}
        	}

        	//fixed_b
        	for (int b = 0; b < this.nbBlocks; b++) {
            		boolean fixed = false;

            		for (List<Integer> pile : piles) {
                		int idx = pile.indexOf(b);
                			if (idx > 0) {
                    			fixed = true;
                		}
            		}

            		if (fixed) inst.add(var("fixed_" + b));
        	}

        	//free_p
        	for (int p = 0; p < this.nbPiles; p++) {
            		if (piles.get(p).isEmpty()) {
                		inst.add(var("free_" + p));
            		}
        	}

        	return inst;
    	}
}
