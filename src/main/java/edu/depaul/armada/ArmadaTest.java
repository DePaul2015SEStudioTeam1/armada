package edu.depaul.armada;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/beans/armada-config-test.xml"})
@TransactionConfiguration(transactionManager="armadaTransactionManager")
@Transactional
public class ArmadaTest {

	@Test
	public void testStarter() {
		Armada test = new Armada();
		try {
			assertTrue(test.startServer(null));
			assertTrue(test.stopServer());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
