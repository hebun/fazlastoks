package fazlastoks;

import static freela.util.FaceUtils.log;
import static java.lang.System.out;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.servlet.http.Part;
import javax.validation.constraints.Size;

import model.Category;
import model.Product;
import model.Productphoto;
import model.State;
import freela.util.Db;
import freela.util.FaceUtils;
import freela.util.Sql;
import freela.util.Sql.Insert;
import freela.util.Sql.Update;

@ViewScoped
@ManagedBean
public class Pro implements Serializable {

	private Product pro;
	private List<Category> cats;

	Map<Integer, Boolean> prosCatsm;
	Map<Integer, Boolean> proState;
	Part file;;
	private List<State> states;
	
	
	private List<String> keywords;

	List<Productphoto> productphotos;
	private String keyword;

	public String addKeyword() {
		try {

			if (this.keywords.size() > 9) {
				FaceUtils.addError("contactform:keyword",
						"En fazla 10 anahtar kelime eklenebilir.");
				return null;
			}

			log.info(keyword);
			if (!keyword.equals("") && !keywords.contains(keyword)
					){
				this.keywords.add(keyword);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public String deleteKeyword(String key) {
		log.info(key);
		for (int i = 0; i < keywords.size(); i++) {
			if (keywords.get(i)==key) {

				this.keywords.remove(i);
			}
		}
		return "";
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@ManagedProperty(value = "#{login}")
	Login login;

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public String deletePhoto(Productphoto cat) {
		log.info(cat.getFile());

		try {
			if (cat.getId() > 0) {
				Db.delete(new Sql.Delete("productphoto").where("id",
						cat.getId()).get());

			}
			for (int i = 0; i < productphotos.size(); i++) {
				Productphoto array_element = productphotos.get(i);
				if (array_element.getFile().equals(cat.getFile())) {
					log.info("removing");
					productphotos.remove(i);
				}
			}

			Files.deleteIfExists(Paths.get(FaceUtils.uploadDir + cat.getFile()));
		} catch (IOException e) {
			log.warning(e.getMessage());
			e.printStackTrace();
		}

		return "";
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
			Productphoto pp = new Productphoto();
			pp.setFile(filex.getName());
			pp.setProductid(pro.getId());

			productphotos.add(pp);
			file = null;
		} catch (IOException e) {
			log.warning(e.getMessage());
			e.printStackTrace();
		}

		return "";
	}

	public Pro() {
		log.setLevel(Level.ALL);
		pro = FaceUtils.getObjectFromGETParam("pid", Product.class, "product");

		cats = Db.select(new Sql.Select().from("category").get(),
				Category.class);
		List<Category> prosCats;
		prosCats = Db.select(
				new Sql.Select().from("category").innerJoin("productcategory")
						.on("category.id", "productcategory.categoryid")
						.where("productid", this.pro.getId()).get(),
				Category.class);

		prosCatsm = new ProHashMap();

		for (Category cat : cats) {
			prosCatsm.put(cat.getId(), false);
		}

		for (Category cat : prosCats) {
			prosCatsm.put(cat.getId(), true);
		}

		states = Db.select(new Sql.Select().from("state").get(), State.class);

		proState = new ProHashMap();
		for (State s : states) {
			proState.put(s.getId(), false);
		}

		List<State> ps = Db.select(
				new Sql.Select().from("state").innerJoin("prostate")
						.on("prostate.stateid", "state.id")
						.where("prostate.productid", pro.getId()).get(),
				State.class);

		for (State s : ps) {
			proState.put(s.getId(), true);
		}
		productphotos = Db.select(
				new Sql.Select().from("productphoto")
						.where("productid", pro.getId()).get(),
				Productphoto.class);

		String keywords2 = pro.getKeywords();
		log.info("from  db" + keywords2);
		if (keywords2 != null && keywords2.length() > 3) {
			keywords = new ArrayList<String>(
					Arrays.asList(keywords2.split(",")));
		} else {
			keywords = new ArrayList<>();
		}
	}

	public String save() {

		try {

			if (!validateInput())
				return null;

			saveOrUpdatePro();
			saveProStates();
			saveProCats();
			saveProPhotos();

			return "urunlerim";
		} catch (Exception e) {
			log.warning(e.getMessage());
			e.printStackTrace();
			return null;
		}

	}

	private void saveProPhotos() {
		if (productphotos.size() > 0) {
			String proCatsql = "insert ignore into productphoto(file,productid) values";

			for (Productphoto pp : productphotos) {

				proCatsql += "('" + pp.getFile() + "'," + pro.getId() + "),";

			}
			proCatsql = proCatsql.substring(0, proCatsql.length() - 1);
			Db.insert(proCatsql);
		}
	}

	private void saveProCats() {
		if (((ProHashMap) prosCatsm).getTrueCount() > 0) {
			String proCatsql = "insert ignore into productcategory(categoryid,productid) values";

			for (Map.Entry<Integer, Boolean> ps : prosCatsm.entrySet()) {
				if (ps.getValue()) {
					proCatsql += "(" + ps.getKey() + "," + pro.getId() + "),";
				}
			}
			proCatsql = proCatsql.substring(0, proCatsql.length() - 1);
			Db.insert(proCatsql);
		}
	}

	private void saveProStates() {
		if (((ProHashMap) proState).getTrueCount() > 0) {
			String prostatesql = "insert ignore into prostate(stateid,productid) values";

			for (Map.Entry<Integer, Boolean> ps : proState.entrySet()) {
				if (ps.getValue()) {
					prostatesql += "(" + ps.getKey() + "," + pro.getId() + "),";
				}
			}
			prostatesql = prostatesql.substring(0, prostatesql.length() - 1);
			Db.insert(prostatesql);
		}
	}

	private void saveOrUpdatePro() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("Y-M-d");

		String dat = dateFormat.format(pro.getExpiredate());
		log.info(pro.getExpiredate().toString());
		int generatedKey = 0;
		int id =  login.user.getId();

		String ks = "";
		for (String s : keywords) {
			ks += s + ",";
		}

		if (pro.getId() <= 0) {
			Insert sql = new Insert("product").add("pname", pro.getPname())
					.add("content", pro.getContent())
					.add("price", pro.getPrice())
					.add("pprice", pro.getPprice()).add("userid", id)
					.add("adet", pro.getAdet()).add("kg", pro.getKg())
					.add("kalem", pro.getKalem()).add("expiredate", dat)
					.add("keywords", ks);
			generatedKey = Db.prepareInsert(sql.prepare().get(), sql.params());
			pro.setId(generatedKey);

		} else {
			Update sql = (Update) new Update("product")
					.add("pname", pro.getPname())
					.add("content", pro.getContent())
					.add("price", pro.getPrice())
					.add("pprice", pro.getPprice()).add("userid", id)
					.add("m3", pro.getM3()).add("kg", pro.getKg())
					.add("kalem", pro.getKalem()).add("keywords", ks)
					.add("expiredate", dat).where("id=", pro.getId());
			generatedKey = Db.prepareInsert(sql.prepare().get(), sql.params());
		}
	}

	public boolean validateInput() {

		if (((ProHashMap) prosCatsm).getTrueCount() > 5) {
			FaceUtils.addError("En fazla 5 tane kategori eklenebilir.");
			return false;
		}
		if (((ProHashMap) proState).getTrueCount() > 5) {
			FaceUtils.addError("En fazla 5 tane durum eklenebilir.");
			return false;
		}
		return true;
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
		if (productphotos.size() > 8) {
			msg = ("En fazla 9 resim yüklenebilir.");
			fail = true;
		}

		if (fail) {

			FaceUtils.addError("contactform:file", msg);
		}
		log.info(fail + "");
		return fail;

	}

	private static final long serialVersionUID = -2018425860394584424L;

	public Map<Integer, Boolean> getProsCatsm() {
		return prosCatsm;
	}

	public void setProsCatsm(Map<Integer, Boolean> prosCatsm) {
		this.prosCatsm = prosCatsm;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public Map<Integer, Boolean> getProState() {
		return proState;
	}

	public void setProState(Map<Integer, Boolean> prosState) {
		this.proState = prosState;
	}

	public List<State> getStates() {
		return states;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}

	public Product getPro() {
		return pro;
	}

	public List<Category> getCats() {
		return cats;
	}

	public void setCats(List<Category> cats) {
		this.cats = cats;
	}

	public void setPro(Product pro) {
		this.pro = pro;
	}

	public List<Productphoto> getProductphotos() {
		return productphotos;
	}

	public void setProductphotos(List<Productphoto> productphotos) {
		this.productphotos = productphotos;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

}