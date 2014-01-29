-- ETL for SG Advisor Data

drop table gene.sg_advisor

CREATE TABLE gene.sg_advisor (haplotype int, chromosome Varchar(25), begin_pos int, end_pos int, 
vartype Varchar(80), reference Varchar(80), allele Varchar(80), gene Varchar(80), gene_type Varchar(80), 
location Varchar(80), distance Varchar(80), coding_impact Varchar(80), protein_pos Varchar(80), 
original_aa Varchar(80), allele_aa Varchar(80), start_stop_dist Varchar(80), 
prop_cons_affected_upstream Varchar(80), prop_cons_affected_downstream Varchar(80), 
trunc_prediction Varchar(80), conserved46way Varchar(80), conserved46wayplacental Varchar(80), 
conserved46wayprimates Varchar(80), asw_minallele INT, ceu_minallele INT, chb_minallele INT, 
chd_minallele INT, gih_minallele INT, jpt_minallele INT, lwk_minallele INT, mex_minallele INT, 
mkk_minallele INT, tsi_minallele INT, yri_minallele INT, genomes_af float, wellderly_af325 float, nhlbi float, 
eqtl_genes Varchar(80), mirna_bs_influenced Varchar(80), mirna_bs_impact Varchar(80), 
mirna_bs_direct Varchar(80), mirna_bs_deltag Varchar(80), mirna_genomic Varchar(80), 
mirna_folding_deltag Varchar(80), mirna_binding_deltag Varchar(80), mirna_top_targets_changed Varchar(80), 
splice_site_pred Varchar(80), splicing_predictionmaxent Varchar(80), ese_sites Varchar(80), 
ess_sites Varchar(80), protein_impact_predictionpolyphen Varchar(80), protein_impact_probabilitypolyphen Varchar(80), 
protein_impact_predictionsift Varchar(80), protein_impact_scoresift Varchar(80), protein_domains Varchar(80), 
protein_domains_impactlogre Varchar(80), protein_impact_predictioncondel Varchar(80), 
protein_impact_probabilitycondel Varchar(80), tf_binding_sites Varchar(80), tfbs_deltas Varchar(80), 
omimgene_id_omimgene_association Varchar(80), protein_domain_gene_ontology Varchar(80), dbsnp_id Varchar(80), 
hgmd_variant_pubmedid Varchar(80), hgmd_gene_disease_association Varchar(80), 
genetic_association_database_pubmedid Varchar(80), pharmgkb_database_drug Varchar(80), 
inheritance_penetrance Varchar(80), severity_treatability Varchar(80), cosmic_variant_numsamples Varchar(80), 
cosmic_gene_numsamples Varchar(80), mskcc_cancergenes Varchar(80), atlas_oncology Varchar(80), 
sanger_cancergenes Varchar(80), sanger_germline_cancergenes Varchar(80), 
sanger_network_informed_cancergenes_pval Varchar(80), segdup_region Varchar(80), 
gene_symbol Varchar(80), drugbank Varchar(80), reactome_pathway Varchar(80), 
gene_onotology Varchar(80), disease_ontology Varchar(80), 
acmg_score_clinical_disease_entry_explanation Varchar(80), 
acmg_score_research_disease_entry_explanation Varchar(80), 
patient_id Varchar(80),
load_date	date not null default sysdate,
load_process	varchar(25)	);



insert /*+ direct */
into wellderly.gene.sg_advisor(
haplotype,
chromosome,
begin_pos,
end_pos,
vartype,
reference,
allele,
gene,
gene_type,
location,
distance,
coding_impact,
protein_pos,
original_aa,
allele_aa,
start_stop_dist,
prop_cons_affected_upstream,
prop_cons_affected_downstream,
trunc_prediction,
conserved46way,
conserved46wayplacental,
conserved46wayprimates,
asw_minallele,
ceu_minallele,
chb_minallele,
chd_minallele,
gih_minallele,
jpt_minallele,
lwk_minallele,
mex_minallele,
mkk_minallele,
tsi_minallele,
yri_minallele,
genomes_af,
wellderly_af325,
nhlbi,
eqtl_genes,
mirna_bs_influenced,
mirna_bs_impact,
mirna_bs_direct,
mirna_bs_deltag,
mirna_genomic,
mirna_folding_deltag,
mirna_binding_deltag,
mirna_top_targets_changed,
splice_site_pred,
splicing_predictionmaxent,
ese_sites,
ess_sites,
protein_impact_predictionpolyphen,
protein_impact_probabilitypolyphen,
protein_impact_predictionsift,
protein_impact_scoresift,
protein_domains,
protein_domains_impactlogre,
protein_impact_predictioncondel,
protein_impact_probabilitycondel,
tf_binding_sites,
tfbs_deltas,
omimgene_id_omimgene_association,
protein_domain_gene_ontology,
dbsnp_id,
hgmd_variant_pubmedid,
hgmd_gene_disease_association,
genetic_association_database_pubmedid,
pharmgkb_database_drug,
inheritance_penetrance,
severity_treatability,
cosmic_variant_numsamples,
cosmic_gene_numsamples,
mskcc_cancergenes,
atlas_oncology,
sanger_cancergenes,
sanger_germline_cancergenes,
sanger_network_informed_cancergenes_pval,
segdup_region,
gene_symbol,
drugbank,
reactome_pathway,
gene_onotology,
disease_ontology,
acmg_score_clinical_disease_entry_explanation,
acmg_score_research_disease_entry_explanation,
patient_id,
load_date,
load_process)

select 
case when haplotype = 'N/A'
    then null
    else cast(haplotype as numeric)
end,
chromosome,
case when begin_pos = 'N/A'
    then null
    else cast(begin_pos as numeric)
end,
case when end_pos = 'N/A'
    then null
    else cast(end_pos as numeric)
end,
vartype,
reference,
allele,
gene,
gene_type,
location,
distance,
coding_impact,
protein_pos,
original_aa,
allele_aa,
start_stop_dist,
prop_cons_affected_upstream,
prop_cons_affected_downstream,
trunc_prediction,
conserved46way,
conserved46wayplacental,
conserved46wayprimates,
case when asw_minallele = 'N/A' then null
     else cast(asw_minallele as numeric)
end,
case when ceu_minallele = 'N/A' then null
     else cast(ceu_minallele as numeric)
end,
case when chb_minallele = 'N/A' then null
     else cast(chb_minallele as numeric)
end,
case when chd_minallele = 'N/A' then null
     else cast(chd_minallele as numeric)
end,
case when gih_minallele = 'N/A' then null
     else cast(gih_minallele as numeric)
end,
case when jpt_minallele = 'N/A' then null
     else cast(jpt_minallele as numeric)
end,
case when lwk_minallele = 'N/A' then null
    else cast(lwk_minallele as numeric)
end,
case when mex_minallele = 'N/A' then null
    else cast(mex_minallele as numeric)
end,
case when mkk_minallele = 'N/A' then null
    else cast(mkk_minallele as numeric)
end,
case when tsi_minallele = 'N/A' then null
    else cast(tsi_minallele as numeric)
end,
case when yri_minallele = 'N/A' then null
     else cast(yri_minallele as numeric)
end,
case when genomes_af = 'N/A' then null
     else cast(genomes_af as numeric)
end,
case when wellderly_af325 = 'N/A' then null
     when wellderly_af325 = '-nan' then null
     else cast(wellderly_af325 as numeric)
end,
case when nhlbi = 'N/A' then null
     else cast(nhlbi as numeric)
end,
eqtl_genes,
mirna_bs_influenced,
mirna_bs_impact,
mirna_bs_direct,
mirna_bs_deltag,
mirna_genomic,
mirna_folding_deltag,
mirna_binding_deltag,
mirna_top_targets_changed,
splice_site_pred,
splicing_predictionmaxent,
ese_sites,
ess_sites,
protein_impact_predictionpolyphen,
protein_impact_probabilitypolyphen,
protein_impact_predictionsift,
protein_impact_scoresift,
protein_domains,
protein_domains_impactlogre,
protein_impact_predictioncondel,
protein_impact_probabilitycondel,
tf_binding_sites,
tfbs_deltas,
omimgene_id_omimgene_association,
protein_domain_gene_ontology,
dbsnp_id,
hgmd_variant_pubmedid,
hgmd_gene_disease_association,
genetic_association_database_pubmedid,
pharmgkb_database_drug,
inheritance_penetrance,
severity_treatability,
cosmic_variant_numsamples,
cosmic_gene_numsamples,
mskcc_cancergenes,
atlas_oncology,
sanger_cancergenes,
sanger_germline_cancergenes,
sanger_network_informed_cancergenes_pval,
segdup_region,
gene_symbol,
drugbank,
reactome_pathway,
gene_onotology,
disease_ontology,
acmg_score_clinical_disease_entry_explanation,
acmg_score_research_disease_entry_explanation,
patient_id,
sysdate,
'ETL Process'
from staging.sg_advisor;

