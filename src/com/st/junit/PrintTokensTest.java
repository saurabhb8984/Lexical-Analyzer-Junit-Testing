/**
 * 
 */
package com.st.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.rules.ExpectedException;

/**
 * @author Saurabh
 *
 */
public class PrintTokensTest {

	@Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();
	public ExpectedException thrown = ExpectedException.none();
	static PrintTokens p;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		p=new PrintTokens();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.st.junit.PrintTokens#open_character_stream(java.lang.String)}.
	 */
	@Test
	public void testOpen_character_stream(){
		String fname1=null;
		BufferedReader br1 = p.open_character_stream(fname1);
		assertNotNull(br1);

		String fname2="E:\\UTA\\abc.txt";
		BufferedReader br2 = p.open_character_stream(fname2);
		assertNotNull(br2);	

		thrown.expect(FileNotFoundException.class);
		String fname3="abc";
		BufferedReader br3 = p.open_character_stream(fname3);
	}



	/**
	 * Test method for {@link com.st.junit.PrintTokens#open_token_stream(java.lang.String)}.
	 */
	@Test
	public void testOpen_token_stream() {
		String fname1="";
		BufferedReader br1 = p.open_token_stream(fname1);
		assertNotNull(br1);

		String fname2="E:\\UTA\\abc.txt";
		BufferedReader br2 = p.open_token_stream(fname2);
		assertNotNull(br2);
	}

	/**
	 * Test method for {@link com.st.junit.PrintTokens#get_token(java.io.BufferedReader)}.
	 */
	@Test
	public void testGet_token() {
		// read it with BufferedReader
		BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(" ".getBytes())));
		String s=new String();
		s=p.get_token(br);
		assertEquals(null,s);
		
		BufferedReader br1 = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("\n".getBytes())));
		String s1=new String();
		s1=p.get_token(br1);
		assertEquals(null,s1);
		
		BufferedReader br2 = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("\r".getBytes())));
		String s2=new String();
		s2=p.get_token(br2);
		assertEquals(null,s2);
		
		BufferedReader br3 = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("(".getBytes())));
		String s3=new String();
		s3=p.get_token(br3);
		assertEquals("(",s3);
		
		BufferedReader br4 = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("\"".getBytes())));
		String s4=new String();
		s4=p.get_token(br4);
		assertEquals("\"",s4);
		
		BufferedReader br5 = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("\"a\"".getBytes())));
		String s5=new String();
		s5=p.get_token(br5);
		assertEquals("\"a\"",s5);
		
		BufferedReader br6 = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("a(".getBytes())));
		String s6=new String();
		s6=new PrintTokens().get_token(br6);
		assertEquals("a",s6);
		
		BufferedReader br7 = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("\"abc\"".getBytes())));
		String s7=new String();
		s7=new PrintTokens().get_token(br7);
		assertEquals("\"abc\"",s7);
		
		BufferedReader br8 = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(";abc".getBytes())));
		String s8=new String();
		s8=new PrintTokens().get_token(br8);
		assertEquals(";abc",s8);
		
		BufferedReader br9 = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("\"\"".getBytes())));
		String s9=new String();
		s9=new PrintTokens().get_token(br9);
		assertEquals("\"\"",s9);
		/*BufferedReader br9 = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("\";abc\"".getBytes())));
		String s9=new String();
		s9=new PrintTokens().get_token(br9);
		assertEquals(";",s9);*/
		/*String s1=new String();
		s1=p.get_token(br);
		assertEquals("(",s1);
		String s2=new String();
		s2=p.get_token(br);
		assertEquals("test",s2);	*/
	}



	/**
	 * Test method for {@link com.st.junit.PrintTokens#is_token_end(int, int)}.
	 */
	@Test
	public void testIs_token_end() {
		assertTrue(PrintTokens.is_token_end(0, 1));
		assertTrue(PrintTokens.is_token_end(0, 10));
		assertTrue(PrintTokens.is_token_end(0, 13));
		assertTrue(PrintTokens.is_token_end(0, 34));
		assertFalse(PrintTokens.is_token_end(0, 9));
		assertTrue(PrintTokens.is_token_end(1, 10));
		assertTrue(PrintTokens.is_token_end(1, 13));
		assertTrue(PrintTokens.is_token_end(1, 32));
		assertFalse(PrintTokens.is_token_end(1, 9));
		assertTrue(PrintTokens.is_token_end(3, 40));
		assertTrue(PrintTokens.is_token_end(3, 13));
		assertTrue(PrintTokens.is_token_end(3, 32));
		assertTrue(PrintTokens.is_token_end(3, 59));
		assertFalse(PrintTokens.is_token_end(3, 9));
	}

	/**
	 * Test method for {@link com.st.junit.PrintTokens#token_type(java.lang.String)}.
	 */
	@Test
	public void testToken_type() {
		assertEquals(PrintTokens.keyword,PrintTokens.token_type("and"));
		assertEquals(PrintTokens.spec_symbol,PrintTokens.token_type("("));
		assertEquals(PrintTokens.identifier,PrintTokens.token_type("abc1"));
		assertEquals(PrintTokens.num_constant,PrintTokens.token_type("123"));
		assertEquals(PrintTokens.str_constant,PrintTokens.token_type("\"abc\""));
		assertEquals(PrintTokens.char_constant,PrintTokens.token_type("#a"));
		assertEquals(PrintTokens.comment,PrintTokens.token_type(";abc"));
		assertEquals(PrintTokens.error,PrintTokens.token_type("$"));
	}

	 /**
	 * Test method for {@link com.st.junit.PrintTokens#print_token(java.lang.String)}.
	 */
	@Test
	public void testPrint_token() {
		OutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		System.setOut(ps);
		String tok1 ="and";
		p.print_token(tok1);
		assertEquals("keyword,\"" + tok1 + "\".\n", os.toString());

		OutputStream os1 = new ByteArrayOutputStream();
		PrintStream ps1 = new PrintStream(os1);
		System.setOut(ps1);
		String tok2 ="@";
		p.print_token(tok2);
		assertEquals("error,\"" + tok2 + "\".\n", os1.toString());
		
		OutputStream os2 = new ByteArrayOutputStream();
		PrintStream ps2 = new PrintStream(os2);
		System.setOut(ps2);
		String tok3 ="[";
		p.print_token(tok3);
		assertEquals("lsquare.\n", os2.toString());
		
		OutputStream os3 = new ByteArrayOutputStream();
		PrintStream ps3 = new PrintStream(os3);
		System.setOut(ps3);
		String tok4 ="abc12";
		p.print_token(tok4);
		assertEquals("identifier,\"" + tok4 + "\".\n", os3.toString());
		
		OutputStream os4 = new ByteArrayOutputStream();
		PrintStream ps4 = new PrintStream(os4);
		System.setOut(ps4);
		String tok5 ="123";
		p.print_token(tok5);
		assertEquals("numeric," + tok5 + ".\n", os4.toString());
		
		OutputStream os5 = new ByteArrayOutputStream();
		PrintStream ps5 = new PrintStream(os5);
		System.setOut(ps5);
		String tok6 ="\"abc\"";
		p.print_token(tok6);
		assertEquals("string," + tok6 + ".\n", os5.toString());
		
		OutputStream os6 = new ByteArrayOutputStream();
		PrintStream ps6 = new PrintStream(os6);
		System.setOut(ps6);
		String tok7 ="#a";
		p.print_token(tok7);
		assertEquals("character,\""+tok7.replace("#", "")+"\".\n", os6.toString());
		
		OutputStream os7 = new ByteArrayOutputStream();
		PrintStream ps7 = new PrintStream(os7);
		System.setOut(ps7);
		String tok8 =";abc";
		p.print_token(tok8);
		assertEquals("comment,\"" + tok8 + "\".\n", os7.toString());
	}
	

	  /**
	  * Test method for {@link com.st.junit.PrintTokens#is_comment(java.lang.String)}.
	  */
	@Test
	public void testIs_comment() {
		assertTrue(PrintTokens.is_comment(";abc"));
		assertFalse(PrintTokens.is_comment("abc"));
	}

	  /**
	   * Test method for {@link com.st.junit.PrintTokens#is_keyword(java.lang.String)}.
	   */
	@Test
	public void testIs_keyword() {
		assertTrue(PrintTokens.is_keyword("and"));
		assertTrue(PrintTokens.is_keyword("or"));
		assertTrue(PrintTokens.is_keyword("if"));
		assertTrue(PrintTokens.is_keyword("xor"));
		assertTrue(PrintTokens.is_keyword("lambda"));
		assertTrue(PrintTokens.is_keyword("=>"));
		assertFalse(PrintTokens.is_keyword("abc"));
	}

	    /**
	    * Test method for {@link com.st.junit.PrintTokens#is_char_constant(java.lang.String)}.
	    */
	@Test
	public void testIs_char_constant() {
		assertTrue(PrintTokens.is_char_constant("#a"));
		assertFalse(PrintTokens.is_char_constant("#1"));
		assertFalse(PrintTokens.is_char_constant("#abc"));
		assertFalse(PrintTokens.is_char_constant("a1"));
	}

	     /**
	     * Test method for {@link com.st.junit.PrintTokens#is_num_constant(java.lang.String)}.
	     */
	@Test
	public void testIs_num_constant() {
		assertFalse(PrintTokens.is_num_constant("a"));
		assertTrue(PrintTokens.is_num_constant("1"));
		assertFalse(PrintTokens.is_num_constant("11a"));
		assertTrue(PrintTokens.is_num_constant("1\0"));
	}

	/**
     * Test method for {@link com.st.junit.PrintTokens#is_str_constant(java.lang.String)}.
     */
	@Test
	public void testIs_str_constant(){
		assertFalse(PrintTokens.is_str_constant("a"));
		assertFalse(PrintTokens.is_str_constant("\"\\"));
		assertTrue(PrintTokens.is_str_constant("\"\""));
		assertFalse(PrintTokens.is_str_constant("\"a"));
		assertFalse(PrintTokens.is_str_constant("\"\0"));
	}
	      /**
	      * Test method for {@link com.st.junit.PrintTokens#is_identifier(java.lang.String)}.
	      */
	@Test
	public void testIs_identifier() {
		assertFalse(PrintTokens.is_identifier("1"));
		assertTrue(PrintTokens.is_identifier("a"));
		assertFalse(PrintTokens.is_identifier("a@"));
		assertTrue(PrintTokens.is_identifier("a\0"));
	}

	 /**
	 * Test method for {@link com.st.junit.PrintTokens#print_spec_symbol(java.lang.String)}.
	 */
	@Test
	public void testPrint_spec_symbol() {
		OutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		System.setOut(ps);
		String tok1 ="(";
		PrintTokens.print_spec_symbol(tok1);
		assertEquals("lparen.\n", os.toString());

		OutputStream os1 = new ByteArrayOutputStream();
		PrintStream ps1 = new PrintStream(os1);
		System.setOut(ps1);
		String tok2 =")";
		PrintTokens.print_spec_symbol(tok2);
		assertEquals("rparen.\n", os1.toString());
		
		OutputStream os2 = new ByteArrayOutputStream();
		PrintStream ps2 = new PrintStream(os2);
		System.setOut(ps2);
		String tok3 ="[";
		PrintTokens.print_spec_symbol(tok3);
		assertEquals("lsquare.\n", os2.toString());
		
		OutputStream os3 = new ByteArrayOutputStream();
		PrintStream ps3 = new PrintStream(os3);
		System.setOut(ps3);
		String tok4 ="]";
		PrintTokens.print_spec_symbol(tok4);
		assertEquals("rsquare.\n", os3.toString());
		
		OutputStream os4 = new ByteArrayOutputStream();
		PrintStream ps4 = new PrintStream(os4);
		System.setOut(ps4);
		String tok5 ="'";
		PrintTokens.print_spec_symbol(tok5);
		assertEquals("quote.\n", os4.toString());
		
		OutputStream os5 = new ByteArrayOutputStream();
		PrintStream ps5 = new PrintStream(os5);
		System.setOut(ps5);
		String tok6 ="`";
		PrintTokens.print_spec_symbol(tok6);
		assertEquals("bquote.\n", os5.toString());
		
		OutputStream os6 = new ByteArrayOutputStream();
		PrintStream ps6 = new PrintStream(os6);
		System.setOut(ps6);
		String tok7 =",";
		PrintTokens.print_spec_symbol(tok7);
		assertEquals("comma.\n", os6.toString());

	}

	/**
	 * Test method for {@link com.st.junit.PrintTokens#is_spec_symbol(char)}.
	 */
	@Test
	public void testIs_spec_symbol() {
		assertTrue(PrintTokens.is_spec_symbol('('));
		assertTrue(PrintTokens.is_spec_symbol(')'));
		assertTrue(PrintTokens.is_spec_symbol('['));
		assertTrue(PrintTokens.is_spec_symbol(']'));
		assertTrue(PrintTokens.is_spec_symbol('\''));
		assertTrue(PrintTokens.is_spec_symbol('`'));
		assertTrue(PrintTokens.is_spec_symbol(','));
		assertFalse(PrintTokens.is_spec_symbol('@'));
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
