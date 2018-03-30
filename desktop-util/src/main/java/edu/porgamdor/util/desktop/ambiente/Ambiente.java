package edu.porgamdor.util.desktop.ambiente;

public class Ambiente {
	public static final String STATUS="status";
	public static final String VERSAO="versao"; 
	public static final String NOME="ambiente"; 
	public static final String WS_PROFILE="WEBSERVICE"; 
	public static final String DB_PROFILE="!WEBSERVICE";
	public static final String DB_DRIVER="db.driver";
	public static final String DB_URL="db.url";
	public static final String DB_USER="db.user";
	public static final String DB_PASS="db.pass";
	public static final String DB_DIALECT="db.dialect";
	public static final String DB_DDL="db.ddl";
	public static final String DB_SHOWSQL="db.showsql";
	
	public static final String NOK="NOK";
	
	private String versao;
	private String nome;
	private String dbUrl;
	private String dbUser;
	private String dbPass;
	private String status;
	
	public Ambiente(String nome, String dbUrl, String dbUser, String dbPass) {
		this.versao="1.0";
		this.status="NOK";
		this.nome = nome;
		this.dbUrl = dbUrl;
		this.dbUser = dbUser;
		this.dbPass = dbPass;
	}
	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}
	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}
	public void setDbPass(String dbPass) {
		this.dbPass = dbPass;
	}
	public String getVersao() {
		return versao;
	}
	public String getNome() {
		return nome;
	}
	public String getDbUrl() {
		return dbUrl;
	}
	public String getDbUser() {
		return dbUser;
	}
	public String getDbPass() {
		return dbPass;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDbShowSql() {
		return "false";
	}
	public String getDbDriver() {
		if(dbUrl.contains("postgresql"))
			return "org.postgresql.Driver";
		else
			return "org.hsqldb.jdbcDriver";
	}
	public String getDbDdl() {
		if(dbUrl.contains("postgresql"))
			return "none"; // DEV
		else
			return "update";
	}
	public String getDbDialect() {
		if(dbUrl.contains("postgresql"))
			return "org.hibernate.dialect.PostgreSQLDialect";
		else
			return "org.hibernate.dialect.HSQLDialect";
	}

	public static Ambiente LOCAL = new Ambiente("LOCAL","jdbc:hsqldb:file:/cfip/database/cfipdb","sa","");
	public static Ambiente SERVER = new Ambiente("SERVER","datasource:postgresql://localhost:5432/cfip","postgres","postgres");
	
	public static Ambiente[] ambientes={LOCAL,SERVER};
	
	
}
