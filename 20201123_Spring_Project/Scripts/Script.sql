-- 회원정보
CREATE TABLE "MEMBER"(
	ID VARCHAR2(20) NOT NULL,
	PW VARCHAR2(25) NOT NULL,
	AUTH CHAR(1) NOT NULL,
	DELFLAG CHAR(1) NOT NULL,
	REGDATE DATE NOT NULL,
	CONSTRAINT "MEMBER_CK" CHECK ("AUTH" IN('U','A')),
	CONSTRAINT "MEMBER_PK" PRIMARY KEY("ID")
);

-- 답변형 게시판
CREATE TABLE ANSWERBOARD(
	SEQ NUMBER NOT NULL,
	ID VARCHAR2(20) NOT NULL,
	TITLE VARCHAR2(50) NOT NULL,
	CONTENT VARCHAR2(4000),
	REFER NUMBER NOT NULL,
	STEP NUMBER NOT NULL,
	DEPTH NUMBER NOT NULL,
	READCOUNT NUMBER NOT NULL,
	DELFLAG CHAR(1) NOT NULL,
	REGDATE DATE NOT NULL,
	CONSTRAINT "ANSWERBOARD_PK" PRIMARY KEY("SEQ"),
	CONSTRAINT "ANSWERBOARD_FK" FOREIGN KEY("ID") REFERENCES "MEMBER" ("ID")
);

CREATE SEQUENCE ANSWERBOARD_SEQ START WITH 1 INCREMENT BY 1;

--MEMBER에 NAME 컬럼 추가
ALTER TABLE MEMBER ADD NAME VARCHAR2(20);


-- TGW101
SELECT ID, NAME, AUTH, DELFLAG , REGDATE 
	 FROM "MEMBER" m ;
	
-- TGW102
INSERT INTO "MEMBER" (ID,PW,NAME,AUTH,DELFLAG,REGDATE)
	VALUES('USER02','USER02','사용자02','U','Y',SYSDATE);

INSERT INTO "MEMBER" (ID,PW,NAME,AUTH,DELFLAG,REGDATE)
	VALUES('ADMIN01','ADMIN01','관리자01','A','N',SYSDATE);

-- TGW103
SELECT COUNT(ID) ID
	FROM "MEMBER" m 
		WHERE ID = 'USER03';

-- TGW104
SELECT ID, NAME, AUTH, REGDATE 
	FROM "MEMBER" m 
		WHERE ID='USER02'
			AND PW='USER02'
			AND DELFLAG ='N';

-- TGW105
SELECT PW
	FROM "MEMBER" m 
	WHERE ID='USER01';

--TGW201
INSERT INTO ANSWERBOARD
(SEQ, ID, TITLE, 
CONTENT, REFER, STEP, 
"DEPTH", READCOUNT, DELFLAG, 
REGDATE)
VALUES(ANSWERBOARD_SEQ.NEXTVAL, 'USER01', '사용자 글 입력', 
'글 입력 테스트', (SELECT NVL(MAX(REFER),0)+1 FROM ANSWERBOARD a), 0, 
0, 0, 'N',
SYSDATE);

SELECT COUNT(*) FROM ANSWERBOARD a ;

--TGW202
UPDATE ANSWERBOARD SET STEP = STEP +1
	WHERE STEP > (SELECT STEP FROM ANSWERBOARD a WHERE SEQ = '1')
		AND REFER = (SELECT REFER FROM ANSWERBOARD a2 WHERE SEQ='1');
--TGW203
INSERT INTO ANSWERBOARD
(SEQ, ID, TITLE, 
CONTENT,
REFER,
STEP, 
"DEPTH",
READCOUNT, DELFLAG, 
REGDATE)
VALUES
(
ANSWERBOARD_SEQ.NEXTVAL, 'ADMIN01', '관리자 답글 입력',
'관리자가 답글을 입력하는 테스트', 
(SELECT REFER FROM ANSWERBOARD a WHERE SEQ='1'),
(SELECT STEP FROM ANSWERBOARD a2 WHERE SEQ='1')+1,
(SELECT "DEPTH" FROM ANSWERBOARD a3 WHERE SEQ='1')+1,
0, 'N',
SYSDATE 
);

--TGW204
SELECT SEQ, ID, TITLE, CONTENT, REFER, STEP,"DEPTH", READCOUNT, DELFLAG, REGDATE
	FROM ANSWERBOARD a 
		WHERE SEQ ='3';

-- TGW205
UPDATE ANSWERBOARD SET READCOUNT = READCOUNT +1
	WHERE SEQ ='3';

-- TGW206
UPDATE ANSWERBOARD 
	SET TITLE ='사용자 수정 입력', CONTENT ='사용자 수업 글 내용 입력'
		WHERE SEQ ='1';


--TGW207 (foreach : Map)
UPDATE ANSWERBOARD SET DELFLAG ='Y'
 WHERE DELFLAG ='N'
 	AND SEQ='3';

--TGW208
SELECT SEQ, ID, TITLE, CONTENT, REFER, STEP,"DEPTH", READCOUNT, DELFLAG, REGDATE
	FROM ANSWERBOARD a 
		WHERE REFER = (SELECT REFER FROM ANSWERBOARD a2 WHERE SEQ='1')
			AND STEP >= (SELECT STEP FROM ANSWERBOARD a3 WHERE SEQ='1')
			AND "DEPTH" >= (SELECT "DEPTH" FROM ANSWERBOARD a4 WHERE SEQ='1') 
	ORDER BY REFER DESC, STEP;

-- TGW209(foreach : Map)
DELETE FROM ANSWERBOARD a 
	WHERE SEQ='2';

-- TGW210
SELECT SEQ, ID, TITLE, CONTENT, REFER, STEP,"DEPTH", READCOUNT, DELFLAG, REGDATE
	FROM ANSWERBOARD a 
		WHERE DELFLAG ='N'
		ORDER BY REFER DESC, STEP;

-- TGW211
SELECT SEQ, ID, TITLE, CONTENT, REFER, STEP,"DEPTH", READCOUNT, DELFLAG, REGDATE
	FROM ANSWERBOARD a 
		ORDER BY REFER DESC, STEP;
	
-- TGW212
SELECT SEQ, ID, TITLE, CONTENT, REFER, STEP,"DEPTH", READCOUNT, DELFLAG, REGDATE
	FROM(
		SELECT ROWNUM RN , S1.*
			FROM 
				(SELECT SEQ, ID, TITLE, CONTENT, REFER, STEP,"DEPTH", READCOUNT, DELFLAG, REGDATE
				FROM ANSWERBOARD a 
					WHERE DELFLAG ='N'
					ORDER BY REFER DESC, STEP) S1
		)
	WHERE RN BETWEEN 1 AND 10;
	
--TGW213
SELECT SEQ, ID, TITLE, CONTENT, REFER, STEP,"DEPTH", READCOUNT, DELFLAG, REGDATE
	FROM(
	SELECT ROW_NUMBER () OVER(ORDER BY REFER DESC , STEP) RN
		,SEQ, ID, TITLE, CONTENT, REFER, STEP,"DEPTH", READCOUNT, DELFLAG, REGDATE
				FROM ANSWERBOARD a 
					WHERE DELFLAG ='N')
	 WHERE RN BETWEEN 1 AND 10;

-- TGW214
SELECT COUNT(*)
	FROM ANSWERBOARD a 
	WHERE DELFLAG ='N';
	
-- TGW215
SELECT COUNT(*)
	FROM ANSWERBOARD a ;
	
----- 보충내용 -----
SELECT *
	FROM "MEMBER" m 
--		WHERE ID LIKE '%' || 'U' || '%'; -- '%' || #{ID} || '%' 
		WHERE ID LIKE CONCAT(CONCAT('%','U'),'%'); 
		
SELECT ID, NAME, AUTH, DELFLAG ,TO_CHAR(REGDATE,'YYYY.MM.DD')
	FROM "MEMBER" m2 ;

-- 비밀번호 암호화를 사용하기 위한 컬럼의 크기를 변환
ALTER TABLE "MEMBER" MODIFY (PW VARCHAR2(2000));




