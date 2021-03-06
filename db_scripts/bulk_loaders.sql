//Bulk loading scripts

COPY staging.sg_advisor
(
haplotype,
  chromosome,
  begin_pos,
  end_pos,
  vartype,
  reference,
  allele,
  notes,
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
 acmg_score_research_disease_entry_explanation 
) from local '/tmp/SUMMARY_masterVarBeta-GS000014945-ASM_novel_annotation.txt' delimiter E'\t' rejected data '/tmp/rejected_data' exceptions '/tmp/exceptions' trailing nullcols direct;


head -n-13 var-GS000015115-ASM.tsv > var-15.tsv

COPY STAGING.VARIANT_QUALITY(LOCUS,
PLOIDY,
ALLELE,
CHROMOSOME,
BEGIN_POS,
END_POS,
VARTYPE,
REFERENCE,
ALLELESEQ,
VARSCOREVAF,
VARSCOREEAF,
VARQUALITY,
HAPLINK,
XREF ) 
FROM
	'/tmp/var-15.tsv' DELIMITER E'\t' REJECTED DATA '/tmp/rejected_data' 
	EXCEPTIONS '/tmp/exceptions' TRAILING NULLCOLS DIRECT;
	
copy staging.demographics from '/tmp/DATA_WHWELLDERLY_2013-09-24.CSV' delimiter ',' enclosed by '"' record terminator E'\r' rejected data '/tmp/rejected_data' exceptions '/tmp/exceptions' trailing nullcols direct;

insert into gene.dbnsfp_variants(chrom, pos, ref, alt, aaref, aaalt, sift_score, 
sift_converted_rankscore, sift_pred, polyphen_hdiv_score, polyphen_hdiv_rankscore, polyphen_hdiv_pred,
polyphen_hvar_score, polyphen_hvar_rankscore, polyphen_hvar_pred,
gerp_nr, gerp_rs, gerp_rankscore, date_loaded)
select chrom, cast(pos as numeric), ref, alt, aaref, aaalt, sift_score /*cast(decode(sift_score, '.', null, sift_score) as numeric)*/, 
cast(decode(sift_converted_rankscore, '.', null, sift_converted_rankscore) as numeric), sift_pred, 
polyphen2_hdiv_score, 
cast(decode(polyphen2_hdiv_rankscore, '.', null, Polyphen2_HDIV_rankscore) as numeric), 
polyphen2_hdiv_pred,
polyphen2_hvar_score, cast(decode(polyphen2_hvar_rankscore, '.', null, polyphen2_hvar_rankscore)  as numeric), polyphen2_hvar_pred,
cast(decode(gerp_nr, '.', null, gerp_nr) as numeric), cast(decode(gerp_rs, '.', null, gerp_rs) as numeric), 
cast(decode(gerp_rs_rankscore, '.', null, gerp_rs_rankscore) as numeric), now() from staging.dbnsfp_variants

copy gene.dbsnp_vcf from 'mod_all.vcf' delimiter E'\t' enclosed by '"' record terminator E'\n' rejected 
data '/tmp/rejected_data' exceptions '/tmp/exceptions' trailing nullcols direct;

nohup python2.7 -c 'import clinvar_upload; clinvar_upload.main();' &



