package com.rss_reader.beans;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserTest {
	protected User op;
	
	@Before
	public void setUp() throws Exception {
		op = new User();
		op.setEmail("olivier@a.com");
		op.setPassword("motdepasse");
		List<Flux> flux = new ArrayList<Flux>();
		op.setFlux(flux);
		List<Item> items = new ArrayList<Item>();
		op.setItems(items);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEmail() throws Exception {
		assertEquals(new String ("olivier@a.com"), 
                op.getEmail());
	}
	
	@Test
	public void testPassword() throws Exception {
		assertEquals(new String ("motdepasse"), 
                op.getPassword());
	}
	
	@Test
	public void testFlux() throws Exception {
		List<Flux> fluxTest = new ArrayList<Flux>();
		assertEquals(fluxTest, 
                op.getFlux());
	}
	
	@Test
	public void testItems() throws Exception {
		List<Item> itemsTest = new ArrayList<Item>();
		assertEquals(itemsTest, 
                op.getItems());
	}
}
