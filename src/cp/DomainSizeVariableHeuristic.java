package cp;

import java.util.*;
import modelling.*;

public class DomainSizeVariableHeuristic implements VariableHeuristic {

    public boolean boolDom;

    public DomainSizeVariableHeuristic(boolean boolDom) {
        this.boolDom = boolDom;
    }

    @Override
    public Variable best(Set<Variable> vars, Map<Variable, Set<Object>> doms) {
        Variable res = null;
        int size;
        if (this.boolDom) {
            size = Integer.MIN_VALUE;
        } else {
            size = Integer.MAX_VALUE;
        }
        for (Variable v : vars) {
            int tailleDomV = doms.get(v).size();
            if ((this.boolDom && (tailleDomV > size)) || (!this.boolDom && (tailleDomV < size))) {
                size = tailleDomV;
                res = v;
            }
        }
        return res;
    }
}
