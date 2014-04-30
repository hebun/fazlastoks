package fazlastoks.admin;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import freela.util.Db;
import freela.util.FaceUtils;
import freela.util.Sql;

@ViewScoped
@ManagedBean(name = "product")
public class Products extends CrudBase implements Serializable {

	List<Map<String, String>> products;



	public List<Map<String, String>> getProducts() {
		return products;
	}

	public void setProducts(List<Map<String, String>> products) {
		this.products = products;
	}
	static int insCount=0;
	public Products() {
		this.table = "product";
		String sql = new Sql.Select("product.*,user.*").from(table).innerJoin("user").on("product.userid", "user.id").get();

		
		products = Db.selectTable(sql);
		FaceUtils.log.info(insCount++ +"");
		super.initColumns();

	}

	private static final long serialVersionUID = 6349455300004482792L;

}