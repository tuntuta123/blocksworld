package datamining;

import modelling.BooleanVariable;
import java.util.*;

public class BruteForceAssociationRuleMiner extends AbstractAssociationRuleMiner {

	public BruteForceAssociationRuleMiner(BooleanDatabase db) {
        	super(db);
    	}
    
    	public static Set<Set<BooleanVariable>> allCandidatePremises(Set<BooleanVariable> items) {
   		Set<Set<BooleanVariable>> result = new HashSet<>();
        	List<BooleanVariable> list = new ArrayList<>(items);
        	generateSubsets(list, 0, new HashSet<>(), result);

        	// boş küme ve tüm küme hariç
        	result.remove(Collections.emptySet());
        	result.remove(new HashSet<>(items));

        	return result;
    	}

    	public static void generateSubsets(List<BooleanVariable> list, int i, Set<BooleanVariable> current, Set<Set<BooleanVariable>> result) {
        	if (i == list.size()) {
            		result.add(new HashSet<>(current));
            		return;
        	}
        	generateSubsets(list, i + 1, current, result);

        	current.add(list.get(i));
        	generateSubsets(list, i + 1, current, result);
        	current.remove(list.get(i));
    	}

    	@Override
    	public Set<AssociationRule> extract(float minFreq, float minConf) {
        	Set<AssociationRule> rules = new HashSet<>();

        	Apriori apriori = new Apriori(this.database);
        	Set<Itemset> frequent = apriori.extract(minFreq);

        	for (Itemset it : frequent) {
		    	Set<BooleanVariable> X = it.getItems();

		    	for (Set<BooleanVariable> premise : allCandidatePremises(X)) {
		        	Set<BooleanVariable> conclusion = new HashSet<>(X);
		        	conclusion.removeAll(premise);

		        	float conf;

		            	conf = AbstractAssociationRuleMiner.confidence(premise, conclusion, frequent);
		        	if (conf >= minConf) {
		            		float freq = AbstractAssociationRuleMiner.frequency(X, frequent);
		            		AssociationRule rule = new AssociationRule(premise, conclusion, freq, conf);
		            		rules.add(rule);
		        	}
		    	}
        	}

        	return rules;
    }
    
    
}
