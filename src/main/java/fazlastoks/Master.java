package fazlastoks;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import freela.util.FaceUtils;

@ViewScoped
@ManagedBean
public class Master implements Serializable {

	String searchText;
	String username;
	String password;
	boolean loggedIn;

	public Master() {

		username = "username blabla";

	}

	public String search() {
		FaceUtils.log.fine("searchtext:" + searchText);
		return "urun-arama?faces-redirect=true&key=" + searchText;

	}

	public void Login() {

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

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String test() {
		return "";
	}

	private static final long serialVersionUID = 4790738744214962785L;

}
