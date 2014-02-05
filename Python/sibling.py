from well_conn import well_conn

class sibling(object):
    
    def get_sibling(self):
        conn_obj = well_conn()
        conn = conn_obj.db_conn()
        cur = conn.cursor()
        cur.execute("SELECT patient_id, sib1_gender, sib1_age_alive, sib1_age_death, sib1_cause_death," + 
                    "sib2_gender, sib2_age_alive, sib2_age_death, sib2_cause_death, " +
                    "sib3_gender, sib3_age_alive, sib3_age_death, sib3_cause_death, " + 
                    "sib4_gender, sib4_age_alive, sib4_age_death, sib4_cause_death, " +
                    "sib5_gender, sib5_age_alive, sib5_age_death, sib5_cause_death, " + 
                    "sib6_gender, sib1_age_alive, sib1_age_death, sib6_cause_death, " +
                    "sib7_gender, sib1_age_alive, sib7_age_death, sib7_cause_death, " + 
                    "sib8_gender, sib8_age_alive, sib8_age_death, sib8_cause_death " +
                    " FROM staging.demographics")
        return cur

    def insert_sibling(self, sql):
        conn_obj = well_conn()
        conn = conn_obj.db_conn()
        cur = conn.cursor()
        cur.execute(sql)
        conn.commit()
        conn.close()
    
    def tranform_sibling(self, result):
        
        patient_id = ''
        sibling_ordinal = 0
        sibling_gender = 0
        sibling_age_alive = 0
        sibling_age_death = 0
        sibling_cause_death = 0
        sql = ''
        for record in result.fetchall(): 
        
            patient_id = record[0]
            for element in range(1, len(record), 4):
                if record[element] != 'None':
                    if element == 1:
                        sibling_ordinal = '1'
                        sibling_gender = str(record[element]) if record[element] != None else 'null'
                        sibling_age_alive = str(record[element+ 1]) if record[element+ 1] != None else 'null'
                        sibling_age_death = str(record[element+2]) if record[element+2] != None else 'null'
                        sibling_cause_death = str(record[element+3]) if record[element+3] != None else 'null'
                        if element != 1 and ((element - 1 )%4) == 0:
                            sibling_ordinal = str(int(sibling_ordinal) + 1)
                            print(element,  sibling_ordinal)
                            sibling_gender = str(record[element]) if record[element] != None else 'null'
                            sibling_age_alive = str(record[element+ 1]) if record[element+ 1] != None else 'null'
                            sibling_age_death = str(record[element+2]) if record[element+2] != None else 'null'
                            sibling_cause_death = str(record[element+3]) if record[element+3] != None else 'null'
                        sql = sql + "Insert into gene.subject_siblings (patient_id, sib_ordinal, sib_gender, sib_age_alive, sib_age_death, sib_cause_death, load_date, load_process) values( '"+ str(patient_id) + "', " + sibling_ordinal + ", " + sibling_gender + ", " + sibling_age_alive + ", " + sibling_age_death + ", " + sibling_cause_death + ", sysdate, 'ETL Process')" +"\n"

            sibling.insert_sibling(self, sql)
            print(sql)
                    





