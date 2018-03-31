package edu.cfip.core.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(value = "edu.cfip.core.dao*")
@EnableJpaRepositories(basePackages = "edu.cfip.core.dao.springjpa")
public class PersistenceConfigEstatico {
	private static final String PERSISTENCE_UNIT = "PU_CFIP";
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(createDataSource());
		emf.setJpaVendorAdapter(jpaVendorApapter());
		emf.setJpaProperties(getProperties());
		emf.setPersistenceUnitName(PERSISTENCE_UNIT);
		emf.setPackagesToScan("edu.cfip.core.model");
		return emf;
	}

	@Bean
	public DataSource createDataSource() {
		DriverManagerDataSource dataSource = null;
		dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
		dataSource.setUrl("jdbc:hsqldb:file:/porgamador/cfip/implementacao/db/cfipdb");
		dataSource.setUsername("sa");
		dataSource.setPassword("");

		return dataSource;
	}

	private Properties getProperties() {
		Properties properties = new Properties();
		properties.put(AvailableSettings.HBM2DDL_AUTO, "update");
		properties.put(AvailableSettings.DIALECT, "org.hibernate.dialect.HSQLDialect");
		properties.put(AvailableSettings.SHOW_SQL, "true");

		return properties;
	}

	@Bean
	public JpaVendorAdapter jpaVendorApapter() {
		return new HibernateJpaVendorAdapter();
	}

	@Bean
	@Autowired
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManager) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManager);
		return transactionManager;
	}

}
