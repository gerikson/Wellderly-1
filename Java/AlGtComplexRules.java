/***
 * Class that parses, trims and sets genotypes for complex mutations (coma separated at the same locus)
 * @author gerikson
 * @date October 30, 2014
 * 1) Read in each variant
 * 2) Split the multiple alleles by comma
 * 3) Trim each allele, modify position, ref, alt, assignate varType
 * 4) If both alleles are of the same varType set 1/2 genotype and list the alles
 * 5) Else represent each Allele on a new line, set genotypes  as 1/X, X/1
 */


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

	public void modifyAlleles(subject_id, chr, begin, ref, alt, genotype) {
		 String[] variation = null;
		 if (alt.contains(",")) {
             variation =  alt.split(",");
             ComplexObject vcfObject1 = new ComplexObject()
             ComplexObject vcfObject2 = new ComplexObject()
             //Trim both alleles
             vcfObject1 = vcfTrim(chr, ref, variation[0], begin, subject_id)
             vcfObject2 = vcfTrim(chr, ref, variation[1], begin, subject_id)
             if (genotype.contains("|")) {
            	 	
             }
             //are both alleles of same varType
             if (vcfObject1.getVarType() == vcfObject2.getVarType()) {
            	 	//if this is deletion compare the refs
            	    if (vcfObject1.getVarType() == "del") {
	            	 	if (vcfObject1.getRef().length() < vcfObject2.getRef().length()) {
	            	 		System.out.println(subject_id + "\t" + chr + "\t" + pos + "\t" + ref + "\t" + alt + "\t" + vcfObject1.getVarType() + "\t" +
	    						  + Integer.toString(vcfObject1.getPos()) + "\t"+ vcfObject1.getAlt() + "\t" + vcfObject1.getRef() + "\t" +  vcfObject2.getRef() + "\t" + 
	            	 				"["  + vcfObject1.getRef() + ","  + vcfObject2.getRef() +  "]\t"+ genotype +"\t");
	            	 	} else {
	            	 		if (genotype == "2|1") {
	            	 			genotype = "1|2";
	            	 		} else if (genotype == "1|2") {
	            	 			genotype = "2|1";
	            	 		} else {
	            	 			genotype = "1/2";
	            	 		}
	            	 		System.out.println(subject_id + "\t" + chr + "\t" + pos + "\t" + ref + "\t" + alt + "\t" + vcfObject1.getVarType() + "\t" +
	    						  + Integer.toString(vcfObject1.getPos()) + "\t"+ vcfObject1.getAlt() + "\t" + vcfObject2.getRef() + "\t" +  vcfObject1.getRef() + "\t" + 
	            	 				"["  + vcfObject2.getRef() + ","  + vcfObject1.getRef() +  "]\t1/2\t");
	            	 	}
            	    } 
            	    //if it's snp, insertion or sub compare the allele lenght
            	    else {
	            	 	if (vcfObject1.getAlt().length() < vcfObject2.getAlt().length()) {
	            	 		if (genotype == "2|1") {
	            	 			genotype = "X|1";
	            	 		} else if (genotype == "1|2") {
	            	 			genotype = "1|X";
	            	 		} else {
	            	 			genotype = "1/X";
	            	 		}
	            	 		System.out.println(subject_id + "\t" + chr + "\t" + pos + "\t" + ref + "\t" + alt + "\t" + vcfObject1.getVarType() + "\t" +
	    						  + Integer.toString(vcfObject1.getPos()) + "\t"+ vcfObject1.getRef() + "\t" + vcfObject1.getAlt() + "\t" +  vcfObject2.getAlt() + "\t" + 
	            	 				"["  + vcfObject1.getAlt() + ","  + vcfObject2.getAlt() +  "]\t1/X\t");
	            	 	} else {
	            	 		System.out.println(subject_id + "\t" + chr + "\t" + pos + "\t" + ref + "\t" + alt + "\t" + vcfObject1.getVarType() + "\t" +
	    						  + Integer.toString(vcfObject1.getPos()) + "\t"+ vcfObject1.getRef() + "\t" + vcfObject2.getAlt() + "\t" +  vcfObject1.getAlt() + "\t" + 
	            	 				"["  + vcfObject2.getAlt() + ","  + vcfObject1.getAlt() +  "]\t1/X\t");
	            	 	}
            	    }
             } 
             //2 separate raws have to be created
             else {
      	 		if (genotype == "2|1") {
     	 			genotype = "1|X";
     	 		} else if (genotype == "1|2") {
     	 			genotype = "X|1";
     	 		} else {
     	 			genotype = "X/1";
     	 		}
            	  if (vcfObject1.getVarType() == "del") {
     	 		System.out.println(subject_id + "\t" + chr + "\t" + pos + "\t" + ref + "\t" + alt + "\t" + vcfObject1.getVarType() + "\t" +
						  + Integer.toString(vcfObject1.getPos()) + "\t"+ vcfObject1.getRef() + "\t" + vcfObject1.getAlt() + "\t" +  vcfObject2.getAlt() + "\t" + 
     	 				"["  + vcfObject1.getRef()  "]\t1/X\t");
     	 		System.out.println(subject_id + "\t" + chr + "\t" + pos + "\t" + ref + "\t" + alt + "\t" + vcfObject2.getVarType() + "\t" +
						  + Integer.toString(vcfObject2.getPos()) + "\t"+ vcfObject2.getRef() + "\t" + vcfObject1.getAlt() + "\t" +  vcfObject2.getAlt() + "\t" + 
     	 				"["  + vcfObject2.getRef()  "]\tX/1\t");
             }
            	  else {
           	 		System.out.println(subject_id + "\t" + chr + "\t" + pos + "\t" + ref + "\t" + alt + "\t" + vcfObject1.getVarType() + "\t" +
  						  + Integer.toString(vcfObject1.getPos()) + "\t"+ vcfObject1.getRef() + "\t" + vcfObject1.getAlt() + "\t" +  vcfObject2.getAlt() + "\t" + 
       	 				"["  + vcfObject1.getAlt()  "]\t1/X\t");
       	 		    System.out.println(subject_id + "\t" + chr + "\t" + pos + "\t" + ref + "\t" + alt + "\t" + vcfObject2.getVarType() + "\t" +
  						  + Integer.toString(vcfObject2.getPos()) + "\t"+ vcfObject2.getRef() + "\t" + vcfObject1.getAlt() + "\t" +  vcfObject2.getAlt() + "\t" + 
       	 				"["  + vcfObject2.getAlt()  "]\tX/1\t");
            	  }
            	 
         }
		 
	}
	
	//Method that trims the begin and ends of complex variations
	public void vcfTrim(String chrom, String ref, String var, String beg, subject_id) {
		   int begin = Integer.parseInt(beg) - 1;  
		 //check the tail 
		   for (int i=1; i < Math.min(var.length(),ref.length()); i++) {
		       if (var.substring(var.length()-i-1, var.length()-i).equals(ref.substring(ref.length()-i-1,ref.length()-i))) {
		             end = end+1 ; }            
		       else {
		             break;
		       }
		   } 
   
		     if (end != 0) {
		    	 	ref = ref.substring(0, ref.length() - end);
		    	 	var = var.substring(0, var.length() - end);
		     } 
		     int end = 0;
		     String varType = null;
		     
		     int  start = 0;
			  for (int i=0; i < Math.min(var.length(),ref.length()); i++) {
			      if (var.charAt(i) == ref.charAt(i)) {
			          start = start+1 ;}            
			      else {
			          break;
			      }
			  }
			  
			 //trim the head 
			  ref = ref.substring(start);
			  var = var.substring(start);
			  begin = begin + start;
     
		     //is this a snp 
		    if (ref.length() == 1 && var.length() == 1) {
		        varType = "snp";
		    } 
    
		    else if (ref.length() == 0 && var.length() > 0) {
		        begin = begin + 1;
		        varType = "ins";
		        ref = "-";
		    }
		    
		    else if (ref.length() > 0 && var.length() == 0) {
		        varType = "del";
		        var = "-";
		        
		    }
				    
		    else if (ref.length() > 0 && var.length() > 0 && !ref.equals(var)) {
		        varType = "sub";
		        
		    }      
		    ComplexObject vcfObject = new ComplexObject(chrom, Integer.toString(begin), ref, var, varType, indiv);
		    return vcfObject;
}
	
	public void getComplexData() throws Exception {

		Connection conn = null;
		ResultSet rs = null;

		try {
			conn = WellConn.getConn();
		} catch (Exception e) {
			System.out.println(e.toString());

		}
        
		String query = "select subject_id, chrom, pos, ref, alt, split_part(file, ':', 1) as GT limit 500";


		try {

			PreparedStatement ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

		} catch (Exception e) {
			System.out.println(e.toString());

		}
		
		modifyAlleles(subject_id, chrom, pos, ref, alt, GT)
	}


}
