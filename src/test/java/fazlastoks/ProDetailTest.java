package fazlastoks;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import model.Talep;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import freela.util.App;
import freela.util.Db;

public class ProDetailTest {
	@Mock
	FacesContext context;

	private ExternalContext externalContext;

	private Map<String, String> map;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Db.debug = true;

		context = ContextMocker.mockFacesContext();
		externalContext = mock(ExternalContext.class);

		when(context.getExternalContext()).thenReturn(externalContext);
		map = new HashMap<>();

		when(externalContext.getRequestParameterMap()).thenReturn(map);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testProDetail() {
		map.put("id", "c5x");

		ProDetail detail = new ProDetail();

		System.out.println(detail.pro.getPname());
		assertNull(detail.pro.getPname());

		Home h = new Home();

		int id = h.getFirsatPakets().get(0).getId();
		map.put("id", id + "");
		detail = new ProDetail();
		assertNotNull(detail.pro.getPname());

	}

	@Test
	public void testSave() {
		ProDetail detail;
		Home h = new Home();

		int id = h.getFirsatPakets().get(0).getId();
		map.put("id", "" + id + "");
		detail = new ProDetail();

		Talep tal = new Talep() {
			{
				setName("testname");
				setEmail("email");
				setGsm("gsm");
				setNotes("blablab");
			}

		};
		detail.setTalep(tal);
		detail.setApp(new App());
		//String ret = detail.saveTalep();
		//assertEquals("bilgi", ret);
		System.out.println(detail.getApp().getCunrrentInfoMessage());
	}

}
