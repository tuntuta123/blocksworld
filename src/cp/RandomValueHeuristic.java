package cp;

import java.util.*;
import modelling.*;

public class RandomValueHeuristic implements ValueHeuristic {

    public Random random;

    public RandomValueHeuristic(Random random) {
        this.random = random;
    }

    @Override 
    public List<Object> ordering(Variable var, Set<Object> dom) {
        List<Object> res = new ArrayList<>(dom);
        Collections.shuffle(res, this.random);
        return res;
    }
}
