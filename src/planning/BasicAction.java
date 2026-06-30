package planning;

import modelling.Variable;
import java.util.*;

public class BasicAction implements Action{
	
	public Map<Variable,Object> cond;
	public Map<Variable,Object> effect;
	public int cost;
	
	public BasicAction(Map<Variable,Object> cond, Map<Variable,Object> effect, int cost){
		this.cond = cond;
		this.effect = effect;
		this.cost = cost;
	}

	/*FALSE*/	
	public boolean isApplicable(Map<Variable,Object> state){
		for (Map.Entry<Variable,Object> entry : this.cond.entrySet()){
			Variable var = entry.getKey();
			Object requiry = entry.getValue();
			Object given = state.get(var);
			
			if ((given==null)||!(given.equals(requiry))){
				return false ;
			}
		}
		return true;
	}
	
	public Map<Variable,Object> successor(Map<Variable,Object> state){
		if(!(isApplicable(state))){
			throw new IllegalArgumentException("Rule not applicable");
		}
		Map<Variable,Object> newState = new HashMap<Variable,Object>();
		newState.putAll(state);
		
		for (Map.Entry<Variable,Object> entry : this.effect.entrySet()){
			Variable var = entry.getKey();
			Object value = entry.getValue();
			newState.put(var, value);
		}
		return newState;
	}
	
	public String toString(){
		return "BasicAction cond:" + cond +" effect: "+effect; 
	}
	
	
	public int getCost(){ return this.cost; }
		
}
