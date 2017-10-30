package com.st.junit;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

public class PrintTokenTestMain {

	@Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.st.junit.PrintTokens#main(java.lang.String[])}.
	 * @throws IOException 
    */
	@Test
	public void testMain() throws IOException {
		exit.expectSystemExit();
		String[] args={"abc.txt","abc"};
		PrintTokens.main(args);
	}
	

	@Test
	public void testMain1() throws IOException {
		exit.expectSystemExit();
		String[] args={"E:\\UTA\\abc.txt"};
		PrintTokens.main(args);
	}

}
