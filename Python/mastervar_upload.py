'''
Created on Apr 14, 2014

@author: bwest
'''

from datetime import date
from well_conn import well_conn
from subprocess import call
import sys

class MasterVarFile():
    
    def upload_file(self, my_file, patient_id):
        load_date = date.today()
        load_process = 'ETL Process'
        
        conn_obj = well_conn()
        conn = conn_obj.db_conn()
        cur = conn.cursor()
        copy_sql = "COPY gene.VARIANT_QUALITY(LOCUS,PLOIDY,CHROMOSOME,BEGIN_POS,END_POS," + \
                    "ZYGOSITY,VARTYPE,REFERENCE,allele1Seq,allele2Seq,allele1VarScoreVAF," + \
                    "allele2VarScoreVAF,allele1VarScoreEAF,allele2VarScoreEAF,allele1VarQuality," + \
                    "allele2VarQuality,allele1HapLink,allele2HapLink,allele1XRef,allele2XRef," +\
                    "evidenceIntervalId,allele1ReadCount, allele2ReadCount,referenceAlleleReadCount," +\
                    "totalReadCount,allele1Gene,allele2Gene,pfam,miRBaseId,repeatMasker," +\
                    "segDupOverlap, relativeCoverageDiploid, calledPloidy,relativeCoverageNondiploid ,calledLevel" +\
                    ")" +\
                    " FROM '"+ my_file +"' BZIP DELIMITER E'\t' skip 20 rejected data '/tmp/rejected_data' EXCEPTIONS '/tmp/exceptions' trailing nullcols DIRECT;"
        
        update_sql = "update gene.variant_quality set patient_id = '" + patient_id +"', load_date = to_date('"+ str(load_date) +"', 'dd/mm/yyy'), load_process ='" + load_process + "'"+ \
                        " where patient_id = 'NA'"
       
        try:
            cur.execute(copy_sql)       
            cur.execute(update_sql)
            conn.commit()
        except:
            print "Unexpected error:", sys.exc_info()[0]
            raise
        
        
        