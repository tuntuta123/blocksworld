package datamining;

import java.util.*;
import modelling.*;

public class AssociationRule{
	
	public Set<BooleanVariable> premise;
	public Set<BooleanVariable> conclusion;
	public float freq;
	public float confidence;
	
	public AssociationRule(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion, float freq, float confidence){
		this.freq = freq;
		this.premise = premise;
		this.conclusion = conclusion;
		this.confidence = confidence;
	}
	
	public Set<BooleanVariable> getPremise(){
		return this.premise;
	}
	public Set<BooleanVariable> getConclusion(){
		return this.conclusion;
	}
	public float getFrequency(){
		return this.freq;
	}
	public float getConfidence(){
		return this.confidence;
	}

}
