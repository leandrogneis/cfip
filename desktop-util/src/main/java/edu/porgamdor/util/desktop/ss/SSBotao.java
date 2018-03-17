package edu.porgamdor.util.desktop.ss;

import java.awt.Dimension;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import edu.porgamdor.util.desktop.ss.util.Imagem;


public class SSBotao extends JButton {
    private String icone;
    private boolean mostrarIcone = true;
    private boolean padrao;
    
    public SSBotao() {
        super();
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(118, 36));
        this.setText("Ação");
        this.addAncestorListener(new AncestorListener() {
                    public void ancestorAdded(AncestorEvent e) {
                        this_ancestorAdded(e);
                    }

                    public void ancestorRemoved(AncestorEvent e) {
                    }

                    public void ancestorMoved(AncestorEvent e) {
                    }
                });
    }

    public void setIcone(String icone) {
        this.icone = icone;        
        ImageIcon imagem = null;
        if (icone != null && (!icone.equals("Ação"))) {
            /*URL url = getClass().getResource("imagens/" + icone + ".png");
            if (url != null)
                imagem = new ImageIcon(url);*/
        	try {
        		imagem=Imagem.png(icone.toLowerCase());
        	}catch (NullPointerException e) {
				// TODO: handle exception
			}
        }
        setIcon(imagem);
    }

    public String getIcone() {
        return icone;
    }

    public void setMostrarIcone(boolean mostrarIcone) {
        this.mostrarIcone = mostrarIcone;
    }

    public boolean isMostrarIcone() {
        return mostrarIcone;
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        if (mostrarIcone && text != null)
            setIcone(text.toLowerCase());
    }

    public void setPadrao(boolean padrao) {
        this.padrao = padrao;
        if (this.getRootPane() != null && padrao)
            this.getRootPane().setDefaultButton(this);
    }

    public boolean isPadrao() {
        return padrao;
    }

    private void this_ancestorAdded(AncestorEvent e) {
        this.setPadrao(this.isPadrao());
    }
}
