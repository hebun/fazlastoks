package fazlastoks;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import model.User;

@SessionScoped
@ManagedBean
public class Login implements Serializable {
	// TODO: db design,managed bean design,

	String username;
	String password;
	boolean loggedIn;
	User user;

	public Login() {
		loggedIn = true;

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
