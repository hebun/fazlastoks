package fazlastoks;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import model.Category;
import model.Product;
import model.Productphoto;
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

	private Map<Integer, String> photos;

	private boolean catSearch;

	private Category category;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public boolean isCatSearch() {
		return catSearch;
	}

	public void setCatSearch(boolean catSearch) {
		this.catSearch = catSearch;
	}

	public Search(String testing) {
		catSearch = false;
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
				.on("p.id", "pc.productid").where("pc.categoryid", catId)
				.prepare();

		list = Db.preparedSelect(sql.get(), sql.params(), Product.class);

		List<Category> cates = Db.select(new Sql.Select().from("category")
				.where("id", catId).get(), Category.class);
		if (cates.size() > 0) {
			this.catSearch = true;
			this.category = cates.get(0);
		}

		loadPhotos();
	}

	public void init() {
		String value = "%" + key + "%";
		Sql.Select sql = (Select) new Sql.Select().from("product")
				.where("pname like ", value).or("content like ", value)
				.or("keywords like ", value).prepare();

		list = Db.preparedSelect(sql.get(), sql.params(), Product.class);

		loadPhotos();

	}

	private void loadPhotos() {
		String proids = "(";

		for (Product p : list) {
			proids += p.getId() + ",";
		}
		proids = proids.substring(0, proids.length() - 1) + ")";
		List<Productphoto> productphotos;
		productphotos = Db
				.select("select * from productphoto where productid in "
						+ proids + " ", Productphoto.class);
		photos = new HashMap<>();
		for (Productphoto pp : productphotos) {

			photos.put(pp.getProductid(), pp.getFile());
		}
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

	public Map<Integer, String> getPhotos() {
		return photos;
	}

	public void setPhotos(Map<Integer, String> photos) {
		this.photos = photos;
	}

}