CREATE SCHEMA PREPROD; 

--------------------------------------------------------
--  DDL for SEQUENCES
--------------------------------------------------------
CREATE SEQUENCE PREPROD.PRIVILEGE_SEQ AS BIGINT START WITH 1;
CREATE SEQUENCE PREPROD.GROUP_SEQ AS BIGINT START WITH 1;
CREATE SEQUENCE PREPROD.LOG_MASTER_SEQ AS BIGINT START WITH 1;
CREATE SEQUENCE PREPROD.NUMBER_ASSIGNMENT_SEQ AS BIGINT START WITH 1;
CREATE SEQUENCE PREPROD.NUMBER_POOL_SEQ AS BIGINT START WITH 1;
CREATE SEQUENCE PREPROD.ROLE_SEQ AS BIGINT START WITH 1;
CREATE SEQUENCE PREPROD.USER_GRP_SEQ AS BIGINT START WITH 1;
CREATE SEQUENCE PREPROD.USER_SEQ AS BIGINT START WITH 1;

--------------------------------------------------------
--  DDL for Table APP_USERS
--------------------------------------------------------

  CREATE TABLE "PREPROD"."APP_USERS" 
   (	"ID" INTEGER, 
	"U_NAME" VARCHAR(32), 
	"U_PWD" VARCHAR(200), 
	"FIRSTNAME" VARCHAR(32), 
	"LASTNAME" VARCHAR(32), 
	"LDAPAUTHENTICATION" INTEGER, 
	"COMMENTS" VARCHAR(128), 
	"CREATEDBY" VARCHAR(32), 
	"MODIFIEDBY" VARCHAR(32), 
	"CREATEDDATE" TIMESTAMP (6), 
	"MODIFIEDDATE" TIMESTAMP (6), 
	"SALT" VARCHAR(200), 
	"EMAIL" VARCHAR(32), 
	"CONTACT_NO" VARCHAR(20), 
	"IS_ACC_DEFAULT_PSWD" INTEGER DEFAULT 1, 
	"IS_ACC_LOCKED" INTEGER DEFAULT 1, 
	"IS_ENABLED" INTEGER DEFAULT 1, 
	"IS_SERVICE_USER" INTEGER DEFAULT 0
   ) ;
--------------------------------------------------------
--  DDL for Table CLEANUP_LOG_MASTER
--------------------------------------------------------

  CREATE TABLE "PREPROD"."CLEANUP_LOG_MASTER" 
   (	"ID" INTEGER, 
	"MSISDN" VARCHAR(20), 
	"IMSI" VARCHAR(30), 
	"SIM_NO" VARCHAR(30), 
	"NMS_POOL" VARCHAR(20), 
	"NMS_PRICE" VARCHAR(20), 
	"ERP_LOC" VARCHAR(20), 
	"CS" VARCHAR(2000), 
	"ESM" VARCHAR(2000), 
	"BSCS" VARCHAR(2000), 
	"NMS" VARCHAR(2000), 
	"RAS" VARCHAR(2000), 
	"ERP" VARCHAR(2000), 
	"PCRF" VARCHAR(2000), 
	"HLR" VARCHAR(2000), 
	"SPR" VARCHAR(2000), 
	"EXEC_DATE" DATE, 
	"EXEC_USER" VARCHAR(20) DEFAULT 't'
   ) ;
--------------------------------------------------------
--  DDL for Table NC_APP_USER_GRP
--------------------------------------------------------

  CREATE TABLE "PREPROD"."NC_APP_USER_GRP" 
   (	"ID" INTEGER, 
	"GRP_NAME" VARCHAR(30), 
	"GRP_DESC" VARCHAR(60), 
	"CREATED_ON" DATE, 
	"CREATED_BY" INTEGER
   ) ;
--------------------------------------------------------
--  DDL for Table NC_GRP_MSISDN
--------------------------------------------------------

  CREATE TABLE "PREPROD"."NC_GRP_MSISDN" 
   (	"GRP_ID" INTEGER, 
	"NP_ID" INTEGER
   ) ;
--------------------------------------------------------
--  DDL for Table NC_USER_GRP
--------------------------------------------------------

  CREATE TABLE "PREPROD"."NC_USER_GRP" 
   (	"USER_ID" INTEGER, 
	"GRP_ID" INTEGER
   ) ;
--------------------------------------------------------
--  DDL for Table NUMBER_ASGNMT_HISTORY
--------------------------------------------------------

  CREATE TABLE "PREPROD"."NUMBER_ASGNMT_HISTORY" 
   (	"ID" INTEGER, 
	"DESCRIPTION" VARCHAR(100), 
	"START_DATE" DATE, 
	"END_DATE" DATE, 
	"CREATED_BY" INTEGER, 
	"CREATED_ON" DATE, 
	"MOD_BY" INTEGER, 
	"MOD_ON" DATE, 
	"ASSIGNED_TO" INTEGER, 
	"REASON" VARCHAR(50), 
	"STATUS" VARCHAR(2)
   ) ;
--------------------------------------------------------
--  DDL for Table NUMBER_POOL
--------------------------------------------------------

  CREATE TABLE "PREPROD"."NUMBER_POOL" 
   (	"ID" INTEGER, 
	"MSISDN" VARCHAR(20), 
	"SIM_NO" VARCHAR(20), 
	"IMSI" VARCHAR(20), 
	"CREATED_BY" INTEGER, 
	"CREATED_DATE" DATE, 
	"MOD_BY" INTEGER, 
	"MOD_DATE" DATE, 
	"ENABLED" INTEGER, 
	"PH_SIM" INTEGER, 
	"ASSMT_ID" INTEGER, 
	"CUR_LOC" VARCHAR(32), 
	"CUR_NMS_POOL" VARCHAR(32)
   ) ;
--------------------------------------------------------
--  DDL for Table PRIVILEGE
--------------------------------------------------------

  CREATE TABLE "PREPROD"."PRIVILEGE" 
   (	"ID" INTEGER, 
	"P_NAME" VARCHAR(32), 
	"CREATEDDATE" TIMESTAMP (6), 
	"CREATEDBY" INTEGER, 
	"MODDATE" TIMESTAMP (6), 
	"MODIFIEDBY" INTEGER, 
	"DESCRIPTION" VARCHAR(200)
   ) ;
--------------------------------------------------------
--  DDL for Table ROLES
--------------------------------------------------------

  CREATE TABLE "PREPROD"."ROLES" 
   (	"ID" INTEGER, 
	"R_NAME" VARCHAR(32), 
	"DESCRIPTION" VARCHAR(128), 
	"CREATEDBY" VARCHAR(32), 
	"MODIFIEDBY" VARCHAR(32), 
	"CREATEDDATE" TIMESTAMP (6), 
	"MODIFIEDDATE" TIMESTAMP (6), 
	"IS_SERVICE_ROLE" INTEGER DEFAULT 0, 
	"ADMIN_ROLE" INTEGER, 
	"SU_ROLE" INTEGER
   ) ;
--------------------------------------------------------
--  DDL for Table ROLE_PRIVILEGE
--------------------------------------------------------

  CREATE TABLE "PREPROD"."ROLE_PRIVILEGE" 
   (	"ROLE_ID" INTEGER, 
	"PRIVILEGE_ID" INTEGER
   ) ;
--------------------------------------------------------
--  DDL for Table USER_ROLES
--------------------------------------------------------

  CREATE TABLE "PREPROD"."USER_ROLES" 
   (	"USER_ID" INTEGER, 
	"ROLE_ID" INTEGER
   ) ;
   
   
--------------------------------------------------------
--  DDL for Index PRIVILEGENAME_UNIQUE
--------------------------------------------------------

  CREATE UNIQUE INDEX "PREPROD"."PRIVILEGENAME_UNIQUE" ON "PREPROD"."PRIVILEGE" ("P_NAME") 
  ;
--------------------------------------------------------
--  DDL for Index CLEANUP_LOG_MASTER_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "PREPROD"."CLEANUP_LOG_MASTER_PK" ON "PREPROD"."CLEANUP_LOG_MASTER" ("ID") 
  ;
--------------------------------------------------------
--  DDL for Index NC_APP_USER_GRP_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "PREPROD"."NC_APP_USER_GRP_PK" ON "PREPROD"."NC_APP_USER_GRP" ("ID") 
  ;
--------------------------------------------------------
--  DDL for Index NUMBER_POOL_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "PREPROD"."NUMBER_POOL_PK" ON "PREPROD"."NUMBER_POOL" ("ID") 
  ;
--------------------------------------------------------
--  DDL for Index NC_U_G_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "PREPROD"."NC_U_G_PK" ON "PREPROD"."NC_USER_GRP" ("USER_ID", "GRP_ID") 
  ;
--------------------------------------------------------
--  DDL for Index ROLEPRIVILEGEID_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "PREPROD"."ROLEPRIVILEGEID_PK" ON "PREPROD"."ROLE_PRIVILEGE" ("ROLE_ID", "PRIVILEGE_ID") 
  ;
--------------------------------------------------------
--  DDL for Index ROLENAME_UNIQUE
--------------------------------------------------------

  CREATE UNIQUE INDEX "PREPROD"."ROLENAME_UNIQUE" ON "PREPROD"."ROLES" ("R_NAME") 
  ;
--------------------------------------------------------
--  DDL for Index NUMBER_ASGNMT_HISTORY_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "PREPROD"."NUMBER_ASGNMT_HISTORY_PK" ON "PREPROD"."NUMBER_ASGNMT_HISTORY" ("ID") 
  ;
--------------------------------------------------------
--  DDL for Index EMAIL_UNIQUE
--------------------------------------------------------

  CREATE UNIQUE INDEX "PREPROD"."EMAIL_UNIQUE" ON "PREPROD"."APP_USERS" ("EMAIL") 
  ;
--------------------------------------------------------
--  DDL for Index ROLEID_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "PREPROD"."ROLEID_PK" ON "PREPROD"."ROLES" ("ID") 
  ;
--------------------------------------------------------
--  DDL for Index NC_G_NP_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "PREPROD"."NC_G_NP_PK" ON "PREPROD"."NC_GRP_MSISDN" ("GRP_ID", "NP_ID") 
  ;
--------------------------------------------------------
--  DDL for Index USERID_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "PREPROD"."USERID_PK" ON "PREPROD"."APP_USERS" ("ID") 
  ;
--------------------------------------------------------
--  DDL for Index CONTACTNO_UNIQUE
--------------------------------------------------------

  CREATE UNIQUE INDEX "PREPROD"."CONTACTNO_UNIQUE" ON "PREPROD"."APP_USERS" ("CONTACT_NO") 
  ;
--------------------------------------------------------
--  DDL for Index NUMBER_POOL_UK1
--------------------------------------------------------

  CREATE UNIQUE INDEX "PREPROD"."NUMBER_POOL_UK1" ON "PREPROD"."NUMBER_POOL" ("MSISDN", "SIM_NO", "IMSI") 
  ;
--------------------------------------------------------
--  DDL for Index USERNAME_UNIQUE
--------------------------------------------------------

  CREATE UNIQUE INDEX "PREPROD"."USERNAME_UNIQUE" ON "PREPROD"."APP_USERS" ("U_NAME") 
  ;
--------------------------------------------------------
--  DDL for Index PRIVILEGEID_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "PREPROD"."PRIVILEGEID_PK" ON "PREPROD"."PRIVILEGE" ("ID") 
  ;
--------------------------------------------------------
--  DDL for Index USERROLEID_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "PREPROD"."USERROLEID_PK" ON "PREPROD"."USER_ROLES" ("USER_ID", "ROLE_ID") 
  ;
  
  
  --------------------------------------------------------
--  Constraints for Table APP_USERS
--------------------------------------------------------

  ALTER TABLE "PREPROD"."APP_USERS" ALTER COLUMN "IS_SERVICE_USER" SET NOT NULL ;
  ALTER TABLE "PREPROD"."APP_USERS" ADD CONSTRAINT "EMAIL_UNIQUE" UNIQUE ("EMAIL") ;
  ALTER TABLE "PREPROD"."APP_USERS" ADD CONSTRAINT "CONTACTNO_UNIQUE" UNIQUE ("CONTACT_NO") ;
  ALTER TABLE "PREPROD"."APP_USERS" ADD CONSTRAINT "USERNAME_UNIQUE" UNIQUE ("U_NAME") ;
  ALTER TABLE "PREPROD"."APP_USERS" ADD CONSTRAINT "USERID_PK" PRIMARY KEY ("ID") ;
  ALTER TABLE "PREPROD"."APP_USERS" ALTER COLUMN "IS_ENABLED" SET NOT NULL;
  ALTER TABLE "PREPROD"."APP_USERS" ALTER COLUMN  "IS_ACC_LOCKED" SET NOT NULL;
  ALTER TABLE "PREPROD"."APP_USERS" ALTER COLUMN  "IS_ACC_DEFAULT_PSWD" SET NOT NULL ;
  ALTER TABLE "PREPROD"."APP_USERS" ALTER COLUMN  "CONTACT_NO" SET NOT NULL ;
  ALTER TABLE "PREPROD"."APP_USERS" ALTER COLUMN  "EMAIL" SET NOT NULL ;
  ALTER TABLE "PREPROD"."APP_USERS" ALTER COLUMN  "SALT" SET NOT NULL ;
  ALTER TABLE "PREPROD"."APP_USERS" ALTER COLUMN  "CREATEDDATE" SET NOT NULL ;
  ALTER TABLE "PREPROD"."APP_USERS" ALTER COLUMN  "CREATEDBY" SET NOT NULL ;
  ALTER TABLE "PREPROD"."APP_USERS" ALTER COLUMN  "LDAPAUTHENTICATION" SET NOT NULL ;
  ALTER TABLE "PREPROD"."APP_USERS" ALTER COLUMN  "LASTNAME" SET NOT NULL ;
  ALTER TABLE "PREPROD"."APP_USERS" ALTER COLUMN  "FIRSTNAME" SET NOT NULL ;
  ALTER TABLE "PREPROD"."APP_USERS" ALTER COLUMN  "U_PWD" SET NOT NULL ;
  ALTER TABLE "PREPROD"."APP_USERS" ALTER COLUMN  "U_NAME" SET NOT NULL ;
  ALTER TABLE "PREPROD"."APP_USERS" ALTER COLUMN  "ID" SET NOT NULL ;
  
  --------------------------------------------------------
--  Constraints for Table NC_USER_GRP
--------------------------------------------------------

  ALTER TABLE "PREPROD"."NC_USER_GRP" ADD CONSTRAINT "NC_U_G_PK" PRIMARY KEY ("USER_ID", "GRP_ID");
  ALTER TABLE "PREPROD"."NC_USER_GRP" ALTER COLUMN  "GRP_ID" SET NOT NULL ;
  ALTER TABLE "PREPROD"."NC_USER_GRP" ALTER COLUMN  "USER_ID" SET NOT NULL;
  
--------------------------------------------------------
--  Constraints for Table ROLE_PRIVILEGE
--------------------------------------------------------

  ALTER TABLE "PREPROD"."ROLE_PRIVILEGE" ADD CONSTRAINT "ROLEPRIVILEGEID_PK" PRIMARY KEY ("ROLE_ID", "PRIVILEGE_ID");
  ALTER TABLE "PREPROD"."ROLE_PRIVILEGE" ALTER COLUMN "PRIVILEGE_ID"  SET NOT NULL;
  ALTER TABLE "PREPROD"."ROLE_PRIVILEGE" ALTER COLUMN "ROLE_ID"  SET NOT NULL;
  
--------------------------------------------------------
--  Constraints for Table ROLES
--------------------------------------------------------

  ALTER TABLE "PREPROD"."ROLES" ALTER COLUMN "IS_SERVICE_ROLE" SET NOT NULL;
  ALTER TABLE "PREPROD"."ROLES" ALTER COLUMN "DESCRIPTION" SET NOT NULL;
  ALTER TABLE "PREPROD"."ROLES" ADD CONSTRAINT "ROLENAME_UNIQUE" UNIQUE ("R_NAME") ;
  ALTER TABLE "PREPROD"."ROLES" ADD CONSTRAINT "ROLEID_PK" PRIMARY KEY ("ID") ;
  ALTER TABLE "PREPROD"."ROLES" ALTER COLUMN "CREATEDDATE" SET NOT NULL;
  ALTER TABLE "PREPROD"."ROLES" ALTER COLUMN "CREATEDBY" SET NOT NULL;
  ALTER TABLE "PREPROD"."ROLES" ALTER COLUMN "R_NAME" SET NOT NULL;
  ALTER TABLE "PREPROD"."ROLES" ALTER COLUMN "ID" SET NOT NULL;
--------------------------------------------------------
--  Constraints for Table NC_GRP_MSISDN
--------------------------------------------------------

  ALTER TABLE "PREPROD"."NC_GRP_MSISDN" ADD CONSTRAINT "NC_G_NP_PK" PRIMARY KEY ("GRP_ID", "NP_ID") ;
  ALTER TABLE "PREPROD"."NC_GRP_MSISDN" ALTER COLUMN "NP_ID" SET NOT NULL;
  ALTER TABLE "PREPROD"."NC_GRP_MSISDN" ALTER COLUMN "GRP_ID" SET NOT NULL;
  
 --------------------------------------------------------
--  Constraints for Table PRIVILEGE
--------------------------------------------------------

  ALTER TABLE "PREPROD"."PRIVILEGE" ALTER COLUMN "DESCRIPTION" SET NOT NULL;
  ALTER TABLE "PREPROD"."PRIVILEGE" ADD CONSTRAINT "PRIVILEGENAME_UNIQUE" UNIQUE ("P_NAME") ;
  ALTER TABLE "PREPROD"."PRIVILEGE" ADD CONSTRAINT "PRIVILEGEID_PK" PRIMARY KEY ("ID") ;
  ALTER TABLE "PREPROD"."PRIVILEGE" ALTER COLUMN "CREATEDBY" SET NOT NULL;
  ALTER TABLE "PREPROD"."PRIVILEGE" ALTER COLUMN "CREATEDDATE" SET NOT NULL;
  ALTER TABLE "PREPROD"."PRIVILEGE" ALTER COLUMN "P_NAME" SET NOT NULL;
  ALTER TABLE "PREPROD"."PRIVILEGE" ALTER COLUMN "ID" SET NOT NULL;
--------------------------------------------------------
--  Constraints for Table NC_APP_USER_GRP
--------------------------------------------------------

  ALTER TABLE "PREPROD"."NC_APP_USER_GRP" ADD CONSTRAINT "NC_APP_USER_GRP_PK" PRIMARY KEY ("ID") ;
  ALTER TABLE "PREPROD"."NC_APP_USER_GRP" ALTER COLUMN "CREATED_BY" SET NOT NULL;
  ALTER TABLE "PREPROD"."NC_APP_USER_GRP" ALTER COLUMN "CREATED_ON" SET NOT NULL;
  ALTER TABLE "PREPROD"."NC_APP_USER_GRP" ALTER COLUMN "GRP_DESC" SET NOT NULL;
  ALTER TABLE "PREPROD"."NC_APP_USER_GRP" ALTER COLUMN "GRP_NAME" SET NOT NULL;
  ALTER TABLE "PREPROD"."NC_APP_USER_GRP" ALTER COLUMN "ID" SET NOT NULL;
--------------------------------------------------------
--  Constraints for Table USER_ROLES
--------------------------------------------------------

  ALTER TABLE "PREPROD"."USER_ROLES" ADD CONSTRAINT "USERROLEID_PK" PRIMARY KEY ("USER_ID", "ROLE_ID") ;
  ALTER TABLE "PREPROD"."USER_ROLES" ALTER COLUMN "ROLE_ID" SET NOT NULL;
  ALTER TABLE "PREPROD"."USER_ROLES" ALTER COLUMN "USER_ID" SET NOT NULL;
  
--------------------------------------------------------
--  Constraints for Table NUMBER_POOL
--------------------------------------------------------

  ALTER TABLE "PREPROD"."NUMBER_POOL" ADD CONSTRAINT "NUMBER_POOL_UK1" UNIQUE ("MSISDN", "SIM_NO", "IMSI") ;
  ALTER TABLE "PREPROD"."NUMBER_POOL" ADD CONSTRAINT "NUMBER_POOL_PK" PRIMARY KEY ("ID") ;
  ALTER TABLE "PREPROD"."NUMBER_POOL" ALTER COLUMN "ENABLED" SET NOT NULL;
  ALTER TABLE "PREPROD"."NUMBER_POOL" ALTER COLUMN "CREATED_DATE" SET NOT NULL;
  ALTER TABLE "PREPROD"."NUMBER_POOL" ALTER COLUMN "CREATED_BY" SET NOT NULL;
  ALTER TABLE "PREPROD"."NUMBER_POOL" ALTER COLUMN "IMSI" SET NOT NULL;
  ALTER TABLE "PREPROD"."NUMBER_POOL" ALTER COLUMN "SIM_NO" SET NOT NULL;
  ALTER TABLE "PREPROD"."NUMBER_POOL" ALTER COLUMN "MSISDN" SET NOT NULL;
  ALTER TABLE "PREPROD"."NUMBER_POOL" ALTER COLUMN "ID" SET NOT NULL;
--------------------------------------------------------
--  Constraints for Table CLEANUP_LOG_MASTER
--------------------------------------------------------

  ALTER TABLE "PREPROD"."CLEANUP_LOG_MASTER" ALTER COLUMN "EXEC_DATE" SET NOT NULL;
  ALTER TABLE "PREPROD"."CLEANUP_LOG_MASTER" ALTER COLUMN "EXEC_USER" SET NOT NULL;
  ALTER TABLE "PREPROD"."CLEANUP_LOG_MASTER" ADD CONSTRAINT "CLEANUP_LOG_MASTER_PK" PRIMARY KEY ("ID") ;
  ALTER TABLE "PREPROD"."CLEANUP_LOG_MASTER" ALTER COLUMN "SIM_NO" SET NOT NULL;
  ALTER TABLE "PREPROD"."CLEANUP_LOG_MASTER" ALTER COLUMN "IMSI" SET NOT NULL;
  ALTER TABLE "PREPROD"."CLEANUP_LOG_MASTER" ALTER COLUMN "MSISDN" SET NOT NULL;
  ALTER TABLE "PREPROD"."CLEANUP_LOG_MASTER" ALTER COLUMN "ID" SET NOT NULL;
--------------------------------------------------------
--  Constraints for Table NUMBER_ASGNMT_HISTORY
--------------------------------------------------------

  ALTER TABLE "PREPROD"."NUMBER_ASGNMT_HISTORY" ADD CONSTRAINT "NUMBER_ASGNMT_HISTORY_PK" PRIMARY KEY ("ID") ;
  ALTER TABLE "PREPROD"."NUMBER_ASGNMT_HISTORY" ALTER COLUMN "STATUS" SET NOT NULL;
  ALTER TABLE "PREPROD"."NUMBER_ASGNMT_HISTORY" ALTER COLUMN "REASON" SET NOT NULL;
  ALTER TABLE "PREPROD"."NUMBER_ASGNMT_HISTORY" ALTER COLUMN "CREATED_ON" SET NOT NULL;
  ALTER TABLE "PREPROD"."NUMBER_ASGNMT_HISTORY" ALTER COLUMN "CREATED_BY" SET NOT NULL;
  ALTER TABLE "PREPROD"."NUMBER_ASGNMT_HISTORY" ALTER COLUMN "ASSIGNED_TO" SET NOT NULL;
  ALTER TABLE "PREPROD"."NUMBER_ASGNMT_HISTORY" ALTER COLUMN "START_DATE" SET NOT NULL;
  ALTER TABLE "PREPROD"."NUMBER_ASGNMT_HISTORY" ALTER COLUMN "DESCRIPTION" SET NOT NULL;
  ALTER TABLE "PREPROD"."NUMBER_ASGNMT_HISTORY" ALTER COLUMN "ID" SET NOT NULL;
  
--------------------------------------------------------
--  Ref Constraints for Table NC_GRP_MSISDN
--------------------------------------------------------

  ALTER TABLE "PREPROD"."NC_GRP_MSISDN" ADD CONSTRAINT "NC_G_NP_1" FOREIGN KEY ("GRP_ID")
	  REFERENCES "PREPROD"."NC_APP_USER_GRP" ("ID") ;
  ALTER TABLE "PREPROD"."NC_GRP_MSISDN" ADD CONSTRAINT "NC_G_NP_2" FOREIGN KEY ("NP_ID")
	  REFERENCES "PREPROD"."NUMBER_POOL" ("ID") ;
--------------------------------------------------------
--  Ref Constraints for Table NC_USER_GRP
--------------------------------------------------------

  ALTER TABLE "PREPROD"."NC_USER_GRP" ADD CONSTRAINT "FK_U_G_1" FOREIGN KEY ("USER_ID")
	  REFERENCES "PREPROD"."APP_USERS" ("ID") ;
  ALTER TABLE "PREPROD"."NC_USER_GRP" ADD CONSTRAINT "NC_U_G_2" FOREIGN KEY ("GRP_ID")
	  REFERENCES "PREPROD"."NC_APP_USER_GRP" ("ID") ;
--------------------------------------------------------
--  Ref Constraints for Table NUMBER_ASGNMT_HISTORY
--------------------------------------------------------

  ALTER TABLE "PREPROD"."NUMBER_ASGNMT_HISTORY" ADD CONSTRAINT "NUMBER_ASGNMT_HISTORY_FK1" FOREIGN KEY ("ASSIGNED_TO")
	  REFERENCES "PREPROD"."APP_USERS" ("ID") ;
--------------------------------------------------------
--  Ref Constraints for Table NUMBER_POOL
--------------------------------------------------------

  ALTER TABLE "PREPROD"."NUMBER_POOL" ADD CONSTRAINT "NUMBER_POOL_ASSIGN_FK" FOREIGN KEY ("ASSMT_ID")
	  REFERENCES "PREPROD"."NUMBER_ASGNMT_HISTORY" ("ID") ;
--------------------------------------------------------
--  Ref Constraints for Table ROLE_PRIVILEGE
--------------------------------------------------------

  ALTER TABLE "PREPROD"."ROLE_PRIVILEGE" ADD CONSTRAINT "PROVILEGEID_FK" FOREIGN KEY ("PRIVILEGE_ID")
	  REFERENCES "PREPROD"."PRIVILEGE" ("ID") ;
  ALTER TABLE "PREPROD"."ROLE_PRIVILEGE" ADD CONSTRAINT "ROLEID_FK1" FOREIGN KEY ("ROLE_ID")
	  REFERENCES "PREPROD"."ROLES" ("ID") ;
--------------------------------------------------------
--  Ref Constraints for Table USER_ROLES
--------------------------------------------------------

  ALTER TABLE "PREPROD"."USER_ROLES" ADD CONSTRAINT "ROLEID_FK" FOREIGN KEY ("ROLE_ID")
	  REFERENCES "PREPROD"."ROLES" ("ID") ;
  ALTER TABLE "PREPROD"."USER_ROLES" ADD CONSTRAINT "USERID_FK" FOREIGN KEY ("USER_ID")
	  REFERENCES "PREPROD"."APP_USERS" ("ID") ;

--------------------------------------------------------
--  DML for Table PRIVILEGE
--------------------------------------------------------
Insert into PREPROD.PRIVILEGE (ID,P_NAME,CREATEDDATE,CREATEDBY,MODDATE,MODIFIEDBY,DESCRIPTION) values ( NEXT VALUE FOR PREPROD.PRIVILEGE_SEQ,'CREATE_USER',SYSDATE,1,null,null,'Privilege to create new user');
Insert into PREPROD.PRIVILEGE (ID,P_NAME,CREATEDDATE,CREATEDBY,MODDATE,MODIFIEDBY,DESCRIPTION) values (NEXT VALUE FOR PREPROD.PRIVILEGE_SEQ,'UPDATE_USER',SYSDATE,1,null,null,'Privilege to update an existing user');
Insert into PREPROD.PRIVILEGE (ID,P_NAME,CREATEDDATE,CREATEDBY,MODDATE,MODIFIEDBY,DESCRIPTION) values (NEXT VALUE FOR PREPROD.PRIVILEGE_SEQ,'SEARCH_USER',SYSDATE,1,null,null,'Privilege to search existing user');
Insert into PREPROD.PRIVILEGE (ID,P_NAME,CREATEDDATE,CREATEDBY,MODDATE,MODIFIEDBY,DESCRIPTION) values (NEXT VALUE FOR PREPROD.PRIVILEGE_SEQ,'RESET_PWD',SYSDATE,1,null,null,'Privilege to reset password');
Insert into PREPROD.PRIVILEGE (ID,P_NAME,CREATEDDATE,CREATEDBY,MODDATE,MODIFIEDBY,DESCRIPTION) values (NEXT VALUE FOR PREPROD.PRIVILEGE_SEQ,'CLEANUP_PROFILE',SYSDATE,1,null,null,'Privilege to cleanup profile');
Insert into PREPROD.PRIVILEGE (ID,P_NAME,CREATEDDATE,CREATEDBY,MODDATE,MODIFIEDBY,DESCRIPTION) values (NEXT VALUE FOR PREPROD.PRIVILEGE_SEQ,'CREATE_ROLE',SYSDATE,1,null,null,'Privilege to create role');
Insert into PREPROD.PRIVILEGE (ID,P_NAME,CREATEDDATE,CREATEDBY,MODDATE,MODIFIEDBY,DESCRIPTION) values (NEXT VALUE FOR PREPROD.PRIVILEGE_SEQ,'UPDATE_ROLE',SYSDATE,1,null,null,'Privilege to update role');
Insert into PREPROD.PRIVILEGE (ID,P_NAME,CREATEDDATE,CREATEDBY,MODDATE,MODIFIEDBY,DESCRIPTION) values (NEXT VALUE FOR PREPROD.PRIVILEGE_SEQ,'CREATE_PRIVILEGE',SYSDATE,1,null,null,'Privilege to create privilege');
Insert into PREPROD.PRIVILEGE (ID,P_NAME,CREATEDDATE,CREATEDBY,MODDATE,MODIFIEDBY,DESCRIPTION) values (NEXT VALUE FOR PREPROD.PRIVILEGE_SEQ,'UPDATE_NUMBER',SYSDATE,1,null,null,'Privilege to update number details');
Insert into PREPROD.PRIVILEGE (ID,P_NAME,CREATEDDATE,CREATEDBY,MODDATE,MODIFIEDBY,DESCRIPTION) values (NEXT VALUE FOR PREPROD.PRIVILEGE_SEQ,'ADD_NUMBER',SYSDATE,1,null,null,'Privilege to add new number to number pool');
Insert into PREPROD.PRIVILEGE (ID,P_NAME,CREATEDDATE,CREATEDBY,MODDATE,MODIFIEDBY,DESCRIPTION) values (NEXT VALUE FOR PREPROD.PRIVILEGE_SEQ,'UPDATE_ASSIGNMENT',SYSDATE,1,null,null,'Privilege to update assignment');
Insert into PREPROD.PRIVILEGE (ID,P_NAME,CREATEDDATE,CREATEDBY,MODDATE,MODIFIEDBY,DESCRIPTION) values (NEXT VALUE FOR PREPROD.PRIVILEGE_SEQ,'ADD_ASMT',SYSDATE,1,null,null,'Privilege to create new assignment');
Insert into PREPROD.PRIVILEGE (ID,P_NAME,CREATEDDATE,CREATEDBY,MODDATE,MODIFIEDBY,DESCRIPTION) values (NEXT VALUE FOR PREPROD.PRIVILEGE_SEQ,'UPDATE_ASMT',SYSDATE,1,null,null,'Privilege to update assignment');
Insert into PREPROD.PRIVILEGE (ID,P_NAME,CREATEDDATE,CREATEDBY,MODDATE,MODIFIEDBY,DESCRIPTION) values (NEXT VALUE FOR PREPROD.PRIVILEGE_SEQ,'SEARCH_SERVICE_USER',SYSDATE,1,null,null,'Privilege to search service user');
Insert into PREPROD.PRIVILEGE (ID,P_NAME,CREATEDDATE,CREATEDBY,MODDATE,MODIFIEDBY,DESCRIPTION) values (NEXT VALUE FOR PREPROD.PRIVILEGE_SEQ,'UPDATE_GROUP',SYSDATE,1,null,null,'Privilege to Update group information');
Insert into PREPROD.PRIVILEGE (ID,P_NAME,CREATEDDATE,CREATEDBY,MODDATE,MODIFIEDBY,DESCRIPTION) values (NEXT VALUE FOR PREPROD.PRIVILEGE_SEQ,'CREATE_GROUP',SYSDATE,1,null,null,'Privilege to create new user group');
Insert into PREPROD.PRIVILEGE (ID,P_NAME,CREATEDDATE,CREATEDBY,MODDATE,MODIFIEDBY,DESCRIPTION) values (NEXT VALUE FOR PREPROD.PRIVILEGE_SEQ,'CREATE_SERVICE_USER',SYSDATE,1,null,null,'Privilege to create service user');
Insert into PREPROD.PRIVILEGE (ID,P_NAME,CREATEDDATE,CREATEDBY,MODDATE,MODIFIEDBY,DESCRIPTION) values (NEXT VALUE FOR PREPROD.PRIVILEGE_SEQ,'SHOW_SUSER_DETAIL',SYSDATE,1,null,null,'Privilege to show service user details');

COMMIT;
--------------------------------------------------------
--  DML for Table ROLES
--------------------------------------------------------
Insert into PREPROD.ROLES (ID,R_NAME,DESCRIPTION,CREATEDBY,MODIFIEDBY,CREATEDDATE,MODIFIEDDATE,IS_SERVICE_ROLE,ADMIN_ROLE,SU_ROLE) values (NEXT VALUE FOR PREPROD.ROLE_SEQ,'ROLE_SU','Super Administrative Role','1',null,SYSDATE,null,0,0,1);
Insert into PREPROD.ROLES (ID,R_NAME,DESCRIPTION,CREATEDBY,MODIFIEDBY,CREATEDDATE,MODIFIEDDATE,IS_SERVICE_ROLE,ADMIN_ROLE,SU_ROLE) values (NEXT VALUE FOR PREPROD.ROLE_SEQ,'ROLE_ADMIN','Administrative Role','1',null,SYSDATE,null,0,1,0);
Insert into PREPROD.ROLES (ID,R_NAME,DESCRIPTION,CREATEDBY,MODIFIEDBY,CREATEDDATE,MODIFIEDDATE,IS_SERVICE_ROLE,ADMIN_ROLE,SU_ROLE) values (NEXT VALUE FOR PREPROD.ROLE_SEQ,'ROLE_CAS','Cleanup Service Role','1',null,SYSDATE,null,1,0,0);
Insert into PREPROD.ROLES (ID,R_NAME,DESCRIPTION,CREATEDBY,MODIFIEDBY,CREATEDDATE,MODIFIEDDATE,IS_SERVICE_ROLE,ADMIN_ROLE,SU_ROLE) values (NEXT VALUE FOR PREPROD.ROLE_SEQ,'ROLE_CLS','Profile Cleanup Role','1',null,SYSDATE,null,0,0,0);
Insert into PREPROD.ROLES (ID,R_NAME,DESCRIPTION,CREATEDBY,MODIFIEDBY,CREATEDDATE,MODIFIEDDATE,IS_SERVICE_ROLE,ADMIN_ROLE,SU_ROLE) values (5,'ROLE_PS','Subscriber Profile Search Role','1',null,SYSDATE,null,0,0,0);
Insert into PREPROD.ROLES (ID,R_NAME,DESCRIPTION,CREATEDBY,MODIFIEDBY,CREATEDDATE,MODIFIEDDATE,IS_SERVICE_ROLE,ADMIN_ROLE,SU_ROLE) values (6,'ROLE_PCH','Search Profile Cleanup History Role','1',null,SYSDATE,null,0,0,0);

COMMIT;
--------------------------------------------------------
--  DML for Table ROLE_PRIVILEGE
--------------------------------------------------------
Insert into PREPROD.ROLE_PRIVILEGE (ROLE_ID,PRIVILEGE_ID) values (1,1);
Insert into PREPROD.ROLE_PRIVILEGE (ROLE_ID,PRIVILEGE_ID) values (1,2);
Insert into PREPROD.ROLE_PRIVILEGE (ROLE_ID,PRIVILEGE_ID) values (1,3);
Insert into PREPROD.ROLE_PRIVILEGE (ROLE_ID,PRIVILEGE_ID) values (1,4);
Insert into PREPROD.ROLE_PRIVILEGE (ROLE_ID,PRIVILEGE_ID) values (1,5);
Insert into PREPROD.ROLE_PRIVILEGE (ROLE_ID,PRIVILEGE_ID) values (1,6);
Insert into PREPROD.ROLE_PRIVILEGE (ROLE_ID,PRIVILEGE_ID) values (1,7);
Insert into PREPROD.ROLE_PRIVILEGE (ROLE_ID,PRIVILEGE_ID) values (1,8);
Insert into PREPROD.ROLE_PRIVILEGE (ROLE_ID,PRIVILEGE_ID) values (1,9);
Insert into PREPROD.ROLE_PRIVILEGE (ROLE_ID,PRIVILEGE_ID) values (1,10);
Insert into PREPROD.ROLE_PRIVILEGE (ROLE_ID,PRIVILEGE_ID) values (1,11);
Insert into PREPROD.ROLE_PRIVILEGE (ROLE_ID,PRIVILEGE_ID) values (1,12);
Insert into PREPROD.ROLE_PRIVILEGE (ROLE_ID,PRIVILEGE_ID) values (1,13);
Insert into PREPROD.ROLE_PRIVILEGE (ROLE_ID,PRIVILEGE_ID) values (1,14);
Insert into PREPROD.ROLE_PRIVILEGE (ROLE_ID,PRIVILEGE_ID) values (1,15);
Insert into PREPROD.ROLE_PRIVILEGE (ROLE_ID,PRIVILEGE_ID) values (1,16);
Insert into PREPROD.ROLE_PRIVILEGE (ROLE_ID,PRIVILEGE_ID) values (1,17);
Insert into PREPROD.ROLE_PRIVILEGE (ROLE_ID,PRIVILEGE_ID) values (1,18);

COMMIT;
--------------------------------------------------------
--  DML for Table APP_USERS
--------------------------------------------------------

Insert into PREPROD.APP_USERS (ID,U_NAME,U_PWD,FIRSTNAME,LASTNAME,LDAPAUTHENTICATION,COMMENTS,CREATEDBY,MODIFIEDBY,CREATEDDATE,MODIFIEDDATE,SALT,EMAIL,CONTACT_NO,IS_ACC_DEFAULT_PSWD,IS_ACC_LOCKED,IS_ENABLED,IS_SERVICE_USER) values (NEXT VALUE FOR PREPROD.USER_SEQ,'superu','TEh6aendnTMJBcTG37UdnkZS0XP+jdIzJAMYwteAa4Y=','Super','User',0,'Number Cleanup Super User-User@1234','su',null,SYSDATE,null,'Uom2rJpf3YRlZzOqmTctOfJed9CFdq','nc_superu@diyarme.com','00000000001',0,0,1,0);

Insert into PREPROD.APP_USERS (ID,U_NAME,U_PWD,FIRSTNAME,LASTNAME,LDAPAUTHENTICATION,COMMENTS,CREATEDBY,MODIFIEDBY,CREATEDDATE,MODIFIEDDATE,SALT,EMAIL,CONTACT_NO,IS_ACC_DEFAULT_PSWD,IS_ACC_LOCKED,IS_ENABLED,IS_SERVICE_USER) values (NEXT VALUE FOR PREPROD.USER_SEQ,'GUI','$2a$11$yZaaamQz3w9e4s3CRNT0TeRvJifpGA8rIpcAcXk6tzeD1akU2zCem','GUI','GUI',0,'This credential will be used by Number Cleanup Admin UI to invoke microservices-DDS','superu',null,SYSDATE,null,'none','nc_uiserviceu@diyarme.com','00000000000',0,0,1,1);

COMMIT;
--------------------------------------------------------
--  DML for Table APP_USERS
--------------------------------------------------------
Insert into PREPROD.USER_ROLES (USER_ID,ROLE_ID) values (1,1);
Insert into PREPROD.USER_ROLES (USER_ID,ROLE_ID) values (2,3);

COMMIT;