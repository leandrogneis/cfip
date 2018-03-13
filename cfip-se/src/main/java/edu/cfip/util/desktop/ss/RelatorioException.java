package edu.cfip.util.desktop.ss;

public class RelatorioException extends Exception {
    public RelatorioException() {
        super();
    }
    
    public RelatorioException(Exception e) {
        super(e);
    }
    
    public RelatorioException(String msg) {
        super(msg);
    }
    
    public RelatorioException(String message, Throwable cause) {
        super(message, cause);
    }
}
