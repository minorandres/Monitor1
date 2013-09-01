/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author casa
 */
public class ConectorSQL {
    
     Connection conexion = null;
     Statement stmt = null;
     
     public boolean conectar(String usuario,String contrasena,String puerto,String ip) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException e) { 
            JOptionPane.showMessageDialog(null, "No se encontro el Driver JDBC de PostgreSQL", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
        conexion = null;
        try {
            conexion = DriverManager.getConnection(
                    "jdbc:oracle:thin:@//"+ip+":"+puerto+"/"+"XE",usuario,
                    contrasena);
            // "jdbc:oracle:thin:@//localhost:1521/XE", "system", "root");
            conexion.setAutoCommit(false);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Fallo la conexion, revise los datos", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (conexion == null) {
            JOptionPane.showMessageDialog(null, "Fallo la conexion, revise los datos", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else{            
            System.out.println("Conexion BD exitosa");getTableSpaces();
        }
        getAllObjectsFromTableSpace();
        return true;
    }
     
     public String getTableSpaces() {
         String datos="";
         try {// get tables=  select * from pg_tables where tablespace = 'tablespace1';
             stmt = conexion.createStatement();             
             ResultSet resultados = stmt.executeQuery( ""
              + "select  spcname,pg_size_pretty(pg_tablespace_size(spcname)) "
                     + "from pg_tablespace;");
             while ( resultados.next() ) {
                String  name = resultados.getString("spcname");
                String size= resultados.getString("pg_size_pretty");
                datos+=name+","+size+"\n";
             }
             resultados.close();
             stmt.close();
            // conexion.close();
         } catch (SQLException ex) {
             Logger.getLogger(ConectorSQL.class.getName()).log(Level.SEVERE, null, ex);
         }      
             return datos;
     }
     
     public String getAllObjectsFromTableSpace(){ 
         String datos="";       
         try {
             conexion.setAutoCommit(false);
             stmt = conexion.createStatement();
             ResultSet resultados = stmt.executeQuery( 
               "SELECT datafile.tablespace_name chr(39)||'TableSpace'||chr(39),"+
                "usado chr(39)||'usado'||chr(39),"+
		"(datafile.total - t.usado) chr(39)'||libre'||chr(39),"+
                     "datafile.total chr(39)||'total'||chr(39),"+
            "(100*((datafile.total - t.usado)/datafile.total)) chr(39)||'% libre'||chr(39)"+ 
		 " FROM (SELECT tablespace_name,(SUM(bytes)/1048576) total"+
        " FROM dba_data_files GROUP BY tablespace_name) datafile,"+
		"(SELECT (SUM(bytes)/(1048576)) usado,tablespace_name"+
		" FROM dba_segments GROUP BY tablespace_name) t"+
		" WHERE datafile.tablespace_name=t.tablespace_name;"                   
                     );
             while ( resultados.next() ) {
                String  TableSpace = resultados.getString("TableSpace");
                String usado= resultados.getString("usado");
                String libre= resultados.getString("libre");
                String total= resultados.getString("total");
                String plibre= resultados.getString("% libre");
                datos+=TableSpace+","+usado+"\n";
             }
             resultados.close();
             stmt.close();
            // conexion.close();
         } catch (SQLException ex) {
             Logger.getLogger(ConectorSQL.class.getName()).log(Level.SEVERE, null, ex);
         }         
             return datos;     
     }
}
