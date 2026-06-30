package cp;

import modelling.*;
import java.util.*;

public abstract class AbstractSolver implements Solver{
	public Set<Variable> variables;
	public Set<Constraint> constraints;
	
	public AbstractSolver(Set<Variable> variables, Set<Constraint> constraints){
		this.variables = variables;
		this.constraints = constraints;
	}
	
	public boolean isConsistent(Map<Variable,Object> inst){
		boolean isAssigned = true;
		for (Constraint c : this.constraints){
			for(Variable v : c.getScope()){
				if(!inst.containsKey(v)){
					isAssigned = false;
					break;
				}
			}
			if(isAssigned && !c.isSatisfiedBy(inst)){
				return false;
			}
		}
		return true;
	}

}
