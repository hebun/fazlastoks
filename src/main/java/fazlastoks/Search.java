package fazlastoks;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import model.Product;
import freela.util.Db;
import freela.util.FaceUtils;
import freela.util.Sql;
import freela.util.Sql.Select;

@RequestScoped
@ManagedBean
public class Search implements Serializable {

	private List<Product> list;

	private String key;

	private int catId;

	public Search(String testing) {

	}

	public Search() {
		processUrl();
	}

	public String processUrl() {
		this.key = FaceUtils.getGET("key");
		String searchText = this.key;
		if (searchText != null && !searchText.equals("")
				&& searchText.length() > 2) {
			init();
			return "init";
		} else {
			try {
				this.catId = Integer.parseInt(FaceUtils.getGET("catid"));

				initWithCat();
				return "cat";
			} catch (NumberFormatException nfe) {
				return null;
			}
		}
	}

	public void initWithCat() {
		Sql.Select sql = (Select) new Sql.Select().from("product").as("p")
				.innerJoin("productcategory").as("pc")
				.on("p.id", "pc.productid").where("pc.categoryid", catId).prepare();
		
		list = Db.preparedSelect(sql.get(),sql.params(), Product.class);
	}

	public void init() {
		String value = "%" + key + "%";
		Sql.Select sql = (Select) new Sql.Select().from("product")
				.where("pname like ", value).or("content like ", value)
				.or("keywords like ", value).prepare();

		list = Db.preparedSelect(sql.get(),sql.params() ,Product.class);

		FaceUtils.log.info(sql.get());

	}

	public List<Product> getList() {
		return list;
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setList(List<Product> list) {
		this.list = list;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2018425860394584424L;

}