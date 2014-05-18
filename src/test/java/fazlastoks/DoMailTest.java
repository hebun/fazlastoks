package fazlastoks;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import freela.util.Db;
import freela.util.DoMail;
import freela.util.FaceUtils;

public class DoMailTest {

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

	@Test
	public void testSend() {
		int del= Db.delete("update keyword set keyword='xxxxx' limit 2");
		FaceUtils.log.fine(del+"");
		//DoMail doMail = new DoMail();
	//	doMail.send();
	}

}
