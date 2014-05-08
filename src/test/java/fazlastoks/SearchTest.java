package fazlastoks;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import freela.util.FaceUtils;

public class SearchTest {

	@Mock
	FacesContext context;
	private ExternalContext externalContext;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		FaceUtils.log.setLevel(Level.INFO);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	Search search;
	

	@Before
	public void setUp() throws Exception {

		context = ContextMocker.mockFacesContext();
		externalContext = mock(ExternalContext.class);

		when(context.getExternalContext()).thenReturn(externalContext);
		search = new Search("testing");

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSearchString() {

	}

	@Test
	public void testSearch() {

		Map<String,String> map=new HashMap<>();
	
		map.put("key", "test");
		
		when(externalContext.getRequestParameterMap()).thenReturn(map);
		
		
		String ret = search.processUrl();
		assertEquals("init", ret);
		
		map.put("key", null);
		map.put("catid", "9");
		ret = search.processUrl();
		assertEquals("cat", ret);
		
	}

	@Test
	public void testInit() {
		search.setKey("test");
		search.init();
		assertTrue(search.getList().size() > 0);
	}

	@Test
	public void testinitWithCat() {

		Master master = new Master();

		int id = master.getCats().get(0).getId();

		search.setCatId(id);
		search.initWithCat();
		assertTrue(search.getList().size() > 0);
	}

}
