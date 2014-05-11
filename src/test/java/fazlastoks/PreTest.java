package fazlastoks;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import freela.util.Db;
import freela.util.FaceUtils;

public class PreTest {

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
	public void testLogFormatter() {
		extracted();
	}

	private void extracted() {
		Db.debug=true;
		Db.selectTable("select * from category");
	}

	@Test
	public void testDeletePhoto() {
	}

	@Test
	public void testUpload() {
	}

	@Test
	public void testPro() {
	}

	@Test
	public void testSave() {
	}

	@Test
	public void testValidateInput() {
	}

	@Test
	public void testValidateFile() {
	}

}
