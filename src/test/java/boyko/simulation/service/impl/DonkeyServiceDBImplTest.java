package boyko.simulation.service.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import boyko.simulation.model.Donkey;

public class DonkeyServiceDBImplTest {

	@Test
	public void test() {
		
		DonkeyServiceDBImpl donkeyServiceDBImpl = new DonkeyServiceDBImpl();
		
		donkeyServiceDBImpl.create(new Donkey("hello", "hello", 17));
		
		assertEquals(3, donkeyServiceDBImpl.count());
	}

}
