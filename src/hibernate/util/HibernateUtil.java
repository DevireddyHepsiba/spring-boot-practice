package hibernate.util;

import hibernate.entity.Role;
import hibernate.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import java.util.Properties;

/**
 * Utility to build and provide the Hibernate SessionFactory.
 * Configured programmatically so there is no need for an XML config file.
 */
public class HibernateUtil {
	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			Properties settings = new Properties();
			settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
			settings.put(Environment.URL, "jdbc:mysql://localhost:3306/fleet_management?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
			settings.put(Environment.USER, "root");
			settings.put(Environment.PASS, "Hepsiba@2005");
			settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
			settings.put(Environment.SHOW_SQL, "true");
			settings.put(Environment.FORMAT_SQL, "true");
			// update will create missing tables and apply basic schema changes; safe for development
			settings.put(Environment.HBM2DDL_AUTO, "update");

			StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
					.applySettings(settings)
					.build();

			MetadataSources sources = new MetadataSources(registry)
					.addAnnotatedClass(User.class)
					.addAnnotatedClass(Role.class);

			Metadata metadata = sources.getMetadataBuilder().build();
			return metadata.getSessionFactoryBuilder().build();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ExceptionInInitializerError("Initial SessionFactory creation failed: " + ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void shutdown() {
		getSessionFactory().close();
	}
}
