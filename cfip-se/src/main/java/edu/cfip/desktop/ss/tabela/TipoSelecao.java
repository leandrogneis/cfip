package edu.cfip.desktop.ss.tabela;

import javax.swing.ListSelectionModel;

public enum TipoSelecao {
    SELECAO_UNICA (ListSelectionModel.SINGLE_SELECTION),    
    SELECAO_MULTIPLA (ListSelectionModel.MULTIPLE_INTERVAL_SELECTION),
    SELECAO_INTERVALO (ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    
    private int codigo;
    
    TipoSelecao (int codigo) {
        this.codigo = codigo;
    }
    
    public int getCodigo() {
        return this.codigo;
    }
}
