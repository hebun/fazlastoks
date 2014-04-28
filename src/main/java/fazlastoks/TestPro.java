package fazlastoks;

import java.util.Date;
import java.util.List;

import model.ColumnModel;
import model.Talep;

import org.junit.Test;

import fazlastoks.admin.Talepler;
import freela.util.ASCIITable;
import freela.util.App;

public class TestPro {

	/**
	 * CURRENT:Admin panel, users
	 * 
	 *
	 * 
	 * TEST CASES: length vb v alidation on all input componenents tab
	 * indexes,validations{max fiyat,past time),turkce
	 * karakter{urunlarim.xhmtl,urun.xhtml,master.xhtml}, authfilter for member
	 * acess(session attr status needed)
	 * 
	 * ISSUES: user's own product control, datepicker language and format,url
	 * reseting on invalidation,product filter in urunlerim, or true state in
	 * authfilter
	 * 
	 */

	// @Test
	public void testPro() {
		Pro p = new Pro();

		p.preRenderView();
		p.getPro().setExpiredate(new Date());
		p.validateInput();
	}

	@Test
	public void talepler() {

		Talepler talepler = new Talepler();

		ASCIITable table = new ASCIITable();

		List<Talep> taleps = talepler.getTaleps();
		String[][] data = new String[taleps.size()][];

		int k = 0;
		for (Talep t : taleps) {
			data[k++] = new String[] { t.getName(), t.getEmail(), t.getGsm(),
					t.getNotes(), t.getProductid() + "" };
		}
		List<ColumnModel> columns2 = talepler.getColumns();

		String[] cols = getHeader(columns2);

		table.printTable(cols, data);
	}

	private String[] getHeader(List<ColumnModel> columns2) {
		int k;
		List<ColumnModel> columns = columns2;

		String[] cols = new String[columns.size()];
		k = 0;
		for (ColumnModel columnModel : columns) {
			cols[k++] = columnModel.getHeader().toString();

		}
		return cols;
	}
}
