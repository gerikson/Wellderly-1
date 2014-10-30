
ALTER TABLE gene.variant_quality
	 ALTER COLUMN patient_id
	   [ SET DEFAULT default-expression ] 
	   [ DROP DEFAULT ] 
	   [ { SET | DROP } NOT NULL] 
	   [ SET DATA TYPE data-type ] 
GO


SELECT * 
FROM TRANSACTIONS LIMIT 1000 

SELECT DISTINCT CHROMOSOME, BEGIN_POS, END_POS, ALLELE1SEQ, ALLELE2SEQ 
FROM GENE.VARIANT_QUALITY limit 1000

select * from gene.did_subject_xref where subject_id in (
SELECT DISTINCT PATIENT_ID 
FROM GENE.REF_COVERAGE); 




SELECT PATIENT_ID, CHROMOSOME, load_date, COUNT(*) 
FROM GENE.REF_COVERAGE 
WHERE CHROMOSOME = 'chr1' 
GROUP BY PATIENT_ID, CHROMOSOME, load_date 
ORDER BY PATIENT_ID, CHROMOSOME, load_date 


COPY GENE.REF_COVERAGE (OFFSET_COV,REFSCORE, UNIQUESEQUENCECOVERAGE, WEIGHTSUMSEQUENCECOVERAGE, GCCORRECTEDCOVERAGE, GROSSWEIGHTSUMSEQUENCECOVERAGE) WITH SOURCE PLOADDELIMITEDSOURCE(file='/localdisk/batch01/GS000010896-DID/GS000008165-ASM/GS01077-DNA_A02/ASM/REF/coverageRefScore-chr10-GS000008165-ASM.tsv.bz2') DELIMITER E'\t' SKIP 9  TRAILING NULLCOLS DIRECT;


select * from locks

select did from gene.did_subject_xref where subject_id in
(select distinct patient_id from gene.ref_coverage)
order by 1

select patient_id, count(distinct chromosome) from gene.ref_coverage 
group by patient_id order by 1

select count(*) from gene.ref_coverage

select distinct chromosome, begin_pos, end_pos, allele1Seq, allele2Seq from gene.variant_quality limit 10000

select * from gene.variant_quality where allele1Seq is not null limit 10000

SELECT add_location ('/projects/stsi-aeon-nfs1/well1' , 'v_wellderly_node0002' , 'DATA', 'well_data');


select analyze_histogram('')

select * from configuration_parameters where parameter_name = 'LockTimeout';

select * from storage_location

select * from delete_vectors order by projection_name

select * from system

select purge()

select make_ahm_now()


select MERGE_PARTITIONS('gene.ref_coverage', 'HE00030', 'HE01248')


select purge_table('vs_output_deployment_status_node0001')

delete from gene.ref_coverage where patient_id = 'HE00018';

select * from v_monitor.partitions where partition_key like '%chr%'


alter table gene.ref_coverage drop partition (true

select drop_partition('gene.ref_coverage', 'HE00018', true, true)

drop projection gene.ref_coverage_DBD_1_seg_well_perf4_b0

select * from projections where anchor_table_name  = 'ref_coverage'

SELECT MEASURE_LOCATION_PERFORMANCE('/projects/stsi_db/wellderly/v_wellderly_node0001_data','v_wellderly_node0001')

select * from locks;

select * from columns where table_name like '%l%'

select * from disk_storage

select * from transactions limit 1000

select * from sessions

select * from projections where anchor_table_name = 'ref_coverage'

select analyze_statistics('gene.ref_coverage');

4766, -117.10465290714286, 32.83234665928571
4766, -117.0978992, 32.84014168

2566,2564,"2216 S EL CAMINO REAL, STE 108-109, OCEANSIDE, CA  92054-6370","KIM, MICHAEL",0.0911118271027494,-117.2303629,32.97766773

select analyze_statistics('gene.ref_coverage')
select analyze_statistics('gene.ref_coverage_DBD_3_seg_well_perf7_b0')

select dv.projection_name,deleted_row_count*100/row_count as PctDeleted from delete_vectors dv,projection_storage ps 
where ps.projection_schema = dv.schema_name and ps.projection_name = dv.projection_name order by projection_name;

select * from delete_vectors

select start_refresh()

select min(allele1VarScoreEAF) from gene.variant_quality 

select * from projection_refreshes where is_executing is true

select * from projection_refreshes where anchor_table_name = 'ref_coverage'

select * from sessions where session_id = 'stsi1.sdsc.edu-22554:0x4e'

select * from transactions where transaction_id in (select transaction_id from locks)

select rc.patient_id, rc.chromosome, begin_pos, end_pos, reference, allele1Seq, allele2Seq,  
							referenceAlleleReadCount, allele1ReadCount, allele2ReadCount, totalReadCount, allele1Gene,  
							allele2Gene, refScore, gcCorrectedCoverage, segDupOverlap, relativeCoverageDiploid,   
							relativeCoverageNondiploid    
							from gene.ref_coverage rc, gene.variant_quality vq  
							where rc.patient_id = vq.patient_id  
							and rc.chromosome = vq.chromosome  
							and rc.offset_cov = vq.begin_pos  
							and rc.chromosome = 'chr1' 
							and offset_cov >= 14000000 and offset_cov <= 14000100 
							limit 20 offset 0

select distinct chromosome, begin_pos, end_pos, allele1Gene from gene.variant_quality limit 20


select count(*) from gene.variant_quality

select
  node_name,
  operator_name ,counter_name,sum(counter_value) from
  execution_engine_profiles where
  (
    session_id='stsi1.sdsc.edu-547:0xd4cc'
  ) and (
    (  operator_name='DataTarget'
      and counter_name in ('input rows','written rows')
    ) or (
      operator_name='Scan'
      and counter_name='rows produced'
    )
  )
group by
  node_name
,operator_name
,counter_name
order by node_name,counter_name;

select count(*) from gene.ref_coverage

select schema_name, projection_name, count(*) from delete_vectors group
by schema_name, projection_name

select do_tm_task('mergeout', 'v_dbd_well_perf7.vs_output_deployment_status_node0002');

select * from gene.variant_quality limit 50

select distinct allele1Gene, allele2Gene from gene.variant_quality where allele1Gene is not null
and allele2Gene is not null
limit 50

select * from projections where is_up_to_date is false

select * from epochs

select chromosome, begin_pos, reference, allele1Seq, allele2Seq, allele1ReadCount, allele2ReadCount,  referenceAlleleReadCount, totalReadCount 
from gene.variant_quality where reference != '=' order by chromosome, begin_pos limit 1000


drop projection gene.ref_coverage_DBD_5_seg_well_perf7_b0

select chromosome, begin_pos, count(reference) as referenceCount
from gene.variant_quality where reference != '=' group by chromosome, begin_pos order by chromosome, begin_pos limit 1000

select * from gene.variant_quality where chromosome 

CREATE TABLE gene.variant_quality  ( 
	patient_id                	varchar(80) NOT NULL DEFAULT 'NA',
	locus                     	int,
	ploidy                    	varchar(80),
	chromosome                	varchar(80) NOT NULL,
	begin_pos                 	int NOT NULL,
	end_pos                   	int NOT NULL,
	zygosity                  	varchar(80),
	varType                   	varchar(80),
	reference                 	varchar(80) NOT NULL,
	allele1Seq                	varchar(80),
	allele2Seq                	varchar(80),
	allele1VarScoreVAF        	int,
	allele2VarScoreVAF        	int,
	allele1VarScoreEAF        	int,
	allele2VarScoreEAF        	int,
	allele1VarQuality         	varchar(80),
	allele2VarQuality         	varchar(80),
	allele1HapLink            	varchar(80),
	allele2HapLink            	varchar(80),
	allele1XRef               	varchar(80),
	allele2XRef               	varchar(80),
	evidenceIntervalId        	varchar(80),
	allele1ReadCount          	int,
	allele2ReadCount          	int,
	referenceAlleleReadCount  	varchar(80),
	totalReadCount            	int,
	allele1Gene               	varchar(80),
	allele2Gene               	varchar(80),
	pfam                      	varchar(80),
	miRBaseId                 	varchar(80),
	repeatMasker              	varchar(80),
	segDupOverlap             	varchar(80),
	relativeCoverageDiploid   	varchar(80),
	calledPloidy              	varchar(80),
	relativeCoverageNondiploid	varchar(80),
	calledLevel               	varchar(80),
	load_date                 	date,
	load_process              	varchar(80),
	PRIMARY KEY(begin_pos,chromosome,end_pos,patient_id,reference)
)
PARTITION BY variant_quality.patient_id
GO
GRANT SELECT, INSERT, UPDATE, DELETE, REFERENCES ON TABLE gene.variant_quality TO dbadmin WITH GRANT OPTION
GO
GRANT SELECT, REFERENCES ON TABLE gene.variant_quality TO stsi
GO
 as variant_quality_pk

select count(distinct patient_id) from gene.variant_quality

select did, subject_id from gene.did_subject_xref
minus 
select did, subject_id from gene.did_subject_xref where subject_id in (select distinct patient_id from gene.variant_quality)
order by did


select d.chrom, d.pos, d.ref, n.reference
from gene.dbsnp_vcf d, gene.variant_quality n
where d.chrom = n.chromosome
and d.pos - 1 = n.begin_pos
and n.reference != '='
order by chrom
limit 1000
GO

select count(*) from gene.variant_quality where patient_id ='NA'
select count(*) from gene.illumina_vcf

select count(*), patient_id from gene.variant_quality 
group by patient_id
order by 2 desc

delete from gene.variant_quality where patient_id = 'None'

insert into gene.variant_gene_notation_ref( select patient_id, chromosome, begin_pos, end_pos, 
split_part(split_part(allele1Gene, ';', 2), ':', 1) as ncbi_gene_id, 
split_part(split_part(allele1Gene, ';', 2), ':', 2) as refseq_id, 
split_part(split_part(allele1Gene, ';', 2), ':', 3) as hugo_gene_symbol, 
split_part(split_part(allele1Gene, ';', 2), ':', 4) as splce_site_type, 
split_part(split_part(allele1Gene, ';', 2), ':', 5) as mutation_site from gene.variant_quality where split_part(split_part(allele1Gene, ';', 2), ':', 1) != '' and patient_id = 'HE00014'



select distinct subject_id, chrom, pos, ref, alt, count(*) from staging.illumina_vcfa
group by subject_id, chrom, pos, ref, alt
having count(*) > 1
limit 10
GO

select * from locks

select * from projections where has_statistics is false


drop table gene.illumina_vcf2


select make_ahm_now()

select * from transactions where description like '%gene.illumina_vcf1%'


select set_config_parameter('MaxMrgOutROSSizeMB', 1024);
GO

select set_config_parameter('PurgeMergeoutPercent',1);
GO

select set_config_parameter('MaxMrgOutROSSizeMB', -1);
GO

select set_config_parameter('PurgeMergeoutPercent',20);
GO


select * from gene.variant_quality where reference != to_char('=') and varType = 'del' limit 1000

select *
from gene.variant_quality where (end_pos - begin_pos) = 30000000
where reference != '='
limit 1000

select count(*) from gene.variant_quality where reference != '='
and (end_pos - begin_pos) > 80

select count(*) from gene.variant_quality where (end_pos - begin_pos) > 80

// 14232663108 

//3587350763 

select 391129 / 14232663108

select count(*) from gene.illumina_vcf where vartype = 'subs' limit 1000


select * from sessions

select * from locks;
select * from projections where is_up_to_date is false

select make_ahm_now()

select dv.projection_name,deleted_row_count*100/row_count as PctDeleted from delete_vectors dv,projection_storage ps 
where ps.projection_schema = dv.schema_name and ps.projection_name = dv.projection_name order by projection_name;

drop projection staging.illumina_vcfa_DBD_5_seg_illumina_vcf_b0

select purge()
