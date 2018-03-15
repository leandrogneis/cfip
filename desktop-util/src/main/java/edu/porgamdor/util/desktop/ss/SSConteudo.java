package edu.porgamdor.util.desktop.ss;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class SSConteudo extends JPanel {    
    private GridBagLayout gridBagLayout1 = new GridBagLayout();

    public SSConteudo() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(gridBagLayout1);
        this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));                
    }    
    
}
