package boyko.simulation.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import boyko.simulation.model.Donkey;
import boyko.simulation.service.DonkeyJDBCService;

public class DonkeyQueueListener {
	
	private Queue<Donkey> queue;
	private int id;
	private static int maxNumberOfInsertsInBatch = 5000;
	private DonkeyJDBCService donkeyJdbcService;
	
	public DonkeyQueueListener(Queue<Donkey> queue, int id) {
		
		this.queue = queue;
		this.donkeyJdbcService = new DonkeyJDBCService();
		this.id = id;
	}
	
	public void startListening() {
		
		new Thread(() -> {
			
			System.out.println("Listener " + id + " started listening...");
			
			while(true) {
				
				System.out.println("Listener " + id + " sleeping.");
				
				try { Thread.sleep(1000);} catch (Exception e) { e.printStackTrace(); }
				
				System.out.println("Listener " + id + " awake.");
				
				Donkey donkey = queue.poll();
				int numberOfDonkeysToBeInserted = 1;
				List<Donkey> donkeysToBeInsertedInThisBatch = new ArrayList<Donkey>();
				
				while(donkey != null && numberOfDonkeysToBeInserted < maxNumberOfInsertsInBatch) {
					
					donkeysToBeInsertedInThisBatch.add(donkey);
					numberOfDonkeysToBeInserted++;
					donkey = queue.poll();
				}
				
				if(donkey != null) {
					donkeysToBeInsertedInThisBatch.add(donkey);
					numberOfDonkeysToBeInserted++;
				}
				
				System.out.println("Number of donkeys to be inserted  in this batch: " + (numberOfDonkeysToBeInserted - 1));
				
				donkeyJdbcService.createBatch(donkeysToBeInsertedInThisBatch);
				
			}
			
		}).start();
	}

}
