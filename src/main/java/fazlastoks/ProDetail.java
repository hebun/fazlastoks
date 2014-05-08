package fazlastoks;

import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import freela.util.App;
import freela.util.Db;
import freela.util.FaceUtils;
import freela.util.Sql;
import freela.util.Sql.Insert;
import model.Product;
import model.Talep;

@ViewScoped
@ManagedBean(name = "prodetail")
public class ProDetail implements Serializable {

	Product pro;
	String proStates;
	Talep talep;
	
	@ManagedProperty(value = "#{app}")
	private App app;

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public ProDetail() {
		talep = new Talep();

		this.pro = FaceUtils.getObjectFromGETParam("id", Product.class,
				"product");

		proStates = "";

		String sql = new Sql.Select().from("prostate").as("ps")
				.innerJoin("state").as("s").on("ps.stateid", "s.id")
				.where("ps.productid", pro.getId()).get();

		Db.SelectCallbackLoop callback = new Db.SelectCallbackLoop() {
			@Override
			public void callback(Map<String, String> map) {
				proStates += map.get("sname") + ",";

			}
		};
		Db.select(sql, callback);
		if (proStates.length() > 0) {
			if (proStates.endsWith(",")) {
				proStates = proStates.substring(0, proStates.length() - 1);
			}
		}

	}

	public String saveTalep() {

		if (pro.getId() == 0) {
			return null;
		}

		Sql.Insert insert = new Insert("talep").add("productid", pro.getId())
				.add("userid", pro.getUserid()).add("name", talep.getName())
				.add("email", talep.getEmail()).add("gsm", talep.getGsm())
				.add("notes", talep.getNotes()).prepare();
		
		Db.prepareInsert(insert.get(), insert.params());

		app.setCurrentInfoMessage("Talebiniz başarıyla iletildi.");
		return "bilgi";
	

	}

	public Talep getTalep() {
		return talep;
	}

	public void setTalep(Talep talep) {
		this.talep = talep;
	}

	public Product getPro() {
		return pro;
	}

	public void setPro(Product pro) {
		this.pro = pro;
	}

	public String getProStates() {
		return proStates;
	}

	public void setProStates(String proStates) {
		this.proStates = proStates;
	}

	private static final long serialVersionUID = 2647447812313684383L;

}
