package edu.porgamdor.util.desktop.ss;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;


public class SSRodape extends JPanel {
    private boolean iniciado = false;
    private FlowLayout layout = new FlowLayout();
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
        this.setLayout(layout);
        layout.setAlignment(FlowLayout.RIGHT);
        this.iniciado = true;
    }
    public void setAlinhamento(int alinhamento) {
    	layout.setAlignment(alinhamento);
    }
}
