package cp;

import java.util.*;
import modelling.*;

public class HeuristicMACSolver extends AbstractSolver {

    public VariableHeuristic variableHeuristic;
    public ValueHeuristic valueHeuristic;

    public HeuristicMACSolver(Set<Variable> variables, Set<Constraint> constraints, VariableHeuristic variableHeuristic, ValueHeuristic valueHeuristic) {
        super(variables, constraints);
        this.variableHeuristic = variableHeuristic;
        this.valueHeuristic = valueHeuristic;
    }

    @Override
    public Map<Variable, Object> solve() {
        Map<Variable, Set<Object>> ED = new HashMap<>();
        for (Variable v : variables) {
            ED.put(v, new HashSet<>(v.getDomain()));
        }
        return mac(new HashMap<>(), new LinkedList<>(this.variables), ED);
    }

    public Map<Variable, Object> mac(Map<Variable, Object> I, LinkedList<Variable> V, Map<Variable, Set<Object>> ED) {
        if (V.isEmpty()) return I;
        else {
            ArcConsistency ac = new ArcConsistency(this.constraints);
            if (!ac.ac1(ED)) return null;

            Variable xi = this.variableHeuristic.best(new HashSet<>(V), ED);
            V.remove(xi);
            List<Object> domainXi = this.valueHeuristic.ordering(xi, ED.get(xi));

            for (Object v : domainXi) {
                Map<Variable, Object> N = new HashMap<>(I);
                N.put(xi, v);

                Map<Variable, Set<Object>> newED = new HashMap<>();
                for (Map.Entry<Variable, Set<Object>> e : ED.entrySet()) {
                    newED.put(e.getKey(), new HashSet<>(e.getValue()));
                }

                Set<Object> idk = new HashSet<>();
                idk.add(v);
                newED.put(xi, idk);

                if (isConsistent(N)) {
                    Map<Variable, Object> R = mac(N, V, newED);
                    if (R != null) return R;
                }
            }
            V.addFirst(xi);
        }
        return null;
    }
}

