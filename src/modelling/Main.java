package modelling;

import modellingtests.VariableTests;
import modellingtests.BooleanVariableTests;
import modellingtests.DifferenceConstraintTests;
import modellingtests.ImplicationTests;
import modellingtests.UnaryConstraintTests;
import planningtests.BasicActionTests;
import planningtests.BasicGoalTests;
import planningtests.DFSPlannerTests;
import planningtests.BFSPlannerTests;
import planningtests.DijkstraPlannerTests;
import planningtests.AStarPlannerTests;
import cptests.AbstractSolverTests;
import cptests.ArcConsistencyTests;
import cptests.BacktrackSolverTests;
import cptests.DomainSizeVariableHeuristicTests;
import cptests.HeuristicMACSolverTests;
import cptests.MACSolverTests;
import cptests.NbConstraintsVariableHeuristicTests;
import cptests.RandomValueHeuristicTests;
import dataminingtests.AbstractAssociationRuleMinerTests;
import dataminingtests.AbstractItemsetMinerTests;
import dataminingtests.AprioriTests;
import dataminingtests.BruteForceAssociationRuleMinerTests;

public class Main {
    public static void main(String[] args) {
        boolean ok = true;

        ok = ok && VariableTests.testGetName();
        ok = ok && VariableTests.testGetDomain();
        ok = ok && VariableTests.testEquals();
        ok = ok && VariableTests.testHashCode();
        ok = ok && BooleanVariableTests.testConstructor();
        ok = ok && BooleanVariableTests.testEquals();
        ok = ok && BooleanVariableTests.testHashCode();
        ok = ok && DifferenceConstraintTests.testGetScope();
	ok = ok && DifferenceConstraintTests.testIsSatisfiedBy();
	ok = ok && ImplicationTests.testGetScope();
	ok = ok && ImplicationTests.testIsSatisfiedBy();
	ok = ok && UnaryConstraintTests.testGetScope();
	ok = ok && UnaryConstraintTests.testIsSatisfiedBy();
        ok = ok && BasicActionTests.testIsApplicable();
	ok = ok && BasicActionTests.testSuccessor();
	ok = ok && BasicActionTests.testGetCost();
	ok = ok && BasicGoalTests.testIsSatisfiedBy();
	ok = ok && DFSPlannerTests.testPlan();
	ok = ok && BFSPlannerTests.testPlan();
	ok = ok && DijkstraPlannerTests.testPlan();
	ok = ok && AStarPlannerTests.testPlan();
	ok = ok && AbstractSolverTests.testIsConsistent();
	ok = ok && BacktrackSolverTests.testSolve();
	ok = ok && ArcConsistencyTests.testEnforceNodeConsistency();
	ok = ok && ArcConsistencyTests.testRevise();
	ok = ok && ArcConsistencyTests.testAC1();
	ok = ok && MACSolverTests.testSolve();
	ok = ok && HeuristicMACSolverTests.testSolve();
	ok = ok && NbConstraintsVariableHeuristicTests.testBest();
	ok = ok && DomainSizeVariableHeuristicTests.testBest();
	ok = ok && RandomValueHeuristicTests.testOrdering();
	ok = ok && AbstractItemsetMinerTests.testFrequency();
	ok = ok && AprioriTests.testFrequentSingletons();
	ok = ok && AprioriTests.testCombine();
	ok = ok && AprioriTests.testAllSubsetsFrequent();
	ok = ok && AprioriTests.testExtract();
	ok = ok && AbstractAssociationRuleMinerTests.testFrequency();
	ok = ok && AbstractAssociationRuleMinerTests.testConfidence();
	ok = ok &&
	BruteForceAssociationRuleMinerTests.testAllCandidatePremises();
	ok = ok && BruteForceAssociationRuleMinerTests.testExtract();

        System.out.println(ok ? "All tests OK" : "At least one test KO");
    }
}
