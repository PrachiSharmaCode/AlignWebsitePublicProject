# AlignWebsiteProject - Public



## Database Access

The database connection in this project is using Hibernate query language using
C3P0 as the connection pool. The connection has been protected with TLS layer. To
access the connection settings, you can look at the resources sections and look at
the cfg.xml file. There are currently 2 cfg xml in the public side to access the 
test databases and original databases for the public databases. The Username for
the database access for public is "backend_public" and the password is "password".

## Database UML Diagram

For the higher level for the public schema, you can look at the ALIGN diagram pdf files 
in this repo. For more details of the Database construction, you can look inside the
sql_scripts folder for the SQL Scripts to create the Databases.