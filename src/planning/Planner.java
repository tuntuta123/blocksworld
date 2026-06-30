package planning;

import modelling.*;
import java.util.*;


public interface Planner{
	public List<Action> plan();
	public Map<Variable, Object> getInitalState();
	public Set<Action> getActions();
	public Goal getGoal();
	public int getExploredNodeCount();
}
