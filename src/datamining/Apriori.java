package datamining;

import modelling.*;
import java.util.*;

public class Apriori extends AbstractItemsetMiner{
	public BooleanDatabase base;
	
	public Apriori(BooleanDatabase base){
		super(base);
		this.base = base;
	}
	
	public Set<Itemset> frequentSingletons(float freq){
		Set<Itemset> result = new HashSet<>();
		
		for (BooleanVariable item : base.getItems()){
			SortedSet<BooleanVariable> singleton = new TreeSet<>(COMPARATOR);
			singleton.add(item);
			
			float f = frequency(singleton);
			
			if(f>=freq){
				result.add(new Itemset(singleton, f));
			}
		}
		return result;
	}
	
	public static SortedSet<BooleanVariable> combine(SortedSet<BooleanVariable> s1, SortedSet<BooleanVariable> s2){
		if (s1 == null || s2 == null) return null;
		if (s1.size() != s2.size()) return null;
		
		int k = s1.size();
    		if (k == 0) return null;
		Iterator<BooleanVariable> it1 = s1.iterator();
		Iterator<BooleanVariable> it2 = s2.iterator(); 
	
		for (int i = 0; i < k-1; i++){
			BooleanVariable v1 = it1.next();
			BooleanVariable v2 = it2.next();
			
			if(!v1.equals(v2)) return null;
		}
		
		BooleanVariable last1 = it1.next();
        	BooleanVariable last2 = it2.next();
        	if (last1.equals(last2)) return null;

        	SortedSet<BooleanVariable> combined = new TreeSet<>(COMPARATOR);
        	combined.addAll(s1);
        	combined.addAll(s2);

        	return combined;
		
		
	}
	
	public static boolean allSubsetsFrequent(Set<BooleanVariable> itemset, Collection<SortedSet<BooleanVariable>> prev) {

    		for (BooleanVariable v : itemset) {
			SortedSet<BooleanVariable> subset = new TreeSet<>(COMPARATOR);
			subset.addAll(itemset);
			subset.remove(v);

			if (!prev.contains(subset)) {
			    return false;
			}
	        }
	    return true;
	}
	
	
	@Override
	public Set<Itemset> extract(float minFreq) {
		Set<Itemset> allFrequent = new HashSet<>();

		Set<Itemset> L1 = frequentSingletons(minFreq);
		allFrequent.addAll(L1);

	        List<SortedSet<BooleanVariable>> Lk = new ArrayList<>();
	        for (Itemset it : L1) {
			SortedSet<BooleanVariable> s = new TreeSet<>(COMPARATOR);
			s.addAll(it.getItems());
			Lk.add(s);
    	        }

	    	while (!Lk.isEmpty()) {
			List<SortedSet<BooleanVariable>> candidates = new ArrayList<>();
		 	for (int i = 0; i < Lk.size(); i++) {
				for (int j = i + 1; j < Lk.size(); j++) {
					SortedSet<BooleanVariable> c = combine(Lk.get(i), Lk.get(j));
					if (c != null && allSubsetsFrequent(c, Lk)) {
				    		candidates.add(c);
					}
			    	}
		  	}

		        List<SortedSet<BooleanVariable>> Lnext = new ArrayList<>();
		        for (SortedSet<BooleanVariable> c : candidates) {
			      	float f = frequency(c);
				    if (f >= minFreq) {
					Lnext.add(c);
					allFrequent.add(new Itemset(c, f));
				    }
			}

		       Lk = Lnext;
               }

    		return allFrequent;
	}

}
