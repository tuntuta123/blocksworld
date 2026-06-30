package planning;

import modelling.Variable;
import java.util.*;

public class DijkstraPlanner implements Planner{

    private final Map<Variable, Object> init;
    private final Set<Action> actions;
    private final Goal goal;
    public int explored = 0;

    public DijkstraPlanner(Map<Variable, Object> init, Set<Action> actions, Goal goal) {
        this.init = init;
        this.actions = actions;
        this.goal = goal;
        this.explored = 0;
    }


    public Goal getGoal() { return this.goal; }
    public Map<Variable, Object> getInitialState() { return this.init; }
    public Map<Variable, Object> getInitalState(){return this.init;}
    public Set<Action> getActions() { return this.actions; }
    public int getExploredNodeCount() {return explored;}

    @Override
    public List<Action> plan() {
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        Set<Map<Variable, Object>> open = new HashSet<>();
        Map<Map<Variable,Object>, Double> distance = new HashMap<>();

        open.add(this.init);
        father.put(this.init, null);
        distance.put(this.init, 0.0);

        

	while (!open.isEmpty()) {
		this.explored++;
		Map<Variable,Object> inst = null;
		double bestDist = Double.POSITIVE_INFINITY;

		for (Map<Variable,Object> state : open) {
		    double d = distance.getOrDefault(state, Double.POSITIVE_INFINITY);
		    if (d < bestDist) {
			bestDist = d;
			inst = state;
		    }
		}
		open.remove(inst);
	    
		if (goal.isSatisfiedBy(inst)) {
		   return getDijkstraPlan(father, plan, inst);
		}
	    
		for (Action a : this.actions) {
                	if (a.isApplicable(inst)) {
                    		Map<Variable, Object> next = a.successor(inst);
				if(!distance.containsKey(next)){
					distance.put(next, Double.POSITIVE_INFINITY);
				}
		            	if(distance.get(next)>distance.get(inst)+a.getCost()){
		            		distance.put(next,(distance.get(inst)+a.getCost()));
		            		father.put(next, inst);
    					plan.put(next, a);
    					open.add(next);
		            	}
                	}
            	}
        }
        return null;
    }

    private List<Action> getDijkstraPlan(
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
}

