package blocksworld;

import java.util.*;
import modelling.Variable;
import planning.Heuristic;

public class MisplacedHeuristic implements Heuristic {

//idea: count the difference between goal and init state. if current val does not equal goal val, we add +1.

    	public Map<Variable, Object> goalState;

    	public MisplacedHeuristic(Map<Variable, Object> goalState) {
        	this.goalState = new HashMap<>(goalState);
    	}

    	@Override
    	public float estimate(Map<Variable, Object> state) {
        	int count = 0;

        	for (Map.Entry<Variable, Object> e : goalState.entrySet()) {
            		Variable v = e.getKey();

            		if (!v.getName().startsWith("on_")) {
                		continue;
            		}

            		Object goalVal = e.getValue();
            		Object curVal  = state.get(v);
			
			//where the magic happens
            		if (!curVal.equals(goalVal)) {
                		count++;
            		}
		}
	return (float) count;
    	}
}

