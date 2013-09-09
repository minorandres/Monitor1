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