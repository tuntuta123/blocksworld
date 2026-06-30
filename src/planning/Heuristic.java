package planning;

import modelling.*;
import java.util.*;

public interface Heuristic{
	float estimate(Map<Variable, Object> state);
}
