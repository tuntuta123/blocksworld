package blocksworld;


import java.util.*;
import modelling.*;

public class BlocksWorldCSP{
		
	public int nbBlocks;
	public int nbPiles;
	public Set<Variable> variables;
	public Set<Constraint> constraints;
	
	public BlocksWorldCSP(int nbPiles, int nbBlocks){
		this.nbBlocks = nbBlocks;
		this.nbPiles = nbPiles;
		this.variables = new BlocksWorldVariables(nbPiles, nbBlocks).getVariables();
		this.constraints = new HashSet<>();
		buildConstraints();
	}
	
	public Variable findVar(String s){
		for (Variable v : this.variables){
			if(v.getName().equals(s)) return v;	
		}
		
		throw new IllegalArgumentException("Variable not found " +s);
	}
	
	public void buildConstraints(){
		for(int b = 0 ; b < this.nbBlocks ; b++){
			Variable on_b = findVar("on_" + b);
			for(int b2 = b+1 ; b2 < this.nbBlocks ; b2++){
				Variable on_b2 = findVar("on_" + b2);
				this.constraints.add(new DifferenceConstraint(on_b,on_b2));
			}
		}
		
		for(int b = 0 ; b < this.nbBlocks ; b++){
			Variable on_b = findVar("on_" + b);
			for(int b2 = 0 ; b2 < this.nbBlocks ; b2++){
				if (b2 == b) continue;
				Variable fixed_b2 = findVar("fixed_" + b2);
				
				Set<Object> s1 = new HashSet<>();
				s1.add(fixed_b2);
				
				Set<Object> s2 = new HashSet<>();
				s2.add(true);
				
				this.constraints.add(new Implication(on_b, s1, fixed_b2, s2));
			}
		}
		
		//her blok b ve pile p icin onb = -(p+1) ise freep false
		
		for(int b = 0 ; b < this.nbBlocks ; b++){
			Variable on_b = findVar("on_" + b);
			for(int p = 0 ; p < this.nbPiles ; p++){
				Variable free_p = findVar("free_" + p);
				
				Set<Object> s1 = new HashSet<>();
				s1.add(-(p+1));
				
				Set<Object> s2 = new HashSet<>();
				s2.add(false);
				
				this.constraints.add(new Implication(on_b, s1, free_p, s2));
			}
		}
	}
		
	public Set<Constraint> getConstraints(){
		return this.constraints;
	}
	
	public Set<Variable> getVariables(){
		return this.variables;
	}
	public int getNbBlocks(){
		return this.nbBlocks;
	}
		
}
