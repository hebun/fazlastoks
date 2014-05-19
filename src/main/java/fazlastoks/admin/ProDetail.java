package fazlastoks.admin;

import static freela.util.FaceUtils.log;

import java.io.Serializable;
import java.util.ArrayList;
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
import freela.util.Sql.Update;

@ViewScoped
@ManagedBean(name = "prodet")
public class ProDetail extends CrudBase implements Serializable {

	List<Map<String, String>> categories, states, photos;
	List<String> keywords;
	Product pro;
	List<ColumnModel> photoColumns, keywordColumns;

	public String deleteKeyword(String rec) {
		keywords.remove(rec);
		String ks = "";
		for (String s : keywords) {
			ks += s + ",";
		}
		int generatedKey = Db.update(new Sql.Update("product")
				.add("keywords", ks).where("id", pro.getId()).get());
		if (generatedKey > 0) {
			super.success("Anahtar Kelime Silindi.");
		} else {
			super.errorOccured();
		}
		return "";
	}

	public String deleteCat(Map<String, String> rec) {
		categories.remove(rec);
		int generatedKey = Db.delete(new Sql.Delete("productcategory").where(
				"id", rec.get("id")).get());
		if (generatedKey > 0) {
			super.success("Kategori Silindi.");
		} else {
			super.errorOccured();
		}
		return "";
	}

	public String deleteState(Map<String, String> rec) {
		states.remove(rec);
		int generatedKey = Db.delete(new Sql.Delete("prostate").where("id",
				rec.get("id")).get());
		if (generatedKey > 0) {
			super.success("Durum Silindi.");
		} else {
			super.errorOccured();
		}
		return "";
	}

	public String deletePhoto(Map<String, String> rec) {
		photos.remove(rec);
		int generatedKey = Db.delete(new Sql.Delete("productphoto").where("id",
				rec.get("id")).get());
		if (generatedKey > 0) {
			super.success("Resim Silindi.");
		} else {
			super.errorOccured();
		}
		return "";
	}

	public String updatePro() {

		FaceUtils.log.info("updatecalled");
		Update sql = (Update) new Update("product")
				.add("pname", pro.getPname())
				.add("content", pro.getContent())
				.add("price", pro.getPrice())
				.add("pprice", pro.getPprice())
				.add("m3", pro.getM3())
				.add("kg", pro.getKg())
				.add("kalem", pro.getKalem())
				.add("expiredate",
						FaceUtils.getFormattedTime(pro.getExpiredate()))
				.where("id=", pro.getId());
		int generatedKey = Db.prepareInsert(sql.prepare().get(), sql.params());
		FaceUtils.log.info(generatedKey + "");
		if (generatedKey > 0) {
			super.success("Ürün Güncellendi.");
		} else {
			super.errorOccured();
		}

		return null;

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

		log.fine(pro.getKeywords().length() + "");

		String[] split = pro.getKeywords().split(",");
		if (split.length > 0) {
			if (split[0].length() > 1) {
				keywords = new ArrayList<String>(Arrays.asList(split));
				System.out.println(keywords.size());
			}
		}

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

	public List<String> getKeywords() {
		return keywords;
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

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	private static final long serialVersionUID = 7897026886187758190L;

}
