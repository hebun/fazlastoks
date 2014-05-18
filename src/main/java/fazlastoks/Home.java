package fazlastoks;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import freela.util.Db;
import freela.util.FaceUtils;
import freela.util.Sql;
import model.Product;
import model.Productphoto;

@ViewScoped
@ManagedBean(name = "hom")
public class Home implements Serializable {

	public Map<Integer, String> getPhotos() {
		return photos;
	}

	public void setPhotos(Map<Integer, String> photos) {
		this.photos = photos;
	}

	List<Product> firsatPakets;


	
	private Map<Integer, String> photos;

	public Home() {
		loadFirsats();
		loadPhotos();
	}



	@PostConstruct
	public void init() {
	
	}

	public void loadFirsats() {
		firsatPakets = Db.select(
				new Sql.Select().from("product").innerJoin("firsatproduct")
						.on("product.id", "firsatproduct.productid").get(),
				Product.class);

	}

	private void loadPhotos() {
		String proids = "(";

		for (Product p : firsatPakets) {
			proids += p.getId() + ",";
		}
		proids = proids.substring(0, proids.length() - 1) + ")";
		List<Productphoto> productphotos;
		productphotos = Db
				.select("select * from productphoto where productid in "
						+ proids + " ", Productphoto.class);
		photos = new HashMap<>();
		for (Productphoto pp : productphotos) {

			photos.put(pp.getProductid(), pp.getFile());
		}
	}

	public List<Product> getFirsatPakets() {
		return firsatPakets;
	}

	public void setFirsatPakets(List<Product> firsatPakets) {
		this.firsatPakets = firsatPakets;
	}

	private static final long serialVersionUID = -7626324143300709074L;

}
