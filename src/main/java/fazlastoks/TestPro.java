package fazlastoks;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class TestPro {

	/**
	 * TEST CASES:
	 * tab indexes,validations{max fiyat,past time),turkce karakter
	 * 
	 * ISSUES:
	 * datepicker language and format,url resetin on invalidation
	 *  
	 */
	
	
	@Test
	public void testPro() {
		Pro p=new Pro();
		
		p.preRenderView();
		p.getPro().setExpiredate(new Date());
		p.validateInput();
	}

}
