package edu.sdsc.wellderly.rules;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "gene.illumina_vcf")
public class VCFData {

	private String subjectID;
	private String ref;
	private String genotype;
	private int pos;
	private String chrom;
	private String alt;
	private String allele1;
	private String allele2;
	private String altList1;
	private String modAlt;
	private String modRef;
	private int modStartPos;
	private String vartype;
	

	public VCFData() {

	}


	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getGenotype() {
		return genotype;
	}

	public void setGenotype(String genotype) {
		this.genotype = genotype;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public String getChrom() {
		return chrom;
	}

	public void setChrom(String chrom) {
		this.chrom = chrom;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public String getAllele1() {
		return allele1;
	}

	public void setAllele1(String allele1) {
		this.allele1 = allele1;
	}

	public String getAllele2() {
		return allele2;
	}

	public void setAllele2(String allele2) {
		this.allele2 = allele2;
	}


	public String getAltList1() {
		return altList1;
	}


	public void setAltList1(String altList1) {
		this.altList1 = altList1;
	}


	public String getSubjectID() {
		return subjectID;
	}


	public void setSubjectID(String subjectID) {
		this.subjectID = subjectID;
	}


	public String getModAlt() {
		return modAlt;
	}


	public void setModAlt(String modAlt) {
		this.modAlt = modAlt;
	}


	public String getModRef() {
		return modRef;
	}


	public void setModRef(String modRef) {
		this.modRef = modRef;
	}


	public int getModStartPos() {
		return modStartPos;
	}


	public void setModStartPos(int modStartPos) {
		this.modStartPos = modStartPos;
	}


	public String getVartype() {
		return vartype;
	}


	public void setVartype(String vartype) {
		this.vartype = vartype;
	}

}
