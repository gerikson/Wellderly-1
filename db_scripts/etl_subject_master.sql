/* tables related to the demographc data*/

DROP
	TABLE gene.subject_master CASCADE; 
CREATE
	TABLE gene.subject_master( patient_id VARCHAR
	(
		80
	)
	,
	sample_type INT,
	eligibility INT,
	elig_comm VARCHAR,
	enroll_dt DATE,
	gender INT,
	birth_dt DATE,
	height_ft INT,
	height_in INT,
	height_cm INT,
	weight_lbs INT,
	weight_kg INT,
	hisp_ethnicity INT,
	other_race_ethn VARCHAR,
	origin_mat_grmother VARCHAR
	(
		80
	)
	,
	origin_mat_grfather VARCHAR
	(
		80
	)
	,
	origin_pat_grmother VARCHAR
	(
		80
	)
	,
	origin_pat_grfather VARCHAR
	(
		80
	)
	,
	mother_age_alive INT,
	mother_age_death INT,
	mother_cause_death VARCHAR
	(
		80
	)
	,
	father_age_alive INT,
	father_age_death INT,
	father_cause_death VARCHAR
	(
		80
	)
	,
	have_siblings BOOLEAN,
	smoking_hist INT,
	curr_pk_yrs INT,
	former_pk_yrs INT,
	curr_alcohol BOOLEAN,
	drinks_wk VARCHAR,
	exercise_3x BOOLEAN,
	highest_educ INT,
	reqs_assist BOOLEAN,
	take_meds BOOLEAN,
	depr_anx BOOLEAN,
	osteopor BOOLEAN,
	hrt BOOLEAN,
	gerd BOOLEAN,
	bph BOOLEAN,
	osteoarth BOOLEAN,
	hypothy BOOLEAN,
	hyperten BOOLEAN,
	dyslipid BOOLEAN,
	blad_con BOOLEAN,
	copd_asthma BOOLEAN,
	glaucoma BOOLEAN,
	macular_degen BOOLEAN,
	oth_med_cond_comment VARCHAR
	(
		80
	)
	,
	med_hist_comment VARCHAR
	(
		800
	)
	,
	last_hist_phys VARCHAR
	(
		800
	)
	,
	gene_bk_related BOOLEAN,
	load_date DATE,
	load_process VARCHAR
	(
		15
	)
	); 
	
INSERT
INTO
	gene.subject_master(patient_id,
	sample_type,
	eligibility,
	elig_comm,
	enroll_dt,
	gender,
	birth_dt,
	height_ft,
	height_in,
	height_cm,
	weight_lbs,
	weight_kg,
	hisp_ethnicity,
	other_race_ethn,
	origin_mat_grmother,
	origin_mat_grfather,
	origin_pat_grmother,
	origin_pat_grfather,
	mother_age_alive,
	mother_age_death,
	mother_cause_death,
	father_age_alive,
	father_age_death,
	father_cause_death,
	have_siblings,
	smoking_hist,
	curr_pk_yrs,
	former_pk_yrs,
	curr_alcohol,
	drinks_wk,
	exercise_3x,
	highest_educ,
	reqs_assist,
	take_meds,
	depr_anx,
	osteopor,
	hrt,
	gerd,
	bph,
	osteoarth,
	hypothy,
	hyperten,
	dyslipid,
	blad_con,
	copd_asthma,
	glaucoma,
	macular_degen,
	oth_med_cond_comment,
	med_hist_comment,
	last_hist_phys,
	gene_bk_related,
	load_date,
	load_process) 
SELECT
	patient_id,
	CAST(sample_type AS NUMERIC),
	CAST(eligibility AS NUMERIC),
	elig_comm,
	CAST(enroll_dt AS DATE),
	CAST(gender AS NUMERIC),
	CAST(birth_dt AS DATE),
	CAST(height_ft AS NUMERIC),
	CAST(height_in AS NUMERIC),
	CAST(height_cm AS NUMERIC),
	CAST(weight_lbs AS NUMERIC),
	CAST(weight_kg AS NUMERIC),
	CAST(hisp_ethnicity AS NUMERIC),
	other_race_ethn,
	origin_mat_grmother,
	origin_mat_grfather,
	origin_pat_grmother,
	origin_pat_grfather,
	CAST(mother_age_alive AS NUMERIC),
	CAST(mother_age_death AS NUMERIC),
	mother_cause_death,
	CAST(father_age_alive AS NUMERIC),
	CAST(father_age_death AS NUMERIC),
	father_cause_death,
	CAST(have_siblings AS BOOLEAN),
	CAST(smoking_hist AS NUMERIC),
	CAST(curr_pk_yrs AS NUMERIC),
	CAST(former_pk_yrs AS NUMERIC),
	CAST(curr_alcohol AS BOOLEAN),
	drinks_wk,
	CAST(exercise_3x AS BOOLEAN),
	CAST(highest_educ AS NUMERIC),
	CAST(reqs_assist AS BOOLEAN),
	CAST(take_meds AS BOOLEAN),
	CAST(depr_anx AS BOOLEAN),
	CAST(osteopor AS BOOLEAN),
	CAST(hrt AS BOOLEAN),
	CAST(gerd AS BOOLEAN),
	CAST(bph AS BOOLEAN),
	CAST(osteoarth AS BOOLEAN),
	CAST(hypothy AS BOOLEAN),
	CAST(hyperten AS BOOLEAN),
	CAST(dyslipid AS BOOLEAN),
	CAST(blad_con AS BOOLEAN),
	CAST(copd_asthma AS BOOLEAN),
	CAST(glaucoma AS BOOLEAN),
	CAST(macular_degen AS BOOLEAN),
	oth_med_cond_comment,
	med_hist_comment,
	last_hist_phys,
	CAST(gene_bk_related AS BOOLEAN),
	sysdate,
	'ETL Load' 
FROM
	staging.demographics 
DROP
	TABLE gene.subject_race; 

CREATE
	TABLE gene.subject_race( patient_id VARCHAR
	(
		25
	)
	NOT NULL,
	race___1 BOOLEAN,
	race___2 BOOLEAN,
	race___3 BOOLEAN,
	race___4 BOOLEAN,
	race___5 BOOLEAN,
	race___6 BOOLEAN,
	race___7 BOOLEAN,
    load_date DATE,
	load_process VARCHAR
	(
		80
	)
	NOT NULL); 


INSERT
INTO
	gene.subject_race( patient_id, 
    race___1,
	race___2,
	race___3,
	race___4,
	race___5,
	race___6,
	race___7,
	load_date,
	load_process) 
 select
	patient_id,
	cast(race___1 as boolean),
	cast(race___2 as boolean),
	cast(race___3 as boolean),
	cast(race___4 as boolean),
	cast(race___5 as boolean),
	cast(race___6 as boolean),
	cast(race___7 as boolean),
	sysdate,
	'ETL Load' 
FROM
	staging.demographics


select * from gene.subject_race

create table gene.subject_siblings(
patient_id	varchar(80),
sib_gender	int,
sib_age_alive	int,
sib_age_death	int,
sib_cause_death	varchar(80),
sib_ordinal	int,
load_date	date,
load_process	varchar(80)
);


select 
case
when sib1_gender is not null
then sib1_gender
end
case
else if sib2_gender is not null
then sib2_gender
end 
from staging.demographics


create table gene.subject_meds(
patient_id	varchar(80),
med_type	varchar(80),
load_date	date,
load_process	varchar(80)
)

drop table business_terms

create table business_terms(
term_id     int not null,
desc_term    varchar(800) not null,
table_name  varchar(100) not null,
column_name varchar(100) not null);

CREATE SEQUENCE "business_term_seq"
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START WITH 1
	NO CYCLE

create table ontology_master(
ontology_id int not null,
ontology_desc       varchar(800));


drop table ontology_xref;

create table ontology_xref(
ontology_id     int    not null,
term_id         int    not null
);

CREATE SEQUENCE "ontology_seq"
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START WITH 1
	NO CYCLE

