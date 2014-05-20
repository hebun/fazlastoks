package fazlastoks.admin;

import static freela.util.FaceUtils.log;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;

import model.Category;
import model.ColumnModel;
import model.Product;
import model.Productphoto;
import freela.util.ASCIITable;
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
	boolean addCategory, addState, addKeyword, addPhoto;

	public boolean isAddPhoto() {
		return addPhoto;
	}

	public void setAddPhoto(boolean addPhoto) {
		this.addPhoto = addPhoto;
	}

	String selectedCatId, selectedStateId, selectedKeyword;
	Part file;

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public boolean isAddKeyword() {
		return addKeyword;
	}

	public void setAddKeyword(boolean addKeyword) {
		this.addKeyword = addKeyword;
	}

	public String getSelectedKeyword() {
		return selectedKeyword;
	}

	public void setSelectedKeyword(String selectedKeyword) {
		this.selectedKeyword = selectedKeyword;
	}

	public String getSelectedCatId() {
		return selectedCatId;
	}

	public void setSelectedCatId(String selectedCatId) {
		this.selectedCatId = selectedCatId;
	}

	public String addKeywordToProduct() {
		try {
			if (keywords == null) {
				keywords = new ArrayList<String>();
			}

			keywords.add(selectedKeyword);

			int insertId = updateKeywordColumn();
			if (insertId > 0) {

				super.success("Kelime Eklendi");
			} else {
				super.errorOccured();
				;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public boolean validateFile() {

		boolean fail = false;
		String msg = null;
		if (file.getSize() > 1_000_000) {
			msg = ("En fazla 1 MB boyuntunda dosya yüklenebilir.");
			fail = true;
		}
		if (!"image".equals(file.getContentType().split("/")[0])) {
			msg = ("Sadece resim dosyaları yüklenebilir.");
			fail = true;
		}
		if (photos.size() > 8) {
			msg = ("En fazla 9 resim yüklenebilir.");
			fail = true;
		}

		if (fail) {

			FaceUtils.addError("f:msgs", msg);
		}
		log.info(fail + "");
		return fail;

	}

	public String upload() {

		if (file == null)
			return "";

		if (validateFile())
			return null;

		try {
			final File filex = File.createTempFile("pre",
					FaceUtils.getFilename(file), new File(FaceUtils.uploadDir));

			String absolutePath = filex.getAbsolutePath();
			file.write(absolutePath);

			Db.insert(new Sql.Insert("productphoto")
					.add("file", filex.getName()).add("productid", pro.getId())
					.get());
			photos = Db.selectTable(new Sql.Select().from("productphoto")
					.where("productid=", this.pro.getId()).get());

			file = null;
		} catch (IOException e) {
			log.warning(e.getMessage());
			e.printStackTrace();
		}

		return "";
	}

	public String addStateToProduct() {
		int insertId = Db.insert(new Sql.Insert("prostate")
				.add("productid", pro.getId()).add("stateid", selectedStateId)
				.get());
		if (insertId > 0) {

			states = Db.selectTable(new Sql.Select().from("prostate")
					.innerJoin("state").on("state.id", "prostate.stateid")
					.where("productid=", this.pro.getId()).get());

			super.success("Durum Eklendi");
		} else {
			super.error("Bu durum zaten eklenmiş.");
		}
		return null;
	}

	public boolean isAddState() {
		return addState;
	}

	public void setAddState(boolean addState) {
		this.addState = addState;
	}

	public String getSelectedStateId() {
		return selectedStateId;
	}

	public void setSelectedStateId(String selectedStateId) {
		this.selectedStateId = selectedStateId;
	}

	public String addCategoryToProduct() {
		System.out.println(selectedCatId);
		int insertId = Db.insert(new Sql.Insert("productcategory")
				.add("productid", pro.getId()).add("categoryid", selectedCatId)
				.get());
		if (insertId > 0) {

			categories = Db.selectTable(new Sql.Select()
					.from("productcategory").innerJoin("category")
					.on("category.id", "productcategory.categoryid")
					.where("productid=", this.pro.getId()).get());

			super.success("Kategori Eklendi");
		} else {
			super.error("Bu kategori zaten eklenmiş.");
		}
		return null;

	}

	public boolean isAddCategory() {
		return addCategory;
	}

	public void setAddCategory(boolean addCategory) {
		this.addCategory = addCategory;
	}

	public String deleteKeyword(String rec) {
		keywords.remove(rec);
		int generatedKey = updateKeywordColumn();
		if (generatedKey > 0) {
			super.success("Anahtar Kelime Silindi.");
		} else {
			super.errorOccured();
		}
		return null;
	}

	private int updateKeywordColumn() {
		String ks = "";
		for (String s : keywords) {
			ks += s + ",";
		}
		int generatedKey = Db.update(new Sql.Update("product")
				.add("keywords", ks).where("id", pro.getId()).get());
		return generatedKey;
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
		return null;
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
		return null;
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
		return null;
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
		try {
			setCurrent();

			init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
