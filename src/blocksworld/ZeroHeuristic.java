package blocksworld;

import java.util.Map;
import modelling.Variable;
import planning.Heuristic;

public class ZeroHeuristic implements Heuristic{

	//because it uses zero, a star algo works like dijkstra exploring everything....

    	@Override
    	public float estimate(Map<Variable, Object> state){
        	return 0f;
    	}
}

