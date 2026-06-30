package cp;

import modelling.*;
import java.util.*;

public class ArcConsistency{
	public Set<Constraint> constraints;
	
	public ArcConsistency(Set<Constraint> constraints){
		for (Constraint c : constraints) {
            		int size = c.getScope().size();
            			if (size != 1 && size != 2) {
                			throw new IllegalArgumentException("Ni unaire ni binaire");
            			}
        	}
        	this.constraints = constraints;
	}
	
	public boolean enforceNodeConsistency(Map<Variable, Set<Object>> domains) {
    		boolean emptied = false;

		for (Constraint constraint : this.constraints) {
			Set<Variable> scope = constraint.getScope();

			if (scope.size() == 1) {
				Variable var = scope.iterator().next();
			    	Set<Object> dom = domains.get(var);
				if (dom == null) continue;

			    	Set<Object> toRemove = new HashSet<>();

			    	for (Object val : dom) {
					Map<Variable, Object> inst = new HashMap<>();
					inst.put(var, val);
					if (!constraint.isSatisfiedBy(inst)) {
				    		toRemove.add(val);
					}
			    	}

			    	dom.removeAll(toRemove);

			    	if (dom.isEmpty()) {
					emptied = true;
			    	}
			}
		    }

		    return !emptied;
	}

	
	public boolean revise(Variable v1, Set<Object> d1, Variable v2, Set<Object> d2){ // eNC'nin benzeri ama burada iki tane domaini karşılaştırıyoruz şartları sağlıyor mu diye
		boolean del = false;
		Set<Object> remove = new HashSet<>();
		
		for(Object val1 : d1){
			boolean viable = false;
			for(Object val2 : d2){
				boolean allOk = true;
				Map<Variable,Object> inst = new HashMap<>();
            			inst.put(v1, val1);
            			inst.put(v2, val2);
            			
            			for(Constraint c : constraints){
            				Set<Variable> scope = c.getScope();
            				if (scope.size() == 2 && scope.contains(v1) && scope.contains(v2)) {
                    				if (!c.isSatisfiedBy(inst)) { allOk = false; break; }
                			}
            			}
            			if (allOk) { 
            				viable = true; 
            				break; 
            			}
			}
			if (!viable) { 
				remove.add(val1); 
				del = true; 
			}
		}
		d1.removeAll(remove);
		return del;
	}
	
	//arc consistency 1 - tüm değişken çiftlerini alıp "revise()" etmek. iki yönlü filtreleme
	public boolean ac1(Map<Variable, Set<Object>> domains) {
		if (!enforceNodeConsistency(domains)) return false;

		boolean change = true;
		while (change) {
			change = false;

			List<Variable> vars = new ArrayList<>(domains.keySet());
			for (int i = 0; i < vars.size(); i++) {
		    		Variable xi = vars.get(i);
		    		Set<Object> dxi = domains.get(xi);

		    		for (int j = 0; j < vars.size(); j++) {
		        		if (i == j) continue;
		        		Variable xj = vars.get(j);
		        		Set<Object> dxj = domains.get(xj);

		        		if (revise(xi, dxi, xj, dxj)) {
		            			change = true;
		        		}
		    		}
			}
	    	}

	    	for (Set<Object> domain : domains.values()) {
			if (domain.isEmpty()) return false;
	    	}
	    	return true;
	}

}
