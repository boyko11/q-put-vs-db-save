package boyko.simulation.service;

import boyko.simulation.model.Donkey;

public interface DonkeyService {
	
	public void wakeUp();

	public void create(Donkey donkey);

	public int count();

}
