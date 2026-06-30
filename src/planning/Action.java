package planning;

import modelling.*;
import java.util.*;


public interface Action{

	public boolean isApplicable(Map<Variable,Object> val);
	public Map<Variable,Object> successor(Map<Variable,Object> val);
	public int getCost();

}
