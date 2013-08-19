SELECT d.datname AS Name,  pg_catalog.pg_get_userbyid(d.datdba) AS Owner,
    CASE WHEN pg_catalog.has_database_privilege(d.datname, 'CONNECT')
        THEN pg_catalog.pg_size_pretty(pg_catalog.pg_database_size(d.datname))
        ELSE 'No Access'
    END AS Size
FROM pg_catalog.pg_database d
    ORDER BY
    CASE WHEN pg_catalog.has_database_privilege(d.datname, 'CONNECT')
        THEN pg_catalog.pg_database_size(d.datname)
        ELSE NULL
    END DESC -- nulls first
    LIMIT 20;
	
	
select datname,pg_catalog.pg_size_pretty(pg_catalog.pg_database_size(datname)) from pg_catalog.pg_database;

  datname  | pg_size_pretty
-----------+----------------
 template1 | 6185 kB
 template0 | 6185 kB
 postgres  | 6282 kB
 maynor    | 6298 kB
(4 filas)

SELECT d.datname as "Name",
       r.rolname as "Owner",
       pg_catalog.pg_encoding_to_char(d.encoding) as "Encoding",
       pg_catalog.shobj_description(d.oid, 'pg_database') as "Description",
       t.spcname as "Tablespace"
FROM pg_catalog.pg_database d
  JOIN pg_catalog.pg_roles r ON d.datdba = r.oid
  JOIN pg_catalog.pg_tablespace t on d.dattablespace = t.oid
ORDER BY 1;
**************************

                           List of databases
   Name    | Owner  | Encoding |        Description        | Tablespace
-----------+--------+----------+---------------------------+------------
 postgres  | pyarra | LATIN1   |                           | pg_default
 pyarra    | pyarra | LATIN1   |                           | pg_default
 spctest   | pyarra | LATIN1   |                           | spctable
 template0 | pyarra | LATIN1   |                           | pg_default
 template1 | pyarra | LATIN1   | Default template database | pg_default
 
 
 SELECT
    table_name,
    pg_size_pretty(table_size) AS table_size
FROM (
    SELECT
        table_name,
        pg_table_size(table_name) AS table_size
    FROM (
        SELECT ('"' || table_schema || '"."' || table_name || '"') AS table_name
        FROM information_schema.tables
    ) AS all_tables
) AS pretty_sizes


maynor=# select * from information_schema.tables;
 table_catalog |    table_schema    |              table_name               | table_type | self_referencing_column_name | reference_generation | user_defined_type_catalog | user_defined_type_schema | user_defined_type_name | is_insertable_into | is_typed | commit_action

 maynor        | pg_catalog         | pg_statistic                          | BASE TABLE |                              |                      |      				       |                          |                        | YES 				| NO       |
 maynor        | pg_catalog         | pg_type                               | BASE TABLE |                              |                      |         				   |                          |                        | YES  			    | NO       |
 maynor        | public             | persona                               | BASE TABLE |                              |                      |       				       |                          |                        | YES   			    | NO       |
 
 
  copy(
 SELECT schema, name,
   pg_size_pretty(s) AS size,
   pg_size_pretty(st - s) AS index,
   (100.0 * s / NULLIF(st, 0))::numeric(10,1) AS "% data of total",
   st AS total
 FROM (
   SELECT n.nspname AS schema,
          c.relname AS name,
          pg_relation_size(c.oid) AS s,
          pg_total_relation_size(c.oid) AS st
   FROM pg_class c, pg_namespace n
   WHERE c.relnamespace = n.oid 
 ) as query where schema='public'
 ORDER BY st DESC
 )to e'D:\\Maynor\\try.sql';
 
 
 public	musica	8192 bytes	8192 bytes	50.0	16384
 
 --ESTAs 2 LINEAs ME DA LA CANT DE TUPLAS EN CADA TABLA
 copy(SELECT relname, reltuples, relpages * 8 / 1024 AS "MB" FROM pg_class ORDER BY reltuples DESC )to e'D:\\Maynor\\try.sql';
 VACUUM;

 
 -- musica 60 tuplas
 
  schema |  name   |    size    | index | % data of total | total
--------+---------+------------+-------+-----------------+-------
 public | musica  | 8192 bytes | 40 kB |            16.7 | 49152
(2 filas)


maynor=# vacuum;
--musica 0 tuplas
 schema |  name   |    size    | index | % data of total | total
--------+---------+------------+-------+-----------------+-------
 public | musica  | 0 bytes    | 24 kB |             0.0 | 24576
(2 filas)

--musica 1 tupla
 schema |  name   |    size    | index | % data of total | total
--------+---------+------------+-------+-----------------+-------
 public | musica  | 8192 bytes | 40 kB |            16.7 | 49152
 
 --musica 241 tuplas
  schema |  name   |    size    | index | % data of total | total
--------+---------+------------+-------+-----------------+-------
 public | musica  | 16 kB      | 40 kB |            28.6 | 57344
 
 