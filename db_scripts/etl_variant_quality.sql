INSERT
INTO
	gene.variant_quality 
	(
		patient_id,
		locus,
		ploidy,
		allele,
		chromosome,
		begin_pos,
		end_pos,
		varType,
		reference,
		alleleSeq,
		varScoreVAF,
		varScoreEAF,
		varQuality,
		hapLink,
		xRef,
		load_date,
		load_process
	)
SELECT
	patient_id,
	cast(locus as numeric),
	ploidy,
	allele,
	chromosome,
	cast(begin_pos as numeric),
	cast(end_pos as numeric),
	varType,
	reference,
	alleleSeq,
	cast(varScoreVAF as numeric),
	cast(varScoreEAF as numeric),
	varQuality,
	cast(hapLink as numeric),
	xRef,
	sysdate,
	'ETL process' 
FROM
	staging.variant_quality;