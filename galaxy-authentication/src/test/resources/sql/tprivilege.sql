
drop table if exists TPRIVILEGE;

CREATE TABLE PUBLIC.TPRIVILEGE (
	C_PRIVILEGEID VARCHAR(100),
	C_PRIVILEGECODE VARCHAR(100),
	C_PRIVILEGENAME VARCHAR(100),
	C_PRIVILEGEFULLCODE VARCHAR(100),
	C_PARENTFULLCODE VARCHAR(100),
	C_PRIVILEGETYPE VARCHAR(100),
	C_PRIVILEGEURL VARCHAR(100),
	C_ICON VARCHAR(100),
	L_SORTID INTEGER,
	C_PRIVILEGEDESC VARCHAR(100),
	C_CREATEDBYID VARCHAR(100),
	D_CREATEDTIME DATE,
	C_LASTMODIFIEDBYID VARCHAR(100),
	D_LASTMODIFIEDTIME DATE
);