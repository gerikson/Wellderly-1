//script to delete records which were bulk uploaded in error

\set pat_id 'HE00267'

delete from gene.ref_coverage where patient_id = 'HE00489';
delete from gene.variant_quality where patient_id = 'HE00489';
delete from gene.diploid_beta_detail where patient_id = 'HE00489';
delete from gene.nondiploid_beta_detail where patient_id = 'HE00489';
delete from gene.depth_of_coverage where patient_id = 'HE00489';
delete from gene.segments_diploid_beta where patient_id = 'HE00489';
delete from gene.segments_nondiploid_beta where patient_id = 'HE00489';

delete from gene.ref_coverage where patient_id = 'NA';
delete from gene.variant_quality where patient_id = 'NA';
delete from gene.diploid_beta_detail where patient_id = 'NA';
delete from gene.nondiploid_beta_detail where patient_id = 'NA';
delete from gene.depth_of_coverage where patient_id = 'NA';
delete from gene.segments_diploid_beta where patient_id = 'NA';
delete from gene.segments_nondiploid_beta where patient_id = 'NA';

select description from transactions limit 500


select * from gene.segments_nondiploid_beta where patient_id = 'HE00267'

commit;

select * from v_monitor.storage_policies;

select * from disk_storage

select set_object_storage_policy('wellderly', 'well_data');

select DO_TM_TASK('mergeout')

select * from nodes

SELECT drop_LOCATION('/projects/stsi-aeon-nfs1/wellderly', 'v_wellderly_node0002')

SELECT MEASURE_LOCATION_PERFORMANCE('/projects/stsi-aeon-nfs1/well1/','v_wellderly_node0001')

update configuration_parameters set current_value = 2000 where parameter_name = 'LockTimeout';

select * from sessions
select close_session('stsi1.sdsc.edu-5928:0x7b0b')

select distinct patient_id from gene.ref_coverage

select analyze_histogram('')

select * from delete_vectors

select user_id, user_name, description from transactions limit 1000

select * from disk_storage


insert into gene.variant_gene_notation_ref(
select patient_id, chromosome, begin_pos, end_pos, 
split_part(split_part(allele1Gene, ';', 1), ':', 1) as ncbi_gene_id, 
split_part(split_part(allele1Gene, ';', 1), ':', 2) as refseq_id,
split_part(split_part(allele1Gene, ';', 1), ':', 3) as hugo_gene_symbol,
split_part(split_part(allele1Gene, ';', 1), ':', 4) as splce_site_type,
split_part(split_part(allele1Gene, ';', 1), ':', 5) as mutation_site
from gene.variant_quality where allele1Gene is not null)


insert into gene.variant_gene_notation_ref(
select patient_id, chromosome, begin_pos, end_pos,
split_part(split_part(allele1Gene, ';', 2), ':', 1) as ncbi_gene_id, 
split_part(split_part(allele1Gene, ';', 2), ':', 2) as refseq_id,
split_part(split_part(allele1Gene, ';', 2), ':', 3) as hugo_gene_symbol,
split_part(split_part(allele1Gene, ';', 2), ':', 4) as splce_site_type,
split_part(split_part(allele1Gene, ';', 2), ':', 5) as mutation_site
from gene.variant_quality
where split_part(split_part(allele1Gene, ';', 2), ':', 1) != '') 


insert into gene.variant_gene_notation_ref(
select patient_id, chromosome, begin_pos, end_pos,
split_part(split_part(allele1Gene, ';', 3), ':', 1) as ncbi_gene_id, 
split_part(split_part(allele1Gene, ';', 3), ':', 2) as refseq_id,
split_part(split_part(allele1Gene, ';', 3), ':', 3) as hugo_gene_symbol,
split_part(split_part(allele1Gene, ';', 3), ':', 4) as splce_site_type,
split_part(split_part(allele1Gene, ';', 3), ':', 5) as mutation_site
from gene.variant_quality
where split_part(split_part(allele1Gene, ';', 3), ':', 1) != '')


select allele1Gene, allele2Gene from gene.variant_quality where 
patient_id = 'HE01248'
and chromosome = 'chr17'
and begin_pos = 970560

create table gene.variant_gene_notation_ref(
patient_id varchar(15),
chromosome  varchar(10),
begin_pos integer,
end_pos integer,
ncbi_gene_id varchar(80),
refseq_id varchar(80),
hugo_gene_symbol    varchar(80),
splice_site_type varchar(80),
mutation_site varchar(80))


select * from gene.variant_quality where allele1Seq != '=' and allele1Seq != '?' limit 500

select * from gene.segments_nondiploid_beta limit 5000


select * from gene.variant_gene_notation_ref limit 500

select distinct(hugo_gene_symbol) from gene.variant_gene_notation_ref order by hugo_gene_symbol


select * from gene.variant_quality vq, gene.variant_gene_notation_ref vg
where hugo_gene_symbol = 'ETS2'
and vq.patient_id = vg.patient_id
and vq.chromosome = vg.chromosome
and vq.begin_pos = vg.begin_pos
and vq.end_pos = vg.end_pos


select patient_id, count(distinct chromosome) from gene.ref_coverage 
group by patient_id order by 1

select count(*) from gene.dbsnp_vcf
truncate table gene.dbsnp_vcf

select * from staging.dbnsfp_variants limit 1000

select distinct vq.chromosome|| ':' || begin_pos as variant_position, id, vq.reference|| '>' || allele1Seq as Allele, pos, end_pos, allele1Seq, allele2Seq, refa, alt
from gene.dbsnp_vcf dv, gene.variant_quality vq 
where dv.chromosome = vq.chromosome
and dv.pos = vq.begin_pos
and dv.refa = vq.reference
and dv.pos between 1 and 1172819
and vq.chromosome = 'chr1'
limit 500

select chromosome, begin_pos, max(avgNormalizedCoverage) from gene.depth_of_coverage dc
where chromosome = 'chr1' and begin_pos between 1 and 117173819
group by chromosome, begin_pos
order by begin_pos

insert into gene.dbsnp_vcf (chromosome, pos, id, refa, alt, KGPhase1, RSPOS, ASP, SAO, dbSNPBuildID, SSR, VP, WGT, VC, GMAF, KGPROD) values( 'chr1', '5480616', 'rs142218088', 'C', '[T]', 'True', '5480616', 'True', '['0']', '['134']', '['0']', '['050000000005000014000100']', '['1']', '['SNV']', '['0.0005']', 'True')
select * from gene.variant_quality limit 100
select * from gene.dbsnp_vcf limit 50

insert into gene.dbsnp_vcf (chromosome, pos, id, refa, alt, 'KGPhase1', 'RSPOS', 'ASP', 'SAO', 'dbSNPBuildID', 'SSR', 'VP', 'WGT', 'VC', 'GMAF', 'KGPROD') values( 'chr1', '5480616', 'rs142218088', 'C', '[T]', True , '5480616' , True , '0' , '134' , '0' , '050000000005000014000100' , '1' , 'SNV' , '0.0005' , True )




