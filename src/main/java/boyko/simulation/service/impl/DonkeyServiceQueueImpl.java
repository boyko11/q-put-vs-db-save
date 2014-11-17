package boyko.simulation.service.impl;

import java.util.Queue;

import boyko.simulation.model.Donkey;
import boyko.simulation.service.DonkeyService;

public class DonkeyServiceQueueImpl implements DonkeyService {

	private Queue<Donkey> queue;
	
	public DonkeyServiceQueueImpl(Queue<Donkey> queue) {

		this.queue = queue;
	}
	
	@Override
	public void create(Donkey donkey) {
		queue.add(donkey);
	}

	@Override
	public int count() {
		return queue.size();
	}

	@Override
	public void wakeUp() {
		
		System.out.println("I am always awake!!!");
	}

}
