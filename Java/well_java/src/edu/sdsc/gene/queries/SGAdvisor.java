package edu.sdsc.gene.queries;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "allele_query")
@XmlType(propOrder = { "patientId", "chrom", "alt", "ref", "begin_pos", "end_pos",
		"well_af", "genome_af", "gene", "gene_type" })
public class SGAdvisor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4028324867576281161L;
	protected int patientId;
	protected String chrom;
	protected int begin_pos;
	protected int end_pos;
	protected String ref;
	protected String alt;
	protected float well_af;
	protected float genome_af;
	protected String go_id;
	protected String gene;
	protected String gene_type;

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	@XmlElement
	public int getPatientId() {
		return patientId;
	}

	public void setChrom(String chrom) {
		this.chrom = chrom;
	}

	@XmlElement
	public String getChrom() {
		return chrom;
	}

	public void setBeginPos(int begin_pos) {
		this.begin_pos = begin_pos;
	}

	@XmlElement
	public int getBeginPos() {
		return begin_pos;
	}
	
	public void setEndPos(int end_pos) {
		this.end_pos = end_pos;
	}

	@XmlElement
	public int getEndPos() {
		return end_pos;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	@XmlAttribute
	public String getRef() {
		return ref;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	@XmlAttribute
	public String getAlt() {
		return alt;
	}

	public void setWellAf(float well_af) {
		this.well_af = well_af;
	}

	@XmlAttribute
	public float getWellAf() {
		return well_af;
	}

	public void setGOID(String go_id) {
		this.go_id = go_id;
	}

	@XmlAttribute
	public String getGOID() {
		return go_id;
	}

	public void setGenomeAf(float genome_af) {
		this.genome_af = genome_af;
	}

	@XmlAttribute
	public float getGenomeAf() {
		return genome_af;
	}

	public void setGene(String gene) {
		this.gene = gene;
	}

	@XmlAttribute
	public String getGene() {
		return gene;
	}

	public void setGeneType(String gene_type) {
		this.gene_type = gene_type;
	}

	@XmlAttribute
	public String getGeneType() {
		return gene_type;
	}

}
