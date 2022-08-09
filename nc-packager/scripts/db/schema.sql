--------------------------------------------------------
--  DDL for Sequence GROUP_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  GROUP_SEQ  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence LOG_DETAILS_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  LOG_DETAILS_SEQ  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence LOG_MASTER_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  LOG_MASTER_SEQ  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence NUMBER_ASSIGNMENT_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  NUMBER_ASSIGNMENT_SEQ  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence NUMBER_POOL_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  NUMBER_POOL_SEQ  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence PRIVILEGE_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  PRIVILEGE_SEQ  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence ROLE_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  ROLE_SEQ  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence USER_GRP_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  USER_GRP_SEQ  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence USER_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  USER_SEQ  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER  NOCYCLE ;
   
--------------------------------------------------------
--  DDL for Sequence BULK_ACTIVATION_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  BULK_ACTIVATION_SEQ  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER  NOCYCLE ;
   
--------------------------------------------------------
--  DDL for Sequence BULK_ACTIVATION_BATCH_ID_SEQ
-------------------------------------------------------- 
  
   CREATE SEQUENCE  BULK_ACTIVATION_BATCH_ID_SEQ  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER  NOCYCLE ;
   
--------------------------------------------------------
--  DDL for Sequence BULK_ACTIVATION_BATCH_ID_SEQ
-------------------------------------------------------- 
  
   CREATE SEQUENCE  DIGITAL_ONBOARDING_SEQ  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Table APP_USERS
--------------------------------------------------------

  CREATE TABLE APP_USERS 
   (	ID NUMBER, 
	U_NAME VARCHAR2(32), 
	U_PWD VARCHAR2(200), 
	FIRSTNAME VARCHAR2(32), 
	LASTNAME VARCHAR2(32), 
	LDAPAUTHENTICATION NUMBER(1,0), 
	COMMENTS VARCHAR2(128), 
	CREATEDBY VARCHAR2(32), 
	MODIFIEDBY VARCHAR2(32), 
	CREATEDDATE TIMESTAMP (6), 
	MODIFIEDDATE TIMESTAMP (6), 
	SALT VARCHAR2(200), 
	EMAIL VARCHAR2(32), 
	CONTACT_NO VARCHAR2(20), 
	IS_ACC_DEFAULT_PSWD NUMBER(1,0) DEFAULT 1, 
	IS_ACC_LOCKED NUMBER(1,0) DEFAULT 1, 
	IS_ENABLED NUMBER(1,0) DEFAULT 1, 
	IS_SERVICE_USER NUMBER(1,0) DEFAULT 0
   ) ;
--------------------------------------------------------
--  DDL for Table CLEANUP_LOG_MASTER
--------------------------------------------------------

  CREATE TABLE CLEANUP_LOG_MASTER 
   (	ID NUMBER, 
	MSISDN VARCHAR2(20), 
	IMSI VARCHAR2(30), 
	SIM_NO VARCHAR2(30), 
	NMS_POOL VARCHAR2(20), 
	NMS_PRICE VARCHAR2(20), 
	ERP_LOC VARCHAR2(20), 
	CS VARCHAR2(2000), 
	ESM VARCHAR2(2000), 
	BSCS VARCHAR2(2000), 
	NMS VARCHAR2(2000), 
	RAS VARCHAR2(2000), 
	ERP VARCHAR2(2000), 
	PCRF VARCHAR2(2000), 
	HLR VARCHAR2(2000), 
	SPR VARCHAR2(2000), 
	EXEC_DATE DATE, 
	EXEC_USER VARCHAR2(20) DEFAULT 't'
   ) ;
--------------------------------------------------------
--  DDL for Table NC_APP_USER_GRP
--------------------------------------------------------

  CREATE TABLE NC_APP_USER_GRP 
   (	ID NUMBER, 
	GRP_NAME VARCHAR2(30), 
	GRP_DESC VARCHAR2(60), 
	CREATED_ON DATE, 
	CREATED_BY NUMBER
   ) ;
--------------------------------------------------------
--  DDL for Table NC_GRP_MSISDN
--------------------------------------------------------

  CREATE TABLE NC_GRP_MSISDN 
   (	GRP_ID NUMBER, 
	NP_ID NUMBER
   ) ;
--------------------------------------------------------
--  DDL for Table NC_USER_GRP
--------------------------------------------------------

  CREATE TABLE NC_USER_GRP 
   (	USER_ID NUMBER, 
	GRP_ID NUMBER
   ) ;
--------------------------------------------------------
--  DDL for Table NUMBER_ASGNMT_HISTORY
--------------------------------------------------------

  CREATE TABLE NUMBER_ASGNMT_HISTORY 
   (	ID NUMBER, 
	DESCRIPTION VARCHAR2(100), 
	START_DATE DATE, 
	END_DATE DATE, 
	CREATED_BY NUMBER, 
	CREATED_ON DATE, 
	MOD_BY NUMBER, 
	MOD_ON DATE, 
	ASSIGNED_TO NUMBER, 
	REASON VARCHAR2(50), 
	STATUS VARCHAR2(2)
   ) ;
--------------------------------------------------------
--  DDL for Table NUMBER_POOL
--------------------------------------------------------

  CREATE TABLE NUMBER_POOL 
   (	ID NUMBER, 
	MSISDN VARCHAR2(20), 
	SIM_NO VARCHAR2(20), 
	IMSI VARCHAR2(20), 
	CREATED_BY NUMBER, 
	CREATED_DATE DATE, 
	MOD_BY NUMBER, 
	MOD_DATE DATE, 
	ENABLED NUMBER(1,0), 
	ASSMT_ID NUMBER, 
	CUR_LOC VARCHAR2(32), 
	CUR_NMS_POOL VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table PRIVILEGE
--------------------------------------------------------

  CREATE TABLE PRIVILEGE 
   (	ID NUMBER, 
	P_NAME VARCHAR2(32), 
	CREATEDDATE TIMESTAMP (6), 
	CREATEDBY NUMBER, 
	MODDATE TIMESTAMP (6), 
	MODIFIEDBY NUMBER, 
	DESCRIPTION VARCHAR2(200)
   ) ;
--------------------------------------------------------
--  DDL for Table ROLES
--------------------------------------------------------

  CREATE TABLE ROLES 
   (	ID NUMBER, 
	R_NAME VARCHAR2(32), 
	DESCRIPTION VARCHAR2(128), 
	CREATEDBY VARCHAR2(32), 
	MODIFIEDBY VARCHAR2(32), 
	CREATEDDATE TIMESTAMP (6), 
	MODIFIEDDATE TIMESTAMP (6), 
	IS_SERVICE_ROLE NUMBER(1,0) DEFAULT 0, 
	ADMIN_ROLE NUMBER, 
	SU_ROLE NUMBER
   ) ;
--------------------------------------------------------
--  DDL for Table ROLE_PRIVILEGE
--------------------------------------------------------

  CREATE TABLE ROLE_PRIVILEGE 
   (	ROLE_ID NUMBER, 
	PRIVILEGE_ID NUMBER
   ) ;
--------------------------------------------------------
--  DDL for Table USER_ROLES
--------------------------------------------------------

  CREATE TABLE USER_ROLES 
   (	USER_ID NUMBER, 
	ROLE_ID NUMBER
   ) ;
   
--------------------------------------------------------
--  DDL for Table BULK_ACTIVATION_DETAILS
--------------------------------------------------------
  CREATE TABLE BULK_ACTIVATION_DETAILS
   (	ID NUMBER NOT NULL ENABLE, 
	MSISDN VARCHAR2(20) NOT NULL ENABLE, 
	IMSI VARCHAR2(20), 
	SIM_NO VARCHAR2(20), 
	CL_ERP_LOCATION VARCHAR2(100), 
	CL_NMS_POOL VARCHAR2(100), 
	NMS_DEALER VARCHAR2(30), 
	PROMO_ID VARCHAR2(100), 
	SUBS_TYPE VARCHAR2(2), 
	START_TIME TIMESTAMP (6), 
	END_TIME TIMESTAMP (6), 
	STATUS VARCHAR2(20), 
	ERROR_DETAILS VARCHAR2(3000), 
	USERNAME VARCHAR2(100), 
	BATCH_ID NUMBER NOT NULL ENABLE, 
	CO_TYPE VARCHAR2(1) DEFAULT 'V' NOT NULL ENABLE, 
	SPC_TYPE VARCHAR2(30), 
	SPC_ID VARCHAR2(30), 
	CIVIL_ID VARCHAR2(50), 
	PROMO_NAME VARCHAR2(100), 
	 CONSTRAINT BULK_ACTIVATION_DETAILS_PK PRIMARY KEY (ID)
	);
	
	
	  CREATE TABLE NC_DIGITAL_ONBOARDING (	
	   	ID NUMBER NOT NULL ENABLE, 
		MSISDN VARCHAR2(20) NOT NULL ENABLE, 
		BSCS_SIM_NO VARCHAR2(40), 
		BSCS_IMSI VARCHAR2(40), 
		LOCAL_SIM VARCHAR2(40) NOT NULL ENABLE, 
		LOCAL_IMSI VARCHAR2(40) NOT NULL ENABLE, 
		CLEANUP_STATUS VARCHAR2(1) NOT NULL ENABLE, 
		ONBOARDING_STATUS VARCHAR2(1) NOT NULL ENABLE, 
		TRX_DATE DATE NOT NULL ENABLE, 
		TRX_DETAILS VARCHAR2(3000), 
		 CONSTRAINT NC_DIGITAL_ONBOARDING_PK PRIMARY KEY (ID)
	 );
	 
	CREATE TABLE DIGITAL_OFFERS (
     	OFFER_ID NUMBER NOT NULL ENABLE, 
		OFFER_NAME VARCHAR2(100) NOT NULL ENABLE, 
		OFFER_DESC VARCHAR2(1000) NOT NULL ENABLE, 
		ACC_GROUP VARCHAR2(20) NOT NULL ENABLE, 
		ENABLED NUMBER(1,0) DEFAULT 1 NOT NULL ENABLE, 
 		CONSTRAINT DIGITAL_OFFERS_PK PRIMARY KEY (OFFER_ID)
	 );
--------------------------------------------------------
--  DDL for Index PRIVILEGENAME_UNIQUE
--------------------------------------------------------

  CREATE UNIQUE INDEX PRIVILEGENAME_UNIQUE ON PRIVILEGE (P_NAME) 
  ;
--------------------------------------------------------
--  DDL for Index CLEANUP_LOG_MASTER_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX CLEANUP_LOG_MASTER_PK ON CLEANUP_LOG_MASTER (ID) 
  ;
--------------------------------------------------------
--  DDL for Index NC_APP_USER_GRP_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX NC_APP_USER_GRP_PK ON NC_APP_USER_GRP (ID) 
  ;
--------------------------------------------------------
--  DDL for Index NUMBER_POOL_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX NUMBER_POOL_PK ON NUMBER_POOL (ID) 
  ;
--------------------------------------------------------
--  DDL for Index NC_U_G_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX NC_U_G_PK ON NC_USER_GRP (USER_ID, GRP_ID) 
  ;
--------------------------------------------------------
--  DDL for Index ROLEPRIVILEGEID_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX ROLEPRIVILEGEID_PK ON ROLE_PRIVILEGE (ROLE_ID, PRIVILEGE_ID) 
  ;
--------------------------------------------------------
--  DDL for Index ROLENAME_UNIQUE
--------------------------------------------------------

  CREATE UNIQUE INDEX ROLENAME_UNIQUE ON ROLES (R_NAME) 
  ;
--------------------------------------------------------
--  DDL for Index NUMBER_ASGNMT_HISTORY_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX NUMBER_ASGNMT_HISTORY_PK ON NUMBER_ASGNMT_HISTORY (ID) 
  ;
--------------------------------------------------------
--  DDL for Index EMAIL_UNIQUE
--------------------------------------------------------

  CREATE UNIQUE INDEX EMAIL_UNIQUE ON APP_USERS (EMAIL) 
  ;
--------------------------------------------------------
--  DDL for Index ROLEID_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX ROLEID_PK ON ROLES (ID) 
  ;
--------------------------------------------------------
--  DDL for Index NC_G_NP_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX NC_G_NP_PK ON NC_GRP_MSISDN (GRP_ID, NP_ID) 
  ;
--------------------------------------------------------
--  DDL for Index USERID_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX USERID_PK ON APP_USERS (ID) 
  ;
--------------------------------------------------------
--  DDL for Index CONTACTNO_UNIQUE
--------------------------------------------------------

  CREATE UNIQUE INDEX CONTACTNO_UNIQUE ON APP_USERS (CONTACT_NO) 
  ;
--------------------------------------------------------
--  DDL for Index NUMBER_POOL_UK1
--------------------------------------------------------

  CREATE UNIQUE INDEX NUMBER_POOL_UK1 ON NUMBER_POOL (MSISDN, SIM_NO, IMSI) 
  ;
--------------------------------------------------------
--  DDL for Index USERNAME_UNIQUE
--------------------------------------------------------

  CREATE UNIQUE INDEX USERNAME_UNIQUE ON APP_USERS (U_NAME) 
  ;
--------------------------------------------------------
--  DDL for Index PRIVILEGEID_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX PRIVILEGEID_PK ON PRIVILEGE (ID) 
  ;
--------------------------------------------------------
--  DDL for Index USERROLEID_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX USERROLEID_PK ON USER_ROLES (USER_ID, ROLE_ID) 
  ;
--------------------------------------------------------
--  Constraints for Table APP_USERS
--------------------------------------------------------

  ALTER TABLE APP_USERS MODIFY (IS_SERVICE_USER NOT NULL ENABLE);
  ALTER TABLE APP_USERS ADD CONSTRAINT EMAIL_UNIQUE UNIQUE (EMAIL) ENABLE;
  ALTER TABLE APP_USERS ADD CONSTRAINT CONTACTNO_UNIQUE UNIQUE (CONTACT_NO) ENABLE;
  ALTER TABLE APP_USERS ADD CONSTRAINT USERNAME_UNIQUE UNIQUE (U_NAME) ENABLE;
  ALTER TABLE APP_USERS ADD CONSTRAINT USERID_PK PRIMARY KEY (ID) ENABLE;
  ALTER TABLE APP_USERS MODIFY (IS_ENABLED NOT NULL ENABLE);
  ALTER TABLE APP_USERS MODIFY (IS_ACC_LOCKED NOT NULL ENABLE);
  ALTER TABLE APP_USERS MODIFY (IS_ACC_DEFAULT_PSWD NOT NULL ENABLE);
  ALTER TABLE APP_USERS MODIFY (CONTACT_NO NOT NULL ENABLE);
  ALTER TABLE APP_USERS MODIFY (EMAIL NOT NULL ENABLE);
  ALTER TABLE APP_USERS MODIFY (SALT NOT NULL ENABLE);
  ALTER TABLE APP_USERS MODIFY (CREATEDDATE NOT NULL ENABLE);
  ALTER TABLE APP_USERS MODIFY (CREATEDBY NOT NULL ENABLE);
  ALTER TABLE APP_USERS MODIFY (LDAPAUTHENTICATION NOT NULL ENABLE);
  ALTER TABLE APP_USERS MODIFY (LASTNAME NOT NULL ENABLE);
  ALTER TABLE APP_USERS MODIFY (FIRSTNAME NOT NULL ENABLE);
  ALTER TABLE APP_USERS MODIFY (U_PWD NOT NULL ENABLE);
  ALTER TABLE APP_USERS MODIFY (U_NAME NOT NULL ENABLE);
  ALTER TABLE APP_USERS MODIFY (ID NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table NC_USER_GRP
--------------------------------------------------------

  ALTER TABLE NC_USER_GRP ADD CONSTRAINT NC_U_G_PK PRIMARY KEY (USER_ID, GRP_ID) ENABLE;
  ALTER TABLE NC_USER_GRP MODIFY (GRP_ID NOT NULL ENABLE);
  ALTER TABLE NC_USER_GRP MODIFY (USER_ID NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ROLE_PRIVILEGE
--------------------------------------------------------

  ALTER TABLE ROLE_PRIVILEGE ADD CONSTRAINT ROLEPRIVILEGEID_PK PRIMARY KEY (ROLE_ID, PRIVILEGE_ID) ENABLE;
  ALTER TABLE ROLE_PRIVILEGE MODIFY (PRIVILEGE_ID NOT NULL ENABLE);
  ALTER TABLE ROLE_PRIVILEGE MODIFY (ROLE_ID NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ROLES
--------------------------------------------------------

  ALTER TABLE ROLES MODIFY (IS_SERVICE_ROLE NOT NULL ENABLE);
  ALTER TABLE ROLES MODIFY (DESCRIPTION NOT NULL ENABLE);
  ALTER TABLE ROLES ADD CONSTRAINT ROLENAME_UNIQUE UNIQUE (R_NAME) ENABLE;
  ALTER TABLE ROLES ADD CONSTRAINT ROLEID_PK PRIMARY KEY (ID) ENABLE;
  ALTER TABLE ROLES MODIFY (CREATEDDATE NOT NULL ENABLE);
  ALTER TABLE ROLES MODIFY (CREATEDBY NOT NULL ENABLE);
  ALTER TABLE ROLES MODIFY (R_NAME NOT NULL ENABLE);
  ALTER TABLE ROLES MODIFY (ID NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table NC_GRP_MSISDN
--------------------------------------------------------

  ALTER TABLE NC_GRP_MSISDN ADD CONSTRAINT NC_G_NP_PK PRIMARY KEY (GRP_ID, NP_ID) ENABLE;
  ALTER TABLE NC_GRP_MSISDN MODIFY (NP_ID NOT NULL ENABLE);
  ALTER TABLE NC_GRP_MSISDN MODIFY (GRP_ID NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PRIVILEGE
--------------------------------------------------------

  ALTER TABLE PRIVILEGE MODIFY (DESCRIPTION NOT NULL ENABLE);
  ALTER TABLE PRIVILEGE ADD CONSTRAINT PRIVILEGENAME_UNIQUE UNIQUE (P_NAME) ENABLE;
  ALTER TABLE PRIVILEGE ADD CONSTRAINT PRIVILEGEID_PK PRIMARY KEY (ID) ENABLE;
  ALTER TABLE PRIVILEGE MODIFY (CREATEDBY NOT NULL ENABLE);
  ALTER TABLE PRIVILEGE MODIFY (CREATEDDATE NOT NULL ENABLE);
  ALTER TABLE PRIVILEGE MODIFY (P_NAME NOT NULL ENABLE);
  ALTER TABLE PRIVILEGE MODIFY (ID NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table NC_APP_USER_GRP
--------------------------------------------------------

  ALTER TABLE NC_APP_USER_GRP ADD CONSTRAINT NC_APP_USER_GRP_PK PRIMARY KEY (ID) ENABLE;
  ALTER TABLE NC_APP_USER_GRP MODIFY (CREATED_BY NOT NULL ENABLE);
  ALTER TABLE NC_APP_USER_GRP MODIFY (CREATED_ON NOT NULL ENABLE);
  ALTER TABLE NC_APP_USER_GRP MODIFY (GRP_DESC NOT NULL ENABLE);
  ALTER TABLE NC_APP_USER_GRP MODIFY (GRP_NAME NOT NULL ENABLE);
  ALTER TABLE NC_APP_USER_GRP MODIFY (ID NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table USER_ROLES
--------------------------------------------------------

  ALTER TABLE USER_ROLES ADD CONSTRAINT USERROLEID_PK PRIMARY KEY (USER_ID, ROLE_ID) ENABLE;
  ALTER TABLE USER_ROLES MODIFY (ROLE_ID NOT NULL ENABLE);
  ALTER TABLE USER_ROLES MODIFY (USER_ID NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table NUMBER_POOL
--------------------------------------------------------

  ALTER TABLE NUMBER_POOL ADD CONSTRAINT NUMBER_POOL_UK1 UNIQUE (MSISDN, SIM_NO, IMSI) ENABLE;
  ALTER TABLE NUMBER_POOL ADD CONSTRAINT NUMBER_POOL_PK PRIMARY KEY (ID) ENABLE;
  ALTER TABLE NUMBER_POOL MODIFY (ENABLED NOT NULL ENABLE);
  ALTER TABLE NUMBER_POOL MODIFY (CREATED_DATE NOT NULL ENABLE);
  ALTER TABLE NUMBER_POOL MODIFY (CREATED_BY NOT NULL ENABLE);
  ALTER TABLE NUMBER_POOL MODIFY (IMSI NOT NULL ENABLE);
  ALTER TABLE NUMBER_POOL MODIFY (SIM_NO NOT NULL ENABLE);
  ALTER TABLE NUMBER_POOL MODIFY (MSISDN NOT NULL ENABLE);
  ALTER TABLE NUMBER_POOL MODIFY (ID NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table CLEANUP_LOG_MASTER
--------------------------------------------------------

  ALTER TABLE CLEANUP_LOG_MASTER MODIFY (EXEC_DATE NOT NULL ENABLE);
  ALTER TABLE CLEANUP_LOG_MASTER MODIFY (EXEC_USER NOT NULL ENABLE);
  ALTER TABLE CLEANUP_LOG_MASTER ADD CONSTRAINT CLEANUP_LOG_MASTER_PK PRIMARY KEY (ID) ENABLE;
  ALTER TABLE CLEANUP_LOG_MASTER MODIFY (SIM_NO NOT NULL ENABLE);
  ALTER TABLE CLEANUP_LOG_MASTER MODIFY (IMSI NOT NULL ENABLE);
  ALTER TABLE CLEANUP_LOG_MASTER MODIFY (MSISDN NOT NULL ENABLE);
  ALTER TABLE CLEANUP_LOG_MASTER MODIFY (ID NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table NUMBER_ASGNMT_HISTORY
--------------------------------------------------------

  ALTER TABLE NUMBER_ASGNMT_HISTORY ADD CONSTRAINT NUMBER_ASGNMT_HISTORY_PK PRIMARY KEY (ID) ENABLE;
  ALTER TABLE NUMBER_ASGNMT_HISTORY MODIFY (STATUS NOT NULL ENABLE);
  ALTER TABLE NUMBER_ASGNMT_HISTORY MODIFY (REASON NOT NULL ENABLE);
  ALTER TABLE NUMBER_ASGNMT_HISTORY MODIFY (CREATED_ON NOT NULL ENABLE);
  ALTER TABLE NUMBER_ASGNMT_HISTORY MODIFY (CREATED_BY NOT NULL ENABLE);
  ALTER TABLE NUMBER_ASGNMT_HISTORY MODIFY (ASSIGNED_TO NOT NULL ENABLE);
  ALTER TABLE NUMBER_ASGNMT_HISTORY MODIFY (START_DATE NOT NULL ENABLE);
  ALTER TABLE NUMBER_ASGNMT_HISTORY MODIFY (DESCRIPTION NOT NULL ENABLE);
  ALTER TABLE NUMBER_ASGNMT_HISTORY MODIFY (ID NOT NULL ENABLE);
--------------------------------------------------------
--  Ref Constraints for Table NC_GRP_MSISDN
--------------------------------------------------------

  ALTER TABLE NC_GRP_MSISDN ADD CONSTRAINT NC_G_NP_1 FOREIGN KEY (GRP_ID)
	  REFERENCES NC_APP_USER_GRP (ID) ENABLE;
  ALTER TABLE NC_GRP_MSISDN ADD CONSTRAINT NC_G_NP_2 FOREIGN KEY (NP_ID)
	  REFERENCES NUMBER_POOL (ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table NC_USER_GRP
--------------------------------------------------------

  ALTER TABLE NC_USER_GRP ADD CONSTRAINT FK_U_G_1 FOREIGN KEY (USER_ID)
	  REFERENCES APP_USERS (ID) ENABLE;
  ALTER TABLE NC_USER_GRP ADD CONSTRAINT NC_U_G_2 FOREIGN KEY (GRP_ID)
	  REFERENCES NC_APP_USER_GRP (ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table NUMBER_ASGNMT_HISTORY
--------------------------------------------------------

  ALTER TABLE NUMBER_ASGNMT_HISTORY ADD CONSTRAINT NUMBER_ASGNMT_HISTORY_FK1 FOREIGN KEY (ASSIGNED_TO)
	  REFERENCES APP_USERS (ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table NUMBER_POOL
--------------------------------------------------------

  ALTER TABLE NUMBER_POOL ADD CONSTRAINT NUMBER_POOL_ASSIGN_FK FOREIGN KEY (ASSMT_ID)
	  REFERENCES NUMBER_ASGNMT_HISTORY (ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ROLE_PRIVILEGE
--------------------------------------------------------

  ALTER TABLE ROLE_PRIVILEGE ADD CONSTRAINT PROVILEGEID_FK FOREIGN KEY (PRIVILEGE_ID)
	  REFERENCES PRIVILEGE (ID) ENABLE;
  ALTER TABLE ROLE_PRIVILEGE ADD CONSTRAINT ROLEID_FK1 FOREIGN KEY (ROLE_ID)
	  REFERENCES ROLES (ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table USER_ROLES
--------------------------------------------------------

  ALTER TABLE USER_ROLES ADD CONSTRAINT ROLEID_FK FOREIGN KEY (ROLE_ID)
	  REFERENCES ROLES (ID) ENABLE;
  ALTER TABLE USER_ROLES ADD CONSTRAINT USERID_FK FOREIGN KEY (USER_ID)
	  REFERENCES APP_USERS (ID) ENABLE;
