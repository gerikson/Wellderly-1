from vertica_python import connect
import pyodbc
import sys


class well_conn(object):
    
    def db_conn(self):
       
        connection = connect({
                              'host': '198.202.90.32',
                              'port': 5433,
                              'user': 'dbadmin',
                              'password': '3Nathan$',
                              'database': 'wellderly'

                              })
        
        return connection
    
class well_odbc(object):
    
    def db_conn(self):
        
        try:
            connection = pyodbc.connect('DRIVER={HPVertica};SERVER=localhost;DATABASE=wellderly;UID=dbadmin;PWD=3Nathan$;PORT=5433')
        except:
            print("Unexpected error:", sys.exc_info()[0])
            raise
        
        return connection

    


    
