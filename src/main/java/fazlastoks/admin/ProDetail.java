package fazlastoks.admin;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import model.ColumnModel;
import model.Product;
import freela.util.Db;
import freela.util.FaceUtils;
import freela.util.Sql;

@ViewScoped
@ManagedBean(name = "prodet")
public class ProDetail extends CrudBase implements Serializable {

	List<Map<String, String>> categories, states, photos;
	List<String> keywords;
	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	Product pro;
	List<ColumnModel> photoColumns, keywordColumns;

	public List<ColumnModel> getPhotoColumns() {
		return photoColumns;
	}

	public void setPhotoColumns(List<ColumnModel> photoColumns) {
		this.photoColumns = photoColumns;
	}

	public List<ColumnModel> getKeywordColumns() {
		return keywordColumns;
	}

	public void setKeywordColumns(List<ColumnModel> keywordColumns) {
		this.keywordColumns = keywordColumns;
	}

	public ProDetail() {
		setCurrent();

		init();
	}

	private void init() {

		categories = Db.selectTable(new Sql.Select().from("productcategory")
				.innerJoin("category")
				.on("category.id", "productcategory.categoryid")
				.where("productid=", this.pro.getId()).get());
		keywords = Arrays.asList(pro.getKeywords().split(","));
		states = Db.selectTable(new Sql.Select().from("prostate")
				.innerJoin("state").on("state.id", "prostate.stateid")
				.where("productid=", this.pro.getId()).get());
		photos = Db.selectTable(new Sql.Select().from("productphoto")
				.where("productid=", this.pro.getId()).get());

		photoColumns = Db.select(
				new Sql.Select("header,name").from("gridfield")
						.where("tableName=", "photo").and("state=", "0").get(),
				ColumnModel.class);
		keywordColumns = Db.select(
				new Sql.Select("header,name").from("gridfield")
						.where("tableName=", "keyword").and("state=", "0")
						.get(), ColumnModel.class);

	}

	public void setCurrent() {

		this.pro = FaceUtils.getObjectFromGETParam("id", Product.class,
				"product");

	}

	public List<Map<String, String>> getCategories() {
		return categories;
	}

	public void setCategories(List<Map<String, String>> categories) {
		this.categories = categories;
	}



	public List<Map<String, String>> getStates() {
		return states;
	}

	public void setStates(List<Map<String, String>> states) {
		this.states = states;
	}

	public List<Map<String, String>> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Map<String, String>> photos) {
		this.photos = photos;
	}

	public Product getPro() {
		return pro;
	}

	public void setPro(Product pro) {
		this.pro = pro;
	}

	private static final long serialVersionUID = 7897026886187758190L;

}
