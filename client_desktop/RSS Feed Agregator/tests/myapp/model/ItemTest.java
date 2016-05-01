package myapp.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ItemTest {
	protected Item op;
	
	@Before
	public void setUp() throws Exception {
		op = new Item("itemName", "link");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testName() throws Exception {
		assertEquals (new String ("itemName"),
				op.getName());
	}
	
	@Test
	public void testLink() throws Exception {
		assertEquals (new String ("link"),
				op.getLink());
	}

}
