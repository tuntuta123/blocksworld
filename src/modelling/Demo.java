package modelling;

import java.util.*;

public class Demo {

    private static Set<Object> S(Object... xs) {
        Set<Object> out = new HashSet<>();
        Collections.addAll(out, xs);
        return out;
    }

    public static void main(String[] args) {
        Variable A = new Variable("A", S(1, 2, 3));
        Variable B = new Variable("B", S(1, 2, 3));
        Variable Z = new Variable("Z", S(3, 4, 5));
        Variable BV = new BooleanVariable("BV");

        Constraint c1 = new DifferenceConstraint(A, B);
        Constraint c2 = new Implication(A, S(1), B, S(3));
        Constraint c3 = new UnaryConstraint(Z, S(3, 4));

        List<Constraint> constraints = List.of(c1, c2, c3);

        List<Map<Variable, Object>> insts = new ArrayList<>();

        Map<Variable, Object> i1 = new HashMap<>();
        i1.put(A, 1); i1.put(B, 2); i1.put(BV, true); i1.put(Z, 3);
        insts.add(i1);

        Map<Variable, Object> i2 = new HashMap<>();
        i2.put(A, 2); i2.put(B, 2); i2.put(BV, false); i2.put(Z, 4);
        insts.add(i2);

        Map<Variable, Object> i3 = new HashMap<>();
        i3.put(A, 1); i3.put(B, 3); i3.put(BV, true); i3.put(Z, 3);
        insts.add(i3);

        Map<Variable, Object> i4 = new HashMap<>();
        i4.put(A, 3); i4.put(B, 1); i4.put(BV, false); i4.put(Z, 5);
        insts.add(i4);

        Map<Variable, Object> i5 = new HashMap<>();
        i5.put(A, 2); i5.put(B, 2); i5.put(BV, true);
        insts.add(i5);

        int caseNo = 1;
        for (Map<Variable, Object> inst : insts) {
            System.out.println("\n=== Instanciation #" + caseNo++ + " ===");
            System.out.println(inst);
            for (Constraint c : constraints) {
                boolean ok;
                try {
                    ok = c.isSatisfiedBy(inst);
                    if (ok) {
                        System.out.println(c.getClass().getSimpleName() + " : satisfies");
                    } 
                    else {
                        System.out.println(c.getClass().getSimpleName() + " : does not satisfy");
                    }
                } 
                catch (IllegalArgumentException e) {
                    System.out.println(c.getClass().getSimpleName() + " : missing value");
                }
            }
        }
    }
}

