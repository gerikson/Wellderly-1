select distinct chrom, pos, ref, alt, split_part(file, ':', 1) as GT 
from gene.illumina_vcf

select * from gene.illumina_vcf where chrom = 'chr1' and pos = 151369248