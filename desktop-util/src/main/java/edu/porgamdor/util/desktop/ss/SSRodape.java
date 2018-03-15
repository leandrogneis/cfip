package edu.porgamdor.util.desktop.ss;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;


public class SSRodape extends JPanel {
    private boolean iniciado = false;
    private FlowLayout flowLayout1 = new FlowLayout();

    public SSRodape() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(430, 41));
        this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        this.setLayout(flowLayout1);
        flowLayout1.setAlignment(2);
        this.iniciado = true;
    }
    
}
