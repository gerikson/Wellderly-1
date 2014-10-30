select rc.patient_id, rc.chromosome, begin_pos, end_pos, reference, allele1Seq, allele2Seq,
referenceAlleleReadCount, allele1ReadCount, allele2ReadCount, totalReadCount, allele1Gene, 
allele2Gene, refScore, gcCorrectedCoverage, segDupOverlap, relativeCoverageDiploid, 
relativeCoverageNondiploid  
from gene.ref_coverage rc, gene.variant_quality vq
where rc.patient_id = vq.patient_id
and rc.chromosome = vq.chromosome
and rc.offset_cov = vq.begin_pos
and rc.chromosome = 'chr17'
and offset_cov >= 41196352 and offset_cov <= 41277500
limit 20 offset 0


select * from locks;