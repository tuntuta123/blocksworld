package planning;

import modelling.Variable;
import java.util.*;

public class AStarPlanner implements Planner{

    private final Map<Variable, Object> init;
    private final Set<Action> actions;
    private final Goal goal;
    public Heuristic h;
    public int explored = 0;

    public AStarPlanner(Map<Variable, Object> init, Set<Action> actions, Goal goal, Heuristic h) {
        this.init = init;
        this.actions = actions;
        this.goal = goal;
        this.h = h;
        this.explored = explored;
    }


    public Goal getGoal() { return this.goal; }
    public Map<Variable, Object> getInitalState(){return this.init;}
    public Set<Action> getActions() { return this.actions; }

    @Override
    public List<Action> plan() {
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        Set<Map<Variable, Object>> open = new HashSet<>();
        Map<Map<Variable,Object>, Float> distance = new HashMap<>();
	Map<Map<Variable,Object>, Float> value = new HashMap<>();

        open.add(this.init);
        father.put(this.init, null);
        distance.put(this.init, 0f);
        value.put(this.init, 0f + this.h.estimate(this.init));

	while (!open.isEmpty()) {
		this.explored++;
		Map<Variable,Object> inst = null;
		Float best = Float.POSITIVE_INFINITY;

		for (Map<Variable,Object> state : open) {
		    Float val = value.getOrDefault(state, Float.POSITIVE_INFINITY);
		    if (val < best) {
			best = val;
			inst = state;
		    }
		}
		
		if (goal.isSatisfiedBy(inst)) {
		   return getBfsPlan(father, plan, inst);
		}
	    	
	    	open.remove(inst);
	    	
		for (Action a : this.actions) {
                	if (a.isApplicable(inst)) {
                    		Map<Variable, Object> next = a.successor(inst);
				if(!distance.containsKey(next)){
					distance.put(next, Float.POSITIVE_INFINITY);
				}
		            	if(distance.get(next)>distance.get(inst)+a.getCost()){
		            		distance.put(next,(distance.get(inst)+a.getCost()));
		            		value.put(next, distance.get(next) + this.h.estimate(next));

		            		father.put(next, inst);
    					plan.put(next, a);
    					open.add(next);
		            	}
                	}
            	}
        }
        return null;
    }
    
    	public List<Action> getBfsPlan(
            	Map<Map<Variable, Object>, Map<Variable, Object>> father,
            	Map<Map<Variable, Object>, Action> plan,
            	Map<Variable, Object> goalState) {

        	Deque<Action> stack = new ArrayDeque<>();
        	Map<Variable, Object> cur = goalState;

        	while (father.get(cur) != null) {
            		stack.push(plan.get(cur));
            		cur = father.get(cur);
        	}
        	return new ArrayList<>(stack);
    	}
    	public int getExploredNodeCount() {
    		return explored;
	}
   
}

