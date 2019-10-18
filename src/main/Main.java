package main;

import function.Client;
import function.Method;
import function.RemoteMethod;
import java.awt.Color;
import java.io.File;
import java.net.ServerSocket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Main extends javax.swing.JFrame {

    public Main() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmdStart = new javax.swing.JButton();
        cmdStop = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt = new javax.swing.JTextArea();
        lbStatus = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        bckgrnd = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SERVIDOR");
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cmdStart.setBackground(new java.awt.Color(102, 255, 102));
        cmdStart.setText("Iniciar Servidor");
        cmdStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdStartActionPerformed(evt);
            }
        });
        getContentPane().add(cmdStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, -1, -1));

        cmdStop.setBackground(new java.awt.Color(255, 153, 153));
        cmdStop.setText("Detener");
        cmdStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdStopActionPerformed(evt);
            }
        });
        getContentPane().add(cmdStop, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 11, -1, -1));

        txt.setEditable(false);
        txt.setColumns(20);
        txt.setRows(5);
        jScrollPane1.setViewportView(txt);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 330, 100));

        lbStatus.setForeground(new java.awt.Color(255, 51, 51));
        lbStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbStatus.setText("ESTADO SERVIDOR");
        lbStatus.setFocusable(false);
        getContentPane().add(lbStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 140, 23));

        jButton1.setText("X");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 140, 40, 20));

        bckgrnd.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/main/blur_bgrnd.jpg"))); // NOI18N
        bckgrnd.setEnabled(false);
        getContentPane().add(bckgrnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 350, 170));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private ServerSocket server;
    private Thread run;

    private void startServer() throws Exception {
        Method.setClients(new ArrayList<>());
        File f = new File("data");
        for (File fs : f.listFiles()) {
            fs.delete();
        }
        run = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    server = new ServerSocket(5000);
                    Registry registry = LocateRegistry.createRegistry(5001);
                    registry.bind("raven", new RemoteMethod());
                    lbStatus.setForeground(Color.GREEN);
                    Method.setTxt(txt);
                    txt.setText("Servidor iniciado\n");
                    while (true) {
                        new Client(server.accept());
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(Main.this, e, "Error", JOptionPane.ERROR_MESSAGE);
                    //e.printStackTrace();
                }
            }
        });
        run.start();
    }

    private void stopServer() throws Exception {
        int c = JOptionPane.showConfirmDialog(this, "¿Está seguro de detener el servidor?", "DETENER SERVIDOR", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (c == JOptionPane.YES_OPTION) {
            lbStatus.setForeground(new Color(255, 51, 51));
            txt.setText("Servidor detenido");
            run.interrupt();
            server.close();
        }
    }
    private void cmdStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdStartActionPerformed
        try {
            int c = JOptionPane.showConfirmDialog(this, "El archivo de datos se eliminará cuando se inicie el servidor", "INICIAR SERVIDOR", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (c == JOptionPane.YES_OPTION) {
                startServer();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_cmdStartActionPerformed

    private void cmdStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdStopActionPerformed
        try {
            stopServer();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_cmdStopActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.exit(0);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bckgrnd;
    private javax.swing.JButton cmdStart;
    private javax.swing.JButton cmdStop;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbStatus;
    private javax.swing.JTextArea txt;
    // End of variables declaration//GEN-END:variables
}
