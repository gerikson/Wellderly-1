package edu.sdsc.wellderly.rules;

import edu.sdsc.dao.WellConn;
import edu.sdsc.wellderly.rules.VCFData;
import edu.sdsc.wellderly.rules.VCFGroup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import edu.sdsc.wellderly.rules.LengthFirstComparator;

public class AlGtRules {
	
	
	static final Set<String> alts1 = new TreeSet<String>();
	static final Set<String> modAlts = new TreeSet<String>();

	public AlGtRules() {
		super();
	}

	public static void main(String args[]) {
		try {
			getData();
			//AlGtComplexRules vcfComp = new AlGtComplexRules();
			//vcfComp.getComplexData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void getData() throws Exception {

		Connection conn = null;
		ResultSet rs = null;

		try {
			conn = WellConn.getConn();
		} catch (Exception e) {
			System.out.println(e.toString());

		}
		//ignore offset and limit. For testing only. They will be removed when in production.
		String query = "select chrom, pos, ref, alt, mod_start_pos, mod_ref, mod_alt, split_part(file, ':', 1) as GT, subject_id, vartype "
				+ "from gene.illumina_vcf where chrom = 'chr2' " 
				+ "and alt not like '%,%' and vartype = 'del' or (alt like '%,%' and length(split_part(alt,',', 1)) = 1 "
				+ "and length(split_part(alt,',', 2)) = 1 and vartype = 'del' and chrom = 'chr22') "
				+ "Union "
				+ "select chrom, pos, ref, alt, mod_start_pos, mod_ref, mod_alt, split_part(file, ':', 1) as GT, subject_id, vartype,  mod_end_pos "
				+ "from gene.illumina_vcf where chrom = 'chr2' " 
				+ "union "
				+ "select subject_id, chrom, pos, ref, split_part(alt, ',', 1) as allele1, "
				+ "split_part(alt, ',', 2) as allele2, "
				+ "TypeRules(ref,split_part(alt, ',', 1)) as vartype1, "
				+ "TypeRules(ref,split_part(alt, ',', 2)) as vartype2, "
				+ "ReferenceRules(TypeRules(ref,split_part(alt, ',', 1)), ref, split_part(alt, ',', 1)) as mod_ref1, "
				+ "ReferenceRules(TypeRules(ref,split_part(alt, ',', 2)), ref, split_part(alt, ',', 2)) as mod_ref2, "
				+ "AltRules(TypeRules(ref,split_part(alt, ',', 1)), ref, split_part(alt, ',', 1)) as mod_alt1, "
				+ "AltRules(TypeRules(ref,split_part(alt, ',', 2)), ref, split_part(alt, ',', 2)) as mod_alt2, "
				+ "PosRules(upper(TypeRules(ref,split_part(alt, ',', 1))), ref, split_part(alt, ',', 1)) + pos as pos_alt1, "
				+ "PosRules(upper(TypeRules(ref,split_part(alt, ',', 2))), ref, split_part(alt, ',', 2)) + pos as pos_alt2, "
				+ "split_part(file, ':', 1) as GT "
				+ "from gene.illumina_vcf where  alt like '%,%' and pos = 3097725 "
				+ "and length(split_part(alt,',', 1)) > 1 or length(split_part(alt,',', 2)) > 1 "
				+ "order by 1, 2, 3, 5, 6 " + "limit 500";
		
		try {

			PreparedStatement ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

		} catch (Exception e) {
			System.out.println(e.toString());

		}

		ArrayList<Object> recordList = new ArrayList<Object>();
		ArrayList<Object> groupList = new ArrayList<Object>();

		try {

			int lastPos = 0;

			VCFGroup vcfGrp = new VCFGroup();
			while (rs.next()) {
				VCFData vcf = new VCFData();
				String chrom = rs.getString(1);
				int pos = rs.getInt(2);
				//Create a new index when chrom/pos change
				if (lastPos != 0 && lastPos != pos) {
					vcfGrp.setAltList1(alts1.toString());
					vcfGrp.setModAltList(modAlts.toString());
					groupList.add(vcfGrp);
					vcfGrp = new VCFGroup();
					alts1.clear();
					modAlts.clear();
				}
				String ref = rs.getString(3);
				String alt = rs.getString(4);
				String genotype = rs.getString(8);
				int modStartPos = rs.getInt(5);
				String modRef = rs.getString(6);
				String modAlt = rs.getString(7);
				String subjID = rs.getString(9);
				String varType = rs.getString(10);
				vcf.setChrom(chrom);
				vcfGrp.setChrom(chrom);
				vcf.setPos(pos);
				vcfGrp.setPos(pos);
				vcf.setRef(ref);
				vcf.setAlt(alt);
				vcf.setModStartPos(modStartPos);
				vcf.setModRef(modRef);
				vcf.setModAlt(modAlt);
				vcfGrp.setAlt(alt);
				vcf.setGenotype(genotype);
				vcfGrp.setGt(genotype);
				vcf.setSubjectID(subjID);
				vcf.setVartype(varType);
				createAlleles(vcf);
				recordList.add(vcf);
				lastPos = pos;
			}
			// last group record
			vcfGrp.setAltList1(alts1.toString());
			vcfGrp.setModAltList(modAlts.toString());
			groupList.add(vcfGrp);

			// cycle through the object to assign the distinct alts set to each
			// record
			for (Object group : groupList) {

				String chrom1 = ((VCFGroup) group).getChrom();
				String chrom2 = "";
				int pos1 = ((VCFGroup) group).getPos();
				int pos2 = 0;

				for (Object record : recordList) {

					chrom2 = ((VCFData) record).getChrom();
					pos2 = ((VCFData) record).getPos();
					String altList1 = ((VCFGroup) group).getAltList1();
					String modAltList = ((VCFGroup) group).getModAltList();
					// assign discrete list of alts or refs
					if (chrom1.equals(chrom2) && pos1 == pos2) {
						((VCFData) record).setAltList1(altList1);
						((VCFData) record).setModAltList(modAltList);
					}

				}
			}
			// Now that you have the distinct list recompute the genotype for
			// each variant
			for (Object record : recordList) {
				String chrom = ((VCFData) record).getChrom();
				int pos = ((VCFData) record).getPos();
				String subjID = ((VCFData) record).getSubjectID();
				String modAlt = ((VCFData) record).getModAlt();
				String modRef = ((VCFData) record).getModRef();
				int modStartPos = ((VCFData) record).getModStartPos();
				String ref = ((VCFData) record).getRef();
				String alt = ((VCFData) record).getAlt();
				String gt1 = ((VCFData) record).getGenotype();
				String allele1 = ((VCFData) record).getAllele1();
				String allele2 = ((VCFData) record).getAllele2();
				String altList = ((VCFData) record).getAltList1();
				String modAltList = ((VCFData) record).getModAltList();
				String vartype = ((VCFData) record).getVartype();
				String gt2 = createGenotype(gt1, ref, allele1, allele2, altList, vartype);
				
				//this will eventually be loaded into a delimited file and bulk loaded to the db. For now just print to standard out
				System.out.println(subjID + "\t" + chrom + "\t" + pos + "\t"
						+ ref + "\t" + alt + "\t" + vartype + "\t" + gt1 + "\t"
						+ modStartPos + "\t" + modRef + "\t" + modAlt + "\t"
						+ allele1 + "\t" + allele2 + "\t" + modAltList + "\t"
						+ gt2);
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if (conn != null)
				conn.close();
		}

	}

	// This takes the alts and the original genotype and creates the fields
	// allele1 and allele2 (like the CGI data)
	public static void createAlleles(Object vcf) {

		String[] gtA = new String[3];
		String[] alts = null;
		String gt = ((VCFData) vcf).getGenotype();
		String ref = ((VCFData) vcf).getRef();
		String alt = ((VCFData) vcf).getAlt();
		String modRef = ((VCFData) vcf).getModRef();
		String modAlta = ((VCFData) vcf).getModAlt();
		String vartype = ((VCFData) vcf).getVartype();

		gtA = gt.split("");
		if (vartype.equals("del")) {
			if (alt.contains(",")) {
				alts = alt.split(",");

				if (gtA[1].equals("0")) {
					((VCFData) vcf).setAllele1(alts[0]);
				} else {
					((VCFData) vcf).setAllele1(ref);
					alts1.add(ref);
					modAlts.add(modRef);

				}
				if (gtA[3].equals("0")) {
					((VCFData) vcf).setAllele2(alts[1]);
				} else {
					((VCFData) vcf).setAllele2(ref);
					alts1.add(ref);
					modAlts.add(modRef);
				}

			} else {
				if (gtA[1].equals("0")) {
					((VCFData) vcf).setAllele1(alt);
				} else {
					((VCFData) vcf).setAllele1(ref);
					alts1.add(ref);
					modAlts.add(modRef);

				}
				if (gtA[3].equals("0")) {
					((VCFData) vcf).setAllele2(alt);
				} else {
					((VCFData) vcf).setAllele2(ref);
					alts1.add(ref);
					modAlts.add(modRef);
				}
			}
		} else {
			String[] modAltb = null;
			if (alt.contains(",")) {
				alts = alt.split(",");
				modAltb = modAlta.split(",");

				if (gtA[1].equals("0")) {
					((VCFData) vcf).setAllele1(ref);
				} else {
					((VCFData) vcf).setAllele1(alts[0]);
					alts1.add(alts[0]);
					modAlts.add(modAltb[0]);

				}
				if (gtA[3].equals("0")) {
					((VCFData) vcf).setAllele2(ref);
				} else {
					((VCFData) vcf).setAllele2(alts[1]);
					alts1.add(alts[1]);
					modAlts.add(modAltb[1]);

				}

			} else {
				if (gtA[1].equals("0")) {
					((VCFData) vcf).setAllele1(ref);
				} else {
					((VCFData) vcf).setAllele1(alt);
					alts1.add(alt);
					modAlts.add(modAlta);

				}
				if (gtA[3].equals("0")) {
					((VCFData) vcf).setAllele2(ref);
				} else {
					((VCFData) vcf).setAllele2(alt);
					alts1.add(alt);
					modAlts.add(modAlta);
				
				}
			}
		}

	}

	// Recompute the genotype using the distinct bases list and each allele
	protected static String createGenotype(String gt, String ref, String allele1,
			String allele2, String altList, String vartype) {

		int a1 = 0;
		String a2 = "";
		int a3 = 0;

		String altList1 = altList.replace("[", "");
		String altList2 = altList1.replace("]", "");
		String genoType1 = "";
		java.lang.String[] altElement;
		String[] genoType = gt.split("");

		Collections.sort(Arrays.asList(altList2), new LengthFirstComparator());
		String base1 = null;
		String base2 = null;
		if(vartype.equals("del")){
			base1 = ref;
			base2 = ref;
		} else {
			base1 = allele1;
			base2 = allele2;
		}
	
		//if this is a simple SNP split them up and assign to allele1 and allele2
		if (altList2.contains(",")) {
			// had to add space after the comma for the split to work correctly
			altElement = altList2.split(", ");
			if (allele1.equals(base1)) {
				a1 = Arrays.asList(altElement).indexOf(base1) + 1;}
	
			if (allele2.equals(base2)) {
				a3 = Arrays.asList(altElement).indexOf(base2) + 1;}
			//put the genotype together for snps
			a2 = genoType[2];
			genoType1 = Integer.toString(a1) + a2 + Integer.toString(a3);
		} else {
			if (allele1.equals(base1)) {
				a1 = Arrays.asList(altList2).indexOf(base1) + 1;
			}
			if (allele2.equals(base2)) {
				a3 = Arrays.asList(altList2).indexOf(base2) + 1;
			}
			// create the genotype for everything but simple snps
			a2 = genoType[2];
			genoType1 = Integer.toString(a1) + a2 + Integer.toString(a3);
		}
		return genoType1;
	}
}
