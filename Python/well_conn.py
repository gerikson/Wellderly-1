from vertica_python import connect

class well_conn(object):
    
    def db_conn(self):
       
        connection = connect({
                              'host': '198.202.90.181',
                              'port': 5433,
                              'user': 'dbadmin',
                              'password': '3Nathan$',
                              'database': 'wellderly'

                              })
        
        return connection
    


    