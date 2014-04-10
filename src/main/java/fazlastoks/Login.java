package fazlastoks;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@SessionScoped
@ManagedBean
public class Login implements Serializable {

	private static final long serialVersionUID = -8938217548612577279L;
	String username;
	String password;

	public Login() {
		username = "kullanici adi:";
		System.out.println("cons called");
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
