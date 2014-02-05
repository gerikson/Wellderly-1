from well_conn import well_conn

class all_variants_report(object):
    
    def get_records(self, patient_id): 
        conn_obj = well_conn()
        conn = conn_obj.db_conn()
        cur = conn.cursor()
        sql = "select patient_id, go_id, gene, chromosome, allele, reference, begin_pos, end_pos, genomes_af, wellderly_af325 from gene.sg_advisor where patient_id ='" + patient_id + "' order by chromosome, begin_pos, end_pos limit 100"
        cur.execute(sql)
        page_content = cur.fetchall()
        return page_content
    