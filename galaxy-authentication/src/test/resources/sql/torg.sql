
drop table if exists TORG;

CREATE TABLE TORG (
	C_ORGID VARCHAR(100),
	C_ORGNAME VARCHAR(100),
	C_ORGCODE VARCHAR(100),
	C_ORGFULLCODE VARCHAR(100),
	C_PARENTFULLCODE VARCHAR(100),
	C_ORGTYPE VARCHAR(100),
	C_CREATEDBYID VARCHAR(100),
	D_CREATEDTIME DATE,
	C_LASTMODIFIEDBYID VARCHAR(100),
	D_LASTMODIFIEDTIME DATE
);
