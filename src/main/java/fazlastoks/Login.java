package fazlastoks;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import freela.util.Db;
import freela.util.FaceUtils;
import freela.util.Sql;
import freela.util.Sql.Select;
import model.User;

@SessionScoped
@ManagedBean
public class Login implements Serializable {
	// TODO: db design,managed bean design,

	String username;
	String password;
	private boolean loggedIn;
	User user;

	public Login() {
		// loggedIn = true;

	}

	public String login() {
		Sql.Select select = (Select) new Select().from("user")
				.where("email", username).and("password", password);
		String normalSelect = select.get();

		List<Map<String, String>> table = Db.preparedSelect(select.prepare()
				.get(), select.params());

		if (table.size() > 0) {

			if (table.get(0).get("state").toString().equals("PENDING")) {
				FaceUtils.addError("Hesabınız Aktif Değil!");
				loggedIn = false;
				return null;
			}

			user = Db.select(normalSelect, User.class).get(0);

			loggedIn = true;
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
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
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

	public void setLoggedIn(boolean isLoggedIn) {
		this.loggedIn = isLoggedIn;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
