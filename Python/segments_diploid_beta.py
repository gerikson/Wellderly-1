'''
Created on Apr 14, 2014

@author: bwest
'''

from datetime import date
from well_conn import well_odbc
from subprocess import call
import sys


class SegmentsDiploidBetaFile():
    
    def upload_file(self, my_file, patient_id):
        load_date = date.today()
        load_process = 'ETL Process'
        
        conn_obj = well_odbc()
        conn = conn_obj.db_conn()
        conn.timeout = 0
        cur = conn.cursor()
        copy_sql = "copy gene.segments_diploid_beta(chr,begin_pos,end_pos,avgNormalizedCvg, " +\
                    "relativeCvg,calledPloidy, calledCNVType, ploidyScore, CNVTypeScore, " +\
                    "overlappingGene, repeats) from '" + my_file + "' " +\
                    "delimiter E'\t' record terminator E'\n' skip 14 rejected data '/tmp/rejected_data' exceptions '/tmp/exceptions' trailing nullcols direct;"
        
        update_sql = "update gene.segments_diploid_beta set patient_id = '" + patient_id + "', load_date = to_date('" + str(load_date) + "', 'dd/mm/yyyy'), loaded_by ='" + load_process + "'" + \
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
