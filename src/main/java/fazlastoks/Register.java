package fazlastoks;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

import model.User;
import freela.util.App;
import freela.util.Db;
import freela.util.DoMail;
import freela.util.FaceUtils;
import freela.util.Sql;
import freela.util.Sql.Insert;

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
			if (!checkExistingEmail()) {
				return null;
			}
			UUID uuid = UUID.randomUUID();
			user.setUuid(uuid.toString());

			Insert insertUser = new Sql.Insert("user")
					.add("email", user.getEmail())
					.add("password", user.getPassword())
					.add("firmaname", user.getFirmaname())
					.add("uname", user.getUname())
					.add("cepno", user.getCepno())
					.add("sabitno", user.getSabitno())
					.add("uuid", user.getUuid());
			int insertedId = Db.prepareInsert(insertUser.prepare().get(),
					insertUser.params());

			String insertAct = new Sql.Insert("activation").add("code", uuid)
					.add("userid", insertedId)
					.add("tarih", FaceUtils.getFormattedTime() ) .get();

			Db.insert(insertAct);

			sendActivation(uuid.toString());

			app.setCurrentInfoMessage("Aktivasyon Kodunuz Mail Adresinize Gönderildi. "
					+ "Lütfen E-Mail Adresinizi Ziyaret Ediniz.");
			return "bilgi";
		} catch (Exception e) {
			// e.printStackTrace();
			log.severe(e.getMessage());
			FaceUtils.addError("Hata olustu");
			return null;
		}
	}

	private void sendActivation(String uid)
			throws AuthenticationFailedException, MessagingException {
		
		List<Map<String, String>> table = Db.selectTable(new Sql.Select()
				.from("mailcontent").where("name", "activation").get());

		String mc = table.get(0).get("content");

		mc = mc.replaceAll("#link#",
				FaceUtils.getRootUrl() + "/activation?code=" + uid).replaceAll(
				"#fullname#", user.getUname());

		log.fine(mc);

		DoMail.postMail(
				new String[] { "info@fazlastoklar.com", user.getEmail() },
				"fazlastoklar.com aktivasyon", mc, "");

	}

	public boolean checkExistingEmail() {
		int size = Db.selectTable(
				new Sql.Select().from("user").where("email=", user.getEmail())
						.get()).size();
		if (size > 0) {
			FaceUtils.addError("Bu E-mail adresi daha önce kullanıldı.");

			return false;
		}
		return true;
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
