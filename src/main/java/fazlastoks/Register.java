package fazlastoks;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

import javax.enterprise.inject.New;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import freela.util.App;
import freela.util.Db;
import freela.util.FaceUtils;
import freela.util.Sql;
import model.User;

@ViewScoped
@ManagedBean
public class Register implements Serializable {
	User user;
	@ManagedProperty(value = "#{app}")
	App app;

	String rePassword;

	Logger log = FaceUtils.log;

	public Register() {
		user = new User();
	}

	public String save() {

		if (!rePassword.equals(user.getPassword())) {
			FaceUtils.addError("Şifreler aynı değil");
			return null;
		}

		try {
			int size = Db.selectTable(
					new Sql.Select().from("user")
							.where("email=", user.getEmail()).get()).size();
			if (size > 0) {
				FaceUtils.addError("Bu E-mail adresi daha önce kullanıldı.");

				return null;
			}

			String insertUser = new Sql.Insert("user")
					.add("email", user.getEmail())
					.add("password", user.getPassword())
					.add("firmaname", user.getFirmaname())
					.add("uname", user.getUname())
					.add("cepno", user.getCepno())
					.add("sabitno", user.getSabitno()).get();
			int insertedId = Db.insert(insertUser);
			
			Date time = Calendar.getInstance().getTime();
			SimpleDateFormat dateFormat = new SimpleDateFormat("Y-m-d H:m:s");
			String insertAct = new Sql.Insert("activation")
					.add("code", UUID.randomUUID()).add("userid", insertedId)
					.add("tarih", dateFormat.format(time)).get();

			Db.insert(insertAct);
			app.setCunrrentInfoMessage("Aktivasyon Kodunuz Mail Adresinize Gönderildi. Lütfen E-Mail Adresinizi Ziyaret Ediniz.");
			return "bilgi";
		} catch (Exception e) {
			log.severe(e.getMessage());
			FaceUtils.addError("Hata olustu");
			return null;
		}
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public User getUser() {
		return user;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private static final long serialVersionUID = 5950439051586912211L;

}
