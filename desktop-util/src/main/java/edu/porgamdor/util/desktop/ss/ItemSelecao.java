package edu.porgamdor.util.desktop.ss;

public class ItemSelecao {
    private String rotulo;
    private Object valor;
    
    public ItemSelecao() {
    }
    
    public ItemSelecao(String rotulo, Object valor) {
        setRotulo(rotulo);
        setValor(valor);
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getRotulo() {
        return rotulo;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public Object getValor() {
        return valor;
    }
    
    public String toString() {
        if (getRotulo() != null)
            return getRotulo();
        else
            return getValor().toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ItemSelecao)) {
            return false;
        }
        final ItemSelecao other = (ItemSelecao)object;
        if (!(valor == null ? other.valor == null : valor.equals(other.valor))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((valor == null) ? 0 : valor.hashCode());
        return result;
    }
}
