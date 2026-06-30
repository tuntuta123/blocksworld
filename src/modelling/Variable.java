package modelling;

import java.util.*;


public class Variable{
	public String nom;
	public Set<Object> type;
	
	public Variable(String nom, Set<Object> type){
		this.nom = nom;
		this.type = type;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Variable)) return false;
		Variable other = (Variable) o;  // cast
		return Objects.equals(this.nom, other.nom);
	}
	
	@Override
	public int hashCode(){
		return (nom == null) ? 0 : nom.hashCode();
	}
	
	public String getName(){
		return this.nom;
	}
	
	public Set<Object> getDomain(){
		return this.type;
	}
	public String toString(){
		/*if(this.type instanceof Integer){int a = }*/;
		return "Variable  = " +nom+ " Domain = " + type; 
	}
}
