/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import BD.ConectorSQL;
import Modelo.LogIn;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;
/**
 *
 * @author casa
 */
public class Conector extends javax.swing.JFrame implements Observer{

    /**
     * 
     * Creates new form Conector
     */
    public Conector() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        ajustarComponentes();
        login=login.obtenerInstancia(); 
        login.addObserver(this);
        setDatosArchivo();       
    }

    public void ajustarComponentes() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SalirPrograma();
            }
        });
        initComponents();
    }

    public void SalirPrograma() {
        if ((JOptionPane.showConfirmDialog(this,
                "¿Realmente desea salir de la aplicación?", "Salir del Monitor 1 de Bases de Datos",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE)
                == JOptionPane.YES_OPTION)) {
            System.exit(0);
        }
    }

    
    //para poner los datos que han sido recordados
    public void setDatosArchivo(){     
       String[] valores= login.getDatos();
          if(valores!=null){
            this.fieldUsuario.setText(valores[0]);
            this.fieldContrasena.setText(valores[1]);
            this.fieldPuerto.setText(valores[2]);
            this.fieldIP.setText(valores[3]);
            this.checkRecordar.setSelected(true);
         } 
    }
    
    // llamado cuando se presiona boton conectar
      
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        fieldUsuario = new javax.swing.JTextField();
        fieldPuerto = new javax.swing.JTextField();
        fieldIP = new javax.swing.JTextField();
        botonConectar = new javax.swing.JButton();
        checkRecordar = new javax.swing.JCheckBox();
        fieldContrasena = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/postg.png"))); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/hdr_right.png"))); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/bd.jpg"))); // NOI18N

        jLabel4.setText("Usuario:");

        jLabel5.setText("Contraseña:");

        jLabel6.setText("Puerto:");

        jLabel7.setText("IP:");

        fieldUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldUsuarioActionPerformed(evt);
            }
        });
        fieldUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fieldUsuarioFocusGained(evt);
            }
        });

        fieldPuerto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldPuertoActionPerformed(evt);
            }
        });

        fieldIP.setText("localhost");
        fieldIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldIPActionPerformed(evt);
            }
        });

        botonConectar.setText("Conectar");
        botonConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonConectarActionPerformed(evt);
            }
        });

        checkRecordar.setText("Recordar");
        checkRecordar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                checkRecordarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                checkRecordarFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(checkRecordar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botonConectar))
                    .addComponent(fieldUsuario)
                    .addComponent(fieldPuerto)
                    .addComponent(fieldIP)
                    .addComponent(fieldContrasena))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(0, 10, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(fieldUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(fieldContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(fieldPuerto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(fieldIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(checkRecordar)
                            .addComponent(botonConectar))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    private void fieldUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldUsuarioActionPerformed

    private void fieldPuertoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldPuertoActionPerformed
//         TODO add your handling code here:
    }//GEN-LAST:event_fieldPuertoActionPerformed

    private void fieldIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldIPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldIPActionPerformed

    private void botonConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonConectarActionPerformed
            if(conectorBD.conectar(this.fieldUsuario.getText(),
                this.fieldContrasena.getText(),this.fieldPuerto.getText(),
                this.fieldIP.getText())){
                    if(this.checkRecordar.isSelected()){
                        login.escritor(recordarEstosDatos());
                        login.setValores(this.fieldUsuario.getText(), this.fieldContrasena.getText(),
                this.fieldPuerto.getText(), this.fieldIP.getText());
                    }
                ventanaPrincipal=ventanaPrincipal.obtenerInstancia();
                this.setVisible(false);
                ventanaPrincipal.setVisible(true);
            }
              
    }//GEN-LAST:event_botonConectarActionPerformed

    private void fieldUsuarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fieldUsuarioFocusGained
        
    }//GEN-LAST:event_fieldUsuarioFocusGained

    private void checkRecordarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_checkRecordarFocusGained

    }//GEN-LAST:event_checkRecordarFocusGained

    private void checkRecordarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_checkRecordarFocusLost
 
    }//GEN-LAST:event_checkRecordarFocusLost

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Conector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Conector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Conector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Conector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Conector().setVisible(true);                
            }
        });
    }
    
   
    public static ConectorSQL conectorBD=new ConectorSQL();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonConectar;
    private javax.swing.JCheckBox checkRecordar;
    private javax.swing.JPasswordField fieldContrasena;
    private javax.swing.JTextField fieldIP;
    private javax.swing.JTextField fieldPuerto;
    private javax.swing.JTextField fieldUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
    public VentanaPrincipal ventanaPrincipal; 
    public LogIn login;
    
    private String recordarEstosDatos() {
        return this.fieldUsuario.getText()+","+
               this.fieldContrasena.getText()+","+
               this.fieldPuerto.getText()+","+
               this.fieldIP.getText();
    }

    @Override
    public void update(Observable o, Object arg) {         
         String[] valores= login.getDatos();
          if(valores!=null){
            this.fieldUsuario.setText(valores[0]);
            this.fieldContrasena.setText(valores[1]);
            this.fieldPuerto.setText(valores[2]);
            this.fieldIP.setText(valores[3]);
            this.checkRecordar.setSelected(true);
        }
       
    }
}


