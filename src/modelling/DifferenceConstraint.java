package modelling;

import java.util.*;

public class DifferenceConstraint implements Constraint{

	public Variable v1;
	public Variable v2;
	
	public DifferenceConstraint(Variable v1, Variable v2){
		this.v1 = v1;
		this.v2 = v2;
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
			throw new IllegalArgumentException("missing var");
		    }

    		return !val1.equals(val2);
	}
	
	@Override
public String toString() {
    return "DifferenceConstraint(" + v1.getName() + " /= " + v2.getName() + ")";
}
}
