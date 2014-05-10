package fazlastoks;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

import model.Category;
import model.Product;
import model.Productphoto;
import model.State;
import freela.util.Db;
import freela.util.FaceUtils;
import freela.util.Sql;

@ViewScoped
@ManagedBean
public class Pro implements Serializable {

	private Product pro;
	private List<Category> cats;

	Map<Integer, Boolean> prosCatsm;
	Map<Integer, Boolean> proState;
	Part file;
	Logger log = FaceUtils.log;

	List<Productphoto> productphotos;

	public List<Productphoto> getProductphotos() {
		return productphotos;
	}

	public void setProductphotos(List<Productphoto> productphotos) {
		this.productphotos = productphotos;
	}

	public String upload() {
		log.severe(file.getContentType() + ":" + FaceUtils.getFilename(file)
				+ ":" + file.getSize());
		if (file == null)
			return "";
		try {
			final File filex = File.createTempFile("pre",
					FaceUtils.getFilename(file), new File(FaceUtils.uploadDir));

			String absolutePath = filex.getAbsolutePath();
			file.write(absolutePath);
			Productphoto pp = new Productphoto();
			pp.setFile(filex.getName());
			pp.setProduct(pro.getId());

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

		prosCatsm = new HashMap<>();
		for (Category cat : cats) {
			prosCatsm.put(cat.getId(), false);
		}

		for (Category cat : prosCats) {
			System.out.println(cat.getId());
			prosCatsm.put(cat.getId(), true);
		}

		states = Db.select(new Sql.Select().from("state").get(), State.class);

		proState = new HashMap<>();
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
		productphotos = new ArrayList<Productphoto>();
	}

	public String save() {

		for (Map.Entry<Integer, Boolean> m : prosCatsm.entrySet()) {
			System.out.println(m.getKey() + ":" + m.getValue());
		}

		FaceUtils.log.info("save called pro.id:" + pro.getId());

		// if (!validateInput()) return null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("Y-m-d");
		String dat = dateFormat.format(pro.getExpiredate());
		Sql sql = null;

		if (pro.getId() <= 0) {
			sql = new Sql.Insert("product").add("pname", pro.getPname())
					.add("content", pro.getContent()).add("expiredate", dat);
		} else {
			sql = new Sql.Update("product").add("pname", pro.getPname())
					.add("content", pro.getContent()).add("expiredate", dat)
					.where("id=", pro.getId());
		}

		Db.insert(sql.get());

		return "urunlerim";

	}

	public boolean validateInput() {

		if (pro.getExpiredate().compareTo(new Date()) <= 0) {
			System.out.println(pro.getExpiredate().toString() + "---"
					+ new Date().toString());
			String msg = "Lütfen ileri bir tarih seçin!";
			FaceUtils.addError(msg);

			return false;
		}
		return true;
	}

	public void validateFile(FacesContext ctx, UIComponent comp, Object value) {

		Part file = (Part) value;
		boolean fail = false;
		String msg = null;
		if (file.getSize() > 1_000_000) {
			msg=("En fazla 1 MB boyuntunda dosya yüklenebilir.");
			fail = true;
		}
		if (!"image".equals(file.getContentType().split("/")[0])) {
			msg=("Sadece resim dosyaları yüklenebilir.");
			fail = true;
		}
		if(fail)
		throw new ValidatorException(new FacesMessage(msg));
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

	private List<State> states;

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
}