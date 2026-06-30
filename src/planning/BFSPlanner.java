package planning;

import modelling.Variable;
import java.util.*;

public class BFSPlanner implements Planner{

    private final Map<Variable, Object> init;
    private final Set<Action> actions;
    private final Goal goal;
    public int explored = 0;
	
    public BFSPlanner(Map<Variable, Object> init, Set<Action> actions, Goal goal) {
        this.init = init;
        this.actions = actions;
        this.goal = goal;
        this.explored = explored;
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
        Set<Map<Variable, Object>> closed = new HashSet<>();
        Queue<Map<Variable, Object>> open = new ArrayDeque<>();

        closed.add(this.init);
        open.add(this.init);
        father.put(this.init, null);

        if (goal.isSatisfiedBy(init)) {
            return List.of();
        }

        while (!open.isEmpty()) {
            this.explored++;
            Map<Variable, Object> inst = open.remove();

            for (Action a : this.actions) {
                if (a.isApplicable(inst)) {
                    Map<Variable, Object> next = a.successor(inst);

                    if (!closed.contains(next) && !father.containsKey(next)) {
                        father.put(next, inst);
                        plan.put(next, a);

                        if (goal.isSatisfiedBy(next)) {
                            return getBfsPlan(father, plan, next);
                        } else {
                            open.add(next);
                        }
                    }
                }
            }

            closed.add(inst);
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
}

