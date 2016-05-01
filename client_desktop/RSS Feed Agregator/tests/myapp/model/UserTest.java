package myapp.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserTest {
	protected User op;
	
	@Before
	public void setUp() throws Exception {
		op = new User("user", "motdepasse");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBody() throws Exception {
		assertEquals (new String ("email:user, password:motdepasse"),
				op.getBody());
	}

}
