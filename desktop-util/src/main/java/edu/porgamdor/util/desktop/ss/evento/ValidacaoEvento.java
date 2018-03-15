package edu.porgamdor.util.desktop.ss.evento;

import java.awt.AWTEvent;


public class ValidacaoEvento extends AWTEvent {    
    private boolean validado = true;
    
    public ValidacaoEvento(Object source) {
        super(source, 200803);        
    }

    public void setValidado(boolean validado) {
        this.validado = validado;
    }

    public boolean isValidado() {
        return validado;
    }
}
