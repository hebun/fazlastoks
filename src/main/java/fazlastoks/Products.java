package fazlastoks;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import freela.util.Db;
import freela.util.Db.SelectCallback;
import freela.util.FaceUtils;
import freela.util.Sql;
import freela.util.Sql.Select;
import model.Product;

@ViewScoped
@ManagedBean
public class Products implements Serializable {

	private List<Product> list;

	@ManagedProperty(value = "#{login}")
	Login login;

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public Products() {
		

	}

	@PostConstruct
	public void init() {
		Sql.Select select = (Select) ((Select) new Select().from("product")
				.where("userid", login.user.getId())).order("id").desc();
		list = Db.select(select.get(), Product.class);
	}

	public List<Product> getList() {

		return list;
	}

	public void setList(List<Product> list) {
		this.list = list;
	}

	public String delete(Product pro) {
		FaceUtils.log.finest("delete pro.id" + pro.getId());

		list.remove(pro);

		Db.delete(new Sql.Delete("product").where("id=", pro.getId()).get());

		return null;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2018425860394584424L;

}