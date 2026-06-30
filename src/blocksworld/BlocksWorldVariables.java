package blocksworld;

import java.util.*;
import modelling.*;

public class BlocksWorldVariables{
	
	public int nbBlocks;
	public int nbPiles;
	public Set<Variable> variables;
	
	public BlocksWorldVariables(int nbPiles,int nbBlocks){
		this.nbPiles = nbPiles;
		this.nbBlocks = nbBlocks;
		this.variables = new HashSet<>();
		buildVariables();
	}
	
	public void buildVariables(){
		for(int b = 0 ; b < this.nbBlocks ; b++){
			Set<Object> domOn = new LinkedHashSet<>();
			
			for(int p = 0 ; p < this.nbPiles ; p++){
				domOn.add(-(p+1));
			}
			
			for(int b2 = 0; b2 < this.nbBlocks ; b2++){
				if(b2 != b){
					domOn.add(b2);
				}
			}
			this.variables.add(new Variable("on_" + b, domOn));
			this.variables.add(new BooleanVariable("fixed_" + b));
		}
		
		for(int p = 0 ; p < this.nbPiles ; p++){
				this.variables.add(new BooleanVariable("free_" + p));
		}
	}
	
	public Set<Variable> getVariables(){
		return this.variables;
	}

}
