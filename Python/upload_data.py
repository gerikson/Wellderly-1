'''
Created on Apr 14, 2014

@author: bwest
'''

import os
from subprocess import call
import re
from mastervar_upload import MasterVarFile
from depth_of_coverage import DepthOfCoverageFile
from ref_coverage import RefCoverageFile
from diploid_beta_details import DetailsDiploidBetaFile
from nondiploid_beta_detail import DetailsNonDiploidBetaFile
from segments_diploid_beta import SegmentsDiploidBetaFile
from segments_nondiploid_beta import SegmentsNonDiploidBetaFile
from well_conn import well_conn
import sys

class WellFiles():
    
     def __init__(self, my_dir):    
         self.my_dir = my_dir
         my_files = self.getFiles(my_dir)
        
     def getFiles(self,my_dir):
       
         my_files = []
         try:
             for root, dir, files in os.walk(my_dir):
                 if re.search('-DID', str(dir)):
                     dir_name = str(dir)
                     dids = str(dir_name).strip('[]').split(', ')
                     for did in dids: 
                           patient_id = self.get_patient(did).strip('\'')
                           dir_path = root + '/' + did.strip('\'')
                           self.process_files(dir_path, patient_id)
                                                  
         except:
            print "Unexpected error:", sys.exc_info()[0]
            raise
                
         return my_files
     
     def get_patient(self, gs_id):
         
         conn_obj = well_conn()
         conn = conn_obj.db_conn()
         cur = conn.cursor()
         did_list = gs_id.split(',')
         sql = "select subject_id from gene.did_subject_xref where did = " + gs_id
         cur.execute(sql)
         subject_id = cur.fetchone()
        
         return str(subject_id).strip('[u]')
     
     def process_files(self, dir, patient_id):
         
        for root, dir, files in os.walk(dir):
            for file in files:
                if (re.match('masterVarBeta', file)):
                    mastervar_file = MasterVarFile()
                    path_file = root + '/' + file
                    mastervar_file.upload_file(path_file, patient_id) 
                elif (re.match('coverageRefScore', file)):
                    ref_file = RefCoverageFile()
                    path_file = root + '/' + file
                    ref_file.upload_file(path_file, patient_id)
                elif (re.match('depthOfCoverage', file)):
                    doc_file = DepthOfCoverageFile()
                    path_file = root + '/' + file
                    doc_file.upload_file(path_file, patient_id)
                elif (re.match('cnvDetailsDiploidBeta', file)):
                    ddb_file = DetailsDiploidBetaFile()
                    path_file = root + '/' + file
                    ddb_file.upload_file(path_file, patient_id)
                elif (re.match('cnvDetailsNondiploidBeta', file)):
                    nddb_file = DetailsNonDiploidBetaFile()
                    path_file = root + '/' + file
                    nddb_file.upload_file(path_file, patient_id)
                elif (re.match('cnvSegmentsDiploidBeta', file)):
                    dbs_file = SegmentsDiploidBetaFile()
                    path_file = root + '/' + file
                    dbs_file.upload_file(path_file, patient_id)
                elif (re.match('cnvSegmentsNondiploidBeta', file)):
                    ndbs_file = SegmentsNonDiploidBetaFile()
                    path_file = root + '/' + file
                    ndbs_file.upload_file(path_file, patient_id)
                    
my_dir = '/localdisk/batch03'     
        
my_uploads = WellFiles(my_dir)

