package fazlastoks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.logging.Level;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import freela.util.FaceUtils;

public class LoginTest {

	static Login login;

	@Mock
	FacesContext context;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		login = new Login();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

		context = ContextMocker.mockFacesContext();
		FaceUtils.log.setLevel(Level.INFO);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLogin() {

		login.setUsername("fakeusername");
		login.setPassword("wrongpassword");
		String result = login.login();

		assertNull(result);
		assertFalse(login.isLoggedIn());

		login.setUsername("bla@ma.com");
		login.setPassword("asdf");
		result = login.login();

		assertNull(result);
		assertFalse(login.isLoggedIn());

		login.setUsername("fff@fff.dff");
		login.setPassword("qwer");
		result = login.login();

		assertEquals("index", result);
		assertTrue(login.isLoggedIn());

	}

	@Test
	public void testLogout() {
	
	}

}
