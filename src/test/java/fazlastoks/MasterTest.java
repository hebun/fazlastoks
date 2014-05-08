package fazlastoks;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MasterTest {

	static Master master;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		master = new Master();
		assertNotNull(master.getCats());
		assertTrue("size is 0", master.getCats().size() > 0);

	}

	@Test
	public void testSearch() {
		master.setSearchText("x");
		String ret = master.search();
		assertNull(ret);
		master.setSearchText("test");
		ret = master.search();
		assertEquals("urun-arama?faces-redirect=true&key=test", ret);
	
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		master=null;
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMaster() {

	}

}
