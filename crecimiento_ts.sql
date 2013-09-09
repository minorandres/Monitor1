	CREATE TABLE LLENADO(
	TABLESPACE VARCHAR2(500),
	DIAS NUMBER
	);
	
CREATE TABLE TAM_MAX_TABLA(
	TABLA VARCHAR2(500),
	TAM NUMBER,
	TABLESPACE VARCHAR2(500)
	);	
	
	-- total registros en X tabla en la ultima fecha registrada-- debe ser ejecutado despues de registrar(recoleccion datos)
CREATE OR REPLACE PROCEDURE CALC_NUM_COLS(nombre_tabla IN VARCHAR2,num_cols OUT NUMBER)
	IS	
	BEGIN
		select total_registros INTO num_cols 
		from (select total_registros 
				from registros WHERE tabla=nombre_tabla order by fecha desc)
				where rownum=1;		
	END;
/

CREATE OR REPLACE PROCEDURE	CALC_TABLESPACE(num_cols IN NUMBER,ESPACIO IN VARCHAR2)
	IS	
		suma NUMBER;
		query VARCHAR2(500);
	BEGIN
		SELECT SUM(tam)/num_cols INTO suma FROM TAM_MAX_TABLA WHERE TABLESPACE=ESPACIO;
		dbms_output.put_line(suma||espacio);
		query :='INSERT INTO LLENADO('''||ESPACIO||''','''||SUMA||''')';
		EXECUTE IMMEDIATE query;		
	END;
/


CREATE OR REPLACE PROCEDURE	CALC_DIAS_LLENADO(espacio_libre IN NUMBER,numero_a IN NUMBER,numero_b IN NUMBER,prom_crec_tabla IN NUMBER ,dias_lleno OUT NUMBER)
	IS	
		suma NUMBER;
	BEGIN
		suma:= (espacio_libre+numero_b)/numero_a;
		dias_lleno:=suma/prom_crec_tabla;
	END;
/

CREATE OR REPLACE PROCEDURE CALC_VARIANZA(prom_gente IN NUMBER,num_cols IN NUMBER,la_tabla IN VARCHAR2,varianza OUT NUMBER)
	IS	
		suma NUMBER;
	BEGIN
		select sum((total_registros-prom_gente)*(total_registros-prom_gente)) INTO suma
						from registros  where tabla=la_tabla;
		varianza:=suma/num_cols;
	END;
/

CREATE OR REPLACE PROCEDURE CALC_A(prom_gente IN NUMBER,prom_mb IN NUMBER,num_cols IN NUMBER,la_tabla IN VARCHAR2,varianza IN NUMBER,numero_a OUT NUMBER)
	IS
		suma NUMBER;
	BEGIN
		SELECT SUM((TOTAL_REGISTROS-prom_gente)*(TAMANIO_TOTAL_MB-prom_mb)) INTO suma 
		FROM REGISTROS WHERE TABLA=la_tabla;
		numero_a:=suma/(num_cols*varianza);
	END;
/

CREATE OR REPLACE PROCEDURE CALC_B(prom_gente IN NUMBER,prom_mb IN NUMBER,numero_a IN NUMBER,numero_b OUT NUMBER)
	IS
	BEGIN
		numero_b:=prom_mb-(numero_a*prom_gente);
	END;
/


/* debe ejecutarse despues de haber hecho el analisis en registrar*/
CREATE OR REPLACE PROCEDURE TIEMPO_LLENADO
	IS
		num_cols NUMBER;
		prom_mb NUMBER;
		prom_gente NUMBER;
		varianza NUMBER;
		numero_a NUMBER;
		numero_b NUMBER;
		prom_crec_tabla NUMBER;
		dias_lleno NUMBER;
		query VARCHAR2(500);
	BEGIN	
		FOR espacio_tabla IN( select df.tablespace_name as Tablespace,(df.totalspace - tu.totalusedspace) as Free_MB,
								df.totalspace as Total_MB from (select tablespace_name,(sum(bytes) / 1048576) TotalSpace
																from dba_data_files group by tablespace_name) df,
																(select (sum(bytes)/(1024*1024)) totalusedspace, 
																tablespace_name from dba_segments group by tablespace_name) tu
																where df.tablespace_name = tu.tablespace_name )
		LOOP
		--FOR CADA TABLA en tablespace
			FOR elemento IN (select r1.tabla as tabla,sum(r1.tamanio_total_mb) as sum_mb,SUM(r1.total_registros)as  sum_gente 
							from registros r1,registros	r2 
							where r1.tabla=r2.tabla and r1.tablespace=espacio_tabla.Tablespace group by r1.tabla)
			LOOP
					CALC_NUM_COLS(elemento.tabla,num_cols);
					prom_mb:=elemento.sum_mb/num_cols;
					prom_gente:=elemento.sum_gente/num_cols;
					CALC_VARIANZA(prom_gente,num_cols,elemento.tabla,varianza);
					CALC_A(prom_gente,prom_mb,num_cols,elemento.tabla,varianza,numero_a);
					CALC_B(prom_gente,prom_mb,numero_a,numero_b);
					select (sum(nuevos_registros)/num_cols) into prom_crec_tabla 
							from registros where tabla=elemento.tabla AND TABLESPACE=espacio_tabla.Tablespace;
					CALC_DIAS_LLENADO(espacio_tabla.Free_MB,numero_a,numero_b,prom_crec_tabla,dias_lleno);
					query := 'INSERT INTO TAM_MAX_TABLA VALUES
								('''||elemento.tabla||''','''||dias_lleno||''','''||espacio_tabla.Tablespace||''')';
					EXECUTE IMMEDIATE query;					
			END LOOP;
			CALC_TABLESPACE(num_cols,espacio_tabla.Tablespace);
		END LOOP;
	END;
	/


