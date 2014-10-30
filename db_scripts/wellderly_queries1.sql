select c.patient_id, vq.chromosome, vq.begin_pos, vq.end_pos, c.offset_cov, vq.allele1seq, 
vq.allele2seq, vq.reference, uniqueSequenceCoverage, weightSumSequenceCoverage, 
gcCorrectedCoverage, grossWeightSumSequenceCoverage 
from gene.variant_quality vq, gene.ref_coverage c 
where vq.patient_id = c.patient_id and vq.chromosome = c.chromosome and vq.end_pos = c.offset_cov limit 50

 wget https://bootstrap.pypa.io/ez_setup.py -O - | sudo python2.7

