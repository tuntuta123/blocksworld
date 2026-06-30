package cp;

import modelling.*;
import java.util.*;

public class BacktrackSolver extends AbstractSolver {
	
	public BacktrackSolver(Set<Variable> variables, Set<Constraint> constraints){
		super(variables, constraints);
	}
	
	@Override
	
	public Map<Variable,Object> solve(){
		return bt(new HashMap<>(), new LinkedList<>(this.variables));
	}
	
	public Map<Variable,Object> bt(Map<Variable,Object> inst, LinkedList<Variable> V){
		if(V.isEmpty()) return inst;
		
		Variable xi = V.removeFirst();
		
		for (Object vi : xi.getDomain()){
			inst.put(xi, vi);
			 if (isConsistent(inst)){
			 	Map<Variable, Object> R = bt(inst, V);
			 	if (R != null) {
                    			V.addFirst(xi);
                    			return R;
                		}
			 }
			 inst.remove(xi); 
		}
		V.addFirst(xi);
        	return null;
	}
}
