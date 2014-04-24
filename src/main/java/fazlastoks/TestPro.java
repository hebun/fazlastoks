package fazlastoks;

import java.util.Date;

import org.junit.Test;

public class TestPro {

	/**
	 * CURRENT:Admin panel
	 *
	 * 
	 * TEST CASES: 
	 * tab indexes,validations{max fiyat,past time),turkce
	 * karakter{urunlarim.xhmtl,urun.xhtml,master.xhtml}, authfilter for member
	 *  acess(session attr status needed)
	 * 
	 * ISSUES: own product control, 
	 * datepicker language and format,url reseting on
	 * invalidation,product filter in urunlerim, or true state in authfilter
	 * 
	 */

	@Test
	public void testPro() {
		Pro p = new Pro();

		p.preRenderView();
		p.getPro().setExpiredate(new Date());
		p.validateInput();
	}

}
