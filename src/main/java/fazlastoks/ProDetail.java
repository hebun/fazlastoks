package fazlastoks;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import freela.util.FaceUtils;
import model.Product;

@ViewScoped
@ManagedBean(name = "prodetail")
public class ProDetail implements Serializable {

	Product pro;
	
	public ProDetail(){
		this.pro = FaceUtils.getObjectFromGETParam("id", Product.class,
				"product");
		
	}

	public Product getPro() {
		return pro;
	}

	public void setPro(Product pro) {
		this.pro = pro;
	}

	private static final long serialVersionUID = 2647447812313684383L;

}
