package datamining;

import java.util.*;
import modelling.*;

public interface ItemsetMiner{
	public BooleanDatabase getDatabase();
	public Set<Itemset> extract(float f);

}
