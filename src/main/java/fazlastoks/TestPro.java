package fazlastoks;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import model.ColumnModel;
import model.Talep;

import org.junit.Test;

import fazlastoks.admin.Talepler;
import fazlastoks.admin.Users;
import freela.util.ASCIITable;
import freela.util.Db;
import freela.util.Sql;
import freela.util.Sql.Insert;
import freela.util.Sql.Select;

public class TestPro {

	/**
	 * 
	 * CURRENT: register with test driven
	 * 
	 * admin CURRENT:{dynamic column(add columns to db), users}
	 * 
	 * TODO: register,activation mail, login,search results,make talep
	 * 
	 * 
	 * Admin( products,products master-detail, in
	 * detail(prophotso,prokates,prokeyword etc.) url filter products, link to
	 * products from users,column editing module)
	 * 
	 * TEST CASES: length vb validation on all input componenents ,tab
	 * indexes,validations{max fiyat,past time),turkce karakter{*.xhtml,
	 * db.gridfield}, authfilter for member acess(session attr status needed)
	 * 
	 * ISSUES:multiple handler added to logger, user's own product control,
	 * datepicker language and format,url reseting on invalidation,product
	 * filter in urunlerim, or true state in authfilter
	 * 
	 */
	@Test
	public void prepareStatement() {

		Sql.Select select = (Select) new Select().from("user")
				.where("email", "fff@fff.dff").and("password", "' or ''='").prepare();
		System.out.println(select.get());

		for (String str : select.params()) {
			System.out.println(str);
		}

		List<Map<String, String>> table = Db.preparedSelect(select.get(),
				select.params());

		for (Map<String, String> map : table) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				System.out.println(entry.getKey() + ":" + entry.getValue());
			}
			System.out.println();
		}
	}

	// @Test
	public void testRegister() {
		int insertedId = 3;
		Date time = Calendar.getInstance().getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("Y-M-d H:m:s");
		String insertAct = new Sql.Insert("activation")
				.add("code", UUID.randomUUID()).add("userid", insertedId)
				.add("tarih", dateFormat.format(time)).get();
		Db.insert(insertAct);
	}

	// @Test
	public void testPro() {
		Pro p = new Pro();

		p.preRenderView();
		p.getPro().setExpiredate(new Date());
		p.validateInput();
	}

	// @Test
	public void products() {
		fazlastoks.admin.Products user = new fazlastoks.admin.Products();
		ASCIITable table = new ASCIITable();

		List<Map<String, String>> users = user.getProducts();
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

		String[] cols = users.get(0).keySet().toArray(new String[] {});

		table.printTable(cols, data);

	}

	// @Test
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

		List<ColumnModel> columns2 = user.getColumns();

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
