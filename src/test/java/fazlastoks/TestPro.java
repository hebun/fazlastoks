package fazlastoks;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import model.Category;
import model.ColumnModel;
import model.Product;
import model.Talep;

import org.junit.Test;

import fazlastoks.admin.Talepler;
import fazlastoks.admin.Users;
import freela.util.ASCIITable;
import freela.util.Db;
import freela.util.Sql;
import freela.util.Sql.Select;

public class TestPro {

	/**
	 * 
	 * CURRENT: 
	 * 
	 * admin CURRENT:{dynamic column(add columns to db), users}
	 * 
	 * TODO: lightbox for pictures in talep, uye profil(with
	 * update),, masterpage bottom activation mail,add product,list/delete/edit product
	 * 
	 * Admin( products,products master-detail, in
	 * detail(prophotos,procates,prokeyword etc.) url filter products, link to
	 * products from users,column editing module)
	 * 
	 * TEST CASES: length vb validation on all input componenents ,tab
	 * indexes,validations{max fiyat,past time),turkce karakter{*.xhtml,
	 * db.gridfield}, authfilter for member acess(session attr status needed)
	 * 
	 * ISSUES:set 'there is no record' warning on paket-detay, user's own product
	 * control,default picture in product detail and results,product filter in
	 * urunlerim, or true state in authfilter, Db.slect<T> the fields that is
	 * not in db,, preparedstatement must be on every user input,make categories in master sessionscoped,
	 * 
	 * NOTES:unnecesary file include in master,beter(persist) unit testing,
	 * activation kontrol on logging(PENDING)
	 * 
	 * THOUGHTS: externalize messages, 	 * build test scenario,
	 * , do test scenario with code
	 * 
	 * SCENARIO:cat search, text search, paket-detay, make talep, register,
	 * login, add product, list product, edit/delete product
	 */

	// @Test
	public void search() {
		Search search = new Search("testing");
		search.setKey("test");
		search.init();
		List<Product> l = search.getList();

		ASCIITable asciiTable = new ASCIITable();

		asciiTable.printTable(l);

	}

	// @Test
	public void testMaster() {

		Master master = new Master();
		master.setSearchText("  ");
		System.out.println(master.search());

		Db.debug = true;
		List<Category> cats;
		List<Product> firsatPakets;

		cats = Db.select(new Sql.Select().from("catcount").get(),
				Category.class);
		for (Category cat : cats) {
			System.out.println(cat.getCname());
		}
		firsatPakets = Db.select(
				new Sql.Select().from("product").innerJoin("firsatproduct")
						.on("product.id", "firsatproduct.productid").get(),
				Product.class);
		for (Product product : firsatPakets) {
			System.out.println(product.getId() + ":" + product.getPname());
		}
	}

	// @Test
	public void prepareStatement() {

		Sql.Select select = (Select) new Select().from("user")
				.where("email", "fff@fff.dff").and("password", "' or ''='")
				.prepare();
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
		Date time = new Date(System.currentTimeMillis());
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
		p.getPro().setExpiredate(new Date(System.currentTimeMillis()));
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

	@Test
	public void talepler() {

		Talepler talepler = new Talepler();

		ASCIITable table = new ASCIITable();

		List<Talep> taleps = talepler.getTaleps();

		table.printTable(taleps);
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
