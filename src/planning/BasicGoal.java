package planning;

import java.util.*;
import modelling.*;

public class BasicGoal implements Goal{
	public Map<Variable,Object> cond;
	
	public BasicGoal(Map<Variable,Object> cond){
		this.cond = cond;
	}
	
	public boolean isSatisfiedBy(Map<Variable,Object> state){
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
	
	public String toString(){
		return "BasicGoal" + cond; 
	}
}
