package modelling;

import java.util.*;

public class Implication implements Constraint{

	public Variable v1;
	public Variable v2;
	public Set<Object>s1;
	public Set<Object>s2;
	
	public Implication(Variable v1, Set<Object>s1, Variable v2, Set<Object> s2){
		this.v1 = v1;
		this.s1 = s1;
		this.v2 = v2;
		this.s2 = s2;
	}
	
	public Set<Variable> getScope(){
		Set<Variable> scope = new HashSet<>();
    		scope.add(v1);
    		scope.add(v2);
    		return scope;
	}
	public boolean isSatisfiedBy(Map<Variable, Object> inst){
		Object val1 = inst.get(v1);
    		Object val2 = inst.get(v2);

		if (val1 == null || val2 == null) {
        		throw new IllegalArgumentException("Missing value for variable in scope");
    		}

    		return !s1.contains(val1) || s2.contains(val2);
	}
	
	@Override
	public String toString() {
    		return "Implication(" + v1.getName() + " ∈ " + s1 + " -> " +
           v2.getName() + " ∈ " + s2 + ")";
	}
}
