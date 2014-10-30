select * from gene.illumina_vcf where file like '%|4%' limit 1000


select * from (
select split_part(file, ':', 1) as a from gene.illumina_vcf) alias
where alias like "%3%"

select pos from gene.illumina_vcf where chrom = 'chr22'
intersect
select pos from gene.illumina_vcf where chrom = 'chr1'

select chrom, count(chrom) from staging.illumina_vcfa
group by chrom
