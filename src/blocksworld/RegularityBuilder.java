package blocksworld;

import java.util.*;
import modelling.*;

public class RegularityBuilder{

	private final Set<Variable> variables;
    	private final int nbBlocks;
    	private final int diff;

	public RegularityBuilder(BlocksWorldCSP world, int diff){
		this.variables = world.getVariables();
		this.nbBlocks = world.getNbBlocks();
        	this.diff = diff;
	}
	
	public Variable findVar(String s){
		for (Variable v : this.variables){
			if(v.getName().equals(s)) return v;	
		}
		
		throw new IllegalArgumentException("Variable not found " +s);
	}
	
	public Set<Constraint> getRegularity(){
		Set<Constraint> constraints = new HashSet<>();
		
		for(int b = 0 ; b < this.nbBlocks ; b++){
			Variable on_b = findVar("on_" + b);
			for(int bDiff = 0 ; bDiff < this.nbBlocks ; bDiff++){
				if (bDiff == b) continue;
				int d = b - bDiff; //test b - b'
					
				if (d != this.diff){
					//adding a constraitn (type imp.) here to highlt the roadblock
					Set<Object> bad = new HashSet<>();
            				bad.add(bDiff);
            				constraints.add(new Implication(on_b, bad, on_b, new HashSet<>())); //always impossible bc false || false 
				}
			}
		}
		return constraints;
	}
}
