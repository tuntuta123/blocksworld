package blocksworld;

import modelling.*;
import planning.*;
import cp.*;
import java.util.*;

public class BlocksWorldDemo {

    	public static void main(String[] args) {

		// world init
		BlocksWorldCSP world = new BlocksWorldCSP(3, 2);
		Set<Variable> vars = world.getVariables();
		Set<Constraint> baseConstraints = world.getConstraints();

		System.out.println("nb piles: 3");
		System.out.println("nb blocks: 2\n");

		// cfgs
		Map<Variable, Object> cfg1 = new HashMap<>();
		cfg1.put(var(vars, "on_0"), -1);
		cfg1.put(var(vars, "on_1"), 0);
		cfg1.put(var(vars, "fixed_0"), false);
		cfg1.put(var(vars, "fixed_1"), false);
		cfg1.put(var(vars, "free_0"), false);
		cfg1.put(var(vars, "free_1"), true);
		cfg1.put(var(vars, "free_2"), true);

		Map<Variable, Object> cfg2 = new HashMap<>();
		cfg2.put(var(vars, "on_0"), -1);
		cfg2.put(var(vars, "on_1"), -1);
		cfg2.put(var(vars, "fixed_0"), false);
		cfg2.put(var(vars, "fixed_1"), false);
		cfg2.put(var(vars, "free_0"), true);
		cfg2.put(var(vars, "free_1"), true);
		cfg2.put(var(vars, "free_2"), true);

		
		System.out.println("testing constraints");
		runVariableDemo("cfg1", cfg1, baseConstraints);
		runVariableDemo("cfg2", cfg2, baseConstraints);

		runPlanningDemo(world, vars);

		runCSPDemo("Base Constraints", vars, baseConstraints);

		CroissantBuilder cb = new CroissantBuilder(world);
		Set<Constraint> croissantCons = cb.getRegularity();

		RegularityBuilder rb = new RegularityBuilder(world, 1); // diff=1
		Set<Constraint> regularityCons = rb.getRegularity();

		// combination
		Set<Constraint> fullCons = new HashSet<>();
		fullCons.addAll(croissantCons);
		fullCons.addAll(regularityCons);

		// csp for each set
		runCSPDemo("ex10: croissant", vars, croissantCons);
		runCSPDemo("ex10: regularity", vars, regularityCons);
		runCSPDemo("ex10: cro + reg", vars, fullCons);
	}



    		public static Variable var(Set<Variable> vars, String name) {
			for (Variable v : vars){
				if(v.getName().equals(name)) return v;	
			}
		
		throw new IllegalArgumentException("Variable not found " + name);
    		}

    		public static Map<Variable,Object> stateFromPiles(List<List<Integer>> piles, Set<Variable> vars) {

			Map<Variable,Object> s = new HashMap<>();

			// on_b
			for (int p = 0; p < piles.size(); p++) {
		    		List<Integer> pile = piles.get(p);
		    		if (pile.isEmpty()) continue;
		    		int top = pile.get(0);
		    		s.put(var(vars, "on_" + top), -(p + 1));

		    		for (int i = 1; i < pile.size(); i++) {
		        		int b = pile.get(i);
		        		int below = pile.get(i - 1);
		        		s.put(var(vars, "on_" + b), below);
		    		}
			}

			// fixed_b
			for (Variable v : vars) {
		    		if (!v.getName().startsWith("fixed_")) continue;

		    		int b = Integer.parseInt(v.getName().substring(6));
		    		boolean fixed = false;

		    		for (Variable v2 : vars) {
		        		if (!v2.getName().startsWith("on_")) continue;
		        		if (s.get(v2) instanceof Integer dest && dest == b) {
		            			fixed = true;
		            			break;
		        		}
		    		}
		    		s.put(v, fixed);
			}

			// free_p
			for (Variable v : vars) {
		    		if (!v.getName().startsWith("free_")) continue;
		    		int p = Integer.parseInt(v.getName().substring(5));
		    		boolean free = piles.get(p).isEmpty();
		    		s.put(v, free);
			}

        	return s;
    	}

    	// variable demo
    	public static void runVariableDemo(String name, Map<Variable,Object> cfg, Set<Constraint> cons) {
			System.out.println("\n--- " + name + " ---");
			boolean allOK = true;
			for (Constraint c : cons) {
			    	boolean ok = c.isSatisfiedBy(cfg);
			    	System.out.println(c + " -> " + ok);
			    	if (!ok) allOK = false;
		}
		System.out.println("result: " + (allOK ? "all is ok" : "all is NOT ok"));
    	}

    	// planning demo
    	public static void runPlanningDemo(BlocksWorldCSP world, Set<Variable> vars) {

		System.out.println("\n---planning---");

		List<List<Integer>> initPiles = List.of(
		    	List.of(1,0),
		    	List.of(),
		    	List.of()
		);

		List<List<Integer>> goalPiles = List.of(
		    	List.of(0),
		    	List.of(1),
		    	List.of()
		);

		Map<Variable,Object> initState = stateFromPiles(initPiles, vars);
		Map<Variable,Object> goalState = stateFromPiles(goalPiles, vars);

		Goal goal = new BasicGoal(goalState);
		Heuristic h = new MisplacedHeuristic(goalState);
		BlocksWorldActions bwa = new BlocksWorldActions(3,2);
		Set<Action> actions = bwa.getActions();

		List<Planner> planners = List.of(
		        	new BFSPlanner(initState, actions, goal),
		        	new DFSPlanner(initState, actions, goal),
		        	new AStarPlanner(initState, actions, goal, h)
		);

		for (Planner p : planners) {
		    	long t0 = System.currentTimeMillis();
		    	List<Action> sol = p.plan();
		    	long t1 = System.currentTimeMillis();

		   	System.out.println("-- " + p.getClass().getSimpleName() + " --");
		    	System.out.println("time : " + (t1 - t0) + " ms");
		    	System.out.println("total nodes explored : " + p.getExploredNodeCount());

		    	if (sol == null) System.out.println("No plan found");
		    	else {
		        	System.out.println("Plan:");
		        	for (Action a : sol) System.out.println(" - " + a);
		    	}
		}
    	}

    	public static void runCSPDemo(String title, Set<Variable> vars, Set<Constraint> cons) {

		System.out.println("\n---csp demo for: " + title + "---");

		// same test config for all solvers
		Map<Variable,Object> cfg = new HashMap<>();
		cfg.put(var(vars,"on_0"), -1);
		cfg.put(var(vars,"on_1"), 0);
		cfg.put(var(vars,"fixed_0"), false);
		cfg.put(var(vars,"fixed_1"), false);
		cfg.put(var(vars,"free_0"), false);
		cfg.put(var(vars,"free_1"), true);
		cfg.put(var(vars,"free_2"), true);

		List<cp.Solver> solvers = List.of(
				new cp.BacktrackSolver(vars, cons),
		        	new cp.MACSolver(vars, cons),
		        	new cp.HeuristicMACSolver(vars, cons, new cp.DomainSizeVariableHeuristic(false), new cp.RandomValueHeuristic(new Random()))
		);

		for (cp.Solver solver : solvers) {
		    	System.out.println("\n--- " + solver.getClass().getSimpleName() + " ---");

		    	long t0 = System.currentTimeMillis();
		    	Map<Variable,Object> sol = solver.solve();
		    	long t1 = System.currentTimeMillis();

		    	System.out.println("Time: " + (t1 - t0) + " ms");

		    	if (sol == null)
		        	System.out.println("no sol");
		    	else
		        	System.out.println("solution: " + sol);
		}
    	}
}

