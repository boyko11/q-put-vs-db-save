package boyko.simulation;

import java.util.stream.IntStream;

import boyko.simulation.model.Donkey;
import boyko.simulation.service.DonkeyService;
import boyko.simulation.service.impl.DonkeyServiceDBImpl;
import boyko.simulation.service.impl.DonkeyServiceQueueImpl;

public class CreateRecordBenchmarkSimulation {
	
	private static int threadCompleteCount = 0;
	private static final DonkeyService donkeyService = new DonkeyServiceQueueImpl();
	//private static final DonkeyService donkeyService = new DonkeyServiceDBImpl();
	
	public static void main(String[] args) {
		
		long startTime = System.currentTimeMillis();
		
		IntStream.rangeClosed(1, 10).forEach( i -> {
				new Thread(() -> {
					System.out.println("Thread " + i);
					IntStream.rangeClosed(1, 10000).forEach(j-> {
						Donkey newDonkey = new Donkey(String.valueOf(j), String.valueOf(j), j);
						donkeyService.create(newDonkey);
					});
					signalComplete();
				}).start();
			}
		);
		
		int whileCount = 0;
		while( threadCompleteCount != 10 ) {
			if(whileCount % 1000000 == 0) {
				System.out.println("threadCompleteCount inside while: " + threadCompleteCount);
			}
			whileCount++;
		}
		
		long endTime = System.currentTimeMillis();
		
		System.out.println("Time it took to run: " + (endTime - startTime) + " millis" );
		System.out.println("Final queue depth: " + donkeyService.count());
	}
	
	synchronized static void signalComplete() {
		
		threadCompleteCount++;
		System.out.println("threadCompleteCount: " + threadCompleteCount);
	}
}
