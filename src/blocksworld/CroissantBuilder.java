package blocksworld;

import java.util.*;
import modelling.*;

public class CroissantBuilder{

	private final Set<Variable> variables;
    	private final int nbBlocks;

	public CroissantBuilder(BlocksWorldCSP world){
		this.variables = world.getVariables();
		this.nbBlocks = world.getNbBlocks();
	}
	
	public Variable findVar(String s){
		for (Variable v : this.variables){
			if(v.getName().equals(s)) return v;	
		}
		
		throw new IllegalArgumentException("Variable not found " +s);
	}
	
	public Set<Constraint> getRegularity(){
		Set<Constraint> constraints = new HashSet<>(); //list of not allowed actions
		
		for(int b = 0 ; b < this.nbBlocks ; b++){
			Variable on_b = findVar("on_" + b);
			for(int bDiff = 0 ; bDiff < this.nbBlocks ; bDiff++){
				if (bDiff == b) continue;
				//this time we only check if the block is bigger
				if (bDiff >= b){
					Set<Object> bad = new HashSet<>();
            				bad.add(bDiff);
            				constraints.add(new Implication(on_b, bad, on_b, new HashSet<>())); //always impossible bc false || false 
				}
			}
		}
		return constraints;
	}
}
