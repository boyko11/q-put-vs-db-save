package boyko.simulation.service.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import boyko.simulation.model.Donkey;
import boyko.simulation.service.DonkeyService;
import boyko.simulation.service.hibernate.HibernateService;

public class DonkeyServiceDBImpl implements DonkeyService {
	
	public void wakeUp() {
		System.out.println("Grrr. Good Morning, Number of donkeys at begining: " + count());
	}

	public void create(Donkey donkey) {
		
		SessionFactory sessionFactory = HibernateService.getSessionFactory();
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		session.save(donkey);
		
		tx.commit();
		session.close();
	}

	public int count() {
		SessionFactory sessionFactory = HibernateService.getSessionFactory();
		
		Session session = sessionFactory.openSession();
		
		int count = ((Long)session.createQuery("select count(d) from Donkey d").uniqueResult()).intValue();
		
		session.close();
		
		return count;
	}
}
