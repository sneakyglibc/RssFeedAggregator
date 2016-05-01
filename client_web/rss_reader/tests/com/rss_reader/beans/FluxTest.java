package com.rss_reader.beans;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FluxTest {
	protected Flux op;
	
	@Before
	public void setUp() throws Exception {
		op = new Flux();
		op.setLink("http://9gag.com");
		op.setTitle("9GAG");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLink() throws Exception {
		assertEquals(new String ("http://9gag.com"), 
                op.getLink());
	}
	
	@Test
	public void testTitle() throws Exception {
		assertEquals(new String ("9GAG"), 
                op.getTitle());
	}
}
