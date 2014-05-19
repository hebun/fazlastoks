package fazlastoks.admin;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import model.State;
import freela.util.Db;
import freela.util.FaceUtils;
import freela.util.Sql;

@ViewScoped
@ManagedBean(name = "product")
public class Products extends CrudBase implements Serializable {

	List<Map<String, String>> products;

	public void addToFirsats(Map<String, String> pr) {
		int id = Db.insert(new Sql.Insert("firsatproduct").add("productid",
				pr.get("id")).get());

		pr.put("firsatid", id+"");

		if (id > 0) {
			super.success("Ürün haftanın fırsat paketlerine ekledi.");
		} else {
			super.warn("Hata olustu.");
		}

	}

	public void removeFromFirsats(Map<String, String> pr) {
		int id = Db.delete(new Sql.Delete("firsatproduct").where("productid",
				pr.get("id")).get());
		pr.put("firsatid", "NULL");
		if (id > 0) {
			super.success("Ürün haftanın fırsat paketlerinden çıkarıldı.");
		} else {
			super.warn("Hata oluştu.");
		}

	}

	public void delete(Map<String, String> pr) {

		int ret = Db.delete(new Sql.Delete(table).where("id", pr.get("id"))
				.get());
		hasMessage = true;
		if (ret == 0) {

			messageType = "alert_warning";
			message = "Kayıt silinemedi. Bu ürüne atanmış başka kayıtlar(resim,kategori vb) var."
					+ " Öncelikle onları silmelisiniz.";
			return;
		}
		messageType = "alert_success";
		message = "Kayıt başarıyla silindi.";
		products.remove(pr);
	}

	public List<Map<String, String>> getProducts() {
		return products;
	}

	public void setProducts(List<Map<String, String>> products) {
		this.products = products;
	}

	static int insCount = 0;

	public Products() {
		this.table = "product";
		String sql = new Sql.Select(
				"product.*,user.*,firsatproduct.id as firsatid").from(table)
				.innerJoin("user").on("product.userid", "user.id")
				.leftJoin("firsatproduct")
				.on("firsatproduct.productid", "product.id").get();

		products = Db.selectTable(sql);

		super.initColumns();

	}

	private static final long serialVersionUID = 6349455300004482792L;

}