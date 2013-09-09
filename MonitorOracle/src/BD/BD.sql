set serveroutput on;	
 
 --algunas tablas tienen el num_rows vacio que es necesario para hacer la estimacion de crecimiento, este proceso hace que aparezcan  
CREATE OR REPLACE PROCEDURE DESBLOQUEAR_ESTADISTICAS
	IS 	
	begin
		FOR e IN(select DISTINCT owner from dba_tab_statistics where stattype_locked is not null)
		LOOP
			dbms_stats.unlock_schema_stats(ownname => e.owner);
		END LOOP;	
		dbms_utility.analyze_database('COMPUTE');--dura como 2 min
	END;
/

CREATE TABLE REGISTROS(
	tabla varchar2(500),
	tablespace varchar2(500),
	fecha DATE,
	total_registros NUMBER,
	tamanio_total_mb NUMBER,
	nuevos_registros NUMBER,
	CONSTRAINT  PKREGISTROS PRIMARY KEY (tabla,tablespace,fecha)
	);

-- CONSULTA PARA OBTENER DATOS: SELECT * FROM REGISTROS ORDER BY TABLA,FECHA;

CREATE TABLE ERROR(
    DESCRIPCION VARCHAR2(1000);
);

CREATE OR REPLACE PROCEDURE NUEVOS_INDIVIDUOS(actual_total IN NUMBER,nombre_tabla IN VARCHAR2,nuevos OUT NUMBER)
	IS
		anterior_total NUMBER;
		query VARCHAR2(1000);
	BEGIN
		-- obtiener total_registros de la ultima fecha registrada en tabla(nombre_tabla)(anterior)
		select total_registros INTO anterior_total 
		from (select total_registros 
				from registros WHERE tabla=nombre_tabla order by fecha desc)
				where rownum=1;
		--obteniendo total de nuevos registros
		nuevos:= actual_total-anterior_total;--(RETURN)
		EXCEPTION
			WHEN no_data_found THEN--- si es el primer registro
				nuevos:= actual_total;--(RETURN)
			WHEN OTHERS THEN
				query:='INSERT INTO ERROR VALUES(''ERROR DATA NOT FOUND ON SYS.NUEVOS_INDIVIDUOS PROC-->'||nombre_tabla||''')';
				EXECUTE IMMEDIATE query;
				nuevos:= actual_total;				
	END NUEVOS_INDIVIDUOS;
/

--dura alrededor de 5 minutos
CREATE OR REPLACE PROCEDURE REGISTRAR	
	IS
		 sql_str VARCHAR2(1000);
		 tam NUMBER;
		 gente_act NUMBER;
		 nuevos_reg NUMBER;
	BEGIN
		gente_act:=0;
		DESBLOQUEAR_ESTADISTICAS;
		FOR tablespace IN (SELECT NAME from V$TABLESPACE)	
		LOOP
			--FOR TODAS LAS TABLAS EN EL TABLESPACE, HAY QUE HACER UNION CON ALL_TABLES PORQUE LOS EXTENTS PUEDEN SER OTRAS COSAS ADEMAS DE TABLAS
			FOR tabla IN (SELECT SEGMENT_NAME,SUM(BYTES)/1024/1024 FROM ALL_TABLES,DBA_EXTENTS WHERE ALL_TABLES.TABLESPACE_NAME=TABLESPACE.NAME AND SEGMENT_NAME=TABLE_NAME GROUP BY SEGMENT_NAME)
			LOOP
				---total individuos
				FOR e IN (select distinct num_rows from all_tables where table_name=TABLA.SEGMENT_NAME)
				LOOP
					gente_act:=e.num_rows;
				END LOOP;				
				--tamanio
				SELECT SUM(BYTES)/1024/1024 INTO tam FROM DBA_EXTENTS WHERE TABLESPACE_NAME = tablespace.name  AND segment_name=tabla.SEGMENT_NAME GROUP BY SEGMENT_NAME;--tamanio
				--proc devuelve el valor de nuevos reg buscando el ultimo reg existente y restando el total act y el del existente
				NUEVOS_INDIVIDUOS(gente_act,tabla.segment_name,nuevos_reg);
				--insertando todo en tabla de registro
				sql_str := 'INSERT INTO REGISTROS VALUES(
							'''||tabla.SEGMENT_NAME||
							''','''||tablespace.NAME||
							''','''||SYSDATE||''','''
							||gente_act||''','''||tam||
							''','''||nuevos_reg||''')';
				EXECUTE IMMEDIATE sql_str;
			END LOOP;
		END LOOP;
		EXCEPTION
			WHEN no_data_found THEN--- si es el primer registro
				dbms_output.put_line('ERROR DATA NOT FOUND ON SYS.REGISTRAR PROC');
	   END REGISTRAR;
 /	
 EXEC REGISTRAR;