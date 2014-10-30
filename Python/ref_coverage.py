'''
Created on Apr 14, 2014

@author: bwest
'''

from datetime import date
from well_conn import well_odbc
from subprocess import call
import sys

class RefCoverageFile():
    
    def upload_file(self, my_file, patient_id):
        load_date = date.today()
        load_process = 'ETL Process'
        
        conn_obj = well_odbc()
        conn = conn_obj.db_conn()
        conn.timeout = 0
        cur = conn.cursor()
        copy_sql = "copy gene.ref_coverage (offset_cov,refScore, uniqueSequenceCoverage, " +\
                    "weightSumSequenceCoverage, gcCorrectedCoverage, grossWeightSumSequenceCoverage) " +\
                    " FROM '"+ my_file +"' BZIP DELIMITER E'\t' skip 9 rejected data '/tmp/rejected_data' EXCEPTIONS '/tmp/exceptions' trailing nullcols DIRECT;"
        
        chr = self.get_chr(my_file)
        
        update_sql = "update gene.ref_coverage set patient_id = '" + patient_id +"', chromosome = '" + chr + "', load_date = to_date('"+ str(load_date) +"', 'dd/mm/yyyy'), loaded_by ='" + load_process + "'"+ \
                        " where patient_id = 'NA'"
                        
        try:
            cur.execute(copy_sql)
            print("Processed data for: " + patient_id + " - " + chr)      
            cur.execute(update_sql)
            print("Processed update for: " + patient_id + " - " + chr)      
            conn.commit()
        except:
            conn.close()
            print "Unexpected error:", sys.exc_info()[0]
            raise
        conn.close()
        
    def get_chr(selfself, my_file):
        
        chr = my_file.split('-')
        
        return chr[4]