package fazlastoks;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import freela.util.App;
import freela.util.AuthFilter;
import freela.util.Db;
import freela.util.FaceUtils;
import freela.util.Sql;
import freela.util.Sql.Select;
import freela.util.Sql.Update;
import model.User;

@SessionScoped
@ManagedBean
public class Login implements Serializable {
	String username;
	String password;
	private boolean loggedIn;
	User user;
	private boolean remember;

	public boolean isRemember() {
		return remember;
	}

	public void setRemember(boolean remember) {
		this.remember = remember;
	}

	String newPassword;
	String reNewPassword;

	@ManagedProperty(value = "#{app}")
	App app;

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public String update() {

		updateUser();

		app.setCurrentInfoMessage("Profiliniz başarıyla güncellendi.");

		return "bilgi";

	}

	public void updateUser() {
		Update prepare = (Update) new Sql.Update("user")
				.add("uname", user.getUname())
				.add("firmaname", user.getFirmaname())
				.add("sabitno", user.getSabitno())
				.add("cepno", user.getCepno())
				.add("address", user.getAddress()).add("city", user.getCity())
				.add("vergidaire", user.getVergidaire())
				.add("vergino", user.getVergino())
				.add("website", user.getWebsite()).where("id", user.getId())
				.prepare();

		Db.prepareInsert(prepare.get(), prepare.params());
	}

	public Login() {
		Object object = FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("user");
		if (object != null) {
			this.user = (User) object;
			this.loggedIn = true;
		}
	}

	public String login() {
		Sql.Select select = (Select) new Select().from("user")
				.where("email", username).and("password", password);
		List<Map<String, String>> table = Db.preparedSelect(select.prepare()
				.get(), select.params());

		if (table.size() > 0) {

			if (table.get(0).get("state").toString().equals("PENDING")) {
				FaceUtils.addError("Hesabınız Aktif Değil!");
				loggedIn = false;
				return null;
			}

			user = Db.preparedSelect(select.prepare().get(), select.params(),
					User.class).get(0);

			loggedIn = true;

			if (remember) {
				FaceUtils.addCookie("remember", user.getUuid(), 100_000_000);

				FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().put("user", user);
			}

			return "index";
		} else {
			FaceUtils.addError("Kullanıcı ve/veya şifre yanlış.");
			loggedIn = false;
			return null;
		}
	}

	public String logout() {
		username = "";
		password = "";
		user = null;
		loggedIn = false;
		FacesContext.getCurrentInstance().getExternalContext()
				.invalidateSession();
		FaceUtils.removeCookie((HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse(),
				"remember");
		return "index?faces-redirect=true";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public String getReNewPassword() {
		return reNewPassword;
	}

	public void setReNewPassword(String reNewPassword) {
		this.reNewPassword = reNewPassword;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.loggedIn = isLoggedIn;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	private static final long serialVersionUID = -8938217548612577279L;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String test() {

		return "";

	}
}
