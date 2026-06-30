package planning;

import modelling.*;
import java.util.*;


public interface Goal{
	public boolean isSatisfiedBy(Map<Variable,Object> val);
}
