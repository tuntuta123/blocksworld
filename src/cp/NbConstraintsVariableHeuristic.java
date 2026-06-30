package cp;

import java.util.*;
import modelling.*;


//return two
public class NbConstraintsVariableHeuristic implements VariableHeuristic{
	
	public Set<Constraint> constraints;
	public boolean most;
	
	public NbConstraintsVariableHeuristic(Set<Constraint> constraints, boolean most){
		this.most = most;
		this.constraints = constraints;
	}
	
	@Override
	public Variable best(Set<Variable> vars, Map<Variable, Set<Object>> doms) {
		int count;
		Variable res = null;
		if (this.most) {
		    count = Integer.MIN_VALUE;
		} 
		else {
		    count = Integer.MAX_VALUE;
		}
		for (Variable v : vars) {
		    int countV = 0;
		    for (Constraint c : this.constraints) {
		        if (c.getScope().contains(v)) {
		            countV++;
		        }
		    }
		    if ((this.most && countV > count) || (!this.most && count > countV)) {
		        count = countV;
		        res = v;
		    }
		}
		return res;
	    }
}
