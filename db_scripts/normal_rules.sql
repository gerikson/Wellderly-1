drop table gene.exome_vcf cascade
GO

CREATE TABLE gene.exome_vcf AS 
SELECT chrom, pos, ref, alt, id, TypeRules(ref,alt)                                                                                                                                                                               
	AS 'vartype', ReferenceRules(TypeRules(ref, alt), ref, alt) AS 'mod_ref', 
	AltRules(TypeRules(ref, alt), ref, alt) AS 'mod_alt', PosRules(TypeRules(ref
	, alt), ref, alt) + pos AS 'mod_start_pos', 
	CASE TypeRules(ref, alt) 
		WHEN 'ins' 
		THEN pos 
		ELSE (PosRules(TypeRules(ref, alt), ref, alt) + LENGTH(ReferenceRules(
		TypeRules(ref, alt), ref, alt)) + pos) 
	END                                                                                                                                                                                                                            
	AS "mod_end_pos", qual, filter, info, now() AS "date_loaded", 'ETL Process' 
	AS "loaded_by" 
FROM staging.exome_vcf
GO

drop table gene.dbsnp_vcf cascade
GO

CREATE TABLE gene.dbsnp_vcf AS 
SELECT chrom, pos, ref, alt, id, TypeRules(ref,alt)                                                                                                                                                                               
	AS 'vartype', ReferenceRules(TypeRules(ref, alt), ref, alt) AS 'mod_ref', 
	AltRules(TypeRules(ref, alt), ref, alt) AS 'mod_alt', PosRules(TypeRules(ref
	, alt), ref, alt) + pos AS 'mod_start_pos', 
	CASE TypeRules(ref, alt) 
		WHEN 'ins' 
		THEN pos 
		ELSE (PosRules(TypeRules(ref, alt), ref, alt) + LENGTH(ReferenceRules(
		TypeRules(ref, alt), ref, alt)) + pos) 
	END                                                                                                                                                                                                                            
	AS "mod_end_pos", qual, filter, info, now() AS "date_loaded", 'ETL Process' 
	AS "loaded_by" 
FROM staging.dbsnp_vcf
GO

drop table gene.one_k_genome_vcf cascade
GO

CREATE TABLE gene.one_k_genome_vcf AS 
SELECT chrom, pos, ref, alt, id, TypeRules(ref,alt)                                                                                                                                                                               
	AS 'vartype', ReferenceRules(TypeRules(ref, alt), ref, alt) AS 'mod_ref', 
	AltRules(TypeRules(ref, alt), ref, alt) AS 'mod_alt', PosRules(TypeRules(ref
	, alt), ref, alt) + pos AS 'mod_start_pos', 
	CASE TypeRules(ref, alt) 
		WHEN 'ins' 
		THEN pos 
		ELSE (PosRules(TypeRules(ref, alt), ref, alt) + LENGTH(ReferenceRules(
		TypeRules(ref, alt), ref, alt)) + pos) 
	END                                                                                                                                                                                                                            
	AS "mod_end_pos", qual, filter, info, now() AS "date_loaded", 'ETL Process' 
	AS "loaded_by" 
FROM staging.one_k_genome_vcf
GO

drop table gene.illumina_vcf cascade
GO

CREATE TABLE gene.illumina_vcf AS 
SELECT chrom, pos, ref, alt, id, TypeRules(ref,alt)                                                                                                                                                                               
	AS 'vartype', ReferenceRules(TypeRules(ref, alt), ref, alt) AS 'mod_ref', 
	AltRules(TypeRules(ref, alt), ref, alt) AS 'mod_alt', PosRules(TypeRules(ref
	, alt), ref, alt) + pos AS 'mod_start_pos', 
	CASE TypeRules(ref, alt) 
		WHEN 'ins' 
		THEN pos 
		ELSE (PosRules(TypeRules(ref, alt), ref, alt) + LENGTH(ReferenceRules(
		TypeRules(ref, alt), ref, alt)) + pos) 
	END                                                                                                                                                                                                                            
	AS "mod_end_pos", qual, filter, info, format, file, now() AS "date_loaded", 'ETL Process' 
	AS "loaded_by" 
FROM staging.illumina_vcf
GO

select count(*) from gene.illumina_vcf3 where vartype = 'ins' limit 500

select * from transactions where description like '%gene.illumina_vcf %'

select CHANGE_CURRENT_STATEMENT_RUNTIME_PRIORITY(45035996279138358, 'high')

select chromosome, begin_pos, end_pos, vartype, reference, allele1Seq, count(allele1Seq), allele2Seq, count(allele2Seq)
from gene.variant_quality where vartype = 'del' and reference is not null
group by  chromosome, begin_pos, end_pos, vartype, reference, allele1Seq, allele2Seq
order by chromosome, begin_pos, end_pos, length(allele1Seq), allele1Seq, length(allele2Seq), allele2Seq limit 5000

select * from gene.variant_quality limit 1000

select chrom, mod_start_pos, mod_end_pos, vartype, mod_ref, mod_alt, count(mod_alt)
from gene.illumina_vcf
group by  chrom, mod_start_pos, mod_end_pos, vartype, mod_ref, mod_alt
order by chrom, mod_start_pos, mod_end_pos, length(mod_alt), mod_alt limit 5000

select chrom, pos, ref, alt, info from gene.illumina_vcf limit 5000


