package fazlastoks;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import model.Product;

@ViewScoped
@ManagedBean
public class Pro implements Serializable {

	private Product pro;

	public Product getPro() {
		return pro;
	}

	public void setPro(Product pro) {
		this.pro = pro;
	}

	public Pro() {

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2018425860394584424L;

}