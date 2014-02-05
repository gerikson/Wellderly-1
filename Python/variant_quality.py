from well_conn import well_conn

class variants_qual_report(object):
    
    def get_records(self, af, quality): 
        conn_obj = well_conn()
        conn = conn_obj.db_conn()
        cur = conn.cursor()
        sql = "select patient_id, go_id, gene, chromosome, allele, reference, begin_pos, end_pos, variant_quality, genomes_af, wellderly_af325 from gene.sg_advisor where genomes_af <=" + str(af) + " and variant_quality = '" + quality + "' order by chromosome, begin_pos, end_pos limit 100"
        cur.execute(sql)
        print
        page_content = cur.fetchall()
        return page_content