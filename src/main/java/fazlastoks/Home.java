package fazlastoks;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import freela.util.Db;
import freela.util.Sql;
import model.Product;

@ViewScoped
@ManagedBean(name="hom")
public class Home implements Serializable {

	List<Product> firsatPakets;

	public Home() {
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
