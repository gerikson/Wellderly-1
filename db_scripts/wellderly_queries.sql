--all patients, all variants

SELECT
	d.patient_id,
	gender,
	birth_dt,
	height_ft,
	height_in,
	weight_lbs,
	haplotype,
	chromosome,
	begin_pos,
	end_pos,
	vartype,
	reference,
	allele,
	notes,
	variant_quality,
	gene,
	gene_symbol,
	gene_type,
	location,
	distance,
	genomes_af,
	wellderly_af325,
	nhlbi tfbs_deltas 
FROM
	gene.sg_advisor sg,
	staging.demographics d 
WHERE
	d.patient_id = sg.patient_id 
	and patient_id = ?


--all patients with allele frequencies <= .01
SELECT
	d.patient_id,
	gender,
	birth_dt,
	height_ft,
	height_in,
	weight_lbs,
	haplotype,
	chromosome,
	begin_pos,
	end_pos,
	vartype,
	reference,
	allele,
	notes,
	variant_quality,
	gene,
	gene_symbol,
	gene_type,
	location,
	distance,
	genomes_af,
	wellderly_af325,
	nhlbi tfbs_deltas 
FROM
	gene.sg_advisor sg,
	staging.demographics d 
WHERE
	d.patient_id = sg.patient_id AND
	wellderly_af325 <= .01 AND
	genomes_af <= .01; ;


--all patients with allele frequencies <= .01 and high variant quality
SELECT
	d.patient_id,
	gender,
	birth_dt,
	height_ft,
	height_in,
	weight_lbs,
	haplotype,
	chromosome,
	begin_pos,
	end_pos,
	vartype,
	reference,
	allele,
	notes,
	variant_quality,
	gene,
	gene_symbol,
	gene_type,
	location,
	distance,
	genomes_af,
	wellderly_af325,
	nhlbi tfbs_deltas 
FROM
	gene.sg_advisor sg,
	staging.demographics d 
WHERE
	d.patient_id = sg.patient_id AND
	wellderly_af325 <= .01 AND
	genomes_af <= .01 AND
	variant_quality = 'VQHIGH'; 
