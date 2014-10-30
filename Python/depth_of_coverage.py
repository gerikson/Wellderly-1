'''
Created on Apr 14, 2014

@author: bwest
'''

from datetime import date
from well_conn import well_odbc
from subprocess import call
import sys


class DepthOfCoverageFile():
    
    def upload_file(self, my_file, patient_id):
        load_date = date.today()
        load_process = 'ETL Process'
        
        conn_obj = well_odbc()
        conn = conn_obj.db_conn()
        conn.timeout = 0
        cur = conn.cursor()
        copy_sql = "copy gene.depth_of_coverage(chromosome, begin_pos, end_pos, uniqueSequenceCoverage, weightSumSequenceCoverage, " + \
                    " gcCorrectedCvg, avgNormalizedCoverage) from '" + my_file + "'" + \
                    " delimiter E'\t' skip 11 rejected data '/tmp/rejected_data' exceptions '/tmp/exceptions' trailing nullcols direct;"
                    
        update_sql = "update gene.depth_of_coverage set patient_id = '" + patient_id + "', load_date = to_date('" + str(load_date) + "', 'dd/mm/yyyy'), loaded_by ='" + load_process + "'" + \
                        " where patient_id is null"
                        
        try:
            cur.execute(copy_sql)
            print("Processed data for: " + patient_id)      
            cur.execute(update_sql)
            print("Processed update for: " + patient_id)      
            conn.commit()
        except:
            conn.close()
            print "Unexpected error:", sys.exc_info()[0]
            raise
        conn.close()
