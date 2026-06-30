package cp;

import java.util.*;
import modelling.*;

public interface VariableHeuristic{
	public Variable best(Set<Variable> vars, Map<Variable, Set<Object>> doms);
}
