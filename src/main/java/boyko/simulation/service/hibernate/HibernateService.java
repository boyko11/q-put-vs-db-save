package boyko.simulation.service.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import boyko.simulation.model.Donkey;


public class HibernateService {

	private static SessionFactory sessionFactory;

	private static void init() {

		//create_record_perf_test_db
		//org.hibernate.dialect.MySQLDialect
		Configuration configuration = new Configuration();

		configuration.setProperty("hibernate.connection.driver_class",
				"com.mysql.jdbc.Driver");
		configuration.setProperty("hibernate.dialect",
				"org.hibernate.dialect.MySQLDialect");
		configuration.setProperty("hibernate.connection.url",
				"jdbc:mysql://localhost:3306/create_record_perf_test_db");
		configuration.setProperty("hibernate.connection.username", "root");
		configuration.setProperty("hibernate.connection.password", "root+1");
		//configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		//configuration.setProperty("hibernate.show_sql", "true");
		//configuration.setProperty("hibernate.format_sql", "true");

		configuration.addPackage("boyko.simulation.model")
			.addAnnotatedClass(Donkey.class);

		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();

		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}

	private static void bootstrapData() {
			
		System.out.println("Bootstrap started------------------------------------------------");
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		Donkey d1 = new Donkey();
		d1.setAge(14);
		d1.setName("Donkey1");
		d1.setTemper("Cray-Cray");
		
		Donkey d2 = new Donkey();
		d2.setAge(24);
		d2.setName("Donkey777");
		d2.setTemper("Chill-Chill");
		
		session.save(d1);
		session.save(d2);

		tx.commit();
		
		System.out.println("Donkey1 id: " + d1.getId());
		System.out.println("Donkey2 id: " + d2.getId());
		
		session.close();
		
		System.out.println("Bootstrap finished------------------------------------------------");
	}

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			init();
		}
		return sessionFactory;
	}
	
	static {
		init();
		bootstrapData();
	}

}
