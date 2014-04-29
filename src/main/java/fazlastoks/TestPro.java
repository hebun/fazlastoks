package fazlastoks;

import java.util.Date;
import java.util.List;
import java.util.Map;

import model.ColumnModel;
import model.Talep;

import org.junit.Test;

import fazlastoks.admin.Talepler;
import fazlastoks.admin.Users;
import freela.util.ASCIITable;

public class TestPro {

	/**
	 * 
	 * CURRENT:Admin panel, dynamic column(add columns to db), users
	 * 
	 *TODO: products,url filter products, link to products from users,column editing module
	 * 
	 * TEST CASES: length vb v alidation on all input componenents ,tab
	 * indexes,validations{max fiyat,past time),turkce
	 * karakter{*.xhtml, db.gridfield}, authfilter for member
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
	public void users() {
		Users user = new Users();
		ASCIITable table = new ASCIITable();

		List<Map<String, String>> users = user.getUsers();
		String[][] data = new String[users.size()][];

		int k = 0;
		for (Map<String, String> t : users) {

			String[] dizi = new String[t.values().size()];
			int i = 0;
			for (String m : t.values()) {
				dizi[i++] = m;
			
			}
			data[k++] = dizi;
		}

		List<ColumnModel> columns2 =  user.getColumns();

		String[] cols = getHeader(columns2);

		table.printTable(cols, data);

	}

	// @Test
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
