package planning;

import modelling.Variable;
import java.util.*;

public class DFSPlanner implements Planner{
	
	public Map<Variable,Object> init;
	public Set<Action> actions;
	public Goal goal;
	public int explored = 0;
		
	public DFSPlanner(Map<Variable,Object> init, Set<Action> actions, Goal goal){
		this.init = init;
		this.actions = actions;
		this.goal = goal;	
		this.explored = explored;
}
	public Goal getGoal(){return this.goal;}
	public Map<Variable, Object> getInitalState(){return this.init;}
	public Set<Action> getActions(){return this.actions;}
	public int getExploredNodeCount() {return explored;}
	
	
	public List<Action> plan(){
		Set<Map<Variable,Object>> visited = new HashSet<>();
		
		List<Action> path = new ArrayList<Action>();
		
		return dfs(this.init, visited, path);
		
	}

	public List<Action> dfs(Map<Variable, Object> state, Set<Map<Variable, Object>> visited, List<Action> path) {
		this.explored++;
		if (goal.isSatisfiedBy(state)) {
			return new ArrayList<>(path);
	        }

		for (Action a : actions) {
			if (a.isApplicable(state)) {
		    		Map<Variable, Object> succ = a.successor(state);

		    
			    	if (!visited.contains(succ)) {
					visited.add(succ);
					path.add(a);

					List<Action> sol = dfs(succ, visited, path);
					if (sol != null) {
				    		return sol;
					}

				path.remove(path.size() - 1);
			    	}
			}
	    	}

		    return null;
		}
		
}



	
	//there should be a dfs algo here =)
	//we need a set of visited states so Set<Map<Variable,Object>> ??
	//we need a path which is a list of actions i guess
	
	//plan is a helper function which makes the set and the list??????????
	
	//so unsure about the list and the set. just look it up on the tp
