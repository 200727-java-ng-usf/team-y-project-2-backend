package com.revature.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@EnableWebMvc
@Configuration
@ComponentScan("com.revature")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement
@EnableWebSocketMessageBroker
//@PropertySource("classpath:application.properties")
public class ApplicationConfig implements WebApplicationInitializer, WebSocketMessageBrokerConfigurer {

//	@Value("${db.driver}")
	private String dbDriver;
//
//	@Value("${db.url}")
	private String dbUrl;
//
//	@Value("${db.schema}")
	private String dbSchema;
//
//	@Value("${db.username}")
	private String dbUsername;
//
//	@Value("${db.password}")
	private String dbPassword;

	@Bean
	public BasicDataSource dataSource() {
		Properties props = new Properties();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream propsInput = loader.getResourceAsStream("application.properties");
		try {
			if(propsInput == null){
				if(System.getProperty("db.url") == null){
					dbDriver = System.getenv("db.driver");
					dbSchema = System.getenv("db.schema");
					dbUrl = System.getenv("db.url");
					dbUsername = System.getenv("db.username");
					dbPassword = System.getenv("db.password");

				}else{
					dbDriver = System.getProperty("db.driver");
					dbSchema = System.getProperty("db.schema");
					dbUrl = System.getProperty("db.url");
					dbUsername = System.getProperty("db.username");
					dbPassword = System.getProperty("db.password");
				}

			} else {
				props.load(propsInput);
				dbDriver = props.getProperty("db.driver");
				dbSchema = props.getProperty("db.schema");
				dbUrl = props.getProperty("db.url");
				dbUsername = props.getProperty("db.username");
				dbPassword = props.getProperty("db.password");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan("com.revature"); // tell hibernate where to look for entities.
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}

	@Bean
	public PlatformTransactionManager txManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}


	// NOT A BEAN
	private final Properties hibernateProperties() {
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty(Environment.DIALECT, "org.hibernate.dialect.PostgreSQL95Dialect");
		hibernateProperties.setProperty(Environment.SHOW_SQL, "true");
		hibernateProperties.setProperty(Environment.FORMAT_SQL, "true");
		//	validate: validate the schema, makes no changes to the database.
		//	update: update the schema.
		//	create: creates the schema, destroying previous data.
		//	create-drop: drop the schema when the SessionFactory is closed explicitly, typically when the application is stopped.
		//	none: does nothing with the schema, makes no changes to the database
		hibernateProperties.setProperty(Environment.HBM2DDL_AUTO, "update");
		hibernateProperties.setProperty(Environment.HBM2DDL_IMPORT_FILES, "import.sql");

		return hibernateProperties;
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/vote-socket")
				.setAllowedOrigins("*")
				.withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/app")
				.enableSimpleBroker("/vote-message");
	}


	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		AnnotationConfigWebApplicationContext container = new AnnotationConfigWebApplicationContext();
		container.register(ApplicationConfig.class);

		servletContext.addListener(new ContextLoaderListener(container));
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet", new DispatcherServlet(container));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");

	}

}
