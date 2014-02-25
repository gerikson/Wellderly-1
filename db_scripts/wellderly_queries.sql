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
	d.patient_id = sg.patient_id AND
	patient_id = ?

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
	nhlbi 
FROM
	gene.sg_advisor sg,
	staging.demographics d 
WHERE
	d.patient_id = sg.patient_id AND
	wellderly_af325 <= .01 AND
	genomes_af <= .01 AND
    nhlbi <= .01
ORDER BY patient_id, chromosome, begin_pos; 


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
	nhlbi 
FROM
	gene.sg_advisor sg,
	staging.demographics d 
WHERE
	d.patient_id = sg.patient_id AND
	wellderly_af325 <= .01 AND
	genomes_af <= .01 AND
    nhlbi <= .01 AND
	variant_quality = 'VQHIGH'
ORDER BY patient_id, chromosome, begin_pos; 

-- all allele with a given GO_ID
SELECT
	patient_id,
	go_id,
	gene,
	chromosome,
	allele,
	reference,
	begin_pos,
	end_pos,
	variant_quality,
	genomes_af,
	wellderly_af325,
    nhlib
FROM
	gene.sg_advisor 
WHERE
	genomes_af <= ? AND
	variant_quality = ? 
ORDER BY
	chromosome,
	begin_pos,
	end_pos 
SELECT
	patient_id,
	go_id,
	gene,
	chromosome,
	allele,
	reference,
	begin_pos,
	end_pos,
	genomes_af,
	wellderly_af325 
FROM
	gene.sg_advisor 
WHERE
	go_id IN(?)
ORDER BY patient_id, chromosome, begin_pos; 