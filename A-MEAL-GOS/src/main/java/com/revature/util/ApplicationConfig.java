package com.revature.util;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@ComponentScan
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class ApplicationConfig {

	@Value("${db.driver}")
	private String dbDriver;

	@Value("${db.url}")
	private String dbUrl;

	@Value("${db.schema}")
	private String dbSchema;

	@Value("${db.username}")
	private String dbUsername;

	@Value("${db.password}")
	private String dbPassword;

	@Bean
	public BasicDataSource dataSource() {
		System.out.println("Creating BasicDataSource bean...");
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(dbDriver);
		dataSource.setUrl(dbUrl);
		dataSource.setDefaultSchema(dbSchema);
		dataSource.setUsername(dbUsername);
		dataSource.setPassword(dbPassword);
		return dataSource;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		System.out.println("Creating SessionFactory bean...");
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan("com.revature");
		sessionFactory.setHibernateProperties(hibernateProperties());
		System.out.println("SessionFactory bean successfully created");
		return sessionFactory;
	}

	@Bean
	public PlatformTransactionManager txManager() {
		System.out.println("Creating PlatformTransactionManager bean...");
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		System.out.println("PlatformTransactionManager bean successfully created");
		return transactionManager;
	}

	private final Properties hibernateProperties() {
		System.out.println("Loading Hibernate properties");
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty(Environment.DIALECT, "org.hibernate.dialect.PostgreSQL95Dialect");
		hibernateProperties.setProperty(Environment.SHOW_SQL, "true");
		hibernateProperties.setProperty(Environment.FORMAT_SQL, "true");
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "validate");
		System.out.println("Hibernate properties loaded");
		return hibernateProperties;
	}
}
