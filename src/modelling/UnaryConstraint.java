package modelling;

import java.util.*;


public class UnaryConstraint implements Constraint{

	public Variable v;
	public Set<Object>s;
	
	public UnaryConstraint(Variable v, Set<Object>s){
		this.v = v;
		this.s = s;
	}
	
	public Set<Variable> getScope(){
		Set<Variable> scope = new HashSet<>();
    		scope.add(this.v);
    		return scope;
	}
	public boolean isSatisfiedBy(Map<Variable, Object> inst){
		Object val = inst.get(v);

		if (val == null) {
        		throw new IllegalArgumentException("Missing value for variable in scope");
    		}

    		return s.contains(val);
	}
}
