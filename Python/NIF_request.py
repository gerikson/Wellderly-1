import urllib2
import xml.etree.ElementTree as ET
import re
from well_conn import well_conn

class nif_report(object):

    def get_nif_recs(self, ont_key):

        clean_list = ''
        # Send the GET request
        url = 'http://nif-services.neuinfo.org/ontoquest/rel/subclasses/term/' + ont_key + '?level=1'      
        req = urllib2.Request(url, None)

        # Read the response
        resp = urllib2.urlopen(req).read()
        root = ET.fromstring(resp)

   
        #drill down the xml Tree to get the GO id values from reponse xml
        for child in root:
                for next_child1 in child:
                    for next_child2 in next_child1:
                        for next_child3 in next_child2:
                            for next_child4 in next_child3:
                                if next_child4.tag == 'subject':
                                    go_list = self.mod_go_id(next_child4.attrib)
                                    clean_list = clean_list + str(re.findall(r'GO:\d+', str(go_list)))

        page_content = self.get_records(clean_list)
        return page_content
    
    def mod_go_id(self, go_id):
        return re.sub('_', ':', str(go_id))
    
    def get_records(self, go_list): 
        conn_obj = well_conn()
        conn = conn_obj.db_conn()
        cur = conn.cursor()
        go_list1 = re.sub('\[', '', str(go_list))
        go_list2 = re.sub('\]', '', str(go_list1))
        go_list3 = re.sub("''",  "','", go_list2)
        sql = "select patient_id, go_id, gene, chromosome, allele, reference, begin_pos, end_pos, genomes_af, wellderly_af325 from gene.sg_advisor where go_id in(" + str(go_list3) + ")"
        cur.execute(sql)
        page_content = cur.fetchall()
        
        return page_content


#nif_report_obj = nif_report()
#my_list = nif_report_obj.get_nif_recs('aging')
#print(my_list)

