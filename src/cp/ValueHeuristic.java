package cp;

import java.util.*;
import modelling.*;

public interface ValueHeuristic{
	public List<Object> ordering(Variable var, Set<Object> dom);
}
