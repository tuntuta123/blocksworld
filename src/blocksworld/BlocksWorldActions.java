package blocksworld;

import modelling.*;
import planning.*;
import java.util.*;

public class BlocksWorldActions{
	
	public int nbPiles;
	public int nbBlocks;
	public Set<Variable> variables;
	public Set<Action> actions;
	
	public BlocksWorldActions(int nbPiles, int nbBlocks){
		this.nbPiles = nbPiles;
		this.nbBlocks = nbBlocks;
		this.variables = new BlocksWorldVariables(nbPiles, nbBlocks).getVariables();
		this.actions = buildActions();
	}
	
	public Set<Action> getActions(){return this.actions;}
	
	public Variable var(String s){
		for (Variable v : this.variables){
			if(v.getName().equals(s)) return v;	
		}
		
		throw new IllegalArgumentException("Variable not found " +s);
	}
	
	public Set<Action> buildActions(){
		Set<Action> res = new HashSet<>();
		
		//1st action with b's go from on_b' to on_b''
		
		for (int b = 0 ; b < this.nbBlocks ; b++){
			Variable on_b = var("on_" + b);
			Variable fixed_b = var("fixed_" + b);
			
			for (int notb = 0 ; notb < this.nbBlocks ; notb++){
				if (notb == b) continue;
				Variable fixed_notb = var("fixed_" + notb);
				
				for (int notnotb = 0 ; notnotb < this.nbBlocks ; notnotb++){
					//basicaction has cond and effect and ....cost? cost just =1? idk.
					if (notnotb == b || notnotb == notb) continue;
					Variable fixed_notnotb = var("fixed_" + notnotb);
					
					Map<Variable,Object> pre = new HashMap<>();
					Map<Variable,Object> eff = new HashMap<>();
					
					pre.put(on_b, notb);
					pre.put(fixed_b, false);
					pre.put(fixed_notnotb, false);
					
					eff.put(on_b, notnotb);
					eff.put(fixed_notnotb, true);
					eff.put(fixed_notb, false);
					
					res.add(new BasicAction(pre,eff,1));
				}
			}
		}
		
		//2nd action block b (onb')to an empty pile
		
		for (int b = 0 ; b < this.nbBlocks ; b++){
			Variable on_b = var("on_" + b);
			Variable fixed_b = var("fixed_" + b);
			
			for (int p = 0; p < this.nbPiles; p++) {
                		Variable free_p = var("free_" + p);

                		for (int notb = 0; notb < this.nbBlocks; notb++) {
                    			if (notb == b) continue;
                    			Variable fixed_notb = var("fixed_" + notb);

                    			Map<Variable,Object> pre = new HashMap<>();
                    			Map<Variable,Object> eff = new HashMap<>();

                    			pre.put(on_b, notb); // pre.put(on_b, -(p + 1)); 
                    			pre.put(fixed_b, false);
                    			pre.put(free_p, true); //pre.put(fixed_notb, false);

                    			eff.put(on_b, -(p+1)); //2. notb
                    			eff.put(fixed_notb, false);  //false
                    			eff.put(free_p, false); //false

                    			res.add(new BasicAction(pre, eff, 1));
				}
			}
		}
		
		//3rd action - opposite of action2 basically (kinda)
		
		for (int b = 0 ; b < this.nbBlocks ; b++){
			Variable on_b = var("on_" + b);
			Variable fixed_b = var("fixed_" + b);
			
			for (int p = 0; p < this.nbPiles; p++) {
                		Variable free_p = var("free_" + p);

                		for (int notb = 0; notb < this.nbBlocks; notb++) {
                    			if (notb == b) continue;
                    			Variable fixed_notb = var("fixed_" + notb);

                    			Map<Variable,Object> pre = new HashMap<>();
                    			Map<Variable,Object> eff = new HashMap<>();

                    			eff.put(on_b, -(p + 1)); 
                    			eff.put(fixed_b, false);
                    			eff.put(fixed_notb, false);

                    			pre.put(on_b, notb);
                    			pre.put(free_p, true);
                    			pre.put(fixed_notb, true);

                    			res.add(new BasicAction(pre, eff, 1));
				}
			}
		}
		
		//p to p2
		
		for (int b = 0 ; b < this.nbBlocks ; b++){
			Variable on_b = var("on_" + b);
			Variable fixed_b = var("fixed_" + b);
			
			for (int p = 0; p < this.nbPiles; p++) {
                		Variable free_p = var("free_" + p);

                		for (int p2 = 0; p2 < this.nbPiles; p2++) {
                    			if (p == p2) continue;
                    			Variable free_p2 = var("free_" + p2);

                    			Map<Variable,Object> pre = new HashMap<>();
                    			Map<Variable,Object> eff = new HashMap<>();

                    			pre.put(on_b, -(p + 1)); 
                    			pre.put(fixed_b, false);
                    			pre.put(free_p2, true);

                    			eff.put(on_b, -(p2 + 1));
                    			eff.put(free_p, true);
                    			eff.put(free_p2, false);

                    			res.add(new BasicAction(pre, eff, 1));
				}
			}
		}
		
		return res;
	}
}
