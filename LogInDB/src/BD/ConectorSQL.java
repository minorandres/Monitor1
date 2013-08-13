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
            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) { 
            JOptionPane.showMessageDialog(null, "No se encontro el Driver JDBC de PostgreSQL", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
        conexion = null;
        try {
            conexion = DriverManager.getConnection(
                    "jdbc:postgresql://"+ip+":"+puerto+"/postgres", usuario,
                    contrasena);
            // "jdbc:postgresql://localhost:5432/postgres", "postgres(BD)", "root");
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
        return true;
    }
     
     public String getTableSpaces() {
         String datos="";
         try {
             stmt = conexion.createStatement();             
             ResultSet resultados = stmt.executeQuery( "SELECT * FROM pg_tablespace;" );
             while ( resultados.next() ) {
                String  name = resultados.getString("spcname");
                datos+=name+",";
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
