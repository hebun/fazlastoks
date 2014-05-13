package fazlastoks;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import freela.util.Db;
import freela.util.FaceUtils;
import freela.util.Sql;
import model.Product;

@ViewScoped
@ManagedBean(name = "hom")
public class Home implements Serializable {

	List<Product> firsatPakets;

	@ManagedProperty(value = "#{login}")
	Login login;

	public Home() {
		loadFirsats();

	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	@PostConstruct
	public void init() {
		if (login.isLoggedIn()) {
			FaceUtils.log.fine("logged in true");
		}
	}

	public void loadFirsats() {
		firsatPakets = Db.select(
				new Sql.Select().from("product").innerJoin("firsatproduct")
						.on("product.id", "firsatproduct.productid").get(),
				Product.class);

	

	}

	public List<Product> getFirsatPakets() {
		return firsatPakets;
	}

	public void setFirsatPakets(List<Product> firsatPakets) {
		this.firsatPakets = firsatPakets;
	}

	private static final long serialVersionUID = -7626324143300709074L;

}
