package boyko.simulation.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;

import boyko.simulation.model.Donkey;
import boyko.simulation.service.hibernate.HibernateService;

public class DonkeyJDBCService {
	
	public void createBatch(List<Donkey> donkeysInBatch) {
		
		if(CollectionUtils.isEmpty(donkeysInBatch)) {
			
			System.out.println("No donkeys found in this batch.");
			return;
		}
		
		Session session = null;
		Connection dbConnection = null;
		PreparedStatement donkeyInsertPreparedStatement = null;
		
		
		try {
			
			session = HibernateService.getSessionFactory().openSession();
			
			dbConnection = ((SessionImpl) session).connection();
			
			dbConnection.setAutoCommit(false);
			 
			String insertTableSQL = "insert into donkey (name, temper, age) values (?, ?, ?)";
				
			donkeyInsertPreparedStatement = dbConnection.prepareStatement(insertTableSQL);
			 
			for(Donkey donkey : donkeysInBatch) {
				
				donkeyInsertPreparedStatement.setString(1, donkey.getName());
				donkeyInsertPreparedStatement.setString(2, donkey.getTemper());
				donkeyInsertPreparedStatement.setInt(3, donkey.getAge());
				donkeyInsertPreparedStatement.addBatch();
			}
			
			donkeyInsertPreparedStatement.executeBatch();
		    dbConnection.commit();
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(session != null) { session.close();} 
			try { if(donkeyInsertPreparedStatement != null) { donkeyInsertPreparedStatement.close();} if(dbConnection != null) { dbConnection.close();}}catch(Exception e1){e1.printStackTrace();}
		}
		
		System.out.println(donkeysInBatch.size() + " inserted in this batch.");
	}

}
