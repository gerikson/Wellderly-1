package edu.sdsc.wellderly.rules;

import edu.sdsc.dao.WellConn;
import edu.sdsc.wellderly.rules.VCFComplexData;
import edu.sdsc.wellderly.rules.VCFComplexGroup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import edu.sdsc.wellderly.rules.LengthFirstComparator;

public class AlGtComplexRules extends AlGtSimpleRules {

	static final Set<String> alts1 = new TreeSet<String>();
	static final Set<String> alts2 = new TreeSet<String>();

	public AlGtComplexRules() {
		super();
	}

	public void getComplexData() throws Exception {

		Connection conn = null;
		ResultSet rs = null;

		try {
			conn = WellConn.getConn();
		} catch (Exception e) {
			System.out.println(e.toString());

		}

		String query = "select subject_id, chrom, pos, ref, split_part(alt, ',', 1) as allele1, "
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
		ArrayList<Object> recordList1 = new ArrayList<Object>();
		ArrayList<Object> groupList = new ArrayList<Object>();

		try {

			int lastPos = 0;

			VCFComplexGroup vcfGrp = new VCFComplexGroup();
			while (rs.next()) {
				VCFComplexData vcf = new VCFComplexData();
				VCFComplexData vcf1 = new VCFComplexData();
				String chrom = rs.getString(2);
				int pos = rs.getInt(3);
				if (lastPos != 0 && lastPos != pos) {
					vcfGrp.setAltList1(alts1.toString());
					vcfGrp.setAltList2(alts2.toString());
					groupList.add(vcfGrp);
					vcfGrp = new VCFComplexGroup();
					alts1.clear();
					alts2.clear();
				}
				String ref = rs.getString(4);
				String allele1 = rs.getString(5);
				String allele2 = rs.getString(6);
				String genotype = rs.getString(15);
				int modStartPos1 = rs.getInt(13);
				String modRef1 = rs.getString(9);
				String modAlt1 = rs.getString(11);
				int modStartPos2 = rs.getInt(14);
				String modRef2 = rs.getString(10);
				String modAlt2 = rs.getString(12);
				String subjID = rs.getString(1);
				String varType1 = rs.getString(7);
				String varType2 = rs.getString(8);
				vcf.setChrom(chrom);
				vcfGrp.setChrom(chrom);
				vcf.setPos(pos);
				vcfGrp.setChrom(chrom);
				vcfGrp.setPos(pos);
				vcf.setRef(ref);
				vcf1.setRef(ref);
				vcf.setAlt(allele1);
				vcf.setModStartPos1(modStartPos1);
				vcf.setModRef1(modRef1);
				vcf.setModAlt1(modAlt1);
				vcfGrp.setAlt(allele1);
				vcf.setAlt(allele1);
				vcf.setModStartPos2(modStartPos2);
				vcf.setModRef2(modRef2);
				vcf.setModAlt2(modAlt2);
				vcfGrp.setAlt(allele2);
				vcf.setGenotype(genotype);
				vcfGrp.setGt(genotype);
				vcf.setSubjectID(subjID);
				vcf.setVartype1(varType1);
				vcf.setVartype2(varType2);
				vcf.setAllele1(allele1);
				vcf.setAllele2(allele2);
				if (varType1.equals("del")) {
					alts1.add(ref);
				} else {
					alts1.add(allele1);
					alts1.add(allele2);
				}
				if (varType2.equals("del")) {
					alts2.add(ref);
				} else {
					alts2.add(allele1);
					alts2.add(allele2);
				}

				// super.createAlleles(vcf);
				recordList.add(vcf);
				recordList1.add(vcf1);
				lastPos = pos;
			}
			vcfGrp.setAltList1(alts1.toString());
			vcfGrp.setAltList2(alts2.toString());
			groupList.add(vcfGrp);

			// cycle through the object to assign the distinct alts set to each
			// record
			for (Object group : groupList) {

				String chrom1 = ((VCFComplexGroup) group).getChrom();
				String chrom2 = "";
				int pos1 = ((VCFComplexGroup) group).getPos();
				int pos2 = 0;

				for (Object record : recordList) {

					chrom2 = ((VCFComplexData) record).getChrom();
					pos2 = ((VCFComplexData) record).getPos();
					String altList1 = ((VCFComplexGroup) group).getAltList1();
					String altList2 = ((VCFComplexGroup) group).getAltList2();

					if (chrom1.equals(chrom2) && pos1 == pos2) {
						((VCFComplexData) record).setAltList1(altList1);
						((VCFComplexData) record).setAltList2(altList2);

					}

				}
			}

			for (Object record : recordList) {

				String chrom = ((VCFComplexData) record).getChrom();
				int pos = ((VCFComplexData) record).getPos();
				String subjID = ((VCFComplexData) record).getSubjectID();
				String modAlt1 = ((VCFComplexData) record).getModAlt1();
				String modAlt2 = ((VCFComplexData) record).getModAlt2();
				String modRef = ((VCFComplexData) record).getModRef1();
				int modStartPos1 = ((VCFComplexData) record).getModStartPos1();
				int modStartPos2 = ((VCFComplexData) record).getModStartPos2();
				String ref = ((VCFComplexData) record).getRef();

				String gt = ((VCFComplexData) record).getGenotype();
				String allele1 = ((VCFComplexData) record).getAllele1();
				String allele2 = ((VCFComplexData) record).getAllele2();
				String altList1 = ((VCFComplexData) record).getAltList1();
				String altList2 = ((VCFComplexData) record).getAltList2();
				String varType1 = ((VCFComplexData) record).getVartype1();
				String varType2 = ((VCFComplexData) record).getVartype2();

				List<String> altList3 = new ArrayList<String>();
				String altLista = null;
				String altListb = null;
				altLista = altList1.replace("[", "");
				altListb = altLista.replace("]", "");

				for (String alt : altListb.split(", ")) {
					altList3.add(alt);
				}

				List<String> altList4 = new ArrayList<String>();
				String altListc = null;
				String altListd = null;
				altListc = altList2.replace("[", "");
				altListd = altListc.replace("]", "");

				for (String alt : altListd.split(", ")) {
					altList4.add(alt);
				}

				Collections.sort(altList3, new LengthFirstComparator());
				Collections.sort(altList4, new LengthFirstComparator());

				String[] gt2 = createGenotype(gt, ref, allele1, allele2,
						altList3, varType1, altList4, varType2);

				if(varType1.equals(varType2)){
					System.out.println(subjID + "\t" + chrom + "\t" + pos + "\t"
							+ ref + "\t" + allele1 + "\t" + allele2 + "\t"
							+ varType1 + "\t" + gt + "\t"
							+ modStartPos1 + "\t"  + modRef
							+ "\t" + modAlt1 + "\t" + modAlt2 + "\t" + altList3
							+ "\t" + gt2[0] + "\t");
					
				}
				else{
					System.out.println(subjID + "\t" + chrom + "\t" + pos + "\t"
							+ ref + "\t" + allele1 + "\t" + allele2 + "\t"
							+ varType1 + "\t" + gt + "\t"
							+ modStartPos1 + "\t"  + modRef
							+ "\t" + modAlt1 + "\t" + modAlt1 + "\t" + altList3
							+ "\t" + gt2[0] + "\t");
					
					System.out.println(subjID + "\t" + chrom + "\t" + pos + "\t"
							+ ref + "\t" + allele1 + "\t" + allele2 + "\t"
							+ varType2+ "\t" + gt + "\t"
							+ modStartPos2 + "\t"  + modRef
							+ "\t" + modAlt2 + "\t" + modAlt2 + "\t" + altList4
							+ "\t" + gt2[1] + "\t");
					 	
				}
				
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if (conn != null)
				conn.close();
		}

	}

	// Recompute the genotype using the distinct list and each allele
	protected static String[] createGenotype(String gt, String ref,
			String allele1, String allele2, List<String> altList3,
			String vartype1, List<String> altList4, String vartype2) {

		int a1 = 0;
		String a2 = "";
		int a3 = 0;
		int a4 = 0;
		int a5 = 0;

		String genoType1 = "";
		String genoType2 = "";
		String[] genoType = gt.split("");

		try {

			String base1 = null;
			String base2 = null;
			
			//set the basis for the calculation. dels have a base of ref and all others have a basis of the alts
			if (vartype1.equals("del")) {
				base1 = ref;
			} else {
				base1 = allele1;
			}
			if (vartype2.equals("del")) {
				base2 = ref;
			} else {
				base2 = allele2;
			}

			if (vartype1.equals(vartype2)) {

				if (allele1.equals(base1)) {
					a1 = 0;
				} else {
					a1 = altList3.indexOf(allele1) + 1;
				}
				if (allele2.equals(base2)) {
					a3 = 0;
				} else {
					a3 = altList3.indexOf(allele2) + 1;
				}
				a2 = genoType[2];
				genoType1 = Integer.toString(a1) + a2 + Integer.toString(a3);
			} else {
				if (allele1.equals(base1)) {
					a1 = 0;
				} else {
					a1 = altList3.indexOf(allele1) + 1;
				}
				if (allele2.equals(base2)) {
					a3 = 0;
				} else {
					a3 = altList3.indexOf(allele2) + 1;
					
				}
				if (allele2.equals(base2)) {
					a4 = 0;
				} else {
					a4 = altList4.indexOf(allele1) + 1;
				}
				if (allele2.equals(base2)) {
					a5 = 0;
				} else {
					a5 = altList4.indexOf(allele2) + 1;
				}
				a2 = genoType[2];
				genoType1 = Integer.toString(a1) + a2 + "X";
				genoType2 = "X" + a2 + Integer.toString(a5);
			
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return new String[] { genoType1, genoType2 };
	}

}
