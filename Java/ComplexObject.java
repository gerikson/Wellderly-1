package edu.sdsc.wellderly.rules;

import java.io.Serializable;


import javax.persistence.Embeddable;

@Embeddable
public class ComplexObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3406974240440761472L;
	private String chrom;
	private int pos;
	private String ref;
	private String alt;
	private String varType;
	private String individual;
	
	public ComplexObject() {
		
	}
	
	public ComplexObject(String chrom, int pos, String ref, String alt, String varType, String indiv) {
		this.chrom = chrom;
		this.pos = pos;
		this.ref = ref;
		this.alt = alt;
		this.varType = varType;
		this.individual = indiv;
	}
	
	public String getIndiv() {
		return individual;
	}

	public void setIndiv(String indiv) {
		this.individual = indiv;
	}

	public String getChrom() {
		return chrom;
	}

	public void setChrom(String chrom) {
		this.chrom = chrom;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}


	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}
	
	public String getVarType() {
		return varType;
	}

	public void setVarType(String varType) {
		this.varType = varType;
	}
	@Override
	public boolean equals(Object ob) {
		return false;

	}
}
