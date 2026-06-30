package datamining;

import java.util.*;
import modelling.*;

public class Itemset{

	public Set<BooleanVariable> items;
	public float freq;
	
	public Itemset(Set<BooleanVariable> items, float freq){
		if(freq<0f || freq>1f){
			throw new IllegalArgumentException("freq must be between [0,1]");
		}
		this.freq = freq;
		this.items = items;
	}
	
	public Set<BooleanVariable> getItems(){
		return this.items;
	}
	
	public float getFrequency(){
		return this.freq;
	}


}
