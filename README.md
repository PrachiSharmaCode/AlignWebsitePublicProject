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

### Public facing has the following APIs:

![image](https://user-images.githubusercontent.com/31941282/39389283-c4f10232-4a3b-11e8-84a1-336bea41fccf.png)
![image](https://user-images.githubusercontent.com/31941282/39389314-fd85ba16-4a3b-11e8-94f6-c7ac7eb45caa.png)
![image](https://user-images.githubusercontent.com/31941282/39389138-add8047a-4a3a-11e8-878c-94f32a1176ae.png)
![image](https://user-images.githubusercontent.com/31941282/39389152-cbe8e9d4-4a3a-11e8-8654-fe295b4fced2.png)
![image](https://user-images.githubusercontent.com/31941282/39389173-ea8c7b58-4a3a-11e8-8e67-7192b0f50caa.png)
![image](https://user-images.githubusercontent.com/31941282/39389180-fc0b8fcc-4a3a-11e8-8c62-816521f3f456.png)
![image](https://user-images.githubusercontent.com/31941282/39389209-1bd44c68-4a3b-11e8-853a-1a7177913f59.png)
![image](https://user-images.githubusercontent.com/31941282/39389217-2e43f66e-4a3b-11e8-9155-6e769b8e8841.png)
![image](https://user-images.githubusercontent.com/31941282/39389228-471799ca-4a3b-11e8-814b-6c1ebe66288d.png)
![image](https://user-images.githubusercontent.com/31941282/39389243-6013b9ae-4a3b-11e8-9169-766fa97cdade.png)
![image](https://user-images.githubusercontent.com/31941282/39389251-72985a76-4a3b-11e8-95bd-c5ff80c65246.png)
