/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.tools;

import constants.ItemConstants;
import constants.ServerConstants;
import gui.GUIWindow;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import tools.FileoutputUtil;
import tools.HexTool;
import tools.Pair;

/**
 *
 * @author Pungin
 */
public class ShopDecode extends javax.swing.JFrame implements GUIWindow {

    private int pos = 0;
    private long bytesRead = 0;
    private byte[] arr;

    /**
     * Creates new form BuffStatusCalculator
     */
    public ShopDecode() {
        initComponents();
    }

    public void init() {
        jTextArea1.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("基址計算器");
        setResizable(false);

        jLabel1.setText("封包");

        jButton1.setText("开始");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("清空");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(274, 274, 274)
                .addComponent(jLabel1)
                .addContainerGap(301, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String packet = jTextArea1.getText();
        pos = 0;
        bytesRead = 0;
        arr = null;
        int shopid, shopsize;
        if ((packet.isEmpty())) {
            return;
        }
        arr = HexTool.getByteArrayFromHexString(packet);
        skip(5);
        shopid = readInt();
        skip(4);
        int Rank = readByte();
        List<Pair<Integer, String>> ranks = new ArrayList<>();
        if (Rank > 0) {
            skip(1);
            for (int i = 0; i < Rank; i++) {
                ranks.get(i).left = readInt();
                ranks.get(i).right = readMapleAsciiString();
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO shops (`shopid`, `npcid`) VALUES (").append(shopid).append(", ").append(shopid).append(");\r\n\r\n");
        sb.append("INSERT INTO `shopitems` (`shopid`,`itemid`,`price`,`position`,`reqitem`,`reqitemq`,`rank`,`quantity`,`buyable`,`category`,`minLevel`,`expiration`) VALUES");
        FileoutputUtil.logToFile("商店/" + shopid + ".sql", sb + "\r\n");
        sb.delete(0, sb.length());
        shopsize = readShort();
        for (int i = 0; i < shopsize; i++) {
            if (i != shopsize - 1) {
                sb.append(LoadItem(i, shopid)).append(",\r\n");
            } else {
                sb.append(LoadItem(i, shopid)).append(";\r\n");
            }
        }
        FileoutputUtil.logToFile("商店/" + shopid + ".sql", sb + "\r\n");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        init();
        pos = 0;
        bytesRead = 0;
        arr = null;
    }//GEN-LAST:event_jButton3ActionPerformed

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BuffStatusCalculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BuffStatusCalculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BuffStatusCalculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BuffStatusCalculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ShopDecode().setVisible(true);
            }
        });
    }

    @Override
    public void setVisible(boolean bln) {
        init();
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int) (size.getWidth() - getWidth()) / 2, (int) (size.getHeight() - getHeight()) / 2);
        super.setVisible(bln);
    }

    private final String LoadItem(int slot, int shopid) {
        boolean potential = false;
        short quantity = 1;
        short buyable = 1;
        int rank = 0;
        final int itemId = readInt();
        final int price = readInt();
        skip(1);
        final int reqItem = readInt();
        final int reqItemQ = readInt();
        skip(16);
        final int expiration = readInt();
        final int minLevel = readInt();
        skip(17);
        skip(16);
        final int category = readInt();
        readMapleAsciiString();
        readMapleAsciiString();
        if (ItemConstants.类型.装备(itemId)) {
            potential = readByte() == 1;
        } else {
            potential = false;
        }
        skip(8);
        if (!ItemConstants.类型.可充值道具(itemId)) {
            quantity = readShort();
            buyable = readShort();
        } else {
            skip(10);
        }
        if (readByte() == 1) {
            //addItemInfo(mplew, i);
        }

        if (arr[pos + 16] != 0x75) {
            if (readByte() == 1) {
                rank = readByte();
            }
        }
        skip(16 + 40);
        StringBuilder sb = new StringBuilder();
        String out = "(" + shopid + "," + itemId + "," + price + "," + slot + "," + reqItem + "," + reqItemQ + "," + rank + "," + quantity + "," + buyable + "," + category + "," + minLevel + "," + expiration + ")";
        return out;
    }

    public final String readMapleAsciiString() {
        return readAsciiString(readShort());
    }

    public final String readAsciiString(final int n) {
        try {
            final byte ret[] = new byte[n];
            for (int x = 0; x < n; x++) {
                ret[x] = (byte) readByte();
            }
            return new String(ret, ServerConstants.MAPLE_TYPE.getANSI());
        } catch (UnsupportedEncodingException ex) {
            System.err.println(ex);
        }
        return "";
    }

    public int readByte() {
        return (arr[pos++]) & 0xFF;
    }

    public final short readShort() {
        final int byte1 = readByte();
        final int byte2 = readByte();
        return (short) ((byte2 << 8) + byte1);
    }

    public final int readInt() {
        final int byte1 = readByte();
        final int byte2 = readByte();
        final int byte3 = readByte();
        final int byte4 = readByte();
        return (byte4 << 24) + (byte3 << 16) + (byte2 << 8) + byte1;
    }

    public final void skip(final int num) {
        pos = getPosition() + num;
    }

    public final int getPosition() {
        return pos;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}