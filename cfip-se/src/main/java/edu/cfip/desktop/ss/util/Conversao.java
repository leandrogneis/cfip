package edu.cfip.desktop.ss.util;
import java.math.BigDecimal;

import java.sql.Date;
import java.sql.Timestamp;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.GregorianCalendar;

import edu.cfip.desktop.ss.sql.TipoDados;

public class Conversao {
    
    public static Double numeroDouble(Object o) {
        if (o == null)
            return null;
        if (o instanceof Double)
            return (Double)o;
        if (o instanceof Number)
            return ((Number)o).doubleValue();
        if (o instanceof String)
            return new Double(o.toString());
        return new Double(o.toString());
    }
    
    public static BigDecimal bigDecimal(Object valor) {
        if (valor == null)
            return null;
        if (valor instanceof BigDecimal)
            return (BigDecimal)valor;
        if (valor instanceof Double)
            return BigDecimal.valueOf((Double)valor);
        if (valor instanceof Float)
            return BigDecimal.valueOf((Float)valor);
        if (valor instanceof Number)
            return BigDecimal.valueOf(((Number)valor).longValue());
        else
            return BigDecimal.valueOf(Double.valueOf(valor.toString()));
    }
    
    public static int periodo(String periodo) {
        return DataHora.converterPeriodo(periodo);
    }
    
    public static String[] texto(Object valor[]){
        String resultado[] = new String[valor.length];
        for (int i = 0; i < valor.length; i++)
            resultado[i] = texto(valor[i]);
        return resultado;
    }
    
    public static String texto(Object valor){
        if (valor == null)
            return "";
        switch (TipoDados.verifica(valor)){
            case TipoDados.TEXTO:
                return valor.toString();
            case TipoDados.BYTE:
            case TipoDados.INTEIRO_LONGO:
            case TipoDados.INTEIRO_PADRAO:
            case TipoDados.INTEIRO_PEQUENO:
                return String.valueOf(Conversao.inteiro(valor));
            case TipoDados.FLUTUANTE_DUPLO:
                return DecimalFormat.getInstance().format(valor);
            case TipoDados.FLUTUANTE_PADRAO:
                return DecimalFormat.getInstance().format(Conversao.flutuante((Float)valor));
            case TipoDados.DATA:
                return SimpleDateFormat.getDateInstance().format((java.sql.Date)valor);
            case TipoDados.HORA: 
                return SimpleDateFormat.getTimeInstance().format((java.sql.Time)valor);
            case TipoDados.DATA_HORA:
                return SimpleDateFormat.getDateTimeInstance().format((java.sql.Timestamp)valor);
            case TipoDados.SIM_NAO:
                if (((Boolean)valor).booleanValue())
                    return "Sim";
                else
                    return "Não";
            default:
                return valor.toString();
        }
        
    }

    public static String texto(double valor){
        return NumberFormat.getNumberInstance().format(valor);
    }
    
    public static String texto(long valor){
        return NumberFormat.getNumberInstance().format(valor);
    }
        
    public static int inteiro(long valor){
        return new Long(valor).intValue();
    }
    
    public static int inteiro(float valor){
        return new Float(valor).intValue();
    }
    
    public static int inteiro(double valor){
        return new Double(valor).intValue();
    }
    
    public static int inteiro(String valor) throws ParseException{
        return NumberFormat.getNumberInstance().parse(valor).intValue();
    }
    
    public static int[] inteiro(Object valor[]){
        int resultado[] = new int[valor.length];
        for (int i = 0; i < valor.length; i++)
            resultado[i] = inteiro(valor[i]);
        return resultado;
    }
    
    public static Integer inteiro(Object valor){
        if (valor instanceof Integer)
            return ((Integer)valor);
        if (valor instanceof Number)
            return ((Number)valor).intValue();        
        return Integer.valueOf(valor.toString());
    }
            
    public static long longo(int valor){
        return (long)valor;
    }
    
    public static long longo(float valor){
        return (long)valor;
    }
    
    public static long longo(double valor){
        return (long)valor;
    }
    
    public static long longo(String valor) throws ParseException{
        return NumberFormat.getNumberInstance().parse(valor).longValue();
    }
    
    public static Long longo(Object valor) {
        if (valor instanceof Long)
            return (Long)valor;
        if (valor instanceof String)
            return Long.valueOf((String)valor);
        else if (valor instanceof Number)
            return ((Number)valor).longValue();
        else
            return Long.valueOf(valor.toString());
    }
            
    public static double duplo(int valor){
        return (double)valor;
    }
    
    public static double duplo(float valor){
        return (double)valor;
    }
    
    public static double duplo(long valor){
        return (double)valor;
    }
            
    public static double duplo(String valor) throws ParseException{
        return NumberFormat.getNumberInstance().parse(valor).doubleValue();
    }
    
    public static double duplo(Double valor){
        return valor.doubleValue();
    }
    
    public static Object duplo(Object valor){
        if (valor == null)
            return null;
        if (valor instanceof Double)
            return (Double)valor;            
        else if (valor instanceof Float)
            return duplo(flutuante(valor));        
        else
            return Double.valueOf(valor.toString());
    }
    
    public static boolean booleano(int valor){
        if (valor != 0) 
            return true;
        else
            return false;
    }
    
    public static boolean booleano(long valor){
        if (valor != 0) 
            return true;
        else
            return false;
    }
    
    public static boolean booleano(float valor){
        if (valor != 0.0F) 
            return true;
        else
            return false;
    }
    
    public static boolean booleano(double valor){
        if (valor != 0.0) 
            return true;
        else
            return false;
    }
    
    public static boolean booleano(String valor) {
        if (valor == null)
            return false;
        if (((String)valor).equals("Sim") || ((String)valor).equals("1") || ((String)valor).equals("True"))
            return true;
        else
            return false;
    }
    
    public static boolean[] booleano(Object valor[]){
        boolean resultado[] = new boolean[valor.length];
        for (int i = 0; i < valor.length; i++)
            resultado[i] = booleano(valor[i]);
        return resultado;
    }
    
    public static boolean booleano(Object valor){
        if (valor instanceof Byte)
            return booleano(((Byte)valor).byteValue());
        else if (valor instanceof Boolean)
            return ((Boolean)valor).booleanValue();
        return false;        
    }
    
    public static java.sql.Date dataSql(java.util.Date data) {
        String strData = Formatador.formatar(data, "yyyy-MM-dd");
        return java.sql.Date.valueOf(strData);
    }
    
    public static java.sql.Date data(java.sql.Timestamp valor) {
        if (valor == null)
            return null;
        return new java.sql.Date(valor.getTime());
    }
    
    public static java.util.Date dataDePeriodo(Integer periodo) throws ParseException{
        return data(periodo.toString(), "yyyyMM");
    }
    
    public static java.util.Date data(String texto) throws ParseException{
        return data(texto, "dd/MM/yyyy");
    }
    
    public static java.util.Date data(String valor, String formato) throws ParseException{
        java.text.SimpleDateFormat formata = new java.text.SimpleDateFormat(formato);
        formata.setLenient(false);
        return formata.parse(valor);
    }
    
    public static java.util.Date data(Object valor) throws ParseException {
        if (valor instanceof java.sql.Date) {
            return (java.sql.Date)valor;        
        }else if (valor instanceof java.util.Date) {
            return new java.sql.Date(((java.util.Date)valor).getTime());
        }else if (valor instanceof String)
            return data(valor.toString());
        return null;
    }
    
    public static java.sql.Timestamp dataHora(long valor) {
        return new java.sql.Timestamp(valor);
    }
    
    public static java.sql.Timestamp dataHora(String valor) throws ParseException {
        // 01/12/2004 15:35:00
        // 1234567890123456789
        int dia = Conversao.inteiro(Texto.meio(valor, 1, 2));
        int mes = Conversao.inteiro(Texto.meio(valor, 4, 2));
        int ano = Conversao.inteiro(Texto.meio(valor, 7, 4));
        String strHora = Texto.meio(valor, 12, 2);
        int hora = 0;
        if (strHora.trim().length() > 0)
            hora = Conversao.inteiro(strHora);
        String strMinuto = Texto.meio(valor, 15, 2);
        int minuto = 0;
        if (strMinuto.trim().length() > 0)
            minuto = Conversao.inteiro(strMinuto);
        int segundo = 0;
        if (valor.length() > 16) 
            segundo = Conversao.inteiro(Texto.meio(valor, 18, 2));
        
        GregorianCalendar gc = new GregorianCalendar(ano, mes - 1, dia, hora, minuto, segundo);
        
        return new Timestamp(gc.getTime().getTime());
    }
    
    public static java.sql.Timestamp dataHora(Object valor) {
        return (java.sql.Timestamp)valor;
    }
    
    public static java.sql.Time hora(java.sql.Timestamp valor) {
        if (valor == null)
            return null;
        return new java.sql.Time(valor.getTime());
    }
    
    public static java.sql.Time hora(String valor) throws ParseException {
        int hora = Conversao.inteiro(Texto.meio(valor, 1, 2));
        int minuto = Conversao.inteiro(Texto.meio(valor, 4, 2));
        int segundo = 0;
        if (valor.length() > 5) 
            segundo = Conversao.inteiro(Texto.meio(valor, 7, 2));
        
        GregorianCalendar gc = new GregorianCalendar(0, 0, 0, hora, minuto, segundo);
        
        return new java.sql.Time(gc.getTime().getTime());
    }
    
    public static java.sql.Time hora(Object valor) {
        return (java.sql.Time)valor;
    }
            
    public static float flutuante(int valor){
        return (float)valor;
    }
    
    public static float flutuante(float valor){
        return (float)valor;
    }
    
    public static float flutuante(long valor){
        return (float)valor;
    }
    
    public static float flutuante(Float valor){
        return valor.floatValue();
    }
    
    public static float flutuante(String valor) throws ParseException{
        return NumberFormat.getNumberInstance().parse(valor).floatValue();
    }
    
    public static Float flutuante(Object valor){
        if (valor == null)
            return null;
        
        if (valor instanceof Float)
            return (Float)valor;
        
        if (valor instanceof Number)
            return ((Number)valor).floatValue();
        
        return Float.valueOf(valor.toString());
    }
    
}
