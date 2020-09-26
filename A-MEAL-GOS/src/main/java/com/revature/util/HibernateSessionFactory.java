package com.revature.util;

import com.revature.models.AppUser;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * A singleton of the <code>{@link SessionFactory}</code> class that handles sessions for hibernate.
 */
public class HibernateSessionFactory {
	private static SessionFactory sessionFactory;
	private static Properties props = new Properties();

	static{

		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			InputStream propsInput = loader.getResourceAsStream("application.properties");
			if (propsInput == null) {
				if(System.getProperty("url") == null){
					props.setProperty("url", System.getenv("url"));
					props.setProperty("username", System.getenv("username"));
					props.setProperty("password", System.getenv("password"));
				}else {
					props.setProperty("url", System.getProperty("url"));
					props.setProperty("username", System.getProperty("username"));
					props.setProperty("password", System.getProperty("password"));
				}
			} else {
				props.load(propsInput);
			}
//			props.load(new FileReader("./src/main/resources/application.properties"));
//			if(props.isEmpty()){
//				props.load(new FileReader("./application.properties"));
//			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to load application properties.");
//			e.printStackTrace();
		}
	}

	private HibernateSessionFactory(){
	}

	/**
	 * Returns an instance of the <code>{@link SessionFactory}</code> class configured as necessary to talk to
	 * the Revabursement repository.
	 * @return an instance of the <code>{@link SessionFactory}</code> class.
	 */
	public static SessionFactory getInstance(){
		if(sessionFactory != null){
			return sessionFactory;
		}

		try{

			System.out.println(props.getProperty("url"));
			System.out.println(props.getProperty("username"));
			System.out.println(props.getProperty("password"));
			System.out.println(props.getProperty("db.url"));
			System.out.println(props.getProperty("db.username"));
			System.out.println(props.getProperty("db.password"));
			System.out.println(props.getProperty("db.driver"));
			System.out.println(props.getProperty("db.schema"));
			Configuration config = new Configuration()
					.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
					.setProperty("hibernate.connection.url", "jdbc:" +
							"postgresql://" +
							props.getProperty("url") +
							":5432" +
							"/postgres")
					.setProperty("hibernate.connection.username", props.getProperty("username"))
					.setProperty("hibernate.connection.password", props.getProperty("password"))
//					.setProperty("hibernate.connection.pool_size", "1")
					.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect")
					.setProperty("hibernate.show_sql", "true")
					.setProperty("hibernate.current_session_context_class", "thread")
//					.setProperty("hibernate.hbm2ddl.auto", "create-drop")
				.addAnnotatedClass(AppUser.class)
				;

			config.setImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE);

			System.out.println("Configuration: " + config);

			sessionFactory = config.buildSessionFactory();

			System.out.println("SessionFactory: " + sessionFactory);

			return sessionFactory;
		} catch(Exception e) {
			System.out.println(e.getMessage());
		    e.printStackTrace();
		    return null;
		}
	}

//	@Override
//	protected Object clone() throws CloneNotSupportedException {
//		throw new CloneNotSupportedException();
//	}
}
