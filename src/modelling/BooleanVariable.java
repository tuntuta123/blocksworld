package modelling;

import java.util.*;


public class BooleanVariable extends Variable{
	
	public BooleanVariable(String nom){
		super(nom, new HashSet<>(Set.of(true, false)));
	}

}
