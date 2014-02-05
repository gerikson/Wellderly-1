from well_conn import well_conn

class meds(object):
    
    def get_meds(self): 
        conn_obj = well_conn()
        conn = conn_obj.db_conn()
        cur = conn.cursor()
        cur.execute("SELECT  'depr_meds___1', depr_meds___1,"+
                    "'depr_meds___2',depr_meds___2,'depr_meds___3',depr_meds___3,'depr_meds___4',depr_meds___4,"+
                    "'osteopor_meds___1',osteopor_meds___1,'osteopor_meds___2',osteopor_meds___2,'osteopor_meds___3',"+
                    "osteopor_meds___3,'hrt_meds___1',hrt_meds___1,"+
                    "'hrt_meds___2',hrt_meds___2,'hrt_meds___3',hrt_meds___3,'hrt_meds___4',hrt_meds___4,"+
                    "'hrt_meds___5',hrt_meds___5,'gerd_meds___1',gerd_meds___1,"+
                    "'gerd_meds___2',gerd_meds___2,'gerd_meds___3',gerd_meds___3,'gerd_meds___4',gerd_meds___4,"+
                    "'bph_meds___1P',bph_meds___1,'bph_meds___2',bph_meds___2,"+
                    "'bph_meds___3',bph_meds___3,'bph_meds___4',bph_meds___4,'osteoarth_meds___1',osteoarth_meds___1,"+
                    "'osteoarth_meds___2',osteoarth_meds___2,"+
                    "'osteoarth_meds___3',osteoarth_meds___3,'osteoarth_meds___4',osteoarth_meds___4,'hypothy_meds___1',"+
                    "hypothy_meds___1,'hypothy_meds___2',hypothy_meds___2,'hypothy_meds___3',hypothy_meds___3,'hyperten_meds___1',"+
                    "hyperten_meds___1,'hyperten_meds___2',hyperten_meds___2,'hyperten_meds___3',hyperten_meds___3,'hyperten_meds___4',"+
                    "hyperten_meds___4,'hyperten_meds___5',hyperten_meds___5,'hyperten_meds___6',hyperten_meds___6,"+
                    "'hyperten_meds___7',hyperten_meds___7,"+
                    "'hyperten_meds___8',hyperten_meds___8,'dyslipid_meds___1',dyslipid_meds___1,'dyslipid_meds___2',"+
                    "dyslipid_meds___2,'dyslipid_meds___3',dyslipid_meds___3,'dyslipid_meds___4',dyslipid_meds___4,"+
                    "'dyslipid_meds___5',dyslipid_meds___5,"+
                    "'blad_con_meds___1',blad_con_meds___1,'blad_con_meds___2',blad_con_meds___2,"+
                    "'blad_con_meds___3',blad_con_meds___3,"+
                    "'copd_ast_meds___1',copd_ast_meds___1,'copd_ast_meds___2',copd_ast_meds___2,'copd_ast_meds___3',"+
                    "copd_ast_meds___3,'copd_ast_meds___4',copd_ast_meds___4,'glaucoma_meds___1',glaucoma_meds___1,"+
                    "'glaucoma_meds___2',glaucoma_meds___2,"+
                    "'glaucoma_meds___3',glaucoma_meds___3,'glaucoma_meds___4',glaucoma_meds___4,'mac_degen_meds___1',"+
                    "mac_degen_meds___1,'mac_degen_meds___2',mac_degen_meds___2,'mac_degen_meds___3',mac_degen_meds___3,"+
                    "'mac_degen_meds___4',mac_degen_meds___4"+
                    " from staging.demographics")
        
        return cur
    
    def insert_meds(self, sql):
        conn_obj = well_conn()
        conn = conn_obj.db_conn()
        cur = conn.cursor()
        cur.execute(sql)
        conn.commit()
        conn.close()
        
    def tranform_meds(self, result, patient_id):
        
        sql = ''
        for record in result.fetchall():
            dict_result = dict(record[i:i+2] for i in range(0, len(record), 2))
            for key in dict_result: 
                if int(dict_result[key]) == 1:
                    query_field = key
                    sql = "insert into gene.subject_meds (patient_id, med_type, load_date, load_process) " + "values('" + patient_id + "', '" + query_field + "', sysdate, 'ETL Process')"
                    meds.insert_meds(self, sql)
            
meds_obj = meds()
result = meds_obj.get_meds()        
trans_data = meds_obj.tranform_meds(result, 'NA12877')   
                    