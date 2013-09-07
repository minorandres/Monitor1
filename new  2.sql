CREATE TABLE REGISTROS(
	tabla varchar2(20),
	tablespace varchar2(20),
	fecha DATE,
	total_registros int,
	tamanio_total_mb int,
	nuevos_registros int
	);



create table a(algo varchar(100));

BEGIN
DBMS_SCHEDULER.CREATE_JOB(job_name        => 'JOB1',
                          job_type        => 'PLSQL_BLOCK',
                          JOB_ACTION      => 'BEGIN PROCESO; END;',
                          start_date      => '04-SEP-13 11.18.00PM',
                          repeat_interval => 'FREQ=MINUTELY;INTERVAL=3',
                          end_date        => NULL,
                          enabled         => TRUE,
                          comments        => 'Calls PLSQL once');
END;
/

BEGIN
DBMS_SCHEDULER.RUN_JOB (
   'yuri',TRUE);
   END;
/

exec dbms_scheduler.drop_job('yuri', TRUE);

SELECT JOB_NAME FROM DBA_SCHEDULER_JOBS;

set serveroutput on;
CREATE OR REPLACE PROCEDURE PROCESO
	IS
		 sql_str VARCHAR2(1000);
		 aaa VARCHAR2(100);
	BEGIN
		SELECT SUM(BYTES)/1024/1024 INTO AAA FROM DBA_EXTENTS WHERE TABLESPACE_NAME = 'ACADEMICO' AND segment_name='PANCHOS' GROUP BY SEGMENT_NAME;
		--FOR E IN (SELECT name from v$tablespace)
		--LOOP
			sql_str := 'INSERT INTO bonito values('''||aaa||''')';
			EXECUTE IMMEDIATE sql_str;
		--END LOOP;
	END PROCESO;
/



















































BEGIN
  DBMS_SCHEDULER.create_job (
    job_name        => 'PRUEBALA',
    job_type        => 'PLSQL_BLOCK',
    job_action      => 'BEGIN PPP; END;',
    start_date      => '4/09/13 9:59:00',
    repeat_interval => 'freq=secondly;',
    end_date        => NULL,
    enabled         => TRUE,
    comments        => 'Job defined entirely by the CREATE JOB procedure.');
END;
/

-- created the AQ code to do this is not included here
BEGIN
  dbms_scheduler.create_event_schedule(
			'TEST_EVENTS_SCHED', 
			SYSTIMESTAMP,
			event_condition => 'tab.user_data.event_type = ''ZERO_BALANCE''', 
  queue_spec => 'entry_events_q, entry_agent1');
END;
/

BEGIN
DBMS_SCHEDULER.CREATE_JOB (
   job_name             => 'my_job2',
   job_type             => 'PLSQL_BLOCK',
   job_action           => 'EXEC PPP; END;',
   start_date           => '04-SEP-13 10.16.00PM',
   repeat_interval      => 'FREQ=SECONDLY', 
   end_date             => '15-SEP-13 1.00.00AM',
   enabled              =>  TRUE,
   comments             => 'xxxxxxxxxxxxxxxxxxxxxxxxxxx');
END;
/


SELECT SEGMENT_NAME, SUM(BYTES)/1024/1024 
FROM DBA_EXTENTS 
WHERE TABLESPACE_NAME = 'ACADEMICO'
GROUP BY SEGMENT_NAME
ORDER BY 2 DESC;


SELECT SEGMENT_NAME, (SUM(BYTES)/1024/1024) tam
FROM DBA_EXTENTS
WHERE TABLESPACE_NAME = 'ACADEMICO'
AND segment_name='PANCHOS'
GROUP BY SEGMENT_NAME;

select count(*) 
from user_tab_columns
where table_name='BONITO'


CREATE OR REPLACE PROCEDURE REGISTRAR	
	IS
		 sql_str VARCHAR2(1000);
		 auxiliar VARCHAR2(100);
		 auxnum int;
		 fecha DATE;
	BEGIN
		FOR tablespace IN (SELECT NAME from V$TABLESPACE)	
		LOOP
			FOR tabla IN (select SEGMENT_NAME,SUM(BYTES)/1024/1024 from dba_EXTENTS where TABLESPACE_NAME=tablespace.NAME)
			LOOP
				SELECT COUNT(*) FROM tabla.SEGMENT_NAME;
				SELECT SUM(BYTES)/1024/1024 INTO auxiliar FROM DBA_EXTENTS WHERE TABLESPACE_NAME = tablespace.name  AND segment_name=tabla.SEGMENT_NAME GROUP BY SEGMENT_NAME;
				sql_str := 'INSERT INTO REGISTRO VALUES('''||tabla.SEGMENT_NAME||''','''tablespace.NAME||''','''||SYSDATE||''','''||
				EXECUTE IMMEDIATE sql_str;
			END LOOP;
		END LOOP;
	   END REGISTRAR;
 /	

bysecond_clause = "BYSECOND" "=" second_list
   second_list = second ( "," second)*
   second = 0 through 59
   
   