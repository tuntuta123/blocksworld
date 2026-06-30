package datamining;

import java.util.*;
import modelling.*;

public interface AssociationRuleMiner{
	public BooleanDatabase getDatabase();
	public Set<AssociationRule> extract(float minFreq, float minConf);

}
